package com.xjt.cloud.message.manage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xjt.cloud.commons.utils.PercentageUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送语音工具
 *
 * @author huanggc
 * @date 2020/11/17
 */
public class SendPhoneUtils {
    public static void main(String[] args) {

    }

    /**
     * 发送电话
     *
     * @param msgType 类型
     * @param telPhone 电话号码
     * @param json JSONObject
     */
    public static void sendPhone(Integer msgType, String telPhone, JSONObject json) {
        // Tts模板ID
        String ttsCode;
        // 模板中存在变量时需要设置此值
        String ttsParam;

        if(msgType == -1){
            //火警主机
            ttsCode = "TTS_164681768";
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "\",\"code2\":\"" + subStringAndPattern(json.get("deviceLocation").toString())
                    + "\",\"code3\":\"" + subStringAndPattern(json.get("deviceName").toString()) + "\",\"code4\":\"\",\"code5\":\""
                    + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else if(msgType == -2){
            //火眼
            ttsCode = "TTS_164681765";
            ttsParam = "{\"code1\":\"" + subStringAndPattern(PercentageUtils.numberToChinese(json.get("alarmMessage").toString())) + "\",\"code2\":\""
                    + subStringAndPattern(json.get("date").toString())
                    + "\",\"code3\":\"" + subStringAndPattern(json.get("buildingName").toString()) + "\",\"code4\":\"" + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else if(msgType == 13) {
            //	//尊敬的用户，现发现${code1}项目极早期设备${code2}回路探测器${code3}于${code4}发生${code7}，请及时处理。
            ttsCode = "TTS_173349033";
            ttsParam = "{\"code1\":\"" + subStringAndPattern(PercentageUtils.numberToChinese(json.get("projectName").toString())) + "\",\"code2\":\""
                    + subStringAndPattern(json.get("loopName").toString()) + "\",\"code3\":\"" + subStringAndPattern(json.get("slaveId").toString())
                    + "\",\"code4\":\"" + subStringAndPattern(json.get("createTime").toString()) + "\",\"code7\":\"" + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else{
            /*ttsCode = "TTS_164666719";
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "\",\"code2\":\"" + subStringAndPattern(json.get("buildingName").toString())
                    + "\",\"code3\":\"" + subStringAndPattern(json.get("floorName").toString()) + "\",\"code4\":\"" + subStringAndPattern(json.get("deviceLocation").toString())
                    + "\",\"code5\":\"" + subStringAndPattern(json.get("deviceName").toString()) + "\",\"code6\":\"" + subStringAndPattern(json.get("qrNo").toString())
                    + "\",\"code7\":\"" + subStringAndPattern(json.get("event").toString()) + "\"}";*/

            ttsCode = "TTS_205468828";
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "\",\"code2\":\"" + subStringAndPattern(json.get("buildingName").toString())
                    + subStringAndPattern(json.get("floorName").toString()) +  subStringAndPattern(json.get("deviceLocation").toString())
                    + subStringAndPattern(json.get("deviceName").toString()) + "\",\"code3\":\"" + subStringAndPattern(json.get("pointQrNo").toString()) +
                    "\",\"code4\":\"" + subStringAndPattern(json.get("sensorNo").toString()) + "\",\"code5\":\""
                    + subStringAndPattern(json.get("event").toString()) + "\"}";
        }

        SingleCallByTtsResponse singleCallByTtsResponse;
        try {
            singleCallByTtsResponse = VoiceUtils.singleCallByTts(telPhone, ttsCode, ttsParam);

            SysLog.info("RequestId=" + singleCallByTtsResponse.getRequestId());
            SysLog.info("Code=" + singleCallByTtsResponse.getCode());
            SysLog.info("Message=" + singleCallByTtsResponse.getMessage());
            SysLog.info("CallId=" + singleCallByTtsResponse.getCallId());
        } catch (ClientException e) {
            SysLog.error(e);
        }
    }

    /**
     * getCode
     *
     * @param msgType 类型
     * @return String
     */
    public static String getCode(Integer msgType){
        // Tts模板ID
        if(msgType == -1){
            //火警主机
            return "TTS_164681768";
        }else if(msgType == -2){
            //火眼
            return "TTS_164681765";
        }else if(msgType == 13) {
            //	//尊敬的用户，现发现${code1}项目极早期设备${code2}回路探测器${code3}于${code4}发生${code7}，请及时处理。
            return "TTS_173349033";
        }else{
            return "TTS_205622072";
        }
    }

    /**
     * getParam
     *
     * @param msgType 类型
     * @param json JSONObject
     * @return String
     */
    public static String getParam(Integer msgType, JSONObject json){
        // 模板中存在变量时需要设置此值
        String ttsParam;
        /*if(msgType == -1){
            // 火警主机
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "," + subStringAndPattern(json.get("deviceLocation").toString())
                    + "处," + subStringAndPattern(json.get("deviceName").toString()) + "设备,发生"
                    + subStringAndPattern(json.get("event").toString()) + "事件\"}";
        }else if(msgType == -2){
            // 火眼
            ttsParam = "{\"code1\":\"" + subStringAndPattern(PercentageUtils.numberToChinese(json.get("alarmMessage").toString())) + ",通道于"
                    + subStringAndPattern(json.get("date").toString())
                    + ",监测到" + subStringAndPattern(json.get("buildingName").toString()) + ",发生" + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else if(msgType == 13) {
            //	//尊敬的用户，现发现${code1}项目极早期设备${code2}回路探测器${code3}于${code4}发生${code7}，请及时处理。
            ttsParam = "{\"code1\":\"" + subStringAndPattern(PercentageUtils.numberToChinese(json.get("projectName").toString())) + ",极早期设备"
                    + subStringAndPattern(json.get("loopName").toString()) + ",回路探测器" + subStringAndPattern(json.get("slaveId").toString())
                    + ",于" + subStringAndPattern(json.get("createTime").toString()) + ",发生" + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else{*/
            /*ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "\",\"code2\":\"" + subStringAndPattern(json.get("buildingName").toString())
                    + subStringAndPattern(json.get("floorName").toString()) +  subStringAndPattern(json.get("deviceLocation").toString())
                    + subStringAndPattern(json.get("deviceName").toString()) + "\",\"code3\":\"" + subStringAndPattern(json.get("pointQrNo").toString()) +
                    "\",\"code4\":\"" + subStringAndPattern(json.get("sensorNo").toString()) + "\",\"code5\":\""
                    + subStringAndPattern(json.get("event").toString()) + "\"}";*/
        if(msgType == -2){// 火眼
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "项目，" + subStringAndPattern(json.get("deviceLocation").toString())
                    + "位置，" + subStringAndPattern(json.get("videoName").toString()) + "摄像头，在" + subStringAndPattern(json.get("date").toString()) +
                    "，发生" + subStringAndPattern(json.get("event").toString()) + "\"}";
        }else {
            ttsParam = "{\"code1\":\"" + subStringAndPattern(json.get("projectName").toString()) + "," + subStringAndPattern(json.get("buildingName").toString())
                    + subStringAndPattern(json.get("floorName").toString()) + subStringAndPattern(json.get("deviceLocation").toString())
                    + subStringAndPattern(json.get("deviceName").toString()) + ",巡检点ID：" + subStringAndPattern(json.get("pointQrNo").toString()) +
                    ",传感器ID：" + subStringAndPattern(json.get("sensorNo").toString()) + ",出现"
                    + subStringAndPattern(json.get("event").toString()) + "\"}";
        }
        //}

        return ttsParam;
    }

    /**
     * 截取字符长度且去除特殊字符
     *
     * @param str String
     * @return String
     */
    private static String subStringAndPattern(String str){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        if(str.length() > 190){
            str = str.substring(0, 16) + "…";
        }
        String regEx = "[`~!@#$^&()+=|{}';',\\[\\]<>/?~！@#￥……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
