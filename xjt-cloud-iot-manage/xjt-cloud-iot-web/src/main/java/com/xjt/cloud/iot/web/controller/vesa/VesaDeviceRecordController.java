package com.xjt.cloud.iot.web.controller.vesa;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:34
 * @Description: 极早期设备记录管理类
 */
@RestController
@RequestMapping("/vesa/record/")
public class VesaDeviceRecordController extends AbstractController {

    @Autowired
    private VesaDeviceRecordService vesaDeviceRecordService;

    /**
     * 功能描述:　查询极早期设备事件记录列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaDeviceEventList/{projectId}")
    public Data findVesaDeviceEventList(String json) {
        return vesaDeviceRecordService.findVesaDeviceEventList(json);
    }

    /**
     * 功能描述:查询极早期设备事件汇总饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findVesaDeviceEventSummaryReport/{projectId}")
    public Data findVesaDeviceEventSummaryReport(String json) {
        return vesaDeviceRecordService.findVesaDeviceEventSummaryReport(json);
    }

    /**
     * 功能描述: 项目主页 极早期
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/20
     */
    @RequestMapping(value = "findVesaDeviceProjectHomeData/{projectId}")
    public Data findVesaDeviceProjectHomeData(String json) {
        return vesaDeviceRecordService.findVesaDeviceProjectHomeData(json);
    }

    /**
     *
     * 功能描述:　查询极早期设备记录列表　曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
/*
    @RequestMapping(value = "findVesaDeviceRecordList/{projectId}")
    public Data findVesaDeviceRecordList(String json){
        return vesaDeviceRecordService.findVesaDeviceRecordList(json);
    }
*/

    /**
     *
     * 功能描述:　查询极早期设备记录曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    /*@RequestMapping(value = "findVesaDeviceRecordCurveChart/{projectId}")
    public Data findVesaDeviceRecordCurveChart(String json){
        return vesaDeviceRecordService.findVesaDeviceRecordCurveChart(json);
    }*/

    /**
     * 功能描述:　查询极早期设备事件记录按时间汇总（曲线图）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaDeviceEventReportCount/{projectId}")
    public Data findVesaDeviceEventReportCount(String json) {
        return vesaDeviceRecordService.findVesaDeviceEventReportCount(json);
    }

    /**
     * 功能描述:　查询极早期设备事件记录汇总，饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaDeviceEventReportTotal/{projectId}")
    public Data findVesaDeviceEventReportTotal(String json) {
        return vesaDeviceRecordService.findVesaDeviceEventReportTotal(json);
    }

    /**
     * 功能描述:　查询极早期设备故障汇总（按故障类型）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaFaultNameCount/{projectId}")
    public Data findVesaFaultNameCount(String json) throws ParseException {
        return vesaDeviceRecordService.findVesaFaultNameCount(json);
    }


    /**
     *
     * 功能描述:极早期设备事件下载
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
/*
    @RequestMapping(value = "downloadVesaDeviceDeviceEvent/{projectId}")
    public void downloadVesaDeviceDeviceEvent(HttpServletResponse response, String json){
        vesaDeviceRecordService.downloadVesaDeviceDeviceEvent(response, json);
    }
*/

    /**
     * 功能描述:极早期设备事件下载(所有设备)
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    @RequestMapping(value = "downloadVesaDeviceEvent/{projectId}")
    public void downloadVesaDeviceEvent(HttpServletResponse response, String json) {
        vesaDeviceRecordService.downloadVesaDeviceEvent(response, json);
    }

    public VesaDeviceRecordService getVesaDeviceRecordService() {
        return vesaDeviceRecordService;
    }

    public void setVesaDeviceRecordService(VesaDeviceRecordService vesaDeviceRecordService) {
        this.vesaDeviceRecordService = vesaDeviceRecordService;
    }
}
