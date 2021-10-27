package com.xjt.cloud.netty.manage.common.utils;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.modbus.ModbusInit;
import com.xjt.cloud.netty.manage.netty.NettyInit;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/8 0008 10:30
 * @Description:
 */
public class SpringBootInitUtils {
    public static void SpringBootServletInitializer(){
        if (StringUtils.isEmpty(ConstantsClient.IS_EXIST_NETTY) || "true".equals(ConstantsClient.IS_EXIST_NETTY)) {
            NettyInit.NettyPortInit();
        }

        if (StringUtils.isNotEmpty(ConstantsClient.IS_EXIST_MODBUS) && "true".equals(ConstantsClient.IS_EXIST_MODBUS)) {
            ModbusInit.ModbusPortInit(ConstantsClient.MODBUS_IP);
            //ModbusInit.ModbusPortInit("192.168.0.27");
        }
    }
}
