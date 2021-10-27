package com.xjt.cloud.iot.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * IotDeviceReport
 *
 * @author huanggc
 * @date 2020/12/01
 */
public class IotDeviceReport extends BaseEntity {
    private String[] orderByProjectIds;//排序的项目id数组
    private String[] findProjectIds;//查询项目id数组
    private String projectName;//项目名称
    // 周, 月
    private String timeChinese;

    // 任务总数
    private Integer taskTotalCount;
    // 任务完成数
    private Integer taskCompleteCount;
    // 任务执行中数
    private Integer taskImplementCount;
    // 任务未完成数
    private Integer taskNoCount;

    // 故障报修 设备总数
    private Integer faultRepairTotalCount;
    // 故障报修 已修复数
    private Integer faultRepairCount;
    // 故障报修 维修中数
    private Integer faultRepairUnderRepairCount;
    // 故障报修 未修复
    private Integer faultRepairNoCount;

    private Integer waterGageTotalCount;//水压总设备数
    private Integer waterGageOnLineCount;//水压在线数
    private Integer waterGageOffLineCount;//水压离线数
    // 水压 事件数
    private Integer waterGageEventCount;
    // 水压 超高事件数
    private Integer waterSuperelevationEventCount;
    // 水压 超低事件数
    private Integer waterUltralowEventCount;
    // 水压 低电量事件数
    private Integer waterLowPowerEventCount;
    // 水压 离线事件数
    private Integer waterOffLineEventCount;

    private Integer waterLeachingTotalCount;//水浸总设备数
    private Integer waterLeachingOnLineCount;//水浸在线数
    private Integer waterLeachingOffLineCount;//水浸离线数
    // 水浸 事件数
    private Integer waterLeachingEventCount;
    // 水浸 漏水事件数
    private Integer waterLeachingLeakageEventCount;
    // 水浸 低电量事件数
    private Integer waterLeachingPowerEventCount;
    // 水浸 离线事件数
    private Integer waterLeachingOffLineEventCount;

    private Integer smokeTotalCount;//烟感总设备数
    private Integer smokeOnLineCount;//烟感在线数
    private Integer smokeOffLineCount;//烟感离线数
    // 烟感 事件数
    private Integer smokeEventCount;
    // 烟感 报警事件数
    private Integer smokeFireEventCount;
    // 烟感 离线事件数
    private Integer smokeOffLineEventCount;
    // 烟感 低电量事件数
    private Integer smokeLowPowerEventCount;
    // 烟感 故障事件数
    private Integer smokeFaultEventCount;

    private Integer fireAlarmTotalCount;//火警总设备数
    private Integer fireAlarmOnLineCount;//火警在线数
    private Integer fireAlarmOffLineCount;//火警离线数
    // 火警主机 事件数
    private Integer fireAlarmEventCount;
    // 火警主机 火警事件数
    private Integer fireAlarmFireEventCount;
    // 火警主机 故障事件数
    private Integer fireAlarmFaultEventCount;
    // 火警主机 监视事件数
    private Integer fireAlarmMonitorEventCount;
    // 火警主机 离线事件数
    private Integer fireAlarmOffLineEventCount;

    private Integer vesaTotalCount;//极早期总设备数
    private Integer vesaOnLineCount;//极早期在线数
    private Integer vesaOffLineCount;//极早期离线数
    // 极早期 事件数
    private Integer vesaEventCount;
    // 极早期 火警1事件数
    private Integer vesaFire1EventCount;
    // 极早期 火警2事件数
    private Integer vesaFire2EventCount;
    // 极早期 行动事件数
    private Integer vesaActionEventCount;
    // 极早期 警告事件数
    private Integer vesaWarningEventCount;
    // 极早期 故障事件数
    private Integer vesaFaultEventCount;
    // 极早期 离线事件数
    private Integer vesaOffLineEventCount;

    private Integer fireHydrantTotalCount;//消火栓总设备数
    private Integer fireHydrantOnLineCount;//消火栓在线数
    private Integer fireHydrantOffLineCount;//消火栓离线数
    // 消火栓 故障事件总数
    private Integer fireHydrantFaultCount;
    // 消火栓 超高表数
    private Integer fireHydrantSuperelevationCount;
    // 消火栓 超低表数
    private Integer fireHydrantUltralowCount;
    // 消火栓 故障事件数
    private Integer fireHydrantFaultEventCount;
    // 消火栓 低电量
    private Integer fireHydrantLowPowerCount;
    // 消火栓 离线事件数
    private Integer fireHydrantOffLineEventCount;

