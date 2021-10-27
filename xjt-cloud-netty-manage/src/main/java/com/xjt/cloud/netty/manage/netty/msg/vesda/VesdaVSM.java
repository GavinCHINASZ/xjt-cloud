package com.xjt.cloud.netty.manage.netty.msg.vesda;

import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.VesdaVSMEvent;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName VesdaVSM
 * @Author dwt
 * @Date 2019-06-26 9:53
 * @Description:vesda vsm 信息解析
 * @Version 1.0
 */
public class VesdaVSM extends BaseReceive {
    //暂时保存分段消息
    private static Map<String,String> map = new HashMap<>();
    private static String  sectionMsg ;
    /**
     *@Author: dwt
     *@Date: 2019-07-17 10:29
     *@Param: io.netty.channel.socket,String msg,String regId
     *@Return:java.lang.String
     *@Description:判断信息是否是多条组合，拆分判断每条信息是否符合协议标准
     */
    public static String analysisVSM(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        //SysLog.error("vesda vsm 信息解析"+regId+":"+msg);
        //SysLog.error("需要保存的msg:" + msg);
        SysLog.info("regId:"+regId +"收到的原始数据："+msg);
        if (msg == null || "".equals(msg)) {
            return Constants.DISCARD_CODE + "";
        }
        msg = msg.replace(" ", "");
        String[] strMsg = msg.split("001B55310D0A");
        String a = Constants.DISCARD_CODE + "";
        if(strMsg != null && strMsg.length > 0){
            for(String str : strMsg){
                if (str.length() < 200) {
                    sectionMsg = map.get(regId);
                    if(StringUtils.isNotEmpty(sectionMsg)){
                        sectionMsg = sectionMsg + str;
                        if(sectionMsg.length() < 200){
                            synchronized (map){
                                map.put(regId,str);
                            }
                            continue;
                        }
                    }else{
                        synchronized (map){
                            map.put(regId,str);
                        }
                        continue;
                    }
                }
                a = nfsReceiveAnalysis(socketChannel, str, regId);
                synchronized (map){
                    map.put(regId,"");
                }
            }
            return a;
        }
        return Constants.DISCARD_CODE +"";
    }

