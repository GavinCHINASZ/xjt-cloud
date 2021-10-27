package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.device.TaskDeviceDao;
import com.xjt.cloud.task.core.dao.project.TaskOrganizationDao;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.*;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.check.*;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.fault.FaultRepairRecord;
import com.xjt.cloud.task.core.entity.task.AppTaskCheckItem;
import com.xjt.cloud.task.core.entity.task.AppTaskDevice;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.service.service.CheckRecordService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 巡检记录 CheckRecordServiceImpl
 *
 * @author dwt
 * @version 1.0
 * @date 2019-07-26 11:31
 */
@Service
public class CheckRecordServiceImpl extends AbstractService implements CheckRecordService {
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private TaskDeviceDao taskDeviceDao;
    @Autowired
    private CheckRecordImageDao checkRecordImageDao;
    @Autowired
    private CheckItemRecordDao checkItemRecordDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;
    @Autowired
    private TaskCheckPointDao taskCheckPointDao;
    @Autowired
    private TaskOrganizationDao taskOrganizationDao;
    @Autowired
    private TaskSpotCheckToolDao taskSpotCheckToolDao;
    @Autowired
    private Configuration configuration;

    /**
     * 查询巡检记录列表(APP 维保记录)
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt             huangGuiChuan
     * @date 2019-07-26        2019-12-04
     */
    @Override
    public Data findCheckRecordList(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        checkInitJudge(checkRecord);

        Long versionNo = checkRecord.getVersionNo();
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        Map<String, Object> map = new HashMap<>();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = checkRecordDao.findCheckRecordTotalCount(checkRecord);
            CheckRecord entity = checkRecordDao.findCheckRecordData(checkRecord);
            CheckRecord checkRecordData = checkRecordDao.findCheckRecordDeviceData(checkRecord);
            if (null == entity) {
                entity = new CheckRecord();
            }
            // 总条数 / 巡查记录总数
            map.put("totalCount", totalCount);
            if (null == versionNo) {
                // 故障巡查数
                map.put("faultCheckCount", entity.getFaultCheckCount());
            } else {
                // 故障巡查数
                map.put("faultCheckCount", checkRecordData.getFaultDeviceCount());
            }
            // 总设备数
            map.put("deviceCount", checkRecordData.getDeviceCount());
            // 故障设备数量
            map.put("faultDeviceCount", checkRecordData.getFaultDeviceCount());
            // 正常设备数量
            map.put("normalDeviceCount", checkRecordData.getNormalDeviceCount());
        }

        if (null == checkRecord.getOrderCols()) {
            String[] orderCols = {"createTime"};
            checkRecord.setOrderCols(orderCols);
            checkRecord.setOrderDesc(true);
        }

