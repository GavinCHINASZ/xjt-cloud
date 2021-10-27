package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.CheckProjectReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CheckProjectReportDao
 * @Description 评估项目报表记录
 * @Author wangzhiwen
 * @Date 2021/5/14 10:42
 **/
@Repository
public interface CheckProjectReportDao {

    /**
     * @Description 新增评估项目报表记录
     *
     * @param checkProjectReport
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return int
     */
    int saveCheckProjectReport(CheckProjectReport checkProjectReport);

    /**
     * @Description 修改评估项目报表记录
     *
     * @param checkProjectReport
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return int
     */
    int modifyCheckProjectReport(CheckProjectReport checkProjectReport);

    /**
     * @Description 查询评估项目报表记录列表
     *
     * @param checkProjectReport
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<CheckProjectReport> findCheckProjectReportList(CheckProjectReport checkProjectReport);

    /**
     * @Description 查询评估项目报表记录列表
     *
     * @param checkProjectReport
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    int findCheckProjectReportListCount(CheckProjectReport checkProjectReport);
}
