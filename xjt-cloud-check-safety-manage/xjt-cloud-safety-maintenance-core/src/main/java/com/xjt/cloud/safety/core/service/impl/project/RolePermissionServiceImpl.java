package com.xjt.cloud.safety.core.service.impl.project;

import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.entity.project.Role;
import com.xjt.cloud.safety.core.entity.project.RolePermission;
import com.xjt.cloud.safety.core.dao.project.RolePermissionDao;
import com.xjt.cloud.safety.core.entity.project.Permission;
import com.xjt.cloud.safety.core.service.service.project.PermissionService;
import com.xjt.cloud.safety.core.service.service.project.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @ClassName ProjectRolePermissionServiceImpl 项目角色权限
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class RolePermissionServiceImpl extends AbstractService implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionService permissionService;


    /**
     * @MethodName: findByRolePpermission 查询角色权限
     * @Description: TODO
     * @Param: [roleId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/22 14:52
     **/
    @Override
    public Boolean findByRolePermission(Long roleId, Long permissionId) {
        int count = rolePermissionDao.findByRolePermission(roleId, permissionId);
        if (count > 0) {
            return true;
        }
        return false;
    }


    /**
     * @MethodName: findByRolePermissionList 查询角色所有权限
     * @Description:
     * @Param: [roleId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRolePermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/29 17:26
     **/
    @Override
    public List<RolePermission> findByRolePermissionList(Long roleId) {
        return rolePermissionDao.findByRolePermissionList(roleId);
    }


    /**
     * @MethodName: deleteProjectRolePermission 删除角色下所有权限
     * @Description:
     * @Param: [roleId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/25 14:26
     **/
    @Override
    public Data deleteProjectRolePermission(Long roleId) {
        rolePermissionDao.deleteProjectRolePermission(roleId);
        return Data.isSuccess();
    }

    /**
     * @MethodName: addProjectAdminRolePermission 给管理员授权
     * @Description:
     * @Param: [roleId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/2 17:52
     **/
    @Override
    public Data addProjectAdminRolePermission(Role role) {
        deleteProjectRolePermission(role.getId());
        Permission permission = new Permission();
        //查询项目所有功能权限，授权给管理员
        permission.setPerType(2);//项目权限
        List<Permission> permissions = permissionService.findByPermission(permission);
        List<RolePermission> list = new ArrayList<>();
        for (Permission projectPermission : permissions) {
            if (!"signature_manage_edit".equals(projectPermission.getSign())) {
                RolePermission entity = new RolePermission();
                entity.setRoleId(role.getId());
                entity.setSourceId(role.getSourceId());
                entity.setSourceType(role.getSourceType());
                entity.setPermissionId(projectPermission.getId());
                list.add(entity);
            }
        }
        rolePermissionDao.saveList(list);
        return Data.isSuccess();
    }

    /**
     * @MethodName: rolePermission 授权
     * @Description:
     * @Param: [permissions]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/6 13:59
     **/
    private Data rolePermission(Set<Long> permissionIdSet, Role role) {
        List<RolePermission> list = new ArrayList<>();
        Iterator<Long> it = permissionIdSet.iterator();
        while (it.hasNext()) {
            Long permissionId = it.next();
            RolePermission entity = new RolePermission();
            entity.setRoleId(role.getId());
            entity.setSourceId(role.getSourceId());
            entity.setSourceType(entity.getSourceType());
            entity.setPermissionId(permissionId);
            list.add(entity);
        }
        rolePermissionDao.saveList(list);
        return Data.isSuccess();
    }

    /**
     * @MethodName: addProjectOrdinaryRolePermission 给普通成员授权
     * @Description:
     * @Param: [ordinaryRole]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/6 13:56
     **/
    @Override
    public Data addProjectOrdinaryRolePermission(Role ordinaryRole, List<Long> ids) {
        deleteProjectRolePermission(ordinaryRole.getId());
        Set<Long> permissionIdSet = permissionService.findByPermissionSet(ids);
        return rolePermission(permissionIdSet, ordinaryRole);

    }

    /**
     * @MethodName: addProjectRolePermission 角色授权
     * @Description: TODO
     * @Param: [permissionIdArr, roleId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/25 13:46
     **/
    @Override
    public Data addProjectRolePermission(List<Long> permissionIds, Role role) {
        //删除角色下所有权限
        deleteProjectRolePermission(role.getId());
        //查询所有权限的父目录及自己，授权给角色
        Set<Long> permissionIdSet = permissionService.findByPermissionSet(permissionIds);
        return rolePermission(permissionIdSet, role);
    }

}
