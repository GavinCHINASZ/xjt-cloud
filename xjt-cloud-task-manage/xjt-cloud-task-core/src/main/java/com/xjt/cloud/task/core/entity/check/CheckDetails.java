package com.xjt.cloud.task.core.entity.check;

import java.util.Date;

/**
 *
 * 巡检详情实体--自定义实体,用于接收数据(数据库中无 巡检详情表)
 * @ClassName CheckDetails
 * @Author dwt
 * @Date 2019-08-14 15:22
 * @Version 1.0
 */
public class CheckDetails {
    private Date createTime;
    private Long checkRecordId;// 巡查点ID
    private String checkPointName;// 巡查点名称
    private String checkPointQrNo;// 巡查码

    private String deviceName;// 设备名称
    private String deviceQrNo;// 设备二维码

    private String buildingName;// 建筑物名称
    private String floorName;// 楼层名称
    private String pointLocation;// 设备地址
    private Integer checkResult;// 巡查结果: 0正常, 1故障
    private String checkerName;// 巡查人
    private String orgName;// 部门
    private Date checkTime;
    private String taskName;// 任务名称
    private Integer taskType;// 任务类型
    private Integer taskStatus;// 任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
    private String deviceMemo;// 设备描述(备注)

    private Long versionNo;// 巡检记录的版本号

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDeviceMemo() {
        return deviceMemo;
    }

    public void setDeviceMemo(String deviceMemo) {
        this.deviceMemo = deviceMemo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
}