    /**
     * 功能描述: vesda 图文打印输出消息解析
     *
     * @param socketChannel
     * @param str
     * @param regId
     * @return: java.lang.String
     * @auther: dwt
     * @date: 2019-05-10 10:42
     */
    public static String nfsReceiveAnalysis(SocketChannel socketChannel, String str, String regId) throws UnsupportedEncodingException{
        byte[] bs = StringToByteArr.getByteArr(str);
        String parseFormat = CharacterType.GBK;
        String msg = new String(bs, parseFormat);
        SysLog.info("解析后的msg:"+msg+"regId:"+regId);
        msg = msg.trim();
        //去掉M事件,M事件是已经上报过的重复事件， msg.substring(0, 6).indexOf("X") > -1 ||
        if( msg.substring(0,6).indexOf("M") > -1){
            return Constants.DISCARD_CODE + "";
        }
        int vesda = 0;
        String dateStr = "";
        boolean flag = true;

        if(msg.indexOf("VESDA") > -1){
            vesda = msg.indexOf("VESDA");
        }else if(msg.indexOf("LaserSCANNER的区段") > -1){
            vesda = msg.indexOf("LaserSCANNER的区段");
        }
        if(vesda <= 0){
            return Constants.DISCARD_CODE + "";
        }

        if( msg.substring(0,6).indexOf("X") > -1){
            dateStr = msg.substring(1,vesda).trim();
        }else{
            dateStr = msg.substring(0,vesda).trim();
        }

        flag = StringUtils.isValidDate(dateStr,"yyyy-MM-dd HH:mm:ss");
        if(flag == false){
            flag = StringUtils.isValidDate(dateStr,"yyyy/MM/dd HH:mm:ss");
        }
        if(!flag){
            return Constants.DISCARD_CODE + "";
        }
        String detectorType = "";
        int vls = 0;
        int addressIndex = 56;
        if(msg.indexOf("VLS") > -1){
            detectorType = "VESDA VLS";
            vls = msg.indexOf("VLS")+3;
        }else if(msg.indexOf("VLC") > -1){
            detectorType = "VESDA VLC";
            vls = msg.indexOf("VLC")+3;
        }else if(msg.indexOf("VLP") > -1){
            detectorType = "VESDA VLP";
            vls = msg.indexOf("VLP")+3;
        }else if(msg.indexOf("滑窗HLI") > -1){
            detectorType = "滑窗HLI";
            vls = msg.indexOf("滑窗HLI")+7;
        }else if(msg.indexOf("LaserSCANNER的区段") > -1){
            detectorType = "VESDA VLS";
            vls = msg.indexOf("LaserSCANNER的区段")+16;
        }

        if(vls == 0 || "".equals(detectorType)){
            return Constants.DISCARD_CODE + "";
        }

        int recordType = 0;
        if(msg.substring(0, 6).indexOf("X") > -1){
            recordType = 1;
        }

        //事件类型  :0:故障，1：警告，2：行动，3：火警1，4：火警2
        int eventType = -1;
        int faultIndex = 0;
        if(msg.indexOf("异常") > -1){
            eventType = 0;
            faultIndex = msg.indexOf("异常");
        }else if(msg.indexOf("预警报") > -1){
            if(msg.indexOf("行动") > -1){
                eventType = 2;
            }else{
                eventType = 1;
            }
            faultIndex = msg.indexOf("预警报");
        }else if(msg.indexOf("火警１") > -1){
            eventType = 3;
            faultIndex = msg.indexOf("警告");
        }else if(msg.indexOf("火警２") > -1 || msg.indexOf("火警警报") > -1){
            eventType = 4;
            faultIndex = msg.indexOf("警告");
        }

        if(eventType==-1){
            return Constants.DISCARD_CODE + "";
        }
        VesdaVSMEvent vesdaVSMEvent = new VesdaVSMEvent();
        //截取回路，探测器id和扇区
        String loopAndSlaveId = msg.substring(vls,addressIndex).trim();
        String[] arr = loopAndSlaveId.split("\\.");
        //回路名称
        String loopName = "";
        //探测器Id
        int slaveId = 0;

        //根据数组长度判断扇区是否存在
        if(arr.length==2){
            loopName = arr[0];
            slaveId = Integer.parseInt(arr[1].substring(0, 3));
        }
        if(arr.length==3){
            loopName = arr[0];
            slaveId = Integer.parseInt(arr[1]);
            //扇区  Integer.parseInt(arr[2].substring(0,2));
            String sector = arr[2];
            vesdaVSMEvent.setSector(sector);//VLS 探测器扇区
        }
        if(StringUtils.isEmpty(loopName)||StringUtils.isEmpty(slaveId+"")){
            return Constants.DISCARD_CODE + "";
        }
        vesdaVSMEvent.setLoopName(loopName.trim());//回路名称
        vesdaVSMEvent.setSlaveId(slaveId);//探测器ID

        if(eventType == 0){
            //截取故障号
            String faultNo = msg.substring(faultIndex+2,faultIndex+13);
            vesdaVSMEvent.setFaultNo(Integer.parseInt(faultNo.trim()));
            //截取故障描述
            String faultDescribe = msg.substring(faultIndex+13);
            vesdaVSMEvent.setFaultDescribe(faultDescribe.trim());
            SysLog.info("!!!!!!!!!!!!!!!"+faultDescribe);
        }
/*        else{
            //火警事件，将扇区号放入故障描述字段
            String faultDescribe = "扇区"+sector;
            vesdaVSMEvent.setFaultDescribe(faultDescribe.trim());
            SysLog.error("!!!!!!!!!!!!!!!"+faultDescribe);
        }*/

        //截取探测器地址
        String address = msg.substring(addressIndex,faultIndex);
        vesdaVSMEvent.setAddress(address.trim());
        vesdaVSMEvent.setDetectorType(detectorType);//探测器类型
        vesdaVSMEvent.setEventType(eventType);
        vesdaVSMEvent.setRecordType(recordType);

        vesdaVSMEvent.setMsgType(Constants.VESDA_VSM_MSG_TYPE);
        vesdaVSMEvent.setHeartbeat(false);
        vesdaVSMEvent.setFrom(regId);
        vesdaVSMEvent.setEventTime(dateStr);
        vesdaVSMEvent.setSourceData(msg);
        SysLog.info("regId:"+regId +"发送事件信息！！！！！！！！！！！！！");
        return sendHttpGet(socketChannel, str, regId, vesdaVSMEvent);
    }
}
