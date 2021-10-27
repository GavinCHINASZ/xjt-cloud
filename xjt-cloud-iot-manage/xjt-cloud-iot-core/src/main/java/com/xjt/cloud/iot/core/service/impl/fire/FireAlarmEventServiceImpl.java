package com.xjt.cloud.iot.core.service.impl.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.other.MetroUtils;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventDao;
import com.xjt.cloud.iot.core.dao.iot.fire.PictureUrlDao;
import com.xjt.cloud.iot.core.entity.fire.*;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 火警主机事件Service实现类
 *
 * @author dwt
 * @date 2019-10-11 15:34
 */
@Service
public class FireAlarmEventServiceImpl extends AbstractService implements FireAlarmEventService {
    @Autowired
    protected FireAlarmEventDao fireAlarmEventDao;
    @Autowired
    private FireAlarmDeviceDao fireAlarmDeviceDao;
    @Autowired
    private PictureUrlDao pictureUrlDao;

    /**
     * 火警主机事件汇总分析
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-22 18:15
     */
    @Override
    public Data findFireAlarmEventCount(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        Date startDate = fireAlarmEvent.getStartTime();
        Date endDate = fireAlarmEvent.getEndTime();
        fireAlarmEvent.setFlag(true);
        Integer recoverStatus = fireAlarmEvent.getRecoverStatus();
        Integer dateType = null;

        if (startDate != null && endDate != null) {
            dateType = DateUtils.getBetweenDateTimeType(startDate, endDate);
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.MILLISECOND, -1);
            endDate = c.getTime();
        }

