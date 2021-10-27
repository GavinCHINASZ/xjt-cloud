package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName EdwardPrint
 * @Author dwt
 * @Date 2019-12-27 13:44
 * @Description 爱德华火警主机打印信息解析
 * @Version 1.0
 */
public class EdwardPrint extends BaseReceive {
    /**
     * 功能描述: 爱德华火警主机信息解析
     *
     * @param socketChannel
     * @param msg
     * @param regId
     * @return: java.lang.String
     * @auther: dwt
     * @date: 2019-05-10 10:42
     */
    public static String nfsReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        String sourceData = msg;
        msg = msg.trim().replace("0D0A","");
        String parseFormat = CharacterType.GBK;
        byte[] bs = StringToByteArr.getByteArr(msg);
        String eventMsg = new String(bs, parseFormat);
        SysLog.info("爱德华火警主机事件解析：" + regId + ":"  + regId + "!!!!!!!!!!!eventMsg:" + eventMsg);
        String event = eventMsg.substring(0,2);
        int eventType = 0;
        if("火警".indexOf(event) > -1 || "手报".indexOf(event) > -1){
            eventType = 1;
        }else if("故障".indexOf(event) > -1 || "交流".indexOf(event) > -1 || "状态".indexOf(event) > -1){
            eventType = 7;
        }/*else if("状态".indexOf(event) > -1 || "开关".indexOf(event) > -1){
            eventType = 2;
        }else if("故障".indexOf(event) > -1 || "交流".indexOf(event) > -1){
            eventType = 7;
        }*/else if("恢复".indexOf(event) > -1){
            eventType = 6;
        }
        if(eventType != 0){
            String discNo = eventMsg.substring(eventMsg.indexOf("控制盘:") + 4,eventMsg.indexOf("卡")).trim();
            String cardNo = eventMsg.substring(eventMsg.indexOf("卡:") + 2,eventMsg.indexOf("器件")).trim();
            String deviceNo = eventMsg.substring(eventMsg.indexOf("器件:") + 3,eventMsg.indexOf("器件:") + 7).trim();
            String device = eventMsg.substring(eventMsg.indexOf("控制盘"),eventMsg.indexOf("器件") + 7).trim();
            String position = eventMsg.substring(eventMsg.indexOf("器件") + 7).trim();
            String time = eventMsg.substring(eventMsg.indexOf("::") + 2,eventMsg.indexOf("控制盘"));
            FireAlarmEvent fireEvent = new FireAlarmEvent();
            fireEvent.setHeartbeat(false);
            fireEvent.setSourceData(sourceData);
            fireEvent.setFrom(regId);
            fireEvent.setBrand("Edward");
            fireEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
            fireEvent.setEvent(eventType);
            fireEvent.setTime(time);
            fireEvent.setPosition(position);
            fireEvent.setMtName(device);
            fireEvent.setMtCode(discNo + cardNo + deviceNo);
            return sendHttpGet(socketChannel, msg, regId, fireEvent);
        }
        return Constants.DISCARD_CODE + "";
    }
}
