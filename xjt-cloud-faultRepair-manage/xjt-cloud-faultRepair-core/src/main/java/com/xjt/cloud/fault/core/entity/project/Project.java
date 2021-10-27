package com.xjt.cloud.fault.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Project 项目实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Project extends BaseEntity {

    //项目拥有者Id
    private Long ownerId;

    //是否拥有
    private Boolean isOwner;

    //项目名称
    private String projectName;

    //描述
    private String description;

    //消防安全责任人
    private String fireSafetyOwner;

    //消防安全责任人联系电话
    private String fireSafetyOwnerPhone;

    //消防安全管理人
    private String fireSafetyManager;

    //消防安全管理人联系电话
    private String fireSafetyManagerPhone;

    //公司名称
    private String companyName;

    //项目状态0、待审核 1、已驳回  2、已通过
    private Integer projectStatus;

    //项目营业执照图片路径
    private String imageUrl;

    //地址
    private String address;

    //立项人电话
    private String phone;

    //验证码
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFireSafetyOwner() {
        return fireSafetyOwner;
    }

    public void setFireSafetyOwner(String fireSafetyOwner) {
        this.fireSafetyOwner = fireSafetyOwner;
    }

    public String getFireSafetyOwnerPhone() {
        return fireSafetyOwnerPhone;
    }

    public void setFireSafetyOwnerPhone(String fireSafetyOwnerPhone) {
        this.fireSafetyOwnerPhone = fireSafetyOwnerPhone;
    }

    public String getFireSafetyManager() {
        return fireSafetyManager;
    }

    public void setFireSafetyManager(String fireSafetyManager) {
        this.fireSafetyManager = fireSafetyManager;
    }

    public String getFireSafetyManagerPhone() {
        return fireSafetyManagerPhone;
    }

    public void setFireSafetyManagerPhone(String fireSafetyManagerPhone) {
        this.fireSafetyManagerPhone = fireSafetyManagerPhone;
    }
}
