package com.xjt.cloud.admin.manage.controller.report;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.report.PvUvReport;
import com.xjt.cloud.admin.manage.entity.report.UserActiveReport;
import com.xjt.cloud.admin.manage.service.service.report.UserActiveReportService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户活跃 Controller
 *
 * @author huangGuiChuan
 * @date 2021/03/03
 */
@Controller
@RequestMapping("/userActiveReport/")
public class UserActiveReportController extends AbstractController {
    @Autowired
    private UserActiveReportService userActiveReportService;

    /**
     * 功能描述: 打开 用户活跃 页面
     *
     * @author huanggc
     * @date 2020/11/05
     * @return ModelAndView
     */
    @RequestMapping("toUserActiveReportPage")
    public ModelAndView toUserActiveReportPage(){
        // viewName=jsp的路径
        return new ModelAndView("pv/userActiveReportList");
    }

    /**
     * 查询 UV统计
     *
     * @author huanggc
     * @date 2021/03/15
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    @RequestMapping("findUvCountTableList")
    @ResponseBody
    public ScriptPage<PvUvReport> findUvCountTableList(){
        return userActiveReportService.findUvCountTableList();
    }

    /**
     * 查询 用户活跃列表
     *
     * @param ajaxPage AjaxPage
     * @param userActiveReport UserActiveReport
     * @author huanggc
     * @date 2020/11/05
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<PageViewReport>
     **/
    @RequestMapping("findUserActiveReportList")
    @ResponseBody
    public ScriptPage<UserActiveReport> findPageViewReportList(AjaxPage ajaxPage, UserActiveReport userActiveReport){
        return userActiveReportService.findUserActiveReportList(ajaxPage, userActiveReport);
    }

    /**
     * 查询 用户活跃 柱状图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    @RequestMapping("findUserActiveColumnarList")
    @ResponseBody
    public Data findUserActiveColumnarList(UserActiveReport userActiveReport) {
        return userActiveReportService.findUserActiveColumnarList(userActiveReport);
    }

    /**
     * 查询 用户活跃 饼图
     *
     * @author huanggc
     * @date 2020/11/18
     * @return Data
     **/
    @RequestMapping("findUserActiveCountPie")
    @ResponseBody
    public Data findUserActiveCountPie(UserActiveReport userActiveReport) {
        return userActiveReportService.findUserActiveCountPie(userActiveReport);
    }
}
