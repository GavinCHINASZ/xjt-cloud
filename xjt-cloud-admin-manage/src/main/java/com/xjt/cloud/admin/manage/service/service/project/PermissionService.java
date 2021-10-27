package com.xjt.cloud.admin.manage.service.service.project;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.project.Path;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.PermissionPath;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:11
 * @Description: 权限管理接口
 */
public interface PermissionService {

    /**
     *
     * 功能描述: 查询用户权限列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    Data getUserPermissionList(String json);

    /**
     *
     * 功能描述: 查询所有权限菜单列表
     *
     * @param userId
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    Map<Permission, List<Permission>> findUserMenuList(Long userId);

    /**
     *
     * 功能描述: 查询所有菜单权限列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    Data getPermissionList(Boolean isClear);

    /**@MethodName: permissionListPage
     * @Description: 查询权限列表
     * @Param: [ajaxPage, permission]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 9:42
     **/
    ScriptPage<Permission> findPermissionListPage(AjaxPage ajaxPage, Permission permission);

    /**@MethodName: permissionListPage
     * @Description: 查询权限列表
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 9:42
     **/
    List<Permission> findPermissionList();

    /**@MethodName: savePermission
     * @Description: 保存权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/11 16:25
     **/
    Data savePermission(Permission permission);

    /**@MethodName: permissionPathListPage
     * @Description: 查询权限接口
     * @Param: [ajaxPage, permissionPath]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.PermissionPath>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 18:21
     **/
    ScriptPage<PermissionPath> findPermissionPathListPage(AjaxPage ajaxPage, PermissionPath permissionPath);

    /**@MethodName: permissionPathListPage
     * @Description: 查询接口路径
     * @Param: [ajaxPage, permissionPath]
     * @Return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.project.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/12/11 17:23
     **/
    ScriptPage<Path> findPathListPage(AjaxPage ajaxPage, Path path);

    /**@MethodName: savePath
     * @Description: 添加Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:31
     **/
    Data savePath(Path path);

    /**@MethodName: modifyPath
     * @Description: 修改Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    Data modifyPath(Path path);

    /**@MethodName: modifyPath
     * @Description: 删除Path
     * @Param: [path]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:32
     **/
    Data delPath(Path path);

    /**@MethodName: modifyPermission
     * @Description: 修改权限
     * @Param: [permission]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:45
     **/
    Data modifyPermission(Permission permission);

    /**
     *
     * 功能描述:查询权限关联的项目列表与状态
     *
     * @param permission
     * @return: ScriptPage<Permission>
     * @auther: wangzhiwen
     * @date: 2020/8/6 15:26
     */
    ScriptPage<Permission> findRoleProjectList(AjaxPage ajaxPage,Permission permission);

    /**@MethodName: deletePermissionPath
     * @Description: 删除权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:45
     **/
    Data deletePermissionPath(PermissionPath permissionPath);

    /**@MethodName: modifyPermissionPath
     * @Description: 修改权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:46
     **/
    Data modifyPermissionPath(PermissionPath permissionPath);

    /**@MethodName: savePermissionPath
     * @Description: 保存权限Path
     * @Param: [permissionPath]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/13 9:46
     **/
    Data savePermissionPath(String list);

    /**@MethodName: findPathList
     * @Description: 查询Path列表
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/13 18:07
     **/
    List<Path> findPathList(Path path);

    /**@MethodName: findPermissionPathTree
     * @Description: 查询权限树结构
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2020/4/1 14:18
     **/
    List<Path> findPermissionPathTree(Path path);
}
