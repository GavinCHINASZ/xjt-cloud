package com.xjt.cloud.iot.core.entity.vesa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 极早期事件与记录信息对象
 *
 * @author wangzhiwen
 * @date 2019/9/23 17:18
 */
public class VesaRecord extends BaseEntity {
    //设备类型ID
    private Long deviceTypeId;

    /**
     * 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
     * 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
     */
    private Integer deviceType;
    //设备名称
    private String deviceSysName;
    private String qrNo;//设备二维码
    private Long deviceId;//设备id
    private Long recordId;//记录id
    private String sensorNo;//传感器id
    private String sensorName; //传感器名称

    private Long vesaId;//vesa id
    //=vesa id
    private Long vesaDeviceId;
    //设备状态　1正常　2离线
    private Integer deviceStatus;
    //记录类型，1：新发生的记录，2：恢复的记录
    private Integer recordType;
    //事件类型: 火警1=3, 火警2=4, 行动=2, 警告=1, 故障0, 离线5
    private Integer eventType;
    private String loopName;//回路名称
    private String eventDesc;//事件描述
    private String detector;//探测器
    private String detectorType;//探测器类型
    private String position;//事件地址

    private Integer faultNo;//故障号
    private String faultDesc;//故障原始描述
    private String faultName;//故障名称，是映射后的故障描述
    private String handleMeasure;//事件描述
    private Integer deviceNum;//故障设备台数
    // 事件类型　1监测状态　　2信号强度　　3电量 4设备状态
    private Integer type;
    private Integer recoverStatus;//恢复状态　0未恢复　1已恢复
    private Date recoverTime;//恢复时间
    private Long recoverRecordId;//恢复记录id
    private String rawData;//原始数据

    private Integer[] eventTypeArr;//查询条件　事件类型
    private Integer[] recoverStatusArr;//查询条件　事件状态
    private Integer[] ids;//查询条件　所选事件清单，用于事件导出

    private Date startTime;
    private Date beginTime;
    private Date endTime;
    private int groupType = 1;//分组类型　1　按小时　2按日期  3按月  4按年

    private String timeType;//查询日期范围类型，day：按天，month：按月，year：按年
    private String timeStr;//查询的日期

    private String title;//标题
    //巡检点id
    private Long checkPointId;
    private String pointName;//巡检点名称
    private String pointQrNo;//巡检点二维码
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private String pointLocation;// 巡检位置
    private Integer areaType;//区域类型 默认0,1外围，2洁净区
    private String unit;//责任单位
    private Date createTime;

    /**
     * 是否确认 2:未确认/未处理，1已确认/已处理
     */
    private Integer confirm;
    //处理状态，1已处理  2未处理
    private Integer handleStatus;
    private Integer[] handleStatuss;
    /**
     * 处理时间
     */
    private Date eventHandleTime;
    // 处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;

    // 以下字段用于 PC快照报表导出
    //滞后原因及描述/备注
    private String remarks;
    //描述
    private String description;

    // 处理描述
    private String handleDescription;

    // 处理人
    private String handleUserName;

    //报警位置
    private String alarmPosition;
    //报警设备名称
    private String alarmDeviceName;

    private String pictureUrl;

    private Integer dateNum;
    private Integer rowNum;

    //查询异常类型
    private Integer byFaultEventType;
    //异常类型id
    private Long faultTypeId;
    //事件处理的异常类型
    private String faultType;
    //物联设备异常处理类型id
    private Long deviceFaultTypeId;
    // 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
    // 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备',
    private Integer handleDeviceType;

    public VesaRecord() { }

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Integer getFaultNo() {
        return faultNo;
    }

