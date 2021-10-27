package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Author: dwt
 * @Date: 2019-05-6 10:20
 * @Description 诺蒂菲尔火警主机协议信息解析和数据封装
 */
public class NFS_3030 extends BaseReceive {
    //暂时保存分段消息
    private static String sectionMsg = null;

    public static String analysisNFSInfo(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException{
       // SysLog.error("火警主机3030"+regId+":"+msg);
        msg = msg.replace(" ", "");
        if (msg.trim().length() < 664 && sectionMsg != null) {
            msg = sectionMsg + msg;
        } else if (msg.trim().length() < 664 && sectionMsg == null) {
            sectionMsg = msg;
            return Constants.DISCARD_CODE + "";
        }
        //SysLog.error("msg.length:"+msg.length());
        sectionMsg = null;
        //SysLog.error("需要保存的msg:" + msg);
        if (msg == null || "".equals(msg)) {
            return Constants.DISCARD_CODE + "";
        }
        String[] strMsg = msg.split("000D000A000D000A");
        String a = Constants.DISCARD_CODE + "";
        if(strMsg != null && strMsg.length > 0){
            for(String str : strMsg){
                str = str + "000D000A000D000A";
                if (str.length() < 664) {
                    continue;
                }
                a = nfsReceiveAnalysis(socketChannel, str, regId);
            }
            return a;
        }
        return Constants.DISCARD_CODE +"";
    }

    /**
     * 功能描述: 诺帝菲儿信息解析
     *
     * @param socketChannel
     * @param str
     * @param regId
     * @return: java.lang.String
     * @auther: dwt
     * @date: 2019-05-10 10:42
     */
    public static String nfsReceiveAnalysis(SocketChannel socketChannel, String str, String regId) throws UnsupportedEncodingException{
            str = str.trim();
            byte[] bs = StringToByteArr.getByteArr(str);
            //SysLog.error("拆分后msg "+regId+":"+str);
            String parseFormat = CharacterType.UTF_16;
            //SysLog.error("解析后的msg"+" regId:" + new String(bs, parseFormat));
            String event = new String(Arrays.copyOfRange(bs, 0, 40), parseFormat);

            String mtName = new String(Arrays.copyOfRange(bs, 200, 240), parseFormat).trim();

            if(StringUtils.isEmpty(mtName)){
                return "";
            }
            FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
            event = event.trim();
            if(event.indexOf("恢复")>-1){
                fireAlarmEvent.setEvent(6);
            }else if (event.indexOf("报警") > -1) {
                fireAlarmEvent.setEvent(1);//火警
            } else if (event.indexOf("故障") > -1) {
                fireAlarmEvent.setEvent(7);//故障
            } else if (event.indexOf("监控") > -1 || event.indexOf("监视") > -1) {
                fireAlarmEvent.setEvent(2);//监视
            } else if(event.indexOf("系统复位") > -1){
                fireAlarmEvent.setEvent(17);//系统复位
            }
            String position = new String(Arrays.copyOfRange(bs, 80, 120), parseFormat).trim();
            //SysLog.error("截取到火警类型:" + "event:" + event.trim() + "fireAlarmEvent.getEvent():" + fireAlarmEvent.getEvent());
            if (fireAlarmEvent.getEvent() != 0) {
                fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);//消息类型
                //SysLog.error("来源ID  regId:"+regId);
                fireAlarmEvent.setFrom(regId);//来源ID
                fireAlarmEvent.setBrand("诺蒂菲尔");//品牌
                //SysLog.error("mtName:"+mtName.trim());
                fireAlarmEvent.setMtName(mtName.trim());//设备名称
                String positionCode = new String(Arrays.copyOfRange(bs, 298, 332), parseFormat);
                fireAlarmEvent.setPositionCode(positionCode.trim());//位置编码
                if(positionCode.startsWith("N")){
                    String hostNumber = positionCode.substring(1,4);
                    fireAlarmEvent.setHostNumber(Integer.parseInt(hostNumber) + "");
                }
                fireAlarmEvent.setPosition(position.trim()+" "+positionCode.trim());//位置
                //fireAlarmEvent.setTime(DateUtils.getDateTimeString(new Date()));//时间
                fireAlarmEvent.setSourceData(str);//源数据
                return sendHttpGet(socketChannel, str, regId, fireAlarmEvent);
                //fireList.add(fireAlarmEvent);
            }
        return Constants.DISCARD_CODE + "";
    }
}
