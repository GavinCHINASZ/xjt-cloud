package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.DeviceFaultType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备故障类型Dao
 *
 * @author huanggc
 * @date 2020/11/27
 */
@Repository
public interface DeviceFaultTypeDao {
    /**
     * 设备故障类型list
     *
     * @param deviceFaultType DeviceFaultType
     * @author huanggc
     * @date 2020/11/19
     * @return List<DeviceFaultType>
     */
    List<DeviceFaultType> findDeviceFaultTypeList(DeviceFaultType deviceFaultType);

    /**
     * 新增 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @author huanggc
     * @date 2020/11/19
     * @return int
     */
    int saveDeviceFaultType(DeviceFaultType deviceFaultType);

    /**
     * 更新 设备故障类型
     *
     * @param deviceFaultType DeviceFaultType
     * @author huanggc
     * @date 2020/11/19
     * @return int
     */
    int updateDeviceFaultType(DeviceFaultType deviceFaultType);

    /**
     * 查询 设备故障类型数量
     *
     * @param deviceFaultType DeviceFaultType
     * @author huanggc
     * @date 2020/11/19
     * @return int
     */
    Integer findDeviceFaultTypeListTotalCount(DeviceFaultType deviceFaultType);

    /**
     * 功能描述:查询 异常类型(告警类型)
     *
     * @param deviceFaultType DeviceFaultType
     * @author huanggc
     * @date 2020/12/10
     * @return List<DeviceFaultType>
     */
    List<DeviceFaultType> findFireFaultTypeList(DeviceFaultType deviceFaultType);
}
