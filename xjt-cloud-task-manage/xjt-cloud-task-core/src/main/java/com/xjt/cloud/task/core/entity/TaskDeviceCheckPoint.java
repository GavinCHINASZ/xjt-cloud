package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.List;

/**
 * @ClassName Device
 * @Author dwt
 * @Date 2019-08-07 18:30
 * @Description 巡更点
 * @Version 1.0
 */
public class TaskDeviceCheckPoint {
    private Long id;
    private String pointName;
    private String qrNo;
    private Integer totalCount;
    private String deviceQrNo;
    private List<TaskDeviceEntity> deviceList;
    private List<TaskDeviceType> deviceTypeList;
    private Integer checkPointCount;

    private Long buildingId;
    private Long floorId;
    private String buildingName;
    private String floorName;

    /**
     * 位置
     */
    private String pointLocation;

    private String orgName;
    private String deviceSysName;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<TaskDeviceEntity> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<TaskDeviceEntity> deviceList) {
        this.deviceList = deviceList;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public List<TaskDeviceType> getDeviceTypeList() {
        return deviceTypeList;
    }

    public void setDeviceTypeList(List<TaskDeviceType> deviceTypeList) {
        this.deviceTypeList = deviceTypeList;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Integer getCheckPointCount() {
        return checkPointCount;
    }

    public void setCheckPointCount(Integer checkPointCount) {
        this.checkPointCount = checkPointCount;
    }

    /**
     *
     * 功能描述:得到建筑物名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getBuildingName(){
        if(getBuildingId() != null && getBuildingId() != 0){
            buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
        }else{
            buildingName = pointName;
        }
        return buildingName;
    }
    /**
     *
     * 功能描述:得到建筑物楼层名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getFloorName(){
        if(getFloorId() != null && getFloorId() != 0){
            floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
        }
        return floorName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getOrgName() {
        return pointName;
    }

    public String getDeviceSysName() {
        return pointName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }
}
