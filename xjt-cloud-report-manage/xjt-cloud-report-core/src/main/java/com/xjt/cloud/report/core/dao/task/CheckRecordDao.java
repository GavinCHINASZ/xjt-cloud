package com.xjt.cloud.report.core.dao.task;

import com.xjt.cloud.report.core.entity.task.CheckRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 巡检记录 Dao
 * @Author huanggc
 * @Date 2019/11/08
 **/
@Repository
public interface CheckRecordDao {

    List<CheckRecord> findCheckResultBySql(CheckRecord checkRecordEntity);

    /**
     * 功能描述: 根据 巡检记录实体 获取
     *
     * @param checkRecord 巡检记录实体
     * @auther: huanggc
     * @date: 2019/11/11
     * @return: List<CheckRecord>
     */
    List<CheckRecord> findGroupBySql(CheckRecord checkRecord);
}
