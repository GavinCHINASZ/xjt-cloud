package com.xjt.cloud.task.core.entity.protect;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 地铁 班前防护记录
 *
 * @author huangGuiChuan
 * @date 2021/03/04
 */
public class ProtectRecord extends BaseEntity {
    /**
     * 作业id
     */
    private Long protectId;

    /**
     * 施工负责人
     */
    private String protectEvent;

    /**
     * 图片
     */
    private String imageUrls;

    private String submitter;

    public Long getProtectId() {
        return protectId;
    }

    public void setProtectId(Long protectId) {
        this.protectId = protectId;
    }

    public String getProtectEvent() {
        return protectEvent;
    }

    public void setProtectEvent(String protectEvent) {
        this.protectEvent = protectEvent;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }
}
