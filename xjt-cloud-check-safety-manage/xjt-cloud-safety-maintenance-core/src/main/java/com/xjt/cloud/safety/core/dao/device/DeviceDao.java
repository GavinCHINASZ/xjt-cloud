package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName DeviceDao
 * @Author dwt
 * @Date 2020-04-10 15:55
 */
@Repository
public interface DeviceDao {
    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:11
     * @Param: Device
     * @Return: java.lang.Long
     * 查询设备总数
     */
    Integer findDeviceListTotalCount(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:12
     * @Param: Device
     * @Return: java.util.List
     * 查询设备列表
     */
    List<Device> findDeviceList(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:13
     * @Param: Device
     * @Return: Device
     * 查询设备信息
     */
    Device findDevice(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:15
     * @Param: Device
     * @Return: java.lang.Integer
     * 保存设备
     */
    Integer saveDevice(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:16
     * @Param: Device
     * @Return: java.lang.Integer
     * 批量保存设备
     */
    int saveDevices(@Param("list") List<Device> list);

    /**
     * @Author: dwt
     * @Date: 2020-04-10 17:17
     * @Param: Device
     * @Return: java.lang.Integer
     * 编辑设备
     */
    int modifyDevice(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-11 9:14
     * @Param: Device
     * @Return: Device
     * 根据项目明细id查询所有设备
     */
    List<Device> findAllDeviceListByProjectInfoId(Device device);

    /**
     * @Author: dwt
     * @Date: 2020-04-11 17:52
     * @Param: Device
     * @Return: java.lang.Integer
     * 逻辑删除
     */
    Integer deleteDevice(Device device);
}
