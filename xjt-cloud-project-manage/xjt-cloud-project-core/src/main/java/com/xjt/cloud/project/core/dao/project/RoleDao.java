package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.OrgUserRole;
import com.xjt.cloud.project.core.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * @MethodName: addProjectRole 添加项目角色
     * @Description:
     * @Param: [entity]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/19 14:18
     **/
    Long addProjectRoleCV5(Role entity);

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
    /**
     * @MethodName: findByProjectRoleList 查询角色列表
     * @Description:
     * @Param: [entity]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRole>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 14:37
     **/
    List<Role> findByProjectRoleListCV5(Role entity);

    /**@MethodName: findByProjectRoleId 查询角色列表
     * @Description: 
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRole>
     * @Author: zhangZaiFa
     * @Date:2019/8/20 9:50
     **/
    List<Role> findByProjectRoleId(List<Long> ids);

    /**@MethodName: findByProjectRoleId 查询角色列表
     * @Description:
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectRole>
     * @Author: zhangZaiFa
     * @Date:2019/8/20 9:50
     **/
    List<Role> findByProjectRoleIdCV5(List<Long> ids);

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

    /**
     *
     * 功能描述: 以角色名称查询角色信息
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/5 18:45
     */
    List<Role> findRoleByNamesCV5(@Param(value="sql") String sql, @Param(value = "projectId") Long projectId);

    /**@MethodName: findByProjectRole 查询项目角色
     * @Description: 
     * @Param: [projectId]
     * @Return: com.xjt.cloud.project.core.entity.Role
     * @Author: zhangZaiFa
     * @Date:2019/9/26 14:46 
     **/

    Role findByProjectRole(Role role);

    /**@MethodName: findByProjectRole 查询项目角色
     * @Description:
     * @Param: [projectId]
     * @Return: com.xjt.cloud.project.core.entity.Role
     * @Author: zhangZaiFa
     * @Date:2019/9/26 14:46
     **/

    Role findByProjectRoleCV5(Role role);
}
