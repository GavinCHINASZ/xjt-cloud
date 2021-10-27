package com.xjt.cloud.task.core.dao.project;


import com.xjt.cloud.task.core.entity.project.TaskBuilding;
import com.xjt.cloud.task.core.entity.task.TaskDevice;

import java.util.List;

/**
 *@ClassName BuildingDao
 *@Author dwt
 *@Date 2019-08-06 17:58
 *@Description 建筑物Dao层接口
 *@Version 1.0
 */
public interface TaskBuildingDao {

    /**
     *@Author: dwt
     *@Date: 2019-08-06 18:01
     *@Param: java.lang.Long
     *@Return: 建筑物列表
     *@Description: 根据项目id查询建筑列表
     */
    List<TaskBuilding> findBuildingListByProjectId(TaskDevice taskDevice);
    List<TaskBuilding> findByProjectId(Long projectId);
    /**
     *@Author: dwt
     *@Date: 2019-08-08 10:37
     *@Param: 任务设备筛选实体
     *@Return: 建筑物列表
     *@Description: 按建筑物楼添加设备：筛选参数
     */
    List<TaskBuilding> findBuilding(TaskDevice taskDevice);
    List<TaskBuilding> findBuildingSelORNOtSel(TaskDevice taskDevice);
}
