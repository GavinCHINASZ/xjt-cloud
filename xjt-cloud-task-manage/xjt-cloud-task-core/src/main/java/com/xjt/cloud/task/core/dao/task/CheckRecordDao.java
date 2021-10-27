package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.AppTaskCheckRecord;
import com.xjt.cloud.task.core.entity.check.CheckDetails;
import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.check.CheckRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 巡检记录Dao
 * 
 * @author dwt
 * @date  2019/07/25 16:25
 * @version 1.0
 */
@Repository
public interface CheckRecordDao {

    /**
     * 查询符合条件的巡检记录列表
     * 
     * @author huangGuiChuan
     * @date  2019/12/04
     * @param checkRecord 巡检记录实体
     * @return List<CheckRecord> 巡检记录列表
     */
    List<CheckRecord> findCheckRecordList(CheckRecord checkRecord);

    /**
     * 查询符合条件的巡检记录列表
     *
     * @author huanggc
     * @date  2019/12/04
     * @param checkRecord 巡检记录实体
     * @return List<CheckRecord> 巡检记录列表
     */
    List<CheckRecord> findCheckRecordLists(CheckRecord checkRecord);

    /**
     * 查询符合条件的巡检记录统计数
     * 
     * @author dwt
     * @date  2019/07/25 17:10
     * @param checkRecord 巡检记录
     * @return 巡检记录列表
     */
    Integer findCheckRecordCount(CheckRecord checkRecord);

    /**
     * 根据id查询巡检记录
     * 
     * @author dwt
     * @date  2019/07/25 17:12
     * @param id 巡检记录id
     * @return CheckRecord 巡检记录对象
     */
    CheckRecord findCheckRecordById(Long id);

    /**
     * 保存巡检记录
     * 
     * @author dwt
     * @date  2019/07/25 17:13
     * @param checkRecord 巡检记录
     * @return 主键id
     */
    Integer saveCheckRecord(CheckRecord checkRecord);

    /**
     * 修改巡检记录
     * 
     * @author dwt
     * @date  2019/07/25 17:19
     * @param checkRecord 巡检记录
     * @return Integer
     */
    Integer modifyCheckRecord(CheckRecord checkRecord);

    /**
     * 根据任务id查询
     * 
     * @author dwt
     * @date  2019/08/12 14:58
     * @param taskId 任务ID
     * @return List<CheckRecord> 巡检记录
     */
    List<CheckRecord> findCheckRecordByTaskId(Long taskId);

    /**
     * 根据筛选条件查询所有符合条件的设备id
     * 
     * @author dwt
     * @date  2019/08/14 14:31
     * @param checkRecord 巡检记录实体
     * @return List<Long> 设备id列表
     */
    List<Long> findCheckRecordDeviceIds(CheckRecord checkRecord);

    /**
     * 根据设备id查询最新的设备状态
     * 
     * @author dwt
     * @date  2019/08/14 14:27
     * @param deviceId 设备ID
     * @return Integer
     */
    Integer findCheckRecordResultByDeviceId(Long deviceId);

    /**
     * 根据巡检记录id查询巡检详情
     * 
     * @author dwt
     * @date  2019/08/14 16:19
     * @param id 巡检记录ID
     * @return 巡检详情: 基本设备信息
     */
    CheckDetails findCheckDetailsByCheckRecordId(Long id);

    /**
     * 根据巡检记录id查询设备系统名称
     * 
     * @author dwt
     * @date  2019/08/14 16:39
     * @param id 巡检记录ID
     * @return 设备系统名称
     */
    String findDeviceSysNameByCheckRecordId(Long id);

    /**
     * 根据任务id查询已检设备数量
     * 
     * @author dwt
     * @date  2019/08/15 14:20
     * @param taskId 任务ID
     * @return java.lang.Integer
     */
    Integer findCheckedDeviceNumByTaskId(Long taskId);

    /**
     * 根据巡检记录id查询巡检详情
     * 
     * @author dwt
     * @date  2019/08/15 16:34
     * @param id 巡检记录ID
     * @return AppTaskCheckRecord
     */
    AppTaskCheckRecord findCheckRecordDetailsById(Long id);

