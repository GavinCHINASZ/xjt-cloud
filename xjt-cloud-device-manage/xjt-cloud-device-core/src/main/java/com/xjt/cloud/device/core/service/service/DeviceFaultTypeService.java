package com.xjt.cloud.device.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * 设备故障类型Service
 *
 * @author huanggc
 * @date 2020/11/27
 */
public interface DeviceFaultTypeService {

    /**
     * 功能描述:查询设备故障类型信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceFaultTypeList(String json);

    /**
     * 功能描述:查询 异常类型(告警类型)
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findFireFaultTypeList(String json);
}
