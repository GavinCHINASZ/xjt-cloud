package com.xjt.cloud.iot.core.entity.smoke;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 烟感  设备
 *
 * @author huanggc
 * @date 2020/07/15
 */
public class SmokeDevice extends BaseEntity {
    private Long[] ids;
    private Long[] projectIds;
    // 巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    private String checkPointName;
    private String pointName;
    // 位置
    private String pointLocation;

    /**
     * 设备qrNo
     */
    private String deviceQrNo;

    // 设备ID
    private Long deviceId;

    // 设备名称
    private String deviceName;

    // 设备类型ID
    private Long deviceTypeId;

    // 设备类型: 19烟感
    private Integer deviceType;

    // 传感器ID  "imei":"860640040648828"
    private String sensorId;
    private String oldSensorId;

    // 电量　*　100
    private Integer electricQuantity;
    // 电量状态　1正常　2电量低
    private Integer electricQuantityStatus;

    // NB device ID
    private String devId;
    // NB device psk
    private String nbPsk;

    // 建筑物ID
    private Long buildingId;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    private List<SmokeDevice> smokeDeviceList;

    /**
     * 事件类型: 赛特 objid ID=5503
     * GS524N型号
     * 1. 烟雾报警   （报警）
     * 5. 传感器故障 （故障）
     */
    private Integer eventType;
    private Integer[] eventTypeArray;

    // 信号强度状态　1正常　2信号弱
    private Integer signalStatus;
    // 信号值  1至5
    private Integer signalValue;

    /**
     * Lora
     * NB_OneNET
     * NB_CT
     * NB_CU
     */
    private String networkType;

    // 心跳时间(心跳时间 小于 系统当前时间 超过26H为离线状态) SUM(IF(UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(heartbeat_time)  > 86400, 1, 0)) offLine,
    private Date heartbeatTime;
    // 设备状态　1正常　2离线
    private Integer deviceStatus;

    // 恢复时间
    private Date recoverTime;
    // 事件状态 0初始状态 1-已恢复，2-未恢复
    private Integer recoverStatus;
    private Integer[] recoverStatuss;
    private Date endTime;

    // 查询条件　设备状态 1在线, 2离线
    private Integer[] deviceStatuss;
    // 查询条件　信号状态 1信号旨, 2信号弱
    private Integer[] signalIntensitys;
    // 查询条件　电量状态 1电量高, 2电量低
    private Integer[] electricQuantitys;
    // 1烟雾  21离线  4低电量  5故障
    private Integer[] leakMonitorStatuss;
    // 1烟雾   5故障
    private Integer[] monitorStatuss;

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

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getHeartbeatTimeDesc() {
        if (deviceStatus == null || deviceStatus == 2){
            return "离线";
        }
        return "在线";
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
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

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointLocation() {
        if (StringUtils.isEmpty(pointLocation)){
            return "/";
        }
        return pointLocation;
    }

    public String getPointLocationDesc() {
        return getBuildingName() + getFloorName() + getPointLocation();
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Integer getEventType() {
        return eventType;
    }

    public String getEventTypeDesc() {
        if (recoverStatus != null && recoverStatus == 2 && eventType != null){
            if (eventType == 1) {
                return "报警";
            }else {
                return "故障";
            }
        }
        return "正常";
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

    public Integer getSignalStatus() {
        return signalStatus;
    }

    public void setSignalStatus(Integer signalStatus) {
        this.signalStatus = signalStatus;
    }

    public Integer getSignalValue() {
        return signalValue;
    }

    public String getSignalValueDesc(){
        if (signalValue == null){
            return "";
        }
        return signalValue.toString();
    }

    public void setSignalValue(Integer signalValue) {
        this.signalValue = signalValue;
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

    public String getElectricQuantityDesc(){
        if (getElectricQuantity() != null){
            return (getElectricQuantity() / 100) + "%";
        }
        return "0%";
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
        buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
        if (StringUtils.isEmpty(buildingName)){
            return "";
        }
        return this.buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)){
            return "";
        }
        return this.floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getNbPsk() {
        return nbPsk;
    }

    public void setNbPsk(String nbPsk) {
        this.nbPsk = nbPsk;
    }


    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public List<SmokeDevice> getSmokeDeviceList() {
        return smokeDeviceList;
    }

    public void setSmokeDeviceList(List<SmokeDevice> smokeDeviceList) {
        this.smokeDeviceList = smokeDeviceList;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer[] getDeviceStatuss() {
        return deviceStatuss;
    }

    public void setDeviceStatuss(Integer[] deviceStatuss) {
        if (deviceStatuss != null && deviceStatuss.length > 0){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            heartbeatTime = calendar.getTime();
        }
        this.deviceStatuss = deviceStatuss;
    }

    public Integer[] getSignalIntensitys() {
        return signalIntensitys;
    }

    public void setSignalIntensitys(Integer[] signalIntensitys) {
        this.signalIntensitys = signalIntensitys;
    }

    public Integer[] getElectricQuantitys() {
        return electricQuantitys;
    }

    public void setElectricQuantitys(Integer[] electricQuantitys) {
        if (electricQuantitys != null && electricQuantitys.length > 0){
            electricQuantityStatus = electricQuantitys[0];
        }
        this.electricQuantitys = electricQuantitys;
    }

    public Integer[] getLeakMonitorStatuss() {
        return leakMonitorStatuss;
    }

    public void setLeakMonitorStatuss(Integer[] leakMonitorStatuss) {
        // 1烟雾 4低电量  5故障 21离线
        if (leakMonitorStatuss != null && leakMonitorStatuss.length > 0){
            for (int i : leakMonitorStatuss) {
                switch (i) {
                    case 1:
                        eventType = 1;
                        recoverStatus = 2;
                        break;
                    case 4:
                        electricQuantityStatus = 2;
                        break;
                    case 7:
                        recoverStatus = 1;
                        break;
                    case 21:
                        deviceStatus = 2;
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        heartbeatTime = calendar.getTime();
                        break;
                    default:
                        eventType = 5;
                        recoverStatus = 2;
                        break;
                }
            }
        }
        this.leakMonitorStatuss = leakMonitorStatuss;
    }

    public Integer[] getMonitorStatuss() {
        return monitorStatuss;
    }

    public void setMonitorStatuss(Integer[] monitorStatuss) {
        if (monitorStatuss != null){
            recoverStatus = 2;
        }
        this.monitorStatuss = monitorStatuss;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

}
