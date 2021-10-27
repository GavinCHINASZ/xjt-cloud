package com.xjt.cloud.task.core.dao.report;

import com.xjt.cloud.task.core.entity.report.TaskReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务报表,统计,月报 相关共用Dao
 * 
 * @author huanggc
 * @date 2020/12/01
 */
@Repository
public interface TaskReportDao {
    /**
     * 设备异常统计:异常设备统计--柱形图
     *
     * @param taskReport 报表实体类(数据库中无表)
     * @author huanggc
     * @date 2020/12/01
     * @return List<TaskReport>
     */
    List<TaskReport> deviceColumnar(TaskReport taskReport);

    /**
     * 设备异常统计:饼图
     *
     * @param taskReport TaskReport
     * @author huanggc
     * @date 2020/12/01
     * @return TaskReport
     */
    List<TaskReport> devicePie(TaskReport taskReport);

    /**
     * 设备异常统计:设备类型列表
     *
     * @param taskReport TaskReport
     * @author huanggc
     * @date 2020/12/01
     * @return List<TaskReport>
     */
    List<TaskReport> findDeviceTypeList(TaskReport taskReport);

    /**
     * 设备异常统计:查询总数量
     *
     * @param taskReport TaskReport
     * @author huanggc
     * @date 2020/12/07
     * @return Integer
     */
    Integer findTaskReportTotalCount(TaskReport taskReport);
}
