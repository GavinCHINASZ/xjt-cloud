package com.xjt.cloud.iot.core.dao.iot.fire;

import com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName FireAlarmDeviceDao
 *@Author dwt
 *@Date 2019-10-11 15:15
 *@Description 火警主机Dao层
 *@Version 1.0
 */
@Repository
public interface FireAlarmDeviceDao {
    /**
     *@Author: dwt
     *@Date: 2019-10-18 16:23
     *@Param: FireAlarmDevice
     *@Return: java.util.List
     *@Description 根据条件筛选火警主机设备
     */
    List<FireAlarmDevice>  findFireAlarmDeviceList(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-10-21 10:50
     *@Param: FireAlarmDevice
     *@Return: java.lang.Integer
     *@Description 根据条件查询火警主机设备数
     */
    Integer findFireAlarmDeviceCount(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-10-21 10:56
     *@Param: java.lang.Integer
     *@Return: java.lang.Integer
     *@Description 统计设备是否在线数 1 在线，2 离线
     */
    Integer findFireAlarmIsLineCount(@Param("deviceStatus") Integer deviceStatus, @Param("projectId") Long projectId);
    /**
     *@Author: dwt
     *@Date: 2019-10-24 15:27
     *@Param: java.lang.String
     *@Return: FireAlarmDevice
     *@Description 根据传感器id查询火警主机设备
     */
    FireAlarmDevice findFireAlarmDevice(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-10-28 16:00
     *@Param: java.lang.String
     *@Return: void
     *@Description 通过传输装置id修改设备心跳时间
     */
    void modifyHeartTime(FireAlarmDevice fireAlarmDevice );
    /**
     *@Author: dwt
     *@Date: 2019-10-28 16:05
     *@Param: java.lang.Long
     *@Return: void
     *@Description 根据id修改设备状态
     */
    void modifyDeviceStatusById(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-20 15:38
     *@Param: FireAlarmDevice
     *@Return: void
     *@Description 保存火警主机设备
     */
    void saveFireAlarmDevice(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-20 16:03
     *@Param:
     *@Return: FireAlarmDevice
     *@Description 查询所有火警主机设备
     */
    List<FireAlarmDevice> findAllFireAlarmDevice();
    /**
     *@Author: dwt
     *@Date: 2019-11-25 15:34
     *@Param: FireAlarmDevice
     *@Return: FireAlarmDevice
     *@Description 查询是否有相同的火警主机设备
     */
    List<FireAlarmDevice> findIsSameFireAlarmDevice(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-25 16:23
     *@Param: 
     *@Return: 
     *@Description TODO
     */
    void modifyFireAlarmDevice(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-27 15:58
     *@Param: FireAlarmDevice
     *@Return:
     *@Description 逻辑删除火警主机设备
     */
    void deleteFireAlarmDevice(List<FireAlarmDevice> list);
    /**
     *@Author: dwt
     *@Date: 2019-11-28 9:33
     *@Param: FireAlarmDevice
     *@Return: List<FireAlarmDevice>
     *@Description 根据id/ids查询火警主机
     */
    List<FireAlarmDevice> findFireAlarmDeviceById(FireAlarmDevice fireAlarmDevice);
    /**
     *@Author: dwt
     *@Date: 2019-12-11 16:23
     *@Param: java.lang.Long
     *@Return: FireAlarmDevice
     *@Description 根据id查询火警主机
     */
    FireAlarmDevice findFireDeviceById(Long id);
    /**
     *@Author: dwt
     *@Date: 2020-04-09 10:49
     *@Param: FireAlarmDevice
     *@Return: java.lang.Integer
     *@Description 大屏火警主机设备数和状态数查询接口
     */
    Integer findFireAlarmIsLineCountScreen(FireAlarmDevice fireAlarmDevice);
}
