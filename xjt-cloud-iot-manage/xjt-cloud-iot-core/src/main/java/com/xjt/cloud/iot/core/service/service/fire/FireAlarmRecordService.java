package com.xjt.cloud.iot.core.service.service.fire;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 *@ClassName FireAlarmRecordService
 *@Author dwt
 *@Date 2019-10-11 15:30
 *@Description 火警主机记录Service
 *@Version 1.0
 */
public interface FireAlarmRecordService {
    /**
     *@Author: dwt
     *@Date: 2019-10-24 14:28
     *@Param: json
     *@Return: Data
     *@Description 保存事件记录
     */
    List<JSONObject> saveFireAlarmRecord(String json);
}
