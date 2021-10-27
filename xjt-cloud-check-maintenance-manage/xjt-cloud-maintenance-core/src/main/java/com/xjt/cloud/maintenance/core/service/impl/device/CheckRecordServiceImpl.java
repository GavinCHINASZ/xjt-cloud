package com.xjt.cloud.maintenance.core.service.impl.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.dao.device.CheckPointDao;
import com.xjt.cloud.maintenance.core.dao.device.CheckRecordDao;
import com.xjt.cloud.maintenance.core.dao.project.PlanManagementDao;
import com.xjt.cloud.maintenance.core.dao.project.ProjectDao;
import com.xjt.cloud.maintenance.core.entity.device.CheckRecord;
import com.xjt.cloud.maintenance.core.entity.device.CheckReport;
import com.xjt.cloud.maintenance.core.entity.project.PlanManagement;
import com.xjt.cloud.maintenance.core.entity.project.PlanManagementReport;
import com.xjt.cloud.maintenance.core.entity.project.Project;
import com.xjt.cloud.maintenance.core.service.service.device.CheckRecordService;
import com.xjt.cloud.maintenance.core.service.service.fault.FaultRepairRecordService;
import com.xjt.cloud.maintenance.core.service.service.project.CheckProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * app检测 巡检记录
 *
 * @author wangzhiwen
 * @date 2020/4/11 09:32
 */
