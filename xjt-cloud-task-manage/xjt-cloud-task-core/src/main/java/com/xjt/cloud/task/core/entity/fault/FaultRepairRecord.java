package com.xjt.cloud.task.core.entity.fault;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.task.core.entity.check.CheckRecord;

import java.util.List;
import java.util.StringJoiner;

/**
 * 故障报修 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class FaultRepairRecord extends BaseEntity {
    private Long taskId;
    private Long deviceId;
    private String deviceName;
    /**
     * 设备码(页面显示的设备ID)
     */
    private String deviceQrNo;
    private Integer deviceCount;
    /**
     * 设备位置(建筑物+楼层+具体位置)
     */
    private String deviceLocation;
    /**
     * 巡查点ID
     */
    private Long checkPointId;
    /**
     * 巡查码(页面显示的 巡查点ID)
     */
    private String checkPointQrNo;
    private String checkPointName;
    /**
     * 巡检记录ID(数据库表的id)
     */
    private Long checkRecordId;
    /**
     * 故障部位
     */
    private String faultLocation;
    /**
     * 故障描述(故障原因)
     */
    private String faultDescription;
    /**
     * 补充说明
     */
    private String increaseExplain;
    /**
     * 故障来源 1、APP内报修  2、巡检记录报修（巡查）  3、微信报修
     */
    private Integer fromType;

    /**
     * 部门ID
     */
    private Long organizationId;
    private String orgName;

    private Long buildingId;
    private Long buildingFloorId;
    private String imageUrl;
    private List<String> imageUrls;

    /**
     * 工单状态 “待指派”传1, “维修中”传2, “待审核”传3, “已修复”传4, “不予处理”传5;
     */
    private Integer workOrderStatus;
    private List<FaultRepairRecord> faultRepairRecordList;

    public FaultRepairRecord() {
    }

    public FaultRepairRecord(CheckRecord checkRecord, Long orgId) {
        this.deviceId = checkRecord.getDeviceId();
        this.deviceName = checkRecord.getDeviceName();
        this.deviceQrNo = checkRecord.getDeviceQrNo();
        this.deviceCount = checkRecord.getDeviceCount();
        this.deviceLocation = checkRecord.getPointLocation();
        this.checkPointId = checkRecord.getCheckPointId();
        this.checkPointQrNo = checkRecord.getCheckPointQrNo();
        this.checkPointName = checkRecord.getCheckPointName();
        this.checkRecordId = checkRecord.getId();
        this.increaseExplain = checkRecord.getFaultDescription();
        this.faultLocation = checkRecord.getFaultLocation();
        this.faultDescription = checkRecord.getFaultReason();
        this.fromType = 2;
        this.taskId = checkRecord.getTaskId();
        this.organizationId = orgId;
        this.orgName = checkRecord.getOrgName();
        this.buildingId = checkRecord.getBuildingId();
        this.buildingFloorId = checkRecord.getBuildingFloorId();
        this.imageUrls = checkRecord.getImageUrls();

        if(checkRecord.getImageUrls()!= null){
            StringJoiner stringJoiner = new StringJoiner(";");
            for (String url: checkRecord.getImageUrls()) {
                stringJoiner.add(url);
            }
            this.imageUrl = stringJoiner.toString();
        }

        if(checkRecord.getHandleStatus() == 1){
            this.workOrderStatus = 1;
        }else if(checkRecord.getHandleStatus() == 2){
            this.workOrderStatus = 4;
        }
        this.setProjectId(checkRecord.getProjectId());
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getIncreaseExplain() {
        return increaseExplain;
    }

    public void setIncreaseExplain(String increaseExplain) {
        this.increaseExplain = increaseExplain;
    }

    public String getFaultLocation() {
        return faultLocation;
    }

    public void setFaultLocation(String faultLocation) {
        this.faultLocation = faultLocation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

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

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
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

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<FaultRepairRecord> getFaultRepairRecordList() {
        return faultRepairRecordList;
    }

    public void setFaultRepairRecordList(List<FaultRepairRecord> faultRepairRecordList) {
        this.faultRepairRecordList = faultRepairRecordList;
    }
}
