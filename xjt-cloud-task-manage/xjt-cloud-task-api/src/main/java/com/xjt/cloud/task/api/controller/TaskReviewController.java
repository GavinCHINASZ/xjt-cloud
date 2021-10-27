package com.xjt.cloud.task.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.TaskReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TaskReviewController
 * @Author dwt
 * @Date 2019-10-16 17:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/review")
public class TaskReviewController extends AbstractController {
    @Autowired
    private TaskReviewService taskReviewService;
    /**
     *@Author: dwt
     *@Date: 2019-10-16 17:25
     *@Param: java.lang.String
     *@Return: Data
     *@Description 保存审核记录
     */
    @RequestMapping(value = "saveTaskReview/{projectId}")
    public Data saveTaskReview(String json){
        return taskReviewService.transactionSaveTaskReview(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-10-17 10:22
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 查询审核记录审核状态
     */
    @RequestMapping(value = "findTaskReviewDetail/{projectId}")
    public Data findTaskReviewDetail(Long id){
        return taskReviewService.findTaskReviewDetail(id);
    }
    /**
     *@Author: dwt
     *@Date: 2019-10-29 14:59
     *@Param: json
     *@Return: Data
     *@Description 查询审核记录列表
     */
    @RequestMapping(value = "findTaskReviewList/{projectId}")
    public Data findTaskReviewList(String json){
        return taskReviewService.findTaskReviewList(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-11-22 16:27
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据审核记录id查询审核设备列表
     */
    @RequestMapping(value = "findTaskReviewDeviceList/{projectId}")
    public Data findTaskReviewDeviceList(Long id){
        return taskReviewService.findTaskReviewDeviceList(id);
    }
}
