package com.xjt.cloud.iot.core.service.service.electrical;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 电气火灾事件记录
 *
 * @author dwt
 * @date 2020-09-18 10:57
 */
public interface ElectricalFireEventRecordService {

    /**
     * 接收移动物联平台推送信息
     *
     * @param request   HttpServletRequest
     * @param msg       消息
     * @param nonce     String
     * @param signature String
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-24 15:48
     */
    Data receiveMobileIotInformation(HttpServletRequest request, String msg, String nonce, String signature);

    /**
     * @param msg
     * @param nonce
     * @param signature
     * @return
     */
    String check(String msg, String nonce, String signature);

    /**
     * 查询电气火灾设备事件记录数据分析
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 14:29
     */
    Data findDeviceEventRecordList(String json);

    /**
     * 查询电气火灾设备最新事件记录
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 14:30
     */
    Data findNewestEventRecord(String json);

    /**
     * netty 电气火灾消息
     *
     * @param jsonObject JSONObject
     * @return List<JSONObject>
     * @author huanggc
     * @date 2021/05/17
     */
    List<JSONObject> electricalFireEventRecordServiceSys(JSONObject jsonObject);
}
