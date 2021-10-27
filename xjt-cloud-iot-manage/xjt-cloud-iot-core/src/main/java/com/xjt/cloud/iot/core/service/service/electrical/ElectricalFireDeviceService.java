package com.xjt.cloud.iot.core.service.service.electrical;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 *@ClassName ElectricalFireService
 *@Author dwt
 *@Date 2020-09-11 13:52
 *@Version 1.0
 */
public interface ElectricalFireDeviceService {

    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:53
     *@Param: json
     *@Return: Data
     *@Description 根据条件查询电气火灾设备列表
     */
    Data findElectricalFireDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:56
     *@Param: json
     *@Return: Data
     *@Description 保存电气火灾设备信息
     */
    Data saveElectricalFireDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-16 14:29
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询是否有相同的注册id
     */
    Data findSameElectricalDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-18 9:52
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 解绑电气火灾设备
     */
    Data deleteElectricalDevice(String json);

    /**
     *@Author: dwt
     *@Date: 2020-09-21 11:39
     *@Param: json
     *@Return: Data
     *@Description 电气火灾设备和事件概览
     */
    Data deviceAndEventGeneralView(String json);
    /**
     *@Author: dwt
     *@Date: 2020-09-23 14:11
     *@Param: java.lang.String, javax.servlet.http.HttpServletResponse
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 导出电气火灾设备列表
     */
    void downLoadElectricalFireDeviceList(String json, HttpServletResponse resp);
    /**
     *@Author: dwt
     *@Date: 2020-10-13 14:59
     *@Param:
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 修改电气火灾设备在线离线状态
     */
    Data modifyDeviceStatus();

}
