package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @ClassName NFS_6000
 * @Author Administrator
 * @Date 2019-06-11 17:08
 * @Description:火警主机6000型号
 * @Version 1.0
 */
public class NFS_6000 extends BaseReceive{

    //暂时保存分段消息
    private static String sectionMsg = null;
    /**
     *@Author: dwt
     *@Date: 2019-06-19 11:32
     *@Param:
     *@Return:
     *@Description:火警主机信息接收处理
     */
    public static String analysisNFS6000Info(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException{
        //SysLog.error("接收到的"+regId+"msg:"+msg);
        msg = msg.replace(" ", "").trim();
        int length = msg.length();
        if (msg.length() < 324 && sectionMsg != null) {
            msg = sectionMsg + msg;
        } else if (msg.trim().length() < 324 && sectionMsg == null) {
            sectionMsg = msg;
            return Constants.DISCARD_CODE + "";
        }
        //SysLog.error("msg.length:"+msg.length());
        sectionMsg = null;
        //SysLog.error("需要保存的msg:" + msg);
        if (msg == null || "".equals(msg)) {
            return Constants.DISCARD_CODE + "";
        }
        String[] strMsg = msg.split("0D0A03");
        String a = Constants.DISCARD_CODE + "";
        if(strMsg != null && strMsg.length > 0){
            for(String str : strMsg){
                if(str.length() < 350){
                    str = str+"0D0A03";
                    if(str.length() < 324){
                        continue;
                    }
                    a = nfsReceiveAnalysis(socketChannel, str, regId);
                }
            }
            return a;
        }
        return Constants.DISCARD_CODE +"";
    }
    /**
     *@Author: dwt
     *@Date: 2019-06-19 11:32
     *@Param:
     *@Return:
     *@Description:火警主机信息解析
     */
    public static String nfsReceiveAnalysis(SocketChannel socketChannel, String str, String regId) throws UnsupportedEncodingException {
        //if (strMsg != null && strMsg.length > 0) {
        //List<FireAlarmEvent> fireList = new ArrayList<FireAlarmEvent>();
        //for (String str : strMsg) {
        str = str.trim();
        str = str.replace(" ","")+"0D0A03";
        String parseFormat = CharacterType.GBK;
        //SysLog.error("拆分后火警主机"+regId+":"+str);
        byte[] bs = StringToByteArr.getByteArr(str);
        //SysLog.error("解析后的msg:" +regId+"-"+ new String(bs, parseFormat));
        String event = new String(Arrays.copyOfRange(bs, 0, 18), parseFormat);//DISABLED
        event = event.trim();
        int eventType = 0;
        //1:火警 2:监视 7:故障
        if("ACTIVE".indexOf(event)>-1||"FEEDBACK OFF".indexOf(event)>-1||"SUPERVISORY".indexOf(event)>-1){
            eventType = 2;
        }else if("FIRE ALARM".indexOf(event)>-1||"DISABLED".indexOf(event)>-1||"ADDRESS CONFLICT".indexOf(event)>-1){
            eventType = 1;
        }else if("OFFLINE".indexOf(event)>-1||"TROUBLE".indexOf(event)>-1){
            eventType = 7;
        }else if("NORMAL".indexOf(event) > -1){
            eventType = 6;
        }else if("RESET".indexOf(event) > -1){
            eventType = 17;
        }
        if (eventType == 0) {
            return "";
        }
        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
        fireAlarmEvent.setEvent(eventType);
        fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);//消息类型
        //SysLog.error("来源ID  regId:"+regId);
        fireAlarmEvent.setFrom(regId);//来源ID
        fireAlarmEvent.setBrand("诺蒂菲尔");//品牌
        String equipment = new String(Arrays.copyOfRange(bs, 16, 36),parseFormat);//光电感烟
        equipment = equipment.trim();
        if(StringUtils.isContainErrorCode(equipment.trim()) && eventType != 17){
            return "";
        }
        fireAlarmEvent.setMtName(equipment.trim());//设备名称
        /*String address = new String(Arrays.copyOfRange(bs, 36, 56),"GBK");//B1层3区
        String position = new String(Arrays.copyOfRange(bs, 56, 100),"GBK");//6号楼电梯前室*/
        String position = new String(Arrays.copyOfRange(bs, 38, 100),parseFormat);//B1层3区 6号楼电梯前室
        String[] arr1 = position.trim().split(" ");
        position = arr1[0] + " " +arr1[arr1.length-1];
        if(StringUtils.isContainErrorCode(position.trim())){
            return "";
        }
        String model = new String(Arrays.copyOfRange(bs, 148, 170),parseFormat);//I N004L09.003
        String[] arr = model.trim().split(" ");
        if(arr.length == 2){
            model = arr[1];
            if(model.startsWith("N")){
                String host = model.substring(1,4);
                fireAlarmEvent.setHostNumber(Integer.parseInt(host) + "");
                Integer n = Integer.parseInt(host);
                String mStr = model.substring(5,model.indexOf("."));
                Integer m = Integer.parseInt(mStr);
                String loopName = "N" + n + "-" +"M" + m;
                fireAlarmEvent.setLoopName(loopName);
            }
        }
        //fireAlarmEvent.setPositionCode(model.trim());//位置编码
        //SysLog.error("positionCode:"+positionCode);
        //fireAlarmEvent.setTime(DateUtils.getDateTimeString(new Date()));//时间
        fireAlarmEvent.setPosition(position.trim()+" "+model.trim());//位置
        fireAlarmEvent.setSourceData(str);//源数据
        return sendHttpGet(socketChannel, str, regId, fireAlarmEvent);

    }
}
