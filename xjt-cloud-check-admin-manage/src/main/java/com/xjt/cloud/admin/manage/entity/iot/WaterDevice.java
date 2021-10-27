package com.xjt.cloud.admin.manage.entity.iot;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 17:03
 * @Description: 水压设备信息对象
 */
public class WaterDevice extends BaseEntity {
    private Long deviceTypeId;//设备类型ID
    private Integer deviceType;//设备类型　默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控
    private Long checkPointId;//巡检点id
    private Long deviceId;//设备id
    private String sensorNo;//传感器id
    private String projectName;//项目名称
    private Integer minValue;//最小值 * 100
    private Integer maxValue;//最大值 * 100
    private Float minValueFloat;//最小值 * 100
    private Float maxValueFloat;//最大值 * 100
    private Integer presentValue;//当前值 * 100
    private Integer deviceStatus;//设备状态　1正常　2离线
    private Integer monitorStatus;//设备状态　1正常　2超高　3超低　
    private Integer signalIntensity;//信息强度　* 100
    private Integer signalStatus;//信息强度状态　1正常　2信号弱
    private Integer electricQuantity;//电量　*　100
    private Integer electricQuantityStatus;//电量状态　1正常　2电量低
    private Date statusUpdateTime;//最新状态修改时间
    private Date endHeartbeatTime;//最新状态修改时间
    private Integer setUpdateStatus;//1、等待下发指令到设备   2、已成功下发指令到设备    3、未成功下发指令到设备 4、已成功更改指令到设备
    private Integer type;//默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控 13水厢以前单位为Ｍ 14两端压力设备
    private String deviceSysName;//设备名称
    private String qrNo;//设备二维码
    private String pointLocation;//巡检点位置（设备位置）
    private Long buildingId;//建筑物id
    private Long buildingFloorId;//楼层id
    private String simProduct;//卡号
    private Integer alarmMode;//告警方式设置  1；阈值告警，2；波动值告警，3；阈值和波动值告警，对水浸表无效
    private Integer dataInterval;//数据采样间隔01~13 对应  5s,10s,30s,1,5,10,15,30,60,120,360,720,1440分钟
    private Integer heartbeat;//心跳发送间隔       09~13 对应 60,120,360,720,1440分钟
    private String url;//域名路径
    private Integer waveAlarmValue;//波动告警值 * 100
    private Float waveAlarmValueFloat;
    private Integer port;//端口
    private Integer sendType;//发送信息给设备类型　1不发送　2返回信息　3主动发送
    private String unit;//单位
    private List<Long> ids;
    private String pointName;//巡检点名称
    private String pointQrNo;//巡检点二维码
    private Long waterTerminalId;//两个端　供水端设备的主键id
    private String pinYinInitials;//拼音首写字母
    private String deviceIdAndDeviceType;//设备类型与设备id以_分隔
    private String tableName;//表格名称
    private Integer valueUpdateStatus;//1、等待下发指令到设备   2、已成功下发指令到设备    3、未成功下发指令到设备 4、已成功更改指令到设备

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getValueUpdateStatus() {
        return valueUpdateStatus;
    }

