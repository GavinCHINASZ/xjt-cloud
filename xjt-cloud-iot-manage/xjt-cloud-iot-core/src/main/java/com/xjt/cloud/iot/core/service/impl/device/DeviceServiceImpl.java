package com.xjt.cloud.iot.core.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.common.MessageModel;
import com.xjt.cloud.iot.core.dao.device.IotDeviceReportDao;
import com.xjt.cloud.iot.core.entity.device.IotDeviceReport;
import com.xjt.cloud.iot.core.service.service.device.DeviceService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DeviceServiceImpl
 *
 *@author huanggc
 *@date 2020/11/25
 */
@Service
public class DeviceServiceImpl extends AbstractService implements DeviceService {
    @Autowired
    private IotDeviceReportDao iotDeviceReportDao;
    @Autowired
    private MessageService messageService;

    /**
     * 功能描述:物联设备故障统计(报告)
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/25
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data iotDeviceFaultStatistics(String json) {
        IotDeviceReport iotDeviceReport = JSONObject.parseObject(json, IotDeviceReport.class);
        // 日/上周/上月/上年
        String timeChinese = iotDeviceReport.getTimeChinese();
        if ("年".equals(timeChinese)){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            long timeInMillis = c.getTimeInMillis();

            // 上年开始时间
            iotDeviceReport.setCreateTime(DateUtils.getYearStartTime(timeInMillis));
            // 上年结束时间
            iotDeviceReport.setLastModifyTime(DateUtils.getYearEndTime(timeInMillis));
        }else if ("月".equals(timeChinese)){
            // 上个月开始时间
            Date dateAddMonth = DateUtils.getDateAddMonth(new Date(), -1);
            iotDeviceReport.setCreateTime(dateAddMonth);
            // 上个月结束时间
            iotDeviceReport.setLastModifyTime(DateUtils.monthEndDate(dateAddMonth));
        }else if ("周".equals(timeChinese)){
            iotDeviceReport.setCreateTime(DateUtils.getBeginDayOfLastWeek());
            iotDeviceReport.setLastModifyTime(DateUtils.getEndDayOfLastWeek());
        }else if ("季度".equals(timeChinese)){
            timeChinese = "上季度";
            iotDeviceReport.setCreateTime(DateUtils.getStarttQuarter());
            iotDeviceReport.setLastModifyTime(DateUtils.getEndQuarter());
        }else {
            // 日
            Date date = new Date();
            iotDeviceReport.setCreateTime(DateUtils.startDayDate(date));
            iotDeviceReport.setLastModifyTime(date);
        }

        List<IotDeviceReport> iotDeviceReportList = iotDeviceReportDao.iotDeviceFaultStatistics(iotDeviceReport);
        List<String> roleList = new ArrayList<>(1);
        // 报表--物联设备运营消息接收
        roleList.add("iot_device_report_msg_receive");

        List<String> checkMessageRoleList = new ArrayList<>(1);
        // 报表--巡查消息消息接收
        checkMessageRoleList.add("check_device_message_receive");

        JSONObject jsonObject = new JSONObject();

        for (IotDeviceReport entity : iotDeviceReportList) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            if (entity.getTaskTotalCount() > 0){
                MessageModel.taskMessage(stringBuilder, entity, timeChinese);
            }
            if (entity.getFaultRepairTotalCount() > 0){
                MessageModel.faultRepairMessage(stringBuilder, entity, timeChinese);
            }

            if (entity.getFireAlarmTotalCount() > 0){
                MessageModel.fireAlarmMessage(sb, entity, timeChinese);
            }
            if (entity.getVesaTotalCount() > 0){
                MessageModel.vesaMessage(sb, entity, timeChinese);
            }
            if (entity.getWaterGageTotalCount() > 0){
                MessageModel.waterGageMessage(sb, entity, timeChinese);
            }
            if (entity.getWaterLeachingTotalCount() > 0){
                MessageModel.waterLeachingMessage(sb, entity, timeChinese);
            }
            if (entity.getSmokeTotalCount() > 0){
                MessageModel.smokeMessage(sb, entity, timeChinese);
            }
            if (entity.getFireHydrantTotalCount() > 0){
                MessageModel.fireHydrantMessage(sb, entity, timeChinese);
            }
            if (entity.getLinkageTotalCount() > 0){
                MessageModel.linkageMessage(sb, entity, timeChinese);
            }

            Long projectId = entity.getProjectId();
            // 巡查设备运营x报=工单统计+故障报修统计
            if (stringBuilder.length() > 0){
                // 给项目下有xxx权限的人发送消息
                messageService.saveMessageRole(16, checkMessageRoleList, "巡查设备运营 " + timeChinese + " 报告",1,0,
                        entity.getProjectName() + "项目" + stringBuilder.toString() + "<p>详情请前往【巡查设备统计表】中查看。</p>",
                        "url", projectId, null, null, jsonObject);
            }

            /*
             * 项目名称+时间周期+物联设备运行情况：
             * 火警+
             * 极早期+
             * 智能烟感+
             * 水压设备+
             * 水浸设备+
             * 消火栓+
             * 声光设备+
             */
            // 物联设备运营
            if (sb.length() > 0){
                // 给项目下有xxx权限的人发送消息
                messageService.saveMessageRole(16, roleList, "物联设备运营 " + timeChinese + " 报告",2,0,
                        entity.getProjectName() + "项目" + sb.toString() + "<p>详情请前往【物联设备运营报表】查看。</p>",
                        "url", projectId, null, null, jsonObject);
            }
        }
        return asseData(200);
    }

}
