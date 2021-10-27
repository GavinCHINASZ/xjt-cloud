package com.xjt.cloud.safety.core.entity.fault;

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
}
