package com.xjt.cloud.netty.web.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:客户端连接信息与注册id关系类
 */
public class ClientMsg {

    public ClientMsg(){}

    public ClientMsg(String regId, ChannelHandlerContext socketChannel){
        this.regId = regId;
        this.socketChannel = socketChannel;
    }

    private String regId;

    private String msg;

    private long time;

    private ChannelHandlerContext socketChannel;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public ChannelHandlerContext getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(ChannelHandlerContext socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
