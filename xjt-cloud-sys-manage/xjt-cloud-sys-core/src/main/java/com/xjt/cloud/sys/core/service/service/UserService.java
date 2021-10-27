package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.entity.User;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/29 11:41
 * @Description:
 */
public interface UserService {
    /**
     * 功能描述:查询用户信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/23
     */
    Data getUser(String json);

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUser(String json);

    /**
     *
     * 功能描述:修改用户登录用户名
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUserLoginName(String json);

    /**
     *
     * 功能描述:修改用户信息发送验证码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUserSendCaptcha(String json);

    /**
     *
     * 功能描述:修改用户手机号码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUserPhone(String json);

    /**
     *
     * 功能描述:修改用户密码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUserPassword(String json);

    /**
     * 功能描述:查询用户信息
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/23
     */
    Data transactionTest(String json);

    /**
     *
     * 功能描述:用户注册
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/8/1 9:56
     */
    int registerUser(User user);

    /**
     *
     * 功能描述: 以手机号码查询是否存在该手机号码或登录名的用户
     *
     * @param phone
     * @param loginName 登录用户名
     * @param isRegister 如不存在是否要注册
     * @param password 密码
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/4 16:58
     */
    User isUserExistByPhone(String phone, String loginName,String password, boolean isRegister);

    /**
     * 功能描述:查询主题颜色
     *
     * @author huanggc
     * @date 2020/09/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findThemeColor();

    /**
     * @Description 以经伟度得到地址信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/10/9 14:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data getAddressByLngLat(String json);
}
