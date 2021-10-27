package com.xjt.cloud.iot.core.entity.fire;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName FireAlarmRecord
 * @Author dwt
 * @Date 2019-10-11 15:05
 * @Description 火警主机记录实体
 * @Version 1.0
 */
public class FireAlarmRecord extends BaseEntity {
    //位置编码
    private String positionCode;
    //位置
    private String recordPosition;
    //来源id（注册码）传感器ID/传输装置id
    private String transDeviceId;
    //火警主机编号
    private String fireAlarmNo;
    //品牌
    private String brand;
    //数据源(原始信息字符串)
    private String rawData;
    /*
     * 事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，7-故障事件，8-系统事件，（9-例行检查），10-
     * 其他 11-屏蔽,12-启动,13-延时状态,14-主电故障,15-备电故障,16-总线故障,17-系统复位, 18-隔离事件,
     * 20,传输装置复位,21-离线事件',
     */
    private Integer eventType;
    // 设备名称
    private String deviceName;
    //设备地址
    private String deviceAddress;
    //设备编号
    private String deviceCode;
    //status '信息状态　1正常　99删除',
    //关联FireAlarmDevice
    //设备id
    private Long deviceId;
    //发生时间
    private String happenTime;
    //火警主机设备id
    private Long fireAlarmDeviceId;
    private Integer[] eventTypeArr;
    private Long[] projectIds;
    //描述
    private String description;
    private String loopName;

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getRecordPosition() {
        return recordPosition;
    }

    public void setRecordPosition(String recordPosition) {
        this.recordPosition = recordPosition;
    }

    public FireAlarmRecord(FireAlarmMessage fireAlarmMessage){
        this.setBrand(fireAlarmMessage.getBrand());
        this.setEventType(fireAlarmMessage.getEvent());
        this.setDeviceAddress(fireAlarmMessage.getAddress());
        this.setDeviceName(fireAlarmMessage.getMtName());
        this.setPositionCode(fireAlarmMessage.getPositionCode());
        this.setRecordPosition(fireAlarmMessage.getPosition());
        this.setRawData(fireAlarmMessage.getSourceData());
        this.setTransDeviceId(fireAlarmMessage.getFrom());
        this.setDeviceCode(fireAlarmMessage.getMtCode());
        this.setFireAlarmNo(fireAlarmMessage.getHostNumber());
        this.setHappenTime(fireAlarmMessage.getTime());
        this.setDescription(fireAlarmMessage.getDescription());
        this.setLoopName(fireAlarmMessage.getLoopName());
    }

    public FireAlarmRecord(){

    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer[] getEventTypeArr() {
        return eventTypeArr;
    }

    public void setEventTypeArr(Integer[] eventTypeArr) {
        this.eventTypeArr = eventTypeArr;
    }

    public String getTransDeviceId() {
        return transDeviceId;
    }

    public void setTransDeviceId(String transDeviceId) {
        this.transDeviceId = transDeviceId;
    }

    public String getFireAlarmNo() {
        return fireAlarmNo;
    }

    public void setFireAlarmNo(String fireAlarmNo) {
        this.fireAlarmNo = fireAlarmNo;
    }

    public Long getFireAlarmDeviceId() {
        return fireAlarmDeviceId;
    }

    public void setFireAlarmDeviceId(Long fireAlarmDeviceId) {
        this.fireAlarmDeviceId = fireAlarmDeviceId;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
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
