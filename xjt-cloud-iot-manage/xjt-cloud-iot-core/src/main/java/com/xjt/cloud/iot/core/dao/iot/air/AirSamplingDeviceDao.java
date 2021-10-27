package com.xjt.cloud.iot.core.dao.iot.air;

import com.xjt.cloud.iot.core.entity.air.AirSamplingDevice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName AirSamplingDao
 * @Description 空气采样设备管理
 * @Author wangzhiwen
 * @Date 2021/3/30 9:25
 **/
@Repository
public interface AirSamplingDeviceDao {

    /**
     * @Description 查询空气采样设备统计报表
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return AirSamplingDevice
     */
    AirSamplingDevice findAirSamplingDeviceSummaryReport(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 查询空气采样设备信息列表
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingDevice>
     */
    List<AirSamplingDevice> findAirSamplingDeviceList(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 查询空气采样设备信息列表总数
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer findAirSamplingDeviceListTotalCount(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 修改空气采样设备阈值
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return int
     */
    int modifyAirSamplingDevice(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 保存空气采样设备阈值修改记录
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return int
     */
    int saveAirSamplingDeviceUpdateRecord(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 查询空气采样设备阈值修改列表
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return List<AirSamplingDevice>
     */
    List<AirSamplingDevice> findAirSamplingDeviceUpdateRecordList(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 查询空气采样设备阈值修改列表总数
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer findAirSamplingDeviceUpdateRecordListTotalCount(AirSamplingDevice airSamplingDevice);

    /**
     * @Description 修改空气采样设备当前值
     *
     * @param airSamplingDevice
     * @author wangzhiwen
     * @date 2021/3/30 14:14
     * @return Integer
     */
    Integer modifyAirSamplingDeviceData(AirSamplingDevice airSamplingDevice);
}
