package com.xjt.cloud.iot.api.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordService;
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
    @Autowired
    private VesaDeviceRecordService vesaDeviceRecordService;

    /**
     *
     * 功能描述:查询极早期设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "findVesaDeviceListApp/{projectId}")
    public Data findVesaDeviceListApp(String json){
        return vesaDeviceService.findVesaDeviceListApp(json);
    }

    /**
     * @Description 查询app首页极早期信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectVesaData/{projectId}")
    public JSONObject findUserProjectVesaData(String json){
        return vesaDeviceRecordService.findUserProjectVesaData(json);
    }

}
