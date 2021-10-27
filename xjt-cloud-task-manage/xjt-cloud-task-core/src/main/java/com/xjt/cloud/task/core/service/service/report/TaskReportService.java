package com.xjt.cloud.task.core.service.service.report;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 任务报表,统计,月报 相关共用
 *
 * @author huanggc
 * @date 2020/11/30
 */
public interface TaskReportService {
    /**
     * 定时任务发送故障统计信息(月报)
     *
     * @author huanggc
     * @date 2020/11/30
     * @return Data
     */
    Data taskFaultStatistics();

    /**
     * 设备异常统计:异常设备统计--柱形图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    Data deviceColumnar(String json);

    /**
     * 设备异常统计:饼图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    Data devicePie(String json);

    /**
     * 设备异常统计:设备类型列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    Data findDeviceTypeList(String json);

    /**
     * 设备异常统计:导出功能
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    void downDeviceTypeList(String json, HttpServletRequest request, HttpServletResponse response);
}
