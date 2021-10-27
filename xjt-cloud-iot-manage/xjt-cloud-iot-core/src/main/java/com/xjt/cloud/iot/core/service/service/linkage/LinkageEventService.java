package com.xjt.cloud.iot.core.service.service.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 联动事件 service接口
 *
 * @author huanggc
 * @date 2020/08/18
 **/
public interface LinkageEventService extends BaseService {

    /**
     * 功能描述:查询 联动事件 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageEventList(String json);

    /**
     * 功能描述:导出 联动事件 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/19
     * @return void
     */
    void downLinkageEventList(String json, HttpServletResponse response);

    /**
     * 功能描述:查询 联动事件 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageEventSummaryReport(String json);

    /**
     * 功能描述:查询 联动事件 拆线图
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/18
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageEventReportCount(String json);
}
