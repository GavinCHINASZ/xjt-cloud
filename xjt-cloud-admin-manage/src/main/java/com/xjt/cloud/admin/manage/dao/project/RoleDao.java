package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/6 15:15
 * @Description: 角色管理
 */
@Repository
public interface RoleDao {

    /////////////////////////////////////////////////// 角色权限//////////////////////////////////////////////////////////
    /**
     *
     * 功能描述:查询角色列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:37
     */
    List<Role> findRoleList(Role role);

    /**
     *
     * 功能描述:查询角色列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:37
     */
    Integer findRoleListTotalCount(Role role);

    /**
     *
     * 功能描述:保存角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    int saveRole(Role role);

    /**
     *
     * 功能描述:修改角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    int modifyRole(Role role);

    /**
     *
     * 功能描述:修改角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    int delRole(Role role);

    /////////////////////////////////////////////////用户角色管理 ////////////////////////////////////////////////////////

    /**
     *
     * 功能描述:查询已关联用户角色信息列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Role> findUserRelationRoleList(Role role);

    /**
     *
     * 功能描述:查询已关联用户角色信息列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findUserRelationRoleListTotalCount(Role role);

    /**
     *
     * 功能描述:查询未关联用户角色信息列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Role> findUserNotRelationRoleList(Role role);

    /**
     *
     * 功能描述:查询未关联用户角色信息列表
     *
     * @param role
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findUserNotRelationRoleListTotalCount(Role role);

    /**
     *
     * 功能描述: 保存用户角色
     *
     * @param list
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int saveUserRelationRole(List<Role> list);

    /**
     *
     * 功能描述: 删除用户角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int delUserRelationRole(Role role);


    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param role
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Role> findUserRelationAdminRoleList(Role role);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param role
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findUserRelationAdminRoleListCount(Role role);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param role
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Role> findUserNotRelationAdminRoleList(Role role);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param role
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findUserNotRelationAdminRoleListCount(Role role);
}
