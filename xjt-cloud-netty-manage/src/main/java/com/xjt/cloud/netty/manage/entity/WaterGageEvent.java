package com.xjt.cloud.netty.manage.entity;

import java.util.Date;

/**
 * @Auther: zhangZaiFa
 * @Date: 2019/9/17 17:00
 * @Description:水压事件
 */
public class WaterGageEvent {
    //水压正常上传数据信息
    public WaterGageEvent(Integer dataType, String sensorId, String msgType, String brand, Integer sign, Integer monitorStatus, Double value, Double electricity,
                          Double voltage, String sourceData,Integer deviceType) {
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sign = sign;
        this.monitorStatus = monitorStatus;
        this.value = value;
        this.electricity = electricity;
        this.voltage = voltage;
        this.sourceData = sourceData;
        this.dataType = dataType;
        this.createTime = new Date();
        this.deviceType = deviceType;
    }
    //设备上发确认信息
    public WaterGageEvent(Integer dataType, String sensorId, String msgType, String brand,String sourceData,Integer deviceType) {
        this.dataType = dataType;
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sourceData = sourceData;
        this.deviceType = deviceType;
    }

    //设备上发设备参数信息
    public WaterGageEvent(Integer dataType, String sensorId, String msgType, String brand, String sourceData, String simCard,
                          Integer dataSamplingInterval, Integer dataSendInterval, Double upperLimit, Double lowerLimit, Integer alarmMode,
                          Double fluctuationAlarmValue, String domainNameUrl, String port,Integer deviceType) {
        this.dataType = dataType;
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sourceData = sourceData;
        this.simCard = simCard;
        this.dataSamplingInterval = dataSamplingInterval;
        this.dataSendInterval = dataSendInterval;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.alarmMode = alarmMode;
        this.fluctuationAlarmValue = fluctuationAlarmValue;
        this.domainNameUrl = domainNameUrl;
        this.port = port;
        this.deviceType = deviceType;
    }
    //设备状态　1正常　2超高　3超低　
    private Integer monitorStatus;
    //是否设置功能  1、无下发设置功能   2、有下发设置功能
    private Integer deviceType;
    //数据类型 0、正常数据推送  1、设备确认信息   2、上发设备参数信息
    private Integer dataType;
    //传感器ID
    private String sensorId;
    //消息类型 如：waterGage
    private String msgType;
    // 品牌
    private String brand;
    //信号
    private Integer sign;
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
    //卡号
    private String simCard;
    // 数据采样间隔01~13 对应  5s,10s,30s,1,5,10,15,30,60,120,360,720,1440分钟
    private Integer dataSamplingInterval;
    // 心跳发送间隔       09~13 对应    60,120,360,720,1440分钟
    private Integer dataSendInterval;
    // 告警阈值上限
    private Double upperLimit;
    //  告警阈值下限
    private Double lowerLimit;
    // 告警方式设置  1；阈值告警，2；波动值告警，3；阈值和波动值告警，对水浸表无效
    private Integer alarmMode;
    // 波动告警值
    private Double fluctuationAlarmValue;
    // 域名的url
    private String domainNameUrl;
    // 端口号
    private String port;

    //创建时间
    private Date createTime;

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WaterGageEvent() {

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

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
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

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public Integer getDataSamplingInterval() {
        return dataSamplingInterval;
    }

    public void setDataSamplingInterval(Integer dataSamplingInterval) {
        this.dataSamplingInterval = dataSamplingInterval;
    }

    public Integer getDataSendInterval() {
        return dataSendInterval;
    }

    public void setDataSendInterval(Integer dataSendInterval) {
        this.dataSendInterval = dataSendInterval;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(Integer alarmMode) {
        this.alarmMode = alarmMode;
    }

    public Double getFluctuationAlarmValue() {
        return fluctuationAlarmValue;
    }

    public void setFluctuationAlarmValue(Double fluctuationAlarmValue) {
        this.fluctuationAlarmValue = fluctuationAlarmValue;
    }

    public String getDomainNameUrl() {
        return domainNameUrl;
    }

    public void setDomainNameUrl(String domainNameUrl) {
        this.domainNameUrl = domainNameUrl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorId() {
        return sensorId;
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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
