package com.xjt.cloud.iot.core.entity.electrical;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;

/**
 * @ClassName ElectricalFireEvent
 * @Author dwt
 * @Date 2020-09-11 14:57
 * @Description 电气火灾事件实体
 * @Version 1.0
 */
public class ElectricalFireEvent extends BaseEntity {

    private Integer eventType;//事件类型 1漏电流报警，2温度报警，3故障，4离线，5漏电流故障恢复，6温度故障恢复，7离线恢复
    private Integer faultType;//故障类型 1漏电流故障，2温度故障
    private String deviceName;//设备名称
    private String description;//描述
    private String leakageCurrValue;//漏电流上限值
    private String tempValue;//温度上限值
    private String leakageAlarmValue;//漏电流报警值
    private String tempAlarmValue;//温度报警值
    private Long recordId;//记录id
    private Date recoverTime;//恢复时间
    private Long recoverRecordId;//恢复记录id
    private Integer recoverStatus;//恢复状态 1：已恢复，2：未恢复
    private Integer handleStatus;//处理状态 1：已处理，2：未处理
    private Integer channel;//通道号
    private String sensorNo;//探测器id
    private Long deviceId;//设备id
    private Long alarmDeviceId;//报警电气火灾id
    private String remarks;//备注，描述
    private String url;//图片路径地址
    private Long handlerId;//处理人id
    private String handlerName;//处理人名称
    private Integer[] eventTypeArr;
    private Date startTime;
    private Date endTime;
    private Long[] projectIds;
    private Integer dateType;
    private Integer dateNum;
    private String deviceLocation;
    private String pointQrNo;//巡查点二维码
    private Long checkPointId;//巡查点id
    private String deviceQrNo;//设备二维码
    private Long[] ids;
    private Integer[] handleStatuss;
    private Integer[] recoverStatuss;

    public ElectricalFireEvent(){

    }

    public ElectricalFireEvent(ElectricalFireDevice fireDevice){
        this.setDeviceQrNo(fireDevice.getDeviceQrNo());
        this.setPointQrNo(fireDevice.getPointQrNo());
        this.setSensorNo(fireDevice.getSensorNo());
        this.setDeviceLocation(fireDevice.getDeviceLocation());
        this.setTempAlarmValue(fireDevice.getTempValue());
        this.setLeakageCurrValue(fireDevice.getLeakageCurrValue());
        this.setDeviceName(fireDevice.getDeviceName());
        this.setDeviceId(fireDevice.getDeviceId());
        this.setAlarmDeviceId(fireDevice.getId());
        this.setProjectId(fireDevice.getProjectId());
    }
    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLeakageAlarmValue() {
        return leakageAlarmValue;
    }

    public void setLeakageAlarmValue(String leakageAlarmValue) {
        this.leakageAlarmValue = leakageAlarmValue;
    }

    public String getTempAlarmValue() {
        return tempAlarmValue;
    }

    public void setTempAlarmValue(String tempAlarmValue) {
        this.tempAlarmValue = tempAlarmValue;
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

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getAlarmDeviceId() {
        return alarmDeviceId;
    }

    public void setAlarmDeviceId(Long alarmDeviceId) {
        this.alarmDeviceId = alarmDeviceId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecoverRecordId() {
        return recoverRecordId;
    }

    public void setRecoverRecordId(Long recoverRecordId) {
        this.recoverRecordId = recoverRecordId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Integer[] getEventTypeArr() {
        return eventTypeArr;
    }

    public void setEventTypeArr(Integer[] eventTypeArr) {
        this.eventTypeArr = eventTypeArr;
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

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public Integer getDateNum() {
        return dateNum;
    }

    public void setDateNum(Integer dateNum) {
        this.dateNum = dateNum;
    }

    public Integer getFaultType() {
        return faultType;
    }

    public void setFaultType(Integer faultType) {
        this.faultType = faultType;
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

    public String getCreateTimeDesc(){
        if(getCreateTime() != null){
            return DateUtils.formatDateTime(getCreateTime());
        }
        return "/";
    }

    public String getRecoverTimeDesc(){
        if(recoverTime != null){
            return DateUtils.formatDateTime(recoverTime);
        }
        return "/";
    }

    public String getRecoverStatusDesc(){
        if(recoverStatus == 1){
            return "已恢复";
        }
        return "未恢复";
    }

    public String getHandleStatusDesc(){
        if(handleStatus == 1){
            return "已处理";
        }
        return "未处理";
    }
    //事件类型 1漏电流报警，2温度报警，3故障，4离线，5漏电流故障恢复，6温度故障恢复，7离线恢复
    public String getEventTypeDesc(){
        switch (eventType){
            case 1:
                return "漏电流报警";
            case 2:
                return "温度报警";
            case 3:
                return "故障事件";
            case 4:
                return "离线事件";
            case 5:
                return "漏电流故障恢复";
            case 6:
                return "温度故障恢复";
            case 7:
                return "离线恢复";
            default:
                return "未知事件";
        }
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
    }

    public Integer[] getRecoverStatuss() {
        return recoverStatuss;
    }

    public void setRecoverStatuss(Integer[] recoverStatuss) {
        this.recoverStatuss = recoverStatuss;
    }
}
