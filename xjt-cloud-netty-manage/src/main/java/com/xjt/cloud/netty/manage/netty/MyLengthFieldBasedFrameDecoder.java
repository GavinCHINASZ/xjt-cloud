package com.xjt.cloud.netty.manage.netty;

import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.netty.msg.ReceiveHandle;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.concurrent.FastThreadLocal;

import java.util.Arrays;

/**
 * @ClassName MyLengthFieldBasedFrameDecoder
 * @Description
 * @Author wangzhiwen
 * @Date 2021/5/31 9:57
 **/
public class MyLengthFieldBasedFrameDecoder extends ChannelInboundHandlerAdapter {

    /*** 实际长度 */
    private FastThreadLocal<Integer> actualLength = new FastThreadLocal<>();
    /*** 实际内容 */
    private FastThreadLocal<byte[]> actualContent = new FastThreadLocal<>();
    /*** 最大长度 */
    private FastThreadLocal<Integer> maxFrameLength = new FastThreadLocal<>();
    /*** 长度位数起始下标 */
    private FastThreadLocal<Integer> lengthFieldOffset = new FastThreadLocal<>();
    /*** 长度位数结束下标 */
    private FastThreadLocal<Integer> lengthFieldLength = new FastThreadLocal<>();
    /*** 长度调整起始下标 */
    private FastThreadLocal<Integer> lengthAdjustment = new FastThreadLocal<>();
    /*** 长度调整结束下标 */
    private FastThreadLocal<Integer> initialBytesToStrip = new FastThreadLocal<>();

    /**
     * 基础构造方法
     * @param maxFrameLength 最大长度
     * @param lengthFieldOffset 长度下标起始位置
     * @param lengthFieldLength 长度所占字节位数
     */
    public MyLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }

    /**
     * 基础构造方法
     * @param maxFrameLength 最大长度
     * @param lengthFieldOffset 长度下标起始位置
     * @param lengthFieldLength 长度所占字节位数
     * @param lengthAdjustment 真实数据下标起始位置
     * @param initialBytesToStrip 初始化略过字节数
     */
    public MyLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
        } else if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
        } else if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
        } else if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") must be equal to or greater than lengthFieldOffset (" + lengthFieldOffset + ") + lengthFieldLength (" + lengthFieldLength + ").");
        }else{
            this.maxFrameLength.set(maxFrameLength);
            this.lengthFieldOffset.set(lengthFieldOffset);
            this.lengthFieldLength.set(lengthFieldLength);
            this.lengthAdjustment.set(lengthAdjustment);
            this.initialBytesToStrip.set(initialBytesToStrip);
        }
    }

    /**
     * 实际数据读取处理
     * @param ctx 上下文
     * @throws Exception 执行异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1. 数据读取
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] requestMsg = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(requestMsg);

        // 2. 首次消息判断,并对数据处理
        if(actualContent.get() == null){
            String receiveMsg = bytesToHex(requestMsg);
            // 因为是字节数据长度，2个字节，故需要截取4位长度。下移开始下标*2，假设不是 length + value 格式
            String length = receiveMsg.substring(this.lengthFieldOffset.get(), this.lengthFieldLength.get()*2);
            // 默认长度为 16进制表示
            int i = Integer.parseInt(length, 16);
            SysLog.logger.info("第一次数据接收:{},数据长度:{}",receiveMsg,i);
            if(i + this.lengthFieldLength.get() == requestMsg.length){
                super.channelRead(ctx,requestMsg);
            }
            // 数据长度，带长度位
            actualLength.set(i + this.lengthFieldLength.get());
            actualContent.set(requestMsg);
        }else{
            // 3. 二次分包处理
            byte[] content = actualContent.get();
            int newLength = content.length + requestMsg.length;
            if(newLength > maxFrameLength.get()){
                throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength.get() + " - discarding");
            }

            byte[] next = appendByteArray(content,requestMsg);
            msg = new String(next, "UTF-8");
            SysLog.logger.info("next:{}",msg);
            if(actualLength.get() == newLength){
                byte[] actByte = null;
                if(lengthAdjustment.get() == 0){
                    actByte = Arrays.copyOfRange(next, initialBytesToStrip.get(), next.length);
                }else{
                    byte[] startByte = Arrays.copyOfRange(next, 0, lengthAdjustment.get());
                    byte[] endByte = Arrays.copyOfRange(next, initialBytesToStrip.get(), next.length);
                    actByte = appendByteArray(startByte,endByte);
                }
                super.channelRead(ctx,actByte);
            }else{
                actualContent.set(next);
            }
            ReceiveHandle receiveHandle = new ReceiveHandle();
            receiveHandle.receiveHandle((SocketChannel) ctx.channel(),msg);
        }
    }

    /**
     * 执行中异常
     * @param ctx 上下文
     * @param cause 执行异常
     * @throws Exception 方法体异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SysLog.logger.info("执行中异常",cause);
        releaseThreadLocalResource();
        ctx.close();
    }

    /**
     * 连接断开时，释放当前线程变量资源
     * @param ctx 上下文
     * @throws Exception 执行中异常
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        releaseThreadLocalResource();
        SysLog.logger.info("客户端连接断开了+++++++{}", ctx.channel().remoteAddress().toString());
        super.channelUnregistered(ctx);
    }

    /**
     * 释放当前线程变量
     */
    private void releaseThreadLocalResource(){
        maxFrameLength.remove();
        lengthFieldOffset.remove();
        lengthFieldLength.remove();
        lengthAdjustment.remove();
        initialBytesToStrip.remove();
        actualLength.remove();
        actualContent.remove();
    }

    /**
     * byte[] 数组转16进制字符串
     * @param bytes 字节数据
     * @return 16进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 字节数组拼接
     * @param array1 拼接第一个数组
     * @param array2 第二个数组
     * @return 拼接结果
     */
    private byte[] appendByteArray(byte[] array1,byte[] array2){
        byte[] newArray = new byte[array1.length + array2.length];
        System.arraycopy(array1,0,newArray,0,array1.length);
        System.arraycopy(array2,0,newArray,array1.length,array2.length);
        return newArray;
    }
}