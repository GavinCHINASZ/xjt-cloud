package com.xjt.cloud.iot.core.service.impl.electrical;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.electricalFire.ElectricalFireEventDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireDevice;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireEvent;
import com.xjt.cloud.iot.core.entity.electrical.ElectricalFireReport;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireDeviceService;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceRelationService;
import com.xjt.cloud.iot.core.service.service.sendMessage.SendElectricalFireMsgService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 电气火灾
 *
 * @author dwt
 * @date 2020-09-11 14:03
 */
@Service
public class ElectricalFireDeviceServiceImpl extends AbstractService implements ElectricalFireDeviceService {

    @Autowired
    private ElectricalFireDeviceDao electricalFireDeviceDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private LinkageDeviceRelationService linkageDeviceRelationService;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private ElectricalFireEventDao electricalFireEventDao;
    @Autowired
    private ElectricalFireEventDao fireEventDao;
    @Autowired
    private SendElectricalFireMsgService messageService;

    /**
     * 根据条件查询电气火灾设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-09-11 13:53
     */
    @Override
    public Data findElectricalFireDevice(String json) {
        ElectricalFireDevice electricalFireDevice = JSONObject.parseObject(json, ElectricalFireDevice.class);
        String[] strArr = electricalFireDevice.getOrderCols();
        if (CollectionUtils.isNotEmpty(strArr)) {
            for (int i = 0; i < strArr.length; i++) {
                Arrays.fill(strArr, i, i + 1, "efd." + strArr[i]);
            }
            electricalFireDevice.setOrderCols(strArr);
        }
        List<ElectricalFireDevice> deviceList = electricalFireDeviceDao.findElectricalFireDeviceList(electricalFireDevice);
        Integer totalCount = electricalFireDeviceDao.findElectricalFireDeviceCount(electricalFireDevice);
        Map<String, Object> map = new HashMap<>(2);
        map.put("totalCount", totalCount);
        map.put("listObj", deviceList);
        return asseData(map);
    }

