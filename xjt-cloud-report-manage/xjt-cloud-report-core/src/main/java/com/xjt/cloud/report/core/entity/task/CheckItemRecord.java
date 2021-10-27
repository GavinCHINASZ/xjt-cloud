package com.xjt.cloud.report.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName CheckItemRecord 巡检项记录  对应旧系统=MtCheckResult(巡检结果)
 * @Author dwt
 * @Date 2019-07-25 10:11
 * @Description TODO
 * @Version 1.0
 */
public class CheckItemRecord extends BaseEntity {
    //巡检记录第id
    private Long checkRecordId;
    //项目id
    private Long projectId;
    //任务id
    private Long taskId;
    //是否是数字0：是，1：否
    private Integer isNum;
    //备注
    private String  remark;
    //结果描述
    private String resultDescription;
    //巡检结果0：正常，1：故障
    private Integer checkResult;
    //设备id
    private Long deviceId;
    //设备名称
    private String deviceName;
    //巡检最大值
    private Integer checkMaxValue;
    //巡检最小值
    private Integer checkMinValue;
    //巡检项id
    private Long checkItemId;
    //巡检项名称
    private String checkItemName;
    //巡检值
    private String checkValue;
    //巡检点id
    private Long checkPointId;

    private String reportNo;



    // 是否数值
    private Boolean dataValue;
    private Integer taskType;// 任务类型
    private Long[] ids;
    private Integer checkType;// ??? 巡检方式ci.check_type checkType

    private Double maxValues;
    private Double minValues;

    public CheckItemRecord(){

    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getIsNum() {
        return isNum;
    }

    public void setIsNum(Integer isNum) {
        this.isNum = isNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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

    public Long getCheckItemId() {
        return checkItemId;
    }

    public void setCheckItemId(Long checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Integer getCheckMaxValue() {
        return checkMaxValue;
    }

    public void setCheckMaxValue(Integer checkMaxValue) {
        this.checkMaxValue = checkMaxValue;
    }

    public Integer getCheckMinValue() {
        return checkMinValue;
    }

    public void setCheckMinValue(Integer checkMinValue) {
        this.checkMinValue = checkMinValue;
    }

    public Boolean getDataValue() {
        return dataValue;
    }

    public void setDataValue(Boolean dataValue) {
        this.dataValue = dataValue;
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

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Double getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(Double maxValues) {
        this.maxValues = maxValues;
    }

    public Double getMinValues() {
        return minValues;
    }

    public void setMinValues(Double minValues) {
        this.minValues = minValues;
    }
}
