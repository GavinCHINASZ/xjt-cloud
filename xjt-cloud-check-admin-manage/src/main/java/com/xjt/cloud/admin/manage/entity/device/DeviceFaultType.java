package com.xjt.cloud.admin.manage.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 设备故障类型
 *
 * @author huanggc
 * @date 2020/11/19
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
     * 故障类型
     */
    private String faultType;

    /**
     * 故障级别:默认1,
     */
    private Integer faultLevel = 1;

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
     * 设备类型 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7声光 8智能消火栓 9可然气  10电气火灾 11烟感
     */
    private Integer deviceType;
    private Integer deviceTypeType;

    /**
     * 颜色
     */
    private String faultColor;

    /**
     * 事件类型:
     */
    private String faultEventType;
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

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
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

    public Integer getDeviceTypeType() {
        return deviceTypeType;
    }

    public void setDeviceTypeType(Integer deviceTypeType) {
        this.deviceTypeType = deviceTypeType;
        // d_device_type 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
        // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备 15多端设备 16吸气式烟雾探测器 17微型消防站  19烟感

        // 本表:设备类型 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7声光 8智能消火栓 9可然气  10电气火灾 11烟感
        if (deviceTypeType != null) {
            switch (deviceTypeType) {
                case 2:
                    this.deviceType = 2;
                    break;
                case 3:
                    this.deviceType = 3;
                    break;
                case 4:
                    this.deviceType = 4;
                    break;
                case 5:
                    this.deviceType = 5;
                    break;
                case 6:
                    this.deviceType = 6;
                    break;
                case 7:
                    this.deviceType = 11;
                    break;
                case 8:
                    this.deviceType = 8;
                    break;
                case 9:
                    this.deviceType = 9;
                    break;
                case 10:
                    this.deviceType = 10;
                    break;
                case 13:
                    this.deviceType = 2;
                    break;
                case 14:
                    this.deviceType = 2;
                    break;
                case 19:
                    this.deviceType = 11;
                    break;
                case 20:
                    this.deviceType = 7;
                    break;
                default:
                    this.deviceType = 1;
                    break;
            }
        }
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
