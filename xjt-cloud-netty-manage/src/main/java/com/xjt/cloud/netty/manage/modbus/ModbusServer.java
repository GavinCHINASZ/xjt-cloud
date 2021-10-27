package com.xjt.cloud.netty.manage.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;

import java.net.InetAddress;

/**
 * @ClassName ModbusServer
 * @Description
 * @Author wangzhiwen
 * @Date 2021/1/20 11:31
 **/
public class ModbusServer {
    private String ip;

    public ModbusServer(String ip){
        this.ip = ip;
    }

    public void run() throws Exception{
        try {
            // 设置主机TCP参数
            TcpParameters tcpParameters = new TcpParameters();
            // 设置TCP的ip地址
            InetAddress adress = InetAddress.getByName(ip);

            // TCP参数设置ip地址
            // tcpParameters.setHost(InetAddress.getLocalHost());
            tcpParameters.setHost(adress);
            // TCP设置长连接
            tcpParameters.setKeepAlive(true);
            // TCP设置端口，这里设置是默认端口502
            tcpParameters.setPort(Integer.parseInt(ConstantsClient.MODBUS_PORT));

            // 创建一个主机
            ModbusMaster m = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);

            int slaveId = Integer.parseInt(ConstantsClient.MODBUS_SLAVE_ID);//从机地址
            int startAddress = Integer.parseInt(ConstantsClient.MODBUS_START_ADDRESS);//寄存器读取开始地址
            int endAddress = Integer.parseInt(ConstantsClient.MODBUS_END_ADDRESS);//寄存器读取开始地址
            int quantity = Integer.parseInt(ConstantsClient.MODBUS_QUANTITY);//读取的寄存器数量
            int modbusIntervalTime = Integer.parseInt(ConstantsClient.MODBUS_INTERVAL_TIME);//modbus读取数据时间间隔毫秒

            try {
                SysLog.error("连接ip:" + ip);
                for(;;) {
                    try {
                        if (!m.isConnected()) {
                            SysLog.error("启动连接ip:" + ip);
                            m.connect();// 开启连接
                        }
                        // 读取对应从机的数据，readInputRegisters读取的写寄存器，功能码03
                        int[] registerValues = m.readHoldingRegisters(slaveId, startAddress, quantity);
                        try {
                            ModbusChannelMap.msgHandle(startAddress, registerValues, endAddress);
                        }catch (Exception ex){

                        }
                        Thread.sleep(modbusIntervalTime);//睡眠时间，隔多久时间读一次
                    }catch (ModbusProtocolException e) {
                        m.disconnect();
                        SysLog.error(e);
                    } catch (ModbusNumberException e) {
                        m.disconnect();
                        SysLog.error(e);
                    } catch (ModbusIOException e) {
                        m.disconnect();
                        SysLog.error(e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }  finally {
                try {
                    m.disconnect();
                } catch (ModbusIOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
