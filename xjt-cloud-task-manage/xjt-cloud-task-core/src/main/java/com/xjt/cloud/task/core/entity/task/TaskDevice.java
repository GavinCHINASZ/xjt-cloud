package com.xjt.cloud.task.core.entity.task;

import java.util.List;

/**
 * @ClassName TaskDevice
 * @Author dwt
 * @Date 2019-08-07 16:10
 * @Description 接收不同条件筛选设备的参数
 * @Version 1.0
 */
public class TaskDevice {
    private Long id;
    //巡更点id
    private Long checkPointId;
    //设备名称
    private String deviceName;
    //部门名称
    private String orgName;
    //楼层
    private Integer floor;
    //楼层名称
    private String floorName;

    private Long projectId;

    private String qrNo;

    private String pointName;

    private Long buildingId;

    private Long companyId;

    private Long parentId;

    private Long floorId;

    private Integer type;

    private String deviceSysName;

    private Long deviceTypeId;

    private Long orgId;

    private List<Long> checkPointIds;

    private Integer sel;

    private Integer notSel;

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<Long> getCheckPointIds() {
        return checkPointIds;
    }

    public void setCheckPointIds(List<Long> checkPointIds) {
        this.checkPointIds = checkPointIds;
    }

    public Integer getSel() {
        return sel;
    }

    public void setSel(Integer sel) {
        this.sel = sel;
    }

    public Integer getNotSel() {
        return notSel;
    }

    public void setNotSel(Integer notSel) {
        this.notSel = notSel;
    }
}
