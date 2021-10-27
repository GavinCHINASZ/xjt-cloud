package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.TaskSummaryIoService;
import com.xjt.cloud.task.core.service.service.TaskSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName TaskSummaryIoController
 * @Author hunaggc
 * @Date 2019-11-25
 * @Description 任务概览表导出详情
 * @Version 1.0
 */
@RestController
@RequestMapping("/taskSummaryIo")
public class TaskSummaryIoController extends AbstractController {
    @Autowired
    private TaskSummaryIoService taskSummaryIoService;

    /**
     *@Author: huanggc
     *@Date: 2019-11-26
     *@Param: json
     *@Return: Data
     *@Description PC月任务汇总--任务概览表 导出详情
     */
    @RequestMapping(value = "downTaskTableDetails/{projectId}")
    public void downTaskTableDetails(String json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        taskSummaryIoService.downTaskTableDetails(json, request, response);
    }

}
