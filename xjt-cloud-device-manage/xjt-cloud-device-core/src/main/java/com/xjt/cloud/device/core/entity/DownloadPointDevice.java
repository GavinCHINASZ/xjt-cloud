package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.utils.DateUtils;
import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/23 15:44
 * @Description:
 */
public class DownloadPointDevice {
    private String pointName;// 巡检名称
    private String qrNo;// 	二维码
    private String deviceSysName;//设备系统名称
    private String deviceQrNo;// 设备二维码编号
    private Integer num;//数量
    private String deviceStatus;//最新设备状态0：正常，1：故障
    private String pointLocation;// 巡检位置
    private String brand;//	品牌
    private String model;//	产品型号
    private String spec;//	规格参数
    private String expiryDateStr;//有效期
    private String sendModifyTimeStr;//送修改时间
    private Date lastModifyTime;//最新状态修改时间
    private String manageRegion;//管理区域
    private String memo;//备注
    private String buildingName;
    private String buildingFloorName;
    private String companyName;
    private String orgName;
    private String projectName;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getManageRegion() {
        return manageRegion;
    }

    public void setManageRegion(String manageRegion) {
        this.manageRegion = manageRegion;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }


    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        this.expiryDateStr = expiryDateStr;
    }

    public String getSendModifyTimeStr() {
        return sendModifyTimeStr;
    }

    public void setSendModifyTimeStr(String sendModifyTimeStr) {
        this.sendModifyTimeStr = sendModifyTimeStr;
    }

    public String getLastModifyTimeDesc() {
        if (lastModifyTime != null){
            return DateUtils.formatDateTime(lastModifyTime);
        }
        return null;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingFloorName() {
        return buildingFloorName;
    }

    public void setBuildingFloorName(String buildingFloorName) {
        this.buildingFloorName = buildingFloorName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
