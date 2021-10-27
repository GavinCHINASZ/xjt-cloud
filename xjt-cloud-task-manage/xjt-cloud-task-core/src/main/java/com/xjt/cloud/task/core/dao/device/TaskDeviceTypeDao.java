package com.xjt.cloud.task.core.dao.device;

import com.xjt.cloud.task.core.entity.TaskDeviceType;
import com.xjt.cloud.task.core.entity.task.TaskDevice;

import java.util.List;

/**
 *@ClassName DeviceType
 *@Author dwt
 *@Date 2019-08-07 14:43
 *@Description 设备类型Dao层接口
 *@Version 1.0
 */
public interface TaskDeviceTypeDao {
    /**
     *@Author: dwt
     *@Date: 2019-08-08 14:24
     *@Param: 任务设备筛选实体
     *@Return: 设备系统列表
     *@Description: 按系统添加设备：根据项目id查询设备系统列表
     */
   List<TaskDeviceType> findDeviceSysByProjectId(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-08-08 14:24
     *@Param: 任务设备筛选实体
     *@Return: 设备系统列表
     *@Description: 按系统添加设备：根据项目id查询设备系统列表
     */
    List<TaskDeviceType> findDeviceSysByProjectIdSelOrNotSel(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-08-08 14:24
     *@Param: 任务设备筛选实体
     *@Return: 设备类型列表
     *@Description: 按系统添加设备：根据系统id查询设备类型列表
     */
   List<TaskDeviceType> findDeviceTypeBySysId(TaskDevice taskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-08-08 14:24
     *@Param: 任务设备筛选实体
     *@Return: 设备类型列表
     *@Description: 按系统添加设备：根据系统id查询设备类型列表
     */
    List<TaskDeviceType> findDeviceTypeBySysIdSelOrNotSel(TaskDevice taskDevice);

    /**
     *@Author: dwt
     *@Date: 2019-11-04 17:26
     *@Param: java.lang.Long
     *@Return: 设备类型列表
     *@Description 根据巡查点id查询设备类型列表
     */
   List<TaskDeviceType> findDeviceTypeByCheckPointId(Long checkPointId);
}
