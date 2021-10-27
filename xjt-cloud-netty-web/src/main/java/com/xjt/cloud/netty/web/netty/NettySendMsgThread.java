package com.xjt.cloud.netty.web.netty;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.SysLog;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/30 09:06
 * @Description:
 */
public class NettySendMsgThread extends Thread{
    private JSONObject jsonObject;

    public NettySendMsgThread(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void run(){
        try {
            ReceiveHandle.nettySendMsg(jsonObject);
            //ReceiveHandle.nettySendMsg("WATERGAGE", "200");
        } catch (Exception e) {
            SysLog.logger.error("服务器推送信息给客户端错误" + jsonObject.toJSONString());
            SysLog.error(e);
        }
    }
}
