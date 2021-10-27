package com.xjt.cloud.iot.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 * DeviceService
 *
 *@author huanggc
 *@date 2020/11/25
 */
public interface DeviceService {

    /**
     * 功能描述:物联设备故障统计
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/25
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data iotDeviceFaultStatistics(String json);
}
