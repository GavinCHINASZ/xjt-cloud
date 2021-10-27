package com.xjt.cloud.device.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 14:52
 * @Description:设备系统与类型管理业务接口
 */
public interface DeviceTypeService {

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
    Data deviceSysCacheInit();

    /**
     * 功能描述: 查询 设备类型(地址)
     *
     * @param
     * @auther huanggc
     * @date 2020-04-27
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceTypeMetroList(String json, HttpServletResponse response);

    /**
     * @Description 以部门id与楼层查询设备类型列表
     *
     * @author wangzhiwen
     * @date 2021/3/10 15:44
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceTypeListByOrgFloor(String json);
}
