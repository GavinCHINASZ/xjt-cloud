package com.xjt.cloud.iot.core.service.service.water;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/23 09:59
 * @Description: 水设备记录解析接口
 */
public interface WaterDeviceRecordResolveService {
    /**
     *
     * 功能描述: 水压OR水浸上传信息参数解析
     *
     * @param json
     * @return: List<JSONObject>
     * @auther: wangzhiwen
     * @date: 2019/10/23 13:49
     */
    List<JSONObject> waterSys(JSONObject json);

    /**
     *
     * 功能描述: 水压设备上传信息处理接口
     *
     * @param jsonObject
     * @return: List<JSONObject>
     * @auther: wangzhiwen
     * @date: 2019/9/27 15:24
     */
    List<JSONObject> waterDeviceDataAccess(JSONObject jsonObject);
}
