package com.xjt.cloud.iot.core.service.service.fire;

import com.xjt.cloud.commons.utils.Data;

/**
 * 火警 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
public interface FireAlarmEventHandleService {

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveFireAlarmEventHandle(String json);

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findFireAlarmEventHandleList(String json);

    /**
     * 查询火警设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findFireAlarmEventFaultTypeColumnChart(String json);
}
