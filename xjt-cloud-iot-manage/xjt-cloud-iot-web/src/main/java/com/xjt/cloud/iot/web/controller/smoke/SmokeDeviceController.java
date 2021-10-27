package com.xjt.cloud.iot.web.controller.smoke;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * 功能描述 导出烟感设备列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/20
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "downSmokeDeviceList/{projectId}")
    public void downSmokeDeviceList(String json, HttpServletResponse response){
        smokeDeviceService.downSmokeDeviceList(json, response);
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
     * 功能描述: 烟感设备离线任务
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/04
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "smokeOffLineTask")
    public Data smokeOffLineTask(){
        return smokeDeviceService.smokeOffLineTask();
    }

    /**
     * 功能描述: 查询支持的平台
     *
     * @auther huanggc
     * @date 2020/08/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findSmokeCloud/{projectId}")
    public Data findSmokeCloud(){
        return smokeDeviceService.findSmokeCloud();
    }

    /**
     * 功能描述: 上传 excel表格 批量绑定烟感
     *
     * @param json
     * @param file 上传的文件
     * @return com.xjt.cloud.commons.utils.Data
     * @auther huanggc
     * @date 2020/08/18
     */
    @RequestMapping(value = "uploadPointDeviceExcel/{projectId}")
    public Data uploadPointDeviceExcel(String json, MultipartFile file){
        return smokeDeviceService.uploadPointDeviceExcel(json, file);
    }

    /**
     * 功能描述: 智能无线烟感 批量导入表格模板下载
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @auther huanggc
     * @date 2020/08/21
     */
    @RequestMapping(value = "downSmokeModelExcel/{projectId}")
    public Data downSmokeModelExcel(String json, HttpServletResponse response){
        return smokeDeviceService.downSmokeModelExcel(json, response);
    }

}
