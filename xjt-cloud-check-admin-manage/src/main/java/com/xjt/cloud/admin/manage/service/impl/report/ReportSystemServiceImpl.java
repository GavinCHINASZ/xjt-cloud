package com.xjt.cloud.admin.manage.service.impl.report;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.report.ReportSystemDao;
import com.xjt.cloud.admin.manage.entity.report.ReportSystem;
import com.xjt.cloud.admin.manage.service.service.report.ReportSystemService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 报表
 * @Auther: huangGuiChuan
 * @Date: 2019/12/11
 */
@Service
public class ReportSystemServiceImpl extends AbstractAdminService implements ReportSystemService {
    @Autowired
    private ReportSystemDao reportSystemDao;

    /**
     * 功能描述:  查询 报表系统列表
     *
     * @param ajaxPage
     * @param reportSystem 报表系统项
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.report.ReportSystem>
     */
    @Override
    public ScriptPage<ReportSystem> findReportSystemList(AjaxPage ajaxPage, ReportSystem reportSystem) {
        reportSystem = asseFindObj(reportSystem, ajaxPage);// 分页
        List<ReportSystem> reportSystemList = reportSystemDao.findReportSystemList(reportSystem);
        Integer totalCount = reportSystemDao.findTotalCount(reportSystem);

        return asseScriptPage(reportSystemList, totalCount);
    }

    /**
     * 功能描述:  删除 报表系统
     *
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     * @auther huangGuiChuan
     * @date 2019/12/12
     */
    @Override
    public Data delReportSystem(ReportSystem reportSystem) {
        int num = reportSystemDao.modifyReportSystem(reportSystem);
        return asseData(num);
    }

    /**
     * 功能描述:  更新/修改 报表系统
     *
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     */
    @Override
    public Data modifyReportSystem(ReportSystem reportSystem) {
        Integer num = reportSystemDao.modifyReportSystem(reportSystem);
        return asseData(num);
    }

    /**
     * 功能描述:  新增/保存 报表系统
     *
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     */
    @Override
    public Data saveReportSystem(ReportSystem reportSystem) {
        Integer num = reportSystemDao.saveReportSystem(reportSystem);
        return asseData(num);
    }

}
