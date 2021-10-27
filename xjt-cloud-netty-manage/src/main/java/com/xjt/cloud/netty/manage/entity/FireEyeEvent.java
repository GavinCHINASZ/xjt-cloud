package com.xjt.cloud.netty.manage.entity;

/**
 * @Auther: zhangzaifa
 * @Date: 2019/4/24 17:56
 * @Description:火眼上传信息类
 */
public class FireEyeEvent {
    //消息类型 如：FireAlarm  火警主机 ，Hydrant 消火栓
    private String msgType;
    // 来源ID
    private String from;
    //火警类型  1、火警警告  2、烟雾警告
    private Integer eventType;
    //通道号
    private String aisleNumber ;
    //图片路径
    private String imageUrl;
    //摄想头IP地址
    private String ipAddress;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getAisleNumber() {
        return aisleNumber;
    }

    public void setAisleNumber(String aisleNumber) {
        this.aisleNumber = aisleNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
