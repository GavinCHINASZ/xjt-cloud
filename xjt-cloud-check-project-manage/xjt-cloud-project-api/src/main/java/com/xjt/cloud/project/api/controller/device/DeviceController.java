package com.xjt.cloud.project.api.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName DeviceController
 * @Author dwt
 * @Date 2020-04-10 19:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends AbstractController {

    @Autowired
    private DeviceService deviceService;
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:12
     *@Param: Device
     *@Return: Data
     *@Description 查询设备列表
     */
    @RequestMapping(value = "/findDeviceList/{projectId}")
    public Data findDeviceList(String json){
        return deviceService.findDeviceList(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-10 17:15
     *@Param: Device
     *@Return: Data
     *@Description 保存设备
     */
    @RequestMapping(value = "/saveDevice/{projectId}")
    public Data saveDevice(String json){
        return deviceService.saveDevice(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:00
     *@Param: json,file
     *@Return: Data
     *@Description 设备登记导入
     */
    @RequestMapping(value = "/uploadDeviceList/{projectId}")
    public Data uploadDeviceList(String json, MultipartFile file){
        return deviceService.uploadDeviceList(json, file);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-11 17:56
     *@Param: json
     *@Return: Data
     *@Description 逻辑删除
     */
    @RequestMapping(value = "/deleteDevice/{projectId}")
    public Data deleteDevice(String json){
        return deviceService.deleteDevice(json);
    }
}
