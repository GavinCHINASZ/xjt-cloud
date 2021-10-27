package com.xjt.cloud.iot.core.service.impl.water;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventRecordService;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmDeviceService;
import com.xjt.cloud.iot.core.service.service.fire.FireAlarmRecordService;
import com.xjt.cloud.iot.core.service.service.eye.FireEyeEventService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceService;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordResolveService;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceService;
import com.xjt.cloud.iot.core.service.service.water.IotDeviceService;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceRecordResolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/27 10:19
 * @Description: 物联设备管理接口实现类
 */
@Service
public class IotDeviceServiceImpl extends AbstractService implements IotDeviceService {

    @Autowired
    private WaterDeviceRecordResolveService waterDeviceRecordResolveService;
    @Autowired
    private FireAlarmRecordService fireAlarmRecordService;
    @Autowired
    private FireAlarmDeviceService fireAlarmDeviceService;
    @Autowired
    private VesaDeviceRecordResolveService vesaDeviceRecordResolveService;
    @Autowired
    private VesaDeviceService vesaDeviceService;
    @Autowired
    private LinkageDeviceService linkageDeviceService;
    @Autowired
    private FireEyeEventService fireEyeEventService;
    @Autowired
    private ElectricalFireEventRecordService electricalFireEventRecordService;

    /**
     * 功能描述:物联网设备信息保存
     *
     * @param data
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    @Override
    public List<JSONObject> dataAccess(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        List<JSONObject> jsonList  = new ArrayList<>();
        String writeBackMsg = "";//回写信息

        String msgType = jsonObject.getString("msgType");
        if ("FireAlarm".equals(msgType)) {
            //火警主机
            jsonList = fireAlarmRecordService.saveFireAlarmRecord(data);
        } else if ("Hydrant".equals(msgType)) {
            //消火栓
            jsonList = waterDeviceRecordResolveService.waterSys(jsonObject);
        } else if ("WaterGage".equals(msgType) || "WaterImmersion".equals(msgType)) {
            //水压和水浸
            jsonList = waterDeviceRecordResolveService.waterSys(jsonObject);
            /*jsonObject = new JSONObject();
            jsonObject.put("writeBackMsg", writeBackMsg);
            jsonObject.put("iotType", ConstantsIot.WATER_GAGE);
            jsonObject.put("msg", 200);
            jsonList.add(jsonObject);
            jsonObject = new JSONObject();
            jsonObject.put("iotType", ConstantsIot.WATER_GAGE);
            jsonObject.put("msg", 200);
            jsonList.add(jsonObject);*/
        } else if ("CombustibleGas".equals(msgType)) {
            //烟感

        } else if ("IntelligentSmoke".equals(msgType)) {
            //可燃气体

        } else if ("VesdaVSM".equals(msgType)) {
            //极早期设备
            jsonList = vesaDeviceRecordResolveService.vesaSys(jsonObject);

        }else if("LinkageDevice".equals(msgType)) {
            //联动设备
            jsonList = linkageDeviceService.linkageDeviceAnalyze(jsonObject);

        }else if("ElectricalFireEventRecord".equals(msgType)) {
            // 电气火灾
            jsonList = electricalFireEventRecordService.electricalFireEventRecordServiceSys(jsonObject);

        }else if("FireEye".equals(msgType)){
            jsonList = fireEyeEventService.fireEyeEventSave(jsonObject);

        }
        return jsonList;
    }

    /**
     * 功能描述:物联网设备信息批量保存
     *
     * @param data
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    @Override
    public List<JSONObject> dataListAccess(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String msgType = jsonObject.getString("msgType").toUpperCase();
        List<JSONObject> jsonList;
        if (ConstantsIot.WATER_GAGE.equals(msgType) || ConstantsIot.WATER_IMMERSION.equals(msgType)) {
            jsonList = waterDeviceRecordResolveService.waterDeviceDataAccess(jsonObject);
        }else if (ConstantsIot.LINKAGE_DEVICE.equals(msgType)){// 联动设备(声光报警设备)心跳上传消息
            // SysLog.info("IotDeviceServiceImpl.dataListAccess声光报警上传消息data=" + data);
            jsonList = linkageDeviceService.linkageDeviceAnalyze(jsonObject);
        } else {
            jsonList = new ArrayList<>();
            jsonObject = new JSONObject();
            jsonObject.put("code", 500);
            jsonList.add(jsonObject);
            jsonList = new ArrayList<>();
            jsonObject = new JSONObject();
            jsonObject.put("iotType", ConstantsIot.WATER_GAGE);
            jsonObject.put("msg", 500);
        }
        return jsonList;
    }

    /**
     * 功能描述:判断注册码是否存在
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 16:49
     */
    @Override
    public JSONObject isSensorPresence(String msgType,String sensor) {
        JSONObject jsonObject = new JSONObject();
        if ("FireAlarm".equals(msgType)) { //火警主机
            jsonObject = fireAlarmDeviceService.isSensorPresence(sensor);
        } else if ("Hydrant".equals(msgType)) {//消火栓

        } else if ("WaterGage".equals(msgType) || "WaterImmersion".equals(msgType)) {//水压和水浸

        } else if ("CombustibleGas".equals(msgType)) {//烟感

        } else if ("IntelligentSmoke".equals(msgType)) {//可燃气体

        } else if ("VesdaVSM".equals(msgType)) {//极早期
            jsonObject = vesaDeviceService.isSensorPresence(sensor);
        }else if("LinkageDevice".equals(msgType)){//联动设备
            jsonObject = linkageDeviceService.isSensorPresence(sensor);
        }
        /*if (ConstantsIot.WATER_GAGE.equals(msgType) || ConstantsIot.WATER_IMMERSION.equals(msgType)) {

        } else {
            jsonObject = new JSONObject();
            jsonObject.put("isPresence", "false");
            jsonObject.put("code", 200);
            jsonObject.put("msg", "success");
        }*/
        return jsonObject;
    }
}
