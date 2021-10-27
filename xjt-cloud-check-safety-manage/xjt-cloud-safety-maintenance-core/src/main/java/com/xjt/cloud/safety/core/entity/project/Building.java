package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.List;

/**
 * 建筑物实体
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
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

    //高度
    private Integer height;

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
    private Integer checkPointCount;
    //设备故障数量
    private Integer faultDeviceCount;
    //检测项目id
    private Long checkProjectId;
    private List<BuildingFloor> buildingFloors;

    private Integer automaticFireFighting;//自动消防设施 1有 2无
    private Integer acreage;//占地物面积
    private Integer buildingAcreage;//建筑物面积
    private Integer floorNumber;//楼层数
    private Integer safetyExitNumber;//安全出口数
    private Integer evacuationStaircasesNumber;//疏散楼梯数
    private Integer fireLiftNumber;//消防电梯数
    private String locationRefugeFloor;//避难层位置

    // 建筑物类别 1 一类高层民用建筑 2 二类高层民用建筑 3 高层厂房 4 高层库房 5 单、多层民用建筑
    // 6 单、多层厂房 7 单、多层库房 8 地下建筑 9  隧道、涵洞 10 其他建筑
    private Integer buildingType;

    public Integer getBuildingType() {
        return buildingType;
    }

    public String getBuildingTypeDesc() {
        if (buildingType != null) {
            switch (buildingType) {
                case 1:
                    return "一类高层民用建筑";
                case 2:
                    return "二类高层民用建筑";
                case 3:
                    return "高层厂房";
                case 4:
                    return "高层库房";
                case 5:
                    return "单、多层民用建筑";
                case 6:
                    return "单、多层厂房";
                case 7:
                    return "单、多层库房";
                case 8:
                    return "地下建筑";
                case 9:
                    return "隧道、涵洞";
                case 10:
                    return "其他建筑";
                default:
                    return "";
            }
        }
        return "";
    }

    public void setBuildingType(Integer buildingType) {
        this.buildingType = buildingType;
    }

    public Integer getAutomaticFireFighting() {
        return automaticFireFighting;
    }

    public String getAutomaticFireFightingDesc() {
        if (automaticFireFighting != null && automaticFireFighting == 1) {
            return "有";
        }
        return "无";
    }

    public void setAutomaticFireFighting(Integer automaticFireFighting) {
        this.automaticFireFighting = automaticFireFighting;
    }

    public Integer getAcreage() {
        if (acreage == null) {
            return 0;
        }
        return acreage;
    }

    public Integer getAcreageDesc() {
        if (acreage == null) {
            return 0;
        }
        return acreage / 100;
    }

    public void setAcreage(Integer acreage) {
        this.acreage = acreage;
    }

    public Integer getBuildingAcreage() {
        if (buildingAcreage == null) {
            return 0;
        }
        return buildingAcreage;
    }

    public Integer getBuildingAcreageDesc() {
        if (buildingAcreage == null) {
            return 0;
        }
        return buildingAcreage / 100;
    }

    public void setBuildingAcreage(Integer buildingAcreage) {
        this.buildingAcreage = buildingAcreage;
    }

    public Integer getFloorNumber() {
        if (floorNumber == null) {
            return 0;
        }
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getSafetyExitNumber() {
        if (safetyExitNumber == null) {
            return 0;
        }
        return safetyExitNumber;
    }

    public void setSafetyExitNumber(Integer safetyExitNumber) {
        this.safetyExitNumber = safetyExitNumber;
    }

    public Integer getEvacuationStaircasesNumber() {
        if (evacuationStaircasesNumber == null) {
            return 0;
        }
        return evacuationStaircasesNumber;
    }

    public void setEvacuationStaircasesNumber(Integer evacuationStaircasesNumber) {
        this.evacuationStaircasesNumber = evacuationStaircasesNumber;
    }

    public Integer getFireLiftNumber() {
        if (fireLiftNumber == null) {
            return 0;
        }
        return fireLiftNumber;
    }

    public void setFireLiftNumber(Integer fireLiftNumber) {
        this.fireLiftNumber = fireLiftNumber;
    }

    public String getLocationRefugeFloor() {
        if (locationRefugeFloor == null) {
            return "";
        }
        return locationRefugeFloor;
    }

    public void setLocationRefugeFloor(String locationRefugeFloor) {
        this.locationRefugeFloor = locationRefugeFloor;
    }

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
        if (StringUtils.isEmpty(address)) {
            return "";
        }
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
        if (undergroundFloor == null) {
            return 0;
        }
        return undergroundFloor;
    }

    public void setUndergroundFloor(Integer undergroundFloor) {
        this.undergroundFloor = undergroundFloor;
    }

    public Integer getUpstairsFloor() {
        if (upstairsFloor == null) {
            return 0;
        }
        return upstairsFloor;
    }

    public void setUpstairsFloor(Integer upstairsFloor) {
        this.upstairsFloor = upstairsFloor;
    }

    public String getBuildingName() {
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
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

    public Integer getHeight() {
        if (height == null) {
            return 0;
        }
        return height;
    }

    public Integer getHeightDesc() {
        if (height == null) {
            return 0;
        }else {
            return height / 100;
        }
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
