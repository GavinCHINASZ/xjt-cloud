package com.xjt.cloud.iot.core.service.impl.gateway;

import com.serotonin.modbus4j.ip.listener.TcpMultiListener;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ModbusUtils;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmEventDao;
import com.xjt.cloud.iot.core.dao.iot.fire.FireAlarmRecordDao;
import com.xjt.cloud.iot.core.dao.iot.gateway.FireGatewayDao;
import com.xjt.cloud.iot.core.dao.iot.gateway.FirePointTableDao;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmDevice;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmEvent;
import com.xjt.cloud.iot.core.entity.fire.FireAlarmRecord;
import com.xjt.cloud.iot.core.entity.gateway.FireGateway;
import com.xjt.cloud.iot.core.entity.gateway.FirePointTable;
import com.xjt.cloud.iot.core.service.service.gateway.FireGatewayService;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendFireMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName FireGatewayServiceImpl
 * @Author dwt
 * @Date 2019-11-29 9:07
 * @Version 1.0
 */
@Service
public class FireGatewayServiceImpl implements FireGatewayService {

    @Autowired
    private FireGatewayDao fireGatewayDao;
    @Autowired
    private FirePointTableDao firePointTableDao;
    @Autowired
    private FireAlarmDeviceDao fireAlarmDeviceDao;
    @Autowired
    private FireAlarmEventDao fireAlarmEventDao;
    @Autowired
    private FireAlarmRecordDao fireAlarmRecordDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SendFireMessageService sendFireMessageService;
    private static ExecutorService cachedThreadPool;
    private static int[] digits = {1, 2, 4, 8, 10, 20, 40, 80, 100, 200, 400, 800, 1000, 2000, 4000, 8000};

