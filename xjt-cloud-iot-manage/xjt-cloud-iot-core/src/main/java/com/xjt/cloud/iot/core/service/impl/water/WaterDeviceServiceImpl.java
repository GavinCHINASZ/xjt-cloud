package com.xjt.cloud.iot.core.service.impl.water;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.water.WaterDeviceDao;
import com.xjt.cloud.iot.core.entity.device.Device;
import com.xjt.cloud.iot.core.entity.water.WaterDevice;
import com.xjt.cloud.iot.core.entity.water.WaterDeviceReport;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceRecordService;
import com.xjt.cloud.iot.core.service.service.water.WaterDeviceService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author wangzhiwen
 * @date 2019/9/20 15:09
 */
@Service
public class WaterDeviceServiceImpl extends AbstractService implements WaterDeviceService {

    @Autowired
    private WaterDeviceDao waterDeviceDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private WaterDeviceRecordService waterDeviceRecordService;
    @Autowired
    private LinkageControlService linkageControlService;

    /**
     * 功能描述:检查传感器id是否存在
     *
     * @param json 参数 json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data checkWaterDeviceSensorNo(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        waterDevice = waterDeviceDao.checkWaterDeviceSensorNo(waterDevice);
        return asseData(waterDevice);
    }

    /**
     * 功能描述:保存水压设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data saveWaterDevice(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        WaterDevice temWaterDevice = waterDeviceDao.checkWaterDeviceSensorNo(waterDevice);
        if (temWaterDevice != null) {
            return asseData(Constants.FAIL_CODE, "传感器ID已存在!");
        }

        if (waterDevice.getCheckPointId() == null && StringUtils.isNotEmpty(waterDevice.getPointQrNo())) {
            Long checkPointId = deviceDao.findCheckPointId(waterDevice.getPointQrNo());
            if (checkPointId == null) {
                return asseData(Constants.FAIL_CODE, "巡检码不存在!");
            }
            waterDevice.setCheckPointId(checkPointId);
        }
        Long projectId = waterDevice.getProjectId();
        waterDevice = setEntityUserIdName(SecurityUserHolder.getUserName(), projectId, waterDevice);
        JSONObject jsonObject = CacheUtils.getCacheByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, waterDevice.getDeviceTypeId() + "");
        String deviceType = jsonObject.getString("deviceType");
        waterDevice.setUnit(jsonObject.getString("unit"));
        if (StringUtils.isNotEmpty(deviceType)) {
            waterDevice.setType(Integer.valueOf(deviceType));
        }
        waterDevice.setWaterTerminalId(0L);
        int num = waterDeviceDao.saveWaterDevice(waterDevice);
        // 修改设备关联物联网
        Device device = new Device();
        device.setId(waterDevice.getDeviceId());
        device.setIotId(waterDevice.getId());
        device.setSensorNo(waterDevice.getSensorNo() + (StringUtils.isNotEmpty(waterDevice.getSensorNo2()) ? "," + waterDevice.getSensorNo2() : ""));
        deviceDao.modifyDevice(device);

        // 判断是否有系统信息,并保存
        WaterDevice waterDevice2 = new WaterDevice();
        if (StringUtils.isNotEmpty(waterDevice.getSensorNo2()) && num > 0) {
            waterDevice2.setSensorNo(waterDevice.getSensorNo2());
            waterDevice2.setMaxValue(waterDevice.getMaxValue2());
            waterDevice2.setMinValue(waterDevice.getMinValue2());
            waterDevice2.setWaterTerminalId(waterDevice.getId());
            waterDevice2.setDeviceTypeId(waterDevice.getDeviceTypeId());
            waterDevice2.setProjectId(projectId);
            waterDevice2.setCheckPointId(waterDevice.getCheckPointId());
            waterDevice2.setDeviceId(waterDevice.getDeviceId());
            waterDevice2.setCreateUserName(waterDevice.getCreateUserName());
            waterDevice2.setCreateUserId(waterDevice.getCreateUserId());
            waterDevice2.setType(waterDevice.getType());
            waterDevice2.setWaterTerminalId(waterDevice.getId());
            waterDevice2.setUnit(waterDevice.getUnit());
            num = waterDeviceDao.saveWaterDevice(waterDevice2);
            waterDeviceDao.saveWaterDeviceEdit(waterDevice2);
            securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_ADD_WATER_DEVICE, SecurityUserHolder.getUserName(), waterDevice2, null,
                    projectId, Constants.SOURCE_PROJECT);
        }
        waterDeviceDao.saveWaterDeviceEdit(waterDevice);
        securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_ADD_WATER_DEVICE, SecurityUserHolder.getUserName(), waterDevice, null,
                projectId, Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     * 功能描述:修改水压设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data modifyWaterDevice(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        WaterDevice oldWaterDevice = null;

        JSONObject jsonObject = CacheUtils.getCacheByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, waterDevice.getDeviceTypeId() + "");

        String deviceType = jsonObject != null ? jsonObject.getString("deviceType") : "";
        waterDevice.setUnit(jsonObject != null ? jsonObject.getString("unit") : "");
        if (StringUtils.isNotEmpty(deviceType)) {
            waterDevice.setType(Integer.valueOf(deviceType));
        }
        if (waterDevice.getId() != null) {
            oldWaterDevice = new WaterDevice();
            oldWaterDevice.setId(waterDevice.getId());
            oldWaterDevice = waterDeviceDao.findWaterDevice(oldWaterDevice);
            // 判断是否有修改传感器id，id是否存在
            if (StringUtils.isNotEmpty(waterDevice.getSensorNo()) && null != oldWaterDevice &&
                    !waterDevice.getSensorNo().equals(oldWaterDevice.getSensorNo())) {
                WaterDevice temWaterDevice = new WaterDevice();
                temWaterDevice.setSensorNo(waterDevice.getSensorNo());
                temWaterDevice = waterDeviceDao.checkWaterDeviceSensorNo(temWaterDevice);
                if (temWaterDevice != null) {
                    return asseData(Constants.FAIL_CODE, "传感器ID已存在!");
                }
            }
        }
        int num;
        waterDevice = setEntityUserIdName(SecurityUserHolder.getUserName(), waterDevice.getProjectId(), waterDevice);
        if (StringUtils.isEmpty(waterDevice.getSensorNo())) {
            waterDevice.setSensorNoNull(true);
        }
        // 判断老传感器信息是否存在，如不存在，则新增
        if (null == oldWaterDevice) {
            num = waterDeviceDao.saveWaterDeviceEdit(waterDevice);
            waterDevice.setId(waterDevice.getId());
        } else {
            num = waterDeviceDao.modifyWaterDevice(waterDevice);
            securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_MODIFY_WATER_DEVICE, SecurityUserHolder.getUserName(), waterDevice, oldWaterDevice,
                    waterDevice.getProjectId(), Constants.SOURCE_PROJECT);
        }

        Long waterTerminalId = waterDevice.getId();

        waterDeviceDao.saveWaterDeviceEdit(waterDevice);

        // 判断是否有系统端
        if (waterDevice.getId2() != null || StringUtils.isNotEmpty(waterDevice.getSensorNo2())) {
            WaterDevice waterDevice2 = new WaterDevice();
            oldWaterDevice = null;
            if (waterDevice.getId2() != null) {
                waterDevice2.setId(waterDevice.getId2());
                oldWaterDevice = waterDeviceDao.findWaterDevice(waterDevice2);
                // 判断是否有修改传感器id，id是否存在
                if (StringUtils.isNotEmpty(waterDevice.getSensorNo2()) && null != oldWaterDevice && !waterDevice.getSensorNo2().equals(oldWaterDevice.getSensorNo())) {
                    WaterDevice temWaterDevice = new WaterDevice();
                    temWaterDevice.setSensorNo(waterDevice.getSensorNo2());
                    temWaterDevice = waterDeviceDao.checkWaterDeviceSensorNo(temWaterDevice);
                    if (temWaterDevice != null) {
                        return asseData(Constants.FAIL_CODE, "传感器ID已存在!");
                    }
                }
            }
            waterDevice2.setSensorNo(waterDevice.getSensorNo2());
            if (StringUtils.isEmpty(waterDevice2.getSensorNo())) {
                waterDevice2.setSensorNoNull(true);
            }
            waterDevice2.setMaxValue(waterDevice.getMaxValue2());
            waterDevice2.setMinValue(waterDevice.getMinValue2());
            waterDevice2.setProjectId(waterDevice.getProjectId());
            waterDevice2.setEditRecordId(waterDevice.getId());
            waterDevice2.setUnit(waterDevice.getUnit());

            waterDevice2 = setEntityUserIdName(SecurityUserHolder.getUserName(), waterDevice2.getProjectId(), waterDevice2);
            // 判断老传感器信息是否存在，如不存在，则新增
            if (null == oldWaterDevice) {
                waterDevice2.setWaterTerminalId(waterTerminalId);
                waterDevice2.setDeviceTypeId(waterDevice.getDeviceTypeId());
                waterDevice2.setProjectId(waterDevice.getProjectId());
                waterDevice2.setCheckPointId(waterDevice.getCheckPointId());
                waterDevice2.setDeviceId(waterDevice.getDeviceId());
                waterDevice2.setType(waterDevice.getType());
                num = waterDeviceDao.saveWaterDevice(waterDevice2);
                waterDevice.setId2(waterDevice2.getId());
            } else {
                num = waterDeviceDao.modifyWaterDevice(waterDevice2);
                securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_MODIFY_WATER_DEVICE, SecurityUserHolder.getUserName(), waterDevice2, oldWaterDevice,
                        waterDevice.getProjectId(), Constants.SOURCE_PROJECT);
            }
            waterDeviceDao.saveWaterDeviceEdit(waterDevice2);
        }
        // 修改设备关联物联网
        Device device = new Device();
        device.setId(waterDevice.getDeviceId());
        device.setSensorNo(waterDevice.getSensorNo() + (StringUtils.isNotEmpty(waterDevice.getSensorNo2()) ? "," + waterDevice.getSensorNo2() : ""));
        deviceDao.modifyDevice(device);
        return asseData(num);
    }

    /**
     * 功能描述:删除水压设备信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data delWaterDevice(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        //waterDevice.setStatus(99);

        List<WaterDevice> list = waterDeviceDao.findWaterDeviceList(waterDevice);
        int num = waterDeviceDao.delWaterDevice(waterDevice);
        Long[] ids = new Long[list.size()];
        WaterDevice wd;
        for (int i = 0; i < list.size(); i++) {
            wd = list.get(i);
            ids[i] = wd.getDeviceId();
        }
        //修改设备关联物联网
        Device device = new Device();
        device.setIds(ids);
        deviceDao.modifyDeviceClearIot(device);

        securityLogService.saveSecurityLog(ConstantsIot.SECURITY_LOG_TYPE_DEL_WATER_DEVICE, SecurityUserHolder.getUserName(), waterDevice, null,
                waterDevice.getProjectId(), Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     * 功能描述:查询水压设备信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data findWaterDeviceList(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        Integer totalCount = waterDevice.getTotalCount();
        Integer pageSize = waterDevice.getPageSize();
        if (waterDevice.getOrderCols() == null) {
            String[] orderCols = {"createTime"};
            waterDevice.setOrderCols(orderCols);
            waterDevice.setOrderDesc(true);
        }
        Integer[] faultStatuss = waterDevice.getFaultStatuss();
        if (faultStatuss != null && faultStatuss.length > 0) {
            waterDevice.setFaultStatus(faultStatuss[0]);
        }
        waterDevice.setFindSysTerminal(true);
        // 判断是否要查询系统端数据,如是水浸或消火栓则不用查询
        if (waterDevice.getDeviceType() != null && (waterDevice.getDeviceType() == 3 || waterDevice.getDeviceType() == 8)) {
            waterDevice.setFindSysTerminal(false);
        }
        // 是否查询系统端 ,只有在查询水压设备列表时查询系统端, 是否是以传感器id做为查询条件
        if (waterDevice.isFindSysTerminal() && StringUtils.isNotEmpty(waterDevice.getSensorNo())) {
            waterDevice.setFindWaterLBySensorNo(true);
        }
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = waterDeviceDao.findWaterDeviceListTotalCount(waterDevice);
        }
        List<WaterDevice> list = waterDeviceDao.findWaterDeviceList(waterDevice);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询水压设备修改记录信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/23 17:36
     */
    @Override
    public Data findWaterDeviceEditList(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        waterDevice.setFindSysTerminal(true);
        // 判断是否要查询系统端数据,如是水浸或消火栓则不用查询
        if (waterDevice.getDeviceType() != null && (waterDevice.getDeviceType() == 3 || waterDevice.getDeviceType() == 8)) {
            waterDevice.setFindSysTerminal(false);
        }
        List<WaterDevice> list = waterDeviceDao.findWaterDeviceEditList(waterDevice);
        return asseData(list);
    }

