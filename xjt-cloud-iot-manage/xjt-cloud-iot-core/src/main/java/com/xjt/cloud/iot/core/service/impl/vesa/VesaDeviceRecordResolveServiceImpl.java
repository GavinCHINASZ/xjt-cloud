package com.xjt.cloud.iot.core.service.impl.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaAreaTypeDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.vesa.VesaAreaType;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordResolveService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 极早期备记录解析接口
 *
 * @author wangzhiwen
 * @date 2019/10/23 10:00
 */
@Service
public class VesaDeviceRecordResolveServiceImpl extends AbstractService implements VesaDeviceRecordResolveService {
    @Autowired
    private VesaDeviceDao vesaDeviceDao;
    @Autowired
    private VesaDeviceRecordDao vesaDeviceRecordDao;
    @Autowired
    private LinkageControlService linkageControlService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private VesaAreaTypeDao vesaAreaTypeDao;
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 功能描述: 极早期设备上传信息处理接口
     *
     * @param jsonObject JSONObject
     * @return JSONObject
     * @author wangzhiwen
     * @date 2019/9/27 15:24
     */
    @Override
    public List<JSONObject> vesaDeviceDataAccess(JSONObject jsonObject) {
        List<JSONObject> jsonList = new ArrayList<>();
        return jsonList;
    }

    /**
     * 功能描述: 极早期设备上传信息参数解析
     *
     * @param json JSONObject
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/10/23 13:49
     */
    @Override
    public List<JSONObject> vesaSys(JSONObject json) {
        String sensorNo = json.getString("from");
        String msgType = json.getString("msgType");
        String sourceData = json.getString("sourceData");

        List<JSONObject> jsonList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonList.add(jsonObject);

        vesaDataAnalyze(jsonList, 0, sensorNo, sourceData, msgType, json);

        String msg = "[#end]";
        jsonObject.put("writeBackMsg", msg);
        jsonObject.put("iotType", ConstantsIot.VESA);
        jsonList.add(0, jsonObject);

        return jsonList;

    }

