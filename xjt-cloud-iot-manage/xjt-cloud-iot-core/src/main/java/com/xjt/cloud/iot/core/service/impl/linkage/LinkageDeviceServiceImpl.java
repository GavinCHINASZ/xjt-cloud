package com.xjt.cloud.iot.core.service.impl.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.NettySocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageDeviceRelationDao;
import com.xjt.cloud.iot.core.dao.iot.linkage.LinkageEventDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.linkage.*;
import com.xjt.cloud.iot.core.entity.project.Building;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联动设备 service实现类
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@Service
public class LinkageDeviceServiceImpl extends AbstractService implements LinkageDeviceService {
    @Autowired
    private LinkageDeviceDao linkageDeviceDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private LinkageDeviceRelationDao linkageDeviceRelationDao;
    @Autowired
    protected MessageService messageService;
    @Autowired
    private LinkageEventDao linkageEventDao;

    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/17
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findLinkageDeviceList(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);

        Integer totalCount = linkageDevice.getTotalCount();
        Integer pageSize = linkageDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = linkageDeviceDao.findLinkageDeviceTotalCount(linkageDevice);
        }

        if (null == linkageDevice.getOrderCols()){
            // 排序的字段
            String[] orderCols = {"sountAction", "lastModifyTime"};
            linkageDevice.setOrderCols(orderCols);
            linkageDevice.setOrderDesc(true);
        }
        List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceList(linkageDevice);
        return asseData(totalCount, linkageDeviceList);
    }

    /**
     * 功能描述:查询 联动设备 汇总
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/17
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findLinkageDeviceSummary(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);
        LinkageDeviceReport linkageDeviceReport = linkageDeviceDao.findLinkageDeviceSummary(linkageDevice);
        return asseData(linkageDeviceReport);
    }

    /**
     * 查询app首页联动设备信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return JSONObject
     */
    @Override
    public JSONObject findUserProjectLinkageData(String json){
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);
        LinkageDeviceReport linkageDeviceReport = linkageDeviceDao.findLinkageDeviceSummary(linkageDevice);

        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("modelIndex", 28);
        if (linkageDeviceReport != null) {
            jsonObject.put("failDevice", linkageDeviceReport.getFailDevice());
            jsonObject.put("offLine", linkageDeviceReport.getOffLine());
        }else{
            Integer total = linkageDeviceDao.findLinkageDeviceTotalCount(linkageDevice);
            if(total == null || total == 0){
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("failDevice", 0);
            jsonObject.put("offLine", 0);
        }
        return jsonObject;
    }

    /**
     * 功能描述:导出 联动设备 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/17
     */
    @Override
    public void downLinkageDeviceList(String json, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);

        if (null == linkageDevice.getOrderCols()){
            String[] orderCols = {"sountAction", "lastModifyTime"};
            linkageDevice.setOrderCols(orderCols);
            linkageDevice.setOrderDesc(true);
        }
        List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceList(linkageDevice);
        if (CollectionUtils.isEmpty(linkageDeviceList)){
            return ;
        }

        Long projectId = linkageDevice.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--声光报警表");

        String[] keys = {"rowNum", "checkPointName", "checkPointQrNo", "sensorId", "sountActionDesc", "eventTypeDesc", "endHeartbeatTimeDesc", "signalValue",
                "pointLocationDesc"};

        ExcelUtils.createAndDownloadExcel(response, linkageDeviceList, keys, ConstantsIot.LINKAGE_DEVICE_EXCEL_MODEL_FILE_PATH, 3, null,
                jsonObject, "1:0");
    }

    /**
     * 功能描述:删除(解绑) 联动设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/20
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deleteLinkageDevice(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);

        Long[] ids = linkageDevice.getIds();
        Integer deleteLinkageDeviceNum = linkageDeviceDao.deleteLinkageDevice(ids);
        if (deleteLinkageDeviceNum > 0){
            // 删除已关联的设备
            LinkageDeviceRelation linkageDeviceRelation = new LinkageDeviceRelation();
            linkageDeviceRelation.setLinkageDeviceIds(ids);
            linkageDeviceRelationDao.deleteLinkageRelationDevice(linkageDeviceRelation);

            Long[] deviceIdArray = linkageDeviceDao.findDeviceIdList(ids);

            // 修改设备关联物联网
            Device device = new Device();
            device.setIds(deviceIdArray);
            deviceDao.modifyDeviceClearIot(device);

            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述:绑定声光联动 设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveLinkageDevice(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);

        linkageDevice.setEventType(1);
        linkageDevice.setDeviceState(2);
        linkageDevice.setSountAction(1);
        linkageDevice.setSignalValue(5);
        linkageDevice.setElectricQuantity(10000);
        Integer saveLinkageDeviceNum = linkageDeviceDao.saveLinkageDevice(linkageDevice);
        if (saveLinkageDeviceNum > 0){
            // 更新设备表 device_manage.d_device 中的 iot_id 相关数据
            // 修改 设备关联物联网
            Device device = new Device();
            device.setId(linkageDevice.getDeviceId());
            device.setIotId(linkageDevice.getId());
            device.setSensorNo(linkageDevice.getSensorId());
            device.setIotStatus(1);
            deviceDao.modifyDevice(device);

            if (CollectionUtils.isNotEmpty(linkageDevice.getLinkageDeviceRelationList())){
                linkageDeviceRelationDao.saveLinkageDeviceRelationList(linkageDevice.getId(), linkageDevice.getLinkageDeviceRelationList());
            }
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述:联动设备--更新
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/08/20
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateLinkageDevice(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);
        if (linkageDevice.getSountAction() != null && linkageDevice.getSountAction() == 1){
            LinkageDevice linkageDeviceEntity = new LinkageDevice();
            LinkageEvent linkageEvent = new LinkageEvent();
            Device device = new Device();
            if (linkageDevice.getIds() != null){
                // 批量消音
                linkageDeviceEntity.setIds(linkageDevice.getIds());
                linkageEvent.setLinkageDeviceIds(linkageDevice.getIds());
                device.setIotIds(linkageDevice.getIds());
            }else {
                // 消音
                linkageDeviceEntity.setId(linkageDevice.getId());
                linkageEvent.setLinkageDeviceId(linkageDevice.getId());
                device.setIotId(linkageDevice.getId());
            }

            List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceAndRelation(linkageDeviceEntity);
            if (CollectionUtils.isNotEmpty(linkageDeviceList)){
                List<LinkageDevice> linkageDevices = new ArrayList<>(linkageDeviceList.size());
                for (LinkageDevice entity : linkageDeviceList) {
                    LinkageDevice linkageEntity = new LinkageDevice();
                    linkageEntity.setRegId(ConstantsIot.LINKAGE_DEVICE_REG_ID + entity.getSensorId().split("sgbj")[1]);
                    switch (entity.getChannel()){
                        case 1:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_ON_MSG);
                            break;
                        case 2:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_ON_MSG);
                            break;
                        case 3:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_ON_MSG);
                            break;
                        default:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_ON_MSG);
                            break;
                    }
                    linkageDevices.add(linkageEntity);
                }

                SysLog.info("updateLinkageDevice 下发信息给联动设备");
                // 发送消息给设备
                sendMsgLinkageDevice(linkageDevices);
            }

            // 报警事件 更新
            Long projectId = linkageDevice.getProjectId();
            String userName = SecurityUserHolder.getUserName();
            Long userId = getLoginUserId(userName);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("projectId", projectId);
            jsonObject.put("userId", userId);
            try {
                // 项目内 成员名称
                userName = HttpUtils.sendGet(ConstantsIot.FIND_ORG_USER_NAME_URL, "json=" + jsonObject.toJSONString());
            }catch (Exception e){
                userName = "/";
            }
            linkageEvent.setRecoverStatus(1);
            linkageEvent.setUpdateUserName(userName);
            linkageEvent.setUpdateUserId(userId);
            linkageEventDao.updateLinkageEvent(linkageEvent);

            // 消音后状态恢复 正常
            linkageDevice.setEventType(1);
            // 修改设备关联物联网
            device.setIotStatus(1);
            device.setDeviceType(20);
            deviceDao.modifyDeviceIotStatus(device);
        }

        int num = linkageDeviceDao.updateLinkageDevice(linkageDevice);
        if (CollectionUtils.isNotEmpty(linkageDevice.getLinkageDeviceRelationList())){
            // 先删除关联设备
            LinkageDeviceRelation linkageDeviceRelation = new LinkageDeviceRelation();
            linkageDeviceRelation.setLinkageDeviceId(linkageDevice.getId());
            linkageDeviceRelationDao.deleteLinkageRelationDevice(linkageDeviceRelation);
            // 增加 关联的设备
            linkageDeviceRelationDao.saveLinkageDeviceRelationList(linkageDevice.getId(), linkageDevice.getLinkageDeviceRelationList());
        }

        if (num > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述:联动设备--点击"新增"按钮跳转"联动设备新增"页面
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data jumpNewPage(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        List<String> deviceNameList = deviceDao.findDeviceName(device);
        return asseData(deviceNameList);
    }

    /**
     * 功能描述:联动设备--根据"设备名称"查询"设备ID"
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findQrNo(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        List<String> qrNoList = deviceDao.findQrNo(device);
        return asseData(qrNoList);
    }

    /**
     * 功能描述:查询 联动设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/10/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findLinkageDevice(String json) {

        return null;
    }

    /**
     * 校验联动设备是否在线离线 定时任务
     *
     * @author zhangZaiFa
     * @date 2020/3/31 10:30
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data linkageDeviceOffLineTask() {
        SysLog.info("linkageDeviceOffLineTask 校验联动设备是否在线离线");
        try {
            // 从数据词典中得到声光离线时间
            Integer date = Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.OFF_LINE_TIME, ConstantsIot.OFF_LINE_TIME_IOT_LINKAGE));
            // 查询出离线的声光报警设备
            List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceOffLineTaskList(date);

            // 修改联动设备离线状态
            linkageDeviceDao.updateLinkageDeviceOffLineStatus(date);

            if (CollectionUtils.isNotEmpty(linkageDeviceList)){
                Long projectId;
                StringBuilder sbu;
                String projectName;
                for (LinkageDevice entity : linkageDeviceList) {
                    entity.setEventType(3);
                    entity.setRecoverStatus(2);
                    entity.setFaultDeviceId(entity.getDeviceId());

                    // 发消息给 项目下 声光管理权限的人
                    List<String> roleSignList = new ArrayList<>(1);
                    // 声光(联动)离线权限
                    roleSignList.add("linkage_manage_offline");

                    JSONObject object = new JSONObject();
                    // 大屏消息弹杠需要用到buildingId
                    object.put("buildingId", entity.getBuildingId());

                    projectId = entity.getProjectId();
                    // 从缓存中取出项目对象
                    projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
                    sbu = new StringBuilder();
                        sbu.append("【")
                            .append(projectName)
                            .append("】")
                            .append(entity.getBuildingName())
                            .append("/")
                            .append(entity.getFloorName())
                            .append(" ")
                            .append(entity.getPointLocation())
                            .append(" ")
                            .append(entity.getDeviceName())
                            .append(" ")
                            .append(entity.getCheckPointQrNo())
                            .append(" 传感器ID:")
                            .append(entity.getSensorId())
                            .append(" 发生离线事件，请及时处理。");

                    messageService.saveMessageRole(15, roleSignList, "离线通知",3, 0, sbu.toString(),
                            "url", projectId, null, "data", object);
                }

                // 新增 离线事件
                linkageEventDao.saveLinkageEventByDevice(linkageDeviceList);
            }
        }catch (Exception e){
            SysLog.error(e.fillInStackTrace());
            return  Data.isFail();
        }
        return Data.isSuccess();
    }

    /**
     * 传感器ID是否存在
     *
     * @param sensorId 传感器ID
     * @author zhangZaiFa
     * @date 2020/3/31 11:40
     * @return JSONObject
     **/
    @Override
    public JSONObject isSensorPresence(String sensorId) {
        SysLog.info("isSensorPresence传感器ID是否存在=" + sensorId);
        LinkageDevice linkageDevice = new LinkageDevice();
        linkageDevice.setSensorId(sensorId);
        LinkageDevice linkageDevices = linkageDeviceDao.findLinkageDevice(linkageDevice);

        JSONObject jsonObject = new JSONObject();
        if(linkageDevices != null){
            jsonObject.put("isPresence", "true");
            return jsonObject;
        }

        jsonObject.put("code", 200);
        jsonObject.put("msg", "success");
        jsonObject.put("isPresence", "false");
        return jsonObject;
    }

    /**
     * 声光报警
     * 联动设备信息数据解析(包含心跳上传的消息)
     *
     * @param data JSONObject
     * @author zhangZaiFa
     * @date 2020/3/31 11:41
     * @return java.util.List<JSONObject> JSONObject
     **/
    @Override
    public List<JSONObject> linkageDeviceAnalyze(JSONObject data) {
        SysLog.info("联动设备信息数据解析date=" + data);

        // msgType=LinkageDevice  from=$$_LINKAGE_DEVICE_0001
        String deviceSensorId = data.getString("from");
        // 例: 11050000FF008EAA
        String msg = data.getString("msg");
        String sensorId = "sgbj" + deviceSensorId.split("\\$\\$_LINKAGE_DEVICE_")[1];
        linkageDeviceDao.updateLinkageDeviceEndHeartbeatTime(sensorId);

        // 关闭声光报警失败 或 打开声光报警失败 处理  msg=1105000100009E9A   msg=114500000008020000AD0F
        if (StringUtils.isNotEmpty(msg)){
            if (msg.equals(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_ON_MSG) || msg.equals(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_ON_MSG)
                || msg.equals(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_ON_MSG) || msg.equals(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_ON_MSG)
                || msg.equals(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_OPEN_MSG) || msg.equals(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_OPEN_MSG)
                || msg.equals(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_OPEN_MSG) || msg.equals(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_OPEN_MSG)){

                SysLog.info("关闭声光报警失败 或 打开声光报警失败 处理msg=" + msg);
                LinkageDevice linkageDevice = new LinkageDevice();
                linkageDevice.setSensorId(sensorId);

                int channel = 0;
                // 消音:1关,2开
                int sountAction = 0;
                if (msg.equals(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_ON_MSG)) {
                    channel = 1;
                    sountAction = 1;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_ON_MSG)) {
                    channel = 2;
                    sountAction = 1;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_ON_MSG)) {
                    channel = 3;
                    sountAction = 1;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_ON_MSG)) {
                    channel = 4;
                    sountAction = 1;

                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_OPEN_MSG)) {
                    channel = 1;
                    sountAction = 2;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_OPEN_MSG)) {
                    channel = 2;
                    sountAction = 2;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_OPEN_MSG)) {
                    channel = 3;
                    sountAction = 2;
                } else if (msg.equals(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_OPEN_MSG)) {
                    channel = 4;
                    sountAction = 2;
                }
                LinkageDevice entity = linkageDeviceDao.findLinkageDevice(linkageDevice);

                if (null != entity && sountAction != entity.getSountAction()){
                    linkageDevice.setEventType(sountAction == 1 ? 1 : 2);
                    linkageDevice.setSountAction(sountAction == 1 ? 1 : 2);
                    linkageDevice.setId(entity.getId());
                    linkageDevice.setProjectId(entity.getProjectId());
                    linkageDeviceDao.updateLinkageDevice(linkageDevice);
                }
            }else if (msg.startsWith(ConstantsIot.LINKAGE_DEVICE_REG_ID)){
                // msg=$$_LINKAGE_DEVICE_0001 这种心跳,不能判断 通道 是否打开,就恢复设备
                LinkageDevice linkageDevice = new LinkageDevice();
                linkageDevice.setSountAction(1);
                linkageDevice.setEventType(1);
                linkageDevice.setSensorId(sensorId);
                linkageDeviceDao.updateLinkageDeviceBySensorId(linkageDevice);
            }
        }

        updateLinkageEvent(new LinkageEvent(), sensorId);

        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("msg", 200);
        jsonObject.put("code", 200);
        jsonObject.put("iotType", ConstantsIot.LINKAGE_DEVICE);

        List<JSONObject> jsonList = new ArrayList<>(3);
        jsonList.add(0, jsonObject);
        jsonList.add(1, jsonObject);
        jsonList.add(2, jsonObject);
        return jsonList;
    }

    /**
     * 只恢复离线的事件
     *
     * @param linkageEvent LinkageEvent
     * @param sensorId 传感器ID
     */
    private void updateLinkageEvent(LinkageEvent linkageEvent, String sensorId) {
        // 只恢复离线的事件
        linkageEvent.setRecoverStatus(1);
        linkageEvent.setEventType(3);
        linkageEvent.setSensorId(sensorId);
        linkageEventDao.updateLinkageEvent(linkageEvent);
    }

    /**
     * 功能描述:查询 声光报警设备所在的建筑物
     *
     * @author huanggc
     * @date 2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findLinkageDeviceBuilding(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);
        List<Building> buildingList = linkageDeviceDao.findlinkageDeviceBuilding(linkageDevice);
        return asseData(buildingList);
    }

    /**
     * 功能描述:查询 声光报警设备所有的巡查点
     *
     * @author huanggc
     * @date 2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findLinkageDeviceCheckPoint(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);
        List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceCheckPoint(linkageDevice);
        return asseData(linkageDeviceList);
    }

    /**
     * 功能描述:查询单个 声光报警设备 及 关联的设备
     *
     * @author huanggc
     * @date 2020/08/26
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findLinkageDeviceView(String json) {
        LinkageDevice linkageDevice = JSONObject.parseObject(json, LinkageDevice.class);

        LinkageDeviceRelation linkageDeviceRelation;
        if (linkageDevice.getLinkageDeviceRelation() != null){
            linkageDeviceRelation = linkageDevice.getLinkageDeviceRelation();
        }else {
            linkageDeviceRelation = new LinkageDeviceRelation();
        }

        linkageDevice = linkageDeviceDao.findLinkageDevice(linkageDevice);

        linkageDeviceRelation.setLinkageDeviceId(linkageDevice.getId());
        List<LinkageDeviceRelation> linkageDeviceRelationList = linkageDeviceRelationDao.findLinkageDeviceRelationList(linkageDeviceRelation);

        Map<String, Object> map = new HashMap<>(2);
        map.put("linkageDevice", linkageDevice);
        map.put("linkageDeviceRelationList", linkageDeviceRelationList);
        return asseData(map);
    }

    /**
     * 功能描述:下发信息给联动设备
     *
     * @param list List<LinkageDevice>
     * @author zZaiFa
     * @date 2020/3/31 17:15
     * @return com.xjt.cloud.commons.utils.Data
     **/
    public Data sendMsgLinkageDevice(List<LinkageDevice> list){
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                SysLog.info("sendMsgLinkageDevice list="+list.toString());
                for (LinkageDevice ld: list) {
                    NettySocketSendMsgUtils.nettySendMsg((JSONObject) JSONObject.toJSON(ld));
                }
            }
        }catch (Exception e){
            SysLog.error(e.fillInStackTrace());
            return Data.isFail();
        }
        return Data.isSuccess();
    }

}
