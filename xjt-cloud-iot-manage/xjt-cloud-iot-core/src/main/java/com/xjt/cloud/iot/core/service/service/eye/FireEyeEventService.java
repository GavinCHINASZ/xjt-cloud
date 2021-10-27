package com.xjt.cloud.iot.core.service.service.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 火眼事件接口
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:35
 */
public interface FireEyeEventService {

    /**
     * 火眼事件保存
     *
     * @param jsonObject JSONObject
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     * @author zhangZaiFa
     * @date 2020/4/3 14:57
     **/
    List<JSONObject> fireEyeEventSave(JSONObject jsonObject);

    /**
     * 查询火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:23
     **/
    Data findFireEyeEventList(String json);

    /**
     * 保存 火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    Data saveFireEyeEvent(String json);

    /**
     * 查询 火眼事件汇总
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/21
     **/
    Data findFireEyeEventSummary(String json);

    /**
     * 查询 火眼事件 折线图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 202103-23
     **/
    Data findFireEyeEventReportCount(String json);

    /**
     * 火眼事件列表导出表格
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2021-03-25
     **/
    void downFireEyeEventList(String json, HttpServletResponse resp);

    /**
     * 查询 火眼事件 大屏
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/04/28
     **/
    Data findFireEyeEventScreen(String json);
}
