package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.Report;
import com.xjt.cloud.report.core.entity.report.ReportSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 报表系统 Dao
 * @Author huanggc
 * @Date 2019/11/08
 **/
@Repository
public interface ReportSystemDao {

    List<Report> findByReportNameAndNoBySql(Report report);
}
