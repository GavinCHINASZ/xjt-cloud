package com.xjt.cloud.iot.core.dao.device;

import com.xjt.cloud.iot.core.entity.device.IotDeviceReport;

import java.util.List;

/**
 *IotDeviceReportDao
 *
 * @author huanggc
 * @date 2020/11/25
 */
public interface IotDeviceReportDao {
    /**
     * 功能描述:物联设备故障统计
     *
     * @param iotDeviceReport IotDeviceReport
     * @author huanggc
     * @date 2020/11/25
     * @return List<IotDeviceReport>
     */
    List<IotDeviceReport> iotDeviceFaultStatistics(IotDeviceReport iotDeviceReport);
}
