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
 * @ClassName NFSGraphic
 * @Author dwt
 * @Date 2020-06-15 13:43
 * @Description 诺蒂菲尔图文报警信息解析
 * @Version 1.0
 */
public class NFSGraphic {

    public static String nfsGraphicReceiveAnalysis(SocketChannel socketChannel, String msg, String regId) throws Exception {
        msg = msg.replaceAll(" ","").trim();
        byte[] bs = StringToByteArr.getByteArr(msg);
        String parseFormat = CharacterType.GBK;
        String temStr = new String(bs, parseFormat);
        if (StringUtils.isNotEmpty(temStr)){
            String[] msgs = temStr.split("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*");
            for (String msgInfo:msgs){
                if(msgInfo.indexOf("状态") > -1 && msgInfo.indexOf("时间") > -1){
                    msgInfo = msgInfo.replaceAll("\n","").replaceAll("\r","").replaceAll("\r\n","");
                    String eventStr = msgInfo.substring(msgInfo.indexOf("状态") + 3,msgInfo.indexOf("点类型")).trim();
                    String deviceType = msgInfo.substring(msgInfo.indexOf("点类型") + 4,msgInfo.indexOf("节点"));
                    String node;
                    String dot;
                    if(msgInfo.indexOf("节点节点") > -1){
                        node = msgInfo.substring(msgInfo.indexOf("节点") + 2,msgInfo.indexOf("节点") + 9).replace("节点","").trim();
                        dot = msgInfo.substring(msgInfo.indexOf("节点") + 10,msgInfo.indexOf("描述"));
                    }else{
                        node = msgInfo.substring(msgInfo.indexOf("节点") + 2 ,msgInfo.indexOf("节点") + 7).replace("节点","").trim();
                        dot = msgInfo.substring(msgInfo.indexOf("节点") + 8,msgInfo.indexOf("描述"));
                    }
                    node = node.replaceAll("点","");
                    String position = msgInfo.substring(msgInfo.lastIndexOf("描述") + 3,msgInfo.lastIndexOf("时间")).trim();
                    int eventType = 0;
                    int recoverType = 0;
                    if(eventStr.indexOf("故障恢复") > -1 || eventStr.indexOf("故障已解除") > -1){
                        recoverType = 7;
                    }else if("监管设备正常".indexOf(eventStr) > -1 || "火灾监视正常".indexOf(eventStr) > -1){
                        recoverType = 2;
                    }else if("火灾报警已回复".indexOf(eventStr) > -1){
                        recoverType = 1;
                    }else if(eventStr.indexOf("管理报警") > -1 || "火灾监视报警".indexOf(eventStr) > -1){
                        eventType = 2;
                    }else if(eventStr.indexOf("火警") > -1){
                        eventType = 1;
                    }else if(eventStr.indexOf("火灾故障 -") > -1 || eventStr.indexOf("故障状态") > -1){
                        eventType = 7;
                    }else if("火灾报警已回复".indexOf(eventStr) > -1){
                        recoverType = 1;
                    }else if("重置历史记录".indexOf(eventStr) > -1 || eventStr.indexOf("火灾故障 - 系统初始化") > -1){
                        eventType = 17;
                    }

                    //事件类型：  监管设备正常
                    //设备类型：跟踪监管/手动报警按钮
                    //位置：-5F消防水池高水位(N001L02M062)
                    if(eventType != 0 || recoverType != 0){
                        if(eventType == 0 && recoverType != 0){
                            eventType = 6;
                        }
                        FireAlarmEvent event = new FireAlarmEvent();
                        if(eventType == 7){
                            String description = "";
                            if(msgInfo.indexOf("NO RESPONSE") > -1){
                                description = "无应答";
                            }else if(msgInfo.indexOf("MISMATCHED") > -1 || msgInfo.indexOf("TWO DEVICES") > -1
                                    || msgInfo.indexOf("LOW THRESHOLD") > -1 || msgInfo.indexOf("OPEN") > -1
                                    || msgInfo.indexOf("SHORT") > -1 || msgInfo.indexOf("GROUND") > -1){
                                description = "设备错误";
                            }else if(msgInfo.indexOf("MAINTENANCE") > -1){
                                description = "脏";
                            }
                            event.setDescription(description);
                        }
                        if(StringUtils.isNotEmpty(node) && StringUtils.isNotEmpty(dot) && eventType != 17 && recoverType == 0){
                            Integer n = Integer.parseInt(node.replace("N",""));
                            Integer l = Integer.parseInt(dot.replace("点","").replace("\n","").substring(0,4).replace("M","").replace("L","").replace("D",""));
                            if(n != 0){
                                event.setLoopName("N" + n + "-M" + l);
                            }
                        }
                        event.setHeartbeat(false);
                        event.setFrom(regId);
                        event.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);//消息类型
                        event.setBrand("诺蒂菲尔图文");//品牌
                        event.setPosition(position + " " + node.trim() + dot.replace("点","").trim());
                        event.setSourceData(msgInfo);
                        event.setMtName(deviceType);
                        event.setEvent(eventType);
                        event.setRecoverEvent(recoverType);
                        sendHttpGet(socketChannel, msg, regId, event);
                    }
                }
            }
            return Constants.SUCCESS_CODE + "";
        }
        return Constants.DISCARD_CODE + "";
    }

}
