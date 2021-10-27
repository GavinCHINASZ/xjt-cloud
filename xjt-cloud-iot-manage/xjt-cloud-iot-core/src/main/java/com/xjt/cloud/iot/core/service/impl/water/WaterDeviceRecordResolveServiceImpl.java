package com.xjt.cloud.iot.core.service.impl.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.water.WaterDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.water.WaterDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.water.WaterDevice;
import com.xjt.cloud.iot.core.entity.water.WaterRecord;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceRecordResolveService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/23 10:00
 * @Description: 水设备记录解析接口
 */
@Service
public class WaterDeviceRecordResolveServiceImpl extends AbstractService implements WaterDeviceRecordResolveService {

    @Autowired
    private WaterDeviceDao waterDeviceDao;
    @Autowired
    private WaterDeviceRecordDao waterDeviceRecordDao;
    @Autowired
    private LinkageControlService linkageControlService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 功能描述: 水压设备上传信息处理接口
     *
     * @param jsonObject
     * @return: JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 15:24
     */
    @Override
    public List<JSONObject> waterDeviceDataAccess(JSONObject jsonObject) {
        List<JSONObject> jsonList = new ArrayList<>();
        return jsonList;
    }

    /**
     * 功能描述: 水压/水浸/消火栓上传信息参数解析
     *
     * @param json
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/23 13:49
     */
    @Override
    public List<JSONObject> waterSys(JSONObject json) {
        SysLog.info(json+"-------->参数信息");
        String sensorNo;
        Integer dataType;
        String sourceData;
        Integer deviceType;
        Integer monitorStatus = 0;

        String msgType = json.getString("msgType");
        if("Hydrant".equals(msgType)){
            sensorNo = json.getString("from");
        }else {
            sensorNo = json.getString("sensorId");
            monitorStatus = json.getInteger("monitorStatus");
        }

        deviceType = json.getInteger("deviceType");
        dataType = json.getInteger("dataType");
        sourceData = json.getString("sourceData");
        List<JSONObject> jsonList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(2);
        jsonList.add(jsonObject);
        String msg = "[#end]";
        if (deviceType == 1) {
            //没有设置功能
            waterDataAnalyze(jsonList, 0, sensorNo, sourceData, msgType, json, monitorStatus);
        } else if (deviceType == 2) {
            //有设置功能
            msg = deviceSetting(msg, dataType, jsonList, sensorNo, sourceData, msgType, json, monitorStatus);
        }

        jsonObject.put("writeBackMsg", msg);
        jsonObject.put("iotType", ConstantsIot.WATER_GAGE);
        jsonList.add(0, jsonObject);
        return jsonList;
    }

