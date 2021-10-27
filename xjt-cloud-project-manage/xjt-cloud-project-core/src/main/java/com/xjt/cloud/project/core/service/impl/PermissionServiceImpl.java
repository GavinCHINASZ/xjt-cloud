package com.xjt.cloud.project.core.service.impl;

import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.project.core.dao.project.PermissionDao;
import com.xjt.cloud.project.core.entity.OrgUser;
import com.xjt.cloud.project.core.entity.Permission;
import com.xjt.cloud.project.core.service.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @ClassName ProjectPermissionServiceImpl 项目参与成员实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class PermissionServiceImpl extends AbstractService implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;


    /**
     * @MethodName: findByPermission 角色权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:37
     **/
    @Override
    public List<Permission> findByPermission(Permission entity) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return permissionDao.findByPermissionCV5(entity);
        }
        return permissionDao.findByPermission(entity);
    }

    /**
     * @MethodName: findByPermissionParentId 查询子权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:37
     **/
    @Override
    public List<Permission> findByPermissionParentId(Permission entity) {
        return permissionDao.findByPermissionParentId(entity);
    }

    /**
     * @MethodName: findByUserPermission 查询用户权限
     * @Description:
     * @Param: [map]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectPermission>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:37
     **/
    @Override
    public List<Permission> findByUserPermission(Permission entity) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return permissionDao.findByUserPermissionCV5(entity);
        }
        return permissionDao.findByUserPermission(entity);
    }

    /**
     * @MethodName: findByPermissionSet 查询权限父目录和自己
     * @Description:
     * @Param: [permissionIdSet]
     * @Return: java.util.Set<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/8/1 14:37
     **/
    @Override
    public Set<Long> findByPermissionSet(List<Long> permissionIds) {
        return permissionDao.findByPermissionSet(permissionIds);
    }

    /**@MethodName: findByOrgUserProjectPermission 查询当前用户在所属项目的权限
     * @Description:
     * @Param: [orgUser]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Permission>
     * @Author: zhangZaiFa
     * @Date:2019/11/7 11:46
     **/
    @Override
    public List<String> findByOrgUserProjectPermission(OrgUser orgUser) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")) {
            return permissionDao.findByOrgUserProjectPermissionCV5(orgUser);
        }else{
            return permissionDao.findByOrgUserProjectPermission(orgUser);
        }
    }
}
