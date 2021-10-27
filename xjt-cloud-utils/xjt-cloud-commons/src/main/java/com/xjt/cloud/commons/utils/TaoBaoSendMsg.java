/*
package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;

*/
/**
 * 淘宝发送短信
 *
 * @author wangzhiwen
 * @date 2019/8/1 14:16
 *//*

public class TaoBaoSendMsg {
    */
/**
     * 功能描述: 使用淘宝发送短信
     *
     * @param phone   手机号码
     * @param content 发送内容
     * @return boolean
     * @author wangzhiwen
     * @date 2019/8/1 14:44
     *//*

    public static boolean sendMsg(String phone, String content) {
        TaobaoClient client = null;
        String appKey=PropertyUtils.getProperty("sms.appKey");
        String appSecret=PropertyUtils.getProperty("sms.appSecret");
        if(StringUtils.isNotEmpty(appKey) && StringUtils.isNotEmpty(appSecret)){
            client=new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", appKey, appSecret);
        }else{
            client=new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953", "320396eba397264367f1c602cd735b97");
        }
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");

        String smsFreeSignName = PropertyUtils.getProperty("sms.free.sign.name");
        //如果sms.free.sign.xjt不为空，那么短信签名为 消维通
        if (StringUtils.isNotEmpty(smsFreeSignName)) {
            SysLog.info("-------------TaoBaoSendMsg.sendMsg.smsFreeSignName------------->" + smsFreeSignName);
            req.setSmsFreeSignName(smsFreeSignName);
        //否则，短信签名为 消检通
        } else {
            SysLog.info("-------------TaoBaoSendMsg.sendMsg.smsFreeSignName------------->" + "消检通");
            req.setSmsFreeSignName("消检通");
        }

        req.setSmsParamString(content);
        req.setRecNum(phone);
        // 短信id(阿里云平台短信模板ID) 如果sms.send不为空，存入模板信息
        String smsSend= PropertyUtils.getProperty("sms.send");
        if(StringUtils.isNotEmpty(smsSend)){
            req.setSmsTemplateCode(smsSend);
        }else{
            req.setSmsTemplateCode("SMS_169636961");
        }

        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
            System.out.println(rsp.getBody());
            JSONObject json = JSONObject.parseObject(rsp.getBody());
            if ("0".equals(json.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result").getString("err_code"))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.SEND_MSG_FAIL, ServiceErrCode.REQ_ERR.getCode());
        }
    }

    public static void main(String[] args) {
        sendMsg("15976885887", "{\"code\":\"123\"}");
    }
}
*/
