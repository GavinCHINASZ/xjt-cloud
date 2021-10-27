package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.entity.project.Role;

import java.util.List;

/**
 * @ClassName ProjectRoleService 项目角色
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 */
public interface RoleService {
    /**
     * @MethodName: addProjectRole 添加项目角色
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:01
     **/
    Data addProjectRole(String json);

    /**
     * @MethodName: updateProjectRole 修改角色信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:37
     **/
    Data updateProjectRole(String json);

    /**
     * @MethodName: deleteProjectRole 删除角色信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:37
     **/
    Data deleteProjectRole(String json);

    /**
     * @MethodName: findProjectRole 查询项目角色
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 15:17
     **/
    Data findByProjectRole(String json);

    /**
     * @MethodName: findProjectRoleList查询角色列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:31
     **/
    Data findByProjectRoleList(String json);

    /**
     * @MethodName: addProjectMemberAndPermission 角色添加成员及授权
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/24 10:44
     **/
    Data addProjectOrgUserAndPermission(String json);

    /**
     * @MethodName: addProjectRoleIsAdmin 创建项目管理员角色
     * @Description:
     * @Param: [projectId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/2 14:04
     **/
    Data addProjectRoleAdmin(Long projectId);

    /**
     * 功能描述:以角色名称查询角色信息是否存在
     *
     * @param sql
     * @param roleList
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/6 10:44
     */
    Data checkUserListRole(String sql, List<Role> roleList);


    /**
     * @MethodName: findByRoleOrgUserTree 查询角色下成员树结构
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/20 14:19
     **/
    Data findByRoleOrgUserTree(String json);

    /**
     * @MethodName: findByProjectOrdinaryRole 查询项目普通角色
     * @Description:
     * @Param: [projectId]
     * @Return: com.xjt.cloud.project.core.entity.Role
     * @Author: zhangZaiFa
     * @Date:2019/9/26 14:44
     **/
    Role findByProjectOrdinaryRole(Long projectId);


    /**
     * @MethodName: findByProjectRoleUserIdListInit 查询项目指定权限的UserId
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 10:31
     **/
    Data findByProjectPermissionUserIdList(String json);

}
