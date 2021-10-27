package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TaskDeviceController
 * @Author dwt
 * @Date 2019-08-15 14:57
 * @Description 任务设备controller层
 * @Version 1.0
 */
@RestController
@RequestMapping("/taskDevice")
public class TaskDeviceController extends AbstractController {

    @Autowired
    private DeviceService deviceService;

    /**
     *@Author: dwt
     *@Date: 2019-08-15 15:30
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据任务id查询设备信息列表
     */
    @RequestMapping(value = "findTaskDeviceListApp/{projectId}")
    public Data findTaskDeviceListApp(String json){
        return deviceService.findAppTaskDeviceListByTaskId(json);
    }

}
