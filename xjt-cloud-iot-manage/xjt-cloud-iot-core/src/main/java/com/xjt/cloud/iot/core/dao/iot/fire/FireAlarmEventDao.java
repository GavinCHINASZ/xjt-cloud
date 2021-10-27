package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmRecord;
import com.xjt.cloud.iot.core.entity.fire.FireEventReport;
import com.xjt.cloud.iot.core.entity.fire.SubwayFireEvent;
import com.xjt.cloud.iot.core.entity.inter.InterEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName FireAlarmEventDao
 * @Author dwt
 * @Date 2019-10-11 15:16
 * 火警主机事件Dao层
 * @Version 1.0
 */
@Repository
public interface FireAlarmEventDao {
    /**
     * 查询火警主机事件列表
     * 
     * @author dwt
     * @date 2019-10-21 11:32
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.util.List
     */
    List<FireAlarmEvent> findFireAlarmEventList(FireAlarmEvent fireAlarmEvent);

    /**
     * 查询火警主机事件列表
     *
     * @author huanggc
     * @date 2020-07-25
     * @param fireAlarmEvent 火警主机事件实体
     * @return List<FireAlarmEvent>
     */
    List<FireAlarmEvent> findFireAlarmEventLists(FireAlarmEvent fireAlarmEvent);

    /**
     * 根据条件统计火警主机事件数
     *
     * @author dwt
     * @date 2019-10-21 11:33
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.lang.Long
     */
    Long findFireAlarmEventCount(FireAlarmEvent fireAlarmEvent);

    /**
     * 保存事件受影响行数
     *
     * @author dwt
     * @date 2019-10-21 11:35
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.lang.Integer
     */
    Integer saveFireAlarmEvent(FireAlarmEvent fireAlarmEvent);

    /**
     * 修改火警主机事件恢复时间
     *
     * @author dwt
     * @date 2019-10-21 11:38
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.lang.Integer
     */
    Integer modifyFireAlarmEventRecoverStatus(FireAlarmEvent fireAlarmEvent);

    /**
     * 火警主机折线图事件数统计查询
     *
     * @author dwt
     * @date 2019-10-22 15:37
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.util.List
     */
    List<FireEventReport> findBrokenLineEventNum(FireAlarmEvent fireAlarmEvent);

    /**
     * 火警主机饼图事件数统计查询
     *
     * @author dwt
     * @date 2019-10-22 15:39
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireEventReport
     */
    FireEventReport findPieEventNum(FireAlarmEvent fireAlarmEvent);

    /**
     * 根据记录筛选事件列表
     *
     * @author dwt
     * @date 2019-11-07 11:38
     * @param fireAlarmRecord 火警主机事件实体
     * @return FireAlarmEvent
     */
    List<FireAlarmEvent> findEventListByRecord(FireAlarmRecord fireAlarmRecord);

    /**
     * 根据记录筛选事件
     *
     * @author dwt
     * @date 2019-11-07 11:38
     * @param fireAlarmRecord 火警主机事件实体
     * @return FireAlarmEvent 火警主机事件实体
     */
    FireAlarmEvent findEventByRecord(FireAlarmRecord fireAlarmRecord);

    /**
     * 火警主机事件导出
     *
     * @author dwt
     * @date 2019-11-13 13:30
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireAlarmEvent
     */
    List<FireAlarmEvent> downLoadFireAlarmEventList(FireAlarmEvent fireAlarmEvent);

    /**
     * 根据条件查询最新的一条火警主机事件
     *
     * @author dwt
     * @date 2019-11-26 13:59
     * @param fireAlarmEvent 火警主机事件实体
     * @return com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent
     */
    FireAlarmEvent findFireAlarmEventNewest(FireAlarmEvent fireAlarmEvent);

    /**
     * 更新离线事件状态
     *
     * @author dwt
     * @date 2019-11-26 14:23
     * @param transDeviceId t
     */
    void modifyOffLineEventStatus(@Param("transDeviceId") String transDeviceId);

