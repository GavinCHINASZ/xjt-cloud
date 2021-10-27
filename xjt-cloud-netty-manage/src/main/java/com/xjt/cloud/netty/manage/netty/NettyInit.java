package com.xjt.cloud.netty.manage.netty;

import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.ThreadPoolUtils;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/8 0008 09:24
 * @Description:
 */
public class NettyInit {
    public static void NettyPortInit(){
        String[] ports = Constants.NETTY_PORTS.split(",");
        try {
            for (String port : ports){
                if (NettyChannelMap.isNettyPortInit(Integer.parseInt(port))) {//判断该端口是否已监控
                    ThreadPoolUtils.getInstance().execute(new NettyStartThread(Integer.parseInt(port)));
                }
                Thread.sleep(10000);
            }
            ThreadPoolUtils.getInstance().execute(new NettyStartThread(1182));
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }
}
