package com.xjt.cloud.iot.core.entity.water;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:02
 * @Description: 水压设备报表实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterDeviceReport {
    private Integer deviceTotal;//总设备数
    private Integer failDevice;//异常设备数
    private Integer waterTotal;//总水设备数
    private Integer failWaterTotal;//异常水设备数
    private Integer superHigh;//超高数
    private Integer ultralow;//超低数
    private Integer offLine;//离线数
    private Integer lowPower;//电压低
    private Integer leak;//漏水

    private Integer alarmEvent;//报警事件
    private Integer impactDamage;//撞击损坏
    private Integer faultEvent;//故障事件

    private String createTime;//时间
    private String timeDesc;
    private Integer deviceType;//设备类型　默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控
    private Integer[] deviceTypes;
    private Long waterTerminalId;//两端设备时，供水端的主键id
    private Integer normal;//正常设备数

    public Integer getAlarmEvent() {
        return alarmEvent;
    }

    public void setAlarmEvent(Integer alarmEvent) {
        this.alarmEvent = alarmEvent;
    }

    public Integer getImpactDamage() {
        return impactDamage;
    }

    public void setImpactDamage(Integer impactDamage) {
        this.impactDamage = impactDamage;
    }

    public Integer getFaultEvent() {
        return faultEvent;
    }

    public void setFaultEvent(Integer faultEvent) {
        this.faultEvent = faultEvent;
    }

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

    public Integer getWaterTotal() {
        return waterTotal;
    }

    public void setWaterTotal(Integer waterTotal) {
        this.waterTotal = waterTotal;
    }

    public Integer getFailWaterTotal() {
        return failWaterTotal;
    }

    public void setFailWaterTotal(Integer failWaterTotal) {
        this.failWaterTotal = failWaterTotal;
    }

    public Integer getSuperHigh() {
        return superHigh;
    }

    public void setSuperHigh(Integer superHigh) {
        this.superHigh = superHigh;
    }

    public Integer getUltralow() {
        return ultralow;
    }

    public void setUltralow(Integer ultralow) {
        this.ultralow = ultralow;
    }

    public Integer getOffLine() {
        return offLine;
    }

    public void setOffLine(Integer offLine) {
        this.offLine = offLine;
    }

    public Integer getLowPower() {
        return lowPower;
    }

    public void setLowPower(Integer lowPower) {
        this.lowPower = lowPower;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Integer getLeak() {
        return leak;
    }

    public void setLeak(Integer leak) {
        this.leak = leak;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public Long getWaterTerminalId() {
        return waterTerminalId;
    }

    public void setWaterTerminalId(Long waterTerminalId) {
        this.waterTerminalId = waterTerminalId;
    }

    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }
}
