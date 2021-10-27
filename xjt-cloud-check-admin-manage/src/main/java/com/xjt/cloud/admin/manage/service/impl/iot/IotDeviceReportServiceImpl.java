package com.xjt.cloud.admin.manage.service.impl.iot;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.iot.IotDeviceReportDao;
import com.xjt.cloud.admin.manage.entity.iot.IotDeviceReport;
import com.xjt.cloud.admin.manage.service.service.iot.IotDeviceReportService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.ExcelUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @ClassName IotDeviceReportServiceImpl
 * @Author dwt
 * @Date 2020-11-10 10:14
 * @Version 1.0
 */
@Service
public class IotDeviceReportServiceImpl extends AbstractAdminService implements IotDeviceReportService {

    @Autowired
    private IotDeviceReportDao iotDeviceReportDao;


    /**
     * @Author: dwt
     * @Date: 2020-11-10 9:45
     * @Param:
     * @Return:
     * @Description 查询火警主机物联设备报表
     */
    @Override
    public ScriptPage<IotDeviceReport> findIotDeviceReportList(AjaxPage ajaxPage, IotDeviceReport iotDeviceReport) {
        iotDeviceReport = asseFindObj(iotDeviceReport, ajaxPage);
        iotDeviceReport = asseFindWhere(iotDeviceReport);
        return asseScriptPage(iotDeviceReportDao.findIotDeviceReportList(iotDeviceReport), iotDeviceReportDao.findIotDeviceReportListCount(iotDeviceReport));
    }

    /**
     * @return void
     * @Description 物联设备报表下载
     * @author wangzhiwen
     * @date 2020/12/1 15:11
     */
    @Override
    public void downIotDeviceReportExcel(HttpServletResponse response) {
        IotDeviceReport iotDeviceReport = new IotDeviceReport();
        String[] keys = {"rowNum", "projectName", "totalCount", "waterGageTotalCount", "waterGageOnLineCount", "waterGageOffLineCount", "waterLeachingTotalCount",
                "waterLeachingOnLineCount", "waterLeachingOffLineCount", "smokeTotalCount", "smokeOnLineCount", "smokeOffLineCount", "fireAlarmTotalCount",
                "fireAlarmOnLineCount", "fireAlarmOffLineCount", "vesaTotalCount", "vesaOnLineCount", "vesaOffLineCount", "fireHydrantTotalCount",
                "fireHydrantOnLineCount", "fireHydrantOffLineCount", "linkageTotalCount", "linkageOnLineCount", "linkageOffLineCount"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "物联设备离线报表");

        iotDeviceReport = asseFindWhere(iotDeviceReport);
        iotDeviceReport.setPageSize(null);
        List<IotDeviceReport> list = iotDeviceReportDao.findIotDeviceReportList(iotDeviceReport);

        ExcelUtils.createAndDownloadExcelNotStyle(response, list, keys, ConstantsClient.IOT_DEVICE_REPORT_MODEL_PATH, 3, null,
                jsonObject, "1:0");
    }

    /**
     * @param iotDeviceReport
     * @return com.xjt.cloud.admin.manage.entity.iot.IotDeviceReport
     * @Description 组装物联设备报表查询条件
     * @author wangzhiwen
     * @date 2020/12/1 15:17
     */
    private IotDeviceReport asseFindWhere(IotDeviceReport iotDeviceReport) {
        String orderByProjectIds = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_REPORT_CONFIG, ConstantsClient.IOT_DEVICE_REPORT_ORDER, "itemDescription");//排序的项目id数组
        String findProjectIds = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_REPORT_CONFIG, ConstantsClient.IOT_REPORT_FIND_PROJECT_ID, "itemDescription");//查询项目id数组
        if (StringUtils.isNotEmpty(orderByProjectIds)) {
            iotDeviceReport.setOrderByProjectIds(orderByProjectIds.split(","));
        }
        if (StringUtils.isNotEmpty(findProjectIds)) {
            iotDeviceReport.setFindProjectIds(findProjectIds.split(","));
        }
        return iotDeviceReport;
    }

    /**
     * @Author: dwt
     * @Date: 2020-11-16 11:24
     * @Param:
     * @Return:
     * @Description 查询火警主机饼图数据
     */
    @Override
    public Data findIotFireAlarmDeviceCountPie() {
        IotDeviceReport iotDeviceReport = iotDeviceReportDao.findIotFireAlarmDevicePieData();
        return asseData(iotDeviceReport);
    }


}
