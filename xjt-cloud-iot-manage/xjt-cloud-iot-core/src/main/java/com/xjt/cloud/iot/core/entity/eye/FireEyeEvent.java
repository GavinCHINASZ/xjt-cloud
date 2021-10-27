package com.xjt.cloud.iot.core.entity.eye;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;

/**
 * 火眼事件
 *
 * @author zhangZaifa
 * @date 2020/04/03 10:03
 */
public class FireEyeEvent extends BaseEntity {
    private Long[] projectIds;
    // 设备id
    private Long deviceId;
    /**
     * ==device_type
     */
    private Integer handleDeviceType;
    /**
     * 设备故障ID
     */
    private Long deviceFaultTypeId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备ID
     */
    private String deviceQrNo;
    /**
     * 设备类型
     * 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气
     * 10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
     */
    private Integer deviceType;
    private Integer[] deviceTypes;

    //巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    /**
     * 巡检点名称
     */
    private String checkPointName;
    // 巡检点位置（设备位置）
    private String pointLocation;

    // 火眼设备ID
    private Long fireEyeDeviceId;
    // 传感器名称
    private String sensorName;
    // 注册码
    private String sensorNo;

    /**
     * 摄像机名称
     */
    private String videoName;

    /**
     * 位置(手动输入)
     */
    private String videoLocation;

    /**
     * 旧(1、火警警告  2、烟雾警告)
     * 类脑火眼: 0:正常(恢复所有事件,不新增事件), ,98 心跳(更新心跳,不新增事件)
     *          1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障, 21离线
     */
    private Integer eventType;
    private Integer[] eventTypeArray;

    //通道号
    private Integer channelNo;

    //图片路径
    private String imageUrl;

    /**
     * 摄像头IP地址
     */
    private String ipAddress;

    /**
     * 恢复状态　0未恢复　1已恢复
     */
    private Integer recoverStatus;
    private Integer byRecoverStatus;//以恢复状态查询 0未恢复　1已恢复
    private Integer[] recoverStatusArray;

    /**
     * 恢复时间
     */
    private Date recoverTime;

    /**
     * 处理描述
     */
    private String handleDescription;

    /**
     * 处理人
     */
    private String handleUserName;

    /**
     * 滞后原因及描述/备注
     */
    private String remarks;
    //监视类型 1:正常监视，2异常监视
    private Integer monitorType;

    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirm;
    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirms;

    //处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;

    //事件处理的异常类型
    private String faultType;

    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 处理状态，1已处理  2未处理
     */
    private Integer handleStatus;
    private Integer[] handleStatuss;

    /**
     * 1今日, 2本周, 3本月
     */
    private Integer dateType;

    private Date startTime;
    private Date endTime;
    /**
     * 处理时间
     */
    private Date eventHandleTime;

    private Long[] ids;

    // 建筑物ID
    private Long buildingId;
    // 楼层ID
    private Long buildingFloorId;
    private Integer[] buildingFloorIds;
    // 建筑物名称
    private String buildingName;
    // 楼层名称
    private String floorName;

    /**
     * //分组类型　1　按时间　　2按日期 3按月  4按年
     */
    private int groupType = 1;

    private String timeType;

    public Integer getByRecoverStatus() {
        return byRecoverStatus;
    }

    public void setByRecoverStatus(Integer byRecoverStatus) {
        this.byRecoverStatus = byRecoverStatus;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
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

    public Integer getHandleDeviceType() {
        return handleDeviceType;
    }

    public void setHandleDeviceType(Integer handleDeviceType) {
        this.handleDeviceType = handleDeviceType;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public String getPointLocationDesc() {
        return getBuildingName() + getFloorName() + pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getFireEyeDeviceId() {
        return fireEyeDeviceId;
    }

    public void setFireEyeDeviceId(Long fireEyeDeviceId) {
        this.fireEyeDeviceId = fireEyeDeviceId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoLocation() {
        return videoLocation;
    }

    public String getVideoLocationDesc() {
        return getBuildingName() + getFloorName() + videoLocation;
    }

    public void setVideoLocation(String videoLocation) {
        this.videoLocation = videoLocation;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeDesc() {
        if (eventType != null){
            // 1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障
            switch (eventType){
                case 1:
                    return "烟雾预警";
                case 2:
                    return "烟雾报警";
                case 4:
                    return "火焰预警";
                case 8:
                    return "火焰报警";
                case 16:
                    return "遮挡";
                default:
                    return "故障";
            }
        }
        return "/";
    }

    public String getSignDesc() {
        if (eventType != null){
            // 1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障
            switch (eventType){
                case 1:
                    return "fireEye_smoke_warning";
                case 2:
                    return "fireEye_smoke";
                case 4:
                    return "fireEye_flame_warning";
                case 8:
                    return "fireEye_flame";
                case 16:
                    return "fireEye_occlusion";
                case 21:
                    return "fireEye_offline";
                default:
                    // 32
                    return "fireEye_fault_event";
            }
        }
        return "";
    }

    /**
     * 得到事件异常处理类型
     *
     * @return java.lang.Integer
     * @author huanggc
     * @date 2020/12/10
     */
    public Integer getFaultEventType() {
        // 事件类型: 类脑火眼: 0:正常(恢复所有事件,不新增事件), ,98 心跳(更新心跳,不新增事件)
        //      1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障, 21离线
        // 对应device_manage.d_device_fault_type的fault_event_type
        if (eventType != null) {
            return eventType;
        }
        return null;
    }

    public Integer[] getEventTypeArray() {
        return eventTypeArray;
    }

    public void setEventTypeArray(Integer[] eventTypeArray) {
        this.eventTypeArray = eventTypeArray;
    }

    public Integer[] getRecoverStatusArray() {
        return recoverStatusArray;
    }

    public void setRecoverStatusArray(Integer[] recoverStatusArray) {
        this.recoverStatusArray = recoverStatusArray;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public String getRecoverStatusDesc() {
        if (recoverStatus != null && recoverStatus == 1){
            return "已恢复";
        }
        return "未恢复";
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public String getRecoverTimeDesc() {
        return DateUtils.formatDateTime(recoverTime);
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public void setHandleDescription(String handleDescription) {
        this.handleDescription = handleDescription;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getConfirms() {
        return confirms;
    }

    public void setConfirms(Integer confirms) {
        this.confirms = confirms;
    }

    public Integer getEventExistStatus() {
        return eventExistStatus;
    }

    public void setEventExistStatus(Integer eventExistStatus) {
        this.eventExistStatus = eventExistStatus;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime());
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    public String getEventHandleTimeDesc() {
        return DateUtils.formatDateTime(eventHandleTime);
    }

    public Date getEventHandleTime() {
        return eventHandleTime;
    }

    public void setEventHandleTime(Date eventHandleTime) {
        this.eventHandleTime = eventHandleTime;
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

    public Integer[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Integer[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getBuildingName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public String getHandleStatusDesc() {
        if (handleStatus != null && handleStatus == 1){
            return "已处理";
        }
        return "未处理";
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    @JsonIgnore
    public Integer getDateTimeCount() {
        if (getEndTimeDesc() != null && getCreateTime() != null) {
            return DateUtils.getDateTimeCount(groupType, getEndTimeDesc(), getCreateTime());
        }
        return null;
    }

    @JsonIgnore
    public String getTimeType() {
        if (1 == groupType) {
            return "HOUR";
        } else if (2 == groupType) {
            return "DAY";
        } else if (3 == groupType) {
            // 计算月分
            return "MONTH";
        } else if (4 == groupType) {
            return "YEAR";
        }
        return null;
    }

}

