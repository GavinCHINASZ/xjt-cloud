package com.xjt.cloud.netty.web.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 10:43
 * @Description: 客户端资源文件信息与常量类
 */
public interface ConstantsNettyWeb {
    //netty 端口配置
    String NETTY_PORTS = PropertyUtils.getProperty("netty.ports");

    //客户端注册id开始值
    String BEGIN_REG_ID = PropertyUtils.getProperty("begin.reg.id");
    //大屏web socket标识
    String BIG_SCREEN_SOCKET = "BIGSCREENSOCKET";
    String LOGIN_WEBSOCKET = "_WEBSOCKET_";
    //用户登录标识
    String LOGIN_TYPE = "LOGIN";
    //#web socket发送信息地址
    String WEB_SOCKET_SEND_MSG_URL = PropertyUtils.getProperty("web.socket.send.msg.url");
    //map数据过期时间
    Long MAP_EXPIRED = Long.parseLong(PropertyUtils.getProperty("map.expired"));
    //缓存数据过期时间
    Long MSG_BACK_TIME = Long.parseLong(PropertyUtils.getProperty("msg.back.time"));
}
