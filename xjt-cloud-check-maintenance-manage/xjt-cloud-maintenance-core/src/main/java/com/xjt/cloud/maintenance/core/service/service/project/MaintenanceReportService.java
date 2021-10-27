package com.xjt.cloud.maintenance.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * 维保报告
 *
 * @author huanggc
 * @date 2020-04-10
 */
public interface MaintenanceReportService {
    /**
     * 生成 维保报告
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data saveMaintenanceReport(String json, HttpServletResponse response);

    /**
     * 报告查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     **/
    Data findReport(String json);

    /**
     * 报告下载
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020-04-10
     **/
    void downReport(String json, HttpServletResponse response);

    /**
     * 打印标签
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-14
     **/
    Data saveLabel(String json, HttpServletResponse response);


}
