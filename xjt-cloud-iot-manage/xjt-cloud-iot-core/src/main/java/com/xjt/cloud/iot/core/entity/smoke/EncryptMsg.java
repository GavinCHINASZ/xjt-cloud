package com.xjt.cloud.iot.core.entity.smoke;

/**
 * 移动 智慧消防平台https://smartsensor.eastcmiot.com
 * 接口消息实体
 *
 * @author huanggc
 * @date 2020/07/21
 */
public class EncryptMsg {
    private String msgType;
    private String encryptMsg;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEncryptMsg() {
        return encryptMsg;
    }

    public void setEncryptMsg(String encryptMsg) {
        this.encryptMsg = encryptMsg;
    }
}
