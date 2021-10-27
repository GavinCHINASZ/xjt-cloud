package com.xjt.cloud.iot.core.dao.iot.vesa;

import com.xjt.cloud.iot.core.entity.inter.InterEntity;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaDeviceReport;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/26 15:37
 * @Description: 极早期管理接口Dao
 */
@Repository
public interface VesaDeviceRecordDao {

    /**
     *
     * 功能描述: 保存极早期记录
     *
     * @param vesaRecord
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int saveVesaRecord(VesaRecord vesaRecord);

    /**
     *
     * 功能描述: 保存极早期事件记录
     *
     * @param vesaRecord
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int  saveVesaRecordEvent(VesaRecord vesaRecord);

    /**
     *
     * 功能描述: 修改极早期事件记录
     *
     * @param vesaRecord
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/23 16:19
     */
    int modifyVesaRecordEvent(VesaRecord vesaRecord);
    /**
     *
     * 功能描述:　查询极早期事件记录
     *
     * @param vesaRecord
     * @return: List<VesaRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord>  findVesaUnrecoverEvent(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录
     *
     * @param vesaRecord
     * @return: List<VesaRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    VesaRecord findLatestVesaDeviceOfflineEvent(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录总数
     *
     * @param vesaRecord
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    int findVesaDeviceEventListTotalCount(VesaRecord vesaRecord);

    /**
     * 功能描述:　查询极早期事件记录列表　导出
     *
     * @param vesaRecord
     * @auther huanggc
     * @date 2020-07-05
     * @return List<VesaRecord>
     */
    List<VesaRecord> findVesaDeviceEventLists(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录列表　
     *
     * @param vesaRecord
     * @return: List<VesaRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord> findVesaDeviceEventList(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param vesaRecord
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    VesaDeviceReport findVesaDeviceEventSummaryReport(VesaRecord vesaRecord);


    /**
     *
     * 功能描述:查询极早期设备事件汇总曲线图 API接口
     *
     * @param vesaRecord
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    VesaDeviceReport findVesaEventReportCountApp(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期记录列表　
     *
     * @param vesaRecord
     * @return: List<VesaRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord> findVesaDeviceRecordList(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期记录曲线图
     *
     * @param vesaRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord> findVesaDeviceRecordCurveChart(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录按时间汇总（曲线图）
     *
     * @param vesaRecord
     * @return: List<WaterRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord> findVesaDeviceEventReportCount(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录汇总，饼图
     *
     * @param vesaRecord
     * @return: List<VesaRecord>
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    List<VesaRecord> findVesaDeviceEventReportTotal(VesaRecord vesaRecord);

    VesaDeviceReport findVesaDeviceUnrecoverEventSummary(VesaRecord vesaRecord);

    /**
     *
     * 功能描述:　查询极早期事件记录列表　
     *
     * @param vesaRecord
     * @return: VesaRecord
     * @auther: wangzhiwen
     * @date: 2019/9/26 15:44
     */
    VesaRecord findVesaFaultName(VesaRecord vesaRecord);


    /**
     *
     * 功能描述:查询水压设备事件汇总饼图
     *
     * @param vesaRecord
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    List<VesaRecord>  findVesaFaultNameCount(VesaRecord vesaRecord);
    /**
     *@Author: dwt
     *@Date: 2020-07-13 14:42
     *@Param: VesaRecord
     *@Return: VesaDeviceReport
     *@Description 改版极早期未恢复事件统计
     */
    VesaDeviceReport findVesaEventNumSum(VesaDevice vesaDevice);
    /**
     *@Author: dwt
     *@Date: 2020-07-15 16:11
     *@Param: VesaRecord
     *@Return: InterEntity
     *@Description 因特火警主机事件统计报表
     */
    List<InterEntity> findInterAreaTypeEventCount(VesaRecord vesaRecord);

    /**
     * 功能描述: 项目主页 极早期
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/20
     */
    VesaDeviceReport findVesaDeviceProjectHomeData(VesaRecord vesaRecord);
}
