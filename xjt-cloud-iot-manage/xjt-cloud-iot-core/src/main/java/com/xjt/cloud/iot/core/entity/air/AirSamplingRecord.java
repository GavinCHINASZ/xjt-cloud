package com.xjt.cloud.iot.core.entity.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @ClassName AirSamplingRecord
 * @Description 空气采样设备记录管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:09
 **/
public class AirSamplingRecord extends BaseEntity {
    private Long checkPointId;     //巡检点id
    private Long deviceId;     //设备id
    private Long airSamplingId;//空气采样设备id
    private Long[] airSamplingIds;//空气采样设备id
    private String deviceCoding;    //设备编码
    private Integer pipelineMaxValue;    //管道最大值 * 100
    private Integer pipelineMinValue;     //管道最小值 * 100
    private Integer pipelineValue1;     //管道1值 * 100
    private Integer pipelineValue2;     //管道2值 * 100
    private Integer pipelineStatus;     //管道1状态 1正常,2异常
    private Integer pipelineStatus1;     //管道1状态 1正常,2超高，3超低
    private Integer pipelineStatus2;     //管道2状态 1正常,2超高，3超低
    private Integer[] pipelineStatuss1;     //管道1状态 1正常,2超高，3超低
    private Integer[] pipelineStatuss2;     //管道2状态 1正常,2超高，3超低
    private Integer smogValue;     //烟雾值 * 100
    private Integer deviationSet;     //偏差值设置 * 100
    private Integer deviationValue1;     //管道1偏差值 * 100
    private Integer deviationValue2;     //管道2偏差值 * 100
    private Integer deviationStatus;     //偏差状态 1正常,2超高，3超低
    private Date dataUpdateTime;    //最新状态修改时间
    private Date endTime;    //最新状态修改时间
    private Integer totalDevice;//总设备数
    private Integer faultDevice;//异常设备数
    private Integer pipelineFault1;//管道1异常数
    private Integer pipelineFault2;//管道2异常数
    private String qrNo;    //巡检码
    private String pointLocation;    //巡检点位置
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private int groupType = 1;//分组类型　1　按时间　　2按日期 3按月  4按年
    private Integer dateType;//时间类型　1周　　2月　3年
    private String dateIndex;//时间值　0　本周本月本年　其它按正常值
    private String timeDesc;
    private String[] files;

    public Integer[] getPipelineStatuss1() {
        return pipelineStatuss1;
    }

    public void setPipelineStatuss1(Integer[] pipelineStatuss1) {
        this.pipelineStatuss1 = pipelineStatuss1;
    }

    public Integer[] getPipelineStatuss2() {
        return pipelineStatuss2;
    }

    public void setPipelineStatuss2(Integer[] pipelineStatuss2) {
        this.pipelineStatuss2 = pipelineStatuss2;
    }

    public Long[] getAirSamplingIds() {
        return airSamplingIds;
    }

