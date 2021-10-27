package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.CheckRecord;
import com.xjt.cloud.safety.core.entity.device.CheckReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:32
 * @Description:app检测
 */
@Repository
public interface CheckRecordDao {

    /**
     *
     * 功能描述:查询设备系统评估汇总信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckRecord>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<CheckRecord> findCheckRecordDeviceSysCountReport(CheckRecord checkRecord);

    /**
     *
     * 功能描述:查询设备类型评估汇总信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckRecord>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<CheckRecord> findCheckRecordDeviceTypeCountReport(CheckRecord checkRecord);

    /**
     *
     * 功能描述:查询项目评估汇总信息
     *
     * @param
     * @return: CheckRecord
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    CheckReport findCheckRecordTotalReport(CheckRecord checkRecord);

    /**
     *
     * 功能描述:新增app检测
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckRecord
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveCheckRecord(CheckRecord checkRecord);

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckRecord
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer modifyCheckRecord(CheckRecord checkRecord);


    /**
     *
     * 功能描述:查询app检测列表总行数
     *
     * @param checkRecord
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findCheckRecordListCount(CheckRecord checkRecord);

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckRecord>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<CheckRecord> findCheckRecordList(CheckRecord checkRecord);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 17:21
     *@Param: CheckRecord
     *@Return: CheckRecord
     *@Description 查询所巡检项目的巡检记录
     */
    List<CheckReport> findCheckRecordSynthesisScoreList(CheckRecord checkRecord);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 17:21
     *@Param: CheckRecord
     *@Return: CheckRecord
     *@Description 查询所巡检项目的巡检记录
     */
    List<CheckReport> findCheckRecordItemScoreList(CheckRecord checkRecord);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 17:26
     *@Param: CheckRecord
     *@Return: CheckRecord
     *@Description 查询所有不合格巡检记录
     */
    List<CheckRecord> findFailCheckRecordList(CheckRecord checkRecord);

    /**
     *
     * 功能描述:查询检测汇总报表
     *
     * @param checkProjectId 项目id
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/11 17:59
     */
    List<CheckReport> findCheckReport(@Param("checkProjectId") Long checkProjectId);

    /**
     * 查询 创建人
     *
     * @param checkProjectId 项目id
     * @return String
     * @author huanggc
     * @date 2020/4/11 17:59
     */
    String findCreateUserName(@Param("checkProjectId") Long checkProjectId);
}
