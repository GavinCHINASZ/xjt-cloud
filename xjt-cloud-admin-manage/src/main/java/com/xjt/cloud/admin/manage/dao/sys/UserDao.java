package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/29 11:52
 * @Description:
 */
@Repository
public interface UserDao {

    /**
     * 功能描述:根据用户ID查询实体
     * @Author huanggc
     * @Date 2019/5/23
     * @param user
     * @return com.xjt.cloud.commons.utils.Data
     */
    User getUser(User user);

    /**
     * 功能描述:查询用户列表
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @param user
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<User> findUserList(User user);

    /**
     * 功能描述:查询用户总数
     * @Author wangzhiwen
     * @Date 2019/5/23
     * @param user
     * @return com.xjt.cloud.commons.utils.Data
     */
    Integer findUserListTotalCount(User user);

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    int modifyUser(User user);
    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    int saveUser(User user);

    /**
     *
     * 功能描述: 查询登录用户信息
     *
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/3 18:15
     */
    User findLoginUser(User user);

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
