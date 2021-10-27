package com.xjt.cloud.iot.api.smoke;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 烟感 事件处理信息
 * 
 * @author huanggc
 * @date 2020/12/7 10:30
 **/
@RestController
@RequestMapping("/smoke/handle/event/")
public class SmokeEventHandleController extends AbstractController {
    @Autowired
    private SmokeEventHandleService smokeEventHandleService;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveSmokeEventHandle/{projectId}")
    public Data saveSmokeEventHandle(String json){
        return smokeEventHandleService.saveSmokeEventHandle(json);
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeEventHandleList/{projectId}")
    public Data findSmokeEventHandleList(String json){
        return smokeEventHandleService.findSmokeEventHandleList(json);
    }

    /**
     * 查询 烟感设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeEventFaultTypeColumnChart/{projectId}")
    public Data findSmokeEventFaultTypeColumnChart(String json){
        return smokeEventHandleService.findSmokeEventFaultTypeColumnChart(json);
    }

}
