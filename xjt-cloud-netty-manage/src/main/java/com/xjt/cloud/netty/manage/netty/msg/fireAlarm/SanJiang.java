package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName SanJiang
 * @Author dwt
 * @Date 2020-06-11 16:42
 * @Version 1.0
 */
public class SanJiang {

    public static String sanJiangReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        msg = msg.trim().replaceAll(" ","");
        byte[] bs = StringToByteArr.getByteArr(msg);
        //SysLog.error("拆分后msg "+regId+":"+str);
        String parseFormat = CharacterType.GBK;
        String msgInfo = new String(bs, parseFormat);
        SysLog.info("解析后的msg"+" regId:" + msgInfo);
        String address = msgInfo.substring(msgInfo.indexOf("&") + 1,msgInfo.indexOf("i"));
        String eventMsg = msgInfo.substring(msgInfo.indexOf("i") + 2);
        int eventType = 0;
        if(eventMsg.indexOf("&火警") > -1){
            eventType = 1;
        }else if(eventMsg.indexOf("&故障消除") > -1){
        }else if(eventMsg.indexOf("&故障") > -1){
            eventType = 6;
        }
        if(eventType != 0 && eventType == 1){
            String[] strArr = address.split("  ");
            if(strArr != null && strArr.length >= 2){
                String deviceName = strArr[1];
                String position = strArr[0];
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String str = "@ .20-05-21 12:21:12  address: 032 &2层楼梯间走道  手报. i  &火警 .001 i";
        String address = str.substring(str.indexOf("&")+1,str.indexOf("i") - 2);
        System.out.println(address);
        String eventType = str.substring(str.indexOf("i") + 2);
        if(eventType.indexOf("&火警") > -1){
            String[] strArr = address.split("  ");
        }else if(eventType.indexOf("&故障消除") > -1){
        }else if(eventType.indexOf("&故障") > -1){

        }
    }

}
