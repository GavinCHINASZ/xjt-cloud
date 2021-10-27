package com.xjt.cloud.iot.api.smoke;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * SmokeEventController 烟感事件 控制类
 *
 * @author huanggc
 * @date 2020/07/15
 * @version  1.0
 */
@RestController
@RequestMapping("/smoke/event/")
public class SmokeEventController extends AbstractController{
    @Autowired
    private SmokeEventService smokeEventService;

    /**
     * 功能描述 查询烟感事件列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeEventList/{projectId}")
    public Data findSmokeEventList(String json){
        return smokeEventService.findSmokeEventList(json);
    }

    /**
     * 功能描述: 增加 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveSmokeEvent/{projectId}")
    public Data saveSmokeEvent(String json){
        return smokeEventService.saveSmokeEvent(json);
    }

    /**
     * 功能描述: 更新 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateSmokeEvent/{projectId}")
    public Data updateSmokeEvent(String json){
        return smokeEventService.updateSmokeEvent(json);
    }

    /**
     * 功能描述: 删除 烟感事件
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deletedSmokeEvent/{projectId}")
    public Data deletedSmokeEvent(String json){
        return smokeEventService.deletedSmokeEvent(json);
    }

    /**
     * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
     * @param msg 验证消息
     * @param nonce 随机串
     * @param signature 签名
     * @author huanggc
     * @return msg值
     */
    @RequestMapping(value = "receive", method = RequestMethod.GET)
    public String check(String msg, String nonce, String signature){
        return smokeEventService.check(msg, nonce, signature);
    }

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param encryptMsg 加密内容
     * @param msgType 消息内容
     * @author huanggc
     * @date 2020/07/21
     * @return 返回200后才可保存成功
     */
    @RequestMapping(value = "receiveCM", method = RequestMethod.GET)
    public String checkCM(String encryptMsg, String msgType){
        return smokeEventService.checkCM(encryptMsg, msgType);
    }

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param request
     * @author huanggc
     * @date 2020/07/21
     * @return
     */
    @RequestMapping(value = "receiveCM", method = RequestMethod.POST)
    public String checkCM(HttpServletRequest request){
        return smokeEventService.checkCM(request);
    }

    /**
     * 功能描述: 烟感告警事件 报表 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeEventSummaryReport/{projectId}")
    public Data findSmokeEventSummaryReport(String json){
        return smokeEventService.findSmokeEventSummaryReport(json);
    }
}
