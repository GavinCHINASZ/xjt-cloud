package com.xjt.cloud.iot.core.service.impl.fire;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.fire.*;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.fire.*;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmDeviceService;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmRecordService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendFireMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FireAlarmRecordServiceImpl
 * @Author dwt
 * @Date 2019-10-11 15:36
 * @Description 火警主机记录Service实现类
 * @Version 1.0
 */
@Service
public class FireAlarmRecordServiceImpl extends AbstractService implements FireAlarmRecordService {

    @Autowired
    private FireAlarmRecordDao fireAlarmRecordDao;
    @Autowired
    private FireAlarmDeviceDao fireAlarmDeviceDao;
    @Autowired
    private FireAlarmEventDao fireAlarmEventDao;
    @Autowired
    private FireAlarmCodeDao fireAlarmCodeDao;
    @Autowired
    private SendFireMessageService sendFireMessageService;
    @Autowired
    private FireAlarmAreaTypeDao fireAlarmAreaTypeDao;
    @Autowired
    private LinkageControlService linkageControlService;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private FireAlarmDeviceService fireAlarmDeviceService;
    /**
     *@Author: dwt
     *@Date: 2019-10-24 14:28
     *@Param: json
     *@Return: Data
     *@Description 保存事件记录
     */
    @Override
    public List<JSONObject> saveFireAlarmRecord(String json){
        FireAlarmMessage fireAlarmMessage = JSONObject.parseObject(json,FireAlarmMessage.class);
        boolean flag = fireAlarmMessage.isHeartbeat();
        String from = fireAlarmMessage.getFrom();
        Integer eventType = fireAlarmMessage.getEvent();
        JSONObject jsonObject = new JSONObject();

        if(StringUtils.isNotEmpty(from) && from.startsWith("$$_")){
            FireAlarmRecord fireAlarmRecord = new FireAlarmRecord(fireAlarmMessage);
            //查询火警主机设备信息
            FireAlarmDevice fireAlarmDevice = fireAlarmDeviceService.getFireAlarmDeviceByRedis(from,fireAlarmRecord.getFireAlarmNo());

            //查询离线事件列表
            List<FireAlarmEvent> deviceList = fireAlarmEventDao.findFireEventByFrom(from);
            if(deviceList != null && deviceList.size() > 0){
                fireAlarmEventDao.modifyOffLineEventStatus(from);//更新离线事件记录为恢复状态
                for (FireAlarmEvent event : deviceList){
                    event.setFloorName(fireAlarmDevice.getFloorName());
                    event.setBuildingName(fireAlarmDevice.getBuildingName());
                    event.setBuildingId(fireAlarmDevice.getBuildingId());
                    event.setBuildingFloorId(fireAlarmDevice.getBuildingFloorId());
                    event.setQrNo(fireAlarmDevice.getCheckPointQrNo());
                    sendFireMessageService.sendFireMessage(event,8);//发送离线恢复消息
                    break;
                }
            }

            fireAlarmDevice.setTransDeviceId(from);
            int fireAlarmStatus = 0;
            int faultStatus = 0;
            if (eventType == 17){//判断火警状态是否正常
                fireAlarmStatus = 1;
            }else if(eventType == 1){//判断火警状态是否是异常
                fireAlarmStatus = 2;
            }
            if (eventType == 6 ){//判断故障状态是否正常
                faultStatus = 1;
            }else if(eventType == 7 || eventType == 14 || eventType == 15 || eventType == 16){//判断故障状态是否正常
                faultStatus = 2;
            }
            fireAlarmDevice.setFireAlarmStatus(fireAlarmStatus);
            fireAlarmDevice.setFaultStatus(faultStatus);
            fireAlarmDeviceDao.modifyHeartTime(fireAlarmDevice);//更新火警主机设备状态和心跳时间
            boolean isSave = true;//判断是否要保存信息

            String key = "fireAlarm_" + StringUtils.urlEncode(from + fireAlarmMessage.getPosition());
            if (eventType == 2 || eventType == 7) {//判断2-监管事件 7-故障事件每个设备每日只发送一条
                key += eventType;
                String sendOldTime = redisUtils.getString(key);
                Long time = DateUtils.getDateTime();
                if (StringUtils.isNotEmpty(sendOldTime) && Long.parseLong(sendOldTime) + ConstantsIot.ONE_DAY_TIME > time){
                    isSave = false;
                }else{
                    redisUtils.set(key,time, ConstantsIot.ONE_DAY_SECONDS);
                }
            }else if(eventType == 6 || eventType == 17){
                redisUtils.del(key + 2);
                redisUtils.del(key + 7);
            }
            Device device = new Device();
            device.setId(fireAlarmDevice.getDeviceId());
            if(!flag && isSave){//是否心跳,是否要保存信息
                if(fireAlarmDevice != null){
                    /*
                     * 事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，7-故障事件，8-系统事件，（9-例行检查），10-
                     * 其他 11-屏蔽,12-启动,13-延时状态,14-主电故障,15-备电故障,16-总线故障,17-系统复位,20,传输装置复位,21-离线事件',
                     */
                    fireAlarmRecord.setDeviceId(fireAlarmDevice.getDeviceId());
                    fireAlarmRecord.setFireAlarmDeviceId(fireAlarmDevice.getId());
                    fireAlarmRecord.setProjectId(fireAlarmDevice.getProjectId());
                    fireAlarmRecord.setFireAlarmNo(fireAlarmDevice.getFireAlarmNo());
                    fireAlarmRecordDao.saveFireAlarmRecord(fireAlarmRecord);//保存信息记录
                /*fireAlarmDevice.setDeviceStatus(1);
                fireAlarmDeviceDao.modifyDeviceStatusById(fireAlarmDevice);*/
                    List<FireAlarmEvent> fireAlarmEventList;
                    if(eventType == 6){//故障恢复
                        fireAlarmRecord.setEventType(null);
                    /*if(fireAlarmMessage.getFrom().startsWith("$$_NFS")){
                        fireAlarmRecord.setEventType(7);
                    }else */
                        if(from.startsWith("$$_XPLS") || from.startsWith("$$_NFSG")){
                            fireAlarmRecord.setEventType(fireAlarmMessage.getRecoverEvent());
                        }else{
                            Integer[] eventTypeArr = {1,2,7};
                            fireAlarmRecord.setEventTypeArr(eventTypeArr);
                        }
                        fireAlarmEventList = fireAlarmEventDao.findEventListByRecord(fireAlarmRecord);
                        if(fireAlarmEventList != null && fireAlarmEventList.size() > 0){
                            for(FireAlarmEvent fire : fireAlarmEventList){
                                fire.setRecoverStatus(1);
                                fire.setRecoverRecordId(fireAlarmRecord.getId());
                                fireAlarmEventDao.modifyFireAlarmEventRecoverStatus(fire);
                                eventType = sendFireMessageService.recoverEventStatus(fire.getEventType(),1);//返回消息事件类型
                                fire.setFloorName(fireAlarmDevice.getFloorName());
                                fire.setBuildingName(fireAlarmDevice.getBuildingName());
                                fire.setBuildingId(fireAlarmDevice.getBuildingId());
                                fire.setBuildingFloorId(fireAlarmDevice.getBuildingFloorId());
                                fire.setQrNo(fireAlarmDevice.getCheckPointQrNo());
                                sendFireMessageService.sendFireMessage(fire,eventType);
                            }
                        }

                    }else if(eventType == 17){//系统复位
                        FireAlarmEvent fire = new FireAlarmEvent();
                        fire.setTransDeviceId(fireAlarmRecord.getTransDeviceId());
                        fire.setProjectId(fireAlarmRecord.getProjectId());
                        fire.setDeviceId(fireAlarmRecord.getDeviceId());
                        //事件状态 1-已恢复，2-未恢复
                        fire.setRecoverStatus(1);
                        fire.setRecoverRecordId(fireAlarmRecord.getId());
                        fireAlarmEventDao.modifyFireAlarmEventRecoverStatus(fire);
                        device.setIotStatus(1);
                        deviceDao.modifyDeviceIotStatus(device);
                    }else if(eventType == 1 || eventType == 2 || eventType == 7){//1-火警事件，2-监管事件 7-故障事件
                        if (eventType == 1) {
                            jsonObject.put("iotType", ConstantsIot.FIRE_ALARM);
                            jsonObject.put("msg", 200);
                            WebSocketSendMsgUtils.nettySendMsg(jsonObject);
                        }
                        FireAlarmEvent event = null;
                        if(!fireAlarmRecord.getTransDeviceId().startsWith("$$_ADH_")){
                            event = fireAlarmEventDao.findEventByRecord(fireAlarmRecord);
                        }
                        //设备类型 0:默认不查编码表;1：userCode;2：按主机号,回路,地址查询;3：主机号,设备地址;
                        if(event == null || fireAlarmRecord.getTransDeviceId().startsWith("$$_ADH_")){
                            device.setIotStatus(2);
                            deviceDao.modifyDeviceIotStatus(device);
                            saveFireAlarmEvent(fireAlarmRecord,fireAlarmDevice);//保存事件信息

                        }
                    }
                }
            }
        }
        List<JSONObject> jsonList = new ArrayList<>();
        try {
            jsonObject = new JSONObject();
            jsonObject.put("iotType",ConstantsIot.FIRE_ALARM);
            jsonList.add(0, jsonObject);
            jsonList.add(1, jsonObject);
            jsonList.add(2,jsonObject);
            //休眠一秒
            //Thread.sleep(1000L);
        } catch (Exception e) {
            SysLog.error(e);
        }
        return jsonList;
    }
    /**
     *@Author: dwt
     *@Date: 2019-10-25 10:07
     *@Param: FireAlarmRecord,int
     *@Return: Data
     *@Description 保存事件
     */
    private void saveFireAlarmEvent(FireAlarmRecord fireAlarmRecord,FireAlarmDevice fireAlarmDevice){
        //设备类型 0:默认不查编码表;1：主机号,回路,设备编号查询;2：按主机号,回路,地址查询;3：主机号,设备地址;
        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent(fireAlarmRecord);
        Integer type = fireAlarmDevice.getCodeType();//设备编码类型 0:默认不查编码表;1：userCode查询;2:按主机号,回路查询;3:按主机号,回路,地址查询,4:按userCode,主机号查询
        Integer eventType = fireAlarmRecord.getEventType();
        if(type != null && type != 0){
            FireAlarmCode fireAlarmCode = new FireAlarmCode();
            fireAlarmCode.setProjectId(fireAlarmRecord.getProjectId());
            if(type == 1){
                if(fireAlarmRecord.getTransDeviceId().startsWith("$$_SIEMENS_")){
                    fireAlarmCode.setControllerLoop(fireAlarmRecord.getRecordPosition()+"");
                }
                fireAlarmCode.setUserCode(fireAlarmRecord.getDeviceCode());
            }else if(type == 2){
                //按主机号,回路查询 （火警主机:新普利斯）
                fireAlarmCode.setControllerNumber(Integer.parseInt(fireAlarmRecord.getRecordPosition()));
                fireAlarmCode.setControllerLoop(fireAlarmRecord.getPositionCode());
            }else if(type == 3){
                //按主机号,回路,地址查询 （火警主机:依爱）
                fireAlarmCode.setControllerNumber(Integer.parseInt(fireAlarmRecord.getFireAlarmNo()));
                fireAlarmCode.setControllerLoop(fireAlarmRecord.getPositionCode());
                fireAlarmCode.setDeviceAddress(fireAlarmRecord.getDeviceAddress());
            }else if(type == 4){
                //userCode,controllerNumber
                fireAlarmCode.setUserCode(fireAlarmRecord.getDeviceCode());
                fireAlarmCode.setControllerNumber(Integer.parseInt(fireAlarmRecord.getPositionCode()));
            }
            if(StringUtils.isEmpty(fireAlarmEvent.getAlarmPosition())){
                fireAlarmEvent.setAlarmPosition(fireAlarmRecord.getDeviceAddress());
            }
            FireAlarmCode code = fireAlarmCodeDao.findFireAlarmCode(fireAlarmCode);
            if(code != null){
                fireAlarmEvent.setAlarmDeviceName(code.getDeviceName());
                fireAlarmEvent.setAlarmPosition(code.getDeviceAddress());
            }
        }
        fireAlarmEvent.setTransDeviceName(fireAlarmDevice.getTransDeviceName());
        //事件状态 1-已恢复，2-未恢复
        fireAlarmEvent.setRecoverStatus(2);
        fireAlarmEvent.setDeviceId(fireAlarmDevice.getDeviceId());
        fireAlarmEvent.setFireAlarmDeviceId(fireAlarmDevice.getId());
        fireAlarmEvent.setFireAlarmNo(fireAlarmDevice.getFireAlarmNo());
        fireAlarmEvent.setDescription(fireAlarmRecord.getDescription());
        fireAlarmEvent.setFloorName(fireAlarmDevice.getFloorName());
        fireAlarmEvent.setBuildingName(fireAlarmDevice.getBuildingName());
        fireAlarmEvent.setBuildingId(fireAlarmDevice.getBuildingId());
        fireAlarmEvent.setBuildingFloorId(fireAlarmDevice.getBuildingFloorId());
        fireAlarmEvent.setQrNo(fireAlarmDevice.getCheckPointQrNo());
        if(StringUtils.isNotEmpty(fireAlarmRecord.getLoopName())){
            FireAlarmAreaType fireAlarmAreaType = new FireAlarmAreaType();
            fireAlarmAreaType.setLoopName(fireAlarmRecord.getLoopName());
            fireAlarmAreaType.setProjectId(fireAlarmRecord.getProjectId());
            fireAlarmAreaType = fireAlarmAreaTypeDao.findAreaTypeByLoopAndProjectId(fireAlarmAreaType);
            if(fireAlarmAreaType != null){
                fireAlarmEvent.setAreaType(fireAlarmAreaType.getAreaType());
                fireAlarmEvent.setUnit(fireAlarmAreaType.getUnit());
            }
        }
        fireAlarmEventDao.saveFireAlarmEvent(fireAlarmEvent);
        //联动报警
        String sign = "fireAlarm_fire_event";
        if(eventType == 2){
            sign = "fireAlarm_monitor_event";
        }else if(eventType == 7){
            sign = "fireAlarm_fault_event";
        }
        int messageType = sendFireMessageService.recoverEventStatus(fireAlarmEvent.getEventType(),2);//返回消息事件类型
        sendFireMessageService.sendFireMessage(fireAlarmEvent,messageType);
        linkageControlService.deviceFault(fireAlarmDevice.getDeviceId(),sign);
        //联动报警
        //HttpUtils.sendGet(ConstantsIot.SEND_LINKAGE_DEVICE_MSG_URL, "json={\"projectId\":" + fireAlarmEvent.getProjectId() + ",\"deviceQrNo\":" + fireAlarmRecord.getDeviceCode() +"}");
        if(StringUtils.isNotEmpty(ConstantsIot.IS_METRO_PROJECT) && "1".equals(ConstantsIot.IS_METRO_PROJECT)){
            //地铁抽检设备自动检测
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("projectId",fireAlarmEvent.getProjectId());
            jsonObject.put("deviceQrNo",fireAlarmRecord.getDeviceCode());
            JSONObject taskObj = HttpUtils.httpGet(ConstantsIot.FIRE_ALARM_METRO_DEVICE_AUTO_CHECK, jsonObject.toString(), "json");
            SysLog.info("阿发信息返回：————————"+taskObj.toString());
        }
    }
}
