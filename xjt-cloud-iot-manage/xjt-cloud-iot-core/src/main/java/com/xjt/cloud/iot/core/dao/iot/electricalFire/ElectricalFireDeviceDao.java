package com.xjt.cloud.iot.core.dao.iot.electricalFire;

import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireDevice;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireReport;

import java.util.List;

/**
 *@ClassName ElectricalFireDeviceDao
 *@Author dwt
 *@Date 2020-09-08 9:51
 *@Description 电气火灾设备Dao层
 *@Version 1.0
 */
public interface ElectricalFireDeviceDao {
    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:53
     *@Param: ElectricalFireDevice
     *@Return: ElectricalFireDevice
     *@Description 根据条件查询电气火灾设备列表
     */
    List<ElectricalFireDevice>  findElectricalFireDeviceList(ElectricalFireDevice electricalFireDevice);
    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:56
     *@Param: ElectricalFireDevice
     *@Return: java.lang.Integer
     *@Description 保存电气火灾设备信息
     */
    Integer saveElectricalFireDevice(ElectricalFireDevice electricalFireDevice);
    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:57
     *@Param: ElectricalFireDevice
     *@Return: java.lang.Integer
     *@Description 修改电气火灾设备信息
     */
    Integer modifyElectricalFireDevice(ElectricalFireDevice electricalFireDevice);
    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:57
     *@Param: ElectricalFireDevice
     *@Return: java.lang.Integer
     *@Description 查询电气火灾设备总数
     */
    Integer findElectricalFireDeviceCount(ElectricalFireDevice electricalFireDevice);

    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:53
     *@Param: ElectricalFireDevice
     *@Return: ElectricalFireDevice
     *@Description 根据条件查询电气火灾设备
     */
    ElectricalFireDevice  findElectricalFireDevice(ElectricalFireDevice electricalFireDevice);
    /**
     *@Author: dwt
     *@Date: 2020-09-21 11:20
     *@Param: ElectricalFireDevice
     *@Return: ElectricalFireReport
     *@Description 查询电气火灾设备总数和在线数
     */
    ElectricalFireReport findElectricalFireDeviceStatusCount(ElectricalFireReport electricalFireReport);
    /**
     *@Author: dwt
     *@Date: 2020-09-28 10:38
     *@Param: ElectricalFireDevice
     *@Return: java.lang.Integer
     *@Description 更新电气火灾设备状态或者心跳时间
     */
    Integer modifyElectricalFireDeviceStatus(ElectricalFireDevice electricalFireDevice);
    /**
     *@Author: dwt
     *@Date: 2020-10-13 16:06
     *@Param: ElectricalFireDevice
     *@Return: java.lang.Integer
     *@Description 更新电气火灾工作状态
     */
    Integer modifyElectricalFireWorkStatus(ElectricalFireDevice electricalFireDevice);

    /**
     *@Author: dwt
     *@Date: 2020-10-13 15:04
     *@Param:
     *@Return: ElectricalFireDevice
     *@Description 查询所有电气火灾设备
     */
    List<ElectricalFireDevice> findAllElectricalFireDevice();
}
