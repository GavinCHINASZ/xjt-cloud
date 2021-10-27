package com.xjt.cloud.maintenance.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.entity.device.DeviceType;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:52
 * @Description:设备系统与类型管理业务接口
 */
public interface DeviceTypeService {
    /**
     *
     * 功能描述:查询设备系统信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findParentDeviceTypeList(String json);

    /**
     *
     * 功能描述:查询设备系统信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findParentDeviceType(String json);

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findDeviceTypeList(String json);

    /**
     *
     * 功能描述:查询设备类型信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<DeviceType> findDeviceTypeList(DeviceType deviceType);
    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 未绑定物联设备
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findDeviceTypeListByCheckPoint(String json);

    /**
     *
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 已绑定绑定物联设备
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findDeviceTypeListByCheckPointBind(String json);


    /**
     *
     * 功能描述:查询设备类型信息
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Data findDeviceType(String json);

    /**
     *
     * 功能描述:查询设备系统树
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    Data findDeviceSysTree(String json);

    /**
     *
     * 功能描述:查询未删除设备类型信息列表，以拼音排序
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/9 9:03
     */
    Data findDeviceTypeOrderPinYin(String json);

    /**
     *
     * 功能描述:设备系统缓存初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:51
     */
    void deviceSysCacheInit();
}
