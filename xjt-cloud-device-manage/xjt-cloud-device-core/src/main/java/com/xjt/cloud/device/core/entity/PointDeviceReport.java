package com.xjt.cloud.device.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/15 09:46
 * @Description:巡检点设备报表信息类
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PointDeviceReport {
    //巡检点正常数
    private Integer pointNormalNum;
    //巡检点异常数
    private Integer pointExceptionNum;
    //设备正常数
    private Integer deviceNormalNum;
    //设备异常数
    private Integer deviceExceptionNum;
    //项目id
    private Long projectId;
    private Long buildingId;//建筑物id
    private Long checkPointId;//巡检点id
    private Long buildingFloorId;// 楼层id
    private Long[] projectIds;//项目id数组
    private String startTime;//开始时间
    private String endTime;//结束时间

    public Integer getPointNormalNum() {
        return pointNormalNum;
    }

    public void setPointNormalNum(Integer pointNormalNum) {
        this.pointNormalNum = pointNormalNum;
    }

    public Integer getPointExceptionNum() {
        return pointExceptionNum;
    }

    public void setPointExceptionNum(Integer pointExceptionNum) {
        this.pointExceptionNum = pointExceptionNum;
    }

    public Integer getPointTotalNum(){
        return getPointExceptionNum() + getPointNormalNum();
    }

    public Integer getDeviceNormalNum() {
        return deviceNormalNum;
    }

    public void setDeviceNormalNum(Integer deviceNormalNum) {
        this.deviceNormalNum = deviceNormalNum;
    }

    public Integer getDeviceExceptionNum() {
        return deviceExceptionNum;
    }

    public void setDeviceExceptionNum(Integer deviceExceptionNum) {
        this.deviceExceptionNum = deviceExceptionNum;
    }

    public Integer getDeviceTotalNum(){
        return (getDeviceNormalNum() == null ? 0 : getDeviceNormalNum()) +
        (getDeviceExceptionNum() == null ? 0 : getDeviceExceptionNum());
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
    public String getBuildingNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }
}
