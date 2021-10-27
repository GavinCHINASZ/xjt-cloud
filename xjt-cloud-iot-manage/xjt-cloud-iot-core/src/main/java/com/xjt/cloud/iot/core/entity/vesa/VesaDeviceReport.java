package com.xjt.cloud.iot.core.entity.vesa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:02
 * @Description: 极早期设备报表实体类
 */
@JsonSerialize( include = JsonSerialize.Inclusion.NON_NULL)
public class VesaDeviceReport {
    //总设备数
    private Integer deviceTotal;
    //异常 vesa设备数
    private Integer failVesaTotal;
    //在线设备数
    private Integer onlineDeviceTotal;
    //离线设备数
    private Integer offlineDeviceTotal;
    //火警2事件数
    private Integer fireAlarm2Total;
    //火警1事件数
    private Integer fireAlarm1Total;
    //行动事件数
    private Integer actionTotal;
    //警告事件数
    private Integer alarmTotal;
    //故障事件数
    private Integer faultTotal;
    // 开始时间
    private String startTime;
    // 结束时间
    private Date createTime;

    //总事件数
    private Integer eventTotal;
    //离线事件数
    private Integer offlineEventTotal;

    public Integer getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Integer deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public Integer getFailVesaTotal() {
        return failVesaTotal;
    }

    public void setFailVesaTotal(Integer failVesaTotal) {
        this.failVesaTotal = failVesaTotal;
    }

    public Integer getOnlineDeviceTotal() {
        return onlineDeviceTotal;
    }

    public void setOnlineDeviceTotal(Integer onlineDeviceTotal) {
        this.onlineDeviceTotal = onlineDeviceTotal;
    }

    public Integer getOfflineDeviceTotal() {
        return offlineDeviceTotal;
    }

    public void setOfflineDeviceTotal(Integer offlineDeviceTotal) {
        this.offlineDeviceTotal = offlineDeviceTotal;
    }

    public Integer getFireAlarm2Total() {
        return fireAlarm2Total;
    }

    public void setFireAlarm2Total(Integer fireAlarm2Total) {
        this.fireAlarm2Total = fireAlarm2Total;
    }

    public Integer getFireAlarm1Total() {
        return fireAlarm1Total;
    }

    public void setFireAlarm1Total(Integer fireAlarm1Total) {
        this.fireAlarm1Total = fireAlarm1Total;
    }

    public Integer getActionTotal() {
        return actionTotal;
    }

    public void setActionTotal(Integer actionTotal) {
        this.actionTotal = actionTotal;
    }

    public Integer getAlarmTotal() {
        return alarmTotal;
    }

    public void setAlarmTotal(Integer alarmTotal) {
        this.alarmTotal = alarmTotal;
    }

    public Integer getFaultTotal() {
        return faultTotal;
    }

    public void setFaultTotal(Integer faultTotal) {
        this.faultTotal = faultTotal;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getOfflineEventTotal() {
        return offlineEventTotal;
    }

    public void setOfflineEventTotal(Integer offlineEventTotal) {
        this.offlineEventTotal = offlineEventTotal;
    }

    public Integer getEventTotal() {
        return eventTotal;
    }

    public void setEventTotal(Integer eventTotal) {
        this.eventTotal = eventTotal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
