package com.xjt.cloud.admin.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.utils.ErrCode;
import com.xjt.cloud.admin.manage.dao.sys.UserDao;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.admin.manage.service.service.LoginService;
import com.xjt.cloud.admin.manage.service.service.project.PermissionService;
import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;

/**
 * netty逻辑入口接口中实现类
 *
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:56
 */
@Service
public class LoginServiceImpl extends AbstractService implements LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorizationParam authorizationParam;
    @Autowired
    private PermissionService permissionService;

    /**
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @param user
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @Override
    public Data login(User user) {
        String loginName = user.getLoginName();
        String password = user.getPassword();

        Data data = isCanLogin(loginName, password);
        if (Constants.SUCCESS_CODE != data.getStatus()) {//判断用户名密码是否验证成功
            return data;
        }
        return userLogin(loginName, password);
    }

    /**
     * 功能描述:通过用户名密码登录方法
     *
     * @param loginName
     * @param password
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @Override
    public Data userLogin(String loginName, String password) {
        //组装获取token信息
        //Http Basic 验证
        String clientAndSecret = authorizationParam.getClientId() + ":" + authorizationParam.getSecret();
        //这里需要注意为 Basic 而非 Bearer 注意：Basic  与密码串之间为一个空格
        clientAndSecret = "Basic " + Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", clientAndSecret);
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginName));
        map.put("password", Collections.singletonList(password));
        map.put("grant_type", Collections.singletonList(authorizationParam.getAuthorizedGrantTypes()));
        map.put("scope", Collections.singletonList("write"));
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);
        //获取 Token
        RestTemplate restTemplate = new RestTemplate();
        Object mapToken = restTemplate.exchange(authorizationParam.getCasServerHostUrl(), HttpMethod.POST, httpEntity, Object.class);
        String tokenStr = mapToken.toString();
        tokenStr = tokenStr.substring(tokenStr.indexOf("{"), tokenStr.indexOf("}") + 1);
        JSONObject jsonObjectToken = StringUtils.mapStringToJson(tokenStr);
        User user = new User();
        user.setLoginName(loginName);
        user = userDao.getUser(user);
        jsonObjectToken.put("user", user);
        permissionService.getPermissionList(false);
        permissionService.getUserPermissionList(JSONObject.toJSONString(user));
        return asseData(jsonObjectToken);
    }

    /**
     * 功能描述:注销登录
     *
     * @param user
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @Override
    public Data logout(User user) {
        String loginName = user.getLoginName();
        String accessToken = user.getAccessToken();
        redisUtils.del(loginName);
        redisUtils.del("userPermissionList_3_" + loginName + "_0");
        redisUtils.del("auth:" + accessToken);
        redisUtils.del("access:" + accessToken);
        redisUtils.del("access_to_refresh:" + accessToken);
        return Data.isSuccess();
    }

    /**
     * 功能描述: 判断用户是否能登录，验证用户是否存在，与密码验证
     *
     * @param loginName
     * @param password
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/12 17:33
     */
    private Data isCanLogin(String loginName, String password) {
        User user = new User();
        user.setLoginName(loginName);
        user = userDao.findLoginUser(user);
        if (null == user) {
            return asseData(ErrCode.NOT_USER.getCode(), ErrCode.NOT_USER.getMsg());
        } else {
            PasswordEncoder passwordEncoder = new StandardPasswordEncoder();//判断密码是否正确
            boolean b = passwordEncoder.matches(password, user.getPassword());
            if (!b) {
                return asseData(ErrCode.PASSWORD_ERR.getCode(), ErrCode.PASSWORD_ERR.getMsg());
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述:以缓存的key清除缓存
     *
     * @param keys
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/29 15:12
     */
    @Override
    public Data clearCache(String keys) {
        if (StringUtils.isNotEmpty(keys)) {
            String[] key = keys.split(",");
            for (String str : key) {
                if (StringUtils.isNotEmpty(str)) {
                    redisUtils.dels(str);
                }
            }
        }
        return Data.isSuccess();
    }
}
