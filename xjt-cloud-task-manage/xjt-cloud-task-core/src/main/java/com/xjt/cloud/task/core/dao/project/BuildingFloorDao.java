package com.xjt.cloud.task.core.dao.project;

import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.project.TaskFloor;

import java.util.List;

/**
 *@ClassName BuildingFloorDao
 *@Author dwt
 *@Date 2019-08-07 9:45
 *@Description 建筑物DAO层接口
 *@Version 1.0
 */
public interface BuildingFloorDao {

    /**
     *@Author: dwt
     *@Date: 2020-07-27 10:01
     *@Param: TaskDevice
     *@Return: List<TaskFloor>
     *@Description 根据建筑物id和项目id查询楼层列表
     */
    List<TaskFloor> findFloorListByTaskDevice(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2020-07-27 10:01
     *@Param: TaskDevice
     *@Return: List<TaskFloor>
     *@Description 根据建筑物id和项目id查询楼层列表的选中和未选
     */
    List<TaskFloor> findFloorListSelOrNotSel(TaskDevice taskDevice);
}
