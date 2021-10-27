package com.xjt.cloud.iot.core.service.impl.report;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.report.IotReportDao;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.linkage.LinkageEvent;
import com.xjt.cloud.iot.core.entity.smoke.SmokeEvent;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.entity.water.EventFaultReport;
import com.xjt.cloud.iot.core.entity.water.EventFaultReportSort;
import com.xjt.cloud.iot.core.entity.water.WaterRecord;
import com.xjt.cloud.iot.core.service.impl.fire.FireAlarmEventServiceImpl;
import com.xjt.cloud.iot.core.service.impl.linkage.LinkageEventServiceImpl;
import com.xjt.cloud.iot.core.service.impl.smoke.SmokeEventServiceImpl;
import com.xjt.cloud.iot.core.service.impl.vesa.VesaDeviceRecordServiceImpl;
import com.xjt.cloud.iot.core.service.impl.water.WaterDeviceRecordServiceImpl;
import com.xjt.cloud.iot.core.service.service.report.IotReportService;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *  物联设备报表
 *
 * @author wangzhiwen
 * @date 2020/12/9 10:48
 **/
@Service
public class IotReportServiceImpl extends AbstractService implements IotReportService {
    @Autowired
    private IotReportDao iotReportDao;
    @Autowired
    private SmokeEventServiceImpl smokeEventService;
    @Autowired
    private FireAlarmEventServiceImpl fireAlarmEventService;
    @Autowired
    private VesaDeviceRecordServiceImpl vesaDeviceRecordService;
    @Autowired
    private LinkageEventServiceImpl linkageEventService;
    @Autowired
    private WaterDeviceRecordServiceImpl waterDeviceRecordService;

    /**
     *  查询物联设备运营报表饼图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/12/14 9:54
     */
    @Override
    public Data findIotDeviceFailCountPieChart(String json) {
        EventFaultReport waterEventHandle = JSONObject.parseObject(json, EventFaultReport.class);
        return asseData(iotReportDao.findIotDeviceFailCountPieChart(waterEventHandle));
    }

