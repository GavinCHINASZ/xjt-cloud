package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.PlanManagement;
import com.xjt.cloud.safety.core.entity.project.PlanManagementReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 计划管理
 *
 * @author huanggc
 * @date 2019-07-29 15:15
 */
@Repository
public interface PlanManagementDao {
    /**
     * 保存 计划管理
     *
     * @param planManagement 计划管理
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    int savePlanManagement(PlanManagement planManagement);

    /**
     * 保存 计划管理list
     *
     * @param planManagement 计划管理
     * @param planNameList 计划名称
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    int savePlanManagementList(@Param(value="planNameList") List<String> planNameList, @Param(value="planManagement") PlanManagement planManagement);

    int savePlanManagementLists(List<PlanManagement> planManagementList);

    /**
     * 查询 计划管理首页 数量
     *
     * @param planManagement 计划管理
     * @return Integer
     * @author huanggc
     * @date 2021/04/17
     **/
    Integer findPlanManagementPageCount(PlanManagement planManagement);

    /**
     * 查询 计划管理 数量
     *
     * @param planManagement 计划管理
     * @return Integer
     * @author huanggc
     * @date 2021/04/17
     **/
    Integer findPlanManagementCount(PlanManagement planManagement);

    /**
     * 查询 计划管理首页 列表
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author huanggc
     * @date 2021/04/17
     **/
    List<PlanManagementReport> findPlanManagementPage(PlanManagement planManagement);

    /**
     * 查询 计划管理
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author huanggc
     * @date 2021/04/17
     **/
    List<PlanManagement> findPlanManagementList(PlanManagement planManagement);

    /**
     * 查询 计划管理
     *
     * @param planManagement 计划管理
     * @return int
     * @author huanggc
     * @date 2021/04/17
     **/
    Integer findPlanManagementReportCount(PlanManagement planManagement);

    /**
     * 查询 计划管理 已生成了报告
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author huanggc
     * @date 2021/04/17
     **/
    List<PlanManagement> findPlanManagementReportList(PlanManagement planManagement);

    /**
     * 查询 查询计划维设备系统汇总报表
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    List<PlanManagementReport> findPlanDeviceSysReportList(PlanManagement planManagement);

    /**
     * 查询 查询计划维设备系统汇总报表
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    List<PlanManagementReport> findCheckRecordList(PlanManagement planManagement);

    /**
     * 查询 查询计划维设备系统巡检测试列表
     *
     * @param planManagement 计划管理
     * @return List<PlanManagement>
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    List<PlanManagementReport> findPlanDeviceSysCheckReportList(PlanManagement planManagement);

    /**
     * 查询 计划管理
     *
     * @param planManagement 计划管理
     * @return num
     * @author huanggc
     * @date 2021/04/17
     **/
    int savePlanDeviceSystem(PlanManagement planManagement);

    /**
     * 删除 计划管理
     *
     * @param planManagement 计划管理
     * @return int
     * @author huanggc
     * @date 2021/04/17
     **/
    int delPlanManagement(PlanManagement planManagement);

    /**
     * 删除 计划管理设备系统关联
     *
     * @param planManagement 计划管理
     * @author huanggc
     * @date 2021/04/17
     **/
    void delPlanDeviceSystem(PlanManagement planManagement);

    /**
     * 更新 计划管理
     *
     * @param planManagement 计划管理
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    int updatePlanManagement(PlanManagement planManagement);

    /**
     * 更新 计划管理
     *
     * @param planManagement 计划管理
     * @return PlanManagement
     * @author huanggc
     * @date 2021/04/17
     **/
    PlanManagement findPlanManagement(PlanManagement planManagement);

    List<PlanManagement> findPlanManagementListByPlanName(@Param(value="planNameList") List<String> planNameList,
                                                          @Param(value="planManagement") PlanManagement planManagement);
}
