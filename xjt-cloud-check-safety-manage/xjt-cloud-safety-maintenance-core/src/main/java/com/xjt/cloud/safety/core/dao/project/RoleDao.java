package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.Role;
import com.xjt.cloud.safety.core.entity.project.OrgUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProjectRoleDao 项目角色Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface RoleDao {

    /**
     * @MethodName: addProjectRole 添加项目角色
     * @Description:
     * @Param: [entity]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:18
     **/
    Long addProjectRole(Role entity);

    /**
     * @MethodName: deleteProjectRole删除角色信息
     * @Description:
     * @Param: [ids]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:41
     **/
    void deleteProjectRole(List<Long> list);

    /**
     * @MethodName: updateProjectRole 修改角色信息
     * @Description:
     * @Param: [entity]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:41
     **/
    void updateProjectRole(Role entity);

    /**
     * @MethodName: findByProjectRoleList 查询角色列表
     * @Description:
     * @Param: [entity]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRole>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:37
     **/
    List<Role> findByProjectRoleList(Role entity);

    /**@MethodName: findByProjectRoleId 查询角色列表
     * @Description: 
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRole>
     * @Author: zhangZaiFa
     * @Date:2019/8/20 9:50
     **/
    List<Role> findByProjectRoleId(List<Long> ids);

    /**
     *
     * 功能描述: 以角色名称查询角色信息
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/5 18:45
     */
    List<Role> findRoleByNames(@Param(value="sql") String sql, @Param(value = "projectId") Long projectId);

    /**@MethodName: findByProjectRole 查询项目角色
     * @Description: 
     * @Param: [projectId]
     * @Return: com.xjt.cloud.project.core.entity.Role
     * @Author: zhangZaiFa
     * @Date:2019/9/26 14:46 
     **/

    Role findByProjectRole(Role role);

    /**@MethodName: findProjectRoleUserList
     * @Description: 查询角色成员列表
     * @Param: [permission]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.project.OrgUser>
     * @Author: zhangZaiFa
     * @Date:2020/4/15 11:26
     **/
    List<OrgUser> findProjectRoleUserList(Role permission);
}
