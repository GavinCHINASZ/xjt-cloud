package com.xjt.cloud.project.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.device.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:51
 * @Description: 设备系统与类型管理Controller
 */
@RestController
@RequestMapping("/device/type/")
public class DeviceTypeController extends AbstractController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     *
     * 功能描述:查询设备系统信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "findParentDeviceTypeList")
    public Data findParentDeviceTypeList(String json){
        return deviceTypeService.findParentDeviceTypeList(json);
    }

    /**
     *
     * 功能描述:查询设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "findParentDeviceType")
    public Data findParentDeviceType(String json){
        return deviceTypeService.findParentDeviceType(json);
    }

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "findDeviceTypeList")
    public Data findDeviceTypeList(String json){
        return deviceTypeService.findDeviceTypeList(json);
    }

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "findDeviceTypeListByCheckPoint")
    public Data findDeviceTypeListByCheckPoint(String json){
        return deviceTypeService.findDeviceTypeListByCheckPoint(json);
    }

    /**
     *
     * 功能描述:查询设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @RequestMapping(value = "findDeviceType")
    public Data findDeviceType(String json){
        return deviceTypeService.findDeviceType(json);
    }

    /**
     *
     * 功能描述:查询设备系统树
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    @RequestMapping(value = "findDeviceSysTree")
    public Data findDeviceSysTree(String json){
        return deviceTypeService.findDeviceSysTree(json);
    }


    /**
     *
     * 功能描述:设备系统缓存初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:51
     */
    @RequestMapping(value = "deviceSysCacheInit")
    public void deviceSysCacheInit(){
        deviceTypeService.deviceSysCacheInit();
    }
}
