package com.xjt.cloud.task.core.dao.protect;

import com.xjt.cloud.task.core.entity.protect.ProtectGrade;

import java.util.List;

/**
 * 地铁 防护等级 Dao
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public interface ProtectGradeDao {

    /**
     * 查询 防护等级 数量
     *
     * @param protectGrade 防护等级
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return ProtectGrade
     */
    Integer findProtectGradeListTotalCount(ProtectGrade protectGrade);

    /**
     * 查询 防护等级名称 list
     *
     * @param protectGrade 防护等级
     * @author huangGuiChuan
     * @date 2020/09/28
     * @return List<ProtectGrade>
     */
    List<ProtectGrade> findProtectGradeNameList(ProtectGrade protectGrade);

    /**
     * 查询 防护等级
     *
     * @param protectGrade 防护等级
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return ProtectGrade
     */
    ProtectGrade findProtectGrade(ProtectGrade protectGrade);

    /**
     * 查询 防护等级 list
     *
     * @param protectGrade 防护等级
     * @author 防护等级
     * @date 2020/09/27
     * @return List<ProtectGrade>
     */
    List<ProtectGrade> findProtectGradeList(ProtectGrade protectGrade);

    /**
     * 编辑 防护等级 list
     *
     * @param protectGrade 防护等级
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void updateProtectGrade(ProtectGrade protectGrade);

    /**
     * 新增 防护等级
     *
     * @param protectGrade 防护等级
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void saveProtectGrade(ProtectGrade protectGrade);
}
