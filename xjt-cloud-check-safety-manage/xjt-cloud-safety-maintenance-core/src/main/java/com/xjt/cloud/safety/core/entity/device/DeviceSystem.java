package com.xjt.cloud.safety.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:51
 * @Description: 设备系统与类型管理实体类
 */
public class DeviceSystem extends BaseEntity {
    private Long parentId; //父id,
    private String deviceSysName; //名称
    private String useMethod;//功能和使用方法
    private String description;//描述
    private Integer pressureMonitor;//是否具有压力监测或液位监测 1 有 0无
    private Integer sysPressure;//系统端压力 1有 0无
    private Integer type;//设备系统类型 1 设备系统  9设备类型
    private String pinYinInitials;//拼音首写字母
    private String imgUrl;//图片路径
    private Integer deviceType;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
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
    private Long IotId;
    private List<Device> deviceList;
    private boolean isChecked;//是否选中
    private Long[]  sysTypeIdList;

    public Long[] getSysTypeIdList() {
        return sysTypeIdList;
    }

    public void setSysTypeIdList(Long[] sysTypeIdList) {
        this.sysTypeIdList = sysTypeIdList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getCheckDesc() {
        if (StringUtils.isNotEmpty(deviceSysName)){
            if (isChecked){
                return "√";
            }
            return "□";
        }
        return "";
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
        if (StringUtils.isEmpty(deviceSysName)){
            return "";
        }
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
        return IotId;
    }

    public void setIotId(Long iotId) {
        IotId = iotId;
    }

    public List<Device> getDeviceList() {
        if (CollectionUtils.isEmpty(deviceList)){
            deviceList = new ArrayList<>();
        }
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
