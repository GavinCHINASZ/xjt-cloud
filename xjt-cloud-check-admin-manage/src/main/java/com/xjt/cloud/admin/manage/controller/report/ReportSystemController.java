package com.xjt.cloud.admin.manage.controller.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.ReportSystem;
import com.xjt.cloud.admin.manage.service.service.report.ReportSystemService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 报表系统 Controller
 * @Auther: huangGuiChuan
 * @Date: 2019/12/12
 */
@Controller
@RequestMapping("/reportSystem")
public class ReportSystemController extends AbstractController {
    @Autowired
    private ReportSystemService reportSystemService;

    /**
     *
     * 功能描述: 打开 报表巡查项目管理页面
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @return
     */
    @RequestMapping("/toReportSystemListPage")
    public ModelAndView toReportSystemListPage(){
        return new ModelAndView("report/reportSystemList");
    }

    /**
     *
     * 功能描述:  查询 报表系统列表
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param ajaxPage
     * @param reportSystem 报表系统项
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.report.ReportSystem>
     */
    @RequestMapping(value = "/findReportSystemList")
    @ResponseBody
    public ScriptPage<ReportSystem> findReportSystemList(AjaxPage ajaxPage, ReportSystem reportSystem){
        return reportSystemService.findReportSystemList(ajaxPage, reportSystem);
    }

    /**
     *
     * 功能描述:  删除 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    @RequestMapping(value = "/delReportSystem")
    @ResponseBody
    public Data delReportSystem(ReportSystem reportSystem){
        return reportSystemService.delReportSystem(reportSystem);
    }

    /**
     * 功能描述:  更新/修改 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    @RequestMapping(value = "/modifyReportSystem")
    @ResponseBody
    public Data modifyReportSystem(ReportSystem reportSystem){
        return reportSystemService.modifyReportSystem(reportSystem);
    }

    /**
     * 功能描述:  新增/保存 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    @RequestMapping(value = "/saveReportSystem")
    @ResponseBody
    public Data saveReportSystem(ReportSystem reportSystem){
        return reportSystemService.saveReportSystem(reportSystem);
    }

}
