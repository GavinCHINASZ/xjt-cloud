package com.xjt.cloud.admin.manage.controller.device;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.device.DeviceFaultType;
import com.xjt.cloud.admin.manage.service.service.device.DeviceFaultTypeService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备故障类型管理Controller
 *
 * @author huanggc
 * @date 2020/11/19
 */
@Controller
@RequestMapping("/device/fault/")
public class DeviceFaultTypeController extends AbstractController {
    @Autowired
    private DeviceFaultTypeService deviceFaultTypeService;

    /**
     * 查询 故障类型列表
     *
     * @param ajaxPage AjaxPage
     * @param deviceFaultType DeviceFaultType
     * @return ScriptPage<DeviceFaultType>
     * @author huanggc
     * @date 2020/11/19
     */
    @RequestMapping(value = "findDeviceFaultTypeList")
    @ResponseBody
    public ScriptPage<DeviceFaultType> findDeviceFaultTypeList(AjaxPage ajaxPage, DeviceFaultType deviceFaultType){
        return deviceFaultTypeService.findDeviceFaultTypeList(ajaxPage, deviceFaultType);
    }

    /**
     * 删除 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/19
     */
    @RequestMapping(value = "delDeviceFaultType")
    @ResponseBody
    public Data delDeviceFaultType(DeviceFaultType deviceFaultType){
        return deviceFaultTypeService.updateDeviceFaultType(deviceFaultType);
    }

    /**
     * 更新 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/19
     */
    @RequestMapping(value = "updateDeviceFaultType")
    @ResponseBody
    public Data updateDeviceFaultType(DeviceFaultType deviceFaultType){
        return deviceFaultTypeService.updateDeviceFaultType(deviceFaultType);
    }

    /**
     * 保存 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/19
     */
    @RequestMapping(value = "saveDeviceFaultType")
    @ResponseBody
    public Data saveDeviceFaultType(DeviceFaultType deviceFaultType){
        return deviceFaultTypeService.saveDeviceFaultType(deviceFaultType);
    }
}
