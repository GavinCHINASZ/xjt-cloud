package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.report.TaskReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 任务报表,统计,月报 相关共用
 *
 * @author huanggc
 * @date 2020/11/30
 */
@RestController
@RequestMapping("/task/report/")
public class TaskReportController extends AbstractController {
    @Autowired
    private TaskReportService taskReportService;

    /**
     * 定时任务:发送故障统计信息(月报)
     *
     * @author huanggc
     * @date 2020/11/30
     * @return Data
     */
    @RequestMapping(value = "taskFaultStatistics")
    public Data taskFaultStatistics(){
        return taskReportService.taskFaultStatistics();
    }

    /**
     * 设备异常统计:异常设备统计--柱形图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @RequestMapping(value = "deviceColumnar/{projectId}")
    public Data deviceColumnar(String json){
        return taskReportService.deviceColumnar(json);
    }

    /**
     * 设备异常统计:饼图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @RequestMapping(value = "devicePie/{projectId}")
    public Data devicePie(String json){
        return taskReportService.devicePie(json);
    }

    /**
     * 设备异常统计:设备类型列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @RequestMapping(value = "findDeviceTypeList/{projectId}")
    public Data findDeviceTypeList(String json){
        return taskReportService.findDeviceTypeList(json);
    }

    /**
     * 设备异常统计:导出功能
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @RequestMapping(value = "downDeviceTypeList/{projectId}")
    public void downDeviceTypeList(String json, HttpServletRequest request, HttpServletResponse response){
        taskReportService.downDeviceTypeList(json, request, response);
    }
}