    /**
     * 根据条件查询火警主机离线未恢复事件列表
     *
     * @author dwt
     * @date 2019-11-26 16:07
     * @param fireAlarmEvent 火警主机事件实体
     * @return List<FireAlarmEvent>
     */
    List<FireAlarmEvent> findOffLineEventNewestList(FireAlarmEvent fireAlarmEvent);

    /**
     * 查询火警主机网关事件
     *
     * @author dwt
     * @date 2019-11-29 11:48
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireAlarmEvent
     */
    FireAlarmEvent findGatewayEvent(FireAlarmEvent fireAlarmEvent);

    /**
     * 查询火警主机事件监测
     *
     * @author dwt
     * @date 2019-12-04 15:34
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireEventReport
     */
    FireEventReport findFireAlarmEventMonitor(FireAlarmEvent fireAlarmEvent);

    /**
     * 根据传感器id查询离线事件列表
     *
     * @author dwt
     * @date 2019-12-19 14:41
     * @param from from
     * @return FireAlarmEvent
     */
    List<FireAlarmEvent> findFireEventByFrom(@Param("from") String from);

    /**
     * 地铁事件原因前五排行统计
     *
     * @author dwt
     * @date 2020-05-11 16:04
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireEventReport
     */
    List<FireEventReport> findSubwayEventOfCauseCount(FireAlarmEvent fireAlarmEvent);

    /**
     * 地铁事件处理接口
     *
     * @author dwt
     * @date 2020-05-12 13:52
     * @param fireAlarmEvent 火警主机事件实体
     * @return java.lang.Integer
     */
    Integer updateEventCauseById(FireAlarmEvent fireAlarmEvent);

    /**
     * 地铁大屏火警主机事件统计
     *
     * @author dwt
     * @date 2020-05-13 10:44
     * @param fireAlarmEvent 火警主机事件实体
     * @return SubwayFireEvent
     */
    List<SubwayFireEvent> findSubwayEventCount(FireAlarmEvent fireAlarmEvent);

    /**
     * 火警主机改版处理状态接口
     *
     * @author dwt
     * @date 2020-07-07 14:15
     * @param fireAlarmEvent 火警主机事件实体
     * @return Integer
     */
    Integer updateFireEventHandleStatus(FireAlarmEvent fireAlarmEvent);

    /**
     * 勾选设备导出列表
     *
     * @author dwt
     * @date 2019-11-05 15:22
     * @param fireAlarmEvent 火警主机事件实体
     * @return FireAlarmEvent
     */
    FireAlarmEvent findFireAlarmEventById(FireAlarmEvent fireAlarmEvent);

    /**
     * 因特火警主机事件统计报表
     *
     * @author dwt
     * @date 2020-07-15 16:11
     * @param fireAlarmEvent 火警主机事件实体
     * @return InterEntity
     */
    List<InterEntity> findInterAreaTypeEventCount(FireAlarmEvent fireAlarmEvent);

    /**
     * 地铁班前防护故障列表查询
     * 
     * @author dwt
     * @date 2020-09-29 10:08
     * @param fireAlarmEvent 火警主机事件实体
     * @return List<FireAlarmEvent>
     */
    List<FireAlarmEvent> findMetroProtectionFaultEventList(FireAlarmEvent fireAlarmEvent);

    /**
     * 查询用户是否在项目的作业中
     *
     * @author huanggc
     * @date 2020/10/27
     * @param fireAlarmEvent 火警主机事件实体
     * @return int
     */
    int findProtectUserNum(FireAlarmEvent fireAlarmEvent);

    /**
     * 更新 火警主机事件实体
     *
     * @author huanggc
     * @date 2020/10/29
     * @param fireAlarmEvent 火警主机事件实体
     */
    void updateFireAlarmEvent(FireAlarmEvent fireAlarmEvent);

    /**
     * 查询作业是否完成
     *
     * @author huanggc
     * @date 2020/10/29
     * @param fireAlarmEvent 火警主机事件实体
     * @return List<FireAlarmEvent>
     */
    int findProtectIsComplete(FireAlarmEvent fireAlarmEvent);
}
