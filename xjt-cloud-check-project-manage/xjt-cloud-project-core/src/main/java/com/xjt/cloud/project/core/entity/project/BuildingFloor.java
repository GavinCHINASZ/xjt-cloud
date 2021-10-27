package com.xjt.cloud.project.core.entity.project;

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
}
