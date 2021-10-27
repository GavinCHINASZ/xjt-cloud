package com.xjt.cloud.iot.core.service.impl.electrical;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireEventDao;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireReport;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ClassName ElectricalFireEventServiceImpl
 * @Author Administrator
 * @Date 2020-09-23 14:22
 * @Description TODO
 * @Version 1.0
 */
@Service
public class ElectricalFireEventServiceImpl extends AbstractService implements ElectricalFireEventService {

    @Autowired
    private ElectricalFireEventDao electricalFireEventDao;

    /**
     * @Author: dwt
     * @Date: 2020-09-15 14:02
     * @Param: java.lang.String
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Description 查询电气火灾事件列表
     */
    @Override
    public Data findElectricalFireEventList(String json) {
        ElectricalFireEvent electricalFireEvent = JSONObject.parseObject(json, ElectricalFireEvent.class);
        String[] orderCols = electricalFireEvent.getOrderCols();
        if (orderCols != null && orderCols.length > 0) {
            for (int i = 0; i < orderCols.length; i++) {
                Arrays.fill(orderCols, i, i + 1, "efe." + orderCols[i]);
            }
            electricalFireEvent.setOrderCols(orderCols);
        }
        List<ElectricalFireEvent> objList = electricalFireEventDao.findElectricalFireEventList(electricalFireEvent);
        Integer totalCount = electricalFireEventDao.findElectricalFireEventCount(electricalFireEvent);
        Map<String, Object> map = new HashMap<>(2);
        map.put("listObj", objList);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * @Author: dwt
     * @Date: 2020-09-24 9:14
     * @Param: java.lang.String
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Description 电气火灾事件处理
     */
    @Override
    public Data modifyElectricalFireEvent(String json) {
        ElectricalFireEvent electricalFireEvent = JSONObject.parseObject(json, ElectricalFireEvent.class);
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());// 用户ID
        String loginName = SecurityUserHolder.getUserName();
        electricalFireEvent.setHandlerId(loginUserId);
        electricalFireEvent.setHandlerName(loginName);
        electricalFireEvent.setHandleStatus(1);
        Integer a = electricalFireEventDao.modifyElectricalFireEvent(electricalFireEvent);
        if (a > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * @Author: dwt
     * @Date: 2020-09-24 9:43
     * @Param: java.lang.String
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Description 查询电气火灾事件汇总分析
     */
    @Override
    public Data findElectricalFireEventSummaryAnalysis(String json) {
        ElectricalFireEvent electricalFireEvent = JSONObject.parseObject(json, ElectricalFireEvent.class);
        ElectricalFireReport electricalFireReport = JSONObject.parseObject(json, ElectricalFireReport.class);
        Date startTime = electricalFireEvent.getStartTime();
        Date endTime = electricalFireEvent.getEndTime();
        Calendar c = Calendar.getInstance();
        if (endTime != null) {
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.SECOND, -1);
            endTime = c.getTime();
            electricalFireEvent.setEndTime(endTime);
            electricalFireReport.setEndTime(endTime);
        }
        Integer dateType = DateUtils.getBetweenDateTimeType(startTime, endTime);
        Integer dValue = DateUtils.getDateTimeCount(dateType, endTime, startTime);
        dValue = Math.abs(dValue);
        electricalFireEvent.setDateType(dateType);
        electricalFireEvent.setDateNum(dValue);
        List<ElectricalFireReport> listObj = electricalFireEventDao.findBrokenLineEventNum(electricalFireEvent);
        ElectricalFireReport eventReport = electricalFireEventDao.countElectricalFireReport(electricalFireReport);
        Map<String, Object> map = new HashMap<>(2);
        map.put("listObj", listObj);
        map.put("eventReport", eventReport);
        return asseData(map);
    }

    /**
     * @Author: dwt
     * @Date: 2020-09-24 14:06
     * @Param: java.lang.String
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Description 查询电气火灾事件详情
     */
    @Override
    public Data findElectricalFireEventDetail(String json) {
        ElectricalFireEvent electricalFireEvent = JSONObject.parseObject(json, ElectricalFireEvent.class);
        electricalFireEvent = electricalFireEventDao.findElectricalFireEventDetail(electricalFireEvent);
        return asseData(electricalFireEvent);
    }

    /**
     * @Author: dwt
     * @Date: 2020-09-23 14:11
     * @Param: java.lang.String, javax.servlet.http.HttpServletResponse
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Description 导出电气火灾设备事件列表
     */
    @Override
    public void downLoadElectricalFireDeviceEventList(String json, HttpServletResponse resp) {
        ElectricalFireEvent event = JSONObject.parseObject(json, ElectricalFireEvent.class);
        Date endTime = event.getEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            event.setEndTime(c.getTime());
        }
        List<ElectricalFireEvent> eventList = electricalFireEventDao.findElectricalFireEventList(event);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, event.getProjectId());
        String projectName = projectJson.getString("projectName");
        jsonObject.put("title", projectName + "电气火灾设备事件列表");
        String[] keys = {"rowNum", "eventTypeDesc", "description", "deviceName", "leakageCurrValue", "leakageAlarmValue",
                "tempValue", "tempAlarmValue", "createTimeDesc", "recoverTimeDesc", "recoverStatusDesc", "handleStatusDesc",
                "deviceLocation", "deviceQrNo", "pointQrNo", "sensorNo"};
        ExcelUtils.createAndDownloadExcel(resp, eventList, keys, ConstantsIot.ELECTRICAL_FIRE_DEVICE_EVENT_LIST_MODEL_FIRE_PATH,
                3, null, jsonObject, "1:0");
    }
}
