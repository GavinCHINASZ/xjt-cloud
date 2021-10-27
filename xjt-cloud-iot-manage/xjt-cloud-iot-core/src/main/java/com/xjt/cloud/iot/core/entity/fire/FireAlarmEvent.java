package com.xjt.cloud.iot.core.entity.fire;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 火警主机事件实体
 *
 * @author dwt
 * @date 2019-10-11 15:05
 */
public class FireAlarmEvent extends BaseEntity {
    //巡检点id
    private Long checkPointId;
    // 巡检点qrNo
    private String checkPointQrNo;
    private String checkPointName;

    //设备id
    private Long deviceId;
    private String deviceSysName;
    //火警主机设备id
    private Long fireAlarmDeviceId;

    //'事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，
    // 7-故障事件，8-系统事件，（9-例行检查），10-其他 ，11-屏蔽，12-启动，13-延时状态，
    // 14-主电故障，15-备电故障，16-总线故障，17-系统复位，18-隔离事件
    // 20-传输装置复位，21-离线事件',22-在线恢复
    private Integer eventType;
    // 事件类型筛选数组
    private int[] eventTypeArr;

    //报警位置
    private String alarmPosition;
    //报警设备名称
    private String alarmDeviceName;
    //恢复时间
    private Date recoverTime;

    //事件状态 1-已恢复/历史记录，2-未恢复/活跃事件
    private Integer recoverStatus;
    private int[] recoverStatusArr;

    //来源id（注册码）传感器ID/传输装置id
    private String transDeviceId;
    //传感器/传输装置名称
    private String transDeviceName;
    //火警主机编号
    private String fireAlarmNo;
    //事件记录id
    private Long recordId;
    //事件记录恢复id
    private Long recoverRecordId;

    //1:现场施工或环境震动,导致灰尘或异物进入 2:外部环境扭曲,3:周边环境电磁干扰,4:设备本体故障,5:其他,6:测试,7:火警
    private Integer eventCause;
    //描述
    private String description;

    // 处理描述
    private String handleDescription;

    // 处理人
    private String handleUserName;

    //滞后原因及描述/备注
    private String remarks;
    //监视类型 1:正常监视，2异常监视
    private Integer monitorType;
    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirm;
    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirms;
    //处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;
    //处理时间
    private Date handleTime;

    //处理状态，1已处理  2未处理
    private Integer handleStatus;
    private Integer[] handleStatuss;
    private Date endHandleTime;
    //区域类型 默认0没有区域，1外围，2洁净区
    private Integer areaType;
    //责任单位
    private String unit;
    private List<String> pictureUrlArr;
    private String pictureUrl;

    //status  '信息状态　1正常　99删除',
    //1:hour,2:day,3:month,4:year
    private Integer dateType;
    private Date startTime;
    private Date beginTime;
    private Date endTime;
    private Integer dateNum;

    private String[] keys;
    //导出类型：1：按时间段;2：按id数组
    private Integer downLoadType;
    private Long[] ids;
    private Long[] projectIds;

    private Date createTime;
    private String timeType;
    private String timeStr;
    private boolean flag;

    /**
     * 分组类型　1 小时　　2 天  3按月  4按年
     */
    private int groupType = 1;
    //1:消检通,2:地铁
    private Integer type;
    private String appId;
    private Integer[] monitorTypeArr;

    private Integer rowNum;
    //建筑物名称
    private String buildingName;
    //楼层名称
    private String floorName;
    private Long buildingId;
    private Long buildingFloorId;
    private String qrNo;
    private String[] alarmPositionArr;

    /**
     * 防护等级ID
     */
    private Long protectGradeId;

    /**
     * 标记
     */
    private Integer sign;

    // 班前防护 作业ID
    private Long protectId;
    // 班前防护 作业状态 0未提交, 1已提交
    private Integer protectStatus;
    private Integer[] protectStatusType;

    /**
     * 1未开始
     * 2进行中
     * 4防护失效
     */
    private Integer taskState;
    private Integer[] taskStateType;

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

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
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

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getDateNum() {
        return dateNum;
    }

