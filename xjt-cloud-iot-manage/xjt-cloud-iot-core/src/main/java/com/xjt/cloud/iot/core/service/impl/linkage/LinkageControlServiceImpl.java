package com.xjt.cloud.iot.core.service.impl.linkage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.linkage.*;
import com.xjt.cloud.iot.core.entity.linkage.LinkageDevice;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.linkage.LinkageAction;
import com.xjt.cloud.iot.core.entity.linkage.LinkageControl;
import com.xjt.cloud.iot.core.entity.linkage.LinkageRequirement;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 联动控制 service实现类
 *
 * @author huanggc
 * @date 2019/09/19
 **/
@Service
public class LinkageControlServiceImpl extends AbstractService implements LinkageControlService {
    @Autowired
    private LinkageDeviceServiceImpl linkageDeviceService;
    @Autowired
    private LinkageControlDao linkageControlDao;
    @Autowired
    private LinkageActionDao linkageActionDao;
    @Autowired
    private LinkageRequirementDao linkageRequirementDao;
    @Autowired
    private LinkageDeviceDao linkageDeviceDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LinkageEventDao linkageEventDao;
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/19
     */
    @Override
    public Data findLinkageControlList(String json) {
        LinkageControl linkageDevice = JSONObject.parseObject(json, LinkageControl.class);
        List<LinkageControl> repairRecordList = linkageControlDao.findLinkageControlList(linkageDevice);

        Integer totalCount = linkageDevice.getTotalCount();
        Integer pageSize = linkageDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = linkageControlDao.findLinkageControlTotalCount(linkageDevice);
        }
        return asseData(totalCount, repairRecordList);
    }

    /**
     * 功能描述: 删除
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/26
     */
    @Override
    public Data deleteLinkageControl(String json) {
        LinkageControl linkageControl = JSONObject.parseObject(json, LinkageControl.class);
        int num = linkageControlDao.deleteLinkageControl(linkageControl);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述: 新增保存
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/26
     */
    @Override
    public Data saveLinkageControl(String json) {
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        LinkageControl linkageControl = JSONObject.parseObject(map.get("linkageControl").toString(), LinkageControl.class);
        // 条件
        List<LinkageRequirement> linkageRequirementList = JSONArray.parseArray(map.get("linkageRequirement").toString(), LinkageRequirement.class);
        // 动作
        List<LinkageAction> linkageActionList = JSONArray.parseArray(map.get("linkageAction").toString(), LinkageAction.class);

        String userName = SecurityUserHolder.getUserName();
        Long userId = 0L;
        if (StringUtils.isNotEmpty(userName)) {
            userId = getLoginUserId(userName);
        }

        linkageControl.setCreateUserName(userName);
        linkageControl.setCreateUserId(userId);
        int linkageControlNum = linkageControlDao.saveLinkageControl(linkageControl);
        if (linkageControlNum > 0) {
            // 新增成功
            Long linkageControlId = linkageControl.getId();
            for (LinkageAction linkageAction : linkageActionList) {
                linkageAction.setLinkageControlId(linkageControlId);
            }
            linkageActionDao.saveLinkageActions(linkageActionList);

            for (LinkageRequirement linkageRequirement : linkageRequirementList) {
                linkageRequirement.setLinkageControlId(linkageControlId);
            }
            linkageRequirementDao.saveLinkageRequirements(linkageRequirementList);
        }

        return Data.isSuccess();
    }

    /**
     * 功能描述: 启用 或 停止(开或关)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/30
     */
    @Override
    public Data openOrClose(String json) {
        LinkageControl linkageControl = JSONObject.parseObject(json, LinkageControl.class);
        int num = linkageControlDao.updateLinkageControl(linkageControl);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述: 进入"水系统联运控制"新增页面
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/10/22
     */
    @Override
    public Data addPage(String json) {
        // 只有 projectId
        Device device = JSONObject.parseObject(json, Device.class);
        List<String> deviceNameList = new ArrayList<>();
        deviceNameList.add("水位");
        deviceNameList.add("水压");
        deviceNameList.add("水浸");

        LinkageAction linkageAction = new LinkageAction();
        linkageAction.setProjectId(device.getProjectId());
        List<String> actionNameList = linkageDeviceDao.findNames(linkageAction);

        Map<String, Object> map = new HashMap<>(2);
        // 触发条件的设备类型
        map.put("deviceNameList", deviceNameList);
        // 触发动作的设备名称
        map.put("actionNameList", actionNameList);
        return asseData(map);
    }

    /**
     * 功能描述: 设备发生故障调用这个方法, 查询是否关联动设备,有关联的话联动设备报警
     *
     * @param deviceIds 设备ID数组
     * @param sign      别名:例 smoke_off_line_event 对应 m_msg_push_type 表sign
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/24
     */
    @Override
    public JSONObject deviceFaults(Long[] deviceIds, String sign) {
        return deviceFaultMethod(deviceIds, sign);
    }

    /**
     * 功能描述: 设备发生故障调用这个方法, 查询是否关联动设备,有关联的话联动设备报警
     *
     * @param deviceId 设备ID
     * @param sign     别名:例 smoke_off_line_event 对应 m_msg_push_type 表sign
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/24
     */
    @Override
    public JSONObject deviceFault(Long deviceId, String sign) {
        Long[] deviceIds = {deviceId};
        return deviceFaultMethod(deviceIds, sign);
    }

    /**
     * 功能描述: 设备发生故障调用这个方法, 查询是否关联动设备,有关联的话联动设备报警
     *
     * @param deviceIds 设备ID数组
     * @param sign      别名:例 smoke_off_line_event 对应 m_msg_push_type 表sign
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/24
     */
    private JSONObject deviceFaultMethod(Long[] deviceIds, String sign) {
        SysLog.info("deviceFault sign=" + sign);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(sign)) {
            return jsonObject;
        }

        try {
            LinkageDevice linkageDevice = new LinkageDevice();
            linkageDevice.setDeviceIds(deviceIds);
            List<LinkageDevice> linkageDeviceList = linkageDeviceDao.findLinkageDeviceAndRelation(linkageDevice);
            if (CollectionUtils.isNotEmpty(linkageDeviceList)) {
                /*
                 * 查询 声光通知 是否选中
                 */
                Long projectId = linkageDeviceList.get(0).getProjectId();
                jsonObject.put("projectId", projectId);
                // 别名,例;fireAlarm_fire_event
                jsonObject.put("sign", sign);
                // 推送类型 1、短信 2、语音, 3声光
                jsonObject.put("pushType", 3);
                String msgPushManageStr = HttpUtils.sendGet(ConstantsIot.FIND_MSG_PUSH_MANAGE_NUM_URL, "json=" + jsonObject.toJSONString());
                if (StringUtils.isNotEmpty(msgPushManageStr)) {
                    JSONObject object = JSONObject.parseObject(msgPushManageStr);
                    String pushManageStr = object.get("object").toString();
                    if (Integer.parseInt(pushManageStr) < 1) {
                        // 没有选中的 声光报警不响
                        return jsonObject;
                    }
                }

                Long[] linkageDeviceIds = new Long[linkageDeviceList.size()];
                List<LinkageDevice> linkageDevices = new ArrayList<>(linkageDeviceList.size());
                StringBuilder sbu;
                for (int i = 0; i < linkageDeviceList.size(); i++) {
                    LinkageDevice entity = linkageDeviceList.get(i);
                    linkageDeviceIds[i] = entity.getDeviceId();
                    entity.setEventType(2);
                    // 开
                    entity.setSountAction(2);
                    // 未恢复
                    entity.setRecoverStatus(2);
                    entity.setFaultDeviceId(entity.getFaultDeviceId());

                    LinkageDevice linkageEntity = new LinkageDevice();
                    linkageEntity.setRegId(ConstantsIot.LINKAGE_DEVICE_REG_ID + entity.getSensorId().split("sgbj")[1]);
                    switch (entity.getChannel()) {
                        case 1:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_1_CHANNEL_OPEN_MSG);
                            break;
                        case 2:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_2_CHANNEL_OPEN_MSG);
                            break;
                        case 3:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_3_CHANNEL_OPEN_MSG);
                            break;
                        default:
                            linkageEntity.setMsg(ConstantsIot.LINKAGE_DEVICE_4_CHANNEL_OPEN_MSG);
                            break;
                    }
                    linkageDevices.add(linkageEntity);

                    /*
                     * 声光报警通知
                     * 报警通知
                     * 【消检通】 项目名称/建筑物/位置+设备名称+巡检点ID+传感器ID：发生报警事件，请及时处理。
                     */
                    // 发消息给 项目下 声光管理权限的人
                    List<String> roleSignList = new ArrayList<>(1);
                    // 声光(联动)消息权限
                    roleSignList.add("linkage_manage_info");
                    JSONObject object = new JSONObject();
                    // 大屏消息弹杠需要用到buildingId
                    object.put("buildingId", entity.getBuildingId());

                    projectId = entity.getProjectId();
                    // 从缓存中取出项目对象
                    String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
                    sbu = new StringBuilder();
                    sbu.append("【")
                            .append(projectName)
                            .append("】")
                            .append(entity.getBuildingName())
                            .append(entity.getFloorName())
                            .append(" ")
                            .append(entity.getPointLocation())
                            .append(" ")
                            .append(entity.getDeviceName())
                            .append(" ")
                            .append(entity.getCheckPointQrNo())
                            .append(" 传感器ID:")
                            .append(entity.getSensorId())
                            .append(" 发生报警事件，请及时处理。");

                    messageService.saveMessageRole(15, roleSignList, "报警通知", 2, 0, sbu.toString(),
                            "url", projectId, entity.getId(), "data", object);
                }

                // 下发信息给联动设备
                SysLog.info("deviceFault 下发信息给联动设备");
                linkageDeviceService.sendMsgLinkageDevice(linkageDevices);
                // 新增 故障事件
                linkageEventDao.saveLinkageEventByDevice(linkageDeviceList);
                // 更新设备事件类型
                linkageDeviceDao.updateLinkageDeviceList(linkageDeviceList);

                // 修改设备关联物联网
                Device device = new Device();
                device.setIds(linkageDeviceIds);
                device.setIotStatus(2);
                deviceDao.modifyDeviceIotStatus(device);

                // PC声光报警页面webSocket
                jsonObject.put("msg", 200);
                jsonObject.put("iotType", ConstantsIot.LINKAGE_DEVICE);
                WebSocketSendMsgUtils.nettySendMsg(jsonObject);
            }
        }catch (Exception e){
            SysLog.error(e);
            return jsonObject;
        }
        return jsonObject;
    }

}
