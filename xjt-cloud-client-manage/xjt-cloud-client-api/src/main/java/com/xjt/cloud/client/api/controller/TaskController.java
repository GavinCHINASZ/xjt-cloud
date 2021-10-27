package com.xjt.cloud.client.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.client.core.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务Controller层
 *
 * @ClassName TaskController
 * @Author huanggc
 * @Date 2020-03-25
 * @Version 1.0
 */
@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService taskService;

    /**
     * 查询 任务列表
     *
     *@Author huanggc
     *@Date: 2020-03-25
     *@Param: 任务实体
     *@Return: 任务列表
     */
    @RequestMapping(value = "findTaskList/{projectId}")
    public Data findTaskList(String json){
        return taskService.findTaskList(json);
    }

}
