package com.xjt.cloud.iot.web.controller.vesa;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/20 15:07
 * @Description: 极早期设备管理
 */
@RestController
@RequestMapping("/vesa/device/")
public class VesaDeviceController extends AbstractController {

    @Autowired
    private VesaDeviceService vesaDeviceService;

    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "checkVesaDeviceSensorNo/{projectId}")
    public Data checkVesaDeviceSensorNo(String json){
        return vesaDeviceService.checkVesaDeviceSensorNo(json);
    }

    /**
     *
     * 功能描述:保存极早期设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "saveVesaDevice/{projectId}")
    public Data saveVesaDevice(String json){
        return vesaDeviceService.saveVesaDevice(json);
    }

    /**
     *
     * 功能描述:修改极早期设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "modifyVesaDevice/{projectId}")
    public Data modifyVesaDevice(String json){
        return vesaDeviceService.modifyVesaDevice(json);
    }

    /**
     *
     * 功能描述:删除极早期设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "delVesaDevice/{projectId}")
    public Data delVesaDevice(String json){
        return vesaDeviceService.delVesaDevice(json);
    }

    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "findVesaDeviceList/{projectId}")
    public Data findVesaDeviceList(String json){
        return vesaDeviceService.findVesaDeviceList(json);
    }

    /**
     *
     * 功能描述:查询极早期设备汇总报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findVesaDeviceSummaryReport/{projectId}")
    public Data findVesaDeviceSummaryReport(String json){
        return vesaDeviceService.findVesaDeviceSummaryReport(json);
    }

    /**
     *
     * 功能描述:极早期设备离线任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    @RequestMapping(value = "vesaOffLineTask")
    public Data vesaOffLineTask(){
        return vesaDeviceService.vesaOffLineTask();
    }

}
