package com.xjt.cloud.iot.netty.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 10:43
 * @Description: 客户端资源文件信息与常量类
 */
public interface ConstantsIotNetty {
    //netty 端口配置
    String NETTY_PORTS = PropertyUtils.getProperty("netty.ports");

    //客户端注册id开始值
    String BEGIN_REG_ID = PropertyUtils.getProperty("begin.reg.id");
    //判断注册码是否是本公司产品请求url
    String IS_MY_PRODUCT_BY_REG_ID_URL = PropertyUtils.getProperty("is.my.product.by.reg.id.url");

    //信息过期时间，（秒）
    long MSG_BACK_TIME = Long.parseLong(PropertyUtils.getProperty("msg.back.time"));

    //大屏web socket标识
    String BIG_SCREEN_SOCKET = "BIGSCREENSOCKET";
    //物联设备web socket标识
    String WEB_SOCKET_KEYS = PropertyUtils.getProperty("web_socket_keys");
}
