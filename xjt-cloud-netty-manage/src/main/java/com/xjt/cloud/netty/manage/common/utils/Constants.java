package com.xjt.cloud.netty.manage.common.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
* 工程公共常量
 *
* @author wangzhiwen
* @date 2015-11-16 下午02:21:51 
*
 */
public interface Constants extends com.xjt.cloud.commons.utils.Constants {
    //火警主机消息类型
    String FIRE_ALARM_MSG_TYPE = "FireAlarm";
    //消火栓消息类型
    String HY_DRANT_MSG_TYPE = "Hydrant";
    //压力表液位表消息类型
    String WATER_GAGE_MSG_TYPE="WaterGage";
    //netty 端口配置
    String NETTY_PORTS = PropertyUtils.getProperty("netty.ports");
    //Vesda VSM 消息类型
    String VESDA_VSM_MSG_TYPE = "VesdaVSM";
    //联动设备消息类型
    String LINKAGE_DEVICE_MSG_TYPE = "LinkageDevice";
    //火眼消息类型
    String FIRE_EYE_MSG_TYPE = "FireEye";

    /**
     * 西安 拓普索尔 消火栓 消息头 7470736C=TOPSAIL
     */
    String TOPSAIL_MSG_STARTS_WITH = "7470736C";
    String TOPSAIL_DEVICE_MSG_TYPE = "TOPSAIL";

    /**
     * 天津合极电气火灾消息头
     *    起始符 1 byte ’@’（0x40）
     *    协议版本号 1 byte 固定 0x11（表示 V1.1）
     *
     *    数据结束符: 23
     */
    String HE_JI_MSG_STARTS_WITH = "4011";
    String HE_JI_MSG_END_WITH = "23";

}
