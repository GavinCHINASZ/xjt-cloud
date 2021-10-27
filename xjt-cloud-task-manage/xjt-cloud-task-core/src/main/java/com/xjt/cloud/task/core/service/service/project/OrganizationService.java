package com.xjt.cloud.task.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName OrganizationService
 *@Author dwt
 *@Date 2019-08-09 10:24
 *@Description 组织架构Service层
 *@Version 1.0
 */
public interface OrganizationService {
    /**
     *@Author: dwt
     *@Date: 2019-08-07 16:23
     *@Param: com.xjt.cloud.task.core.entity.task.TaskDevice
     *@Return: 组织架构实体
     *@Description 根据条件筛选公司  条件筛选
     */
    Data findCompanyByTaskDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2019-08-07 17:42
     *@Param: java.lang.Long
     *@Return:  组织架构实体
     *@Description 根据项目id查询公司
     */
    Data findOrganizationByProjectId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-24 9:31
     *@Param: json
     *@Return: Data
     *@Description 查询部门以及子部门下的所有巡查点
     */
    Data findOrgAndSonOrgCheckPoints(String json);
}
