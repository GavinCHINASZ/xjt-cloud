package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/9 0009 17:05
 * @Description:新普利期信息解析
 */
public class XPLS extends BaseReceive{

    /**
     *
     * 功能描述: 新普利期信息解析
     *
     * @param socketChannel
     * @param msg
     * @param regId
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019-04-26 17:42
     */
    public static String receiveAnalysisMsgs(SocketChannel socketChannel, String msg, String regId){
        msg = msg.replaceAll("\r","");
        String[] msgs = msg.split("\n");
        String res = Constants.DISCARD_CODE + "";
        if (msgs.length >=1){
            for (String s : msgs) {
                if (StringUtils.isNotEmpty(s)) {
                    res = receiveAnalysis(socketChannel, s.trim(), regId);
                }
            }
        }
        return res;
    }

    /**
     *
     * 功能描述: 新普利期信息解析
     *
     * @param socketChannel
     * @param msg
     * @param regId
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019-04-26 17:42
     */
    private static String receiveAnalysis(SocketChannel socketChannel, String msg, String regId){
        String[] strs = msg.split(" ");
        if (strs != null && strs.length >= 2) {
            String str = strs[2];
            //事件类型 F   火警,T   故障,C   控制,S   监视,U   Utility,F，T，C，S为常用
           if (str.length() == 3 && ConstantsClient.XPLS_NEED_EVENT_TYPE.indexOf(str.charAt(0)) != -1) {
               //1表示on或Abnorma 异常, 0表示off或Normal 恢复
               String type = strs[2]; // 火警类型
               int event = 0;
               int recoverEvent = 0;
               if(ConstantsClient.XPLS_SWITCH_STATE.indexOf(str.charAt(1)) != -1 && ConstantsClient.XPLS_EVENT_STATE.indexOf(str.charAt(2)) != -1){
                   if (type.startsWith("F")) {
                       event = 1;// 火警
                   } else if (type.startsWith("T")) {
                       event = 7;// 故障
                   } else if (type.startsWith("S")) {
                       event = 2;// 监管事件
                   }
                }else if(str.indexOf("0") > 0){
                   event = 6;//恢复事件
                   if (type.startsWith("F")) {
                       recoverEvent = 1;// 火警
                   } else if (type.startsWith("T")) {
                       recoverEvent = 7;// 故障
                   } else if (type.startsWith("S")) {
                       recoverEvent = 2;// 监管事件
                   }
               }
               if(event == 0){
                   return Constants.FAIL_CODE + "";
               }
               FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
               fireAlarmEvent.setEvent(event);
               fireAlarmEvent.setRecoverEvent(recoverEvent);
               fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);//消息类型
               fireAlarmEvent.setFrom(regId);//来源ID
               //有的产品要通过接口去后台取
               fireAlarmEvent.setBrand("新普利期");//品牌
               //fireAlarmEvent.setMtName();//设备名称
                /*//组装时间
                str = strs[0];
                fireAlarmEvent.setTime("20" + str.substring(12,14) + "-" + str.substring(10,12) + "-" + str.substring(8,10) + " " +
                        str.substring(0,2) + ":" + str.substring(2,4) + ":" + str.substring(4,6));*/
                strs = strs[1].split(":");
                String positionCode;
               if(strs[0].startsWith("P") || strs[0].indexOf("-") > -1){
                   return Constants.FAIL_CODE + "";
               }
                if (strs.length >= 2){
                    //fireAlarmEvent.setHostNumber(strs[0]);//火警主机号
                    fireAlarmEvent.setPosition(strs[0]);
                    //fireAlarmEvent.setPosition(strs[1]);//报警位置
                    positionCode = strs[1];
                    String loopName = "P" + strs[0] + "-" + strs[1].substring(0,strs[1].indexOf("-"));
                    fireAlarmEvent.setLoopName(loopName);
                }else{
                    positionCode = strs[0];//报警编号
                }
                if (positionCode.endsWith("-0")){
                    positionCode = positionCode.substring(0,positionCode.length() - 2);
                }
                fireAlarmEvent.setPositionCode(positionCode);//报警编号
                fireAlarmEvent.setSourceData(msg);//
                return  sendHttpGet(socketChannel,msg,regId,fireAlarmEvent);
            }
        }
        return Constants.DISCARD_CODE + "";
    }
}
