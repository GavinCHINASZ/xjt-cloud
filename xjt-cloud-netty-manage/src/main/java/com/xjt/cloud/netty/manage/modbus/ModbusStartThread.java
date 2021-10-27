package com.xjt.cloud.netty.manage.modbus;

import com.xjt.cloud.commons.utils.SysLog;

/**
 * @ClassName ModbusStartThread
 * @Description
 * @Author wangzhiwen
 * @Date 2021/1/20 11:30
 **/
public class ModbusStartThread extends Thread {
    private String ip;

    public ModbusStartThread(String ip){
        this.ip = ip;
    }

    @Override
    public void run(){
        try {
            new ModbusServer(ip).run();
        } catch (Exception e) {
            SysLog.error("Modbus线程启动错误");
            SysLog.error(e);
        }
    }
}
