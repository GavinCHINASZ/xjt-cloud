package com.xjt.cloud.admin.manage.dao.report;

import com.xjt.cloud.admin.manage.entity.report.PageViewReport;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.entity.report.UserActiveReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserActiveReportDao
 *
 * @author huanggc
 * @date 2020/11/05
 **/
@Repository
public interface UserActiveReportDao {

    /**
     * 查询 UV统计
     *
     * @author huanggc
     * @date 2021/03/15
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    List<PvUvReport> findUvCountTableList();

    /**
     * 查询 用户活跃list
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/05
     * @return List<UserActiveReport>
     **/
    List<UserActiveReport> findUserActiveReportList(UserActiveReport userActiveReport);

    /**
     * 查询 用户活跃 数量
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/05
     * @return int
     **/
    int findUserActiveReportCount(UserActiveReport userActiveReport);

    /**
     * 查询 用户活跃 饼图
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/18
     * @return List
     **/
    List<UserActiveReport> findUserActiveColumnarList(UserActiveReport userActiveReport);

    /**
     * 查询 用户活跃 柱状图
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/18
     * @return UserActiveReport
     **/
    UserActiveReport findUserActiveCountPie(UserActiveReport userActiveReport);

    /**
     * 查询 折线图 月
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/18
     * @return UserActiveReport
     **/
    List<UserActiveReport> findMonthUserActiveColumnarList(UserActiveReport userActiveReport);

    /**
     * 查询 折线图 年
     *
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/18
     * @return UserActiveReport
     **/
    List<UserActiveReport> findYearUserActiveColumnarList(UserActiveReport userActiveReport);
}
