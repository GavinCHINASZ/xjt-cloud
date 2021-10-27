package com.xjt.cloud.iot.core.service.service.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * 火眼接口
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:35
 */
public interface FireEyeDeviceService {
    /**
     * 查询火眼设备列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:06
     **/
    Data findFireEyeDeviceList(String json);

    /**
     * 查询火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:06
     **/
    Data findFireEyeDevice(String json);

    /**
     * 查询火眼设备状态
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-22
     **/
    Data findFireEyeDeviceState(String json);

    /**
     * 修改火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:06
     **/
    Data updFireEyeDevice(String json);

    /**
     * 删除火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:06
     **/
    Data delFireEyeDevice(String json);

    /**
     * 保存火眼设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:06
     **/
    Data saveFireEyeDevice(String json);

    /**
     * 火眼离线任务
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-23
     **/
    Data fireEyeDeviceOffLineTask();

    /**
     * 火眼设备列表导出表格
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2021-03-25
     **/
    void downFireEyeDeviceList(String json, HttpServletResponse resp);

    /**
     * 查询火眼设备监测状态
     *
     * @param json 参数
     * @author huanggc
     * @date 2021-03-25
     **/
    Data findFireEyeDeviceMonitoringStatus(String json);

    /**
     * @Description 查询app首页火眼信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectFireEyeData(String json);
}
