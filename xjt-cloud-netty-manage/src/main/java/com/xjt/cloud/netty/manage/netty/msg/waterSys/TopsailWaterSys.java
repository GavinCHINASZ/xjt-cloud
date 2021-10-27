package com.xjt.cloud.netty.manage.netty.msg.waterSys;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.commons.utils.TopSailMsgUtils;
import com.xjt.cloud.netty.manage.entity.HydrantEvent;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 西安 拓普索尔
 *
 * @author huanggc
 * @date 2020/10/09
 */
public class TopsailWaterSys extends BaseReceive {

    /**
     * 拓普索尔 消火栓
     *
     * @author huanggc
     * @param socketChannel socketChannel
     * @param msg 消息
     * @param regId regId
     * @return String
     */
    public static String topsailHydrant(SocketChannel socketChannel, String msg, String regId){
        SysLog.info("拓普索尔消火栓协议信息" + msg);

        JSONObject object = TopSailMsgUtils.comParse(msg);
        if (object.get("eventType") != null){
            String sensorNo = object.get("sensorNo").toString();
            // 心跳时间
            //String endHeartbeatTime = object.get("endHeartbeatTime").toString();
            Double electricQuantity = Double.parseDouble(object.get("electricQuantity").toString());

            // 信息强度状态signalValue 1正常　2信号弱
            //int signalValue = Integer.parseInt(object.get("signalValue").toString());

            // 0 ：表示数据正常； 1 ：表示数据阈值下限告警； 2 ：表示数据阈值上限告警， 3 ：表示设备故障； 4 ：表示数据动态变化阈值告警； 5 ：表示碰撞告警；
            // 6 ：表示倾斜告警；7 ：表示水流告警； 8 ：表示进水告警； 9 ：表示低电量告警。
            int eventType = Integer.parseInt(object.get("eventType").toString());

            // 1 ：表示压力 MPa ， 2 ：表示压力 Bar ， 3 ：表示压力 KPa ，
            // 4 ：表示液位 M ； 5 ：表示温度℃； 6 ：表示流量 m ³ /h ； 7 ：表示角度°
            String unit = object.get("unit").toString();

            // 0表示无小数点， 1表示小数点后有 1 位有效数字， 2表示小数点后有 2 位有效数字， 3表示小数点后有 3 位有效数字； 4表示小数点后有 4 位有效数字。
            Double value = Double.parseDouble(object.get("presentValue").toString());

            String res = "";
            HydrantEvent he = new HydrantEvent();
            he.setDeviceType(1);
            he.setDataType(0);
            he.setSourceData(msg);
            he.setValue(value);
            he.setUnit(unit);
            he.setFrom(sensorNo);
            he.setElectricity(electricQuantity);

            // 撞击报警 0、正常 1、撞击报警 2、撞击传感器故障
            String toHit;
            switch (eventType){
                case 5:
                    toHit = "1";
                    break;
                case 3:
                    toHit = "2";
                    break;
                default:
                    toHit = "0";
            }
            he.setToHit(toHit);

            //0、正常  1、漏水报警  2-7 取水报警  8、传感器报警
            String leakage;
            switch (eventType){
                case 8:
                    leakage = "1";
                    break;
                case 3:
                    leakage = "8";
                    break;
                default:
                    leakage = "0";
            }
            he.setLeakage(leakage);

            //0、正常  1、开盖报警
            he.setOpenCover("0");
            he.setMsgType("Hydrant");
            he.setBrand("拓普索尔");

            String rep = sendHttpGet(socketChannel, msg, sensorNo, he);
            SysLog.info("拓普索尔 消火栓返回信息" + rep);

            if (StringUtils.isNotEmpty(rep)){
                return rep;
            }else if(StringUtils.isNotEmpty(res)){
                return res;
            }
        }
        //{"yyyy-MM-dd,HH:mm:ss":"OK"}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        return format.format(new Date());
    }

}