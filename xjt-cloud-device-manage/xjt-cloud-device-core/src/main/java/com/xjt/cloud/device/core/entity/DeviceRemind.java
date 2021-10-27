package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 设备过期提醒 实体
 *
 * @Auther huanggc
 * @Date 2020-03-27
 */
public class DeviceRemind extends BaseEntity {
    private Long projectId;// 项目ID

    private Integer deviceRemind;// 设备过期提醒时间(过期前?日提醒)
    private Integer deviceOrder;// 生成报修工单: 1是, 2否

    private Integer repairRemind;// 送修提醒时间(送修前?提醒)
    private Integer repairOrder;// 生成送修工单: 1是, 2否

    @Override
    public Long getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getDeviceRemind() {
        return deviceRemind;
    }

    public void setDeviceRemind(Integer deviceRemind) {
        this.deviceRemind = deviceRemind;
    }

    public Integer getDeviceOrder() {
        return deviceOrder;
    }

    public void setDeviceOrder(Integer deviceOrder) {
        this.deviceOrder = deviceOrder;
    }

    public Integer getRepairRemind() {
        return repairRemind;
    }

    public void setRepairRemind(Integer repairRemind) {
        this.repairRemind = repairRemind;
    }

    public Integer getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(Integer repairOrder) {
        this.repairOrder = repairOrder;
    }
}
