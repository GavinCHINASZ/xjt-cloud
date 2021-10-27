package com.xjt.cloud.iot.core.entity.smoke;

import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @author wangzhiwen
 * @date 2019/8/5 09:41
 */
public class PointDevice {
    private Long id;//巡检点id
    private Long checkPointId;//巡检点id
    private Long[] ids;//巡检点id
    private Long deviceId;//设备id
    private Long rowNum;// 建筑物id
    private String pointName;// 巡检名称
    private String qrNo;// 	二维码
    private String checkPointQrNo;// 	二维码

    private String companyName;//公司名称
    private Long companyId;//公司id
    private String buildingName;//建筑物名称
    private Long buildingId;//建筑物id
    private String buildingFloorName;//楼层名称
    private Long buildingFloorId;//楼层id
    // 楼层名称
    private String floorName;

    private String orgName;//责任单位
    private Long orgId;//责任单位id
    private String pointLocation;// 巡检位置
    private String manageRegion;//管理区域

    private String deviceQrNo;// 设备二维码编号
    private String deviceName;//设备名称

    // 传感器ID
    private String sensorId;
    private String cardId;
    private Long deviceTypeId;

    private String memo;//备注
    private Integer num;//数量
    private String brand;//	品牌
    private String model;//	产品型号
    private String spec;//	规格参数
    private Date productionDate;//生产日期
    private Long projectId;//项目id
    private String projectName;
    private String deviceSysName;//设备系统名称
    private Long deviceSysId;//设备系统Id
    private Date expiryDate;//有效期
    private String expiryDateStr;//有效期
    private Date expiryDateEnd;//有效期结束
    private Date statusUpdateTime;//最新状态修改时间
    private String deviceStatus;//最新设备状态0：正常，1：故障
    private Date sendModifyTime;//送修改时间
    private String sendModifyTimeStr;//送修改时间
    private Date sendModifyTimeEnd;//送修改时间结束
    private String pinYinInitials;//拼音首写字母
    private String createUserName;
    private Long createUserId;
    private String[] orderCols;
    private String orderStr;
    private boolean orderDesc;
    private Boolean nullQrNo;//是否查询空二维码

    public Boolean getNullQrNo() {
        return nullQrNo;
    }

    public void setNullQrNo(Boolean nullQrNo) {
        this.nullQrNo = nullQrNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getManageRegion() {
        return manageRegion;
    }

    public void setManageRegion(String manageRegion) {
        this.manageRegion = manageRegion;
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

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getCardId() {
        if (StringUtils.isEmpty(cardId)){
            return "123456789";
        }
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingFloorName() {

        return this.buildingName;
    }

    public void setBuildingFloorName(String buildingFloorName) {
        this.buildingFloorName = buildingFloorName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getFloorName() {

        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Long getDeviceSysId() {
        return deviceSysId;
    }

    public void setDeviceSysId(Long deviceSysId) {
        this.deviceSysId = deviceSysId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public String getStatusUpdateTimeDesc() {
        if (statusUpdateTime != null){
            DateUtils.formatDateTime(statusUpdateTime);
        }
        return null;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Date getSendModifyTime() {
        return sendModifyTime;
    }

    public void setSendModifyTime(Date sendModifyTime) {
        this.sendModifyTime = sendModifyTime;
    }

    public String getPinYinInitials() {
        return pinYinInitials;
    }

    public void setPinYinInitials(String pinYinInitials) {
        this.pinYinInitials = pinYinInitials;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        this.expiryDateStr = expiryDateStr;
    }

    public String getSendModifyTimeStr() {
        return sendModifyTimeStr;
    }

    public void setSendModifyTimeStr(String sendModifyTimeStr) {
        this.sendModifyTimeStr = sendModifyTimeStr;
    }

    public String[] getOrderCols() {
        if (this.orderCols != null && this.orderCols.length > 0) {
            String[] strs = new String[this.orderCols.length];

            for(int i = 0; i < this.orderCols.length; ++i) {
                strs[i] = StringUtils.humpToLine(this.orderCols[i]);
            }

            return strs;
        } else {
            return null;
        }
    }

    public void setOrderCols(String[] orderCols) {
        this.orderCols = orderCols;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public boolean getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(boolean orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Integer getSaveDeviceType() {
        if (deviceSysId != null && deviceSysId > 0){
            String value = CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, deviceSysId, "deviceType");
            if (StringUtils.isNotEmpty(value)) {
                return Integer.valueOf(value);
            }
        }
        return null;
    }

    /**
     *
     * 功能描述: 以部门id从缓存中得到部门名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getOrgNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, getOrgId(), "orgName");
    }

    /**
     *
     * 功能描述:以部门id从缓存中得到公司id，再以公司id得到公司名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getCompanyNameDesc(){
        String coId = CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, getOrgId(), "owerCompany");
        if(StringUtils.isNotEmpty(coId)) {
            return CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, Long.valueOf(coId), "orgName");
        }
        return null;
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
    public String getBuildingNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
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
    public String getBuildingFloorNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }
    /**
     *
     * 功能描述:得到项目名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getProjectNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, getProjectId(), "projectName");
    }


}
