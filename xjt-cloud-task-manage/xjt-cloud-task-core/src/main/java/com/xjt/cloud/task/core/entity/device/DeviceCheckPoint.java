package com.xjt.cloud.task.core.entity.device;

/**
 * @ClassName DeviceCheckPoint
 * @Author dwt
 * @Date 2019-08-08 9:27
 * @Description 设备巡更点
 * @Version 1.0
 */
public class DeviceCheckPoint {
    private Long orgId;
    private Long projectId;
    private Long buildingId;
    private Long floorId;
    private String qrNo;
    private String pointName;
    private String orgName;
    private Long deviceSysId;
    private Long deviceTypeId;
    private Long companyId;
    private String deviceSysName;
    private String deviceTypeName;

    /**
     * 任务模板ID
     */
    private Long taskModelManageId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Long getTaskModelManageId() {
        return taskModelManageId;
    }

    public void setTaskModelManageId(Long taskModelManageId) {
        this.taskModelManageId = taskModelManageId;
    }
}
