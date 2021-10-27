package com.xjt.cloud.netty.manage.netty.msg.waterSys;

import com.alibaba.fastjson.JSONArray;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.entity.WaterGageEvent;
import com.xjt.cloud.netty.manage.entity.WaterImmersionEvent;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName 麦克水压协议
 * @Author zhangzf
 * @Date 2019-10-15 9:53
 * @Description:vesda vsm 信息解析
 * @Version 1.0
 */
public class MKWaterSys extends BaseReceive {

    /***@MethodName: analysisWaterSys 解析麦克水系统协议
     * @Description:
     * @Param: [socketChannel, msg, from]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/9/17 16:47 
     **/
    public static String analysisWaterSys(SocketChannel socketChannel, String sourceMsg, String from) throws UnsupportedEncodingException {
        SysLog.info("水压表信息推送内容:" + sourceMsg);
        String msg = sourceMsg.replaceAll("\\[", "");
        msg = msg.replaceAll("\\]", "");
        String[] parameterArr = msg.split(";");
        int state = 0;
        Object obj = null;
        String sensorId = null;
        int length = parameterArr[0].length();
        if (length >= 8) {// 日常数据接收
            sensorId = parameterArr[0];// 传感器ID
            Integer monitorStatus = Integer.valueOf(parameterArr[1].substring(1,2));// 消息类型
            monitorStatus++;
            String value = parameterArr[2];// 压力值or水浸状态
            Double voltage = Double.valueOf(parameterArr[3]);// 电压
            Integer sign = Integer.valueOf(parameterArr[4]);// 信号强度
            Double electricity = 0d;
            if (voltage > 3.6d) {
                electricity = 100d;
            } else if (voltage >= 3.5d) {
                electricity = 70d;
            } else if (voltage >= 3.4d) {
                electricity = 30d;
            } else if (voltage >= 3.3d) {
                electricity = 10d;
            }
            SysLog.info("传感器ID:" + sensorId + "消息类型:" + monitorStatus + "压力值 :" + value + "电压:" + voltage + "信号强度:" + sign);
            if (sensorId.startsWith("B")) { // 水浸
                obj = new WaterImmersionEvent(0, sensorId, "WaterImmersion", "麦克新协议", sign, value, electricity, voltage, sourceMsg,2);
            } else { // 水压或液位设备
                obj = new WaterGageEvent(0, sensorId, "WaterGage", "麦克新协议", sign, monitorStatus, Double.valueOf(value), electricity, voltage, sourceMsg,2);

            }
        } else {// 设备上发设备信息
            String dataType = parameterArr[0];// 数据类型
            if ("A".equals(dataType)) {// 设备确信信息
                sensorId = parameterArr[1];// 传感器ID
                if (sensorId.startsWith("B")) { // 水浸
                    obj = new WaterImmersionEvent(1, sensorId, "WaterImmersion", "麦克新协议", sourceMsg,2);
                } else { // 水压或液位设备
                    obj = new WaterGageEvent(1, sensorId, "WaterGage", "麦克新协议", sourceMsg,2);
                }
            } else if ("R".equals(dataType)) {// 设备参数信息上传
                obj = checkData(parameterArr, sourceMsg);
            }
        }
        if(obj==null){
            return "[#end]";
        }
        String json = sendHttpGet(socketChannel, sourceMsg, from, obj);
        return json;
    }

    private static Object checkData(String[] parameterArr, String sourceMsg) {
        Object obj = null;
        String sensorId = parameterArr[1];
        String domainNameUrl = parameterArr[2].split(",")[0];//域名或IP
        String port = parameterArr[2].split(",")[1];//端口号
        String SIMCard = parameterArr[3];//SIM卡号
        Integer dataSendInterval = Integer.valueOf(parameterArr[6]);//心跳间隔
        if (!parameterArr[1].startsWith("B")) {
            Integer dataSamplingInterval = Integer.valueOf(parameterArr[5]);//心跳间隔
            Integer alarmMode = Integer.valueOf(parameterArr[7]);//告警方式
            Double upperLimit = Double.valueOf(parameterArr[8]);//上限
            Double lowerLimit = Double.valueOf(parameterArr[9]);//下限
            Double fluctuationAlarmValue = Double.valueOf(parameterArr[10]);//
            obj = new WaterGageEvent(2, sensorId, "WaterImmersion", "麦克新协议", sourceMsg, SIMCard, dataSamplingInterval, dataSendInterval, upperLimit, lowerLimit, alarmMode, fluctuationAlarmValue, domainNameUrl, port,2);
        } else {
            obj = new WaterImmersionEvent(2, sensorId, "WaterGage", "麦克新协议", sourceMsg, SIMCard, dataSendInterval, domainNameUrl, port,2);

        }
        return obj;
    }

    public static String oldAnalysisWaterSys(SocketChannel socketChannel, String msg, String regId)  {
        msg = msg.replaceAll("\r|\n*", "");
        msg = "[" + msg + "]";
        if (msg.indexOf("}{") > -1) {
            msg = msg.replaceAll("\\}\\{", "\\},\\{");
        }
        SysLog.info(msg);
        List<Map> list = JSONArray.parseArray(msg,Map.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date creTime = null;
        for (Map<String,String> map:list) {
            if (map.get("time").contains("2015")) {// 设备刚上线时间会是2015年，如是取自己本地时间
                creTime = new Date();
            } else {
                String time = map.get("time");
                try {
                    creTime = format.parse(time);
                }catch (Exception e ){
                    SysLog.error("时间格式错误");
                }
            }
            String from = map.get("from");
            if (from.trim().startsWith("E") || from.trim().startsWith("D")) {
                WaterGageEvent wa = new WaterGageEvent();
                wa.setSourceData(msg);
                wa.setMsgType("WaterGage");
                wa.setBrand("麦克");
                wa.setCreateTime(creTime);
                wa.setValue(Double.valueOf(map.get("value")));
                wa.setSensorId(from);
                wa.setSourceData(msg);
                wa.setDeviceType(1);
                Double voltage = Double.valueOf(map.get("voltage"));
                wa.setElectricity(voltage);
                if (map.get("unit") != null) {
                    wa.setUnit(map.get("unit"));
                } else {
                    if (from.startsWith("E")) {
                        wa.setUnit("m");
                    } else {
                        wa.setUnit("MPa");
                    }
                }
                sendHttpGet(socketChannel, msg, from, wa);
            } else if (from.trim().startsWith("B") || from.trim().startsWith("G")) {
                WaterImmersionEvent wa = new WaterImmersionEvent();
                wa.setSourceData(msg);
                wa.setBrand("麦克");
                wa.setMsgType("WaterImmersion");
                Double voltage = Double.valueOf(map.get("voltage"));
                wa.setElectricity(voltage);
                String value = map.get("value");
                wa.setSensorId(from);
                wa.setDeviceType(1);
                SysLog.error("标志位1：" + value);
                wa.setValue(value.substring(0, 2));
                sendHttpGet(socketChannel, msg, from, wa);
            }
        }
        format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        String date = format.format(new Date());
        StringBuffer result = new StringBuffer();
        result.append("{\"time\":\"" + date + "\",");
        result.append("\"interval\":\"10\",");
        result.append("\"state\":\"success\"}");
        return result.toString();
    }
}
