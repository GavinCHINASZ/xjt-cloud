package com.xjt.cloud.admin.manage.service.service.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.ReportSystem;
import com.xjt.cloud.commons.utils.Data;

/**
 *
 * 报表
 * @Auther: huangGuiChuan
 * @Date: 2019/12/11
 */
public interface ReportSystemService {

    /**
     *
     * 功能描述:  查询 报表系统列表
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param ajaxPage
     * @param reportSystem 报表系统项
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.report.ReportSystem>
     */
    ScriptPage<ReportSystem> findReportSystemList(AjaxPage ajaxPage, ReportSystem reportSystem);

    /**
     *
     * 功能描述:  删除 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param reportSystem 报表系统项
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.report.ReportSystem>
     */
    Data delReportSystem(ReportSystem reportSystem);

    /**
     * 功能描述:  更新/修改 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    Data modifyReportSystem(ReportSystem reportSystem);

    /**
     * 功能描述:  新增/保存 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    Data saveReportSystem(ReportSystem reportSystem);
}
