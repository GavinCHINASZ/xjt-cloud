package com.xjt.cloud.project.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.project.OrgUser;
import com.xjt.cloud.project.core.entity.project.OrgUserRole;
import com.xjt.cloud.project.core.entity.project.Role;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/6 16:23
 * @Description:
 */
public interface OrgUserRoleService {

    /**
     *
     * 功能描述:保存用户部门角色关系
     *
     * @param list
     * @param roleList
     * @param projectId
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/6 14:58
     */
    Data saveUserOrgRole(List<OrgUser> list, List<Role> roleList, Long projectId);

    /***@MethodName: deleteRole 删除角色下的角色成员
     * @Description:
     * @Param: [ids]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/19 15:47
     **/
    Data deleteRoleIds(List<Long> roleIds);

    /**@MethodName: findByRoleOrgUserTree 查询部门成员树结构
     * @Description:
     * @Param: [orgUserRole]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @Author: zhangZaiFa
     * @Date:2019/9/20 14:50
     **/
    List<OrgUserRole> findByRoleOrgUserTree(OrgUserRole orgUserRole);

    /**@MethodName: updateAdmin 修改项目管理员
     * @Description:
     * @Param: [oldOwnerId, ownerId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/24 10:16
     **/
    Data updateAdmin(Long oldOwnerId, Long ownerId,Long projectId);

    /**@MethodName: findByRoleOrgUser 按条件查询
     * @Description: 
     * @Param: [orgUserRole]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @Author: zhangZaiFa
     * @Date:2019/9/25 17:18 
     **/
    List<OrgUserRole> findByRoleOrgUser(OrgUserRole orgUserRole);

    /**@MethodName: deleteOrgUserRoleByUser 按条件删除
     * @Description:
     * @Param: [orgUserRole]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/5 10:47
     **/
    Data deleteOrgUserRoleByUser(OrgUserRole orgUserRole);

    /**@MethodName: addOrdinary 添加普通权限
     * @Description: 
     * @Param: [userId, id]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/12 17:41 
     **/
    void addOrdinary(Long userId, Long projectId);

    /**@MethodName: findByProjectPermessionUserIdListInit 查询项目指定权限的UserId
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 10:31
     **/
    Data findByProjectPermissionUserIdList(String json);

    /**@MethodName: saveProjectUserOrgRole  保存角色信息
     * @Description:
     * @Param: [list, role, projectId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/19 14:44
     **/
    Data saveProjectUserOrgRole(List<OrgUser> list, Role role, Long projectId);

    /**@MethodName: findByIsOrgUserRoleSign
     * @Description: 查询用户在指定项目内是否有此权限
     * @Param: [userId, projectId, signList]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/1/15 11:23
     **/
    Integer findByIsOrgUserRoleSign(Long userId,Long projectId, List<String> signList);
}
