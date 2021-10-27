package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.device.core.common.ConstantsDevice;
import com.xjt.cloud.device.core.dao.device.CheckPointDao;
import com.xjt.cloud.device.core.dao.device.DeviceDao;
import com.xjt.cloud.device.core.dao.device.DeviceRemindDao;
import com.xjt.cloud.device.core.entity.*;
import com.xjt.cloud.device.core.service.service.DeviceService;
import com.xjt.cloud.device.core.service.service.QrNoService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:设备管理
 */
@Service
public class DeviceServiceImpl extends AbstractService implements DeviceService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private CheckPointDao checkPointDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private QrNoService qrNoService;
    @Autowired
    private DeviceRemindDao deviceRemindDao;
    @Autowired
    private MessageService messageService;

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data findDeviceList(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        if (StringUtils.isNotEmpty(device.getAppId())){
            String projects = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, device.getAppId());
            device.setProjectIds(projects);
        }
        if(device.getOrderCols() == null){
            String[] orderCols = {"createTime"};
            device.setOrderCols(orderCols);
            device.setOrderDesc(true);
        }
        Integer totalCount = device.getTotalCount();
        Integer pageSize = device.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = deviceDao.findDeviceListTotalCount(device);
        }
        List<Device> list = deviceDao.findDeviceList(device);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询设备列表 树结构
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/24
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceListTree(String json) {
        Device device = JSONObject.parseObject(json, Device.class);
        List<Device> deviceList = deviceDao.findDeviceListTree(device);
        return asseData(deviceList);
    }

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data findDeviceListByAppId(String json){
        return findDeviceList(json);
    }

    /**
     *
     * 功能描述:查询未关联水压设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data findDeviceListNotBoundIot(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        device.setIotId(0L);
        List<Device> list = deviceDao.findDeviceListBoundIot(device);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询关联水压设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data findDeviceListBoundIot(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        List<Device> list = deviceDao.findDeviceListBoundIot(device);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data findDevice(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        device = deviceDao.findDevice(device);
        return asseData(device);
    }

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data saveDevice(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        String qrNo = qrNoService.getOneQrNo(device.getProjectId(), 2);
        device.setQrNo(qrNo);
        device = setEntityUserIdName(SecurityUserHolder.getUserName(), device.getProjectId(), device);
        int num = deviceDao.saveDevice(device);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_ADD_DEVICE, SecurityUserHolder.getUserName(), device, null,
                device.getProjectId(), Constants.SOURCE_PROJECT);
        if (null != device.getCheckPointId()) {
            CheckPoint checkPoint = new CheckPoint();//删除新二维码所在信息的记录
            checkPoint.setId(device.getCheckPointId());
            checkPoint.setCreateUserId(device.getCreateUserId());
            checkPoint.setCreateUserName(device.getCreateUserName());
            checkPointDao.modifyCheckPoint(checkPoint);
        }
        return asseSaveData(num, device);
    }

    /**
     *
     * 功能描述:查询设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public JSONObject findDeviceReport(String json){
        PointDeviceReport pointDeviceReport = JSONObject.parseObject(json, PointDeviceReport.class);
        pointDeviceReport = deviceDao.findDeviceReport(pointDeviceReport);
        JSONObject jsonObject = new JSONObject();
        int deviceExceptionNum = pointDeviceReport.getDeviceExceptionNum();
        int deviceNormalNum = pointDeviceReport.getDeviceNormalNum();
        int deviceNum = deviceExceptionNum + deviceNormalNum;
        jsonObject.put("faultDeviceNum", deviceExceptionNum);
        jsonObject.put("normalDeviceNum", deviceNormalNum);
        jsonObject.put("deviceNum", deviceNum);
        DecimalFormat df = new DecimalFormat("#.00");
        jsonObject.put("normalRateDesc", df.format(deviceNormalNum * 100.00 / deviceNum) + "%");
        jsonObject.put("faultRateDesc", df.format(deviceExceptionNum * 100.00 / deviceNum) + "%");
        return jsonObject;
    }

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data saveDevices(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Device> list = JSONArray.parseArray(jsonObject.getString("saveDevices"), Device.class);
        int num = 0;
        if (CollectionUtils.isNotEmpty(list)){
            Long projectId = jsonObject.getLong("projectId");
            List<Device> saveList = new ArrayList<>();
            for (Device device:list){
                device.setProjectId(projectId);
                if (device.getId() != null){
                    num++;
                    deviceDao.modifyDevice(device);
                }else {
                    saveList.add(device);
                }
            }

            securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(),
                    list.get(0).getPointName() + " 巡检点批量修改了 " + num + " 个设备！" , projectId, Constants.SOURCE_PROJECT);

            if (CollectionUtils.isNotEmpty(saveList)) {
                //批量生成设备的二维码
                QrNo qrNo = new QrNo();
                qrNo.setType(2);
                qrNo.setProjectId(projectId);
                qrNo.setNum(saveList.size());
                List<QrNo> qrNoList = qrNoService.generationQrNo(qrNo);

                List<Device> temList = new ArrayList<>();
                Device device;
                String loginName = SecurityUserHolder.getUserName();
                Long userId = getLoginUserId(loginName);
                String userName = getOrgUserName(userId, projectId);

                for (int i = 0; i < qrNoList.size(); i++) {//为设备添加二维码
                    device = saveList.get(i);
                    device.setQrNo(qrNoList.get(i).getQrNo());
                    device.setCreateUserName(userName);
                    device.setCreateUserId(userId);
                    temList.add(device);
                }
                num = deviceDao.saveDevices(temList);//保存设备
                securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(),
                        saveList.get(0).getPointName() + " 巡检点批量增加了 " + num + " 个设备！" , projectId, Constants.SOURCE_PROJECT);
            }
        }
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    @Override
    public Data modifyDevice(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        Device oldDevice = new Device();
        oldDevice.setId(device.getId());
        oldDevice = deviceDao.findDevice(oldDevice);

        int num = deviceDao.modifyDevice(device);

        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_DEVICE, SecurityUserHolder.getUserName(), device, oldDevice,
                device.getProjectId(), Constants.SOURCE_PROJECT);
        if (null != device.getCheckPointId()) {
            CheckPoint checkPoint = new CheckPoint();//删除新二维码所在信息的记录
            checkPoint.setId(device.getCheckPointId());
            checkPoint.setCreateUserId(device.getCreateUserId());
            checkPoint.setCreateUserName(device.getCreateUserName());
            checkPointDao.modifyCheckPoint(checkPoint);
        }
        return asseData(num);
    }

    /**
     *
     * 功能描述:巡检点批量绑定设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @Override
    public Data pointBindDevices(String json){
        Device device = JSONObject.parseObject(json, Device.class);

        int num = deviceDao.modifyDevice(device);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(),
                device.getPointName() + " 巡检点批量增加了 " + num + " 个设备！" , device.getProjectId(), Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     *
     * 功能描述:巡检点批量清除设备绑定
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    @Override
    public Data pointClearBindDevices(String json){
        Device device = JSONObject.parseObject(json, Device.class);

        int num = deviceDao.pointClearBindDevices(device);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(),
                device.getPointName() + " 巡检点批量清除了 " + num + " 个设备的绑定！" , device.getProjectId(), Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @Override
    public void downloadDeviceRecord(HttpServletResponse response, String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        ReportDeviceRecord rdr = deviceDao.reportFindDeviceRecordBaseInfo(reportDeviceRecord);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, reportDeviceRecord.getProjectId());
        rdr.setTitle(projectJson.getString("projectName") + "（" + rdr.getDeviceName() + "）- 设备档案导出表");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(rdr);

        reportDeviceRecord.setPageSize(0);
        List<ReportDeviceRecord> list = deviceDao.reportFindDeviceRecordList(reportDeviceRecord);
        String[] keys = {"rowNum", "type", "orderName", "createTimeDesc", "operationResult", "createUserName","orgName"};

        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsDevice.REPORT_DEVICE_RECORD_MODEL_FILE_PATH,
                10, ConstantsDevice.REPORT_DEVICE_RECORD_DICT_ITEM_CODE, jsonObject, "1:0");
    }

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @Override
    public void downloadDeviceRecordPicList(HttpServletResponse response,String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        ReportDeviceRecord rdr = deviceDao.reportFindDeviceRecordBaseInfo(reportDeviceRecord);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, reportDeviceRecord.getProjectId());
        rdr.setTitle(projectJson.getString("projectName") + "（" + rdr.getDeviceName() + "）- 设备档案导出表");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(rdr);

        JSONObject jsonList = JSONObject.parseObject(json);
        List<ReportDeviceRecord> list = JSONArray.parseArray(jsonList.getString("list"), ReportDeviceRecord.class);
        if(CollectionUtils.isEmpty(list)) {
            reportDeviceRecord.setPageSize(0);
            list = deviceDao.reportFindDeviceRecordList(reportDeviceRecord);
        }

        String[] keys = {"rowNum", "type", "orderName", "createTimeDesc", "operationResult", "createUserName","orgName"};

        String[] files = reportDeviceRecord.getFiles();
        if (files != null){
            MultipartFile[] fs = new MultipartFile[files.length];
            for (int i = 0 ;i < files.length;i++){
                fs[i] = Base64DecodeMultipartFile.base64Convert(files[i]);
            }
            int[] pictRowIndexs = {2};
            int[] pictColIndexs = {0};
            ExcelUtils.createAndDownloadImgAndListExcelSingleSheetNotStyle(response, list, keys, ConstantsDevice.REPORT_DEVICE_RECORD_PIC_MODEL_FILE_PATH,
                    11, ConstantsDevice.REPORT_MODEL_DEVICE_RECORD_PIC, jsonObject, "1:0", fs, pictRowIndexs,pictColIndexs);
        }
    }

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    @Override
    public void downloadDeviceRecordByList(HttpServletResponse response, String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        json = jsonObject.getString("reportDeviceRecord");
        List<ReportDeviceRecord> list = JSONArray.parseArray(jsonObject.getString("list"), ReportDeviceRecord.class);
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);

        ReportDeviceRecord rdr = deviceDao.reportFindDeviceRecordBaseInfo(reportDeviceRecord);
        rdr.setTitle(rdr.getProjectName() + "-设备档案导出表");
        jsonObject = (JSONObject) JSONObject.toJSON(rdr);

        String[] keys = {"rowNum", "type", "orderName", "createTimeDesc", "operationResult", "createUserName","orgName"};

        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsDevice.REPORT_DEVICE_RECORD_MODEL_FILE_PATH,
                10, ConstantsDevice.REPORT_DEVICE_RECORD_DICT_ITEM_CODE, jsonObject, "1:0");
    }

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @Override
    public Data findDeviceRecordList(String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        Integer totalCount = reportDeviceRecord.getTotalCount();
        Integer pageSize = reportDeviceRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = deviceDao.reportFindDeviceRecordListTotalCount(reportDeviceRecord);
        }
        List<ReportDeviceRecord> list = deviceDao.reportFindDeviceRecordList(reportDeviceRecord);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @Override
    public Data findDeviceRecordCount(String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        reportDeviceRecord = deviceDao.findDeviceRecordCount(reportDeviceRecord);
        return asseData(reportDeviceRecord);
    }

    /**
     *
     * 功能描述:查询设备档案基本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @Override
    public Data findDeviceRecordBaseInfo(String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        reportDeviceRecord = deviceDao.reportFindDeviceRecordBaseInfo(reportDeviceRecord);
        return asseData(reportDeviceRecord);
    }

    /**
     *
     * 功能描述:以巡检点id查询设备档案基本信息总数
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    @Override
    public Data findDeviceRecordCountByPoint(String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        reportDeviceRecord = deviceDao.findDeviceRecordCountByPoint(reportDeviceRecord);
        return asseData(reportDeviceRecord);
    }

    /**
     * 功能描述:查询 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceRemind(String json) {
        DeviceRemind deviceRemind = JSONObject.parseObject(json, DeviceRemind.class);
        deviceRemind = deviceRemindDao.findDeviceRemind(deviceRemind);
        return asseData(deviceRemind);
    }

    /**
     * 功能描述:更新 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data updateDeviceRemind(String json) {
        DeviceRemind deviceRemind = JSONObject.parseObject(json, DeviceRemind.class);
        deviceRemindDao.updateDeviceRemind(deviceRemind);
        return asseData(deviceRemind);
    }

    /**
     * 功能描述:保存 设备过期提醒
     *
     * @param json 参数
     * @author huanggc
     * @date 2020-03-27
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveDeviceRemind(String json) {
        DeviceRemind deviceRemind = JSONObject.parseObject(json, DeviceRemind.class);
        deviceRemindDao.saveDeviceRemind(deviceRemind);
        return asseData(deviceRemind);
    }

    /**
     * 功能描述: 设备过期提醒 每天定时任务
     *
     * @author huanggc
     * @date 2020-03-30
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data deviceRemindTask() {
        // 查询设备了 提醒 的项目
        List<DeviceRemind> deviceRemindList = deviceRemindDao.findDeviceRemindTaskList();

        for (DeviceRemind deviceRemind: deviceRemindList) {
            List<Device> deviceList = deviceDao.findDeviceRemindTask(deviceRemind);

            Long projectId = deviceRemind.getProjectId();
            Integer deviceOrder = deviceRemind.getDeviceOrder();
            FaultRepairRecord faultRepairRecord = new FaultRepairRecord();
            for (Device device: deviceList) {
                // 生成 故障报修
                if (deviceOrder == 1){
                    faultRepairRecord.setProjectId(projectId);
                    faultRepairRecord.setDeviceName(device.getDeviceName());
                    faultRepairRecord.setDeviceQrNo(device.getQrNo());
                    faultRepairRecord.setDeviceLocation(device.getPointLocation());
                    faultRepairRecord.setBuildingId(device.getBuildingId());
                    faultRepairRecord.setBuildingFloorId(device.getBuildingFloorId());
                    faultRepairRecord.setFaultDescription("过期提醒");
                    faultRepairRecord.setWorkOrderStatus(1);

                    HttpUtils.httpGet(ConstantsDevice.SAVE_FAULT_URL + projectId, JSONObject.toJSONString(faultRepairRecord), "json");
                }

                List<String> roleSignList = new ArrayList<>(1);
                roleSignList.add("device_manage_edit");
                // 生成消息
                // 【消检通】项目名称/建筑物楼层具体位置/设备/设备名称设备ID过期，请及时处理。
                // 【消检通】深圳华星项目项目/20#生产厂房1F31轴交S轴 （东支持区）M3-154/消火栓AA00000000001过期，请及时处理。
                StringBuffer content = new StringBuffer();
                content.append("【消检通】");
                content.append(device.getProjectName());
                content.append("/" + device.getBuildingName());
                content.append(device.getBuildingFloorName());
                content.append(device.getPointLocation());
                content.append("/" + device.getDeviceName());
                content.append(device.getQrNo());
                content.append("即将过期，请及时处理。");

                Long recordId = faultRepairRecord.getId();
                return messageService.saveMessageRole(4, roleSignList, "设备过期通知", 1, 0,
                        "" +content, "", projectId, recordId, "", null);
            }
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 送修提醒 每天定时任务
     *
     * @author huanggc
     * @date 2020-03-30
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data repairRemindTask() {
        // 查询设备了 提醒 的项目
        List<DeviceRemind> deviceRemindList = deviceRemindDao.findDeviceRemindTaskList();

        for (DeviceRemind deviceRemind: deviceRemindList) {
            List<Device> deviceList = deviceDao.findRepairRemindTask(deviceRemind);

            Long projectId = deviceRemind.getProjectId();
            Integer repairOrder = deviceRemind.getRepairOrder();
            FaultRepairRecord faultRepairRecord = new FaultRepairRecord();
            for (Device device: deviceList) {
                // 生成 送修工单
                if (repairOrder == 1) {
                    faultRepairRecord.setProjectId(projectId);
                    faultRepairRecord.setDeviceName(device.getDeviceName());
                    faultRepairRecord.setDeviceQrNo(device.getQrNo());
                    faultRepairRecord.setDeviceLocation(device.getPointLocation());
                    faultRepairRecord.setBuildingId(device.getBuildingId());
                    faultRepairRecord.setBuildingFloorId(device.getBuildingFloorId());
                    faultRepairRecord.setFaultDescription("送修提醒");
                    faultRepairRecord.setWorkOrderStatus(1);

                    HttpUtils.httpGet(ConstantsDevice.SAVE_FAULT_URL + projectId, JSONObject.toJSONString(faultRepairRecord), "json");
                }

                List<String> roleSignList = new ArrayList<>(1);
                roleSignList.add("device_manage_edit");
                // 生成消息
                // 【消检通】项目名称/建筑物楼层具体位置/设备/设备名称设备ID过期，请及时处理。
                // 【消检通】深圳华星项目项目/20#生产厂房1F31轴交S轴 （东支持区）M3-154/消火栓AA00000000001过期，请及时处理。
                StringBuffer content = new StringBuffer();
                content.append("【消检通】");
                content.append(device.getProjectName());
                content.append("/" + device.getBuildingName());
                content.append(device.getBuildingFloorName());
                content.append(device.getPointLocation());
                content.append("/" + device.getDeviceName());
                content.append(device.getQrNo());
                content.append("即将送修，请及时处理。");

                Long recordId = faultRepairRecord.getId();
                return messageService.saveMessageRole(4, roleSignList, "送修通知", 2, 0,
                        "" + content, "", projectId, recordId, "", null);
            }
        }
        return Data.isSuccess();
    }

    /**
     * @Description 查询设备操作记录饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/2/22 14:05
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceReportPie(String json){
        ReportDeviceRecord reportDeviceRecord = JSONObject.parseObject(json, ReportDeviceRecord.class);
        return asseData(deviceDao.findDeviceReportPie(reportDeviceRecord));
    }
}
