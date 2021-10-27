package com.xjt.cloud.admin.manage.service.service.iot;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.iot.IotDeviceReport;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 *@ClassName IotDeviceReportService
 *@Author dwt
 *@Date 2020-11-10 10:10
 *@Version 1.0
 */
public interface IotDeviceReportService {
    /**
     *@Author: dwt
     *@Date: 2020-11-10 9:45
     *@Param:
     *@Return:
     *@Description 查询火警主机物联设备报表
     */
    ScriptPage<IotDeviceReport> findIotDeviceReportList(AjaxPage ajaxPage, IotDeviceReport iotDeviceReport);

    /**
     * @Description 物联设备报表下载
     *
     * @author wangzhiwen
     * @date 2020/12/1 15:11
     * @return void
     */
    void downIotDeviceReportExcel(HttpServletResponse response);
    /**
     *@Author: dwt
     *@Date: 2020-11-16 11:13
     *@Param:
     *@Return:
     *@Description 火警主机饼图数据查询
     */
    Data findIotFireAlarmDeviceCountPie();
}
