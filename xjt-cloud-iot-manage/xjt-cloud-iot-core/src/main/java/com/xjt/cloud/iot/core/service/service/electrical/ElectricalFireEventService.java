package com.xjt.cloud.iot.core.service.service.electrical;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 *@ClassName ElectricalFireEventService
 *@Author dwt
 *@Date 2020-09-23 14:20
 *@Version 1.0
 */
public interface ElectricalFireEventService {

    /**
     *@Author: dwt
     *@Date: 2020-09-15 14:02
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾事件列表
     */
   Data findElectricalFireEventList(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-24 9:14
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 电气火灾事件处理
     */
   Data modifyElectricalFireEvent(String json);
   /**
    *@Author: dwt
    *@Date: 2020-09-24 9:43
    *@Param: java.lang.String
    *@Return: com.xjt.cloud.commons.utils.Data
    *@Description 查询电气火灾事件汇总分析
    */
   Data findElectricalFireEventSummaryAnalysis(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-24 14:06
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询电气火灾事件详情
     */
   Data findElectricalFireEventDetail(String json);

    /**
     *@Author: dwt
     *@Date: 2020-09-23 14:11
     *@Param: java.lang.String, javax.servlet.http.HttpServletResponse
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 导出电气火灾设备事件列表
     */
     void downLoadElectricalFireDeviceEventList(String json, HttpServletResponse resp);

}
