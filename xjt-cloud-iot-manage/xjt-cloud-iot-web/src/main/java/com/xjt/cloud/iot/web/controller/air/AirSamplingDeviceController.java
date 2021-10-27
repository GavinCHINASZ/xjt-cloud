package com.xjt.cloud.iot.web.controller.air;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingController
 * @Description 空气采样设备管理
 * @Author wangzhiwen
 * @Date 2021/3/30 9:18
 **/
@RestController
@RequestMapping("/air/sampling/device/")
public class AirSamplingDeviceController extends AbstractController {

    @Autowired
    private AirSamplingDeviceService airSamplingDeviceService;

    /**
     * @Description 查询空气采样设备统计报表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingDeviceSummaryReport/{projectId}")
    public Data findAirSamplingDeviceSummaryReport(String json){
        return airSamplingDeviceService.findAirSamplingDeviceSummaryReport(json);
    }

    /**
     * @Description 查询空气采样设备信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingDeviceList/{projectId}")
    public Data findAirSamplingDeviceList(String json){
        return airSamplingDeviceService.findAirSamplingDeviceList(json);
    }

    /**
     * @Description 修改空气采样设备阈值
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyAirSamplingDevice/{projectId}")
    public Data modifyAirSamplingDevice(String json){
        return airSamplingDeviceService.modifyAirSamplingDevice(json);
    }

    /**
     * @Description 查询空气采样设备阈值修改列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingDeviceUpdateRecordList/{projectId}")
    public Data findAirSamplingDeviceUpdateRecordList(String json){
        return airSamplingDeviceService.findAirSamplingDeviceUpdateRecordList(json);
    }

    /**
     * @Description 下载空气采样设备信息
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @RequestMapping(value = "downloadAirSamplingDeviceList/{projectId}")
    public void downloadAirSamplingDeviceList(HttpServletResponse response, String json){
        airSamplingDeviceService.downloadAirSamplingDeviceList(response,json);
    }
}
