package com.xjt.cloud.message.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * PageViewReport
 *
 * @author huanggc
 * @date 2020/10/23
 */
public class PageViewReport extends BaseEntity {
    /**
     * 1 Android, 2 ios,3 PC
     */
    private Integer clientType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 模块类型(名称)
     */
    private Integer pageType;

    private String pageUrl;

    /**
     * 页面级别
     */
    private Integer pageLevel;

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Integer getPageLevel() {
        return pageLevel;
    }

    public void setPageLevel(Integer pageLevel) {
        this.pageLevel = pageLevel;
    }
}
