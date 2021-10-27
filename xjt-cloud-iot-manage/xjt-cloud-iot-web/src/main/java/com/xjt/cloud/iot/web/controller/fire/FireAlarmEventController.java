package com.xjt.cloud.iot.web.controller.fire;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventService;
import com.xjt.cloud.iot.core.service.service.inter.InterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 火警主机事件Controller
 *
 * @Author dwt
 * @Date 2019-10-11 15:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/fireAlarm/event/")
public class FireAlarmEventController extends AbstractController {

    @Autowired
    private FireAlarmEventService fireAlarmEventService;
    @Autowired
    private InterService interService;

    /**
     * @Author: dwt
     * @Date: 2019-10-28 9:54
     * @Param: json
     * @Return: Data
     * 火警主机事件统计汇总分析
     */
    @RequestMapping(value = "findFireAlarmEventCount/{projectId}")
    public Data findFireAlarmEventCount(String json) {
        return fireAlarmEventService.findFireAlarmEventCount(json);
    }

    /**
     * 火警主机事件列表筛选以及查询
     *
     * @Author: dwt
     * @Date: 2019-10-28 9:57
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireAlarmEventList/{projectId}")
    public Data findFireAlarmEventList(String json) {
        return fireAlarmEventService.findFireAlarmEventList(json);
    }

    /**
     * 火警主机事件导出
     *
     * @Author: dwt
     * @Date: 2019-10-28 9:59
     * @Param: javax.servlet.http.HttpServletResponse, java.lang.String
     * @Return: Data
     */
    @RequestMapping(value = "downLoadFireAlarmEvent/{projectId}")
    public void downLoadFireAlarmEvent(String json, HttpServletResponse resp) {
        fireAlarmEventService.downLoadFireAlarmEvent(json, resp);
    }

    /**
     * 大屏火警主机事件汇总分析
     *
     * @Author: dwt
     * @Date: 2020-04-08 13:36
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireEventGreatScreen")
    public Data findFireEventGreatScreen(String json) {
        return fireAlarmEventService.selectFireEventCount(json);
    }

    /**
     * 大屏火警主机事件列
     *
     * @Author: dwt
     * @Date: 2020-04-09 9:26
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireEventListGreatScreen")
    public Data findFireEventListGreatScreen(String json) {
        return fireAlarmEventService.findFireAlarmEventList(json);
    }

    /**
     * 地铁大屏火警主机事件统计
     *
     * @Author: dwt
     * @Date: 2020-05-13 10:48
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findSubwayEventCount")
    public Data findSubwayEventCount(String json) {
        return fireAlarmEventService.findSubwayEventCount(json);
    }

    /**
     * 地铁大屏火警主机事件列表查询
     *
     * @Author: dwt
     * @Date: 2020-05-22 16:07
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireEventSubwayBigScreen/{projectId}")
    public Data findFireEventSubwayBigScreen(String json) {
        return fireAlarmEventService.findFireEventSubwayBigScreen(json);
    }

    /**
     * 改版火警主机处理接口
     *
     * @Author: dwt
     * @Date: 2020-07-07 15:23
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "updateFireEventHandleStatus/{projectId}")
    public Data updateFireEventHandleStatus(String json) {
        return fireAlarmEventService.updateFireEventHandleStatus(json);
    }

    /**
     * 根据id查询事件信息
     *
     * @Author: dwt
     * @Date: 2020-07-07 16:17
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireAlarmEventById/{projectId}")
    public Data findFireAlarmEventById(String json) {
        return fireAlarmEventService.findFireAlarmEventById(json);
    }

    /**
     * 因特报表接口
     *
     * @Author: dwt
     * @Date: 2020-07-15 16:41
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findInterAreaTypeEventCount/{projectId}")
    public Data findInterAreaTypeEventCount(String json) {
        return interService.findInterAreaTypeEventCount(json);
    }

    /**
     * 查询火警主机和极早期当天五点前的所有活跃事件（未恢复事件）
     *
     * @Author: dwt
     * @Date: 2020-07-16 11:05
     * @Param: json
     * @Return: Data
     */
    @RequestMapping(value = "findFireAlarmAndVesaEvent/{projectId}")
    public Data findFireAlarmAndVesaEvent(String json) {
        return interService.findFireAlarmAndVesaEvent(json);
    }

}
