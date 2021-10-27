package com.xjt.cloud.iot.core.service.service.air;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingRecordService
 * @Description 空气采样设备记录管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:10
 **/
public interface AirSamplingRecordService {

    /**
     * @Description 查询空气采样记录曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingRecordGraph(String json);

    /**
     * @Description 查询空气采样记录列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingRecordList(String json);

    /**
     * @Description 下载空气采样单个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    void downloadAirSamplingDeviceRecordList(HttpServletResponse response, String json);

    /**
     * @Description 下载空气采样多个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    void downloadAirSamplingDevicesRecordList(HttpServletResponse response, String json);

    /**
     * @Description 空气采样数据获取接口
     *
     * @author wangzhiwen
     * @date 2021/4/2 11:37
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data airSamplingRecordGetDataTask();
}
