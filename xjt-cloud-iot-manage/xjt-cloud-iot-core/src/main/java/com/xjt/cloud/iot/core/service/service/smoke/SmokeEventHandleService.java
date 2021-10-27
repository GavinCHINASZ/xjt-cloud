package com.xjt.cloud.iot.core.service.service.smoke;

import com.xjt.cloud.commons.utils.Data;

/**
 * 烟感 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
public interface SmokeEventHandleService {

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveSmokeEventHandle(String json);

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeEventHandleList(String json);

    /**
     * 查询 烟感设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeEventFaultTypeColumnChart(String json);
}
