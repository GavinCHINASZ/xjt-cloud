package com.xjt.cloud.netty.web.netty;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.commons.utils.ThreadPoolUtils;
import com.xjt.cloud.netty.web.common.ConstantsNettyWeb;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 11:00
 * @Description:
 */
public class ReceiveHandle {

    /**
     *
     * 功能描述:netty接收到的客户端上传信息处理
     *
     * @param obj 上传信息obj
     * @return: java.lang.String 返回给客户端的信息，如果为空，表示不用返回信息
     * @auther: wangzhiwen
     * @date: 2019/4/25 10:04
     */
    public static String receiveHandle(ChannelHandlerContext socketChannel, Object obj){
        ByteBuf in = (ByteBuf)obj;
        String msg;
        if(in.hasArray()) { // 处理堆缓冲区
            msg = new String(in.array(), in.arrayOffset() + in.readerIndex(), in.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[in.readableBytes()];
            in.getBytes(in.readerIndex(), bytes);
            msg = new String(bytes);
        }
        msg =  msg.replace("\r","").replace("\n","").trim();
        SysLog.info("接收到的数据:" + msg);
        if (msg.toUpperCase().startsWith(ConstantsNettyWeb.BEGIN_REG_ID.toUpperCase())) {//判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
            //把连接信息与客户端信息保存在map中
            NettyChannelMap.addClient(msg , socketChannel);
        }

        return "OK";
    }

    /**
     *
     * 功能描述:主动发送信息到客户端
     *
     * @param jsonObject
     * @return: boolean
     * @auther: dwt
     * @date: 2019/4/25 10:06
     */
    public static boolean nettySendMsg(JSONObject jsonObject){
        String channelId = jsonObject.getString("channelId");
        String msg = jsonObject.getString("msg");
        if(StringUtils.isNotEmpty(channelId)){
            SysLog.info("开始给web发送信息,channelId = " + channelId + " msg = " + msg);
            if (StringUtils.isNotEmpty(channelId)){
                return sendMsg(NettyChannelMap.getChannelByChannelId(channelId),msg);
            }
        }else {
            String clientType = jsonObject.getString("iotType");

            if (StringUtils.isEmpty(msg) || StringUtils.isEmpty(msg)) {
                return false;
            }
            SysLog.info("开始给web发送信息,iotType = " + clientType + " msg = " + msg);
            //给大屏发送信息
            sendMsgAll(NettyChannelMap.getRegIdListByIotType(ConstantsNettyWeb.BIG_SCREEN_SOCKET), msg + "_" + clientType);
            //给单个页面发送信息
            return sendMsgAll(NettyChannelMap.getRegIdListByIotType(clientType), msg);
        }
        return false;
    }

    /**
     * @Description netty发送信息
     *
     * @param regIdList
     * @param msg
     * @author wangzhiwen
     * @date 2020/8/19 11:09
     * @return boolean
     */
    private static boolean sendMsgAll(List<String> regIdList, String msg){
        if (CollectionUtils.isNotEmpty(regIdList)) {
            for (String regId : regIdList) {//循环往该类型的客户端发送信息
                try {
                SysLog.info("regId=" + regId);
                    //ThreadPoolUtils.getInstance("webSocketLock").execute(new SendWebSocketThread(regId,msg));
                    sendMsg((ChannelHandlerContext) NettyChannelMap.getChannelByRegId(regId), msg);
                }catch (Exception ex){
                    SysLog.error(ex);
                }
            }

        }
        return true;
    }

    /**
     * @Description 发送信息
     *
     * @param ctx
     * @param msg
     * @author wangzhiwen
     * @date 2020/8/24 10:26
     * @return boolean
     */
    private static boolean sendMsg(ChannelHandlerContext ctx, String msg){
        if (ctx != null) {
            try {
                //ByteBuf sendMsgBf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));//进行处理
                ctx.channel().writeAndFlush(new TextWebSocketFrame(msg));
                ctx.flush();
                //ctx.writeAndFlush(sendMsgBf);
                return true;
            } catch (Exception ex) {
                SysLog.error(ex);
            }
        }
        return false;
    }

}
