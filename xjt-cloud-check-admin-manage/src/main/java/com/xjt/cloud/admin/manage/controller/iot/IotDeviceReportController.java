package com.xjt.cloud.admin.manage.controller.iot;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.iot.IotDeviceReport;
import com.xjt.cloud.admin.manage.service.service.iot.IotDeviceReportService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName IotDeviceReportController
 * @Author Administrator
 * @Date 2020-11-10 10:25
 * @Description TODO
 * @Version 1.0
 */
@Controller
@RequestMapping("/iot/deviceReport/")
public class IotDeviceReportController extends AbstractController {

    @Autowired
    private IotDeviceReportService iotDeviceReportService;

    /**
     *
     * 功能描述: 跳转到物联设备火警主机页面
     *
     * @return:
     * @auther: dwt
     * @date: 2019/12/6 11:12
     */
    @RequestMapping("toIotDeviceReportListPage")
    public ModelAndView toIotDeviceReportListPage(){
        return new ModelAndView("iot/iotDeviceReport");
    }

    /**
     *@Author: dwt
     *@Date: 2020-11-10 9:45
     *@Param:
     *@Return:
     *@Description 查询火警主机物联设备报表
     */
    @RequestMapping("findIotDeviceReportList")
    @ResponseBody
    public ScriptPage<IotDeviceReport> findIotDeviceReportList(AjaxPage ajaxPage, IotDeviceReport iotDeviceReport) {
        return iotDeviceReportService.findIotDeviceReportList(ajaxPage, iotDeviceReport);
    }

    /**
     * @Description 物联设备报表下载
     *
     * @author wangzhiwen
     * @date 2020/12/1 15:11
     * @return void
     */
    @RequestMapping(value = "downIotDeviceReportExcel")
    public void downIotDeviceReportExcel(HttpServletResponse response){
        iotDeviceReportService.downIotDeviceReportExcel(response);
    }

    /**
     *@Author: dwt
     *@Date: 2020-11-16 11:25
     *@Param:
     *@Return:
     *@Description 查询火警主机物联设备饼图数据
     */
    @RequestMapping("findIotFireAlarmDeviceCountPie")
    @ResponseBody
    public Data findIotFireAlarmDeviceCountPie() {
        return iotDeviceReportService.findIotFireAlarmDeviceCountPie();
    }
}
