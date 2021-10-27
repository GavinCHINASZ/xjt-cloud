package com.xjt.cloud.maintenance.core.dao.device;

import com.xjt.cloud.maintenance.core.entity.device.DeviceType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:52
 * @Description:设备系统与类型管理DAO
 */
@Repository
public interface DeviceTypeDao {

    /**
     *
     * 功能描述:查询设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceType
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    DeviceType findParentDeviceType(DeviceType deviceType);

    /**
     *
     * 功能描述:查询设备类型信息列表总行数
     *
     * @param deviceType
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findDeviceTypeListCount(DeviceType deviceType);

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<DeviceType> findDeviceTypeList(DeviceType deviceType);

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<DeviceType> findDeviceTypeListByCheckPoint(DeviceType deviceType);

    /**
     *
     * 功能描述:查询设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceType
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    DeviceType findDeviceType(DeviceType deviceType);

    /**
     *
     * 功能描述:查询设备系统树
     *
     * @param deviceType
     * @return: DeviceType
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    List<DeviceType> findDeviceSysTree(DeviceType deviceType);

    /**
     *
     * 功能描述:查询未删除设备类型信息列表，以拼音排序
     *
     * @param deviceType
     * @return: DeviceType
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    List<DeviceType> findDeviceTypeOrderPinYin(DeviceType deviceType);

}