@Service
public class CheckRecordServiceImpl extends AbstractService implements CheckRecordService {
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private CheckPointDao checkPointDao;
    @Autowired
    private CheckProjectService checkProjectService;
    @Autowired
    private PlanManagementDao planManagementDao;
    @Autowired
    private FaultRepairRecordService faultRepairRecordService;
    @Autowired
    protected ProjectDao projectDao;

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findCheckRecordList(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkRecordDao.findCheckRecordListCount(checkRecord);
        }
        List<CheckRecord> list = checkRecordDao.findCheckRecordList(checkRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:新增app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data saveCheckRecord(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<CheckRecord> list = JSONArray.parseArray(jsonObject.getString("list"), CheckRecord.class);
        jsonObject.remove("list");

        if (CollectionUtils.isNotEmpty(list)) {
            return saveCheckRecordList(list);
        }

        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        if (checkRecord.getCheckType() != null && checkRecord.getCheckType() == 1) {
            PlanManagement planManagement = new PlanManagement();
            planManagement.setCheckDate(new Date());
            planManagement.setCustomerId(checkRecord.getCheckPointId());
            List<PlanManagement> planManagementList = planManagementDao.findPlanManagementList(planManagement);
            if (CollectionUtils.isNotEmpty(planManagementList)) {
                checkRecord.setPlanId(planManagementList.get(0).getId());
            }
        }
        int num;
        if (checkRecord.getId() == null) {
            Long userId = getLoginUserId(SecurityUserHolder.getUserName());
            String userName = getOrgUserName(userId, checkRecord.getProjectId());
            checkRecord.setCreateUserId(userId);
            checkRecord.setCreateUserName(userName);
            num = checkRecordDao.saveCheckRecord(checkRecord);
        } else {
            num = checkRecordDao.modifyCheckRecord(checkRecord);
        }

        // 新增故障报修
        saveFaultRepairRecord(checkRecord);

        modifyPlanStatus(checkRecord);
        return asseData(num);
    }

    /**
     * 新增故障报修
     *
     * @author huanggc
     * @date 2021/05/07
     * @param checkRecord CheckRecord
     */
    private void saveFaultRepairRecord(CheckRecord checkRecord) {
        if (checkRecord.getTreatmentMeasures() != null && checkRecord.getTreatmentMeasures() == 2) {
            JSONObject object = new JSONObject(11);
            // 故障描述
            object.put("faultDescription", checkRecord.getCauseFailure());
            object.put("imageUrl", checkRecord.getCheckImgUrls());
            object.put("deviceSysId", checkRecord.getDeviceSysId());
            object.put("deviceSysName",checkRecord.getDeviceSysNameDesc());
            object.put("deviceName",checkRecord.getDeviceTypeNameDesc());
            // 是否上报: 1是, 2否
            object.put("handIn", 2);
            // 工单状态: 2维修中, 4已完成
            object.put("workOrderStatus", 2);
            // 紧急程度: 1一般, 2紧急, 3特急
            object.put("urgentDegree", 1);

            Long checkProjectId = checkRecord.getCheckProjectId();
            Project project = projectDao.findByCheckProjectId(checkProjectId);
            object.put("projectId", project.getId());
            object.put("projectName", project.getProjectName());
            object.put("checkProjectId", checkProjectId);

            faultRepairRecordService.saveFaultRepairRecord(object.toString());
        }
    }

    /**
     * 判断是否检测完成
     *
     * @param checkRecord CheckRecord
     * @author wangzhiwen
     * @date 2021/4/20 20:50
     */
    private void modifyPlanStatus(CheckRecord checkRecord) {
        PlanManagement planManagement = new PlanManagement();
        planManagement.setId(checkRecord.getPlanId());
        planManagement.setFindCount(true);
        List<PlanManagementReport> list = planManagementDao.findPlanDeviceSysReportList(planManagement);
        if (CollectionUtils.isNotEmpty(list)) {
            PlanManagementReport pr = list.get(0);
            if (pr.getTotalCheckNum().equals(pr.getCheckNum())) {
                planManagement.setPlanState(3);
                planManagementDao.updatePlanManagement(planManagement);
            }
        }
    }

    /**
     * 保存巡检记录数组
     *
     * @param list List
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/4/20 17:29
     */
    private Data saveCheckRecordList(List<CheckRecord> list) {
        CheckRecord checkRecord = list.get(0);
        Long checkPointId = checkRecord.getId();
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        String userName = getOrgUserName(userId, checkRecord.getProjectId());
        int num = 0;
        for (CheckRecord cr : list) {
            cr.setCheckPointId(checkPointId);
            cr.setCreateUserId(userId);
            cr.setCreateUserName(userName);
            if (cr.getId() == null) {
                num = checkRecordDao.saveCheckRecord(cr);
            } else {
                num = checkRecordDao.modifyCheckRecord(cr);
            }

            saveFaultRepairRecord(cr);
        }
        modifyPlanStatus(checkRecord);
        return asseData(num);
    }

    /**
     * 功能描述:修改app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data modifyCheckRecord(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        int num = checkRecordDao.modifyCheckRecord(checkRecord);
        return asseData(num);
    }

    /**
     * 检测记录导出
     *
     * @param id id
     * @return JSONObject
     * @author dwt
     * @date 2020-04-11 19:14
     */
    @Override
    public JSONObject findAllCheckRecordByProjectInfoId(Long id) {
        CheckRecord cr = new CheckRecord();
        List<CheckRecord> list = checkRecordDao.findCheckRecordList(cr);
        List<CheckRecord> failList = checkRecordDao.findFailCheckRecordList(cr);
        JSONObject json = new JSONObject(2);
        json.put("allCheckRecord", list);
        json.put("failCheckRecord", failList);
        return json;
    }

    /**
     * 功能描述:查询检测汇总报表
     *
     * @param checkProjectId 项目id
     * @return List<CheckReport>
     * @author wangzhiwen
     * @date 2020/4/11 17:59
     */
    @Override
    public List<CheckReport> findCheckReport(Long checkProjectId) {
        List<CheckReport> list = checkRecordDao.findCheckReport(checkProjectId);
        if (CollectionUtils.isNotEmpty(list)) {
            JSONObject json = new JSONObject();
            json.put("id", checkProjectId);
            int checkResult = 1;
            for (CheckReport checkReport : list) {
                if (checkReport.getDecisionResult() == 2) {
                    checkResult = 2;
                    break;
                }
            }
            json.put("checkResult", checkResult);
            checkProjectService.updCheckProject(json.toJSONString());
        }
        return list;
    }

    /*--------------------------CheckPoint-----------------------------*/

    /**
     * 功能描述:查询app检测列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findCheckPointList(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkPointDao.findCheckPointListCount(checkRecord);
        }
        List<CheckRecord> list = checkPointDao.findCheckPointList(checkRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    @Override
    public Data findCheckPointSysList(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkPointDao.findCheckPointSysListCount(checkRecord);
        }
        List<CheckRecord> list = checkPointDao.findCheckPointSysList(checkRecord);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:修改app检测
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data modifyCheckPoint(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        int num = checkPointDao.modifyCheckPoint(checkRecord);
        return asseData(num);
    }

    /**
     * 保存 CheckPoint
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/21
     */
    @Override
    public Data saveCheckPoint(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);
        checkRecord.setDeviceCode(createNo(checkRecord.getProjectId()));
        Integer num = checkPointDao.saveCheckPoint(checkRecord);
        return asseData(num);
    }
    /*--------------------------CheckPoint end -----------------------------*/

    /**
     * 生成 设备编码(按项目分)
     *
     * @author huanggc
     * @date 2021/04/25
     * @param projectId 项目ID
     * @return String 例:XFSB00001
     */
    private synchronized String createNo(Long projectId) {
        String keyPrefix = "maintenance_XFSB_" + projectId;
        Integer keySuffix = redisUtils.getInteger(keyPrefix);
        if (null == keySuffix) {
            keySuffix = 0;
        }
        keySuffix++;

        redisUtils.set(keyPrefix, keySuffix);

        // 保留六位,不够六位自动补全六位,超过六位的都保留
        String keySuffixStr = String.format("%06d", keySuffix);
        return "XFSB" + keySuffixStr;
    }

}
