package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.entity.project.CheckProjectReport;

/**
 * @ClassName CheckProjectReportService
 * @Description 评估项目报表记录
 * @Author wangzhiwen
 * @Date 2021/5/14 10:42
 **/
public interface CheckProjectReportService {
    /**
     * @Description 新增评估项目报表记录
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveCheckProjectReport(String json);

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
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyCheckProjectReport(String json);

    /**
     * @Description 查询评估项目报表记录列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findCheckProjectReportList(String json);
}
