package com.xjt.cloud.admin.manage.service.service.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.PageViewReport;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.commons.utils.Data;

/**
 * PV报表
 *
 * @author huangGuiChuan
 * @date 2020/11/05
 */
public interface PageViewReportService {

    /**
     * 查询PVUV统计
     *
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    ScriptPage<PvUvReport> findPageViewReportPvUvList();

    /**
     * 查询PV报表
     *
     * @param ajaxPage AjaxPage
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    ScriptPage<PageViewReport> findPageViewReportList(AjaxPage ajaxPage, PageViewReport pageViewReport);

    /**
     * 查询PV报表 柱状图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    Data findPageViewColumnarList(PageViewReport pageViewReport);

    /**
     * 查询PV报表 饼图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    Data findPageViewCountPie(PageViewReport pageViewReport);

    /**
     * 查询 uv 饼图
     *
     * @author huanggc
     * @date 2021/03/15
     * @return Data
     **/
    Data findUvPageViewCountPie(PageViewReport pageViewReport);
}
