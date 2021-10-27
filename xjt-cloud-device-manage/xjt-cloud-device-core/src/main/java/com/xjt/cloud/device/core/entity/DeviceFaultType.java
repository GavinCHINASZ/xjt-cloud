package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 设备故障类型D
 *
 * @author huanggc
 * @date 2020/11/27
 */
public class DeviceFaultType extends BaseEntity {
    /**
     * 设备类型id
     */
    private Long deviceTypeId;

    /**
     * 设备系统id    1设备系统  9设备类型
     */
    private Long parentId;

    /**
     * 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7声光 8智能消火栓 9可然气  10电气火灾 11烟感
     */
    private Integer deviceType;

    /**
     * 色值
     */
    private String faultColor;

    /**
     * 事件类型:
     */
    private String faultEventType;

    /**
     * 故障类型
     */
    private String faultType;

    /**
     * 故障级别:默认1,
     */
    private Integer faultLevel;

    /**
     * 维修方式
     */
    private String repairMethod;

    /**
     * 原因分析
     */
    private String causeAnalysis;

    /**
     * 维护建议
     */
    private String repairProposal;
    /**
     * 排序
     */
    private Integer sort;

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Integer getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(Integer faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public String getRepairProposal() {
        return repairProposal;
    }

    public void setRepairProposal(String repairProposal) {
        this.repairProposal = repairProposal;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getFaultColor() {
        return faultColor;
    }

    public void setFaultColor(String faultColor) {
        this.faultColor = faultColor;
    }

    public String getFaultEventType() {
        return faultEventType;
    }

    public void setFaultEventType(String faultEventType) {
        this.faultEventType = faultEventType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
