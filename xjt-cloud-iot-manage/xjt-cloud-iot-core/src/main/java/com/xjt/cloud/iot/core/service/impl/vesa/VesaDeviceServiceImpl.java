package com.xjt.cloud.iot.core.service.impl.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceDao;
import com.xjt.cloud.iot.core.dao.iot.vesa.VesaDeviceRecordDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaDeviceReport;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceRelationService;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceRecordResolveService;
import com.xjt.cloud.iot.core.service.service.vesa.VesaDeviceService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wangzhiwen
 * @date 2019/9/20 15:09
 */
@Service
public class VesaDeviceServiceImpl extends AbstractService implements VesaDeviceService {
    @Autowired
    private VesaDeviceDao vesaDeviceDao;
    @Autowired
    private VesaDeviceRecordDao vesaDeviceRecordDao;
    @Autowired
    private VesaDeviceRecordResolveService vesaDeviceRecordResolveService;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private LinkageDeviceRelationService linkageDeviceRelationService;
    @Autowired
    private MessageService messageService;

    /**
     * 功能描述:检查传感器id是否存在
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data checkVesaDeviceSensorNo(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        vesaDevice = vesaDeviceDao.checkVesaDeviceSensorNo(vesaDevice);
        return asseData(vesaDevice);
    }

    /**
     * 功能描述:保存极早期设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data saveVesaDevice(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        VesaDevice temVesaDevice = vesaDeviceDao.checkVesaDeviceSensorNo(vesaDevice);
        if (temVesaDevice != null) {
            return asseData(Constants.FAIL_CODE, "传感器ID已存在");
        }

        Long projectId = vesaDevice.getProjectId();
        vesaDevice = setEntityUserIdName(SecurityUserHolder.getUserName(), projectId, vesaDevice);
        JSONObject jsonObject = CacheUtils.getCacheByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, vesaDevice.getDeviceTypeId() + "");
        String deviceType = jsonObject.getString("deviceType");
        if (StringUtils.isNotEmpty(deviceType)) {
            vesaDevice.setType(Integer.valueOf(deviceType));
        }
        int num = vesaDeviceDao.saveVesaDevice(vesaDevice);

        //修改设备关联物联网
        Device device = new Device();
        device.setId(vesaDevice.getDeviceId());
        device.setIotId(vesaDevice.getId());
        device.setSensorNo(vesaDevice.getSensorNo());
        deviceDao.modifyDevice(device);

        vesaDevice = setEntityUserIdName(SecurityUserHolder.getUserName(), projectId, vesaDevice);
        //vesaDeviceDao.saveVesaDeviceEdit(vesaDevice);
        securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_ADD_VESA_DEVICE, SecurityUserHolder.getUserName(), vesaDevice, null,
                projectId, Constants.SOURCE_PROJECT);
/*
        if (StringUtils.isNotEmpty(vesaDevice.getSensorNo2()) && num > 0){
            vesaDevice.setSensorNo(vesaDevice.getSensorNo2());
            num = vesaDeviceDao.saveVesaDevice(vesaDevice);
            //vesaDeviceDao.saveVesaDeviceEdit(vesaDevice);
            securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_ADD_WATER_DEVICE, SecurityUserHolder.getUserName(), vesaDevice, null,
                    projectId, Constants.SOURCE_PROJECT);
        }
*/
        return asseData(num);
    }

    /**
     * 功能描述:修改极早期设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data modifyVesaDevice(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        VesaDevice tempVesaDevice = vesaDeviceDao.findVesaDeviceById(vesaDevice);
        if (tempVesaDevice == null) {
            return asseData(Constants.FAIL_CODE, "该极早期设备ID不存在");
        }

        if (tempVesaDevice.getSensorNo().equals(vesaDevice.getSensorNo())) {
            tempVesaDevice.setSensorName(vesaDevice.getSensorName());
        } else {
            VesaDevice checkVesaDevice = vesaDeviceDao.checkVesaDeviceSensorNo(vesaDevice);
            if (checkVesaDevice != null) {
                return asseData(Constants.FAIL_CODE, "传感器ID已存在");
            }

            tempVesaDevice.setSensorNo(vesaDevice.getSensorNo());
            tempVesaDevice.setSensorName(vesaDevice.getSensorName());
        }

        int num = vesaDeviceDao.modifyVesaDevice(tempVesaDevice);

        securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_MODIFY_VESA_DEVICE, SecurityUserHolder.getUserName(), vesaDevice, tempVesaDevice,
                vesaDevice.getProjectId(), Constants.SOURCE_PROJECT);

        return asseData(num);
    }


    /**
     * 功能描述:删除极早期设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data delVesaDevice(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        List<VesaDevice> list = vesaDeviceDao.findVesaDeviceList(vesaDevice);

        vesaDevice.setStatus(99);
        int num = vesaDeviceDao.modifyVesaDevice(vesaDevice);

        if (CollectionUtils.isNotEmpty(list)) {
            Long[] ids = new Long[list.size()];
            int i = 0;
            for (VesaDevice vd : list) {
                ids[i] = vd.getDeviceId();
                i++;
            }

            // 修改设备关联物联网
            Device device = new Device();
            device.setIds(ids);
            deviceDao.modifyDeviceClearIot(device);

            Data data = linkageDeviceRelationService.deleteDeviceTableByDeviceId(ids);
            securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_DEL_VESA_DEVICE, SecurityUserHolder.getUserName(), vesaDevice, null,
                    vesaDevice.getProjectId(), Constants.SOURCE_PROJECT);
            if (data != null && Integer.parseInt(data.getObject().toString()) > 0) {
                return asseData(600, "请将声光关联设备解绑！");
            }
        }
        return asseData(num);
    }

    /**
     * 功能描述:查询极早期设备信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data findVesaDeviceList(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        Integer totalCount = vesaDevice.getTotalCount();
        Integer pageSize = vesaDevice.getPageSize();
        if (vesaDevice.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            vesaDevice.setOrderCols(orderCols);
            vesaDevice.setOrderDesc(true);
        }
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = vesaDeviceDao.findVesaDeviceListTotalCount(vesaDevice);
        }
        List<VesaDevice> list = vesaDeviceDao.findVesaDeviceList(vesaDevice);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询极早期设备汇总报表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findVesaDeviceSummaryReport(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        VesaDeviceReport vesaDeviceReport = vesaDeviceDao.findVesaDeviceSummaryReport(vesaDevice);
        return asseData(vesaDeviceReport);
    }

    /**
     * 功能描述:极早期设备离线任务
     *
     * @return Data
     * @author wangzhiwen
     * @date 2019/11/19 14:44
     */
    @Override
    public Data vesaOffLineTask() {
        VesaDevice vesaDevice = new VesaDevice();
        //从数据词典中得到计算的开始值
        Integer offLineTime = Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.OFF_LINE_TIME, ConstantsIot.OFF_LINE_TIME_IOT_VESA));
        Date currentDate = DateUtils.getCurrentDate();

        List<VesaDevice> list = vesaDeviceDao.findVesaDeviceList(vesaDevice);

        if (CollectionUtils.isNotEmpty(list)) {
            Date heartbeatTime;
            Device device = new Device();
            List<String> signList = new ArrayList<>(1);
            signList.add(ConstantsIot.vesaRoleSignListOffline);

            for (int i = 0; i < list.size(); i++) {
                vesaDevice = list.get(i);
                heartbeatTime = vesaDevice.getEndHeartbeatTime();

                if (heartbeatTime == null) {
                    continue;
                }

                int mins = DateUtils.minuteBetween(heartbeatTime, currentDate);
                Integer deviceStatus = vesaDevice.getDeviceStatus();

                // 离线处理
                if (mins >= offLineTime && deviceStatus == 1) {
                    VesaRecord newRecord;
                    newRecord = new VesaRecord();
                    newRecord.setProjectId(vesaDevice.getProjectId());
                    newRecord.setDeviceId(vesaDevice.getDeviceId());
                    newRecord.setSensorNo(vesaDevice.getSensorNo());
                    newRecord.setSensorName(vesaDevice.getSensorName());
                    newRecord.setVesaId(vesaDevice.getId());
                    newRecord.setPosition(vesaDevice.getPointLocation());
                    // 离线事件
                    newRecord.setEventType(5);
                    // 新产生的事件
                    newRecord.setRecordType(0);
                    newRecord.setCreateTime(currentDate);
                    newRecord.setDeviceStatus(2);
                    vesaDeviceRecordResolveService.saveVesaInfo(vesaDevice, newRecord);
                    device.setIotStatus(2);
                    device.setId(vesaDevice.getDeviceId());
                    deviceDao.modifyDeviceIotStatus(device);

                    // Eg:【消检通大厦项目】时间+位置+设备名称+传输装置名称已离线，请前往【火警主机】查看详情。

                    String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, vesaDevice.getProjectId(), "projectName");

                    JSONObject jsonObject = new JSONObject(5);
                    jsonObject.put("projectName", projectName);
                    jsonObject.put("event", "已离线");
                    jsonObject.put("buildingId", vesaDevice.getBuildingId());
                    jsonObject.put("buildingName", vesaDevice.getBuildingNameDesc());
                    jsonObject.put("floorName", vesaDevice.getBuildingFloorNameDesc());

                    // 组装要发送的信息内容
                    String content = "【" + projectName + "】" + vesaDevice.getBuildingNameDesc() + vesaDevice.getBuildingFloorNameDesc()
                            + vesaDevice.getPointLocation() + " " + vesaDevice.getDeviceSysName() + " " + vesaDevice.getSensorNo()
                            + " 已离线,请前往【极早期预警】查看详情。";

                    messageService.saveMessageRole(13, signList, "离线通知", 5, 0, content, ConstantsIot.VESA_MSG_URL,
                            vesaDevice.getProjectId(), null, null, jsonObject);
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 判断注册码是否存在
     *
     * @param transDeviceId 任务设备ID
     * @return JSONObject
     * @author dwt
     * @date 2019-11-26 16:52
     */
    @Override
    public JSONObject isSensorPresence(String transDeviceId) {
        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("code", 200);
        jsonObject.put("msg", "success");
        VesaDevice vesaDevice = new VesaDevice();
        vesaDevice.setSensorNo(transDeviceId);
        VesaDevice device = vesaDeviceDao.findVesaDevice(vesaDevice);
        if (device != null) {
            jsonObject.put("isPresence", "true");
            return jsonObject;
        }
        jsonObject.put("isPresence", "false");
        return jsonObject;
    }

    /**
     * 功能描述:查询极早期设备信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data findVesaDeviceListApp(String json) {
        VesaDevice vesaDevice = JSONObject.parseObject(json, VesaDevice.class);
        Integer totalCount = vesaDevice.getTotalCount();
        Integer pageSize = vesaDevice.getPageSize();
        if (vesaDevice.getOrderCols() == null) {
            String[] orderCols = {"sensorName"};
            vesaDevice.setOrderCols(orderCols);
            vesaDevice.setOrderDesc(true);
        }
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = vesaDeviceDao.findVesaDeviceListTotalCount(vesaDevice);
        }

        List<VesaDevice> list = vesaDeviceDao.findVesaDeviceListApp(vesaDevice);

        //查询在线设备数
        vesaDevice.setDeviceStatus(1);
        Integer onlineCount = vesaDeviceDao.findVesaDeviceListTotalCount(vesaDevice);
        Date endTime = vesaDevice.getEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            vesaDevice.setEndTime(c.getTime());
        }
        VesaDeviceReport vesaDeviceReport = vesaDeviceRecordDao.findVesaEventNumSum(vesaDevice);
        Map<String, Object> map = new HashMap<>(4);
        map.put("totalCount", totalCount);
        map.put("onlineCount", onlineCount);
        map.put("listObj", list);
        map.put("eventItem", vesaDeviceReport);
        return asseData(map);
    }

}
