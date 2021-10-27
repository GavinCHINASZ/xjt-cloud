package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 报表Dao
 * @Author huanggc
 * @Date 2019/11/05
 **/
@Repository
public interface ReportDao {

    List<Report> findByPeriodAndNoBySql(Report report);
}
