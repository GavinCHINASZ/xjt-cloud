package com.xjt.cloud.task.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName Device
 *@Author dwt
 *@Date 2019-08-09 11:05
 *@Description 设备Service接口
 *@Version 1.0
 */
public interface DeviceService {
    /**
     *@Author: dwt
     *@Date: 2019-08-07 11:23
     *@Param: java.lang.Long
     *@Return: 设备列表
     *@Description 根据巡更点查询设备列表
     */
    Data findDeviceListByCheckPointId(String json);
    /**
     *@Author: dwt
     *@Date: 2019-08-15 14:54
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据任务id查询设备列表
     */
    Data findAppTaskDeviceListByTaskId(String json);
}
