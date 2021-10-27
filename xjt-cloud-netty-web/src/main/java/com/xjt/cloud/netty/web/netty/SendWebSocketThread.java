package com.xjt.cloud.netty.web.netty;

import com.xjt.cloud.commons.utils.SysLog;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ClassName SendWebSocketThread
 * @Description
 * @Author wangzhiwen
 * @Date 2021/5/15 15:06
 **/
public class SendWebSocketThread  extends Thread {
    private String regId;
    private String msg;

    public SendWebSocketThread(String regId, String msg) {
        this.regId = regId;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            sendMsg(NettyChannelMap.getChannelByRegId(regId), msg);
        } catch (Exception e) {
            SysLog.logger.error("服务器推送信息给客户端错误regId=" + regId  + " msg=" + msg);
            SysLog.error(e);
        }
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