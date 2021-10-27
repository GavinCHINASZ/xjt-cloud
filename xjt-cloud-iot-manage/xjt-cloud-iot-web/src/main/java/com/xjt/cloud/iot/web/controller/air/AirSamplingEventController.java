package com.xjt.cloud.iot.web.controller.air;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.air.AirSamplingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AirSamplingEventController
 * @Description 空气采样设备事件管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:05
 **/
@RestController
@RequestMapping("/air/sampling/event/")
public class AirSamplingEventController extends AbstractController {
    @Autowired
    private AirSamplingEventService airSamplingEventService;

    /**
     * @Description 查询空气采样设备事件汇总曲线图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingEventGraph/{projectId}")
    public Data findAirSamplingEventGraph(String json){
        return airSamplingEventService.findAirSamplingEventGraph(json);
    }

    /**
     * @Description 查询空气采样设备事件汇总饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingEventPie/{projectId}")
    public Data findAirSamplingEventPie(String json){
        return airSamplingEventService.findAirSamplingEventPie(json);
    }

    /**
     * @Description 查询空气采样设备事件列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 14:40
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findAirSamplingEventList/{projectId}")
    public Data findAirSamplingEventList(String json){
        return airSamplingEventService.findAirSamplingEventList(json);
    }

    /**
     * @Description 下载空气采样设备事件信息列表
     *
     * @param response
     * @param json
     * @author wangzhiwen
     * @date 2021/4/1 17:38
     * @return void
     */
    @RequestMapping(value = "downloadAirSamplingEventList/{projectId}")
    public void downloadAirSamplingEventList(HttpServletResponse response, String json){
        airSamplingEventService.downloadAirSamplingEventList(response,json);
    }
}
