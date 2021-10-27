package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.FuAnInfo;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @ClassName FuAn
 * @Author dwt
 * @Date 2019-09-02 15:50
 * @Description 赋安火警主机
 * @Version 1.0
 */
public class FuAn extends BaseReceive{

    /**
     *@Author: dwt
     *@Date: 2019-09-02 15:54
     *@Param: io.netty.channel.socket.SocketChannel,java.lang.String
     *@Return:
     *@Description 赋安火警主机信息解析
     */
    public static String fuAnReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException{
        msg = msg.replace(" ","");
        if(msg.length() >= 12){
            //命令字
            String command = msg.substring(6,8);
            if("A2".equals(command)){
                if("F00001A2A699".equals(msg)){
                    return "F00003A200004820";
                }else{
                    return "F00003A2A100CC3E";
                }
            }else if("A5".equals(command)){
                String eventStr = FuAnInfo.functionAttriMap.get(msg.substring(8,10));
                if(StringUtils.isEmpty(eventStr)){
                    return "";
                }
                if("14".equals(msg.substring(4,6)) && msg.length() < 50){
                    return "F00003A2A100CC3E";
                }else if("28".equals(msg.substring(4,6)) && msg.length() < 90){
                    return "F00003A2A100CC3E";
                }

                //F0 00(地址4) 14/28(数据长度6) A5(命令字：A1 复位 A2 巡检A5 发送事件8) (C3 02 05 01 81 12 03 01 10 18 3B 01 01 01 01 01 02 00 0A)(数据段) (48 6B)(CRC16/XMODEM协议校验码:高位在前,低位在后)
                //数据段解析  数据长度为14表示没有位置代码
                /*  14(20字节为例)C3 02 05 01 81 12 03 01 10 18 3B 01 01 01 01 01 02 00 0A
                    功能属性 1(C3) 主要用于区别当前事件的类型，如：火警、启动、故障、屏蔽等。具体代码见功能属性说明。
                    信息类型 1(02) 主要用于区别产生事件的部件类，如：探头、模块、多线等。具体代码见信息类型说明。
                    设备状态 1(05) 主要用于区别当前报警设备的状态，如：正常、地址丢失，启动成功等。具体代码见设备状态说明。
                    网络号 1(01) 此值由火灾报警控制器中 CAN 联网设置参数决定 取值范围为：0-99
                    主从和机号 1(81) 此值由火灾报警控制器中 CAN 联网设置参数决定 机号取值范围为：0-99 当设置控制器为主机时，此字节等于机号取值 当设置控制器为从机时，
                                 此字节等于机号取值+128(0x80) 即此字节的最高位表示控制器为主机还是从机 最高位为 1：表示从机 最高位为 0：表示主机
                    年 1  取值范围为：0-99 计算实际年份时需加 2000 例：此值为 18 表示当前年份为 2018 年。
                    月 1 取值范围为：1-12
                    日 1 取值范围为：1-31
                    时 1 取值范围为：0-23
                    分 1 取值范围为：0-59
                    秒 1 取值范围为：0-59
                    回路 1 取值范围为：1-250
                    地址 1 取值范围为：1-250
                    栋 1 取值范围为：0-99
                    区 1 取值范围为：0-99
                    层 1 取值范围为：  0-99（整数层） 113-119（十六进制 0x71~0x77，表示负 1-7 层）129-137（十六进制 0x81~0x89，表示夹层 1.5-9.5 层）
                    号 2 取值范围为：0-999。低字节先发。例：2 号设备，收到 0x02 0x00 500 号设备，收到 0xF4 0x01
                    设备类型 1 具体代码见设备类型说明。
                    位置代码 20 （数据长度为28表示有位置代码）
                * */
                FireAlarmEvent event = new FireAlarmEvent();
                event.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
                event.setFrom(regId);
                event.setSourceData(msg);
                event.setBrand("赋安");
                event.setEvent(Integer.parseInt(eventStr));
                //event.setTime(new Date());
                event.setHeartbeat(false);
                //F000 14 A5 C3 02 05 01 81 12 03 01 10 18 3B 01 01 01 01 01 02 00 0A 486B
                String mtName = FuAnInfo.equipmentTypeMap.get(new BigInteger(msg.substring(44,46),16)+"");
                event.setMtName(mtName);
                StringBuffer sb = new StringBuffer();

                Integer num1;
                Integer num2;
                //回路
                num1 = Integer.parseInt(new BigInteger(msg.substring(30,32),16) + "");
                if(num1 != 0){
                    sb.append(num1 + "号回路");
                }
                //地址
                num1 = Integer.parseInt(new BigInteger(msg.substring(32,34),16) + "");
                if(num1 != 0){
                    sb.append(num1 + "号地址");
                }
                //栋
                num1 = Integer.parseInt(new BigInteger(msg.substring(34,36),16) + "");
                if(num1 != 0){
                    sb.append(num1 + "栋");
                }
                //区
                num1 = Integer.parseInt(new BigInteger(msg.substring(36,38),16) + "");
                if(num1 != 0){
                    sb.append(num1 + "区");
                }
                //层
                String floor = msg.substring(38,40);
                num1 = Integer.parseInt(new BigInteger(floor,16) + "");
                if(num1 != 0){
                    String floorStr = floor.substring(0,1);
                    if("7".equals(floorStr)){
                        sb.append("-"+floor.substring(1,2)+"层");
                    }else if("8".equals(floorStr)){
                        sb.append(floor.substring(1,2)+".5层");
                    }else{
                        sb.append(num1 + "层");
                    }
                    //号
                    num1 = Integer.parseInt(new BigInteger(msg.substring(40,42),16) + "");
                    num2 = Integer.parseInt(new BigInteger(msg.substring(42,44),16) + "");
                    if(num2 != 0){
                        num2 = num2*16;
                        num1 = num1 + num2;
                    }
                    if(num1 != 0){
                        sb.append(num1+"号");
                    }
                }
                //event.setAddress(sb.toString());
                event.setPosition(sb.toString());
                //event.setHostNumber("1号从机");
                return sendHttpGet(socketChannel, msg, regId, event);
            }
        }
        return "";
    }
}
