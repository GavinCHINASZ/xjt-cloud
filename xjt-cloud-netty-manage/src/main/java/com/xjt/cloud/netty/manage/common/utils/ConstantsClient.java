package com.xjt.cloud.netty.manage.common.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 10:43
 * @Description: 客户端资源文件信息与常量类
 */
public interface ConstantsClient {
    //是否存在netty 为空时或true，表示有
    String IS_EXIST_NETTY = PropertyUtils.getProperty("is.exist.netty");

    //是否存在netty  true，表示有
    String IS_EXIST_MODBUS = PropertyUtils.getProperty("is.exist.modbus");
    //modbus ip地址
    String MODBUS_IP = PropertyUtils.getProperty("modbus.ip");
    //modbus 端口
    String MODBUS_PORT = PropertyUtils.getProperty("modbus.port");
    //从机地址
    String MODBUS_SLAVE_ID = PropertyUtils.getProperty("modbus.slave.id");
    //寄存器读取开始地址
    String MODBUS_START_ADDRESS = PropertyUtils.getProperty("modbus.start.address");
    //寄存器读取结束地址
    String MODBUS_END_ADDRESS = PropertyUtils.getProperty("modbus.end.address");
    //读取的寄存器数量
    String MODBUS_QUANTITY = PropertyUtils.getProperty("modbus.quantity");
    //modbus读取数据时间间隔毫秒
    String MODBUS_INTERVAL_TIME = PropertyUtils.getProperty("modbus.interval.time");
    //modbus注册码
    String MODBUS_BEGIN_REG_ID = PropertyUtils.getProperty("modbus.begin.reg.id");
    //modbus数据上传
    String MODBUS_SEND_MSG_URL = PropertyUtils.getProperty("modbus.send.msg.url");

    //客户端注册id开始值
    String BEGIN_REG_ID = PropertyUtils.getProperty("begin.reg.id");
    //判断注册码是否是本公司产品请求url
    String IS_MY_PRODUCT_BY_REG_ID_URL = PropertyUtils.getProperty("is.my.product.by.reg.id.url");
    //#netty socket发送信息地址
    String NETTY_SOCKET_SEND_MSG_URL = PropertyUtils.getProperty("netty.socket.send.msg.url");


    //连接进期时间
    long CONN_FAI_TIME = Long.parseLong(PropertyUtils.getProperty("conn.fail.time")) * 60;

    //上传信息保存接口url
    String SAVE_MSG_URL = PropertyUtils.getPropertyNull("save.msg.url");
    //上传VESDA信息保存接口url
    String SAVE_MSGVSM_URL = PropertyUtils.getPropertyNull("save.msgvsm.url");
    //信息过期时间，（秒）
    long MSG_BACK_TIME = Long.parseLong(PropertyUtils.getProperty("msg.back.time"));
    long MAP_EXPIRED = Long.parseLong(PropertyUtils.getProperty("map.expired"));

    //#新普利斯 需要处理的信息配置
    //新普利斯客户端注册id开始值
    String BEGIN_XPLS_REG_ID = PropertyUtils.getProperty("begin.xpls.reg.id");
    //事件类型 F   火警,T   故障,C   控制,S   监视,U   Utility,F，T，C，S为常用
    String XPLS_NEED_EVENT_TYPE = PropertyUtils.getProperty("xpls.need.event.type");
    //1表示on或Abnorma, 0表示off或Normal
    String XPLS_SWITCH_STATE = PropertyUtils.getProperty("xpls.switch.state");
    //* 	表示事件未确认,-  	表示事件已确认
    String XPLS_EVENT_STATE = PropertyUtils.getProperty("xpls.event.state");
    //新普利期心跳包开始字符串
    String XPLS_EVENT_XTXT = PropertyUtils.getProperty("xpls.event.xtxt");

    //诺蒂菲尔3030客户端注册id开始值
    String BEGIN_NFS_REG_ID = PropertyUtils.getProperty("begin.nfs.reg.id");

    //西门子客户端注册id开始值
    String BEGIN_SIEMENS_REG_ID = PropertyUtils.getProperty("begin.siemens.reg.id");

    //上传多条信息保存接口url
    String SAVE_MSGLIST_URL = PropertyUtils.getProperty("save.msglist.url");

    //诺蒂菲尔6000客户端注册id开始值
    String BEGIN_NFS6000_REG_ID = PropertyUtils.getProperty("begin.nfs6000.reg.id");

    //VESDA VSM DTU客户端注册id开始字符串
    String BEGIN_VESDA_REG_ID = PropertyUtils.getProperty("begin.vesda.reg.id");

    //VESDA VSM DTU客户端注册id开始字符串
    String BEGIN_FUAN_REG_ID = PropertyUtils.getProperty("begin.fuan.reg.id");

    //麦克水压表新协议数据头
    String BEGIN_MK_WATERGAGE_START_MSG = PropertyUtils.getProperty("begin.mk,waterGage.startMsg");

    //麦克水压表新协议数据尾
    String BEGIN_MK_WATERGAGE_END_MSG = PropertyUtils.getProperty("begin.mk.waterGage.endMsg");

    //爱德华 DTU客户端注册id开始字符
    String BEGIN_ADH_REG_ID = PropertyUtils.getProperty("begin.aidehua.reg.id");

    //火眼协议
    String BEGIN_FIRE_EYE_REG_ID = PropertyUtils.getProperty("begin.fire.eye.reg.id");
    //联动设备注册码头
    String BEGIN_LINKAGE_DEVICE_REG_ID = PropertyUtils.getProperty("begin.linkage.device.reg.id");

    //火眼图片保存URL
    String BEGIN_FIRE_EYE_IMAGE_URL = PropertyUtils.getProperty("begin.fire.eye.image.url");

    //北大青鸟客户端注册id开始字符串
    String BEGIN_PEKING_BLUE_BIRD_REG_ID = PropertyUtils.getProperty("begin.peking.blue.bird.reg.id");

    //三江火警主机客户端注册id开始字符
    String BEGIN_SNAJIANG_REG_ID = PropertyUtils.getProperty("begin.sanjiang.reg.id");

    //诺蒂菲尔图文报警客户端注册id开始字符
    String BEGIN_NFS_GRAPHIC_REG_ID = PropertyUtils.getProperty("begin.nfs.graphic.reg.id");

    //新普利斯图文报警客户端注册id开始字符
    String BEGIN_XPLS_GRAPHIC_REG_ID = PropertyUtils.getProperty("begin.xpls.graphic.reg.id");

    //赋安火警主机接打印口报警客户端注册id开始字符
    String BEGIN_FUAN_PRINT_REG_ID = PropertyUtils.getProperty("begin.fuan.print.reg.id");

    /**
     * 西安 拓普索尔 消火栓 消息头 7470736C=TOPSAIL
     */
    String BEGIN_TOPSAIL_DEVICE_REG_ID = PropertyUtils.getProperty("begin.topsail.device.reg.id");
}
