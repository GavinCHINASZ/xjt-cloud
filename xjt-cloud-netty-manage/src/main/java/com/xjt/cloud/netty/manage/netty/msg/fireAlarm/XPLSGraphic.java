package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;

import static com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive.sendHttpGet;

/**
 * @ClassName XPLSGraphic
 * @Author dwt
 * @Date 2020-07-14 14:08
 * @Description 新普利斯图文信息解析
 * @Version 1.0
 */
public class XPLSGraphic {

    public static String xplsGraphicReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        msg = msg.replaceAll(" ","").trim();
        byte[] bs = StringToByteArr.getByteArr(msg);
        String parseFormat = CharacterType.GBK;
        String msgInfo = new String(bs, parseFormat);
        msgInfo = msgInfo.replaceAll("\n","").replaceAll("\r","").trim();
        if(msgInfo.indexOf("Default") > -1 || msgInfo.indexOf("系统复位进行中") > -1){
            Integer eventType = 0;
            if(msgInfo.indexOf("Trouble") > -1){
                eventType = 7;
            }else if(msgInfo.indexOf("Supervisory") > -1){
                eventType = 2;
            }else if(msgInfo.indexOf("Alarm") > -1){
                eventType = 1;
            }else if(msgInfo.indexOf("系统复位进行中") > -1){
                eventType = 17;
            }
            String alarmDevice = null;
            String description = null;
            String loopName = null;
            String position = null;
            if(eventType != 17){
                position = msgInfo.substring(10,msgInfo.indexOf("星期")).trim();
                if(position.indexOf("-") > -1){
                    loopName = position.substring(position.lastIndexOf("-") - 6,position.lastIndexOf("-"));
                    if(!(loopName.indexOf("M") > -1 && loopName.indexOf("-") > -1)){
                        loopName = msgInfo.substring(msgInfo.indexOf("星期") - 9,msgInfo.indexOf("星期") - 1);
                        loopName = loopName.substring(0,loopName.indexOf("-"));
                    }
                    Boolean flag = StringUtils.isNumber(loopName.substring(0,1));
                    while (!flag){
                        if(loopName.length() <= 0){
                            break;
                        }
                        loopName = loopName.replace(loopName.substring(0,1),"").trim();
                        flag = StringUtils.isNumber(loopName.substring(0,1));
                    }
                    String[] strArr = loopName.split("M");
                    if(strArr.length == 2){
                        loopName = "P" + strArr[0] +"-M"+strArr[1];
                    }else{
                        loopName = null;
                    }
                }
                alarmDevice = msgInfo.substring(msgInfo.indexOf("星期") + 12,msgInfo.indexOf("星期") + 30);
                description = "故障";
                if(msgInfo.indexOf("错误应答") > -1 || msgInfo.indexOf("无应答") > -1){
                    description = "无应答";
                }else if(msgInfo.indexOf("脏") > -1){
                    description = "脏";
                }else if(msgInfo.indexOf("设备错误") > -1){
                    description = "设备错误";
                }
            }

            if(eventType != 0){
                FireAlarmEvent event = new FireAlarmEvent();
                event.setSourceData(msg);
                event.setBrand("新普利斯图文");
                event.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);//消息类型
                event.setFrom(regId);
                event.setHeartbeat(false);
                event.setPosition(position);
                event.setMtName(alarmDevice);
                event.setEvent(eventType);
                event.setLoopName(loopName);
                event.setDescription(description);
                return sendHttpGet(socketChannel, msg, regId, event);
            }
        }
        return Constants.DISCARD_CODE + "";
    }
}
