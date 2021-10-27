package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.thread.ThreadPoolHttpGet;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.common.utils.ErrCode;
import com.xjt.cloud.sys.core.dao.project.PermissionDao;
import com.xjt.cloud.sys.core.dao.sys.UserDao;
import com.xjt.cloud.sys.core.entity.Permission;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.service.service.LoginService;
import com.xjt.cloud.sys.core.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * 逻辑入口接口中实现类
 *
 * @author wangzhiwen
 * @date 2019/4/24 17:56
 */
@Service
public class LoginServiceImpl extends AbstractService implements LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private AuthorizationParam authorizationParam;
    @Autowired
    private UserService userService;

    /**
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @param json 参数
     * @param cloudType cloudType
     * @return Data
     * @author wangzhiwen
     * @date 2019/7/10 18:06
     */
    @Override
    public Data login(String json, String cloudType){
        JSONObject jsonObject = JSON.parseObject(json);
        String loginName = jsonObject.getString("loginName");
        Integer captchaNum = redisUtils.getInteger(loginName + "_captcha_num");

        if (null == captchaNum){
            captchaNum = 0;
        }else{
            /*if (3 <= captchaNum) {//判断是否是三次以上密码错误
                String code = jsonObject.getString("captcha");//验证码
                if (StringUtils.isEmpty(code)){
                    return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_NULL_FAIL);
                }
                String loginCode = redisUtils.getString(loginName + "_captcha");//从缓存中取出验证码
                if (StringUtils.isEmpty(loginCode)){
                    return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_EXPIRE);
                }
                if (!loginCode.equals(code.toUpperCase())) {//判断验证码是否正确
                    return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_FAIL);
                }
            }*/
        }

        String password = jsonObject.getString("password");

        Data data = isCanLogin(loginName, password);
        // 判断用户名密码是否验证成功
        if (Constants.SUCCESS_CODE != data.getStatus()){
            captchaNum += 1;
            data.setObject(captchaNum);
            redisUtils.set(loginName + "_captcha_num", captchaNum, Constants.TEN_TIME_SECONDS);
            return data;
        }
        return userLogin(loginName, password,cloudType);
    }

    /**
     * 功能描述:通过用户名密码登录方法
     *
     * @param loginName 用户名
     * @param password 密码
     * @param cloudType cloudType
     * @return Data
     * @author wangzhiwen
     * @date 2019/7/10 18:06
     */
    @Override
    public Data userLogin(String loginName, String password, String cloudType){
        //组装获取token信息
        //授权请求信息
        MultiValueMap<String, String> formDataMap = new LinkedMultiValueMap<>(4);
        formDataMap.put("username", Collections.singletonList(loginName));
        formDataMap.put("password", Collections.singletonList(password));
        formDataMap.put("grant_type", Collections.singletonList(authorizationParam.getAuthorizedGrantTypes()));
        formDataMap.put("scope", Collections.singletonList("write"));

        User user = new User();
        user.setLoginName(loginName);
        user = userDao.getUser(user);

        JSONObject jsonObjectToken = restTemplateCasServer(formDataMap);

        if (user.getProjectId() != null && user.getProjectId() > 0){
            jsonObjectToken.put("project",CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, user.getProjectId()));
        }
        userInfoCacheInit(JSONObject.toJSONString(user));
        String socketId = System.nanoTime() + "";
        user.setSocketId(socketId);
        jsonObjectToken.put("user", user);
        if (StringUtils.isNotEmpty(ConstantsClient.REMOVE_ACCESS_TOKEN) && "true".equals(ConstantsClient.REMOVE_ACCESS_TOKEN)) {
            try {
                String key = user.getLoginName() + "_WEBSOCKET_" + cloudType + "_";
                List<String> loginSocketIdList = redisUtils.getStrings(key + "*");
                if (CollectionUtils.isNotEmpty(loginSocketIdList)) {
                    for (String s : loginSocketIdList) {
                        String[] loginInfo = s.split("_");
                        if (!socketId.equals(loginInfo[2])) {
                            JSONObject sendJson = new JSONObject(3);
                            sendJson.put("channelId", loginInfo[0]);
                            sendJson.put("url", loginInfo[1]);
                            sendJson.put("msg", 240);
                            ThreadPoolHttpGet.httpGet(sendJson);
                        }
                    }
                }
            } catch (Exception ex) {
                SysLog.error("登录socket信息发送错误");
            }
        }
        if(0 == user.getStatus()){
            // 如果用户是未激活状态，则改成激活
            userDao.updateUserStatus(user);
        }
        return asseData(jsonObjectToken);
    }

    /**
     * 功能描述:token刷新接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/7/13 11:13
     */
    @Override
    public Data accessRefresh(String json){
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            SysLog.info("刷新token=" + json);
            // 解析异常，如果是401则处理
            // 组装刷新token的参数
            MultiValueMap<String, String> formDataMap = new LinkedMultiValueMap<>(4);
            formDataMap.add("client_id", authorizationParam.getClientId());
            formDataMap.add("client_secret", authorizationParam.getSecret());
            formDataMap.add("grant_type", "refresh_token");
            //从参数中得到token信息
            formDataMap.add("refresh_token", jsonObject.getString("accessToRefresh"));

            return asseData(restTemplateCasServer(formDataMap));
        } catch (Exception e) {
            SysLog.error(e);
        }
        return Data.isFail();
    }

    /**
     * 功能描述:cas server请求
     *
     * @param formDataMap formDataMap
     * @return com.alibaba.fastjson.JSONObject
     * @author wangzhiwen
     * @date 2020/7/13 14:33
     */
    private JSONObject restTemplateCasServer(MultiValueMap<String, String> formDataMap){
        // Http Basic 验证
        String clientAndSecret = authorizationParam.getClientId() + ":" + authorizationParam.getSecret();
        // 这里需要注意为 Basic 而非 Bearer 注意：Basic  与密码串之间为一个空格
        clientAndSecret = "Basic "+ Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",clientAndSecret);
        // HttpEntity
        HttpEntity httpEntity = new HttpEntity(formDataMap, httpHeaders);
        // 获取 Token
        RestTemplate restTemplate = new RestTemplate();
        Object mapToken = restTemplate.exchange(authorizationParam.getCasServerHostUrl(), HttpMethod.POST,httpEntity,Object.class);
        String tokenStr = mapToken.toString();
        tokenStr = tokenStr.replaceAll(", ",",");
        tokenStr = tokenStr.substring(tokenStr.indexOf("{"), tokenStr.indexOf("}") + 1);
        JSONObject jsonObjectToken = StringUtils.mapStringToJson(tokenStr);
        return jsonObjectToken;
    }

    /**
     * 功能描述:生成登录的验证码
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/13 14:41
     */
    @Override
    public Data loginGenerateCaptcha(String json){
        JSONObject jsonObject = JSON.parseObject(json);
        try {
            // 生成验证码
            String captcha = StringUtils.generateRanNum(4);
            String loginName = jsonObject.getString("loginName");
            redisUtils.set(loginName + "_captcha", captcha.toUpperCase(), Constants.TEN_TIME_SECONDS);
            String imgStr = ImgUtils.generateCaptcha(captcha);
            return asseData(imgStr);
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return Data.isFail();
    }

    /**
     * 功能描述: 初使化用户权限信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2019/12/6 10:31
     */
    private void userInfoCacheInit(String json){
        // 取得用户的所有权限信息
        try {
            if (!"true".equals(ConstantsClient.IS_PERMISSION_NOT_INIT)) {
                getUserPermissionList(json);
            }
            userService.getUser(json);
        }catch (Exception ex){
            SysLog.error(ex);
            throw BaseServiceException.initException(ConstantsMsg.GET_USER_PERMISSION_FAIL,ServiceErrCode.REQ_PERMISSION_ERR.getCode());
        }
    }

    /**
     * 功能描述:注销登录
     *
     * @param json 参数
     * @return Data
     * @author wangzhiwen
     * @date 2019/7/10 18:06
     */
    @Override
    public Data logout(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        String loginName = jsonObject.getString("loginName");
        String accessToken = jsonObject.getString("accessToken");
        //redisUtils.del("userPermissionList_" + authorizationParam.getProjectType() + "_" + loginName + "_*");
        redisUtils.del("auth:" + accessToken);
        redisUtils.del("access:" + accessToken);
        redisUtils.del("access_to_refresh:" + accessToken);
        /*TokenStore tokenStore = SpringBeanUtil.getBean(TokenStore.class);
        //清除原有登录信息
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(authorizationParam.getClientId(), loginName);
        if (CollectionUtils.isNotEmpty(tokens)) {
            for (OAuth2AccessToken oauth2AccessToken : tokens) {
                //if (accessToken.equals(oauth2AccessToken.))
                tokenStore.removeAccessToken(oauth2AccessToken);
                tokenStore.removeRefreshToken(oauth2AccessToken.getRefreshToken());
            }
        }*/
        return Data.isSuccess();
    }

    /**
     * 功能描述:手机验证码登录发送验证码接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/2 14:43
     */
    @Override
    public Data loginSendCaptcha(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(phone)){
            return asseData(ServiceErrCode.REQ_PARAM_ERR);
        }
        // 生成验证码
        String captcha = StringUtils.generateRanNum(4);
        // 发送验证码信息
        boolean b = TaoBaoSendMsg.sendMsg(phone,"{\"code\":\"" + captcha + "\"}");
        if (!b){
            return asseData(ServiceErrCode.SERVER_ERR.getCode(), ConstantsClient.SEND_MSG_FAIL);
        }
        String key = "key_" + System.nanoTime();
        redisUtils.set(key, captcha + "_" + phone, Constants.TEN_TIME_SECONDS);
        return asseData(key);
    }

    /**
     * 功能描述:手机验证码登录接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/2 14:43
     */
    @Override
    public Data loginCaptcha(String json, String cloudType){
        JSONObject jsonObject = JSONObject.parseObject(json);
        String key = jsonObject.getString("key");
        String phone = jsonObject.getString("phone");
        String captcha = jsonObject.getString("captcha");
        String password = jsonObject.getString("password");

        Object obj = redisUtils.get(key);
        if (null != obj){
            // 判断信息是否存在
            String[] str = obj.toString().split("_");
            if (!phone.equals(str[1])){
                // 判断是否是同一个手机号码
                return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.PHONE_FAIL);
            }
            if (!str[0].equals(captcha)){
                //判断验证码是否正确
                return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_FAIL);
            }

            long keyTime = Long.parseLong(key.split("_")[1]);
            long time = System.nanoTime();
            // 判断二维码时间是否大于10分钟
            if ((time - keyTime) / 1000000000 < Constants.TEN_TIME_SECONDS) {
                // 判断用户是否存在，如不存在直接注册
                User user = userService.isUserExistByPhone(phone,phone, password,true);
                if (user == null) {
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), ConstantsClient.REGISTER_FAIL);
                }
                Boolean registerStatus = user.getRegisterStatus();
                Data data = userLogin(user.getLoginName(), user.getPassword(),cloudType);
                //判断是否是第一次注册，如是，返回注册标志
                if (data.getStatus() == Constants.SUCCESS_CODE && registerStatus != null && registerStatus){
                    data.setMsg("register");
                }
                return data;
            }else{
                return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), ConstantsClient.CAPTCHA_EXPIRE);
            }
        }
        return asseData(ServiceErrCode.NOTFOUND_RESULT_ERR);
    }

    /**
     * 功能描述: 判断用户是否能登录，验证用户是否存在，与密码验证
     *
     * @param loginName 用户名
     * @param password 密码
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/12 17:33
     */
    private Data isCanLogin(String loginName, String password){
        User user = new User();
        user.setLoginName(loginName);
        user = userDao.findLoginUser(user);
        if (null == user){
            return asseData(ErrCode.NOT_USER.getCode(), ErrCode.NOT_USER.getMsg());
        }else{
            // 判断密码是否正确
            PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
            boolean b = passwordEncoder.matches(password, user.getPassword());
            if (!b){
                return asseData(ErrCode.PASSWORD_ERR.getCode(), ErrCode.PASSWORD_ERR.getMsg());
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述:以缓存的key清除缓存
     *
     * @param keys key字符串数组 逗号,分隔
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/29 15:12
     */
    @Override
    public Data clearCache(String keys){
        if (StringUtils.isNotEmpty(keys)){
            String[] key = keys.split(",");
            for (String str : key){
                if (StringUtils.isNotEmpty(str)){
                    redisUtils.dels(str);
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 查询用户权限列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/22 16:02
     */
    @Override
    public Data getUserPermissionList(String json) {
        /*User user = JSONObject.parseObject(json, User.class);
        Long userId = user.getId();
        String loginName = user.getLoginName();
        if (null == userId){
            userId = getLoginUserId(loginName);
        }
        String key = "userPermissionList_" + authorizationParam.getProjectType() + "_" + loginName + "_";
        redisUtils.dels(key + "*");

        List<Permission> list;
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            list = permissionDao.getUserPermissionListCV5(userId, authorizationParam.getProjectType());
        }else{
            list = permissionDao.getUserPermissionList(userId, authorizationParam.getProjectType());
        }*/
        /*if (CollectionUtils.isNotEmpty(list)){
            StringBuilder urls = new StringBuilder();
            Permission permission;
            Long projectId;
            int size = list.size();
            for (int i = 0;i < size;i++){
                permission = list.get(i);
                projectId = permission.getProjectId();
                if (projectId != null && projectId > 0){
                    urls.append("," + permission.getUrl() + "/" + projectId + ",");
                }else{
                    urls.append("," + permission.getUrl()+ ",");
                }

                if (i == size - 1 || !projectId.equals(list.get(i + 1).getProjectId())){
                    redisUtils.set( key + projectId, urls, Constants.CACHE_CANCEL);
                    urls = new StringBuilder();
                }
            }
        }*/
        return Data.isSuccess();
    }

    /**
     * 功能描述: 查询所有菜单权限列表
     *
     * @param isClear isClear
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/22 16:02
     */
    @Override
    public Data getPermissionList(Boolean isClear) {
        String permissionList = redisUtils.getString("permissionList_" + authorizationParam.getProjectType());
        boolean clearBoolean = (isClear != null && isClear);
        if (StringUtils.isEmpty(permissionList) || clearBoolean) {
            List<String> list = permissionDao.getPermissionList(authorizationParam.getProjectType());
            String jsonStr = JSON.toJSONString(list);
            redisUtils.set("permissionList_" + authorizationParam.getProjectType(), jsonStr, Constants.CACHE_CANCEL);
        }
        return Data.isSuccess();
    }
}