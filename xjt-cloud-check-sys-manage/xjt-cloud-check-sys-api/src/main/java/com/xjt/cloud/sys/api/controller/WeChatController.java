package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 16:47
 * @Description:微信登录管理
 */
@RestController
@RequestMapping("/we/chat/")
public class WeChatController extends AbstractController {
    @Autowired
    private WeChatService weChatService;

    /**
     *
     * 功能描述:微信登录回调方法
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/31 17:36
     */
    @RequestMapping(value = "loginWeChatCallback")
    public Data loginWeChatCallback(String json){
        return weChatService.loginWeChatCallback(json, "WEB");
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
    @RequestMapping(value = "loginWeChatBindUser")
    public Data loginWeChatBindUser(String json){
        return weChatService.loginWeChatBindUser(json,"WEB");
    }
}
