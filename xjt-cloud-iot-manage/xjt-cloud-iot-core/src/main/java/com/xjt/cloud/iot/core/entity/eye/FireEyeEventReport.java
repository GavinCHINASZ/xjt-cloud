package com.xjt.cloud.iot.core.entity.eye;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 火眼事件 报表
 *
 * @author huanggc
 * @date 2021/01/21
 */
public class FireEyeEventReport extends BaseEntity {
    private Integer totalCount;
    /**
     * 设备数量
     */
    private Long deviceCount;

    /**
     * 故障设备数
     */
    private Long faultDeviceCount;

    /**
     * 烟雾
     */
    private Long smokeCount;

    /**
     * 烟雾预警
     */
    private Long smokeWarningCount;

    /**
     * 火焰
     */
    private Long flameCount;

    /**
     * 火焰预警
     */
    private Long flameWarningCount;

    /**
     * 遮挡
     */
    private Long occlusionCount;

    /**
     * 故障
     */
    private Long faultEventCount;
    private String timeDesc;

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Long deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Long getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Long faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Long getFlameCount() {
        return flameCount;
    }

    public void setFlameCount(Long flameCount) {
        this.flameCount = flameCount;
    }

    public Long getSmokeCount() {
        return smokeCount;
    }

    public void setSmokeCount(Long smokeCount) {
        this.smokeCount = smokeCount;
    }

    public Long getFlameWarningCount() {
        return flameWarningCount;
    }

    public void setFlameWarningCount(Long flameWarningCount) {
        this.flameWarningCount = flameWarningCount;
    }

    public Long getSmokeWarningCount() {
        return smokeWarningCount;
    }

    public void setSmokeWarningCount(Long smokeWarningCount) {
        this.smokeWarningCount = smokeWarningCount;
    }

    public Long getOcclusionCount() {
        return occlusionCount;
    }

    public void setOcclusionCount(Long occlusionCount) {
        this.occlusionCount = occlusionCount;
    }

    public Long getFaultEventCount() {
        return faultEventCount;
    }

    public void setFaultEventCount(Long faultEventCount) {
        this.faultEventCount = faultEventCount;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }
}

