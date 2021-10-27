package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.task.core.entity.task.AppTaskCheckItem;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AppTaskDeviceItem
 * @Author dwt
 * @Date 2019-08-15 16:19
 * @Description 设备巡检项
 * @Version 1.0
 */
public class AppTaskDeviceItem {
    //任务名称
    private String taskName;
    //任务开始时间
    private Date startTime;
    //任务结束时间
    private Date endTime;
    //设备名称
    private String deviceName;
    //设备二维码
    private String deviceQrNo;
    //执行人名称
    private List<String> executorName;
    //设备巡检项
    private List<AppTaskCheckItem> appTaskCheckItemList;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public List<String> getExecutorName() {
        return executorName;
    }

    public void setExecutorName(List<String> executorName) {
        this.executorName = executorName;
    }

    public List<AppTaskCheckItem> getAppTaskCheckItemList() {
        return appTaskCheckItemList;
    }

    public void setAppTaskCheckItemList(List<AppTaskCheckItem> appTaskCheckItemList) {
        this.appTaskCheckItemList = appTaskCheckItemList;
    }
}
