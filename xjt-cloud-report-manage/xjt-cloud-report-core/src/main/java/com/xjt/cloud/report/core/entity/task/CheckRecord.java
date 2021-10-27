package com.xjt.cloud.report.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CheckRecord 巡检记录
 * @Author dwt
 * @Date 2019-07-25 9:30
 * @Description:定时任务生成的巡检记录
 * @Version 1.0
 */
public class CheckRecord extends BaseEntity {
    //任务id
    private Long taskId;
    //任务名称
    private String taskName;
    //任务父id
    private Long taskParentId;
    //项目id
    private Long projectId;
    //设备id
    private Long deviceId;
    //巡检结果 0：正常，1：故障
    private Integer checkResult;
    //部门名称
    private String orgName;
    //任务状态 0:正常,1:异常,2:未检
    private Integer deviceStatus;
    //设备二维码
    private String deviceQrNo;
    //巡更点二维码
    private String checkPointQrNo;
    //巡检人名称
    private String checkerName;
    //巡检点id
    private Long checkPointId;
    //设备名称
    private String deviceName;
    //建筑物名称
    private String buildingName;
    //楼层名称
    private String floorName;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //街道
    private String roadNo;
    //开始日期
    private Date startDate;
    //结束日期
    private Date endDate;
    //设备巡检项
    private List<AppTaskCheckItem> appTaskCheckItemList;
    private String userName;
    private Integer taskStatus;




    private Integer taskType;//任务类型 0巡查任务，1检查任务，2保养任务
    private Long[] ids;
    private  Long[] deviceCheckItemIds;// LK_MT_CHECK_ITEM
    private String reportName;
    private String reportNo;
    private String sysName;
    private String checkItem;
    private String startTime;
    private String endTime;
    private String mtItem;
    private String mtItemId;
    // 是否数值
    private Boolean dataValue;
    private String resultDescription;
    // 当前巡检的最大值
    private Double max;
    // 当前巡检的最小值
    private Double min;
    // 实测值
    private Double checkValue;
    private Double maxValue;
    private Double minValue;
    private Integer checkType;

    public CheckRecord(){

    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    //巡检结果 0：正常，1：故障
    public String getCheckResultDesc() {
        if (checkResult == 0){
            return  "正常";
        }
        return "故障";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
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

    public List<AppTaskCheckItem> getAppTaskCheckItemList() {
        return appTaskCheckItemList;
    }

    public void setAppTaskCheckItemList(List<AppTaskCheckItem> appTaskCheckItemList) {
        this.appTaskCheckItemList = appTaskCheckItemList;
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

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMtItem() {
        return mtItem;
    }

    public void setMtItem(String mtItem) {
        this.mtItem = mtItem;
    }

    public String getMtItemId() {
        return mtItemId;
    }

    public void setMtItemId(String mtItemId) {
        this.mtItemId = mtItemId;
    }

    public Boolean getDataValue() {
        return dataValue;
    }

    public void setDataValue(Boolean dataValue) {
        this.dataValue = dataValue;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(Double checkValue) {
        this.checkValue = checkValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Long[] getDeviceCheckItemIds() {
        return deviceCheckItemIds;
    }

    public void setDeviceCheckItemIds(Long[] deviceCheckItemIds) {
        this.deviceCheckItemIds = deviceCheckItemIds;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}
