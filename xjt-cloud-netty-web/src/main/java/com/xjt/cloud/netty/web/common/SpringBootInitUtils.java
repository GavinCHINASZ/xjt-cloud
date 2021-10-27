package com.xjt.cloud.netty.web.common;


import com.xjt.cloud.netty.web.netty.NettyInit;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/8 0008 10:30
 * @Description:
 */
public class SpringBootInitUtils {
    public static void SpringBootServletInitializer(){
        NettyInit.NettyPortInit();
    }
}
