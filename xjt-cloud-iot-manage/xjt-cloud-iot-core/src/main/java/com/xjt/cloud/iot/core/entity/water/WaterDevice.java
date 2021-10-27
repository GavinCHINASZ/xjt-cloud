package com.xjt.cloud.iot.core.entity.water;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 17:03
 * @Description: 水压设备信息对象
 */
public class WaterDevice extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    // 设备类型　默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控
    private Integer deviceType;
    private Long checkPointId;//巡检点id
    private Long deviceId;//设备id
    private Long id2;
    private String sensorNo;//传感器id
    private boolean sensorNoNull;//传感器id为空;
    private String findSensorNo;//传感器id
    private String sensorNo2;//传感器id
    private Integer minValue;//最小值 * 100
    private Integer minValue2;//最小值 * 100
    private Integer maxValue;//最大值 * 100
    private Integer maxValue2;//最大值 * 100
    private Integer presentValue;//当前值 * 100
    private Integer presentValue2;//当前值 * 100
    private Integer deviceStatus;//设备状态　1正常　2离线
    private Integer deviceStatus2;//设备状态　1正常　2离线
    private Integer monitorStatus;//设备状态　1正常　2超高　3超低　
    private Integer monitorStatus2;//设备状态　1正常　2超高　3超低　
    private Integer signalIntensity;//信息强度　* 100
    private Integer signalIntensity2;//信息强度　* 100
    private Integer signalStatus;//信息强度状态　1正常　2信号弱
    private Integer electricQuantity;//电量　*　100
    private Integer electricQuantity2;//电量　*　100
    private Integer electricQuantityStatus;//电量状态　1正常　2电量低
    private Date statusUpdateTime;//最新状态修改时间
    private Date endHeartbeatTime;//最新状态修改时间
    private Integer setUpdateStatus;//1、等待下发指令到设备   2、已成功下发指令到设备    3、未成功下发指令到设备 4、已成功更改指令到设备
    /**
     * 默认 1 默认设备  2水压监测  4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾
     * 11视频监控 12防火门监控
     * (水压监测: 3智能水浸 13水厢以前单位为Ｍ 14两端压力设备)
     */
    private Integer type;
    private Integer[] deviceTypes;
    private String deviceSysName;//设备名称
    private String qrNo;//设备二维码
    private String pointLocation;//巡检点位置（设备位置）
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private String simCard;//卡号
    private Integer alarmMode;//告警方式设置  1；阈值告警，2；波动值告警，3；阈值和波动值告警，对水浸表无效
    private Integer dataInterval;//数据采样间隔01~13 对应  5s,10s,30s,1,5,10,15,30,60,120,360,720,1440分钟
    private Integer heartbeat;//心跳发送间隔       09~13 对应 60,120,360,720,1440分钟
    private String url;//域名路径
    private Integer waveAlarmValue;//波动告警值 * 100
    private Integer port;//端口
    private Integer sendType;//发送信息给设备类型　1不发送　2返回信息　3主动发送
    private String unit;//单位
    private Long[] ids;
    private String pointName;//巡检点名称
    private String pointQrNo;//巡检点二维码
    private Long waterTerminalId;//两个端　供水端设备的主键id
    private Integer[] monitorStatuss;//查询条件　鉴测状态
    private Integer[] deviceStatuss;//查询条件　设备状态
    private Integer[] signalIntensitys;//查询条件　信号状态
    private Integer[] electricQuantitys;//查询条件　电量状态
    private Integer leakMonitorStatus;//漏水监测 1正常 2漏水 3取水 4漏水传感器故障
    private Integer strikeMonitorStatus;//撞击监测 1正常 2撞击 3开盖 4撞击传感器故障
    private Integer[] leakMonitorStatuss;//漏水监测 1正常 2漏水 3取水 4漏水传感器故障
    private Integer[] buildingFloorIds;//楼层id数组
    private boolean findSysTerminal;//是否查询系统端 ,只有在查询水压设备列表时查询系统端
    private boolean findWaterLBySensorNo;//是否查询系统端 ,只有在查询水压设备列表时查询系统端, 是否是以传感器id做为查询条件
    private Long editRecordId;//供水端修改记录id
    private Long[] projectIds;//项目id数组
    private Integer sendNum;//当日发送短信次数
    private Date sendTime;//最后发送时间
    private Date endTime;//最后时间
    //标题
    private String title;
    private Integer openCoverStatus;//开盖监测 1正常 2开盖
    private int lat;//纬度
    private int lng;//经度
    private int baiduLat;//纬度
    private int baiduLng;//经度
    private Integer faultStatus;// 1正常，2故障
    private Integer[] faultStatuss;
    private Integer valueUpdateStatus;

    private Integer[] alarmEvents;//报警事件 1正常  2超高 3超低 4漏水 5取水
    private Integer[] impactDamages;//撞击损坏 1正常 2撞击 3开盖
    private Integer[] faultEvents;//故障事件 1正常 2撞击传感器故障  3漏水传感器故障

    public String getAlarmEventDesc(){
        if (monitorStatus != null && leakMonitorStatus != null) {
            String monitorStatusStr = null;
            String leakMonitorStatusStr = null;
            if (monitorStatus == 1 && leakMonitorStatus == 1) {
                return  "正常";
            }

            if(monitorStatus == 2){
                monitorStatusStr = "超高";
            }else if(monitorStatus == 3){
                monitorStatusStr = "超低";
            }

            if(leakMonitorStatus == 2){
                leakMonitorStatusStr = "漏水";
            }else if(leakMonitorStatus == 3){
                leakMonitorStatusStr = "取水";
            }
            return (StringUtils.isNotEmpty(monitorStatusStr) && StringUtils.isNotEmpty(leakMonitorStatusStr)) ?
                    (monitorStatusStr + "/" + leakMonitorStatusStr) : (StringUtils.isNotEmpty(monitorStatusStr) ? monitorStatusStr : leakMonitorStatusStr);
        }
        return "/";
    }

    public String getImpactDamageDesc(){
        if (strikeMonitorStatus != null) {
            if (strikeMonitorStatus == 1) {
                return  "正常";
            }else if (strikeMonitorStatus == 2){
                return  "撞击";
            }else if (strikeMonitorStatus == 3){
                return  "开盖";
            }
        }
        return "/";
    }

    public String getFaultEventDesc(){
        if (leakMonitorStatus != null && strikeMonitorStatus != null) {
            String leakMonitorStatusStr = null;
            String strikeMonitorStatusStr = null;
            if (leakMonitorStatus == 1 && strikeMonitorStatus == 1) {
                return  "正常";
            }

            if(strikeMonitorStatus == 4){
                strikeMonitorStatusStr = "撞击传感器故障";
            }

            if(leakMonitorStatus == 4){
                leakMonitorStatusStr = "漏水传感器故障";
            }
            return (StringUtils.isNotEmpty(leakMonitorStatusStr) && StringUtils.isNotEmpty(strikeMonitorStatusStr)) ?
                    (leakMonitorStatusStr + "/" + strikeMonitorStatusStr) : (StringUtils.isNotEmpty(leakMonitorStatusStr) ? leakMonitorStatusStr : strikeMonitorStatusStr);
        }
        return "/";
    }

    public String getAlarmEventsDesc(){
        if (alarmEvents != null && alarmEvents.length > 0){
            StringBuilder sql = new StringBuilder();
            for(int event:alarmEvents){
                if(event == 1){
                    sql.append(" OR w.monitor_status = 1 AND w.leak_monitor_status = 1");
                }else if(event == 2){//超高报警
                    sql.append(" OR w.monitor_status = 2");
                }else if(event == 3){//超低报警
                    sql.append(" OR w.monitor_status = 3");
                }else if(event == 4){//漏水报警
                    sql.append(" OR w.leak_monitor_status = 2");
                }else if(event == 5){//取水报警
                    sql.append(" OR w.leak_monitor_status = 3");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getImpactDamagesDesc(){
        if (impactDamages != null && impactDamages.length > 0){
            StringBuilder sql = new StringBuilder();
            for(int event:impactDamages){
                if(event == 1){
                    sql.append(" OR w.strike_monitor_status = 1");
                }else if(event == 2){//2撞击
                    sql.append(" OR w.strike_monitor_status = 2");
                }else if(event == 3){//3开盖
                    sql.append(" OR w.strike_monitor_status = 3");
                }
            }
            return "(" + sql.substring(3) + ")";
        }
        return null;
    }

    public String getFaultEventsDesc(){
        if (faultEvents != null && faultEvents.length > 0){
            StringBuilder sql = new StringBuilder();
            for(int event:faultEvents){
                if(event == 1){
                    sql.append(" OR w.leak_monitor_status = 1 AND w.strike_monitor_status = 1");
                }else if(event == 2){//4撞击传感器故障
                    sql.append(" OR w.strike_monitor_status = 4");
                }else if(event == 3){//4漏水传感器故障
                    sql.append(" OR w.leak_monitor_status = 4");
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

    public int getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(int baiduLat) {
        this.baiduLat = baiduLat;
    }

    public int getBaiduLng() {
        return baiduLng;
    }

    public void setBaiduLng(int baiduLng) {
        this.baiduLng = baiduLng;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public boolean isSensorNoNull() {
        return sensorNoNull;
    }

    public void setSensorNoNull(boolean sensorNoNull) {
        this.sensorNoNull = sensorNoNull;
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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public String getFindSensorNo() {
        return findSensorNo;
    }

    public void setFindSensorNo(String findSensorNo) {
        this.findSensorNo = findSensorNo;
    }

    public String getSensorNoDesc() {
        if (type != null && type == 14){
            if (waterTerminalId == null || waterTerminalId == 0){
                return "供水端" + sensorNo;
            }else{
                return "系统端" + sensorNo;
            }
        }
        return sensorNo;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getPresentValue() {
        return presentValue;
    }

    public void setPresentValue(Integer presentValue) {
        this.presentValue = presentValue;
    }

    public String getPresentValueDesc(){
        if (getPresentValue() != null){
            return (getPresentValue() / 100.00f) + (StringUtils.isNotEmpty(unit) ? unit : "");
        }
        return "/";
    }
    public String getPresentValue2Desc(){
        if (getPresentValue2() != null){
            return (getPresentValue2() / 100.00f) + (StringUtils.isNotEmpty(unit) ? unit : "");
        }
        return "/";
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatusDesc(){
        Integer s = getDeviceStatus();
        if (s != null) {
            if (s == 1) {
                return "在线";
            } else if (s == 2) {
                return "离线";
            }
        }
        return "/";
    }

    public Integer getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Integer signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public String getSignalIntensityDesc(){
        return getSignalIntensityValue(signalIntensity);
    }

    public String getSignalIntensity2Desc(){
        return getSignalIntensityValue(signalIntensity2);
    }

    private String getSignalIntensityValue(Integer singInt){
        if (singInt != null) {
            if (singInt < 1000) {
                return "1";
            } else if (singInt < 1500) {
                return "2";
            } else if (singInt < 2000) {
                return "3";
            }else if (singInt < 2500) {
                return "4";
            }
            return "5";
        }
        return "/";
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

    public String getElectricQuantityDesc(){
        if (getElectricQuantity() != null){
            return (getElectricQuantity() / 100.00f) + "%";
        }
        return 0.00f + "%";
    }

    public Integer getElectricQuantityStatus() {
        return electricQuantityStatus;
    }

    public void setElectricQuantityStatus(Integer electricQuantityStatus) {
        this.electricQuantityStatus = electricQuantityStatus;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public Integer getSetUpdateStatus() {
        return setUpdateStatus;
    }

    public void setSetUpdateStatus(Integer setUpdateStatus) {
        this.setUpdateStatus = setUpdateStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getPointLocationDesc(){
        return getBuildingNameDesc() + getBuildingFloorNameDesc() + getPointLocation();
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public String getBuildingNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, buildingId, "buildingName");
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public String getBuildingFloorNameDesc(){
        return CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, buildingFloorId, "floorName");
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getSensorNo2() {
        return sensorNo2;
    }

    public void setSensorNo2(String sensorNo2) {
        this.sensorNo2 = sensorNo2;
    }

    public Integer getMinValue2() {
        return minValue2;
    }

    public void setMinValue2(Integer minValue2) {
        this.minValue2 = minValue2;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getMaxValue2() {
        return maxValue2;
    }

    public void setMaxValue2(Integer maxValue2) {
        this.maxValue2 = maxValue2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }

    public Integer getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(Integer alarmMode) {
        this.alarmMode = alarmMode;
    }

    public Integer getDataInterval() {
        return dataInterval;
    }

    public void setDataInterval(Integer dataInterval) {
        this.dataInterval = dataInterval;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWaveAlarmValue() {
        return waveAlarmValue;
    }

    public void setWaveAlarmValue(Integer waveAlarmValue) {
        this.waveAlarmValue = waveAlarmValue;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Integer getPresentValue2() {
        return presentValue2;
    }

    public void setPresentValue2(Integer presentValue2) {
        this.presentValue2 = presentValue2;
    }

    public Integer getDeviceStatus2() {
        return deviceStatus2;
    }

    public void setDeviceStatus2(Integer deviceStatus2) {
        this.deviceStatus2 = deviceStatus2;
    }

    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public Integer[] getLeakMonitorStatuss() {
        return leakMonitorStatuss;
    }

    public void setLeakMonitorStatuss(Integer[] leakMonitorStatuss) {
        this.leakMonitorStatuss = leakMonitorStatuss;
    }

    public String getMonitorStatusDesc() {
        if (monitorStatus != null){
            if (monitorStatus == 1){
                return "正常";
            }else if (monitorStatus == 2){
                return "超高";
            }else if (monitorStatus == 3){
                return "超低";
            }
        }
        return "/";
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public Integer getMonitorStatus2() {
        return monitorStatus2;
    }

    public void setMonitorStatus2(Integer monitorStatus2) {
        this.monitorStatus2 = monitorStatus2;
    }

    public Integer getSignalIntensity2() {
        return signalIntensity2;
    }

    public void setSignalIntensity2(Integer signalIntensity2) {
        this.signalIntensity2 = signalIntensity2;
    }

    public Integer getElectricQuantity2() {
        return electricQuantity2;
    }

    public void setElectricQuantity2(Integer electricQuantity2) {
        this.electricQuantity2 = electricQuantity2;
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Long getWaterTerminalId() {
        return waterTerminalId;
    }

    public void setWaterTerminalId(Long waterTerminalId) {
        this.waterTerminalId = waterTerminalId;
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

    public Integer getLeakMonitorStatus() {
        return leakMonitorStatus;
    }

    public void setLeakMonitorStatus(Integer leakMonitorStatus) {
        this.leakMonitorStatus = leakMonitorStatus;
    }

    public String getLeakMonitorStatusDesc() {
        if (leakMonitorStatus != null){
            if (leakMonitorStatus == 1){
                return "正常";
            }else if (leakMonitorStatus == 2){
                return "漏水";
            }else if (leakMonitorStatus == 3){
                return "取水";
            }else if (leakMonitorStatus == 4){
                return "漏水传感器故障";
            }
        }
        return "/";
    }

    public Integer getStrikeMonitorStatus() {
        return strikeMonitorStatus;
    }

    public void setStrikeMonitorStatus(Integer strikeMonitorStatus) {
        this.strikeMonitorStatus = strikeMonitorStatus;
    }

    public String getStrikeMonitorStatusDesc() {
        if (strikeMonitorStatus != null){
            if (strikeMonitorStatus == 1){
                return "正常";
            }else if (strikeMonitorStatus == 2){
                return "撞击";
            }else if (strikeMonitorStatus == 3){
                return "开盖";
            }else if (strikeMonitorStatus == 4){
                return "撞击传感器故障";
            }
        }
        return "/";
    }
    public Integer[] getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Integer[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public Integer[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Integer[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public boolean isFindSysTerminal() {
        return findSysTerminal;
    }

    public void setFindSysTerminal(boolean findSysTerminal) {
        this.findSysTerminal = findSysTerminal;
    }

    public boolean isFindWaterLBySensorNo() {
        return findWaterLBySensorNo;
    }

    public void setFindWaterLBySensorNo(boolean findWaterLBySensorNo) {
        this.findWaterLBySensorNo = findWaterLBySensorNo;
    }

    public Long getEditRecordId() {
        return editRecordId;
    }

    public void setEditRecordId(Long editRecordId) {
        this.editRecordId = editRecordId;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOpenCoverStatus() {
        return openCoverStatus;
    }

    public void setOpenCoverStatus(Integer openCoverStatus) {
        this.openCoverStatus = openCoverStatus;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public Integer getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
    }

    public Integer[] getFaultStatuss() {
        return faultStatuss;
    }

    public void setFaultStatuss(Integer[] faultStatuss) {
        this.faultStatuss = faultStatuss;
    }

    public String getFaultDesc(){
        if ((leakMonitorStatus != null && leakMonitorStatus  == 4) ||
                (strikeMonitorStatus != null && strikeMonitorStatus == 4) ||
                (openCoverStatus != null && openCoverStatus > 1)){
                return "故障";
        }
        return "/";
    }

    public Integer getValueUpdateStatus() {
        return valueUpdateStatus;
    }

    public void setValueUpdateStatus(Integer valueUpdateStatus) {
        this.valueUpdateStatus = valueUpdateStatus;
    }
}
