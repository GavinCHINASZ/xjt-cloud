package com.xjt.cloud.iot.api.water;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.water.WaterEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WaterEventHandleController
 * @Description 水压水浸消火栓事件处理信息
 * @Author wangzhiwen
 * @Date 2020/12/7 10:30
 **/
@RestController
@RequestMapping("/water/event/handle/")
public class WaterEventHandleController extends AbstractController {
    @Autowired
    private WaterEventHandleService waterEventHandleService;

    /**
     * @Description 保存事件处理信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveWaterEventHandle/{projectId}")
    public Data saveWaterEventHandle(String json){
        return waterEventHandleService.saveWaterEventHandle(json);
    }

    /**
     * @Description 查询事件处理信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findWaterEventHandleList/{projectId}")
    public Data findWaterEventHandleList(String json){
        return waterEventHandleService.findWaterEventHandleList(json);
    }

    /**
     * @Description 查询水设备事件导常分类统计柱状图
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findWaterEventFaultTypeColumnChart/{projectId}")
    public Data findWaterEventFaultTypeColumnChart(String json){
        return waterEventHandleService.findWaterEventFaultTypeColumnChart(json);
    }
}
