package com.xjt.cloud.iot.core.service.service.inter;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName InterService
 *@Author dwt
 *@Date 2020-07-15 15:00
 *@Description 因特需求
 *@Version 1.0
 */
public interface InterService {
    /**
     *@Author: dwt
     *@Date: 2020-07-15 16:13
     *@Param: json
     *@Return: Data
     *@Description 因特事件统计报表接口
     */
    Data findInterAreaTypeEventCount(String json);
    /**
     *@Author: dwt
     *@Date: 2020-07-16 11:05
     *@Param: json
     *@Return: Data
     *@Description 查询火警主机和极早期当天五点前的所有活跃事件（未恢复事件）
     */
    Data findFireAlarmAndVesaEvent(String json);
}
