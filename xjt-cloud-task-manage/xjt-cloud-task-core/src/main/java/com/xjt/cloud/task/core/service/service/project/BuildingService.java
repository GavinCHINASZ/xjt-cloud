package com.xjt.cloud.task.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName BuildingService
 *@Author dwt
 *@Date 2019-08-09 10:20
 *@Description 建筑物Service层
 *@Version 1.0
 */
public interface BuildingService {
    /**
     *@Author: dwt
     *@Date: 2019-08-06 18:03
     *@Param: java.lang.Long
     *@Return: 建筑物，楼层，巡更点列表
     *@Description: 按建筑物楼层添加设备：根据项目id查询
     */
    Data findBuildingByProjectId(String json);
    /**
     *@Author: dwt
     *@Date: 2019-08-08 10:37
     *@Param: 任务设备筛选实体
     *@Return: 建筑物列表
     *@Description: 按建筑物楼添加设备：筛选参数
     */
    Data findBuilding(String json);
}
