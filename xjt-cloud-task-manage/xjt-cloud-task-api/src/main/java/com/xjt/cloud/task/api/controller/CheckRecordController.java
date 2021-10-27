package com.xjt.cloud.task.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.CheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  巡查记录 Controller
 *
 * @Author Administrator
 * @Date 2019-07-26 11:49
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkRecord")
public class CheckRecordController extends AbstractController {
    @Autowired
    private CheckRecordService checkRecordService;

    /**
     * 查询巡检记录列表
     *
     * @Author huangGuiChuan
     * @Date 2020-03-12
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckRecordList/{projectId}")
    public Data findCheckRecordList(String json) {
        return checkRecordService.findCheckRecordList(json);
    }

    /**
     * @Description 查询app首页巡检记录信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectCheckRecordData/{projectId}")
    public JSONObject findUserProjectCheckRecordData(String json){
        return checkRecordService.findUserProjectCheckRecordData(json);
    }

    /**
     * 查询巡检点设备的巡检项
     *
     *@Author: zhangZaiFa
     *@Date: 2019-11-27 11:36
     *@Param: json
     *@Return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findCheckPointDeviceCheckItem/{projectId}")
    public Data findCheckPointDeviceCheckItem(String json) {
        return checkRecordService.findCheckPointDeviceCheckItem(json);
    }

    /**@
     *  查询任务巡查点巡检记录
     *
     * MethodName: findTaskCheckPointCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/29 9:25
     **/
    @RequestMapping(value = "findTaskCheckPointCheckRecord/{projectId}")
    public Data findTaskCheckPointCheckRecord(String json) {
        return checkRecordService.findTaskCheckPointCheckRecord(json);
    }

    /**
     *  保存巡检记录
     *
     * @MethodName: saveCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/28 10:01
     **/
    @RequestMapping(value = "saveCheckRecord/{projectId}")
    public Data saveCheckRecord(String json, HttpServletRequest request) {
        return checkRecordService.saveCheckRecord(json,request);
    }

    /**
     *  保存巡检记录带任务检测工具，现只有地铁app使用此接口
     *
     * @MethodName: saveTaskDeviceCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/28 10:01
     **/
    @RequestMapping(value = "saveTaskDeviceCheckRecord/{projectId}")
    public Data saveTaskDeviceCheckRecord(String json) {
        return checkRecordService.saveTaskDeviceCheckRecord(json);
    }

    /**
     *  保存离线巡检记录带任务检测工具，现只有地铁app使用此接口
     * @MethodName: saveOfficeCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/20 15:32
     **/
    @RequestMapping(value = "saveTaskDeviceOfficeCheckRecord/{projectId}")
    public Data saveTaskDeviceOfficeCheckRecord(String json) {
        return checkRecordService.saveTaskDeviceOfficeCheckRecord(json);
    }

    /**
     *
     * @MethodName: saveOfficeCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/20 15:32
     **/
    @RequestMapping(value = "saveOfficeCheckRecord/{projectId}")
    public Data saveOfficeCheckRecord(String json,HttpServletRequest request) {
        return checkRecordService.saveOfficeCheckRecord(json,request);
    }

    /**
     * 删除, 批量删除巡查记录
     *
     * @param json
     * @Author huangGuiChuan
     * @Date 2020-01-16
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/deletedCheckRecord/{projectId}")
    public Data deletedCheckRecord(String json) {
        return checkRecordService.deletedCheckRecord(json);
    }


    /**@MethodName: findSpotCheckTaskDeviceCheckItem
     * @Description: 查询抽查设备的巡检项
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/8 14:52
     **/
    @RequestMapping(value = "findSpotCheckTaskDeviceCheckItem/{projectId}")
    public Data findSpotCheckTaskDeviceCheckItem(String json) {
        return checkRecordService.findSpotCheckTaskDeviceCheckItem(json);
    }

    /**
     *  保存抽查设备记录
     *
     * @MethodName: saveSpotCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/28 10:01
     **/
    @RequestMapping(value = "saveSpotCheckRecord/{projectId}")
    public Data saveSpotCheckRecord(String json) {
        return checkRecordService.saveSpotCheckRecord(json);
    }

    /**
     *  查询抽查设备记录
     *
     * @MethodName: findSpotCheckRecord
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/28 10:01
     **/
    @RequestMapping(value = "findSpotCheckRecord/{projectId}")
    public Data findSpotCheckRecord(String json) {
        return checkRecordService.findSpotCheckRecord(json);
    }

    /**@MethodName: findAutomaticCheckDeviceStatus
     * @Description: 查询自动检测设备状态
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/11 14:55
     **/
    @RequestMapping(value = "findAutomaticCheckDeviceStatus/{projectId}")
    public Data findAutomaticCheckDeviceStatus(String json) {
        return checkRecordService.findAutomaticCheckDeviceStatus(json);
    }

    /**@MethodName: updAutomaticCheckDeviceStatus
     * @Description: 修改自动检测设备状态
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/11 14:55
     **/
    @RequestMapping(value = "updAutomaticCheckDeviceStatus")
    public Data updAutomaticCheckDeviceStatus(String json) {
        return checkRecordService.updAutomaticCheckDeviceStatus(json);
    }
}
