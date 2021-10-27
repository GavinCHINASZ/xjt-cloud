package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Organization 建筑物楼层实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class BuildingFloor extends BaseEntity {

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

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
}
