package com.xjt.cloud.iot.core.service.service.water;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName WaterEventHandleService
 * @Description 水压水浸消火栓事件处理信息
 * @Author wangzhiwen
 * @Date 2020/12/7 10:29
 **/
public interface WaterEventHandleService {

    /**
     * @Description 保存事件处理信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveWaterEventHandle(String json);

    /**
     * @Description 查询事件处理信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findWaterEventHandleList(String json);

    /**
     * @Description 查询水设备事件导常分类统计柱状图
     *
     * @param json
     * @author wangzhiwen
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findWaterEventFaultTypeColumnChart(String json);
}
