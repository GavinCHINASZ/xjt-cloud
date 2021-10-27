package com.xjt.cloud.iot.core.service.service.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/20 15:09
 * @Description: 水压设备信息管理
 */
public interface VesaDeviceService {
    /**
     *
     * 功能描述:检查传感器id是否存在
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data checkVesaDeviceSensorNo(String json);
    /**
     *
     * 功能描述:保存水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data saveVesaDevice(String json);

    /**
     *
     * 功能描述:修改水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data modifyVesaDevice(String json);

    /**
     *
     * 功能描述:删除水压设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data delVesaDevice(String json);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data findVesaDeviceList(String json);

    /**
     *
     * 功能描述:查询水压设备汇总报表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:59
     */
    Data findVesaDeviceSummaryReport(String json);

    /**
     *
     * 功能描述:水压设备离线任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/19 14:44
     */
    Data vesaOffLineTask();

    JSONObject isSensorPresence(String sensor);

    /**
     *
     * 功能描述:查询水压设备信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/23 17:36
     */
    Data findVesaDeviceListApp(String json);

}
