package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.project.OrgUser;
import com.xjt.cloud.project.core.entity.project.Organization;
import com.xjt.cloud.project.core.entity.project.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName OrgUserDao 平台成员Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface OrgUserDao {
    /**
     * @MethodName: 查询用户部门公司树关系
     * @Description:
     * @Param: [search]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 15:25
     **/
    List<Organization> findOrgUserTree(Organization org);

    /**
     *
     * 功能描述: 新增用户与部门关系
     *
     * @param orgUser
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/9/5 11:32
     */
    int saveOrgUser(OrgUser orgUser);

    /**
     *
     * 功能描述: 删除用户与部门关系
     *
     * @param orgUser
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/9/5 16:54
     */
    int deleteOrgUser(OrgUser orgUser);

    /**
     *
     * 功能描述: 批量关联用户与部门关系
     *
     * @param list
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/9/5 16:54
     */
    int relateOrgUsers(List<OrgUser> list);

    /**
     *
     * 功能描述: 查询公司与部门是否存在
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/5 18:45
     */
    List<Organization> findCoDepByNames(@Param(value="sql") String sql, @Param(value = "projectId") Long projectId);

    /**@MethodName: findOrgUser 查询成员
     * @Description: 
     * @Param: [orgUser]
     * @Return: com.xjt.cloud.project.core.entity.OrgUser
     * @Author: zhangZaiFa
     * @Date:2019/9/9 16:55
     **/
    OrgUser findOrgUser(OrgUser orgUser);


    /**@MethodName: findOrgUserList 查询项目成员列表
     * @Description: 
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @Author: zhangZaiFa
     * @Date:2019/9/20 9:58 
     **/
    List<OrgUser> findOrgUserList(OrgUser orgUser);

    /**@MethodName: findByOrgListCount 查询项目成员总数
     * @Description: 
     * @Param: [orgUser]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/9/20 9:59
     **/
    Integer findByOrgListCount(OrgUser orgUser);

    /**@MethodName: findByJoinProjectIds 查询用户参与项目ID
     * @Description: 
     * @Param: [userId]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/9/29 10:07 
     **/
    List<Long> findByJoinProjectIds(@Param(value="userId") Long userId);

    /**@MethodName: findByProjectJoinOrgUserTree 查询用户参与项目成员结构
     * @Description: 
     * @Param: [projectIds]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/9/29 10:07 
     **/
    List<Organization> findByProjectJoinOrgUserTree(List<Long> list);

    /**@MethodName: findByOrgUserIdList 通过ID查询对象
     * @Description: 
     * @Param: [orgUserIds]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/9/30 16:53 
     **/
    List<OrgUser> findByOrgUserIdList(List<Long> orgUserIds);

    /**@MethodName: updateOrgUser 更新成员信息
     * @Description: 
     * @Param: [orgUser]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/16 16:05
     **/
    void updateOrgUser(OrgUser orgUser);

    /**@MethodName: updateOrgUsers 批量更新用户信息
     * @Description: 
     * @Param: [orgUser]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/16 16:07 
     **/
    void updateOrgUsers(OrgUser orgUser);

    /**@MethodName: findByDepIdOrgUserList 查询部门下面的人员
     * @Description: 
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 16:24
     **/
    List<OrgUser> findByDepIdOrgUserList(List<Long> ids);

    /**@MethodName: findByProjectIsAdmin 查询成员中是否存在项目拥有者
     * @Description:
     * @Param: [orgUser]
     * @Return: com.xjt.cloud.project.core.entity.OrgUser
     * @Author: zhangZaiFa
     * @Date:2019/11/1 14:10 
     **/
    OrgUser findByProjectIsAdmin(OrgUser orgUser);

    /**@MethodName: findByOrgUserList 按条件查询成员
     * @Description:
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/11/5 10:54
     **/
    List<OrgUser> findByOrgUserList(OrgUser orgUser);
    
    /**
     *
     * 功能描述: 以sql文查询用户部门
     *
     * @param sql
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/11/15 17:21
     */
    List<OrgUser> findOrgUserListBySql(@Param(value="list")List<User> list, @Param(value="sql")String sql,@Param("projectId")Long projectId);

    /**@MethodName: findByRoleOrgUserList 查询角色下成员信息
     * @Description: 
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2019/11/5 18:17 
     **/
    List<OrgUser> findByRoleOrgUserList(OrgUser orgUser);

    /**@MethodName: findProjectOrgUserCount
     * @Description: 查询项目成员数量
     * @Param: [projectIds]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/4/9 10:38
     **/
    Integer findProjectOrgUserCount(@Param("projectIds") List<Long> projectIds);
}
