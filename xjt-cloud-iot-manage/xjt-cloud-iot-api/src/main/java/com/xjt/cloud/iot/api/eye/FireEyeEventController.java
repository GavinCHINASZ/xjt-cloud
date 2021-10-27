package com.xjt.cloud.iot.api.eye;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app火眼事件
 *
 * @author huanggc
 * @date 2020/01/20
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

}