    /**
     * 功能描述:设置信息
     *
     * @param msg
     * @param dataType
     * @param jsonList
     * @param sensorNo
     * @param sourceData
     * @param msgType
     * @param json
     * @param monitorStatus
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/12/27 9:51
     */
    private String deviceSetting(String msg, Integer dataType, List jsonList, String sensorNo, String sourceData, String msgType, JSONObject json, Integer monitorStatus) {
        try {
            int state = 0;
            if (dataType == 0) {
                //正常记录数据
                state = waterDataAnalyze(jsonList, state, sensorNo, sourceData, msgType, json, monitorStatus);
            } else if (dataType == 1 || dataType == 3  || dataType == 4 ) {
                // 1水压设备设置信息确定收到，2消火栓ip设置 3消火栓设置
                WaterDevice waterDevice = new WaterDevice();
                waterDevice.setFindSensorNo(sensorNo);
                if (dataType == 4){
                    waterDevice.setValueUpdateStatus(4);
                }else{
                    waterDevice.setSetUpdateStatus(4);
                }
                waterDeviceDao.modifyWaterDevice(waterDevice);
                state = 2;
            } else if (dataType == 2) {
                // 设备参数设置信息上传
                checkData(json);
                state = 4;
            }
            //休眠一秒
            Thread.sleep(1000);
            if (state == 4 || state == 0) {
                // 结束
                msg = "[#end]";
            } else if (state == 1 || state == 3 || state == 6 || state == 8) {
                // 下发设置参数
                msg = waterDevice(sensorNo);
            } else if (state == 2) {
                // 下发查询
                if("Hydrant".equals(msgType)){
                    msg = "{end}";
                }else {
                    msg = "[#QUERY]";
                }
            } else if (state == -1) {
                msg = "[#poweroff]";
            } else if ("Hydrant".equals(msgType)){
                msg = "";
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return msg;
    }

    /**
     * @MethodName: waterDataAnalyze 麦克水压水浸数据解析
     * @Description:
     * @Param: [state, sensorNo, sourceData, msgType, map]
     * @Return: int
     * @Author: zhangZaiFa
     * @Date:2019/10/22 14:09
     **/
    private int waterDataAnalyze(List<JSONObject> jsonList, int state, String sensorNo, String sourceData, String msgType, JSONObject json, Integer monitorStatus) {
        WaterDevice waterDevice = new WaterDevice();
        waterDevice.setSensorNo(sensorNo);
        waterDevice = waterDeviceDao.findWaterDevice(waterDevice);
        if (waterDevice != null) {
            Float electricity = json.getFloat("electricity") * 100.00F;
            Integer electricQuantity = null;
            if(electricity != null){
                electricQuantity = Integer.valueOf(electricity.intValue());
            }
            Float voltage = json.getFloat("voltage");
            Integer sign = json.getInteger("sign");
            SysLog.info("传感器ID:" + sensorNo + "消息类型:" + msgType + "监测值 :" + json.getString("value") + "电压:" + voltage + "信号强度:" + sign);
            WaterRecord wr = new WaterRecord();
            wr.setRawData(sourceData);
            wr.setSensorNo(sensorNo);
            wr.setDeviceTypeId(waterDevice.getDeviceTypeId());
            wr.setUnit(waterDevice.getUnit());
            if (sign != null) {
                sign = sign * 100;
            }
            wr.setSignalIntensity(sign);
            wr.setElectricQuantity(electricQuantity);
            wr.setDeviceId(waterDevice.getDeviceId());
            wr.setProjectId(waterDevice.getProjectId());
            //wr.setQrNo(waterDevice.getQrNo());
            //wr.setDeviceSysName(waterDevice.getDeviceSysName());
            wr.setWaterId(waterDevice.getId());
            if (sign != null) {
                wr.setSignalIntensity(sign);
                wr.setSignalStatus(sign <= 1500 ? 2 : 1);
            }
            wr.setElectricQuantity(electricQuantity);
            wr.setElectricQuantityStatus(electricQuantity <= 3000 ? 2 : 1);//判断电量是否超低
            String valueStr = json.getString("value");
            if (msgType.equals("WaterImmersion")) { // 水浸
                wr.setDeviceType(7);//设备类型，用于判断事件信息发送与声光报警
                wr.setValueStr(valueStr);
                if (StringUtils.isNotEmpty(valueStr)){
                    String status = "0000000000000000";
                    if(status.startsWith(valueStr)){
                        wr.setLeakMonitorStatus(1);
                    }else{
                        wr.setLeakMonitorStatus(2);
                    }
                }
                waterDevice = waterImmersion(waterDevice);
                state = waterDevice.getSetUpdateStatus();
            } else if (msgType.equals("WaterGage")) { // 水压或液位设备
                wr.setDeviceType(-3);//设备类型，用于判断事件信息发送与声光报警
                wr.setType(1);
                wr.setValueStr(valueStr);
                wr.setMaxValue(waterDevice.getMaxValue());//当前最大值
                wr.setMinValue(waterDevice.getMinValue());//当前最小值
                int value;
                BigDecimal value1 = new BigDecimal(valueStr);
                BigDecimal value2 = new BigDecimal("100.00");
                value = (int)(value1.multiply(value2).doubleValue());
                wr.setPresentValue(value);

                if (monitorStatus != null && monitorStatus != 0) {//取设备实时的状态
                    wr.setMonitorStatus(monitorStatus);
                } else {//根据实侧值判断设备状态
                    if (value > waterDevice.getMaxValue()) {
                        wr.setMonitorStatus(2);
                    } else if (value < waterDevice.getMinValue()) {
                        wr.setMonitorStatus(3);
                    } else {
                        wr.setMonitorStatus(1);
                    }
                }

                if (waterDevice.getUnit() == null) {
                    if (sensorNo.startsWith("E")) {
                        waterDevice.setUnit("m");
                        wr.setUnit("m");
                    } else {
                        waterDevice.setUnit("MPa");
                        wr.setUnit("MPa");
                    }
                } else {
                    wr.setUnit(waterDevice.getUnit());
                }

                waterDevice = waterGage(waterDevice);
                state = waterDevice.getSetUpdateStatus();
            }else if("Hydrant".equals(msgType)){
                wr.setDeviceType(8);//设备类型，用于判断事件信息发送与声光报警
                wr.setValueStr(valueStr);
                wr.setMaxValue(waterDevice.getMaxValue());//当前最大值
                wr.setMinValue(waterDevice.getMinValue());//当前最小值
                Float value = Float.parseFloat(valueStr);
                wr.setPresentValue((int)(value * 100.00f));
                wr.setUnit(json.getString("unit"));
                waterDevice.setUnit(json.getString("unit"));
                Integer leakage = json.getInteger("leakage");
                if (monitorStatus == null || monitorStatus == 0) {//取设备实时的状态
                    //根据实侧值判断设备状态
                    if (wr.getPresentValue() > waterDevice.getMaxValue()) {
                        wr.setMonitorStatus(2);
                    } else if (wr.getPresentValue() < waterDevice.getMinValue()) {
                        wr.setMonitorStatus(3);
                    } else {
                        wr.setMonitorStatus(1);
                    }
                }
                if(leakage != null){
                    if(leakage == 0){
                        wr.setLeakMonitorStatus(1);
                    }else if(leakage == 1){
                        wr.setLeakMonitorStatus(2);
                    }else if(leakage >= 2 && leakage <= 7){
                        wr.setLeakMonitorStatus(3);
                    }else if(leakage == 8){
                        wr.setLeakMonitorStatus(4);
                    }
                }

                Integer toHit = json.getInteger("toHit");
                if(toHit != null){
                    if(toHit == 0){
                        wr.setStrikeMonitorStatus(1);
                    }else if(toHit == 1){
                        wr.setStrikeMonitorStatus(2);
                    }else if(toHit == 2){
                        wr.setStrikeMonitorStatus(4);
                    }
                }
                Integer openCover = json.getInteger("openCover");
                if(openCover != null && openCover == 1){
                    wr.setOpenCoverStatus(2);
                }else{
                    wr.setOpenCoverStatus(1);
                }
                state = waterDevice.getSetUpdateStatus();
                if(state == 0 || state == 4){
                    state = 5 + waterDevice.getValueUpdateStatus();
                }
            }
            waterDevice.setDeviceType(1);//设备设备在线
            List<JSONObject> jsons = saveWaterInfo(wr, waterDevice);
            jsonList.addAll(jsons);
            return state;
        } else {
            SysLog.info(sensorNo + "未绑定设备");
            waterDevice = new WaterDevice();
            waterDevice.setSensorNo(sensorNo);
            waterDevice = waterDeviceDao.findWaterDeviceDelTime(waterDevice);
            if(waterDevice != null ){
                SysLog.info(sensorNo + "设备在3天内删除");
                return 0;
            }
            return -1;
        }
    }

    /**
     * 功能描述:保存水设备状态信息 、记录信息、事件信息
     *
     * @param waterRecord
     * @param waterDevice
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/10/23 15:44
     */
    private List<JSONObject> saveWaterInfo(WaterRecord waterRecord, WaterDevice waterDevice) {
        //保存水压记录
        waterRecord.setDeviceStatus(1);//设备在线
        waterDeviceRecordDao.saveWaterRecord(waterRecord);

        List<JSONObject> jsonList = new ArrayList<>();
        JSONObject jsonObject;
        Integer recordMonitorStatus = waterRecord.getMonitorStatus();//设备监测状态
        Integer electricQuantityStatus = waterRecord.getElectricQuantityStatus();//电量状态
        Integer leakMonitorStatus = waterRecord.getLeakMonitorStatus();//漏水监测
        Integer strikeMonitorStatus = waterRecord.getStrikeMonitorStatus();//撞击监测
        Integer deviceType = waterRecord.getDeviceType();
        Integer openCoverStatus = 1;
        if(deviceType == 8){
            openCoverStatus = waterRecord.getOpenCoverStatus();//消火栓开盖
        }
        //判断是否是修改设备状态
        if (waterDevice.getSignalStatus() == null || waterDevice.getElectricQuantityStatus() == null ||
                waterDevice.getMonitorStatus() == null || waterDevice.getLeakMonitorStatus() == null ||
                waterDevice.getStrikeMonitorStatus() == null || //各状态是否为空
                waterDevice.getDeviceStatus() == 2 || //设备状态
                !waterDevice.getSignalStatus().equals(waterRecord.getSignalStatus()) || //判断信号
                !waterDevice.getElectricQuantityStatus().equals(electricQuantityStatus) || //电量
                !waterDevice.getMonitorStatus().equals(recordMonitorStatus) || //监测状态
                !waterDevice.getLeakMonitorStatus().equals(leakMonitorStatus) || //漏水监测
                !waterDevice.getStrikeMonitorStatus().equals(strikeMonitorStatus)) { //撞击监测

            int modifySaveMsg = 0;
            int iotStatus = 1;//水压设备是否有问题1：正常，2:异常
            if (null != electricQuantityStatus && electricQuantityStatus == 2 ||
                    null != recordMonitorStatus && recordMonitorStatus >= 2 ||
                    null != leakMonitorStatus && leakMonitorStatus == 2 ||
                    null != strikeMonitorStatus && strikeMonitorStatus == 2){
                iotStatus = 2;
            }
            Device device = new Device();
            device.setId(waterDevice.getDeviceId());
            device.setIotStatus(iotStatus);
            deviceDao.modifyDeviceIotStatus(device);

            //判断设备是否离线,如离线,则修改离线事件信息
            modifySaveMsg = saveDeviceStatus(waterDevice, waterRecord, modifySaveMsg);

            //判断电量是否过低
            modifySaveMsg = saveEventInfo(waterDevice,waterRecord.getElectricQuantity(), waterRecord,waterDevice.getElectricQuantityStatus(), electricQuantityStatus,
                    3, 1, modifySaveMsg);

            //判断设备是否超高超低
            modifySaveMsg = saveEventInfo(waterDevice, waterRecord.getPresentValue(),waterRecord,waterDevice.getMonitorStatus(), recordMonitorStatus,
                    1, 2, modifySaveMsg);

            //漏水监测
            modifySaveMsg = saveEventInfo(waterDevice, null,waterRecord,waterDevice.getLeakMonitorStatus(), leakMonitorStatus,
                    5, 3, modifySaveMsg);
            //撞击监测
            modifySaveMsg = saveEventInfo(waterDevice,null, waterRecord,waterDevice.getStrikeMonitorStatus(), strikeMonitorStatus,
                    6, 4, modifySaveMsg);
            if(deviceType == 8){//消火栓开盖
                modifySaveMsg = saveEventInfo(waterDevice,null, waterRecord,waterDevice.getOpenCoverStatus(), openCoverStatus,
                        7, 4, modifySaveMsg);
            }

            if (modifySaveMsg == 4) {//判断是否要通知web页面
                //配置发送的短信次数与发送时间
                Date date = DateUtils.getDate();
                Date sendTime = waterDevice.getSendTime();
                //判断最后一次发送是否是当天
                if (sendTime == null || DateUtils.formatyyyyMMddDate(date).equals(DateUtils.formatyyyyMMddDate(sendTime))){
                    waterDevice.setSendTime(date);
                    waterDevice.setSendNum((waterDevice.getSendNum() == null ? 0 : waterDevice.getSendNum()) + 1);
                }else{
                    waterDevice.setSendTime(date);
                    waterDevice.setSendNum(1);
                }
                jsonObject = new JSONObject();
                jsonObject.put("msg", 200);
                if (deviceType == 8){
                    jsonObject.put("iotType", ConstantsIot.HYDRANT);
                }else if(deviceType == 3){
                    jsonObject.put("iotType", ConstantsIot.WATER_IMMERSION);
                }else{
                    jsonObject.put("iotType", ConstantsIot.WATER_GAGE);
                }

                jsonList.add(jsonObject);
                WebSocketSendMsgUtils.nettySendMsg(jsonObject);
            }
            SysLog.info("水压联动值modifySaveMsg=" + modifySaveMsg);
            /*if (modifySaveMsg > 1) {
                //联动报警
                linkageControlService.deviceFault(waterDevice.getDeviceId());
            }*/
        }

        modifyWaterDevice(waterDevice, waterRecord);
        return jsonList;
    }

    /**
     *
     * 功能描述: 水压设备信息保存处理
     *
     * @param waterDevice　数据库中的设备信息
     * @param waterRecord　此次上传的记录信息
     * @param deviceStatus　数据库中的状态
     * @param recordStatus　记录中的状态
     * @param eventType　事件类型　1监测状态　　2信号强度　　3电量 4设备状态 5漏水监测  6撞击监测
     * @param sendType　发送信息类型　1　发送电量信息　2监测值信息
     * @param modifySave　是否有修改保存标志
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/12/27 9:44
     */
    private int saveEventInfo(WaterDevice waterDevice,Integer presentValue, WaterRecord waterRecord, Integer deviceStatus, Integer recordStatus,
                              int eventType, Integer sendType, int modifySave){
        if ((deviceStatus == null && recordStatus != null) || (deviceStatus != null && recordStatus != null && !deviceStatus.equals(recordStatus))) {
            WaterRecord wr = new WaterRecord();
            wr.setSensorNo(waterRecord.getSensorNo());
            wr.setEventType(eventType);
            if(waterRecord.getDeviceType() == 8 && recordStatus != 1){
                wr.setDeviceStatus(recordStatus);
            }
            if(waterRecord.getDeviceType() == 8){
                waterRecord.setEventType(eventType);
            }
            wr = waterDeviceRecordDao.findNewestWaterDeviceEvent(wr);

            //状态为正常
            if (1 == recordStatus || (wr != null && wr.getDeviceStatus() != recordStatus)) {
                if (wr != null) {//最新记录为异常时，修改状态为已恢复
                    modifySave = 1;
                    wr.setRecoverRecordId(waterRecord.getId());
                    wr.setRecordId(wr.getId());
                    waterDeviceRecordDao.modifyWaterRecordEvent(wr);
                }
            }
            //eventType 事件类型　1监测状态　　2信号强度　　3电量 4设备状态 5漏水监测  6撞击监测 7开盖
            //recordStatus设备状态　1正常　2超高/信号弱/电量低/离线/漏水/撞击/开盖　3超低/ / / /取水/ / /　 4漏水/ / / /漏水传感器故障/撞击传感器故障/ /
            if (recordStatus > 1){//状态为异常
                //发送信息的类型发送不同的信息
                sendMsg(waterDevice, waterRecord, sendType, recordStatus);
                String sign = "";
                if(waterRecord.getDeviceType() == 8){//消火栓
                    if(eventType == 3){
                        sign = "hydrant_lackOf_electricity";
                    }else if(eventType == 1){
                        sign = "hydrant_pressure_event";
                    }else if(eventType == 5){
                        sign = "hydrant_fault_event";
                    }else if(eventType == 6){
                        sign = "hydrant_fault_event";
                    }
                }else if(waterRecord.getDeviceType() == -3){//水压
                    if(eventType == 3){
                        sign = "waterGage_lackOf_electricity";
                    }else if(eventType == 1){
                        sign = "waterGage_pressure_abnormality_event";
                    }
                }else if(waterRecord.getDeviceType() == 7){//水浸，7为信息判断时的值，
                    if(eventType == 5){
                        sign = "waterImmersion_overflow_event";
                    }
                }

                if (StringUtils.isNotEmpty(sign)){
                    linkageControlService.deviceFault(waterDevice.getDeviceId(), sign);
                }

                if (wr == null || (wr != null && wr.getDeviceStatus() != recordStatus)) {
                    //没有异常事件记录，直接添加事件记录
                    WaterRecord saveWr = new WaterRecord();
                    modifySave = 2;
                    saveWr.setType(eventType);
                    saveWr.setDeviceStatus(recordStatus);
                    saveWr.setPresentValue(presentValue);
                    saveWr.setProjectId(waterRecord.getProjectId());
                    saveWr.setDeviceId(waterRecord.getDeviceId());
                    saveWr.setWaterId(waterRecord.getWaterId());
                    saveWr.setMinValue(waterRecord.getMinValue());
                    saveWr.setMaxValue(waterRecord.getMaxValue());
                    saveWr.setSensorNo(waterRecord.getSensorNo());
                    saveWr.setDeviceTypeId(waterRecord.getDeviceTypeId());
                    saveWr.setDeviceType(waterRecord.getDeviceType());
                    saveWr.setUnit(waterRecord.getUnit());
                    saveWr.setId(waterRecord.getId());
                    waterDeviceRecordDao.saveWaterRecordEvent(saveWr);
                }
                if (modifySave == 2){
                    modifySave = 4;
                }else{
                    modifySave = 3;
                }
            }
        }
        return modifySave;
    }

    /**
     * 功能描述:修改水设备信息
     *
     * @param waterDevice
     * @param waterRecord
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/12/26 16:44
     */
    private void modifyWaterDevice(WaterDevice waterDevice,WaterRecord waterRecord){
        //修改水压设备基本信息
        Date date = new Date();
        waterDevice.setSensorNo(null);
        waterDevice.setWaterTerminalId(null);
        waterDevice.setDeviceStatus(1);//设备在线
        waterDevice.setEndHeartbeatTime(date);//最后心跳时间
        waterDevice.setSignalIntensity(waterRecord.getSignalIntensity());//信号
        waterDevice.setSignalStatus(waterRecord.getSignalStatus());
        waterDevice.setPresentValue(waterRecord.getPresentValue());//监测当前值
        waterDevice.setMonitorStatus(waterRecord.getMonitorStatus());
        waterDevice.setElectricQuantity(waterRecord.getElectricQuantity());//电量
        waterDevice.setElectricQuantityStatus(waterRecord.getElectricQuantityStatus());
        waterDevice.setStatusUpdateTime(date);
        waterDevice.setLeakMonitorStatus(waterRecord.getLeakMonitorStatus());
        waterDevice.setStrikeMonitorStatus(waterRecord.getStrikeMonitorStatus());
        waterDevice.setOpenCoverStatus(waterRecord.getOpenCoverStatus());
        waterDeviceDao.modifyWaterDevice(waterDevice);
    }

    /**
     * 功能描述: 设备状态信息
     *
     * @param waterDevice
     * @param waterRecord
     * @param modifySave
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/12/26 16:29
     */
    private int saveDeviceStatus(WaterDevice waterDevice,WaterRecord waterRecord, int modifySave){
        //判断设备是否离线,如离线,则修改离线事件信息
        if (waterDevice.getDeviceStatus() == null || waterDevice.getDeviceStatus() == 2) {
            modifySave = 1;
            WaterRecord wr = new WaterRecord();
            wr.setSensorNo(waterRecord.getSensorNo());
            wr.setDeviceStatus(2);
            wr.setEventType(4);
            wr = waterDeviceRecordDao.findNewestWaterDeviceEvent(wr);
            if (wr != null) {
                wr.setDeviceStatus(1);
                wr.setRecordId(wr.getId());
                waterDeviceRecordDao.modifyWaterRecordEvent(wr);
            }
            /*sendMsg(waterDevice, waterRecord, 99, null);//发送离线信息
            String sign = "";
            if(waterDevice.getDeviceType() == 8){
                sign = "hydrant_offLine_event";
            }else{
                sign = "waterGage_offline_event";
            }
            linkageControlService.deviceFault(waterDevice.getDeviceId(),sign);*/
        }
        return modifySave;
    }

    /**
     * 功能描述: 发送报警信息
     *
     * @param waterDevice
     * @param waterRecord
     * @param sendType
     * @param recordStatus
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/12/26 16:41
     */
    private void sendMsg(WaterDevice waterDevice, WaterRecord waterRecord, Integer sendType,Integer recordStatus){
        try {
            if (sendType != null) {
                Integer eventType = waterRecord.getEventType();
                //调用接口,从设备模块获取设备信息
                Device device = getObjByUrl(ConstantsIot.FIND_DEVICE_MODULE_CALLS, "{\"id\":" + waterRecord.getDeviceId() + "}", Device.class);
                String url = "";
                String msg = "";
                ////消息字体显示颜色 0、灰 1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫 8、水蓝 9、湖蓝 10、深湖蓝 11、浅黄 12、亚麻黄 13、橘红
                int fontColor = 0;
                int deviceType = waterRecord.getDeviceType();
                String eventRoleType = "";
                String title = "";
                String presentValueMsg = "";
                if (deviceType == -3){//水压通知
                    presentValueMsg = (waterRecord.getPresentValue() / 100.00f) + waterDevice.getUnit();
                    url = ConstantsIot.WATER_GAGE_MSG_URL;
                    if (sendType == 1){//电量
                        title = "低电量通知";
                        fontColor = 2;
                        msg = ConstantsIot.LOW_POWER_MSG;//电量超低提醒
                        eventRoleType = ConstantsIot.WATER_GAGE_LACK_OF_ELECTRICITY;
                    }else if (sendType == 2){//水压超高超低提醒
                        title = "水压消息通知";
                        fontColor = 1;
                        if (waterDevice.getType() == 14){
                            if (waterDevice.getWaterTerminalId() > 0){//判断是否是系统端
                                if (recordStatus == 2) {//水压超高提醒
                                    msg = ConstantsIot.WATER_SYS_MSG + presentValueMsg + ConstantsIot.SUPER_HIGH_MSG;
                                } else if (recordStatus == 3) {//水压超低提醒
                                    msg = ConstantsIot.WATER_SYS_MSG + presentValueMsg + ConstantsIot.ULTRALOW_MSG;
                                }
                            }else{
                                if (recordStatus == 2) {//水压超高提醒
                                    msg = ConstantsIot.WATER_SUPPLY_MSG + presentValueMsg + ConstantsIot.SUPER_HIGH_MSG;
                                } else if (recordStatus == 3) {//水压超低提醒
                                    msg = ConstantsIot.WATER_SUPPLY_MSG + presentValueMsg + ConstantsIot.ULTRALOW_MSG;
                                }
                            }
                        }else if(waterDevice.getType() == 13){//水箱
                            title = "液位消息通知";
                            if (recordStatus == 2) {//水压超高提醒
                                msg = ConstantsIot.WATER_TANK_MSG + presentValueMsg + ConstantsIot.SUPER_HIGH_MSG;
                            } else if (recordStatus == 3) {//水压超低提醒
                                msg = ConstantsIot.WATER_TANK_MSG + presentValueMsg + ConstantsIot.ULTRALOW_MSG;
                            }
                        }else{
                            if (recordStatus == 2) {//水压超高提醒
                                msg = ConstantsIot.WATER_BEGIN_MSG + presentValueMsg + ConstantsIot.SUPER_HIGH_MSG;
                            } else if (recordStatus == 3) {//水压超低提醒
                                msg = ConstantsIot.WATER_BEGIN_MSG + presentValueMsg + ConstantsIot.ULTRALOW_MSG;
                            }
                        }
                        if (waterDevice.getSendNum() != null && waterDevice.getSendNum() == 19){
                            msg = ConstantsIot.SEND_MSG_NUM_20;
                        }
                        eventRoleType = ConstantsIot.WATER_GAGE_PRESSURE_ABNORMALITY_EVENT;
                    }else if (sendType == 99){//水压离线恢复
                        title = "水压设备状态通知";
                        eventRoleType = ConstantsIot.WATER_GAGE_OFFLINE_EVENT;
                        msg = ConstantsIot.WATER_ONLINE_MSG;//水压离线
                    }
                }else if(deviceType == 7){//水浸
                    url = ConstantsIot.WATER_LEACHING_MSG_URL;
                    if (sendType == 1){//电量
                        title = "低电量通知";
                        fontColor = 2;
                        msg = ConstantsIot.LOW_POWER_MSG;//电量超低提醒
                        eventRoleType = ConstantsIot.WATER_IMMERSION_LACK_OF_ELECTRICITY;
                    }else if (sendType == 3){//水浸漏水
                        title = "漏水通知";
                        fontColor = 6;
                        msg = ConstantsIot.WATER_LEAKAGE;
                        eventRoleType = ConstantsIot.WATER_IMMERSION_OVERFLOW_EVENT;
                    }else if (sendType == 99){//水浸离线恢复
                        title = "水浸设备状态通知";
                        msg = ConstantsIot.WATER_ONLINE_LEAKAGE;
                        eventRoleType = ConstantsIot.WATER_IMMERSION_OFFLINE_EVENT;
                    }
                    if (waterDevice.getSendNum() != null && waterDevice.getSendNum() == 19){
                        msg = ConstantsIot.SEND_MSG_NUM_20;
                    }
                }else if(deviceType == 8){//5智能消火栓
                    eventRoleType = ConstantsIot.HYDRANT_FAULT_EVENT;
                    url = ConstantsIot.HYDRANT_MSG_URL;
                    presentValueMsg = (waterRecord.getPresentValue() / 100.00f) + waterDevice.getUnit();
                    if (sendType == 4 && eventType == 6) {//消火栓撞击
                        sendType = 5;
                        if(recordStatus == 4){
                            title = "撞击传感器故障通知";
                            fontColor = 12;
                            msg = ConstantsIot.HYDRANT_HIT_FAULT;
                        }else if(recordStatus == 2){
                            title = "撞击通知";
                            fontColor = 3;
                            msg = ConstantsIot.HYDRANT_HIT_MSG;
                        }
                    }else if(sendType == 4 && eventType == 7){//消火栓开盖
                        sendType = 5;
                        title = "开盖通知";
                        fontColor = 11;
                        msg = ConstantsIot.HYDRANT_OPEN_COVER;
                    }else if(sendType == 3){//消火栓漏水
                        sendType = 4;
                        if(recordStatus == 2){//漏水
                            title = "漏水通知";
                            fontColor = 6;
                            msg = ConstantsIot.WATER_LEAKAGE;
                        }else if(recordStatus == 3){//取水
                            title = "取水报警通知";
                            fontColor = 9;
                            msg = ConstantsIot.HYDRANT_INTAKE;
                        }else if(recordStatus == 4){//漏水传感器故障
                            title = "漏水传感器故障通知";
                            fontColor = 10;
                            msg = ConstantsIot.HYDRANT_LEAKAGE_FAULT;
                        }

                    }else if(sendType == 2){//消火栓超高超低
                        eventRoleType = ConstantsIot.HYDRANTMONITOR_PRESSURE_ABNORMALITY_EVENT;
                        title = "水压通知";
                        fontColor = 1;
                        if (waterDevice.getWaterTerminalId() > 0){//判断是否是系统端
                            if (recordStatus == 2) {//水压超高提醒
                                sendType = 2;
                                msg = ConstantsIot.WATER_SYS_MSG + presentValueMsg + ConstantsIot.HYDRANT_SUPER_HIGH_MSG;
                            } else if (recordStatus == 3) {//水压超低提醒
                                sendType = 3;
                                msg = ConstantsIot.WATER_SYS_MSG + presentValueMsg + ConstantsIot.HYDRANT_ULTRALOW_MSG;
                            }
                        }else{
                            if (recordStatus == 2) {//水压超高提醒
                                msg = ConstantsIot.WATER_SUPPLY_MSG + presentValueMsg + ConstantsIot.HYDRANT_SUPER_HIGH_MSG;
                                sendType = 2;
                            } else if (recordStatus == 3) {//水压超低提醒
                                sendType = 3;
                                msg = ConstantsIot.WATER_SUPPLY_MSG + presentValueMsg + ConstantsIot.HYDRANT_ULTRALOW_MSG;
                            }
                        }
                    }else if(sendType == 1){//消火栓低电压
                        eventRoleType = ConstantsIot.HYDRANTMONITOR_LACKOF_ELECTRICITY;
                        title = "低电量通知";
                        fontColor = 2;
                        msg = ConstantsIot.LOW_POWER_MSG;//电量超低提醒
                    }else if(sendType == 99){//消火栓离线恢复
                        title = "消火栓设备状态通知";
                        msg = ConstantsIot.HYDRANT_ONLINE_MSG;
                    }
                }

                String buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, device.getBuildingId(), "buildingName");
                String floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, device.getBuildingFloorId(), "floorName");
                String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, waterRecord.getProjectId(), "projectName");
                //组装要发送的信息内容
                String content = "【" + projectName + "】 " + buildingName + floorName + device.getPointLocation() +
                        device.getDeviceName() + device.getPointQrNo() + " 传感器ID:" + waterDevice.getSensorNo() + msg;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("projectName",projectName);
                jsonObject.put("buildingName",buildingName);
                jsonObject.put("floorName",floorName);
                jsonObject.put("deviceLocation",device.getPointLocation());
                jsonObject.put("deviceName",device.getDeviceName());
                jsonObject.put("qrNo",device.getQrNo());
                jsonObject.put("pointQrNo",device.getPointQrNo());
                jsonObject.put("event",msg);
                jsonObject.put("buildingId",device.getBuildingId());
                jsonObject.put("sensorNo",device.getSensorNo());
                jsonObject.put("date",DateUtils.getDateTimeString(waterRecord.getCreateTime()));
                //发送提醒信息
                messageService.saveMessageRole(deviceType, Arrays.asList(eventRoleType.split(",")), title,sendType,fontColor, content,
                        url , waterDevice.getProjectId(), waterRecord.getId(), null, jsonObject);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    // 麦克水压
    private WaterDevice waterGage(WaterDevice waterDevice) {
        if (waterDevice.getSendType() == 1) {
            waterDevice.setSendType(2);
            waterDevice.setSetUpdateStatus(1);// 状态
            waterDevice.setDataInterval(Integer.valueOf(ConstantsIot.iot_water_dataInterval));// 采样周期
            String waveAlarmValueStr = ConstantsIot.iot_water_waveAlarmValue;

            if ("".equals(waterDevice.getUnit())) {
                waveAlarmValueStr = ConstantsIot.iot_water_Liquid_waveAlarmValue;
            }
            Float waveAlarmValue = Float.valueOf(waveAlarmValueStr) * 100.00f;
            waterDevice.setWaveAlarmValue(Integer.valueOf(waveAlarmValue.intValue()));
            waterDevice.setHeartbeat(Integer.valueOf(ConstantsIot.iot_water_heartbeat));// 上发周期
            waterDevice.setAlarmMode(Integer.valueOf(ConstantsIot.iot_water_alarmMode));// 告警方式
            waterDevice.setUrl(ConstantsIot.iot_url);// 域名地址
            waterDevice.setPort(Integer.valueOf(ConstantsIot.iot_port));// 端口号
            return waterDevice;
        } else {
            return waterDevice;
        }
    }

    /*
     * 校验数据
     */
    private void checkData(JSONObject json) {
        boolean state = true;
        WaterDevice waterDevice = new WaterDevice();
        waterDevice.setSensorNo(json.getString("sensorId"));
        waterDevice = waterDeviceDao.findWaterDevice(waterDevice);
        String domainNameUrl = json.getString("domainNameUrl");
        Integer port = json.getInteger("port");
        //SysLog.info(domainNameUrl + "," + port + "-------端口-------->" + waterDevice.getUrl() + "," + waterDevice.getPort());
        Integer dataSendInterval = json.getInteger("dataSendInterval");
        String SIMCord = json.getString("simCard");
        if (!domainNameUrl.equals(waterDevice.getUrl())) {
            state = false;
        }
        if (!port.equals(waterDevice.getPort())) {
            state = false;
        }
        SysLog.info(dataSendInterval + "-------心跳上报周期-------->" + waterDevice.getHeartbeat());
        if (dataSendInterval != waterDevice.getHeartbeat()) { // 心跳上报周期
            state = false;
        }
        if (waterDevice.getType() != 3) { //水压比较
            Integer dataSamplingInterval = json.getInteger("dataSamplingInterval");
            Integer alarmMode = json.getInteger("alarmMode");
            Double upperLimit = json.getDouble("upperLimit") * 100;
            Integer maxValue = Integer.valueOf(upperLimit.intValue());
            Double lowerLimit = json.getDouble("lowerLimit") * 100;
            Integer minValue = Integer.valueOf(lowerLimit.intValue());

            Double fluctuationAlarmValue = json.getDouble("fluctuationAlarmValue") * 100;
            Integer waveAlarmValue = Integer.valueOf(fluctuationAlarmValue.intValue());
            SysLog.info(dataSamplingInterval + "-------采样周期比较-------->" + waterDevice.getDataInterval());
            if (dataSamplingInterval != waterDevice.getDataInterval()) { // 采样周期比较
                state = false;
            }
            SysLog.info(alarmMode + "-------告警方式-------->" + waterDevice.getAlarmMode());
            if (alarmMode != waterDevice.getAlarmMode()) { // 告警方式
                state = false;
            }
            SysLog.info(maxValue + "-------上限-------->" + waterDevice.getMaxValue());
            if (maxValue != waterDevice.getMaxValue()) { // 上限
                state = false;
            }
            SysLog.info(minValue + "-------下限-------->" + waterDevice.getMinValue());
            if (minValue != waterDevice.getMinValue()) { // 下限
                state = false;
            }
            SysLog.info(waveAlarmValue + "-------波动-------->" + waterDevice.getWaveAlarmValue());
            if (waveAlarmValue != waterDevice.getWaveAlarmValue()) { // 波动
                state = false;
            }
        }
        if (!state) {//没有更改成功
            waterDevice.setSetUpdateStatus(3);
            SysLog.info("设备值没有修改成功");
        } else {
            SysLog.info("设备值修改成功");
            waterDevice.setSetUpdateStatus(4);
            waterDevice.setStatusUpdateTime(new Date());
        }
        waterDevice.setSimCard(SIMCord);
        waterDevice.setWaterTerminalId(null);
        waterDeviceDao.modifyWaterDevice(waterDevice);
    }

    /*
     * 返回设置参数
     */
    private String waterDevice(String sensorNo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = formatter.format(new Date());
        WaterDevice waterDevice = new WaterDevice();
        waterDevice.setSensorNo(sensorNo);
        waterDevice = waterDeviceDao.findWaterDevice(waterDevice);
        SysLog.info(JSONObject.toJSONString(waterDevice)+"------------》设备参数");
        StringBuffer msg = new StringBuffer();
        String url = waterDevice.getUrl();
        Integer port = waterDevice.getPort();
        if(waterDevice.getType() != null && waterDevice.getType() == 8 && StringUtils.isNotEmpty(sensorNo) && sensorNo.startsWith("C")){
            //  heartBeat测量速度：0~9， 0表示20秒测量一次，其他数字n表示n秒测量一次
            //  dataInterval上报频率：1每天1次, 2每天2次, 3每天4次，4每3小时1次, 5每两小时1次, 6每1小时1次, 7每半小时1次
            //  waveAlarmValue引发上报的压力波动值 范围000~255
            //  maxValue压力上限,minValue压力下限
            //加5判断是否是修改终端参数的命令
            Integer state = 5 + waterDevice.getValueUpdateStatus();
            int setUpdateStatus = waterDevice.getSetUpdateStatus();
            Integer heartBeat = waterDevice.getHeartbeat();
            Integer dataInterval = waterDevice.getDataInterval();
            Integer waveAlarmValue = waterDevice.getWaveAlarmValue();
            //state 等于6||8表示是修改终端参数的命令
            if(setUpdateStatus != 1 && setUpdateStatus != 3 && (state == 6 || state == 8) && (heartBeat != null && dataInterval != null && waveAlarmValue != null)){
                //修改终端参数的命令
                Integer maxValue = waterDevice.getMaxValue();
                Integer minValue = waterDevice.getMinValue();
                String maxStr = getValueStr(maxValue + "", 1);
                String minStr = getValueStr(minValue + "", 1);
                String waveValue = getValueStr(waveAlarmValue + "", 0);
                String str = "{C:" + dataInterval + heartBeat + "P" + waveValue +"H" + maxStr + "L" + minStr +" "
                        + "C:" + dataInterval + heartBeat + "P" + waveValue +"H" + maxStr + "L" + minStr +"}";
                return str;
            }else if((setUpdateStatus == 1 || setUpdateStatus == 3) && StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(port + "")){
                //修改终端上网的IP地址及端口
                return "{I:\"" + url + "\",\"" + port + "\" I:\"" + url + "\",\"" + port + "\"}";
            }
        }else{
            msg.append("[#SET;");
            msg.append(waterDevice.getSensorNo() + ";");
            msg.append((StringUtils.isEmpty(url) ? ConstantsIot.iot_url: waterDevice.getUrl() ) + ",");
            msg.append((port == null ?  ConstantsIot.iot_port: waterDevice.getPort() ) + ";");
            msg.append(dateStr + ";");
            msg.append((waterDevice.getDataInterval() == null ? ConstantsIot.iot_water_dataInterval: waterDevice.getDataInterval() ) + ";");
            msg.append((waterDevice.getHeartbeat()== null ? ConstantsIot.iot_water_heartbeat: waterDevice.getHeartbeat() )+ ";");
            msg.append((waterDevice.getAlarmMode()== null ? ConstantsIot.iot_water_alarmMode: waterDevice.getAlarmMode())+ ";");
            msg.append((waterDevice.getMaxValue() == null ? 0 : waterDevice.getMaxValue()) / 100.00f + ";");
            msg.append((waterDevice.getMinValue() == null ? 0 : waterDevice.getMinValue()) / 100.00f + ";");
            msg.append((waterDevice.getWaveAlarmValue() == null ? 0 : waterDevice.getWaveAlarmValue()) / 100.00f+ ";SET_END]");
            SysLog.info(msg.toString()+"----------》返回参数");
        }
        return msg.toString();
    }

    private String getValueStr(String  value,Integer type){
        String str = "000";
        if(type == 1){
            str = "0000";
        }
        if(StringUtils.isNotEmpty(value)){
            if(type == 1){
                if(value.length() < 4){
                    str = str.substring(0,4 - value.length()) + value;
                }else{
                    str = value;
                }
            }else{
                if(value.length() < 3){
                    str = str.substring(0,3 - value.length()) + value;
                }else{
                    str = value;
                }
            }
        }
        return str;
    }

    // 麦克水浸
    private WaterDevice waterImmersion(WaterDevice waterDevice) {
        if (waterDevice.getSendType() == 1) {
            waterDevice.setSendType(2);
            waterDevice.setSetUpdateStatus(1);// 状态
            waterDevice.setDataInterval(Integer.valueOf(ConstantsIot.iot_water_dataInterval));// 采样周期
            waterDevice.setWaveAlarmValue(Integer.valueOf(ConstantsIot.iot_water_waveAlarmValue));
            waterDevice.setHeartbeat(Integer.valueOf(ConstantsIot.iot_water_heartbeat));// 上发周期
            waterDevice.setAlarmMode(Integer.valueOf(ConstantsIot.iot_water_alarmMode));// 告警方式
            waterDevice.setUrl(ConstantsIot.iot_url);// 域名地址
            waterDevice.setPort(Integer.valueOf(ConstantsIot.iot_port));// 端口号
        }
        return waterDevice;
    }
}
