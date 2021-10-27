package com.xjt.cloud.task.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName Role
 *@Author dwt
 *@Date 2019-08-09 9:57
 *@Description 角色service层
 *@Version 1.0
 */
public interface RoleService {
    /**
     *@Author: dwt
     *@Date: 2019-08-09 9:54
     *@Param: java.lang.Long
     *@Return: 角色列表
     *@Description
     */
    Data findRoleByProjectId(String json);
}
