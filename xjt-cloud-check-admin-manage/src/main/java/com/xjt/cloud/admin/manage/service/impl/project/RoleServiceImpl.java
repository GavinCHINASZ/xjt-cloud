package com.xjt.cloud.admin.manage.service.impl.project;

import com.alibaba.fastjson.JSONArray;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.project.PermissionDao;
import com.xjt.cloud.admin.manage.dao.project.RoleDao;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.Role;
import com.xjt.cloud.admin.manage.service.service.project.RoleService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色管理
 *
 * @Auther: wangzhiwen
 * @Date: 2020/8/6 15:19
 */
@Service
public class RoleServiceImpl extends AbstractAdminService implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    /////////////////////////////////////////////////// 角色权限//////////////////////////////////////////////////////////

    /**
     * 功能描述:查询角色列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:37
     */
    @Override
    public ScriptPage<Role> findRoleList(AjaxPage ajaxPage, Role role) {
        role = asseFindObj(role, ajaxPage);
        return asseScriptPage(roleDao.findRoleList(role), roleDao.findRoleListTotalCount(role));
    }

    /**
     * 功能描述:保存角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @Override
    public Data saveRole(Role role) {
        return asseData(roleDao.saveRole(role));
    }

    /**
     * 功能描述:修改角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @Override
    public Data modifyRole(Role role) {
        return asseData(roleDao.modifyRole(role));
    }

    /**
     * 功能描述:删除角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @Override
    public Data delRole(Role role) {
        return asseData(roleDao.delRole(role));
    }

    /**
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Permission> findRoleRelationPermissionList(AjaxPage ajaxPage, Permission permission) {
        permission = asseFindObj(permission, ajaxPage);
        return asseScriptPage(permissionDao.findRoleRelationPermissionList(permission), permissionDao.findRoleRelationPermissionListTotalCount(permission));
    }

    /**
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Permission> findNotRoleRelationPermissionList(AjaxPage ajaxPage, Permission permission) {
        permission = asseFindObj(permission, ajaxPage);
        return asseScriptPage(permissionDao.findNotRoleRelationPermissionList(permission), permissionDao.findNotRoleRelationPermissionListTotalCount(permission));
    }

    /**
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data saveRoleRelationPermission(Permission permission) {
        return asseData(permissionDao.saveRoleRelationPermission(permission));
    }

    /**
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data delRoleRelationPermission(Permission permission) {
        return asseData(permissionDao.delRoleRelationPermission(permission));
    }

    /**
     * @param permission
     * @return Data
     * @Description 修改角色权限信息
     * @author wangzhiwen
     * @date 2020/10/13 15:09
     */
    @Override
    public Data modifyRolePermission(Permission permission) {
        return asseData(permissionDao.modifyRolePermission(permission));
    }

    /////////////////////////////////////////////////用户角色管理 ////////////////////////////////////////////////////////

    /**
     * 功能描述:查询已关联用户角色信息列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Role> findUserRelationRoleList(AjaxPage ajaxPage, Role role) {
        role = asseFindObj(role, ajaxPage);
        return asseScriptPage(roleDao.findUserRelationRoleList(role), roleDao.findUserRelationRoleListTotalCount(role));
    }

    /**
     * 功能描述:查询未关联用户角色信息列表
     *
     * @param ajaxPage
     * @param role
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Role> findUserNotRelationRoleList(AjaxPage ajaxPage, Role role) {
        role = asseFindObj(role, ajaxPage);
        return asseScriptPage(roleDao.findUserNotRelationRoleList(role), roleDao.findUserNotRelationRoleListTotalCount(role));
    }

    /**
     * 功能描述: 保存用户角色
     *
     * @param json
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data saveUserRelationRole(String json) {
        List<Role> list = JSONArray.parseArray(json, Role.class);
        return asseData(roleDao.saveUserRelationRole(list));
    }

    /**
     * 功能描述: 删除用户角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data delUserRelationRole(Role role) {
        return asseData(roleDao.delUserRelationRole(role));
    }

    //////////////////////////////////////////  管理员默认权限管理///////////////////////////////////////

    /**
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Permission> findProjectManagerRelationPermissionList(AjaxPage ajaxPage, Permission permission) {
        permission = asseFindObj(permission, ajaxPage);
        return asseScriptPage(permissionDao.findProjectManagerRelationPermissionList(permission), permissionDao.findProjectManagerRelationPermissionListTotalCount(permission));
    }

    /**
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param ajaxPage
     * @param permission
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    @Override
    public ScriptPage<Permission> findNotProjectManagerRelationPermissionList(AjaxPage ajaxPage, Permission permission) {
        permission = asseFindObj(permission, ajaxPage);
        return asseScriptPage(permissionDao.findNotProjectManagerRelationPermissionList(permission), permissionDao.findNotProjectManagerRelationPermissionListTotalCount(permission));
    }

    /**
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data saveProjectManagerRelationPermission(Permission permission) {
        return asseData(permissionDao.saveProjectManagerRelationPermission(permission));
    }

    /**
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public Data delProjectManagerRelationPermission(Permission permission) {
        return asseData(permissionDao.delProjectManagerRelationPermission(permission));
    }

    /**
     * 功能描述: 查询用户已关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public ScriptPage<Role> findUserRelationAdminRoleList(AjaxPage ajaxPage, Role role) {
        role = asseFindObj(role, ajaxPage);
        return asseScriptPage(roleDao.findUserRelationAdminRoleList(role), roleDao.findUserRelationAdminRoleListCount(role));
    }

    /**
     * 功能描述: 查询用户未关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @Override
    public ScriptPage<Role> findUserNotRelationAdminRoleList(AjaxPage ajaxPage, Role role) {
        role = asseFindObj(role, ajaxPage);
        return asseScriptPage(roleDao.findUserNotRelationAdminRoleList(role), roleDao.findUserNotRelationAdminRoleListCount(role));
    }
}
