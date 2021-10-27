package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @ClassName Edward
 * @Author dwt
 * @Date 2019-12-23 17:13
 * @Version 1.0
 */
public class EdwardInteractive extends BaseReceive{

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
        msg = msg.replace(" ","");
        if(msg.length() >= 10){
            String returnStr;
            if(msg.length() == 10){
                returnStr = "81" + msg.substring(2,6);
                returnStr = returnStr + StringUtils.Make_Edward_CRC(returnStr);
               return returnStr;
            }
            if(msg.length() >= 34){
                String type = msg.substring(12,14);
                if("02".equals(type)){
                    msg.substring(14,16);
                    int num = Integer.parseInt(new BigInteger(msg.substring(14,16),16).toString());
                    String str;
                    String discNo;
                    String cardNo;
                    String deviceNo;
                    String eventType;
                    String deviceCode;
                    int a;
                    FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
                    fireAlarmEvent.setHeartbeat(false);
                    fireAlarmEvent.setSourceData(msg);
                    fireAlarmEvent.setFrom(regId);
                    fireAlarmEvent.setBrand("Edward");
                    fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
                    for(int j = 0; j < num; j++){
                        a = j * 12;
                        str = msg.substring(19 + a,20 + a);
                        if("1".equals(str) || "3".equals(str)){
                            eventType = msg.substring(20 + a,22 + a);
                            discNo = new BigInteger(msg.substring(22 + a,24 + a),16).toString();
                            if(discNo.length() == 1){
                                discNo = "0" + discNo;
                            }
                            cardNo = new BigInteger(msg.substring(24 + a,26 + a),16).toString();
                            if(cardNo.length() == 1){
                                cardNo = "0" +cardNo;
                            }
                            deviceNo = new BigInteger(msg.substring(24 + a,28 + a),16).toString();
                            if(deviceNo.length() == 1){
                                deviceNo = "000" + deviceNo;
                            }else if(deviceNo.length() == 2){
                                deviceNo = "00" + deviceNo;
                            }else if(deviceNo.length() == 3){
                                deviceNo = "0" + deviceNo;
                            }
                            deviceCode = discNo + cardNo + deviceNo;
                            fireAlarmEvent.setMtCode(deviceCode);
                            //sendHttpGet(socketChannel, msg, regId, fireAlarmEvent);
                        }
                    }
                }
                returnStr = "8100" + msg.substring(4,6);
                returnStr = returnStr + StringUtils.Make_Edward_CRC(returnStr);
                return returnStr;
            }
        }
        return Constants.DISCARD_CODE + "";
    }
}
