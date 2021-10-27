package com.xjt.cloud.safety.core.entity.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;


/**
 * 设备登记信息实体
 *
 * @author dwt
 * @date 2020-04-10 15:38
 */
public class AssessmentTestRecord extends BaseEntity {
    private Long[] ids;
    private Long checkProjectId;
    /**
     * 客户名称
     */
    private String checkProjectName;

    //设备系统id
    private Long deviceTypeId;

    private Long deviceSysId;
    //系统名称
    private String deviceSysName;

    //系统设备id
    private Long deviceId;
    //设备名称
    private String deviceName;

    //数量
    private Integer num;

    //备注
    private String remark;

    //建筑物id
    private Long buildingId;
    //楼层id
    private Long floorId;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    /**
     * 位置
     */
    private String address;

    /**
     * 现场图片  ;号分隔
     */
    private String sceneImageUrl;

    private Date startTime;
    private Date endTime;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getCheckProjectName() {
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSceneImageUrl() {
        return sceneImageUrl;
    }

    public void setSceneImageUrl(String sceneImageUrl) {
        this.sceneImageUrl = sceneImageUrl;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingName() {
        if (getBuildingId() != null && getBuildingId() != 0) {
            this.buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
        }
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
        return buildingName;
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getFloorName() {
        if (getFloorId() != null && getFloorId() != 0) {
            this.floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
        }
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }
}
