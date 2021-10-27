package com.xjt.cloud.sys.core.dao.project;

import com.xjt.cloud.sys.core.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/11 09:32
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
    List<String> getPermissionList(@Param(value = "projectType")Long projectType);

    /**
     *
     * 功能描述: 查询用户权限列表
     *
     * @param userId
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    List<Permission> getUserPermissionList(@Param(value = "userId")Long userId, @Param(value = "projectType")Long projectType);

    /**
     *
     * 功能描述: 查询用户权限列表
     *
     * @param userId
     * @return: List<Permission>
     * @auther: wangzhiwen
     * @date: 2019/7/22 16:02
     */
    List<Permission> getUserPermissionListCV5(@Param(value = "userId")Long userId, @Param(value = "projectType")Long projectType);
}
