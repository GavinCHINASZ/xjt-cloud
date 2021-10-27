package com.xjt.cloud.project.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.project.core.entity.project.Log;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:29
 * @Description:巡检项信息实体类
 */
public class CheckItem extends BaseEntity {
    private String checkName;//巡检项名称
    private Long deviceTypeId;//设备类型iD
    private String deviceTypeName;//设备类型名称
    private Long deviceSysId;//设备系统iD
    private String deviceSysName;//设备系统名称
    private String checkSpecification;//检查标准
    private Integer minValue;//最小值 两位小数 * 100 转成整数
    private Integer maxValue;//最大值 两位小数 * 100 转成整数
    private String unit;//	单位
    private Integer periodType;//周期类型
    private String itemAvailableResults;//巡检项可能结果
    private Integer checkType;//巡检类型0:默认 1：数值  2：计数  3：描述
    private String description;//描述
    private Integer checkAction;//巡检项类型1:巡检 2:测试 3:保养
    private Integer type;//巡检项类型　默认1　一般项　　2大小值项
    private String code;//类型代码
    private String grade;//等级ＡＢＣ
    private Long[] deviceSysIds;//检测系统id
    private Long checkProjectId;//检测项目id
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }


    public String getCheckSpecification() {
        return checkSpecification;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public void setCheckSpecification(String checkSpecification) {
        this.checkSpecification = checkSpecification;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCheckAction() {
        return checkAction;
    }

    public void setCheckAction(Integer checkAction) {
        this.checkAction = checkAction;
    }

    public String getItemAvailableResults() {
        return itemAvailableResults;
    }

    public void setItemAvailableResults(String itemAvailableResults) {
        this.itemAvailableResults = itemAvailableResults;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long[] getDeviceSysIds() {
        return deviceSysIds;
    }

    public void setDeviceSysIds(Long[] deviceSysIds) {
        this.deviceSysIds = deviceSysIds;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
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

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }
}
