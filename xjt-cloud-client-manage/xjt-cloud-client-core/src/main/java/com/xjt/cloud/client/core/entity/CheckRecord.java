package com.xjt.cloud.client.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 巡检记录
 *
 * @Author dwt
 * @Date 2019-07-25 9:30
 * @Description:定时任务生成的巡检记录
 * @Version 1.0
 */
public class CheckRecord extends BaseEntity {
    private Long[] ids;// CheckRecord的ID数组
    //任务id
    private Long taskId;
    //任务名称
    private String taskName;
    //任务父id
    private Long taskParentId;
    //项目id
    private Long projectId;

    //部门ID
    private Long orgId;
    //部门名称
    private String orgName;
    //设备id
    private Long deviceId;
    //设备二维码
    private String deviceQrNo;
    //故障设备数量
    private Integer faultDeviceCount;
    //设备总数
    private Integer deviceCount;
    //设备名称
    private String deviceName;
    //设备地址
    private String pointLocation;
    private String imgUrl;

    private Integer checkResult;//巡检结果 0：正常，1：故障
    private Integer[] checkResults;
    private Integer checkType;//巡检类型: 0巡查任务，1(检测)检查任务，2保养任务
    private Integer[] checkTypes;

    //任务状态 0:正常,1:异常,2:未检
    private Integer deviceStatus;

    //巡更点二维码
    private String checkPointQrNo;
    //巡检人名称
    private String checkerName;
    //巡检点id
    private Long checkPointId;
    //巡查点名称
    private String checkPointName;
    private Integer faultCheckCount;// 故障巡查数

    private Long buildingId;//建筑物id
    private Long[] buildingIds;//建筑物id
    private String buildingName;// 建筑物名称
    private Long buildingFloorId;//建筑物楼层id
    private Long[] buildingFloorIds;//建筑物楼层id
    //楼层名称
    private String floorName;
    //开始日期
    private Date startDate;
    //结束日期
    private Date endDate;
    //是否修复  0、正常   1、未处理需报修 2、已处理
    private Integer handleStatus;
    //补充说明
    private String faultDescription;

    private Long versionNo;//版本号

    private String userName;
    private Integer taskStatus;// 任务状态

    private Boolean nearDate;// 近30天
    private List<String> imageUrls;

    private String faultLocation;// 故障部位
    private String faultReason;// 故障原因

    private String queryDate;// 查询时间: 年月

    public CheckRecord(){

    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Integer faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public String getFaultLocation() {
        return faultLocation;
    }

    public void setFaultLocation(String faultLocation) {
        this.faultLocation = faultLocation;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
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

    public Long[] getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(Long[] buildingIds) {
        this.buildingIds = buildingIds;
    }

    public Long[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Long[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(Long taskParentId) {
        this.taskParentId = taskParentId;
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

    public Integer getCheckResult() {
        return checkResult;
    }


    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer[] getCheckResults() {
        return checkResults;
    }

    public void setCheckResults(Integer[] checkResults) {
        this.checkResults = checkResults;
    }

    public Integer[] getCheckTypes() {
        return checkTypes;
    }

    public void setCheckTypes(Integer[] checkTypes) {
        this.checkTypes = checkTypes;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

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

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Boolean getNearDate() {
        return nearDate;
    }

    public void setNearDate(Boolean nearDate) {
        this.nearDate = nearDate;
    }


    public Integer getFaultCheckCount() {
        return faultCheckCount;
    }

    public void setFaultCheckCount(Integer faultCheckCount) {
        this.faultCheckCount = faultCheckCount;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }
}
