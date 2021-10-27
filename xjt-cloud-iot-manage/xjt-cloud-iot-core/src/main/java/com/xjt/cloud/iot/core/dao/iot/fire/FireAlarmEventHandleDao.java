package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmEventHandle;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 火警 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
@Repository
public interface FireAlarmEventHandleDao {

    /**
     * 保存事件处理信息
     *
     * @param fireAlarmEventHandleList FireAlarmEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return Integer
     */
    Integer saveFireAlarmEventHandle(List<FireAlarmEventHandle> fireAlarmEventHandleList);

    /**
     * 查询事件处理信息列表
     *
     * @param fireAlarmEventHandle FireAlarmEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<FireAlarmEventHandle>
     */
    List<FireAlarmEventHandle> findFireAlarmEventHandleList(FireAlarmEventHandle fireAlarmEventHandle);

    /**
     * 查询事件处理信息总数
     *
     * @param fireAlarmEventHandle FireAlarmEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<FireAlarmEventHandle>
     */
    Integer findFireAlarmEventHandleListCount(FireAlarmEventHandle fireAlarmEventHandle);

    /**
     * 查询事件处理信息列表
     *
     * @param fireAlarmEventHandle FireAlarmEventHandle
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findFireAlarmEventFaultTypeColumnChart(FireAlarmEventHandle fireAlarmEventHandle);
}
