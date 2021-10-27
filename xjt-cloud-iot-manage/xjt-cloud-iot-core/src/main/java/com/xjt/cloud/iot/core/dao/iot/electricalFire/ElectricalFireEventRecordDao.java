package com.xjt.cloud.iot.core.dao.iot.electricalFire;

import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEventRecord;

import java.util.List;

/**
 *@ClassName ElectricalFireRecordDao
 *@Author dwt
 *@Date 2020-09-15 11:52
 *@Version 1.0
 */
public interface ElectricalFireEventRecordDao {
    /**
     *@Author: dwt
     *@Date: 2020-09-16 11:32
     *@Param: ElectricalFireEventRecord
     *@Return: ElectricalFireEventRecord
     *@Description 查询电气火灾事件记录
     */
    List<ElectricalFireEventRecord> findElectricalFireEventRecord(ElectricalFireEventRecord electricalFireEventRecord);

    /**
     *@Author: dwt
     *@Date: 2020-09-16 11:33
     *@Param: ElectricalFireEventRecord
     *@Return: java.lang.Integer
     *@Description 保存电气火灾事件记录
     */
    Integer saveElectricalFireEventRecord(ElectricalFireEventRecord electricalFireEventRecord);
    /**
     *@Author: dwt
     *@Date: 2020-09-29 14:17
     *@Param: ElectricalFireEventRecord
     *@Return: ElectricalFireEventRecord
     *@Description 查询电气火灾最新的事件记录
     */
    ElectricalFireEventRecord findNewestEventRecord(ElectricalFireEventRecord eventRecord);

    /**
     *@Author: dwt
     *@Date: 2020-09-29 14:26
     *@Param: ElectricalFireEventRecord
     *@Return: ElectricalFireEventRecord
     *@Description 查询电气火灾
     */
    List<ElectricalFireEventRecord> findDeviceEventRecordList(ElectricalFireEventRecord eventRecord);
}
