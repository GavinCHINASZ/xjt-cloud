package com.xjt.cloud.iot.core.service.service.sendMessage;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;

/**
 *@ClassName SendFireMessage
 *@Author dwt
 *@Date 2019-12-19 9:31
 *@Description 火警主机事件消息推送
 *@Version 1.0
 */
public interface SendFireMessageService {
    /**
     *@Author: dwt
     *@Date: 2019-12-19 9:33
     *@Param:
     *@Return: void
     *@Description 火警主机事件消息推送接口
     */
    void sendFireMessage(FireAlarmEvent fireAlarmEvent, int type);
    /**
     *@Author: dwt
     *@Date: 2019-12-19 17:00
     *@Param: int
     *@Return: java.lang.Integer
     *@Description 返回消息事件类型
     */
    Integer recoverEventStatus(int eventType,int type);
}
