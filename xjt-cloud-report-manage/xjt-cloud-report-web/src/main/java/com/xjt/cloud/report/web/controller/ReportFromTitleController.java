package com.xjt.cloud.report.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.ReportFromService;
import com.xjt.cloud.report.core.service.service.ReportFromTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日报报表 标题
 *
 * @author huangGuiCHuan
 * @Date: 2020/07/06
 * @Description: 日报报表 标题 web控制层
 */
@RestController
@RequestMapping("/title")
public class ReportFromTitleController extends AbstractController {

    @Autowired
    private ReportFromTitleService reportFromTitleService;

    /**
     * 功能描述: 查看 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findReportFromTitleList/{projectId}")
    public Data findReportFromList(String json){
        return reportFromTitleService.findReportFromTitleList(json);
    }

    /**
     * 功能描述: 保存/新增 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveReportFromTitle/{projectId}")
    public Data saveReportFromTitle(String json){
        return reportFromTitleService.saveReportFromTitle(json);
    }

    /**
     * 功能描述: 修改 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "updateReportFromTitle/{projectId}")
    public Data updateReportFromTitle(String json){
        return reportFromTitleService.updateReportFromTitle(json);
    }

    /**
     * 功能描述: 删除 日报报表 标题
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deletedReportFromTitle/{projectId}")
    public Data deletedReportFromTitle(String json){
        return reportFromTitleService.deletedReportFromTitle(json);
    }

}
