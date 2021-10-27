package com.xjt.cloud.project.core.service.service.project;


import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.project.Role;
import com.xjt.cloud.project.core.entity.project.RolePermission;

import java.util.List;

/**
 * @ClassName ProjectRolePermissionService 项目角色权限
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface RolePermissionService {
    /**
     * @MethodName: findByRolePermission 查询角色是否有权限
     * @Description:
     * @Param: [roleId, permissionId]
     * @Return: java.lang.Boolean
     * @Author: zhangZaiFa
     * @Date:2019/7/23 9:54
     **/
    Boolean findByRolePermission(Long roleId, Long permissionId);


    /**
     * @MethodName: addProjectRolePermission 项目角色授权
     * @Description:
     * @Param: [permissionIdArr, roleId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/24 13:56
     **/
    Data addProjectRolePermission(List<Long> permissionIdArr, Role role);


    /**
     * @MethodName: findByRolePermissionList 查询角色所有权限
     * @Description:
     * @Param: [roleId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.RolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/29 17:26
     **/
    List<RolePermission> findByRolePermissionList(Long roleId);

    /**
     * @MethodName: deleteRoleId 按角色ID删除
     * @Description:
     * @Param: [roleIds]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/29 17:57
     **/
    Data deleteProjectRolePermission(Long roleId);


    /**
     * @MethodName: addProjectAdminRolePermission 给管理员授权
     * @Description:
     * @Param: [roleId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/2 17:52
     **/
    Data addProjectAdminRolePermission(Role role);

    /**@MethodName: addProjectOrdinaryRolePermission 给普通成员授权
     * @Description:
     * @Param: [ordinaryRole]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/6 13:56
     **/
    Data addProjectOrdinaryRolePermission(Role ordinaryRole,List<Long> ids);
}