    /**
     * 功能描述:查询水压设备汇总报表
     *
     * @param json 参数 json
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findWaterDeviceSummaryReport(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        WaterDeviceReport waterDeviceReport = waterDeviceDao.findWaterDeviceSummaryReport(waterDevice);
        return asseData(waterDeviceReport);
    }

    /**
     * 查询app首页水设备信息
     *
     * @param json 参数
     * @return JSONObject
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     */
    @Override
    public JSONObject findUserProjectWaterData(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        WaterDeviceReport waterDeviceReport = waterDeviceDao.findWaterDeviceSummaryReport(waterDevice);

        JSONObject jsonObject = new JSONObject(5);
        jsonObject.put("modelIndex", waterDevice.getType());
        if (waterDeviceReport != null) {
            jsonObject.put("offLine", waterDeviceReport.getOffLine());
            jsonObject.put("faultEvent", waterDeviceReport.getFaultEvent());
            jsonObject.put("alarmEvent", waterDeviceReport.getAlarmEvent());
        } else {
            Integer total = waterDeviceDao.findWaterDeviceListTotalCount(waterDevice);
            if (total == null || total == 0) {
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("offLine", 0);
            jsonObject.put("faultEvent", 0);
            jsonObject.put("alarmEvent", 0);
        }
        return jsonObject;
    }

    /**
     * 功能描述:查询水压设备大屏信息
     *
     * @param json 参数 json
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/26 14:59
     */
    @Override
    public Data findWaterDeviceScreen(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        JSONObject jsonObject = new JSONObject(3);
        WaterDeviceReport waterDeviceReport = waterDeviceDao.findWaterDeviceSummaryReport(waterDevice);
        // 汇总数
        jsonObject.put("waterDeviceSummaryReport", waterDeviceReport);

        Data data = waterDeviceRecordService.findWaterDeviceEventReportCount(json);
        // 查询水设备事件记录按时间汇总（曲线图）
        jsonObject.put("waterDeviceEventReportCount", data);

        data = waterDeviceRecordService.findWaterDeviceEventList(json);
        // 事件列表
        jsonObject.put("waterDeviceEventList", data);
        return asseData(jsonObject);
    }

    /**
     * 功能描述:水压设备下载
     *
     * @param response HttpServletResponse
     * @param json 参数
     * @author wangzhiwen
     * @date 2019/10/8 9:51
     */
    @Override
    public void downloadWaterDevice(HttpServletResponse response, String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        waterDevice.setPageSize(0);
        String[] orderCols = {"pointQrNo", "qrNo"};
        waterDevice.setOrderCols(orderCols);
        waterDevice.setWaterTerminalId(-1L);
        List<WaterDevice> list = waterDeviceDao.findWaterDeviceList(waterDevice);
        createAndDownloadExcel(response, waterDevice, list);
    }

    /**
     * 功能描述:选中水压设备下载
     *
     * @param response HttpServletResponse
     * @param json 参数
     * @author wangzhiwen
     * @date 2019/10/8 9:51
     */
    @Override
    public void downloadWaterDeviceByList(HttpServletResponse response, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);

        List<WaterDevice> list = JSONArray.parseArray(jsonObject.getString("list"), WaterDevice.class);
        createAndDownloadExcel(response, waterDevice, list);
    }

