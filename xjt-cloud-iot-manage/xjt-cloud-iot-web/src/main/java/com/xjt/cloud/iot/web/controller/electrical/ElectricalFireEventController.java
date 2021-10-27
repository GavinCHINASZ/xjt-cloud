package com.xjt.cloud.iot.web.controller.electrical;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName ElectricalFireEventController
 * @Author dwt
 * @Date 2020-09-23 14:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/electrical/event")
public class ElectricalFireEventController extends AbstractController {

    @Autowired
    private ElectricalFireEventService electricalFireEventService;

    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:02
     *@Param: ElectricalFireEvent
     *@Return: ElectricalFireEvent
     *@Description 查询电气火灾事件列表
     */
    @RequestMapping(value = "findElectricalFireEventList/{projectId}")
    public Data findElectricalFireEventList(String json){
        return  electricalFireEventService.findElectricalFireEventList(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-09-24 9:14
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 电气火灾事件处理
     */
    @RequestMapping(value = "modifyElectricalFireEvent/{projectId}")
    public Data modifyElectricalFireEvent(String json) {
        return electricalFireEventService.modifyElectricalFireEvent(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-09-24 10:28
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾事件汇总分析
     */
    @RequestMapping(value = "findElectricalFireEventSummaryAnalysis/{projectId}")
    public Data findElectricalFireEventSummaryAnalysis(String json) {
        return electricalFireEventService.findElectricalFireEventSummaryAnalysis(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-09-24 14:08
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾事件详情
     */
    @RequestMapping(value = "findElectricalFireEventDetail/{projectId}")
    public Data findElectricalFireEventDetail(String json) {
        return electricalFireEventService.findElectricalFireEventDetail(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-23 14:11
     *@Param: java.lang.String, javax.servlet.http.HttpServletResponse
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 导出电气火灾设备事件列表
     */
    @RequestMapping(value = "downLoadElectricalFireDeviceEventList/{projectId}")
    public void downLoadElectricalFireDeviceEventList(String json, HttpServletResponse resp) {
        electricalFireEventService.downLoadElectricalFireDeviceEventList(json,resp);
    }
}
