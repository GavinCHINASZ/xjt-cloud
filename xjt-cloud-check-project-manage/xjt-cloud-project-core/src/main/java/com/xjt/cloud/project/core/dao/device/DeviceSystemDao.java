package com.xjt.cloud.project.core.dao.device;

import com.xjt.cloud.project.core.entity.device.Device;
import com.xjt.cloud.project.core.entity.device.DeviceSystem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:52
 * @Description:设备系统与类型管理DAO
 */
@Repository
public interface DeviceSystemDao {

    /**
     *
     * 功能描述:查询设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    DeviceSystem findParentDeviceType(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:新增设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveParentDeviceType(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询设备类型信息列表总行数
     *
     * @param deviceSystem
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findDeviceTypeListCount(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.DeviceSystem>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<DeviceSystem> findDeviceTypeList(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.DeviceSystem>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<DeviceSystem> findDeviceTypeListByCheckPoint(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    DeviceSystem findDeviceType(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询设备系统树
     *
     * @param deviceSystem
     * @return: DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    List<DeviceSystem> findDeviceSysTree(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:查询未删除设备类型信息列表，以拼音排序
     *
     * @param deviceSystem
     * @return: DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    List<DeviceSystem> findDeviceTypeOrderPinYin(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:新增设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveDeviceType(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:修改设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.DeviceSystem
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer modifyDeviceType(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述:检查输入的设备系统名称是否唯一
     *
     * @param deviceSystem
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 9:37
     */
    Integer checkDeviceTypeNameUnique(DeviceSystem deviceSystem);

    /**
     *
     * 功能描述: 以类型名称数组查询设备类型列表
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:49
     */
    List<DeviceSystem> findDeviceSysList(String sql);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 10:41
     *@Param: Device
     *@Return: DeviceSystem
     *@Description 根据设备名称查询设备系统
     */
    Device findDeviceSystemByDeviceName(Device device);
}
