package com.xjt.cloud.commons.utils.thread;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.commons.utils.ThreadPoolUtils;

/**
 * @ClassName ThreadPoolHttpGet
 * @Description netty服务器端发送信息
 * @Author wangzhiwen
 * @Date 2020/8/24 14:29
 **/
public class ThreadPoolHttpGet {
    public static void httpGet(JSONObject jsonObject){
        SysLog.info("多线程请求的参数json=" + jsonObject.toJSONString());
        ThreadPoolUtils.getInstance().execute(new HttpGetThread(jsonObject));//以多线程方式给客户端发送信息
    }
}
