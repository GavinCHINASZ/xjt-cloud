package com.xjt.cloud.iot.api.vesa;

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
     *
     * 功能描述:　查询极早期设备事件记录列表　
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaDeviceEventListApp/{projectId}")
    public Data findVesaDeviceEventListApp(String json)  throws ParseException {
        return vesaDeviceRecordService.findVesaDeviceEventListApp(json);
    }

    /**
     *
     * 功能描述:查询极早期设备事件汇总饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findVesaEventSummaryApp/{projectId}")
    public Data findVesaEventSummaryApp(String json){
        return vesaDeviceRecordService.findVesaEventSummaryReportApp(json);
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
     *
     * 功能描述:　查询极早期设备事件记录按时间汇总（曲线图）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaEventReportCountApp/{projectId}")
    public Data findVesaEventReportCountApp(String json) throws ParseException {
        return vesaDeviceRecordService.findVesaEventReportCountApp(json);
    }

    /**
     *
     * 功能描述:　查询极早期设备故障汇总（按故障类型）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    @RequestMapping(value = "findVesaFaultNameCountApp/{projectId}")
    public Data findVesaFaultNameCountApp(String json) throws ParseException {
        return vesaDeviceRecordService.findVesaFaultNameCountApp(json);
    }

    /**
     *
     * 功能描述:　查询极早期设备事件记录汇总，饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
/*    @RequestMapping(value = "findVesaDeviceEventReportTotalApp/{projectId}")
    public Data findVesaDeviceEventReportTotal(String json){
        return vesaDeviceRecordService.findVesaDeviceEventReportTotal(json);
    }*/

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
     *
     * 功能描述:极早期设备事件下载(所有设备)
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
/*    @RequestMapping(value = "downloadVesaDeviceEvent/{projectId}")
    public void downloadVesaDeviceEvent(HttpServletResponse response, String json){
        vesaDeviceRecordService.downloadVesaDeviceEvent(response, json);
    }

    public VesaDeviceRecordService getVesaDeviceRecordService() {
        return vesaDeviceRecordService;
    }

    public void setVesaDeviceRecordService(VesaDeviceRecordService vesaDeviceRecordService) {
        this.vesaDeviceRecordService = vesaDeviceRecordService;
    }*/
}
