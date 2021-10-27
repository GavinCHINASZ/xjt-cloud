package com.xjt.cloud.netty.manage.netty;

import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:51
 * @Description:netty服务初使化类
 */
public class NettyServer {
    private int port;
    public NettyServer(int port){
        this.port = port;
    }

    public void run() throws Exception{
        SysLog.logger.error("开始启动监控：port=" + port);
        //Group：群组，Loop：循环，Event：事件，这几个东西联在一起，相比大家也大概明白它的用途了。
        //Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
        //NioEventLoopGroup是一个处理I/O操作的多线程事件循环
        //bossGroup作为boss,接收传入连接
        //因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        //workerGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worker
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            //ServerBootstrap负责建立服务端
            //你可以直接使用Channel去建立服务端，但是大多数情况下你无需做这种乏味的事情
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //指定使用NioServerSocketChannel产生一个Channel用来接收连接 添加线程池
                    .channel(NioServerSocketChannel.class)
                    //ChannelInitializer用于配置一个新的Channel
                    //用于向你的Channel当中添加ChannelInboundHandler的实现
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch) throws Exception {
                            //ChannelPipeline用于存放管理ChannelHandel
                            //ChannelHandler用于处理请求响应的业务逻辑相关代码
                            //readerIdleTime：读超时时间  writerIdleTime：写超时时间  allIdleTime：所有类型超时时间  添加时,发需在handler之前
                            ch.pipeline().addLast(new IdleStateHandler(300, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new NettyServerHandler());0
                                    //.addLast(new ReadTimeoutHandler(ConstantsClient.CONN_FAI_TIME, TimeUnit.SECONDS));//闲置超时间，300 秒
                        }
                    })

                    //对Channel进行一些配置
                    //注意以下是socket的标准参数
                    //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    //Option是为了NioServerSocketChannel设置的，用来接收传入连接的
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
                    //childOption是用来给父级ServerChannel之下的Channels设置参数的
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定,并开始接受传入的连接
            ChannelFuture f=b.bind(port).sync();
            //sync()会同步等待连接操作结果，用户线程将在此wait()，直到连接操作完成之后，线程被notify(),用户代码继续执行
            //closeFuture()当Channel关闭时返回一个ChannelFuture,用于链路检测
            f.channel().closeFuture().sync();

             //绑定端口 开始接收连接
             //绑定多个端口
             /*ChannelFuture f = b.bind(5100);
             ChannelFuture f1 = b.bind(5200);

             f.channel().closeFuture().sync();
             f1.channel().closeFuture().sync();*/
            SysLog.logger.error("启动监控成功：port=" + port);
        }catch (Exception ex){
            SysLog.error("启动监控失败：port=" + port + ex);
        }finally{
            //资源释放
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=8088;
        try {
            new NettyServer(port).run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

