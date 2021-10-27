package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

/**
 * 设备系统与类型管理实体类
 *
 * @author wangzhiwen
 * @date 2019/7/17 14:51
 */
public class DeviceType extends BaseEntity {
    private Long parentId; //父id,
    private Long[] parentIds; //父id,

    private String deviceSysName; //名称
    private String useMethod;//功能和使用方法
    private String description;//描述
    private Integer pressureMonitor;//是否具有压力监测或液位监测 1 有 0无
    private Integer sysPressure;//系统端压力 1有 0无
    private Integer type;//设备系统类型 1 设备系统  9设备类型
    private String pinYinInitials;//拼音首写字母
    private String imgUrl;//图片路径
    private Integer checkItemVsType;//项目的巡检项类型，1默认 2精简版 3项目自定义

    // 后台管理--数据词典--设备系统类型 配置
    // 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备 15多端设备 16吸气式烟雾探测器 17微型消防站 20声光报警
    // 地铁: 20FAS系统,  21气体灭火控制系统,  22感温光纤测温系统,  23电气火灾监控系统
    private Integer deviceType;
    private Integer minValue;//最小值 * 100
    private Integer maxValue;//最大值 * 100
    private Integer minValue2;//最小值 * 100
    private Integer maxValue2;//最大值 * 100
    private Integer itemType;//巡检项类型　默认1　一般项　　2大小值项
    private Integer[] deviceTypes;//查询设备类型数组
    private Long buildingId;// 建筑物id
    private Long buildingFloorId;// 楼层id
    private String unit;//单位
    private String limitConfig;//限制设置
    private Long checkPointId;
    private Long iotId;
    private Long orgId;//部门id
    private String downType;// doc pdf html
    private String checkName;// 检查项名称
    private String checkSpecification;// 检查标准
    private Integer pointLayout;//是否查询巡检点布点信息 0/null不查询  1查询已布点  2查询未布点


    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getPointLayout() {
        return pointLayout;
    }

    public void setPointLayout(Integer pointLayout) {
        this.pointLayout = pointLayout;
    }

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }

    public Long[] getParentIds() {
        return parentIds;
    }

    public void setParentIds(Long[] parentIds) {
        this.parentIds = parentIds;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(String useMethod) {
        this.useMethod = useMethod;
    }

    public String getDescription() {
        if(StringUtils.isEmpty(description)){
            return "/";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPressureMonitor() {
        return pressureMonitor;
    }

    public void setPressureMonitor(Integer pressureMonitor) {
        this.pressureMonitor = pressureMonitor;
    }

    public Integer getSysPressure() {
        return sysPressure;
    }

    public void setSysPressure(Integer sysPressure) {
        this.sysPressure = sysPressure;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getPinYinInitials() {
        return pinYinInitials;
    }

    public void setPinYinInitials(String pinYinInitials) {
        this.pinYinInitials = pinYinInitials;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLimitConfig() {
        return limitConfig;
    }

    public void setLimitConfig(String limitConfig) {
        this.limitConfig = limitConfig;
    }

    public Integer getMinValue2() {
        return minValue2;
    }

    public void setMinValue2(Integer minValue2) {
        this.minValue2 = minValue2;
    }

    public Integer getMaxValue2() {
        return maxValue2;
    }

    public void setMaxValue2(Integer maxValue2) {
        this.maxValue2 = maxValue2;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public String getDownType() {
        return downType;
    }

    public void setDownType(String downType) {
        this.downType = downType;
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

    public void setCheckSpecification(String checkSpecification) {
        this.checkSpecification = checkSpecification;
    }
}
