package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * 计划管理报表
 *
 * @author huanggc
 * @date 2019-07-29 15:15
 */
public class PlanManagementReport extends BaseEntity {
    private Long[] ids;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 计划总数
     */
    private Integer planCount;
    /**
     * 未开始数
     */
    private Integer notStartedCount;

    /**
     * 执行中
     */
    private Integer implementCount;

    /**
     * 待生成报告
     */
    private Integer stayReportCount;

    /**
     * 已完成数
     */
    private Integer completeCount;

    /**
     * 盖章数
     */
    private Integer sealCount;

    private Integer totalCheckNum;//检测项数量
    private Integer checkNum;//已检测数量
    private String deviceSysName;//设备系统
    private Long deviceSysId;//设备系统id
    private String deviceTypeName;//设备类型
    private Long deviceTypeId;//设备类型id
    private Long checkItemId;//巡检项id
    private String checkName;//巡检项名称
    private Integer checkType;//巡检类型 1维保 2巡检 3测试 4保养
    private String address;//'具体地址',

    //'建筑物id',
    private Long buildingId;
    //楼层id
    private Long buildingFloorId;
    // 建筑物名称
    private String buildingName;
    private String floorName;//楼层名称

    private String createUserName;//操作员
    private String completePercentage;
    private Date createTime;//创建时间

    private Integer checkResult;//'检测结果1合格 2不合格 3无',
    private Long checkRecordId;//记录id
    private Integer treatmentMeasures;//1现场 解决  2故障报修
    private String checkImgUrls;//巡检图片 以；分隔
    private String causeFailure;//故障原因及处理

    /**
     * 巡查时间
     */
    private String checkDate;

    public Integer getTreatmentMeasures() {
        return treatmentMeasures;
    }

    public void setTreatmentMeasures(Integer treatmentMeasures) {
        this.treatmentMeasures = treatmentMeasures;
    }

    public String getCheckImgUrls() {
        return checkImgUrls;
    }

    public void setCheckImgUrls(String checkImgUrls) {
        this.checkImgUrls = checkImgUrls;
    }

    public String getCauseFailure() {
        return causeFailure;
    }

    public void setCauseFailure(String causeFailure) {
        this.causeFailure = causeFailure;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Long checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
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

    public String getDeviceTypeName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceTypeId(), "deviceSysName");
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Integer getTotalCheckNum() {
        return totalCheckNum;
    }

    public void setTotalCheckNum(Integer totalCheckNum) {
        this.totalCheckNum = totalCheckNum;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getDeviceSysName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceSysId(), "deviceSysName");
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return CacheUtils.getCacheValueByTypeAndId("maintenance_" + Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return CacheUtils.getCacheValueByTypeAndId("maintenance_" + Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    public String getCreateUserName() {
        return createUserName;
    }

    @Override
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getNotStartedCount() {
        return notStartedCount;
    }

    public void setNotStartedCount(Integer notStartedCount) {
        this.notStartedCount = notStartedCount;
    }

    public Integer getImplementCount() {
        return implementCount;
    }

    public void setImplementCount(Integer implementCount) {
        this.implementCount = implementCount;
    }

    public Integer getStayReportCount() {
        return stayReportCount;
    }

    public void setStayReportCount(Integer stayReportCount) {
        this.stayReportCount = stayReportCount;
    }

    public Integer getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(Integer completeCount) {
        this.completeCount = completeCount;
    }

    public Integer getSealCount() {
        return sealCount;
    }

    public void setSealCount(Integer sealCount) {
        this.sealCount = sealCount;
    }

    public String getCompletePercentage() {
        if (planCount != null && completeCount != null){
            // 保留后两位小数
            String format = String.format("%.2f", (float) completeCount / (float) planCount * 100);
            return format + "%";
        }
        return "0.00%";
    }

    public void setCompletePercentage(String completePercentage) {
        this.completePercentage = completePercentage;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
}
