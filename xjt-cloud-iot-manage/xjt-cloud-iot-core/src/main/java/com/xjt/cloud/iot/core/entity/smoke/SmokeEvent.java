package com.xjt.cloud.iot.core.entity.smoke;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 烟感  事件
 *
 * @author huanggc
 * @date 2020/07/15
 */
public class SmokeEvent extends BaseEntity {
    private Long[] ids;
    private Long[] projectIds;
    // 烟感设备ID
    private Long smokeDeviceId;
    // 设备ID
    private Long deviceId;
    // 设备qrNo
    private String deviceQrNo;
    // 设备名称
    private String deviceName;

    //巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    private String checkPointName;
    //巡检点位置（设备位置）
    private String pointLocation;

    // 传感器ID
    private String sensorId;
    private String oldSensorId;
    // NB device ID
    private String devId;

    // 建筑物ID
    private Long buildingId;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;
    // 电量　*　100
    private Integer electricQuantity;
    //电量状态　1正常　2电量低
    private Integer electricQuantityStatus;
    private Integer[] electricQuantityStatuss;
    // 处理状态 0:未处理 1:已处理 2:已忽略
    private Integer dealStatus;
    /**
     * 事件类型: 赛特 objid ID=5503
     * GS524N型号
     * 1. 烟雾报警   （报警）
     * 5. 传感器故障 （故障）
     * 21 离线
     */
    private Integer eventType;
    // 设备状态　1正常　2离线
    private Integer deviceStatus;
    private Integer[] deviceStatuss;

    /**
     * 事件类型: 赛特 objid ID=5503
     * GS524N型号
     * 恢复
     * 1. 烟雾报警   （报警）
     * 4 低电量
     * 5. 传感器故障 （故障）
     * 7 正常
     * 21 离线
     */
    private Integer eventTypes;
    // 报警恢复
    private Integer eventTypeSmoke;
    private Integer[] eventTypeArray;

    // 心跳时间(心跳时间 小于 系统当前时间 超过26H为离线状态)
    private Date heartbeatTime;
    // 信号值  1至5
    private Integer signalValue;
    //信息强度状态　1正常　2信号弱
    private Integer signalStatus;
    private Integer[] signalStatuss;

    // 恢复时间
    private Date recoverTime;
    // 事件状态 1-已恢复，2-未恢复
    private Integer recoverStatus;
    private Integer[] recoverStatuss;
    // 1烟雾  21离线  4低电量  5故障
    private Integer[] leakMonitorStatuss;

    private Date beginTime;
    private Date endTime;
    //分组类型　1　按时间　　2按日期 3按月  4按年
    private int groupType = 1;
    private String timeDesc;

    // 是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirm;
    // 处理状态，1已处理  2未处理
    private Integer handleStatus;
    private Integer[] handleStatuss;
    // 处理时间
    private Date endHandleTime;

    // 处理描述
    private String description;

    // 处理人
    private String handleUserName;

    // 处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;

    //查询异常类型
    private Integer byFaultEventType;
    //异常类型id
    private Long faultTypeId;
    //事件处理的异常类型
    private String faultType;
    //物联设备异常处理类型id
    private Long deviceFaultTypeId;
    //默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备',
    private Integer handleDeviceType;

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
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

    public Long getSmokeDeviceId() {
        return smokeDeviceId;
    }

