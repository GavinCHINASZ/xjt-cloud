package com.xjt.cloud.iot.core.service.impl.sendMessage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireDevice;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendElectricalFireMsgService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SendElectricalFireMsgServiceImpl
 * 电气火灾消息推送接口
 * 
 * @author dwt
 * @date 2020-10-13 15:28
 */
@Service
public class SendElectricalFireMsgServiceImpl extends AbstractService implements SendElectricalFireMsgService {
    @Autowired
    private MessageService messageService;

    /**
     * 发送 电气火灾消息
     *
     * @param alarmEvent ElectricalFireEvent
     * @param device ElectricalFireDevice
     * @param recoverEvent int
     */
    @Override
    public void sendFireMessage(ElectricalFireEvent alarmEvent, ElectricalFireDevice device, int recoverEvent) {
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, device.getProjectId(), "projectName");
        Date date = new Date();
        String timeStr = DateUtils.getDateTimeString(date);
        String location = device.getDeviceLocation();
        String deviceName = device.getDeviceName();

        String content = "【" + projectName + "】" + timeStr + " " + location + " " + deviceName;
        String title = "未知事件通知";
        String url = "";
        String event = "";
        List<String> signList = new ArrayList<>();
        //消息字体显示颜色 0、灰 1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫
        int fontColor = 0;
        //事件类型 1漏电流报警，2温度报警，3故障，4离线
        int eventType;
        if (recoverEvent == 0) {
            eventType = alarmEvent.getEventType();
        } else {
            eventType = recoverEvent;
        }
        int faultType = alarmEvent.getFaultType();
        Long id = alarmEvent.getId();
        //故障类型 1漏电流故障，2温度故障
        switch (eventType) {
            case 1:
                content = content + "发生漏电流报警事件";
                title = "漏电流报警通知";
                event = "漏电流报警事件";
                fontColor = 1;
                signList.add("electrical_leakage_event");
                break;
            case 2:
                content = content + "发生温度报警事件";
                title = "温度报警通知";
                event = "温度报警事件";
                fontColor = 1;
                signList.add("electrical_temp_event");
                break;
            case 3:
                if (faultType == 1) {
                    content = content + "发生漏电流传感器故障事件";
                    signList.add("electrical_leakage_fault_event");
                } else {
                    content = content + "发生温度传感器故障事件";
                    signList.add("electrical_temp_fault_event");
                }
                title = "故障通知";
                event = "故障事件";
                fontColor = 2;
                break;
            case 4:
                content = content + "已离线";
                title = "离线通知";
                event = "离线事件";
                fontColor = 0;
                signList.add("electrical_offLine_event");
                id = null;
                break;
            case 5:
                content = content + "漏电流故障已恢复";
                title = "故障恢复通知";
                event = "故障恢复事件";
                fontColor = 0;
                signList.add("electrical_recover_leakage_event");
                break;
            case 6:
                content = content + "温度故障已恢复";
                title = "故障恢复通知";
                event = "故障恢复事件";
                fontColor = 0;
                signList.add("electrical_recover_temp_event");
                break;
            case 7:
                content = content + " 已恢复在线";
                title = "在线通知";
                event = "离线已恢复";
                fontColor = 4;
                signList.add("electrical_onLine_event");
                break;
        }

        content = content + "，请前往【电气火灾】查看详情。";
        JSONObject json = new JSONObject(9);
        json.put("projectName", projectName);
        json.put("deviceLocation", device.getDeviceLocation());
        json.put("deviceName", device.getDeviceName());
        json.put("date", timeStr);
        json.put("event", event);
        json.put("buildingId", device.getBuildingId());
        json.put("buildingName", device.getBuildingName());
        json.put("floorName", device.getFloorName());
        json.put("qrNo", device.getPointQrNo());
        messageService.saveMessageRole(10, signList, title, eventType, fontColor, content, url, device.getProjectId(), id, null, json);
    }
}
