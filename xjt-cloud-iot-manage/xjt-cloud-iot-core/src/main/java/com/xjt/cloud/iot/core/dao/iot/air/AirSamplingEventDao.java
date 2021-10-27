package com.xjt.cloud.iot.core.dao.iot.air;

import com.xjt.cloud.iot.core.entity.air.AirSamplingEvent;
import com.xjt.cloud.iot.core.entity.air.AirSamplingEventReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AirSamplingEventDao
 * @Description 空气采样设备事件管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:07
 **/
@Repository
public interface AirSamplingEventDao {
    /**
     * @Description 查询空气采样记录列表
     *
     * @param airSamplingEvent
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingRecord>
     */
    List<AirSamplingEvent> findAirSamplingEventList(AirSamplingEvent airSamplingEvent);

    /**
     * @Description 查询空气采样记录列表总数
     *
     * @param airSamplingEvent
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer findAirSamplingEventListTotalCount(AirSamplingEvent airSamplingEvent);

    /**
     * @Description 查询空气采样记录曲线图
     *
     * @param airSamplingEvent
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingEventReport>
     */
    List<AirSamplingEventReport> findAirSamplingEventGraph(AirSamplingEvent airSamplingEvent);

    /**
     * @Description 查询空气采样记录列表总数
     *
     * @param airSamplingEvent
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return AirSamplingEventReport
     */
    AirSamplingEventReport findAirSamplingEventPie(AirSamplingEvent airSamplingEvent);

    /**
     * @Description 添加空气采样设备事件信息
     *
     * @param airSamplingEvent
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer saveAirSamplingEvent(AirSamplingEvent airSamplingEvent);
}
