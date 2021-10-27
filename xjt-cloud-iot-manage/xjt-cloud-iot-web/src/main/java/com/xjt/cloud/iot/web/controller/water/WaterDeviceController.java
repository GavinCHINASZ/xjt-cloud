package com.xjt.cloud.iot.web.controller.water;

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
     * 功能描述:查询水压设备大屏信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    @RequestMapping(value = "findWaterDeviceScreen")
    public Data findWaterDeviceScreen(String json){
        return waterDeviceService.findWaterDeviceScreen(json);
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
}
