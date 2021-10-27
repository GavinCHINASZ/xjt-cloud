package com.xjt.cloud.device.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.DeviceFaultTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备故障类型管理Controller
 *
 * @author huanggc
 * @date 2020/11/27
 */
@RestController
@RequestMapping("/fault/type/")
public class DeviceFaultTypeController extends AbstractController {
    @Autowired
    private DeviceFaultTypeService deviceFaultTypeService;

    /**
     * 功能描述:查询设备故障类型信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceFaultTypeList")
    public Data findDeviceFaultTypeList(String json){
        return deviceFaultTypeService.findDeviceFaultTypeList(json);
    }

    /**
     * 功能描述:查询 异常类型(告警类型)
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findFireFaultTypeList/{projectId}")
    public Data findFireFaultTypeList(String json){
        return deviceFaultTypeService.findFireFaultTypeList(json);
    }

}