    static {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-11-29 9:21
     * @Param:
     * @Return: Data
     * @Description 定时任务读取网关列表, 多线程读取火警主机地址数据
     */
    @Override
    public Data readFireGateway(TcpMultiListener listener) {
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        List<String> sList = fireGatewayDao.findGatewayList();
        if (sList != null && sList.size() > 0) {
            for (String gateWay : sList) {
                cachedThreadPool.submit(() -> readModbusFireGateway(gateWay, listener));
            }
        }
        return Data.isSuccess();
    }

    /**
     * @Author: dwt
     * @Date: 2019-11-29 9:34
     * @Param: java.lang.String
     * @Return: void
     * @Description 读取modbus火警主机网关
     */
    private void readModbusFireGateway(String gateway, TcpMultiListener listener) {
        List<FireGateway> list = fireGatewayDao.findFireGatewayList(gateway);
        SysLog.info("gateway: " + "gateway  " + "List<String> list.size() :" + list.size() + "!!!!!!!!!!!!!!!!!!!!!!!");
        if (list.size() <= 0) {
            return;
        }
        String reg;
        FirePointTable firePointTable = new FirePointTable();
        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
        List<FirePointTable> pList = new ArrayList<>();
        int addressCount;
        String registerCode;
        short[] s;
        //初始地址
        int a;
        int num;
        //读取次数
        int length;
        //读取数量
        int account;
        String transDeviceId = "";
        Long projectId;
        for (FireGateway entity : list) {
            addressCount = entity.getAddressAccount();
            projectId = entity.getProjectId();
            registerCode = entity.getRegisterCode();
            if (addressCount > 125) {
                num = addressCount % 125;//求余数
                if (num == 0) {
                    length = addressCount / 125;
                } else {
                    length = addressCount / 125 + 1;//不能整除商加1
                }
            } else {
                length = 1;
                num = addressCount;
            }
            for (int n = 1; n <= length; n++) {
                if (n == length && num != 0) {
                    a = entity.getStartAddress() + addressCount - num;
                    account = num;
                } else {
                    a = entity.getStartAddress() + 125 * (n - 1);
                    account = 125;
                }
                s = ModbusUtils.readHoldingRegister(listener, entity.getSlaveId(), a, account, registerCode);
                SysLog.info("short[] s :" + Arrays.toString(s) + "!!!!!!!!!!!!!!!!!!!!!!!");
                if (s != null && s.length > 0) {
                    reg = parseModbusData(s, projectId, gateway, firePointTable,
                            entity, pList, fireAlarmEvent, a, transDeviceId);
                    if (StringUtils.isEmpty(transDeviceId)) {
                        transDeviceId = reg;
                    }
                }
            }
        }
        if ("".equals(transDeviceId)) {
            return;
        }
        FireAlarmDevice fireAlarm = new FireAlarmDevice();
        fireAlarm.setTransDeviceId(transDeviceId);
        fireAlarm = fireAlarmDeviceDao.findFireAlarmDevice(fireAlarm);
        if (fireAlarm != null) {
            List<FireAlarmEvent> deviceList = fireAlarmEventDao.findFireEventByFrom(transDeviceId);
            if (deviceList != null && deviceList.size() > 0) {
                for (FireAlarmEvent event : deviceList) {
                    sendFireMessageService.sendFireMessage(event, 8);
                }
            }

            fireAlarm.setTransDeviceId(transDeviceId);
            int fireAlarmStatus = 0;
            int faultStatus = 0;
            int eventType;
            if (CollectionUtils.isNotEmpty(pList)) {
                for (FirePointTable pEntity : pList) {
                    eventType = pEntity.getFireEventType();
                    if (eventType == 17) {//判断火警状态是否正常
                        fireAlarmStatus = 1;
                    } else if (eventType == 1) {//判断火警状态是否是异常
                        fireAlarmStatus = 2;
                    }
                    if (eventType == 6) {//判断故障状态是否正常
                        faultStatus = 1;
                    } else if (eventType == 7 || eventType == 14 || eventType == 15 || eventType == 16) {//判断故障状态是否正常
                        faultStatus = 2;
                    }
                }
            }

            fireAlarm.setFireAlarmStatus(fireAlarmStatus);
            fireAlarm.setFaultStatus(faultStatus);
            fireAlarmDeviceDao.modifyHeartTime(fireAlarm);
            fireAlarmEventDao.modifyOffLineEventStatus(transDeviceId);
        }
        if (pList.size() > 0) {
            saveFirePointTableEntity(pList, fireAlarm);
            //fireKeyValueDao.saveFireKeyValue(keyList);
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-12-09 16:31
     * @Param:
     * @Return:
     * @Description 解析modbus数据
     */
    private String parseModbusData(short[] s, Long projectId, String gateway,
                                   FirePointTable firePointTable, FireGateway entity, List<FirePointTable> pList,
                                   FireAlarmEvent fireAlarmEvent, int a, String transDeviceId) {
        if (s != null && s.length > 0) {
            int aM;//处理address
            for (int i = 0; i < s.length; i++) {
                aM = i + a;
                if (s[i] != 0) {
                    packageFaultEvent(s, i, projectId, gateway, aM,
                            firePointTable, entity, pList, fireAlarmEvent);
                } else {
                    removeGatewayCache(entity, aM, fireAlarmEvent, "0");
                }
            }
            transDeviceId = entity.getSlaveId() + "";
        }
        return transDeviceId;
    }

    /**
     * @Author: dwt
     * @Date: 2019-12-09 10:39
     * @Param:
     * @Return:
     * @Description 封装故障信息
     */
    private void packageFaultEvent(short[] s, int i, Long projectId, String gateway, int aM,
                                   FirePointTable firePointTable, FireGateway entity,
                                   List<FirePointTable> pList, FireAlarmEvent fireAlarmEvent) {
        String str = Integer.toBinaryString(s[i] & 65535);
        if (str.length() <= 16) {
            for (int j = 0; j < str.length(); j++) {
                //处理digits数组取值
                int digit = digits[str.length() - j - 1];
                if ("1".equals(str.charAt(j) + "")) {
                    String key = redisUtils.getString(projectId + gateway + aM + digit);
                    if (StringUtils.isEmpty(key)) {
                        packageData(firePointTable, aM, digit, gateway, entity, pList, projectId, str);
                    }
                } else {
                    removeRecoverEvent(entity, aM, digit, fireAlarmEvent, str);
                }
            }
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-12-09 10:27
     * @Param:
     * @Return:
     * @Description 清除恢复正常的故障缓存
     */
    private void removeGatewayCache(FireGateway entity, int aM, FireAlarmEvent fireAlarmEvent, String str) {
        for (int m = 0; m < 16; m++) {
            removeRecoverEvent(entity, aM, digits[m], fireAlarmEvent, str);
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-12-04 10:56
     * @Param:
     * @Return:
     * @Description 封装键值和编码表信息
     */
    private void packageData(FirePointTable firePointTable, int aM, int digit, String gateway,
                             FireGateway entity, List<FirePointTable> pList, Long projectId, String str) {
        firePointTable.setAddress(aM);
        firePointTable.setDigit(digit);
        firePointTable.setGateway(gateway);
        firePointTable.setProjectId(entity.getProjectId());
        FirePointTable pEntity = firePointTableDao.findFirePointTable(firePointTable);
        if (pEntity != null) {
            //keyList.add(getKeyValueEntity(aM + "" + digit,projectId + gateway, pEntity.getId()));
            redisUtils.set(projectId + gateway + aM + digit, pEntity.getId());
            pEntity.setSlaveId(entity.getSlaveId() + "");
            pEntity.setProjectId(entity.getProjectId());
            pEntity.setBinaryString(str);
            pList.add(pEntity);
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-11-29 16:09
     * @Param:
     * @Return:
     * @Description 保存网关记录和事件
     */
    private void saveFirePointTableEntity(List<FirePointTable> pList, FireAlarmDevice fireAlarm) {
        FireAlarmRecord record = new FireAlarmRecord();
        FireAlarmEvent event;
        FireAlarmEvent fireAlarmEvent;
        int messageType;
        for (FirePointTable pEntity : pList) {
            record.setId(null);
            record.setDeviceName(pEntity.getDeviceName());
            record.setRecordPosition(pEntity.getPosition());
            record.setPositionCode(pEntity.getAddress() + "-" + pEntity.getDigit());
            record.setFireAlarmNo(pEntity.getFireAlarmNo());
            record.setTransDeviceId(fireAlarm.getTransDeviceId());
            record.setRawData(pEntity.getAddress() + "-" + pEntity.getDigit() + "-" + pEntity.getBinaryString());
            record.setEventType(pEntity.getFireEventType());
            record.setDeviceCode(pEntity.getGateway());
            record.setProjectId(fireAlarm.getProjectId());
            record.setDeviceId(fireAlarm.getDeviceId());
            record.setFireAlarmDeviceId(fireAlarm.getId());
            fireAlarmEvent = fireAlarmEventDao.findEventByRecord(record);
            if (fireAlarmEvent == null) {
                fireAlarmRecordDao.saveFireAlarmRecord(record);
                event = new FireAlarmEvent(record);
                event.setRecoverStatus(2);
                event.setTransDeviceName(fireAlarm.getTransDeviceName());
                fireAlarmEventDao.saveFireAlarmEvent(event);
                messageType = sendFireMessageService.recoverEventStatus(event.getEventType(), 2);
                sendFireMessageService.sendFireMessage(event, messageType);
            } else {
                redisUtils.set(fireAlarm.getProjectId() + pEntity.getGateway() + pEntity.getAddress()
                        + pEntity.getDigit(), pEntity.getId());
            }
        }
    }

    /**
     * @Author: dwt
     * @Date: 2019-11-29 15:13
     * @Param:
     * @Return:
     * @Description 恢复对应事件状态
     */
    private void removeRecoverEvent(FireGateway entity, Integer aM, int m, FireAlarmEvent fireAlarmEvent, String str) {
        //Long id = map.get(entity.getGateway()+aM+digits[m]);
        String key = redisUtils.getString(entity.getProjectId() + entity.getGateway() + aM + m);
        if (StringUtils.isNotEmpty(key)) {
            FirePointTable firePointTable = firePointTableDao.findFirePointTableById(Long.parseLong(key));
            if (firePointTable != null) {
                fireAlarmEvent.setFireAlarmNo(firePointTable.getFireAlarmNo() + "");
                fireAlarmEvent.setTransDeviceId(entity.getRegisterCode() + "");
                fireAlarmEvent.setAlarmPosition(firePointTable.getPosition());
                fireAlarmEvent.setAlarmDeviceName(firePointTable.getDeviceName());
                fireAlarmEvent.setEventType(firePointTable.getFireEventType());
                fireAlarmEvent.setProjectId(entity.getProjectId());
                FireAlarmEvent event = fireAlarmEventDao.findGatewayEvent(fireAlarmEvent);
                if (event != null) {
                    FireAlarmRecord record = new FireAlarmRecord();
                    record.setDeviceName(firePointTable.getDeviceName());
                    record.setRecordPosition(firePointTable.getPosition());
                    record.setFireAlarmNo(event.getFireAlarmNo());
                    record.setTransDeviceId(event.getTransDeviceId());
                    record.setRawData(firePointTable.getGateway() + "-" + aM + "-" + m + "-" + str);
                    record.setEventType(event.getEventType());
                    record.setDeviceCode(firePointTable.getGateway());
                    record.setProjectId(entity.getProjectId());
                    fireAlarmRecordDao.saveFireAlarmRecord(record);
                    event.setRecoverStatus(1);
                    event.setRecoverRecordId(record.getId());
                    fireAlarmEventDao.modifyFireAlarmEventRecoverStatus(event);
                    Integer eventType = sendFireMessageService.recoverEventStatus(event.getEventType(), 1);
                    sendFireMessageService.sendFireMessage(event, eventType);
                }
            }
            redisUtils.del(entity.getProjectId() + entity.getGateway() + aM + m);
        }
    }

}
