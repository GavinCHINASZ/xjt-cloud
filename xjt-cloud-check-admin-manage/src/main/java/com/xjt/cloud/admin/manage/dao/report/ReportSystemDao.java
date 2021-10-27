package com.xjt.cloud.admin.manage.dao.report;

import com.xjt.cloud.admin.manage.entity.report.ReportSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * 报表系统 Dao
 * @Author huanggc
 * @Date 2019/12/11
 **/
@Repository
public interface ReportSystemDao {

    /**
     *
     * 功能描述:  查询 报表系统列表
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param reportSystem 报表系统项
     * @return List<ReportSystem>
     */
    List<ReportSystem> findReportSystemList(ReportSystem reportSystem);

    /**
     *
     * 功能描述:  查询 报表系统 数量
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param reportSystem 报表系统项
     * @return Integer
     */
    Integer findTotalCount(ReportSystem reportSystem);

    /**
     *
     * 功能描述:  修改 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/12
     * @param reportSystem 报表系统项
     * @return Integer 更新成功数量
     */
    Integer modifyReportSystem(ReportSystem reportSystem);

    /**
     * 功能描述:  新增/保存 报表系统
     *
     * @auther: huangGuiChuan
     * @date: 2019/12/13
     * @param reportSystem 报表系统项
     * @return com.xjt.cloud.commons.utils.Data;
     */
    Integer saveReportSystem(ReportSystem reportSystem);
}
