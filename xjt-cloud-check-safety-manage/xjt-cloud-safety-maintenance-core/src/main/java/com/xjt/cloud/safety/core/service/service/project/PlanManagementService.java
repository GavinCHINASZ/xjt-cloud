package com.xjt.cloud.safety.core.service.service.project;


import com.xjt.cloud.commons.utils.Data;

/**
 * PlanManagementService
 * 计划管理
 *
 * @author huanggc
 * @date 2019-08-20 16:37
 **/
public interface PlanManagementService {

    /**
     * 保存 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data savePlanManagement(String json);

    /**
     * 查询 维保计划管理首页列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data findPlanManagementPage(String json);

    /**
     * 查询 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data findPlanManagementList(String json);

    /**
     * 查询 计划管理 已生成了报告
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data findPlanManagementReportList(String json);

    /**
     * 删除 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data delPlanManagement(String json);

    /**
     * 更新 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    Data updatePlanManagement(String json);

    /**
     * 查询计划维设备系统汇总报表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    Data findPlanDeviceSysReportList(String json) ;

    /**
     * 查询计划维设备系统巡检测试列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    Data findPlanDeviceSysCheckReportList(String json);
}
