package com.xjt.cloud.iot.api.smoke;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * SmokeDeviceController 烟感设备 控制类
 *
 * @author huanggc
 * @date 2020/07/15
 * @version  1.0
 */
@RestController
@RequestMapping("/smoke/device/")
public class SmokeDeviceController extends AbstractController{
    @Autowired
    private SmokeDeviceService smokeDeviceService;

    /**
     * 功能描述 查询烟感设备列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeDeviceList/{projectId}")
    public Data findSmokeDeviceList(String json){
        return smokeDeviceService.findSmokeDeviceList(json);
    }

    /**
     * 功能描述 查询烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeDevice/{projectId}")
    public Data findSmokeDevice(String json){
        return smokeDeviceService.findSmokeDeviceList(json);
    }

    /**
     * 功能描述: 增加 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveSmokeDevice/{projectId}")
    public Data saveSmokeDevice(String json){
        return smokeDeviceService.saveSmokeDevice(json);
    }

    /**
     * 功能描述: 更新 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateSmokeDevice/{projectId}")
    public Data updateSmokeDevice(String json){
        return smokeDeviceService.updateSmokeDevice(json);
    }

    /**
     * 功能描述: 删除(解绑) 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deletedSmokeDevice/{projectId}")
    public Data deletedSmokeDevice(String json){
        return smokeDeviceService.deletedSmokeDevice(json);
    }

    /**
     * 功能描述: 烟感设备汇总报表 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSomkeDeviceSummaryReport/{projectId}")
    public Data findSomkeDeviceSummaryReport(String json){
        return smokeDeviceService.findSomkeDeviceSummaryReport(json);
    }

    /**
     * @Description 查询app首页智能烟感信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectSmokeData/{projectId}")
    public JSONObject findUserProjectSmokeData(String json){
        return smokeDeviceService.findUserProjectSmokeData(json);
    }

    /**
     * 功能描述: 查询支持的平台
     *
     * @auther huanggc
     * @date 2020/08/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeCloud")
    public Data findSmokeCloud(){
        return smokeDeviceService.findSmokeCloud();
    }
}