    /**
     * 根据设备id查询最新的巡检记录id
     * 
     * @author dwt
     * @date  2019/08/16 14:43
     * @param deviceId 设备ID
     * @return java.lang.Long
     */
    Long findCheckRecordIdByDeviceId(Long deviceId);

    /** 
     * saveCheckRecordList
     * 保存巡检记录
     * 
     * @param list List<CheckRecord>
     * @author zhangZaiFa
     * @date  2019/11/28 14:11
     **/
    void saveCheckRecordList(List<CheckRecord> list);

    /** 
     * findTaskCheckPointCheckRecordList
     * 查询任务巡检点记录
     * 
     * @param checkRecord
     * @return java.util.List<com.xjt.cloud.task.core.entity.check.CheckRecord>
     * @author zhangZaiFa
     * @date  2019/11/29 9:47
     **/
    List<CheckRecord> findTaskCheckPointCheckRecordList(CheckRecord checkRecord);

    /**
     * 查询 巡检记录 数量
     * findCheckRecordTotalCount
     * @author huangGuiChuan
     * @date  2019/12/04
     * @param checkRecord 巡检记录实体
     * @return Integer 巡检记录总条数
     */
    Integer findCheckRecordTotalCount(CheckRecord checkRecord);

    Integer findCheckRecordTotalCounts(CheckRecord checkRecord);

    /**
     *
     * 功能描述: 查询 故障巡查数量  总巡检设备数  故障设备数量
     * findCheckRecordData
     * @author huangGuiChuan
     * @date  2019/12/04
     * @param checkRecord 巡检记录实体
     * @return CheckRecord 巡检记录实体
     */
    CheckRecord findCheckRecordData(CheckRecord checkRecord);

    /**
     * 删除, 批量删除巡查记录
     *
     * @param checkRecord
     * @author huangGuiChuan
     * @date  2020/01/16
     * @return Integer
     *
     */
    Integer deletedCheckRecord(CheckRecord checkRecord);

    /**
     * 月度工单统计//人员情况 数量
     *
     * @param checkRecord
     * @author huangGuiChuan
     * @date  2020/03/16
     * @return Integer
     */
    Integer findUserOverviewTotalCount(CheckRecord checkRecord);

    /**
     * 月度工单统计//人员情况
     *
     * @param checkRecord
     * @author huangGuiChuan
     * @date  2020/03/16
     * @return List<CheckRecord>
     */
    List<CheckRecord> findUserOverview(CheckRecord checkRecord);

    /**
     * findProjectCurrentMonthFaultCheckRecordCount
     * 查询项目当月故障巡检记录数量
     *
     * @param checkRecord
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/3/19 14:43
     **/
    Integer findProjectCurrentMonthFaultCheckRecordCount(CheckRecord checkRecord);

    /**
     * 查询项目当月故障巡检记录数量
     *
     * findCheckRecordDeviceData
     * @author huanggc
     * @date  2020/04/03
     * @param checkRecord
     * @return CheckRecord
     **/
    CheckRecord findCheckRecordDeviceData(CheckRecord checkRecord);

    /**
     * 查询 建筑消防设施巡查表//巡检内容
     *
     * findReportItem
     * @author huanggc
     * @date  2020/04/17
     * @param checkRecord
     * @return List<CheckRecord>
     **/
    List<CheckRecord> findReportItem(CheckRecord checkRecord);

    /**
     * 查询 建筑消防设施巡查表//巡检内容//总数
     *
     * findReportItemTotalCount
     * @author huanggc
     * @date  2020/04/17
     * @param checkRecord
     * @return Integer
     **/
    Integer findReportItemTotalCount(CheckRecord checkRecord);


    /** 
     * findCheckRecord
     * 查询巡检记录
     * 
     * @param checkRecord
     * @return com.xjt.cloud.task.core.entity.check.CheckRecord
     * @author zhangZaiFa
     * @date 2020/5/7 9:00
     **/
    CheckRecord findCheckRecord(CheckRecord checkRecord);

    /** saveAutomaticCheckDevice
     * 保存自动检测设备
     * 
     * @param checkRecord
     * @return void
     * @author zhangZaiFa
     * @date 2020/5/11 14:45
     **/
    void saveAutomaticCheckDevice(CheckRecord checkRecord);

