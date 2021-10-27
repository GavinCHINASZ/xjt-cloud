package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.entity.project.PermissionPath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:12
 * @Description:
 */
@Repository
public interface PermissionPathDao {

    /**@MethodName: permissionPathListPage
     * @Description: 权限Path集合
     * @Param: [permissionPath]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.PermissionPath>
     * @Author: zhangZaiFa
     * @Date:2019/12/12 9:50
     **/
    List<PermissionPath> findPermissionPathListPage(PermissionPath permissionPath);

    /**@MethodName: permissionPathListPageCount
     * @Description: 权限Path总数
     * @Param: [permissionPath]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/12/12 9:50
     **/
    Integer findPermissionPathListPageCount(PermissionPath permissionPath);

    /**@MethodName: savePermissionPath
     * @Description: 保存权限Path
     * @Param: [permissionPath]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/13 13:35
     **/
    void savePermissionPath(PermissionPath permissionPath);

    /**@MethodName: modifyPermissionPath
     * @Description: 修改权限Path
     * @Param: [permissionPath]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/13 13:35
     **/
    void modifyPermissionPath(PermissionPath permissionPath);

    /**@MethodName: deletePermissionPath
     * @Description: 删除权限Path
     * @Param: [permissionPath]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/13 13:35
     **/
    void deletePermissionPath(PermissionPath permissionPath);

    /**@MethodName: savePermissionPaths
     * @Description: 保存权限Path
     * @Param: [list]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/2 11:22
     **/
    void savePermissionPaths(List<PermissionPath> list);
}
