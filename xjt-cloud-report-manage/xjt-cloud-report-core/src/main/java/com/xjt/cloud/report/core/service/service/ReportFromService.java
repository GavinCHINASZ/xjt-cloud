package com.xjt.cloud.report.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日报报表 service接口
 *
 * @Auther huanggc
 * @date 2020/07/06
 */
public interface ReportFromService {

    /**
     * 功能描述: 查看 日报报表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findReportFromList(String json, HttpServletRequest request, HttpServletResponse response);

    /**
     * 功能描述: 日报报表 导出表格
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/08/25
     * @return void
     */
    void downReportFromExcel(String json, HttpServletResponse response);

    /**
     * 功能描述: 保存/新增 日报报表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveReportFrom(String json);

    /**
     * 功能描述: 查询 填写过的日报 日期
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/07
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findReportFromDate(String json);
}
