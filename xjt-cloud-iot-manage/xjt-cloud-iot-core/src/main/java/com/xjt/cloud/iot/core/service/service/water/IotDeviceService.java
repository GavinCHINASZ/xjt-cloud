package com.xjt.cloud.iot.core.service.service.water;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/27 10:19
 * @Description: 物联设备管理接口
 */
public interface IotDeviceService {
    /**
     *
     * 功能描述:物联网设备信息保存
     *
     * @param json
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    List<JSONObject> dataAccess(String json);

    /**
     *
     * 功能描述:物联网设备信息批量保存
     *
     * @param json
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    List<JSONObject> dataListAccess(String json);

    /**
     *
     * 功能描述:判断注册码是否存在
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 16:49
     */
    JSONObject isSensorPresence(String mtType,String from);
}
