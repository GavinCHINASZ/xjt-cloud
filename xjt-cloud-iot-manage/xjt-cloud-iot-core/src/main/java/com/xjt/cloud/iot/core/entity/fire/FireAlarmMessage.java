package com.xjt.cloud.iot.core.entity.fire;

/**
 * @ClassName FireAlarmMessage
 * @Author Administrator
 * @Date 2019-10-24 14:38
 * @Description TODO
 * @Version 1.0
 */
public class FireAlarmMessage {
    //消息类型 如：FireAlarm  火警主机 ，Hydrant 消火栓
    private String msgType;
    // 品牌
    private String brand;
    // 来源ID  (注册id)
    private String from;
    // 时间 yyyy-MM-dd HH:mm:ss
    private String time;
    /*
     * 事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，7-故障事件，8-系统事件，（9-例行检查），10-
     * 其他 11-屏蔽,12-启动,13-延时状态,14-主电故障,15-备电故障,16-总线故障,17-系统复位,20,传输装置复位,21-离线事件',
     */
    private int event;
    // 报警编号  回路地址
    private String positionCode;
    // 设备名称
    private String mtName;
    // 报警位置
    private String position;
    //火警主机号
    private String hostNumber;
    // 源数据
    private String sourceData;
    //是否心跳
    private boolean heartbeat;
    //设备地址
    private String address;
    //设备编号
    private String mtCode;
    //恢复事件类型
    private int recoverEvent;
    //描述
    private String description;
    //回路
    private String loopName;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getMtName() {
        return mtName;
    }

    public void setMtName(String mtName) {
        this.mtName = mtName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHostNumber() {
        return hostNumber;
    }

    public void setHostNumber(String hostNumber) {
        this.hostNumber = hostNumber;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public boolean isHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMtCode() {
        return mtCode;
    }

    public void setMtCode(String mtCode) {
        this.mtCode = mtCode;
    }

    public int getRecoverEvent() {
        return recoverEvent;
    }

    public void setRecoverEvent(int recoverEvent) {
        this.recoverEvent = recoverEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }
}
