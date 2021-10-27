package com.xjt.cloud.iot.core.service.impl.electrical;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireEventDao;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireEventRecordDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireDevice;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEventRecord;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventRecordService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendElectricalFireMsgService;
import onenet.datapush.receiver.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 电气火灾事件记录
 *
 * @author dwt
 * @date 2020-09-18 11:06
 */
@Service
public class ElectricalFireEventRecordServiceImpl extends AbstractService implements ElectricalFireEventRecordService {

    @Autowired
    private ElectricalFireDeviceDao electricalDeviceDao;
    @Autowired
    private ElectricalFireEventRecordDao eventRecordDao;
    @Autowired
    private ElectricalFireEventDao eventDao;
    @Autowired
    private SendElectricalFireMsgService messageService;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private LinkageControlService linkageControlService;

    @Override
    public Data receiveMobileIotInformation(HttpServletRequest request, String msg, String nonce, String signature) {
        InputStream in;
        String body = "";
        try {
            in = request.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int iRead;
            while ((iRead = buf.read(buffer)) != -1) {
                body += new String(buffer, 0, iRead, "UTF-8");
            }
            in.close();
            buf.close();
        } catch (IOException e) {
            SysLog.error(e);
            e.printStackTrace();
        }

        /*
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         */
        // 明文模式  start
        SysLog.info("receiveMobileIotInformation --> data body :--- " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        String value = jsonObject.get("msg").toString();
        jsonObject = JSONObject.parseObject(value);
        value = jsonObject.getString("value");
        String imei = jsonObject.get("imei").toString();
        ElectricalFireDevice electricalDevice = new ElectricalFireDevice();
        electricalDevice.setSensorNo(imei);
        electricalDevice = electricalDeviceDao.findElectricalFireDevice(electricalDevice);
        if (electricalDevice != null && StringUtils.isNotEmpty(value) && value.length() >= 300) {
            Float leakageCurrValue = Float.parseFloat(electricalDevice.getLeakageCurrValue()) * 10;
            Float tempValue = Float.parseFloat(electricalDevice.getTempValue());
           /* //设备编码
            String deviceCode = value.substring(2,14);
            //相电压Ua(0.1V)
            String ua = value.substring(42,46);
            //线电压Uca(0.1V)
            String uca = value.substring(46,50);
            //A相电流(mA)
            String currentA = value.substring(50,54);
            //相电压Ub(0.1V)
            String ub = value.substring(74,78);
            //线电压Uab(0.1V)
            String uab = value.substring(78,82);
            //B相电流(mA)
            String currentB = value.substring(82,86);
            //相电压Uc(0.1V)
            String uc = value.substring(106,110);
            //线电压Ubc(0.1V)
            String ubc = value.substring(110,114);
            // C相电流(mA)
            String currentC = value.substring(114,118);
            //三相平均相电压(0.1V)
            String threePhaseAveragePhaseVoltage = value.substring(138,142);
            //三相平均线电压(0.1V)
            String threePhaseAverageLineVoltage = value.substring(142,146);
            //三相平均相电流(mA)
            String threePhaseAveragePhaseCurrent = value.substring(146,150);
            */
            //漏电流(mA)
            Float leakageCurrent = Float.parseFloat(StringUtils.hexStringToAlgorism(value.substring(206, 210)) + "");
            //温度1(0.1度)
            Float temperature1 = Float.parseFloat(StringUtils.hexStringToAlgorism(value.substring(210, 214)) + "");
            //温度2(0.1度)
            Float temperature2 = Float.parseFloat(StringUtils.hexStringToAlgorism(value.substring(214, 218)) + "");
            //温度3(0.1度)
            Float temperature3 = Float.parseFloat(StringUtils.hexStringToAlgorism(value.substring(218, 222)) + "");
            //温度4(0.1度)
            Float temperature4 = Float.parseFloat(StringUtils.hexStringToAlgorism(value.substring(222, 226)) + "");
            ElectricalFireEventRecord eventRecord = new ElectricalFireEventRecord(electricalDevice);
            eventRecord.setLeakageCurrent1(leakageCurrent + "");
            eventRecord.setTemperature1(temperature1 + "");
            eventRecord.setTemperature2(temperature2 + "");
            eventRecord.setTemperature3(temperature3 + "");
            eventRecord.setTemperature4(temperature4 + "");
            eventRecord.setRawData(body);
            int num = eventRecordDao.saveElectricalFireEventRecord(eventRecord);
            if (num > 0) {
                String sign = "electrical_leakage_event";
                //事件类型 1漏电流报警，2温度报警，3故障，4离线
                if (leakageCurrent > leakageCurrValue) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 1, 1, 0, leakageCurrent + "", sign);
                }
                sign = "electrical_temp_event";
                if (temperature1 > tempValue) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 2, 1, 0, temperature1 + "", sign);
                }
                if (temperature2 > tempValue) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 2, 2, 0, temperature2 + "", sign);
                }
                if (temperature3 > tempValue) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 2, 3, 0, temperature3 + "", sign);
                }
                if (temperature4 > tempValue) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 2, 4, 0, temperature4 + "", sign);
                }
            }

            // 故障信息(附录2)
            String faultMsg = value.substring(246, 250);
            num = StringUtils.hexStringToAlgorism(faultMsg);
            electricalDevice.setWorkStatus(1);
            List<ElectricalFireEvent> eventList;
            ElectricalFireEvent fireEvent = new ElectricalFireEvent();
            fireEvent.setSensorNo(imei);
            fireEvent.setRecoverStatus(2);
            fireEvent.setEventType(4);
            eventList = eventDao.findElectricalFireEventList(fireEvent);
            Device device = new Device();
            if (electricalDevice.getSendMessage() == 1) {
                electricalDevice.setSendMessage(2);
            }

            if (num == 0) {
                // 更新设备状态为正常，该设备所有故障事件恢复
                electricalDevice.setDeviceStatus(1);
                electricalDeviceDao.modifyElectricalFireDeviceStatus(electricalDevice);
            } else {
                // 更新设备状态为故障
                electricalDevice.setDeviceStatus(2);
                electricalDeviceDao.modifyElectricalFireDeviceStatus(electricalDevice);
            }

            device.setId(electricalDevice.getDeviceId());
            device.setIotStatus(1);
            deviceDao.modifyDeviceIotStatus(device);

            if (eventList != null && eventList.size() > 0) {
                fireEvent.setRecoverStatus(1);
                fireEvent.setRecoverRecordId(eventRecord.getId());
                eventDao.recoverEventStatus(fireEvent);
                fireEvent.setRecoverStatus(2);
                fireEvent.setRecoverRecordId(null);
                messageService.sendFireMessage(eventList.get(0), electricalDevice, 7);
            }

            faultMsg = Integer.toBinaryString(StringUtils.hexStringToAlgorism(faultMsg));
            if (StringUtils.isNotEmpty(faultMsg) && faultMsg.length() >= 2) {
                StringBuilder sBuff1 = new StringBuilder(faultMsg);
                sBuff1.reverse();
                faultMsg = sBuff1.toString();
            }

            String str;
            int channel;
            int faultType;
            fireEvent.setEventType(3);
            int recoverEvent;
            String sign;
            for (int i = 1; i < 6; i++) {
                /*if (i == 6) {
                    // 进不来
                    break;
                }*/
                if (i == 1) {
                    channel = 1;
                    faultType = 1;
                    recoverEvent = 5;
                    sign = "electrical_leakage_fault_event";
                } else {
                    channel = i - 1;
                    faultType = 2;
                    recoverEvent = 6;
                    sign = "electrical_temp_fault_event";
                }
                fireEvent.setChannel(channel);
                fireEvent.setFaultType(faultType);
                if (faultMsg.length() >= i) {
                    str = faultMsg.substring(i - 1, i);
                } else {
                    str = "0";
                }
                eventList = eventDao.findElectricalFireEventList(fireEvent);
                if ("1".equals(str) && eventList == null) {
                    saveElectricalFireEvent(electricalDevice, eventRecord, 3, channel, faultType, null, sign);
                } else {
                    if (eventList != null && eventList.size() > 0) {
                        fireEvent.setRecoverStatus(1);
                        fireEvent.setRecoverRecordId(eventRecord.getId());
                        eventDao.recoverEventStatus(fireEvent);
                        fireEvent.setRecoverRecordId(null);
                        fireEvent.setRecoverStatus(2);
                        messageService.sendFireMessage(eventList.get(0), electricalDevice, recoverEvent);
                    }
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 保存电气火灾报警事件
     *
     * @param device      ElectricalFireDevice
     * @param eventRecord ElectricalFireEventRecord
     * @param eventType   eventType
     * @param channel     channel
     * @param faultType   faultType
     * @param alarmValue  alarmValue
     * @param sign        sign
     */
    private void saveElectricalFireEvent(ElectricalFireDevice device, ElectricalFireEventRecord eventRecord,
                                         Integer eventType, Integer channel, Integer faultType, String alarmValue, String sign) {

        ElectricalFireEvent event = new ElectricalFireEvent();
        event.setProjectId(eventRecord.getProjectId());
        event.setChannel(channel);
        event.setEventType(eventType);
        event.setFaultType(faultType);
        event.setRecordId(eventRecord.getId());
        event.setRecoverStatus(2);
        event.setSensorNo(eventRecord.getSensorNo());
        event.setAlarmDeviceId(eventRecord.getAlarmDeviceId());
        event.setDeviceId(eventRecord.getDeviceId());
        event.setDeviceLocation(device.getDeviceLocation());
        event.setPointQrNo(device.getPointQrNo());
        event.setDeviceQrNo(device.getDeviceQrNo());
        event.setCheckPointId(device.getCheckPointId());
        // 事件类型 1漏电流报警，2温度报警，3故障，4离线
        if (eventType == 1) {
            event.setLeakageAlarmValue(alarmValue);
            event.setLeakageCurrValue(device.getLeakageCurrValue());
        } else if (eventType == 2) {
            event.setTempValue(device.getTempValue());
            event.setTempAlarmValue(alarmValue);
        }
        event.setDeviceName(eventRecord.getDeviceName());
        eventDao.saveElectricalFireEvent(event);
        messageService.sendFireMessage(event, device, 0);

        if (StringUtils.isNotEmpty(sign)) {
            // 查询是否关联动设备,有关联的话联动设备报警
            linkageControlService.deviceFault(device.getDeviceId(), sign);
        }
    }

    /**
     * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
     *
     * @param msg       验证消息
     * @param nonce     随机串
     * @param signature 签名
     * @return msg值
     * @author huanggc
     * @date 2020/07/20
     */
    @Override
    public String check(String msg, String nonce, String signature) {
        SysLog.info("check = url&token check: msg:{" + msg + "} nonce{" + nonce + "} signature:{ " + signature + "}");
        try {
            if (Util.checkToken(msg, nonce, signature, ConstantsIot.ELECTRICAL_FIRE_ONE_NET_TOKEN)) {
                return msg;
            }
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
            SysLog.info("UnsupportedEncodingException = " + e);
        }
        return "error";
    }

    /**
     * 查询电气火灾设备事件记录数据分析
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 14:29
     */
    @Override
    public Data findDeviceEventRecordList(String json) {
        ElectricalFireEventRecord eventRecord = JSONObject.parseObject(json, ElectricalFireEventRecord.class);
        Date endTime = eventRecord.getEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DAY_OF_MONTH, 1);
            eventRecord.setEndTime(c.getTime());
        }
        List<ElectricalFireEventRecord> eventRecords = eventRecordDao.findDeviceEventRecordList(eventRecord);
        return asseData(eventRecords);
    }

    /**
     * 查询电气火灾设备最新事件记录
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-29 14:30
     */
    @Override
    public Data findNewestEventRecord(String json) {
        ElectricalFireEventRecord eventRecord = JSONObject.parseObject(json, ElectricalFireEventRecord.class);
        eventRecord = eventRecordDao.findNewestEventRecord(eventRecord);
        return asseData(eventRecord);
    }

    /**
     * netty 电气火灾消息(有:天津合极, )
     * 保存记录
     *
     * @param json JSONObject
     * @return List<JSONObject>
     * @author huanggc
     * @date 2021/05/17
     */
    @Override
    public List<JSONObject> electricalFireEventRecordServiceSys(JSONObject json) {
        try {
            // 待完善:电气火灾事件表,设备ID,项目ID......
            ElectricalFireEventRecord eventRecord = JSONObject.parseObject(json.toJSONString(), ElectricalFireEventRecord.class);

            eventRecordDao.saveElectricalFireEventRecord(eventRecord);
        } catch (Exception e) {
            SysLog.error(e);
        }

        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("msg", 200);
        jsonObject.put("iotType", ConstantsIot.ELECTRICAL_FIRE);
        WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        jsonObject.remove("msg");

        List<JSONObject> jsonList = new ArrayList<>(3);
        jsonList.add(jsonObject);
        jsonList.add(jsonObject);
        jsonList.add(jsonObject);
        return jsonList;
    }
}
