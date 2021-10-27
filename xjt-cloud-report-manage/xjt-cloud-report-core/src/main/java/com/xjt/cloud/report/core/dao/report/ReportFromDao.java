package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.ReportFrom;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日报报表 Dao
 *
 * @author huanggc
 * @date 2020/07/06
 */
@Repository
public interface ReportFromDao {

    /**
     * 功能描述: 查看 日报报表
     *
     * @param reportFrom
     * @author huanggc
     * @date 2020/07/06
     * @return List<ReportFrom>
     **/
    List<ReportFrom> findReportFromList(ReportFrom reportFrom);

    /**
     * 功能描述: 保存/新增 日报报表
     *
     * @param reportFrom
     * @author huanggc
     * @date 2020/07/06
     * @return Integer
     */
    Integer saveReportFrom(ReportFrom reportFrom);

    /**
     * 功能描述: 查询 日报报表
     *
     * @param reportFrom
     * @author huanggc
     * @date 2020/07/07
     * @return ReportFrom
     */
    ReportFrom findReportFromOne(ReportFrom reportFrom);

    /**
     * 功能描述: 查询 填写过的日报 日期
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/07
     * @return: com.xjt.cloud.commons.utils.Data
     */
    List<ReportFrom> findReportFromDate(ReportFrom reportFrom);

    /**
     * 功能描述: 查询 填写过的日报 日期
     *
     * @param id
     * @auther huanggc
     * @date 2020/07/09
     * @return ReportFrom
     */
    ReportFrom findReportFromById(Long id);
}
