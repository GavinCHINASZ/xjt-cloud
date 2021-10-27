package com.xjt.cloud.project.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.SysLog;

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
    //建筑物楼层名称
    private String floorName;
    //建筑物楼层id
    private Long floorId;
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
    private Double acreage;
    //高度
    private Double height;
    //面积*100 入库数据
    private Integer acreageInt;
    //高度*100 入库数据
    private Integer heightInt;

    //地下楼层数
    private Integer undergroundFloor;
    //地上楼层数
    private Integer upstairsFloor;
    //项目ID
    private List<Long> projectIds;
    //详细地址
    private String address;
    //ids
    private List<Long> ids;
    //设备数量
    private Integer deviceCount;
    //巡查点数量
    private  Integer checkPointCount;
    //设备故障数量
    private Integer faultDeviceCount;
    //检测项目id
    private Long checkProjectId;
    private List<BuildingFloor> buildingFloors;

    public List<BuildingFloor> getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(List<BuildingFloor> buildingFloors) {
        this.buildingFloors = buildingFloors;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }


    public Integer getCheckPointCount() {
        return checkPointCount;
    }

    public void setCheckPointCount(Integer checkPointCount) {
        this.checkPointCount = checkPointCount;
    }

    public Integer getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Integer faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Double getAcreage() {
        if (this.acreageInt!=null){
            this.acreage = this.acreageInt/100.00d;
        }
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public Double getHeight() {
        if(this.heightInt!=null){
            this.height = this.heightInt/100.00d;
        }
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getAcreageInt() {
        return acreageInt;
    }

    public void setAcreageInt(Integer heightInt) {
        this.acreageInt = heightInt;

    }

    public Integer getHeightInt() {
        return heightInt;
    }

    public void setHeightInt(Integer heightInt) {
        this.heightInt = heightInt;
    }
    public void initHeightAndAcreage(Double height,Double acreage){
        if(acreage!=null){
            acreage = acreage*100;
            this.acreageInt = acreage.intValue();
        }
        if(height!=null){
            height = height*100;
            this.heightInt = height.intValue();
        }
    }
}
