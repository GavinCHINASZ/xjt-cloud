package com.xjt.cloud.iot.api.electrical;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ElectricalFireDeviceController
 * @Author dwt
 * @Date 2020-09-21 9:56
 * @Description 电气火灾设备控制层
 * @Version 1.0
 */
@RestController
@RequestMapping("/electrical/device")
public class ElectricalFireDeviceController extends AbstractController {

    @Autowired
    private ElectricalFireDeviceService electricalFireDeviceService;


    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:53
     *@Param: json
     *@Return: Data
     *@Description 根据条件查询电气火灾设备列表
     */
    @RequestMapping(value = "findElectricalFireDevice/{projectId}")
    public Data findElectricalFireDevice(String json) {
        return electricalFireDeviceService.findElectricalFireDevice(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-11 13:56
     *@Param: json
     *@Return: Data
     *@Description 保存电气火灾设备信息
     */
    @RequestMapping(value = "saveElectricalFireDevice/{projectId}")
    public Data saveElectricalFireDevice(String json) {
        return electricalFireDeviceService.saveElectricalFireDevice(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-16 14:29
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 查询是否有相同的注册id
     */
    @RequestMapping(value = "findSameElectricalDevice/{projectId}")
    public Data findSameElectricalDevice(String json) {
        return electricalFireDeviceService.findSameElectricalDevice(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-18 10:07
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 解绑电气火灾设备
     */
    @RequestMapping(value = "deleteElectricalDevice/{projectId}")
    public Data deleteElectricalDevice(String json) {
        return electricalFireDeviceService.deleteElectricalDevice(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-21 11:39
     *@Param: json
     *@Return: Data
     *@Description 电气火灾设备和事件概览
     */
    @RequestMapping(value = "deviceAndEventGeneralView/{projectId}")
    public Data deviceAndEventGeneralView(String json){
        return electricalFireDeviceService.deviceAndEventGeneralView(json);
    }
}
