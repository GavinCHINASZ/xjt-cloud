package com.xjt.cloud.iot.core.entity.smoke;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 烟感  设备
 *
 * @author huanggc
 * @date 2020/07/15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmokeDeviceReport extends BaseEntity {
    // 总设备数
    private Integer deviceTotal;
    // 异常设备数
    private Integer failDevice;
    // 离线数
    private Integer offLine;
    // 烟雾数
    private Integer smokeNum;
    // 拆卸数
    private Integer disassembleNum;
    // 传感器故障数
    private Integer sensorFault;
    // 电压低
    private Integer lowPower;
    private String timeDesc;

    public Integer getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Integer deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public Integer getFailDevice() {
        return failDevice;
    }

    public void setFailDevice(Integer failDevice) {
        this.failDevice = failDevice;
    }

    public Integer getOffLine() {
        return offLine;
    }

    public void setOffLine(Integer offLine) {
        this.offLine = offLine;
    }

    public Integer getSmokeNum() {
        return smokeNum;
    }

    public void setSmokeNum(Integer smokeNum) {
        this.smokeNum = smokeNum;
    }

    public Integer getDisassembleNum() {
        return disassembleNum;
    }

    public void setDisassembleNum(Integer disassembleNum) {
        this.disassembleNum = disassembleNum;
    }

    public Integer getSensorFault() {
        return sensorFault;
    }

    public void setSensorFault(Integer sensorFault) {
        this.sensorFault = sensorFault;
    }

    public Integer getLowPower() {
        return lowPower;
    }

    public void setLowPower(Integer lowPower) {
        this.lowPower = lowPower;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }
}
