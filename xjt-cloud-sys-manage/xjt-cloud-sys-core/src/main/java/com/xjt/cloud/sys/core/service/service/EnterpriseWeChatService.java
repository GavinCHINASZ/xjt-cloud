package com.xjt.cloud.sys.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/3/12 09:43
 * @Description: 企业微信管理
 */
public interface EnterpriseWeChatService {

    /**
     *
     * 功能描述: 企业微信授权回调接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/3/12 9:52
     */
    Data authorizeCallback(String json,String cloudType);

    /**
     *
     * 功能描述:获取企业微信token
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/3/17 9:33
     */
    String getToken(boolean isClear);

    /**
     *
     * 功能描述:获取企业微信权限签名
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/3 14:14
     */
    String getJsapiTicket(boolean isClear, boolean isAgent);

    /**
     *
     * 功能描述: 获取签名
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/3 14:39
     */
    JSONObject getSign(String json,boolean isAgent);

    /**
     *
     * 功能描述:以用户id数组得到用户登录账号
     *
     * @param userIds
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/3/17 11:24
     */
    String getUserLoginNames(String[] userIds);
}
