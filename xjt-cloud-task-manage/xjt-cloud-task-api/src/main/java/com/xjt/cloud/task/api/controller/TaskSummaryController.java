package com.xjt.cloud.task.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.TaskSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务汇总Controller层
 *
 * @ClassName TaskSummaryController
 * @Author hunaggc
 * @Date 2019-12-09
 * @Version 1.0
 */
@RestController
@RequestMapping("/taskSummary")
public class TaskSummaryController extends AbstractController {
    @Autowired
    private TaskSummaryService taskSummaryService;

    /**
     * 月度工单统计--工单概览
     *
     * @Author: huanggc
     * @Date: 2019-11-22
     * @param json
     * @return Data
     */
    @RequestMapping(value = "taskOverview/{projectId}")
    public Data taskOverview(String json){
        return taskSummaryService.taskOverview(json);
    }

    /**
     * @Description 查询app首页月度工单信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectTaskOverviewData/{projectId}")
    public JSONObject findUserProjectTaskOverviewData(String json){
        return taskSummaryService.findUserProjectTaskOverviewData(json);
    }

    /**
     * 月度工单统计--巡查点概览
     *
     * @Author: huanggc
     * @Date: 2020-03-16
     * @param json
     * @return Data
     */
    @RequestMapping(value = "checkOverview/{projectId}")
    public Data checkOverview(String json){
        return taskSummaryService.checkOverview(json);
    }

    /**
     * APP月度工单统计--巡查点概览列表
     *
     * @Author: huanggc
     * @Date: 2020-03-18
     * @param json
     * @return Data
     */
    @RequestMapping(value = "findCheckOverviewList/{projectId}")
    public Data findCheckOverviewList(String json){
        return taskSummaryService.findCheckOverviewList(json);
    }

    /**
     *  任务概览表
     *
     * @Author: huanggc
     * @Date: 2019-11-26
     * @param json
     * @return Data
     */
    @RequestMapping(value = "taskOverviewTable")
    public Data taskOverviewTable(String json){
        return taskSummaryService.taskOverviewTable(json);
    }

    /**
     * 月度工单统计--设备系统详情
     *
     * @Author: huanggc
     * @Date: 2019-11-26
     * @param json
     * @return Data
     */
    @RequestMapping(value = "deviceDetails/{projectId}")
    public Data deviceDetails(String json){
        return taskSummaryService.deviceDetails(json);
    }

    /**
     * PC月任务汇总--巡查点概览图/设备点检概览图
     *
     * @Author: huanggc
     * @Date: 2019-11-26
     * @param json
     * @return Data
     */
    @RequestMapping(value = "checkOverviewChart/{projectId}")
    public Data checkOverviewChart(String json){
        return taskSummaryService.checkOverviewChart(json);
    }

    /**
     * APP月度工单统计--人员情况
     *
     * @Author: huanggc
     * @Date: 2020-03-18
     * @param json
     * @return Data
     */
    @RequestMapping(value = "userOverview/{projectId}")
    public Data userOverview(String json){
        return taskSummaryService.userOverview(json);
    }

}
