package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/29 11:39
 * @Description:
 */
@RestController
@RequestMapping("/user/")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    /**
     * 功能描述:获取用户信息
     * @Author huanggc
     * @Date 2019/5/23
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "getUser")
    public Data getUser(String json){
        return userService.getUser(json);
    }

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUser")
    public Data modifyUser(String json){
        return userService.modifyUser(json);
    }

    /**
     *
     * 功能描述:修改用户登录用户名
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUserLoginName")
    public Data modifyUserLoginName(String json){
        return userService.modifyUserLoginName(json);
    }

    /**
     *
     * 功能描述:修改用户信息发送验证码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUserSendCaptcha")
    public Data modifyUserSendCaptcha(String json){
        return userService.modifyUserSendCaptcha(json);
    }

    /**
     *
     * 功能描述:修改用户手机号码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUserPhone")
    public Data modifyUserPhone(String json){
        return userService.modifyUserPhone(json);
    }

    /**
     *
     * 功能描述:修改用户密码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUserPassword")
    public Data modifyUserPassword(String json){
        return userService.modifyUserPassword(json);
    }




    /**
     * 功能描述:获取用户信息
     * @Author huanggc
     * @Date 2019/5/23
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/loginTest")
    public Data test(String json){
        return userService.transactionTest(json);
    }

    /**
     * @Description 以经伟度得到百度地址信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/10/9 14:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "getAddressByLngLat")
    public Data getAddressByLngLat(String json){
        return userService.getAddressByLngLat(json);
    }
}
