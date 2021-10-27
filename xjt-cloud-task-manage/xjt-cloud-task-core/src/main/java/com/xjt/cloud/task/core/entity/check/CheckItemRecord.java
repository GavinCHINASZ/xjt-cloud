package com.xjt.cloud.task.core.entity.check;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

/**
 * 巡查信息, 巡查项
 *
 * @Author dwt
 * @Date 2019-07-25 10:11
 * @Description TODO
 * @Version 1.0
 */
public class CheckItemRecord extends BaseEntity {
    //巡检记录第id
    private Long checkRecordId;
    //任务id
    private Long taskId;
    private Long[] taskIds;
    private Long taskParentId;
    private Integer taskStatus;
    private Integer downType;// 1 doc,  2 excel,  3 pdf
    //是否是数字0：是，1：否
    private Integer isNum;
    //备注
    private String remark;
    //结果描述
    private String resultDescription;
    private String description;
    //巡检结果0：正常，1：故障
    private Integer checkResult;

    private Long deviceTypeId;// 设备类型ID
    private Long[] deviceTypeIds;// 设备类型ID
    private String deviceSysName;// 设备系统名称
    private Integer[] deviceDateType;// 设备巡检时间类型 1月度,2季度,3年度

    //类型  1、默认  4、多端项  5、带自动检测的项
    private Integer type;

    //设备id
    private Long deviceId;
    //设备名称
    private String deviceName;
    //巡检最大值
    private Integer maxValue;
    //巡检最小值
    private Integer minValue;
    //巡检项id
    private Long checkId;
    //巡检项名称
    private String checkName;
    //巡检值
    private String checkValue;
    private String checkValues;
    //巡检点id
    private Long checkPointId;
    //巡检类型0:默认 1：数值  2：计数  3：描述  4地铁抽检
    private Integer checkType;
    //检查标准
    private String checkSpecification;
    //单位
    private String unit;
    //排序
    private Integer sort;
    //终端编号
    private String terminalCode;
    //地址
    private String address;
    // 巡检项的项目类型，1默认 2精简版 3项目自定义
    private Integer checkItemVsType;

    private Long deviceFaultTypeId;
    private String deviceFaultType;

    // d_device_fault_type.fault_type
    private String faultType;

    public CheckItemRecord() {

    }

    public CheckItemRecord(CheckItemTask checkItemTask) {
        this.checkId = checkItemTask.getId();
        this.checkType = checkItemTask.getCheckType();
        this.checkName = checkItemTask.getCheckName();
        this.maxValue = checkItemTask.getMaxValue();
        this.minValue = checkItemTask.getMinValue();
        this.checkSpecification = checkItemTask.getCheckSpecification();
        this.unit = checkItemTask.getUnit();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckSpecification() {
        if (StringUtils.isEmpty(checkSpecification)) {
            return "";
        }
        return checkSpecification;
    }

    public void setCheckSpecification(String checkSpecification) {
        this.checkSpecification = checkSpecification;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
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

    public Long[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long[] taskIds) {
        this.taskIds = taskIds;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }

    public Long getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(Long taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getIsNum() {
        return isNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setIsNum(Integer isNum) {
        this.isNum = isNum;
    }

    public String getRemark() {
        if (StringUtils.isEmpty(remark)) {
            return "";
        }
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResultDescription() {
        if (StringUtils.isEmpty(resultDescription)) {
            return "";
        }
        return resultDescription;
    }

    public String getResultDescriptionDesc() {
        if (StringUtils.isEmpty(resultDescription)) {
            return "/";
        }
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionDesc() {
        if (StringUtils.isNotEmpty(resultDescription)) {
            return resultDescription;
        }

        if (StringUtils.isEmpty(description)) {
            return "/";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCheckResult() {
        if (null == checkResult) {
            return -1;
        }
        return checkResult;
    }

    public String getCheckResultDesc() {
        if (null != checkResult && 0 == checkResult) {
            return "☑ 符合";
        }
        return "☐ 符合";
    }

    public String getCheckResultDescs() {
        if (null != checkResult && 1 == checkResult) {
            return "☑ 不符";
        }
        return "☐ 不符";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long[] getDeviceTypeIds() {
        return deviceTypeIds;
    }

    public void setDeviceTypeIds(Long[] deviceTypeIds) {
        this.deviceTypeIds = deviceTypeIds;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Integer[] getDeviceDateType() {
        return deviceDateType;
    }

    public void setDeviceDateType(Integer[] deviceDateType) {
        this.deviceDateType = deviceDateType;
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

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public String getCheckName() {
        if (StringUtils.isEmpty(checkName)) {
            return "";
        }
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckValue() {
        if (StringUtils.isEmpty(checkValue)) {
            return "";
        }
        return checkValue;
    }

    public String getCheckValueDesc() {
        if (StringUtils.isEmpty(checkValue)) {
            return "/";
        }
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getCheckValues() {
        if (StringUtils.isEmpty(checkValues)) {
            return "";
        }
        return checkValues;
    }

    public String getCheckValuesDesc() {
        if (StringUtils.isEmpty(checkValues)) {
            return "/";
        }
        return checkValues;
    }

    public void setCheckValues(String checkValues) {
        this.checkValues = checkValues;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getDeviceFaultType() {
        return deviceFaultType;
    }

    public void setDeviceFaultType(String deviceFaultType) {
        this.deviceFaultType = deviceFaultType;
    }
}
