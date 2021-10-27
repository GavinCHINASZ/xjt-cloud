package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

/**
 * @ClassName AnShe
 * @Date 2020-03-30 14:42
 * @Description 安舍火警火警主机协议解析
 * @Version 1.0
 */
public class AnShe extends BaseReceive{

    public static String anSheReceiveAnalysis(SocketChannel socketChannel, String msg, String regId){
        String[] hexArr = msg.trim().split(" ");
        int event = StringUtils.hexStringToAlgorism(hexArr[4]);
        int eventType = 0;
        if(event==2 || event==3){  //火警事件
            eventType = 1;
        }else if((event>3&&event<8)|| event==10 || event==11 || event==17 || event==92){ //故障事件
            eventType = 7;
        }else if(event>=54 && event <=57){   //恢复事件
            eventType = 6;
        }/*else if(event==8 || event==52 || event== 80){   //复位事件
			eventType = 17;
			if(event == 8){
				pushEvent.setTag("复位");
			}else if(event == 52){
				pushEvent.setTag("火警复位");
			}else if(event == 80){
				pushEvent.setTag("控制机复位");
			}
        }*/


        /*int month = StringUtils.hexStringToAlgorism(hexArr[10]);
        int day = StringUtils.hexStringToAlgorism(hexArr[11]);
        int hour = StringUtils.hexStringToAlgorism(hexArr[12]);
        int minute = StringUtils.hexStringToAlgorism(hexArr[13]);
        int second = StringUtils.hexStringToAlgorism(hexArr[14]);*/
        if(eventType != 0){
            int type = StringUtils.hexStringToAlgorism(hexArr[5]);;
            String  address = hexStringToAlgorismTome(hexArr[6]) + "" + hexStringToAlgorismTome(hexArr[7]);//区号(userCode)
            String slewDevice = hexStringToAlgorismTome(hexArr[8]) + "" + hexStringToAlgorismTome(hexArr[9]);//位号(controllerNumber)
            FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
            fireAlarmEvent.setBrand("安舍");
            fireAlarmEvent.setEvent(eventType);
            fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
            fireAlarmEvent.setFrom(regId);
            fireAlarmEvent.setSourceData(msg);
            fireAlarmEvent.setHeartbeat(false);
            fireAlarmEvent.setMtCode(address);
            fireAlarmEvent.setPositionCode(slewDevice);
            sendHttpGet(socketChannel, msg, regId, fireAlarmEvent);

        }


        String result = "9A10"+hexArr[2]+hexArr[3]+"630000000000000000";
        int sum =0;
        for(int i=0;i<result.length();i++){
            int a =StringUtils.hexStringToAlgorism(result.substring(i,i+2));
            sum+=a;
            i++;
        }
        int start = sum/100;
        int end = sum%100;
        result += intToHex(start)+intToHex(end)+"9C";
        return result;
    }

    private static String intToHex(int n) {
        int tab = n;
        StringBuffer s = new StringBuffer();
        String a;
        char[] b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        if(tab ==0 ){
            a = "0";
        }else{
            a = s.reverse().toString();
        }
        if(a.length()==1 ){
            a="0"+a;
        }
        return a;
    }

    /**
     * 十六进制字符串装十进制前面补0
     *
     * @param hex
     *            十六进制字符串
     * @return 十进制数值
     */
    public static String  hexStringToAlgorismTome(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        if((result+"").length()==1){
            return "0"+result;
        }
        return result+"";
    }

}
