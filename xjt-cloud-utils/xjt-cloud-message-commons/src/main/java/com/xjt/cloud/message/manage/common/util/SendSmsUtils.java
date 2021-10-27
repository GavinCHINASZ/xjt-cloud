package com.xjt.cloud.message.manage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xjt.cloud.commons.utils.PercentageUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送短信工具
 *
 * @author huanggc
 * @date 2020/11/17
 */
public class SendSmsUtils {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("ProName", "烟感设备离线的数量");
        jsonObject.put("position", Integer.toString(52));
        Integer event = 91;
        String[] split = {"15976885887", "15712021546"};

        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953","320396eba397264367f1c602cd735b97");
        AlibabaAliqinFcSmsNumSendRequest request = getAlibabaAliqinFcSmsNumSendRequest(event, "", jsonObject);
        AlibabaAliqinFcSmsNumSendResponse rsp;
        for (String phone : split) {
            request.setRecNum(phone);

            try {
                rsp = client.execute(request);
                SysLog.info(rsp.getBody());
            } catch (ApiException e) {
                SysLog.error(e);
            }
        }
    }

    /**
     * 获取 AlibabaAliqinFcSmsNumSendRequest
     *
     * @param event 类型
     * @param telPhone 电话号码
     * @param json JSONObject
     * @return AlibabaAliqinFcSmsNumSendRequest
     */
    public static AlibabaAliqinFcSmsNumSendRequest getAlibabaAliqinFcSmsNumSendRequest(Integer event, String telPhone, JSONObject json) {
        //TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953","320396eba397264367f1c602cd735b97");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName("消检通");

        // 短信模板在 阿里云--短信服务 中设置
        //SMS_154505483
        //尊敬的用户，现发现${code1}项目${code2}建筑${code3}楼${code4}处${code5}设备${code6}出现${code7}问题，请及时处理。”
        //SMS_154591928     火警主机
        //尊敬的用户，现发现${code1}项目${code2}处${code3}设备${code4}发生${code5}事件，请及时处理。

        if(event == -1){//火警主机
            req.setSmsTemplateCode("SMS_155357100");
            req.setSmsParamString("{\"code1\":\"" + subString(json.get("projectName").toString()) + "\",\"code2\":\"" + subString(json.get("deviceLocation").toString())
                    + "\",\"code3\":\"" + subString(json.get("deviceName").toString()) + "\",\"code4\":\"" + subString(json.get("date").toString()) + "\",\"code5\":\""
                    + subString(json.get("event").toString()) + "\"}");

        }else if(event == -2){//火眼
            //SMS_154591939   火眼
            //尊敬的用户，${code1}通道于${code2}监测到${code3}发生${code4}，请及时处理。
            req.setSmsTemplateCode("SMS_210078708");
            req.setSmsParamString("{\"code1\":\"" + subString(json.get("projectName").toString()) + "\",\"code2\":\"" + subString(json.get("deviceLocation").toString())
                    + "\",\"code3\":\"" + subString(json.get("videoName").toString()) + "\",\"code4\":\"" + subString(json.get("date").toString()) + "\",\"code5\":\""
                    + subString(json.get("event").toString()) + "\"}");
        }else if(event == 13) {
            //SMS_173349030 极早期
            ////尊敬的用户，现发现${code1}项目极早期设备${code2}回路探测器${code3}于${code4}发生${code7}，请及时处理。
            req.setSmsTemplateCode("SMS_173349030");
            req.setSmsParamString("{\"code1\":\"" + subStringAndPattern(PercentageUtils.numberToChinese(json.get("projectName").toString())) + "\",\"code2\":\""
                    + subStringAndPattern(json.get("loopName").toString()) + "\",\"code3\":\"" + subStringAndPattern(json.get("slaveId").toString())
                    + "\",\"code4\":\"" + subStringAndPattern(json.get("date").toString()) + "\",\"code5\":\"" + subStringAndPattern(json.get("event").toString()) + "\"}");

        }else if (event == 91){// 监听项目运行状态
            //SMS_48320070   ${ProName}项目${position}，请您及时处理！
            req.setSmsTemplateCode("SMS_48320070");
            req.setSmsParamString("{\"ProName\":\"" + PercentageUtils.numberToChinese(json.get("ProName").toString())
                    + "\",\"position\":\"" + subStringAndPattern(json.get("position").toString()) + "\"}");

        } else{
            /*req.setSmsTemplateCode("SMS_155455465");
            req.setSmsParamString("{\"code1\":\"" + subString(json.get("projectName").toString()) + "\",\"code2\":\"" + subString(json.get("buildingName").toString())
                    + "\",\"code3\":\"" + subString(json.get("floorName").toString()) + "\",\"code4\":\"" + subString(json.get("deviceLocation").toString())
                    + "\",\"code5\":\"" + subString(json.get("deviceName").toString()) + "\",\"code6\":\"" + subString(json.get("qrNo").toString())
                    + "\",\"code7\":\"" + subString(json.get("event").toString()) + "\"}");*/

            req.setSmsTemplateCode("SMS_205473939");
            req.setSmsParamString("{\"code1\":\"" + subString(json.get("projectName").toString()) + "\",\"code2\":\"" + subString(json.get("buildingName").toString())
                    + subString(json.get("floorName").toString()) +  subString(json.get("deviceLocation").toString())
                    + subString(json.get("deviceName").toString()) + "\",\"code3\":\"" + subString(json.get("pointQrNo").toString()) +
                    "\",\"code4\":\"" + subString(json.get("sensorNo").toString()) + "\",\"code5\":\""
                    + subString(json.get("event").toString()) + "\"}");
        }

        req.setSmsType("normal");
        req.setSmsFreeSignName("消检通");
        req.setRecNum(telPhone);
        return req;
    }

    /**
     * 截取字符长度
     *
     * @param str String
     * @return String
     */
    private static String subString(String str){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        if(str.length() > 190){
            str = str.substring(0, 16) + "…";
        }
        return str;
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
