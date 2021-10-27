package com.xjt.cloud.iot.web.controller.report;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.report.IotReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName IotReportController
 * @Description 物联设备报表
 * @Author wangzhiwen
 * @Date 2020/12/9 10:48
 **/
@RestController
@RequestMapping("/iot/report/")
public class IotReportController extends AbstractController {

    @Autowired
    private IotReportService iotReportService;

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询物联设备运营报表饼图
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     */
    @RequestMapping("findIotDeviceFailCountPieChart/{projectId}")
    public Data findIotDeviceFailCountPieChart(String json) {
        return iotReportService.findIotDeviceFailCountPieChart(json);
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询运营报表柱状图
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    @RequestMapping("findIotDeviceEventCountColumnChart/{projectId}")
    public Data findIotDeviceEventCountColumnChart(String json) {
        return iotReportService.findIotDeviceEventCountColumnChart(json);
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询运营报表设备事件异常信息列表
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    @RequestMapping("findIotDeviceEventFailTypeCountList/{projectId}")
    public Data findIotDeviceEventFailTypeCountList(String json) {
        return iotReportService.findIotDeviceEventFailTypeCountList(json);
    }

    /**
     * 导出 运营报表设备事件异常信息列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/01/4
     */
    @RequestMapping("downDeviceEventFailTypeCount/{projectId}")
    public void downDeviceEventFailTypeCount(String json, HttpServletRequest request, HttpServletResponse response) {
        iotReportService.downDeviceEventFailTypeCount(json, request, response);
    }

    /**
     * 导出 运营报表设备事件异常信息 详情
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/01/13
     */
    @RequestMapping("downDeviceEventFailTypeDetails/{projectId}")
    public void downDeviceEventFailTypeDetails(String json, HttpServletRequest request, HttpServletResponse response) {
        iotReportService.downDeviceEventFailTypeDetails(json, request, response);
    }
}
