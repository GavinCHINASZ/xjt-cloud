package com.xjt.cloud.commons.utils.thread;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.SysLog;

/**
 * @ClassName HttpGetThread
 * @Description
 * @Author wangzhiwen
 * @Date 2020/8/21 18:16
 **/
public class HttpGetThread extends Thread{
    private JSONObject jsonObject;

    public HttpGetThread(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void run(){
        try {
            String urls[] = jsonObject.getString("url").split(",");
            jsonObject.remove("url");
            for (String url:urls){
                SysLog.info("多线程发送信息url=" + url + "json=" + jsonObject);
                HttpUtils.httpPostByJson(url.trim(), jsonObject.toJSONString());
            }
        } catch (Exception e) {
            SysLog.logger.error("服务器推送信息给客户端错误" + jsonObject.toJSONString());
            SysLog.error(e);
        }
    }
}
