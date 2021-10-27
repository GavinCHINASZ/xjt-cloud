package com.xjt.cloud.admin.manage.dao.iot;

import com.xjt.cloud.admin.manage.entity.iot.IotDeviceReport;

import java.util.List;

/**
 *@ClassName IotDeviceReportDao
 *@Author dwt
 *@Date 2020-11-10 9:39
 *@Version 1.0
 */
public interface IotDeviceReportDao {

    /**
     *@Author: dwt
     *@Date: 2020-11-10 9:45
     *@Param:
     *@Return: 
     *@Description 查询火警主机物联设备报表
     */
    List<IotDeviceReport> findIotDeviceReportList(IotDeviceReport iotDeviceReport);

    /**
     *@Author: dwt
     *@Date: 2020-11-10 9:45
     *@Param:
     *@Return:
     *@Description 查询火警主机物联设备总数
     */
    Integer findIotDeviceReportListCount(IotDeviceReport iotDeviceReport);

    /**
     *@Author: dwt
     *@Date: 2020-11-16 11:15
     *@Param:
     *@Return:
     *@Description 查询火警主机饼图数据
     */
    IotDeviceReport findIotFireAlarmDevicePieData();
}
