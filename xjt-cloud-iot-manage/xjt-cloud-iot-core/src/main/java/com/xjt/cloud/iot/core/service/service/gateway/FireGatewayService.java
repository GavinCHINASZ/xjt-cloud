package com.xjt.cloud.iot.core.service.service.gateway;

import com.serotonin.modbus4j.ip.listener.TcpMultiListener;
import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName FireGatewayService
 *@Author dwt
 *@Date 2019-11-29 9:05
 *@Version 1.0
 */
public interface FireGatewayService {
    /**
     *@Author: dwt
     *@Date: 2019-11-29 9:21
     *@Param:
     *@Return: Data
     *@Description 读取网关列表
     */
    Data readFireGateway(TcpMultiListener listener);
    /**
     *@Author: dwt
     *@Date: 2019-11-29 16:13
     *@Param:
     *@Return:
     *@Description 添加事物
     */
    //void transactionSaveFireGatewayEvent();
}
