package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/30 14:59
 * @Description: 楼层巡检点点位
 */
public class FloorPointPosition extends BaseEntity {
    private String ids;
    private Long floorId;//楼层id
    private Long checkPointId;//巡检点id
    private Long[] byCheckPointIds;//巡检点id
    private Long[] byDeviceIds;//巡检点id
    private Long orgId;//部门id，不按部门布点时为0
    private Long deviceId;//设备id
    private Integer sort;//布点顺序
    private Integer axisX;//布点x轴
    private Integer axisY;//布点y轴
    private String pointName;//巡检点名称
    private String pointLocation;//位置
    private String qrNo;// 	二维码
    private String pointQrNo;//巡检点二维码
    private Long buildingId;//楼层id
    private Long deviceTypeId;//设备类型id
    private String deviceName;//设备名称
    private Long upperId;//上一个节点的主键id
    private Long nextId;//下一个切点的主键id
    private Long oldUpperId;//上一个节点的主键id
    private Long oldNextId;//下一个切点的主键id
    private String oldUpperIds;//上一个节点的主键id
    private String oldNextIds;//下一个切点的主键id
    private String points;//空点记录信息
    private Integer checkPointStatus;//巡检点状态  1 正常 2异常
    private Integer devicePointStatus;//设备状态  1 正常 2异常
    private Date statusUpdateTime;//设备状态最后修改时间
    private Long taskId;

    public Integer getDevicePointStatus() {
        return devicePointStatus;
    }

    public void setDevicePointStatus(Integer devicePointStatus) {
        this.devicePointStatus = devicePointStatus;
    }

    public Long[] getByDeviceIds() {
        return byDeviceIds;
    }

    public void setByDeviceIds(Long[] byDeviceIds) {
        this.byDeviceIds = byDeviceIds;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long[] getByCheckPointIds() {
        return byCheckPointIds;
    }

    public void setByCheckPointIds(Long[] byCheckPointIds) {
        this.byCheckPointIds = byCheckPointIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getAxisX() {
        return axisX;
    }

    public void setAxisX(Integer axisX) {
        this.axisX = axisX;
    }

    public Integer getAxisY() {
        return axisY;
    }

    public void setAxisY(Integer axisY) {
        this.axisY = axisY;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Long getUpperId() {
        return upperId;
    }

    public void setUpperId(Long upperId) {
        this.upperId = upperId;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Long getOldUpperId() {
        return oldUpperId;
    }

    public void setOldUpperId(Long oldUpperId) {
        this.oldUpperId = oldUpperId;
    }

    public Long getOldNextId() {
        return oldNextId;
    }

    public void setOldNextId(Long oldNextId) {
        this.oldNextId = oldNextId;
    }

    public Integer getCheckPointStatus() {
        return checkPointStatus;
    }

    public void setCheckPointStatus(Integer checkPointStatus) {
        this.checkPointStatus = checkPointStatus;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public String getOldUpperIds() {
        return oldUpperIds;
    }

    public void setOldUpperIds(String oldUpperIds) {
        this.oldUpperIds = oldUpperIds;
    }

    public String getOldNextIds() {
        return oldNextIds;
    }

    public void setOldNextIds(String oldNextIds) {
        this.oldNextIds = oldNextIds;
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
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getFloorId(), "floorName");
    }
    /**
     *
     * 功能描述:得到设备类型名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getDeviceTypeName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, deviceTypeId, "deviceSysName");
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