    /**
     * 功能描述:生成水设备下载文件
     *
     * @param response    HttpServletResponse
     * @param waterDevice WaterDevice
     * @param list        List<WaterDevice>
     * @author wangzhiwen
     * @date 2020/3/11 14:36
     */
    private void createAndDownloadExcel(HttpServletResponse response, WaterDevice waterDevice, List<WaterDevice> list) {
        Integer deviceType = waterDevice.getDeviceType();
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, waterDevice.getProjectId());
        String fileName = "-水压监测设备管理表";
        String modelFilePath = ConstantsIot.REPORT_WATER_DEVICE_MODEL_FILE_PATH;
        String[] keys = {"rowNum", "deviceSysName", "pointQrNo", "qrNo", "sensorNoDesc", "presentValueDesc", "monitorStatusDesc", "deviceStatusDesc",
                "signalIntensityDesc", "electricQuantityDesc", "pointLocationDesc"};
        if (deviceType != null && 3 == deviceType) {
            fileName = "-智能水浸设备管理表";
            keys = new String[]{"rowNum", "deviceSysName", "pointQrNo", "qrNo", "sensorNo", "leakMonitorStatusDesc", "deviceStatusDesc", "signalIntensityDesc",
                    "electricQuantityDesc", "pointLocationDesc"};
            modelFilePath = ConstantsIot.REPORT_WATER_SOAKING_DEVICE_MODEL_FILE_PATH;
        } else if (deviceType != null && 8 == deviceType) {
            fileName = "-智能消火栓设备管理表";
            keys = new String[]{"rowNum", "deviceSysName", "pointQrNo", "qrNo", "sensorNo", "presentValueDesc", "strikeMonitorStatusDesc", "deviceStatusDesc",
                    "faultDesc", "electricQuantityDesc", "pointLocationDesc"};
            modelFilePath = ConstantsIot.REPORT_WATER_HYDRANT_DEVICE_MODEL_FILE_PATH;
        }
        waterDevice.setTitle(projectJson.getString("projectName") + fileName);
        JSONObject jsonObj = (JSONObject) JSONObject.toJSON(waterDevice);

