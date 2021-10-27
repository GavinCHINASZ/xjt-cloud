package com.xjt.cloud.iot.netty.controller.eye;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 保存 火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @RequestMapping(value = "saveFireEyeEvent")
    public Data saveFireEyeEvent(String json) {
        return fireEyeEventService.saveFireEyeEvent(json);
    }

}
