package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:34
 * @Description: 平台信息初使化管理实体类
 */
public class CloudInit extends BaseEntity {
    private Integer parentId;//父id
    private Integer cloudType;//信息发布平台类型见词典
    private String appVersion;//app版本号
    private String infoName;//信息名称
    private Integer type;//信息分类见词典
    private String typeCode;//信息code
    private String title;//信息标题
    private String keyword;//关键词
    private Integer sortOrder;//顺序
    private Integer carousel;//是否轮播 1默认不轮播 2轮播
    private String content;//信息内容
    private String intro;//简介
    private Integer modifyStatus;//是否有信息修改,要发送给关端初使化 1默认 没有 2有信息要修改
    private String thumbnail;//缩略图

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCloudType() {
        return cloudType;
    }

    public void setCloudType(Integer cloudType) {
        this.cloudType = cloudType;
    }

    public String getCloudTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("CLOUD_INIT_TYPE",cloudType + "");
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("CLOUD_INIT_INFO_TYPE",type + "");
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCarousel() {
        return carousel;
    }

    public void setCarousel(Integer carousel) {
        this.carousel = carousel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(Integer modifyStatus) {
        this.modifyStatus = modifyStatus;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
