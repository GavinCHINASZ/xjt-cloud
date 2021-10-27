package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.ReportFromRecord;
import com.xjt.cloud.report.core.entity.report.ReportFromTitle;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日报报表 内容 Dao
 *
 * @author huanggc
 * @date 2020/07/06
 */
@Repository
public interface ReportFromRecordDao {

    /**
     * 功能描述: 批量保存 日报内容
     *
     * @param reportFromRecordList
     * @author huanggc
     * @date 2020/07/06
     * @return Integer
     **/
    Integer saveReportFromRecordList(List<ReportFromRecord> reportFromRecordList);

    /**
     * 功能描述: 查询 日报内容
     *
     * @param reportFromRecord
     * @author huanggc
     * @date 2020/07/07
     * @return List<ReportFromRecord>
     **/
    List<ReportFromRecord> findReportFromRecordList(ReportFromRecord reportFromRecord);

    /**
     * 功能描述: 查询 日报内容
     *
     * @param reportFromRecord
     * @author huanggc
     * @date 2020/07/07
     * @return List<ReportFromRecord>
     **/
    List<ReportFromRecord> findReportFromRecordLists(ReportFromRecord reportFromRecord);
}
