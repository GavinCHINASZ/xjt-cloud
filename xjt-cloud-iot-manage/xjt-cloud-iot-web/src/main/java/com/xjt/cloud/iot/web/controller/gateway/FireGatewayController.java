package com.xjt.cloud.iot.web.controller.gateway;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.iot.core.service.service.gateway.FireGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FireGatewayController
 * @Author dwt
 * @Date 2019-12-03 16:39
 * @Version 1.0
 */
@RestController
@RequestMapping("/gateway")
public class FireGatewayController extends AbstractController {

    @Autowired
    private FireGatewayService fireGatewayService;
    
    /**
     *@Author: dwt
     *@Date: 2019-12-03 16:43
     *@Param: 
     *@Return: 
     *@Description 定时触发火警主机网关接口读取modbus网关地址
     */
    @RequestMapping(value = "readFireGateway")
    public void readFireGateway(){
        //fireGatewayService.readFireGateway();
    }
}
