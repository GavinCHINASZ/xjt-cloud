package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.dao.sys.WeChatDao;
import com.xjt.cloud.sys.core.entity.User;
import com.xjt.cloud.sys.core.entity.WeChatUser;
import com.xjt.cloud.sys.core.service.service.LoginService;
import com.xjt.cloud.sys.core.service.service.UserService;
import com.xjt.cloud.sys.core.service.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 16:52
 * @Description: 微信登录逻辑接口实现类
 */
@Service
public class WeChatServiceImpl extends AbstractService implements WeChatService {

    @Autowired
    private WeChatDao weChatDao;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    //app微信登录 微信公众号信息
    @Value("${we.chat.app.secret}")
    private String secret;
    @Value("${we.chat.app.app.id}")
    private String appId;
    //pc微信登录 微信公众号信息
    @Value("${we.chat.web.secret}")
    private String secretWeb;
    @Value("${we.chat.web.app.id}")
    private String appIdWeb;

    /**
     *
     * 功能描述:微信登录回调方法
     *
     * @param json
     * @param loginType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/31 17:36
     */
    @Override
    public Data loginWeChatCallback(String json, String loginType){
        WeChatUser weChatUser = new WeChatUser();
        JSONObject jsonObject = JSONObject.parseObject(json);
        String code = jsonObject.getString("code");
        weChatUser = getWeChatUser(code,weChatUser,loginType);//得到从微信中取到的用户信息

        if (weChatUser != null) {
            WeChatUser oldWeChatUser = weChatDao.findWeChatUserByOpenid(weChatUser.getOpenid());//从数据库中查询该微信用户绑定的用户信息
            if (oldWeChatUser != null) {
                //修改数据库中微信用户信息
                weChatDao.updateWeChatUser(weChatUser);
                if (StringUtils.isNotEmpty(oldWeChatUser.getLoginName())) {//判断微信是否绑定用户
                    User user = userService.isUserExistByPhone(null, oldWeChatUser.getLoginName(), null, false);
                    if (user != null) {
                        return loginService.userLogin(user.getLoginName(), user.getPassword(),loginType);
                    }
                }
            }else {//添加微信绑定用户信息
                int num = weChatDao.saveWeChatUser(weChatUser);
                if (0 == num) {//判断是否添加成功
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), "微信信息绑定失败！");
                }
            }
            return asseData(Constants.NOT_DATA_CODE, weChatUser.getOpenid());
        }else {
            return asseData(ServiceErrCode.REQ_PARAM_ERR.getCode(), "微信信息获取不正确！");
        }
    }

    /**
     *
     * 功能描述:微信用户登录以手机号码绑定用户信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/31 17:37
     */
    @Override
    public Data loginWeChatBindUser(String json,String cloudType){
        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        String phone = jsonObject.getString("phone");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(openid)) {
            return asseData(ServiceErrCode.REQ_PARAM_ERR);
        }
        User user = userService.isUserExistByPhone(phone, phone, null,true);//判断用户是否存在，如不存在直接注册
        if (user == null) {
            return asseData(ServiceErrCode.REQ_ERR.getCode(), ConstantsClient.REGISTER_FAIL);
        }
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setUserId(user.getId());
        weChatUser.setLoginName(user.getLoginName());
        weChatUser.setOpenid(openid);
        int num = weChatDao.updateWeChatUser(weChatUser);
        if (0 == num){//判断是否添加成功
            return asseData(ServiceErrCode.REQ_ERR.getCode(), "修改绑定信息失败！");
        }
        return loginService.userLogin(user.getLoginName(), user.getPassword(),cloudType);
    }
    /**
     * 得到微信用户信息
     * @author wangzhiwe
     * @date 2015-1-30
     * @return
     */
    private WeChatUser getWeChatUser(String code, WeChatUser weChatUser, String loginType){
        String secretStr;
        String appIdStr;
        if (StringUtils.isNotEmpty(loginType) && "APP".equals(loginType.toUpperCase())) {
            secretStr = secret;
            appIdStr = appId;
        }else{
            secretStr = secretWeb;
            appIdStr = appIdWeb;
        }
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appIdStr +"&secret=" + secretStr + "&code=" + code + "&grant_type=authorization_code";
        try {
            JSONObject jsonObject = HttpUtils.httpGet(url);
            weChatUser.setOpenid(jsonObject.getString("openid"));
            weChatUser.setAccessToken(jsonObject.getString("access_token"));
            weChatUser.setUnionid(jsonObject.getString("unionid"));
            weChatUser.setExpiresIn(jsonObject.getInteger("expires_in"));
            SysLog.warn("openid=" + jsonObject.getString("openid"));
            refreshToken(jsonObject.getString("refresh_token"),weChatUser,loginType);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
        return weChatUser;
    }

    /**
     * 延长授权时间
     * @author wangzhiwe
     * @date 2015-1-30
     * @return
     */
    private WeChatUser refreshToken(String refresh_token,WeChatUser weChatUser,String loginType){
        String appIdStr;
        if (StringUtils.isNotEmpty(loginType) && "APP".equals(loginType.toUpperCase())) {
            appIdStr = appId;
        }else{
            appIdStr = appIdWeb;
        }
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                "appid=" + appIdStr +"&refresh_token=" + refresh_token + "&grant_type=refresh_token";
        try {
                JSONObject jsonObject = HttpUtils.httpGet(url);
                weChatUser.setExpiresIn(jsonObject.getInteger("expires_in"));
                weChatUser.setRefreshToken(jsonObject.getString("refresh_token"));
        }catch (Exception e) {
            SysLog.error(e);
        }
        return weChatUser;
    }
}
