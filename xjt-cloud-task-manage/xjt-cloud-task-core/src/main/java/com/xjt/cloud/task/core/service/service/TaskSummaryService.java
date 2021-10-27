package com.xjt.cloud.task.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * 任务汇总service接口
 * @ClassName TaskSummaryService
 * @Author huanggc
 * @Date 2019-11-25
 * @version 1.0
 */
public interface TaskSummaryService {

    /**
     * 月度工单统计--工单概览
     *
     * @Author: huanggc
     * @Date: 2019-11-22
     * @Param: json
     * @Return: Data
     */
    Data taskOverview(String json);

    /**
     * @Description 查询app首页月度工单信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectTaskOverviewData(String json);

    /**
     *月度工单统计--巡查点概览
     *
     *@Author: huanggc
     *@Date: 2019-11-22
     *@Param: json
     *@Return: Data
     */
    Data checkOverview(String json);

    /**
     *月度工单统计--巡查点概览列表
     *
     *@Author: huanggc
     *@Date: 2019-11-22
     *@Param: json
     *@Return: Data
     */
    Data checkOverviewList(String json);

    /**
     *@Author: huanggc
     *@Date: 2019-11-26
     *@Param: json
     *@Return: Data
     *@Description PC月任务汇总--任务概览表
     */
    Data taskOverviewTable(String json);

    /**
     *@Author: huanggc
     *@Date: 2019-11-26
     *@Param: json
     *@Return: Data
     *@Description PC月任务汇总--任务概览表 导出列表
     */
    void downTaskTable(String json, HttpServletResponse response);

    /**
     * PC月度工单统计--巡查点概览表
     *
     * @Author: huanggc
     * @Date: 2019-11-26
     * @Param: json
     * @Return: Data
     */
    Data checkOverviewTable(String json);

    /**
     * APP月度工单统计--设备系统详情
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @Param: json
     * @Return: Data
     */
    Data deviceDetails(String json);

    /**
     * PC月度工单统计--巡查点概览表 导出列表
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param json
     * @return void
     */
    void downCheckTable(String json, HttpServletResponse response);

    /**
     *@Author: huanggc
     *@Date: 2019-11-26
     *@Param: json
     *@Return: Data
     *@Description PC月任务汇总--巡查点概览图
     */
    Data checkOverviewChart(String json);

    /**
     *
     * APP月任务汇总--人员情况
     * @Author: huanggc
     * @Date: 2019-12-10
     * @param json
     * @return Data
     */
    Data userOverview(String json);

    /**
     * APP月度工单统计--巡查点概览列表
     *
     * @Author: huanggc
     * @Date: 2020-03-18
     * @param json
     * @return Data
     */
    Data findCheckOverviewList(String json);

    /**
     * 项目主页 数量
     *
     * @Author: huanggc
     * @Date: 2020-03-19
     * @param json
     * @return Data
     */
    Data findProjectCount(String json);
}
