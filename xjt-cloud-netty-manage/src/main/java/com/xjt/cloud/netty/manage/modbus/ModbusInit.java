package com.xjt.cloud.netty.manage.modbus;

import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.ThreadPoolUtils;
import com.xjt.cloud.netty.manage.netty.NettyStartThread;

/**
 * @ClassName ModbusInit
 * @Description
 * @Author wangzhiwen
 * @Date 2021/1/20 11:13
 **/
public class ModbusInit {
    public static void ModbusPortInit(String ip){
        try {
            ThreadPoolUtils.getInstance().execute(new ModbusStartThread(ip));
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }
}
