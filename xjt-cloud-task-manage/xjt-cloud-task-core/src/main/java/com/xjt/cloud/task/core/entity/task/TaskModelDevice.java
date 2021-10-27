package com.xjt.cloud.task.core.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 巡检任务
 *
 * @author huanggc
 * @date 2020/03/09
 */
public class TaskModelDevice extends BaseEntity {
    private Long[] ids;

    /**
     * 模板管理ID
     */
    private Long taskModelManageId;
    private Long[] taskModelManageIdArr;

    private Integer rowNum;

    /**
     * 设备系统名称，也为设备名称
     */
    private String deviceName;

    private Long checkPointId;
    /**
     * 巡检点二维码
     */
    private String pointQrNo;

    /**
     * 巡检点名称
     */
    private String pointName;

    /**
     * 模板类型:
     */
    private Integer modelType;
    private String modelTypeName;

    /**
     * 建筑物id
     */
    private Long buildingId;
    /**
     * 楼层id
     */
    private Long buildingFloorId;
    /**
     * 建筑物名称
     */
    private String buildingName;
    /**
     * 楼层名称
     */
    private String floorName;

    /**
     * 位置
     */
    private String pointLocation;

    private Date startTime;
    private Date endTime;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getTaskModelManageId() {
        return taskModelManageId;
    }

    public void setTaskModelManageId(Long taskModelManageId) {
        this.taskModelManageId = taskModelManageId;
    }

    public Long[] getTaskModelManageIdArr() {
        return taskModelManageIdArr;
    }

    public void setTaskModelManageIdArr(Long[] taskModelManageIdArr) {
        this.taskModelManageIdArr = taskModelManageIdArr;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public String getModelTypeName() {
        return modelTypeName;
    }

    public void setModelTypeName(String modelTypeName) {
        this.modelTypeName = modelTypeName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
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

    public String getBuildingName() {
        if (StringUtils.isNotEmpty(this.buildingName)){
            return buildingName;
        }
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        if (StringUtils.isNotEmpty(this.floorName)){
            return floorName;
        }
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
