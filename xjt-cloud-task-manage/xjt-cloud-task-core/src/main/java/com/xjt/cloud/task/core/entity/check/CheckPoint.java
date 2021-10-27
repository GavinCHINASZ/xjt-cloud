package com.xjt.cloud.task.core.entity.check;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:29
 * @Description:巡检点信息实体类
 */
public class CheckPoint extends BaseEntity {
    private Long checkPointId;//巡更点id
    private String buildingName;// 建筑物id
    private String floorName;// 楼层id
    private String deviceName;//设备名称
    private String qrNo;//巡更点二维码
    private Long deviceTypeId;
    private Long buildingId;
    private Long buildingFloorId;
    private Long id;
    private String pointLocation;
    private String pointName;
    private Long projectId;
    private Long orgId;
    private Integer lng;
    private Integer lat;
    private String imgUrl;
    private String createUserName;
    private Integer checkStatus;
    private Date statusUpdateTime;
    private Long taskId;
    private String qrNoOrName;
    private Integer pointLayout;

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
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
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }

    public String getDeviceName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceTypeId(), "deviceSysName");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getQrNoOrName() {
        return qrNoOrName;
    }

    public void setQrNoOrName(String qrNoOrName) {
        this.qrNoOrName = qrNoOrName;
    }

    public Integer getPointLayout() {
        return pointLayout;
    }

    public void setPointLayout(Integer pointLayout) {
        this.pointLayout = pointLayout;
    }
}
