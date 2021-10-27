package com.xjt.cloud.maintenance.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.service.service.project.PlanManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ProjectLogController
 * 计划管理
 *
 * @author huanggc
 * @date 2021/04/17
 */
@RestController
@RequestMapping("/plan/")
public class PlanManagementController extends AbstractController {

    @Autowired
    private PlanManagementService planManagementService;

    /**
     * 保存 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "savePlanManagement/{projectId}")
    public Data savePlanManagement(String json) {
        return planManagementService.savePlanManagement(json);
    }

    /**
     * 查询 维保计划管理首页列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "findPlanManagementPage/{projectId}")
    public Data findPlanManagementPage(String json) {
        return planManagementService.findPlanManagementPage(json);
    }

    /**
     * 查询 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "findPlanManagementList/{projectId}")
    public Data findPlanManagementList(String json) {
        return planManagementService.findPlanManagementList(json);
    }

    /**
     * 查询 计划管理 已生成了报告
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "findPlanManagementReportList/{projectId}")
    public Data findPlanManagementReportList(String json) {
        return planManagementService.findPlanManagementReportList(json);
    }

    /**
     * 删除 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "delPlanManagement/{projectId}")
    public Data delPlanManagement(String json) {
        return planManagementService.delPlanManagement(json);
    }

    /**
     * 更新 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @RequestMapping(value = "updatePlanManagement/{projectId}")
    public Data updatePlanManagement(String json) {
        return planManagementService.updatePlanManagement(json);
    }

    /**
     * 查询计划维设备系统汇总报表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    @RequestMapping(value = "findPlanDeviceSysReportList/{projectId}")
    public Data findPlanDeviceSysReportList(String json) {
        return planManagementService.findPlanDeviceSysReportList(json);
    }

    /**
     * 查询计划维设备系统巡检测试列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    @RequestMapping(value = "findPlanDeviceSysCheckReportList/{projectId}")
    public Data findPlanDeviceSysCheckReportList(String json) {
        return planManagementService.findPlanDeviceSysCheckReportList(json);
    }
}
