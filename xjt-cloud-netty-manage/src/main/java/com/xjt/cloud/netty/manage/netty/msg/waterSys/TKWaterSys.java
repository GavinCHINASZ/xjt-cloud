package com.xjt.cloud.netty.manage.netty.msg.waterSys;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.entity.HydrantEvent;
import com.xjt.cloud.netty.manage.entity.WaterGageEvent;
import com.xjt.cloud.netty.manage.entity.WaterImmersionEvent;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName 泰科水系统
 * @Author zhangzf
 * @Date 2019-10-16 9:53
 * @Description:vesda vsm 信息解析
 * @Version 1.0
 */
public class TKWaterSys extends BaseReceive {

    /***@MethodName: analysisWaterSys 解析泰科系统协议
     * @Description:
     * @Param: [socketChannel, msg, from]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/9/17 16:47
     **/
    public static String analysisWaterSys(SocketChannel socketChannel, String sourceMsg, String from) throws UnsupportedEncodingException {
        SysLog.info("泰科水压表信息推送内容:" + sourceMsg);
        String information = sourceMsg.replace("}", "");
        information = information.replace("{", "");
        StringBuffer res = new StringBuffer();
        if (information.indexOf("-") > -1) {
            WaterGageEvent wa = new WaterGageEvent();
            wa.setMsgType("WaterGage");
            wa.setDeviceType(1);
            wa.setBrand("深圳泰科");
            wa.setSourceData(sourceMsg);
            // {D00-2B0006963106758:0750KPA:3.7V} 水压
            String[] arr = information.split("-");
            String[] arr1 = arr[1].split(":");
            wa.setSensorId(arr[0].trim() + "" + arr1[0].trim());
            wa.setSimCard(arr1[0].trim());
            String waterGageStr = "0";
            if (arr1[1].indexOf("KPA") > -1) {
                waterGageStr = arr1[1].replace("KPA", "");
            } else if ((arr1[1].indexOf("MPA") > -1)) {
                waterGageStr = arr1[1].replace("MPA", "");
            }
            Double waterGage = 0d;
            if (arr[0].trim().startsWith("E")) {//液位设备
                waterGage = Double.valueOf(waterGageStr);
                //获取监测值 /10
                BigDecimal bg = new BigDecimal(waterGage / 10);
                waterGage = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                wa.setUnit("m");
            } else {//水压设备
                waterGage = Double.valueOf(waterGageStr);
                //获取监测值 /1000
                BigDecimal bg = new BigDecimal(waterGage / 1000);
                waterGage = bg.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
                wa.setUnit("MPa");
            }
            wa.setValue(waterGage);
            wa.setCreateTime(new Date());
            String voltageStr = arr1[2].replace("V", "");
            Double voltage = Double.valueOf(voltageStr);
            wa.setVoltage(voltage);
            if (voltage >= 3.9d) {
                wa.setElectricity(100d);
            } else if (voltage <= 3.3d) {
                wa.setElectricity(0d);
            } else {
                BigDecimal bg = new BigDecimal(voltage - 3.3d);
                voltage = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                Double electricity = voltage * 166;
                wa.setElectricity(electricity);
            }
            res.append("{A:" + arr1[1] + ":" + arr1[2] + " end}") ;
            sendHttpGet(socketChannel, sourceMsg, from, wa);
        } else {
            WaterImmersionEvent wi = new  WaterImmersionEvent();
            wi.setMsgType("WaterImmersion");
            wi.setBrand("深圳泰科");
            wi.setDeviceType(1);
            wi.setSourceData(sourceMsg);
            String checkStr = information.trim().substring(0, information.length() - 2);
            String hex = StringUtils.strTo16(checkStr);
            String check = information.substring(information.length() - 2, information.length());
            String[] arr = information.split(":");
            String mtNumber = arr[0];
            String newCheck = StringUtils.makeChecksum(hex);
            if (!check.equalsIgnoreCase(newCheck)) {
                SysLog.error("校验码错误");
                return "";
            }
            String[] arr1 = arr[1].split(":");
            wi.setSensorId(arr[0].trim() + "" + arr1[0].trim());
            wi.setSimCard(arr1[0].trim());
            // {G4291618C1310069:4.2V:211E}老协议
            // {G4291618C1310069:p99%:211E}新协议
            String voltageStr = arr[1].replace("p", "");
            voltageStr = voltageStr.replace("P", "");
            voltageStr = voltageStr.replace("%", "");
            voltageStr = voltageStr.replace("V", "");
            Double voltage = Double.valueOf(voltageStr);
            if (arr[1].indexOf("V") > -1) {
                BigDecimal bg = null;
                Double electricity = 0d;
                if (voltage >= 3.9d) {
                    wi.setElectricity(100d);
                } else if (voltage <= 3.3d) {
                    wi.setElectricity(0d);
                } else {
                    bg = new BigDecimal(voltage - 3.3d);
                    voltage = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    electricity = voltage * 166;
                    wi.setElectricity(electricity);
                }
            } else {
                wi.setElectricity(voltage);
            }
            wi.setValue(arr[2].substring(0, 2));
            wi.setSensorId(mtNumber);
            SysLog.info("标志位：" + arr[2].substring(0, 2));
            res.append("{A:P" + voltageStr + "%:" +arr[2].substring(0, 2) + "}");
            sendHttpGet(socketChannel, sourceMsg, from, wi);
        }
        return  res.toString();
    }


