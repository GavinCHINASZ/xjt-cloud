package com.xjt.cloud.admin.manage.dao.device;

import com.xjt.cloud.admin.manage.entity.device.DeviceQrNo;

import java.util.List;

/**
 *@ClassName DeviceQrNoDao
 *@Author dwt
 *@Date 2020-10-26 14:00
 *@Description
 *@Version 1.0
 */
public interface DeviceQrNoDao {

    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:54
     *@Param:
     *@Return: void
     *@Description 清空test表
     */
    void deleteAllDeviceQrNo();

    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:57
     *@Param: com.xjt.cloud.admin.manage.entity.device.DeviceQrNo
     *@Return: void
     *@Description 保存要添加的二维码
     */
    void saveDeviceQrNo(List<DeviceQrNo> list);
    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:58
     *@Param: com.xjt.cloud.admin.manage.entity.device.DeviceQrNo
     *@Return: void
     *@Description 生成设备二维码和巡查点
     */
    void saveCheckPoint(List<DeviceQrNo> list);

    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:58
     *@Param: com.xjt.cloud.admin.manage.entity.device.DeviceQrNo
     *@Return: void
     *@Description 生成设备二维码和巡查点
     */
    void saveQrNo(List<DeviceQrNo> list);

    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:58
     *@Param: com.xjt.cloud.admin.manage.entity.device.DeviceQrNo
     *@Return: void
     *@Description 生成设备二维码和巡查点
     */
    int modifyQrNoProject(DeviceQrNo deviceQrNo);

    /**
     *@Author: dwt
     *@Date: 2020-10-28 9:58
     *@Param: com.xjt.cloud.admin.manage.entity.device.DeviceQrNo
     *@Return: void
     *@Description 生成设备二维码和巡查点
     */
    int modifyCheckPointProject(DeviceQrNo deviceQrNo);

}
