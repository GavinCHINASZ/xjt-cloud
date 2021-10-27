package com.xjt.cloud.iot.core.service.service.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:35
 * @Description: 水设备管理接口
 */
public interface VesaDeviceRecordService {

    /**
     *
     * 功能描述:　查询水设备事件记录列表　
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findVesaDeviceEventList(String json);

    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    Data findVesaDeviceEventSummaryReport(String json);

    /**
     * @Description 查询app首页极早期信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectVesaData(String json);

    /**
     *
     * 功能描述:　查询水设备记录列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
/*    Data findVesaDeviceRecordList(String json);*/

    /**
     *
     * 功能描述:　查询水设备记录曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
/*    Data findVesaDeviceRecordCurveChart(String json);*/



    /**
     *
     * 功能描述:　查询水设备事件记录按时间汇总（曲线图）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findVesaDeviceEventReportCount(String json);

    /**
     *
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findVesaDeviceEventReportTotal(String json);

    /**
     *
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    //int modifyVesaRecordEvent(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:水压设备设备事件下载
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
/*    void downloadVesaDeviceDeviceEvent(HttpServletResponse response, String json);*/

    /**
     *
     * 功能描述:极早期设备事件下载(所有设备)
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    void downloadVesaDeviceEvent(HttpServletResponse response, String json);

    Data findVesaEventSummaryReportApp(String json);

    Data findVesaEventReportCountApp(String json) throws ParseException;

    /**
     *
     * 功能描述:　查询水设备事件记录列表　
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findVesaDeviceEventListApp(String json) throws ParseException;

    Data findVesaFaultNameCountApp(String json) throws ParseException;

    Data findVesaFaultNameCount(String json) throws ParseException;

    /**
     * 功能描述: 项目主页 极早期
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/20
     */
    Data findVesaDeviceProjectHomeData(String json);
}
