package com.xjt.cloud.iot.core.entity.linkage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 联动事件 实体类
 *
 * @author huanggc
 * @date 2020-03-30
 **/
public class LinkageEvent extends BaseEntity {
    private Long[] ids;
    private Long[] projectIds;

    // 联动设备ID
    private Long linkageDeviceId;
    private Long[] linkageDeviceIds;

    // 设备ID
    private Long deviceId;
    private Long faultDeviceId;
    // 设备名称
    private String deviceName;
    private String faultDeviceName;
    // 设备二维码
    private String deviceQrNo;
    private String faultDeviceQrNo;
    // 设备类型ID
    private Long deviceTypeId;

    // 巡检点ID
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    private String faultCheckPointQrNo;
    // 巡检点名称
    private String checkPointName;
    // 位置
    private String pointLocation;

    // 传感器ID
    private String sensorId;

    // 事件类型 2报警, 3离线
    private Integer eventType;
    private Integer[] eventTypes;

    // 管道
    private Integer channel;

    // 事件状态 1已恢复, 2未恢复
    private Integer recoverStatus;
    private Integer[] recoverStatuss;

    // 最后心跳时间, 恢复时间
    private Date endHeartbeatTime;

    // 建筑物ID
    private Long buildingId;
    // 建筑物名称
    private String buildingName;
    // 楼层ID
    private Long buildingFloorId;

    //楼层id数组
    private Integer[] buildingFloorIds;

    // 楼层名称
    private String floorName;
    private Date beginTime;
    private Date endTime;
    //分组类型　1　按时间　　2按日期 3按月  4按年
    private int groupType = 1;
    private String timeDesc;

    private Integer totalCount;
    // 异常设备数
    private Integer failDevice;
    // 离线数
    private Integer offLine;
    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirm;

    //处理状态，1已处理  2未处理
    private Integer handleStatus;
    //处理状态，1已处理  2未处理
    private Integer[] handleStatuss;

    // 处理时间
    private Date endHandleTimes;

    // 处理描述
    private String handleDescription;

    // 处理人
    private String handleUserName;

    // 处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;

    //查询异常类型
    private Integer byFaultEventType;
    //异常类型id
    private Long faultTypeId;
    //事件处理的异常类型
    private String faultType;
    //物联设备异常处理类型id
    private Long deviceFaultTypeId;
    //默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备',
    private Integer handleDeviceType;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Long getLinkageDeviceId() {
        return linkageDeviceId;
    }

    public void setLinkageDeviceId(Long linkageDeviceId) {
        this.linkageDeviceId = linkageDeviceId;
    }

    public Long[] getLinkageDeviceIds() {
        return linkageDeviceIds;
    }

    public void setLinkageDeviceIds(Long[] linkageDeviceIds) {
        this.linkageDeviceIds = linkageDeviceIds;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getFaultDeviceId() {
        return faultDeviceId;
    }

    public void setFaultDeviceId(Long faultDeviceId) {
        this.faultDeviceId = faultDeviceId;
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

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public String getFaultDeviceName() {
        return faultDeviceName;
    }

    public void setFaultDeviceName(String faultDeviceName) {
        this.faultDeviceName = faultDeviceName;
    }

    public String getFaultDeviceQrNo() {
        return faultDeviceQrNo;
    }

    public void setFaultDeviceQrNo(String faultDeviceQrNo) {
        this.faultDeviceQrNo = faultDeviceQrNo;
    }

    public String getFaultCheckPointQrNo() {
        return faultCheckPointQrNo;
    }

    public void setFaultCheckPointQrNo(String faultCheckPointQrNo) {
        this.faultCheckPointQrNo = faultCheckPointQrNo;
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

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeDesc() {
        if (eventType != null) {
            switch (eventType) {
                case 2:
                    return "报警";
            }
        }
        return "离线";
    }

    public Integer[] getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Integer[] eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Integer[] getRecoverStatuss() {
        return recoverStatuss;
    }

    public void setRecoverStatuss(Integer[] recoverStatuss) {
        this.recoverStatuss = recoverStatuss;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public String getRecoverStatusDesc() {
        if (recoverStatus != null && recoverStatus == 1) {
            return "已恢复";
        }
        return "未恢复";
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public String getEndHeartbeatTimeDesc() {
        return DateUtils.formatDateTime(endHeartbeatTime);
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getFloorName() {
        floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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

    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime());
    }

    public String getUpdateUserNameDesc() {
        return getUpdateUserName() == null ? "/" : getUpdateUserName();
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
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
        } else if (3 == groupType) {//计算月分
            return "MONTH";
        } else if (4 == groupType) {
            return "YEAR";
        }
        return null;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFailDevice() {
        return failDevice;
    }

    public void setFailDevice(Integer failDevice) {
        this.failDevice = failDevice;
    }

    public Integer getOffLine() {
        return offLine;
    }

    public void setOffLine(Integer offLine) {
        this.offLine = offLine;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleStatusDesc() {
        if (handleStatus == null) {
            return "/";
        } else {
            if (handleStatus == 1) {
                return "已处理";
            } else {
                return "未处理";
            }
        }
    }

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
    }

    public String getEndHandleTimesDesc() {
        return DateUtils.formatDateTime(getEndHandleTimes());
    }

    public Date getEndHandleTimes() {
        return endHandleTimes;
    }

    public void setEndHandleTimes(Date endHandleTimes) {
        this.endHandleTimes = endHandleTimes;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public String getHandleDescriptionDesc() {
        if (StringUtils.isEmpty(handleDescription)) {
            return "/";
        }
        return handleDescription;
    }

    public void setHandleDescription(String handleDescription) {
        this.handleDescription = handleDescription;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public String getHandleUserNameDesc() {
        if (StringUtils.isEmpty(handleUserName)) {
            return "/";
        }
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Integer getEventExistStatus() {
        return eventExistStatus;
    }

    public void setEventExistStatus(Integer eventExistStatus) {
        this.eventExistStatus = eventExistStatus;
    }

    /**
     * 得到事件异常处理类型
     *
     * @return java.lang.Integer
     * @author huanggc
     * @date 2020/12/10
     */
    public Integer getFaultEventType() {
        // 事件类型: 2报警, 3离线
        // 对应device_manage.d_device_fault_type的fault_event_type
        if (eventType != null) {
            return eventType;
        }
        return null;
    }

    public Integer getByFaultEventType() {
        return byFaultEventType;
    }

    public void setByFaultEventType(Integer byFaultEventType) {
        this.byFaultEventType = byFaultEventType;
    }

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public Integer getHandleDeviceType() {
        return handleDeviceType;
    }

    public void setHandleDeviceType(Integer handleDeviceType) {
        this.handleDeviceType = handleDeviceType;
    }
}