package com.xjt.cloud.iot.core.service.service.linkage;

import com.xjt.cloud.commons.utils.Data;

/**
 * 声光 事件处理信息
 *
 * @author huanggc
 * @date 2020/12/9
 **/
public interface LinkageEventHandleService {

    /**
     * 保存事件处理信息
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveLinkageEventHandle(String json);

    /**
     * 查询事件处理信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageEventHandleList(String json);

    /**
     * 查询 声光设备事件导常分类统计柱状图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/7 10:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageEventFaultTypeColumnChart(String json);
}
