package com.xjt.cloud.iot.core.entity.fire;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * @ClassName FireAlarmDevice
 * @Author dwt
 * @Date 2019-10-11 15:04
 * @Description 火警主机设备实体
 * @Version 1.0
 */
public class FireAlarmDevice extends BaseEntity {
    private Long deviceId;
    //设备名称
    private String deviceName;
    //品牌
    private String brand;
    //型号
    private String model;
    //传感器/传输装置名称
    private String transDeviceName;
    //传感器ID/传输装置id
    private String transDeviceId;
    private String oldTransDeviceId;
    //传输装置类型 1：直连，2：诺蒂菲尔Modbus网关,3:集中显示器
    private Integer transDeviceType;
    //火警主机编号
    private String fireAlarmNo;
    private String oldFireAlarmNo;
    //巡检点id
    private Long checkPointId;
    //设备位置
    private String devicePosition;
    //设备编码类型 0:默认不查编码表;1：userCode查询;2:按主机号,回路查询;3:按主机号,回路,地址查询,4:按userCode,主机号查询
    private Integer codeType;
    //心跳时间
    private Date endHeartbeatTime;
    //巡检点qrNo
    private String checkPointQrNo;
    //设备qrNo
    private String deviceQrNo;
    //设备状态 1：在线，2：离线
    private Integer deviceStatus;
    //status  '信息状态　1正常　99删除',
    //发送信息设备类型　1不发送　2返回信息　3主动发送
    private Integer sendType;
    private Date statusUpdateTime;
    private Long fireCount;
    private Long faultCount;
    private Long monitorCount;
    //事件状态 1-已恢复，2-未恢复
    private Integer recoverStatus;
    //巡更点名称
    private String pointName;
    //建筑物名称
    private String buildingName;
    //楼层名称
    private String floorName;
    //备注
    private String memo;
    //火警主机类型：1 新普利斯，2西门子，3 诺蒂菲尔3030，4 诺蒂菲尔6000,5 赋安火警主机，6 海湾200/5000，7 modbus网关,8 爱德华,9诺帝菲儿图文，10,新普利斯图文，11赋安火警主机接打印口
    private Integer fireAlarmType;
    private Boolean isSearchTransDeviceId;
    private Long buildingId;
    private Long buildingFloorId;
    private Long deviceTypeId;
    private Long[] ids;
    private Integer[] deviceStatusArr;
    private Long[] buildingFloorIds;
    private Long[] buildingIds;
    private Long[] projectIds;
    //0:未发送消息，1：已发送消息，2：不需要发送消息
    private Integer sendMessage;
    private Integer fireAlarmStatus;//火警主机状态，1正常 2异常
    private Integer faultStatus;//故障状态，1正常 2异常

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Integer getFireAlarmStatus() {
        return fireAlarmStatus;
    }

    public void setFireAlarmStatus(Integer fireAlarmStatus) {
        this.fireAlarmStatus = fireAlarmStatus;
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

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getFireCount() {
        return fireCount;
    }

    public void setFireCount(Long fireCount) {
        this.fireCount = fireCount;
    }

    public Long getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Long faultCount) {
        this.faultCount = faultCount;
    }

    public Long getMonitorCount() {
        return monitorCount;
    }

    public void setMonitorCount(Long monitorCount) {
        this.monitorCount = monitorCount;
    }

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getTransDeviceName() {
        return transDeviceName;
    }

    public void setTransDeviceName(String transDeviceName) {
        this.transDeviceName = transDeviceName;
    }

    public String getTransDeviceId() {
        return transDeviceId;
    }

    public void setTransDeviceId(String transDeviceId) {
        this.transDeviceId = transDeviceId;
    }

    public Integer getTransDeviceType() {
        return transDeviceType;
    }

    public void setTransDeviceType(Integer transDeviceType) {
        this.transDeviceType = transDeviceType;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getFireAlarmNo() {
        return fireAlarmNo;
    }

    public void setFireAlarmNo(String fireAlarmNo) {
        this.fireAlarmNo = fireAlarmNo;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getOldTransDeviceId() {
        return oldTransDeviceId;
    }

    public void setOldTransDeviceId(String oldTransDeviceId) {
        this.oldTransDeviceId = oldTransDeviceId;
    }

    public String getOldFireAlarmNo() {
        return oldFireAlarmNo;
    }

    public void setOldFireAlarmNo(String oldFireAlarmNo) {
        this.oldFireAlarmNo = oldFireAlarmNo;
    }

    public Integer getFireAlarmType() {
        return fireAlarmType;
    }

    public void setFireAlarmType(Integer fireAlarmType) {
        this.fireAlarmType = fireAlarmType;
    }

    public Boolean getSearchTransDeviceId() {
        return isSearchTransDeviceId;
    }

    public void setSearchTransDeviceId(Boolean searchTransDeviceId) {
        isSearchTransDeviceId = searchTransDeviceId;
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

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer[] getDeviceStatusArr() {
        return deviceStatusArr;
    }

    public void setDeviceStatusArr(Integer[] deviceStatusArr) {
        this.deviceStatusArr = deviceStatusArr;
    }

    public Long[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Long[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public Long[] getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(Long[] buildingIds) {
        this.buildingIds = buildingIds;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
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

    public Integer getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Integer sendMessage) {
        this.sendMessage = sendMessage;
    }
}
