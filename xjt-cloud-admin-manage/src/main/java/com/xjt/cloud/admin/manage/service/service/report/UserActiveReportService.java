package com.xjt.cloud.admin.manage.service.service.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.entity.report.UserActiveReport;
import com.xjt.cloud.commons.utils.Data;

/**
 * PV报表
 *
 * @author huangGuiChuan
 * @date 2020/11/05
 */
public interface UserActiveReportService {
    /**
     * 查询 UV统计
     *
     * @author huanggc
     * @date 2021/03/15
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    ScriptPage<PvUvReport> findUvCountTableList();

    /**
     * 查询 用户活跃
     *
     * @param ajaxPage AjaxPage
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    ScriptPage<UserActiveReport> findUserActiveReportList(AjaxPage ajaxPage, UserActiveReport userActiveReport);

    /**
     * 查询 用户活跃 柱状图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    Data findUserActiveColumnarList(UserActiveReport userActiveReport);

    /**
     * 查询 用户活跃 饼图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    Data findUserActiveCountPie(UserActiveReport userActiveReport);
}