    /**@MethodName: analysisHydrant 泰科消火栓
     * @Description: 
     * @Param: [socketChannel, msg, regId]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/10/15 16:36 
     **/
    public static String analysisHydrant(SocketChannel socketChannel, String msg, String regId) {
        HydrantEvent he =  new HydrantEvent();
        Map<String,String> map;
        String res = "";
        if(msg.startsWith("{A")){//判断是否是新消火栓下发设置，终端回复信息
            he.setDeviceType(2);
            if (msg.split(",").length > 1){//A:"111.222.333.444","1234"}  ip设置回复数据
                he.setDataType(3);
            }else {//{A:21P100H1600L0600}  设置信息回复数据
                he.setDataType(4);
            }
            he.setFrom(regId);
            he.setMsgType("Hydrant");
            map = new HashMap<>();
            map.put("from", regId);
        }else {
            if (msg.startsWith("{C")) {
                SysLog.info("泰科消火栓新协议信息" + msg);
                map = new HashMap<>();
                res = msg.substring(1, msg.length() - 1);
                String[] msgs = res.split(":");
                map.put("from", msgs[0]);
                String str = msgs[1];
                int index = str.indexOf(".") == -1 ? 4 : 5;
                if (str.indexOf("-") != -1) {
                    map.put("value", "0");
                } else {
                    Integer val = new Integer(str.substring(0, index));
                    Double value = val / 1000.000D;
                    map.put("value", value + "");
                }

                map.put("unit", str.substring(index));
                map.put("voltage", msgs[2]);
                str = msgs[3];
                char c = str.charAt(1);
                map.put("leakage", str.charAt(0) + "");
                if (c == '8') {
                    map.put("toHit", "2");
                } else if (c == '2') {
                    map.put("openCover", "1");
                } else {
                    map.put("toHit", c + "");
                    map.put("openCover", "0");
                }
                res = "{A:" + msgs[1] + ":" + msgs[2] + ":" + msgs[3].substring(0,4) +  "}";
                he.setDeviceType(2);
                he.setDataType(0);
            } else {
                map = JSONObject.parseObject(msg, Map.class);
                he.setDeviceType(1);
                he.setDataType(0);
            }

            String value = map.get("value"); // 压力值
            String unit = map.get("unit"); // 单位 如：压力单位为：MPa 温度为： ℃ 倾斜为： °
            if (StringUtils.isNotEmpty(unit) && unit.toUpperCase().indexOf("KPA") != -1) {
                unit = unit.toUpperCase().replace("KPA", "MPa");
            }
            String voltageStr = map.get("voltage");// 电压
            voltageStr = voltageStr.replace("V", "");
            he.setBrand("深圳泰科");
            he.setSourceData(msg);
            Double waterGage = Double.valueOf(value);
            he.setValue(waterGage);
            he.setUnit(unit);
            he.setFrom(map.get("from"));
            Double voltage = Double.valueOf(voltageStr);
            he.setVoltage(voltage);
            BigDecimal bg = new BigDecimal(waterGage);
            waterGage = bg.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double electricity = 0d;
            if (voltage >= 3.9d) {
                he.setElectricity(100d);
            } else if (voltage <= 3.3d) {
                electricity = 0d;
                he.setElectricity(electricity);
            } else {
                bg = new BigDecimal(voltage - 3.3d);
                voltage = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                electricity = voltage * 166;
                he.setElectricity(electricity);
            }
            he.setValue(waterGage);
            he.setVoltage(voltage);
            he.setToHit(map.get("toHit"));
            he.setOpenCover(map.get("openCover"));
            he.setLeakage(map.get("leakage"));
            he.setMsgType("Hydrant");
        }
        String rep = sendHttpGet(socketChannel, msg, map.get("from"), he);
        SysLog.info("泰科消火栓返回信息" + rep);

        /*if (rep.indexOf("end") == -1 && rep.indexOf("{C") == -1 && rep.indexOf("{I") == -1){
            return "{X:2}";
        }*/
        if (StringUtils.isNotEmpty(rep)){
            return rep;
        }else if(StringUtils.isNotEmpty(res)){
            return res;
        }
        //{"yyyy-MM-dd,HH:mm:ss":"OK"}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        return  format.format(new Date());
    }
}