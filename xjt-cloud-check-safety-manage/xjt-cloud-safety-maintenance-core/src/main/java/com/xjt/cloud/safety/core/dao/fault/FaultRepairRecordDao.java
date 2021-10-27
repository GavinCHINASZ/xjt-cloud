package com.xjt.cloud.safety.core.dao.fault;

import com.xjt.cloud.safety.core.entity.fault.FaultRepairRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 故障报修
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Repository
public interface FaultRepairRecordDao {
    /**
     * 新建故障报修
     *
     * @param faultRepairRecord FaultRepairRecord
     * @return int
     * @author huanggc
     * @date 2021/04/22
     */
    int saveFaultRepairRecord(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param faultRepairRecord 故障报修实体
     * @return List<FaultRepairRecord>
     * @author huanggc
     * @date 2019/09/02
     */
    List<FaultRepairRecord> findFaultRepairRecordList(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:查询 故障报修
     *
     * @param faultRepairRecord FaultRepairRecord
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    FaultRepairRecord findFaultRepairRecord(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:查询 故障报修 数据条数
     *
     * @param faultRepairRecord 故障报修实体
     * @return Integer
     * @author huanggc
     * @date 2019/09/06
     */
    Integer findFaultRepairRecordTotalCount(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:更新故障报修
     *
     * @param faultRepairRecord FaultRepairRecord
     * @return int
     * @author huanggc
     * @date 2019/09/02
     */
    int updateFaultRepairRecord(FaultRepairRecord faultRepairRecord);
}
