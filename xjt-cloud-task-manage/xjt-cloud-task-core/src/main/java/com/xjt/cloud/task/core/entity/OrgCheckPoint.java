package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

/**
 * @ClassName OrgCheckPoint
 * @Author dwt
 * @Date 2019-11-18 19:17
 * @Version 1.0
 */
public class OrgCheckPoint {
    private Long orgId;
    private Integer deviceNum;
    private String deviceName;
    private Long checkPointId;
    private String qrNo;
    private String pointName;
    private Long companyId;
    private Long parentId;

    private Long buildingId;
    private Long floorId;
    private String buildingName;
    private String floorName;

    /**
     * 位置
     */
    private String pointLocation;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
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

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
        }else{
            buildingName = pointName;
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
        if(getFloorId() != null && getFloorId() != 0){
            floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
        }
        return floorName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }
}