        Map<String, Object> map = new HashMap<>();
        if (recoverStatus != null && recoverStatus == 2) {
            fireAlarmEvent.setEndTime(endDate);
            findFireAlarmAndTotalCount(fireAlarmEvent, map);
        } else {
            if (dateType == null) {
                return Data.isFail();
            }
            fireAlarmEvent.setType(1);
            map = getFireEventInfo(dateType, endDate, startDate, fireAlarmEvent);
            fireAlarmEvent.setEndTime(endDate);
            Long totalCount = fireAlarmEventDao.findFireAlarmEventCount(fireAlarmEvent);
            map.put("totalCount", totalCount);
        }
        return asseData(map);
    }

    /**
     * 大屏火警主机（多项目）近一周或本月/当天事件统计分析汇总
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-04-08 10:04
     */
    @Override
    public Data selectFireEventCount(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        Long[] projectIds = fireAlarmEvent.getProjectIds();
        if (projectIds == null || projectIds.length <= 0) {
            return Data.isFail();
        }
        String timeType = fireAlarmEvent.getTimeType();
        Calendar c = Calendar.getInstance();
        if ("week".equals(timeType)) {
            fireAlarmEvent.setDateType(2);
            fireAlarmEvent.setGroupType(2);

            fireAlarmEvent.setEndTime(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -6);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            fireAlarmEvent.setStartTime(c.getTime());
            fireAlarmEvent.setDateNum(7);
        } else if ("month".equals(timeType)) {
            fireAlarmEvent.setDateType(2);
            fireAlarmEvent.setGroupType(2);

            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            fireAlarmEvent.setStartTime(c.getTime());
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.MILLISECOND, -1);
            fireAlarmEvent.setEndTime(c.getTime());
            fireAlarmEvent.setDateNum(c.getActualMaximum(Calendar.DAY_OF_MONTH));
        }else if ("day".equals(timeType)) {
            fireAlarmEvent.setDateType(1);
            fireAlarmEvent.setGroupType(1);

            fireAlarmEvent.setStartTime(DateUtils.startDayDate(c.getTime()));
            fireAlarmEvent.setEndTime(DateUtils.endDayDate(c.getTime()));

            fireAlarmEvent.setDateNum(24);
        }

        List<FireEventReport> fireEventReportList = fireAlarmEventDao.findBrokenLineEventNum(fireAlarmEvent);
        FireEventReport fireEventReport = fireAlarmEventDao.findPieEventNum(fireAlarmEvent);

        FireAlarmDevice fireAlarmDevice = new FireAlarmDevice();
        fireAlarmDevice.setProjectIds(projectIds);
        Integer deviceSum = fireAlarmDeviceDao.findFireAlarmIsLineCountScreen(fireAlarmDevice);
        fireAlarmDevice.setDeviceStatus(1);
        Integer deviceOnLineCount = fireAlarmDeviceDao.findFireAlarmIsLineCountScreen(fireAlarmDevice);

        Map<String, Object> map = new HashMap<>(4);
        map.put("fireEventReportList", fireEventReportList);
        map.put("fireEventReport", fireEventReport);
        map.put("deviceSum", deviceSum);
        map.put("OnLineCount", deviceOnLineCount);
        return asseData(map);
    }

    /**
     * 火警主机事件列表导出
     *
     * @param json 参数
     * @author dwt
     * @date 2019-10-23 15:49
     */
    @Override
    public void downLoadFireAlarmEvent(String json, HttpServletResponse resp) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<FireAlarmEvent> list = downLoadFireAlarmEventList(fireAlarmEvent);

        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, fireAlarmEvent.getProjectId());
        String projectName = projectJson.getString("projectName");
        jsonObject.put("title", projectName + "告警事件列表");
        String[] keys = {"rowNum", "eventTypeDesc", "fireAlarmNo", "transDeviceName", "alarmPosition", "description", "createTimeDesc", "recoverTimeDesc", "eventCauseDesc",
                "monitorTypeDesc", "recoverStatusDesc", "handleStatusDesc", "endHandleTimeDesc"};
        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsIot.REPORT_FIRE_DEVICE_EVENT_MODEL_FILE_PATH, 3, null, jsonObject, "1:0");
    }

    /**
     * 火警事件list
     *
     * @param fireAlarmEvent FireAlarmEvent
     * @return List<FireAlarmEvent>
     */
    public List<FireAlarmEvent> downLoadFireAlarmEventList(FireAlarmEvent fireAlarmEvent) {
        fireAlarmEvent.setFlag(true);
        fireAlarmEvent.setPageSize(0);
        if (fireAlarmEvent.getType() == null) {
            fireAlarmEvent.setType(1);
        }
        Date endTime = fireAlarmEvent.getEndTime();
        if (fireAlarmEvent.getEndTime() != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            fireAlarmEvent.setEndTime(c.getTime());
            return fireAlarmEventDao.findFireAlarmEventList(fireAlarmEvent);
        } else {
            return fireAlarmEventDao.findFireAlarmEventList(fireAlarmEvent);
        }
    }

    /**
     * App端火警主机事件数据对比分析
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-03-04 11:50
     */
    @Override
    public Data findFireAlarmEventCountApp(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        if (fireAlarmEvent == null || fireAlarmEvent.getProjectId() == null) {
            return null;
        }
        String timeType = fireAlarmEvent.getTimeType();
        Date startDate = null;
        Date previousDate = null;
        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if ("day".equals(timeType)) {
            startDate = c.getTime();
            c.add(Calendar.DAY_OF_MONTH, -1);
            previousDate = c.getTime();
        } else if ("month".equals(timeType)) {
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = c.getTime();
            c.add(Calendar.MONTH, -1);
            previousDate = c.getTime();
        } else if ("year".equals(timeType)) {
            c.set(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startDate = c.getTime();
            c.add(Calendar.YEAR, -1);
            previousDate = c.getTime();
        }
        fireAlarmEvent.setStartTime(startDate);
        fireAlarmEvent.setEndTime(currentDate);
        FireEventReport currentReport = fireAlarmEventDao.findPieEventNum(fireAlarmEvent);
        fireAlarmEvent.setStartTime(previousDate);
        fireAlarmEvent.setEndTime(startDate);
        FireEventReport previousReport = fireAlarmEventDao.findPieEventNum(fireAlarmEvent);

        Map<String, Object> map = new HashMap<>(2);
        map.put("current", currentReport);
        map.put("previous", previousReport);
        return asseData(map);
    }

    /**
     * 火警主机App端图形数据和事件列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-03-05 10:31
     */
    @Override
    public Data findFireEventPieAndListApp(String json) throws ParseException {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        String timeType = fireAlarmEvent.getTimeType();
        String timeStr = fireAlarmEvent.getTimeStr();
        String pattern = null;
        //1:hour, 2:day, 3:month, 4:year
        int dateType = 0;
        if ("day".equals(timeType)) {
            pattern = "yyyy-MM-dd";
            dateType = 1;
            fireAlarmEvent.setGroupType(1);
        } else if ("month".equals(timeType)) {
            pattern = "yyyy-MM";
            dateType = 2;
            fireAlarmEvent.setGroupType(2);
        } else if ("year".equals(timeType)) {
            pattern = "yyyy";
            dateType = 3;
            fireAlarmEvent.setGroupType(3);
        }
        Date endDate;
        Date startDate;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        startDate = sdf.parse(timeStr);
        fireAlarmEvent.setStartTime(startDate);

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (dateType == 1) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        } else if (dateType == 2) {
            c.add(Calendar.MONTH, 1);
        } else if (dateType == 3) {
            c.add(Calendar.YEAR, 1);
        }
        c.add(Calendar.SECOND, -1);
        endDate = c.getTime();

        Map<String, Object> map = getFireEventInfo(dateType, endDate, startDate, fireAlarmEvent);
        return asseData(map);
    }

    /**
     * 火警主机事件列表和报表数据封装
     *
     * @return Map<String, Object>
     * @author dwt
     * @date 2020-03-05 10:32
     */
    private Map<String, Object> getFireEventInfo(int dateType, Date endDate, Date startDate, FireAlarmEvent fireAlarmEvent) {
        fireAlarmEvent.setEndTime(endDate);
        String[] orderCols = fireAlarmEvent.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"fae.createTime"};
            fireAlarmEvent.setOrderDesc(true);
            fireAlarmEvent.setOrderCols(orderCols);
        }

        Map<String, Object> map = new HashMap<>(4);
        findFireAlarmAndTotalCount(fireAlarmEvent, map);
        if (fireAlarmEvent.getFlag()) {
            findFireAlarmEventPieList(dateType, startDate, endDate, fireAlarmEvent, map);
            Integer type = fireAlarmEvent.getType();
            if (type == null || type == 1 || type == 0) {
                FireEventReport fireEventReport = fireAlarmEventDao.findPieEventNum(fireAlarmEvent);
                map.put("fireEventReport", fireEventReport);
            } else if (type == 2) {
                // 统计地铁事件原因前五排行榜
                List<FireEventReport> causeList = fireAlarmEventDao.findSubwayEventOfCauseCount(fireAlarmEvent);
                map.put("eventCauseList", causeList);
            }
        }
        return map;
    }

    /**
     * 地铁事件处理接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-12 13:54
     */
    @Override
    public Data updateEventCauseById(String json) {
        FireAlarmEvent event = JSONObject.parseObject(json, FireAlarmEvent.class);
        Integer a = fireAlarmEventDao.updateEventCauseById(event);
        if (a != null && a > 0 && event.getId() != null && event.getId() != 0 && event.getPictureUrlArr() != null && event.getPictureUrlArr().size() > 0) {
            pictureUrlDao.deletePictureUrlByEventId(event.getId());
            a = pictureUrlDao.saveEventHandlePictures(event);
        }

        if (a != null && a <= 0) {
            return Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     * 地铁事件处理图片路径查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-12 14:22
     */
    @Override
    public Data findPictureUrlByEventId(String json) {
        FireAlarmEvent event = JSONObject.parseObject(json, FireAlarmEvent.class);
        Long id = event.getId();
        String[] urls = pictureUrlDao.findPictureUrlByEventId(id);
        return asseData(urls);
    }

    /**
     * 火警主机事件列表查询以及筛选
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-22 18:16
     */
    @Override
    public Data findFireAlarmEventList(String json) {
        FireAlarmEvent fireAlarmEvent = parseFireAlarmEvent(json);
        Map<String, Object> map = new HashMap<>(1);
        findFireAlarmAndTotalCount(fireAlarmEvent, map);
        return asseData(map);
    }

    /**
     * 地铁大屏火警主机事件统计
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-13 10:45
     */
    @Override
    public Data findSubwayEventCount(String json) {
        FireAlarmEvent fireAlarmEvent = parseFireAlarmEvent(json);
        List<SubwayFireEvent> eventList = fireAlarmEventDao.findSubwayEventCount(fireAlarmEvent);
        return asseData(eventList);
    }

    private FireAlarmEvent parseFireAlarmEvent(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        Date endTime = fireAlarmEvent.getEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            fireAlarmEvent.setEndTime(c.getTime());
        }
        return fireAlarmEvent;
    }

    /**
     * 地铁大屏火警主机事件列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-05-22 16:07
     */
    @Override
    public Data findFireEventSubwayBigScreen(String json) {
        FireAlarmEvent event = JSONObject.parseObject(json, FireAlarmEvent.class);
        String appId = event.getAppId();
        if (StringUtils.isNotEmpty(appId)) {
            String project = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, appId);
            if (project != null) {
                Long[] projectIds = ConvertUtils.stringToLong(project.split(","));
                event.setProjectIds(projectIds);
                String[] orderCols = event.getOrderCols();
                if (orderCols == null || orderCols.length <= 0) {
                    orderCols = new String[]{"fae.createTime"};
                    event.setOrderDesc(true);
                    event.setOrderCols(orderCols);
                }
                List<FireAlarmEvent> list = fireAlarmEventDao.findFireAlarmEventList(event);
                Long totalCount = fireAlarmEventDao.findFireAlarmEventCount(event);

                Map<String, Object> map = new HashMap<>(2);
                map.put("listObj", list);
                map.put("totalCount", totalCount);
                return asseData(map);
            }
        }
        return Data.isFail();
    }

    @Override
    public Data findBrokenLineAndEventCount(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        Date startDate = fireAlarmEvent.getStartTime();
        Date endDate = fireAlarmEvent.getEndTime();
        fireAlarmEvent.setFlag(true);
        Integer dateType = DateUtils.getBetweenDateTimeType(startDate, endDate);
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        endDate = c.getTime();
        fireAlarmEvent.setType(1);
        fireAlarmEvent.setEndTime(endDate);

        Map<String, Object> map = new HashMap<>(1);
        findFireAlarmEventPieList(dateType, startDate, endDate, fireAlarmEvent, map);
        Integer type = fireAlarmEvent.getType();
        if (type == null || type == 1 || type == 0) {
            FireEventReport fireEventReport = fireAlarmEventDao.findPieEventNum(fireAlarmEvent);
            map.put("fireEventReport", fireEventReport);
        }
        return asseData(map);
    }

    /**
     * 火警主机改版事件列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-30 10:44
     */
    @Override
    public Data findFireAlarmListApp(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);

        // 事件状态 1-已恢复，2-未恢复
        Integer recoverStatus = fireAlarmEvent.getRecoverStatus();
        String alarmPosition = fireAlarmEvent.getAlarmPosition();
        Date startDate = fireAlarmEvent.getStartTime();
        Date endDate = fireAlarmEvent.getEndTime();
        Calendar c = Calendar.getInstance();
        if (startDate == null || endDate == null) {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            endDate = c.getTime();
            c.add(Calendar.DAY_OF_MONTH, -7);
            c.add(Calendar.SECOND, 1);
            startDate = c.getTime();
        } else {
            c.setTime(endDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.MILLISECOND, -1);
            endDate = c.getTime();
        }
        fireAlarmEvent.setStartTime(startDate);
        fireAlarmEvent.setEndTime(endDate);

        Map<String, Object> map = new HashMap<>(3);
        if (recoverStatus == null || StringUtils.isNotEmpty(alarmPosition)) {
            fireAlarmEvent.setFlag(true);
            Integer dateType = DateUtils.getBetweenDateTimeType(startDate, endDate);
            findFireAlarmEventPieList(dateType, startDate, endDate, fireAlarmEvent, map);
        }
        findFireAlarmAndTotalCount(fireAlarmEvent, map);
        return asseData(map);
    }

    /**
     * 改版火警主机处理接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-07 15:18
     */
    @Override
    public Data updateFireEventHandleStatus(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        Integer a = fireAlarmEventDao.updateFireEventHandleStatus(fireAlarmEvent);
        if (a > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 根据id查询事件信息
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-07 16:14
     */
    @Override
    public Data findFireAlarmEventById(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        fireAlarmEvent = fireAlarmEventDao.findFireAlarmEventById(fireAlarmEvent);
        return asseData(fireAlarmEvent);
    }

    /**
     * 事件列表查询公用方法
     */
    private void findFireAlarmAndTotalCount(FireAlarmEvent fireAlarmEvent, Map<String, Object> map) {
        setFireAlarmEventOrder(fireAlarmEvent);
        List<FireAlarmEvent> fireAlarmEventList = fireAlarmEventDao.findFireAlarmEventList(fireAlarmEvent);
        map.put("fireAlarmEventList", fireAlarmEventList);
        Long totalCount = fireAlarmEventDao.findFireAlarmEventCount(fireAlarmEvent);
        map.put("totalCount", totalCount);
    }

    /**
     * 柱状图列表查询公用方法
     */
    private void findFireAlarmEventPieList(Integer dateType, Date startDate, Date endDate, FireAlarmEvent fireAlarmEvent, Map<String, Object> map) {
        Integer dValue = DateUtils.getDateTimeCount(dateType, endDate, startDate);
        dValue = Math.abs(dValue);

        fireAlarmEvent.setDateType(dateType);
        fireAlarmEvent.setDateNum(dValue);
        List<FireEventReport> fireEventReportList = fireAlarmEventDao.findBrokenLineEventNum(fireAlarmEvent);
        map.put("fireEventReportList", fireEventReportList);
    }

    /**
     * 设置排序
     *
     * @param fireAlarmEvent FireAlarmEvent
     */
    private void setFireAlarmEventOrder(FireAlarmEvent fireAlarmEvent) {
        String[] orderCols = fireAlarmEvent.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"fae.createTime"};
            fireAlarmEvent.setOrderDesc(true);
            fireAlarmEvent.setOrderCols(orderCols);
        }
    }

    /**
     * 地铁班前防护故障事件查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 10:11
     */
    @Override
    public Data findMetroProtectionFaultEventList(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);

        // 查询用户是否在项目的作业中(未关闭的作业)
        if (fireAlarmEvent.getCreateUserId() != null) {
            int totalCount = fireAlarmEventDao.findProtectUserNum(fireAlarmEvent);
            if (totalCount <= 0) {
                fireAlarmEvent.setRemarks("fail");
                return asseData(fireAlarmEvent);
            }
        }

        // 查询作业是否完成
        Integer recoverStatus = fireAlarmEvent.getRecoverStatus();
        if (recoverStatus != null) {
            // 未结束的作业
            Integer[] types = {1, 3};
            fireAlarmEvent.setProtectStatusType(types);

            Integer[] taskState = {1, 2};
            fireAlarmEvent.setTaskStateType(taskState);
            int totalCount = fireAlarmEventDao.findProtectUserNum(fireAlarmEvent);
            if (totalCount <= 0) {
                // 用户没有作业
                fireAlarmEvent.setEventExistStatus(-3);
                return asseData(fireAlarmEvent);
            } else {
                fireAlarmEvent.setProtectStatusType(null);
                fireAlarmEvent.setTaskStateType(null);
                fireAlarmEvent.setProtectStatus(1);
                fireAlarmEvent.setTaskState(1);
                int protectUserNum = fireAlarmEventDao.findProtectUserNum(fireAlarmEvent);
                if (protectUserNum > 0) {
                    // 用户有未开始的作业
                    fireAlarmEvent.setEventExistStatus(-2);
                }
            }

            /*if (totalCount <= 0){
                // 没有未提交的作业
                fireAlarmEvent.setRecoverStatus(-1);
                return asseData(fireAlarmEvent);
            }*/

            fireAlarmEvent.setRecoverStatus(2);
            if (fireAlarmEvent.getIds() != null && fireAlarmEvent.getIds().length > 0) {
                // 班前防护 历史作业 已完成
                return asseData(fireAlarmEvent);
            }

            int recoverStatusNum = fireAlarmEventDao.findProtectIsComplete(fireAlarmEvent);
            if (recoverStatusNum > 0) {
                fireAlarmEvent.setRecoverStatus(1);
            }
            return asseData(fireAlarmEvent);
        }

        // 查询火警事件
        List<FireAlarmEvent> eventList = fireAlarmEventDao.findMetroProtectionFaultEventList(fireAlarmEvent);
        if (fireAlarmEvent.getProtectId() != null) {
            for (FireAlarmEvent f : eventList) {
                if (f.getRecoverStatus() != null && f.getRecoverStatus() != 2) {
                    JSONObject jsonObject = new JSONObject(2);
                    jsonObject.put("id", fireAlarmEvent.getProtectId());
                    jsonObject.put("taskState", 4);
                    HttpUtils.httpPostByJson(ConstantsIot.METRO_PROTECT_UPDATE_PATH, jsonObject.toString());
                    break;
                }
            }
        }
        return asseData(eventList);
    }

    /**
     * 地铁班前防护--综合监测
     *
     * @param json java.lang.String
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-03-30 INTEGRATED_MONITORING
     */
    @Override
    public Data heLiShiIntegratedMonitoring(String json) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);

        String data;
        String memo;
        String monitoringMemo;
        // 1．	根据设备编码请求设备的全部测点实时数据 DEVICE_CODE
        if (fireAlarmEvent.getRowNum() == 1){
            // "[\"ASD_11\",\"ASD_12\",\"ASD_13\",\"ASD_14\",\"ASD_15\",\"ASD_16\",\"ASD_17\",\"ASD_18\"]";
            Dict dict = DictUtils.getDictByDictAndItemCode(ConstantsIot.INTEGRATED_MONITORING, ConstantsIot.DEVICE_CODE);
            data = dict.getItemDescription();
            memo = dict.getMemo();

            // 手动触发/自动触发;检修/正常
            Dict dictMemo = DictUtils.getDictByDictAndItemCode(ConstantsIot.INTEGRATED_MONITORING, ConstantsIot.MONITORING_MEMO);
            monitoringMemo = dictMemo.getItemDescription();
        }else {
            // 2．	根据测点编码请求具体测点的实时数据  MEASURING_POINT_CODE
            // "[\"2703:TRIG0001.AITR\",\"2703:TRIG0004.AITR\"]"
            Dict dict = DictUtils.getDictByDictAndItemCode(ConstantsIot.INTEGRATED_MONITORING, ConstantsIot.MEASURING_POINT_CODE);
            data = dict.getItemDescription();
            // FAS触发火灾报警手自动,FAS-BAS接口检修/正常状态
            memo = dict.getMemo();

            // 手动触发/自动触发;检修/正常
            Dict dictMemo = DictUtils.getDictByDictAndItemCode(ConstantsIot.INTEGRATED_MONITORING, ConstantsIot.MONITORING_MEMO);
            monitoringMemo = dictMemo.getItemDescription();
        }

        if (StringUtils.isNotEmpty(data) && StringUtils.isNotEmpty(memo)){
            data = data.replace("\\", "");
            String res = MetroUtils.heLiShiIntegratedMonitoring(data);
            // res={"retcode":1,"retmsg":"操作成功","data":{"2703:TRIG0004.AITR":"1.0","2703:TRIG0001.AITR":"1.0"}}
            SysLog.info("地铁班前防护--综合监测------>res=" + res);
            JSONObject jsonObject = JSONObject.parseObject(res);

            /*retcode	处理状态码	1：请求成功，0：请求失败
            retmsg	结果说明
            data	数据*/
            String dataValue = "";
            if (jsonObject != null && jsonObject.getString("data") != null){
                dataValue = jsonObject.getString("data");
            }
            String[] dataArr = null;
            if (StringUtils.isNotEmpty(dataValue)){
                // {"2703:TRIG0004.AITR":"0.0","2703:TRIG0001.AITR":"0.0"}
                dataValue = dataValue.replace(" ", "");
                dataValue = dataValue.replace("\"", "");
                dataValue = dataValue.replace("{", "");
                dataValue = dataValue.replace("}", "");
                SysLog.info("地铁班前防护--综合监测------>dataValue=" + dataValue);
                dataArr = dataValue.split(",");
            }

            data = data.replace(" ", "");
            data = data.replace("\"", "");
            data = data.replace("[", "");
            data = data.replace("]", "");
            String[] split = data.split(",");

            memo = memo.replace("\"", "");
            String[] memoStr = memo.split(",");

            String[] monitoringMemoArr = monitoringMemo.split(";");

            boolean b = dataArr != null;
            List<IntegratedMonitoring> integratedMonitoringList = new ArrayList<>(split.length);
            for (int i = 0; i < split.length; i++) {
                IntegratedMonitoring entity = new IntegratedMonitoring();
                entity.setMonitoringName(memoStr[i]);

                entity.setMonitoringMemo(monitoringMemoArr[i]);

                String x = split[i];
                entity.setMonitoringCode(x);
                if (b) {
                    for (int j = 0; j < dataArr.length; j++) {
                        String s2 = dataArr[j];
                        if (s2.contains(x)) {
                            String[] s = s2.split(":");
                            entity.setMonitoringValue(s.length == 3 ? s[2] : "");
                            break;
                        }
                    }
                }

                integratedMonitoringList.add(entity);
            }
            return asseData(integratedMonitoringList);
        }
        return Data.isFail();
    }
}
