package com.xjt.cloud.iot.core.service.impl.eye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.iot.eye.FireEyeDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.eye.FireEyeEventDao;
import com.xjt.cloud.iot.core.entity.eye.FireEyeDevice;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEvent;
import com.xjt.cloud.iot.core.entity.eye.FireEyeEventReport;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 火眼事件实现类
 *
 * @author zhangZaifa
 * @date 2020/4/3 15:35
 */
@Service
public class FireEyeEventServiceImpl extends AbstractService implements FireEyeEventService {
    @Autowired
    private FireEyeEventDao fireEyeEventDao;
    @Autowired
    private FireEyeDeviceDao fireEyeDeviceDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LinkageControlService linkageControlService;

    /**
     * 保存火眼事件
     *
     * @param json 参数
     * @return JSONObject
     * @author zhangZaiFa
     * @date 2020/4/3 17:51
     **/
    @Override
    public List<JSONObject> fireEyeEventSave(JSONObject json) {
        try {
            FireEyeEvent fireEyeEvent = JSONObject.parseObject(json.toJSONString(), FireEyeEvent.class);
            fireEyeEvent.setSensorNo(json.getString("from"));
            FireEyeDevice fed = new FireEyeDevice();
            fed.setSensorNo(fireEyeEvent.getSensorNo());
            fed = fireEyeDeviceDao.findFireEyeDevice(fed);

            fireEyeEvent.setDeviceId(fed.getDeviceId());
            fireEyeEvent.setCheckPointId(fed.getCheckPointId());
            fireEyeEvent.setFireEyeDeviceId(fed.getId());

            String aisleNumber = json.getString("aisleNumber");
            if (StringUtils.isNotEmpty(aisleNumber)) {
                fireEyeEvent.setChannelNo(Integer.parseInt(aisleNumber));
            }
            fireEyeEventDao.save(fireEyeEvent);
        } catch (Exception e) {
            SysLog.error(e);
        }

        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("msg", 200);
        jsonObject.put("iotType", ConstantsIot.FIRE_EYE);
        WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        jsonObject.remove("msg");

        List<JSONObject> jsonList = new ArrayList<>(3);
        jsonList.add(jsonObject);
        jsonList.add(jsonObject);
        jsonList.add(jsonObject);
        return jsonList;
    }

    /**
     * 查询火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:24
     **/
    @Override
    public Data findFireEyeEventList(String json) {
        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);

        initFireEyeEventDate(fireEyeEvent);

