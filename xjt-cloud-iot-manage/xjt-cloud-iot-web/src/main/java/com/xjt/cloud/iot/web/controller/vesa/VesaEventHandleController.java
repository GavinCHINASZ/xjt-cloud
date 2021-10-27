package com.xjt.cloud.iot.web.controller.vesa;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.vesa.VesaEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 极早期 事件处理信息
 * 
 * @author huanggc
 * @date 2020/12/7 10:30
 **/
@RestController
@RequestMapping("/vesa/event/handle/")
public class VesaEventHandleController extends AbstractController {
    @Autowired
    private VesaEventHandleService vesaEventHandleService;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveVesaEventHandle/{projectId}")
    public Data saveVesaEventHandle(String json){
        return vesaEventHandleService.saveVesaEventHandle(json);
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findVesaEventHandleList/{projectId}")
    public Data findVesaEventHandleList(String json){
        return vesaEventHandleService.findVesaEventHandleList(json);
    }

    /**
     * 查询 极早期设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findVesaEventFaultTypeColumnChart/{projectId}")
    public Data findVesaEventFaultTypeColumnChart(String json){
        return vesaEventHandleService.findVesaEventFaultTypeColumnChart(json);
    }
}
