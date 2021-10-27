package com.xjt.cloud.admin.manage.controller.iot;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.iot.WaterDevice;
import com.xjt.cloud.admin.manage.service.service.iot.WaterDeviceService;
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
 * @Date: 2019/12/6 10:50
 * @Description: 水压设备管理Controller
 */
@Controller
@RequestMapping("/iot/water/")
public class WaterDeviceController  extends AbstractController {
    @Autowired
    private WaterDeviceService waterDeviceService;

    /**
     *
     * 功能描述: 跳转到水压设备管理页面
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/6 11:12
     */
    @RequestMapping("toWaterDeviceListPage")
    public ModelAndView toWaterDeviceListPage(){
        return new ModelAndView("iot/waterList");
    }

    /**
     *
     * 功能描述:  查询水压设备系统列表
     *
     * @param ajaxPage
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @RequestMapping(value = "findWaterDeviceList")
    @ResponseBody
    public ScriptPage<WaterDevice> findWaterDeviceList(AjaxPage ajaxPage, WaterDevice waterDevice){
        return waterDeviceService.findWaterDeviceList(ajaxPage,waterDevice);
    }

    /**
     *
     * 功能描述:修改水压设备
     *
     * @param waterDevice
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    @RequestMapping(value = "modifyWaterDevice")
    @ResponseBody
    public Data modifyWaterDevice(WaterDevice waterDevice){
        return waterDeviceService.modifyWaterDevice(waterDevice);
    }

    /**
     *
     * 功能描述:  查询物联设备列表
     *
     * @param waterDevice
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.WaterDevice>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    @RequestMapping(value = "findSensorNoList")
    @ResponseBody
    public List<WaterDevice> findSensorNoList(WaterDevice waterDevice){
        return waterDeviceService.findSensorNoList(waterDevice);
    }
}
