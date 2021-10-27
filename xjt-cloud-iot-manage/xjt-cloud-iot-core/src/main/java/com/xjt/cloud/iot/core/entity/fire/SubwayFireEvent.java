package com.xjt.cloud.iot.core.entity.fire;

/**
 * @ClassName SubwayFireEvent
 * @Date 2020-05-13 10:28
 * @Version 1.0
 */
public class SubwayFireEvent {
    private Integer eventType;
    private String alarmDeviceName;
    private Integer alarmDeviceNum;
    //1:现场施工或环境震动,导致灰尘或异物进入 2:外部环境扭曲,3:周边环境电磁干扰,4:设备本体故障,5:其他,6:测试,7:火警
    private Integer eventCause;

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getAlarmDeviceName() {
        return alarmDeviceName;
    }

    public void setAlarmDeviceName(String alarmDeviceName) {
        this.alarmDeviceName = alarmDeviceName;
    }

    public Integer getAlarmDeviceNum() {
        return alarmDeviceNum;
    }

    public void setAlarmDeviceNum(Integer alarmDeviceNum) {
        this.alarmDeviceNum = alarmDeviceNum;
    }

    public String getEventTypeDesc(){
        String eventTypeDesc = "未知事件";
        if(eventType != null && eventType != 0){
            if(eventType == 1){
                eventTypeDesc = "火警事件";
            }else if(eventType == 2){
                    eventTypeDesc = "监管事件";
            }else if(eventType == 7){
                eventTypeDesc = "故障事件";
            }else if(eventType == 21){
                eventTypeDesc = "离线事件";
            }
        }
        return eventTypeDesc;
    }
    public String getEventCauseDesc(){
        String eventCauseDesc = "其他";
        switch (eventCause){
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
            case 7:
                eventCauseDesc = "火警";
                break;
        }
        return eventCauseDesc;
    }

    public void setEventCause(Integer eventCause) {
        this.eventCause = eventCause;
    }
}
