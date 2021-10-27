package com.xjt.cloud.netty.manage.netty;


import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.NettyThread;

public class NettyStartThread extends Thread{
    private int port;

    private NettyStartThread(){}

    public NettyStartThread(int port){
        this.port = port;
    }

    @Override
    public void run(){
        try {
            NettyChannelMap.addNettyPort(port);
            if(this.port == 1182){
                new NettyLengthServer(port).run();
            }else{
                new NettyServer(port).run();
            }

        } catch (Exception e) {
            SysLog.error("netty线程启动错误，port=" + port);
            SysLog.error(e);
        }
    }
}
