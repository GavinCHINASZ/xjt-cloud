package com.xjt.cloud.maintenance.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 签到日志实体
 *
 * @author dwt
 * @date 2020-04-12 11:10
 */
public class SignLog extends BaseEntity {
    private Long userId;//签到用户
    private Long checkProjectId;//客户id
    private String userName;//签到用户名称
    private String companyName;//公司名称
    private String location;//签到地址
    private String lng;//经度
    private String lat;//纬度

    /**
     * 签到是否成功 1成功 2失败,
     * 3签退成功, 4签退失败
     */
    private Integer signSucceed;
    private Integer[] signSucceedArr;

    /**
     * 1签到, 2签退
     */
    private Integer signType;

    // 签到图片地址
    private String imgPath;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getSignSucceed() {
        return signSucceed;
    }

    public void setSignSucceed(Integer signSucceed) {
        this.signSucceed = signSucceed;
    }

    public Integer[] getSignSucceedArr() {
        return signSucceedArr;
    }

    public void setSignSucceedArr(Integer[] signSucceedArr) {
        this.signSucceedArr = signSucceedArr;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
