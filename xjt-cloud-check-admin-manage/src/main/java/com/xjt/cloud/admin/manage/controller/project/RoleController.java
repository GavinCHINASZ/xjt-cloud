package com.xjt.cloud.admin.manage.controller.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.Role;
import com.xjt.cloud.admin.manage.service.service.project.RoleService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/6 15:14
 * @Description: 角色管理
 */
@Controller
@RequestMapping("/role/")
public class RoleController  extends AbstractController {
    @Autowired
    private RoleService roleService;

    /////////////////////////////////////////////////// 角色权限//////////////////////////////////////////////////////////

    /**
     *
     * 功能描述: 跳转到角色权限页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:35
     */
    @RequestMapping(value = "toRoleListPage")
    public ModelAndView toRoleListPage(){
        return new ModelAndView("permission/roleList");
    }

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
    @RequestMapping(value = "findRoleList")
    @ResponseBody
    public ScriptPage<Role> findRoleList(AjaxPage ajaxPage,Role role){
        return roleService.findRoleList(ajaxPage, role);
    }

    /**
     *
     * 功能描述:保存角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @RequestMapping(value = "saveRole")
    @ResponseBody
    public Data saveRole(Role role){
        return roleService.saveRole(role);
    }

    /**
     *
     * 功能描述:修改角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @RequestMapping(value = "modifyRole")
    @ResponseBody
    public Data modifyRole(Role role){
        return roleService.modifyRole(role);
    }

    /**
     *
     * 功能描述:删除角色信息
     *
     * @param role
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/7 10:39
     */
    @RequestMapping(value = "delRole")
    @ResponseBody
    public Data delRole(Role role){
        return roleService.delRole(role);
    }

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
    @ResponseBody
    @RequestMapping(value = "findRoleRelationPermissionList")
    public ScriptPage<Permission> findRoleRelationPermissionList(AjaxPage ajaxPage, Permission permission){
        return roleService.findRoleRelationPermissionList(ajaxPage, permission);
    }

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
    @ResponseBody
    @RequestMapping(value = "findNotRoleRelationPermissionList")
    public ScriptPage<Permission> findNotRoleRelationPermissionList(AjaxPage ajaxPage,Permission permission){
        return roleService.findNotRoleRelationPermissionList(ajaxPage, permission);
    }

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "saveRoleRelationPermission")
    public Data saveRoleRelationPermission(Permission permission){
        return roleService.saveRoleRelationPermission(permission);
    }

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "delRoleRelationPermission")
    public Data delRoleRelationPermission(Permission permission){
        return roleService.delRoleRelationPermission(permission);
    }

    /**
     * @param permission
     * @return Data
     * @Description 修改角色权限信息
     * @author wangzhiwen
     * @date 2020/10/13 15:09
     */
    @ResponseBody
    @RequestMapping(value = "modifyRolePermission")
    public Data modifyRolePermission(Permission permission){
        return roleService.modifyRolePermission(permission);
    }

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
    @ResponseBody
    @RequestMapping(value = "findUserRelationRoleList")
    public ScriptPage<Role> findUserRelationRoleList(AjaxPage ajaxPage,Role role){
        return roleService.findUserRelationRoleList(ajaxPage, role);
    }

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
    @ResponseBody
    @RequestMapping(value = "findUserNotRelationRoleList")
    public ScriptPage<Role> findUserNotRelationRoleList(AjaxPage ajaxPage,Role role){
        return roleService.findUserNotRelationRoleList(ajaxPage, role);
    }

    /**
     *
     * 功能描述: 保存用户角色
     *
     * @param json
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "saveUserRelationRole")
    public Data saveUserRelationRole(String json){
        return roleService.saveUserRelationRole(json);
    }

    /**
     *
     * 功能描述: 删除用户角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "delUserRelationRole")
    public Data delUserRelationRole(Role role){
        return roleService.delUserRelationRole(role);
    }


    //////////////////////////////////////////  管理员默认权限管理///////////////////////////////////////

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
    @ResponseBody
    @RequestMapping(value = "findProjectManagerRelationPermissionList")
    public ScriptPage<Permission> findProjectManagerRelationPermissionList(AjaxPage ajaxPage, Permission permission){
        return roleService.findProjectManagerRelationPermissionList(ajaxPage, permission);
    }

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
    @ResponseBody
    @RequestMapping(value = "findNotProjectManagerRelationPermissionList")
    public ScriptPage<Permission> findNotProjectManagerRelationPermissionList(AjaxPage ajaxPage,Permission permission){
        return roleService.findNotProjectManagerRelationPermissionList(ajaxPage, permission);
    }

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "saveProjectManagerRelationPermission")
    public Data saveProjectManagerRelationPermission(Permission permission){
        return roleService.saveProjectManagerRelationPermission(permission);
    }

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "delProjectManagerRelationPermission")
    public Data delProjectManagerRelationPermission(Permission permission){
        return roleService.delProjectManagerRelationPermission(permission);
    }

    /**
     *
     * 功能描述: 查询用户已关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "findUserRelationAdminRoleList")
    public ScriptPage<Role> findUserRelationAdminRoleList(AjaxPage ajaxPage,Role role){
        return roleService.findUserRelationAdminRoleList(ajaxPage,role);
    }

    /**
     *
     * 功能描述: 查询用户未关联的管理员角色
     *
     * @param role
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    @ResponseBody
    @RequestMapping(value = "findUserNotRelationAdminRoleList")
    public ScriptPage<Role> findUserNotRelationAdminRoleList(AjaxPage ajaxPage,Role role){
        return roleService.findUserNotRelationAdminRoleList(ajaxPage,role);
    }

}
