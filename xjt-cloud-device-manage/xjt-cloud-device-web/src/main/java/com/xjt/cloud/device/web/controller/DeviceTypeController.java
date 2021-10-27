package com.xjt.cloud.device.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
    public Data deviceSysCacheInit(){
        return deviceTypeService.deviceSysCacheInit();
    }

    /**
     * 功能描述: 查询 设备类型(地铁)
     *
     * @param
     * @auther huanggc
     * @date 2020-04-27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceTypeMetroList/{projectId}")
    public Data findDeviceTypeMetroList(String json, HttpServletResponse response){
        return deviceTypeService.findDeviceTypeMetroList(json, response);
    }

    /**
     * @Description 以部门id与楼层查询设备类型列表
     *
     * @author wangzhiwen
     * @date 2021/3/10 15:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceTypeListByOrgFloor/{projectId}")
    public Data findDeviceTypeListByOrgFloor(String json){
        return deviceTypeService.findDeviceTypeListByOrgFloor(json);
    }
}
