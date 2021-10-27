package com.xjt.cloud.task.core.entity.protect;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * FireAlarmEvent 火警主机事件实体
 *
 * @author dwt
 * @date 2019-10-11 15:05
 */
public class FireAlarmEvent extends BaseEntity {
    //恢复时间
    private Date recoverTime;

    //事件状态 1-已恢复/历史记录，2-未恢复/活跃事件
    private Integer recoverStatus;


    //滞后原因及描述/备注
    private String remarks;

    /**
     * 报警位置
     */
    private String alarmPosition;

    //是否确认 1:未确认/未处理，2：已确认/已处理
    private Integer confirm;

    //处理状态，1存在事件  2没有事件
    private Integer eventExistStatus;


    public Date getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Date recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAlarmPosition() {
        return alarmPosition;
    }

    public void setAlarmPosition(String alarmPosition) {
        this.alarmPosition = alarmPosition;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public Integer getEventExistStatus() {
        return eventExistStatus;
    }

    public void setEventExistStatus(Integer eventExistStatus) {
        this.eventExistStatus = eventExistStatus;
    }
}
