package com.xjt.cloud.iot.core.service.service.sendMessage;

import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireDevice;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;

/**
 *@ClassName SendElectricalFireMsgService
 *@Author dwt
 *@Date 2020-10-13 15:27
 *@Description 电气火灾消息推送接口
 *@Version 1.0
 */
public interface SendElectricalFireMsgService {

    /**
     *@Author: dwt
     *@Date: 2020-10-13 15:30
     *@Param:
     *@Return: void
     *@Description 电气火灾消息推送接口
     */
    void sendFireMessage(ElectricalFireEvent alarmEvent, ElectricalFireDevice device, int type);
}
