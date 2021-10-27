package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.OrgUser;
import com.xjt.cloud.safety.core.entity.project.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @ClassName ProjectPermissionDao 项目权限Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface PermissionDao {

    /**
     * @MethodName: findByPermission 查询权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/22 17:35
     **/
    List<Permission> findByPermission(Permission entity);

    /**
     * @MethodName: findByPermissionParentId 查询子权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/7/23 10:36
     **/
    List<Permission> findByPermissionParentId(Permission entity);

    /**
     * @MethodName: findByUserPermission 查询用户权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<java.security.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:04
     **/
    List<Permission> findByUserPermission(Permission entity);

    /**
     * @MethodName: findByPermissionSet 查询权限父目录和自己
     * @Description:
     * @Param: [permissionIdSet]
     * @Return: java.util.Set<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:37
     **/
    Set<Long> findByPermissionSet(List<Long> list);

    /**@MethodName: findByOrgUserProjectPermission 查询当前用户在所属项目的权限
     * @Description:
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/11/7 11:46
     **/
    List<String> findByOrgUserProjectPermission(OrgUser orgUser);

}
