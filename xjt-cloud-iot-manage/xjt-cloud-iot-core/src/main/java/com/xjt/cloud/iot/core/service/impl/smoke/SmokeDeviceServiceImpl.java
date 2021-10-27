package com.xjt.cloud.iot.core.service.impl.smoke;

import cmcciot.onenet.nbapi.sdk.api.online.CreateDeviceOpe;
import cmcciot.onenet.nbapi.sdk.api.online.DeleteOpe;
import cmcciot.onenet.nbapi.sdk.entity.Delete;
import cmcciot.onenet.nbapi.sdk.entity.Device;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.iot.core.common.ConstantsIot;
import com.xjt.cloud.iot.core.dao.device.DeviceDao;
import com.xjt.cloud.iot.core.dao.iot.smoke.SmokeDeviceDao;
import com.xjt.cloud.iot.core.entity.smoke.PointDevice;
import com.xjt.cloud.iot.core.entity.smoke.SmokeDevice;
import com.xjt.cloud.iot.core.entity.smoke.SmokeDeviceReport;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageDeviceRelationService;
import com.xjt.cloud.iot.core.service.service.smoke.SmokeDeviceService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 烟感设备 service实现类
 *
 * @author huanggc
 * @date 2020/07/15
 **/
@Service
public class SmokeDeviceServiceImpl extends AbstractService implements SmokeDeviceService {
    @Autowired
    private SmokeDeviceDao smokeDeviceDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private LinkageDeviceRelationService linkageDeviceRelationService;
    @Autowired
    private MessageService messageService;

    /**
     * 功能描述 查询烟感设备列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeDeviceList(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);

        Integer totalCount = smokeDevice.getTotalCount();
        Integer pageSize = smokeDevice.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = smokeDeviceDao.findSmokeDeviceListTotalCount(smokeDevice);
        }

        if (null == smokeDevice.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            smokeDevice.setOrderCols(orderCols);
            smokeDevice.setOrderDesc(true);
        }
        List<SmokeDevice> smokeDeviceList = smokeDeviceDao.findSmokeDeviceList(smokeDevice);

        return asseData(totalCount, smokeDeviceList);
    }

    /**
     * 功能描述 查询烟感设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeDevice(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);
        smokeDevice.setDeleted(false);

        SmokeDevice entity = smokeDeviceDao.findSmokeDevice(smokeDevice);
        return asseData(entity);
    }

    /**
     * 功能描述 导出烟感设备列表
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020/07/20
     */
    @Override
    public void downSmokeDeviceList(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);

        if (null == smokeDevice.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            smokeDevice.setOrderCols(orderCols);
            smokeDevice.setOrderDesc(true);
        }
        List<SmokeDevice> list = smokeDeviceDao.findSmokeDeviceList(smokeDevice);

        if (CollectionUtils.isEmpty(list)){
            return ;
        }

        Long projectId = smokeDevice.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--智能无线烟感列表");

