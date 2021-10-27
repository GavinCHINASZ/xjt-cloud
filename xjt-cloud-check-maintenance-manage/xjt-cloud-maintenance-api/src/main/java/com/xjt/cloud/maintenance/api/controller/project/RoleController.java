package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ProjectCompanyController 项目角色
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;


    /**
     * @MethodName: addProjectCompany 添加项目角色
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:00
     **/
    @RequestMapping(value = "/addProjectRole/{projectId}")
    public Data addProjectRole( String json) {
        return roleService.addProjectRole(json);
    }

    /**
     * @MethodName: updateProjectRole 修改角色信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:38
     **/
    @RequestMapping(value = "/updateProjectRole/{projectId}")
    public Data updateProjectRole( String json) {
        return roleService.updateProjectRole(json);
    }

    /**
     * @MethodName: deleteProjectRole 删除角色信息
     * @Description:
     * @Param: [json]  id
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:37
     **/
    @RequestMapping(value = "/deleteProjectRole/{projectId}")
    public Data deleteProjectRole( String json) {
        return roleService.deleteProjectRole(json);
    }

    /**
     * @MethodName: findProjectRole 查询角色信息
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:30
     **/
    @RequestMapping(value = "/findByProjectRole/{projectId}")
    public Data findByProjectRole( String json) {
        return roleService.findByProjectRole(json);
    }

    /**
     * @MethodName: findProjectRoleList查询角色列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:31
     **/
    @RequestMapping(value = "/findByProjectRoleList/{projectId}")
    public Data findByProjectRoleList(String json) {
        return roleService.findByProjectRoleList(json);
    }

    /**
     * @MethodName: addProjectMemberAndPermission 角色添加成员及授权
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/24 10:44
     **/
    @RequestMapping(value = "/addProjectOrgUserAndPermission/{projectId}")
    public Data addProjectOrgUserAndPermission( String json) {
        return roleService.addProjectOrgUserAndPermission(json);
    }

    /**@MethodName: findByRoleOrgUserTree 查询角色下的成员树结构
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 14:17 
     **/
    @RequestMapping(value = "/findByRoleOrgUserTree/{projectId}")
    public Data findByRoleOrgUserTree( String json) {
        return roleService.findByRoleOrgUserTree(json);
    }

    /**@MethodName: findByProjectRoleUserIdListInit 查询项目指定权限的UserId
    * @Description:
    * @Param: [json]
    * @Return: com.xjt.cloud.commons.utils.Data
    * @Author: zhangZaiFa
    * @Date:2019/11/15 10:31
    **/
    @RequestMapping(value = "/findByProjectPermissionUserIdList")
    public Data findByProjectPermissionUserIdList( String json) {
        return roleService.findByProjectPermissionUserIdList(json);
    }

}
