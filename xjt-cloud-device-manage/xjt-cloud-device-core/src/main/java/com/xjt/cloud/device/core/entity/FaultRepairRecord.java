package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 故障报修 实体类
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public class FaultRepairRecord extends BaseEntity {
    private Integer totalCount;
    private Boolean permissionAll;
    private Long[] ids;
    private Long deviceId;// 设备id(数据库表的id)
    private String deviceName;// 设备名称
    private String deviceImageUrl;// 设备图片logo
    private String deviceQrNo;// 设备码(页面显示的设备ID)
    private Integer deviceCount;// 设备数量
    private String deviceLocation;// 设备位置(建筑物+楼层+具体位置)

    private Long checkPointId;// 巡查点ID
    private String checkPointQrNo;// 巡查码(页面显示的 巡查点ID)
    private String checkPointName;// 巡查名称

    private Long checkRecordId;// 巡检记录ID(数据库表的id)

    private String increaseExplain;// 补充说明
    private String faultDescription;// 故障描述(故障原因)
    private String[] faultDescriptions;// 故障描述(故障原因)
    private String faultLocation;// 故障部位
    private String[] faultLocations;// 故障部位

    private String maintenanceMethod;// 维修方法; 维修, 更换, 移位, 增设, 清除, 无
    private String maintenanceResult;// 维修结果 故障维修描述
    private String securityMeasures;// 安全保护措施

    private Integer isStopSystem;// 停用系统 1停, 2不停
    private Date stopSystemDate;// 停用时间

    private Integer runSystem;// 是否恢复系统运行: 1是  2否
    private Integer excludeFault;// 是否排除故障: 1是  2否

    private String workOrderNumber;// 工单号
    private Integer workOrderStatus;// 工单状态:  所有待办(1待指派,  2维修中,  3审核中 )       所有完成: 4已完成  5不予处理
    private String workOrderDescription;// 工单描述
    private Date startHandlingTime;// 故障处理开始时间
    private Date endHandlingTime;// 故障处理结束时间

    private Integer overdue;// 是否超期:1是  2否(已超过处理结束时间)
    private Integer[] overdues;// 是否超期:1是  2否(已超过处理结束时间)

    private Integer fromType;// 故障来源 1、APP内报修  2、巡检记录报修（巡查）  3、微信报修
    private String auditOpinion;// 审核意见

    private Long organizationId;// 部门ID
    private String orgName;// 部门名称(报修人所在的部门)

    private String repairUser;// 维修人
    private Long[] repairUserIds;// 维修人
    private String examineUser;// 审核人
    private Long[] examineUsers;// 审核人

    private Boolean nearDate;// 近30天

    private Date startDate;// 自定义开始时间
    private Date endDate;// 自定义结束时间

    private Integer buttonType;// 页面上的按钮类型: 1不予处理;  2待审核:通过;  3发布任务;  4维修中:提交;   5驳回

    private Long buildingId;// 建筑物id
    private String[] buildingIds;// 建筑物id
    private String buildingName;//建筑物名称

    private Long buildingFloorId;// 楼层id
    private String[] buildingFloorIds;// 楼层id
    private String floorName;//楼层名

    private String imageUrl;// 现场图片
    private String[] imageUrls;

    private String afterImageUrl;// 处理后图片
    private String[] afterImageUrls;

    private Integer optionState;// 1所有待办,  2所有完成
    private Integer[] screenCondition;// 筛选条件

    private String projectName;// 项目名称
    private Integer faultNum;

    private List<FaultRepairRecord> faultRepairRecordList;

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getPermissionAll() {
        return permissionAll;
    }

    public void setPermissionAll(Boolean permissionAll) {
        this.permissionAll = permissionAll;
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

    public String getDeviceImageUrl() {
        return deviceImageUrl;
    }

    public void setDeviceImageUrl(String deviceImageUrl) {
        this.deviceImageUrl = deviceImageUrl;
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

    public String getIncreaseExplain() {
        return increaseExplain;
    }

    public void setIncreaseExplain(String increaseExplain) {
        this.increaseExplain = increaseExplain;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getFaultLocation() {
        return faultLocation;
    }

    public void setFaultLocation(String faultLocation) {
        this.faultLocation = faultLocation;
    }

    public String[] getFaultDescriptions() {
        return faultDescriptions;
    }

    public void setFaultDescriptions(String[] faultDescriptions) {
        this.faultDescriptions = faultDescriptions;
    }

    public String[] getFaultLocations() {
        return faultLocations;
    }

    public void setFaultLocations(String[] faultLocations) {
        this.faultLocations = faultLocations;
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

    public String getSecurityMeasures() {
        return securityMeasures;
    }

    public void setSecurityMeasures(String securityMeasures) {
        this.securityMeasures = securityMeasures;
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

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
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

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }

    public Integer[] getOverdues() {
        return overdues;
    }

    public void setOverdues(Integer[] overdues) {
        this.overdues = overdues;
    }

    public String getRepairUser() {
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    public String getExamineUser() {
        return examineUser;
    }

    public void setExamineUser(String examineUser) {
        this.examineUser = examineUser;
    }

    public Integer getRunSystem() {
        return runSystem;
    }

    public void setRunSystem(Integer runSystem) {
        this.runSystem = runSystem;
    }

    public Integer getExcludeFault() {
        return excludeFault;
    }

    public void setExcludeFault(Integer excludeFault) {
        this.excludeFault = excludeFault;
    }

    public Long[] getRepairUserIds() {
        return repairUserIds;
    }

    public void setRepairUserIds(Long[] repairUserIds) {
        this.repairUserIds = repairUserIds;
    }

    public Integer getOptionState() {
        return optionState;
    }

    public void setOptionState(Integer optionState) {
        this.optionState = optionState;
    }

    public Integer[] getScreenCondition() {
        return screenCondition;
    }

    public void setScreenCondition(Integer[] screenCondition) {
        this.screenCondition = screenCondition;
    }

    public Long[] getExamineUsers() {
        return examineUsers;
    }

    public void setExamineUsers(Long[] examineUsers) {
        this.examineUsers = examineUsers;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public List<FaultRepairRecord> getFaultRepairRecordList() {
        return faultRepairRecordList;
    }

    public void setFaultRepairRecordList(List<FaultRepairRecord> faultRepairRecordList) {
        this.faultRepairRecordList = faultRepairRecordList;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }
}
