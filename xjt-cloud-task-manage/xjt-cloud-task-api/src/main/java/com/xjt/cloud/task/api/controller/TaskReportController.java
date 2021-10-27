package com.xjt.cloud.task.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.report.TaskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务报表,统计,月报 相关共用
 *
 * @author huanggc
 * @date 2020/11/30
 */
@RestController
@RequestMapping("/task/report")
public class TaskReportController extends AbstractController {
    @Autowired
    private TaskReportService taskReportService;

    /**
     * 定时任务发送故障统计信息(月报)
     *
     * @author huanggc
     * @date 2020/11/30
     * @return Data
     */
    @RequestMapping(value = "taskFaultStatistics")
    public Data taskFaultStatistics(){
        return taskReportService.taskFaultStatistics();
    }

}