    /** 
     * findAutomaticCheckDeviceStatus
     * 查询自动检测设备状态
     * 
     * @param checkRecord
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/5/11 14:57
     **/
    Integer findAutomaticCheckDeviceStatus(CheckRecord checkRecord);

    /** updAutomaticCheckDeviceStatus
     * 修改自动检测设备状态
     * @param projectId 项目ID
     * @param deviceQrNo 设备二维码
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/5/11 17:05
     **/
    Integer updAutomaticCheckDeviceStatus(@Param("projectId") Long projectId,@Param("deviceQrNo") String deviceQrNo);

    /** 
     * deletedAutomaticCheckDevice
     * 删除自动检测设备
     * 
     * @param checkRecord 巡检记录实体
     * @return Integer
     * @author zhangZaiFa
     * @date  2020/5/11 17:22
     **/
    Integer deletedAutomaticCheckDevice(CheckRecord checkRecord);

    /**
     * 11号线吸气式烟雾探测器检修记录表 Q/SZDY/K10149/07/B2
     *
     * @author huanggc
     * @date  2020/05/13
     * @param checkRecord
     * @return List<CheckRecord>
     */
    List<CheckRecord> findInhaleSmokeSeven(CheckRecord checkRecord);

    /**
     * 11号线吸气式烟雾探测器检修记录表 QSZDY/K10149/08/B2
     *
     * @author huanggc
     * @date  2020/05/13
     * @param checkRecord CheckRecord
     * @return List<CheckRecord>
     */
    List<CheckRecord> downInhaleSmokeTable(CheckRecord checkRecord);

    /**
     * 防灾报警系统报警功能测试记录表 QSZDYK1014909B2
     *
     * @author huanggc
     * @date  2020/04/30
     * @param checkRecord CheckRecord
     * @return List<CheckRecord>
     */
    List<CheckRecord> downAlarmTable(CheckRecord checkRecord);

    /**
     * QSZDYK1014911B2
     *
     * @author huanggc
     * @date  2020/05/14
     * @param checkRecord CheckRecord
     * @return List<CheckRecord>
     */
    List<CheckRecord> downDirectelEctricTable(CheckRecord checkRecord);

    /**
     * 地铁:微型消防站巡检卡
     *
     * @author huanggc
     * @date  2020/05/25
     * @param checkRecord
     * @return List<CheckRecord>
     */
    List<CheckRecord> findFireControlList(CheckRecord checkRecord);

    /** 
     * findCheckPointRecordList
     * 查询巡检点的巡检记录
     * 
     * @param checkPointId 
     * @param taskId 任务ID
     * @author zhangZaiFa
     * @date  2020/5/28 18:31
     * @return java.util.List<com.xjt.cloud.task.core.entity.check.CheckRecord>
     **/
    List<CheckRecord> findCheckPointRecordList(@Param("checkPointId") Long checkPointId,@Param("taskId") Long taskId);

    /** 
     * 查询巡检点的巡检记录
     * 
     * @param checkRecord 巡检记录实体
     * @author zhangZaiFa
     * @date  2020/5/28 18:31
     * @return CheckRecord 巡检记录实体
     **/
    CheckRecord findCheckRecordIdString(CheckRecord checkRecord);

    /**
     * 查询 检修人
     * findCheckerName
     * 
     * @param checkRecord 巡检记录
     * @author huanggc
     * @date  2020/06/02
     * @return String
     **/
    String findCheckerName(CheckRecord checkRecord);

    /**
     * 查询符合条件的巡检记录列表 导出表格
     *
     * @author huangGuiChuan
     * @date  2020/07/27
     * @param checkRecord 巡检记录实体
     * @return List<CheckRecords> 巡检记录列表
     */
    List<CheckRecords> findDownCheckRecordList(CheckRecord checkRecord);

    /**
     * 查询 checkName
     *
     * @author huangGuiChuan
     * @date  2020/07/27
     * @param checkRecord 巡检记录实体
     * @return List<String> checkName List
     */
    List<String> findCheckerNameList(CheckRecord checkRecord);
}
