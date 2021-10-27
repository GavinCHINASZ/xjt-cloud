package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.SummaryDetails;
import com.xjt.cloud.task.core.entity.TaskSummary;

import java.util.List;

/**
 *
 * 任务详情 Dao
 * @ClassName SummaryDetailsDao
 * @Author huanggc
 * @Date 2019-11-27
 * @version 1.0
 */
public interface SummaryDetailsDao {

    /**
     *
     * @Description 根据 任务ID 查询 任务详情
     * @Author huanggc
     * @Date: 2019-11-27
     * @param taskSummaryList 任务汇总list
     * @return List<SummaryDetails> 任务详情
     */
    List<SummaryDetails> findByTaskSummaryList(List<TaskSummary> taskSummaryList);

    /**
     *
     * @Description 根据 任务ID 查询 任务详情
     * @Author huanggc
     * @Date 2019-11-27
     * @param summaryDetails 任务汇总list
     * @return List<SummaryDetails> 任务详情
     */
    List<SummaryDetails> findSummaryDetailsList(SummaryDetails summaryDetails);

    /**
     *
     * @Description 批量保存 任务详情
     * @Author huanggc
     * @Date 2019-11-27
     * @param summaryDetailsList 任务详情List
     * @return Integer 任务汇总 数量
     */
    Integer saveSummaryDetailsList(List<SummaryDetails> summaryDetailsList);

    /**
     * @Description PC月任务汇总--巡查点概览表/设备概览表 列表总数
     * @Author huanggc
     * @Date 2019-11-26
     * @param summaryDetails 任务详情 实体
     * @return Integer
     */
    Integer findCheckOverviewTableTotalCount(SummaryDetails summaryDetails);

    /**
     *
     * @Description PC月任务汇总--巡查点概览表/设备概览表
     * @Author huanggc
     * @Date 2019-11-26
     * @param summaryDetails 任务详情
     * @return List<SummaryDetails> 任务详情 list集合
     */
    List<SummaryDetails> findCheckOverviewTable(SummaryDetails summaryDetails);

    /**
     *
     * @Description PC月任务汇总--巡查点概览表/设备概览表 导出列表
     * @Author huanggc
     * @Date 2019-11-26
     * @param summaryDetails 任务详情
     * @return List<SummaryDetails> 任务详情 list集合
     */
    List<SummaryDetails> findCheckOverviewTableDown(SummaryDetails summaryDetails);

    /**
     *
     * @Description PC月任务汇总--设备点检概览图
     * @Author: huanggc
     * @Date: 2019-11-27
     * @param  summaryDetails 任务详情
     * @return List<SummaryDetails> 任务详情 list集合
     */
    List<SummaryDetails> findCheckOverviewChart(SummaryDetails summaryDetails);

    /**
     * @Description 根据 任务ID 查询 任务下的设备
     * @Author: huanggc
     * @Date: 2019-11-27
     * @param summaryDetails 任务详情
     * @return List<SummaryDetails> 任务详情 list集合
     */
    List<SummaryDetails> findTaskDeviceList(SummaryDetails summaryDetails);
}
