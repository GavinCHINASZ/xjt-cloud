package com.xjt.cloud.iot.core.entity.eye;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;
import java.util.List;

/**
 * 火眼设备
 *
 * @author zhangZaifa
 * @date 2020/04/03 10:03
 */
public class FireEyeDevice extends BaseEntity {
    private Long[] projectIds;
    private Integer totalCount;
    private Long[] ids;
    //巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    /**
     * 巡检点名称
     */
    private String checkPointName;
    //设备id
    private Long deviceId;
    /**
     * 设备ID
     */
    private String deviceQrNo;
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备状态:1在线, 2离线
     */
    private Integer deviceState;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 在线数
     */
    private Integer onlineNum;

    /**
     * 离线数
     */
    private Integer offlineNum;


    // 巡检点位置（设备位置）
    private String pointLocation;
    /**
     * 位置(手动输入)
     */
    private String videoLocation;
    //传感器名称
    private String sensorName;
    //注册码
    private String sensorNo;

    //通道号
    private Integer channelNo;

    /**
     * 摄像机名称
     */
    private String videoName;

    /**
     * 心跳时间
     */
    private Date heartbeatTime;

    // 建筑物ID
    private Long buildingId;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    /**
     * 旧(1、火警警告  2、烟雾警告)
     * 类脑火眼: 0:正常(恢复所有事件,不新增事件), ,98 心跳(更新心跳,不新增事件)
     *          1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障, 21离线
     */
    private Integer eventType;
    private Integer[] eventTypeArray;

    /**
     * 恢复状态　0未恢复　1已恢复
     */
    private Integer recoverStatus;

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getDeviceState() {
        return deviceState;
    }

    public String getDeviceStateDesc() {
        if (this.deviceState != null && this.deviceState == 1){
            return "在线";
        }
        return "离线";
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public Integer getOfflineNum() {
        return offlineNum;
    }

    public void setOfflineNum(Integer offlineNum) {
        this.offlineNum = offlineNum;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public String getPointLocationDesc() {
        return getBuildingName() + getFloorName() + pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getVideoLocation() {
        return videoLocation;
    }

    public String getVideoLocationDesc() {
        return getBuildingName() + getFloorName() + videoLocation;
    }

    public void setVideoLocation(String videoLocation) {
        this.videoLocation = videoLocation;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
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

    public Integer[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Integer[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getBuildingName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer[] getEventTypeArray() {
        return eventTypeArray;
    }

    public void setEventTypeArray(Integer[] eventTypeArray) {
        this.eventTypeArray = eventTypeArray;
    }

    public String getEventTypeDesc() {
        if (eventType != null){
            // 1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障
            switch (eventType){
                case 1:
                    return "烟雾预警";
                case 2:
                    return "烟雾报警";
                case 4:
                    return "火焰预警";
                case 8:
                    return "火焰报警";
                case 16:
                    return "遮挡";
                default:
                    return "故障";
            }
        }
        return "/";
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }
}
