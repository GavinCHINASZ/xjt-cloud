package com.xjt.cloud.message.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * PageView 页面浏览量或点击量
 *
 * @author huanggc
 * @date 2020/10/23
 */
public class PageView extends BaseEntity {
    /**
     * 1 Android, 2 ios,3 PC
     */
    private Integer clientType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成员ID
     */
    private Long orgUserId;

    /**
     * 模块类型(名称)
     */
    private Integer pageType;
    private String pageTypeName;
    private String pageUrl;

    /**
     * 页面级别
     */
    private Integer pageLevel;

    /**
     * 页面来源:1首页, 2菜单
     */
    private Integer sourcePage;

    /**
     * 来源地址
     */
    private String sourceAddress;

    /**
     * 获取发送请求的客户端主机的IP
     */
    private String remoteAddr;

    /**
     * 上一个请示地址
     */
    private String refererAddress;

    /**
     *
     */
    private String userAgent;

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

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public String getPageTypeName() {
        return pageTypeName;
    }

    public void setPageTypeName(String pageTypeName) {
        this.pageTypeName = pageTypeName;
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

    public Integer getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(Integer sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRefererAddress() {
        return refererAddress;
    }

    public void setRefererAddress(String refererAddress) {
        this.refererAddress = refererAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