    /**
     * vesaDataAnalyze 极早期设备上报数据解析
     *
     * @return int
     * @Param: [state, sensorNo, sourceData, msgType, map]
     * @Author: zhangZaiFa
     * @date 2019/10/22 14:09
     **/
    private int vesaDataAnalyze(List<JSONObject> jsonList, int state, String sensorNo, String sourceData, String msgType, JSONObject json) {
        int eventType = 0;
        int recordType = 0;
        boolean heartBeat = FALSE;
        String loopName = null;
        String detector = null;
        String detectorType = null;
        String faultNo = null;
        String faultDesc = null;
        String position = null;

        Date date = new Date();
        VesaDevice vesaDevice = new VesaDevice();
        vesaDevice.setSensorNo(sensorNo);
        vesaDevice = vesaDeviceDao.findVesaDeviceAndBuildingId(vesaDevice);
        if (vesaDevice == null) {
            SysLog.info(sensorNo + "未绑定设备");
            return -1;
        }

        VesaRecord vesaRecord = new VesaRecord();
        vesaRecord.setSensorNo(sensorNo);
        vesaRecord.setProjectId(vesaDevice.getProjectId());
        vesaRecord.setSensorName(vesaDevice.getSensorName());
        vesaRecord.setDeviceId(vesaDevice.getDeviceId());
        vesaRecord.setQrNo(vesaDevice.getQrNo());
        vesaRecord.setDeviceSysName(vesaDevice.getDeviceSysName());
        vesaRecord.setVesaId(vesaDevice.getId());
        vesaRecord.setRawData(sourceData);
        vesaRecord.setCreateTime(date);

        vesaDevice.setDeviceStatus(1);//设备在线
        vesaDevice.setEndHeartbeatTime(date);//最后心跳时间
        vesaDevice.setStatusUpdateTime(date);

        if (StringUtils.isNotEmpty(json.getString("heartbeat")) && json.getBooleanValue("heartbeat") == TRUE) {
            eventType = 5;//生产离线事件
            recordType = 1; //记录是恢复类型
            vesaRecord.setEventType(eventType);
            vesaRecord.setRecordType(recordType);
        } else {
            if (StringUtils.isNotEmpty(json.getString("eventType"))) {
                eventType = json.getIntValue("eventType");
            }
            if (StringUtils.isNotEmpty(json.getString("recordType"))) {
                recordType = json.getIntValue("recordType");
            }
            if (StringUtils.isNotEmpty(json.getString("loopName"))) {
                loopName = json.getString("loopName");
            }
            if (StringUtils.isNotEmpty(json.getString("slaveId"))) {
                detector = json.getString("slaveId");
            }
            if (StringUtils.isNotEmpty(json.getString("sector"))) {
                detector = detector + "." + json.getString("sector");
            }
            if (StringUtils.isNotEmpty(json.getString("detectorType"))) {
                detectorType = json.getString("detectorType");
            }
            if (StringUtils.isNotEmpty(json.getString("faultDescribe"))) {
                faultDesc = json.getString("faultDescribe");
            }
            if (StringUtils.isNotEmpty(json.getString("faultNo"))) {
                faultNo = json.getString("faultNo");
            }
            if (StringUtils.isNotEmpty(json.getString("address"))) {
                position = json.getString("address");
            }

            String eventDesc = null;
            String handleMeasue = null;
            int num = 0;

            //如果是故障事件，则事件描述写成故障号+故障描述，如果是火警事件，且设备是VLS，则事件描述写成扇区号
            if (eventType == 0) {
                VesaRecord vesaFault = new VesaRecord();
                num = Integer.valueOf(faultNo);
                vesaFault.setFaultNo(num);
                VesaRecord faultInfo = vesaDeviceRecordDao.findVesaFaultName(vesaFault);
                if (faultInfo != null) {
                    handleMeasue = faultInfo.getHandleMeasure();
                    eventDesc = faultInfo.getFaultName();
                }

            } else {
                if ("VLS".equals(detectorType)) {
                    eventDesc = faultDesc;
                }
            }

            SysLog.info("传感器ID:" + sensorNo + "消息类型:" + msgType + "回路名称 :" + loopName + "事件类型:"
                    + eventType + "探测器:" + detector + "故障描述：" + eventDesc);

            vesaRecord.setEventType(eventType);
            vesaRecord.setRecordType(recordType);

            vesaRecord.setFaultNo(num);
            vesaRecord.setFaultDesc(faultDesc);
            vesaRecord.setEventDesc(eventDesc);
            vesaRecord.setHandleMeasure(handleMeasue);

            vesaRecord.setLoopName(loopName);
            vesaRecord.setDetector(detector);
            vesaRecord.setDetectorType(detectorType);
            vesaRecord.setPosition(position);
            vesaRecord.setEventDesc(eventDesc);
        }

        List<JSONObject> jsons = saveVesaInfo(vesaDevice, vesaRecord);
        jsonList.addAll(jsons);

        return state;
    }

