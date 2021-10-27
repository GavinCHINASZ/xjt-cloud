package com.xjt.cloud.project.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName SignLog
 * @Author dwt
 * @Date 2020-04-12 11:10
 * @Description 签到日志实体
 * @Version 1.0
 */
public class SignLog extends BaseEntity {

    private Long userId;
    private Long checkProjectId;
    private String userName;
    private String companyName;
    private Long checkUserId;
    private String location;
    //经度
    private String lng;
    //纬度
    private String lat;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
