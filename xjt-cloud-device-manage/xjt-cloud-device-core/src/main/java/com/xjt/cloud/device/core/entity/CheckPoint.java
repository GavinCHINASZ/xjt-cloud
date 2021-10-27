package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:29
 * @Description:巡检点信息实体类
 */
public class CheckPoint extends BaseEntity {
    private Long buildingId;// 建筑物id
    private Long buildingFloorId;// 楼层id
    private Long[] buildingIds;//建筑物id
    private Long[] buildingFloorIds;//建筑物楼层id
    private String qrNo;// 	二维码
    private String qrNoOrName;// 	二维码或名称
    private String byQrNo;// 以二维码编号做为条件时传参
    private String pointLocation;// 巡检位置
    private String pointName;// 巡检名称
    private Long projectId;// 项目id
    private Long orgId;// 部门id
    private Long iotId;//物联网关联ID
    private Long[] orgIds;// 部门id
    private String manageRegion;//管理区域
    private String memo;//备注
    private String sensorNo;//设备传感器id
    private Integer deviceStatus;//巡检点设备状态，只要其下的设备有一个异常测为异常 0 正常 1异常
    private Integer lng;//经度值 * 100000
    private Integer lat;//纬度值* 100000
    private Integer baiduLng;//百度经度值 * 100000
    private Integer baiduLat;//百度纬度值* 100000
    private String[] ids;
    private String imgUrl;//现场照片
    private String pinYinInitials;//拼音首写字母
    private Date statusUpdateTime;//最新巡检时间
    private Integer num;//数量
    private Integer deviceType;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
    private Integer[] deviceTypes;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
    private Integer nullQrNo;//1为空 0 为非空 null为所有
    private Long deviceTypeId;//设备类型id
    private Long[] deviceTypeIds;//设备类型ids
    private Integer pointLayout;//是否查询巡检点布点信息 0/null不查询  1查询已布点  2查询未布点
    private Long taskId;
    private Integer taskCheckPointStatus;

    public Long getIotId() {
        return iotId;
    }

    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    public Integer getBaiduLng() {
        return baiduLng;
    }

    public void setBaiduLng(Integer baiduLng) {
        this.baiduLng = baiduLng;
    }

    public Integer getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(Integer baiduLat) {
        this.baiduLat = baiduLat;
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

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getQrNoOrName() {
        return qrNoOrName;
    }

    public void setQrNoOrName(String qrNoOrName) {
        this.qrNoOrName = qrNoOrName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getManageRegion() {
        return manageRegion;
    }

    public void setManageRegion(String manageRegion) {
        this.manageRegion = manageRegion;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPinYinInitials() {
        return pinYinInitials;
    }

    public void setPinYinInitials(String pinYinInitials) {
        this.pinYinInitials = pinYinInitials;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public String getByQrNo() {
        return byQrNo;
    }

    public void setByQrNo(String byQrNo) {
        this.byQrNo = byQrNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
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

    public Integer getNullQrNo() {
        return nullQrNo;
    }

    public void setNullQrNo(Integer nullQrNo) {
        this.nullQrNo = nullQrNo;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Integer getPointLayout() {
        return pointLayout;
    }

    public void setPointLayout(Integer pointLayout) {
        this.pointLayout = pointLayout;
    }

    public Long[] getDeviceTypeIds() {
        return deviceTypeIds;
    }

    public void setDeviceTypeIds(Long[] deviceTypeIds) {
        this.deviceTypeIds = deviceTypeIds;
    }

    /**
     *
     * 功能描述: 以项目id得到项目名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getProjectName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, getProjectId(), "projectName");
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
    public String getOrgName(){
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
    public String getCompanyName(){
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
    public String getBuildingFloorName(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskCheckPointStatus() {
        return taskCheckPointStatus;
    }

    public void setTaskCheckPointStatus(Integer taskCheckPointStatus) {
        this.taskCheckPointStatus = taskCheckPointStatus;
    }
}