    /**
     * 功能描述:保存极早期设备状态信息 、记录信息、事件信息
     *
     * @param vesaRecord VesaDevice
     * @param vesaDevice VesaRecord
     * @return List<JSONObject>
     * @author wangzhiwen
     * @date 2019/10/23 15:44
     */
    @Override
    public List<JSONObject> saveVesaInfo(VesaDevice vesaDevice, VesaRecord vesaRecord) {
        boolean isModifyEvent = false;
        boolean isSaveEvent = false;
        List<JSONObject> jsonList = new ArrayList<>();
        JSONObject jsonObject;

        if (vesaRecord == null) {
            jsonObject = new JSONObject();
            jsonObject.put("iotType", ConstantsIot.VESA);
            jsonList.add(jsonObject);
            return jsonList;
        }

        int recordType = vesaRecord.getRecordType();
        int eventType = vesaRecord.getEventType();
        Date creatTime = vesaRecord.getCreateTime();

        vesaDeviceRecordDao.saveVesaRecord(vesaRecord);
        jsonObject = new JSONObject();
        jsonObject.put("msg", 200);
        jsonObject.put("iotType", ConstantsIot.VESA);

        // 新产生的事件处理
        if (recordType == 0) {

            isSaveEvent = true;
            // 故障事件去重处理
            if (eventType == 0) {
                VesaRecord checkRecord = new VesaRecord();
                checkRecord.setEventType(eventType);
                checkRecord.setEventDesc(vesaRecord.getEventDesc());
                checkRecord.setLoopName(vesaRecord.getLoopName());
                checkRecord.setDetector(vesaRecord.getDetector());
                checkRecord.setSensorNo(vesaRecord.getSensorNo());

                Date endDate;
                Date startDate;

                startDate = DateUtils.getDate();
                Calendar c = Calendar.getInstance();
                c.setTime(startDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.SECOND, -1);
                endDate = c.getTime();

                checkRecord.setStartTime(startDate);
                checkRecord.setEndTime(endDate);

                int totalCount = vesaDeviceRecordDao.findVesaDeviceEventListTotalCount(checkRecord);
                //重复故障事件处理：当天该设备的该类型的故障事件已经存在，则不再保存
                if (totalCount > 0) {
                    jsonObject.put("code", 200);
                    jsonList.add(jsonObject);
                    return jsonList;
                }
            }
            vesaDeviceWebSocket(jsonObject);

            if (StringUtils.isNotEmpty(vesaRecord.getLoopName())) {
                VesaAreaType vesaAreaType = new VesaAreaType();
                String loopName = vesaRecord.getLoopName().replace("回路", "");
                vesaAreaType.setLoopName(loopName);
                vesaAreaType.setSensorNo(vesaRecord.getSensorNo());
                vesaAreaType.setProjectId(vesaRecord.getProjectId());
                VesaAreaType areaType = vesaAreaTypeDao.findAreaTypeByLoopAndSensor(vesaAreaType);
                if (areaType == null) {
                    vesaRecord.setAreaType(0);
                } else {
                    vesaRecord.setAreaType(areaType.getAreaType());
                    vesaRecord.setUnit(areaType.getUnit());
                }
            }

            vesaDeviceRecordDao.saveVesaRecordEvent(vesaRecord);
            Device device = new Device();
            device.setIotStatus(2);
            device.setId(vesaDevice.getDeviceId());
            deviceDao.modifyDeviceIotStatus(device);
            if (eventType == 5) {
                //设置设备状态为离线
                vesaDevice.setDeviceStatus(2);
            }
            sendMsg(vesaDevice, vesaRecord);
            //0:故障，1：警告，2：行动，3：火警1，4：火警2，5：离线'
            String sign = "";
            if (eventType == 0) {
                sign = "vesa_fault_event";
            } else if (eventType == 1) {
                sign = "vesa_alarm_event";
            } else if (eventType == 2) {
                sign = "vesa_action_event";
            } else if (eventType == 3) {
                sign = "vesa_fireAlarm1_event";
            } else if (eventType == 4) {
                sign = "vesa_fireAlarm2_event";
            }
            if (StringUtils.isNotEmpty(sign)) {
                //联动报警
                linkageControlService.deviceFault(vesaDevice.getDeviceId(), sign);
            }
        } else {
            // 恢复的事件处理
            List<VesaRecord> list = vesaDeviceRecordDao.findVesaUnrecoverEvent(vesaRecord);
            if (list == null) {
                //设备电量事件，最新记录为异常时，修改状态为已恢复
                SysLog.info("这条VESA恢复记录 无对应的新建记录" + vesaRecord.toString());
                jsonList.add(jsonObject);
                return jsonList;
            }

            VesaRecord wr;
            for (int i = 0; i < list.size(); i++) {
                wr = list.get(i);
                isModifyEvent = true;
                wr.setRecoverRecordId(vesaRecord.getId());
                //事件状态已恢复
                wr.setRecoverStatus(1);
                wr.setRecoverTime(creatTime);
                vesaDeviceRecordDao.modifyVesaRecordEvent(wr);
            }
            if (isModifyEvent){
                vesaDeviceWebSocket(jsonObject);
            }

            if (eventType == 5) {
                //设置设备状态为在线
                vesaDevice.setDeviceStatus(1);
                //Eg:【消检通大厦项目】时间+位置+设备名称+传输装置名称已恢复在线，请前往【火警主机】查看详情。
                // 恢复消息......
            }
        }

        vesaDevice.setEndHeartbeatTime(creatTime);
        vesaDevice.setStatusUpdateTime(creatTime);
        int fireAlarm1Status = 0;
        int fireAlarm2Status = 0;
        int faultStatus = 0;
        if (recordType == 0) {
            //事件 火警异常 火警1=3, 火警2=4, 行动=2, 警告=1, 故障0, 离线5
            if (eventType == 3) {
                fireAlarm1Status = 2;
            } else if (eventType == 4) {
                fireAlarm2Status = 2;
            } else if (eventType == 0) {
                faultStatus = 2;
            }
        } else {
            //恢复的事件处理
            if (eventType == 3) {
                fireAlarm1Status = 1;
            } else if (eventType == 4) {
                fireAlarm2Status = 1;
            } else if (eventType == 0) {
                faultStatus = 1;
            }
        }
        vesaDevice.setFireAlarm1Status(fireAlarm1Status);
        vesaDevice.setFireAlarm2Status(fireAlarm2Status);
        vesaDevice.setFaultStatus(faultStatus);
        vesaDeviceDao.modifyVesaDevice(vesaDevice);

        jsonList.add(jsonObject);
        return jsonList;
    }