    public void setSmokeDeviceId(Long smokeDeviceId) {
        this.smokeDeviceId = smokeDeviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
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

    public String getPointLocationDesc() {
        return getBuildingNameDesc() + getBuildingFloorNameDesc() + getPointLocation();
    }

    public String getBuildingNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public String getBuildingFloorNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getOldSensorId() {
        return oldSensorId;
    }

    public void setOldSensorId(String oldSensorId) {
        this.oldSensorId = oldSensorId;
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

    public Integer[] getElectricQuantityStatuss() {
        return electricQuantityStatuss;
    }

    public void setElectricQuantityStatuss(Integer[] electricQuantityStatuss) {
        this.electricQuantityStatuss = electricQuantityStatuss;
        if (electricQuantityStatuss != null){
            if (electricQuantityStatuss[0] == 1){
                electricQuantityStatus = 1;
            }else {
                electricQuantityStatus = 2;

            }        }
    }

    public Integer[] getDeviceStatuss() {
        return deviceStatuss;
    }

    public void setDeviceStatuss(Integer[] deviceStatuss) {
        this.deviceStatuss = deviceStatuss;
        if (deviceStatuss != null){
            if (deviceStatuss[0] == 1){
                deviceStatus = 1;
            }else {
                deviceStatus = 2;
            }
        }
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
        this.signalStatuss = signalStatuss;
        if (signalStatuss != null){
            if (signalStatuss[0] == 1){
                signalStatus = 1;
            }else {
                signalStatus = 2;
            }
        }
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public Integer getEventType() {
        return eventType;
    }

    public String getEventTypeDesc() {
        if (eventType != null){
            switch (eventType) {
                case 1:
                    return "报警";
                case 5:
                    return "故障";
                case 4:
                    return "低电量";
                default:
                    return "离线";
            }
        }

        return "/";
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Integer eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Integer getEventTypeSmoke() {
        return eventTypeSmoke;
    }

    public void setEventTypeSmoke(Integer eventTypeSmoke) {
        this.eventTypeSmoke = eventTypeSmoke;
    }

    public Integer[] getEventTypeArray() {
        return eventTypeArray;
    }

    public void setEventTypeArray(Integer[] eventTypeArray) {
        this.eventTypeArray = eventTypeArray;
    }

    public String getDevId() {
        return devId;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public String getHeartbeatTimeDesc() {
        if (heartbeatTime != null){
            int days = (int) ((System.currentTimeMillis() - heartbeatTime.getTime()) / (1000*3600*26));
            if (days <= 1){
                return "在线";
            }
        }
        return "离线";
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public Integer getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(Integer signalValue) {
        this.signalValue = signalValue;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public String getRecoverTimeDesc() {
        return DateUtils.formatDateTime(recoverTime);
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public String getRecoverStatusDesc() {
        if (recoverStatus != null){
            switch (recoverStatus) {
                case 1:
                    return "已恢复";
                default:
                    return "未恢复";
            }
        }
        return "/";
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer[] getRecoverStatuss() {
        return recoverStatuss;
    }

    public void setRecoverStatuss(Integer[] recoverStatuss) {
        this.recoverStatuss = recoverStatuss;
    }

    public Integer[] getLeakMonitorStatuss() {
        return leakMonitorStatuss;
    }

    public void setLeakMonitorStatuss(Integer[] leakMonitorStatuss) {
        // 1烟雾 4低电量  5故障 21离线
        if (leakMonitorStatuss != null && leakMonitorStatuss.length > 0){
            for (int i : leakMonitorStatuss) {
                switch (i) {
                    case 7:
                        recoverStatus = 1;
                        break;
                }
            }
        }
        this.leakMonitorStatuss = leakMonitorStatuss;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    @JsonIgnore
    public Integer getDateTimeCount(){
        if (getEndTimeDesc() != null && getCreateTime() != null) {
            return DateUtils.getDateTimeCount(groupType, getEndTimeDesc(), getCreateTime());
        }
        return null;
    }

    @JsonIgnore
    public String getTimeType(){
        if (1 == groupType){
            return "HOUR";
        }else if(2 == groupType){
            return "DAY";
        }else if(3 == groupType){
            //计算月分
            return "MONTH";
        }else if(4 == groupType){
            return "YEAR";
        }
        return null;
    }

    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime());
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleStatusDesc() {
        if (handleStatus == null){
            return "/";
        }else{
            if (handleStatus == 1){
                return "已处理";
            }else{
                return "待处理";
            }
        }
    }

    public Date getEndHandleTime() {
        return endHandleTime;
    }

    public void setEndHandleTime(Date endHandleTime) {
        this.endHandleTime = endHandleTime;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionDesc() {
        if (StringUtils.isEmpty(description)){
            return "/";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public String getHandleUserNameDesc() {
        if (StringUtils.isEmpty(handleUserName)) {
            return "/";
        }
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public String getEventHandleTimeDesc() {
        return DateUtils.formatDateTime(getEndHandleTime());
    }

    public Integer getEventExistStatus() {
        return eventExistStatus;
    }

    public void setEventExistStatus(Integer eventExistStatus) {
        this.eventExistStatus = eventExistStatus;
    }

    /**
     * 得到事件异常处理类型
     *
     * @author huanggc
     * @date 2020/12/10
     * @return java.lang.Integer
     */
    public Integer getFaultEventType() {
        // 事件类型: 1烟雾报警（报警）, 4低电量, 5传感器故障 （故障）, 21离线
        // 对应device_manage.d_device_fault_type的fault_event_type
        if (eventType != null) {
            return eventType;
        }
        return null;
    }

    public Integer getByFaultEventType() {
        return byFaultEventType;
    }

    public void setByFaultEventType(Integer byFaultEventType) {
        this.byFaultEventType = byFaultEventType;
    }

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public Integer getHandleDeviceType() {
        return handleDeviceType;
    }

    public void setHandleDeviceType(Integer handleDeviceType) {
        this.handleDeviceType = handleDeviceType;
    }
}