        String[] keys = {"rowNum", "deviceName", "deviceQrNo", "checkPointQrNo", "sensorId", "eventTypeDesc", "signalValueDesc", "electricQuantityDesc",
                "heartbeatTimeDesc", "pointLocationDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsIot.SMOKE_LIST_EXCEL_MODEL_FILE_PATH, 3, null, jsonObject,
                "1:0");
    }

    /**
     * 功能描述: 增加 烟感设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveSmokeDevice(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);

        String devId = null;
        String networkType = smokeDevice.getNetworkType();
        String sensorId = smokeDevice.getSensorId();

        String pointLocation = deviceDao.findPointLocationById(smokeDevice.getCheckPointId());
        smokeDevice.setPointLocation(pointLocation);
        if("NB_OneNET".equals(networkType)){
            // 在OneNet平台上创建设备
            CreateDeviceOpe deviceOpe = new CreateDeviceOpe(ConstantsIot.NB_API_KEY);
            // title=平台上创建的设备名称， imei=烟感设备码,如：863222041024486， imsi=自定义
            Device device = new Device(smokeDevice.getBuildingName() + smokeDevice.getFloorName() + pointLocation, sensorId, "123456789");
            JSONObject s = deviceOpe.operation(device, device.toJsonObject());

            int errno= 1;
            if (s != null){
                errno = s.getInteger("errno");
            }

            // 判断在OneNET平台创建是否成功
            if(errno != 0){
                // 创建失败
                String error = "Create OneNET NB Smoke Device Failure, errno = " + errno + ", error = " + (s == null ? "" : s.getString("error"));
                SysLog.info(errno);
                if (error.contains("imei exists")){
                    return asseData(400, "传感器ID已存在!");
                }
                return asseData(400, "传感器ID错误!");
            }

            JSONObject jData = s.getJSONObject("data");
            SysLog.info("NB OneNET imei = " + sensorId + ", create data = " + jData.toString());

            devId = jData.getString("device_id");
        }

        smokeDevice.setDevId(devId);
        // 默认设置电量100%
        smokeDevice.setElectricQuantity(10000);
        // 默认信号1-5(满格信号)
        smokeDevice.setSignalValue(5);
        int saveSmokeDeviceNum = smokeDeviceDao.saveSmokeDevice(smokeDevice);
        if (saveSmokeDeviceNum > 0){
            // 更新设备表中的 iot_id
            // 修改设备关联物联网
            com.xjt.cloud.iot.core.entity.device.Device device = new com.xjt.cloud.iot.core.entity.device.Device();
            device.setId(smokeDevice.getDeviceId());
            device.setIotId(smokeDevice.getId());
            device.setSensorNo(sensorId);
            deviceDao.modifyDevice(device);
        }
        return asseData(smokeDevice);
    }

    /**
     * 功能描述: 更新 烟感设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/31
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateSmokeDevice(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);

        // 更新烟感设备
        if(!smokeDevice.getSensorId().equals(smokeDevice.getOldSensorId())){
            String deviceId  = smokeDevice.getDevId();
            String sensorId = smokeDevice.getSensorId();
            String oldSensorId = smokeDevice.getOldSensorId();

            String error = "";
            // 中国移动
            if("NB_OneNET".equals(smokeDevice.getNetworkType()) && StringUtils.isNotEmpty(sensorId) && StringUtils.isNotEmpty(oldSensorId)){
                // 先删除 OneNET 平台上的NB设备
                DeleteOpe deviceOpe = new DeleteOpe(ConstantsIot.NB_API_KEY);
                Delete device = new Delete(deviceId);
                JSONObject s = deviceOpe.operation(device, null);

                SysLog.info("NB OneNET device delete ：" + s.toString());
                int errno = s.getInteger("errno");
                SysLog.info("NB OneNET delete error no.：" + errno);

                // 判断在 OneNET 平台删除是否成功
                if(errno != 0){
                    error = "NB OneNET delete error, errno=" + errno +",error=" + s.getString("error");
                    return asseData("/error/error");
                }

                // 在 OneNet 平台上创建设备
                CreateDeviceOpe deviceOpe1 = new CreateDeviceOpe(ConstantsIot.NB_API_KEY);
                Device device1 = new Device("NbSmokeSensor", sensorId, "123456789");
                JSONObject s1 = deviceOpe1.operation(device1, device1.toJsonObject());
                SysLog.info("NB OneNET create ：" + s1);

                int errno1 = s1.getInteger("errno");

                // 判断在OneNET平台创建是否成功
                if(errno1 != 0){
                    error = "OneNET create NB device failure,imei=" + sensorId + ", errno = "+errno1 + ", error = " + s1.getString("error");
                    return asseData("/error/error");
                }

                JSONObject jData = s1.getJSONObject("data");
                SysLog.info("NB OneNET imei=" + sensorId +", create data =" + jData.toString());

                // oneNet 平台上返回的新的ID
                smokeDevice.setDevId(jData.getString("device_id"));
                smokeDevice.setNbPsk(jData.getString("psk"));
            } else if("NB_CT".equals(smokeDevice.getNetworkType()) && StringUtils.isNotEmpty(sensorId) && StringUtils.isNotEmpty(oldSensorId)){
                // 删除CT平台上的NB设备
                /*try{
                    CtCloudAccessUtil.delDevice(deviceId);

                    // System.out.print("NB CT delete device success: sensorId =" + oldSensorId + "deviceId=" + deviceId);
                    SysLog.info("NB CT delete device success: sensorId =" + oldSensorId + "deviceId=" + deviceId);
                }catch(NorthApiException e){
                    // CT平台删除设备失败处理
                    e.printStackTrace();
                    String errorDesc = e.getError_desc();
                    String errorCode = e.getError_code();

                    error = "NB CT delete error, sendsorId = " + oldSensorId +", error code = " + errorCode +", error desc=" + errorDesc;
                    SysLog.info(error);

                    return asseData("/error/error");
                }

                // 在CT平台上新增NB设备
                try{
                    deviceId = CtCloudAccessUtil.addDevice(sensorId);
                    SysLog.info("NB CT add device success, imei=" + sensorId +", device_id =" + deviceId);
                }catch(NorthApiException e){
                    // CT平台创建设备失败处理
                    String errorDesc = e.getError_desc();
                    String errorCode = e.getError_desc();
                    e.printStackTrace();

                    error = "NB CT add error, sendsorId = " + sensorId + ", error code = " + errorCode + ", error desc=" + errorDesc;
                    SysLog.info(error);

                    return asseData("/error/error");
                }

                smokeDevice.setDevId(deviceId);*/
            }

            com.xjt.cloud.iot.core.entity.device.Device device = new com.xjt.cloud.iot.core.entity.device.Device();
            device.setId(smokeDevice.getDeviceId());
            device.setIotId(smokeDevice.getId());
            device.setSensorNo(sensorId);
            deviceDao.modifyDevice(device);
        }

        smokeDeviceDao.updateSmokeDevice(smokeDevice);
        return asseData(smokeDevice);
    }

    /**
     * 功能描述: 删除 烟感设备
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/15
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deletedSmokeDevice(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);

        // 查询 声光是否绑定了烟感设备
        Long[] deviceIdArray = smokeDeviceDao.findSmokeDeviceId(smokeDevice);
        if (deviceIdArray != null){
            Data data = linkageDeviceRelationService.deleteDeviceTableByDeviceId(deviceIdArray);
            if (data != null && Integer.parseInt(data.getObject().toString()) > 0){
                return asseData(600, "请将声光关联设备解绑");
            }
        }

        List<SmokeDevice> smokeDeviceList = smokeDeviceDao.findSmokeDeviceList(smokeDevice);
        if (CollectionUtils.isNotEmpty(smokeDeviceList)){
            List<Long> smokeDeviceIdList = new ArrayList<>(smokeDeviceList.size());
            Long[] deviceIds = new Long[smokeDeviceList.size()];
            boolean flag = true;
            int i = 0;

            for (SmokeDevice entity : smokeDeviceList) {
                String networkType = entity.getNetworkType();

                // (旧系统代码)3.8.3 搬过来的
                String msg = "";
                // 移动
                if("NB_OneNET".equals(networkType)){
                    String devId = entity.getDevId();
                    String sensorId = entity.getSensorId();

                    // 删除 oneNet 平台上的设备
                    DeleteOpe deviceOpe = new DeleteOpe(ConstantsIot.NB_API_KEY);
                    Delete device = new Delete(devId);
                    JSONObject s = deviceOpe.operation(device, null);
                    SysLog.info("NB OneNET imei= " + sensorId + ", devId = " + devId + ", device delete data=" + s.toString());

                    int errno = s.getInteger("errno");

                    // 判断在OneNET平台删除NB设备是否成功
                    if(errno != 0){
                        flag = false;
                        msg = "OneNET delete fail, imei= sensorId = " + sensorId + ", devId = " + devId + ", errno=" + errno + ", error = " + s.getString("error");
                        SysLog.info(msg);
                        return asseData(400, msg);
                    }
                } else if("NB_CT".equals(networkType)){
                    // 电信
                    /*try{
                        CtCloudAccessUtil.delDevice(deviceId);
                        System.out.print("NB CT delete device success: sensorId =" + intelligentSmoke.getSensorId() + ", deviceId=" + deviceId);
                        log.debug("NB CT delete device success: sensorId =" + intelligentSmoke.getSensorId() + ", deviceId=" + deviceId);
                    }catch(NorthApiException e){
                        // CT平台删除设备失败处理
                        e.printStackTrace();
                        String errorDesc = e.getError_desc();
                        String errorCode = e.getError_code();
                        log.debug("NB CT delete device error, sensorId =" + intelligentSmoke.getSensorId() + ", error code =" + errorCode +", error Desc = " + errorDesc);
                        return errorDesc;
                    }*/
                }else {
                    flag = false;
                }
                smokeDeviceIdList.add(entity.getId());
                deviceIds[i] = entity.getDeviceId();
                i++;
            }

            if (flag){
                Integer deletedSmokeDeviceNum = smokeDeviceDao.deletedSmokeDevice(smokeDeviceIdList);
                if (deletedSmokeDeviceNum != null && deletedSmokeDeviceNum > 0){
                    // 修改设备关联物联网
                    com.xjt.cloud.iot.core.entity.device.Device device = new com.xjt.cloud.iot.core.entity.device.Device();
                    device.setIds(deviceIds);
                    deviceDao.modifyDeviceClearIot(device);
                }
            }else {
                return asseData(600, "解绑失败");
            }
        }
        return asseData(200, "success");
    }

    /**
     * 功能描述: 烟感设备汇总报表 饼图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/07/27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSomkeDeviceSummaryReport(String json) {
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);
        SmokeDeviceReport smokeDeviceReport = smokeDeviceDao.findSomkeDeviceSummaryReport(smokeDevice);
        return asseData(smokeDeviceReport);
    }

    /**
     * 查询app首页智能烟感信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return JSONObject
     */
    @Override
    public JSONObject findUserProjectSmokeData(String json){
        SmokeDevice smokeDevice = JSONObject.parseObject(json, SmokeDevice.class);
        SmokeDeviceReport smokeDeviceReport = smokeDeviceDao.findSomkeDeviceSummaryReport(smokeDevice);

        JSONObject jsonObject = new JSONObject(5);
        jsonObject.put("modelIndex", 8);
        if (smokeDeviceReport != null){
            jsonObject.put("offLine", smokeDeviceReport.getOffLine());
            jsonObject.put("sensorFault", smokeDeviceReport.getSensorFault());
            jsonObject.put("smokeNum", smokeDeviceReport.getSmokeNum());
        }else{
            Integer total = smokeDeviceDao.findSmokeDeviceListTotalCount(smokeDevice);
            if(total == null || total == 0){
                jsonObject.put("deviceCount", 0);
            }
            jsonObject.put("offLine", 0);
            jsonObject.put("sensorFault", 0);
            jsonObject.put("smokeNum", 0);
        }
        return jsonObject;
    }

    /**
     * 功能描述: 烟感设备离线任务
     *
     * @author huanggc
     * @date 2020/08/04
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public synchronized Data smokeOffLineTask() {
        SmokeDevice smokeDevice = new SmokeDevice();
        // 从数据词典中得到计算的开始值 单位分钟
        Integer offLineTime = Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.OFF_LINE_TIME, ConstantsIot.OFF_LINE_TIME_IOT_SMOKE));
        Date date = DateUtils.addSecond(DateUtils.getCurrentDate(), 0 - offLineTime * 60);
        smokeDevice.setHeartbeatTime(date);
        // 添加离线事件
        smokeDeviceDao.saveSmokeOffLineEvent(date);

        // 修改设备离线状态
        int offLineNum = smokeDeviceDao.smokeOffLineTask(smokeDevice);
        // 离线数量 发邮件
        if (offLineNum >= Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.IOT_DEVICE_CONFIG, ConstantsIot.SMOKE_DEVICE_OFF_LINE_NUM_MAIL))
            && offLineNum < Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.IOT_DEVICE_CONFIG, ConstantsIot.SMOKE_DEVICE_OFF_LINE_NUM_SMS))){

            String mailStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsIot.IOT_DEVICE_CONFIG, ConstantsIot.MAIL_RECEIVE,
                    "itemDescription");
            if (StringUtils.isNotEmpty(mailStr)){
                MailUtils.send("消检通系统", Integer.toString(offLineNum), "烟感设备离线数量", mailStr.split(";"));
            }
        }else if (offLineNum >= Integer.valueOf(DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.IOT_DEVICE_CONFIG, ConstantsIot.SMOKE_DEVICE_OFF_LINE_NUM_SMS))){
            // 发短信  SMS_48320070   ${ProName}项目${position}，请您及时处理！   event == 91
            String phoneStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsIot.IOT_DEVICE_CONFIG, ConstantsIot.PHONE_RECEIVE,
                    "itemDescription");

            if (StringUtils.isNotEmpty(phoneStr)){
                JSONObject jsonObject = new JSONObject(2);
                jsonObject.put("ProName", "烟感设备离线的数量");
                jsonObject.put("position", Integer.toString(offLineNum));
                Integer event = Integer.valueOf(ConstantsIot.MONITOR_PROJECT_EVENT);
                String[] split = phoneStr.split(";");
                for (String i : split) {
                    messageService.sendSMS(event, i, jsonObject);
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 查询支持的平台
     *
     * @author huanggc
     * @date 2020/08/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findSmokeCloud() {
        String cloudValue = DictUtils.getDictItemValueByDictAndItemCode(ConstantsIot.FIND_SMOKE_CLOUD_KEY, ConstantsIot.FIND_SMOKE_CLOUD_VALUE);
        String[] split = cloudValue.split(",");
        return asseData(split);
    }

    /**
     * 功能描述: 上传 excel表格 批量绑定烟感
     *
     * @param json 参数
     * @param file 上传的文件
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/18
     */
    @Override
    public Data uploadPointDeviceExcel(String json, MultipartFile file) {
        PointDevice pointDevice = JSONObject.parseObject(json, PointDevice.class);
        // 解析表格，设备系统名称＝表格中的设备名称
        String[] keys = {"rowNum", "deviceQrNo", "sensorId", "cardId"};

        // 解析表格   rowindex从0开始
        List<PointDevice> list = ExcelUtils.readyExcel(file, 3,0,0, keys, PointDevice.class);
        if (CollectionUtils.isNotEmpty(list)){
            List<PointDevice> deviceList = new ArrayList<>();
            // 公司与部门列表
            StringBuilder devceQrNos = new StringBuilder();
            String deviceName;
            for (PointDevice p : list) {
                if(StringUtils.isEmpty(p.getDeviceQrNo()) || StringUtils.isEmpty(p.getSensorId())){
                    continue;
                }

                deviceName = " SELECT '" + p.getDeviceQrNo() + "' qr_no UNION ";
                if (devceQrNos.indexOf(deviceName) == -1) {
                    // 设备系统名称
                    devceQrNos.append(deviceName);
                }
                deviceList.add(p);
            }

            List<PointDevice> findList = deviceDao.findDeviceListBySql(devceQrNos.substring(0, devceQrNos.length() - 6));
            if (CollectionUtils.isNotEmpty(deviceList)){
                StringBuilder stringBuilderError = new StringBuilder();
                StringBuilder stringBuilderExists = new StringBuilder();

                Long projectId = pointDevice.getProjectId();
                SmokeDevice smokeDevice;
                List<SmokeDevice> smokeDeviceList = new ArrayList<>();
                // 在OneNet平台上创建设备
                CreateDeviceOpe deviceOpe = new CreateDeviceOpe(ConstantsIot.NB_API_KEY);
                String devId;
                String sensorId;
                PointDevice tempPd;
                for (PointDevice p : deviceList) {
                    tempPd = null;
                    for (PointDevice tem : findList){
                        if (p.getDeviceQrNo().equals(tem.getDeviceQrNo())){
                            tempPd = tem;
                            break;
                        }
                    }
                    sensorId = p.getSensorId();
                    // title=oneNote平台上创建的设备名称， imei=烟感设备码,如：863222041024486， imsi=物联网卡ID
                    Device device = new Device(tempPd.getBuildingName() + tempPd.getFloorName() + tempPd.getPointLocation(), sensorId, p.getCardId());
                    JSONObject s = deviceOpe.operation(device, device.toJsonObject());

                    int errno = 1;
                    if (s != null){
                        errno = s.getInteger("errno");
                    }

                    // 判断在OneNET平台创建是否成功
                    if(errno != 0){
                        // 创建失败
                        String errors = "Create OneNET NB Smoke Device Failure, errno = " + errno + ", error = " + s.getString("error");
                        if (errors.contains("imei exists")){
                            stringBuilderExists.append(sensorId + ",");
                        }else {
                            stringBuilderError.append(sensorId + ",");
                        }
                        continue;
                    }

                    JSONObject jData = s.getJSONObject("data");
                    SysLog.info("NB OneNET imei = " + sensorId + ", create data = " + jData.toString());

                    devId = jData.getString("device_id");
                    //String psk = jData.getString("psk");

                    smokeDevice = new SmokeDevice();
                    smokeDevice.setBuildingId(tempPd.getBuildingId());
                    smokeDevice.setBuildingFloorId(tempPd.getBuildingFloorId());
                    smokeDevice.setDeviceId(tempPd.getDeviceId());
                    smokeDevice.setDeviceTypeId(tempPd.getDeviceTypeId());
                    smokeDevice.setCheckPointId(tempPd.getCheckPointId());
                    smokeDevice.setSensorId(sensorId);

                    smokeDevice.setProjectId(projectId);
                    smokeDevice.setDevId(devId);
                    smokeDevice.setElectricQuantity(10000);
                    smokeDevice.setSignalValue(5);
                    smokeDevice.setNetworkType("NB_OneNET");
                    smokeDeviceList.add(smokeDevice);
                }

                if (CollectionUtils.isNotEmpty(smokeDeviceList)){
                    // 批量保存 烟感设备
                    Integer saveSmokeDeviceListNum = smokeDeviceDao.saveSmokeDeviceList(smokeDeviceList);
                    if (saveSmokeDeviceListNum != null){
                        // 更新设备表中的 iot_id
                        // 修改设备关联物联网
                        deviceDao.modifyDeviceBySmokeDevice(smokeDeviceList);
                    }
                }
                if (stringBuilderError.length() > 0 || stringBuilderExists.length() > 0){
                    return asseData((stringBuilderError.length() > 0 ? "传感器ID错误:" + stringBuilderError.toString() : "")
                        + (stringBuilderExists.length() > 0 ? "传感器已存在:" + stringBuilderExists.toString() : ""));
                }
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 智能无线烟感 批量导入表格模板下载
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/08/21
     */
    @Override
    public Data downSmokeModelExcel(String json, HttpServletResponse response) {
        String[] keys = {"rowNum", "deviceQrNo", "sensorId", "carId"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "智能无线烟感批量导入表");

        ExcelUtils.createAndDownloadExcel(response, null, keys,  ConstantsIot.SMOKE_EXCEL_MODEL_FILE_PATH, 3, null,
                jsonObject, "1:0");
        return Data.isSuccess();
    }

}
