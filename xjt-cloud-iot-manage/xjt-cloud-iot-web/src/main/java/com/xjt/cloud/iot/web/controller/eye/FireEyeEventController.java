package com.xjt.cloud.iot.web.controller.eye;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * web火眼事件
 *
 * @author zhangZaifa
 * @date 2020-04-06 10:31
 */
@RestController
@RequestMapping("/fireEyeEvent/")
public class FireEyeEventController extends AbstractController {
    @Autowired
    private FireEyeEventService fireEyeEventService;

    /**
     * 查询火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "findFireEyeEventList/{projectId}")
    public Data findFireEyeEventList(String json) {
        return fireEyeEventService.findFireEyeEventList(json);
    }

    /**
     * 查询 火眼事件汇总
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/21
     **/
    @RequestMapping(value = "findFireEyeEventSummary/{projectId}")
    public Data findFireEyeEventSummary(String json) {
        return fireEyeEventService.findFireEyeEventSummary(json);
    }

    /**
     * 查询 火眼事件 折线图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 202103-23
     **/
    @RequestMapping(value = "findFireEyeEventReportCount/{projectId}")
    public Data findFireEyeEventReportCount(String json) {
        return fireEyeEventService.findFireEyeEventReportCount(json);
    }

    /**
     * 火眼事件列表导出表格
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2021-03-25
     **/
    @RequestMapping(value = "downFireEyeEventList/{projectId}")
    public void downFireEyeEventList(String json, HttpServletResponse resp) {
        fireEyeEventService.downFireEyeEventList(json, resp);
    }

    /**
     * 查询 火眼事件 大屏
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/04/28
     **/
    @RequestMapping(value = "findFireEyeEventScreen")
    public Data findFireEyeEventScreen(String json) {
        return fireEyeEventService.findFireEyeEventScreen(json);
    }
}
