package com.xjt.cloud.iot.core.service.service.water;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:35
 * @Description: 水设备管理接口
 */
public interface WaterDeviceRecordService {
    /**
     *
     * 功能描述:　查询水设备事件记录列表　
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findWaterDeviceEventList(String json);

    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    Data findWaterDeviceEventSummaryReport(String json);

    /**
     *
     * 功能描述:　查询水设备记录列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findWaterDeviceRecordList(String json);

    /**
     *
     * 功能描述:　查询水设备记录曲线图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findWaterDeviceRecordCurveChart(String json);


    /**
     *
     * 功能描述:　查询水设备事件记录按时间汇总（曲线图）
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findWaterDeviceEventReportCount(String json);

    /**
     *
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Data findWaterDeviceEventReportTotal(String json);

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
    void downloadWaterDeviceDeviceEvent(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:水压设备事件下载(所有设备)
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    void downloadWaterDeviceEvent(HttpServletResponse response, String json);
    /**
     *@Author: dwt
     *@Date: 2020-08-10 10:43
     *@Param: String
     *@Return: Data
     *@Description 查询消火栓故障信息统计
     */
    Data findHydrantFaultMsg(String json);
}