        Integer totalCount = fireEyeEvent.getTotalCount();
        Integer pageSize = fireEyeEvent.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = fireEyeEventDao.findFireEyeEventListCount(fireEyeEvent);
        }

        if (null == fireEyeEvent.getOrderCols()){
            String[] orderCols = {"createTime"};
            fireEyeEvent.setOrderCols(orderCols);
            // 降序
            fireEyeEvent.setOrderDesc(true);
        }
        List<FireEyeEvent> list = fireEyeEventDao.findFireEyeEventList(fireEyeEvent);
        return asseData(totalCount, list);
    }

    /**
     * 保存 火眼事件列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/20 15:02
     **/
    @Override
    public Data saveFireEyeEvent(String json) {
        SysLog.info("火眼事件信息:" + json);
        /*json.put("sensorNo",ConstantsClient.MODBUS_BEGIN_REG_ID); 传感器ID
        json.put("channelNo",address); 通道号 0 1 2 3 4 5
        json.put("status",value); 事件类型*/

        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);

        Integer eventType = fireEyeEvent.getStatus();
        // 0:正常(恢复所有事件,不新增事件)
        String sensorNo = fireEyeEvent.getSensorNo();
        FireEyeDevice fireEyeDevice = new FireEyeDevice();
        fireEyeDevice.setSensorNo(sensorNo);
        fireEyeDevice.setChannelNo(fireEyeEvent.getChannelNo());
        fireEyeDevice.setHeartbeatTime(new Date());
        fireEyeDevice.setDeviceState(1);

        if (eventType == 0){
            fireEyeEvent.setRecoverStatus(1);
            fireEyeEvent.setByRecoverStatus(0);
            int updateNum = fireEyeEventDao.updateFireEyeEvent(fireEyeEvent);
            //修改设备信息
            fireEyeDevice.setEventType(0);
            fireEyeDeviceDao.updateFireEyeDevice(fireEyeDevice);
            if (updateNum > 0){
                List<String> roleList = new ArrayList<>(1);
                // 火眼管理权限
                roleList.add("fireEye_manage_edit");

                FireEyeDevice eyeDevice = new FireEyeDevice();
                eyeDevice.setSensorNo(fireEyeEvent.getSensorNo());
                fireEyeDevice = fireEyeDeviceDao.findFireEyeDevice(eyeDevice);
                Long projectId = fireEyeDevice.getProjectId();
                /*
                 * 【消检通】 项目名称/摄像机名称/位置+传输ID：发生火焰事件，请及时处理。
                 */
                StringBuilder str = new StringBuilder();
                str.append("【");
                // 从缓存中取出项目对象
                String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
                str.append(projectName).append("】 ");
                str.append(fireEyeDevice.getVideoName());
                str.append(fireEyeDevice.getPointLocation());
                //str.append(fireEyeEvent.getSensorNo());
                str.append(",事件已恢复。");
                JSONObject object = getJsonObject(fireEyeEvent, fireEyeDevice, null, projectName);

                SysLog.info("saveFireEyeEvent messageService ---->");
                messageService.saveMessageRole(-2, roleList, "恢复通知", eventType,0, str.toString(),
                        "url", projectId, null, null, object);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("iotType", ConstantsIot.FIRE_EYE);
                jsonObject.put("msg", 200);
                WebSocketSendMsgUtils.nettySendMsg(jsonObject);
                return Data.isSuccess();
            }
        } else if (eventType == 98) {
            // 心跳
            int updFireEyeDevice = fireEyeDeviceDao.updateFireEyeDevice(fireEyeDevice);

            /*FireEyeEvent eyeEvent = new FireEyeEvent();
            eyeEvent.setRecoverStatus(1);
            eyeEvent.setSensorNo(sensorNo);
            fireEyeEventDao.updateFireEyeEvent(eyeEvent);*/
            if (updFireEyeDevice > 0){
                return Data.isSuccess();
            }
        } else {
            if (eventType != 1 && eventType != 2 && eventType != 4 && eventType != 8 && eventType != 16 && eventType != 32){//计算多个事件组合
                int[] eventTypes = {1,2,4,8,16,32};//1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障,
                int index = 0;
                int maxEvent = 0;
                for (int i = 0; i < eventTypes.length;i++){
                    if (eventType < eventTypes[i]){
                        index = i;
                        maxEvent = eventTypes[i - 1];
                        break;
                    }else if(i == eventTypes.length - 1){
                        index = i;
                        maxEvent = eventTypes[i];
                    }
                }
                List<Integer> eventList = new ArrayList<>(eventTypes.length);
                eventList.add(maxEvent);
                for (int i = index - 1; i >= 0 ;i--){//计算包含了哪几个事件
                    if (maxEvent + eventTypes[i] <= eventType){
                        eventList.add(eventTypes[i]);
                        maxEvent += eventTypes[i];
                    }
                }

                if (CollectionUtils.isNotEmpty(eventList)){
                    for (Integer event:eventList){
                        if (event != null && event != 0){
                            saveFireEyeEvent(fireEyeEvent, event);
                        }
                    }
                    return Data.isSuccess();
                }
            }else {
                return saveFireEyeEvent(fireEyeEvent, eventType);
            }
        }
        return Data.isFail();
    }



    /**
     * 新增火眼事件
     *
     * @param fireEyeEvent FireEyeEvent
     * @param eventType 事件类型
     * @return Data
     */
    private Data saveFireEyeEvent(FireEyeEvent fireEyeEvent, Integer eventType) {
        // 新增火眼事件
        FireEyeDevice eyeDevice = new FireEyeDevice();
        eyeDevice.setSensorNo(fireEyeEvent.getSensorNo());
        eyeDevice.setChannelNo(fireEyeEvent.getChannelNo());
        FireEyeDevice fireEyeDevice = fireEyeDeviceDao.findFireEyeDevice(eyeDevice);
        Long deviceId = fireEyeDevice.getDeviceId();
        Long projectId = fireEyeDevice.getProjectId();

        fireEyeEvent.setProjectId(projectId);
        fireEyeEvent.setDeviceId(deviceId);
        fireEyeEvent.setVideoName(fireEyeDevice.getVideoName());
        fireEyeEvent.setCheckPointId(fireEyeDevice.getCheckPointId());
        fireEyeEvent.setFireEyeDeviceId(fireEyeDevice.getId());

        fireEyeEvent.setRecoverStatus(0);
        fireEyeEvent.setEventType(eventType);
        int saveNum = 0;
        if (fireEyeEventDao.findFireEyeLastNotRecoverEvent(fireEyeEvent) == 0) {
            saveNum = fireEyeEventDao.save(fireEyeEvent);
        }

        fireEyeDevice.setEventType(eventType);
        fireEyeDeviceDao.updFireEyeDevice(fireEyeDevice);
        if (saveNum > 0){
            JSONObject jsonObject = new JSONObject(2);
            jsonObject.put("msg", 200);
            jsonObject.put("iotType", ConstantsIot.FIRE_EYE);
            WebSocketSendMsgUtils.nettySendMsg(jsonObject);
            SysLog.info("saveFireEyeEvent WebSocketSendMsgUtils SUCCESS jsonObject=---->" + jsonObject);

            List<String> roleList = new ArrayList<>(1);
            // 火眼管理权限
            roleList.add("fireEye_manage_edit");

            /*
             * 【消检通】 项目名称/摄像机名称/位置+传输ID：发生火焰事件，请及时处理。
             */
            StringBuilder str = new StringBuilder();
            str.append("【");
            // 从缓存中取出项目对象
            String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
            str.append(projectName).append("】 ");
            str.append(fireEyeDevice.getVideoName());
            str.append(fireEyeDevice.getPointLocation());
            //str.append(fireEyeEvent.getSensorNo());
            str.append(":发生");
            String eventTypeDesc = fireEyeEvent.getEventTypeDesc();
            str.append(eventTypeDesc);
            str.append("事件");

            JSONObject object = getJsonObject(fireEyeEvent, fireEyeDevice, eventTypeDesc, projectName);
            SysLog.info("saveFireEyeEvent messageService ---->");
            messageService.saveMessageRole(-2, roleList, eventTypeDesc + "通知", eventType,0, str.toString(),
                    "url", projectId, null, null, object);

            // 声光报警
            linkageControlService.deviceFault(deviceId, fireEyeEvent.getSignDesc());

            return Data.isSuccess();
        }

        return Data.isFail();
    }

    /**
     * 组装短信内容
     *
     * @param fireEyeEvent FireEyeEvent
     * @param fireEyeDevice FireEyeDevice
     * @param eventTypeDesc 事件类型
     * @param projectName 项目名称
     * @return JSONObject
     */
    private JSONObject getJsonObject(FireEyeEvent fireEyeEvent, FireEyeDevice fireEyeDevice, String eventTypeDesc, String projectName) {
        JSONObject object = new JSONObject(12);
        object.put("projectName", projectName);
        object.put("buildingName", fireEyeDevice.getBuildingName());
        object.put("floorName", fireEyeDevice.getFloorName());
        object.put("deviceLocation", fireEyeDevice.getPointLocation());
        object.put("deviceName", fireEyeDevice.getDeviceName());
        object.put("videoName", fireEyeDevice.getVideoName());
        object.put("qrNo", fireEyeDevice.getDeviceQrNo());
        object.put("pointQrNo", fireEyeDevice.getCheckPointQrNo());
        object.put("event", eventTypeDesc);
        object.put("buildingId", fireEyeDevice.getBuildingId());
        object.put("sensorNo", fireEyeDevice.getSensorNo());
        object.put("date", DateUtils.getDateTimeString(fireEyeEvent.getCreateTime() == null ? new Date() : fireEyeEvent.getCreateTime()));
        return object;
    }

    /**
     * 查询 火眼事件汇总
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/21
     **/
    @Override
    public Data findFireEyeEventSummary(String json) {
        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);

        initFireEyeEventDate(fireEyeEvent);

        FireEyeEventReport fireEyeEventReport = fireEyeEventDao.findFireEyeEventSummary(fireEyeEvent);
        return asseData(fireEyeEventReport);
    }

    /**
     * 查询 火眼事件 折线图
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 202103-23
     **/
    @Override
    public Data findFireEyeEventReportCount(String json) {
        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);
        if (fireEyeEvent.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            fireEyeEvent.setCreateTime(date);
            fireEyeEvent.setEndTime(date);
        }
        fireEyeEvent.setGroupType(DateUtils.getBetweenDateTimeType(fireEyeEvent.getCreateTime(), fireEyeEvent.getEndTime()));

        List<FireEyeEventReport> fireEyeEventReportList = fireEyeEventDao.findFireEyeEventReportCount(fireEyeEvent);
        return asseData(fireEyeEventReportList);
    }

    /**
     * 火眼事件列表导出表格
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2021-03-25
     **/
    @Override
    public void downFireEyeEventList(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);

        if (null == fireEyeEvent.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            fireEyeEvent.setOrderCols(orderCols);
            fireEyeEvent.setOrderDesc(true);
        }
        List<FireEyeEvent> list = fireEyeEventDao.findFireEyeEventList(fireEyeEvent);

        if (CollectionUtils.isEmpty(list)){
            return ;
        }

        Long projectId = fireEyeEvent.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--类脑火眼告警事件导出表");

        String[] keys = {"rowNum", "eventTypeDesc", "videoName", "channelNo", "checkPointQrNo", "createTimeDesc", "recoverTimeDesc", "recoverStatusDesc",
                    "videoLocationDesc", "handleStatusDesc", "eventHandleTimeDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsIot.DOWN_FIRE_EYE_EVENT_LIST_PATH, 4, null,
                jsonObject, "1:0");
    }

    /**
     * 查询 火眼事件 大屏
     *
     * @param json 参数
     * @author huanggc
     * @date 2021/04/28
     * @return Data
     **/
    @Override
    public Data findFireEyeEventScreen(String json) {
        FireEyeEvent fireEyeEvent = JSONObject.parseObject(json, FireEyeEvent.class);
        if (fireEyeEvent.getCreateTime() == null) {
            Date date = DateUtils.getDate();
            fireEyeEvent.setCreateTime(date);
            fireEyeEvent.setEndTime(date);
        }
        fireEyeEvent.setGroupType(DateUtils.getBetweenDateTimeType(fireEyeEvent.getCreateTime(), fireEyeEvent.getEndTime()));

        List<FireEyeEventReport> fireEyeEventReportList = fireEyeEventDao.findFireEyeEventReportCount(fireEyeEvent);

        FireEyeEventReport fireEyeEventReport = fireEyeEventDao.findFireEyeEventSummary(fireEyeEvent);

        FireEyeDevice fireEyeDevice = new FireEyeDevice();
        fireEyeDevice.setProjectIds(fireEyeEvent.getProjectIds());
        FireEyeEventReport fireEyeDeviceMonitoringStatus = fireEyeDeviceDao.findFireEyeDeviceMonitoringStatus(fireEyeDevice);

        Map<String, Object> map = new HashMap<>(3);
        map.put("fireEyeEventReportList", fireEyeEventReportList);
        map.put("fireEyeEventReport", fireEyeEventReport);
        map.put("fireEyeDeviceReport", fireEyeDeviceMonitoringStatus);
        return asseData(map);
    }

    /**
     * 时间判断
     *
     * @param fireEyeEvent FireEyeEvent
     */
    private void initFireEyeEventDate(FireEyeEvent fireEyeEvent) {
        Integer dateType = fireEyeEvent.getDateType();
        if (dateType != null){
            if (dateType == 2){
                // 本周
                fireEyeEvent.setStartTime(DateUtils.getTimesWeekmorning());
                fireEyeEvent.setEndTime(DateUtils.getTimesWeeknight());
            }else if (dateType == 3){
                // 本月
                Date date = new Date();
                fireEyeEvent.setStartTime(DateUtils.monthStarDate(date));
                fireEyeEvent.setEndTime(DateUtils.monthEndDate(date));
            }else {
                // 1今日
                // 没有时间时查当天的事件
                Date date = DateUtils.getDate();
                fireEyeEvent.setStartTime(DateUtils.startDayDate(date));
                fireEyeEvent.setEndTime(DateUtils.endDayDate(date));
            }
        }
    }

}
