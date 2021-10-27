package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmAreaType;

/**
 *@ClassName FireAlarmAreaTypeDao
 *@Author dwt
 *@Date 2020-07-15 13:42
 *@Version 1.0
 */
public interface FireAlarmAreaTypeDao {
    FireAlarmAreaType findAreaTypeByLoopAndProjectId(FireAlarmAreaType fireAlarmAreaType);
}
