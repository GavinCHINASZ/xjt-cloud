package com.xjt.cloud.safety.core.dao.sys;

import com.xjt.cloud.safety.core.entity.project.User;
import org.apache.ibatis.annotations.Param;
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
     * 功能描述: 判断手机号码是否已存在
     *
     * @param sql
     * @return: List<User>
     * @auther: wangzhiwen
     * @date: 2019/9/6 13:40
     */
    List<User> findUserByPhones(@Param(value = "sql") String sql);

    /**
     * 功能描述:新增成员:手动添加
     * @Author huanggc
     * @Date 2019/5/27
     * @param user
     * @return int
     */
    Integer saveUser(User user);

    /**
     *
     * 功能描述: 批量保存用户信息
     *
     * @param list
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/9/6 13:56
     */
    Integer saveUserList(List<User> list);

    /**@MethodName: findByUser 查询用户信息
     * @Description:
     * @Param: [user]
     * @Return: com.xjt.cloud.project.core.entity.User
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:57
     **/
    User findByUser(User user);


    /**@MethodName: updateUserProject 修改用户信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 9:58
     **/
    void updateUser(User user);

    /**@MethodName: updateUserStatus
     * @Description: 修改用户状态
     * @Param: [userIds]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/16 15:48
     **/
    void updateUserStatus(@Param("userIds") Long[] userIds);
}
