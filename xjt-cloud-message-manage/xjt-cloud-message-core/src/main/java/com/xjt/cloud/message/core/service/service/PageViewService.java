package com.xjt.cloud.message.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * PageViewService 页面浏览量或点击量
 *
 * @author huanggc
 * @date 2020/10/23
 */
public interface PageViewService {

    /**
     * 保存 PV
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data savePageView(String json, HttpServletRequest request);

    /**
     * 多线程保存 PV
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     **/
    void savePageViewThread(String json);

    /**
     * 查询 PV list
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/10/23
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findPageViewList(String json);

    /**
     * PV统计定时任务
     *
     * @author huanggc
     * @date 2020/11/03
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data pageViewReportTask();
}
