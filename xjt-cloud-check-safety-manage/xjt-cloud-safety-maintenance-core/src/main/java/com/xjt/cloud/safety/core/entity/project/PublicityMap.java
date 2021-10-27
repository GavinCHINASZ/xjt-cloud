package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName NationalFireProtectionSetting 全民消防设置
 * @Author zhangZaiFa
 * @Date 2019-12-02 15:15
 * @Description
 */
public class PublicityMap extends BaseEntity {
    //1、项目宣传图  2、报修宣传图
    private Integer sourceType;
    //图片URL
    private String imageUrl;
    //所属ID
    private Long sourceId;

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
