package com.xjt.cloud.iot.core.service.service.report;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName IotReportService
 * @Description 物联设备报表
 * @Author wangzhiwen
 * @Date 2020/12/9 10:48
 **/
public interface IotReportService {
    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询物联设备运营报表饼图
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     */
    Data findIotDeviceFailCountPieChart(String json);

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询运营报表柱状图
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    Data findIotDeviceEventCountColumnChart(String json);

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询运营报表设备事件异常信息列表
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    Data findIotDeviceEventFailTypeCountList(String json);

    /**
     * 查询运营报表设备事件异常信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/01/4
     */
    void downDeviceEventFailTypeCount(String json, HttpServletRequest request, HttpServletResponse response);

    /**
     * 导出 运营报表设备事件异常信息 详情
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/01/13
     */
    void downDeviceEventFailTypeDetails(String json, HttpServletRequest request, HttpServletResponse response);
}
