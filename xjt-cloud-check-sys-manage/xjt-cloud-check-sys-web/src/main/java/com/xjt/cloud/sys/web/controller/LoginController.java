package com.xjt.cloud.sys.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:系统用户控制类
 */
@RestController
public class LoginController extends AbstractController {

    @Autowired
    private LoginService loginService;

    /**
     *
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @param json
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/login")
    public Data login(String json){
        return loginService.login(json,"WEB");
    }

    /**
     *
     * 功能描述:生成登录的验证码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 14:41
     */
    @RequestMapping(value = "/loginGenerateCaptcha")
    public Data loginGenerateCaptcha(String json){
        return loginService.loginGenerateCaptcha(json);
    }

    /**
     *
     * 功能描述:token刷新接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/13 11:13
     */
    @RequestMapping(value = "/accessRefresh")
    public Data accessRefresh(String json){
        return loginService.accessRefresh(json);
    }

    /**
     *
     * 功能描述:注销登录
     *
     * @param json
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/userLogout")
    public Data logout(String json){
        return loginService.logout(json);
    }


    /**
     *
     * 功能描述:手机验证码登录发送验证码接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/2 14:43
     */
    @RequestMapping(value = "loginSendCaptcha")
    public Data loginSendCaptcha(String json){
        return loginService.loginSendCaptcha(json);
    }

    /**
     *
     * 功能描述:手机验证码登录接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/2 14:43
     */
    @RequestMapping(value = "loginCaptcha")
    public Data loginCaptcha(String json){
        return loginService.loginCaptcha(json,"WEB");
    }

    /**
     *
     * 功能描述:以缓存的key清除缓存
     *
     * @param keys
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/29 15:12
     */
    @RequestMapping(value = "clearCache")
    public Data clearCache(String keys){
        return loginService.clearCache(keys);
    }

    /**
     * 功能描述:获取所有权限
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/permission/getPermissionList")
    public Data getPermissionList(Boolean isClear){
        return loginService.getPermissionList(isClear);
    }

    /**
     * 功能描述:获取用户权限
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/permission/getUserPermissionList")
    public Data getUserPermissionList(String json){
        return loginService.getUserPermissionList(json);
    }
}