        List<CheckRecord> checkRecordList;
        if (null == versionNo) {
            checkRecordList = checkRecordDao.findCheckRecordList(checkRecord);
        } else {
            if (versionNo == 0) {
                CheckRecord record = checkRecordDao.findCheckRecordIdString(checkRecord);
                if (record != null) {
                    String idString = record.getIdString();
                    if (StringUtils.isNotEmpty(idString)) {
                        checkRecord.setIdString(idString);
                    }
                }
            }
            checkRecordList = checkRecordDao.findCheckRecordLists(checkRecord);

            totalCount = checkRecordDao.findCheckRecordTotalCounts(checkRecord);
            // 总条数 / 巡查记录总数
            map.put("totalCount", totalCount);
        }
        // 巡检记录
        map.put("checkRecordList", checkRecordList);
        return asseData(map);
    }

    /**
     * 查询app首页巡检记录信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return JSONObject
     */
    @Override
    public JSONObject findUserProjectCheckRecordData(String json){
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        CheckRecord checkRecordData = checkRecordDao.findCheckRecordDeviceData(checkRecord);

        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("modelIndex",14);
        if (checkRecordData != null) {
            jsonObject.put("deviceCount", checkRecordData.getDeviceCount());
            // 故障设备数量
            jsonObject.put("faultDeviceCount", checkRecordData.getFaultDeviceCount());
            // 正常设备数量
            jsonObject.put("normalDeviceCount", checkRecordData.getNormalDeviceCount());
        }else{
            jsonObject.put("deviceCount", 0);
            // 故障设备数量
            jsonObject.put("faultDeviceCount", 0);
            // 正常设备数量
            jsonObject.put("normalDeviceCount", 0);
        }
        return jsonObject;
    }

    /**
     * 一些初始值判断
     *
     * @param checkRecord CheckRecord
     */
    private void checkInitJudge(CheckRecord checkRecord) {
        if (null != checkRecord.getStartDate() && null != checkRecord.getEndDate()) {
            // 自定义时间
            Date startDayDate = DateUtils.startDayDate(checkRecord.getStartDate());
            Date endDayDate = DateUtils.endDayDate(checkRecord.getEndDate());

            checkRecord.setStartDate(startDayDate);
            checkRecord.setEndDate(endDayDate);
        }

        Boolean nearDate = checkRecord.getNearDate();
        if (null != nearDate && nearDate) {
            // 近30天
            Date beforeOrAfterDate = DateUtils.getBeforeOrAfterDate(null, -29);
            Date startDayDate = DateUtils.startDayDate(beforeOrAfterDate);
            Date endDayDate = DateUtils.endDayDate(null);

            checkRecord.setStartDate(startDayDate);
            checkRecord.setEndDate(endDayDate);
        }
    }

    /**
     * 查询巡检记录详情
     *
     * @param checkRecordId 巡检记录ID
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-07-26 11:37
     */
    @Override
    public Data findCheckRecordById(Long checkRecordId) {
        CheckRecord checkRecord = checkRecordDao.findCheckRecordById(checkRecordId);
        return asseData(checkRecord);
    }

    /**
     * 保存巡检记录
     *
     * @param json    参数
     * @param request HttpServletRequest
     * @return 主键id
     * @author dwt
     * @date 2019-07-26 11:37
     */
    @Override
    public Data saveCheckRecord(String json, HttpServletRequest request) {
        TaskCheckPoint tcp = JSONObject.parseObject(json, TaskCheckPoint.class);
        if (tcp == null || tcp.getCheckRecords().size() == 0) {
            return asseData(600, "没有巡检记录提交！");
        }

        List<TaskCheckPoint> list = new ArrayList<>(1);
        list.add(tcp);
        return saveTaskCheckRecord(list, tcp.getTaskId(), tcp);
    }

    /**
     * 保存巡检任务记录
     *
     * @param list TaskCheckPoint实体
     * @param task 任务
     * @return Data
     */
    private Data saveTaskCheckRecord(List<TaskCheckPoint> list, Task task) {
        // 巡检记录
        List<CheckRecord> checkRecords = new ArrayList<>();

        // 巡检项
        List<CheckItemRecord> checkItemRecords = new ArrayList<>();

        // 故障设备ID
        List<Long> faultDeviceList = new ArrayList<>();

        // 故障设备ID
        List<Long> normalDeviceList = new ArrayList<>();

        // 版本号
        Long versionNo = System.currentTimeMillis();

        // 巡检记录图片集合
        List<CheckRecordImage> images = new ArrayList<>();

        // 需要报修的故障记录
        List<CheckRecord> faultRecordList = new ArrayList<>();

        // 故障的巡查点
        List<TaskCheckPoint> faultTaskCheckPointList = new ArrayList<>();

        // 正常巡查点
        List<TaskCheckPoint> normalTaskCheckPointList = new ArrayList<>();

        int taskCheckPointStatus = 2;
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        TaskOrganization org = taskOrganizationDao.findUserOrgName(userId, task.getProjectId());

        for (TaskCheckPoint tcp : list) {
            List<CheckRecordImage> taskCheckPointImage = taskCheckPointImageInit(task.getId(), tcp.getCheckPointId(), task.getImageUrls(), 1);
            images.addAll(taskCheckPointImage);

            Date checkStartTime = tcp.getCheckStartTime();
            // tcp.setOrgName(CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, tcp.getOrgId(), "orgName"));
            for (CheckRecord checkRecord : tcp.getCheckRecords()) {
                checkRecord.setCheckType(task.getTaskType());
                checkRecord = setEntityUserIdName(SecurityUserHolder.getUserName(), tcp.getProjectId(), checkRecord);

                checkRecord = checkRecordInit(checkRecord, tcp.getProjectId(), tcp.getTaskId(), tcp.getCheckPointId(), tcp.getBuildingId(), tcp.getBuildingFloorId(),
                        tcp.getCheckPointName(), task.getTaskName(), tcp.getQrNo(), tcp.getBuildingName(), tcp.getFloorName(), tcp.getPointLocation(), org,
                        checkStartTime);

                checkRecords.add(checkRecord);
                checkRecord.setVersionNo(versionNo);
                if (checkRecord.getHandleStatus() == 1) {
                    // 故障设备
                    faultDeviceList.add(checkRecord.getDeviceId());
                    taskCheckPointStatus = 1;
                    checkRecord.setCheckResult(1);
                } else if (checkRecord.getHandleStatus() == 2) {
                    // 故障设备但是已修复完成
                    normalDeviceList.add(checkRecord.getDeviceId());
                    taskCheckPointStatus = 1;
                    checkRecord.setCheckResult(1);
                } else {
                    // 正常设备
                    normalDeviceList.add(checkRecord.getDeviceId());
                    checkRecord.setCheckResult(0);
                }
            }

            tcp.setTaskCheckPointStatus(taskCheckPointStatus);
            if (tcp.getTaskCheckPointStatus() == 1) {
                faultTaskCheckPointList.add(tcp);
            } else {
                normalTaskCheckPointList.add(tcp);
            }
        }

        // 保存巡检记录
        Set<Long> set = new HashSet<>();
        checkRecordDao.saveCheckRecordList(checkRecords);
        for (CheckRecord checkRecord : checkRecords) {
            List<CheckRecordImage> checkRecordImages = checkRecordImageInit(checkRecord.getId(), checkRecord.getTaskId(), checkRecord.getCheckPointId(),
                    task.getImageUrls(), 0);
            images.addAll(checkRecordImages);

            StringBuilder faultReason = new StringBuilder();
            StringBuilder resultDescription = new StringBuilder();
            for (CheckItemRecord icr : checkRecord.getCheckItemTaskList()) {
                icr = setEntityUserIdName(SecurityUserHolder.getUserName(), checkRecord.getProjectId(), icr);
                icr = checkItemRecordInit(icr, checkRecord.getDeviceId(), checkRecord.getDeviceName(), checkRecord.getTaskId(), checkRecord.getProjectId(),
                        checkRecord.getId(), checkRecord.getCheckPointId());

                if (icr.getCheckResult() == null || icr.getCheckResult() == 0) {
                    // 统计正常设备ID
                    icr.setCheckResult(0);
                } else {
                    if (set.add(checkRecord.getId())) {
                        if (checkRecord.getHandleStatus() != 0) {
                            // 故障且未修复，添加报修故障
                            faultRecordList.add(checkRecord);
                        }
                    }

                    if (checkRecord.getHandleStatus() != 0) {
                        // 故障且未修复
                        if (StringUtils.isNotEmpty(icr.getCheckName())){
                            faultReason.append(icr.getCheckName()).append("xjtgzbx;");
                        }

                        if (StringUtils.isNotEmpty(icr.getResultDescription())){
                            resultDescription.append(icr.getResultDescription()).append("xjtgzbx;");
                        }
                        if (StringUtils.isNotEmpty(icr.getDeviceFaultType())){
                            resultDescription.append(icr.getDeviceFaultType()).append("xjtgzbx;");
                        }
                    }
                }
                checkItemRecords.add(icr);
            }

            // 设置报修记录的故障部位和故障描述
            if (checkRecord.getFaultReason() == null) {
                checkRecord.setFaultLocation(faultReason.toString());
                checkRecord.setFaultReason(resultDescription.toString());
            }
        }

        //保存巡检结果
        if (checkItemRecords.size() > 0) {
            checkItemRecordDao.saveCheckItemRecordList(checkItemRecords);
        }

        // 保存巡检记录图片
        if (images.size() > 0) {
            checkRecordImageDao.saveCheckRecordImageList(images);
        }

        // 更新巡检点和设备状态
        updateTaskCheckPointAndDeviceStatus(faultTaskCheckPointList, normalTaskCheckPointList, faultDeviceList, normalDeviceList);

        // 添加故障报修
        if (faultRecordList.size() > 0) {
            saveFaultRecords(faultRecordList, faultRecordList.get(0).getProjectId());
        }
        return Data.isSuccess();
    }

    /**
     * 保存巡检任务记录
     *
     * @param list   TaskCheckPoint实体
     * @param taskCP TaskCheckPoint
     * @return Data
     */
    private Data saveTaskCheckRecord(List<TaskCheckPoint> list, Long taskId, TaskCheckPoint taskCP) {
        // 巡检记录
        List<CheckRecord> checkRecords = new ArrayList<>();

        // 巡检项
        List<CheckItemRecord> checkItemRecords = new ArrayList<>();

        // 故障设备ID
        List<Long> faultDeviceList = new ArrayList<>();

        // 故障设备ID
        List<Long> normalDeviceList = new ArrayList<>();

        // 版本号
        Long versionNo = System.currentTimeMillis();

        // 巡检记录图片集合
        List<CheckRecordImage> images = new ArrayList<>();

        // 需要报修的故障记录
        List<CheckRecord> faultRecordList = new ArrayList<>();

        // 故障的巡查点
        List<TaskCheckPoint> faultTaskCheckPointList = new ArrayList<>();

        // 正常巡查点
        List<TaskCheckPoint> normalTaskCheckPointList = new ArrayList<>();

        Task task = taskDao.findTaskById(taskId);
        int taskCheckPointStatus = 2;
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        TaskOrganization org = taskOrganizationDao.findUserOrgName(userId, task.getProjectId());

        for (TaskCheckPoint tcp : list) {
            List<CheckRecordImage> taskCheckPointImage;
            if (taskCP != null) {
                taskCheckPointImage = taskCheckPointImageInit(tcp.getTaskId(), tcp.getCheckPointId(), taskCP.getImageUrls(), 1);
            } else {
                taskCheckPointImage = taskCheckPointImageInit(tcp.getTaskId(), tcp.getCheckPointId(), tcp.getImageUrls(), 1);
            }

            images.addAll(taskCheckPointImage);
            Date checkStartTime = tcp.getCheckStartTime();
            // tcp.setOrgName(CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, tcp.getOrgId(), "orgName"));
            for (CheckRecord checkRecord : tcp.getCheckRecords()) {
                checkRecord.setCheckType(task.getTaskType());
                checkRecord = setEntityUserIdName(SecurityUserHolder.getUserName(), tcp.getProjectId(), checkRecord);

                checkRecord = checkRecordInit(checkRecord, tcp.getProjectId(), tcp.getTaskId(), tcp.getCheckPointId(), tcp.getBuildingId(), tcp.getBuildingFloorId(),
                        tcp.getCheckPointName(), task.getTaskName(), tcp.getQrNo(), tcp.getBuildingName(), tcp.getFloorName(), tcp.getPointLocation(), org,
                        checkStartTime);

                // 兼容5.0.5
                if (CollectionUtils.isEmpty(checkRecord.getImageUrls())) {
                    checkRecord.setImageUrls(tcp.getImageUrls());
                }
                checkRecords.add(checkRecord);
                checkRecord.setVersionNo(versionNo);
                if (checkRecord.getHandleStatus() == 1) {
                    // 故障设备
                    faultDeviceList.add(checkRecord.getDeviceId());
                    taskCheckPointStatus = 1;
                    checkRecord.setCheckResult(1);
                } else if (checkRecord.getHandleStatus() == 2) {
                    // 故障设备 已修复完成
                    normalDeviceList.add(checkRecord.getDeviceId());
                    taskCheckPointStatus = 1;
                    checkRecord.setCheckResult(1);
                } else {
                    // 正常设备
                    normalDeviceList.add(checkRecord.getDeviceId());
                    checkRecord.setCheckResult(0);
                }
            }
            tcp.setTaskCheckPointStatus(taskCheckPointStatus);
            if (tcp.getTaskCheckPointStatus() == 1) {
                faultTaskCheckPointList.add(tcp);
            } else {
                normalTaskCheckPointList.add(tcp);
            }
        }

        // 保存巡检记录
        Set<Long> set = new HashSet<>();
        checkRecordDao.saveCheckRecordList(checkRecords);
        for (CheckRecord checkRecord : checkRecords) {
            List<CheckRecordImage> checkRecordImages = checkRecordImageInit(checkRecord.getId(), checkRecord.getTaskId(), checkRecord.getCheckPointId(),
                    checkRecord.getImageUrls(), 0);
            images.addAll(checkRecordImages);

            StringBuilder faultReason = new StringBuilder();
            StringBuilder resultDescription = new StringBuilder();
            for (CheckItemRecord icr : checkRecord.getCheckItemTaskList()) {
                icr = setEntityUserIdName(SecurityUserHolder.getUserName(), checkRecord.getProjectId(), icr);
                icr = checkItemRecordInit(icr, checkRecord.getDeviceId(), checkRecord.getDeviceName(), checkRecord.getTaskId(), checkRecord.getProjectId(),
                        checkRecord.getId(), checkRecord.getCheckPointId());

                if (icr.getCheckResult() == null || icr.getCheckResult() == 0) {
                    //统计正常设备ID
                    icr.setCheckResult(0);
                } else {
                    if (set.add(checkRecord.getId())) {
                        if (checkRecord.getHandleStatus() != 0) {
                            //故障且未修复，添加报修故障
                            faultRecordList.add(checkRecord);
                        }
                    }

                    if (checkRecord.getHandleStatus() != 0) {
                        // 故障且未修复
                        if (StringUtils.isNotEmpty(icr.getCheckName())){
                            faultReason.append(icr.getCheckName()).append("xjtgzbx;");
                        }

                        if (StringUtils.isNotEmpty(icr.getResultDescription())){
                            resultDescription.append(icr.getResultDescription()).append("xjtgzbx;");
                        }
                        if (StringUtils.isNotEmpty(icr.getDeviceFaultType())){
                            resultDescription.append(icr.getDeviceFaultType()).append("xjtgzbx;");
                        }
                    }
                }
                checkItemRecords.add(icr);
            }

            //设置报修记录的故障部位和故障描述
            if (checkRecord.getFaultReason() == null) {
                checkRecord.setFaultLocation(faultReason.toString());
                checkRecord.setFaultReason(resultDescription.toString());
            }
        }

        //保存巡检结果
        if (checkItemRecords.size() != 0) {
            checkItemRecordDao.saveCheckItemRecordList(checkItemRecords);
        }

        // 保存巡检记录图片
        if (images.size() > 0) {
            checkRecordImageDao.saveCheckRecordImageList(images);
        }

        // 更新巡检点和设备状态
        updateTaskCheckPointAndDeviceStatus(faultTaskCheckPointList, normalTaskCheckPointList, faultDeviceList, normalDeviceList);

        // 添加故障报修
        if (faultRecordList.size() > 0) {
            saveFaultRecords(faultRecordList, faultRecordList.get(0).getProjectId());

            JSONObject jsonObject = new JSONObject(3);
            jsonObject.put("msg", 200);
            jsonObject.put("projectId", task.getProjectId());
            jsonObject.put("iotType", ConstantsDevice.CHECK_RECORD);
            WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        }
        return Data.isSuccess();
    }

    /**
     * 更新
     *
     * @param faultTaskCheckPointList  List<TaskCheckPoint>
     * @param normalTaskCheckPointList List<TaskCheckPoint>
     * @param faultDeviceList          List<Long>
     * @param normalDeviceList         List<Long>
     */
    private void updateTaskCheckPointAndDeviceStatus(List<TaskCheckPoint> faultTaskCheckPointList, List<TaskCheckPoint> normalTaskCheckPointList,
                                                     List<Long> faultDeviceList, List<Long> normalDeviceList) {

        // 修改状态为故障
        if (faultTaskCheckPointList.size() > 0) {
            //修改任务巡检点状态
            taskCheckPointDao.updateTaskDeviceCheckPointStatus(faultTaskCheckPointList, 1);
            //修改设备巡查点状态
            deviceCheckPointDao.updateDeviceCheckPointStatus(faultTaskCheckPointList, 2);
        }
        //修改状态为正常
        if (normalTaskCheckPointList.size() > 0) {
            //修改任务巡检点状态
            taskCheckPointDao.updateTaskDeviceCheckPointStatus(normalTaskCheckPointList, 2);
            //修改设备巡查点状态
            deviceCheckPointDao.updateDeviceCheckPointStatus(normalTaskCheckPointList, 1);
        }

        //修改设备状态为故障
        if (faultDeviceList.size() > 0) {
            taskDeviceDao.updateDeviceStatus(faultDeviceList, 2);
        }

        //修改设备状态为正常
        if (normalDeviceList.size() > 0) {
            taskDeviceDao.updateDeviceStatus(normalDeviceList, 1);
        }
    }

    /**
     * 保存故障报修记录
     *
     * @param faultRecordList faultRecordList
     * @param projectId       项目ID
     * @author zhangZaiFa
     * @date 2019/12/18 18:24
     **/
    private void saveFaultRecords(List<CheckRecord> faultRecordList, Long projectId) {
        List<FaultRepairRecord> list = new ArrayList<>();
        FaultRepairRecord data = new FaultRepairRecord();
        data.setProjectId(projectId);
        data.setFaultRepairRecordList(list);
        // 用户名
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        for (CheckRecord checkRecord : faultRecordList) {
            FaultRepairRecord frr = new FaultRepairRecord(checkRecord, checkRecord.getOrgId());
            frr.setCreateUserName(userName);
            frr.setCreateUserId(userId);
            list.add(frr);
        }

        SysLog.info(ConstantsDevice.FAULT_REPAIR_RECORD_URL + projectId + "------------>故障报修连接");
        SysLog.info(JSONObject.toJSONString(data) + "------------>发送数据");

        try {
            HttpUtils.httpGet(ConstantsDevice.FAULT_REPAIR_RECORD_URL + projectId, JSONObject.toJSONString(data), "json");
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 生成任务巡检点记录图片
     *
     * @param taskId 任务ID
     * @param checkPointId 巡检点ID
     * @param imageUrls 图片
     * @param type 类型
     * @return java.util.List<com.xjt.cloud.task.core.entity.check.CheckRecordImage>
     * @author zhangZaiFa
     * @date 2019/11/29 17:19
     **/
    private List<CheckRecordImage> taskCheckPointImageInit(Long taskId, Long checkPointId, List<String> imageUrls, Integer type) {
        List<CheckRecordImage> list = new ArrayList<>();
        if (imageUrls == null) {
            return list;
        }

        for (String imageUrl : imageUrls) {
            CheckRecordImage cri = new CheckRecordImage(imageUrl, taskId, checkPointId, type);
            list.add(cri);
        }
        return list;
    }

    /**
     * 生成巡检记录图片
     *
     * @return java.util.List<com.xjt.cloud.task.core.entity.check.CheckRecordImage>
     * @author zhangZaiFa
     * @date 2019/11/29 17:10
     **/
    private List<CheckRecordImage> checkRecordImageInit(Long checkRecordId, Long taskId, Long checkPointId, List<String> imageUrls, int type) {
        List<CheckRecordImage> list = new ArrayList<>();
        if (imageUrls == null) {
            return list;
        }

        for (String imageUrl : imageUrls) {
            CheckRecordImage cri = new CheckRecordImage(imageUrl, checkRecordId, taskId, checkPointId, type);
            list.add(cri);
        }
        return list;
    }

    /**
     * @param icr           CheckItemRecord
     * @param deviceId      设备ID
     * @param deviceName    设备名称
     * @param taskId        任务ID
     * @param projectId     项目ID
     * @param checkRecordId 巡检记录ID
     * @param checkPointId  巡检点id
     * @return CheckItemRecord
     */
    private CheckItemRecord checkItemRecordInit(CheckItemRecord icr, Long deviceId, String deviceName, Long taskId, Long projectId, Long checkRecordId,
                                                Long checkPointId) {

        icr.setProjectId(projectId);
        icr.setTaskId(taskId);
        icr.setDeviceName(deviceName);
        icr.setDeviceId(deviceId);
        icr.setCheckRecordId(checkRecordId);
        icr.setCheckPointId(checkPointId);
        icr.setCheckId(icr.getId());
        return icr;
    }

    /**
     * 巡检记录初始数据
     *
     * @return com.xjt.cloud.task.core.entity.check.CheckRecord
     * @author zhangZaiFa
     * @date 2019/11/28 17:03
     **/
    private CheckRecord checkRecordInit(CheckRecord checkRecord, Long projectId, Long taskId, Long checkPointId,
                                        Long buildingId, Long buildingFloorId, String checkPointName, String taskName,
                                        String qrNo, String buildingName, String floorName, String pointLocation, TaskOrganization org, Date checkStartTime) {

        checkRecord.setProjectId(projectId);
        checkRecord.setCheckPointId(checkPointId);
        checkRecord.setCheckPointQrNo(qrNo);
        checkRecord.setPointLocation(pointLocation);
        checkRecord.setBuildingName(buildingName);
        checkRecord.setFloorName(floorName);
        checkRecord.setCheckPointName(checkPointName);
        checkRecord.setTaskName(taskName);
        checkRecord.setTaskId(taskId);
        checkRecord.setBuildingId(buildingId);
        checkRecord.setBuildingFloorId(buildingFloorId);

        if (null != checkStartTime) {
            checkRecord.setCheckStartTime(checkStartTime);
        }

        if (org != null) {
            checkRecord.setOrgId(org.getId());
            checkRecord.setOrgName(org.getOrgName());
        }
        if (checkRecord.getHandleStatus() == null) {
            checkRecord.setHandleStatus(0);
        }
        if (checkRecord.getCreateTime() == null) {
            checkRecord.setCreateTime(new Date());
        }
        return checkRecord;
    }

    /**
     * 更新巡检记录
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-07-26 11:38
     */
    @Override
    public Data modifyCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer num = checkRecordDao.modifyCheckRecord(checkRecord);
        return asseData(num);
    }

    /**
     * PC巡检记录--导出列表功能
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huangGuiChuan
     * @date 2019-08-13
     */
    @Override
    public void downCheckRecord(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        checkRecord.setDeleted(false);
        // 不分页
        checkRecord.setPageSize(null);

        checkInitJudge(checkRecord);

        if (null == checkRecord.getOrderCols()) {
            String[] orderCols = {"createTime"};
            checkRecord.setOrderCols(orderCols);
            checkRecord.setOrderDesc(true);
        }
        List<CheckRecords> list = checkRecordDao.findDownCheckRecordList(checkRecord);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Long projectId = checkRecord.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--巡检记录列表导出");

        String[] keys = {"rowNum", "deviceName", "deviceQrNo", "deviceCount", "checkPointLocation", "checkResultDesc", "createTimeDescs", "checkPointName",
                "checkPointQrNo", "checkerName", "taskName"};

        ExcelUtils.createAndDownloadExcelNotStyle(resp, list, keys, ConstantsDevice.CHECK_RECORD_MODEL_FILE_PATH, 3, null,
                jsonObject, "1:0");
    }

    /**
     * 功能描述: 根据巡检记录id查询巡检详情
     *
     * @param json 参数
     * @return Data
     * @author dwt       huangGuiChuan
     * @date 2019-08-14  2019-12-05
     */
    @Override
    public Data findCheckRecordDetails(String json) {
        CheckDetails checkDetails = JSONObject.parseObject(json, CheckDetails.class);
        Long checkRecordId = checkDetails.getCheckRecordId();
        checkDetails = checkRecordDao.findCheckDetailsByCheckRecordId(checkRecordId);

        List<CheckItemRecord> checkItemRecordList = checkItemRecordDao.findByCheckDetails(checkDetails);

        CheckRecordImage checkRecordImage = new CheckRecordImage();
        checkRecordImage.setCheckRecordId(checkRecordId);
        checkRecordImage.setType(0);
        List<String> checkRecordImageList = checkRecordImageDao.findCheckRecordImageList(checkRecordImage);

        Map<String, Object> map = new HashMap<>(3);
        // 巡检详情(设备基本信息)
        map.put("checkDetails", checkDetails);
        // 巡查信息
        map.put("checkItemRecordList", checkItemRecordList);
        // 图片URL
        map.put("checkRecordImageList", checkRecordImageList);
        return asseData(map);
    }

    /**
     * App端根据巡检记录id查询已检设备巡检详情, 根据任务id查询未检设备巡检详情
     *
     * @return 巡检详情
     * @author dwt
     * @date 2019-08-15 16:37
     */
    @Override
    public Data findCheckRecordDetailsByIdApp(String json) {
        AppTaskDevice appTaskDevice = JSONObject.parseObject(json, AppTaskDevice.class);
        Long checkRecordId = appTaskDevice.getCheckRecordId();
        if (checkRecordId != null && checkRecordId != 0) {
            AppTaskCheckRecord appTaskCheckRecord = checkRecordDao.findCheckRecordDetailsById(checkRecordId);
            if (appTaskCheckRecord != null) {
                List<AppTaskCheckItem> appTaskCheckItemList = checkItemRecordDao.findCheckItemByCheckRecordIdApp(checkRecordId);
                appTaskCheckRecord.setTaskCheckItemList(appTaskCheckItemList);
            }
            return asseData(appTaskCheckRecord);
        } else {
            AppTaskDeviceItem appTaskDeviceItem = taskDao.findTaskDeviceDetailsApp(appTaskDevice);
            if (appTaskDeviceItem != null) {
                TaskExecutor taskExecutor = new TaskExecutor();
                taskExecutor.setTaskId(appTaskDevice.getTaskId());
                taskExecutor.setProjectId(appTaskDevice.getProjectId());
                taskExecutor.setExecutorType(0);
                List<String> userNameList = userDao.findUserNameByTaskId(taskExecutor);
                appTaskDeviceItem.setExecutorName(userNameList);
                String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, appTaskDevice.getProjectId() + "", "checkItemVsType");
                if (StringUtils.isNotEmpty(checkItemVsType)) {
                    appTaskDevice.setCheckItemVsType(Integer.parseInt(checkItemVsType));
                } else {
                    appTaskDevice.setCheckItemVsType(1);
                }
                List<AppTaskCheckItem> checkNameList = taskDeviceDao.findDeviceItemByDeviceIdApp(appTaskDevice);
                appTaskDeviceItem.setAppTaskCheckItemList(checkNameList);
            }
            return asseData(appTaskDeviceItem);
        }
    }

    /**
     * 查询巡检点设备的巡检项
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/27 10:42
     **/
    @Override
    public Data findCheckPointDeviceCheckItem(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Task task = taskDao.findTaskById(checkRecord.getTaskId());
        TaskCheckPoint tcp = taskCheckPointDao.findCheckPointIdAndTaskId(checkRecord.getCheckPointId(), checkRecord.getTaskId());

        if (tcp.getImgUrl() == null) {
            tcp.setImgUrl(ConstantsDevice.CHECK_POINT_IMAGE_URL);
        }

        // check_date_type 1默认(所有任务周期类型都要查) 2月度, 3季度, 4年度
        // 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:日任务（checkCount 0：一日一次，1：一日多次）
        Integer taskPeriodType = task.getTaskPeriodType();
        boolean b = false;
        Integer[] a = new Integer[2];
        if(taskPeriodType != null) {
            a[0] = 1;
            if (taskPeriodType == 0 || taskPeriodType == 1 || taskPeriodType == 2) {
                b = true;
                a[1] = 4;
            } else if (taskPeriodType == 4 || taskPeriodType == 5 || taskPeriodType == 6) {
                b = true;
                a[1] = 2;
            } else if (taskPeriodType == 3) {
                b = true;
                a[1] = 3;
            }
        }

        List<Device> devices = taskDeviceDao.findCheckPointIdDeviceList(checkRecord.getCheckPointId());
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        int checkItemType = StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1;
        for (Device device : devices) {
            device.setCheckItemVsType(checkItemType);
            device.setProjectId(checkRecord.getProjectId());

            if(b){
                device.setCheckDateTypeArr(a);
            } else {
                device.setCheckDateType(1);
            }

            List<CheckItemTask> list = checkItemRecordDao.findCheckItemList(device);

            device.setCheckItemTaskList(new ArrayList<>());

            if (list != null && list.size() > 0) {
                for (CheckItemTask checkItemTask : list) {
                    Integer taskType = task.getTaskType();
                    Integer checkAction = checkItemTask.getCheckAction();
                    if (taskType == 4 && checkAction == 1){
                        device.getCheckItemTaskList().add(checkItemTask);
                    } else if (taskType + 1 == checkAction) {
                        device.getCheckItemTaskList().add(checkItemTask);
                    }
                }
            }
        }
        List<TaskSpotCheckTool> taskSpotCheckTools = taskSpotCheckToolDao.findTaskSpotCheckTools(checkRecord.getTaskId());
        task.setTaskSpotCheckTools(taskSpotCheckTools);
        List<String> executorNameList = new ArrayList<>(1);
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        executorNameList.add(getOrgUserName(userId, task.getProjectId()));
        task.setTaskExecutorsName(executorNameList);

        Map<String, Object> data = new HashMap<>(3);
        data.put("task", task);
        data.put("checkPoint", tcp);
        data.put("devices", devices);
        return asseData(data);
    }

    /**
     * findCheckRecordLocation
     * 查询任务巡查点巡检记录
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/29 9:36
     **/
    @Override
    public Data findTaskCheckPointCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        List<CheckRecord> list = checkRecordDao.findCheckPointRecordList(checkRecord.getCheckPointId(), checkRecord.getTaskId());
        JSONObject data = new JSONObject();
        data.put("oldCheckRecord", list);
        if (checkRecord.getTaskId() == null) {
            // 查询巡检点最新一条巡检记录的taskId
            CheckRecord oldCheckRecord = checkRecordDao.findCheckRecord(checkRecord);
            if (oldCheckRecord == null) {
                return asseData(600, "暂无记录");
            }
            checkRecord.setTaskId(oldCheckRecord.getTaskId());
        }

        Task task = taskDao.findTaskById(checkRecord.getTaskId());

        TaskCheckPoint tcp = taskCheckPointDao.findCheckPointIdAndTaskId(checkRecord.getCheckPointId(), checkRecord.getTaskId());

        CheckRecordImage cri = new CheckRecordImage();
        cri.setType(0);

        // 地铁 sign = metro
        boolean signBoolean = StringUtils.isNotEmpty(checkRecord.getSign());
        List<CheckRecord> checkRecords = checkRecordDao.findTaskCheckPointCheckRecordList(checkRecord);
        Long[] checkRecordIdList = new Long[CollectionUtils.isNotEmpty(checkRecords) ? checkRecords.size() : 1];
        for (int i = 0; i < checkRecords.size(); i++) {
            CheckRecord cr = checkRecords.get(i);
            List<CheckItemRecord> checkItemTasks = checkItemRecordDao.findCheckItemRecordByCheckRecordId(cr.getId());
            cr.setCheckItemTaskList(checkItemTasks);

            if (signBoolean) {
                cri.setCheckRecordId(cr.getId());
                List<String> checkRecordImageUrls = checkRecordImageDao.findCheckRecordImageList(cri);
                cr.setImageUrls(checkRecordImageUrls);
                data.put("imageUrls", checkRecordImageUrls);
            } else {
                checkRecordIdList[i] = cr.getId();
            }
        }

        if (CollectionUtils.isEmpty(checkRecords)) {
            return asseData(600, "暂无记录");
        }

        if (!signBoolean) {
            cri.setCheckRecordIdList(checkRecordIdList);
            List<CheckRecordImage> checkRecordImageList = checkRecordImageDao.findCheckRecordImageEntityList(cri);
            if (CollectionUtils.isNotEmpty(checkRecordImageList)) {
                for (CheckRecord record : checkRecords) {
                    List<String> imgList = new ArrayList<>();
                    for (CheckRecordImage recordImage : checkRecordImageList) {
                        if (record.getId().equals(recordImage.getCheckRecordId())) {
                            // 兼容5.0.5版本
                            imgList.add(recordImage.getImageUrl());
                        }
                    }
                    record.setImageUrls(imgList);
                }

                List<String> imageUrls = new ArrayList<>(checkRecordImageList.size());
                for (CheckRecordImage recordImage : checkRecordImageList) {
                    imageUrls.add(recordImage.getImageUrl());
                }
                data.put("imageUrls", imageUrls);
                data.put("tcpImageUrls", imageUrls);
            }
        }

        List<String> executorNameList = new ArrayList<>(1);
        executorNameList.add(checkRecords.get(0).getCheckerName());
        task.setTaskExecutorsName(executorNameList);
        List<TaskSpotCheckTool> taskSpotCheckTools = taskSpotCheckToolDao.findTaskSpotCheckTools(checkRecord.getTaskId());
        task.setTaskSpotCheckTools(taskSpotCheckTools);
        data.put("task", task);

        cri.setType(1);
        if (tcp != null) {
            cri.setTaskId(task.getId());
            if (!signBoolean) {
                List<String> tcpImageUrls = checkRecordImageDao.findCheckRecordImageList(cri);
                data.put("tcpImageUrls", tcpImageUrls);
            } else {
                cri.setCheckPointId(tcp.getId());
                List<String> taskCheckPointImageUrls = checkRecordImageDao.findCheckRecordImageList(cri);
                tcp.setImageUrls(taskCheckPointImageUrls);
                data.put("tcpImageUrls", taskCheckPointImageUrls);
            }
        }

        data.put("TaskCheckPoint", tcp);
        data.put("checkRecords", checkRecords);
        return asseData(data);
    }

    /**
     * 提交离线巡检记录
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/20 15:38
     **/
    @Override
    public Data saveOfficeCheckRecord(String json, HttpServletRequest request) {
        List<TaskCheckPoint> list = JSONArray.parseArray(json, TaskCheckPoint.class);
        return saveTaskCheckRecord(list, list.get(0).getTaskId(), null);
    }

    /**
     * 保存巡检记录带任务检测工具，现只有地铁app使用此接口
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/17 13:34
     **/
    @Override
    public Data saveTaskDeviceCheckRecord(String json) {
        SysLog.info(json + "----------------->参数返回");

        Task task = JSONObject.parseObject(json, Task.class);
        List<TaskCheckPoint> list = task.getTaskCheckPoints();

        saveTaskCheckRecord(list, task);
        //saveTaskSpotCheckTools(task);
        if (task.getTaskSpotCheckTools() != null && task.getTaskSpotCheckTools().size() != 0) {
            taskSpotCheckToolDao.delTaskSpotCheckTools(task.getId());
            taskSpotCheckToolDao.saveTaskSpotCheckTools(task.getTaskSpotCheckTools());
        }else {
            taskSpotCheckToolDao.delTaskSpotCheckTools(task.getId());
        }
        return Data.isSuccess();
    }

    /**
     * 保存任务工器具
     *
     * @author zhangZaiFa
     * @date 2020/6/17 16:15
     **/
    private void saveTaskSpotCheckTools(Task task) {
        if (task.getTaskSpotCheckTools() != null && task.getTaskSpotCheckTools().size() != 0) {
            taskSpotCheckToolDao.delTaskSpotCheckTools(task.getId());
            taskSpotCheckToolDao.saveTaskSpotCheckTools(task.getTaskSpotCheckTools());
        }
    }

    /**
     * 保存离线巡检记录带任务检测工具，现只有地铁app使用此接口
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/17 13:34
     **/
    @Override
    public Data saveTaskDeviceOfficeCheckRecord(String json) {
        SysLog.info("离线巡检!!!!!!!!!!!!!json=" + json);
        Task task = JSONObject.parseObject(json, Task.class);

        List<TaskCheckPoint> list = task.getTaskCheckPoints();
        saveTaskCheckRecord(list, list.get(0).getTaskId(), null);
        saveTaskSpotCheckTools(task);
        return Data.isSuccess();
    }

    /**
     * 删除, 批量删除巡查记录
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huangGuiChuan
     * @date 2020-01-16
     **/
    @Override
    public Data deletedCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer deletedCheckRecordNum = checkRecordDao.deletedCheckRecord(checkRecord);
        return asseData(deletedCheckRecordNum);
    }

    /**
     * 查询项目当月故障巡检记录数量
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/3/19 14:43
     **/
    @Override
    public Data findProjectCurrentMonthFaultCheckRecordCount(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer count = checkRecordDao.findProjectCurrentMonthFaultCheckRecordCount(checkRecord);
        return asseData(count);
    }

    /**
     * 大屏--巡检管理
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-07
     **/
    @Override
    public Data findCheckRecordManage(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);
        Integer[] i = {1, 2, 3, 4};
        task.setTaskStatusArr(i);

        if (task.getTypeTask() == null) {
            task.setTypeTask(1);
        }

        if (null == task.getCreateTime()) {
            Date date = new Date();
            Date starDate = DateUtils.monthStarDate(date);
            Date endDate = DateUtils.monthEndDate(date);
            task.setPeriodStartTime(starDate);
            task.setPeriodEndTime(endDate);
        }

        Task taskEntity = taskDao.taskOverview(task);
        if (null == taskEntity) {
            taskEntity = new Task();
        }

        Task taskEntitys = taskDao.checkOverview(task);
        if (null == taskEntitys) {
            taskEntitys = new Task();
        }

        Device taskDevice = taskDao.taskDeviceNum(task);
        if (null == taskDevice) {
            taskDevice = new Device();
        }

        Map<String, Object> map = new HashMap<>(7);
        // 总工单数(任务总数)
        map.put("taskNum", taskEntity.getTaskNum());
        // 已完成
        map.put("completedNum", taskEntity.getCompletedNum());
        // 总巡查点ID数
        map.put("checkedNum", taskEntitys.getCheckedNum());
        // 已检
        map.put("checkedCount", taskEntitys.getCheckedCount());
        // 总设备数
        map.put("deviceCount", taskDevice.getDeviceCount());
        // 故障设备数量
        map.put("faultDeviceCount", taskDevice.getFaultNum());
        // 正常设备数量
        map.put("normalDeviceCount", taskDevice.getNormalNum());
        return asseData(map);
    }

    /**
     * 功能描述: 报表二级页面  如: GB25201.C1二级页面,
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/11/08
     */
    @Override
    public Data findReportItem(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        // 如: "C1"
        String reportNo = checkRecord.getReportNo();
        // 巡查项目: 火灾自动报警系统
        String sysName = checkRecord.getSysName();
        // 巡查内容: 火灾探测器、手动报警按钮、信号输入模块、输出模块外观及运行状态
        String checkItem = checkRecord.getCheckItem();

        Map<String, Object> map = new HashMap<>(4);
        if (checkRecord.getDeviceCheckItemIds() != null) {
            // 旧LK_MT_CHECK_ITEM id 多个  d_device_check_item Id
            Date createTime = checkRecord.getCreateTime();
            Date lastModifyTime = checkRecord.getLastModifyTime();

            if (createTime != null) {
                checkRecord.setCreateTime(DateUtils.startDayDate(createTime));
            }

            if (lastModifyTime != null) {
                // 当天结束时间 23:59:59
                checkRecord.setLastModifyTime(DateUtils.endDayDate(lastModifyTime));
            } else {
                // 每月结束时间
                checkRecord.setLastModifyTime(DateUtils.monthEndDate(new Date()));
            }

            Integer totalCount = checkRecord.getTotalCount();
            Integer pageSize = checkRecord.getPageSize();
            String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
            if (StringUtils.isNotEmpty(checkItemVsType)) {
                checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
            } else {
                checkRecord.setCheckItemVsType(1);
            }
            if (null == totalCount && null != pageSize && 0 != pageSize) {
                totalCount = checkRecordDao.findReportItemTotalCount(checkRecord);
                map.put("totalCount", totalCount);
            }
            List<CheckRecord> checkRecordList = checkRecordDao.findReportItem(checkRecord);
            map.put("checkRecordList", checkRecordList);
        }

        map.put("sysName", sysName);
        map.put("chechItem", checkItem);
        return asseData(map);
    }

    /**
     * 报表查看 excel
     *
     * @param json 参数 参数
     * @return Data
     * @author huanggc
     * @date 2020-05-19
     */
    @Override
    public Data findReportExcelMetro(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Long[] taskIds = checkRecord.getTaskIds();
        Long projectId = checkRecord.getProjectId();

        Task task = new Task();
        task.setIds(taskIds);
        List<Task> taskList = taskDao.findTaskList(task);
        List<Long> a = new ArrayList<>();
        List<Long> b = new ArrayList<>();
        // 任务类型 0巡查任务，1检查任务，2保养任务, 3抽检任务,4日常巡检
        for (Task t : taskList) {
            if (t.getTaskType() != 3) {
                // 巡检
                a.add(t.getId());
            } else {
                // 抽检
                b.add(t.getId());
            }
        }

        Long[] patrol = new Long[a.size()];
        for (int i = 0; i < a.size(); i++) {
            patrol[i] = a.get(i);
        }

        Long[] smoke = new Long[b.size()];
        for (int i = 0; i < b.size(); i++) {
            smoke[i] = b.get(i);
        }

        Map<String, Object> map = new HashMap<>(6);
        TaskSpotCheckTool taskSpotCheckTool = new TaskSpotCheckTool();
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, projectId + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkRecord.setCheckItemVsType(1);
        }

        if (patrol.length > 0) {
            checkRecord.setTaskIds(patrol);
            // 07 巡检
            List<CheckRecord> smokeList = checkRecordDao.findInhaleSmokeSeven(checkRecord);
            map.put("smokeList", smokeList);
            // 11 巡检
            List<CheckRecord> directelEctricList = checkRecordDao.downDirectelEctricTable(checkRecord);
            taskSpotCheckTool.setTaskIds(patrol);
            List<CheckRecord> directelEctricToolList = taskSpotCheckToolDao.findDateCheckRecordList(taskSpotCheckTool);
            map.put("directelEctricToolList", directelEctricToolList);
            map.put("directelEctricList", directelEctricList);
        }

        if (smoke.length > 0) {
            checkRecord.setTaskIds(smoke);
            // 08
            List<CheckRecord> inhaleSmokeList = checkRecordDao.downInhaleSmokeTable(checkRecord);
            map.put("inhaleSmokeList", inhaleSmokeList);
            // 09
            List<CheckRecord> alarmList = checkRecordDao.downAlarmTable(checkRecord);
            taskSpotCheckTool.setTaskIds(smoke);
            List<CheckRecord> alarmToolList = taskSpotCheckToolDao.findDateCheckRecordList(taskSpotCheckTool);
            map.put("alarmToolList", alarmToolList);
            map.put("alarmList", alarmList);
        }
        return asseData(map);
    }

    /**
     * 地铁:微型消防站巡检卡
     *
     * @param json    参数 参数
     * @param request HttpServletRequest
     * @param resp    HttpServletResponse
     * @return Data
     * @author huanggc
     * @date 2020-05-25
     */
    @Override
    public Data findFireControlList(String json, HttpServletRequest request, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer downType = checkRecord.getDownType();
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkRecord.setCheckItemVsType(1);
        }
        List<CheckRecord> checkRecordList = checkRecordDao.findFireControlList(checkRecord);

        if (null != downType) {
            Long[] taskIds = checkRecord.getTaskIds();
            Task task = taskDao.findTaskById(taskIds[0]);
            String taskName = task.getTaskName();

            String fileName = "(" + taskName + ")" + "微型消防站巡检卡.doc";
            String modelName = ConstantsDevice.MINIATURE_FIRE_CONTROL_FILE_NAME;
            Map<String, Object> map = new HashMap<>(1);
            map.put("checkRecordList", checkRecordList);

            downModel(resp, request, fileName, map, taskName, configuration, modelName);
            return asseData(200);
        }
        return asseData(checkRecordList);
    }

    /**
     * 地铁:巡检记录(巡检记录导出word)
     *
     * @param json    参数 参数
     * @param request HttpServletRequest
     * @param resp    HttpServletResponse
     * @return Data
     * @author huanggc
     * @date 2020-05-26
     */
    @Override
    public Data findCheckRecordMetroList(String json, HttpServletRequest request, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        Task task = taskDao.findTaskById(checkRecord.getTaskId());
        Map<String, Object> map = new HashMap<>(4);
        map.put("taskPeriodTypeDesc", task.getTaskPeriodTypeDesc());
        map.put("periodStartTimeDesc", task.getPeriodStartTimeDesc());

        List<CheckRecord> checkRecordList = taskDeviceDao.findListByTaskId(checkRecord);
        map.put("checkRecordList", checkRecordList);

        if (null != checkRecord.getDownType()) {
            String taskName = task.getTaskName();
            String fileName = "(" + taskName + ")巡检记录表.doc";
            String modelName = ConstantsDevice.CHECK_RECORD_WORD_FILE_NAME;

            map.put("taskName", taskName);
            downModel(resp, request, fileName, map, taskName, configuration, modelName);
            return asseData(200);
        }
        return asseData(map);
    }

    /**
     * 11号线吸气式烟雾探测器检修记录表 Q/SZDY-K10149-07-B2
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020-04-29
     */
    @Override
    public void downSmokeCheckRecord(String json, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        String fileUrl = checkRecord.getFileUrl();

        List<CheckRecord> checkRecordList;
        Long[] taskIds = checkRecord.getTaskIds();
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkRecord.setCheckItemVsType(1);
        }
        if (taskIds != null && taskIds.length > 0) {
            checkRecordList = checkRecordDao.findInhaleSmokeSeven(checkRecord);
        } else {
            checkRecordList = new ArrayList<>();
        }

        String[] keyArray = {"rowNum", "deviceQrNo", "pointLocation", "colADesc", "colBDesc", "colCDesc", "colDDesc", "colEDescs", "colFDescs", "colGDesc",
                "colHDescs", "colIDescs", "checkResultDesc", "faultDescription"};

        CheckRecord checkRecordEntity = new CheckRecord();
        checkRecordEntity.setTitleName("QSZDY-K10149-07-B2");

        // 1填表单位  1  10
        String fillingUnit = "";
        // 2地点：       检修人：    互控人：空  2  10
        String checkLocation = "";
        String checkerName = "";

        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Dict dictUnit = DictUtils.getDictByDictAndItemCode("FILLING_UNIT", "FILLING_UNIT");
            if (null != dictUnit) {
                // 填表单位
                fillingUnit = dictUnit.getItemDescription();
            }

            Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
            if (null != dictLocation) {
                // 检修地点
                checkLocation = dictLocation.getItemDescription();
            }
            // 检修人
            checkerName = checkRecordDao.findCheckerName(checkRecord);
        }
        checkRecordEntity.setProjectName("填表单位:" + fillingUnit);
        checkRecordEntity.setPointLocation("地点:" + checkLocation + "    检修人:" + (checkerName == null ? "" : checkerName) + "   互控人:");

        // 3日期 2 11
        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Task task = new Task();
            task.setIds(checkRecord.getTaskIds());
            List<Task> taskList = taskDao.findTaskList(task);
            Date lastModifyTime = null;
            for (Task t : taskList) {
                if (lastModifyTime == null) {
                    lastModifyTime = t.getLastModifyTime();
                }

                if (lastModifyTime.getTime() < t.getLastModifyTime().getTime()) {
                    lastModifyTime = t.getLastModifyTime();
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = simpleDateFormat.format(lastModifyTime);
            checkRecordEntity.setOrgName("日期:" + format);
        }

        String path = ConstantsDevice.SEVEN_TABLE_FILE_PATH;
        String code = ConstantsDevice.SMOKE_PROBE_DICT_ITEM_CODE;
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(checkRecordEntity);
        ExcelUtils.createAndDownloadExcelLoc(resp, checkRecordList, keyArray, path, fileUrl, 5, code, jsonObject, "1:0");
    }

    /**
     * 地铁 QSZDY-K10149-08-B2
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020-05-13
     */
    @Override
    public void downInhaleSmokeTable(String json, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        String fileUrl = checkRecord.getFileUrl();

        List<CheckRecord> checkRecordList;
        Long[] taskIds = checkRecord.getTaskIds();
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkRecord.setCheckItemVsType(1);
        }
        if (taskIds != null && taskIds.length > 0) {
            checkRecordList = checkRecordDao.downInhaleSmokeTable(checkRecord);
        } else {
            checkRecordList = new ArrayList<>();
        }

        String[] keyArray = {"rowNum", "deviceQrNo", "pointLocation", "manageRegion", "checkName", "terminalCode", "address",
                "colADesc", "colBDesc", "colCDesc", "colDDesc", "checkResultDesc", "faultDescriptionDesc"};

        CheckRecord checkRecordEntity = new CheckRecord();
        checkRecordEntity.setTitleName("QSZDY-K10149-08-B2");
        // 1填表单位  1  10
        String fillingUnit = "";
        // 2地点：       检修人：    互控人：空  2  10
        String checkLocation = "";
        String checkerName = "";

        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Dict dictUnit = DictUtils.getDictByDictAndItemCode("FILLING_UNIT", "FILLING_UNIT");
            if (null != dictUnit) {
                // 填表单位
                fillingUnit = dictUnit.getItemDescription();
            }

            Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
            if (null != dictLocation) {
                // 检修地点
                checkLocation = dictLocation.getItemDescription();
            }
            // 检修人
            checkerName = checkRecordDao.findCheckerName(checkRecord);
        }
        checkRecordEntity.setProjectName("填表单位:" + fillingUnit);
        checkRecordEntity.setPointLocation("地点:" + checkLocation + "    检修人:" + (checkerName == null ? "" : checkerName) + "   互控人:");

        // 3日期 2 11
        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Task task = new Task();
            task.setIds(checkRecord.getTaskIds());
            List<Task> taskList = taskDao.findTaskList(task);
            Date lastModifyTime = null;
            for (Task t : taskList) {
                if (lastModifyTime == null) {
                    lastModifyTime = t.getLastModifyTime();
                }

                if (lastModifyTime.getTime() < t.getLastModifyTime().getTime()) {
                    lastModifyTime = t.getLastModifyTime();
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = simpleDateFormat.format(lastModifyTime);
            checkRecordEntity.setOrgName("日期:" + format);
        }
        // 08
        String path = ConstantsDevice.EIGHT_TABLE_FILE_PATH;
        String code = ConstantsDevice.EIGHT_PROBE_DICT_ITEM_CODE;
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(checkRecordEntity);
        //jsonObject.put("title", taskId + "--" + taskName);// 表名
        List<List<CheckRecord>> list = new ArrayList<>(1);
        list.add(checkRecordList);

        List<String[]> keyList = new ArrayList<>(1);
        keyList.add(keyArray);

        // 每个list要从每几行开始写
        Integer[] headIndexArray = {5};

        ExcelUtils.createAndDownloadExcelMerger(resp, list, keyList, path, fileUrl, headIndexArray, code, jsonObject, "1:0", null);
    }

    /**
     * 防灾报警系统报警功能测试记录表 QSZDYK1014909B2
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020-04-30
     */
    @Override
    public void downAlarmTable(String json, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        String fileUrl = checkRecord.getFileUrl();
        Long[] taskIds = checkRecord.getTaskIds();

        List<CheckRecord> checkRecordList;
        if (taskIds != null && taskIds.length > 0) {
            checkRecordList = checkRecordDao.downAlarmTable(checkRecord);
        } else {
            checkRecordList = new ArrayList<>();
        }

        String[] keyArray = {"rowNum", "deviceName", "checkPointName", "pointLocation", "colADesc", "colBDesc", "checkResultDesc", "faultDescription"};

        CheckRecord checkRecordEntity = new CheckRecord();
        checkRecordEntity.setTitleName("QSZDY-K10149-09-B2");
        // 填表单位  1  10
        String fillingUnit = "";
        // 地点：       检修人：       互控人：  2  10
        String checkLocation = "";
        String checkerName = "";

        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Dict dictUnit = DictUtils.getDictByDictAndItemCode("FILLING_UNIT", "FILLING_UNIT");
            if (null != dictUnit) {
                // 填表单位
                fillingUnit = dictUnit.getItemDescription();
            }

            Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
            if (null != dictLocation) {
                // 检修地点
                checkLocation = dictLocation.getItemDescription();
            }
            // 检修人
            checkerName = checkRecordDao.findCheckerName(checkRecord);
        }

        checkRecordEntity.setProjectName("填表单位:" + fillingUnit);
        checkRecordEntity.setPointLocation("地点:" + checkLocation + "    检修人:" + (checkerName == null ? "" : checkerName) + "   互控人:");

        // 3日期 2 11
        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Task task = new Task();
            task.setIds(checkRecord.getTaskIds());
            List<Task> taskList = taskDao.findTaskList(task);
            Date lastModifyTime = null;
            for (Task t : taskList) {
                if (lastModifyTime == null) {
                    lastModifyTime = t.getLastModifyTime();
                }

                if (lastModifyTime.getTime() < t.getLastModifyTime().getTime()) {
                    lastModifyTime = t.getLastModifyTime();
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = simpleDateFormat.format(lastModifyTime);
            checkRecordEntity.setOrgName("日期:" + format);
        }

        String path = ConstantsDevice.NINE_TABLE_FILE_PATH;
        String code = ConstantsDevice.NINE_TABLE_DICT_ITEM_CODE;
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(checkRecordEntity);

        // 第二个list
        List<List<CheckRecord>> list = new ArrayList<>();
        list.add(checkRecordList);
        if (taskIds != null && taskIds.length > 0) {
            TaskSpotCheckTool taskSpotCheckTool = new TaskSpotCheckTool();
            taskSpotCheckTool.setTaskIds(taskIds);
            List<CheckRecord> taskSpotCheckToolList = taskSpotCheckToolDao.findDateCheckRecordList(taskSpotCheckTool);
            list.add(taskSpotCheckToolList);
        }
        // deviceName 计量器材名称, faultDescription 规格型号, reportName 出厂编号
        String[] keyArrays = {"rowNum", "deviceName", "faultDescription", "reportName"};

        List<String[]> keyList = new ArrayList<>(2);
        keyList.add(keyArray);
        keyList.add(keyArrays);

        // 每个list要从每几行开始写
        Integer[] headIndexArray = {4, 4 + checkRecordList.size() + 2};

        // 要合并的例
        List<Integer[]> mergeList = new ArrayList<>(3);
        Integer[] a = {1, 3};
        Integer[] b = {4, 6};
        Integer[] c = {7, 7};
        mergeList.add(a);
        mergeList.add(b);
        mergeList.add(c);
        ExcelUtils.createAndDownloadExcels(resp, list, keyList, path, fileUrl, headIndexArray, code, jsonObject, "1:0", mergeList);
    }

    /**
     * 地铁 QSZDYK1014911B2
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020-05-14
     */
    @Override
    public void downDirectelEctricTable(String json, HttpServletResponse resp) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        String fileUrl = checkRecord.getFileUrl();
        Long[] taskIds = checkRecord.getTaskIds();

        List<CheckRecord> checkRecordList;
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkRecord.setCheckItemVsType(1);
        }

        if (taskIds != null && taskIds.length > 0) {
            checkRecordList = checkRecordDao.downDirectelEctricTable(checkRecord);
        } else {
            checkRecordList = new ArrayList<>();
        }

        String[] keyArray = {"rowNum", "checkPointName", "colADesc", "colBDesc", "colCDesc", "colDDesc", "colEDescs", "colFDescs", "colGDesc",
                "checkResultDesc", "faultDescription"};

        CheckRecord checkRecordEntity = new CheckRecord();
        checkRecordEntity.setTitleName("QSZDY-K10149-11-B2");
        // 填表单位  1  10
        String fillingUnit = "";

        // 地点：       检修人：       互控人：  2  10
        String checkLocation = "";
        String checkerName = "";

        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Dict dictUnit = DictUtils.getDictByDictAndItemCode("FILLING_UNIT", "FILLING_UNIT");
            if (null != dictUnit) {
                // 填表单位
                fillingUnit = dictUnit.getItemDescription();
            }

            Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
            if (null != dictLocation) {
                // 检修地点
                checkLocation = dictLocation.getItemDescription();
            }
            // 检修人
            checkerName = checkRecordDao.findCheckerName(checkRecord);
        }

        // 3日期 2 11
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(checkRecordList)) {
            Task task = new Task();
            task.setIds(checkRecord.getTaskIds());
            List<Task> taskList = taskDao.findTaskList(task);
            Date lastModifyTime = null;

            for (Task t : taskList) {
                if (lastModifyTime == null) {
                    lastModifyTime = t.getLastModifyTime();
                }

                if (lastModifyTime.getTime() < t.getLastModifyTime().getTime()) {
                    lastModifyTime = t.getLastModifyTime();
                }
                sb.append(t.getTaskName()).append("  ");
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = simpleDateFormat.format(lastModifyTime);
            checkRecordEntity.setOrgName("日期:" + format);
        }

        checkRecordEntity.setProjectName("填表单位:" + fillingUnit);
        checkRecordEntity.setCheckName("检修人:" + (checkerName == null ? "" : checkerName));
        checkRecordEntity.setPointLocation("检修地点:" + checkLocation + "    工单号:" + sb.toString());

        String path = ConstantsDevice.ELEVEN_TABLE_FILE_PATH;
        String code = ConstantsDevice.ELEVEN_PROBE_DICT_ITEM_CODE;

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(checkRecordEntity);

        // 第二个list
        List<List<CheckRecord>> list = new ArrayList<>();
        list.add(checkRecordList);
        if (taskIds != null && taskIds.length > 0) {
            TaskSpotCheckTool taskSpotCheckTool = new TaskSpotCheckTool();
            taskSpotCheckTool.setTaskIds(taskIds);
            List<CheckRecord> taskSpotCheckToolList = taskSpotCheckToolDao.findDateCheckRecordList(taskSpotCheckTool);
            list.add(taskSpotCheckToolList);
        }
        // deviceName 计量器材名称, faultDescription 规格型号, reportName 出厂编号
        String[] keyArrays = {"rowNum", "deviceName", "faultDescription", "reportName"};

        List<String[]> keyList = new ArrayList<>(2);
        keyList.add(keyArray);
        keyList.add(keyArrays);

        // 每个list要从每几行开始写
        Integer[] headIndexArray = {7, 7 + checkRecordList.size() + 2};

        // 要合并的例
        List<Integer[]> mergeList = new ArrayList<>(3);
        // 起始行, 结束行
        Integer[] a = {1, 2};
        Integer[] b = {3, 6};
        Integer[] c = {7, 10};
        mergeList.add(a);
        mergeList.add(b);
        mergeList.add(c);
        ExcelUtils.createAndDownloadExcels(resp, list, keyList, path, fileUrl, headIndexArray, code, jsonObject, "1:0", mergeList);
    }

    /**
     * downReportExcelMetro 下载地铁报表
     *
     * @param json 参数 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2020-05-14
     */
    @Override
    public void downReportExcelMetro(String json, HttpServletRequest request, HttpServletResponse resp) {
        // String fileUrl = System.getProperty("user.dir") + ConstantsDevice.REPORT_EXCEL_FILE_URL;// excel 下载报存路径
        // word 下载报存路径
        String fileUrl = System.getProperty("user.dir") + ConstantsDevice.REPORT_WORD_FILE_URL;

        Task task = JSONObject.parseObject(json, Task.class);

        List<Task> taskList = taskDao.findTaskList(task);
        List<Long> a = new ArrayList<>();
        List<Long> b = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getTaskType() != 3) {
                // 巡检
                a.add(t.getId());
            } else {
                // 抽检
                b.add(t.getId());
            }
        }

        Long[] patrol = new Long[a.size()];
        for (int i = 0; i < a.size(); i++) {
            patrol[i] = a.get(i);
        }

        JSONObject object = new JSONObject(2);
        object.put("taskIds", patrol);
        object.put("fileUrl", fileUrl);
        // 07
        downSmokeCheckRecord(object.toString(), resp);
        // 11
        downDirectelEctricTable(object.toString(), resp);

        Long[] smoke = new Long[b.size()];
        for (int i = 0; i < b.size(); i++) {
            smoke[i] = b.get(i);
        }

        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("taskIds", smoke);
        jsonObject.put("fileUrl", fileUrl);
        // 08
        downInhaleSmokeTable(jsonObject.toString(), resp);
        // 09
        downAlarmTable(jsonObject.toString(), resp);
    }

    /**
     * 查询抽查设备的巡检项
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/8 14:54
     **/
    @Override
    public Data findSpotCheckTaskDeviceCheckItem(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Task task = taskDao.findTaskById(checkRecord.getTaskId());
        Device device = taskDeviceDao.findDeviceLocation(checkRecord.getDeviceId());
        checkRecord = deviceCheckRecordInit(device, task, checkRecord);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            device.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            device.setCheckItemVsType(1);
        }
        List<CheckItemRecord> list = taskDeviceDao.findDeviceSpotCheckItem(device);
        checkRecord.setCheckItemTaskList(list);
        boolean tag = false;

        Map<String, JSONArray> map = new HashMap<>(list.size());
        for (CheckItemRecord checkItemTask : list) {
            checkItemTasksMethod(map, checkItemTask);
            if (checkItemTask.getType() == 5) {
                tag = true;
            }
            if (checkItemTask.getType() == 4) {
                checkRecord.setType(1);
            }
        }

        checkRecord.setObject(map.values());
        if (tag) {
            // 该设备带有自动检测功能 保存信息，待物联设备更新状态
            checkRecordDao.deletedAutomaticCheckDevice(checkRecord);
            checkRecordDao.saveAutomaticCheckDevice(checkRecord);
        }

        List<TaskSpotCheckTool> taskSpotCheckTools = taskSpotCheckToolDao.findTaskSpotCheckTools(checkRecord.getTaskId());
        checkRecord.setTaskSpotCheckTools(taskSpotCheckTools);

        JSONObject data = new JSONObject(1);
        data.put("checkRecord", checkRecord);
        return asseData(data);
    }

    /**
     *
     * @param map Map
     * @param checkItemTask 巡查信息, 巡查项
     */
    private void checkItemTasksMethod(Map<String, JSONArray> map, CheckItemRecord checkItemTask) {
        JSONArray checkItemTasks = new JSONArray(2);
        String sortString = checkItemTask.getSort().toString();
        if (map.get(sortString) != null) {
            checkItemTasks = map.get(sortString);
            checkItemTasks.add(checkItemTask);
            map.put(sortString, checkItemTasks);
        } else {
            checkItemTasks.add(checkItemTask);
            map.put(sortString, checkItemTasks);
        }
    }

    /**
     * 设备转巡检记录
     *
     * @return com.xjt.cloud.task.core.entity.check.CheckRecord
     * @author zhangZaiFa
     * @date 2020/5/8 15:43
     **/
    private CheckRecord deviceCheckRecordInit(Device device, Task task, CheckRecord checkRecord) {
        checkRecord.setCheckType(task.getTaskType());

        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        TaskOrganization org = taskOrganizationDao.findUserOrgName(userId, task.getProjectId());
        if (org != null) {
            checkRecord.setOrgName(org.getOrgName());
        }

        checkRecord.setCheckerName(getOrgUserName(userId, task.getProjectId()));
        checkRecord.setCreateUserId(userId);
        checkRecord.setDeviceName(device.getDeviceName());
        checkRecord.setDeviceCount(device.getDeviceCount());
        checkRecord.setDeviceId(device.getDeviceId());
        checkRecord.setDeviceQrNo(device.getDeviceQrNo());
        checkRecord.setCheckPointId(device.getCheckPointId());
        checkRecord.setCheckPointQrNo(device.getPointQrNo());
        checkRecord.setCheckPointName(device.getPointName());
        checkRecord.setBuildingId(device.getBuildingId());
        checkRecord.setBuildingFloorId(device.getBuildingFloorId());
        checkRecord.setPointLocation(device.getPointLocation());
        checkRecord.setDeviceQrNo(device.getQrNo());
        checkRecord.setDeviceImgUrl(device.getImgUrl());
        checkRecord.setManageRegion(device.getManageRegion());
        checkRecord.setImageUrls(new ArrayList<>());
        checkRecord.setFaultDescription("");
        checkRecord.setCheckResult(0);
        checkRecord.setProjectId(task.getProjectId());
        return checkRecord;
    }

    /**
     * 保存抽查巡检记录
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/8 16:46
     **/
    @Override
    public Data saveSpotCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        //巡检项
        List<CheckItemRecord> checkItemRecords = new ArrayList<>();

        //故障设备ID
        List<Long> faultDeviceList = new ArrayList<>();

        //正常设备ID
        List<Long> normalDeviceList = new ArrayList<>();

        //巡检记录图片集合
        List<CheckRecordImage> images = new ArrayList<>();

        //故障的巡查点
        List<TaskCheckPoint> faultTaskCheckPointList = new ArrayList<>();

        //正常巡查点
        List<TaskCheckPoint> normalTaskCheckPointList = new ArrayList<>();
        checkRecord = setEntityUserIdName(SecurityUserHolder.getUserName(), checkRecord.getProjectId(), checkRecord);

        //版本号
        Long versionNo = System.currentTimeMillis();
        checkRecord.setVersionNo(versionNo);

        //保存巡检记录
        checkRecord.setCreateTime(new Date());
        checkRecord.setFaultDeviceCount(checkRecord.getDeviceCount());

        checkRecordDao.saveCheckRecord(checkRecord);
        List<CheckRecordImage> checkRecordImages = checkRecordImageInit(checkRecord.getId(), checkRecord.getTaskId(), checkRecord.getCheckPointId(),
                checkRecord.getImageUrls(), 0);

        images.addAll(checkRecordImages);

        for (CheckItemRecord icr : checkRecord.getCheckItemTaskList()) {
            icr = setEntityUserIdName(SecurityUserHolder.getUserName(), checkRecord.getProjectId(), icr);
            icr = checkItemRecordInit(icr, checkRecord.getDeviceId(), checkRecord.getDeviceName(), checkRecord.getTaskId(), checkRecord.getProjectId(),
                    checkRecord.getId(), checkRecord.getCheckPointId());

            checkItemRecords.add(icr);
        }

        // 保存任务工具
        if (checkRecord.getTaskSpotCheckTools() != null && checkRecord.getTaskSpotCheckTools().size() != 0) {
            taskSpotCheckToolDao.delTaskSpotCheckTools(checkRecord.getTaskId());
            taskSpotCheckToolDao.saveTaskSpotCheckTools(checkRecord.getTaskSpotCheckTools());
        }

        // 保存巡检结果
        if (checkItemRecords.size() != 0) {
            checkItemRecordDao.saveCheckItemRecordList(checkItemRecords);
        }

        // 保存巡检记录图片
        if (images.size() > 0) {
            checkRecordImageDao.saveCheckRecordImageList(images);
        }

        TaskCheckPoint tcp = new TaskCheckPoint();
        tcp.setCheckPointId(checkRecord.getCheckPointId());
        tcp.setTaskId(checkRecord.getTaskId());
        tcp = taskCheckPointDao.findTaskCheckPoint(tcp);

        // 0、未检  1、故障   2、正常
        //修改状态为故障
        if (checkRecord.getCheckResult() == 1) {
            // 正常
            tcp.setTaskCheckPointStatus(2);
            faultTaskCheckPointList.add(tcp);
            faultDeviceList.add(checkRecord.getDeviceId());
        } else {
            tcp.setTaskCheckPointStatus(1);
            normalTaskCheckPointList.add(tcp);
            normalDeviceList.add(checkRecord.getDeviceId());
        }

        // 更新设备巡查点和设备状态
        updateTaskCheckPointAndDeviceStatus(faultTaskCheckPointList, normalTaskCheckPointList, faultDeviceList, normalDeviceList);
        return Data.isSuccess();
    }

    /**
     * 查询抽查设备记录
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/9 14:39
     **/
    @Override
    public Data findSpotCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        checkRecord.setCheckType(3);
        checkRecord = checkRecordDao.findCheckRecord(checkRecord);
        List<TaskSpotCheckTool> taskSpotCheckTools = taskSpotCheckToolDao.findTaskSpotCheckTools(checkRecord.getTaskId());
        checkRecord.setTaskSpotCheckTools(taskSpotCheckTools);

        CheckRecordImage cri = new CheckRecordImage();

        // 查询设备抽查巡检项
        List<CheckItemRecord> list = checkItemRecordDao.findCheckItemRecordByCheckRecordId(checkRecord.getId());
        Map<String, JSONArray> map = new HashMap<>(list.size());
        for (CheckItemRecord checkItemTask : list) {
            checkItemTasksMethod(map, checkItemTask);
            if (checkItemTask.getType() == 4) {
                // 为多端项，现只有地铁吸气试烟雾报警阀
                checkRecord.setType(1);
            }
        }

        checkRecord.setObject(map.values());
        checkRecord.setCheckItemTaskList(list);
        cri.setCheckRecordId(checkRecord.getId());
        cri.setType(0);
        List<String> checkRecordImageUrls = checkRecordImageDao.findCheckRecordImageList(cri);
        checkRecord.setImageUrls(checkRecordImageUrls);

        JSONObject data = new JSONObject(1);
        data.put("checkRecord", checkRecord);
        return asseData(data);
    }

    /**
     * 查询自动检测设备状态
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/11 14:56
     **/
    @Override
    public Data findAutomaticCheckDeviceStatus(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer status = checkRecordDao.findAutomaticCheckDeviceStatus(checkRecord);
        if (status == null) {
            // 无结果
            status = 0;
        }
        return asseData(status);
    }

    /**
     * 修改自动检测设备状态
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/11 17:01
     **/
    @Override
    public Data updAutomaticCheckDeviceStatus(String json) {
        SysLog.info(json + "---------------------->参数信息");
        Device device = JSONObject.parseObject(json, Device.class);
        Integer status = checkRecordDao.updAutomaticCheckDeviceStatus(device.getProjectId(), device.getDeviceQrNo());
        return asseData(status);
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileName    导出的文件名
     * @param map         数据
     * @param projectName 项目名称
     * @param modelName   模板名
     * @author huanggc
     * @date 2019/11/04
     */
    public static void downModel(HttpServletResponse response, HttpServletRequest request, String fileName,
                                 Map<String, Object> map, String projectName, Configuration configuration, String modelName) {

        OutputStream out = null;
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            response.setContentType("multipart/form-data");

            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = response.getOutputStream();
            oWriter = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板 "GB25201B1.xml"
            Template template = configuration.getTemplate(modelName);
            template.setOutputEncoding("UTF-8");

            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            writer.write(result);
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                SysLog.error(e);
            }
        }
    }
}