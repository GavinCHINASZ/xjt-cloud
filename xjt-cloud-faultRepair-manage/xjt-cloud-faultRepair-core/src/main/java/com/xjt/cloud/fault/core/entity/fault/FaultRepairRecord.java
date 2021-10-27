package com.xjt.cloud.fault.core.entity.fault;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 故障报修 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class FaultRepairRecord extends BaseEntity {
    private Long[] projectIds;
    private Integer totalCount;
    private Boolean permissionAll;
    private Long[] ids;
    private Long deviceId;// 设备id(数据库表的id)
    private String deviceName;// 设备名称
    private String deviceImageUrl;// 设备图片logo
    private String deviceQrNo;// 设备码(页面显示的设备ID)
    private Integer deviceCount;// 设备数量
    private String deviceLocation;// 设备位置(建筑物+楼层+具体位置)
    private String deviceImgUrl;// 设备图片

    private Long checkPointId;// 巡查点ID
    private String checkPointQrNo;// 巡查码(页面显示的 巡查点ID)
    private String checkPointName;// 巡查名称

    private Long checkRecordId;// 巡检记录ID(数据库表的id)
    private Long taskId;// 任务ID

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
    private Integer workOrderStatus;// 工单状态:  所有待办(1待指派,  2维修中,  3审核中); 所有完成: 4已完成  5不予处理
    private String workOrderDescription;// 工单描述
    private Date startHandlingTime;// 故障处理开始时间
    private Date endHandlingTime;// 故障处理结束时间

    private Integer overdue;// 是否超期:1是  2否(已超过处理结束时间)
    private Integer[] overdues;// 是否超期:1是  2否(已超过处理结束时间)

    private Integer fromType;// 故障来源 1、APP内报修  2、巡检记录报修（巡查）  3、微信报修
    private String auditOpinion;// 审核意见
    private String openId;// 微信传

    private Long organizationId;// 部门ID
    private String orgName;// 部门名称(报修人所在的部门)
    private Long orgUserId;// 项目内成员ID

    private String repairUser;// 维修人
    private Long[] repairUserIds;// 维修人(orgUserId)
    private Long[] repairUserId;// 维修人 userId

    private String examineUser;// 审核人
    private Long[] examineUsers;// 审核人
    private Long[] examineUserId;// 审核人 userId
    private Integer faultHandlerNum;// 大于0有权限操作

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
    private Integer normalNum;
    private String createTimes;

    //1:hour, 2:day, 3:month, 4:year
    private Integer dateType;
    private String timeType;
    private Date startTime;
    private Date endTime;
    private Integer dateNum;
    private Integer deviceNum;

    /**
     * 查询时间: 年月
     */
    private String queryDate;
    private String appId;

    private List<FaultRepairRecord> faultRepairRecordList;

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getCreateTimes() {
        return createTimes;
    }

    public void setCreateTimes(String createTimes) {
        this.createTimes = createTimes;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

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

    public String getDeviceLocationDesc() {
        return getBuildingName() + getFloorName() + getDeviceLocation();
    }

    public String getDeviceLocation() {
        if (StringUtils.isEmpty(deviceLocation)) {
            return "";
        }
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getDeviceImgUrl() {
        return deviceImgUrl;
    }

    public void setDeviceImgUrl(String deviceImgUrl) {
        this.deviceImgUrl = deviceImgUrl;
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

    public String getFaultDescription() {
        return faultDescription;
    }

    public String getFaultDescriptionDesc() {
        if (StringUtils.isNotEmpty(faultDescription)) {
            String[] split = faultDescription.split("xjtgzbx;");
            if (split.length > 0) {
                return split[0];
            }
        }
        return "/";
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getFaultLocation() {
        return faultLocation;
    }

    public String getFaultLocationDesc() {
        if (StringUtils.isNotEmpty(faultLocation)) {
            String[] split = faultLocation.split("xjtgzbx;");
            return split[0];
        }
        return "/";
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

    public String getMaintenanceMethodDesc() {
        if (StringUtils.isEmpty(maintenanceMethod)) {
            return "/";
        }
        return maintenanceMethod;
    }

    public void setMaintenanceMethod(String maintenanceMethod) {
        this.maintenanceMethod = maintenanceMethod;
    }

    public String getMaintenanceResult() {
        if (StringUtils.isEmpty(maintenanceResult)) {
            return "";
        }
        return maintenanceResult;
    }

    public void setMaintenanceResult(String maintenanceResult) {
        this.maintenanceResult = maintenanceResult;
    }

    public String getSecurityMeasures() {
        if (StringUtils.isEmpty(securityMeasures)) {
            return "";
        }
        return securityMeasures;
    }

    public String getSecurityMeasuresDesc() {
        if (StringUtils.isEmpty(securityMeasures)) {
            return "/";
        }
        return securityMeasures;
    }

    public void setSecurityMeasures(String securityMeasures) {
        this.securityMeasures = securityMeasures;
    }

    public Integer getIsStopSystem() {
        return isStopSystem;
    }

    public String getIsStopSystemDesc() {
        if (null != isStopSystem && 1 == isStopSystem) {
            return "停";
        }
        return "否";
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
        if (workOrderStatus != null) {
            switch (workOrderStatus) {
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

    public String getWorkOrderStatusDescs() {
        if (null != workOrderStatus) {
            if (4 == workOrderStatus || 5 == workOrderStatus) {
                return "已修复";
            }
        }
        return "未修复";
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getWorkOrderDescription() {
        if (StringUtils.isEmpty(workOrderDescription)) {
            return "";
        }
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
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

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getOverdueDesc() {
        if (this.endHandlingTime != null) {
            Date date = new Date();
            if (date.getTime() > this.endHandlingTime.getTime()) {
                return "是";
            }
        }
        return "否";
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

    public String getRepairUserDesc() {
        if (StringUtils.isEmpty(repairUser)) {
            return "/";
        }
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    public String getExamineUser() {
        return examineUser;
    }

    public String getExamineUserDesc() {
        if (StringUtils.isEmpty(examineUser)) {
            return "/";
        }
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

    public Long[] getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(Long[] repairUserId) {
        this.repairUserId = repairUserId;
    }

    public Long[] getExamineUserId() {
        return examineUserId;
    }

    public void setExamineUserId(Long[] examineUserId) {
        this.examineUserId = examineUserId;
    }

    public Integer getFaultHandlerNum() {
        return faultHandlerNum;
    }

    public void setFaultHandlerNum(Integer faultHandlerNum) {
        this.faultHandlerNum = faultHandlerNum;
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

    public Integer getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(Integer normalNum) {
        this.normalNum = normalNum;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
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

    public Integer getDateNum() {
        return dateNum;
    }

    public void setDateNum(Integer dateNum) {
        this.dateNum = dateNum;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingName() {
        buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
        return buildingName;
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getFloorName() {
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }
}
