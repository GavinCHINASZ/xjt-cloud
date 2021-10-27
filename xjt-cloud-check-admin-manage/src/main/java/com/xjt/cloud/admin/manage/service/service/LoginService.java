package com.xjt.cloud.admin.manage.service.service;

import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;


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
     * @param user
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data login(User user);


    /**
     *
     * 功能描述:注销登录
     *
     * @param user
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data logout(User user);

    /**
     *
     * 功能描述:通过用户名密码登录方法
     *
     * @param loginName
     * @param password
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    Data userLogin(String loginName, String password);

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

}
