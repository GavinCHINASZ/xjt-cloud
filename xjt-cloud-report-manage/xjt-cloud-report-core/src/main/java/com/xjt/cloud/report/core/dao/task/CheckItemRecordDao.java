package com.xjt.cloud.report.core.dao.task;

import com.xjt.cloud.report.core.entity.task.CheckItemRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName CheckItemRecord
 *@Author dwt
 *@Date 2019-07-25 17:35
 *@Description: 巡检项记录Dao
 *@Version 1.0
 */
@Repository
public interface CheckItemRecordDao {

    List<CheckItemRecord> findMtCheckResultsBySql(CheckItemRecord checkItemRecord);
}
