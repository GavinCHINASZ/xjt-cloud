package com.xjt.cloud.device.core.entity;


/**
 * @ClassName Organization 建筑物实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Building{
    private Long id;
    //楼层id
    private Long floorId;
    //楼层名称
    private String floorName;
    //建筑物名称
    private String buildingName;
    //是否关联物联设备状态
    private Boolean iotStatus;
    //是否已关联物联设备 false未关联  true已关联
    public Boolean getIotStatus() {
        return iotStatus;
    }

    public void setIotStatus(Boolean iotStatus) {
        this.iotStatus = iotStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
