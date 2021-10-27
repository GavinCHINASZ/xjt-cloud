package com.xjt.cloud.device.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.service.service.CheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/27 14:40
 * @Description: app巡检点管理Controller
 */
@RestController
@RequestMapping("/check/point/")
public class CheckPointController extends AbstractController {
    @Autowired
    private CheckPointService checkPointService;

    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "findCheckPointList/{projectId}")
    public Data findCheckPointList(String json){
        return checkPointService.findCheckPointList(json);
    }

    /**
     *
     * 功能描述:查询巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "findCheckPoint/{projectId}")
    public Data findCheckPoint(String json){
        return checkPointService.findCheckPoint(json);
    }

    /**
     *
     * 功能描述:添加巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "saveCheckPoint/{projectId}")
    public Data saveCheckPoint(String json){
        return checkPointService.saveCheckPoint(json);
    }

    /**
     *
     * 功能描述:修改巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @RequestMapping(value = "modifyCheckPoint/{projectId}")
    public Data modifyCheckPoint(String json){
        return checkPointService.modifyCheckPoint(json);
    }

    /**
     *
     * 功能描述:删除巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/13 9:19
     */
    @RequestMapping(value = "delCheckPoint/{projectId}")
    public Data delCheckPoint(String json){
        return checkPointService.delCheckPoint(json);
    }

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @RequestMapping(value = "findCheckPointAndDeviceReport/{projectId}")
    public Data findCheckPointAndDeviceReport(String json){
        return checkPointService.findCheckPointAndDeviceReport(json);
    }

    /**
     * @Description 查询app首页设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectDeviceData/{projectId}")
    public JSONObject findUserProjectDeviceData(String json){
        return checkPointService.findUserProjectDeviceData(json);
    }

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息按建筑物分组
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @RequestMapping(value = "findCheckPointAndDeviceReportGroupBuilding/{projectId}")
    public Data findCheckPointAndDeviceReportGroupBuilding(String json){
        return checkPointService.findCheckPointAndDeviceReportGroupBuilding(json);
    }

    /**
     *
     * 功能描述:查询未关联设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findCheckPointNotBoundIot/{projectId}")
    public Data findCheckPointNotBoundIot(String json){
        return checkPointService.findCheckPointNotBoundIot(json);
    }

    /**
     *
     * 功能描述:查询关联设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findCheckPointBoundIot/{projectId}")
    public Data findCheckPointBoundIot(String json){
        return checkPointService.findCheckPointBoundIot(json);
    }

    /**
     *
     * 功能描述:查询已关联物联设备的建物物楼层列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @RequestMapping(value = "findBuildingFloorBoundIot/{projectId}")
    public Data findBuildingFloorBoundIot(String json){
        return checkPointService.findBuildingFloorBoundIot(json);
    }
}
