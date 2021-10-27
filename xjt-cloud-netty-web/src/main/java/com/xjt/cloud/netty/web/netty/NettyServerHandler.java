package com.xjt.cloud.netty.web.netty;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:51
 * @Description:ChannelInboundHandler提供了不同的事件处理方法你可以重写
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final ByteBuf firstMessage;

    public NettyServerHandler() {
        firstMessage = Unpooled.buffer(256);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
    }

    /**
     * 功能描述: 连接成功
     *
     * @param ctx
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 9:58
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SysLog.logger.error("已经连接到服务器");
        ctx.writeAndFlush(firstMessage);
    }

    /**
     * 功能描述:该方法用于接收从客户端接收的信息
     *
     * @param ctx 连接对象
     * @param msg 接收到的客户上传的信息
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 9:57
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        try {
            String res = ReceiveHandle.receiveHandle((ChannelHandlerContext) ctx.channel(), msg);//对上传信息处理，并返回传送给客户端的信息
            if (StringUtils.isNotEmpty(res)) {
                //ChannelHandlerContext提供各种不同的操作用于触发不同的I/O时间和操作
                //调用write方法来逐字返回接收到的信息
                //这里我们不需要在DISCARD例子当中那样调用释放，因为Netty会在写的时候自动释放
                //只调用write是不会释放的，它会缓存，直到调用flush
                byte[] req = res.getBytes("UTF-8");//返回信息进行处理
                ByteBuf resBf = Unpooled.buffer(req.length);
                resBf.writeBytes(req);
                ctx.write(res);//返回信息
            }
        } catch (Exception e) {
            SysLog.error(e);
        }finally {
            ReferenceCountUtil.release(msg);//清除
        }
    }
    /**
     * 功能描述: 捕捉到的异常信息
     *
     * @param ctx
     * @param cause
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 9:58
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        SysLog.error(cause.fillInStackTrace());
        //删除保存在map中的连接信息与客户端信息
        NettyChannelMap.remove((SocketChannel) ctx.channel());
        ctx.close();
    }

    /**
     * 功能描述: 连接不再活动，连接断开
     *
     * @param ctx
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 9:59
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除保存在map中的连接信息与客户端信息
        NettyChannelMap.remove((SocketChannel) ctx.channel());
        SysLog.logger.error("断开连接");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        SysLog.error("registered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        SysLog.error("unregistered");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        SysLog.error("readComplete");
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        SysLog.error("triggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        SysLog.error("changed");
    }
}