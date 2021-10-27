package com.xjt.cloud.iot.core.entity.vesa;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 17:03
 * @Description: 水压设备信息对象
 */
public class VesaDevice extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    // 设备类型　默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控
    private Integer deviceType;
    private Long checkPointId;//巡检点id
    private Long deviceId;//设备id
    private String sensorName;//传感器名称
    private String sensorNo;//传感器id
    private String brand;//设备品牌
    private String model;//设备型号

    private Integer fireAlarm2Num; //火警2的数量
    private Integer fireAlarm1Num; //火警1的数量
    private Integer actionNum; //行动的数量
    private Integer alarmNum; //警告的数量
    private Integer faultNum; //故障数量

    private Integer deviceStatus;//设备状态　1正常　2离线
    private Date statusUpdateTime;//最新状态修改时间
    private Date endHeartbeatTime;//最新状态修改时间

    //默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
    private Integer type;
    private String deviceSysName;//设备名称
    private String qrNo;//设备二维码
    private String pointLocation;//巡检点位置（设备位置）
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private String simCard;//卡号
    private Integer heartbeat;//心跳发送间隔       09~13 对应 60,120,360,720,1440分钟
    private Long[] ids;
    private String pointName;//巡检点名称
    private String pointQrNo;//巡检点二维码
    private Integer[] deviceStatuss;//查询条件　设备状态
    private Date startTime;
    private Date endTime;

    //标题
    private String title;
    private Integer fireAlarm1Status;//火警1状态，1正常 2异常
    private Integer fireAlarm2Status;//火警2状态，1正常 2异常
    private Integer faultStatus;//故障状态，1正常 2异常

    public Integer getFireAlarm1Status() {
        return fireAlarm1Status;
    }

    public void setFireAlarm1Status(Integer fireAlarm1Status) {
        this.fireAlarm1Status = fireAlarm1Status;
    }

    public Integer getFireAlarm2Status() {
        return fireAlarm2Status;
    }

    public void setFireAlarm2Status(Integer fireAlarm2Status) {
        this.fireAlarm2Status = fireAlarm2Status;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Integer getFireAlarm2Num() {
        return fireAlarm2Num;
    }

    public void setFireAlarm2Num(Integer fireAlarm2Num) {
        this.fireAlarm2Num = fireAlarm2Num;
    }

    public Integer getFireAlarm1Num() {
        return fireAlarm1Num;
    }

    public void setFireAlarm1Num(Integer fireAlarm1Num) {
        this.fireAlarm1Num = fireAlarm1Num;
    }

    public Integer getActionNum() {
        return actionNum;
    }

    public void setActionNum(Integer actionNum) {
        this.actionNum = actionNum;
    }

    public Integer getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatusDesc() {
        Integer s = getDeviceStatus();
        if (s != null) {
            if (s == 1) {
                return "在线";
            } else if (s == 2) {
                return "离线";
            }
        }
        return "在线";
    }

    public Date getStatusUpdateTime() {
        return this.statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
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

    public String getBuildingNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public String getBuildingFloorNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Date getEndHeartbeatTime() {
        return this.endHeartbeatTime;
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer[] getDeviceStatuss() {
        return deviceStatuss;
    }

    public void setDeviceStatuss(Integer[] deviceStatuss) {
        this.deviceStatuss = deviceStatuss;
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
}
