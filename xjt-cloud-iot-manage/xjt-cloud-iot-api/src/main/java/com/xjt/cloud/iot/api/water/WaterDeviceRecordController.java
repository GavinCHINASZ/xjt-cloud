package com.xjt.cloud.iot.api.water;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:34
 * @Description: 水设备记录管理类
 */
@RestController
@RequestMapping("/water/record/")
public class WaterDeviceRecordController extends AbstractController {

    @Autowired
    private WaterDeviceRecordService waterDeviceRecordService;

    /**
     *
     * 功能描述:　查询水设备事件记录列表　
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findWaterDeviceEventList/{projectId}")
    public Data findWaterDeviceEventList(String json){
        return waterDeviceRecordService.findWaterDeviceEventList(json);
    }

    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findWaterDeviceEventSummaryReport/{projectId}")
    public Data findWaterDeviceEventSummaryReport(String json){
        return waterDeviceRecordService.findWaterDeviceEventSummaryReport(json);
    }

    /**
     *
     * 功能描述:　查询水设备记录列表　曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findWaterDeviceRecordList/{projectId}")
    public Data findWaterDeviceRecordList(String json){
        return waterDeviceRecordService.findWaterDeviceRecordList(json);
    }

    /**
     *
     * 功能描述:　查询水设备记录曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findWaterDeviceRecordCurveChart/{projectId}")
    public Data findWaterDeviceRecordCurveChart(String json){
        return waterDeviceRecordService.findWaterDeviceRecordCurveChart(json);
    }

    /**
     *
     * 功能描述:　查询水设备事件记录按时间汇总（曲线图）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findWaterDeviceEventReportCount/{projectId}")
    public Data findWaterDeviceEventReportCount(String json){
        return waterDeviceRecordService.findWaterDeviceEventReportCount(json);
    }

    /**
     *
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findWaterDeviceEventReportTotal/{projectId}")
    public Data findWaterDeviceEventReportTotal(String json){
        return waterDeviceRecordService.findWaterDeviceEventReportTotal(json);
    }

    /**
     *
     * 功能描述:水压设备设备事件下载
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    @RequestMapping(value = "downloadWaterDeviceDeviceEvent/{projectId}")
    public void downloadWaterDeviceDeviceEvent(HttpServletResponse response, String json){
        waterDeviceRecordService.downloadWaterDeviceDeviceEvent(response, json);
    }

    /**
     *
     * 功能描述:水压设备事件下载(所有设备)
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    @RequestMapping(value = "downloadWaterDeviceEvent/{projectId}")
    public void downloadWaterDeviceEvent(HttpServletResponse response, String json){
        waterDeviceRecordService.downloadWaterDeviceEvent(response, json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-08-10 10:43
     *@Param: String
     *@Return: Data
     *@Description 查询消火栓故障信息统计
     */
    @RequestMapping(value = "findHydrantFaultMsg/{projectId}")
    public Data findHydrantFaultMsg(String json) {
        return waterDeviceRecordService.findHydrantFaultMsg(json);
    }
}
