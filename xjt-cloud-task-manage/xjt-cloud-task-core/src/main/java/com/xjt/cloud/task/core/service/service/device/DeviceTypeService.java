package com.xjt.cloud.task.core.service.service.device;/**
 * @ClassName DeviceTypeService
 * @Author Administrator
 * @Date 2019-08-09 10:05
 * @Description TODO
 * @Version 1.0
 */

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName DeviceTypeService
 *@Author Administrator
 *@Date 2019-08-09 10:05
 *@Description TODO
 *@Version 1.0
 */
public interface DeviceTypeService {
    /**
     *@Author: dwt
     *@Date: 2019-08-08 14:24
     *@Param: 任务设备筛选实体
     *@Return: 设备系统列表
     *@Description: 按系统添加设备：根据项目id查询设备系统列表
     */
    Data findDeviceSysByProjectId(String json);

}
