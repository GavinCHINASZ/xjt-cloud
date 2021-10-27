package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * 拓普索尔 消火栓 消息解析
 *
 * @author huanggc
 * @date 2020/09/22
 */
public class TopSailMsgUtils {

    public static void main(String[] args) {
        //header,0~3, tpsl  //type, 4, //length, 5-6 //device_type, 7   //devide_id, 8~23
        //7470736C          01         001D          04                 38 36 31 38 373830343132373432383030

        //send_time, 24~29   //battery_level, 30 //signal_strength, 31 //sample_data, 32~35  //crc16, 36~37   //tail, 38~40, iot
        //200922105309       64                  1B                    61400001              706A             696F74

        String value = "7470736C01001D0438363138373830343132373432383030200922105309641B61400001706A696F74";
        //System.out.println(value.length());// 82
        comParse(value);
    }

    /**
     * 解析消息的方式
     *
     * @author haunggc
     * @date 2020/09/22
     * @param value 消息
     * @return JSONObject
     */
    public static JSONObject comParse(String value) {
        JSONObject jsonObject = new JSONObject();

        int type = Integer.valueOf(value.substring(4 * 2, 4 * 2 + 2));

        // 心跳时间 send_time   200922105309=2020-09-22 10:53:09
        String endHeartbeatTime = value.substring(24 * 2, 29 * 2 + 2);
        jsonObject.put("endHeartbeatTime", Calendar.getInstance().get(Calendar.YEAR) + endHeartbeatTime.substring(2));

        // 电量 battery_level
        int electricQuantity = StringUtils.hexStringToAlgorism(value.substring(30 * 2, 30 * 2 + 2)) * 100;
        jsonObject.put("electricQuantity", electricQuantity);

        // 信息强度状态 signal_strength      　signalStatus 1正常　2信号弱      27=满信号
        int signalValue = StringUtils.hexStringToAlgorism(value.substring(31 * 2, 31 * 2 + 2));
        jsonObject.put("signalValue", signalValue > 15 ? 1 : 2);

        // Sample_data 61400001
        String sample = value.substring(32 * 2, 35 * 2 + 2);
        if (type == 1 || type == 2 || type == 3 || type == 4 || type == 7 || type == 8 || type == 9){
            // 0 ：表示数据正常； 1 ：表示数据阈值下限告警； 2 ：表示数据阈值上限告警， 3 ：表示设备故障； 4 ：表示数据动态变化阈值告警； 5 ：表示碰撞告警；
            // 6 ：表示倾斜告警；7 ：表示水流告警； 8 ：表示进水告警； 9 ：表示低电量告警。
            jsonObject.put("eventType", sample.charAt(0));

            // 1 ：表示压力 MPa ， 2 ：表示压力 Bar ， 3 ：表示压力 KPa ，
            // 4 ：表示液位 M ； 5 ：表示温度℃； 6 ：表示流量 m ³ /h ； 7 ：表示角度°
            int unit = sample.charAt(1);
            switch (unit){
                case 1 :
                    jsonObject.put("unit", "MPa");
                    break;
                case 2 :
                    jsonObject.put("unit", "Bar");
                    break;
                case 3 :
                    jsonObject.put("unit", "KPa");
                    break;
                case 4 :
                    jsonObject.put("unit", "M");
                    break;
                case 5 :
                    jsonObject.put("unit", "℃");
                    break;
                case 6 :
                    jsonObject.put("unit", "m³/h");
                    break;
                default:
                    jsonObject.put("unit", "°");
                    break;
            }

            // 0表示无小数点， 1表示小数点后有 1 位有效数字， 2表示小数点后有 2 位有效数字， 3表示小数点后有 3 位有效数字； 4表示小数点后有 4 位有效数字。
            int decimal = sample.charAt(2);
            String presentValue = sample.substring(3, sample.length());
            switch (decimal){
                case 1 :
                    jsonObject.put("presentValue", sample.charAt(3) + "." + sample.substring(4));
                    break;
                case 2 :
                    jsonObject.put("presentValue", sample.substring(3, 4) + "." + sample.substring(5));
                    break;
                case 3 :
                    jsonObject.put("presentValue", sample.substring(3, 5) + "." + sample.substring(6));
                    break;
                case 4 :
                    jsonObject.put("presentValue", sample.substring(3, 6) + "." + sample.charAt(sample.length() - 1));
                    break;
                default:
                    jsonObject.put("presentValue", presentValue);
                    break;
            }
        }else if (type == 5 || type == 6){

        }

        String substring = value.substring(8 * 2, 23 * 2 + 2);
        try {
            String sensorNo = new String(StringUtils.hex2byte(substring),"UTF-8");
            jsonObject.put("sensorNo", sensorNo.substring(0, sensorNo.length() - 1));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // System.out.println(sample.substring(4));
        return jsonObject;
    }
}
