package com.xjt.cloud.iot.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备 Controller
 *
 * @author huanggc
 * @date 2020/11/25
 **/
@RestController
@RequestMapping("/device/")
public class DeviceController extends AbstractController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 功能描述:物联设备故障统计
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/11/25
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "iotDeviceFaultStatistics")
    public Data iotDeviceFaultStatistics(String json) {
        return deviceService.iotDeviceFaultStatistics(json);
    }

}
