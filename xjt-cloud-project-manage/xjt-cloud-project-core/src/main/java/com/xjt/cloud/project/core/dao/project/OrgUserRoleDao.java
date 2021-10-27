package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.OrgUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织架构,成员,角色中间表DAO
 * 
 * @author huanggc
 * @date 2019/5/27
 */
@Repository
public interface OrgUserRoleDao {

    /**
     * 功能描述:组织架构 用户 角色中间表:新增数据
     * @author huanggc
     * @date 2019/5/28
     * @param list
     * @return Integer
     */
    int saveOrgUserRoles(List<OrgUserRole> list);

    /**
     *
     * 功能描述: 批量保存用户部门角色关系
     *
     * @param list
     * @return 
     * @author zhangZaiFa
     * @date 2019/9/5 17:05
     */
    int saveOrgUserRolesByList(List<OrgUserRole> list);

    /**
     * 功能描述:删除
     *
     * @author huanggc
     * @date 2019/5/28
     * @param orgUserRole
     * @return Integer
     */
    Integer deleteOrgUserRole(OrgUserRole orgUserRole);

    /**
     * 功能描述:删除
     *
     * @author zhangZaiFa
     * @date 2019/5/28
     * @param: [orgUserRole]
     * @return Integer
     */
    Integer deleteOrgUserRoleByUser(OrgUserRole orgUserRole);

    /** 
     * deleteRoleIds 删除角色下的成员
     *  
     * @Param: [roleIds]
     * @return void
     * @author: zhangZaiFa
     * @date2019/9/19 15:50 
     **/
    void deleteRoleIds(List<Long> roleIds);

    /** findByRoleOrgUserTree 查询角色成员树结构
     *  
     * @Param: [orgUserRole]
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author: zhangZaiFa
     * @date2019/9/20 15:08
     **/
    List<OrgUserRole> findByRoleOrgUserTree(OrgUserRole orgUserRole);

    /** findByRoleOrgUserTree 查询角色成员树结构
     *
     * @Param: [orgUserRole]
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author: zhangZaiFa
     * @date2019/9/20 15:08
     **/
    List<OrgUserRole> findByRoleOrgUserTreeCV5(OrgUserRole orgUserRole);

    /**
     *
     * 功能描述: 以sql查询用户部门角色关系
     *
     * @param sql
     * @return
     * @author wangzhiwen
     * @date 2019/11/15 17:35
     */
    List<OrgUserRole>findByRoleOrgUserBySql(@Param("list") List<OrgUser> list , @Param("sql") String sql,@Param("projectId")Long projectId);

    /**
     * updateAdmin 成员权限转让
     * 
     * @Param: [oldOrgUserId, orgUserId, projectId]
     * @return void
     * @author: zhangZaiFa
     * @date2019/9/25 17:19
     **/
    void updateAdmin(@Param("oldOrgUserId") Long oldOrgUserId, @Param("orgUserId") Long orgUserId, @Param("userId") Long userId,@Param("projectId")  Long projectId);

    /**
     * updateAdmin 成员权限转让
     *
     * @Param: [oldOrgUserId, orgUserId, projectId]
     * @return void
     * @author: zhangZaiFa
     * @date2019/9/25 17:19
     **/
    void updateAdminCV5(@Param("oldOrgUserId") Long oldOrgUserId, @Param("orgUserId") Long orgUserId, @Param("userId") Long userId,@Param("projectId")  Long projectId);

    /**
     * findByRoleOrgUser 查询成员列表
     * 
     * @Param: [orgUserRole]
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @author: zhangZaiFa
     * @date2019/9/25 17:19
     **/
    List<OrgUserRole> findByRoleOrgUser(OrgUserRole orgUserRole);

    /** findByProjectRoleUserIdList 查询项目指定权限的UserId
     * 
     * @Param: [orgUserRole]
     * @return java.util.List<java.lang.String>
     * @author: zhangZaiFa
     * @date2019/11/15 10:39
     **/
    List<String> findByProjectPermissionUserIdList(OrgUserRole orgUserRole);

    /**
     * findByIsOrgUserRoleSign
     * 查询用户在指定项目内是否有此权限
     *
     * @Param: [userId, projectId, signList]
     * @return java.lang.Integer
     * @author: zhangZaiFa
     * @date2020/1/15 11:25
     **/
    Integer findByIsOrgUserRoleSign(@Param("userId") Long userId, @Param("projectId") Long projectId, @Param("list")List<String> list);
}
