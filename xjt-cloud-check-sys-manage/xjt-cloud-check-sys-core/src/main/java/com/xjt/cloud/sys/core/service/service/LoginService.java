package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.entity.User;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:55
 * @Description:netty逻辑入口接口
 */
public interface LoginService extends BaseService {
    /**
     *
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @param json
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data login(String json,String cloudType);

    /**
     *
     * 功能描述:token刷新接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/13 11:13
     */
    Data accessRefresh(String json);

    /**
     *
     * 功能描述:生成登录的验证码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 14:41
     */
    Data loginGenerateCaptcha(String json);



    /**
     *
     * 功能描述:注销登录
     *
     * @param json
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data logout(String json);

    /**
     *
     * 功能描述:通过用户名密码登录方法
     *
     * @param loginName
     * @param password
     * @param cloudType 平台类型
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data userLogin(String loginName, String password,String cloudType);

    /**
     *
     * 功能描述:手机验证码登录发送验证码接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/2 14:43
     */
    Data loginSendCaptcha(String json);

    /**
     *
     * 功能描述:手机验证码登录接口
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/2 14:43
     */
    Data loginCaptcha(String json,String cloudType);

    /**
     *
     * 功能描述:以缓存的key清除缓存
     *
     * @param keys
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/29 15:12
     */
    Data clearCache(String keys);

    /**
     *
     * 功能描述: 查询用户权限列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    Data getUserPermissionList(String json);

    /**
     *
     * 功能描述: 查询所有菜单权限列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    Data getPermissionList(Boolean isClear);
}
