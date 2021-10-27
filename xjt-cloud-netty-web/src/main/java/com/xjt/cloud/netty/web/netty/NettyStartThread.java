package com.xjt.cloud.netty.web.netty;


public class NettyStartThread extends Thread{
    private int port;

    public NettyStartThread(int port){
        this.port = port;
    }

    @Override
    public void run(){
        try {
            NettyChannelMap.addNettyPort(port);
            new NettyServer(port).run();
        } catch (Exception e) {
            System.out.println("netty线程启动错误，port=" + port);
            e.printStackTrace();
        }
    }
}
