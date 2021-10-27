package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.task.core.entity.task.AppTaskCheckItem;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AppTaskCheckRecord
 * @Author dwt
 * @Date 2019-08-15 15:56
 * @Description app端巡检详情
 * @Version 1.0
 */
public class AppTaskCheckRecord {
    private String taskName;
    private String  deviceName;
    private String  deviceQrNo;
    private String pointLocation;
    private String userName;
    private Integer taskType;
    private Integer taskPeriodType;
    private Integer checkResult;
    private Date createTime;
    private List<AppTaskCheckItem> taskCheckItemList;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskPeriodType() {
        return taskPeriodType;
    }

    public void setTaskPeriodType(Integer taskPeriodType) {
        this.taskPeriodType = taskPeriodType;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<AppTaskCheckItem> getTaskCheckItemList() {
        return taskCheckItemList;
    }

    public void setTaskCheckItemList(List<AppTaskCheckItem> taskCheckItemList) {
        this.taskCheckItemList = taskCheckItemList;
    }
}
