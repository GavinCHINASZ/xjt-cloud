package com.xjt.cloud.fault.core.dao.fault;

import com.xjt.cloud.fault.core.entity.fault.FaultRepairRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 故障报修
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
@Repository
public interface FaultRepairRecordDao {

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param faultRepairRecord 故障报修实体
     * @auther: huanggc
     * @date: 2019/09/02
     * @return List<FaultRepairRecord>
     */
    List<FaultRepairRecord> findFaultRepairRecordList(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param faultRepairRecord 故障报修实体
     * @auther: huanggc
     * @date: 2019/09/02
     * @return List<FaultRepairRecord>
     */
    List<FaultRepairRecord> findFaultRepairRecords(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:查询 故障报修 数据条数
     *
     * @param faultRepairRecord 故障报修实体
     * @auther: huanggc
     * @date: 2019/09/06
     * @return: Integer
     */
    Integer findFaultRepairRecordTotalCount(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:故障报修 删除功能
     *
     * @param idList ID集合
     * @auther: huanggc
     * @date: 2019/09/06
     * @return: Integer
     */
    Integer deletedFaultRepairRecord(List<Long> idList);

    /**
     * 功能描述:查询单条故障报修
     *
     * @param faultRepairRecord
     * @auther: huanggc
     * @date: 2019/09/06
     * @return: FaultRepairRecord
     */
    FaultRepairRecord findFaultRepairRecordOne(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述:更新"故障报修"实体
     *
     * @param faultRepairRecord 故障报修 实体
     * @auther: huanggc
     * @date: 2019/09/09
     * @return: Integer
     */
    Integer updateFaultRepairRecord(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 设备报修--新建故障报修(app)
     *
     * @param faultRepairRecord 故障报修 实体
     * @auther: huanggc
     * @date: 2019/09/12
     * @return: Integer
     */
    Integer saveFaultRepairRecord(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 批量新增 故障报修
     *
     * @param faultRepairRecordList 故障报修list集合
     * @auther: huanggc
     * @date: 2019/12/12
     * @return Integer
     */
    Integer saveFaultRepairRecordList(List<FaultRepairRecord> faultRepairRecordList);

    /**
     * 功能描述: 项目主页 数量
     *
     * @param faultRepairRecord
     * @auther: huanggc
     * @date: 2020-03-19
     * @return: FaultRepairRecord
     */
    FaultRepairRecord findProjectCount(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 大屏--故障报修列表
     *
     * @param faultRepairRecord
     * @auther: huanggc
     * @date: 2020-03-19
     * @return: List<FaultRepairRecord>
     */
    List<FaultRepairRecord> findScreenList(FaultRepairRecord faultRepairRecord);

    /**@MethodName: findFaultRepairPieChartAnalysis
     * @Description: 查询故障饼图分析 统计已修复数量和未修复数
     * @Param: [faultRepairRecord]
     * @Return: com.xjt.cloud.fault.core.entity.fault.FaultRepairRecord
     * @Author: zhangZaiFa
     * @Date:2020/5/13 11:33
     **/
    FaultRepairRecord findFaultRepairPieChartAnalysis(FaultRepairRecord faultRepairRecord);

    /**@MethodName: findFaultRepairBarGraphAnalysis
     * @Description: 查询故障设备条形图 统计故障设备前五类设备及故障数量
     * @Param: [faultRepairRecord]
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @Author: zhangZaiFa
     * @Date:2020/5/13 11:33
     **/
    List<Map<String, Integer>> findFaultRepairBarGraphAnalysis(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 地铁大屏--故障报修列表
     *
     * @param faultRepairRecord
     * @auther: huanggc
     * @date: 2020-05-13
     * @return: com.xjt.cloud.commons.utils.Data
     */
    List<FaultRepairRecord> findMetroScreenList(FaultRepairRecord faultRepairRecord);

    /**
     * 功能描述: 恢复巡检记录
     *
     * @param faultRepairRecord FaultRepairRecord
     * @auther: huanggc
     * @date: 2021/01/09
     */
    void updateCheckRecord(FaultRepairRecord faultRepairRecord);
}
