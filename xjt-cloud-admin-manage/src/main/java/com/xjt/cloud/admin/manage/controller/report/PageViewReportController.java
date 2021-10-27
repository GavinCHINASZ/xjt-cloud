package com.xjt.cloud.admin.manage.controller.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.PageViewReport;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.service.service.report.PageViewReportService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * PV Controller
 *
 * @author huangGuiChuan
 * @date 2020/11/04
 */
@Controller
@RequestMapping("/pageViewReport/")
public class PageViewReportController extends AbstractController {
    @Autowired
    private PageViewReportService pageViewReportService;

    /**
     * 功能描述: 打开 PV报表管理 页面
     *
     * @author huanggc
     * @date 2020/11/05
     * @return ModelAndView
     */
    @RequestMapping("toPageViewReportPage")
    public ModelAndView toPageViewReportPage(){
        // viewName=jsp的路径
        return new ModelAndView("pv/pageViewReportList");
    }

    /**
     * 查询PVUV统计
     *
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    @RequestMapping("findPageViewReportPvUvList")
    @ResponseBody
    public ScriptPage<PvUvReport> findPageViewReportPvUvList(){
        return pageViewReportService.findPageViewReportPvUvList();
    }

    /**
     * 查询PV报表
     *
     * @param ajaxPage AjaxPage
     * @param pageViewReport PageViewReport
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    @RequestMapping("findPageViewReportList")
    @ResponseBody
    public ScriptPage<PageViewReport> findPageViewReportList(AjaxPage ajaxPage, PageViewReport pageViewReport){
        return pageViewReportService.findPageViewReportList(ajaxPage, pageViewReport);
    }

    /**
     * 查询PV报表 柱状图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    @RequestMapping("findPageViewColumnarList")
    @ResponseBody
    public Data findPageViewColumnarList(PageViewReport pageViewReport) {
        return pageViewReportService.findPageViewColumnarList(pageViewReport);
    }

    /**
     * 查询PV报表 饼图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    @RequestMapping("findPageViewCountPie")
    @ResponseBody
    public Data findPageViewCountPie(PageViewReport pageViewReport) {
        return pageViewReportService.findPageViewCountPie(pageViewReport);
    }

    /**
     * 查询 uv 饼图
     *
     * @author huanggc
     * @date 2021/03/15
     * @return Data
     **/
    @RequestMapping("findUvPageViewCountPie")
    @ResponseBody
    public Data findUvPageViewCountPie(PageViewReport pageViewReport) {
        return pageViewReportService.findUvPageViewCountPie(pageViewReport);
    }
}
