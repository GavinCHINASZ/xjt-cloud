package com.xjt.cloud.iot.core.dao.iot.air;

import com.xjt.cloud.iot.core.entity.air.AirSamplingRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AirSamplingRecordDao
 * @Description 空气采样设备记录管理
 * @Author wangzhiwen
 * @Date 2021/3/31 9:09
 **/
@Repository
public interface AirSamplingRecordDao {

    /**
     * @Description 查询空气采样记录列表
     *
     * @param airSamplingRecord
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingRecord>
     */
    List<AirSamplingRecord> findAirSamplingRecordList(AirSamplingRecord airSamplingRecord);

    /**
     * @Description 查询空气采样记录列表总数
     *
     * @param airSamplingRecord
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer findAirSamplingRecordListTotalCount(AirSamplingRecord airSamplingRecord);

    /**
     * @Description 查询空气采样记录曲线图
     *
     * @param airSamplingRecord
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingRecord>
     */
    List<AirSamplingRecord> findAirSamplingRecordGraph(AirSamplingRecord airSamplingRecord);

    /**
     * @Description 添加空气采样设备记录信息
     *
     * @param airSamplingRecord
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer saveAirSamplingRecord(AirSamplingRecord airSamplingRecord);
}
