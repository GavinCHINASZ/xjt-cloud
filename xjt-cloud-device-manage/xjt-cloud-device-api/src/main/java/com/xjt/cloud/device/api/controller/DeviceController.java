package com.xjt.cloud.device.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:24
 * @Description:设备管理
 */
@RestController
@RequestMapping("/device/")
public class DeviceController extends AbstractController {
    @Autowired
    private DeviceService deviceService;

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceList/{projectId}")
    public Data findDeviceList(String json){
        return deviceService.findDeviceList(json);
    }

    /**
     *
     * 功能描述:查询设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDevice/{projectId}")
    public Data findDevice(String json){
        return deviceService.findDevice(json);
    }

    /**
     *
     * 功能描述:批量新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "saveDevices/{projectId}")
    public Data saveDevices(String json){
        return deviceService.saveDevices(json);
    }

    /**
     *
     * 功能描述:修改设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "modifyDevice/{projectId}")
    public Data modifyDevice(String json){
        return deviceService.modifyDevice(json);
    }

    /**
     *
     * 功能描述:巡检点批量绑定设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @RequestMapping(value = "pointBindDevices")
    public Data pointBindDevices(String json){
        return deviceService.pointBindDevices(json);
    }

    /**
     *
     * 功能描述:巡检点批量清除设备绑定
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @RequestMapping(value = "pointClearBindDevices")
    public Data pointClearBindDevices(String json){
        return deviceService.pointClearBindDevices(json);
    }

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @RequestMapping(value = "findDeviceRecordList/{projectId}")
    public Data findDeviceRecordList(String json){
        return deviceService.findDeviceRecordList(json);
    }

    /**
     *
     * 功能描述:查询设备档案列表信息汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @RequestMapping(value = "findDeviceRecordCount/{projectId}")
    public Data findDeviceRecordCount(String json){
        return deviceService.findDeviceRecordCount(json);
    }

    /**
     *
     * 功能描述:以巡检点id查询设备档案基本信息总数
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @RequestMapping(value = "findDeviceRecordCountByPoint/{projectId}")
    public Data findDeviceRecordCountByPoint(String json){
        return deviceService.findDeviceRecordCountByPoint(json);
    }

    /**
     *
     * 功能描述:查询未关联设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceListNotBoundIot/{projectId}")
    public Data findDeviceListNotBoundIot(String json){
        return deviceService.findDeviceListNotBoundIot(json);
    }

    /**
     *
     * 功能描述:查询关联设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceListBoundIot/{projectId}")
    public Data findDeviceListBoundIot(String json){
        return deviceService.findDeviceListBoundIot(json);
    }
}
