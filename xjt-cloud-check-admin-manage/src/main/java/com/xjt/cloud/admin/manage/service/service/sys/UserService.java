package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.commons.utils.Data;

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
     * 功能描述:分页查询用户信息列表
     *
     * @param ajaxPage
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/1/15 9:34
     */
    ScriptPage<User> findUserList(AjaxPage ajaxPage, User user);
    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data modifyUser(User user);

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    Data saveUser(User user);

    /**
     *
     * 功能描述:修改用户密码
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/27 16:15
     */
    Data modifyPassword(User user);

    /**
     *
     * 功能描述: 清除过期数据
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/2 14:31
     */
    void clearData();
}
