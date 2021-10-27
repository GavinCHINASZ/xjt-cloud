package com.xjt.cloud.iot.web.controller.eye;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 火眼 事件处理信息
 * 
 * @author huanggc
 * @date 2020/12/7 10:30
 **/
@RestController
@RequestMapping("/fire/eye/handle/")
public class FireEyeEventHandleController extends AbstractController {
    @Autowired
    private FireEyeEventHandleService fireEyeEventHandleService;

    /**
     * 保存 火眼事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveFireEyeEventHandle/{projectId}")
    public Data saveFireEyeEventHandle(String json){
        return fireEyeEventHandleService.saveFireEyeEventHandle(json);
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFireEyeEventHandleList/{projectId}")
    public Data findFireEyeEventHandleList(String json){
        return fireEyeEventHandleService.findFireEyeEventHandleList(json);
    }

    /**
     * 查询 火眼设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFireEyeEventFaultTypeColumnChart/{projectId}")
    public Data findFireEyeEventFaultTypeColumnChart(String json){
        return fireEyeEventHandleService.findFireEyeEventFaultTypeColumnChart(json);
    }

}
