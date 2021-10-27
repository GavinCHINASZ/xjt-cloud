package com.xjt.cloud.task.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:51
 * @Description: 设备系统与类型管理实体类
 */
public class DeviceType extends BaseEntity {
    private Long parentId; //父id,
    private String deviceSysName; //名称
    private String useMethod;//功能和使用方法
    private String description;//描述
    private Integer pressureMonitor;//是否具有压力监测或液位监测 1 有 2无
    private Integer sysPressure;//系统端压力 1有 2无
    private Integer type;//设备系统类型 1 设备系统  9设备类型
    private String pinYinInitials;//拼音首写字母
    private String imgUrl;//图片路径

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
}
