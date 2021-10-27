package com.xjt.cloud.iot.core.service.service.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice;

/**
 *@ClassName FireAlarmDeviceService
 *@Author dwt
 *@Date 2019-10-11 15:29
 *@Description 火警主机Service层
 *@Version 1.0
 */

public interface FireAlarmDeviceService {
    /**
     *@Author: dwt
     *@Date: 2019-10-21 13:50
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询火警主机设备列表
     */
    Data findFireAlarmDeviceList(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-20 15:40
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 保存火警主机设备
     */
    Data saveFireAlarmDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-20 15:47
     *@Param: java.lang.Long,Integer
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 修改火机主机设备在线离线状态
     */
    Data modifyDeviceStatus();
    /**
     *@Author: dwt
     *@Date: 2019-11-25 15:34
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询是否有相同的火警主机设备
     */
    Data findIsSameFireAlarmDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-26 16:52
     *@Param: java.lang.String
     *@Return: JSONObject
     *@Description 判断注册码是否存在
     */
    JSONObject isSensorPresence(String transDeviceId);
    /**
     *@Author: dwt
     *@Date: 2019-11-27 16:04
     *@Param: json
     *@Return: Data
     *@Description 逻辑删除火警主机设备
     */
    Data deleteFireAlarmDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-27 16:04
     *@Param: json
     *@Return: Data
     *@Description 逻辑删除火警主机设备添加事物
     */
    //Data transactionDeleteFireAlarmDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-27 16:04
     *@Param: json
     *@Return: Data
     *@Description 保存火警主机设备添加事物
     */
    //Data transactionSaveFireAlarmDevice(String json);
    /**
     *@Author: daiwt
     *@Date: 2020-03-04 14:59
     *@Param: json
     *@Return: Data
     *@Description APP端火警主机设备信息
     */
    Data findFireAlarmDeviceInfoApp(String json);
    /**
     *@Author: dwt
     *@Date: 2020-03-26 14:44
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 查询火警主机设备总数，在线数，离线数
     */
    Data findFireAlarmDeviceNum(Long projectId);
    /**
     *@Author: dwt
     *@Date: 2020-05-22 10:10
     *@Param: json
     *@Return: Data
     *@Description 查询设备事件统计信息
     */
    Data findFireAlarmDeviceEventData(String json,int type);
    /**
     * @Description 查询app首页火警主机信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 15:04
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectFireAlarmData(String json);

    /**
     *@Author: dwt
     *@Date: 2020-10-21 9:19
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice
     *@Description 根据传感器ID获取设备
     */
    FireAlarmDevice getFireAlarmDeviceByRedis(String sensor,String fireAlarmNo);
}
