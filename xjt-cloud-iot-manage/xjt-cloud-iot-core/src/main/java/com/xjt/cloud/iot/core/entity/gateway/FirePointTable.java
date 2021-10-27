package com.xjt.cloud.iot.core.entity.gateway;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName FirePointTable
 * @Author dwt
 * @Date 2019-11-28 17:07
 * @Version 1.0
 */
public class FirePointTable extends BaseEntity {
    private Long id;
    //地址
    private Integer address;
    //位数
    private Integer digit;
    //事件类型 0:故障，1：报警，2，联动(监视)
    private Integer eventType;
    //设备名称
    private String deviceName;
    //位置
    private String position;
    //网关
    private String gateway;
    //火警主机编号
    private String fireAlarmNo;

    private String slaveId;

    private String binaryString;

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getDigit() {
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(String slaveId) {
        this.slaveId = slaveId;
    }

    public Integer getFireEventType(){
        Integer fireEventType = 0;
        //'事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，
        // 7-故障事件，8-系统事件，（9-例行检查），10-其他 ，11-屏蔽，12-启动，13-延时状态，
        // 14-主电故障，15-备电故障，16-总线故障，17-系统复位，20-传输装置复位，21-离线事件',
        if (eventType != null){
            switch (eventType){
                case 0 :
                    fireEventType = 7;
                    break;
                case 1 :
                    fireEventType = 1;
                    break;
                case 2 :
                    fireEventType = 2;
                    break;
            }
        }
        return fireEventType;
    }

    public String getBinaryString() {
        return binaryString;
    }

    public void setBinaryString(String binaryString) {
        this.binaryString = binaryString;
    }

    public String getFireAlarmNo() {
        return fireAlarmNo;
    }

    public void setFireAlarmNo(String fireAlarmNo) {
        this.fireAlarmNo = fireAlarmNo;
    }
}
