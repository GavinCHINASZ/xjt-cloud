package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName SIEMENS 西门子
 * @Author dwt
 * @Date 2019-05-16 15:26
 * @Description：西门子协议信息解析和消息发送
 * @Version 1.0
 */
public class SIEMENS extends BaseReceive{

    /**
     * @Author: dwt
     * @Date: 2019-05-16 15:49
     * @Param: SocketChannel socketChannel, String msg, String regId
     * @Return: java.lang.String
     * @Description:西门子信息解析
     */
    public static String siemensReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) {
        //SysLog.debug("西门子响应信息解析！！！！！！！！！！！！！");
        msg = StringUtils.replaceBlank(msg);
        //SysLog.debug("regId:" + regId + ";msg:" + msg);//EB8000017C2801050000000009CFA001081208080F2C11FF3BEB
        String resultStr = "";
        String strAppend = "";
        String[] strArr = msg.split("EBEB");
        List<FireAlarmEvent> fireList = new ArrayList<FireAlarmEvent>();
        for (String str : strArr) {
            str.replace("ECEC", "EB").replace("ECED", "EC");
            if (!str.startsWith("EB")) {
                str = "EB" + str;
            }
            if (!str.endsWith("EB")) {
                str = str + "EB";
            }
            if(str.length()<52){
                continue;
            }
            //SysLog.error("str.length():"+str.length()+"!!!!!!!!!!!!!!!");

            //判断信息是否需要保存
            if (str.length() == 52) {
                //        EB800001091A 01 01 0000 00000D32 A0 0113 130B1E0F3625A46EEB
                // str = "EB8000017C28 01 05 0000 000009CF A0 0108 1208080F2C11FF3BEB";
                //        EB8000017C1C 01 04 0000 00000004 A0 052C 1208080F2C03CF2CEB
                //        EB80000153AB 01 02 0000 00000692 A0 0101 13 041B0B15358A14EB
                //        EB8000010402 01 01 0000 00000F2E A0 0209 1403180B05344861EB
                // 0x05:   -0x0000回路地址 -0x000009CF设备编码 0xA0", "event":"0x0108 EB000500  0000000000000000000000000000000000
                if ("01".equals(str.substring(12, 14))) {
                    String event = str.substring(30, 34).trim();//0108
                    int eventType = 0;
                    if (event.startsWith("01")) {
                        eventType = 1;//火警事件
                    } else if (event.startsWith("02")) {
                        eventType = 2;//监管事件
                    } else if (event.startsWith("03")) {
                        eventType = 7;//故障事件
                    }else if(event.startsWith("0701")){
                        eventType = 6;//恢复事件
                    }
                   // SysLog.error("判断事件类型："+event+"!!!!!!!!!!!!!!!!!!!");
                    if(eventType!=0){
                        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
                        fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
                        fireAlarmEvent.setBrand("西门子");//品牌
                        fireAlarmEvent.setFrom(regId);//来源ID,根据注册Id区分
                        fireAlarmEvent.setSourceData(str);//源数据
                        fireAlarmEvent.setEvent(eventType);// 1:火警事件，2:监管事件，3:故障事件
                        fireAlarmEvent.setAddress(StringUtils.hexStringToAlgorism(str.substring(14, 16)) + "");//地址号
                        fireAlarmEvent.setPositionCode(StringUtils.hexStringToAlgorism(str.substring(16, 20)) + "");//回路地址0000
                        fireAlarmEvent.setMtCode(StringUtils.hexStringToAlgorism(str.substring(20, 28)) + "");//设备编码
                        fireAlarmEvent.setPosition(StringUtils.hexStringToAlgorism(str.substring(14, 16)) + "");
                        fireList.add(fireAlarmEvent);//添加到火警主机集合统一发送保存
                    }
                }
            }
            strAppend = "EB000500" + str.substring(8, 12) + "0000000000000000000000000000000000";
            String crcCode = StringUtils.Make_CRC(strAppend).toUpperCase().replace("EC", "ECED").replace("EB", "ECEC");
            resultStr = resultStr + strAppend + crcCode + "EB";
        }
        if(fireList.size()>0){
            for(FireAlarmEvent entity:fireList){
                sendHttpGet(socketChannel, msg, regId, entity);
            }
        }
        //SysLog.error("回复信息："+regId+"————"+resultStr);
        return resultStr;
    }
}
