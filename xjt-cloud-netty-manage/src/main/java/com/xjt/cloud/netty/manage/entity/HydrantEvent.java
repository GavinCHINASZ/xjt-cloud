package com.xjt.cloud.netty.manage.entity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:56
 * @Description:消火栓
 */
public class HydrantEvent {
    //消息类型 如：FireAlarm  火警主机 ，Hydrant 消火栓
    private String msgType;
    // 品牌
    private String brand;
    // 来源ID
    private String from;
    //监测值
    private Double value;
    //电量
    private Double electricity;
    //电压
    private Double voltage;
    //原始数据
    private  String sourceData;
    //单位
    private String unit;
    //0、正常  1、漏水报警  2-7 取水报警  8、传感器报警
    private String leakage;
    //撞击报警 0、正常 1、撞击报警 2、撞击传感器故障
    private String toHit;
    //0、正常  1、开盖报警
    private String openCover;
    // 1没有设置功能, 2有设置功能
    private Integer deviceType;
    //0正常记录数据, 1水压设备设置信息确定收到，2消火栓ip设置 3消火栓设置
    private Integer dataType;

    public String getOpenCover() {
        return openCover;
    }

    public void setOpenCover(String openCover) {
        this.openCover = openCover;
    }

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getElectricity() {
        return electricity;
    }

    public void setElectricity(Double electricity) {
        this.electricity = electricity;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLeakage() {
        return leakage;
    }

    public void setLeakage(String leakage) {
        this.leakage = leakage;
    }

    public String getToHit() {
        return toHit;
    }

    public void setToHit(String toHit) {
        this.toHit = toHit;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
