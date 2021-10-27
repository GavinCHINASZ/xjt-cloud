package com.xjt.cloud.project.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.device.DeviceSystem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *@ClassName DeviceService
 *@Author dwt
 *@Date 2020-04-10 15:57
 *@Version 1.0
 */
public interface DeviceService {
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:12
     *@Param: Device
     *@Return: Data
     *@Description 查询设备列表
     */
    Data findDeviceList(String json);
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:13
     *@Param: Device
     *@Return: Data
     *@Description 查询设备信息
     */
    Data findDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:15
     *@Param: Device
     *@Return: Data
     *@Description 保存设备
     */
    Data saveDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:16
     *@Param: Device
     *@Return: Data
     *@Description 批量保存设备
     */
    Data saveDevices(String json);

    /**
     *@Author: dwt
     *@Date: 2020-04-11 9:17
     *@Param: json
     *@Return: Map
     *@Description 查询项目下所有系统设备
     */
    List<DeviceSystem> findAllDeviceListByProjectInfoId(Long projectInfoId);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 10:17
     *@Param: json,file
     *@Return: Data
     *@Description 导入设备列表
     */
    Data uploadDeviceList(String json, MultipartFile file);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 17:53
     *@Param: json
     *@Return: Data
     *@Description 逻辑删除
     */
    Data deleteDevice(String json);

}
