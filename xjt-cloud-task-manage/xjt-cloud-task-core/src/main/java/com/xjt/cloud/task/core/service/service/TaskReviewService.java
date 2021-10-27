package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName TaskReviewService
 *@Author dwt
 *@Date 2019-07-26 10:46
 *@Description 任务审核
 *@Version 1.0
 */
public interface TaskReviewService {
    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:48
     *@Param: 任务审核json
     *@Return: 任务审核列表
     *@Description: 筛选符合条件的任务审核对象
     */
    Data findTaskReviewList(String json);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:49
     *@Param: 任务审核json
     *@Return: 主键id
     *@Description: 保存
     */
    Data saveTaskReview(String json);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:51
     *@Param: 任务审核json
     *@Return:
     *@Description: 修改任务审核对象
     */
    Data modifyTaskReview(String json);
    /**
     *@Author: dwt
     *@Date: 2019-10-17 10:21
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 查询任务审核状态
     */
    Data findTaskReviewDetail(Long reviewId);
    /**
     *@Author: dwt
     *@Date: 2019-11-05 13:35
     *@Param: json
     *@Return: Data
     *@Description 任务审核添加事物
     */
    Data transactionSaveTaskReview(String json);
    /**
     *@Author: dwt
     *@Date: 2019-11-22 16:27
     *@Param: java.lang.Long
     *@Return: Data
     *@Description 根据审核记录id查询审核设备列表
     */
    Data findTaskReviewDeviceList(Long id);

}