        ExcelUtils.createAndDownloadExcel(response, list, keys, modelFilePath, 4, null, jsonObj, "1:0");
    }

    /**
     * 功能描述:水压设备离线任务
     *
     * @return
     * @author wangzhiwen
     * @date 2019/11/19 14:44
     */
    @Override
    public synchronized Data waterOffLineTask() {
        WaterDevice waterDevice = new WaterDevice();
        //从数据词典中得到计算的开始值
        Integer offLineTime = Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.OFF_LINE_TIME, ConstantsIot.OFF_LINE_TIME_IOT_WATER));
        Date date = DateUtils.addSecond(DateUtils.getCurrentDate(), 0 - offLineTime * 60);
        int num = waterDeviceDao.saveWaterOffLineEvent(date);//添加离线事件
        if (num > 0) {
            waterDeviceDao.saveWaterOffLineRecord(date);//添加离线记录
            waterDeviceDao.waterOffLineModifyDeviceIotStatus(date);//修改水压设备的设备物联状态
            waterDevice.setEndHeartbeatTime(date);
            waterDevice.setWaterTerminalId(-1L);
            List<WaterDevice> list = waterDeviceDao.findWaterDeviceList(waterDevice);
            if (CollectionUtils.isNotEmpty(list)) {//判断是否有过期的设备
                String buildingName;
                String floorName;
                String pointLocation;
                String deviceName;
                String eventRoleType;
                int deviceType;
                int sendType;
                String url;
                String msg;
                String title;
                for (WaterDevice wd : list) {
                    buildingName = wd.getBuildingNameDesc();
                    floorName = wd.getBuildingFloorNameDesc();
                    pointLocation = wd.getPointLocation();
                    deviceName = wd.getDeviceSysName();
                    deviceType = wd.getDeviceType();
                    msg = ConstantsIot.WATER_OFFLINE_MSG;
                    sendType = 0;
                    eventRoleType = "";
                    if (deviceType == 3) {//水浸设备
                        title = ConstantsIot.WATER_SOAKING_STATUS_TITLE;
                        sendType = 7;
                        url = ConstantsIot.WATER_LEACHING_MSG_URL;
                        eventRoleType = ConstantsIot.WATER_IMMERSION_OFFLINE_EVENT;
                        msg = ConstantsIot.WATER_IMMERSION_OFFLINE_MSG;
                    } else if (deviceType == 8) {//消火栓设备
                        title = ConstantsIot.FIRE_COCK_STATUS_TITLE;
                        sendType = 8;
                        url = ConstantsIot.HYDRANT_MSG_URL;
                        eventRoleType = ConstantsIot.HYDRANT_OFFLINE_EVENT;
                    } else {//水压液位
                        title = ConstantsIot.WATER_STATUS_TITLE;
                        sendType = -3;
                        url = ConstantsIot.WATER_GAGE_MSG_URL;
                        eventRoleType = ConstantsIot.WATER_GAGE_OFFLINE_EVENT;
                    }
                    linkageControlService.deviceFault(waterDevice.getDeviceId(), eventRoleType);
                    String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, wd.getProjectId(), "projectName");
                    // 组装要发送的信息内容
                    String content = "【" + projectName + "】 " + buildingName + floorName + pointLocation + deviceName + wd.getPointQrNo() + " 传感器ID:"
                            + wd.getSensorNo() + msg;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("projectName", projectName);
                    jsonObject.put("buildingName", buildingName);
                    jsonObject.put("floor", floorName);
                    jsonObject.put("deviceLocation", pointLocation);
                    jsonObject.put("deviceName", deviceName);
                    jsonObject.put("qrNo", wd.getQrNo());
                    jsonObject.put("event", msg);
                    jsonObject.put("buildingId", wd.getBuildingId());
                    // 发送提醒信息
                    messageService.saveMessageRole(sendType, Arrays.asList(eventRoleType.split(",")), title, 99, 0, content,
                            url, wd.getProjectId(), null, null, jsonObject);
                }

                waterDeviceDao.waterOffLineTask(waterDevice);
                waterDeviceDao.waterOffLineModifyDeviceIotStatus(date);
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述:查询水压设备
     *
     * @return Data
     * @author zhangZaiFa
     * @date 2019/11/19 14:44
     */
    @Override
    public Data findWaterDevice(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        waterDevice = waterDeviceDao.findWaterDevice(waterDevice);
        //设备类型　默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警
        // 7智能烟感 8智能消火栓 9可然气  10电气火灾 11视频监控 12防火门监控
        List<Map> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>(6);
        map.put("sensorNo", waterDevice.getSensorNo());
        map.put("value", waterDevice.getPresentValueDesc());
        map.put("sign", waterDevice.getSignalIntensityDesc());
        map.put("electric", waterDevice.getElectricQuantityDesc());
        map.put("status", waterDevice.getDeviceStatus());
        // 两个端
        if (waterDevice.getType() == 14) {
            map.put("sensorName", "供水端");
            waterDevice.setWaterTerminalId(waterDevice.getId());
            waterDevice.setId(null);
            waterDevice.setSensorNo(null);
            WaterDevice sysWaterDevice = waterDeviceDao.findWaterDevice(waterDevice);
            if (sysWaterDevice != null) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("sensorName", "系统端");
                map2.put("sensorNo", sysWaterDevice.getSensorNo());
                map2.put("value", sysWaterDevice.getPresentValueDesc());
                map2.put("sign", sysWaterDevice.getSignalIntensityDesc());
                map2.put("electric", sysWaterDevice.getElectricQuantityDesc());
                map2.put("status", sysWaterDevice.getDeviceStatus());
                list.add(map2);
            }
        } else if (waterDevice.getType() == 2) {
            map.put("sensorName", "压力端");
        } else if (waterDevice.getType() == 13) {
            map.put("sensorName", "液位");

        }
        list.add(map);

        return asseData(list);
    }

    /**
     * 查询消火栓设备数以及巡查点信息列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-08-10 14:20
     */
    @Override
    public Data findHydrantDeviceCheckPoints(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        WaterDeviceReport wdr = waterDeviceDao.findHydrantDeviceNum(waterDevice);
        List<WaterDevice> wd = waterDeviceDao.findHydrantDeviceCheckPoints(waterDevice);

        Map<String, Object> map = new HashMap<>(2);
        map.put("checkPointList", wd);
        map.put("hydrantDeviceNum", wdr);
        return asseData(map);
    }

    /**
     * 修改巡查点经纬度
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-08-11 15:35
     */
    @Override
    public Data modifyCheckPointLatAndLng(String json) {
        WaterDevice waterDevice = JSONObject.parseObject(json, WaterDevice.class);
        int lat = waterDevice.getLat();
        int lng = waterDevice.getLng();
        if (lat >= 0 && lng >= 0 && waterDevice.getCheckPointId() != null && waterDevice.getCheckPointId() != 0) {
            int a = waterDeviceDao.modifyCheckPointLatAndLng(waterDevice);
            if (a > 0) {
                return Data.isSuccess();
            }
        }
        return Data.isFail();
    }
}
