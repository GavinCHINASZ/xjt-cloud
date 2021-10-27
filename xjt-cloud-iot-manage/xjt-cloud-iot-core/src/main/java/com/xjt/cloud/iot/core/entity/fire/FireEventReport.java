package com.xjt.cloud.iot.core.entity.fire;

/**
 * @ClassName FireEventReport
 * @Author dwt
 * @Date 2019-10-22 13:39
 * @Description 火警主机报表实体
 * @Version 1.0
 */
public class FireEventReport {
    //事件总数
    private Long eventTotal;
    //火警事件数
    private Long fireEvent;
    //监视事件数
    private Long monitorEvent;
    //故障事件数
    private Long faultEvent;
    //离线事件数
    private Long offLineEvent;
    //时间
    private String createTime;
    //1:现场施工或环境震动,导致灰尘或异物进入 2:外部环境扭曲,3:周边环境电磁干扰,4:设备本体故障,5:其他,6:测试,7:测试
    private Integer eventCause;

    public Long getEventTotal() {
        return eventTotal;
    }

    public void setEventTotal(Long eventTotal) {
        this.eventTotal = eventTotal;
    }

    public Long getFireEvent() {
        return fireEvent;
    }

    public void setFireEvent(Long fireEvent) {
        this.fireEvent = fireEvent;
    }

    public Long getMonitorEvent() {
        return monitorEvent;
    }

    public void setMonitorEvent(Long monitorEvent) {
        this.monitorEvent = monitorEvent;
    }

    public Long getFaultEvent() {
        return faultEvent;
    }

    public void setFaultEvent(Long faultEvent) {
        this.faultEvent = faultEvent;
    }

    public Long getOffLineEvent() {
        return offLineEvent;
    }

    public void setOffLineEvent(Long offLineEvent) {
        this.offLineEvent = offLineEvent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getEventCause() {
        return eventCause;
    }

    public void setEventCause(Integer eventCause) {
        this.eventCause = eventCause;
    }
}
