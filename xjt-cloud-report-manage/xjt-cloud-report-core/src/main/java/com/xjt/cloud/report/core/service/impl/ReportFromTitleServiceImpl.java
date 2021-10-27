package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.dao.report.ReportFromTitleDao;
import com.xjt.cloud.report.core.entity.report.ReportFromTitle;
import com.xjt.cloud.report.core.service.service.ReportFromTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日报报表标题 service实现类
 *
 * @author huanggc
 * @date  2020/07/06
 */
@Service
public class ReportFromTitleServiceImpl extends AbstractService implements ReportFromTitleService {

    @Autowired
    private ReportFromTitleDao reportFromTitleDao;

    /**
     * 功能描述: 查看 日报报表 标题
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findReportFromTitleList(String json) {
        ReportFromTitle reportFromTitle = JSONObject.parseObject(json, ReportFromTitle.class);

        List<ReportFromTitle> reportFromTitleList = reportFromTitleDao.findReportFromTitleList(reportFromTitle);
        return asseData(reportFromTitleList);
    }

    /**
     * 功能描述: 保存/新增 日报报表 标题
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveReportFromTitle(String json) {
        ReportFromTitle reportFromTitle = JSONObject.parseObject(json, ReportFromTitle.class);
        reportFromTitleDao.saveReportFromTitle(reportFromTitle);
        return asseData(reportFromTitle);
    }

    /**
     * 功能描述: 修改 日报报表 标题
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateReportFromTitle(String json) {
        ReportFromTitle reportFromTitle = JSONObject.parseObject(json, ReportFromTitle.class);
        reportFromTitleDao.updateReportFromTitle(reportFromTitle);
        return asseData(reportFromTitle);
    }

    /**
     * 功能描述: 删除 日报报表 标题
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deletedReportFromTitle(String json) {
        ReportFromTitle reportFromTitle = JSONObject.parseObject(json, ReportFromTitle.class);
        Integer deletedReportFromTitleNum = reportFromTitleDao.deletedReportFromTitle(reportFromTitle);
        return asseData(deletedReportFromTitleNum);
    }
}
