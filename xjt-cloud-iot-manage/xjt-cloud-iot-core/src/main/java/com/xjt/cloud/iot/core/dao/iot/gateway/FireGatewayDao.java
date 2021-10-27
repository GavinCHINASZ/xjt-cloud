package com.xjt.cloud.iot.core.dao.iot.gateway;

import com.xjt.cloud.iot.core.entity.gateway.FireGateway;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName FireGatewayDao
 *@Author dwt
 *@Date 2019-11-28 17:53
 *@Version 1.0
 */
@Repository
public interface FireGatewayDao {
    /**
     *@Author: dwt
     *@Date: 2019-11-28 18:59
     *@Param: java.lang.String
     *@Return: java.util.List
     *@Description 查询火警主机网关列表
     */
    List<FireGateway> findFireGatewayList(@Param("gateway") String gateway);
    /**
     *@Author: dwt
     *@Date: 2019-11-28 19:00
     *@Param:
     *@Return: java.util.List
     *@Description 查询网关列表
     */
    List<String> findGatewayList();
}
