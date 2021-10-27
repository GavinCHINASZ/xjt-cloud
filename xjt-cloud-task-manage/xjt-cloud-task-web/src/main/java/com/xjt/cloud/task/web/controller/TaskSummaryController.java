package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 月度工单统计Controller层
 *
 * @ClassName TaskSummaryController
 * @Author hunaggc
 * @Date 2020-04-08
 * @Version 1.0
 */
@RestController
@RequestMapping("/taskSummary")
public class TaskSummaryController extends AbstractController {
    @Autowired
    private TaskSummaryService taskSummaryService;

    /**
     * 月度工单统计--任务概览
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param json
     * @return Data
     */
    @RequestMapping(value = "taskOverview/{projectId}")
    public Data taskOverview(String json){
        return taskSummaryService.taskOverview(json);
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
     * 月度工单统计--任务概览表
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param json
     * @return Data
     */
    @RequestMapping(value = "taskOverviewTable")
    public Data taskOverviewTable(String json){
        return taskSummaryService.taskOverviewTable(json);
    }

    /**
     * 地铁大屏--巡检管理列表
     *
     * @Author: huanggc
     * @Date: 2020-05-22
     * @param json
     * @return Data
     */
    @RequestMapping(value = "taskOverviewTableByAppId/{appId}")
    public Data taskOverviewTableByAppId(String json){
        return taskSummaryService.taskOverviewTable(json);
    }

    /**
     * 月度工单统计--任务概览表 导出列表
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param json
     * @param response HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "downTaskTable/{projectId}")
    public void downTaskTable(String json, HttpServletResponse response){
        taskSummaryService.downTaskTable(json, response);
    }

    /**
     * PC月度工单统计--巡查点概览表
     *
     * @Author: huanggc
     * @Date: 2019-11-26
     * @param json
     * @return Data
     */
    @RequestMapping(value = "checkOverviewTable/{projectId}")
    public Data checkOverviewTable(String json){
        return taskSummaryService.checkOverviewTable(json);
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
     * PC月度工单统计--巡查点概览表 导出列表
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param json
     * @param response HttpServletResponse
     * @return void
     */
    @RequestMapping(value = "downCheckTable/{projectId}")
    public void downCheckTable(String json, HttpServletResponse response){
        taskSummaryService.downCheckTable(json, response);
    }

    /**
     *
     * PC月任务汇总--巡查点概览图/设备点检概览图
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
     * 项目主页 数量
     *
     * @Author: huanggc
     * @Date: 2020-03-19
     * @param json
     * @return Data
     */
    @RequestMapping(value = "findProjectCount/{projectId}")
    public Data findProjectCount(String json){
        return taskSummaryService.findProjectCount(json);
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
