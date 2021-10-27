package com.xjt.cloud.admin.manage.dao.device;

import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.Device;
import com.xjt.cloud.admin.manage.entity.device.DeviceType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/3 11:15
 * @Description: 设备类型管理ＤＡＯ
 */
@Repository
public interface DeviceTypeDao {

    /**
     *
     * 功能描述:  查询设备系统列表
     *
     * @param deviceType
     * @return: List<DeviceType>
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    List<DeviceType> findDeviceSysList(DeviceType deviceType);

    /**
     *
     * 功能描述:  查询设备系统列表
     *
     * @param deviceType
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:22
     */
    int findDeviceSysListTotalCount(DeviceType deviceType);

    /**
     *
     * 功能描述:新增设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    int saveDeviceSys(DeviceType deviceType);

    /**
     *
     * 功能描述:修改设备系统
     *
     * @param deviceType
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/12/3 11:24
     */
    int modifyDeviceSys(DeviceType deviceType);

    /**
     * @Description 查询项目设备类型列表
     *
     * @param deviceType
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.DeviceType>
     */
    List<DeviceType> findProjectDeviceTypeList(DeviceType deviceType);

    /**
     * @Description 查询项目巡检点列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    List<CheckPoint> findProjectCheckPointList(CheckPoint checkPoint);

    /**
     * @Description 查询项目设备列表
     *
     * @param device
     * @author wangzhiwen
     * @date 2020/8/27 15:59
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.device.Device>
     */
    List<Device> findProjectDeviceList(Device device);

}
