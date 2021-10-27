package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;

/**
 * 烟感 事件处理信息类
 *
 * @author huanggc
 * @date 2020/12/7 10:08
 **/
public class LinkageEventHandle extends BaseEntity {
    /**
     * 设备id
     */
    private Long deviceId;
    private Long[] deviceIds;
    /**
     * 设备异常类型id
     */
    private Long deviceFaultTypeId;
    /**
     * 烟感设备id
     */
    private Long linkageDeviceId;
    private Long[] linkageDeviceIds;
    /**
     * 事件记录id
     */
    private Long eventId;
    private Long[] eventIds;

    /**
     * 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
     *     11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
     */
    private Integer deviceType;

    /**
     * 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
     *     11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
     */
    private Integer[] deviceTypes;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片路径,多图片以；分隔
     */
    private String imgUrls;
    /**
     * 故障类型
     */
    private String faultType;
    /**
     * 故障级别
     */
    private Integer faultLevel;
    /**
     * 原因分析
     */
    private String causeAnalysis;
    /**
     * 维修方式
     */
    private String repairMethod;
    /**
     * 维护建议
     */
    private String repairProposal;
    private Date endTime;

    /**
     * 颜色
     */
    private String faultColor;

    public LinkageEventHandle(){

    }
    public LinkageEventHandle(Long projectId, Long deviceId, Long deviceFaultTypeId, Long linkageDeviceId, Long eventId, Integer deviceType,
                            String description, String imgUrls, Long createUserId, String createUserName){
        this.setProjectId(projectId);
        this.setDeviceId(deviceId);
        this.setDeviceFaultTypeId(deviceFaultTypeId);
        this.setLinkageDeviceId(linkageDeviceId);
        this.setEventId(eventId);
        this.setDeviceType(deviceType);
        this.setDescription(description);
        this.setImgUrls(imgUrls);
        this.setCreateUserId(createUserId);
        this.setCreateUserName(createUserName);
    }

    public Long[] getEventIds() {
        return eventIds;
    }

    public void setEventIds(Long[] eventIds) {
        this.eventIds = eventIds;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public Long getLinkageDeviceId() {
        return linkageDeviceId;
    }

    public void setLinkageDeviceId(Long linkageDeviceId) {
        this.linkageDeviceId = linkageDeviceId;
    }

    public Long[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(Long[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Long[] getLinkageDeviceIds() {
        return linkageDeviceIds;
    }

    public void setLinkageDeviceIds(Long[] linkageDeviceIds) {
        this.linkageDeviceIds = linkageDeviceIds;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getFaultColor() {
        return faultColor;
    }

    public void setFaultColor(String faultColor) {
        this.faultColor = faultColor;
    }
}
