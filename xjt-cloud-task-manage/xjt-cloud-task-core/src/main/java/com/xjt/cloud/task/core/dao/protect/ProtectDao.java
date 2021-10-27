package com.xjt.cloud.task.core.dao.protect;

import com.xjt.cloud.task.core.entity.protect.FireAlarmEvent;
import com.xjt.cloud.task.core.entity.protect.IntegratedMonitoring;
import com.xjt.cloud.task.core.entity.protect.Protect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地铁 班前防护 Dao
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public interface ProtectDao {

    /**
     * 查询 班前防护
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return Protect
     */
    Protect findProtect(Protect protect);

    /**
     * 查询 班前防护 数量
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return Integer
     */
    Integer findProtectListTotalCount(Protect protect);

    /**
     * 查询 班前防护 list
     *
     * @param protect 参数
     * @author 班前防护
     * @date 2020/09/27
     * @return List<Protect>
     */
    List<Protect> findProtectList(Protect protect);

    /**
     * 编辑 班前防护 list
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return int
     */
    int updateProtect(Protect protect);

    /**
     * 新增 班前防护
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void saveProtect(Protect protect);

    /**
     * 新增 班前防护图片
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void saveProtectImageList(Protect protect);

    /**
     * 删除 班前防护图片
     *
     * @param protect 班前防护
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void deletedProtectImageList(Protect protect);

    /**
     * 查询 班前防护图片
     *
     * @param id 班前防护id
     * @author huangGuiChuan
     * @date 2020/09/29
     * @return String[]
     */
    String[] findProtectImageUrls(Long id);

    /**
     * 查询用户是否在项目的作业中
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return com.xjt.cloud.commons.utils.Data
     */
    int findIsProtectPerson(Protect protect);

    /**
     * 保存 作业和火警事件关联
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2020/10/10
     */
    void saveProtectFireEvent(Protect protect);

    /**
     * 删除 作业和火警事件关联
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2020/10/10
     */
    void deletedProtectFireEvent(Protect protect);

    /**
     * 删除 作业和火警事件关联
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2020/10/10
     * @return String
     */
    String findUserNameString(Protect protect);

    /**
     * 查询 火警事件
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2021/03/05
     * @return List<FireAlarmEvent>
     */
    List<FireAlarmEvent> findFireAlarmEventList(Protect protect);

    /**
     * 查询 火警事件
     *
     * @param id id
     * @author huangGuiChuan
     * @date 2021/03/05
     * @return Protect
     */
    Protect findProtectById(Long id);

    /**
     * 关闭 班前防护 list
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2021/03/09
     * @return com.xjt.cloud.commons.utils.Data
     */
    int closeProtect(Protect protect);

    /**
     * 查询 成员 所在的作业是否完成
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2021/03/3
     * @return int
     */
    int findUserProtectState(Protect protect);

    /**
     * 删除作业下的 综合监测
     *
     * @param protectId 作业ID
     * @author huangGuiChuan
     * @date 2021/03/3
     */
    void deletedIntegratedMonitoringByProtectId(@Param("protectId") Long protectId);

    /**
     * 保存 综合监测
     *
     * @param protectId 作业ID
     * @param integratedMonitoringList 综合监测
     * @author huangGuiChuan
     * @date 2021/03/3
     * @return int
     */
    int saveIntegratedMonitoring(@Param("protectId") Long protectId, List<IntegratedMonitoring> integratedMonitoringList);

    /**
     * 查询 作业的综合监测
     *
     * @param integratedMonitoring 综合监测实体
     * @author huangGuiChuan
     * @date 2021/04/02
     * @return List<IntegratedMonitoring>
     */
    List<IntegratedMonitoring> findIntegratedMonitoringList(IntegratedMonitoring integratedMonitoring);

    /**
     * 查询 作业的综合监测
     *
     * @param protect 作业实休
     * @author huangGuiChuan
     * @date 2021/04/08
     * @return int
     */
    Integer findSign(Protect protect);
}