    public void setAirSamplingIds(Long[] airSamplingIds) {
        this.airSamplingIds = airSamplingIds;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Long getAirSamplingId() {
        return airSamplingId;
    }

    public void setAirSamplingId(Long airSamplingId) {
        this.airSamplingId = airSamplingId;
    }

    public Integer getPipelineStatus() {
        return pipelineStatus;
    }

    public void setPipelineStatus(Integer pipelineStatus) {
        this.pipelineStatus = pipelineStatus;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(String dateIndex) {
        this.dateIndex = dateIndex;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    @JsonIgnore
    public Integer getDateTimeCount() {
        if (getEndTimeDesc() != null && getCreateTime() != null) {
            return DateUtils.getDateTimeCount(groupType, getEndTimeDesc(), getCreateTime());
        }
        return null;
    }

    @JsonIgnore
    public String getTimeType() {
        if (1 == groupType) {
            return "HOUR";
        } else if (2 == groupType) {
            return "DAY";
        } else if (3 == groupType) {//计算月分
            return "MONTH";
        } else if (4 == groupType) {
            return "YEAR";
        }
        return null;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCoding() {
        return deviceCoding;
    }

    public void setDeviceCoding(String deviceCoding) {
        this.deviceCoding = deviceCoding;
    }

    public Integer getPipelineMaxValue() {
        return pipelineMaxValue;
    }

    public void setPipelineMaxValue(Integer pipelineMaxValue) {
        this.pipelineMaxValue = pipelineMaxValue;
    }

    public Integer getPipelineMinValue() {
        return pipelineMinValue;
    }

    public void setPipelineMinValue(Integer pipelineMinValue) {
        this.pipelineMinValue = pipelineMinValue;
    }

    public Integer getPipelineValue1() {
        return pipelineValue1;
    }

    public void setPipelineValue1(Integer pipelineValue1) {
        this.pipelineValue1 = pipelineValue1;
    }

    public Integer getPipelineValue2() {
        return pipelineValue2;
    }

    public void setPipelineValue2(Integer pipelineValue2) {
        this.pipelineValue2 = pipelineValue2;
    }

    public Integer getPipelineStatus1() {
        return pipelineStatus1;
    }

    public void setPipelineStatus1(Integer pipelineStatus1) {
        this.pipelineStatus1 = pipelineStatus1;
    }

    public Integer getPipelineStatus2() {
        return pipelineStatus2;
    }

    public void setPipelineStatus2(Integer pipelineStatus2) {
        this.pipelineStatus2 = pipelineStatus2;
    }

    public Integer getSmogValue() {
        return smogValue;
    }

    public void setSmogValue(Integer smogValue) {
        this.smogValue = smogValue;
    }

    public Integer getDeviationSet() {
        return deviationSet;
    }

    public void setDeviationSet(Integer deviationSet) {
        this.deviationSet = deviationSet;
    }

    public Integer getDeviationValue1() {
        return deviationValue1;
    }

    public void setDeviationValue1(Integer deviationValue1) {
        this.deviationValue1 = deviationValue1;
    }

    public Integer getDeviationValue2() {
        return deviationValue2;
    }

    public void setDeviationValue2(Integer deviationValue2) {
        this.deviationValue2 = deviationValue2;
    }

    public Integer getDeviationStatus() {
        return deviationStatus;
    }

    public void setDeviationStatus(Integer deviationStatus) {
        this.deviationStatus = deviationStatus;
    }

    public Date getDataUpdateTime() {
        return dataUpdateTime;
    }

    public void setDataUpdateTime(Date dataUpdateTime) {
        this.dataUpdateTime = dataUpdateTime;
    }

    public Integer getTotalDevice() {
        return totalDevice;
    }

    public void setTotalDevice(Integer totalDevice) {
        this.totalDevice = totalDevice;
    }

    public Integer getFaultDevice() {
        return faultDevice;
    }

    public void setFaultDevice(Integer faultDevice) {
        this.faultDevice = faultDevice;
    }

    public Integer getPipelineFault1() {
        return pipelineFault1;
    }

    public void setPipelineFault1(Integer pipelineFault1) {
        this.pipelineFault1 = pipelineFault1;
    }

    public Integer getPipelineFault2() {
        return pipelineFault2;
    }

    public void setPipelineFault2(Integer pipelineFault2) {
        this.pipelineFault2 = pipelineFault2;
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

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }
    public String getPointLocationDesc(){
        String buildingName = getBuildingNameDesc();
        if (StringUtils.isEmpty(buildingName)){
            return null;
        }
        return buildingName + getBuildingFloorNameDesc() + getPointLocation();
    }

    public String getBuildingNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public String getBuildingFloorNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public String getPipelineValue1Desc(){
        if (getPipelineValue1() != null){
            return (getPipelineValue1() / 100.00f) + "%/m";
        }
        return "/";
    }
    public String getPipelineValue2Desc(){
        if (getPipelineValue2() != null){
            return (getPipelineValue2() / 100.00f) + "%/m";
        }
        return "/";
    }

    public String getSmogValueDesc(){
        if (getSmogValue() != null){
            return (getSmogValue() / 100.00f) + "";
        }
        return "/";
    }

    public String getPipelineStatusDesc(){
        if (pipelineStatus1 != null && pipelineStatus2 != null ){
            if (pipelineStatus1 > 1 || pipelineStatus2 > 1 ){
                return "异常";
            }
            return "正常";
        }
        return "/";
    }
    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime()) + " ";
    }
}
