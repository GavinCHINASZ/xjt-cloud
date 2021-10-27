package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.*;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.service.service.TaskSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 月度工单统计 TaskSummaryServiceImpl
 *
 * @author huanggc
 * @date 2019-11-25
 * @version 1.0
 */
@Service
public class TaskSummaryServiceImpl extends AbstractService implements TaskSummaryService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private SummaryDetailsDao summaryDetailsDao;
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private TaskCheckPointDao taskCheckPointDao;
    @Autowired
    private UserDao userDao;

    /**
     * 月度工单统计--工单概览   PC任务概览
     *
     * @author huanggc
     * @date 2019-11-22
     * @param json 请求参数
     * @return Data 返回数据
     */
    @Override
    public Data taskOverview(String json) {
        return asseData(findTaskOverview(json));
    }

    /**
     * 查询月度工单
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/26 13:37
     * @return com.xjt.cloud.task.core.entity.task.Task
     */
    private Task findTaskOverview(String json){
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        if (task.getTaskStatusArr() == null){
            // 任务状态数组 0未启动，1待执行，2已过期，3已完成,4:子任务-待审核
            Integer[] i = {1, 2, 3, 4};
            task.setTaskStatusArr(i);
            task.setTaskStatus(null);
        }

        Integer[] n = {0, 1, 2, 4};
        task.setTaskTypeArr(n);
        task.setTaskType(null);

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        /*Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(loginUserId);*/
        task = taskDao.taskOverview(task);
        return task;
    }

    /**
     * 查询app首页月度工单信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectTaskOverviewData(String json){
        Task task = findTaskOverview(json);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex",21);
        if (task != null) {
            jsonObject.put("taskNum", task.getTaskNum());//总任务
            jsonObject.put("completedNum", task.getCompletedNum());//已完成
            jsonObject.put("checkCompletedNumDesc", task.getCheckCompletedNumDesc());//未完成
        }else {
            jsonObject.put("taskNum", 0);//总任务
            jsonObject.put("completedNum", 0);//已完成
            jsonObject.put("checkCompletedNumDesc", 0);//未完成
        }
        return jsonObject;
    }

    /**
     * 月度工单统计--巡查点概览
     *
     * @author huanggc
     * @date 2019-11-25
     * @param json 参数
     * @return Data
     */
    @Override
    public Data checkOverview(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        if (task.getTaskStatusArr() == null){
            // 任务状态数组 0未启动，1待执行，2已过期，3已完成,4:子任务-待审核
            Integer[] i = {1, 2, 3, 4};
            task.setTaskStatusArr(i);
        }

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setLastModifyTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        task = taskDao.checkOverview(task);
        return asseData(task);
    }

    /**
     * 月度工单统计--巡查点概览列表
     *
     * @author huanggc
     * @date 2019-11-25
     * @param json 参数
     * @return Data
     */
    @Override
    public Data checkOverviewList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = taskDao.findTaskCount(task);
        }

        if (null == task.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }

        return asseData(totalCount);
    }

    /**
     * PC月度工单统计--任务概览表  app--工单概览-->查看详情
     *
     * @author huanggc
     * @date 2020-03-16
     * @param json 参数
     * @return Data
     */
    @Override
    public Data taskOverviewTable(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        if (task.getTaskStatusArr() == null){
            // 任务状态数组 0未启动，1待执行，2已过期，3已完成,4:子任务-待审核
            Integer[] i = {1, 2, 3, 4};
            task.setTaskStatusArr(i);
        }

        // 任务类型数组 0巡查任务，1检查任务，2保养任务
        Integer[] n = {0, 1, 2, 3, 4};
        task.setTaskTypeArr(n);

        if (StringUtils.isNotEmpty(task.getAppId())){
            String projects = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, task.getAppId());
            String[] split = projects.split(",");

            Long[] projectIdArr = ConvertUtils.stringToLong(split);
            task.setProjectIds(projectIdArr);
        }

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setLastModifyTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        Map<String, Object> map = new HashMap<>(3);
        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = taskDao.findTaskCount(task);
            TaskStatusReport taskStatusReport = taskDao.findTaskStatusNum(task);
            map.put("totalCount", totalCount);
            map.put("taskStatusReport", taskStatusReport);
        }

        if (null == task.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }
        List<Task> taskList = taskDao.findTaskOverviewTable(task);
        map.put("taskList", taskList);
        return asseData(map);
    }

    /**
     * PC月度工单统计--任务概览表 导出列表
     *
     * @author huanggc
     * @date 2019-11-26
     * @param json 参数
     * @param response HttpServletResponse
     */
    @Override
    public void downTaskTable(String json, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        if (task.getTaskStatusArr() == null){
            // 任务状态数组 0未启动，1待执行，2已过期，3已完成,4:子任务-待审核
            Integer[] i = {1, 2, 3, 4};
            task.setTaskStatusArr(i);
        }

        // 任务类型数组 0巡查任务，1检查任务，2保养任务
        Integer[] n = {0, 1, 2, 4};
        task.setTaskTypeArr(n);

        if (StringUtils.isNotEmpty(task.getAppId())){
            String projects = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, task.getAppId());
            String[] split = projects.split(",");

            Long[] projectIdArr = ConvertUtils.stringToLong(split);
            task.setProjectIds(projectIdArr);
        }

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        if (null == task.getOrderCols()){
            String[] orderCols = {"lastModifyTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }
        List<Task> taskList = taskDao.findTaskOverviewTable(task);
        if (CollectionUtils.isEmpty(taskList)){
            return;
        }

        Long projectId = task.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--任务概览表");

        String[] keys = {"rowNum", "taskName", "checkPointNum", "checkedCount", "checkCompletedDesc", "faultCount",
                "checkFaultNumDesc", "taskOverviewStatusDesc"};

        ExcelUtils.createAndDownloadExcel(response, taskList, keys, ConstantsDevice.TASK_OVERVIEW_MODEL_FILE_PATH, 3, null,
                jsonObject,"1:0");
    }

    /**
     * 月度工单统计--设备系统详情
     *
     * @author huanggc
     * @date 2020-03-16
     * @param json 参数
     * @return Data
     */
    @Override
    public Data deviceDetails(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = taskDao.findDeviceCount(task);
        }

        List<Device> deviceList = taskDao.deviceDetails(task);

        return asseData(totalCount, deviceList);
    }

    /**
     * PC月度工单统计 巡查点概览表
     *
     * @author huanggc
     * @date 2020-03-16
     * @param json 参数
     * @return Data
     */
    @Override
    public Data checkOverviewTable(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = taskCheckPointDao.findByTaskCount(task);
        }

        if (null == task.getOrderCols()){
            String[] orderCols = {"createTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }
        List<Task> taskList = taskDao.checkOverviewTable(task);
        return asseData(totalCount, taskList);
    }

    /**
     * PC月度工单统计--巡查点概览表 导出列表
     *
     * @author huanggc
     * @date 2020-03-17
     * @param json 参数
     * @param response HttpServletResponse
     */
    @Override
    public void downCheckTable(String json, HttpServletResponse response) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));
        task.setCreateTime(null);

        if (null == task.getOrderCols()){
            String[] orderCols = {"createTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }
        List<Task> list = taskDao.checkOverviewTable(task);

        if (CollectionUtils.isEmpty(list)){
            return;
        }

        Long projectId = task.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--巡查点概览表");

        String[] keys = {"rowNum", "checkPointName", "checkPointNum", "checkedNum", "completedNumDesc", "faultNum", "faultNumDesc"};

        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsDevice.CHECK_OVERVIEW_MODEL_FILE_PATH, 3, null, jsonObject,
                "1:0");
    }

    /**
     * PC月任务汇总--巡查点概览图
     *
     * @author huanggc
     * @date 2019-11-26
     * @param json 参数
     * @return Data
     */
    @Override
    public Data checkOverviewChart(String json) {
        SummaryDetails summaryDetails = JSONObject.parseObject(json, SummaryDetails.class);
        // 查询上个月
        if (null == summaryDetails.getPeriodEndTime() || summaryDetails.getDateType() == 0){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            summaryDetails.setPeriodEndTime(cal.getTime());

        }else if (summaryDetails.getDateType() == 1){
            // 上月同期
            Calendar cal = Calendar.getInstance();
            cal.setTime(summaryDetails.getPeriodEndTime());
            cal.add(Calendar.MONTH, -1);
            summaryDetails.setPeriodEndTime(cal.getTime());

        }else if (summaryDetails.getDateType() == 2){
            // 去年同期
            Calendar cal = Calendar.getInstance();
            cal.setTime(summaryDetails.getPeriodEndTime());
            // 年份减一
            cal.add(Calendar.YEAR, -1);
            summaryDetails.setPeriodEndTime(cal.getTime());
        }

        Date periodEndTime = summaryDetails.getPeriodEndTime();
        summaryDetails.setPeriodStartTime(DateUtils.monthStarDate(periodEndTime));
        summaryDetails.setPeriodEndTime(DateUtils.monthEndDate(periodEndTime));

        List<SummaryDetails> summaryDetailsList = summaryDetailsDao.findCheckOverviewChart(summaryDetails);
        return asseData(summaryDetailsList);
    }

    /**
     * 人员情况
     *
     * @author huangGuiChuan
     * @date 2020-03-18
     * @param json 参数
     * @return Data
     */
    @Override
    public Data userOverview(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        Date createTime = checkRecord.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }

        checkRecord.setCreateTime(DateUtils.monthStarDate(createTime));
        checkRecord.setLastModifyTime(DateUtils.monthEndDate(createTime));

        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = checkRecordDao.findUserOverviewTotalCount(checkRecord);
        }

        if (null == checkRecord.getOrderCols()){
            String[] orderCols = {"createTime"};
            checkRecord.setOrderCols(orderCols);
            checkRecord.setOrderDesc(true);
        }
        List<CheckRecord> checkList = checkRecordDao.findUserOverview(checkRecord);
        return asseData(totalCount, checkList);
    }

    /**
     * APP月度工单统计--巡查点概览列表
     *
     * @author huanggc
     * @date 2020-03-18
     * @param json 参数
     * @return Data
     */
    @Override
    public Data findCheckOverviewList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);
        if (null == task.getTypeTask()){
            task.setTypeTask(1);
        }

        Date createTime = task.getCreateTime();
        if (null == createTime){
            createTime = new Date();
        }
        task.setPeriodStartTime(DateUtils.monthStarDate(createTime));
        task.setPeriodEndTime(DateUtils.monthEndDate(createTime));

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = taskCheckPointDao.findCheckOverviewCount(task);
        }

        List<TaskCheckPoint> taskCheckPointList = taskCheckPointDao.findCheckOverviewList(task);

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setExecutorType(0);

        // 查询巡查点信息
        CheckRecord checkRecord;
        List<String> executorNameList;
        for (TaskCheckPoint tcp : taskCheckPointList) {
            tcp.setBuildingName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, tcp.getBuildingId(), "buildingName"));
            tcp.setFloorName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, tcp.getBuildingFloorId(), "floorName"));
            tcp.setOrgName(CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, tcp.getOrgId(), "orgName"));
            // 巡查点图片为空返回默认图片
            if (tcp.getImgUrl() == null) {
                tcp.setImgUrl(ConstantsDevice.CHECK_POINT_IMAGE_URL);
            }

            taskExecutor.setTaskId(tcp.getTaskId());
            if (tcp.getTaskCheckPointStatus() == 0){
                // 执行人
                executorNameList = findExecutorsName(tcp, taskExecutor, 0);
                tcp.setTaskExecutorsName(executorNameList);
            }else {
                checkRecord = new CheckRecord();
                checkRecord.setCheckPointId(tcp.getCheckPointId());
                checkRecord.setTaskId(tcp.getTaskId());
                List<String> checkRecords = checkRecordDao.findCheckerNameList(checkRecord);
                tcp.setTaskExecutorsName(checkRecords);
            }
        }

        return asseData(totalCount, taskCheckPointList);
    }

    /**
     * 项目主页 数量
     *
     * @author huanggc
     * @date 2020-03-19
     * @param json 参数
     * @return Data
     */
    @Override
    public Data findProjectCount(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);
        if (null != task.getIsAll() && task.getIsAll()){
            // 用户名
            String userName = SecurityUserHolder.getUserName();
            Long userId = 0L;
            if (StringUtils.isEmpty(userName)){
                userName = "";
            }else {
                // 用户ID
                userId = getLoginUserId(userName);
            }
            task.setCreateUserId(userId);
        }

        // task = taskDao.findProjectCount(task);
        return asseData(task);
    }

    /**
     * 查询任务人员   executorType 0、执行人  1、审核人
     * 
     * @param taskCheckPoint 任务巡检点
     * @param taskExecutor 执行人, 审核人
     * @param executorType 类型
     * @return java.util.List<java.lang.String>
     * @author zhangZaiFa
     * @date 2019/12/18 13:59
     **/
    private List<String> findExecutorsName(TaskCheckPoint taskCheckPoint, TaskExecutor taskExecutor, int executorType) {
        List<String> executorNameList;
        taskExecutor.setProjectId(taskCheckPoint.getProjectId());
        taskExecutor.setTaskId(taskCheckPoint.getTaskId());
        taskExecutor.setExecutorType(executorType);
        executorNameList = userDao.findUserNameByTaskId(taskExecutor);
        if (executorNameList == null || executorNameList.size() <= 0) {
            if (taskCheckPoint.getTaskParentId() != null && taskCheckPoint.getTaskParentId() != 0) {
                taskExecutor.setTaskId(taskCheckPoint.getTaskParentId());
                executorNameList = userDao.findUserNameByTaskId(taskExecutor);
            }
        }
        return executorNameList;
    }

}
