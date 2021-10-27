package com.xjt.cloud.iot.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * 设备信息实体类
 *
 * @author wangzhiwen
 * @date 2019/7/18 17:29
 */
public class Device extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    // 默认 1 默认设备
    // 水压(2水压监测 13水厢以前单位为Ｍ  14两端压力设备)
    // 3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感(旧)
    // 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控   19智能无线烟感  20声光报警装置
    private Integer deviceType;

    /**
     * 巡检点id
     */
    private Long checkPointId;

    /**
     * 传感器id
     */
    private String sensorNo;

    private String deviceName;//设备名称
    private String pointName;//巡检点名称
    private String oldDeviceQrNo;//二维码编号（历史记录）
    private String qrNo;// 二维码编号
    private String byQrNo;// 以二维码编号做为条件时传参
    private String pointQrNo;//巡检点二维码
    private Integer num;//数量
    private String brand;//	品牌
    private String model;//	产品型号
    private String spec;//	规格参数
    private Date productionDate;//生产日期
    private Date expiryDate;//有效期
    private Date expiryDateEnd;//有效期结束
    private String memo;//备注
    private Date statusUpdateTime;//最新状态修改时间
    private Integer deviceStatus;//最新设备状态0：正常，1：故障
    private Date sendModifyTime;//送修改时间
    private Date sendModifyTimeEnd;//送修改时间结束
    private Long[] ids;
    private String deviceSysName;//设备系统名称，也为设备名称
    private String imgUrl;//设备图片
    private boolean nullQrNo;//是否查询空设备码
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//建筑物楼层id
    private String pointLocation;//位置

    /**
     * 物联网关联ID
     */
    private Long iotId;
    private Long[] iotIds;
    /**
     * 1：正常，2:异常
     */
    private Integer iotStatus;

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

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public Long[] getIotIds() {
        return iotIds;
    }

    public void setIotIds(Long[] iotIds) {
        this.iotIds = iotIds;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOldDeviceQrNo() {
        return oldDeviceQrNo;
    }

    public void setOldDeviceQrNo(String oldDeviceQrNo) {
        this.oldDeviceQrNo = oldDeviceQrNo;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Date getSendModifyTime() {
        return sendModifyTime;
    }

    public void setSendModifyTime(Date sendModifyTime) {
        this.sendModifyTime = sendModifyTime;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
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

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getByQrNo() {
        return byQrNo;
    }

    public void setByQrNo(String byQrNo) {
        this.byQrNo = byQrNo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getExpiryDateEnd() {
        return expiryDateEnd;
    }

    public void setExpiryDateEnd(Date expiryDateEnd) {
        this.expiryDateEnd = expiryDateEnd;
    }

    public Date getSendModifyTimeEnd() {
        return sendModifyTimeEnd;
    }

    public void setSendModifyTimeEnd(Date sendModifyTimeEnd) {
        this.sendModifyTimeEnd = sendModifyTimeEnd;
    }

    public boolean isNullQrNo() {
        return nullQrNo;
    }

    public void setNullQrNo(boolean nullQrNo) {
        this.nullQrNo = nullQrNo;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Integer getIotStatus() {
        return iotStatus;
    }

    public void setIotStatus(Integer iotStatus) {
        this.iotStatus = iotStatus;
    }
}
