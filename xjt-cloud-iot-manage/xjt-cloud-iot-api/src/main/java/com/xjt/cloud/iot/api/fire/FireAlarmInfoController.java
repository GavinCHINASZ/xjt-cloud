package com.xjt.cloud.iot.api.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmDeviceService;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * FireAlarmInfoController
 * app火警主机信息汇总
 *
 * @author dwt
 * @date 2020-03-06 10:31
 */
@RestController
@RequestMapping("/fireAlarm/info/")
public class FireAlarmInfoController extends AbstractController {

    @Autowired
    private FireAlarmEventService fireAlarmEventService;
    @Autowired
    private FireAlarmDeviceService fireAlarmDeviceService;

    /**
     * app端火警主机（今日，本月，今年）事件数据统计
     *
     * @param json java.lang.String
     * @return Data
     * @author dwt
     * @date 2020-03-06 10:35
     */
    @RequestMapping(value = "findFireAlarmEventCountApp/{projectId}")
    public Data findFireAlarmEventCountApp(String json) {
        return fireAlarmEventService.findFireAlarmEventCountApp(json);
    }

    /**
     * app端火警主机设备信息查询
     *
     * @param json java.lang.String
     * @return Data
     * @author dwt
     * @date 2020-03-06 10:37
     */
    @RequestMapping(value = "findFireAlarmDeviceInfoApp/{projectId}")
    public Data findFireAlarmDeviceInfoApp(String json) {
        return fireAlarmDeviceService.findFireAlarmDeviceInfoApp(json);
    }

    /**
     * app端火警主机柱状图以及饼图和列表数据查询封装
     *
     * @param json java.lang.String
     * @return Data
     * @author dwt
     * @date 2020-03-06 10:41
     */
    @RequestMapping(value = "findFireEventPieAndListApp/{projectId}")
    public Data findFireEventPieAndListApp(String json) throws ParseException {
        return fireAlarmEventService.findFireEventPieAndListApp(json);
    }

    /**
     * 地铁火警事件处理接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-12 14:05
     */
    @RequestMapping(value = "updateEventCauseById/{projectId}")
    public Data updateEventCauseById(String json) {
        return fireAlarmEventService.updateEventCauseById(json);
    }

    /**
     * 地铁事件处理图片路径查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-12 14:29
     */
    @RequestMapping(value = "findPictureUrlByEventId/{projectId}")
    public Data findPictureUrlByEventId(String json) {
        return fireAlarmEventService.findPictureUrlByEventId(json);
    }

    /**
     * APP端改版火警主机实时监测数据查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-30 10:23
     */
    @RequestMapping(value = "findFireAlarmRealTimeMonitorApp/{projectId}")
    public Data findFireAlarmRealTimeMonitorApp(String json) {
        return fireAlarmDeviceService.findFireAlarmDeviceEventData(json, 2);
    }

    /**
     * 改版火警主机处事件理接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-07 15:23
     */
    @RequestMapping(value = "updateFireEventHandleStatus/{projectId}")
    public Data updateFireEventHandleStatus(String json) {
        return fireAlarmEventService.updateFireEventHandleStatus(json);
    }

    /**
     * 根据id查询事件信息
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-07 16:17
     */
    @RequestMapping(value = "findFireAlarmEventById/{projectId}")
    public Data findFireAlarmEventById(String json) {
        return fireAlarmEventService.findFireAlarmEventById(json);
    }

    /**
     * 火警主机改版事件列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-09 14:26
     */
    @RequestMapping(value = "findFireAlarmListApp/{projectId}")
    public Data findFireAlarmListApp(String json) {
        return fireAlarmEventService.findFireAlarmListApp(json);
    }

    /**
     * 地铁班前防护故障事件查询
     *
     * @param json java.lang.String
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 10:15
     */
    @RequestMapping(value = "findMetroProtectionFaultEventList/{projectId}")
    public Data findMetroProtectionFaultEventList(String json) {
        return fireAlarmEventService.findMetroProtectionFaultEventList(json);
    }

    /**
     * 地铁班前防护--综合监测
     *
     * @param json java.lang.String
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-30
     */
    @RequestMapping(value = "heLiShiIntegratedMonitoring/{projectId}")
    public Data heLiShiIntegratedMonitoring(String json) {
        return fireAlarmEventService.heLiShiIntegratedMonitoring(json);
    }

    /**
     * 查询app首页火警主机信息
     *
     * @param json 参数
     * @return com.alibaba.fastjson.JSONObject
     * @author wangzhiwen
     * @date 2021/3/25 15:04
     */
    @RequestMapping(value = "findUserProjectFireAlarmData/{projectId}")
    public JSONObject findUserProjectFireAlarmData(String json) {
        return fireAlarmDeviceService.findUserProjectFireAlarmData(json);
    }
}
