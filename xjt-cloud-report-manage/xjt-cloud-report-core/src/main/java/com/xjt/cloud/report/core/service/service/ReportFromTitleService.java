package com.xjt.cloud.report.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * 日报报表 service接口
 *
 * @author huanggc
 * @date 2020/07/06
 */
public interface ReportFromTitleService {
    /**
     * 功能描述: 查看 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findReportFromTitleList(String json);

    /**
     * 功能描述: 保存/新增 日报报表 标题
     *
     * @param json
     * @auther huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveReportFromTitle(String json);

    /**
     * 功能描述: 修改 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateReportFromTitle(String json);

    /**
     * 功能描述: 删除 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data deletedReportFromTitle(String json);
}