    public void setDateNum(Integer dateNum) {
        this.dateNum = dateNum;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecoverRecordId() {
        return recoverRecordId;
    }

    public void setRecoverRecordId(Long recoverRecordId) {
        this.recoverRecordId = recoverRecordId;
    }

    public String getEventTypeDesc() {
        //'事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，
        // 7-故障事件，8-系统事件，（9-例行检查），10-其他 ，11-屏蔽，12-启动，13-延时状态，
        // 14-主电故障，15-备电故障，16-总线故障，17-系统复位，20-传输装置复位，21-离线事件',
        String eventTypeDesc = "每两年";
        if (eventType != null) {
            switch (eventType) {
                case 1:
                    eventTypeDesc = "火警事件";
                    break;
                case 2:
                    eventTypeDesc = "监管事件";
                    break;
                case 7:
                    eventTypeDesc = "故障事件";
                    break;
                case 21:
                    eventTypeDesc = "离线事件";
                    break;
                default:
                    eventTypeDesc = "未知事件";
                    break;
            }
        }

        return eventTypeDesc;
    }

    public String getRecoverStatusDesc() {
        String recoverStatusDesc = "未恢复";
        if (recoverStatus != null) {
            switch (recoverStatus) {
                case 1:
                    recoverStatusDesc = "已恢复";
                    break;
                case 2:
                    recoverStatusDesc = "未恢复";
                    break;
                default:
                    recoverStatusDesc = "/";
                    break;
            }
        }

        return recoverStatusDesc;
    }

    public FireAlarmEvent() {

    }

    public FireAlarmEvent(FireAlarmRecord fireAlarmRecord) {
        this.setProjectId(fireAlarmRecord.getProjectId());
        this.setEventType(fireAlarmRecord.getEventType());
        this.setDeviceId(fireAlarmRecord.getDeviceId());
        this.setAlarmDeviceName(fireAlarmRecord.getDeviceName());
        this.setFireAlarmNo(fireAlarmRecord.getFireAlarmNo());
        this.setAlarmPosition(fireAlarmRecord.getRecordPosition());
        this.setRecordId(fireAlarmRecord.getId());
        this.setTransDeviceId(fireAlarmRecord.getTransDeviceId());
        this.setFireAlarmDeviceId(fireAlarmRecord.getFireAlarmDeviceId());
    }

    public FireAlarmEvent(FireAlarmDevice fireAlarmDevice) {
        this.setProjectId(fireAlarmDevice.getProjectId());
        this.setEventType(21);
        this.setDeviceId(fireAlarmDevice.getDeviceId());
        this.setFireAlarmNo(fireAlarmDevice.getFireAlarmNo());
        this.setTransDeviceId(fireAlarmDevice.getTransDeviceId());
        this.setTransDeviceName(fireAlarmDevice.getTransDeviceName());
        this.setFireAlarmDeviceId(fireAlarmDevice.getId());
    }

    public int[] getEventTypeArr() {
        return eventTypeArr;
    }

    public void setEventTypeArr(int[] eventTypeArr) {
        this.eventTypeArr = eventTypeArr;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getDownLoadType() {
        return downLoadType;
    }

    public void setDownLoadType(Integer downLoadType) {
        this.downLoadType = downLoadType;
    }

    public String getTransDeviceId() {
        return transDeviceId;
    }

    public void setTransDeviceId(String transDeviceId) {
        this.transDeviceId = transDeviceId;
    }

    public String getFireAlarmNo() {
        return fireAlarmNo;
    }

    public void setFireAlarmNo(String fireAlarmNo) {
        this.fireAlarmNo = fireAlarmNo;
    }

    public String getTransDeviceName() {
        return transDeviceName;
    }

    public void setTransDeviceName(String transDeviceName) {
        this.transDeviceName = transDeviceName;
    }

    public String getAlarmPosition() {
        if (StringUtils.isEmpty(alarmPosition)) {
            return "/";
        }
        return alarmPosition;
    }

    public String getAlarmPositionSql() {
        return alarmPosition;
    }

    public String getAlarmPositionDesc() {
        return getAlarmPosition() + " " + getAlarmDeviceName();
    }

    public void setAlarmPosition(String alarmPosition) {
        this.alarmPosition = alarmPosition;
    }

    public String getAlarmDeviceName() {
        if (StringUtils.isEmpty(alarmDeviceName)) {
            return "";
        }
        return alarmDeviceName;
    }

    public void setAlarmDeviceName(String alarmDeviceName) {
        this.alarmDeviceName = alarmDeviceName;
    }

    public Long getFireAlarmDeviceId() {
        return fireAlarmDeviceId;
    }

    public void setFireAlarmDeviceId(Long fireAlarmDeviceId) {
        this.fireAlarmDeviceId = fireAlarmDeviceId;
    }

    public int[] getRecoverStatusArr() {
        return recoverStatusArr;
    }

    public void setRecoverStatusArr(int[] recoverStatusArr) {
        this.recoverStatusArr = recoverStatusArr;
    }

    public String getCreateTimeDesc() {
        String createTimeDesc = "";
        if (createTime != null) {
            createTimeDesc = DateUtils.formatDateTime(createTime);
        } else {
            createTimeDesc = "/";
        }
        return createTimeDesc;
    }

    public String getRecoverTimeDesc() {
        if (recoverTime != null) {
            return DateUtils.formatDateTime(recoverTime);
        }
        return "/";
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getEventCause() {
        return eventCause;
    }

    public void setEventCause(Integer eventCause) {
        this.eventCause = eventCause;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<String> getPictureUrlArr() {
        return pictureUrlArr;
    }

    public void setPictureUrlArr(List<String> pictureUrlArr) {
        this.pictureUrlArr = pictureUrlArr;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
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

    public String getEndHandleTimeDesc() {
        return DateUtils.formatDateTime(getEndHandleTime());
    }

    public Date getEndHandleTime() {
        return endHandleTime;
    }

    public void setEndHandleTime(Date endHandleTime) {
        this.endHandleTime = endHandleTime;
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

    public Integer[] getMonitorTypeArr() {
        return monitorTypeArr;
    }

    public void setMonitorTypeArr(Integer[] monitorTypeArr) {
        this.monitorTypeArr = monitorTypeArr;
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

    // 1:正常监视，2异常监视
    public String getMonitorTypeDesc() {
        String monitorTypeDesc = "/";
        if (monitorType != null && monitorType == 1) {
            monitorTypeDesc = "正常监视";
        } else if (monitorType != null && monitorType == 2) {
            monitorTypeDesc = "异常监视";
        }
        return monitorTypeDesc;
    }

    // 1:现场施工或环境震动,导致灰尘或异物进入 2:外部环境扭曲, 3:周边环境电磁干扰, 4:设备本体故障, 5:其他, 6:测试, 7:火警
    public String getEventCauseDesc() {
        String eventCauseDesc = "/";
        if (eventCause != null) {
            switch (eventCause) {
                case 1:
                    eventCauseDesc = "现场施工或环境震动,导致灰尘或异物进入";
                    break;
                case 2:
                    eventCauseDesc = "外部环境扭曲";
                    break;
                case 3:
                    eventCauseDesc = "周边环境电磁干扰";
                    break;
                case 4:
                    eventCauseDesc = "设备本体故障";
                    break;
                case 5:
                    eventCauseDesc = "其他";
                    break;
                case 6:
                    eventCauseDesc = "测试";
                    break;
                case 7:
                    eventCauseDesc = "火警";
                    break;
            }
        }
        return eventCauseDesc;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
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

    public String[] getAlarmPositionArr() {
        return alarmPositionArr;
    }

    public void setAlarmPositionArr(String[] alarmPositionArr) {
        this.alarmPositionArr = alarmPositionArr;
    }

    public Long getProtectGradeId() {
        return protectGradeId;
    }

    public void setProtectGradeId(Long protectGradeId) {
        this.protectGradeId = protectGradeId;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Long getProtectId() {
        return protectId;
    }

    public void setProtectId(Long protectId) {
        this.protectId = protectId;
    }

    public Integer getProtectStatus() {
        return protectStatus;
    }

    public void setProtectStatus(Integer protectStatus) {
        this.protectStatus = protectStatus;
    }

    public Integer[] getProtectStatusType() {
        return protectStatusType;
    }

    public void setProtectStatusType(Integer[] protectStatusType) {
        this.protectStatusType = protectStatusType;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Integer[] getTaskStateType() {
        return taskStateType;
    }

    public void setTaskStateType(Integer[] taskStateType) {
        this.taskStateType = taskStateType;
    }

    /**
     * 得到事件异常处理类型
     *
     * @return java.lang.Integer
     * @author huanggc
     * @date 2020/12/10
     */
    public Integer getFaultEventType() {
        // 事件类型: 1火警, 7故障, 2监视, 21离线
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
