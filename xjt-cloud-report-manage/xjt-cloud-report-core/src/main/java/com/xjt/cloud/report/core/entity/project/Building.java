package com.xjt.cloud.report.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

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
    //面积
    private Long acreage;
    //高度
    private Integer height;
    //地下楼层数
    private Integer undergroundFloor;
    //地上楼层数
    private Integer upstairsFloor;
    //项目ID
    private Long projectId;
    //详细地址
    private String address;
    //ids
    private List<Long> ids;
    //设备数量
    private Integer deviceCount;

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