    private Integer linkageTotalCount;//声光总设备数
    private Integer linkageOnLineCount;//声光在线数
    private Integer linkageOffLineCount;//声光离线数
    // 声光事件总数
    private Integer linkageEventCount;
    // 声光 3=离线
    private Integer linkageOffLineEventCount;
    // 声光 2=报警
    private Integer linkageFaultEventCount;


    public Integer getVesaWarningEventCount() {
        return vesaWarningEventCount;
    }

    public void setVesaWarningEventCount(Integer vesaWarningEventCount) {
        this.vesaWarningEventCount = vesaWarningEventCount;
    }

    public Integer getVesaOffLineEventCount() {
        return vesaOffLineEventCount;
    }

    public void setVesaOffLineEventCount(Integer vesaOffLineEventCount) {
        this.vesaOffLineEventCount = vesaOffLineEventCount;
    }

    public Integer getFireHydrantFaultEventCount() {
        return fireHydrantFaultEventCount;
    }

    public void setFireHydrantFaultEventCount(Integer fireHydrantFaultEventCount) {
        this.fireHydrantFaultEventCount = fireHydrantFaultEventCount;
    }

    public Integer getFireHydrantOffLineEventCount() {
        return fireHydrantOffLineEventCount;
    }

    public void setFireHydrantOffLineEventCount(Integer fireHydrantOffLineEventCount) {
        this.fireHydrantOffLineEventCount = fireHydrantOffLineEventCount;
    }

    public Integer getLinkageEventCount() {
        return linkageEventCount;
    }

    public void setLinkageEventCount(Integer linkageEventCount) {
        this.linkageEventCount = linkageEventCount;
    }

    public String[] getOrderByProjectIds() {
        return orderByProjectIds;
    }

    public void setOrderByProjectIds(String[] orderByProjectIds) {
        this.orderByProjectIds = orderByProjectIds;
    }

    public String[] getFindProjectIds() {
        return findProjectIds;
    }

