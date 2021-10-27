package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.OrgUserRole;
import com.xjt.cloud.project.core.entity.Role;

import java.util.List;

/**
 * 组织架构 角色
 * 
 * @author wangzhiwen
 * @date  2019/9/6 16:23
 */
public interface OrgUserRoleService {

    /**
     *
     * 功能描述:保存用户部门角色关系
     *
     * @param list 平台成员
     * @param roleList 角色
     * @param projectId 项目ID
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date  2019/9/6 14:58
     */
    Data saveUserOrgRole(List<OrgUser> list, List<Role> roleList, Long projectId);

    /*** deleteRole 删除角色下的角色成员
     * 
     * @param roleIds 角色ID
     * @return Data
     * @author zhangZaiFa
     * @date 2019/9/19 15:47
     **/
    Data deleteRoleIds(List<Long> roleIds);

    /**
     * findByRoleOrgUserTree 查询部门成员树结构
     * 
     * @param orgUserRole 组织架构 角色
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author zhangZaiFa
     * @date 2019/9/20 14:50
     **/
    List<OrgUserRole> findByRoleOrgUserTree(OrgUserRole orgUserRole);

    /**
     * updateAdmin 修改项目管理员
     * 
     * @param oldOwnerId oldOwnerId
     * @param ownerId ownerId
     * @param projectId 项目ID
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/9/24 10:16
     **/
    Data updateAdmin(Long oldOwnerId, Long ownerId,Long projectId);

    /**
     * findByRoleOrgUser 按条件查询
     *  
     * @param orgUserRole 组织架构角色
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author zhangZaiFa
     * @date 2019/9/25 17:18 
     **/
    List<OrgUserRole> findByRoleOrgUser(OrgUserRole orgUserRole);

    /**
     * deleteOrgUserRoleByUser 按条件删除
     * 
     * @param orgUserRole 组织架构角色
     * @author zhangZaiFa
     * @date 2019/11/5 10:47
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data deleteOrgUserRoleByUser(OrgUserRole orgUserRole);

    /**
     * addOrdinary 添加普通权限
     *  
     * @param userId 用户ID
     * @param projectId 项目ID
     * @author zhangZaiFa
     * @date 2019/11/12 17:41 
     **/
    void addOrdinary(Long userId, Long projectId);

    /**
     * 查询项目指定权限的UserId
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 10:31
     **/
    Data findByProjectPermissionUserIdList(String json);

    /**
     * 保存角色信息
     * 
     * @param list 成员
     * @param role 角色
     * @param projectId 项目ID
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/19 14:44
     **/
    Data saveProjectUserOrgRole(List<OrgUser> list, Role role, Long projectId);

    /**
     * 查询用户在指定项目内是否有此权限
     *
     * @param userId 用户ID
     * @param projectId 项目ID
     * @param signList 别名,例:operators_manage
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/1/15 11:23
     **/
    Integer findByIsOrgUserRoleSign(Long userId, Long projectId, List<String> signList);
}
