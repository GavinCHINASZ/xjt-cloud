package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 联动设备 与 设备绑定
 *
 * @author huanggc
 * @date 2020/08/19
 **/
public class LinkageDeviceRelation extends BaseEntity {
    private Long[] ids;
    private Long[] projectIds;

    // 传感器ID  sensorNo
    private String sensorNo;

    private Long linkageDeviceId;
    private Long[] linkageDeviceIds;
    private Long deviceId;
    // 设备名称
    private String deviceName;
    // 设备二维码
    private String deviceQrNo;
    // 1：正常，2:异常
    private Integer iotStatus;

    // 巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    // 巡检点名称
    private String checkPointName;
    // 位置
    private String pointLocation;

    // 设备类型ID
    private Long deviceTypeId;
    private Integer deviceType;
    private String imgUrl;

    // 建筑物ID
    private Long buildingId;
    // 建筑物名称
    private String buildingName;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;//楼层id数组
    // 楼层名称
    private String floorName;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Long getLinkageDeviceId() {
        return linkageDeviceId;
    }

    public void setLinkageDeviceId(Long linkageDeviceId) {
        this.linkageDeviceId = linkageDeviceId;
    }

    public Long[] getLinkageDeviceIds() {
        return linkageDeviceIds;
    }

    public void setLinkageDeviceIds(Long[] linkageDeviceIds) {
        this.linkageDeviceIds = linkageDeviceIds;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Integer getIotStatus() {
        return iotStatus;
    }

    public void setIotStatus(Integer iotStatus) {
        this.iotStatus = iotStatus;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
        if (StringUtils.isEmpty(buildingName)){
            return "";
        }
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Integer[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Integer[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getFloorName() {
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)){
            return "";
        }
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}