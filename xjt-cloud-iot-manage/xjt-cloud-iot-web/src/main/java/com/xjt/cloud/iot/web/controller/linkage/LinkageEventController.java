package com.xjt.cloud.iot.web.controller.linkage;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 联动事件 Controller
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@RestController
@RequestMapping("/linkage/event/")
public class LinkageEventController extends AbstractController {
    @Autowired
    private LinkageEventService linkageEventService;

    /**
     * 功能描述:查询 联动事件 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageEventList/{projectId}")
    public Data findLinkageEventList(String json) {
        return linkageEventService.findLinkageEventList(json);
    }

    /**
     * 功能描述:导出 联动事件 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/19
     * @return void
     */
    @RequestMapping(value = "downLinkageEventList/{projectId}")
    public void downLinkageEventList(String json, HttpServletResponse response) {
        linkageEventService.downLinkageEventList(json, response);
    }

    /**
     * 功能描述:查询 联动事件 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageEventSummaryReport/{projectId}")
    public Data findLinkageEventSummaryReport(String json) {
        return linkageEventService.findLinkageEventSummaryReport(json);
    }

    /**
     * 功能描述:查询 联动事件 拆线图
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageEventReportCount/{projectId}")
    public Data findLinkageEventReportCount(String json) {
        return linkageEventService.findLinkageEventReportCount(json);
    }

}
