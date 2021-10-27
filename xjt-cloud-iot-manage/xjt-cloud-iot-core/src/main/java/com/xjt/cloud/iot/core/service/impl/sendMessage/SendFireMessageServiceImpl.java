package com.xjt.cloud.iot.core.service.impl.sendMessage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendFireMessageService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SendFireMessageServiceImpl
 * @Author dwt
 * @Date 2019-12-19 9:35
 * 火警主机消息推送
 * @Version 1.0
 */
@Service
public class SendFireMessageServiceImpl extends AbstractService implements SendFireMessageService {
    @Autowired
    private MessageService messageService;
    
    private static List<String> signList;

    static {
        if (signList == null) {
            signList = new ArrayList<>();
        }
    }

    /**
     * 火警主机消息推送
     * 
     * @author dwt
     * @date 2019-12-19 11:52
     * @param fireAlarmEvent FireAlarmEvent
     * @param type int
     */
    @Override
    public void sendFireMessage(FireAlarmEvent fireAlarmEvent, int type) {
        signList.clear();
        Long projectId = fireAlarmEvent.getProjectId();
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        Date date = new Date();
        String timeStr = DateUtils.getDateTimeString(date);
        String position = fireAlarmEvent.getAlarmPosition();
        String alarmDeviceName = fireAlarmEvent.getAlarmDeviceName();
        if (StringUtils.isEmpty(position)) {
            position = "";
        }
        if (StringUtils.isEmpty(alarmDeviceName)) {
            alarmDeviceName = "";
        }
        String timePositionDevice = timeStr + " " + position + " " + alarmDeviceName;
        if (type == 7 || type == 8) {
            String fireAlarmNo = "";
            if (StringUtils.isNotEmpty(fireAlarmEvent.getFireAlarmNo())) {
                fireAlarmNo = " " + fireAlarmEvent.getFireAlarmNo() + "#";
            }
            timePositionDevice = timeStr + " " + fireAlarmEvent.getTransDeviceName() + fireAlarmNo;
        }
        String content = "【" + projectName + "】" + timePositionDevice;
        String title = "未知事件通知";
        String url = "";
        String event = "";
        ////消息字体显示颜色 0、灰 1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫
        int fontColor = 0;
        //1:火警通知，2:火警恢复通知，3:故障通知，4:故障恢复通知，5:监视通知，6:监视恢复通知，7:离线通知，8:在线恢复通知
        //'事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，
        // 7-故障事件，8-系统事件，（9-例行检查），10-其他 ，11-屏蔽，12-启动，13-延时状态，
        // 14-主电故障，15-备电故障，16-总线故障，17-系统复位，20-传输装置复位，21-离线事件',22-在线恢复,23-监视恢复事件
        //24-火警恢复事件
        Long id = fireAlarmEvent.getId();
        switch (type) {
            case 1:
                content = content + "发生火警事件";
                title = "火警通知";
                event = "火警事件";
                fontColor = 1;
                signList.add("fireAlarm_fire_event");
                break;
            case 2:
                content = content + "火警事件已恢复";
                title = "火警恢复通知";
                event = "火警恢复事件";
                url = "";
                fontColor = 1;
                fireAlarmEvent.setEventType(24);
                signList.add("fireAlarm_fire_event");
                break;
            case 3:
                content = content + "发生故障事件";
                title = "故障通知";
                event = "故障事件";
                fontColor = 2;
                signList.add("fireAlarm_fault_event");
                break;
            case 4:
                content = content + "故障事件已恢复";
                title = "故障恢复通知";
                event = "故障恢复事件";
                fontColor = 2;
                fireAlarmEvent.setEventType(6);
                signList.add("fireAlarm_fault_event");
                break;
            case 5:
                content = content + "发生监视事件";
                title = "监视通知";
                event = "监视事件";
                fontColor = 6;
                signList.add("fireAlarm_monitor_event");
                break;
            case 6:
                content = content + "监视事件已恢复";
                title = "监视恢复通知";
                event = "监视恢复事件";
                fontColor = 6;
                fireAlarmEvent.setEventType(23);
                signList.add("fireAlarm_monitor_event");
                break;
            case 7:
                content = content + " 已离线";
                title = "离线通知";
                event = "离线事件";
                fontColor = 0;
                id = null;
                signList.add("fireAlarm_offline_event");
                break;
            case 8:
                content = content + " 已恢复在线";
                title = "在线通知";
                event = "离线已恢复";
                fontColor = 0;
                fireAlarmEvent.setEventType(22);
                signList.add("fireAlarm_offline_event");
                break;
        }
        content = content + "，请前往【火警主机】查看详情。";

        JSONObject json = new JSONObject(10);
        json.put("projectName", projectName);
        json.put("deviceLocation", fireAlarmEvent.getAlarmPosition());
        json.put("deviceName", fireAlarmEvent.getAlarmDeviceName());
        if (type == 7 || type == 8) {
            json.put("deviceName", fireAlarmEvent.getTransDeviceName());
        }
        json.put("date", DateUtils.getDateTimeString(date));
        json.put("event", event);
        json.put("buildingId", fireAlarmEvent.getBuildingId());
        json.put("buildingName", fireAlarmEvent.getBuildingName());
        json.put("floorName", fireAlarmEvent.getFloorName());
        json.put("qrNo", fireAlarmEvent.getQrNo());
        messageService.saveMessageRole(-1, signList, title, fireAlarmEvent.getEventType(), fontColor, content, url, projectId, id, null, json);
    }

    /**
     * 返回消息事件类型
     *
     * @author dwt
     * @date 2019-12-19 17:00
     * @param type int
     * @return java.lang.Integer
     */
    @Override
    public Integer recoverEventStatus(int messageType, int type) {
        // 1:恢复消息类型
        if (type == 1) {
            switch (messageType) {
                case 1:
                    messageType = 2;
                    break;
                case 2:
                    messageType = 6;
                    break;
                case 7:
                    messageType = 4;
                    break;
            }
            return messageType;
        } else if (type == 2) {
            // 2：异常消息类型
            switch (messageType) {
                case 2:
                    messageType = 5;
                    break;
                case 7:
                    messageType = 3;
                    break;
            }
        }
        return messageType;
    }
}