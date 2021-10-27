package com.xjt.cloud.admin.manage.dao.report;

import com.xjt.cloud.admin.manage.entity.report.PageViewReport;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PageViewReportDao
 *
 * @author huanggc
 * @date 2020/11/05
 **/
@Repository
public interface PageViewReportDao {

    /**
     * 查询 pV uV 统计
     *
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    List<PvUvReport> findPageViewReportPvUvList();

    /**
     * 查询 PV报表list
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/05
     * @return List<PageViewReport>
     **/
    List<PageViewReport> findPageViewReportList(PageViewReport pageViewReport);

    /**
     * 查询PV报表
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/05
     * @return int
     **/
    int findPageViewReportCount(PageViewReport pageViewReport);

    /**
     * 查询PV报表 饼图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return PageViewReport
     **/
    PageViewReport findPageViewCountPie(PageViewReport pageViewReport);

    /**
     * 查询PV报表 柱状图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return List
     **/
    List<PageViewReport> findPageViewColumnarList(PageViewReport pageViewReport);

    /**
     * 查询 uv 饼图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2021/03/15
     * @return PageViewReport
     **/
    PageViewReport findUvPageViewCountPie(PageViewReport pageViewReport);

    /**
     * 查询PVUV 柱状/拆线图
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return List
     **/
    List<PageViewReport> findPvColumnarList(PageViewReport pageViewReport);

    /**
     * 查询PVUV 拆线图 月
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return List
     **/
    List<PageViewReport> findMonthPvColumnarList(PageViewReport pageViewReport);

    /**
     * 查询PVUV 拆线图 年
     *
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/18
     * @return List
     **/
    List<PageViewReport> findYearPvColumnarList(PageViewReport pageViewReport);
}
