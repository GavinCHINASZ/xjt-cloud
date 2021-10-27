package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.Organization;
import com.xjt.cloud.project.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrgUserDao 平台成员Dao
 *
 * @author zhangZaiFa
 * @Date 2019-07-29 15:15
 */
@Repository
public interface OrgUserDao {
    /**
     * 查询用户部门公司树关系
     *
     * @param organization Organization
     * @return List<Organization>
     * @author zhangZaiFa
     * @date 2019/7/23 15:25
     **/
    List<Organization> findOrgUserTree(Organization organization);

    /**
     * 功能描述: 新增用户与部门关系
     *
     * @param orgUser OrgUser
     * @return int
     * @author wangzhiwen
     * @date 2019/9/5 11:32
     */
    int saveOrgUser(OrgUser orgUser);

    /**
     * 功能描述: 删除用户与部门关系
     *
     * @param orgUser OrgUser
     * @return int
     * @author wangzhiwen
     * @date 2019/9/5 16:54
     */
    int deleteOrgUser(OrgUser orgUser);

    /**
     * 功能描述: 批量关联用户与部门关系
     *
     * @param list List<OrgUser>
     * @return OrgUser
     * @author wangzhiwen
     * @date 2019/9/5 16:54
     */
    int relateOrgUsers(List<OrgUser> list);

    /**
     * 功能描述: 查询公司与部门是否存在
     *
     * @param sql sql
     * @return List<Organization>
     * @author wangzhiwen
     * @date 2019/9/5 18:45
     */
    List<Organization> findCoDepByNames(@Param(value = "sql") String sql, @Param(value = "projectId") Long projectId);

    /**
     * findOrgUser 查询成员
     *
     * @return com.xjt.cloud.project.core.entity.OrgUser
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/9/9 16:55
     **/
    OrgUser findOrgUser(OrgUser orgUser);

    /**
     * findOrgUserList 查询项目成员列表
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/9/20 9:58
     **/
    List<Organization> findOrgUserList(OrgUser orgUser);

    /**
     * findByOrgListCount 查询项目成员总数
     *
     * @return java.lang.Integer
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/9/20 9:59
     **/
    Integer findByOrgListCount(OrgUser orgUser);

    /**
     * findByJoinProjectIds 查询用户参与项目ID
     *
     * @return java.util.List<java.lang.String>
     * @param userId userId
     * @author zhangZaiFa
     * @date 2019/9/29 10:07
     **/
    List<Long> findByJoinProjectIds(@Param(value = "userId") Long userId);

    /**
     * findByProjectJoinOrgUserTree 查询用户参与项目成员结构
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @param list List<Long>
     * @author zhangZaiFa
     * @date 2019/9/29 10:07
     **/
    List<Organization> findByProjectJoinOrgUserTree(List<Long> list);

    /**
     * findByOrgUserIdList 通过ID查询对象
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @param orgUserIds List<Long>
     * @author zhangZaiFa
     * @date 2019/9/30 16:53
     **/
    List<OrgUser> findByOrgUserIdList(List<Long> orgUserIds);

    /**
     * updateOrgUser 更新成员信息
     *
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/10/16 16:05
     **/
    void updateOrgUser(OrgUser orgUser);

    /**
     * updateOrgUsers 批量更新用户信息
     *
     * @param orgUser  OrgUser
     * @author zhangZaiFa
     * @date 2019/10/16 16:07
     **/
    void updateOrgUsers(OrgUser orgUser);

    /**
     * findByDepIdOrgUserList 查询部门下面的人员
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @param ids ids
     * @author zhangZaiFa
     * @date 2019/10/30 16:24
     **/
    List<OrgUser> findByDepIdOrgUserList(List<Long> ids);

    /**
     * findByProjectIsAdmin 查询成员中是否存在项目拥有者
     *
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/11/1 14:10
     * @return com.xjt.cloud.project.core.entity.OrgUser
     **/
    OrgUser findByProjectIsAdmin(OrgUser orgUser);

    /**
     * findByOrgUserList 按条件查询成员
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * 
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/11/5 10:54
     **/
    List<OrgUser> findByOrgUserList(OrgUser orgUser);

    /**
     * 功能描述: 以sql文查询用户部门
     *
     * @param sql sql
     * @return List<OrgUser>
     * @author wangzhiwen
     * @date 2019/11/15 17:21
     */
    List<OrgUser> findOrgUserListBySql(@Param(value = "list") List<User> list, @Param(value = "sql") String sql, @Param("projectId") Long projectId);

    /**
     * findByRoleOrgUserList 查询角色下成员信息
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/11/5 18:17
     **/
    List<OrgUser> findByRoleOrgUserList(OrgUser orgUser);

    /**
     * findProjectOrgUserCount
     * 查询项目成员数量
     *
     * @return java.lang.Integer
     * @param projectIds List<Long>
     * @author zhangZaiFa
     * @date 2020/4/9 10:38
     **/
    Integer findProjectOrgUserCount(@Param("projectIds") List<Long> projectIds);

    /**
     * findByRoleOrgUserList 查询角色下成员信息
     *
     * @return java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * 
     * @param orgUser OrgUser
     * @author zhangZaiFa
     * @date 2019/11/5 18:17
     **/
    List<OrgUser> findOrgUsers(OrgUser orgUser);

    /**
     * 查询成员名称
     *
     * @param orgUser 成员 实体
     * @return String 成员名
     * @author huanggc
     * @date 2020/09/10
     **/
    String findUsersName(OrgUser orgUser);

    /**
     * 查询 成员 根据 权限
     *
     * @param orgUser 成员 实体
     * @return List<OrgUser>
     * @author huanggc
     * @date 2020/09/28
     **/
    List<OrgUser> findOrgUserByPermission(OrgUser orgUser);
}
