package com.xjt.cloud.task.core.common;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.commons.utils.thread.ThreadPoolHttpGet;

/**
 * @author wangzhiwen
 * @date 2020/8/29 14:03
 **/
public class WebSocketSendMsgUtils {
    /**
     * 功能描述: 服务器主动给web端发送信息
     *
     * @param jsonObject 参数
     * @author wangzhiwen
     * @date 2019/9/27 11:37
     */
    public static void nettySendMsg(JSONObject jsonObject) {
        String[] urls = ConstantsDevice.WEB_SOCKET_SEND_MSG_URLS.split(",");
        for (String url : urls) {
            jsonObject.put("url", url);
            SysLog.info("发送给web端的json=" + jsonObject.toJSONString());
            ThreadPoolHttpGet.httpGet(jsonObject);
        }
    }
}
