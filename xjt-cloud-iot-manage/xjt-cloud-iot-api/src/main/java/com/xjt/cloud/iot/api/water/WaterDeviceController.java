package com.xjt.cloud.iot.api.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/20 15:07
 * @Description: 水压设备管理
 */
@RestController
@RequestMapping("/water/device/")
public class WaterDeviceController extends AbstractController {

    @Autowired
    private WaterDeviceService waterDeviceService;

    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "checkWaterDeviceSensorNo/{projectId}")
    public Data checkWaterDeviceSensorNo(String json){
        return waterDeviceService.checkWaterDeviceSensorNo(json);
    }

    /**
     *
     * 功能描述:保存水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "saveWaterDevice/{projectId}")
    public Data saveWaterDevice(String json){
        return waterDeviceService.saveWaterDevice(json);
    }

    /**
     *
     * 功能描述:修改水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "modifyWaterDevice/{projectId}")
    public Data modifyWaterDevice(String json){
        return waterDeviceService.modifyWaterDevice(json);
    }

    /**
     *
     * 功能描述:删除水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "delWaterDevice/{projectId}")
    public Data delWaterDevice(String json){
        return waterDeviceService.delWaterDevice(json);
    }

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "findWaterDeviceList/{projectId}")
    public Data findWaterDeviceList(String json){
        return waterDeviceService.findWaterDeviceList(json);
    }

    /**
     *
     * 功能描述:查询水压设备修改记录信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    @RequestMapping(value = "findWaterDeviceEditList/{projectId}")
    public Data findWaterDeviceEditList(String json){
        return waterDeviceService.findWaterDeviceEditList(json);
    }
    /**
     *
     * 功能描述:查询水压设备汇总报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findWaterDeviceSummaryReport/{projectId}")
    public Data findWaterDeviceSummaryReport(String json){
        return waterDeviceService.findWaterDeviceSummaryReport(json);
    }

    /**
     *
     * 功能描述:水压设备下载
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    @RequestMapping(value = "downloadWaterDevice/{projectId}")
    public void downloadWaterDevice(HttpServletResponse response, String json){
        waterDeviceService.downloadWaterDevice(response, json);
    }

    /**
     *
     * 功能描述:选中水压设备下载
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    @RequestMapping(value = "downloadWaterDeviceByList/{projectId}")
    public void downloadWaterDeviceByList(HttpServletResponse response, String json){
        waterDeviceService.downloadWaterDeviceByList(response, json);
    }

    /**
     *
     * 功能描述:水压设备离线任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    @RequestMapping(value = "waterOffLineTask")
    public Data waterOffLineTask(){
        return waterDeviceService.waterOffLineTask();
    }

    /**
     *
     * 功能描述:查询水压设备
     *
     * @return:
     * @auther: zhangZaiFa
     * @date: 2019/11/19 14:44
     */
    @RequestMapping(value = "findWaterDevice")
    public Data findWaterDevice(String json){
        return waterDeviceService.findWaterDevice(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-08-10 14:28
     *@Param: json
     *@Return: Data
     *@Description 查询消火栓设备数以及巡查点信息列表
     */
    @RequestMapping(value = "findHydrantDeviceCheckPoints/{projectId}")
    public Data findHydrantDeviceCheckPoints(String json){
        return waterDeviceService.findHydrantDeviceCheckPoints(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-08-11 15:40
     *@Param: json
     *@Return: Data
     *@Description 修改巡查点经纬度
     */
    @RequestMapping(value = "modifyCheckPointLatAndLng/{projectId}")
    public Data modifyCheckPointLatAndLng(String json){
        return waterDeviceService.modifyCheckPointLatAndLng(json);
    }

    /**
     * @Description 查询app首页水设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectWaterData/{projectId}")
    public JSONObject findUserProjectWaterData(String json){
        return waterDeviceService.findUserProjectWaterData(json);
    }

}
