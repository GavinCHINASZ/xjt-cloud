package com.xjt.cloud.iot.core.dao.iot.water;

import com.xjt.cloud.iot.core.entity.water.WaterDeviceReport;
import com.xjt.cloud.iot.core.entity.water.WaterRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:37
 * @Description: 水设备管理接口Dao
 */
@Repository
public interface WaterDeviceRecordDao {

    /**
     *
     * 功能描述: 保存水设备记录
     *
     * @param waterRecord
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int saveWaterRecord(WaterRecord waterRecord);

    /**
     *
     * 功能描述: 保存水设备事件记录
     *
     * @param waterRecord
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int  saveWaterRecordEvent(WaterRecord waterRecord);

    /**
     *
     * 功能描述: 修改水设备事件记录
     *
     * @param waterRecord
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int modifyWaterRecordEvent(WaterRecord waterRecord);
    /**
     *
     * 功能描述:　查询水设备事件记录列
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    WaterRecord findNewestWaterDeviceEvent(WaterRecord waterRecord);
    /**
     *
     * 功能描述:　查询水设备事件记录总数
     *
     * @param waterRecord
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    int findWaterDeviceEventListTotalCount(WaterRecord waterRecord);

    /**
     *
     * 功能描述:　查询水设备事件记录列表　
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<WaterRecord> findWaterDeviceEventList(WaterRecord waterRecord);

    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param waterRecord
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    WaterDeviceReport findWaterDeviceEventSummaryReport(WaterRecord waterRecord);

    /**
     *
     * 功能描述:　查询水设备记录列表总数
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    Integer findWaterDeviceRecordListTotalCount(WaterRecord waterRecord);

    /**
     *
     * 功能描述:　查询水设备记录列表　
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<WaterRecord> findWaterDeviceRecordList(WaterRecord waterRecord);

    /**
     *
     * 功能描述:　查询水设备记录曲线图
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<WaterRecord> findWaterDeviceRecordCurveChart(WaterRecord waterRecord);

    /**
     *
     * 功能描述:查询水压记录时间列表
     *
     * @param waterRecord
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/6/4 14:57
     */
    List<String> findTimeList(WaterRecord waterRecord);


    /**
     *
     * 功能描述:　查询水设备事件记录按时间汇总（曲线图）
     *
     * @param waterRecord
     * @return: List<WaterDeviceReport>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<WaterDeviceReport> findWaterDeviceEventReportCount(WaterRecord waterRecord);

    /**
     *
     * 功能描述:　查询水设备事件记录汇总，饼图
     *
     * @param waterRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<WaterRecord> findWaterDeviceEventReportTotal(WaterRecord waterRecord);
    /**
     *@Author: dwt
     *@Date: 2020-08-10 10:43
     *@Param: WaterRecord
     *@Return: WaterDeviceReport
     *@Description 查询消火栓故障信息统计
     */
    WaterDeviceReport findHydrantFaultMsg(WaterRecord waterRecord);
}
