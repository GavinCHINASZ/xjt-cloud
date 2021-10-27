package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.device.core.entity.DeviceType;
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

    /**
     *
     * 功能描述: 以类型名称数组查询设备类型列表
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:49
     */
    List<DeviceType> findDeviceSysList(String sql);

    /**
     * 功能描述: 查询 设备类型(地址)
     *
     * @param deviceType
     * @auther huanggc
     * @date 2020-04-27
     * @return List<DeviceType>
     */
    List<DeviceType> findDeviceTypeMetroList(DeviceType deviceType);

    /**
     * @Description 以部门id与楼层查询设备类型列表
     *
     * @author wangzhiwen
     * @date 2021/3/10 15:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<DeviceType> findDeviceTypeListByOrgFloor(DeviceType deviceType);

    /**
     * 以部门id与楼层查询设备类型列表
     *
     * @param deviceType 设备类型
     * @author wangzhiwen
     * @date 2021/3/10 15:44
     * @return List<DeviceType>
     */
    List<DeviceType> findDeviceSysByProjectId(DeviceType deviceType);
}
