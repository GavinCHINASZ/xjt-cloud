package com.xjt.cloud.report.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: huanggc
 * @Date: 2019/11/04
 * @Description: 报表
 */
@RestController
@RequestMapping("/report/")
public class ReportController extends AbstractController {
    @Autowired
    private ReportService reportService;

    /**
     * 功能描述: 报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: huanggc
     * @date: 2019/11/06
     */
    @RequestMapping(value = "report/{projectId}")
    public Data report(String json, HttpServletResponse response){
        return reportService.report(json, response);
    }

}
