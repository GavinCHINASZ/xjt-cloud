package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.OrgUser;
import com.xjt.cloud.safety.core.entity.project.OrgUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 组织架构,成员,角色中间表DAO
 * @Author huanggc
 * @Date 2019/5/27
 */
@Repository
public interface OrgUserRoleDao {

    /**
     * 功能描述:组织架构 用户 角色中间表:新增数据
     * @Author huanggc
     * @Date 2019/5/28
     * @param list
     * @return Integer
     */
    int saveOrgUserRoles(List<OrgUserRole> list);

    /**
     *
     * 功能描述: 批量保存用户部门角色关系
     *
     * @param list
     * @return: 
     * @auther: zhangZaiFa
     * @date: 2019/9/5 17:05
     */
    int saveOrgUserRolesByList(List<OrgUserRole> list);

    /**
     * 功能描述:删除
     * @Author huanggc
     * @Date 2019/5/28
     * @param orgUserRole
     * @return Integer
     */
    Integer deleteOrgUserRole(OrgUserRole orgUserRole);

    /**
     * 功能描述:删除
     * @Author zhangZaiFa
     * @Date 2019/5/28
     * @param: [orgUserRole]
     * @return Integer
     */
    Integer deleteOrgUserRoleByUser(OrgUserRole orgUserRole);

    /**@MethodName: deleteRoleIds 删除角色下的成员
     * @Description: 
     * @Param: [roleIds]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/19 15:50 
     **/
    void deleteRoleIds(List<Long> roleIds);

    /**@MethodName: findByRoleOrgUserTree 查询角色成员树结构
     * @Description: 
     * @Param: [orgUserRole]
     * @Return: java.util.List<com.xjt.cloud.maintenance.core.entity.OrgUserRole>
     * @Author: zhangZaiFa
     * @Date:2019/9/20 15:08
     **/
    List<OrgUserRole> findByRoleOrgUserTree(OrgUserRole orgUserRole);

    /**
     *
     * 功能描述: 以sql查询用户部门角色关系
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/15 17:35
     */
    List<OrgUserRole>findByRoleOrgUserBySql(@Param("list") List<OrgUser> list , @Param("sql") String sql,@Param("projectId")Long projectId);

    /**@MethodName: updateAdmin 成员权限转让
     * @Description:
     * @Param: [oldOrgUserId, orgUserId, projectId]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/25 17:19
     **/
    void updateAdmin(@Param("oldOrgUserId") Long oldOrgUserId, @Param("orgUserId") Long orgUserId, @Param("userId") Long userId,@Param("projectId")  Long projectId);

    /**@MethodName: findByRoleOrgUser 查询成员列表
     * @Description:
     * @Param: [orgUserRole]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUserRole>
     * @Author: zhangZaiFa
     * @Date:2019/9/25 17:19
     **/
    List<OrgUserRole> findByRoleOrgUser(OrgUserRole orgUserRole);

    /**@MethodName: findByProjectRoleUserIdList 查询项目指定权限的UserId
     * @Description:
     * @Param: [orgUserRole]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 10:39
     **/
    List<String> findByProjectPermissionUserIdList(OrgUserRole orgUserRole);

    /**@MethodName: findByIsOrgUserRoleSign
     * @Description: 查询用户在指定项目内是否有此权限
     * @Param: [userId, projectId, signList]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/1/15 11:25
     **/
    Integer findByIsOrgUserRoleSign(@Param("userId") Long userId, @Param("projectId") Long projectId, @Param("list")List<String> list);

    /**@MethodName: updateOrgUserRole
     * @Description: 修改成员角色
     * @Param: [orgUser]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/15 11:37
     **/
    void updateOrgUserRole(OrgUser orgUser);
}
