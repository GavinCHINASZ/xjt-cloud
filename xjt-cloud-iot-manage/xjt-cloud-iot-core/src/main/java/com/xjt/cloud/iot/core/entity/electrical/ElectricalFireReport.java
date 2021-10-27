package com.xjt.cloud.iot.core.entity.electrical;

import java.util.Date;

/**
 * @ClassName ElectricalFireReport
 * @Author dwt
 * @Date 2020-09-21 10:19
 * @Version 1.0
 */
public class ElectricalFireReport {

    //设备总数
    private Integer deviceTotalCount;
    //设备在线数
    private Integer deviceOnLineCount;
    //漏电流事件数
    private Integer leakageEventCount;
    //温度事件数
    private Integer tempEventCount;
    //故障事件数
    private Integer faultEventCount;
    //离线事件数
    private Integer offLineCount;
    //事件总数
    private Integer eventTotalCount;
    private Long projectId;
    //时间
    private String createTime;
    private Date startTime;
    private Date endTime;

    public Integer getDeviceTotalCount() {
        return deviceTotalCount;
    }

    public void setDeviceTotalCount(Integer deviceTotalCount) {
        this.deviceTotalCount = deviceTotalCount;
    }

    public Integer getDeviceOnLineCount() {
        return deviceOnLineCount;
    }

    public void setDeviceOnLineCount(Integer deviceOnLineCount) {
        this.deviceOnLineCount = deviceOnLineCount;
    }

    public Integer getLeakageEventCount() {
        return leakageEventCount;
    }

    public void setLeakageEventCount(Integer leakageEventCount) {
        this.leakageEventCount = leakageEventCount;
    }

    public Integer getTempEventCount() {
        return tempEventCount;
    }

    public void setTempEventCount(Integer tempEventCount) {
        this.tempEventCount = tempEventCount;
    }

    public Integer getFaultEventCount() {
        return faultEventCount;
    }

    public void setFaultEventCount(Integer faultEventCount) {
        this.faultEventCount = faultEventCount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getOffLineCount() {
        return offLineCount;
    }

    public void setOffLineCount(Integer offLineCount) {
        this.offLineCount = offLineCount;
    }

    public Integer getEventTotalCount() {
        return eventTotalCount;
    }

    public void setEventTotalCount(Integer eventTotalCount) {
        this.eventTotalCount = eventTotalCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
