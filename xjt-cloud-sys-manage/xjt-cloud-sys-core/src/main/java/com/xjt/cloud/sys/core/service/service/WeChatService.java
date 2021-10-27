package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 16:52
 * @Description:微信登录逻辑接口
 */
public interface WeChatService extends BaseService {

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
    Data loginWeChatCallback(String json, String loginType);

    /**
     *
     * 功能描述:微信用户登录以手机号码绑定用户信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/31 17:37
     */
    Data loginWeChatBindUser(String json,String cloudType);
}
