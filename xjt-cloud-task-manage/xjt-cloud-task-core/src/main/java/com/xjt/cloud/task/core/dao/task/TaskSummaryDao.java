package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.TaskSummary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 任务汇总Dao
 * @ClassName TaskSummaryDao
 * @Author huanggc
 * @Date 2019-11-25
 * @version 1.0
 */
@Repository
public interface TaskSummaryDao {

    /**
     *
     * @Description PC月任务汇总--总概览--任务概览
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return TaskSummary 任务汇总实体
     */
    TaskSummary findTaskOverview(TaskSummary taskSummary);

    /**
     * @Description PC月任务汇总--总概览--巡查点概览
     *
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return TaskSummary 任务汇总实体
     */
    TaskSummary findTaskOverviews(TaskSummary taskSummary);

    /**
     *
     * @Description 把 任务 保存到 任务汇总
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummaryList 任务汇总 实体list
     * @return Integer 保存成功的数量
     */
    Integer saveTaskSummaryList(List<TaskSummary> taskSummaryList);

    /**
     *
     * @Description PC月任务汇总--总概览--点检情况
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return TaskSummary
     */
    TaskSummary checkSituation(TaskSummary taskSummary);

    /**
     *
     * @Description 查询 任务汇总 总数
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return Integer 任务汇总 数量
     */
    Integer findTaskSummaryListTotalCount(TaskSummary taskSummary);

    /**
     *
     * @Description PC月任务汇总--任务概览表
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return List<TaskSummary> 任务汇总 list集合
     */
    List<TaskSummary> findTaskOverviewTable(TaskSummary taskSummary);

    /**
     *
     * @Description PC月任务汇总--任务概览表 导出列表
     * @Author huanggc
     * @Date 2019-11-26
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return List<TaskSummary> 任务汇总 list集合
     */
    List<TaskSummary> downTaskTable(TaskSummary taskSummary);

    /**
     *
     * @Description 查询 任务
     * @Author huanggc
     * @Date 2019-11-27
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return List<TaskSummary>
     */
    List<TaskSummary> findTaskList(TaskSummary taskSummary);

    /**
     *
     * @Description 查询 任务汇总
     * @Author huanggc
     * @Date 2019-11-27
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return List<TaskSummary>
     */
    List<TaskSummary> findTaskSummaryList(TaskSummary taskSummary);

    /**
     *
     * @Description PC任务管理,任务工单导出详情--查询任务
     * @Author: huanggc
     * @Date: 2019-11-27
     * @param taskSummary 任务汇总(与 任务 表相同)
     * @return List<TaskSummary>
     */
    List<TaskSummary> findTaskDetails(TaskSummary taskSummary);

    /**
     *
     * APP月任务汇总--人员情况
     * @Author huangGuiChuan
     * @Date 2019-12-10
     * @param taskSummary 任务汇总
     * @return List<TaskSummary>
     */
    List<TaskSummary> userOverview(TaskSummary taskSummary);
}
