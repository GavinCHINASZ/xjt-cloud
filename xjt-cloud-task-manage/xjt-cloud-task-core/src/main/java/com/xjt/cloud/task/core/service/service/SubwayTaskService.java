package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName SubwayTaskService
 *@Author dwt
 *@Date 2020-06-19 9:36
 *@Version 1.0
 */
public interface SubwayTaskService {

    /**
     *@Author: dwt
     *@Date: 2020-06-19 9:34
     *@Param: json
     *@Return: Data
     *@Description 地铁平面图接口
     */
    Data findCurrentMontSubwayTask(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-22 14:31
     *@Param: json
     *@Return: Data
     *@Description 地铁平面图建筑物楼层巡查点统计
     */
    Data findBuildingFloorMetroCheckPointCount(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-30 14:32
     *@Param: json
     *@Return: Data
     *@Description 地铁平面图根据任务id查询建筑物楼层
     */
    Data findBuildingAndFloorListByTaskId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-02 16:43
     *@Param: json
     *@Return: Data
     *@Description 地铁平面图布点巡查点列表查询
     */
    Data findCheckPointListByTaskIdSubway(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-02 17:36
     *@Param: json
     *@Return: Data
     *@Description 地铁平面图查询用户信息
     */
    Data findUserMessageByUserId(String json);
}
