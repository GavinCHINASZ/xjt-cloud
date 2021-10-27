package com.xjt.cloud.maintenance.core.dao.fault;

import com.xjt.cloud.maintenance.core.entity.fault.RepairProgress;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报修进度 Dao
 * 日志/记录
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Repository
public interface RepairProgressDao {
    /**
     * 功能描述:查询 日志/记录 列表
     *
     * @param repairProgress RepairProgress
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/22
     */
    Integer findRepairProgressTotalCount(RepairProgress repairProgress);

    /**
     * 功能描述:查询 报修进度 列表
     *
     * @param repairProgress 报修进度 实体
     * @author huanggc
     * @date 2019/09/02
     * @return List<RepairProgress> 查询到的 报修进度 实体list
     */
    List<RepairProgress> findRepairProgressList(RepairProgress repairProgress);

    /**
     * 功能描述: 删除
     *
     * @param repairProgress RepairProgress
     * @author huanggc
     * @date 2019/09/06
     * @return Integer 删除成功条数
     */
    int deletedRepairProgressList(RepairProgress repairProgress);

    /**
     * 功能描述: 新增 报修进茺
     *
     * @param repairProgress 报修进度 实体
     * @author huanggc
     * @date 2019/09/06
     * @return Integer 保存成功条数
     */
    int saverRepairProgress(RepairProgress repairProgress);

    /**
     * 功能描述: 批量新增 报修进度
     *
     * @param repairProgressList 要批量新增的 报修进度 实体List
     * @author huanggc
     * @date 2019/11/13
     * @return Integer 保存成功条数
     */
    int saverRepairProgressList(List<RepairProgress> repairProgressList);
}
