package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.MaintenanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 维保报告
 *
 * @author huanggc
 * @date 2020-04-10
 */
@RestController
@RequestMapping("/maintenance/report/")
public class MaintenanceReportController extends AbstractController {

    @Autowired
    private MaintenanceReportService maintenanceReportService;

    /**
     * 生成 维保报告
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "/saveMaintenanceReport/{projectId}")
    public Data saveMaintenanceReport(String json, HttpServletResponse response) {
        return maintenanceReportService.saveMaintenanceReport(json, response);
    }

    /**
     * 报告查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     **/
    @RequestMapping(value = "/findReport/{projectId}")
    public Data findReport(String json) {
        return maintenanceReportService.findReport(json);
    }

    /**
     * 报告下载
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020-04-10
     **/
    @RequestMapping(value = "/downReport/{projectId}")
    public void downReport(String json, HttpServletResponse response) {
        maintenanceReportService.downReport(json, response);
    }

    /**
     * 打印标签
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-14
     **/
    @RequestMapping(value = "/saveLabel/{projectId}")
    public Data saveLabel(String json, HttpServletResponse response) {
        return maintenanceReportService.saveLabel(json, response);
    }

}
