package com.xjt.cloud.iot.core.service.service.vesa;

import com.xjt.cloud.commons.utils.Data;

/**
 * 极早期 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
public interface VesaEventHandleService {

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveVesaEventHandle(String json);

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findVesaEventHandleList(String json);

    /**
     * 查询火警设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findVesaEventFaultTypeColumnChart(String json);
}
