package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProjectRolePermissionDao 项目角色权限Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface RolePermissionDao {
    /**
     * @MethodName: findByRolePermission 查询角色是否拥有权限
     * @Description:
     * @Param: [roleId, permissionId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 9:57
     **/
    Integer findByRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * @MethodName: deleteProjectRolePermission 删除角色下权限
     * @Description:
     * @Param: [roleId]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/25 14:20
     **/
    void deleteProjectRolePermission(@Param("roleId") Long roleId);

    /**
     * @MethodName: saveList 添加角色权限
     * @Description:
     * @Param: [list]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/25 14:27
     **/
    void saveList(List<RolePermission> list);

    /**
     * @MethodName: findByRolePermissionList 查询角色所有权限
     * @Description:
     * @Param: [roleId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/29 17:26
     **/
    List<RolePermission> findByRolePermissionList(@Param("roleId") Long roleId);
}
