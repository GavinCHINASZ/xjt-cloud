package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmCode;
import org.springframework.stereotype.Repository;

/**
 *@ClassName FireAlarmCodeDao
 *@Author dwt
 *@Date 2019-10-25 10:50
 *@Version 1.0
 */
@Repository
public interface FireAlarmCodeDao {
    /**
     *@Author: dwt
     *@Date: 2019-10-25 10:52
     *@Param: FireAlarmCode
     *@Return: FireAlarmCode
     *@Description 根据条件查询火警主机编码表
     */
    FireAlarmCode findFireAlarmCode(FireAlarmCode fireAlarmCode);
}
