package com.xjt.cloud.task.core.dao.task;


import com.xjt.cloud.task.core.entity.check.CheckDetails;
import com.xjt.cloud.task.core.entity.check.CheckItemRecord;
import com.xjt.cloud.task.core.entity.check.CheckItemTask;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.task.AppTaskCheckItem;
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
    
    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:16
     *@Param: 巡检项记录
     *@Return: 巡检项记录列表
     *@Description: 查询巡检项记录列表
     */
    List<CheckItemRecord> findCheckItemRecordList(CheckItemRecord checkItemRecord);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:16
     *@Param: 巡检项记录
     *@Return: 巡检项记录列表总数
     *@Description: 查询巡检项记录列表总数
     */
    Integer findCheckItemRecordCount(CheckItemRecord checkItemRecord);
    
    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:17
     *@Param: java.lang.Long
     *@Return: 巡检记录
     *@Description: 根据主键id查询巡检记录
     */
    CheckItemRecord findCheckItemRecordById(Long id);
    
    /**
     *@Author: dwt
     *@Date: 2019-07-25 18:18
     *@Param: 巡检记录
     *@Return: 主键id
     *@Description: 保存巡检记录
     */
    Integer saveCheckItemRecord(CheckItemRecord checkItemRecord);
    /**
     *@Author: dwt
     *@Date: 2019-08-14 16:29
     *@Param: java.lang.Long
     *@Return: 巡检项记录列表
     *@Description 根据巡检记录id查询巡检项记录列表
     */
    List<CheckItemRecord> findCheckItemRecordByCheckRecordId(Long checkRecordId);
    /**
     *@Author: dwt
     *@Date: 2019-08-15 16:48
     *@Param: java.lang.Long
     *@Return: AppTaskCheckItem
     *@Description  APP 根据巡检记录id查询巡检项记录列表
     */
    List<AppTaskCheckItem> findCheckItemByCheckRecordIdApp(Long checkRecordId);
    /**
     *@Author: dwt
     *@Date: 2019-08-16 10:24
     *@Param: java.lang.Long
     *@Return: CheckItem
     *@Description 根据巡检项id查询巡检项信息
     */
    CheckItemTask findChcekItemByCheckItemId(Long checkItemId);

    /**@MethodName: saveCheckItemRecordList
     * @Description: 保存巡检结果
     * @Param: [checkItemRecords]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/28 14:11
     **/
    void saveCheckItemRecordList(List<CheckItemRecord> list);

    /**
     * 功能描述: 查询巡检结果
     * @MethodName: findByCheckDetails
     * @param checkDetails 巡检详情
     * @Author: huangGuiChuan
     * @Date: 2019-12-05
     * @return  List<CheckItemRecord>
     **/
    List<CheckItemRecord> findByCheckDetails(CheckDetails checkDetails);

    /**
     * 查询 FAS系统巡检结果
     *
     * @MethodName: findFasList
     * @param checkItemRecord 巡检详情
     * @Author: huangGuiChuan
     * @Date 2020-05-11
     * @return  List<CheckItemRecord>
     **/
    List<CheckItemRecord> findFasList(CheckItemRecord checkItemRecord);

    /**
     * 地铁站务--运营总部消防设施日常巡查记录表 Q/SZDY 0044-04-B3
     *
     *@Author: huanggc
     *@Date: 2020-06-02
     *@param checkItemRecord
     *@Return: List<CheckItemRecord>
     */
    List<CheckItemRecord> findDailyPatrol(CheckItemRecord checkItemRecord);
    /**
     *@Author: dwt
     *@Date: 2020-10-23 10:13
     *@Param:
     *@Return:
     *@Description 查询设备巡检项列表
     */
    List<CheckItemTask> findCheckItemList(Device device);
}
