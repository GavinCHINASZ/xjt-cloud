package com.xjt.cloud.iot.web.controller.fire;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FireAlarmDeviceController
 * @Author dwt
 * @Date 2019-10-11 15:41
 * @Description 火警主机Controller层
 * @Version 1.0
 */
@RestController
@RequestMapping("/fireAlarm/device")
public class FireAlarmDeviceController extends AbstractController{

    @Autowired
    private FireAlarmDeviceService fireAlarmDeviceService;

    /**
     *@Author: dwt
     *@Date: 2019-10-25 14:02
     *@Param: json
     *@Return: Data
     *@Description 实时监测,火警主机列表查询
     */
    @RequestMapping(value = "findFireAlarmDeviceList/{projectId}")
    public Data findFireAlarmDeviceList(String json){
        return fireAlarmDeviceService.findFireAlarmDeviceList(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-11-21 9:31
     *@Param:
     *@Return: Data
     *@Description 定时任务修改设备状态
     */
    @RequestMapping(value = "modifyDeviceStatus")
    public Data modifyDeviceStatus(){
        return fireAlarmDeviceService.modifyDeviceStatus();
    }
    /**
     *@Author: dwt
     *@Date: 2019-11-21 9:50
     *@Param: json
     *@Return: Data
     *@Description 新增火警主机设备
     */
    @RequestMapping(value = "saveFireAlarmDevice/{projectId}")
    public Data saveFireAlarmDevice(String json){
        return fireAlarmDeviceService.saveFireAlarmDevice(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-11-25 15:50
     *@Param: json
     *@Return: Data
     *@Description 查询该设备是否已存在
     */
    @RequestMapping(value = "findIsSameFireAlarmDevice/{projectId}")
    public Data findIsSameFireAlarmDevice(String json){
        return fireAlarmDeviceService.findIsSameFireAlarmDevice(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-11-27 16:08
     *@Param: json
     *@Return: Data
     *@Description 逻辑删除火警主机设备
     */
    @RequestMapping(value = "deleteFireAlarmDevice/{projectId}")
    public Data deleteFireAlarmDevice(String json){
        return fireAlarmDeviceService.deleteFireAlarmDevice(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-03-26 14:49
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 查询火警主机设备数，离线数
     */
    @RequestMapping(value = "findFireAlarmDeviceNum/{projectId}")
    public Data findFireAlarmDeviceNum(Long projectId){
        return fireAlarmDeviceService.findFireAlarmDeviceNum(projectId);
    }
    /**
     *@Author: dwt
     *@Date: 2020-05-22 10:12
     *@Param: json
     *@Return: Data
     *@Description 查询火警主机事件统计以及在线离线数
     */
    @RequestMapping(value = "findFireAlarmDeviceEventData/{projectId}")
    public Data findFireAlarmDeviceEventData(String json){
        return  fireAlarmDeviceService.findFireAlarmDeviceEventData(json,1);
    }
}
