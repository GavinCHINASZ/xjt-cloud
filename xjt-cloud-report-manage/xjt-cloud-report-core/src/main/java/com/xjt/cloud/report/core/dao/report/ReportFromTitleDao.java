package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.ReportFromTitle;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日报报表 标题 Dao
 *
 * @author huanggc
 * @date 2020/07/06
 */
@Repository
public interface ReportFromTitleDao {

    /**
     * 功能描述: 查看 日报报表 标题
     *
     * @param reportFromTitle ReportFromTitle
     * @author huanggc
     * @date 2020/07/06
     * @return List<ReportFromTitle>
     **/
    List<ReportFromTitle> findReportFromTitleList(ReportFromTitle reportFromTitle);

    /**
     * 功能描述: 保存/新增 日报报表 标题
     *
     * @param reportFromTitle ReportFromTitle
     * @author huanggc
     * @date 2020/07/06
     * @return Integer
     */
    Integer saveReportFromTitle(ReportFromTitle reportFromTitle);

    /**
     * 功能描述: 修改 日报报表 标题
     *
     * @param reportFromTitle ReportFromTitle
     * @author huanggc
     * @date 2020/07/06
     * @return Integer
     */
    Integer updateReportFromTitle(ReportFromTitle reportFromTitle);

    /**
     * 功能描述: 删除 日报报表 标题
     *
     * @param reportFromTitle ReportFromTitle
     * @author huanggc
     * @date 2020/07/06
     * @return Integer
     */
    Integer deletedReportFromTitle(ReportFromTitle reportFromTitle);
}
