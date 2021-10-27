package com.xjt.cloud.task.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName AppDevice
 * @Author dwt
 * @Date 2019-08-15 14:27
 * @Description App端任务设备
 * @Version 1.0
 */
public class AppTaskDevice extends BaseEntity {
    //任务id
    private Long taskId;
    //项目id
    private Long projectId;
    //设备id
    private Long deviceId;
    //巡检记录id
    private Long checkRecordId;
    //设备名称
    private String deviceName;
    //设备二维码
    private String deviceQrNo;
    //设备位置
    private String pointLocation;
    private Integer checkItemVsType;//巡检项的项目类型，1默认 2精简版 3项目自定义


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
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

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }
}
