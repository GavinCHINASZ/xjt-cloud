package com.xjt.cloud.task.core.dao.project;

import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.TaskOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@ClassName OrganizaitionDao
 *@Author dwt
 *@Date 2019-08-06 14:01
 *@Description 部门Dao层接口
 *@Version 1.0
 */
public interface TaskOrganizationDao {
    
    /**
     *@Author: dwt
     *@Date: 2019-08-07 16:17
     *@Param: java.lang.Long
     *@Return: 组织架构实体
     *@Description 根据公司id和项目id关联查询部门
     */
    List<TaskOrganization> findOrganizationByCompanyId(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2020-07-27 11:06
     *@Param: TaskDevice
     *@Return: 组织架构实体
     *@Description 根据公司id和项目id关联查询部门（已选择和未选择）
     */
    List<TaskOrganization> findOrganizationSelOrNotSel(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-08-07 16:23
     *@Param: com.xjt.cloud.task.core.entity.task.TaskDevice
     *@Return: 组织架构实体
     *@Description 根据条件筛选部门
     */
    List<TaskOrganization> findOrgByTaskDevice(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-08-07 16:23
     *@Param: com.xjt.cloud.task.core.entity.task.TaskDevice
     *@Return: 组织架构实体
     *@Description 根据条件筛选部门
     */
    List<TaskOrganization> findOrgSelOrNotSel(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-08-07 17:42
     *@Param: java.lang.Long
     *@Return:  组织架构实体
     *@Description 根据项目id查询公司
     */
    List<TaskOrganization> findOrganizationByProjectId(Long projectId);
    /**
     *@Author: dwt
     *@Date: 2019-08-12 11:38
     *@Param: java.lang.Long
     *@Return: java.lang.String
     *@Description: 根据id查询组织架构名称
     */
    String findOrgNameByCheckPointId(Long checkPointId);
    /**
     *@Author: dwt
     *@Date: 2019-08-07 17:42
     *@Param: java.lang.Long
     *@Return:  组织架构实体
     *@Description 根据项目id查询公司
     */
    List<TaskOrganization> findCompanyByProjectId(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-08-07 17:42
     *@Param: java.lang.Long
     *@Return:  组织架构实体
     *@Description 根据项目id查询公司
     */
    List<TaskOrganization> findCompanyByProjectIdSelOrNotSel(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-11-11 10:10
     *@Param: java.lang.Long
     *@Return: 组织架构实体
     *@Description 根据id查询组织架构
     */
    List<TaskOrganization> findOrganizationById(Long id);
    /**
     *@Author: dwt
     *@Date: 2019-11-12 16:41
     *@Param: TaskDevice
     *@Return: 组织架构实体
     *@Description 根据条件筛选公司
     */
    List<TaskOrganization> findCompanyByTaskDevice(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-19 10:24
     *@Param: java.lang.Long
     *@Return: TaskOrganization
     *@Description 根据公司id查询公司
     */
    TaskOrganization findCompanyByCompanyId(Long id);
    /**
     *@Author: dwt
     *@Date: 2020-08-05 10:13
     *@Param: java.lang.Long
     *@Return: TaskOrganization
     *@Description 根据公司id查询公司以及选中状态
     */
    TaskOrganization findCompanyByCompanyIdSelOrNotSel(TaskDevice taskDevice);

    /**@MethodName: findUserOrgName
     * @Description:查询用户部门名称
     * @Param: [userId, projectId]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2020/4/30 9:43
     **/
    TaskOrganization findUserOrgName(@Param("userId") Long userId,@Param("projectId") Long projectId);
}
