package com.xjt.cloud.iot.core.service.service.fire;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 *@ClassName FireAlarmEventService
 *@Author dwt
 *@Date 2019-10-11 15:30
 *@Description 火警主机事件Service
 *@Version 1.0
 */
public interface FireAlarmEventService {
    /**
     *@Author: dwt
     *@Date: 2019-10-22 18:15
     *@Param: json
     *@Return: Data
     *@Description 火警主机事件汇总分析
     */
    Data findFireAlarmEventCount(String json);
    /**
     *@Author: dwt
     *@Date: 2019-10-22 18:16
     *@Param: json
     *@Return: Data
     *@Description 火警主机事件列表查询以及筛选
     */
    Data findFireAlarmEventList(String json);
    /**
     *@Author: dwt
     *@Date: 2020-04-08 9:14
     *@Param: json
     *@Return: Data
     *@Description 火警主机事件汇总查询
     */
    Data selectFireEventCount(String json);
    /**
     *@Author: dwt
     *@Date: 2019-10-23 15:27
     *@Param: json,HttpServletResponse
     *@Return: Data
     *@Description 事件列表导出功能接口
     */
    void downLoadFireAlarmEvent(String json, HttpServletResponse resp);
    /**
     *@Author: daiwt
     *@Date: 2020-03-04 10:44
     *@Param: json
     *@Return: Data
     *@Description APP端事件汇总分析
     */
    Data findFireAlarmEventCountApp(String json);
    /**
     *@Author: dwt
     *@Date: 2020-03-05 9:52
     *@Param: java.lang.String
     *@Return: Data
     *@Description APP端图形和列表汇总查询
     */
    Data findFireEventPieAndListApp(String json) throws ParseException;
    /**
     *@Author: dwt
     *@Date: 2020-05-12 13:54
     *@Param: json
     *@Return: Data
     *@Description 地铁事件处理接口
     */
    Data updateEventCauseById(String json);
    /**
     *@Author: dwt
     *@Date: 2020-05-12 14:20
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据事件id查询事件处理图片路径
     */
    Data findPictureUrlByEventId(String json);
    /**
     *@Author: dwt
     *@Date: 2020-05-13 10:45
     *@Param: json
     *@Return: Data
     *@Description 地铁大屏火警主机事件统计
     */
    Data findSubwayEventCount(String json);
    /**
     *@Author: dwt
     *@Date: 2020-05-22 16:07
     *@Param: json
     *@Return: Data
     *@Description 地铁大屏火警主机事件列表查询
     */
    Data findFireEventSubwayBigScreen(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-05 11:51
     *@Param:
     *@Return: 
     *@Description TODO
     */
    Data findBrokenLineAndEventCount(String json);
    /**
     *@Author: dwt
     *@Date: 2020-06-30 10:31
     *@Param: json
     *@Return: Data
     *@Description 火警主机改版事件列表查询
     */
    Data findFireAlarmListApp(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-07 15:17
     *@Param: json
     *@Return: Data
     *@Description 改版火警主机处理接口
     */
    Data updateFireEventHandleStatus(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-07 16:05
     *@Param: json
     *@Return: Data
     *@Description 根据事件id查询事件信息
     */
    Data findFireAlarmEventById(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-29 10:10
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 地铁班前防护故障事件查询
     */
    Data findMetroProtectionFaultEventList(String json);

    /**
     * 地铁班前防护--综合监测
     *
     * @param json java.lang.String
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-30
     */
    Data heLiShiIntegratedMonitoring(String json);
}
