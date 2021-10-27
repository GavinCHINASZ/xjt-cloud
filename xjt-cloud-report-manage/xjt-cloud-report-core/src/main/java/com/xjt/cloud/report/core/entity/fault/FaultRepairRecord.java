package com.xjt.cloud.report.core.entity.fault;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @Description: 故障报修 实体类
 * @Author huanggc
 * @Date 2019/09/02
 **/
public class FaultRepairRecord extends BaseEntity {
    private Long deviceId;// 设备id(数据库表的id)
    private String deviceName;// 设备名称
    private String deviceQrNo;// 设备码(页面显示的设备ID)
    private Integer deviceCount;// 设备数量
    private String deviceLocation;// 设备位置

    private String checkPointQrNo;// 巡查码(页面显示的 巡查点ID)
    private String checkPointName;// 巡查名称
    private Long checkRecordId;// 巡检记录ID(数据库表的id)

    private String faultDescription;// 故障描述

    private String maintenanceMethod;// 维修方法; 维修, 更换, 移位, 增设, 清除, 无
    private String maintenanceResult;// 维修结果

    private Integer isStopSystem;// 停用系统 1停, 2不停
    private Date stopSystemDate;// 停用时间

    private String workOrderNumber;// 工单号
    private Integer workOrderStatus;// 工单状态 1待指派  2维修中  3审核中   4已完成
    private String workOrderDescription;// 工单描述
    private Date startHandlingTime;// 故障处理开始时间
    private Date endHandlingTime;// 故障处理结束时间

    private String fromType;// 故障来源 1、APP内报修  2、巡检记录报修（巡查）  3、微信报修
    private String auditOpinion;// 审核意见

    private Long organizationId;// 部门ID
    private String orgName;// 部门名称(报修人所在的部门)

    private Boolean nearDate;// 近30天

    private Date startDate;// 自定义开始时间
    private Date endDate;// 自定义结束时间

    private Integer buttonType;// 页面上的按钮类型: 1不予处理; 2待审核:通过; 3发布任务; 4维修中:提交; 5驳回

    private Long buildingId;// 建筑物id
    private String[] buildingIds;// 建筑物id

    private Long buildingFloorId;// 楼层id
    private String[] buildingFloorIds;// 楼层id

    private String imageUrl;// 现场图片
    private String[] imageUrls;

    private String afterImageUrl;// 处理后图片
    private String[] afterImageUrls;

    // 是否消防部门备案
    private String fireDepartmentRecord;
    // 安全保护措施
    private String protectiveMeasures;
    // 维修时间
    private Date repairTime;
    // 维修人员
    private String repairUser;// 维修人员 engineer

    // 以下字段不在数据库中
    private String period;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getMaintenanceMethod() {
        return maintenanceMethod;
    }

    public void setMaintenanceMethod(String maintenanceMethod) {
        this.maintenanceMethod = maintenanceMethod;
    }

    public String getMaintenanceResult() {
        return maintenanceResult;
    }

    public void setMaintenanceResult(String maintenanceResult) {
        this.maintenanceResult = maintenanceResult;
    }

    public Integer getIsStopSystem() {
        return isStopSystem;
    }

    public void setIsStopSystem(Integer isStopSystem) {
        this.isStopSystem = isStopSystem;
    }

    public Date getStopSystemDate() {
        return stopSystemDate;
    }

    public void setStopSystemDate(Date stopSystemDate) {
        this.stopSystemDate = stopSystemDate;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getWorkOrderStatusDesc() {
        String workStatus = null;
        if (workOrderStatus != null){
            switch (workOrderStatus){
                case 1:
                    workStatus = "待指派";
                    break;
                case 2:
                    workStatus = "维修中";
                    break;
                case 3:
                    workStatus = "审核中";
                    break;
                default:
                    workStatus = "已完成";
            }
        }
        return workStatus;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getWorkOrderDescription() {
        return workOrderDescription;
    }

    public void setWorkOrderDescription(String workOrderDescription) {
        this.workOrderDescription = workOrderDescription;
    }

    public Date getStartHandlingTime() {
        return startHandlingTime;
    }

    public void setStartHandlingTime(Date startHandlingTime) {
        this.startHandlingTime = startHandlingTime;
    }

    public Date getEndHandlingTime() {
        return endHandlingTime;
    }

    public void setEndHandlingTime(Date endHandlingTime) {
        this.endHandlingTime = endHandlingTime;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public Boolean getNearDate() {
        return nearDate;
    }

    public void setNearDate(Boolean nearDate) {
        this.nearDate = nearDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getButtonType() {
        return buttonType;
    }

    public void setButtonType(Integer buttonType) {
        this.buttonType = buttonType;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String[] getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(String[] buildingIds) {
        this.buildingIds = buildingIds;
    }

    public String[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(String[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getAfterImageUrl() {
        return afterImageUrl;
    }

    public void setAfterImageUrl(String afterImageUrl) {
        this.afterImageUrl = afterImageUrl;
    }

    public String[] getAfterImageUrls() {
        return afterImageUrls;
    }

    public void setAfterImageUrls(String[] afterImageUrls) {
        this.afterImageUrls = afterImageUrls;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getFireDepartmentRecord() {
        return fireDepartmentRecord;
    }

    public void setFireDepartmentRecord(String fireDepartmentRecord) {
        this.fireDepartmentRecord = fireDepartmentRecord;
    }

    public String getProtectiveMeasures() {
        return protectiveMeasures;
    }

    public void setProtectiveMeasures(String protectiveMeasures) {
        this.protectiveMeasures = protectiveMeasures;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairUser() {
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }
}
