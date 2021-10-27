package com.xjt.cloud.netty.manage.common.utils;


import com.xjt.cloud.netty.manage.netty.NettyLengthServer;
import com.xjt.cloud.netty.manage.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

public class NettyThread extends Thread{
    private int port;
    private int type;
    @Autowired
    public NettyThread(){}

    public NettyThread(int port, int type){
        this.port = port;
        this.type = type;
    }
    @Override
    public void run(){
        try {
           if(this.type == 1){
               new NettyLengthServer(port).run();
           }else{
               new NettyServer(port).run();
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