    /**
     * 极早期  WebSocket
     *
     * @return JSONObject
     */
    private JSONObject vesaDeviceWebSocket(JSONObject jsonObject) {
        SysLog.info("vesaDeviceWebSocket WebSocketSendMsgUtils ---->");
        jsonObject = new JSONObject();
        jsonObject.put("msg", 200);
        jsonObject.put("iotType", ConstantsIot.VESA);
        WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        return jsonObject;
    }

    /**
     * 功能描述: 发送报警信息
     *
     * @param veasDevice VesaDevice
     * @param vesaRecord VesaRecord
     * @author yuxiang
     * @date 2019/01/06 16:41
     */
    private void sendMsg(VesaDevice veasDevice, VesaRecord vesaRecord) {
        try {
            //调用接口,从设备模块获取设备信息
            String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, vesaRecord.getProjectId(), "projectName");
            //组装要发送的信息内容
            String content = "【" + projectName + "】";

            JSONObject jsonObject = new JSONObject(8);
            jsonObject.put("projectName", projectName);
            jsonObject.put("loopName", vesaRecord.getLoopName());
            jsonObject.put("slaveId", vesaRecord.getDetector());

            int eventType = vesaRecord.getEventType();
            int recordType = vesaRecord.getRecordType();
            ////消息字体显示颜色 0、灰 1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫
            int fontColor = 0;
            String detectorType = vesaRecord.getDetectorType();
            String title = "未知事件通知";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatTime = formatter.format(vesaRecord.getCreateTime());
            jsonObject.put("date", formatTime);
            String event = null;
            List<String> signList = new ArrayList<>();
            Long recordId = vesaRecord.getId();
            switch (eventType) {
                case 0:
                    content = content + " 回路名称：" + vesaRecord.getLoopName() + " 探测器：" + vesaRecord.getDetector() + " 探测器类型：" + detectorType;
                    if (vesaRecord.getPosition() != null) {
                        content = content + " 位置：" + vesaRecord.getPosition();
                        content = content + " 日期/时间：" + formatTime + " ";
                    }

                    if (recordType == 0) {
                        content = content + "发生设备故障事件,  " + "故障描述：" + vesaRecord.getEventDescs();
                        title = "设备故障通知";
                        event = "设备故障事件";
                    } else {
                        content = content + "设备故障事件已恢复,  " + "故障描述：" + vesaRecord.getEventDescs();
                        title = "设备故障恢复通知";
                        event = "设备故障事件已恢复";
                    }
                    fontColor = 2;
                    signList.add(ConstantsIot.vesaRoleSignListFault);
                    break;
                case 1:
                    content = content + " 回路名称：" + vesaRecord.getLoopName() + " 探测器：" + vesaRecord.getDetector() + " 探测器类型：" + detectorType;
                    if (vesaRecord.getPosition() != null) {
                        content = content + " 位置：" + vesaRecord.getPosition();
                        content = content + " 日期/时间：" + formatTime + " ";
                    }

                    if (recordType == 0) {
                        content = content + "发生警告事件";
                        title = "警告通知";
                        event = "警告事件";
                    } else {
                        content = content + "警告事件已恢复";
                        title = "警告恢复通知";
                        event = "警告事件已恢复";
                    }
                    fontColor = 5;
                    signList.add(ConstantsIot.vesaRoleSignListAlarm);
                    break;
                case 2:
                    content = content + " 回路名称：" + vesaRecord.getLoopName() + " 探测器：" + vesaRecord.getDetector() + " 探测器类型：" + detectorType;
                    if (vesaRecord.getPosition() != null) {
                        content = content + " 位置：" + vesaRecord.getPosition();
                        content = content + " 日期/时间：" + formatTime + " ";
                    }

                    if (recordType == 0) {
                        content = content + "发生行动事件";
                        title = "行动通知";
                        event = "行动事件";
                    } else {
                        content = content + "行动事件已恢复";
                        title = "行动恢复通知";
                        event = "行动事件已恢复";
                    }
                    fontColor = 6;
                    signList.add(ConstantsIot.vesaRoleSignListAction);
                    break;
                case 3:
                    content = content + " 回路名称：" + vesaRecord.getLoopName() + " 探测器：" + vesaRecord.getDetector() + " 探测器类型：" + detectorType;
                    if (vesaRecord.getPosition() != null) {
                        content = content + " 位置：" + vesaRecord.getPosition();
                        content = content + " 日期/时间：" + formatTime + " ";
                    }

                    if (recordType == 0) {
                        content = content + "发生火警1事件";
                        title = "火警1通知";
                        event = "火警1事件";
                    } else {
                        content = content + "火警1事件已恢复";
                        title = "火警1恢复通知";
                        event = "火警1事件已恢复";
                    }
                    fontColor = 1;
                    signList.add(ConstantsIot.vesaRoleSignListFireAlarm1);
                    break;
                case 4:
                    content = content + " 回路名称：" + vesaRecord.getLoopName() + " 探测器：" + vesaRecord.getDetector() + " 探测器类型：" + detectorType;
                    if (vesaRecord.getPosition() != null) {
                        content = content + " 位置：" + vesaRecord.getPosition();
                        content = content + " 日期/时间：" + formatTime + " ";
                    }

                    if (recordType == 0) {
                        content = content + "发生火警2事件";
                        title = "火警2通知";
                        event = "火警2事件";
                    } else {
                        content = content + "火警2事件已恢复";
                        title = "火警2恢复通知";
                        event = "火警2事件已恢复";

                    }
                    fontColor = 3;
                    signList.add(ConstantsIot.vesaRoleSignListFireAlarm2);
                    break;
                case 5:
                    content = content + " 传输装置名称：" + veasDevice.getSensorName() + "  日期/时间：" + formatTime + " ";
                    if (recordType == 0) {
                        content = content + "已离线";
                        title = "离线通知";
                        event = "已离线";
                        recordId = null;
                    } else {
                        content = content + "已恢复在线";
                        title = "在线通知";
                        event = "已在线";
                    }
                    fontColor = 0;
                    signList.add(ConstantsIot.vesaRoleSignListOffline);

                    //暂时屏蔽掉离线、在线通知消息，待系统稳定后，再开放该消息通知
                    SysLog.info("sendMsg: online/offline msg ,content = " + content + " title = " + title + " event" + event);
                    return;
                default:
                    SysLog.info("sendMsg: error eventType = " + eventType);
            }

            jsonObject.put("event", event);
            jsonObject.put("buildingId", veasDevice.getBuildingId());
            jsonObject.put("buildingName", veasDevice.getBuildingNameDesc());
            jsonObject.put("floorName", veasDevice.getBuildingFloorNameDesc());

            messageService.saveMessageRole(13, signList, title, eventType, fontColor, content, ConstantsIot.VESA_MSG_URL, veasDevice.getProjectId(),
                    recordId, null, jsonObject);
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

}



