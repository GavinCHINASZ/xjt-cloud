package com.xjt.cloud.iot.web.controller.linkage;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageEventHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 声光 事件处理信息
 * 
 * @author huanggc
 * @date 2020/12/7 10:30
 **/
@RestController
@RequestMapping("/linkage/event/handle/")
public class LinkageEventHandleController extends AbstractController {
    @Autowired
    private LinkageEventHandleService linkageEventHandleService;

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveLinkageEventHandle/{projectId}")
    public Data saveLinkageEventHandle(String json){
        return linkageEventHandleService.saveLinkageEventHandle(json);
    }

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageEventHandleList/{projectId}")
    public Data findLinkageEventHandleList(String json){
        return linkageEventHandleService.findLinkageEventHandleList(json);
    }

    /**
     * 查询 声光设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageEventFaultTypeColumnChart/{projectId}")
    public Data findLinkageEventFaultTypeColumnChart(String json){
        return linkageEventHandleService.findLinkageEventFaultTypeColumnChart(json);
    }

}
