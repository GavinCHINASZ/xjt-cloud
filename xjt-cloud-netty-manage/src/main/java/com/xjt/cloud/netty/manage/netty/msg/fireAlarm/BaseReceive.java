package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import com.xjt.cloud.netty.manage.netty.NettyChannelMap;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wangzhiwen
 * @date 2019/5/16 0016 17:06
 */
public class BaseReceive extends AbstractService {
    /**
     * 功能描述: 发送保存信息请求
     *
     * @param socketChannel 连接对象
     * @param msg           信息对象
     * @param regId         注册码
     * @param t             参数对象
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/5/21 0021 13:56
     */
    protected static <T> String sendHttpGet(SocketChannel socketChannel, String msg, String regId, T t) {
        String msgData = JSONObject.toJSONString(t);
        boolean flag;
        if (regId.startsWith("$$_VESDA_") || regId.startsWith("%24%24_VESDA_") || StringUtils.startsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG)
                || msg.startsWith("$$") || (msg.startsWith("{") && msg.endsWith("}"))) {
            flag = true;
            SysLog.info("regId:" + regId + "flag:" + flag);
        } else {
            flag = NettyChannelMap.isClientMsgSave(socketChannel, msgData.replaceAll(msg, ""));
        }

        // 判断信息是否要保存
        if (flag) {
            //SysLog.error("信息要保存：" + regId + ":"  + msg);
            SysLog.info("保存信息请求参数：" + regId + ":" + msgData);
            JSONObject jsonObject;
/*
            if(msg.startsWith(ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG) || (msg.startsWith("{")&&msg.endsWith("}"))){
*/
            Object object = HttpUtils.httpGets(ConstantsClient.SAVE_MSG_URL, t).get(0);
            String s = JSONObject.toJSONString(object);
            jsonObject = JSONObject.parseObject(s);
            SysLog.info("保存信息返回结果：" + regId + ":" + jsonObject.toString());
           /* }else{
                jsonObject =  HttpUtils.httpGet(ConstantsClient.SAVE_MSG_URL,t);
                SysLog.debug("保存信息返回结果：" + regId + ":"  + jsonObject.toString());

            }
*/
            if (jsonObject.get("writeBackMsg") != null) {
                return jsonObject.get("writeBackMsg").toString();
            } else if (null != jsonObject) {
                //&& Constants.SUCCESS_CODE == jsonObject.getInteger("code")
                return Constants.SUCCESS_CODE + "";
            } else {
                return Constants.FAIL_CODE + "";
            }
        }
        return Constants.DISCARD_CODE + "";
    }


    /**
     * 功能描述: 发送保存信息请求
     *
     * @param socketChannel 连接对象
     * @param msg           信息对象
     * @param regId         注册码
     * @param t             参数对象
     * @return: java.lang.String
     * @auther: dwt
     * @date: 2019/5/21 0021 13:56
     */
    protected static <T> String sendHttpPost(SocketChannel socketChannel, String msg, String regId, T t) {
        String msgData = JSONObject.toJSONString(t);
        SysLog.info("保存信息请求参数：" + regId + ":" + msgData);
        JSONObject jsonObject = HttpUtils.httpPost(ConstantsClient.SAVE_MSGLIST_URL, msgData);
        SysLog.info("保存信息返回结果：" + regId + ":" + jsonObject.toString());
        if (null != jsonObject && Constants.SUCCESS_CODE == jsonObject.getInteger("code")) {
            return Constants.SUCCESS_CODE + "";
        } else {
            return Constants.FAIL_CODE + "";
        }
    }

}
