package com.xjt.cloud.maintenance.core.service.service.sys;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.entity.project.User;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/6 17:10
 * @Description:
 */
public interface UserService {
    /**
     * 功能描述:新增成员:手动添加
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2019/5/27
     */
    Data saveUser(String json);

    /**
     *
     * 功能描述:保存用户信息
     *
     * @param list
     * @param userNameStr
     * @return: java.util.List<com.xjt.cloud.project.core.entity.User>
     * @auther: wangzhiwen
     * @date: 2019/9/6 13:57
     */
    List<User> saveUser(List<User> list, String userNameStr);

    /**@MethodName: findByUser 查询用户信息
     * @Description: 
     * @Param: [user]
     * @Return: com.xjt.cloud.project.core.entity.User
     * @Author: zhangZaiFa
     * @Date:2019/9/29 13:56 
     **/
    User findByUser(User user);

    /**@MethodName: updateUserProject 修改用户项目信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/12 9:58
     **/
    Data updateUserProject(User user);
}