    /**
     *  查询运营报表柱状图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    @Override
    public Data findIotDeviceEventCountColumnChart(String json) {
        EventFaultReport waterEventHandle = JSONObject.parseObject(json, EventFaultReport.class);
        List<EventFaultReport> list = iotReportDao.findIotDeviceEventCountColumnChart(waterEventHandle);
        return asseData(eventFaultReportGroupSort(list));
    }

    /**
     *  查询运营报表设备事件异常信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2020/12/14 10:50
     */
    @Override
    public Data findIotDeviceEventFailTypeCountList(String json) {
        EventFaultReport waterEventHandle = JSONObject.parseObject(json, EventFaultReport.class);
        List<EventFaultReport> list = iotReportDao.findIotDeviceEventFailTypeCountList(waterEventHandle);
        List<List<EventFaultReport>> efrList = eventFaultReportGroupSort(list);
        list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(efrList)) {
            for (List<EventFaultReport> temList : efrList) {
                for (EventFaultReport efr : temList) {
                    list.add(efr);
                }
            }
        }
        return asseData(list);
    }

    /**
     * 导出 运营报表设备事件异常信息列表
     *
     * @param json 参数 参数
     * @author huanggc
     * @date 2021/01/4
     */
    @Override
    public void downDeviceEventFailTypeCount(String json, HttpServletRequest request, HttpServletResponse response) {
        EventFaultReport waterEventHandle = JSONObject.parseObject(json, EventFaultReport.class);

        List<EventFaultReport> list = iotReportDao.findIotDeviceEventFailTypeCountListDown(waterEventHandle);
        List<List<EventFaultReport>> efrList = eventFaultReportGroupSort(list);
        list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(efrList)) {
            for (List<EventFaultReport> temList : efrList) {
                for (EventFaultReport efr : temList) {
                    list.add(efr);
                }
            }
        }
        Map<Integer, Object> dateMap = new LinkedHashMap<>(1);
        dateMap.put(0, list);

        Map<Integer, Integer> headIndexMap = new LinkedHashMap<>(1);
        headIndexMap.put(0, 5);

        Map<Integer, String> titleIndexMap = new LinkedHashMap<>(1);
        titleIndexMap.put(0, "1:0");

        Long projectId = waterEventHandle.getProjectId();
        JSONObject jsonObject = JSONObject.parseObject(json);
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--物联设备运营报表列表导出");

        String beginTimeStr = DateUtils.getChangeDate(waterEventHandle.getBeginTime());
        String endTimeStr = DateUtils.getChangeDate(waterEventHandle.getEndTime());
        jsonObject.put("beginTimeStrEndTimeStr", beginTimeStr + " 至 " + endTimeStr);

        Map<Integer, String[]> keysMap = new LinkedHashMap<>(1);
        String[] keys = {"devicesTypeDesc", "eventsTypeDesc", "processedDesc", "faultTypeDesc", "repairProposalDesc"};
        keysMap.put(0, keys);

        /*String[] imagesArr = {"http://192.168.0.200:6020/fault/2020/12/02/1606888025402.jpeg",
                              "http://192.168.0.200:6020/fault/2020/12/02/1606888025402.jpeg",
                              "http://192.168.0.200:6020/fault/2020/12/02/1606888025402.jpeg"};
        waterEventHandle.setImages(imagesArr);*/

        String[] images = waterEventHandle.getImages();
        if (images != null && images.length > 0) {
            Map<Integer, String[]> imagesMap = new LinkedHashMap<>(1);
            imagesMap.put(0, waterEventHandle.getImages());

            Map<Integer, List<HSSFClientAnchor>> anchorsMap = new LinkedHashMap<>(1);
            List<HSSFClientAnchor> anchors = new ArrayList<>(images.length);
            // anchor主要用于设置图片的属性
            // dx1 dy1 起始单元格中的x,y坐标. dx2 dy2 结束单元格中的x,y坐标
            // col1, row1 指定起始的单元格，下标从0开始 col2, row2 指定结束的单元格 ，下标从0开始
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 1002, 255, (short) 0, 2, (short) 2, 2);
            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 600, 255, (short) 3, 2, (short) 4, 2);
            HSSFClientAnchor anchor3 = new HSSFClientAnchor(602, 0, 0, 255, (short) 4, 2, (short) 10, 2);
            anchors.add(anchor1);
            anchors.add(anchor2);
            anchors.add(anchor3);
            anchorsMap.put(0, anchors);

            IotReportIoServiceImpl.createAndDownloadExcelNotStyle(response, dateMap, keysMap, ConstantsIot.IOT_DEVICE_REPORT_EXCEL_MODEL_FILE_PATH,
                    headIndexMap, null, jsonObject, titleIndexMap, imagesMap, anchorsMap);
        }else {
            IotReportIoServiceImpl.createAndDownloadExcelNotStyle(response, dateMap, keysMap, ConstantsIot.IOT_DEVICE_REPORT_EXCEL_MODEL_FILE_PATH,
                    headIndexMap, null, jsonObject, titleIndexMap, null, null);
        }
    }

    /**
     * 物联设备运营报表 导出全部
     *
     * @param json 参数
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void downDeviceEventFailTypeDetails(String json, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        EventFaultReport waterEventHandle = JSONObject.parseObject(json, EventFaultReport.class);
        Long projectId = waterEventHandle.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--物联设备运营报表详情");

        String beginTimeStr = DateUtils.getChangeDate(waterEventHandle.getBeginTime());
        String endTimeStr = DateUtils.getChangeDate(waterEventHandle.getEndTime());
        jsonObject.put("beginTimeStrEndTimeStr", beginTimeStr + " 至 " + endTimeStr);

        Map<Integer, Object> dateMap = new LinkedHashMap<>(9);
        Map<Integer, String[]> keysMap = new LinkedHashMap<>(9);
        Map<Integer, Integer> headIndexMap = new LinkedHashMap<>(9);
        Map<Integer, String> titleIndexMap = new LinkedHashMap<>(9);

        List<EventFaultReport> eventFaultReportList = iotReportDao.findIotDeviceEventFailTypeCountListDown(waterEventHandle);
        if (CollectionUtils.isNotEmpty(eventFaultReportList)){
            List<List<EventFaultReport>> efrList = eventFaultReportGroupSort(eventFaultReportList);

            List<EventFaultReport> eventFaultReportLists = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(efrList)) {
                for (List<EventFaultReport> temList : efrList) {
                    for (EventFaultReport efr : temList) {
                        eventFaultReportLists.add(efr);
                    }
                }
            }
            dateMap.put(0, eventFaultReportLists);
            String[] keys = {"devicesTypeDesc", "eventsTypeDesc", "processedDesc", "faultTypeDesc", "repairProposalDesc"};
            keysMap.put(0, keys);
            headIndexMap.put(0, 5);
            titleIndexMap.put(0, "1:0");
        }

        // 水压监测 1=表格中的第几个sheet
        WaterRecord waterRecord = JSONObject.parseObject(json, WaterRecord.class);
        List<WaterRecord> waterRecordList = waterDeviceRecordService.getWaterRecordList(waterRecord);
        if (CollectionUtils.isNotEmpty(waterRecordList)) {
            dateMap.put(1, waterRecordList);
            String[] keys = {"rowNum", "deviceSysName", "pointQrNo", "sensorNoDesc", "eventTypeDesc", "faultType", "descriptionDesc",
                    "createTimeDesc", "recoverTimeDesc", "handleStatusDesc", "handleUserNameDesc", "eventHandleTimeDesc", "pointLocationDesc"};
            keysMap.put(1, keys);
            headIndexMap.put(1, 3);
            titleIndexMap.put(1, "1:0");
        }

        // 智能水浸 2
        waterRecord.setType(3);
        List<WaterRecord> waterImmersionList = waterDeviceRecordService.getWaterRecordList(waterRecord);
        if (CollectionUtils.isNotEmpty(waterImmersionList)) {
            dateMap.put(2, waterImmersionList);
            String[] keys = {"rowNum", "deviceSysName", "pointQrNo", "sensorNoDesc", "eventTypeDesc", "faultType", "descriptionDesc",
                    "createTimeDesc", "recoverTimeDesc", "handleStatusDesc", "handleUserNameDesc", "eventHandleTimeDesc", "pointLocationDesc"};
            keysMap.put(2, keys);
            headIndexMap.put(2, 3);
            titleIndexMap.put(2, "1:0");
        }

        // 智能烟感 3
        SmokeEvent smokeEvent = JSONObject.parseObject(json, SmokeEvent.class);
        List<SmokeEvent> smokeEventList = smokeEventService.getSmokeEventList(smokeEvent);
        if (CollectionUtils.isNotEmpty(smokeEventList)) {
            dateMap.put(3, smokeEventList);
            String[] keys = {"rowNum", "deviceName", "checkPointQrNo", "sensorId", "eventTypeDesc", "faultType", "descriptionDesc",
                    "createTimeDesc", "recoverTimeDesc", "handleStatusDesc", "handleUserNameDesc", "eventHandleTimeDesc", "pointLocationDesc"};
            keysMap.put(3, keys);
            headIndexMap.put(3, 3);
            titleIndexMap.put(3, "1:0");
        }

        // 火警主机 3
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        List<FireAlarmEvent> fireAlarmEventList  = fireAlarmEventService.downLoadFireAlarmEventList(fireAlarmEvent);
        if (CollectionUtils.isNotEmpty(fireAlarmEventList)) {
            dateMap.put(4, fireAlarmEventList);
            String[] keys = {"rowNum", "eventTypeDesc", "fireAlarmNo", "alarmPosition", "description", "createTimeDesc", "recoverTimeDesc",
                    "faultType", "handleDescriptionDesc", "handleStatusDesc", "handleUserNameDesc", "endHandleTimeDesc"};
            keysMap.put(4, keys);
            headIndexMap.put(4, 3);
            titleIndexMap.put(4, "1:0");
        }

        // 极早期预警 5
        VesaRecord vesaRecord = JSONObject.parseObject(json, VesaRecord.class);
        List<VesaRecord> vesaRecordList = vesaDeviceRecordService.getVesaRecordList(vesaRecord);
        if (CollectionUtils.isNotEmpty(vesaRecordList)) {
            dateMap.put(5, vesaRecordList);
            String[] keys = {"rowNum", "pointQrNo", "eventTypeDesc", "eventDescs", "sensorName", "loopName", "detector", "detectorType", "handleDescriptionDesc",
                    "createTimeDesc", "recoverTimeDesc", "handleStatusDesc", "handleUserNameDesc", "eventHandleTimeDesc", "pointName"};
            keysMap.put(5, keys);
            headIndexMap.put(5, 3);
            titleIndexMap.put(5, "1:0");
        }

        // 智能消火栓 6
        waterRecord.setType(8);
        List<WaterRecord> fireHydrantList = waterDeviceRecordService.getWaterRecordList(waterRecord);
        if (CollectionUtils.isNotEmpty(fireHydrantList)) {
            dateMap.put(6, fireHydrantList);
            String[] keys = {"rowNum", "deviceSysName", "pointQrNo", "sensorNoDesc", "eventTypeDesc", "faultType", "descriptionDesc",
                    "createTimeDesc", "recoverTimeDesc", "handleStatusDesc", "handleUserNameDesc", "eventHandleTimeDesc", "pointLocationDesc"};
            keysMap.put(6, keys);
            headIndexMap.put(6, 3);
            titleIndexMap.put(6, "1:0");
        }

        // 声光报警 7
        LinkageEvent linkageEvent = JSONObject.parseObject(json, LinkageEvent.class);
        List<LinkageEvent> linkageEventList = linkageEventService.findLinkageEventList(linkageEvent);
        if (CollectionUtils.isNotEmpty(linkageEventList)) {
            dateMap.put(7, linkageEventList);
            String[] keys = {"rowNum", "eventTypeDesc", "faultCheckPointQrNo", "faultDeviceQrNo", "faultDeviceName", "sensorId", "descriptionDesc", "faultType",
                    "createTimeDesc", "endHeartbeatTimeDesc", "handleStatusDesc", "handleUserNameDesc", "endHandleTimesDesc", "pointLocationDesc"};
            keysMap.put(7, keys);
            headIndexMap.put(7, 3);
            titleIndexMap.put(7, "1:0");
        }

        // 电气火灾 8 ???


        String[] images = waterEventHandle.getImages();
        if (images != null && images.length > 0) {
            Map<Integer, String[]> imagesMap = new LinkedHashMap<>(1);
            imagesMap.put(0, waterEventHandle.getImages());

            Map<Integer, List<HSSFClientAnchor>> anchorsMap = new LinkedHashMap<>(1);
            List<HSSFClientAnchor> anchors = new ArrayList<>(images.length);
            // anchor主要用于设置图片的属性
            // dx1 dy1 起始单元格中的x,y坐标. dx2 dy2 结束单元格中的x,y坐标
            // col1, row1 指定起始的单元格，下标从0开始 col2, row2 指定结束的单元格 ，下标从0开始
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 1002, 255, (short) 0, 2, (short) 2, 2);
            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 600, 255, (short) 3, 2, (short) 4, 2);
            HSSFClientAnchor anchor3 = new HSSFClientAnchor(602, 0, 0, 255, (short) 4, 2, (short) 10, 2);
            anchors.add(anchor1);
            anchors.add(anchor2);
            anchors.add(anchor3);
            anchorsMap.put(0, anchors);

            IotReportIoServiceImpl.createAndDownloadExcelNotStyle(response, dateMap, keysMap, ConstantsIot.IOT_DEVICE_REPORT_DETAILS_EXCEL_MODEL_FILE_PATH,
                    headIndexMap, null, jsonObject, titleIndexMap, imagesMap, anchorsMap);
        }else {
            IotReportIoServiceImpl.createAndDownloadExcelNotStyle(response, dateMap, keysMap, ConstantsIot.IOT_DEVICE_REPORT_DETAILS_EXCEL_MODEL_FILE_PATH,
                    headIndexMap, null, jsonObject, titleIndexMap, null, null);
        }
    }

    /**
     *  物联设备运营报表数据分组排序
     *  
     * @param list List<EventFaultReport>
     * @return java.util.List<java.util.List < com.xjt.cloud.iot.core.entity.water.EventFaultReport>>
     * @author wangzhiwen
     * @date 2020/12/15 16:54
     */
    private List<List<EventFaultReport>> eventFaultReportGroupSort(List<EventFaultReport> list) {
        List<List<EventFaultReport>> temList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<EventFaultReport> efrList = new ArrayList<>();
            List<EventFaultReportSort> efrsList = new ArrayList<>();
            EventFaultReport efr;
            EventFaultReportSort efrs;
            int size = list.size();
            int total = 0;
            for (int i = 0; i < size; i++) {
                efr = list.get(i);
                efrList.add(efr);
                total += efr.getFaultCount();
                if (i < size - 1) {
                    if (efr.getDeviceType() != list.get(i + 1).getDeviceType()) {
                        efrs = new EventFaultReportSort();
                        efrs.setSortNum(total);
                        efrs.setList(efrList);
                        efrsList.add(efrs);
                        efrList = new ArrayList<>();
                        total = 0;
                    }
                } else {
                    efrs = new EventFaultReportSort();
                    efrs.setSortNum(total);
                    efrs.setList(efrList);
                    efrsList.add(efrs);
                }
            }
            if (CollectionUtils.isNotEmpty(efrsList)) {
                Collections.sort(efrsList);
                for (EventFaultReportSort eventFaultReportSort : efrsList) {
                    temList.add(eventFaultReportSort.getList());
                }
            }
        }
        return temList;
    }
}
