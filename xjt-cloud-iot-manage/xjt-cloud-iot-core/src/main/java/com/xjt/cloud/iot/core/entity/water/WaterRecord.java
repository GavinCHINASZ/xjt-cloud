package com.xjt.cloud.iot.core.entity.water;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 17:18
 * @Description: 水压事件与记录信息对象
 */
public class WaterRecord extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    private String deviceSysName;//设备名称
    private String qrNo;//设备二维码
    private Long deviceId;//设备id
    private Long recordId;//记录id
    private Long checkPointId;//巡检点id
    private String sensorNo;//传感器id
    private Long waterId;//传感器id
    private Integer minValue;//设置的最小值 * 100
    private Integer maxValue;//设置的最大值 * 100
    private Integer presentValue;//当前值 * 100
    private Integer deviceStatus;//设备状态　1正常　2超高/信号弱/电量低/离线/漏水/撞击　3超低/ / / /取水/开盖　 4水浸漏水/ / / /漏水传感器故障/撞击传感器故障
    private Integer monitorStatus;//设备状态　1正常　2超高　3超低　
    private Integer signalIntensity;//信息强度　* 100
    private Integer signalStatus;//信息强度状态　1正常　2信号弱
    private Integer electricQuantity;//电量　*　100
    private Integer electricQuantityStatus;//电量状态　1正常　2电量低
    /**
     * 事件类型　1监测状态, 2信号强度, 3电量, 4设备状态, 5漏水监测, 6撞击监测
     */
    private Integer type;
    //事件类型　1监测状态　　2信号强度　　3电量 4设备状态 5漏水监测  6撞击监测
    private Integer eventType;
    private Integer recoverStatus;//恢复状态　0未恢复　1已恢复
    private Integer[] recoverStatuss;//恢复状态　0未恢复　1已恢复
    private Date recoverTime;//恢复时间
    private Long recoverRecordId;//恢复记录id
    private String rawData;//原始数据
    private Date beginTime;
    private Date endTime;
    private int groupType = 1;//分组类型　1　按时间　　2按日期 3按月  4按年
    private String valueStr; //实测值
    private String unit;//单位
    private Integer deviceType;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
    private Integer[] deviceTypes;
    private String title;//标题
    private String pointName;//巡检点名称
    private String pointQrNo;//巡检点二维码
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private String pointLocation;// 巡检位置
    //事件类型数组 1电量低　2超高　3超低　4离线 5 漏水 6撞击 7取水 8漏水传感器故障 9开盖 10撞击传感器故障
    private Integer[] eventTypes;
    private Long waterTerminalId;//两端设备时，供水端的主键id
    private String timeDesc;
    private Integer[] monitorStatuss;//查询条件　鉴测状态1正常　2超高　3超低　
    private Integer[] deviceStatuss;//查询条件　设备状态
    private Integer[] signalIntensitys;//查询条件　信号状态
    private Integer[] electricQuantitys;//查询条件　电量状态
    private Integer leakMonitorStatus;//漏水监测 1正常 2漏水 3取水 4漏水传感器故障
    private Integer strikeMonitorStatus;//撞击监测 1正常 2撞击  4撞击传感器故障
    private Integer[] leakMonitorStatuss;//漏水监测 1正常 2漏水 3取水 4漏水传感器故障
    private Integer[] faultStatuss;//1正常2故障
    private Long[] buildingFloorIds;//楼层id
    private Integer terminal;//终端类型　1　供水端　　2系统端
    private Long[] projectIds;//项目id数组
    private Integer dateType;//时间类型　1周　　2月　3年
    private String dateIndex;//时间值　0　本周本月本年　其它按正常值
    private Long[] eventIds;//事件id数组
    private Boolean findLimitEndData;//查询最后一条数据
    private Integer openCoverStatus;//开盖状态：1正常关闭，2开盖报警
    private Long[] ids;//项目id数组
    private Integer[] faultEventStatuss;//消火栓
    private Integer faultStatus;//1:正常，2:故障
    private Date eventHandleTime;//事件处理时间

    // 处理人
    private String handleUserName;
    // 处理描述
    private String description;

    private Integer handleStatus;//处理状态，1已处理  2未处理
    private Integer eventExistStatus;//处理状态，1存在事件  2没有事件

    private Integer[] alarmEvents;////报警事件 1正常  2超高 3超低 4漏水 5取水
    private Integer[] impactDamages;//撞击损坏 1正常 2撞击 3开盖
    private Integer[] faultEvents;//故障事件 1正常 2撞击传感器故障  3漏水传感器故障
    private Integer[] handleStatuss;//处理状态，1已处理  2未处理

    private Integer byFaultEventType;//查询异常类型
    private Long faultTypeId;//异常类型id
    private String faultType;//事件处理的异常类型
    private Long deviceFaultTypeId;//物联设备异常处理类型id
    private Integer handleDeviceType;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备',

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

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getByFaultEventTypeSql() {
        if ((deviceTypes == null && deviceType == null) || byFaultEventType == null) {
            return null;
        }
        String res = "";
        if (deviceTypes != null) {
            //水压
            if (byFaultEventType == 1) {//超高
                res = "(w.type = 1 AND w.device_status = 2)";
            } else if (byFaultEventType == 2) {//超低
                res = "(w.type = 1 AND w.device_status = 3)";
            } else if (byFaultEventType == 3) {//电量低
                res = "(w.type = 3 AND w.device_status = 2)";
            } else if (byFaultEventType == 4) {//离线
                res = "(w.type = 4 AND w.device_status = 2)";
            }
            return res;
        }

        if (deviceType == 8) {
            //消火栓
            if (byFaultEventType == 1) {
                //报警事件（说明：包含超高、超低、漏水、取水）
                res = "((w.type = 1 OR  w.type = 5) AND ( w.device_status = 2 OR  w.device_status = 3))";
            } else if (byFaultEventType == 2) {//撞击/开盖
                res = "(w.device_status = 2 AND ( w.type = 6 OR  w.type = 7))";
            } else if (byFaultEventType == 3) {
                //故障/撞击传感器故障/漏水传感器故障
                res = "(w.device_status = 4 AND ( w.type = 6 OR  w.type = 7))";
            } else if (byFaultEventType == 4) {//低电量
                res = "(w.type = 3 AND w.device_status = 2)";
            } else if (byFaultEventType == 5) {//离线事件
                res = "(w.type = 4 AND w.device_status = 2)";
            }
        } else if (deviceType == 3) {
            //水浸，7为信息判断时的值，
            if (byFaultEventType == 1) {//漏水
                res = "(w.type = 5 AND w.device_status = 2)";
            } else if (byFaultEventType == 2) {//电量低
                res = "(w.type = 3 AND w.device_status = 2)";
            } else if (byFaultEventType == 3) {//离线
                res = "(w.type = 4 AND w.device_status = 2)";
            }
        }
        return res;
    }

    public Long getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(Long faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public Integer getByFaultEventType() {
        return byFaultEventType;
    }

    public void setByFaultEventType(Integer byFaultEventType) {
        this.byFaultEventType = byFaultEventType;
    }

    public Integer[] getHandleStatuss() {
        return handleStatuss;
    }

    public void setHandleStatuss(Integer[] handleStatuss) {
        this.handleStatuss = handleStatuss;
    }

    public String getAlarmEventDesc() {
        if (monitorStatus != null && leakMonitorStatus != null) {
            String monitorStatusStr = null;
            String leakMonitorStatusStr = null;
            if (monitorStatus == 1 && leakMonitorStatus == 1) {
                return "正常";
            }

            if (monitorStatus == 2) {
                monitorStatusStr = "超高";
            } else if (monitorStatus == 3) {
                monitorStatusStr = "超低";
            }

            if (leakMonitorStatus == 2) {
                leakMonitorStatusStr = "漏水";
            } else if (leakMonitorStatus == 3) {
                leakMonitorStatusStr = "取水";
            }
            return (StringUtils.isNotEmpty(monitorStatusStr) && StringUtils.isNotEmpty(leakMonitorStatusStr)) ?
                    (monitorStatusStr + "/" + leakMonitorStatusStr) : (StringUtils.isNotEmpty(monitorStatusStr) ? monitorStatusStr : leakMonitorStatusStr);
        }
        return "/";
    }

    public String getImpactDamageDesc() {
        if ((strikeMonitorStatus != null && strikeMonitorStatus == 1) && (openCoverStatus != null && openCoverStatus == 1)) {
            return "正常";
        } else if (strikeMonitorStatus != null && strikeMonitorStatus == 2) {
            return "撞击";
        } else if (openCoverStatus != null && openCoverStatus == 2) {
            return "开盖";
        }
        return "/";
    }

    public String getFaultEventDesc() {
        if (leakMonitorStatus != null && strikeMonitorStatus != null) {
            String leakMonitorStatusStr = null;
            String strikeMonitorStatusStr = null;
            if (leakMonitorStatus == 1 && strikeMonitorStatus == 1) {
                return "正常";
            }

            if (strikeMonitorStatus == 4) {
                strikeMonitorStatusStr = "撞击传感器故障";
            }

            if (leakMonitorStatus == 4) {
                leakMonitorStatusStr = "漏水传感器故障";
            }
            return (StringUtils.isNotEmpty(leakMonitorStatusStr) && StringUtils.isNotEmpty(strikeMonitorStatusStr)) ?
                    (leakMonitorStatusStr + "/" + strikeMonitorStatusStr) : (StringUtils.isNotEmpty(leakMonitorStatusStr) ? leakMonitorStatusStr : strikeMonitorStatusStr);
        }
        return "/";
    }

    public String getAlarmEventsDesc() {
        if (alarmEvents != null && alarmEvents.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : alarmEvents) {
                if (event == 1) {
                    sql.append(" OR w.monitor_status = 1 AND w.leak_monitor_status = 1");
                } else if (event == 2) {//超高报警
                    sql.append(" OR w.monitor_status = 2");
                } else if (event == 3) {//超低报警
                    sql.append(" OR w.monitor_status = 3");
                } else if (event == 4) {//漏水报警
                    sql.append(" OR w.leak_monitor_status = 2");
                } else if (event == 5) {//取水报警
                    sql.append(" OR w.leak_monitor_status = 3");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getImpactDamagesDesc() {
        if (impactDamages != null && impactDamages.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : impactDamages) {
                if (event == 1) {
                    sql.append(" OR w.strike_monitor_status = 1");
                } else if (event == 2) {//2撞击
                    sql.append(" OR w.strike_monitor_status = 2");
                } else if (event == 3) {//3开盖
                    sql.append(" OR w.strike_monitor_status = 3");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getFaultEventsDesc() {
        if (faultEvents != null && faultEvents.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : faultEvents) {
                if (event == 1) {
                    sql.append(" OR w.leak_monitor_status = 1 AND w.strike_monitor_status = 1");
                } else if (event == 2) {//4撞击传感器故障
                    sql.append(" OR w.strike_monitor_status = 4");
                } else if (event == 3) {//4漏水传感器故障
                    sql.append(" OR w.leak_monitor_status = 4");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getEventByAlarmEventsDesc() {
        if (alarmEvents != null && alarmEvents.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : alarmEvents) {
                if (event == 2) {//超高报警
                    sql.append(" OR (w.type = 1 AND w.device_status = 2) ");
                } else if (event == 3) {//超低报警
                    sql.append(" OR (w.type = 1 AND w.device_status = 3) ");
                } else if (event == 4) {//漏水报警
                    sql.append(" OR (w.type = 5 AND w.device_status = 2) ");
                } else if (event == 5) {//取水报警
                    sql.append(" OR (w.type = 5 AND w.device_status = 3) ");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getEventByImpactDamagesDesc() {
        if (impactDamages != null && impactDamages.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : impactDamages) {
                if (event == 2) {//2撞击
                    sql.append(" OR (w.type = 6 AND w.device_status = 2) ");
                } else if (event == 3) {//3开盖
                    sql.append(" OR (w.type = 7 AND w.device_status = 2) ");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getEventByFaultEventsDesc() {
        if (faultEvents != null && faultEvents.length > 0) {
            StringBuilder sql = new StringBuilder();
            for (int event : faultEvents) {
                if (event == 2) {//4撞击传感器故障
                    sql.append(" OR (w.type = 6 AND w.device_status = 4) ");
                } else if (event == 3) {//4漏水传感器故障
                    sql.append(" OR (w.type = 5 AND w.device_status = 4) ");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public Integer[] getAlarmEvents() {
        return alarmEvents;
    }

    public void setAlarmEvents(Integer[] alarmEvents) {
        this.alarmEvents = alarmEvents;
    }

    public Integer[] getImpactDamages() {
        return impactDamages;
    }

    public void setImpactDamages(Integer[] impactDamages) {
        this.impactDamages = impactDamages;
    }

    public Integer[] getFaultEvents() {
        return faultEvents;
    }

    public void setFaultEvents(Integer[] faultEvents) {
        this.faultEvents = faultEvents;
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
        if (getEventHandleTime() == null) {
            return "待处理";
        } else {
            return "已处理";
        }
    }

    public String getEventHandleTimeDesc() {
        return DateUtils.formatDateTime(getEventHandleTime());
    }

    public Date getEventHandleTime() {
        return eventHandleTime;
    }

    public void setEventHandleTime(Date eventHandleTime) {
        this.eventHandleTime = eventHandleTime;
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

    public String getDescription() {
        return description;
    }

    public String getDescriptionDesc() {
        if (StringUtils.isEmpty(description)){
            return "/";
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Integer[] getFaultEventStatuss() {
        return faultEventStatuss;
    }

    public void setFaultEventStatuss(Integer[] faultEventStatuss) {
        this.faultEventStatuss = faultEventStatuss;
    }

    public Integer[] getFaultStatuss() {
        return faultStatuss;
    }

    public void setFaultStatuss(Integer[] faultStatuss) {
        this.faultStatuss = faultStatuss;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Boolean getFindLimitEndData() {
        return findLimitEndData;
    }

    public void setFindLimitEndData(Boolean findLimitEndData) {
        this.findLimitEndData = findLimitEndData;
    }

    public Long[] getEventIds() {
        return eventIds;
    }

    public void setEventIds(Long[] eventIds) {
        this.eventIds = eventIds;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(String dateIndex) {
        this.dateIndex = dateIndex;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getWaterId() {
        return waterId;
    }

    public void setWaterId(Long waterId) {
        this.waterId = waterId;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(Integer presentValue) {
        this.presentValue = presentValue;
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

    public String getSensorNoDesc() {
        if (deviceType != null && deviceType == 14) {
            if (waterTerminalId == null || waterTerminalId == 0) {
                return "供水端" + sensorNo;
            } else {
                return "系统端" + sensorNo;
            }
        }
        return sensorNo;
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

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Integer signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public Integer getSignalStatus() {
        return signalStatus;
    }

    public void setSignalStatus(Integer signalStatus) {
        this.signalStatus = signalStatus;
    }

    public Integer getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(Integer electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public Integer getElectricQuantityStatus() {
        return electricQuantityStatus;
    }

    public String getElectricQuantityDesc() {
        if (getElectricQuantity() != null) {
            return (getElectricQuantity() / 100.00f) + "%";
        }
        return 0.00f + "%";
    }

    public void setElectricQuantityStatus(Integer electricQuantityStatus) {
        this.electricQuantityStatus = electricQuantityStatus;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
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

    public Long getWaterTerminalId() {
        return waterTerminalId;
    }

    public void setWaterTerminalId(Long waterTerminalId) {
        this.waterTerminalId = waterTerminalId;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public Integer[] getMonitorStatuss() {
        return monitorStatuss;
    }

    public void setMonitorStatuss(Integer[] monitorStatuss) {
        this.monitorStatuss = monitorStatuss;
    }

    public Integer[] getDeviceStatuss() {
        return deviceStatuss;
    }

    public void setDeviceStatuss(Integer[] deviceStatuss) {
        this.deviceStatuss = deviceStatuss;
    }

    public Integer[] getSignalIntensitys() {
        return signalIntensitys;
    }

    public void setSignalIntensitys(Integer[] signalIntensitys) {
        this.signalIntensitys = signalIntensitys;
    }

    public Integer[] getElectricQuantitys() {
        return electricQuantitys;
    }

    public void setElectricQuantitys(Integer[] electricQuantitys) {
        this.electricQuantitys = electricQuantitys;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    /**
     * 得到事件异常处理类型
     *
     * @return java.lang.Integer
     * @author wangzhiwen
     * @date 2020/12/8 9:58
     */
    public Integer getFaultEventType() {
        type = type == null ? eventType : type;
        if (deviceType != null && type != null) {
            int faultEventType = 1;
            if (deviceType == 8) {//消火栓
                if (type == 1 || type == 5) {//超低/超高/漏水/取水
                    faultEventType = 1;
                } else if (deviceStatus == 2 && (type == 6 || type == 7)) {//撞击/开盖
                    faultEventType = 2;
                } else if (deviceStatus == 4 && (type == 6 || type == 7)) {//故障/撞击传感器故障/漏水传感器故障
                    faultEventType = 3;
                } else if (type == 3) {//低电量
                    faultEventType = 4;
                } else if (type == 4) {//离线
                    faultEventType = 5;
                }
            } else if (deviceType == 3) {//水浸，7为信息判断时的值，
                if (type == 1) {//水浸告警
                    faultEventType = 1;
                } else if (type == 3) {//电量低
                    faultEventType = 2;
                } else if (type == 4) {//离线
                    faultEventType = 3;
                }
            } else {//水压
                if (type == 1 && deviceStatus == 2) {//超高、超低
                    faultEventType = 1;
                } else if (type == 1 && deviceStatus == 3) {//电量低
                    faultEventType = 2;
                } else if (type == 3) {//电量低
                    faultEventType = 3;
                } else if (type == 4) {//离线
                    faultEventType = 4;
                }
            }

            return faultEventType;
        }
        return null;
    }

    public String getFaultEventTypeDesc() {
        type = type == null ? eventType : type;
        if (deviceType != null && type != null) {
            String faultEventTypeDesc = null;
            if (deviceType == 8) {//消火栓
                if (type == 1 || type == 5) {//超低/超高/漏水/取水
                    faultEventTypeDesc = "报警事件";
                } else if (deviceStatus == 2 && (type == 6 || type == 7)) {//撞击/开盖
                    faultEventTypeDesc = "撞击损坏";
                } else if (deviceStatus == 4 && (type == 6 || type == 7)) {//故障/撞击传感器故障/漏水传感器故障
                    faultEventTypeDesc = "故障事件";
                } else if (type == 3) {//低电量
                    faultEventTypeDesc = "低电量";
                } else if (type == 4) {//离线
                    faultEventTypeDesc = "离线";
                }
            } else if (deviceType == 3) {//水浸，7为信息判断时的值，
                if (type == 1) {//水浸告警
                    faultEventTypeDesc = "漏水";
                } else if (type == 3) {//电量低
                    faultEventTypeDesc = "电量低";
                } else if (type == 4) {//离线
                    faultEventTypeDesc = "离线";
                }
            } else {//水压
                if (type == 1 && deviceStatus == 2) {//超高
                    faultEventTypeDesc = "超高";
                } else if (type == 1 && deviceStatus == 3) {//超低
                    faultEventTypeDesc = "超低";
                } else if (type == 3) {//电量低
                    faultEventTypeDesc = "电量低";
                } else if (type == 4) {//离线
                    faultEventTypeDesc = "离线";
                }
            }

            return faultEventTypeDesc;
        }
        return null;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getLeakMonitorStatus() {
        return leakMonitorStatus;
    }

    public void setLeakMonitorStatus(Integer leakMonitorStatus) {
        this.leakMonitorStatus = leakMonitorStatus;
    }

    public Integer getStrikeMonitorStatus() {
        return strikeMonitorStatus;
    }

    public void setStrikeMonitorStatus(Integer strikeMonitorStatus) {
        this.strikeMonitorStatus = strikeMonitorStatus;
    }

    public Integer[] getLeakMonitorStatuss() {
        return leakMonitorStatuss;
    }

    public void setLeakMonitorStatuss(Integer[] leakMonitorStatuss) {
        this.leakMonitorStatuss = leakMonitorStatuss;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
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
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getCreateTimeDesc() {
        return DateUtils.formatDateTime(getCreateTime());
    }

    public Long[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Long[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public String getPresentValueDesc() {
        if (getPresentValue() != null) {
            float value = getPresentValue() / 100.00f;
            if (eventType != null) {
                if (eventType == 1) {
                    return value + getUnit();
                } else if (eventType == 3) {
                    return value + "%";
                } else {
                    return value + "";
                }
            }
        }
        return "/";
    }

    public String getPointLocationDesc() {
        return getBuildingNameDesc() + getBuildingFloorNameDesc() + getPointLocation();
    }

    public Integer[] getRecoverStatuss() {
        return recoverStatuss;
    }

    public void setRecoverStatuss(Integer[] recoverStatuss) {
        this.recoverStatuss = recoverStatuss;
    }

    public String getEventTypeDesc() {
        type = type == null ? eventType : type;
        if (type != null && deviceStatus != null) {
            if (type == 1) {//设备状态事件
                if (deviceStatus == 2) {
                    return "超高";
                } else if (deviceStatus == 3) {
                    return "超低";
                } else if (deviceStatus == 4) {
                    return "离线";
                }
            } else if (type == 2) {//信号强度事件
                if (deviceStatus == 2) {
                    return "信号弱";
                }
            } else if (type == 3) {//电量事件
                if (deviceStatus == 2) {
                    return "电量低";
                }
            } else if (type == 4) {//设备状态事件
                if (deviceStatus == 2) {
                    return "离线";
                }
            } else if (type == 5) {//漏水监测事件
                if (deviceStatus == 2) {
                    return "漏水";
                } else if (deviceStatus == 3) {
                    return "取水";
                } else if (deviceStatus == 4) {
                    return "漏水传感器故障";
                }
            } else if (type == 6) {//撞击监测
                if (deviceStatus == 2) {
                    return "撞击";
                } else if (deviceStatus == 4) {
                    return "撞击传感器故障";
                }
            } else if (type == 7) {//撞击监测
                if (deviceStatus == 2) {
                    return "开盖";
                }
            }
            return "正常";
        }
        return "/";
    }

    public String getSignalIntensityDesc() {
        if (signalIntensity != null) {
            if (signalIntensity < 1000) {
                return "1";
            } else if (signalIntensity < 1500) {
                return "2";
            } else if (signalIntensity < 2000) {
                return "3";
            } else if (signalIntensity < 2500) {
                return "4";
            }
            return "5";
        }
        return "/";
    }

    public Integer[] getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Integer[] eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getEventTypeSql() {
        //事件类型数组 1电量低　2超高　3超低　4离线 5 漏水 6撞击 7取水 8漏水传感器故障 9开盖 10撞击传感器故障
        StringBuilder s = new StringBuilder();
        if (eventTypes != null && eventTypes.length > 0) {
            for (int t : eventTypes) {
                if (t == 1) {
                    s.append(" OR (w.type = 3 AND w.device_status = 2)");
                } else if (t == 2) {
                    s.append(" OR (w.type = 1 AND w.device_status = 2)");
                } else if (t == 3) {
                    s.append(" OR (w.type = 1 AND w.device_status = 3)");
                } else if (t == 4) {
                    s.append(" OR (w.type = 4 AND w.device_status = 2)");
                } else if (t == 5) {
                    s.append(" OR (w.type = 5 AND w.device_status = 2)");
                } else if (t == 6) {
                    s.append(" OR (w.type = 6 AND w.device_status = 2)");
                } else if (t == 7) {
                    s.append(" OR (w.type = 5 AND w.device_status = 3)");
                } else if (t == 8) {
                    s.append(" OR (w.type = 5 AND w.device_status = 4)");
                } else if (t == 9) {
                    s.append(" OR (w.type = 7 AND w.device_status = 2)");
                } else if (t == 10) {
                    s.append(" OR (w.type = 6 AND w.device_status = 4)");
                }
            }
        }

        if (faultEventStatuss != null && faultEventStatuss.length > 0) {
            for (int t : faultEventStatuss) {
                if (t == 1) {
                    s.append(" OR (w.type = 3 AND w.device_status = 2)");
                } else if (t == 2) {
                    s.append(" OR (w.type = 1 AND w.device_status = 2)");
                } else if (t == 3) {
                    s.append(" OR (w.type = 1 AND w.device_status = 3)");
                } else if (t == 4) {
                    s.append(" OR (w.type = 4 AND w.device_status = 2)");
                } else if (t == 5) {
                    s.append(" OR (w.type = 5 AND w.device_status = 2)");
                    s.append(" OR (w.type = 6 AND w.device_status = 2)");
                    s.append(" OR (w.type = 5 AND w.device_status = 3)");
                    s.append(" OR (w.type = 5 AND w.device_status = 4)");
                    s.append(" OR (w.type = 7 AND w.device_status = 2)");
                    s.append(" OR (w.type = 6 AND w.device_status = 4)");
                }
            }
        }
        if (s != null && s.length() > 0) {
            String sql = " (" + s.substring(4) + ") ";
            return sql;
        }
        return null;
    }

    public String getRecoverTimeDesc() {
        return DateUtils.formatDateTime(getRecoverTime()) + " ";
    }


    public String getRecoverStatusDesc() {
        if (recoverStatus != null && recoverStatus == 1) {
            return "已恢复";
        }
        return "未恢复";
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

    public Integer getOpenCoverStatus() {
        return openCoverStatus;
    }

    public void setOpenCoverStatus(Integer openCoverStatus) {
        this.openCoverStatus = openCoverStatus;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }
}
