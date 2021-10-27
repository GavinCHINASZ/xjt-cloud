package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: huanggc
 * @Date: 2019/11/01
 * @Description: 报表 实体类
 */
public class Report extends BaseEntity {
    // 建筑消防设施故障维修记录表(表头)
    // 建筑消防设施巡查记录表(表头)

    private String reportName;// 报表名称(如: GB25201)
    private Long parentId;// 报表名称(如: GB25201)
    private String reportNo;// 报表编号 B1  C1  D1
    private Long reportPeriod;// 报表期数 201911(报表的日期)
    private Integer sortNo;// 排序 1 2 3 ......    sortField
    private String checkResult;// 检查结果 fdResult(正常, 故障, /)

    private Integer checkNormalNumber;// 正常数量
    private Integer checkFaultNumber;// 故障数量

    private String memo;// 备注

    private String fieldTreatment;// 当场处理情况
    private String faultDesc;// 报修情况
    private String parts;// 部位
    private String repairMethods;// 维修方法

    private Date findTime;// 发现时间
    private String findUser;// 发现人 finder

    private String handleMsg;// 消防安全人处理意见
    private Date repairTime;// 维修时间
    private String disableSystem;// 停用系统
    private String departmentRecord;// 是否消防部门备案
    private String protectiveMeasures;// 安全保护措施
    private String repairUser;// 维修人员 engineer
    private String troubleshooting;// 故障排除确认

    private Long taskId;// 任务ID
    private Long checkRecordId;// 巡检记录id
    private Long deviceId;// 设备ID  mtMaterialLoc
    private Long deviceCheckItemId;// 设备巡检项  CheckItem
    private Long reportSystemId;// 报表项

    private Integer deviceNum;// 设备数量
    private Integer normalNum;// 正常数量
    private String checkPointName;

    private Integer systemSortNo;// 报表项 排序
    private String itemIds;// 设备项ID

    // 以下字段数据库表中不存在
    private String period;// 时间2019-11
    private String itemName;
    private String rsName;
    private String systemName;
    private String buildingName;//建筑物名称
    private String floorName;//楼层名

    private List<Long> reportItemList;

    private String downType;// doc pdf html
    private Integer checkItemVsType;//项目的巡检项类型，1默认 2精简版 3项目自定义

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public String getDeviceNumDesc() {
        if (null == deviceNum || deviceNum < 1){
            return  "/";
        }
        return deviceNum.toString();
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getNormalNum() {
        return normalNum;
    }

    public String getNormalNumDesc() {
        if (null == normalNum || normalNum < 1){
            return "/";
        }
        return normalNum.toString();
    }

    public void setNormalNum(Integer normalNum) {
        this.normalNum = normalNum;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBuildingName() {
        if (StringUtils.isEmpty(buildingName)){
            return "";
        }
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        if (StringUtils.isEmpty(floorName)){
            return "/";
        }
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public Long getReportPeriod() {
        return reportPeriod;
    }

    public void setReportPeriod(Long reportPeriod) {
        this.reportPeriod = reportPeriod;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckNormalNumber() {
        return checkNormalNumber;
    }

    public void setCheckNormalNumber(Integer checkNormalNumber) {
        this.checkNormalNumber = checkNormalNumber;
    }

    public Integer getCheckFaultNumber() {
        return checkFaultNumber;
    }

    public void setCheckFaultNumber(Integer checkFaultNumber) {
        this.checkFaultNumber = checkFaultNumber;
    }

    public String getMemo() {
        if (StringUtils.isEmpty(memo)){
            return "/";
        }
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFieldTreatment() {

        return "/";
    }

    public void setFieldTreatment(String fieldTreatment) {
        this.fieldTreatment = fieldTreatment;
    }

    public String getFaultDesc() {
        return "/";
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getRepairMethods() {
        return repairMethods;
    }

    public void setRepairMethods(String repairMethods) {
        this.repairMethods = repairMethods;
    }

    public Date getFindTime() {
        return findTime;
    }

    public void setFindTime(Date findTime) {
        this.findTime = findTime;
    }

    public String getFindUser() {
        return findUser;
    }

    public void setFindUser(String findUser) {
        this.findUser = findUser;
    }

    public String getHandleMsg() {
        return handleMsg;
    }

    public void setHandleMsg(String handleMsg) {
        this.handleMsg = handleMsg;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getDisableSystem() {
        return disableSystem;
    }

    public void setDisableSystem(String disableSystem) {
        this.disableSystem = disableSystem;
    }

    public String getDepartmentRecord() {
        return departmentRecord;
    }

    public void setDepartmentRecord(String departmentRecord) {
        this.departmentRecord = departmentRecord;
    }

    public String getProtectiveMeasures() {
        return protectiveMeasures;
    }

    public void setProtectiveMeasures(String protectiveMeasures) {
        this.protectiveMeasures = protectiveMeasures;
    }

    public String getRepairUser() {
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    public String getTroubleshooting() {
        return troubleshooting;
    }

    public void setTroubleshooting(String troubleshooting) {
        this.troubleshooting = troubleshooting;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceCheckItemId() {
        return deviceCheckItemId;
    }

    public void setDeviceCheckItemId(Long deviceCheckItemId) {
        this.deviceCheckItemId = deviceCheckItemId;
    }

    public Long getReportSystemId() {
        return reportSystemId;
    }

    public void setReportSystemId(Long reportSystemId) {
        this.reportSystemId = reportSystemId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRsName() {
        return rsName;
    }

    public void setRsName(String rsName) {
        this.rsName = rsName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDownType() {
        return downType;
    }

    public void setDownType(String downType) {
        this.downType = downType;
    }

    public Integer getSystemSortNo() {
        return systemSortNo;
    }

    public void setSystemSortNo(Integer systemSortNo) {
        this.systemSortNo = systemSortNo;
    }

    public List<Long> getReportItemList() {
        return reportItemList;
    }

    public void setReportItemList(List<Long> reportItemList) {
        this.reportItemList = reportItemList;
    }
}