    public void setFindProjectIds(String[] findProjectIds) {
        this.findProjectIds = findProjectIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTimeChinese() {
        return timeChinese;
    }

    public void setTimeChinese(String timeChinese) {
        this.timeChinese = timeChinese;
    }

    public Integer getTaskTotalCount() {
        return taskTotalCount;
    }

    public void setTaskTotalCount(Integer taskTotalCount) {
        this.taskTotalCount = taskTotalCount;
    }

    public Integer getTaskCompleteCount() {
        return taskCompleteCount;
    }

    public void setTaskCompleteCount(Integer taskCompleteCount) {
        this.taskCompleteCount = taskCompleteCount;
    }

    public Integer getTaskImplementCount() {
        return taskImplementCount;
    }

    public void setTaskImplementCount(Integer taskImplementCount) {
        this.taskImplementCount = taskImplementCount;
    }

    public Integer getTaskNoCount() {
        return taskNoCount;
    }

    public void setTaskNoCount(Integer taskNoCount) {
        this.taskNoCount = taskNoCount;
    }

    public Integer getFaultRepairTotalCount() {
        return faultRepairTotalCount;
    }

    public void setFaultRepairTotalCount(Integer faultRepairTotalCount) {
        this.faultRepairTotalCount = faultRepairTotalCount;
    }

    public Integer getFaultRepairCount() {
        return faultRepairCount;
    }

    public void setFaultRepairCount(Integer faultRepairCount) {
        this.faultRepairCount = faultRepairCount;
    }

    public Integer getFaultRepairUnderRepairCount() {
        return faultRepairUnderRepairCount;
    }

    public void setFaultRepairUnderRepairCount(Integer faultRepairUnderRepairCount) {
        this.faultRepairUnderRepairCount = faultRepairUnderRepairCount;
    }

    public Integer getFaultRepairNoCount() {
        return faultRepairNoCount;
    }

    public void setFaultRepairNoCount(Integer faultRepairNoCount) {
        this.faultRepairNoCount = faultRepairNoCount;
    }

    public Integer getWaterGageTotalCount() {
        return waterGageTotalCount;
    }

    public void setWaterGageTotalCount(Integer waterGageTotalCount) {
        this.waterGageTotalCount = waterGageTotalCount;
    }

    public Integer getWaterGageOnLineCount() {
        return waterGageOnLineCount;
    }

    public void setWaterGageOnLineCount(Integer waterGageOnLineCount) {
        this.waterGageOnLineCount = waterGageOnLineCount;
    }

    public Integer getWaterGageOffLineCount() {
        return waterGageOffLineCount;
    }

    public void setWaterGageOffLineCount(Integer waterGageOffLineCount) {
        this.waterGageOffLineCount = waterGageOffLineCount;
    }

    public Integer getWaterGageEventCount() {
        return waterGageEventCount;
    }

    public void setWaterGageEventCount(Integer waterGageEventCount) {
        this.waterGageEventCount = waterGageEventCount;
    }

    public Integer getWaterSuperelevationEventCount() {
        return waterSuperelevationEventCount;
    }

    public void setWaterSuperelevationEventCount(Integer waterSuperelevationEventCount) {
        this.waterSuperelevationEventCount = waterSuperelevationEventCount;
    }

    public Integer getWaterUltralowEventCount() {
        return waterUltralowEventCount;
    }

    public void setWaterUltralowEventCount(Integer waterUltralowEventCount) {
        this.waterUltralowEventCount = waterUltralowEventCount;
    }

    public Integer getWaterLowPowerEventCount() {
        return waterLowPowerEventCount;
    }

    public void setWaterLowPowerEventCount(Integer waterLowPowerEventCount) {
        this.waterLowPowerEventCount = waterLowPowerEventCount;
    }

    public Integer getWaterOffLineEventCount() {
        return waterOffLineEventCount;
    }

    public void setWaterOffLineEventCount(Integer waterOffLineEventCount) {
        this.waterOffLineEventCount = waterOffLineEventCount;
    }

    public Integer getWaterLeachingTotalCount() {
        return waterLeachingTotalCount;
    }

    public void setWaterLeachingTotalCount(Integer waterLeachingTotalCount) {
        this.waterLeachingTotalCount = waterLeachingTotalCount;
    }

    public Integer getWaterLeachingOnLineCount() {
        return waterLeachingOnLineCount;
    }

    public void setWaterLeachingOnLineCount(Integer waterLeachingOnLineCount) {
        this.waterLeachingOnLineCount = waterLeachingOnLineCount;
    }

    public Integer getWaterLeachingOffLineCount() {
        return waterLeachingOffLineCount;
    }

    public void setWaterLeachingOffLineCount(Integer waterLeachingOffLineCount) {
        this.waterLeachingOffLineCount = waterLeachingOffLineCount;
    }

    public Integer getWaterLeachingEventCount() {
        return waterLeachingEventCount;
    }

    public void setWaterLeachingEventCount(Integer waterLeachingEventCount) {
        this.waterLeachingEventCount = waterLeachingEventCount;
    }

    public Integer getWaterLeachingLeakageEventCount() {
        return waterLeachingLeakageEventCount;
    }

    public void setWaterLeachingLeakageEventCount(Integer waterLeachingLeakageEventCount) {
        this.waterLeachingLeakageEventCount = waterLeachingLeakageEventCount;
    }

    public Integer getWaterLeachingPowerEventCount() {
        return waterLeachingPowerEventCount;
    }

    public void setWaterLeachingPowerEventCount(Integer waterLeachingPowerEventCount) {
        this.waterLeachingPowerEventCount = waterLeachingPowerEventCount;
    }

    public Integer getWaterLeachingOffLineEventCount() {
        return waterLeachingOffLineEventCount;
    }

    public void setWaterLeachingOffLineEventCount(Integer waterLeachingOffLineEventCount) {
        this.waterLeachingOffLineEventCount = waterLeachingOffLineEventCount;
    }

    public Integer getSmokeTotalCount() {
        return smokeTotalCount;
    }

    public void setSmokeTotalCount(Integer smokeTotalCount) {
        this.smokeTotalCount = smokeTotalCount;
    }

    public Integer getSmokeOnLineCount() {
        return smokeOnLineCount;
    }

    public void setSmokeOnLineCount(Integer smokeOnLineCount) {
        this.smokeOnLineCount = smokeOnLineCount;
    }

    public Integer getSmokeOffLineCount() {
        return smokeOffLineCount;
    }

    public void setSmokeOffLineCount(Integer smokeOffLineCount) {
        this.smokeOffLineCount = smokeOffLineCount;
    }

    public Integer getSmokeFireEventCount() {
        return smokeFireEventCount;
    }

    public void setSmokeFireEventCount(Integer smokeFireEventCount) {
        this.smokeFireEventCount = smokeFireEventCount;
    }

    public Integer getSmokeFaultEventCount() {
        return smokeFaultEventCount;
    }

    public void setSmokeFaultEventCount(Integer smokeFaultEventCount) {
        this.smokeFaultEventCount = smokeFaultEventCount;
    }

    public Integer getSmokeEventCount() {
        return smokeEventCount;
    }

    public void setSmokeEventCount(Integer smokeEventCount) {
        this.smokeEventCount = smokeEventCount;
    }

    public Integer getSmokeOffLineEventCount() {
        return smokeOffLineEventCount;
    }

    public void setSmokeOffLineEventCount(Integer smokeOffLineEventCount) {
        this.smokeOffLineEventCount = smokeOffLineEventCount;
    }

    public Integer getSmokeLowPowerEventCount() {
        return smokeLowPowerEventCount;
    }

    public void setSmokeLowPowerEventCount(Integer smokeLowPowerEventCount) {
        this.smokeLowPowerEventCount = smokeLowPowerEventCount;
    }

    public Integer getFireAlarmTotalCount() {
        return fireAlarmTotalCount;
    }

    public void setFireAlarmTotalCount(Integer fireAlarmTotalCount) {
        this.fireAlarmTotalCount = fireAlarmTotalCount;
    }

    public Integer getFireAlarmOnLineCount() {
        return fireAlarmOnLineCount;
    }

    public void setFireAlarmOnLineCount(Integer fireAlarmOnLineCount) {
        this.fireAlarmOnLineCount = fireAlarmOnLineCount;
    }

    public Integer getFireAlarmOffLineCount() {
        return fireAlarmOffLineCount;
    }

    public void setFireAlarmOffLineCount(Integer fireAlarmOffLineCount) {
        this.fireAlarmOffLineCount = fireAlarmOffLineCount;
    }

    public Integer getFireAlarmOffLineEventCount() {
        return fireAlarmOffLineEventCount;
    }

    public void setFireAlarmOffLineEventCount(Integer fireAlarmOffLineEventCount) {
        this.fireAlarmOffLineEventCount = fireAlarmOffLineEventCount;
    }

    public Integer getFireAlarmEventCount() {
        return fireAlarmEventCount;
    }

    public void setFireAlarmEventCount(Integer fireAlarmEventCount) {
        this.fireAlarmEventCount = fireAlarmEventCount;
    }

    public Integer getFireAlarmFireEventCount() {
        return fireAlarmFireEventCount;
    }

    public void setFireAlarmFireEventCount(Integer fireAlarmFireEventCount) {
        this.fireAlarmFireEventCount = fireAlarmFireEventCount;
    }

    public Integer getFireAlarmFaultEventCount() {
        return fireAlarmFaultEventCount;
    }

    public void setFireAlarmFaultEventCount(Integer fireAlarmFaultEventCount) {
        this.fireAlarmFaultEventCount = fireAlarmFaultEventCount;
    }

    public Integer getFireAlarmMonitorEventCount() {
        return fireAlarmMonitorEventCount;
    }

    public void setFireAlarmMonitorEventCount(Integer fireAlarmMonitorEventCount) {
        this.fireAlarmMonitorEventCount = fireAlarmMonitorEventCount;
    }

    public Integer getVesaTotalCount() {
        return vesaTotalCount;
    }

    public void setVesaTotalCount(Integer vesaTotalCount) {
        this.vesaTotalCount = vesaTotalCount;
    }

    public Integer getVesaOnLineCount() {
        return vesaOnLineCount;
    }

    public void setVesaOnLineCount(Integer vesaOnLineCount) {
        this.vesaOnLineCount = vesaOnLineCount;
    }

    public Integer getVesaOffLineCount() {
        return vesaOffLineCount;
    }

    public void setVesaOffLineCount(Integer vesaOffLineCount) {
        this.vesaOffLineCount = vesaOffLineCount;
    }

    public Integer getVesaEventCount() {
        return vesaEventCount;
    }

    public void setVesaEventCount(Integer vesaEventCount) {
        this.vesaEventCount = vesaEventCount;
    }


    public Integer getVesaFire2EventCount() {
        return vesaFire2EventCount;
    }

    public void setVesaFire2EventCount(Integer vesaFire2EventCount) {
        this.vesaFire2EventCount = vesaFire2EventCount;
    }

    public Integer getVesaFire1EventCount() {
        return vesaFire1EventCount;
    }

    public void setVesaFire1EventCount(Integer vesaFire1EventCount) {
        this.vesaFire1EventCount = vesaFire1EventCount;
    }

    public Integer getVesaActionEventCount() {
        return vesaActionEventCount;
    }

    public void setVesaActionEventCount(Integer vesaActionEventCount) {
        this.vesaActionEventCount = vesaActionEventCount;
    }

    public Integer getVesaFaultEventCount() {
        return vesaFaultEventCount;
    }

    public void setVesaFaultEventCount(Integer vesaFaultEventCount) {
        this.vesaFaultEventCount = vesaFaultEventCount;
    }

    public Integer getFireHydrantTotalCount() {
        return fireHydrantTotalCount;
    }

    public void setFireHydrantTotalCount(Integer fireHydrantTotalCount) {
        this.fireHydrantTotalCount = fireHydrantTotalCount;
    }

    public Integer getFireHydrantOnLineCount() {
        return fireHydrantOnLineCount;
    }

    public void setFireHydrantOnLineCount(Integer fireHydrantOnLineCount) {
        this.fireHydrantOnLineCount = fireHydrantOnLineCount;
    }

    public Integer getFireHydrantOffLineCount() {
        return fireHydrantOffLineCount;
    }

    public void setFireHydrantOffLineCount(Integer fireHydrantOffLineCount) {
        this.fireHydrantOffLineCount = fireHydrantOffLineCount;
    }

    public Integer getFireHydrantFaultCount() {
        return fireHydrantFaultCount;
    }

    public void setFireHydrantFaultCount(Integer fireHydrantFaultCount) {
        this.fireHydrantFaultCount = fireHydrantFaultCount;
    }

    public Integer getFireHydrantSuperelevationCount() {
        return fireHydrantSuperelevationCount;
    }

    public void setFireHydrantSuperelevationCount(Integer fireHydrantSuperelevationCount) {
        this.fireHydrantSuperelevationCount = fireHydrantSuperelevationCount;
    }

    public Integer getFireHydrantUltralowCount() {
        return fireHydrantUltralowCount;
    }

    public void setFireHydrantUltralowCount(Integer fireHydrantUltralowCount) {
        this.fireHydrantUltralowCount = fireHydrantUltralowCount;
    }

    public Integer getFireHydrantLowPowerCount() {
        return fireHydrantLowPowerCount;
    }

    public void setFireHydrantLowPowerCount(Integer fireHydrantLowPowerCount) {
        this.fireHydrantLowPowerCount = fireHydrantLowPowerCount;
    }

    public Integer getLinkageTotalCount() {
        return linkageTotalCount;
    }

    public void setLinkageTotalCount(Integer linkageTotalCount) {
        this.linkageTotalCount = linkageTotalCount;
    }

    public Integer getLinkageOnLineCount() {
        return linkageOnLineCount;
    }

    public void setLinkageOnLineCount(Integer linkageOnLineCount) {
        this.linkageOnLineCount = linkageOnLineCount;
    }

    public Integer getLinkageOffLineCount() {
        return linkageOffLineCount;
    }

    public void setLinkageOffLineCount(Integer linkageOffLineCount) {
        this.linkageOffLineCount = linkageOffLineCount;
    }

    public Integer getLinkageOffLineEventCount() {
        return linkageOffLineEventCount;
    }

    public void setLinkageOffLineEventCount(Integer linkageOffLineEventCount) {
        this.linkageOffLineEventCount = linkageOffLineEventCount;
    }

    public Integer getLinkageFaultEventCount() {
        return linkageFaultEventCount;
    }

    public void setLinkageFaultEventCount(Integer linkageFaultEventCount) {
        this.linkageFaultEventCount = linkageFaultEventCount;
    }
}
