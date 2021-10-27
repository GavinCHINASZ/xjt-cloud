package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.TaskReview;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName TaskReviewDao
 *@Author dwt
 *@Date 2019-07-25 15:41
 *@Description: 任务审核Dao
 *@Version 1.0
 */
@Repository
public interface TaskReviewDao {

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:48
     *@Param: 任务审核
     *@Return: 任务审核列表
     *@Description: 筛选符合条件的任务审核对象
     */
    List<TaskReview> findTaskReviewList(TaskReview taskReview);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:49
     *@Param: 任务审核对象
     *@Return: 主键id
     *@Description: 保存
     */
    Integer saveTaskReview(TaskReview taskReview);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:51
     *@Param: 任务审核对象
     *@Return:
     *@Description: 修改任务审核对象
     */
    Integer modifyTaskReview(TaskReview taskReview);
    /**
     *@Author: dwt
     *@Date: 2019-08-13 10:46
     *@Param: java.lang.Long
     *@Return: void
     *@Description 根据任务id删除审核记录
     */
    void deleteByTaskId(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-10-17 9:46
     *@Param: java.lang.String
     *@Return: java.util.List
     *@Description 根据任务id查询审核人
     */
    List<String> findTaskReviewNameByTaskId(@Param("taskId") Long taskId, @Param("projectId") Long projectId);
    /**
     *@Author: dwt
     *@Date: 2019-10-17 10:27
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 根据id查询审核状态
     */
    Integer findReviewStatusById(Long id);
    /**
     *@Author: dwt
     *@Date: 2019-10-29 14:23
     *@Param: java.lang.Long
     *@Return: TaskReview
     *@Description 根据任务id查询审核列表
     */
    List<TaskReview> findTaskReviewByTaskId(Long taskId);
}
