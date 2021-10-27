package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName NationalFireProtectionSetting 全民消防设置
 * @Author zhangZaiFa
 * @Date 2019-12-02 15:15
 * @Description
 */
public class NationalFireProtectionSetting extends BaseEntity {
    //项目名称
    private String projectName;
    //建筑单位
    private String constructionUnit;
    //项目地址
    private String projectAddress;
    //项目介绍
    private String projectIntroduce;
    //值班电话
    private String onDutyPhone;
    //是否显示项目名称
    private Boolean isProjectName = true;
    //是否显示建设单位
    private Boolean isConstructionUnit = false;
    //是否显示项目地址
    private Boolean isProjectAddress = false;
    //是否显示项目介绍
    private Boolean isProjectIntroduce = false;
    //是否显示设备任务信息
    private Boolean isDeviceTaskDes = false;
    //是否显示水设备物联网信息
    private  Boolean isWaterDevice = false;
    //是否显示设备使用方法
    private Boolean isDeviceMethodDes = false;
    //是否显示值电话
    private Boolean isOnDutyPhone = false;
    //是否显示故障报修功能
    private Boolean isFaultRepairFun = false;
    //项目宣传图集合
    private List<String> projectPublicityMapList;
    //故障宣传图集合
    private List<String> faultPublicityMapList;
    //故障报修图片
    private List<String> faultRepairImageUrlList;

    public NationalFireProtectionSetting() {
    }

    public NationalFireProtectionSetting(Boolean isProjectName, Boolean isDeviceTaskDes, Boolean isDeviceMethodDes, Boolean isFaultRepairFun,Boolean isWaterDevice, Long projectId) {
        this.isProjectName = isProjectName;
        this.isDeviceTaskDes = isDeviceTaskDes;
        this.isDeviceMethodDes = isDeviceMethodDes;
        this.isFaultRepairFun = isFaultRepairFun;
        this.isWaterDevice = isWaterDevice;
        this.setProjectId(projectId);
    }

    public Boolean getIsWaterDevice() {
        return isWaterDevice;
    }

    public void setIsWaterDevice(Boolean isWaterDevice) {
        this.isWaterDevice = isWaterDevice;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectIntroduce() {
        return projectIntroduce;
    }

    public void setProjectIntroduce(String projectIntroduce) {
        this.projectIntroduce = projectIntroduce;
    }

    public String getOnDutyPhone() {
        return onDutyPhone;
    }

    public void setOnDutyPhone(String onDutyPhone) {
        this.onDutyPhone = onDutyPhone;
    }

    public Boolean getIsProjectName() {
        return isProjectName;
    }

    public void setIsProjectName(Boolean isProjectName) {
        this.isProjectName = isProjectName;
    }

    public Boolean getIsConstructionUnit() {
        return isConstructionUnit;
    }

    public void setIsConstructionUnit(Boolean isConstructionUnit) {
        this.isConstructionUnit = isConstructionUnit;
    }

    public Boolean getIsProjectAddress() {
        return isProjectAddress;
    }

    public void setIsProjectAddress(Boolean isProjectAddress) {
        this.isProjectAddress = isProjectAddress;
    }

    public Boolean getIsProjectIntroduce() {
        return isProjectIntroduce;
    }

    public void setIsProjectIntroduce(Boolean isProjectIntroduce) {
        this.isProjectIntroduce = isProjectIntroduce;
    }

    public Boolean getIsDeviceTaskDes() {
        return isDeviceTaskDes;
    }

    public void setIsDeviceTaskDes(Boolean isDeviceTaskDes) {
        this.isDeviceTaskDes = isDeviceTaskDes;
    }

    public Boolean getIsDeviceMethodDes() {
        return isDeviceMethodDes;
    }

    public void setIsDeviceMethodDes(Boolean isDeviceMethodDes) {
        this.isDeviceMethodDes = isDeviceMethodDes;
    }

    public Boolean getIsOnDutyPhone() {
        return isOnDutyPhone;
    }

    public void setIsOnDutyPhone(Boolean isOnDutyPhone) {
        this.isOnDutyPhone = isOnDutyPhone;
    }

    public Boolean getIsFaultRepairFun() {
        return isFaultRepairFun;
    }

    public void setIsFaultRepairFun(Boolean isFaultRepairFun) {
        this.isFaultRepairFun = isFaultRepairFun;
    }

    public List<String> getProjectPublicityMapList() {
        return projectPublicityMapList;
    }

    public void setProjectPublicityMapList(List<String> projectPublicityMapList) {
        this.projectPublicityMapList = projectPublicityMapList;
    }

    public List<String> getFaultPublicityMapList() {
        return faultPublicityMapList;
    }

    public void setFaultPublicityMapList(List<String> faultPublicityMapList) {
        this.faultPublicityMapList = faultPublicityMapList;
    }

    public List<String> getFaultRepairImageUrlList() {
        return faultRepairImageUrlList;
    }

    public void setFaultRepairImageUrlList(List<String> faultRepairImageUrlList) {
        this.faultRepairImageUrlList = faultRepairImageUrlList;
    }

}
