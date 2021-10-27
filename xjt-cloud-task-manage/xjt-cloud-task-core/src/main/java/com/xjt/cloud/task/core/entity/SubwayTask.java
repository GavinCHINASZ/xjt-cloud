package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

/**
 * @ClassName SubwayTask
 * @Author dwt
 * @Date 2020-06-19 10:11
 * @Version 1.0
 */
public class SubwayTask {
    private Integer checkPointNum;
    private Integer faultNum;
    private Integer undetectedNum;
    //楼层名
    private String floorName;
    //楼层号
    private Integer floor;
    //建筑物ID
    private Long buildingId;
    //楼层图片
    private String imageUrl;
    private String lineColor;//布点连线颜色
    private Integer lineSize;//布点连线大小
    private Long buildingFloorId;//楼层ID
    private Long taskId;
    private String buildingName;


    public Integer getCheckPointNum() {
        return checkPointNum;
    }

    public void setCheckPointNum(Integer checkPointNum) {
        this.checkPointNum = checkPointNum;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getUndetectedNum() {
        return undetectedNum;
    }

    public void setUndetectedNum(Integer undetectedNum) {
        this.undetectedNum = undetectedNum;
    }

    //public String getFloorName() {return floorName;}

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setBuildingName(String buildingName){this.buildingName = buildingName;}

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public Integer getLineSize() {
        return lineSize;
    }

    public void setLineSize(Integer lineSize) {
        this.lineSize = lineSize;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
        if(getBuildingFloorId() != null && getBuildingFloorId() != 0){
            floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
        }
        return floorName;
    }
}
