package com.xjt.cloud.safety.core.entity.fault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 故障报修 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class FaultRepairRecord extends BaseEntity {
    // 1查看所有
    private Integer haveAll;

    private Long[] ids;
    private Long[] projectIds;
    // 项目名称  单位名称
    private String projectName;

    // 设备系统id
    private Long deviceSysId;
    // 设备系统名称
    private String deviceSysName;

    // 设备id(数据库表的id)
    private Long deviceId;
    // 设备名称
    private String deviceName;
    // 故障描述(故障原因)
    private String faultDescription;

    /**
     * 工单状态:  2维修中,  4已完成, 6暂停
     */
    private Integer workOrderStatus;
    private Integer[] workOrderStatusArr;

    // 故障类别
    private String faultType;
    private Long faultTypeId;

    /**
     * 是否上报: 1是, 2否
     */
    private Integer handIn;

    /**
     * 紧急程度: 1一般, 2紧急, 3特急
     */
    private Integer urgentDegree;

    // 故障图片
    private String imageUrl;

    // 建筑物id
    private Long buildingId;
    // 楼层id
    private Long buildingFloorId;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    /**
     * 报修人电话
     */
    private String createUserIdPhone;

    /**
     * 维修人电话
     */
    private String maintenancePhone;

    /**
     * 维修人ID
     */
    private Long maintenanceUserId;
    private String maintenanceUserName;
    /**
     * 维修说明
     */
    private String maintenanceResult;
    /**
     * 维修图片url
     */
    private String maintenanceImageUrl;

    // 维修人 userId
    private Long repairUserId;

    private Date startTime;
    private Date endTime;

    public Integer getHaveAll() {
        return haveAll;
    }

    public void setHaveAll(Integer haveAll) {
        this.haveAll = haveAll;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
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

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Integer[] getWorkOrderStatusArr() {
        return workOrderStatusArr;
    }

    public void setWorkOrderStatusArr(Integer[] workOrderStatusArr) {
        this.workOrderStatusArr = workOrderStatusArr;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public Integer getHandIn() {
        return handIn;
    }

    public void setHandIn(Integer handIn) {
        this.handIn = handIn;
    }

    public Integer getUrgentDegree() {
        return urgentDegree;
    }

    public void setUrgentDegree(Integer urgentDegree) {
        this.urgentDegree = urgentDegree;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
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
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY , buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }

    public String getCreateUserIdPhone() {
        return createUserIdPhone;
    }

    public void setCreateUserIdPhone(String createUserIdPhone) {
        this.createUserIdPhone = createUserIdPhone;
    }

    public String getMaintenancePhone() {
        return maintenancePhone;
    }

    public void setMaintenancePhone(String maintenancePhone) {
        this.maintenancePhone = maintenancePhone;
    }

    public Long getMaintenanceUserId() {
        return maintenanceUserId;
    }

    public void setMaintenanceUserId(Long maintenanceUserId) {
        this.maintenanceUserId = maintenanceUserId;
    }

    public String getMaintenanceUserName() {
        return maintenanceUserName;
    }

    public void setMaintenanceUserName(String maintenanceUserName) {
        this.maintenanceUserName = maintenanceUserName;
    }

    public String getMaintenanceResult() {
        return maintenanceResult;
    }

    public void setMaintenanceResult(String maintenanceResult) {
        this.maintenanceResult = maintenanceResult;
    }

    public String getMaintenanceImageUrl() {
        return maintenanceImageUrl;
    }

    public void setMaintenanceImageUrl(String maintenanceImageUrl) {
        this.maintenanceImageUrl = maintenanceImageUrl;
    }

    public Long getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(Long repairUserId) {
        this.repairUserId = repairUserId;
    }
}
