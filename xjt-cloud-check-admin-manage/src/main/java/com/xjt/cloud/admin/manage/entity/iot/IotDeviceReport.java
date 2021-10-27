package com.xjt.cloud.admin.manage.entity.iot;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName IotDeviceReport
 * @Author dwt
 * @Date 2020-11-09 14:55
 * @Version 1.0
 */
public class IotDeviceReport extends BaseEntity {
    private String[] orderByProjectIds;//排序的项目id数组
    private String[] findProjectIds;//查询项目id数组
    private String projectName;//项目名称
    private Integer totalCount;//所有物联设备数
    private Integer waterGageTotalCount;//水压总设备数
    private Integer waterGageOnLineCount;//水压在线数
    private Integer waterGageOffLineCount;//水压离线数
    private Integer waterLeachingTotalCount;//水浸总设备数
    private Integer waterLeachingOnLineCount;//水浸在线数
    private Integer waterLeachingOffLineCount;//水浸离线数
    private Integer smokeTotalCount;//烟感总设备数
    private Integer smokeOnLineCount;//烟感在线数
    private Integer smokeOffLineCount;//烟感离线数
    private Integer fireAlarmTotalCount;//火警总设备数
    private Integer fireAlarmOnLineCount;//火警在线数
    private Integer fireAlarmOffLineCount;//火警离线数
    private Integer vesaTotalCount;//极早期总设备数
    private Integer vesaOnLineCount;//极早期在线数
    private Integer vesaOffLineCount;//极早期离线数
    private Integer fireHydrantTotalCount;//消火栓总设备数
    private Integer fireHydrantOnLineCount;//消火栓在线数
    private Integer fireHydrantOffLineCount;//消火栓离线数
    private Integer linkageTotalCount;//声光总设备数
    private Integer linkageOnLineCount;//声光在线数
    private Integer linkageOffLineCount;//声光离线数

    public String[] getOrderByProjectIds() {
        return orderByProjectIds;
    }

    public void setOrderByProjectIds(String[] orderByProjectIds) {
        this.orderByProjectIds = orderByProjectIds;
    }

    public String[] getFindProjectIds() {
        return findProjectIds;
    }

    public void setFindProjectIds(String[] findProjectIds) {
        this.findProjectIds = findProjectIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getWaterGageTotalCount() {
        return waterGageTotalCount;
    }

    public void setWaterGageTotalCount(Integer waterGageTotalCount) {
        this.waterGageTotalCount = waterGageTotalCount;
    }

    public Integer getWaterGageOnLineCount() {
        return waterGageOnLineCount;
    }

    public void setWaterGageOnLineCount(Integer waterGageOnLineCount) {
        this.waterGageOnLineCount = waterGageOnLineCount;
    }

    public Integer getWaterGageOffLineCount() {
        return waterGageOffLineCount;
    }

    public void setWaterGageOffLineCount(Integer waterGageOffLineCount) {
        this.waterGageOffLineCount = waterGageOffLineCount;
    }

    public Integer getWaterLeachingTotalCount() {
        return waterLeachingTotalCount;
    }

    public void setWaterLeachingTotalCount(Integer waterLeachingTotalCount) {
        this.waterLeachingTotalCount = waterLeachingTotalCount;
    }

    public Integer getWaterLeachingOnLineCount() {
        return waterLeachingOnLineCount;
    }

    public void setWaterLeachingOnLineCount(Integer waterLeachingOnLineCount) {
        this.waterLeachingOnLineCount = waterLeachingOnLineCount;
    }

    public Integer getWaterLeachingOffLineCount() {
        return waterLeachingOffLineCount;
    }

    public void setWaterLeachingOffLineCount(Integer waterLeachingOffLineCount) {
        this.waterLeachingOffLineCount = waterLeachingOffLineCount;
    }

    public Integer getSmokeTotalCount() {
        return smokeTotalCount;
    }

    public void setSmokeTotalCount(Integer smokeTotalCount) {
        this.smokeTotalCount = smokeTotalCount;
    }

    public Integer getSmokeOnLineCount() {
        return smokeOnLineCount;
    }

    public void setSmokeOnLineCount(Integer smokeOnLineCount) {
        this.smokeOnLineCount = smokeOnLineCount;
    }

    public Integer getSmokeOffLineCount() {
        return smokeOffLineCount;
    }

    public void setSmokeOffLineCount(Integer smokeOffLineCount) {
        this.smokeOffLineCount = smokeOffLineCount;
    }

    public Integer getFireAlarmTotalCount() {
        return fireAlarmTotalCount;
    }

    public void setFireAlarmTotalCount(Integer fireAlarmTotalCount) {
        this.fireAlarmTotalCount = fireAlarmTotalCount;
    }

    public Integer getFireAlarmOnLineCount() {
        return fireAlarmOnLineCount;
    }

    public void setFireAlarmOnLineCount(Integer fireAlarmOnLineCount) {
        this.fireAlarmOnLineCount = fireAlarmOnLineCount;
    }

    public Integer getFireAlarmOffLineCount() {
        return fireAlarmOffLineCount;
    }

    public void setFireAlarmOffLineCount(Integer fireAlarmOffLineCount) {
        this.fireAlarmOffLineCount = fireAlarmOffLineCount;
    }

    public Integer getVesaTotalCount() {
        return vesaTotalCount;
    }

    public void setVesaTotalCount(Integer vesaTotalCount) {
        this.vesaTotalCount = vesaTotalCount;
    }

    public Integer getVesaOnLineCount() {
        return vesaOnLineCount;
    }

    public void setVesaOnLineCount(Integer vesaOnLineCount) {
        this.vesaOnLineCount = vesaOnLineCount;
    }

    public Integer getVesaOffLineCount() {
        return vesaOffLineCount;
    }

    public void setVesaOffLineCount(Integer vesaOffLineCount) {
        this.vesaOffLineCount = vesaOffLineCount;
    }

    public Integer getFireHydrantTotalCount() {
        return fireHydrantTotalCount;
    }

    public void setFireHydrantTotalCount(Integer fireHydrantTotalCount) {
        this.fireHydrantTotalCount = fireHydrantTotalCount;
    }

    public Integer getFireHydrantOnLineCount() {
        return fireHydrantOnLineCount;
    }

    public void setFireHydrantOnLineCount(Integer fireHydrantOnLineCount) {
        this.fireHydrantOnLineCount = fireHydrantOnLineCount;
    }

    public Integer getFireHydrantOffLineCount() {
        return fireHydrantOffLineCount;
    }

    public void setFireHydrantOffLineCount(Integer fireHydrantOffLineCount) {
        this.fireHydrantOffLineCount = fireHydrantOffLineCount;
    }

    public Integer getLinkageTotalCount() {
        return linkageTotalCount;
    }

    public void setLinkageTotalCount(Integer linkageTotalCount) {
        this.linkageTotalCount = linkageTotalCount;
    }

    public Integer getLinkageOnLineCount() {
        return linkageOnLineCount;
    }

    public void setLinkageOnLineCount(Integer linkageOnLineCount) {
        this.linkageOnLineCount = linkageOnLineCount;
    }

    public Integer getLinkageOffLineCount() {
        return linkageOffLineCount;
    }

    public void setLinkageOffLineCount(Integer linkageOffLineCount) {
        this.linkageOffLineCount = linkageOffLineCount;
    }
}
