package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.Permission;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ProjectPermissionService 项目权限
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface PermissionService {
    /**
     * @MethodName: findByPermission 查询项目权限
     * @Description:
     * @Param: [roleId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/22 17:14
     **/
    List<Permission> findByPermission(Permission entity);

    /**
     * @MethodName: findByPermissionParentId 查询id的子权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 10:34
     **/
    List<Permission> findByPermissionParentId(Permission entity);


    /**
     * @MethodName: findByUserPermission 查询用户权限菜单
     * @Description:
     * @Param: [map]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/8/1 13:49
     **/
    List<Permission> findByUserPermission(Permission entity);

    /**
     * @MethodName: findByPermissionSet 查询权限父目录和自己
     * @Description:
     * @Param: [permissionIdSet]
     * @Return: java.util.Set<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:27
     **/
    Set<Long> findByPermissionSet(List<Long> permissionIds);

    /**@MethodName: findByOrgUserProjectPermission 查询当前用户在所属项目的权限
     * @Description:
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/11/7 11:46
     **/
    List<String> findByOrgUserProjectPermission(OrgUser orgUser);
}
