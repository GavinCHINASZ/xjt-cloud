package com.xjt.cloud.iot.core.common;

import com.xjt.cloud.iot.core.entity.device.IotDeviceReport;

/**
 * 消息Model
 *
 * @author huanggc
 * @date 2020/12/01
 */
public class MessageModel {
    /**
     * 工单统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void taskMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 工单统计:上月工单总数为12条,已完成10条,执行中1条,未完成1条;详情请前往[工单管理]中查看。
        stringBuilder.append("<p>工单统计:上").append(timeChinese).append("工单总数为<span> ").append(iotDeviceReport.getTaskTotalCount())
                .append(" </span>条,已完成<span> ").append(iotDeviceReport.getTaskCompleteCount())
                .append(" </span>条,执行中<span> ").append(iotDeviceReport.getTaskImplementCount())
                .append(" </span>条,未完成<span> ").append(iotDeviceReport.getTaskNoCount())
                .append(" </span>条;</p>");
    }

    /**
     * 故障报修统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void faultRepairMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 故障报修统计:上周/月/发生故障设备20个,其中已修复5个,维修中2个,未修复13个;详情请前往[故障报修]中查看。
        stringBuilder.append("<p>故障报修统计:上").append(timeChinese).append("发生故障设备<span> ").append(iotDeviceReport.getFaultRepairTotalCount())
                .append(" </span>台,其中已修复<span> ").append(iotDeviceReport.getFaultRepairCount())
                .append(" </span>台,维修中<span> ").append(iotDeviceReport.getFaultRepairUnderRepairCount())
                .append(" </span>台,未修复<span> ").append(iotDeviceReport.getFaultRepairNoCount())
                .append(" </span>台;</p>");
    }

    /**
     * 水压监测故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void waterGageMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 水压监测故障统计:水压监测设备总数112台,正常设备110台,异常设备2台;
        // 上日/周/月共发生40条(数量)告警事件,其中高压事件10条,低压事件8条,监低电量告警2条,离线设备30个;详情请前往[水压监测]查看。
        stringBuilder.append("<p>水压监测故障统计:水压监测设备总数<span> ").append(iotDeviceReport.getWaterGageTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getWaterGageOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getWaterGageOnLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getWaterGageEventCount())
                .append(" </span>条(数量)告警事件,其中超高事件<span> ").append(iotDeviceReport.getWaterSuperelevationEventCount())
                .append(" </span>条;超低事件<span> ").append(iotDeviceReport.getWaterUltralowEventCount())
                .append(" </span>条;离线事件<span> ").append(iotDeviceReport.getWaterOffLineEventCount())
                .append(" </span>条,低电量事件<span> ").append(iotDeviceReport.getWaterLowPowerEventCount())
                .append(" </span>条;<p>");
    }

    /**
     * 水浸故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void waterLeachingMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 水浸故障统计:水浸表设备总数设备112台,正常设备110台,异常设备2台;
        // 上日/周/月共发生40条(数量)告警事件,其中高压事件10条,低压事件8条,监低电量告警2条,线设备30个;详情请前往[水漫监测]查看。
        stringBuilder.append("<p>水浸故障统计:水浸表设备总数<span> ").append(iotDeviceReport.getWaterLeachingTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getWaterLeachingOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getWaterLeachingOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getWaterLeachingEventCount())
                .append(" </span>条(数量)告警事件,其中漏水事件<span> ").append(iotDeviceReport.getWaterLeachingLeakageEventCount())
                .append(" </span>条,低电量事件<span> ").append(iotDeviceReport.getWaterLeachingPowerEventCount())
                .append(" </span>条,离线事件<span> ").append(iotDeviceReport.getWaterLeachingOffLineEventCount())
                .append(" </span>个;</p>");
    }

    /**
     * 智能烟感故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void smokeMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 智能烟感故障统计:智能烟感设备总数设备112台,正常110台,异常2台;
        // 上日/周/同/月共发生40条(数量)告警事件,其中高压事件10条,低压事件8条,监低电量告警2条,离线设备30个;详情请前往[智能烟感]查看。
        stringBuilder.append("<p>智能烟感故障统计:智能烟感设备总数<span> ").append(iotDeviceReport.getSmokeTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getSmokeOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getSmokeOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getSmokeEventCount())
                .append(" </span>条(数量)告警事件,其中报警事件<span> ").append(iotDeviceReport.getSmokeFireEventCount())
                .append(" </span>条,离线事件<span> ").append(iotDeviceReport.getSmokeOffLineEventCount())
                .append(" </span>条,低电量事件<span> ").append(iotDeviceReport.getSmokeLowPowerEventCount())
                .append(" </span>条,故障事件<span> ").append(iotDeviceReport.getSmokeFaultEventCount())
                .append(" </span>条;</p>");
    }

    /**
     * 火警主机
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void fireAlarmMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 火警故障统计:火警主机设备总数12台,在线设备7台,离线设备5台;
        // 上日周/月发生火警事件40条,故障事件8条,监视事件2条;详情请前往[火警主机]查看。
        stringBuilder.append("<p>火警故障统计:火警主机设备总数<span> ").append(iotDeviceReport.getFireAlarmTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getFireAlarmOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getFireAlarmOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getFireAlarmEventCount())
                .append(" </span>条(数量)告警事件,其中火警事件<span> ").append(iotDeviceReport.getFireAlarmFireEventCount())
                .append(" </span>条,故障事件<span> ").append(iotDeviceReport.getFireAlarmFaultEventCount())
                .append(" </span>条,监视事件<span> ").append(iotDeviceReport.getFireAlarmMonitorEventCount())
                .append(" </span>条,离线事件<span> ").append(iotDeviceReport.getFireAlarmOffLineEventCount())
                .append(" </span>条;</p>");
    }

    /**
     * 极早期故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void vesaMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 极早期故障统计:极早期设备总数112台,在线设备110台,离线设备2台;
        // 上日/周/月共发生40条(数量)告警事件,其中火警1事件10条,火警2事件8条,行动事件2条,警告事件30条,故障事件3条,离线事件5条;详情请前往[极早期预警]查看。
        stringBuilder.append("<p>极早期故障统计:极早期设备总数<span> ").append(iotDeviceReport.getVesaTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getVesaOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getVesaOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getVesaEventCount())
                .append(" </span>条(数量)告警事件,其中火警1事件<span> ").append(iotDeviceReport.getVesaFire1EventCount())
                .append(" </span>条,火警2事件<span> ").append(iotDeviceReport.getVesaFire2EventCount())
                .append(" </span>条,行动事件<span> ").append(iotDeviceReport.getVesaActionEventCount())
                .append(" </span>条,警告事件<span> ").append(iotDeviceReport.getVesaWarningEventCount())
                .append(" </span>条,故障事件<span> ").append(iotDeviceReport.getVesaFaultEventCount())
                .append(" </span>条,离线事件<span> ").append(iotDeviceReport.getVesaOffLineEventCount())
                .append(" </span>个;</p>");
    }

    /**
     * 智能消火栓故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void fireHydrantMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 智能消火栓故障统计:智能消火栓设备总数112台,在线设备110台,离线设备2台;
        // 上日/周/月共发生40条(数量)告警事件,其中水压越高事件8条,水压超低事件30条,低电量事件3条,离线事件4条,
        // 故障事件9条(备注:撞击+开盖+漏水+取水+漏水传感器故障+撞击传感器故障);详情请前往[智能消火栓查看]查看。
        stringBuilder.append("<p>智能消火栓故障统计:智能消火栓设备总数<span> ").append(iotDeviceReport.getFireHydrantTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getFireAlarmOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getFireHydrantOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getFireHydrantFaultCount())
                .append(" </span>条(数量)告警事件,其中水压越高事件<span> ").append(iotDeviceReport.getFireHydrantSuperelevationCount())
                .append(" </span>条,水压超低事件<span> ").append(iotDeviceReport.getFireHydrantUltralowCount())
                .append(" </span>条,低电量事件<span> ").append(iotDeviceReport.getFireHydrantLowPowerCount())
                .append(" </span>条,离线事件<span> ").append(iotDeviceReport.getFireHydrantOffLineEventCount())
                .append(" </span>条,故障事件<span> ").append(iotDeviceReport.getFireHydrantFaultEventCount())
                .append(" </span>条(备注:撞击+开盖+漏水+取水+漏水传感器故障+撞击传感器故障);</p>");
    }

    /**
     * 声光故障统计
     *
     * @param stringBuilder StringBuilder
     * @param iotDeviceReport IotDeviceReport
     * @param timeChinese 时间
     */
    public static void linkageMessage(StringBuilder stringBuilder, IotDeviceReport iotDeviceReport, String timeChinese){
        // 声光故障统计:声光报警设备总数112台,在线设备110台,离线设备2台;
        // 上日/周/月共发生40条(数量)告警事件,其中报警事件8条,离线设备事件30条;详情请前往[声光报警]查看。
        stringBuilder.append("<p>声光故障统计:声光报警设备总数<span> ").append(iotDeviceReport.getLinkageTotalCount())
                .append(" </span>台,在线设备<span> ").append(iotDeviceReport.getLinkageOnLineCount())
                .append(" </span>台,离线设备<span> ").append(iotDeviceReport.getLinkageOffLineCount()).append(" </span>台;")
                .append("上").append(timeChinese).append("共发生<span> ").append(iotDeviceReport.getLinkageEventCount())
                .append(" </span>条(数量)告警事件,其中报警事件<span> ").append(iotDeviceReport.getLinkageFaultEventCount())
                .append(" </span>条,离线设备事件<span> ").append(iotDeviceReport.getLinkageOffLineEventCount())
                .append(" <span>条;</p>");
    }
}
