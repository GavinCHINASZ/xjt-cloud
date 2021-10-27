package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 设备信息实体类
 * 
 * @author wangzhiwen
 * @date 2019/7/18 17:29
 */
public class Device extends BaseEntity {
    private Long parentId;
    private Long orgType;
    private Long deviceTypeId;//设备类型ID
    private Long[] deviceTypeIds;//设备类型IDs
    private String projectIds;//项目ID
    private String appId;//客户应用id
    private String projectName;//项目名称
    private Long checkPointId;//巡检点id
    private Long byCheckPointId;//巡检点id
    private String[] byCheckPointIds;//巡检点id
    private Integer pointLayout;//是否查询巡检点布点信息 0/null不查询  1查询已布点  2查询未布点
    // 传感器ID
    private String sensorNo;
    private Long iotId;//物联网关联ID
    //物联网状态 1：正常，2:异常
    private Long iotStatus;
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
    private String[] ids;
    private String deviceSysName;//设备系统名称，也为设备名称
    private String imgUrl;//设备图片
    private boolean nullQrNo;//是否查询空设备码

    private Long buildingId;//建筑物id
    private String buildingName;//建筑物名称
    private Long buildingFloorId;//建筑物楼层id
    private String floorName;//建筑物楼层

    private String useMethod;//设备使用方法
    private Long[] buildingIds;//建筑物id
    private Long[] buildingFloorIds;//建筑物楼层id
    private String pointLocation;//位置

    // 默认 1 默认设备
    // 水压(2水压监测 13水厢以前单位为Ｍ  14两端压力设备)
    // 3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感(旧)
    // 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控   19智能无线烟感  20声光报警装置
    private Integer deviceType;
    private Integer[] deviceTypes;
    private String pinYinInitials;//拼音首写字母
    private Integer linkageType;//查询联动设备关联 1查询未关联  2查询已关联
    private Boolean iotRelation;//iot设备关联状态，true已关联
    private String pointDeviceNameQrNo;//巡检点设备名称与id查询条件

    public String getPointDeviceNameQrNo() {
        return pointDeviceNameQrNo;
    }

    public void setPointDeviceNameQrNo(String pointDeviceNameQrNo) {
        this.pointDeviceNameQrNo = pointDeviceNameQrNo;
    }

    public Integer getPointLayout() {
        return pointLayout;
    }

    public void setPointLayout(Integer pointLayout) {
        this.pointLayout = pointLayout;
    }

    public Integer getLinkageType() {
        return linkageType;
    }

    public Boolean getIotRelation() {
        return iotRelation;
    }

    public void setIotRelation(Boolean iotRelation) {
        this.iotRelation = iotRelation;
    }

    public void setLinkageType(Integer linkageType) {
        this.linkageType = linkageType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOrgType() {
        return orgType;
    }

    public void setOrgType(Long orgType) {
        this.orgType = orgType;
    }

    public String getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(String useMethod) {
        this.useMethod = useMethod;
    }

    public Long[] getDeviceTypeIds() {
        return deviceTypeIds;
    }

    public void setDeviceTypeIds(Long[] deviceTypeIds) {
        this.deviceTypeIds = deviceTypeIds;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Long getIotStatus() {
        return iotStatus;
    }

    public void setIotStatus(Long iotStatus) {
        this.iotStatus = iotStatus;
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

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public Long getByCheckPointId() {
        return byCheckPointId;
    }

    public void setByCheckPointId(Long byCheckPointId) {
        this.byCheckPointId = byCheckPointId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String[] getByCheckPointIds() {
        return byCheckPointIds;
    }

    public void setByCheckPointIds(String[] byCheckPointIds) {
        this.byCheckPointIds = byCheckPointIds;
    }

    public Long[] getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(Long[] buildingIds) {
        this.buildingIds = buildingIds;
    }

    public Long[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Long[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public String getPinYinInitials() {
        return pinYinInitials;
    }

    public void setPinYinInitials(String pinYinInitials) {
        this.pinYinInitials = pinYinInitials;
    }

    public Integer getSaveDeviceType() {
        if (deviceTypeId != null && deviceTypeId > 0){
            String value = CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, deviceTypeId, "deviceType");
            if (StringUtils.isNotEmpty(value)) {
                return Integer.valueOf(value);
            }
        }
        return null;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getBuildingFloorName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }
}
