package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:12
 * @Description:
 */
@Repository
public interface PermissionDao {
    /**
     *
     * 功能描述: 查询所有菜单权限列表
     *
     * @param
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    List<String> getPermissionList();

    /**
     *
     * 功能描述: 查询用户权限列表
     *
     * @param userId
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    List<Permission> getUserPermissionList(@Param(value = "userId")Long userId);

    /**
     *
     * 功能描述: 查询用户权限菜单列表
     *
     * @param userId
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    List<Permission> findUserMenuList(@Param(value = "userId")Long userId);

    /**@MethodName: permissionListPage
     * @Description: 查询列表
     * @Param: [permission]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 9:44
     **/
    List<Permission> findPermissionListPage(Permission permission);

    /**@MethodName: permissionListPageCount
     * @Description: 查询总数
     * @Param: [permission]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/12/11 9:44
     **/
    Integer findPermissionListPageCount(Permission permission);

    /**@MethodName: savePermission
     * @Description: 保存权限
     * @Param: [permission]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/13 14:14
     **/
    void savePermission(Permission permission);

    /**@MethodName: modifyPermission
     * @Description: 修改权限
     * @Param: [permission]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/13 14:14
     **/
    void modifyPermission(Permission permission);

    /**
     *
     * 功能描述:查询权限关联的项目列表与状态
     *
     * @param permission
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Permission> findRoleProjectList(Permission permission);

    /**
     *
     * 功能描述:查询权限关联的项目列表与状态
     *
     * @param permission
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findRoleProjectListTotalCount(Permission permission);


    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param permission
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Permission> findRoleRelationPermissionList(Permission permission);

    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param permission
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findRoleRelationPermissionListTotalCount(Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param permission
     * @return: List<Role>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Permission> findNotRoleRelationPermissionList(Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param permission
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findNotRoleRelationPermissionListTotalCount(Permission permission);

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int saveRoleRelationPermission(Permission permission);

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int delRoleRelationPermission(Permission permission);

    /**
     * @param permission
     * @return int
     * @Description 修改角色权限信息
     * @author wangzhiwen
     * @date 2020/10/13 15:09
     */
   int modifyRolePermission(Permission permission);

    //////////////////////////////////////////  管理员默认权限管理///////////////////////////////////////
    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param permission
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Permission> findProjectManagerRelationPermissionList(Permission permission);

    /**
     *
     * 功能描述:查询已关联角色权限信息列表
     *
     * @param permission
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findProjectManagerRelationPermissionListTotalCount(Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param permission
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    List<Permission> findNotProjectManagerRelationPermissionList(Permission permission);

    /**
     *
     * 功能描述:查询未关联角色权限信息列表
     *
     * @param permission
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    Integer findNotProjectManagerRelationPermissionListTotalCount(Permission permission);

    /**
     *
     * 功能描述: 保存角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int saveProjectManagerRelationPermission(Permission permission);

    /**
     *
     * 功能描述: 删除角色权限
     *
     * @param permission
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:58
     */
    int delProjectManagerRelationPermission(Permission permission);
}
