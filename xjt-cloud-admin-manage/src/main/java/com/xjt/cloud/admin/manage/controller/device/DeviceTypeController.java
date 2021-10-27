package com.xjt.cloud.admin.manage.controller.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.Device;
import com.xjt.cloud.admin.manage.entity.device.DeviceType;
import com.xjt.cloud.admin.manage.service.service.device.DeviceTypeService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/3 11:09
 * @Description:　 设备类型管理Controller
 */
@Controller
@RequestMapping("/device/sys/")
public class DeviceTypeController extends AbstractController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    /**
     *
     * 功能描述: 打开设备系统管理页面
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:21
     */
    @RequestMapping("toDeviceSysListPage")
    public ModelAndView toDeviceSysListPage(){
        return new ModelAndView("device/deviceSysList");
    }

    /**
     *
     * 功能描述:  查询设备系统列表
     *
     * @param ajaxPage
     * @param deviceType
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @RequestMapping(value = "findDeviceSysList")
    @ResponseBody
    public ScriptPage<DeviceType> findDeviceSysList(AjaxPage ajaxPage, DeviceType deviceType){
        return deviceTypeService.findDeviceSysList(ajaxPage,deviceType);
    }

    /**
     *
     * 功能描述:新增设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "saveDeviceSys")
    @ResponseBody
    public Data saveDeviceSys(DeviceType deviceType){
        return deviceTypeService.saveDeviceSys(deviceType);
    }

    /**
     *
     * 功能描述:修改设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "modifyDeviceSys")
    @ResponseBody
    public Data modifyDeviceSys(DeviceType deviceType){
        return deviceTypeService.modifyDeviceSys(deviceType);
    }

    /**
     *
     * 功能描述:删除设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "delDeviceSys")
    @ResponseBody
    public Data delDeviceSys(DeviceType deviceType){
        return deviceTypeService.modifyDeviceSys(deviceType);
    }

    ////////////////////////////////////////　设备类型管理　///////////////////////////////////
    /**
     *
     * 功能描述:  查询设备系统类型列表
     *
     * @param ajaxPage
     * @param deviceType
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @RequestMapping(value = "findDeviceTypeList")
    @ResponseBody
    public ScriptPage<DeviceType> findDeviceTypeList(AjaxPage ajaxPage, DeviceType deviceType){
        return deviceTypeService.findDeviceTypeList(ajaxPage,deviceType);
    }

    /**
     *
     * 功能描述:新增设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "saveDeviceType")
    @ResponseBody
    public Data saveDeviceType(DeviceType deviceType){
        return deviceTypeService.saveDeviceType(deviceType);
    }

    /**
     *
     * 功能描述:修改设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "modifyDeviceType")
    @ResponseBody
    public Data modifyDeviceType(DeviceType deviceType){
        return deviceTypeService.modifyDeviceType(deviceType);
    }

    /**
     *
     * 功能描述:删除设备系统类型
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "delDeviceType")
    @ResponseBody
    public Data delDeviceType(DeviceType deviceType){
        return deviceTypeService.modifyDeviceType(deviceType);
    }

    /**
     * @Description 查询项目设备类型列表
     *
     * @param deviceType
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     */
    @RequestMapping(value = "findProjectDeviceTypeList")
    @ResponseBody
    public List<DeviceType> findProjectDeviceTypeList(DeviceType deviceType){
        return deviceTypeService.findProjectDeviceTypeList(deviceType);
    }

    /**
     * @Description 查询项目巡检点列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    @RequestMapping(value = "findProjectCheckPointList")
    @ResponseBody
    public List<CheckPoint> findProjectCheckPointList(CheckPoint checkPoint){
        return deviceTypeService.findProjectCheckPointList(checkPoint);
    }

    /**
     * @Description 查询项目设备列表
     *
     * @param device
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.Device>
     */
    @RequestMapping(value = "findProjectDeviceList")
    @ResponseBody
    public List<Device> findProjectDeviceList(Device device){
        return deviceTypeService.findProjectDeviceList(device);
    }
}
