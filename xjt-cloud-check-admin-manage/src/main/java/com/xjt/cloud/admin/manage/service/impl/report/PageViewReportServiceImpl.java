package com.xjt.cloud.admin.manage.service.impl.report;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.report.PageViewReportDao;
import com.xjt.cloud.admin.manage.entity.report.PageViewReport;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.service.service.report.PageViewReportService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * PageView报表
 *
 * @author huangGuiChuan
 * @date 2020/11/05
 */
@Service
public class PageViewReportServiceImpl extends AbstractAdminService implements PageViewReportService {
    @Autowired
    private PageViewReportDao pageViewReportDao;

    /**
     * 查询 pV uV 统计
     *
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     * @author huanggc
     * @date 2020/11/05
     **/
    @Override
    public ScriptPage<PvUvReport> findPageViewReportPvUvList() {
        List<PvUvReport> pageViewReportPvUvList = pageViewReportDao.findPageViewReportPvUvList();
        return asseScriptPage(pageViewReportPvUvList, pageViewReportPvUvList.size());
    }

    /**
     * 查询PV报表
     *
     * @param ajaxPage       AjaxPage
     * @param pageViewReport PageViewReport
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     * @author huanggc
     * @date 2020/11/05
     **/
    @Override
    public ScriptPage<PageViewReport> findPageViewReportList(AjaxPage ajaxPage, PageViewReport pageViewReport) {
        pageViewReport = asseFindObj(pageViewReport, ajaxPage);

        if (null == pageViewReport.getOrderCols()) {
            String[] orderCols = {"num"};
            pageViewReport.setOrderCols(orderCols);
            pageViewReport.setOrderDesc(true);
        }
        List<PageViewReport> pageViewReportList = pageViewReportDao.findPageViewReportList(pageViewReport);
        int num = pageViewReportDao.findPageViewReportCount(pageViewReport);
        return asseScriptPage(pageViewReportList, num);
    }

    /**
     * 查询PV报表 柱状/拆线图
     *
     * @return Data
     * @author huanggc
     * @date 2020/11/18
     **/
    @Override
    public Data findPageViewColumnarList(PageViewReport pageViewReport) {
        String startTimeStr = pageViewReport.getStartTimeStr();
        String endTimeStr = pageViewReport.getEndTimeStr();

        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(startTimeStr)) {
            startTime = DateUtils.strToY_M_D(startTimeStr);
        }
        if (StringUtils.isNotEmpty(endTimeStr)) {
            endTime = DateUtils.strToY_M_D(endTimeStr);
        }

        List<PageViewReport> pvList;
        if (startTime != null && endTime != null) {
            pageViewReport.setStartTime(startTime);
            pageViewReport.setEndTime(endTime);
            if (startTime == endTime) {
                // 同一天
                pvList = pageViewReportDao.findPvColumnarList(pageViewReport);
            } else if (DateUtils.matchSameMonth(startTime, endTime, 1)) {
                // 同一月
                pvList = pageViewReportDao.findMonthPvColumnarList(pageViewReport);
            } else {
                // 查询 年
                pvList = pageViewReportDao.findYearPvColumnarList(pageViewReport);
            }
        } else {
            // 默认当天
            pvList = pageViewReportDao.findPvColumnarList(pageViewReport);
        }
        return asseData(pvList);
    }

    /**
     * 查询PV报表 饼图
     *
     * @return Data
     * @author huanggc
     * @date 2020/11/18
     **/
    @Override
    public Data findPageViewCountPie(PageViewReport pageViewReport) {
        PageViewReport entity = pageViewReportDao.findPageViewCountPie(pageViewReport);
        return asseData(entity);
    }

    /**
     * 查询 uv 饼图
     *
     * @return Data
     * @author huanggc
     * @date 2021/03/15
     **/
    @Override
    public Data findUvPageViewCountPie(PageViewReport pageViewReport) {
        PageViewReport entity = pageViewReportDao.findUvPageViewCountPie(pageViewReport);
        return asseData(entity);
    }

}
