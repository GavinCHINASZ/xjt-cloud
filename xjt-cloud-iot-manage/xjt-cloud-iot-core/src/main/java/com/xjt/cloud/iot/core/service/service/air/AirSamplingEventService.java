package com.xjt.cloud.iot.core.service.service.air;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingEventService
 * @Description 空气采样设备事件管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:09
 **/
public interface AirSamplingEventService {
    /**
     * @Description 查询空气采样设备事件汇总曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingEventGraph(String json);

    /**
     * @Description 查询空气采样设备事件汇总饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingEventPie(String json);

    /**
     * @Description 查询空气采样设备事件列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findAirSamplingEventList(String json);

    /**
     * @Description 下载空气采样设备事件信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    void downloadAirSamplingEventList(HttpServletResponse response, String json);
}
