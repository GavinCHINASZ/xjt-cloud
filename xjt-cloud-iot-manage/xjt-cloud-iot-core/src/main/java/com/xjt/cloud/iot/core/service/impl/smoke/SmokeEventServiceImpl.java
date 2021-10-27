package com.xjt.cloud.iot.core.service.impl.smoke;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.AesUtils;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.smoke.SmokeDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.smoke.SmokeEventDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.smoke.*;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeEventService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import onenet.datapush.receiver.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 烟感事件 service实现类
 *
 * @author huanggc
 * @date 2020/07/15
 **/
@Service
public class SmokeEventServiceImpl extends AbstractService implements SmokeEventService {
    @Autowired
    private SmokeEventDao smokeEventDao;
    @Autowired
    private SmokeDeviceDao smokeDeviceDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LinkageControlService linkageControlService;
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 功能描述 查询烟感事件列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeEventList(String json) {
        SmokeEvent smokeEvent = com.alibaba.fastjson.JSONObject.parseObject(json, SmokeEvent.class);

        Date createTime = smokeEvent.getCreateTime();
        if (createTime == null && smokeEvent.getSmokeDeviceId() == null && smokeEvent.getId() == null){
            // 查所有事件
            Date date = DateUtils.getDate();
            smokeEvent.setCreateTime(date);
            smokeEvent.setEndTime(date);
        }

        if (createTime != null){
            smokeEvent.setCreateTime(DateUtils.startDayDate(createTime));
        }

        if (smokeEvent.getLastModifyTime() != null){
            smokeEvent.setLastModifyTime(DateUtils.endDayDate(smokeEvent.getLastModifyTime()));
        }

        Integer totalCount = smokeEvent.getTotalCount();
        Integer pageSize = smokeEvent.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = smokeEventDao.findSmokeEventListTotalCount(smokeEvent);
        }

