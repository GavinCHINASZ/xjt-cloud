package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.User;
import org.springframework.stereotype.Repository;

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
     * 功能描述:修改用户信息
     *
     * @param user 用户实体
     * @return int 修改成功数量
     * @author wangzhiwen
     * @date 2019/11/25 9:37
     */
    int modifyUser(User user);

    /**
     *
     * 功能描述:修改用户登录用户名
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    int modifyUserLoginName(User user);

    /**
     *
     * 功能描述:修改用户手机号码
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    int modifyUserPhone(User user);

    /**
     *
     * 功能描述:修改用户密码
     *
     * @param user
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    int modifyUserPassword(User user);

    /**
     * 功能描述:根据用户ID查询实体
     * @Author huanggc
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    void saveTest();
    /**
     * 功能描述:根据用户ID查询实体
     * @Author huanggc
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    void saveTest1();

    /**
     *
     * 功能描述: 修改用户状态
     *
     * @param user
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/9/5 10:21
     */
    int updateUserStatus(User user);

    /**
     *
     * 功能描述: 用户注册
     *
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/4 11:15
     */
    int registerUser(User user);

    /**
     *
     * 功能描述: 判断该登录名称与手机号码是否已存在
     *
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/3 18:15
     */
    User findUserByLoginNameOrPhone(User user);

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
}
