package com.xjt.cloud.iot.core.common;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.thread.ThreadPoolHttpGet;

/**
 * @ClassName NettySocketSendmsgUtils
 * @Description
 * @Author wangzhiwen
 * @Date 2020/8/31 10:42
 **/
public class NettySocketSendMsgUtils {
    private static volatile RedisUtils redisUtils = CacheUtils.initRedisUtils();
    /**
     *
     * 功能描述: 服务器主动给netty端发送信息
     *
     * @param jsonObject
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/9/27 11:37
     */
    public static void nettySendMsg(JSONObject jsonObject){
        SysLog.info("要发送netty的信息jsonObject=" + jsonObject.toJSONString());
        String regId = jsonObject.getString("regId");
        String regIdUrl = redisUtils.getString(StringUtils.urlEncode(jsonObject.getString("regId")));
        if (StringUtils.isNotEmpty(regIdUrl)){
            JSONObject sendJson = new JSONObject();
            String[] regIdUrls = regIdUrl.split("_");
            sendJson.put("regId",regId);
            sendJson.put("msg",jsonObject.getString("msg"));
            sendJson.put("url", regIdUrls[1]);
            SysLog.info("要发送netty的信息" + sendJson.toJSONString());
            ThreadPoolHttpGet.httpGet(sendJson);
        }
    }
}
