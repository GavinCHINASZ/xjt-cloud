package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmRecord;
import org.springframework.stereotype.Repository;

/**
 *@ClassName FireAlarmRecordDao
 *@Author dwt
 *@Date 2019-10-11 15:18
 *@Description 火警主机记录Dao层
 *@Version 1.0
 */
@Repository
public interface FireAlarmRecordDao {
    /**
     *@Author: dwt
     *@Date: 2019-10-21 11:40
     *@Param: FireAlarmRecord
     *@Return: java.lang.Integer
     *@Description 保存火警主机数据返回受影响行数
     */
    Integer saveFireAlarmRecord(FireAlarmRecord fireAlarmRecord);
}
