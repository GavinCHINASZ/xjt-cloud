package com.xjt.cloud.iot.core.dao.iot.report;

import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IotReportDao 物联设备报表
 *
 * @author wangzhiwen
 * @date 2020/12/9 10:57
 **/
@Repository
public interface IotReportDao {
    /**
     *  查询物联设备运营报表饼图
     *
     * @param eventFaultReport EventFaultReport
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findIotDeviceFailCountPieChart(EventFaultReport eventFaultReport);

    /**
     *  查询运营报表柱状图
     *
     * @param eventFaultReport EventFaultReport
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findIotDeviceEventCountColumnChart(EventFaultReport eventFaultReport);

    /**
     *  查询运营报表设备事件异常信息列表
     *
     * @param eventFaultReport EventFaultReport
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findIotDeviceEventFailTypeCountList(EventFaultReport eventFaultReport);

    /**
     * 查询运营报表设备事件异常信息列表 下载
     *
     * @param eventFaultReport EventFaultReport
     * @author huanggc
     * @date 2020/01/11
     * @return List<EventFaultReport>
     */
    List<EventFaultReport> findIotDeviceEventFailTypeCountListDown(EventFaultReport eventFaultReport);
}
