package com.xjt.cloud.iot.netty.controller.smoke;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeEventService;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
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
        Data data = smokeEventService.saveSmokeEvent(json);
        /*SysLog.info("烟感设备发送给web端的json=" + data.getMsg());
        WebSocketSendMsgUtils.nettySendMsg(JSONObject.parseObject(data.getMsg()));
        //ThreadPoolUtils.getInstance().execute(new NettySendMsgThread(JSONObject.parseObject(data.getMsg())));//以多线程方式给客户端发送信息
        data.setMsg(null);*/
        return data;
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
     * 功能描述：接收OneNET平台的推送消息。<p>
     *      *           <ul>注:
     *      *               <li>1.OneNet平台为了保证数据不丢失，有重发机制，如果重复数据对业务有影响，数据接收端需要对重复数据进行排除重复处理。</li>
     *      *               <li>2.OneNet每一次post数据请求后，等待客户端的响应都设有时限，在规定时限内没有收到响应会认为发送失败。
     *      *                    接收程序接收到数据时，尽量先缓存起来，再做业务逻辑处理。</li>
     *      *           </ul>
     *
     * @param request
     * @auther huanggc
     * @date 2020/07/16
     * @return 任意字符串。OneNet平台接收到http 200的响应，才会认为数据推送成功，否则会重发。
     */
    @RequestMapping(value = "receive", method = RequestMethod.POST)
    public String receive(HttpServletRequest request, String msg, String signature, String nonce){
        smokeEventService.receive(request, msg, nonce, signature);
        return "ok";
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
    @RequestMapping(value = "findSmokeEventSummaryReport")
    public Data findSmokeEventSummaryReport(String json){
        return smokeEventService.findSmokeEventSummaryReport(json);
    }
}
