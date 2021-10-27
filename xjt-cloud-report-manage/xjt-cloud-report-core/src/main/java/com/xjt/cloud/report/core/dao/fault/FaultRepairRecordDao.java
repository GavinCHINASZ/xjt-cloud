package com.xjt.cloud.report.core.dao.fault;

import com.xjt.cloud.report.core.entity.fault.FaultRepairRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 故障报修
 * @Author huanggc
 * @Date 2019/09/02
 **/
@Repository
public interface FaultRepairRecordDao {
    /**
     * 功能描述: GB25201.B1
     *
     * @param faultRepairRecord 故障报修
     * @return: List<FaultRepairRecord>
     * @auther: huanggc
     * @date: 2019/11/04
     */
    List<FaultRepairRecord> reportB1(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 查询故障报修条数
     *
     * @param faultRepairRecord
     * @auther: huanggc
     * @date: 2019/11/13
     * @return: Integer 故障报修总数
     */
    Integer findFaultRepairTotalCount(FaultRepairRecord faultRepairRecord);
}
