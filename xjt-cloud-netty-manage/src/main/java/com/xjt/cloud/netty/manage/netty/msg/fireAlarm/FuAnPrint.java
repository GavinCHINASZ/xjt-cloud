package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName FuAnPrint
 * @Author dwt
 * @Date 2020-08-27 9:25
 * @Version 1.0
 */
public class FuAnPrint extends BaseReceive{

    /**
     *@Author: dwt
     *@Date: 2020-08-27 14:07
     *@Description 赋安火警主机接打印口信息解析
     */
    public static String fuAnPrintReceiveAnalysis(SocketChannel socketChannel, String msg, String regId){
        if(StringUtils.isNotEmpty(msg)){
            byte[] bs = StringToByteArr.getByteArr(msg);
            String parseFormat = CharacterType.GBK;
            String msgInfo = null;
            try {
                msgInfo = new String(bs, parseFormat).trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String[] arrStr = msgInfo.split("\r\n");
            SysLog.info(msgInfo);
            if(arrStr != null){
                FireAlarmEvent event = new FireAlarmEvent();
                event.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
                event.setFrom(regId);
                event.setSourceData(msg);
                event.setBrand("赋安打印口输出");
                event.setEvent(0);
                if(msgInfo.indexOf("复位") > -1){
                    event.setMtName("控制器");
                    event.setEvent(17);
                    String time = arrStr[0].substring(arrStr[0].indexOf("时间") + 3);
                    event.setTime(time);
                }else if(arrStr.length >= 4){
                    String position = arrStr[2];
                    String[] eventMt = arrStr[3].split("!");
                    String mtName = "";
                    String eventType = "";
                    if(eventMt != null && eventMt.length >= 2){
                        mtName = eventMt[1].trim();
                        eventType = eventMt[0].trim();
                    }
                    if("火警".indexOf(eventType) > -1){
                        event.setEvent(1);
                    }else if("地址丢失".indexOf(eventType) > -1){
                        event.setEvent(7);
                    }
                    event.setMtName(mtName);
                    event.setPosition(position);
                }
                if(event.getEvent() > 0){
                    return sendHttpGet(socketChannel, msg, regId, event);
                }
            }

        }
        return Constants.DISCARD_CODE + "";
    }
}
