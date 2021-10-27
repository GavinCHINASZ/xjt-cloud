package com.xjt.cloud.admin.manage.service.impl.report;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.report.UserActiveReportDao;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.entity.report.UserActiveReport;
import com.xjt.cloud.admin.manage.service.service.report.UserActiveReportService;
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
public class UserActiveReportServiceImpl extends AbstractAdminService implements UserActiveReportService {
    @Autowired
    private UserActiveReportDao userActiveReportDao;

    /**
     * 查询 UV统计
     *
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     * @author huanggc
     * @date 2021/03/15
     **/
    @Override
    public ScriptPage<PvUvReport> findUvCountTableList() {
        List<PvUvReport> pageViewReportPvUvList = userActiveReportDao.findUvCountTableList();
        return asseScriptPage(pageViewReportPvUvList, pageViewReportPvUvList.size());
    }

    /**
     * 查询PV报表
     *
     * @param ajaxPage         AjaxPage
     * @param userActiveReport UserActiveReport
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     * @author huanggc
     * @date 2020/11/05
     **/
    @Override
    public ScriptPage<UserActiveReport> findUserActiveReportList(AjaxPage ajaxPage, UserActiveReport userActiveReport) {
        userActiveReport = asseFindObj(userActiveReport, ajaxPage);

        if (null == userActiveReport.getOrderCols()) {
            String[] orderCols = {"num"};
            userActiveReport.setOrderCols(orderCols);
            userActiveReport.setOrderDesc(true);
        }
        List<UserActiveReport> pageViewReportList = userActiveReportDao.findUserActiveReportList(userActiveReport);
        int num = userActiveReportDao.findUserActiveReportCount(userActiveReport);
        return asseScriptPage(pageViewReportList, num);
    }

    /**
     * 查询 用户活跃 柱状图
     *
     * @return Data
     * @author huanggc
     * @date 2020/11/18
     **/
    @Override
    public Data findUserActiveColumnarList(UserActiveReport userActiveReport) {
        String startTimeStr = userActiveReport.getStartTimeStr();
        String endTimeStr = userActiveReport.getEndTimeStr();

        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(startTimeStr)) {
            startTime = DateUtils.strToY_M_D(startTimeStr);
        }
        if (StringUtils.isNotEmpty(endTimeStr)) {
            endTime = DateUtils.strToY_M_D(endTimeStr);
        }

        List<UserActiveReport> userActiveReportList;
        if (startTime != null && endTime != null) {
            userActiveReport.setStartTime(startTime);
            userActiveReport.setEndTime(endTime);

            if (startTime == endTime) {
                // 同一天
                userActiveReportList = userActiveReportDao.findUserActiveColumnarList(userActiveReport);
            } else if (DateUtils.matchSameMonth(startTime, endTime, 1)) {
                // 同一月
                userActiveReportList = userActiveReportDao.findMonthUserActiveColumnarList(userActiveReport);
            } else {
                // 查询 年
                userActiveReportList = userActiveReportDao.findYearUserActiveColumnarList(userActiveReport);
            }
        } else {
            // 默认当天
            userActiveReportList = userActiveReportDao.findUserActiveColumnarList(userActiveReport);
        }

        return asseData(userActiveReportList);
    }

    /**
     * 查询 用户活跃 饼图
     *
     * @return Data
     * @author huanggc
     * @date 2020/11/18
     **/
    @Override
    public Data findUserActiveCountPie(UserActiveReport userActiveReport) {
        UserActiveReport entity = userActiveReportDao.findUserActiveCountPie(userActiveReport);
        return asseData(entity);
    }

}
