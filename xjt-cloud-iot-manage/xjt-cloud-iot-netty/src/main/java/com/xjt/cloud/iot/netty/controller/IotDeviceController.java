package com.xjt.cloud.iot.netty.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.iot.core.service.service.water.IotDeviceService;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 14:25
 * @Description: 物联网接口Controller
 */
@RestController
@RequestMapping("/iot/")
public class IotDeviceController extends AbstractController {

    @Autowired
    private IotDeviceService iotDeviceService;

    /**
     *
     * 功能描述:物联网设备信息保存
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    @RequestMapping(value = "dataAccess")
    public List<JSONObject> dataAccess(String data){
        List<JSONObject> jsonList = iotDeviceService.dataAccess(data);//第一个json为接口返回信息，第二个json为发送给客户端的信息
        SysLog.info(jsonList.size()+"------------》返回信息长度");
        if (jsonList.size() >= 3) {
            //nettySendMsg(jsonList.get(2));
            jsonList.remove(2);
        }
        return jsonList;
    }

    /**
     *
     * 功能描述:物联网设备信息批量保存
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    @RequestMapping(value = "dataListAccess")
    public List<JSONObject> dataListAccess(String data){
        List<JSONObject> jsonList = iotDeviceService.dataListAccess(data);//第一个json为接口返回信息，第二个json为发送给客户端的信息
        if (jsonList.size() >= 3) {
            //nettySendMsg(jsonList.get(2));
            jsonList.remove(2);
        }
        return jsonList;
    }

    /**
     *
     * 功能描述:判断注册码是否存在
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 16:49
     */
    @RequestMapping(value = "isSensorPresence")
    public JSONObject isSensorPresence(String mtType,String from){
        return iotDeviceService.isSensorPresence(mtType,from);
    }

    /**
     *
     * 功能描述: 服务器主动给web端发送信息
     *
     * @param json
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/9/27 11:37
     */
    @RequestMapping(value = "webSocketSendMsg")
    public void webSocketSendMsg(String json){
        if (StringUtils.isNotEmpty(json)){
            nettySendMsg(JSONObject.parseObject(json));
        }
    }

    /**
     *
     * 功能描述: 服务器主动给web端发送信息
     *
     * @param jsonObject
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/9/27 11:37
     */
    private void nettySendMsg(JSONObject jsonObject){
        SysLog.info("nettySendMsg的参数json=" + jsonObject.toJSONString());
        WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        //ThreadPoolUtils.getInstance().execute(new NettySendMsgThread(jsonObject));//以多线程方式给客户端发送信息
    }
}
