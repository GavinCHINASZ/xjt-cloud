package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName PeKingBuleBird
 * @Author dwt
 * @Date 2020-05-15 15:09
 * @Description 北大青鸟火警主机事件解析
 * @Version 1.0
 */
public class PeKingBuleBird extends BaseReceive{

    /**
     *@Author: dwt
     *@Date: 2020-05-15 15:14
     *@Param: io.netty.channel.socket.SocketChannel,java.lang.String
     *@Return: java.lang.String
     *@Description 北大青鸟火警主机事件解析
     */
    public static String pekingBuleBirdReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        String returnStr = Data.isFail() + "";
        if(StringUtils.isNotEmpty(msg) && msg.trim().startsWith("82") && msg.endsWith("83")){
            msg = msg.replaceAll(" ","");
            String[] msgArr = msg.replaceAll(" ","").split("8382");
            if(msgArr != null && msgArr.length > 0){
                for(String str : msgArr){
                    if(!str.startsWith("82")){
                        str = "82"+str;
                    }
                    if(!str.endsWith("83")){
                        str = str + "83";
                    }
                    if(str.length() == 52){
                        returnStr = analysisMsg(socketChannel, str, regId);
                    }
                }

            }
        }
        return returnStr;
    }

    private static String analysisMsg(SocketChannel socketChannel, String msg, String regId){
        String event = msg.substring(0,4);
        int eventType = 0;
        if("3830".equals(event)){
            eventType = 1;
        }else if("3831".equals(event)){
            eventType = 7;
        }
        if(eventType != 0){
            FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
            fireAlarmEvent.setEvent(eventType);
            String deviceNo = (StringUtils.hexStringToAlgorism(msg.substring(5,6)) * 16 + StringUtils.hexStringToAlgorism(msg.substring(7,8))) + "";
            String loopNo = (StringUtils.hexStringToAlgorism(msg.substring(9,10)) * 16 + StringUtils.hexStringToAlgorism(msg.substring(11,12))) + "";
            String detectorNo = (StringUtils.hexStringToAlgorism(msg.substring(13,14)) * 16 + StringUtils.hexStringToAlgorism(msg.substring(15,16))) + "";
            fireAlarmEvent.setPosition(deviceNo + "号控制器 " + loopNo + "回路 " + detectorNo + "号");
            String mtCode = (StringUtils.hexStringToAlgorism(msg.substring(17,18)) * 16 + StringUtils.hexStringToAlgorism(msg.substring(19,20))) + "";
            fireAlarmEvent.setMtCode(mtCode);
            fireAlarmEvent.setPositionCode(mtCode);
            fireAlarmEvent.setSourceData(msg);
            fireAlarmEvent.setFrom(regId);
            fireAlarmEvent.setHeartbeat(false);
            return sendHttpGet(socketChannel, msg, regId, fireAlarmEvent);
        }
        return Data.isFail() + "";
    }
}
