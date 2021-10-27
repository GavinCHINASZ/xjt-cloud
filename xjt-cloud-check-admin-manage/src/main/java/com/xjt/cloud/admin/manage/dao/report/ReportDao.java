package com.xjt.cloud.admin.manage.dao.report;

import com.xjt.cloud.admin.manage.entity.report.Report;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 报表Dao
 * @Author huanggc
 * @Date 2019/12/11
 **/
@Repository
public interface ReportDao {

    List<Report> findByPeriodAndNoBySql(Report report);
}
