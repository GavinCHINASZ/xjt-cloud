package com.xjt.cloud.device.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:24
 * @Description:设备管理
 */
@RestController
@RequestMapping("/device/")
public class DeviceController extends AbstractController {
    @Autowired
    private DeviceService deviceService;

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceList/{projectId}")
    public Data findDeviceList(String json){
        return deviceService.findDeviceList(json);
    }

    /**
     *
     * 功能描述:查询设备列表 树结构
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/24
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceListTree/{projectId}")
    public Data findDeviceListTree(String json){
        return deviceService.findDeviceListTree(json);
    }

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceListByAppId/{appId}")
    public Data findDeviceListByAppId(String json){
        return deviceService.findDeviceList(json);
    }

    /**
     *
     * 功能描述:查询未关联设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceListNotBoundIot/{projectId}")
    public Data findDeviceListNotBoundIot(String json){
        return deviceService.findDeviceListNotBoundIot(json);
    }

    /**
     *
     * 功能描述:查询设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDevice/{projectId}")
    public Data findDevice(String json){
        return deviceService.findDevice(json);
    }

    /**
     *
     * 功能描述:查询其它模块调用的设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceModuleCalls")
    public Data findDeviceModuleCalls(String json){
        return deviceService.findDevice(json);
    }

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "saveDevice/{projectId}")
    public Data saveDevice(String json){
        return deviceService.saveDevice(json);
    }

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findDeviceReport")
    public JSONObject findDeviceReport(String json){
        return deviceService.findDeviceReport(json);
    }

    /**
     *
     * 功能描述:批量新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "saveDevices/{projectId}")
    public Data saveDevices(String json){
        return deviceService.saveDevices(json);
    }

    /**
     *
     * 功能描述:修改设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "modifyDevice/{projectId}")
    public Data modifyDevice(String json){
        return deviceService.modifyDevice(json);
    }

    /**
     *
     * 功能描述:巡检点批量绑定设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @RequestMapping(value = "pointBindDevices/{projectId}")
    public Data pointBindDevices(String json){
        return deviceService.pointBindDevices(json);
    }

    /**
     *
     * 功能描述:巡检点批量清除设备绑定
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @RequestMapping(value = "pointClearBindDevices/{projectId}")
    public Data pointClearBindDevices(String json){
        return deviceService.pointClearBindDevices(json);
    }

    /**
     *
     * 功能描述:删除设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:21
     */
    @RequestMapping(value = "delDevice/{projectId}")
    public Data delDevice(String json){
        return deviceService.modifyDevice(json);
    }

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @RequestMapping(value = "downloadDeviceRecord/{projectId}")
    public void downloadDeviceRecord(HttpServletResponse response, String json){
        deviceService.downloadDeviceRecord(response, json);
    }

    /**
     *
     * 功能描述:下载设备档案饼图与列表
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @RequestMapping(value = "downloadDeviceRecordPicList/{projectId}")
    public void downloadDeviceRecordPicList(HttpServletResponse response, String json){
        deviceService.downloadDeviceRecordPicList(response,json);
    }

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @RequestMapping(value = "downloadDeviceRecordByList/{projectId}")
    public void downloadDeviceRecordByList(HttpServletResponse response, String json){
        deviceService.downloadDeviceRecordByList(response, json);
    }

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @RequestMapping(value = "findDeviceRecordList/{projectId}")
    public Data findDeviceRecordList(String json){
        return deviceService.findDeviceRecordList(json);
    }

    /**
     *
     * 功能描述:查询设备档案基本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @RequestMapping(value = "findDeviceRecordBaseInfo/{projectId}")
    public Data findDeviceRecordBaseInfo(String json){
        return deviceService.findDeviceRecordBaseInfo(json);
    }

    /**
     *
     * 功能描述:查询 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceRemind/{projectId}")
    public Data findDeviceRemind(String json){
        return deviceService.findDeviceRemind(json);
    }

    /**
     * 功能描述:更新 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateDeviceRemind/{projectId}")
    public Data updateDeviceRemind(String json){
        return deviceService.updateDeviceRemind(json);
    }

    /**
     * 功能描述:保存 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveDeviceRemind/{projectId}")
    public Data saveDeviceRemind(String json){
        return deviceService.saveDeviceRemind(json);
    }

    /**
     * 功能描述: 设备过期提醒 每天定时任务
     *
     * @auther: huanggc
     * @date: 2020-03-30
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deviceRemindTask")
    public Data deviceRemindTask(){
        return deviceService.deviceRemindTask();
    }

    /**
     * 功能描述: 送修提醒 每天定时任务
     *
     * @auther: huanggc
     * @date: 2020-03-30
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "repairRemindTask")
    public Data repairRemindTask(){
        return deviceService.repairRemindTask();
    }

    /**
     * @Description 查询设备操作记录饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/2/22 14:05
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findDeviceReportPie/{projectId}")
    public Data findDeviceReportPie(String json){
        return deviceService.findDeviceReportPie(json);
    }
}
