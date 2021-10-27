package com.xjt.cloud.admin.manage.entity.device;

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
    private Float minValueFloat;//最小值 两位小数 * 100 转成整数
    private Float maxValueFloat;//最大值 两位小数 * 100 转成整数
    private String unit;//	单位
    private Integer periodType;//周期类型
    private String itemAvailableResults;//巡检项可能结果
    private Integer checkType;//巡检类型0:默认 1：数值  2：计数  3：描述
    private String description;//描述
    private Integer checkAction;//巡检项类型1:巡检 2:测试 3:保养
    private Integer type;//巡检项类型　默认1　一般项　　2供水端 3系统端
    private Integer checkItemVsType;//巡检项的项目类型，1默认 2精简版 3项目自定义
    private Long deviceSysId;//系统id
    private String deviceSysName;//设备系统名称
    private String deviceTypeName;//设备类型名称
    private String projectName;//项目名称
    private String ids;
    private Long saveProjectId;
    private Integer itemIndex;//默认巡检项索引为巡检项主键，非默认巡检项为对应的默认巡检项索引，用于与国标报表关联
    private boolean notProjectItem;//是否查询未关联到项目的巡检项

    public boolean isNotProjectItem() {
        return notProjectItem;
    }

    public void setNotProjectItem(boolean notProjectItem) {
        this.notProjectItem = notProjectItem;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    public Long getSaveProjectId() {
        return saveProjectId;
    }

    public void setSaveProjectId(Long saveProjectId) {
        this.saveProjectId = saveProjectId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
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

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
        if (null != minValueFloat){
            return (int) (minValueFloat * 100.00F);
        }
        return null;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        if (null != maxValueFloat){
            return (int) (maxValueFloat * 100.00F);
        }
        return null;
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

    public Float getMinValueFloat() {
        if (null != minValue){
            return minValue / 100.00f;
        }
        return null;
    }

    public void setMinValueFloat(Float minValueFloat) {
        this.minValueFloat = minValueFloat;
    }

    public Float getMaxValueFloat() {
        if (null != maxValue){
            return maxValue / 100.00f;
        }
        return null;
    }

    public void setMaxValueFloat(Float maxValueFloat) {
        this.maxValueFloat = maxValueFloat;
    }
}