        if (null == smokeEvent.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            smokeEvent.setOrderCols(orderCols);
            // 降序
            smokeEvent.setOrderDesc(true);
        }
        List<SmokeEvent> smokeEventList = smokeEventDao.findSmokeEventList(smokeEvent);
        return asseData(totalCount, smokeEventList);
    }

    /**
     * 功能描述: 增加 烟感事件
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveSmokeEvent(String json) {
        SmokeEvent smokeEvent = com.alibaba.fastjson.JSONObject.parseObject(json, SmokeEvent.class);
        Integer eventType = smokeEvent.getEventType();
        Long projectId = smokeEvent.getProjectId();
        Integer electric = smokeEvent.getElectricQuantity();

        SmokeDevice smokeDevice = new SmokeDevice();
        JSONObject jsonObject = new JSONObject();
        if (smokeEvent.getEventTypes() != null){
            // 恢复 事件类型
            smokeDevice.setRecoverStatus(1);

            smokeEvent.setRecoverStatus(1);
            smokeEvent.setEventType(smokeEvent.getEventTypes());
            smokeEventDao.updateSmokeEvent(smokeEvent);
        }else {
            smokeDevice.setRecoverStatus(2);
            smokeEvent.setRecoverStatus(2);
            smokeEventDao.saveSmokeEvent(smokeEvent);
        }

        if (electric <= 2000){
            smokeEvent.setEventType(4);
            smokeEvent.setRecoverStatus(2);
            smokeEventDao.saveSmokeEvent(smokeEvent);
        }else {
            smokeEvent.setEventType(4);
            smokeEvent.setRecoverStatus(1);
            smokeEventDao.updateSmokeEvent(smokeEvent);
        }

        smokeDevice.setId(smokeEvent.getSmokeDeviceId());
        smokeDevice.setEventType(eventType);
        smokeDevice.setHeartbeatTime(new Date());
        smokeDevice.setElectricQuantity(electric);
        smokeDevice.setElectricQuantityStatus(electric <= 2000 ? 2 : 1);
        smokeDeviceDao.updateSmokeDevice(smokeDevice);

        SmokeDevice smokeDeviceEntity = new SmokeDevice();
        smokeDeviceEntity.setId(smokeDevice.getId());
        smokeDevice = smokeDeviceDao.findSmokeDevice(smokeDeviceEntity);

        if(eventType != null && eventType > 0){
            Long recordId = smokeEvent.getSmokeDeviceId();
            String title;
            String content;
            switch (eventType) {
                case 1:
                    title = "报警通知";
                    content = " 发生报警";
                    break;
                case 5:
                    title = "故障通知";
                    content = " 传感器故障";
                    break;
                case 21:
                    title = "离线通知";
                    content = " 已离线";
                    recordId = null;
                    break;
                default:
                    title = "低电量通知";
                    content = " 当前电量低于20%";
                    break;
            }

            /**
             * 智能烟感消息模板
             * 智能烟感通知
             *
             * 1报警   5传感器故障
             * 【消检通】 项目名称/建筑物/位置 + 巡检点id + 传感器ID + 发生报警，请及时出处理
             *
             * 4低电量报警
             * 【消检通】项目名称/建筑物/位置 + 巡检点id + 传感器ID + 当前电量低于20%，请及时处理。
             */
            // 发消息给 项目下 烟感管理权限的人
            List<String> roleSignList = new ArrayList<>(1);
            // 烟感编辑权限
            roleSignList.add("smoke_manage_edit");

            // 从缓存中取出项目对象
            String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

            JSONObject object = new JSONObject();
            object.put("buildingId", smokeDevice.getBuildingId());

            StringBuilder sb = new StringBuilder();
            sb.append("【" + projectName + "】" + smokeDevice.getPointLocationDesc() + smokeDevice.getCheckPointQrNo()
                    + " 传感器ID:" + smokeDevice.getSensorId() + content + "，请及时处理。");
            messageService.saveMessageRole(9, roleSignList, title, eventType, 0, sb.toString(),
                    "url", projectId, recordId, "data", object);

            jsonObject.put("msg", 200);
            jsonObject.put("iotType", ConstantsIot.SMOKE_DEVICE);

            WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        }
        return asseData(smokeEvent);
    }

    /**
     * 功能描述: 更新 烟感事件
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateSmokeEvent(String json) {
        return null;
    }

    /**
     * 功能描述: 删除 烟感事件
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deletedSmokeEvent(String json) {
        return null;
    }

    /**
     * 功能描述：接收OneNET平台的推送消息。
     * <ul>注:
     *     <li>1.OneNet平台为了保证数据不丢失，有重发机制，如果重复数据对业务有影响，数据接收端需要对重复数据进行排除重复处理。</li>
     *      <li>2.OneNet每一次post数据请求后，等待客户端的响应都设有时限，在规定时限内没有收到响应会认为发送失败。
     *           接收程序接收到数据时，尽量先缓存起来，再做业务逻辑处理。</li>
     *  </ul>
     *
     * @param request HttpServletRequest数据消息
     * @param msg
     * @param signature
     * @param nonce
     * @return 任意字符串。OneNet平台接收到http 200的响应，才会认为数据推送成功，否则会重发。
     */
    @Override
    public Data receive(HttpServletRequest request, String msg, String signature, String nonce){
        InputStream in;
        String body = "";
        try {
            in = request.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int iRead;
            while((iRead=buf.read(buffer)) != -1) {
                body += new String(buffer,0, iRead,"UTF-8");
            }
        } catch (IOException e) {
            SysLog.error(e);
        }

        if (StringUtils.isEmpty(body)){
            return null;
        }

        /*
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         */
        // 明文模式  start
        SysLog.info("receive = data receive:  body Object --- " + body);

        /*
         * 功能描述 解析数据推送请求，生成code>BodyObj消息对象
         *
         * Parameters:
         * body 数据推送请求body部分
         * encrypted 表征是否为加密消息
         * Returns:生成的BodyObj消息对象
         */
        /*System.out.println(body);
        String replace = body.replace("，", ",");

        StringBuffer sb = new StringBuffer();
        String[] splitArray = replace.split(",");
        int splitLength = splitArray.length - 1;
        for (int i = 0; i < splitArray.length; i++) {
            String s1 = splitArray[i];
            if (s1.contains("nonce") || s1.contains("signature")){
                String[] split = s1.split(":");
                String s = split[1];
                if (s.contains("\"")){
                    continue;
                }

                if (s1.contains("}")){
                    String s2 = s1.replace("}", "\"}");
                    splitArray[i] = (s2.replace(":", ":\""));
                }else {
                    s = ("\"" + s + "\"");
                    splitArray[i] = split[0] + ":" + s;
                }
            }

            if (i == splitLength){
                sb.append(splitArray[i]);
            }else {
                sb.append(splitArray[i] + ",");
            }
        }
        String str = sb.toString();
        System.out.println(str);
        str = str.substring(0, str.length() - 1) + "}";
        System.out.println(str);*/

        Util.BodyObj obj = Util.resolveBody(body, false);
        SysLog.info("receive = data receive:  obj --- " + obj);

        Data data = null;
        if (obj != null){
            boolean dataRight = Util.checkSignature(obj, ConstantsIot.ONE_NET_TOKEN);
            if (dataRight){
                String s = obj.getMsg().toString();

                NbMsgEntity nbMsgEntity = com.alibaba.fastjson.JSONObject.parseObject(obj.getMsg().toString(), NbMsgEntity.class);
                if (nbMsgEntity != null){
                    if (CollectionUtils.isNotEmpty(nbMsgEntity.getMsg())){
                        List<MsgEntity> msgEntityList = nbMsgEntity.getMsg();
                        for (MsgEntity m : msgEntityList) {
                            data = doEvent(m);
                        }
                    }else {
                        MsgEntity msgEntity = new MsgEntity();
                        msgEntity.setType(nbMsgEntity.getType());
                        msgEntity.setDs_id(nbMsgEntity.getDs_id());
                        msgEntity.setImei(nbMsgEntity.getImei());
                        msgEntity.setDev_id(nbMsgEntity.getDev_id());
                        msgEntity.setValue(nbMsgEntity.getValue());
                        msgEntity.setLogin_type(nbMsgEntity.getLogin_type());
                        msgEntity.setStatus(nbMsgEntity.getStatus());

                        data = doEvent(msgEntity);
                    }
                }
                // Object msgs = obj.getMsg();
                // System.out.println(msgs);
                // doEvent(msgs.toString());
            }else {
                SysLog.info("data receive: signature error");
            }
        }else {
            SysLog.info("data receive: body empty error");
        }
        // 明文模式  end


        /*
         *  解析数据推送请求，加密模式
         *
         *  如果是加密模式使用以下代码
         */
        // 加密模式  start
//        Util.BodyObj obj1 = Util.resolveBody(body, true);
//        logger.info("data receive:  body Object--- " +obj1);
//        if (obj1 != null){
//            boolean dataRight1 = Util.checkSignature(obj1, token);
//            if (dataRight1){
//                String msg = Util.decryptMsg(obj1, aeskey);
//                logger.info("data receive: content" + msg);
//            }else {
//                logger.info("data receive:  signature error " );
//            }
//        }else {
//            logger.info("data receive: body empty error" );
//        }
        // 加密模式  end

        return data;
    }

    /**
     * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
     *
     * @param msg 验证消息
     * @param nonce 随机串
     * @param signature 签名
     * @author huanggc
     * @date 2020/07/20
     * @return msg值
     */
    @Override
    public String check(String msg, String nonce, String signature) {
        SysLog.info("check = url&token check: msg:{" + msg +"} nonce{" + nonce +"} signature:{ " + signature + "}");

        try {
            if (Util.checkToken(msg, nonce, signature, ConstantsIot.ONE_NET_TOKEN)){
                return msg;
            }
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
            SysLog.info("UnsupportedEncodingException = " + e);
        }
        return "error";
    }

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param encryptMsg 加密内容
     * @param msgType 消息内容
     * @author huanggc
     * @date 2020/07/21
     * @return 返回200后才可保存成功
     */
    @Override
    public String checkCM(String encryptMsg, String msgType) {
        SysLog.info("移动 智慧消防平台https://smartsensor.eastcmiot.com http推送验证 encryptMsg = " + encryptMsg + ",msgType = " + msgType);

        return "200";
    }

    /**
     * 功能说明： 移动 智慧消防平台https://smartsensor.eastcmiot.com， http推送验证
     * @param request HttpServletRequest
     * @author huanggc
     * @date 2020/07/21
     * @return 返回200后才可保存成功
     */
    @Override
    public String checkCM(HttpServletRequest request) {
        InputStream in;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            in = request.getInputStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            int iRead;
            while((iRead=buf.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer,0, iRead,"UTF-8"));
            }
        } catch (IOException e) {
            SysLog.error(e);
        }

        SysLog.info("receiveCM = data receive:  json --- " + stringBuilder.toString());
        EncryptMsg encryptMsg = JSONObject.parseObject(stringBuilder.toString(), EncryptMsg.class);
        String msg = encryptMsg.getEncryptMsg();
        // 自己设置的Key值
        String deEncrypMsg = AesUtils.decrypt(msg, ConstantsIot.SMARTSENSOR_EASTCMIOT_COM_AES_KEY);
        SysLog.info("receiveCM 解密后 EncryptMsg = " + deEncrypMsg);
        return "ok";
    }

    /**
     * 方法待优化 ???
     * NB--OneNET智能烟感事件处理
     *
     * @param data 数据
     * @return Boolean
     */
    private Data doEvent(MsgEntity data) {
        SysLog.info("OneNET doEvent receive = " + data);
        boolean flag = false;
        int type = data.getType();
        /*
         * 标识消息类型 https://open.iot.10086.cn/doc/book/application-develop/httppush/develop-manual.html
         * 1：设备上传数据点消息
         * 2：设备上下线消息
         * 7：缓存命令下发后结果上报（仅支持NB设备）
         */
        if(type != 1 && type != 2){
            SysLog.info("OneNET doEvent: Receive OneNET error msg type =  "+ type);
            return null;
        }
        // OneNET平台上设备的ID
        int dev_id = data.getDev_id();

        SmokeDevice smoke = new SmokeDevice();
        smoke.setDevId(String.valueOf(dev_id));
        smoke.setDeleted(false);
        // 先查询 烟感设备中 是否绑定了 硬件设备
        SmokeDevice smokeDevice = smokeDeviceDao.findSmokeDevice(smoke);
        if (smokeDevice == null) {
            SysLog.info("OneNET doEvent: can not find smokeDevice by dev_id,：dev_id =  " + dev_id);
            return null;
        }
        Long projectId = smokeDevice.getProjectId();

        // 设备上下线消息处理
        if(type == 2){
            int loginType = data.getLogin_type();
            int status = data.getStatus();

            // 设备登录协议类型: 1-EDP, 6-MODBUS, 7-MQTT, 10-NB-IoT
            if(loginType != 10){
                SysLog.info("OneNET doEvent: Receive OneNET error msg protocol, protocol =  "+ loginType);
                return null;
            }

            /*
             * 设备上下线标识
             * 0：设备下线
             * 1：设备上线
             */
            if(status == 1){
                smokeDevice.setHeartbeatTime(new Date());
                smokeDevice.setDeviceStatus(1);
                smokeDevice.setRecoverStatus(1);
                smokeDeviceDao.updateSmokeDevice(smokeDevice);

                // 恢复事件
                SmokeEvent smokeEvent = new SmokeEvent();
                smokeEvent.setSensorId(smokeDevice.getSensorId());
                smokeEvent.setRecoverStatus(1);
                smokeEventDao.updateSmokeEvent(smokeEvent);
            }
            SysLog.info("OneNET doEvent: Receive OneNET on-line msg, status = "+ status);
            return null;
        }

        // 以下是设备上传数据处理
        String dsId  = data.getDs_id();

        /*
         * GS524N型号  3200_0_5501,  电池电量（0-100）
         * GS524N型号  3200_0_5503， 上报消息类型
         *
         * GS524N-C----26261_1_26244 运行状态变化上报(告警状态信息)
         * GS524N-C----26261_1_26243 心跳信息
         * GS524N-C----26261_1_26242 全量信息
         * GS524N-C----26261_1_26241 上报上电信息
         * GS524N-C----26261_1_26248 日志信息
         *
         * 其他数据不处理
         */
        if (!"3200_0_5501".equals(dsId) && !"3200_0_5503".equals(dsId)
                && !"26261_1_26244".equals(dsId) && !"26261_1_26243".equals(dsId) && !"26261_1_26241".equals(dsId)){

            SysLog.info("OneNET doEvent: not procesed data stream：ds_id =  " + dsId);
            flag = true;
            return null;
        }

        int msgType;
        int smokeDensity;
        // 烟感设备码,如:863222041068160
        String imei = data.getImei();
        Integer devId = data.getDev_id();

        Long smokeDeviceId = smokeDevice.getId();
        Long deviceId = smokeDevice.getDeviceId();
        Integer electric = smokeDevice.getElectricQuantity();
        try {
            SmokeEvent smokeEvent = new SmokeEvent();
            smokeEvent.setElectricQuantity(electric);
            smokeEvent.setSmokeDeviceId(smokeDeviceId);
            smokeEvent.setDeviceId(deviceId);
            smokeEvent.setSensorId(imei);
            smokeEvent.setDevId(devId.toString());
            smokeEvent.setDealStatus(0);

            Integer eventType = 0;
            if("26261_1_26244".equals(dsId) || "26261_1_26243".equals(dsId) || "26261_1_26241".equals(dsId)) {
                String value = data.getValue();
                SysLog.info("OneNET doEvent: jsonData getString value = " + value);

                com.alibaba.fastjson.JSONObject jsonObject = null;
                if ("26261_1_26244".equals(dsId)){
                    // 告警状态信息
                    jsonObject = NbSmokeComParseUtils.comParse(ConstantsIot.GS524N_C_PROPERTY_NAME_FORTY_FOUR, value);
                } else if ("26261_1_26243".equals(dsId)){
                    // 心跳信息
                    jsonObject = NbSmokeComParseUtils.comParse(ConstantsIot.GS524N_C_PROPERTY_NAME_FORTY_THREE, value);
                } else if ("26261_1_26241".equals(dsId)){
                    // 上电上报信息
                    jsonObject = NbSmokeComParseUtils.comParse(ConstantsIot.GS524N_C_PROPERTY_NAME_FORTY_ONE, value);
                }

                if (null != jsonObject){
                    SmokeEvent smokeEventEntity = com.alibaba.fastjson.JSONObject.parseObject(String.valueOf(jsonObject), SmokeEvent.class);

                    if (smokeEventEntity.getSignalValue() != null){
                        // 信号量
                        Integer signalValue = smokeEventEntity.getSignalValue();
                        smokeDevice.setSignalValue(signalValue);
                    }

                    if (smokeEventEntity.getElectricQuantity() != null){
                        electric = smokeEventEntity.getElectricQuantity();
                        smokeEvent.setElectricQuantity(electric);
                    }
                    
                    // 恢复 事件
                    if (smokeEventEntity.getEventTypes() != null && smokeEventEntity.getEventType() == null){
                        Integer eventTypes = smokeEventEntity.getEventTypes();
                        smokeDevice.setRecoverStatus(1);
                        smokeDevice.setEventType(eventTypes);
                        smokeDevice.setDeviceStatus(1);

                        if("26261_1_26244".equals(dsId) || "26261_1_26241".equals(dsId) || "26261_1_26243".equals(dsId)) {
                            smokeEvent.setRecoverStatus(1);
                            // 报警恢复
                            if (smokeEventEntity.getEventTypeSmoke() != null){
                                smokeEvent.setEventType(1);
                                Integer updateSmokeEventNum = smokeEventDao.updateSmokeEvent(smokeEvent);

                                // 发消息给 项目下 烟感管理权限的人
                                List<String> roleSignList = new ArrayList<>(1);
                                // 烟感编辑权限
                                roleSignList.add("smoke_manage_edit");

                                JSONObject object = new JSONObject();
                                object.put("buildingId", smokeDevice.getBuildingId());

                                // 从缓存中取出项目对象
                                String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

                                StringBuilder sbu = new StringBuilder();
                                sbu.append("【");
                                sbu.append(projectName);
                                sbu.append("】");
                                sbu.append(smokeDevice.getPointLocationDesc());
                                sbu.append(smokeDevice.getCheckPointQrNo());
                                sbu.append(" 传感器ID:");
                                sbu.append(smokeDevice.getSensorId());
                                sbu.append(" 发生报警事件已恢复，请查看详情。");
                                messageService.saveMessageRole(9, roleSignList, "报警恢复通知",0, 0, sbu.toString(),
                                        "url", projectId, null, "data", object);
                            }

                            // 故障恢复
                            smokeEvent.setEventType(eventTypes);
                            smokeEventDao.updateSmokeEvent(smokeEvent);

                            Device device = new Device();
                            device.setId(deviceId);
                            device.setIotStatus(1);
                            deviceDao.modifyDeviceIotStatus(device);
                        }
                    }
                    // 故障 事件
                    if (smokeEventEntity.getEventType() != null){
                        eventType = smokeEventEntity.getEventType();
                        smokeDevice.setEventType(eventType);
                        smokeDevice.setRecoverStatus(2);

                        if("26261_1_26244".equals(dsId) || "26261_1_26241".equals(dsId)) {
                            smokeEvent.setEventType(eventType);
                            smokeEvent.setProjectId(projectId);
                            smokeEvent.setRecoverStatus(2);

                            smokeEventDao.saveSmokeEvent(smokeEvent);
                            flag = true;
                        }
                    }

                    if (electric <= 2000){
                        smokeEvent.setEventType(4);
                        smokeEvent.setRecoverStatus(2);
                        smokeEventDao.saveSmokeEvent(smokeEvent);

                        // 发消息给 项目下 烟感管理权限的人
                        List<String> roleSignList = new ArrayList<>(1);
                        // 烟感编辑权限
                        roleSignList.add("smoke_manage_edit");

                        JSONObject object = new JSONObject();
                        object.put("buildingId", smokeDevice.getBuildingId());

                        // 从缓存中取出项目对象
                        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

                        StringBuilder sbu = new StringBuilder();
                        sbu.append("【");
                        sbu.append(projectName);
                        sbu.append("】");
                        sbu.append(smokeDevice.getPointLocationDesc());
                        sbu.append(smokeDevice.getCheckPointQrNo());
                        sbu.append(" 传感器ID:");
                        sbu.append(smokeDevice.getSensorId());
                        sbu.append(" 当前电量低于20%，请及时处理。");

                        messageService.saveMessageRole(9, roleSignList, "低电量通知",4, 0, sbu.toString(),
                                "url", projectId, null, "data", object);

                        flag = true;

                        linkageControlService.deviceFault(deviceId, "smoke_low_power_event");

                        // 低电量时发 WebSocket
                        JSONObject json = new JSONObject();
                        json.put("msg", 200);
                        json.put("iotType", ConstantsIot.SMOKE_DEVICE);
                        WebSocketSendMsgUtils.nettySendMsg(json);
                    }else if (electric > 2000){
                        smokeEvent.setEventType(4);
                        smokeEvent.setRecoverStatus(1);
                        smokeEventDao.updateSmokeEvent(smokeEvent);
                    }
                }

                // 恢复 离线
                smokeEvent.setRecoverStatus(1);
                smokeEvent.setEventType(21);
                smokeEventDao.updateSmokeEvent(smokeEvent);
            }

            if("3200_0_5503".equals(dsId)) {
                int value = Integer.parseInt(data.getValue());
                /* 3200_0_5503 消息类型：
                 *         1. 烟雾   报警
                 *         4. 低压
                 *         5. 传感器 故障
                 */
                msgType = value;
                eventType = value;
                // 报警
                if (msgType == 1) {
                    smokeEvent.setStatus(1);
                    smokeDevice.setStatus(1);
                    smokeEventDao.saveSmokeEvent(smokeEvent);
                    // 发送消息

                    SysLog.info("OneNET doEvent: Alarm, data stream：ds_id =  " + dsId + ", msgType = " + msgType);
                } else if (msgType == 5) {
                    // 设备传感器故障
                    smokeDevice.setStatus(3);
                    smokeEvent.setStatus(3);
                    smokeEventDao.saveSmokeEvent(smokeEvent);
                    // 发送消息

                    SysLog.info("OneNET doEvent: Fault, data stream：ds_id =  " + dsId + ", msgType = " + msgType);
                } else {
                    // 正常
                    smokeDevice.setStatus(0);
                    smokeEvent.setStatus(0);
                    SysLog.info("OneNET doEvent: Normal, data stream：ds_id =  " + dsId + ", msgType = " + msgType);
                }
            }

            if ("3200_0_5501".equals(dsId)){
                int value = Integer.parseInt(data.getValue());
                electric = new Integer(value);
                smokeDevice.setElectricQuantity(electric);

                
                if (Integer.valueOf(electric) < 20) {
                    // 电池电量小于 20% 时告警
                    SmokeEvent smokeEventEntity = smokeEvent;
                    smokeEventEntity.setStatus(2);
                    smokeEventEntity.setElectricQuantity(electric * 100);
                    smokeEventDao.saveSmokeEvent(smokeEventEntity);
                    // 发送消息
                    eventType = 4;

                    SysLog.info("OneNET doEvent: electric < 20, electric =  "+ electric);
                }
                SysLog.info("OneNET doEvent: electric process, electric =  "+ electric);
            }

            smokeDevice.setHeartbeatTime(new Date());
            smokeDevice.setElectricQuantity(electric);
            smokeDevice.setElectricQuantityStatus(electric <= 2000 ? 2 : 1);
            smokeDevice.setDeviceStatus(1);
            smokeDeviceDao.updateSmokeDevice(smokeDevice);

            if(eventType > 0){
                String title = "";
                String content = "";
                switch (eventType) {
                    case 1:
                        title = "报警通知";
                        content = "发生报警";
                        break;
                    case 5:
                        title = "故障通知";
                        content = "传感器故障";
                        break;
                }

                /*
                 * 1报警   5传感器故障
                 * 【消检通】 项目名称/建筑物/位置 + 巡检点id + 传感器ID + 发生报警，请及时出处理
                 */
                // 发消息给 项目下 烟感管理权限的人
                List<String> roleSignList = new ArrayList<>(1);
                // 烟感编辑权限
                roleSignList.add("smoke_manage_edit");

                JSONObject object = new JSONObject();
                object.put("buildingId", smokeDevice.getBuildingId());

                // 从缓存中取出项目对象
                String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

                StringBuilder sbu = new StringBuilder();
                sbu.append("【");
                sbu.append(projectName);
                sbu.append("】");
                sbu.append(smokeDevice.getPointLocationDesc());
                sbu.append(smokeDevice.getCheckPointQrNo());
                sbu.append(" 传感器ID:");
                sbu.append(smokeDevice.getSensorId());
                sbu.append(content);
                sbu.append("，请及时处理。");

                messageService.saveMessageRole(9, roleSignList, title, eventType, 0, sbu.toString(),
                        "url", projectId, null, "data", object);

                if (eventType == 1){
                    // 烟雾报警
                    linkageControlService.deviceFault(deviceId, "smoke_alarm_event");
                }else if (eventType == 5){
                    linkageControlService.deviceFault(deviceId, "smoke_fault_event");
                }
                Device device = new Device();
                device.setId(deviceId);
                device.setIotStatus(2);
                deviceDao.modifyDeviceIotStatus(device);

                // 故障时发 WebSocket
                JSONObject jsonObject = new JSONObject(2);
                jsonObject.put("msg", 200);
                jsonObject.put("iotType", ConstantsIot.SMOKE_DEVICE);
                WebSocketSendMsgUtils.nettySendMsg(jsonObject);
            }

            if (flag){
                return asseData(smokeEvent);
            }
            return asseData("");
        } catch (NumberFormatException e) {
            SysLog.error(e);
        }

        return null;
    }

    /**
     * 功能描述: 烟感告警事件 报表 饼图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeEventSummaryReport(String json) {
        SmokeEvent smokeEvent = com.alibaba.fastjson.JSONObject.parseObject(json, SmokeEvent.class);

        if (smokeEvent.getCreateTime() != null ){
            smokeEvent.setCreateTime(DateUtils.startDayDate(smokeEvent.getCreateTime()));
        }else {
            // 当天时间
            Date date = DateUtils.getDate();
            smokeEvent.setCreateTime(date);
            smokeEvent.setEndTime(date);
        }
        if (smokeEvent.getLastModifyTime() != null ){
            smokeEvent.setLastModifyTime(DateUtils.endDayDate(smokeEvent.getLastModifyTime()));
        }

        SmokeDeviceReport smokeDeviceReport = smokeEventDao.findSmokeEventSummaryReport(smokeEvent);
        return asseData(smokeDeviceReport);
    }

    /**
     * 功能描述: 烟感告警事件 报表 曲线图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/31
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeEventReportCount(String json) {
        SmokeEvent smokeEvent = com.alibaba.fastjson.JSONObject.parseObject(json, SmokeEvent.class);
        if (smokeEvent.getCreateTime() == null){
            Date date = DateUtils.getDate();
            smokeEvent.setCreateTime(date);
            smokeEvent.setEndTime(date);
        }
        smokeEvent.setGroupType(DateUtils.getBetweenDateTimeType(smokeEvent.getCreateTime(), smokeEvent.getEndTime()));

        List<SmokeEvent> smokeEventList = smokeEventDao.findSmokeEventReportCount(smokeEvent);
        return asseData(smokeEventList);
    }

    /**
     * 功能描述 导出烟感告警事件列表
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020/08/07
     */
    @Override
    public void downSmokeEventList(String json, HttpServletResponse response) {
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(json);
        SmokeEvent smokeEvent = com.alibaba.fastjson.JSONObject.parseObject(json, SmokeEvent.class);

        List<SmokeEvent> smokeEventList = getSmokeEventList(smokeEvent);

        if (CollectionUtils.isEmpty(smokeEventList)){
            return ;
        }

        Long projectId = smokeEvent.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--烟感告警事件列表");

        String[] keys = {"rowNum", "deviceName", "eventTypeDesc", "deviceQrNo", "sensorId", "createTimeDesc", "recoverTimeDesc", "recoverStatusDesc",
            "handleStatusDesc", "eventHandleTimeDesc"};

        ExcelUtils.createAndDownloadExcel(response, smokeEventList, keys, ConstantsIot.SMOKE_EVENT_LIST_EXCEL_MODEL_FILE_PATH, 3,
                null, jsonObject, "1:0");
    }

    /**
     * 获取 烟感事件list
     *
     * @param smokeEvent SmokeEvent
     * @return List<SmokeEvent>
     */
    public List<SmokeEvent> getSmokeEventList(SmokeEvent smokeEvent) {
        if (smokeEvent.getBeginTime() != null){
            smokeEvent.setCreateTime(smokeEvent.getBeginTime());
        }

        if (smokeEvent.getCreateTime() == null){
            Date date = DateUtils.getDate();
            smokeEvent.setCreateTime(date);
            smokeEvent.setEndTime(date);
        }

        if (null == smokeEvent.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            smokeEvent.setOrderCols(orderCols);
            smokeEvent.setOrderDesc(true);
        }
        return smokeEventDao.findSmokeEventList(smokeEvent);
    }

}
