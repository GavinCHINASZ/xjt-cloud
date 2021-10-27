package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName TaskReviewDevice
 * @Author dwt
 * @Date 2019-11-22 14:28
 * @Description 任务审核设备
 * @Version 1.0
 */
public class TaskReviewDevice extends BaseEntity {
    //审核记录id
    private Long taskReviewId;
    //设备名称
    private String deviceName;
    //建筑物名称
    private String buildingName;
    //楼层名称
    private String floorName;
    //巡查点二维码
    private String qrNo;
    //设备状态
    private Integer deviceStatus;
    //设备二维码
    private String  deviceQrNo;

    public Long getTaskReviewId() {
        return taskReviewId;
    }

    public void setTaskReviewId(Long taskReviewId) {
        this.taskReviewId = taskReviewId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public TaskReviewDevice(){

    }
    public TaskReviewDevice(TaskDeviceEntity entity){
        this.setBuildingName(entity.getBuildingName());
        this.setDeviceName(entity.getDeviceName());
        this.setDeviceQrNo(entity.getDeviceQrNo());
        this.setFloorName(entity.getFloorName());
        this.setDeviceStatus(entity.getDeviceStatus());
        this.setQrNo(entity.getQrNo());
    }
}
