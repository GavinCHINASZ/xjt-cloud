package com.xjt.cloud.iot.core.service.impl.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.fire.FireEventReport;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmDeviceService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceRelationService;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendFireMessageService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 火警主机Service实现类
 *
 * @author dwt
 * @date 2019-10-11 15:33
 */
@Service
public class FireAlarmDeviceServiceImpl extends AbstractService implements FireAlarmDeviceService {

    @Autowired
    private FireAlarmDeviceDao fireAlarmDeviceDao;
    @Autowired
    private FireAlarmEventDao fireAlarmEventDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private SendFireMessageService sendFireMessageService;
    @Autowired
    private LinkageDeviceRelationService linkageDeviceRelationService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询火警主机设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-21 13:51
     */
    @Override
    public Data findFireAlarmDeviceList(String json) {
        FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
        if (fireAlarmDevice.getOrderCols() == null || fireAlarmDevice.getOrderCols().length <= 0) {
            String[] orderCols = {"fad.create_time"};
            fireAlarmDevice.setOrderDesc(false);
            fireAlarmDevice.setOrderCols(orderCols);
        }
        Map<String, Object> map = new HashMap<>(2);
        List<FireAlarmDevice> fireAlarmDeviceList = fireAlarmDeviceDao.findFireAlarmDeviceList(fireAlarmDevice);
        map.put("listObj", fireAlarmDeviceList);
        Integer totalCount = fireAlarmDeviceDao.findFireAlarmDeviceCount(fireAlarmDevice);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * 查询设备事件统计信息
     *
     * @param json 参数
     * @param type type
     * @return Data
     * @author dwt
     * @date 2020-05-22 10:10
     */
    @Override
    public Data findFireAlarmDeviceEventData(String json, int type) {
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        fireAlarmEvent.setRecoverStatus(2);
        if (type == 1) {
            Calendar c = Calendar.getInstance();
            fireAlarmEvent.setEndTime(c.getTime());

            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            fireAlarmEvent.setStartTime(c.getTime());
        }

        Date endTime = fireAlarmEvent.getEndTime();
        if (endTime != null) {
            fireAlarmEvent.setEndTime(DateUtils.getDayEndTime(endTime));
        }

        FireEventReport fireEventReport = fireAlarmEventDao.findFireAlarmEventMonitor(fireAlarmEvent);
        Integer deviceSum = fireAlarmDeviceDao.findFireAlarmIsLineCount(null, fireAlarmEvent.getProjectId());
        Integer deviceOnLineCount = fireAlarmDeviceDao.findFireAlarmIsLineCount(1, fireAlarmEvent.getProjectId());
        Map<String, Object> map = new HashMap<>(5);
        map.put("deviceSum", deviceSum);
        map.put("deviceOnLineCount", deviceOnLineCount);
        if (fireEventReport != null) {
            map.put("fireSum", fireEventReport.getFireEvent());
            map.put("faultSum", fireEventReport.getFaultEvent());
            map.put("monitorSum", fireEventReport.getMonitorEvent());
        } else {
            map.put("fireSum", 0);
            map.put("faultSum", 0);
            map.put("monitorSum", 0);
        }
        return asseData(map);
    }

    /**
     * @Description 查询app首页火警主机信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 15:04
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectFireAlarmData(String json){
        FireAlarmEvent fireAlarmEvent = JSONObject.parseObject(json, FireAlarmEvent.class);
        fireAlarmEvent.setRecoverStatus(2);
        Date date = DateUtils.getDate();
        fireAlarmEvent.setCreateTime(date);
        fireAlarmEvent.setEndTime(date);

        FireEventReport fireEventReport = fireAlarmEventDao.findFireAlarmEventMonitor(fireAlarmEvent);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex", 4);
        if (fireEventReport != null) {
            jsonObject.put("fireSum", fireEventReport.getFireEvent());
            jsonObject.put("faultSum", fireEventReport.getFaultEvent());
            jsonObject.put("monitorSum", fireEventReport.getMonitorEvent());
        }else{
            FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
            Integer total = fireAlarmDeviceDao.findFireAlarmDeviceCount(fireAlarmDevice);
            if(total == null || total == 0){
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("fireSum", 0);
            jsonObject.put("faultSum", 0);
            jsonObject.put("flameCount", 0);
            jsonObject.put("monitorSum", 0);
        }
        return jsonObject;
    }

    /**
     * 保存火警主机设备
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-11-20 15:42
     */
    @Override
    public Data saveFireAlarmDevice(String json) {
        FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        Integer fireAlarmType = fireAlarmDevice.getFireAlarmType();
        if (fireAlarmType != null && fireAlarmType != 7 && StringUtils.isNotEmpty(fireAlarmDevice.getTransDeviceId())) {
            fireAlarmDevice.setTransDeviceId(fireAlarmDevice.getTransDeviceId().toUpperCase());
        }

        if (fireAlarmDevice.getId() == null || fireAlarmDevice.getId() == 0) {
            String transDeviceId = fireAlarmDevice.getTransDeviceId();
            fireAlarmDevice.setCodeType(3);
            if (transDeviceId.startsWith("$$_XPLS_")) {
                fireAlarmDevice.setCodeType(2);
            } else if (transDeviceId.startsWith("$$_NFS") || transDeviceId.startsWith("$$_ADH") || transDeviceId.startsWith("$$_FA") || transDeviceId.startsWith("$$_XPLSG_")) {
                fireAlarmDevice.setCodeType(0);
            } else if (transDeviceId.startsWith("$$_SIEMENS_")) {
                fireAlarmDevice.setCodeType(1);
            } else if (transDeviceId.startsWith("$$_AnShe_")) {
                fireAlarmDevice.setCodeType(4);
            }
            fireAlarmDevice.setCreateUserId(loginUserId);
            fireAlarmDeviceDao.saveFireAlarmDevice(fireAlarmDevice);
            if (fireAlarmDevice.getTransDeviceType() != 3) {
                redisUtils.set("FireAlarm" + fireAlarmDevice.getTransDeviceId(), JSONObject.toJSONString(fireAlarmDevice));
            } else {
                redisUtils.set("FireAlarm" + fireAlarmDevice.getTransDeviceId() + "_" + fireAlarmDevice.getFireAlarmNo(), JSONObject.toJSONString(fireAlarmDevice));
            }
            try {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_TASK", SecurityUserHolder.getUserName(), fireAlarmDevice, null, fireAlarmDevice.getId(), 2);
            } catch (Exception e) {
                SysLog.error(e);
            }
        } else {
            fireAlarmDevice.setUpdateUserId(loginUserId);
            FireAlarmDevice oldDevice = fireAlarmDeviceDao.findFireDeviceById(fireAlarmDevice.getId());
            List<FireAlarmDevice> list = new ArrayList<>();
            list.add(oldDevice);
            deviceDao.modifyFireAlarmDeviceClearIot(list);
            fireAlarmDeviceDao.modifyFireAlarmDevice(fireAlarmDevice);
            try {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), fireAlarmDevice, oldDevice, fireAlarmDevice.getId(), 2);
            } catch (Exception e) {
                SysLog.error(e);
            }
        }
        //修改设备关联物联网
        Device device = new Device();
        device.setId(fireAlarmDevice.getDeviceId());
        device.setIotId(fireAlarmDevice.getId());
        device.setSensorNo(fireAlarmDevice.getTransDeviceId());
        device.setIotStatus(2);
        deviceDao.modifyDevice(device);
        return Data.isSuccess();
    }

    /**
     * 定时任务更新火警主机设备在线离线状态，生成离线事件以及离线恢复事件
     *
     * @return Data
     * @author dwt
     * @date 2019-11-20 15:56
     */
    @Override
    public synchronized Data modifyDeviceStatus() {
        List<FireAlarmDevice> list = fireAlarmDeviceDao.findAllFireAlarmDevice();
        if (list != null && list.size() > 0) {
            long diff;
            Integer status;
            if (Integer.parseInt(ConstantsIot.FIRE_ALARM_SEND_OFF_LINE_EVENT) == 1) {
                //FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
                Integer sendMessage;
                FireAlarmEvent fireAlarmOffEvent;
                Device device = new Device();
                for (FireAlarmDevice entity : list) {
                    status = entity.getDeviceStatus();
                    //0:未发送消息，1：已发送消息，2：不需要发送消息
                    sendMessage = entity.getSendMessage();
                    if (entity.getEndHeartbeatTime() != null) {
                        diff = (System.currentTimeMillis() - entity.getEndHeartbeatTime().getTime()) / (1000 * 60);
                        if (diff < 15) {
                            entity.setDeviceStatus(1);
                        } else {
                            //设备状态 1：在线，2：离线
                            entity.setDeviceStatus(2);
                            //事件状态 1-已恢复，2-未恢复
                            if (sendMessage != null && sendMessage == 0) {
                                fireAlarmOffEvent = new FireAlarmEvent(entity);
                                fireAlarmOffEvent.setRecoverStatus(2);
                                fireAlarmEventDao.saveFireAlarmEvent(fireAlarmOffEvent);
                                device.setId(entity.getDeviceId());
                                device.setIotStatus(2);
                                deviceDao.modifyDeviceIotStatus(device);
                                fireAlarmOffEvent.setBuildingId(entity.getBuildingId());
                                fireAlarmOffEvent.setBuildingName(entity.getBuildingName());
                                fireAlarmOffEvent.setFloorName(entity.getFloorName());
                                fireAlarmOffEvent.setQrNo(entity.getCheckPointQrNo());
                                sendFireMessageService.sendFireMessage(fireAlarmOffEvent, 7);
                                entity.setSendMessage(1);
                            }
                        }
                    }
                    if ((status == null || entity.getDeviceStatus() != status) || sendMessage != entity.getSendMessage()) {
                        fireAlarmDeviceDao.modifyDeviceStatusById(entity);
                    }
                }
            } else if (Integer.parseInt(ConstantsIot.FIRE_ALARM_SEND_OFF_LINE_EVENT) == 2) {
                for (FireAlarmDevice entity : list) {
                    status = entity.getDeviceStatus();
                    if (entity.getEndHeartbeatTime() != null) {
                        diff = (System.currentTimeMillis() - entity.getEndHeartbeatTime().getTime()) / (1000 * 60);
                        if (diff < 10) {
                            entity.setDeviceStatus(1);
                        } else {
                            //设备状态 1：在线，2：离线
                            entity.setDeviceStatus(2);
                        }
                    }

                    if (status == null || status.equals(entity.getDeviceStatus())) {
                        fireAlarmDeviceDao.modifyDeviceStatusById(entity);
                    }
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 查询是否有相同的火警主机设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-11-25 15:34
     */
    @Override
    public Data findIsSameFireAlarmDevice(String json) {
        FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
        List<FireAlarmDevice> list = fireAlarmDeviceDao.findIsSameFireAlarmDevice(fireAlarmDevice);
        Map<String, Object> map = new HashMap<>(4);
        if (list == null || list.size() <= 0) {
            map.put("obj", null);
            map.put("flag", false);
            return asseData(map);
        }
        map.put("flag", true);
        map.put("obj", list.get(0));
        return asseData(map);
    }

    /**
     * 判断注册码是否存在
     *
     * @param transDeviceId 传感器ID/传输装置id
     * @return JSONObject
     * @author dwt
     * @date 2019-11-26 16:52
     */
    @Override
    public JSONObject isSensorPresence(String transDeviceId) {
        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("code", 200);
        jsonObject.put("msg", "success");
        FireAlarmDevice device = getFireAlarmDeviceByRedis(transDeviceId, null);
        if (device != null) {
            jsonObject.put("isPresence", "true");
            return jsonObject;
        }
        jsonObject.put("isPresence", "false");
        return jsonObject;
    }

    /**
     * 逻辑删除火警主机设备
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-11-27 16:04
     */
    @Override
    public Data deleteFireAlarmDevice(String json) {
        FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
        List<FireAlarmDevice> list = fireAlarmDeviceDao.findFireAlarmDeviceById(fireAlarmDevice);
        if (list != null && list.size() > 0) {
            int status = fireAlarmDevice.getStatus();
            // 用户ID
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            fireAlarmDevice.setUpdateUserId(loginUserId);
            List<Long> deviceIds = new ArrayList<>();
            for (FireAlarmDevice f : list) {
                f.setUpdateUserId(loginUserId);
                f.setStatus(status);
                deviceIds.add(f.getDeviceId());
                if (f.getTransDeviceType() != 3) {
                    redisUtils.set("FireAlarm" + f.getTransDeviceId(), "FireAlarm");
                } else {
                    redisUtils.set("FireAlarm" + f.getTransDeviceId() + "_" + f.getFireAlarmNo(), "FireAlarm");
                }
            }
            fireAlarmDeviceDao.deleteFireAlarmDevice(list);
            deviceDao.modifyFireAlarmDeviceClearIot(list);
            Data data = linkageDeviceRelationService.deleteDeviceTableByDeviceId(deviceIds.toArray(new Long[]{}));
            try {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_TASK", SecurityUserHolder.getUserName(), fireAlarmDevice, null,
                        fireAlarmDevice.getProjectId(), 2);
            } catch (Exception e) {
                SysLog.error(e);
            }

            if (data != null && Integer.parseInt(data.getObject().toString()) > 0) {
                return asseData(600, "请将声光关联设备解绑！");
            }
        }
        return Data.isSuccess();
    }

    /**
     * APP端火警主机设备信息
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-03-04 15:13
     */
    @Override
    public Data findFireAlarmDeviceInfoApp(String json) {
        Map<String, Object> map = new HashMap<>();
        FireAlarmDevice fireAlarmDevice = JSONObject.parseObject(json, FireAlarmDevice.class);
        map = fireAlarmDeviceBaseData(map, fireAlarmDevice);
        return asseData(map);
    }

    /**
     * 火警主机设备基础信息
     *
     * @param map             Map<String, Object>
     * @param fireAlarmDevice FireAlarmDevice
     * @return Map<String, Object>
     * @author dwt
     * @date 2020-03-04 15:10
     */
    private Map<String, Object> fireAlarmDeviceBaseData(Map<String, Object> map, FireAlarmDevice fireAlarmDevice) {
        Integer deviceSum = fireAlarmDeviceDao.findFireAlarmIsLineCount(null, fireAlarmDevice.getProjectId());
        Integer deviceOnLineCount = fireAlarmDeviceDao.findFireAlarmIsLineCount(1, fireAlarmDevice.getProjectId());
        if (fireAlarmDevice.getOrderCols() == null || fireAlarmDevice.getOrderCols().length <= 0) {
            String[] orderCols = {"fad.create_time"};
            fireAlarmDevice.setOrderDesc(false);
            fireAlarmDevice.setOrderCols(orderCols);
        }
        List<FireAlarmDevice> fireAlarmDeviceList = fireAlarmDeviceDao.findFireAlarmDeviceList(fireAlarmDevice);
        map.put("deviceSum", deviceSum);
        map.put("deviceOnLineCount", deviceOnLineCount);
        map.put("listObj", fireAlarmDeviceList);
        return map;
    }

    /**
     * 查询火警主机设备总数，在线数，离线数
     *
     * @param projectId 项目ID
     * @return Data
     * @author dwt
     * @date 2020-03-26 14:44
     */
    @Override
    public Data findFireAlarmDeviceNum(Long projectId) {
        Integer deviceSum = fireAlarmDeviceDao.findFireAlarmIsLineCount(null, projectId);
        Integer deviceOffLineCount = fireAlarmDeviceDao.findFireAlarmIsLineCount(2, projectId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("fireDeviceSum", deviceSum);
        map.put("offlineNum", deviceOffLineCount);
        return asseData(map);
    }

    /**
     * 根据传感器ID获取设备
     *
     * @param sensor      传感器ID/传输装置id
     * @param fireAlarmNo 火警主机编号
     * @return com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice
     * @author dwt
     * @date 2020-10-21 9:19
     */
    @Override
    public FireAlarmDevice getFireAlarmDeviceByRedis(String sensor, String fireAlarmNo) {
        String json;
        if (StringUtils.isNotEmpty(fireAlarmNo)) {
            json = redisUtils.getString("FireAlarm" + sensor + "_" + fireAlarmNo);
        } else {
            json = redisUtils.getString("FireAlarm" + sensor);
        }
        if (json != null && "FireAlarm".equals(json)) {
            return null;
        }
        FireAlarmDevice device = JSONObject.parseObject(json,FireAlarmDevice.class);
        if (device == null) {
            FireAlarmDevice fireAlarmDevice = new FireAlarmDevice();
            fireAlarmDevice.setTransDeviceId(sensor);
            fireAlarmDevice.setFireAlarmNo(fireAlarmNo);
            device = fireAlarmDeviceDao.findFireAlarmDevice(fireAlarmDevice);
            if (device != null) {
                if (StringUtils.isNotEmpty(fireAlarmNo)) {
                    redisUtils.set("FireAlarm" + sensor + "_" + fireAlarmNo, JSONObject.toJSONString(device));
                } else {
                    redisUtils.set("FireAlarm" + sensor, JSONObject.toJSONString(device));
                }
            } else {
                if (StringUtils.isNotEmpty(fireAlarmNo)) {
                    redisUtils.set("FireAlarm" + sensor + "_" + fireAlarmNo, "FireAlarm");
                } else {
                    redisUtils.set("FireAlarm" + sensor, "FireAlarm");
                }
            }
        }
        return device;
    }

}
