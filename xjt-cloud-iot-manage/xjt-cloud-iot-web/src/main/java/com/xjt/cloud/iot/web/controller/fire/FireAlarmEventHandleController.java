package com.xjt.cloud.iot.web.controller.fire;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 火警 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@RestController
@RequestMapping("/fire/alarm/handle/")
public class FireAlarmEventHandleController extends AbstractController {
    @Autowired
    private FireAlarmEventHandleService findFireAlarmEventHandleList;

    /**
     * 保存 火警事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFireAlarmEventHandle/{projectId}")
    public Data saveFireAlarmEventHandle(String json){
        return findFireAlarmEventHandleList.saveFireAlarmEventHandle(json);
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFireAlarmEventHandleList/{projectId}")
    public Data findFireAlarmEventHandleList(String json){
        return findFireAlarmEventHandleList.findFireAlarmEventHandleList(json);
    }

    /**
     * 查询 火警设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFireAlarmEventFaultTypeColumnChart/{projectId}")
    public Data findFireAlarmEventFaultTypeColumnChart(String json){
        return findFireAlarmEventHandleList.findFireAlarmEventFaultTypeColumnChart(json);
    }
}
