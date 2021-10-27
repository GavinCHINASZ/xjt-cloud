package com.xjt.cloud.netty.manage.entity;

/**
 * @ClassName VesdaVSM
 * @Author Administrator
 * @Date 2019-06-26 9:39
 * @Description:VESDA 图文信息解析实体
 * @Version 1.0
 */
public class VesdaVSMEvent {
    //消息类型
    private String msgType;
    //回路名称
    private String loopName;
    //slaveId
    private int slaveId;
    //记录类型，0：产生新记录，1：记录恢复
    private int recordType;
    //事件类型  :0:故障，1：警告，2：行动，3：火警1，4：火警2,5:火警警报
    private int eventType;
    //探测器类型
    private String detectorType;
    //探测器地址
    private String address;
    //故障编号
    private int faultNo;
    //扇区
    private String sector;
    //是否心跳
    private boolean heartbeat;
    // 来源ID  (注册id)注册码
    private String from;
    //故障描述
    private String faultDescribe;
    //事件时间
    private String eventTime;
    //原始数据包
    private String sourceData;

    public VesdaVSMEvent() {
    }


    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }

    public int getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public String getDetectorType() {
        return detectorType;
    }

    public void setDetectorType(String detectorType) {
        this.detectorType = detectorType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFaultNo() {
        return faultNo;
    }

    public void setFaultNo(int faultNo) {
        this.faultNo = faultNo;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public boolean isHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

}

