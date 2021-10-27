package com.xjt.cloud.netty.manage.entity;

/**
 * @Auther: zhangZaiFa
 * @Date: 2019/9/17 17:00
 * @Description:水浸事件
 */
public class WaterImmersionEvent {
    //设备正常上传数据
    public WaterImmersionEvent(Integer dataType, String sensorId, String msgType, String brand, Integer sign, String value, Double electricity, Double voltage, String sourceData,Integer deviceType) {
        this.dataType = dataType;
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sign = sign;
        this.value = value;
        this.electricity = electricity;
        this.voltage = voltage;
        this.sourceData = sourceData;
        this.deviceType = deviceType;
    }
    //设备上发确认信息
    public WaterImmersionEvent(Integer dataType, String sensorId, String msgType, String brand,String sourceData,Integer deviceType) {
        this.dataType = dataType;
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sourceData = sourceData;
        this.deviceType = deviceType;
    }
    //设备上发设备信息
    public WaterImmersionEvent(Integer dataType, String sensorId, String msgType, String brand, String sourceData, String simCard,
                               Integer dataSendInterval, String domainNameUrl, String port,Integer deviceType) {
        this.dataType = dataType;
        this.sensorId = sensorId;
        this.msgType = msgType;
        this.brand = brand;
        this.sourceData = sourceData;
        this.simCard = simCard;
        this.dataSendInterval = dataSendInterval;
        this.domainNameUrl = domainNameUrl;
        this.port = port;
        this.deviceType = deviceType;

    }

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
    //监测点状态0正常1故障，可存在多个监测点，如:01代表两个监测点，第一个监测点正常，第二个监测点故障
    private String value;
    //电压
    private Double electricity;
    //电量
    private Double voltage;
    //原始数据
    private  String sourceData;
    //卡号
    private String simCard;
    // 心跳发送间隔       09~13 对应    60,120,360,720,1440分钟
    private Integer dataSendInterval;
    // 域名的url
    private String domainNameUrl;
    // 端口号
    private String port;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public WaterImmersionEvent() {

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

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public Integer getDataSendInterval() {
        return dataSendInterval;
    }

    public void setDataSendInterval(Integer dataSendInterval) {
        this.dataSendInterval = dataSendInterval;
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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
