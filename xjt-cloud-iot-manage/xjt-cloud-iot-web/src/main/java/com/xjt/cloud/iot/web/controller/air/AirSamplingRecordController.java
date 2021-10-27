package com.xjt.cloud.iot.web.controller.air;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingRecordController
 * @Description 空气采样设备记录管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:05
 **/
@RestController
@RequestMapping("/air/sampling/record/")
public class AirSamplingRecordController extends AbstractController {
    @Autowired
    private AirSamplingRecordService airSamplingRecordService;

    /**
     * @Description 查询空气采样记录曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingRecordGraph/{projectId}")
    public Data findAirSamplingRecordGraph(String json){
        return airSamplingRecordService.findAirSamplingRecordGraph(json);
    }

    /**
     * @Description 查询空气采样记录列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 10:13
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingRecordList/{projectId}")
    public Data findAirSamplingRecordList(String json){
        return airSamplingRecordService.findAirSamplingRecordList(json);
    }

    /**
     * @Description 下载空气采样单个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @RequestMapping(value = "downloadAirSamplingDeviceRecordList/{projectId}")
    public void downloadAirSamplingDeviceRecordList(HttpServletResponse response, String json){
        airSamplingRecordService.downloadAirSamplingDeviceRecordList(response,json);
    }

    /**
     * @Description 下载空气采样多个设备记录信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @RequestMapping(value = "downloadAirSamplingDevicesRecordList/{projectId}")
    public void downloadAirSamplingDevicesRecordList(HttpServletResponse response, String json){
        airSamplingRecordService.downloadAirSamplingDevicesRecordList(response,json);
    }

    /**
     * @Description 空气采样数据获取接口
     *
     * @author wangzhiwen
     * @date 2021/4/2 11:37
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "airSamplingRecordGetDataTask")
    public Data airSamplingRecordGetDataTask(){
        return airSamplingRecordService.airSamplingRecordGetDataTask();
    }
}
