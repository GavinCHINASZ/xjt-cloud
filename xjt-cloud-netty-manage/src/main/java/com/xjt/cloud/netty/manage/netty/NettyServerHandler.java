package com.xjt.cloud.netty.manage.netty;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import com.xjt.cloud.netty.manage.netty.msg.ReceiveHandle;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * ChannelInboundHandler提供了不同的事件处理方法你可以重写
 * 心跳
 * 
 * @author wangzhiwen
 * @date 2019/4/24 17:51
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 功能描述: 连接成功
     *
     * @param ctx
     * @return: void
     * @author wangzhiwen
     * @date 2019/4/25 9:58
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SysLog.logger.info("已经连接到服务器");
    }

    /**
     * 功能描述:该方法用于接收从客户端接收的信息
     *
     * @param ctx 连接对象
     * @param msg 接收到的客户上传的信息
     * @return void
     * @author wangzhiwen
     * @date 2019/4/25 9:57
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ReceiveHandle receiveHandle = new ReceiveHandle();
            // 对上传信息处理，并返回传送给客户端的信息
            String res = receiveHandle.receiveHandle((SocketChannel) ctx.channel(), msg);
            if (StringUtils.isNotEmpty(res)) {
                // ChannelHandlerContext提供各种不同的操作用于触发不同的I/O时间和操作
                // 调用write方法来逐字返回接收到的信息
                // 这里我们不需要在DISCARD例子当中那样调用释放，因为Netty会在写的时候自动释放
                // 只调用write是不会释放的，它会缓存，直到调用flush
                // res = "EB000500539500000000000000000000000000000000004209EB";
                String regId = NettyChannelMap.getRegIdByChannelId((SocketChannel) ctx.channel());
                if(regId !=null){
                    regId= StringUtils.urlDecode(regId);
                }

                if (!StringUtils.isHexNumber(res)) {
                    // 返回信息进行处理
                    byte[] req = res.getBytes("UTF-8");
                    ByteBuf resBf = Unpooled.buffer(req.length);
                    resBf.writeBytes(req);
                    // 返回信息
                    ctx.writeAndFlush(resBf);
                } else if(regId != null && (StringUtils.startsWith(regId,ConstantsClient.BEGIN_SIEMENS_REG_ID) || StringUtils.startsWith(regId,ConstantsClient.BEGIN_FUAN_REG_ID))){
                    if(StringUtils.startsWith(regId,ConstantsClient.BEGIN_FUAN_REG_ID)){
                        res = res.replace(" ","");
                        res = res.replace("0x","");
                    }

                    // 返回信息进行处理
                    byte[] req = StringUtils.hexString2Bytes(res);
                    ByteBuf byteBuf = Unpooled.buffer(req.length);
                    byteBuf.writeBytes(req);
                    res = StringUtils.receiveHexToString(req);
                    // 返回信息
                    // ctx.writeAndFlush(res);
                    final String reqFinal = res;
                    try {
                        // netty需要用ByteBuf传输
                        ByteBuf bufff = Unpooled.buffer();
                        // 对接需要16进制
                        bufff.writeBytes(StringUtils.hexString2Bytes(reqFinal));
                        ctx.writeAndFlush(bufff).addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                StringBuilder sb = new StringBuilder("");
                                if (future.isSuccess()) {
                                    //log.info(sb.toString()+"回写成功"+receiveStr);
                                } else {
                                    //log.error(sb.toString()+"回写失败"+receiveStr);
                                }
                            }
                        });
                    } catch (Exception e) {
                        SysLog.error(e);
                    }
                }
            }
        } catch (Exception e) {
            SysLog.error(e);
        }finally {
            // 清除
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 功能描述: 捕捉到的异常信息
     *
     * @param ctx
     * @param cause
     * @return: void
     * @author wangzhiwen
     * @date 2019/4/25 9:58
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();

        //SysLog.error(cause.getMessage());
        // 删除保存在map中的连接信息与客户端信息
        NettyChannelMap.remove((SocketChannel) ctx.channel());
        ctx.close();
    }

    /**
     * 功能描述: 连接不再活动，连接断开
     *
     * @param ctx
     * @return: void
     * @author wangzhiwen
     * @date 2019/4/25 9:59
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 删除保存在map中的连接信息与客户端信息
        NettyChannelMap.remove((SocketChannel) ctx.channel());
        SysLog.logger.info("断开连接");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception { }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception { }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception { }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 超时事件
        if (evt instanceof IdleStateEvent) {
            /*IdleStateEvent idleEvent = (IdleStateEvent) evt;
            if (idleEvent.state() == IdleState.READER_IDLE) {
                // 读
                NettyChannelMap.remove((SocketChannel) ctx.channel());
                ctx.channel().close();
            } else if (idleEvent.state() == IdleState.WRITER_IDLE) {
                // 写

            } else if (idleEvent.state() == IdleState.ALL_IDLE) {
                // 全部

            }*/
            NettyChannelMap.remove((SocketChannel) ctx.channel());
            ctx.channel().close();
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        SysLog.error("changed");
    }
}
