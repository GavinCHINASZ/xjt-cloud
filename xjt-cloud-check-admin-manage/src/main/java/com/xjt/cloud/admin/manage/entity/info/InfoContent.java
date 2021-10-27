package com.xjt.cloud.admin.manage.entity.info;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/4 16:59
 * @Description:资讯信息初使化信息配置内容
 */
public class InfoContent extends BaseEntity {
    private String cloudType;//平台类型
    private String pageType;//页面类型
    private String infoType;//信息类型
    private Long infoManageId;//资讯类型id
    private Integer imgWidth;//图片宽
    private Integer imgHeight;//图片高
    private Integer sort;//排序
    private String title;//标题
    private String intro;//简介
    private String url;//'第三方url
    private String thumbnail;//缩略图
    private String content;//内容
    private String property1;//属性1
    private String property2;//属性1
    private String property3;//属性1
    private String property4;//属性1
    private String property5;//属性1
    private String property6;//属性1

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public String getProperty4() {
        return property4;
    }

    public void setProperty4(String property4) {
        this.property4 = property4;
    }

    public String getProperty5() {
        return property5;
    }

    public void setProperty5(String property5) {
        this.property5 = property5;
    }

    public String getProperty6() {
        return property6;
    }

    public void setProperty6(String property6) {
        this.property6 = property6;
    }

    public String getCloudType() {
        return cloudType;
    }

    public String getCloudTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("INFO_CLOUD_TYPE",cloudType);
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }

    public String getPageType() {
        return pageType;
    }

    public String getPageTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("INFO_PAGE_TYPE",pageType);
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getInfoType() {
        return infoType;
    }

    public String getInfoTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("INFO_INFO_TYPE",infoType);
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public Long getInfoManageId() {
        return infoManageId;
    }

    public void setInfoManageId(Long infoManageId) {
        this.infoManageId = infoManageId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
