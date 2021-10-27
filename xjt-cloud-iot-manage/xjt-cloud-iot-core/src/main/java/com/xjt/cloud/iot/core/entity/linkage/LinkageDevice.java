package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 联动设备 实体类
 *
 * @author huanggc
 * @date 2020/08/17
 **/
public class LinkageDevice extends BaseEntity {

    private Boolean deleted = false;
    private Long[] ids;
    private Long[] projectIds;

    // 传感器ID, 以 sgbj 开头
    private String sensorId;

    // 通道号 1=DO-1, 2=DO-2, 3=DO-3, 4=DO-4
    private Integer channel;

    // 设备ID
    private Long deviceId;
    private Long[] deviceIds;
    private Long faultDeviceId;

    // 默认 1 默认设备
    // 水压(2水压监测 13水厢以前单位为Ｍ  14两端压力设备)
    // 3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感(旧)
    // 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控   19智能无线烟感  20声光报警装置
    private Integer deviceType;

    // 物联网关联ID
    private Long iotId;

    // 设备名称
    private String deviceName;

    // 设备二维码
    private String deviceQrNo;

    // 巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    // 巡检点名称
    private String checkPointName;
    // 位置
    private String pointLocation;

    // 设备类型ID
    private Long deviceTypeId;

    // 图片路径
    private String imgUrl;

    // 设备状态 1:在线  2:离线
    private Integer deviceState;
    private Integer[] deviceStates;

    // 事件状态 1已恢复, 2未恢复
    private Integer recoverStatus;

    /**
     * 1.正常(默认)
     * 2.报警
     * 3离线
     */
    private Integer eventType;
    private Integer[] eventTypes;

    // 电量　*　100
    private Integer electricQuantity;
    // 电量状态　1正常　2电量低
    private Integer electricQuantityStatus;

    // 消音:1关,2开
    private Integer sountAction;
    // 信号值
    private Integer signalValue;
    // 信号强度状态　1正常　2信号弱
    private Integer signalStatus;
    private Integer[] signalStatuss;
    //心跳时间
    private Date endHeartbeatTime;

    // 建筑物ID
    private Long buildingId;
    // 建筑物名称
    private String buildingName;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;//楼层id数组
    // 楼层名称
    private String floorName;

    // 要发送的信息
    private String msg;
    // 注册码ID, 例: $$_LINKAGE_DEVICE_0001
    private String regId;

    private Date endTime;

    // 声光报警 绑定的设备
    private LinkageDeviceRelation linkageDeviceRelation;
    private List<LinkageDeviceRelation> linkageDeviceRelationList;
    private List<LinkageDeviceRelation> linkageDeviceRelationLists;

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(Long[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Long getFaultDeviceId() {
        return faultDeviceId;
    }

    public void setFaultDeviceId(Long faultDeviceId) {
        this.faultDeviceId = faultDeviceId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public String getPointLocationDesc() {
        return getBuildingName() + getFloorName() + pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
    }

    public Integer[] getDeviceStates() {
        return deviceStates;
    }

    public void setDeviceStates(Integer[] deviceStates) {
        this.deviceStates = deviceStates;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getEventType() {
        return eventType;
    }

    public String getEventTypeDesc() {
        if (eventType == null){
            return "";
        }
        return eventType == 1 ? "正常" : "报警";
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer[] getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Integer[] eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Integer getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(Integer electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public Integer getElectricQuantityStatus() {
        return electricQuantityStatus;
    }

    public void setElectricQuantityStatus(Integer electricQuantityStatus) {
        this.electricQuantityStatus = electricQuantityStatus;
    }

    public Integer getSountAction() {
        return sountAction;
    }

    public String getSountActionDesc() {
        if (sountAction == null){
            return "";
        }
        return sountAction == 2 ? "开" : "关";
    }

    public void setSountAction(Integer sountAction) {
        this.sountAction = sountAction;
    }

    public Integer getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(Integer signalValue) {
        this.signalValue = signalValue;
    }

    public Integer getSignalStatus() {
        return signalStatus;
    }

    public void setSignalStatus(Integer signalStatus) {
        this.signalStatus = signalStatus;
    }

    public Integer[] getSignalStatuss() {
        return signalStatuss;
    }

    public void setSignalStatuss(Integer[] signalStatuss) {
        if (signalStatuss != null){
            signalStatus = signalStatuss[0];
        }
        this.signalStatuss = signalStatuss;
    }

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public String getEndHeartbeatTimeDesc() {
        if (deviceState != null && deviceState == 1){
            return "在线";
        }
        return "离线";
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
        if (StringUtils.isEmpty(buildingName)){
            return "";
        }
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getFloorName() {
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)){
            return "";
        }
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    public LinkageDeviceRelation getLinkageDeviceRelation() {
        return linkageDeviceRelation;
    }

    public void setLinkageDeviceRelation(LinkageDeviceRelation linkageDeviceRelation) {
        this.linkageDeviceRelation = linkageDeviceRelation;
    }

    public List<LinkageDeviceRelation> getLinkageDeviceRelationList() {
        return linkageDeviceRelationList;
    }

    public void setLinkageDeviceRelationList(List<LinkageDeviceRelation> linkageDeviceRelationList) {
        this.linkageDeviceRelationList = linkageDeviceRelationList;
    }

    public List<LinkageDeviceRelation> getLinkageDeviceRelationLists() {
        return linkageDeviceRelationLists;
    }

    public void setLinkageDeviceRelationLists(List<LinkageDeviceRelation> linkageDeviceRelationLists) {
        this.linkageDeviceRelationLists = linkageDeviceRelationLists;
    }
}