    public void setFaultNo(Integer faultNo) {
        this.faultNo = faultNo;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getHandleMeasure() {
        return handleMeasure;
    }

    public void setHandleMeasure(String handleMeasure) {
        this.handleMeasure = handleMeasure;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEventDescs() {
        if (StringUtils.isEmpty(eventDesc)){
            return "/";
        }
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getVesaId() {
        return vesaId;
    }

    public void setVesaId(Long vesaId) {
        this.vesaId = vesaId;
    }

    public Long getVesaDeviceId() {
        return vesaDeviceId;
    }

    public void setVesaDeviceId(Long vesaDeviceId) {
        this.vesaDeviceId = vesaDeviceId;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeType() {
        return timeType;
    }

    public Integer[] getEventTypeArr() {
        return eventTypeArr;
    }

    public void setEventTypeArr(Integer[] eventTypeArr) {
        this.eventTypeArr = eventTypeArr;
    }

    public Integer[] getRecoverStatusArr() {
        return recoverStatusArr;
    }

    public void setRecoverStatusArr(Integer[] recoverStatusArr) {
        this.recoverStatusArr = recoverStatusArr;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getDetector() {
        return detector;
    }

    public void setDetector(String detector) {
        this.detector = detector;
    }

    public String getPosition() {
        if (StringUtils.isEmpty(position)) {
            return "";
        }
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDetectorType() {
        return detectorType;
    }

    public void setDetectorType(String detectorType) {
        this.detectorType = detectorType;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRecoverRecordId() {
        return recoverRecordId;
    }

    public void setRecoverRecordId(Long recoverRecordId) {
        this.recoverRecordId = recoverRecordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public String getBuildingNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public String getBuildingFloorNameDesc() {
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingId, "floorName");
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime()) + " ";
    }

    public String getRecoverTimeDesc() {
        if (recoverTime != null) {
            return DateUtils.formatDateTime(recoverTime);
        }
        return "/";
    }

    public String getRecoverStatusDesc() {
        if (recoverStatus != null && recoverStatus == 1) {
            return "已恢复";
        }
        return "未恢复";
    }

    public String getEventTypeDesc() {
        if (eventType != null) {
            if (eventType == 0) {
                return "故障";
            } else if (eventType == 1) {
                return "警告";
            } else if (eventType == 2) {
                return "行动";
            } else if (eventType == 3) {
                return "火警1";
            } else if (eventType == 4) {
                return "火警2";
            } else if (eventType == 5) {
                return "离线";
            }
        }
        return "/";
    }

    @JsonIgnore
    public Integer getDateTimeCount() {
        int limitCount = 10;
        if (1 == groupType) {
            limitCount = 24;
            DateUtils.hoursBetween(startTime, endTime);
        } else if (2 == groupType) {
            // +1;
            limitCount = DateUtils.daysBetween(startTime, endTime);
        } else if (3 == groupType) {
            limitCount = DateUtils.monthsBetween(startTime, endTime);
        } else if (4 == groupType) {
            limitCount = DateUtils.yearsBetween(startTime, endTime);
        }
        return (int) Math.ceil((double) limitCount);
    }

    @JsonIgnore
    public String getGroupTimeType() {
        if (1 == groupType) {
            return "HOUR";
        } else if (2 == groupType) {
            return "DAY";
        } else if (3 == groupType) {
            return "MONTH";
        } else if (4 == groupType) {
            return "YEAR";
        }
        return null;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    /**
     * 火警主机1:正常监视，2异常监视,  Vesa没有;
     */
    public String getMonitorTypeDesc() {
        return "/";
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public String getUnit() {
        if (StringUtils.isEmpty(unit)) {
            return "/";
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemarks() {
        if (StringUtils.isEmpty(remarks)) {
            return "/";
        }
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDescription() {
        if (StringUtils.isEmpty(description)) {
            return "/";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public String getHandleDescriptionDesc() {
        if (StringUtils.isEmpty(handleDescription)){
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

    public String getAlarmPosition() {
        return alarmPosition;
    }

    public String getAlarmPositionDesc() {
        if (eventType != null && eventType == 5) {
            // 离线事件的具体位置建议显示为 "/"
            return "/";
        }
        return getPosition() + " 回路:" + getLoopName() + " 探测器:" + getDetector() + " 类型:" + getDetectorType();
    }

    public void setAlarmPosition(String alarmPosition) {
        this.alarmPosition = alarmPosition;
    }

    public String getAlarmDeviceName() {
        return alarmDeviceName;
    }

    public void setAlarmDeviceName(String alarmDeviceName) {
        this.alarmDeviceName = alarmDeviceName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 7);
            return c.getTime();
        }
        return null;
    }

    public String getCompleteTimeDesc() {
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(createTime);
            c.add(Calendar.DAY_OF_MONTH, 7);
            return DateUtils.formatDateTime(c.getTime());
        }
        return "/";
    }

    public Date getDelayTime() {
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            Long curr = c.getTimeInMillis();
            c.setTime(createTime);
            Long delay = c.getTimeInMillis();
            Integer weeks = (int) ((curr - delay) / (7 * 24000 * 60 * 60) + 1L);
            c.add(Calendar.DAY_OF_MONTH, weeks * 7);
            return c.getTime();
        }
        return null;
    }

    public String getDelayTimeDesc() {
        if (createTime != null) {
            Calendar c = Calendar.getInstance();
            Long curr = c.getTimeInMillis();
            c.setTime(createTime);
            Long delay = c.getTimeInMillis();
            Integer weeks = (int) ((curr - delay) / (7 * 24000 * 60 * 60) + 1L);
            c.add(Calendar.DAY_OF_MONTH, weeks * 7);

            if (c.getTime().getTime() > System.currentTimeMillis()) {
                return "/";
            } else {
                return c.getTime().toString();
            }
        }
        return "/";
    }

    public Integer getDateNum() {
        return dateNum;
    }

    public void setDateNum(Integer dateNum) {
        this.dateNum = dateNum;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
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

    public Date getEventHandleTime() {
        return eventHandleTime;
    }

    public void setEventHandleTime(Date eventHandleTime) {
        this.eventHandleTime = eventHandleTime;
    }

    public String getEventHandleTimeDesc() {
        return DateUtils.formatDateTime(getEventHandleTime());
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
        // 事件类型: 火警1=3, 火警2=4, 行动=2, 警告=1, 故障0, 离线5
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
