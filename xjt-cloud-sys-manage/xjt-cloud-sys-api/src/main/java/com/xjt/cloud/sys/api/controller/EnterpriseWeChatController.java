package com.xjt.cloud.sys.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.sys.core.service.service.EnterpriseWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/3/12 09:42
 * @Description: 企业微信管理
 */
@RestController
@RequestMapping("/enterprise/we/chat/")
public class EnterpriseWeChatController extends AbstractController {

    @Autowired
    private EnterpriseWeChatService enterpriseWeChatService;

    /**
     *
     * 功能描述: 企业微信授权回调接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/3/12 9:52
     */
    @RequestMapping(value = "authorizeCallback")
    public Data authorizeCallback(String json){
        Data data = enterpriseWeChatService.authorizeCallback(json,"APP");
        SysLog.error("企业微信回调返回结果:" + JSONObject.toJSONString(data));
        return data;
    }

    /**
     *
     * 功能描述:获取企业微信token
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/3/17 9:33
     */
    @RequestMapping(value = "getToken")
    public String getToken(boolean isClear){
        return enterpriseWeChatService.getToken(isClear);
    }

    /**
     *
     * 功能描述:获取企业微信权限签名
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/3 14:14
     */
    @RequestMapping(value = "getJsapiTicket")
    public String getJsapiTicket(boolean isClear, boolean isAgent){
        return enterpriseWeChatService.getJsapiTicket(isClear,isAgent);
    }

    /**
     *
     * 功能描述: 获取签名
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/3 14:39
     */
    @RequestMapping(value = "getAgentSign")
    public JSONObject getAgentSign(String json){
        return enterpriseWeChatService.getSign(json,true);
    }

    /**
     *
     * 功能描述: 获取签名
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/3 14:39
     */
    @RequestMapping(value = "getSign")
    public JSONObject getSign(String json){
        return enterpriseWeChatService.getSign(json,false);
    }

    /**
     *
     * 功能描述:以用户id数组得到用户登录账号
     *
     * @param userIds
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/3/17 11:24
     */
    @RequestMapping(value = "getUserLoginNames")
    public String getUserLoginNames(String[] userIds){
        return enterpriseWeChatService.getUserLoginNames(userIds);
    }
}
