package com.xjt.cloud.task.core.dao.protect;

import com.xjt.cloud.task.core.entity.protect.ProtectRecord;

import java.util.List;

/**
 * 地铁 班前防护记录 Dao
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public interface ProtectRecordDao {

    /**
     * 保存 班前防护记录
     *
     * @author huanggc
     * @param protectRecord ProtectRecord
     */
    void saveProtectRecord(ProtectRecord protectRecord);

    /**
     * 查询 班前防护记录 数量
     *
     * @param protectRecord ProtectRecord
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    Integer findProtectRecordTotalCount(ProtectRecord protectRecord);

    /**
     * 查询 班前防护记录
     *
     * @param protectRecord ProtectRecord
     * @author huangGuiChuan
     * @date 2020/12/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<ProtectRecord> findProtectRecordList(ProtectRecord protectRecord);
}
