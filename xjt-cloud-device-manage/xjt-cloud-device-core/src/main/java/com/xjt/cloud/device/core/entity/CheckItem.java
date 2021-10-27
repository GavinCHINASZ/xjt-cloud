package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:29
 * @Description:巡检项信息实体类
 */
public class CheckItem extends BaseEntity {
    private String checkName;//巡检项名称
    private Long deviceTypeId;//设备系统iD
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
    private Integer checkItemVsType;//项目的巡检项类型，1默认 2精简版 3项目自定义

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
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
}
