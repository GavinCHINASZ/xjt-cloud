package com.xjt.cloud.iot.core.entity.fire;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 地铁 作业 --- 综合监测
 *
 * @author huanggc
 */
public class IntegratedMonitoring extends BaseEntity {
    /**
     * 作业ID
     */
    private Long protectId;

    /**
     * 例: FAS触发火灾报警手自动
     */
    private String monitoringName;


    /**
     * 例: TRIG0001. AITR
     */
    private String monitoringCode;

    /**
     * 例: 97.6(有值算完成)
     */
    private String monitoringValue;

    /**
     * 监测状态 1未完成, 2已完成
     */
    private int monitoringStatus;
    private int oldMonitoringStatus;

    /**
     * 手动触发/自动触发;检修/正常
     */
    private String monitoringMemo;

    public Long getProtectId() {
        return protectId;
    }

    public void setProtectId(Long protectId) {
        this.protectId = protectId;
    }

    public String getMonitoringName() {
        return monitoringName;
    }

    public void setMonitoringName(String monitoringName) {
        this.monitoringName = monitoringName;
    }

    public String getMonitoringCode() {
        return monitoringCode;
    }

    public void setMonitoringCode(String monitoringCode) {
        this.monitoringCode = monitoringCode;
    }

    public String getMonitoringValue() {
        return monitoringValue;
    }

    public void setMonitoringValue(String monitoringValue) {
        this.monitoringValue = monitoringValue;
    }

    public int getMonitoringStatus() {
        return monitoringStatus;
    }

    public void setMonitoringStatus(int monitoringStatus) {
        this.monitoringStatus = monitoringStatus;
    }

    public int getOldMonitoringStatus() {
        return oldMonitoringStatus;
    }

    public void setOldMonitoringStatus(int oldMonitoringStatus) {
        this.oldMonitoringStatus = oldMonitoringStatus;
    }

    public String getMonitoringMemo() {
        return monitoringMemo;
    }

    public void setMonitoringMemo(String monitoringMemo) {
        this.monitoringMemo = monitoringMemo;
    }
}
