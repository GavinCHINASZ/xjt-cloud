package com.xjt.cloud.iot.core.dao.iot.gateway;

import com.xjt.cloud.iot.core.entity.gateway.FirePointTable;

/**
 *@ClassName FirePointTableDao
 *@Author dwt
 *@Date 2019-11-28 18:26
 *@Version 1.0
 */
public interface FirePointTableDao {
    /**
     *@Author: dwt
     *@Date: 2019-11-28 19:01
     *@Param: com.xjt.cloud.iot.core.entity.gateway.FirePointTable
     *@Return: com.xjt.cloud.iot.core.entity.gateway.FirePointTable
     *@Description 根据条件查询火警主机指针表
     */
    FirePointTable findFirePointTable(FirePointTable firePointTable);
    /**
     *@Author: dwt
     *@Date: 2019-11-29 15:59
     *@Param: java.lang.Long
     *@Return: FirePointTable
     *@Description 根据id查询火警主机指针表
     */
    FirePointTable findFirePointTableById(Long id);

}
