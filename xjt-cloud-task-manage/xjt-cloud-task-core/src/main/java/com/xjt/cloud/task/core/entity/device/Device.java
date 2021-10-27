package com.xjt.cloud.task.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.task.core.entity.check.CheckItemTask;

import java.util.Date;
import java.util.List;

/**
 * 设备信息实体类
 *
 * @author wangzhiwen
 * @date 2019/7/18 17:29
 */
public class Device extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    private Long checkPointId;//巡检点id
    private Long iotId;//物联网关联ID
    // 物联网设备关联类型 1、水浸 2、可燃气
    private Integer iotType;
    private String deviceName;//设备名称
    private String pointName;//巡检点名称
    private String oldDeviceQrNo;//二维码编号（历史记录）
    private String qrNo;// 二维码编号
    private String byQrNo;// 以二维码编号做为条件时传参
    private String pointQrNo;//巡检点二维码
    private Integer num;//数量
    private Integer faultNum;// 故障数量
    private Integer normalNum;// 正常数量
    private Integer deviceCount;//设备数量
    private String brand;//	品牌
    private String model;//	产品型号
    private String spec;//	规格参数

    private Date productionDate;//生产日期
    private Date expiryDate;//有效期
    private Date expiryDateEnd;//有效期结束

    private String memo;//备注

    private Date statusUpdateTime;//最新状态修改时间
    private Integer deviceStatus;//最新设备状态0：正常，1：故障，2：未检

    private Date sendModifyTime;//送修改时间
    private Date sendModifyTimeEnd;//送修改时间结束

    private String[] ids;
    private String deviceSysName;//设备系统名称，也为设备名称
    private String imgUrl;//设备图片
    private boolean nullQrNo;//是否查询空设备码
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//建筑物楼层id
    private String pointLocation;//位置
    private List<CheckItemTask> checkItemTaskList;
    private Long deviceId;
    private String deviceQrNo;
    private String buildingName;//建筑物名称
    private String floorName;//楼层名称
    private String description;//描述
    private Date checkTime;//巡检时间
    private String manageRegion;//管理区域
    private Integer checkItemVsType;//巡检项的项目类型，1默认 2精简版 3项目自定义

    /**
     * 1默认(所有任务周期类型都要查) 2月度,3季度,4年度
     */
    private Integer checkDateType;
    private Integer[] checkDateTypeArr;

    public String getManageRegion() {
        return manageRegion;
    }

    public void setManageRegion(String manageRegion) {
        this.manageRegion = manageRegion;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<CheckItemTask> getCheckItemTaskList() {
        return checkItemTaskList;
    }

    public void setCheckItemTaskList(List<CheckItemTask> checkItemTaskList) {
        this.checkItemTaskList = checkItemTaskList;
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

    public Integer getDeviceCount() {
        if (null == deviceCount) {
            return 0;
        }
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public Integer getIotType() {
        return iotType;
    }

    public void setIotType(Integer iotType) {
        this.iotType = iotType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
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

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
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

    public Integer getFaultNum() {
        if (null == faultNum) {
            return 0;
        }
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getNormalNum() {
        if (normalNum == null) {
            return 0;
        }
        return normalNum;
    }

    public void setNormalNum(Integer normalNum) {
        this.normalNum = normalNum;
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

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
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

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }

    public Integer getCheckDateType() {
        return checkDateType;
    }

    public void setCheckDateType(Integer checkDateType) {
        this.checkDateType = checkDateType;
    }

    public Integer[] getCheckDateTypeArr() {
        return checkDateTypeArr;
    }

    public void setCheckDateTypeArr(Integer[] checkDateTypeArr) {
        this.checkDateTypeArr = checkDateTypeArr;
    }
}
