package com.xjt.cloud.iot.core.service.service.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/20 15:09
 * @Description: 水压设备信息管理
 */
public interface WaterDeviceService {
    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data checkWaterDeviceSensorNo(String json);
    /**
     *
     * 功能描述:保存水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data saveWaterDevice(String json);

    /**
     *
     * 功能描述:修改水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data modifyWaterDevice(String json);

    /**
     *
     * 功能描述:删除水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data delWaterDevice(String json);

    /**
     *
     * 功能描述:查询水压设备修改记录信息列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data findWaterDeviceEditList(String json);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data findWaterDeviceList(String json);

    /**
     *
     * 功能描述:查询水压设备汇总报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    Data findWaterDeviceSummaryReport(String json);

    /**
     * @Description 查询app首页水设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectWaterData(String json);

    /**
     *
     * 功能描述:查询水压设备大屏信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    Data findWaterDeviceScreen(String json);

    /**
     *
     * 功能描述:水压设备下载
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    void downloadWaterDevice(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:选中水压设备下载
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/10/8 9:51
     */
    void downloadWaterDeviceByList(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:水压设备离线任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    Data waterOffLineTask();

    /**
     *
     * 功能描述:查询水压设备
     *
     * @return:
     * @auther: zhangZaiFa
     * @date: 2019/11/19 14:44
     */
    Data findWaterDevice(String json);
    /**
     *@Author: dwt
     *@Date: 2020-08-10 14:20
     *@Param: json
     *@Return: Data
     *@Description 查询消火栓设备数以及巡查点信息列表
     */
    Data findHydrantDeviceCheckPoints(String json);
    /**
     *@Author: dwt
     *@Date: 2020-08-11 15:35
     *@Param: json
     *@Return: void
     *@Description 修改巡查点经纬度
     */
    Data modifyCheckPointLatAndLng(String json);
}
