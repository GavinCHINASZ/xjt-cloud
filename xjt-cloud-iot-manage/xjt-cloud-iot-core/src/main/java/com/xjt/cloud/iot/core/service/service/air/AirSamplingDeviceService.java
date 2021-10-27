package com.xjt.cloud.iot.core.service.service.air;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingDeviceService
 * @Description 空气采样设备管理
 * @Author wangzhiwen
 * @Date 2021/3/30 9:26
 **/
public interface AirSamplingDeviceService {

    /**
     * @Description 查询空气采样设备统计报表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingDeviceSummaryReport(String json);

    /**
     * @Description 查询空气采样设备信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingDeviceList(String json);

    /**
     * @Description 修改空气采样设备阈值
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyAirSamplingDevice(String json);

    /**
     * @Description 查询空气采样设备阈值修改列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingDeviceUpdateRecordList(String json);

    /**
     * @Description 下载空气采样设备信息
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    void downloadAirSamplingDeviceList(HttpServletResponse response, String json);
}
