package com.xjt.cloud.report.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: huanggc
 * @Date: 2019/11/04
 * @Description: 报表
 */
public interface ReportService {

    /**
     * 功能描述: 报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/11/06
     */
    Data report(String json, HttpServletResponse response);

}