    /**
     * 保存电气火灾设备信息
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-09-11 13:56
     */
    @Override
    public Data saveElectricalFireDevice(String json) {
        ElectricalFireDevice electricalFireDevice = JSONObject.parseObject(json, ElectricalFireDevice.class);
        Integer count;
        Device device = new Device();

        boolean b1 = electricalFireDevice.getId() != null && electricalFireDevice.getId() != 0;
        boolean b = electricalFireDevice.getIds() != null && electricalFireDevice.getIds().length > 0;
        if (b1 || b) {
            ElectricalFireDevice fireDevice = new ElectricalFireDevice();
            fireDevice.setId(electricalFireDevice.getId());
            fireDevice = electricalFireDeviceDao.findElectricalFireDevice(fireDevice);
            // 用户ID
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            if (fireDevice != null) {
                device.setId(fireDevice.getDeviceId());
                deviceDao.modifyDeviceClearIot(device);
            }
            electricalFireDevice.setUpdateUserId(loginUserId);
            count = electricalFireDeviceDao.modifyElectricalFireDevice(electricalFireDevice);
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_ELECTRICAL_FIRE_DEVICE",
                    SecurityUserHolder.getUserName(), electricalFireDevice, null, electricalFireDevice.getProjectId(), 2);
        } else {
            count = electricalFireDeviceDao.saveElectricalFireDevice(electricalFireDevice);
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_ELECTRICAL_FIRE_DEVICE",
                    SecurityUserHolder.getUserName(), electricalFireDevice, null, electricalFireDevice.getProjectId(), 2);
        }

        if (count > 0) {
            // 修改设备关联物联网
            device.setId(electricalFireDevice.getDeviceId());
            device.setIotId(electricalFireDevice.getId());
            device.setSensorNo(electricalFireDevice.getSensorNo());
            device.setIotStatus(2);
            deviceDao.modifyDevice(device);
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 查询是否有相同的注册id
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-16 14:29
     */
    @Override
    public Data findSameElectricalDevice(String json) {
        ElectricalFireDevice fireDevice = JSONObject.parseObject(json, ElectricalFireDevice.class);
        fireDevice = electricalFireDeviceDao.findElectricalFireDevice(fireDevice);
        if (fireDevice != null) {
            return asseData(false);
        }
        return asseData(true);
    }

    /**
     * 解绑电气火灾设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-09-18 10:07
     */
    @Override
    public Data deleteElectricalDevice(String json) {
        ElectricalFireDevice fireDevice = JSONObject.parseObject(json, ElectricalFireDevice.class);
        List<ElectricalFireDevice> deviceList = electricalFireDeviceDao.findElectricalFireDeviceList(fireDevice);
        if (CollectionUtils.isNotEmpty(deviceList)) {
            // 用户ID
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            fireDevice.setUpdateUserId(loginUserId);
            fireDevice.setStatus(99);

            List<Long> deviceIds = new ArrayList<>(deviceList.size());
            for (ElectricalFireDevice f : deviceList) {
                deviceIds.add(f.getDeviceId());
            }

            Device device = new Device();
            device.setIds(deviceIds.toArray(new Long[]{}));
            electricalFireDeviceDao.modifyElectricalFireDevice(fireDevice);
            deviceDao.modifyDeviceClearIot(device);
            Data data = linkageDeviceRelationService.deleteDeviceTableByDeviceId(deviceIds.toArray(new Long[]{}));
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_ELECTRICAL_FIRE_DEVICE",
                    SecurityUserHolder.getUserName(), fireDevice, null, fireDevice.getProjectId(), 2);
            if (data != null && Integer.parseInt(data.getObject().toString()) > 0) {
                return asseData(600, "请将声光关联设备解绑！");
            }
        }
        return Data.isSuccess();
    }

    /**
     * 电气火灾设备和事件概览
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-09-21 11:39
     */
    @Override
    public Data deviceAndEventGeneralView(String json) {
        ElectricalFireReport electricalFireReport = JSONObject.parseObject(json, ElectricalFireReport.class);
        ElectricalFireReport deviceNumCount = electricalFireDeviceDao.findElectricalFireDeviceStatusCount(electricalFireReport);
        ElectricalFireReport eventNumCount = electricalFireEventDao.countElectricalFireReport(electricalFireReport);
        Map<String, Object> map = new HashMap<>(2);
        map.put("deviceNumCount", deviceNumCount);
        map.put("eventNumCount", eventNumCount);
        return asseData(map);
    }

    /**
     * 导出电气火灾设备列表
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author dwt
     * @date 2020-09-23 14:11
     */
    @Override
    public void downLoadElectricalFireDeviceList(String json, HttpServletResponse resp) {
        ElectricalFireDevice device = JSONObject.parseObject(json, ElectricalFireDevice.class);
        List<ElectricalFireDevice> deviceList = electricalFireDeviceDao.findElectricalFireDeviceList(device);
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, device.getProjectId());
        String projectName = projectJson.getString("projectName");
        jsonObject.put("title", projectName + "电气火灾设备列表");
        String[] keys = {"rowNum", "deviceName", "workStatusDesc", "leakageCurrValue", "tempValue", "deviceStatusDesc", "deviceLocation", "pointQrNo", "deviceQrNo"};
        ExcelUtils.createAndDownloadExcel(resp, deviceList, keys, ConstantsIot.ELECTRICAL_FIRE_DEVICE_LIST_MODEL_FIRE_PATH, 3, null, jsonObject, "1:0");
    }

    /**
     * 定时任务修改电气火灾设备在线离线状态
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-10-13 15:00
     */
    @Override
    public synchronized Data modifyDeviceStatus() {
        List<ElectricalFireDevice> deviceList = electricalFireDeviceDao.findAllElectricalFireDevice();
        if (CollectionUtils.isNotEmpty(deviceList)) {
            long diff;
            Integer sendMessage;
            ElectricalFireEvent event;
            Device device = new Device();
            for (ElectricalFireDevice fireDevice : deviceList) {
                // 1:已发送消息，2：未发送消息
                sendMessage = fireDevice.getSendMessage();
                if (fireDevice.getEndHeartbeatTime() != null) {
                    diff = (System.currentTimeMillis() - fireDevice.getEndHeartbeatTime().getTime()) / (1000 * 60 * 60);
                    // 工作状态 1:在线，2:离线
                    if (diff < 24) {
                        fireDevice.setWorkStatus(1);
                    } else {
                        fireDevice.setWorkStatus(2);
                        if (sendMessage == 2) {
                            event = new ElectricalFireEvent(fireDevice);
                            event.setEventType(4);
                            event.setRecoverStatus(2);
                            event.setHandleStatus(2);
                            fireEventDao.saveElectricalFireEvent(event);
                            messageService.sendFireMessage(event, fireDevice, 0);
                            fireDevice.setSendMessage(1);
                            device.setId(fireDevice.getDeviceId());
                            device.setIotStatus(2);
                            deviceDao.modifyDeviceIotStatus(device);
                        }
                    }
                }
                electricalFireDeviceDao.modifyElectricalFireWorkStatus(fireDevice);
            }
        }
        return Data.isSuccess();
    }

}
