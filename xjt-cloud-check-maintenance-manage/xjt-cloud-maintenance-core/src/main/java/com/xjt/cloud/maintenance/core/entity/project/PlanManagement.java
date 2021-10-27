package com.xjt.cloud.maintenance.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.*;

import java.util.Date;

/**
 * 计划管理
 *
 * @author huanggc
 * @date 2019-07-29 15:15
 */
public class PlanManagement extends BaseEntity implements Cloneable {
    private Long[] ids;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户ID(checkProjectId=p_check_project.id)
     */
    private Long customerId;

    /**
     * 计划年份; 例:2021
     */
    private String planYear;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划类型: 月, 季, 半年, 年
     * ;分隔
     */
    private String planType;

    /**
     * 计划状态: 1未分配(未开始), 2已分配(执行中), 3待生成报告、4已完成, 5已盖章
     */
    private Integer planState;

    /**
     * 1项目负责人, 2维保人员, 3维保内容
     */
    private Integer planDataType;

    /**
     * 项目负责人
     */
    private String projectPerson;
    private String projectPersonIdStr;
    /**
     * 项目负责人ID
     */
    private Long[] projectPersonId;

    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 维保人员
     */
    private String maintenancePerson;
    private String maintenancePersonIdStr;
    private String projectPersonPhoneStr;
    /**
     * 维保人员id
     */
    private Long[] maintenancePersonId;

    /**
     * 维保内容
     */
    private String maintenanceContent;
    private String maintenanceContentId;
    private Long[] maintenanceContentIdArr;

    //维保情况简述
    private String maintenanceInfo;
    private String checkInfo;//巡检情况
    private String maintainInfo;//保养情况
    private Date checkDate;//巡检日期
    private Date testDate;//测试日期
    // 报告路径
    private String reportFileUrl;
    // 临时报告路径
    private String temReportFileUrl;

    /**
     * 报告编号
     */
    private String reportCode;

    /**
     * 标签url路径
     */
    private String checkLabelFileUrl;

    private Long deviceSysId;//设备系统id
    private Long deviceTypeId;//设备类型id

    /**
     * 项目编号
     * 报告编号
     */
    private String number;

    /**
     * 完成时间
     */
    private Date completeDate;

    private boolean findCount;//是否查询汇总

    //'建筑物id',
    private Long buildingId;
    //楼层id
    private Long buildingFloorId;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    private String limitSign;//以权限限制数据

    /**
     * 1APP, 2PC
     */
    private Integer clientType;

    public String getLimitSign() {
        return limitSign;
    }

    public void setLimitSign(String limitSign) {
        this.limitSign = limitSign;
    }

    public boolean isFindCount() {
        return findCount;
    }

    public void setFindCount(boolean findCount) {
        this.findCount = findCount;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getTemReportFileUrl() {
        return temReportFileUrl;
    }

    public void setTemReportFileUrl(String temReportFileUrl) {
        this.temReportFileUrl = temReportFileUrl;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getProjectPersonPhoneStr() {
        return projectPersonPhoneStr;
    }

    public void setProjectPersonPhoneStr(String projectPersonPhoneStr) {
        this.projectPersonPhoneStr = projectPersonPhoneStr;
    }

    public String getMaintenanceInfo() {
        if (StringUtils.isEmpty(maintenanceInfo)){
            return "";
        }
        return maintenanceInfo;
    }

    public void setMaintenanceInfo(String maintenanceInfo) {
        this.maintenanceInfo = maintenanceInfo;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getMaintainInfo() {
        return maintainInfo;
    }

    public void setMaintainInfo(String maintainInfo) {
        this.maintainInfo = maintainInfo;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getCustomerName() {
        if (StringUtils.isEmpty(customerName)){
            return "";
        }
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

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }

    public String getPlanName() {
        if (StringUtils.isEmpty(planName)){
            return "";
        }
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        if (StringUtils.isEmpty(planType)){
            return "";
        }
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public Integer getPlanDataType() {
        return planDataType;
    }

    public void setPlanDataType(Integer planDataType) {
        this.planDataType = planDataType;
    }

    public String getProjectPerson() {
        if (StringUtils.isEmpty(projectPerson)){
            return "";
        }
        return projectPerson;
    }

    public void setProjectPerson(String projectPerson) {
        this.projectPerson = projectPerson;
    }

    public Long[] getProjectPersonId() {
        return projectPersonId;
    }

    public void setProjectPersonId(Long[] projectPersonId) {
        this.projectPersonId = projectPersonId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateDesc() {
        return DateUtils.dateToY_M_DStrings(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateDesc() {
        return DateUtils.dateToY_M_DStrings(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMaintenancePerson() {
        if (StringUtils.isEmpty(maintenancePerson)){
            return "";
        }
        return maintenancePerson;
    }

    public void setMaintenancePerson(String maintenancePerson) {
        this.maintenancePerson = maintenancePerson;
    }

    public Long[] getMaintenancePersonId() {
        return maintenancePersonId;
    }

    public void setMaintenancePersonId(Long[] maintenancePersonId) {
        this.maintenancePersonId = maintenancePersonId;
    }

    public String getMaintenanceContent() {
        return maintenanceContent;
    }

    public void setMaintenanceContent(String maintenanceContent) {
        this.maintenanceContent = maintenanceContent;
    }

    public String getMaintenanceContentId() {
        return maintenanceContentId;
    }

    public void setMaintenanceContentId(String maintenanceContentId) {
        this.maintenanceContentId = maintenanceContentId;
    }

    public Long[] getMaintenanceContentIdArr() {
        return maintenanceContentIdArr;
    }

    public void setMaintenanceContentIdArr(Long[] maintenanceContentIdArr) {
        this.maintenanceContentIdArr = maintenanceContentIdArr;
    }

    public String getProjectPersonIdStr() {
        return projectPersonIdStr;
    }

    public void setProjectPersonIdStr(String projectPersonIdStr) {
        this.projectPersonIdStr = projectPersonIdStr;
        if (StringUtils.isNotEmpty(projectPersonIdStr)){
            String[] split = projectPersonIdStr.split(",");
            projectPersonId = ConvertUtils.stringToLong(split);
        }
    }

    public String getMaintenancePersonIdStr() {
        return maintenancePersonIdStr;
    }

    public void setMaintenancePersonIdStr(String maintenancePersonIdStr) {
        this.maintenancePersonIdStr = maintenancePersonIdStr;
        if (StringUtils.isNotEmpty(maintenancePersonIdStr)){
            String[] split = maintenancePersonIdStr.split(",");
            maintenancePersonId = ConvertUtils.stringToLong(split);
        }
    }

    public String getReportFileUrl() {
        return reportFileUrl;
    }

    public void setReportFileUrl(String reportFileUrl) {
        this.reportFileUrl = reportFileUrl;
    }

    public String getCheckLabelFileUrl() {
        return checkLabelFileUrl;
    }

    public void setCheckLabelFileUrl(String checkLabelFileUrl) {
        this.checkLabelFileUrl = checkLabelFileUrl;
    }

    public String getReportCode() {
        if (StringUtils.isEmpty(reportCode)){
            return "";
        }
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public String getCompleteDateDesc() {
        if (completeDate != null){
            return DateUtils.getDateYearMonthDays(completeDate);
        }
        return "";
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    @Override
    public PlanManagement clone() {
        PlanManagement planManagement = null;
        try{
            planManagement = (PlanManagement)super.clone();
        }catch(CloneNotSupportedException e) {
            SysLog.error(e);
        }
        return planManagement;
    }
}
