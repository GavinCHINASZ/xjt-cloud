package com.xjt.cloud.maintenance.core.entity.fault;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 报修进度 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class RepairProgress extends BaseEntity {
    /**
     * 故障记录ID
     */
    private Long faultRepairRecordId;

    /**
     * 文本
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 工单状态:  2维修中,  4已完成, 6暂停
     */
    private Integer workOrderStatus;

    /**
     * 维修说明
     */
    private String maintenanceResult;
    /**
     * 维修图片url
     */
    private String maintenanceImageUrl;

    /**
     * 故障描述(故障原因)故障说明
     */
    private String faultDescription;
    /**
     * 故障图片 imageUrl
     */
    private String faultDescriptionImageUrl;

    public void setRepairProgress(FaultRepairRecord faultRepairRecord) {
        this.workOrderStatus = faultRepairRecord.getWorkOrderStatus();
        this.maintenanceResult = faultRepairRecord.getMaintenanceResult();
        this.maintenanceImageUrl = faultRepairRecord.getMaintenanceImageUrl();
        this.faultDescription = faultRepairRecord.getFaultDescription();
        this.faultDescriptionImageUrl = faultRepairRecord.getImageUrl();
    }

    public Long getFaultRepairRecordId() {
        return faultRepairRecordId;
    }

    public void setFaultRepairRecordId(Long faultRepairRecordId) {
        this.faultRepairRecordId = faultRepairRecordId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getMaintenanceResult() {
        return maintenanceResult;
    }

    public void setMaintenanceResult(String maintenanceResult) {
        this.maintenanceResult = maintenanceResult;
    }

    public String getMaintenanceImageUrl() {
        return maintenanceImageUrl;
    }

    public void setMaintenanceImageUrl(String maintenanceImageUrl) {
        this.maintenanceImageUrl = maintenanceImageUrl;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getFaultDescriptionImageUrl() {
        return faultDescriptionImageUrl;
    }

    public void setFaultDescriptionImageUrl(String faultDescriptionImageUrl) {
        this.faultDescriptionImageUrl = faultDescriptionImageUrl;
    }
}
