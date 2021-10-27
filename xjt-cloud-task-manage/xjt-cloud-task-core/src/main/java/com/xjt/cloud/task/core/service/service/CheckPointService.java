package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName CheckPointService
 *@Author dwt
 *@Date 2019-08-09 10:15
 *@Description 巡更点Service接口
 *@Version 1.0
 */
public interface CheckPointService {
    /**
     *@Author: dwt
     *@Date: 2019-08-08 11:09
     *@Param: json
     *@Return: 巡更点列表
     *@Description: 按巡更点添加设备：根据项目id查询
     */
    Data findCheckPointList(String json);

    /**
     *@Author: dwt
     *@Date: 2019-08-08 11:44
     *@Param: 任务设备筛选实体
     *@Return: 巡更点列表
     *@Description: 根据巡更点添加设备：筛选参数
     */
    Data findCheckPoint(String  json);
    /**
     *@Author: dwt
     *@Date: 2019-10-14 11:28
     *@Param: Data
     *@Return: 寻根点设备列表
     *@Description 根据任务id查询
     */
    Data findCheckPointDeviceList(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-10-11 17:40
     *@Param: json
     *@Return: Data
     *@Description 保存巡更点添加事物
     */
    Data transactionSaveCheckPoint(String json);
    /**
     *@Author: dwt
     *@Date: 2019-10-14 14:26
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 保存巡更点
     */
    Data saveCheckPoint(String json);
    /**
     *@Author: dwt
     *@Date: 2020-05-21 14:31
     *@Param: json
     *@Return: Data
     *@Description 查询楼层的巡查点列表
     */
    Data findCheckPointListByFloorId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-05-21 14:31
     *@Param: json
     *@Return: Data
     *@Description 根据巡查点id查询巡查点下的设备列表
     */
    Data findDeviceTypeByCheckPointId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-15 16:28
     *@Param: json
     *@Return: Data
     *@Description 根据设备类型id查询巡更点列表
     */
    Data findCheckPointByDeviceTypeId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-28 15:22
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据巡查点id删除任务中的巡查点
     */
    Data deleteCheckPointByCheckPointId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-08-25 14:16
     *@Param: json
     *@Return: Data
     *@Description APP端添加设备巡查点列表
     */
    Data findCheckPointListBuildingAndSys(String json) throws Exception;
}