    public void setValueUpdateStatus(Integer valueUpdateStatus) {
        this.valueUpdateStatus = valueUpdateStatus;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDeviceIdAndDeviceType() {
        return deviceIdAndDeviceType;
    }

    public void setDeviceIdAndDeviceType(String deviceIdAndDeviceType) {
        this.deviceIdAndDeviceType = deviceIdAndDeviceType;
    }

    public String getPinYinInitials() {
        return pinYinInitials;
    }

    public void setPinYinInitials(String pinYinInitials) {
        this.pinYinInitials = pinYinInitials;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    //标题
    private String title;

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
        return "";
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
                return "正常";
            } else if (s == 2) {
                return "离线";
            }
        }
        return "正常";
    }

    public Integer getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Integer signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public Integer getSignalIntensityDesc(){
        return getSignalIntensityValue(signalIntensity);
    }

    private Integer getSignalIntensityValue(Integer singInt){
        if (singInt != null) {
            if (singInt < 1000) {
                return 1;
            } else if (singInt < 1500) {
                return 2;
            } else if (singInt < 2000) {
                return 3;
            }else if (singInt < 2500) {
                return 4;
            }
            return 5;
        }
        return null;
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

    public String getPointLocationDesc() {
        return getBuildingNameDesc() + getBuildingFloorNameDesc() + pointLocation;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSimProduct() {
        return simProduct;
    }

    public void setSimProduct(String simProduct) {
        this.simProduct = simProduct;
    }

    public Integer getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(Integer alarmMode) {
        this.alarmMode = alarmMode;
    }

    public String getAlarmModeDesc() {
        if (alarmMode == null){
            return  null;
        }
        String res = "";
        if(1 == alarmMode){
            res = "阈值告警";
        }else if(2 == alarmMode){
            res = "波动值告警";
        }else if(3 == alarmMode){
            res = "阈值和波动值告警";
        }
        return res;
    }

    public Integer getDataInterval() {
        return dataInterval;
    }

    public void setDataInterval(Integer dataInterval) {
        this.dataInterval = dataInterval;
    }

    public String getDataIntervalDesc() {
        if (dataInterval == null || deviceType == null){
            return  null;
        }
        String res = "l30秒";
        if (deviceType == 8){
            if (0 == dataInterval){
                res = "20秒";
            }else if(1 == dataInterval){
                res = "1秒";
            }else if(2 == dataInterval){
                res = "2秒";
            }else if(3 == dataInterval){
                res = "3秒";
            }else if(4 == dataInterval){
                res = "4秒";
            }else if(5 == dataInterval){
                res = "5秒";
            }else if(6 == dataInterval){
                res = "6秒";
            }else if(7 == dataInterval){
                res = "7秒";
            }else if(8 == dataInterval){
                res = "8秒";
            }else if(9 == dataInterval){
                res = "9秒";
            }
        }else{
            if(4 == dataInterval){
                res = "1分钟";
            }else if(5 == dataInterval){
                res = "5分钟";
            }else if(6 == dataInterval){
                res = "10分钟";
            }else if(7 == dataInterval){
                res = "15分钟";
            }else if(5 == dataInterval){
                res = "30分钟";
            }else if(9 == dataInterval){
                res = "60分钟";
            }else if(10 == dataInterval){
                res = "120分钟";
            }else if(11 == dataInterval){
                res = "360分钟";
            }else if(12 == dataInterval){
                res = "720分钟";
            }else if(13 == dataInterval){
                res = "1440分钟";
            }
        }

        return res;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getHeartbeatDesc() {
        if (heartbeat == null || deviceType == null){
            return  null;
        }
        String res = "l30秒";
        if (deviceType == 8){
            if (1 == heartbeat){
                res = "24小时";
            }else if(2 == heartbeat){
                res = "12小时";
            }else if(3 == heartbeat){
                res = "6小时";
            }else if(4 == heartbeat){
                res = "3小时";
            }else if(5 == heartbeat){
                res = "2小时";
            }else if(6 == heartbeat){
                res = "1小时";
            }else if(7 == heartbeat){
                res = "半小时";
            }
        }else {
            if (9 == heartbeat) {
                res = "60分钟";
            } else if (10 == heartbeat) {
                res = "120分钟";
            } else if (11 == heartbeat) {
                res = "360分钟";
            } else if (12 == heartbeat) {
                res = "720分钟";
            } else if (13 == heartbeat) {
                res = "1440分钟";
            }
        }
        return res;
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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Date getEndHeartbeatTime() {
        return endHeartbeatTime;
    }

    public void setEndHeartbeatTime(Date endHeartbeatTime) {
        this.endHeartbeatTime = endHeartbeatTime;
    }

    public Integer getMonitorStatus() {
        return monitorStatus;
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
        return "正常";
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

    public Float getMinValueFloat() {
        return minValueFloat;
    }
    public Integer getMinValueFloatDesc() {
        if (minValueFloat == null){
            return null;
        }
        return (int)(minValueFloat * 100);
    }

    public void setMinValueFloat(Float minValueFloat) {
        this.minValueFloat = minValueFloat;
    }

    public Float getMaxValueFloat() {
        return maxValueFloat;
    }

    public Integer getMaxValueFloatDesc() {
        if (maxValueFloat == null){
            return null;
        }
        return (int)(maxValueFloat * 100);
    }

    public void setMaxValueFloat(Float maxValueFloat) {
        this.maxValueFloat = maxValueFloat;
    }

    public Float getWaveAlarmValueFloat() {
        return waveAlarmValueFloat;
    }
    public Integer getWaveAlarmValueFloatDesc() {
        if (waveAlarmValueFloat == null){
            return null;
        }
        return (int)(waveAlarmValueFloat * 100);
    }

    public void setWaveAlarmValueFloat(Float waveAlarmValueFloat) {
        this.waveAlarmValueFloat = waveAlarmValueFloat;
    }
}
