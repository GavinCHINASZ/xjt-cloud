package com.xjt.cloud.task.core.dao.project;

import com.xjt.cloud.task.core.entity.project.Role;

import java.util.List;

/**
 *@ClassName RoleDao
 *@Author dwt
 *@Date 2019-08-09 9:37
 *@Description 角色Dao层接口
 *@Version 1.0
 */
public interface RoleDao {
    /**
     *@Author: dwt
     *@Date: 2019-08-09 9:39
     *@Param: java.lang.Long
     *@Return: 角色列表
     *@Description 根据项目id查询
     */
    List<Role> getRoleNameBySourceId(Long sourceId);

    /**
     *@Author: dwt
     *@Date: 2019-08-09 9:39
     *@Param: java.lang.Long
     *@Return: 角色列表
     *@Description 根据项目id查询
     */
    List<Role> getRoleNameBySourceIdCV5(Long sourceId);
}
