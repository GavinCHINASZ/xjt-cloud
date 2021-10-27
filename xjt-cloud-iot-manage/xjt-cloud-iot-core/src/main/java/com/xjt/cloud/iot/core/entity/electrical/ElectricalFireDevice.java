package com.xjt.cloud.iot.core.entity.electrical;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @ClassName ElectricalFireDevice
 * @Author dwt
 * @Date 2020-09-07 14:34
 * @Description 电气火灾设备实体
 * @Version 1.0
 */
public class ElectricalFireDevice extends BaseEntity {
    private String deviceName;//设备名称
    private String leakageCurrValue;//漏电流上限值
    private String tempValue;//温度上限值
    private Integer workStatus;//工作状态 1:在线，2:离线
    private Integer deviceStatus;//设备状态 1:正常，2:故障
    private Integer sendMessage;//信息是否发送 1:已发送，2 未发送
    private String deviceLocation;//设备位置
    private String pointQrNo;//巡查点二维码
    private Long checkPointId;//巡查点id
    private String deviceQrNo;//设备二维码
    private Long deviceId;//设备id
    private String sensorNo;//探测器Id
    private Date endHeartbeatTime;//心跳时间
    private Long buildingId;//建筑物id
    private Long floorId;//楼层id
    private String buildingName;//建筑物名称
    private String floorName;//楼层名称
    private Integer onLineNum;
    private Integer offLineNum;
    private Long[] ids;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLeakageCurrValue() {
        return leakageCurrValue;
    }

    public void setLeakageCurrValue(String leakageCurrValue) {
        this.leakageCurrValue = leakageCurrValue;
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
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

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
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

    public String getBuildingName() {
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

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Integer getOnLineNum() {
        return onLineNum;
    }

    public void setOnLineNum(Integer onLineNum) {
        this.onLineNum = onLineNum;
    }

    public Integer getOffLineNum() {
        return offLineNum;
    }

    public void setOffLineNum(Integer offLineNum) {
        this.offLineNum = offLineNum;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getWorkStatusDesc(){
        if(workStatus == 1){
            return "在线";
        }
        return "离线";
    }
    public String getDeviceStatusDesc(){
        if(deviceStatus == 1){
            return "正常";
        }
        return "故障";
    }

    public Integer getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Integer sendMessage) {
        this.sendMessage = sendMessage;
    }
}
