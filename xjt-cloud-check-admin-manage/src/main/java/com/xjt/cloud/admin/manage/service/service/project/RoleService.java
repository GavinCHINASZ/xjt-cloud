package com.xjt.cloud.admin.manage.service.service.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.Role;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;


/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/6 15:15
 * @Description: 角色管理
 */
public interface RoleService {

    /////////////////////////////////////////////////// 角色权限//////////////////////////////////////////////////////////
    /**
     *
     * 功能描述:查询角色列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:37
     */
    ScriptPage<Role> findRoleList(AjaxPage ajaxPage,Role role);

    /**
     *
     * 功能描述:保存角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    Data saveRole(Role role);

    /**
     *
     * 功能描述:修改角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    Data modifyRole(Role role);

    /**
     *
     * 功能描述:删除角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    Data delRole(Role role);

    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Permission> findRoleRelationPermissionList(AjaxPage ajaxPage,Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Permission> findNotRoleRelationPermissionList(AjaxPage ajaxPage, Permission permission);

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data saveRoleRelationPermission(Permission permission);

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data delRoleRelationPermission(Permission permission);

    /**
     * @param permission
     * @return Data
     * @Description 修改角色权限信息
     * @author wangzhiwen
     * @date 2020/10/13 15:09
     */
    Data modifyRolePermission(Permission permission);

    /////////////////////////////////////////////////用户角色管理 ////////////////////////////////////////////////////////
    /**
     *
     * 功能描述:查询已关联用户角色信息列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Role> findUserRelationRoleList(AjaxPage ajaxPage, Role role);

    /**
     *
     * 功能描述:查询未关联用户角色信息列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Role> findUserNotRelationRoleList(AjaxPage ajaxPage, Role role);

    /**
     *
     * 功能描述: 保存用户角色
     *
     * @param json
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data saveUserRelationRole(String json);

    /**
     *
     * 功能描述: 删除用户角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data delUserRelationRole(Role role);

    //////////////////////////////////////////  管理员默认权限管理///////////////////////////////////////

    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Permission> findProjectManagerRelationPermissionList(AjaxPage ajaxPage,Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Permission> findNotProjectManagerRelationPermissionList(AjaxPage ajaxPage, Permission permission);

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data saveProjectManagerRelationPermission(Permission permission);

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    Data delProjectManagerRelationPermission(Permission permission);

    /**
     *
     * 功能描述: 查询用户已关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    ScriptPage<Role> findUserRelationAdminRoleList(AjaxPage ajaxPage,Role role);

    /**
     *
     * 功能描述: 查询用户未关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    ScriptPage<Role> findUserNotRelationAdminRoleList(AjaxPage ajaxPage,Role role);
}
