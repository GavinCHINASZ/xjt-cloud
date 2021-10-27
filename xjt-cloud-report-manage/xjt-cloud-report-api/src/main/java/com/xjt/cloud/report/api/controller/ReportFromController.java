package com.xjt.cloud.report.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.ReportFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日报报表
 *
 * @author huangGuiCHuan
 * @Date: 2020/07/06
 * @Description: 日报报表 web控制层
 */
@RestController
@RequestMapping("/from")
public class ReportFromController extends AbstractController {

    @Autowired
    private ReportFromService reportFromService;

    /**
     * 功能描述: 查看 日报报表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findReportFromList/{projectId}")
    public Data findReportFromList(String json, HttpServletRequest request, HttpServletResponse response){
        return reportFromService.findReportFromList(json, request,response);
    }

    /**
     * 功能描述: 保存/新增 日报报表
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/06
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveReportFrom/{projectId}")
    public Data saveReportFrom(String json){
        return reportFromService.saveReportFrom(json);
    }

    /**
     * 功能描述: 查询 填写过的日报 日期
     *
     * @param json
     * @auther: huanggc
     * @date: 2020/07/07
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findReportFromDate/{projectId}")
    public Data findReportFromDate(String json){
        return reportFromService.findReportFromDate(json);
    }

}
