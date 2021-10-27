package com.xjt.cloud.fault.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Organization 建筑物实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Building extends BaseEntity {

    //建筑物名称
    private String buildingName;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //街道
    private String roadNo;
    //经度
    private String lng;
    //纬度
    private String lat;
    //地上面积
    private Long acreage;
    //高度
    private Integer height;
    //地下楼层数
    private Integer undergroundFloor;
    //地上楼层数
    private Integer upstairsFloor;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Long getAcreage() {
        return acreage;
    }

    public void setAcreage(Long acreage) {
        this.acreage = acreage;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getUndergroundFloor() {
        return undergroundFloor;
    }

    public void setUndergroundFloor(Integer undergroundFloor) {
        this.undergroundFloor = undergroundFloor;
    }

    public Integer getUpstairsFloor() {
        return upstairsFloor;
    }

    public void setUpstairsFloor(Integer upstairsFloor) {
        this.upstairsFloor = upstairsFloor;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
