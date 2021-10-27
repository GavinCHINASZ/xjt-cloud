package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.common.WebSocketSendMsgUtils;
import com.xjt.cloud.task.core.dao.device.DeviceCheckPointDao;
import com.xjt.cloud.task.core.dao.device.TaskDeviceDao;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.*;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.check.CheckItemRecord;
import com.xjt.cloud.task.core.entity.check.CheckItemTask;
import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.device.DeviceCheckPoint;
import com.xjt.cloud.task.core.entity.task.AppTask;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.task.TaskPointLayout;
import com.xjt.cloud.task.core.service.service.TaskSendMessageService;
import com.xjt.cloud.task.core.service.service.TaskService;
import com.xjt.cloud.task.core.service.service.layout.TaskPointLayoutService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TaskImplService 任务管理
 *
 * @author dwt
 * @date 2019-07-26 9:36
 */
@Service
public class TaskServiceImpl extends AbstractService implements TaskService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ExecutorDao executorDao;
    @Autowired
    private DeviceCheckPointDao deviceCheckPointDao;
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskDeviceDao taskDeviceDao;
    @Autowired
    private TaskReviewDao taskReviewDao;
    @Autowired
    private TaskCheckPointDao taskCheckPointDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private TaskSendMessageService taskSendMessageService;
    @Autowired
    private TaskSpotCheckToolDao taskSpotCheckToolDao;
    @Autowired
    private CheckItemRecordDao checkItemRecordDao;
    @Autowired
    private TaskPointLayoutService taskPointLayoutService;

    /**
     *
     * @param json 参数
     * @return Data
     */
    @Override
    public Data transactionSaveTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        if (task.getId() != null && task.getId() != 0) {
            try {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), task, null, task.getProjectId(), 2);
            } catch (Exception e) {
                SysLog.error("transactionSaveTask---->" + e);
            }
        } else {
            try {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_TASK", SecurityUserHolder.getUserName(), task, null, task.getProjectId(), 2);
            } catch (Exception e) {
                SysLog.error("transactionSaveTask---->" + e);
            }
        }
        return ((TaskServiceImpl) AopContext.currentProxy()).saveTask(json);//必须使用代码调用
    }


    /**
     * 保存任务
     *
     * @param json 参数
     * @return id
     * @author dwt
     * @date 2019-07-25 11:33
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    @Override
    public Data saveTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        if (task.getId() != null && task.getId() != 0) {
            Task oldTask = taskDao.findTaskById(task.getId());
            if (oldTask.getParentId() != null && oldTask.getParentId() != 0) {
                transactionModifySonTask(json);
            } else {
                editTask(task, oldTask);
            }
        } else {
            newTaskCreate(task);
        }
        //任务设备布点初使化接口
        TaskPointLayout taskPointLayout = new TaskPointLayout();
        taskPointLayout.setTaskId(task.getParentId() == null ? task.getId() : task.getParentId());
        taskPointLayout.setByCheckPointIds(task.getCheckPointIds());
        taskPointLayoutService.taskPointPositionInit(taskPointLayout);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", 200);
        jsonObject.put("projectId", task.getProjectId());
        jsonObject.put("iotType", ConstantsDevice.TASK);
        WebSocketSendMsgUtils.nettySendMsg(jsonObject);

        return Data.isSuccess();
    }

    /**
     * 创建新任务
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-08-13 14:50
     */
    private void newTaskCreate(Task task) {
        Long[] checkPointIds = task.getCheckPointIds();
        List<User> executorUserList = task.getExecutorUserList();
        List<User> reviewUserList = task.getReviewUserList();
        if (checkPointIds != null && checkPointIds.length > 0 && executorUserList != null && executorUserList.size() > 0) {
            // 0:父任务，1:子任务
            task.setTypeTask(0);
            // 任务状态   0未开始，1执行中，3已过期，4已完成
            Date endDate = task.getPeriodEndTime();
            Date startDate = task.getPeriodStartTime();
            Date checkStartDate = task.getPeriodStartTime();
            if (task.getCheckCount() != null && task.getCheckCount() == 1) {
                checkStartDate = task.getCheckStartTime();
            }

            if (new Date().compareTo(checkStartDate) > 0) {
                // 0:父任务，1:子任务
                task.setTaskStatus(1);
            } else {
                task.setTaskStatus(0);
            }

            String createUserName = SecurityUserHolder.getUserName();
            // 用户ID
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            task.setCreateUserId(loginUserId);
            task.setCreateUserName(createUserName);

            taskTimeSet(task);

            Integer count = taskDao.saveTask(task);
            task.setPeriodStartTime(startDate);
            task.setPeriodEndTime(endDate);
            if (count <= 0) {
                throw new RuntimeException(task.getTaskName() + ":保存失败");
            }
            saveCheckPoint(task);
            Executor executor = new Executor();
            executor.setTaskId(task.getId());
            executor.setProjectId(task.getProjectId());
            saveExecutor(executorUserList, 0, executor);
            saveExecutor(reviewUserList, 1, executor);

            /*if(task.getReview() == 1){
                saveTaskReview(reviewUserList, task);
            }*/
            createSonTask(task, loginUserId, createUserName);
        }
    }

    /**
     * 根据老数据库迁移的父任务生成为老数据库未生成的子任务
     *
     * @return Data
     * @author dwt
     * @date 2020-11-19 16:48
     */
    @Override
    public Data parentTaskCreateTaskSheetTest() {
        return Data.isSuccess();
    }

    /**
     * 编辑任务
     *
     * @param task    任务实体
     * @param oldTask 任务实体
     * @author dwt
     * @date 2019-08-13 14:52
     */
    private void editTask(Task task, Task oldTask) {
        Long[] checkPointIds = task.getCheckPointIds();
        String createUserName = SecurityUserHolder.getUserName();
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUpdateUserName(createUserName);
        task.setUpdateUserId(loginUserId);
        if (oldTask.getPeriodType() != null && oldTask.getPeriodType() == 2) {
            task.setCheckEndTime(task.getPeriodEndTime());
            taskDao.modifyTask(task);
            editExecutor(task);
            if (oldTask.getTaskStatus() == 0) {
                task.setParentId(task.getId());
                task.setId(null);
                taskDao.modifyTask(task);
                //securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), task, oldTask, task.getProjectId(), 2);
            }
        } else {
            Date oldEndDate = oldTask.getPeriodEndTime();
            Date newEndDate = task.getPeriodEndTime();

            //Date endDate = task.getPeriodEndTime();
            //Date startDate = task.getPeriodStartTime();
            //编辑父任务
            if (task.getParentId() == null || task.getParentId() == 0) {
                Integer taskPeriodType = task.getTaskPeriodType();
                Integer checkCount = task.getCheckCount();
                if (taskPeriodType == 8 && checkCount != null && checkCount == 1) {
                    newEndDate = task.getCheckEndTime();
                    oldEndDate = oldTask.getCheckEndTime();
                }
                task.setProjectId(oldTask.getProjectId());
                Task sonTask = new Task();
                //修改未启动任务
                /*
                 *&& ((task.getPeriodType() != null && task.getPeriodType() != oldTask.getPeriodType())
                 *                     || (task.getTaskPeriodType() != null && task.getTaskPeriodType() != oldTask.getTaskPeriodType())
                 *                     || (task.getTaskType() != null && task.getTaskType() != oldTask.getTaskType()))
                 */
                List<Long> sonIds;
                sonTask.setParentId(task.getId());
                if (oldEndDate != null && newEndDate != null && oldEndDate.compareTo(newEndDate) != 0 || oldTask.getTaskStatus() == 0) {
                    sonTask.setTaskStatus(0);
                    sonTask.setCheckCount(0);
                    if (taskPeriodType == 8 && checkCount != null && checkCount == 1 && oldTask.getTaskStatus() != 0) {
                        sonTask.setCheckCount(1);
                        sonTask.setPeriodEndTime(newEndDate);
                    }
                    sonIds = taskDao.findNotStartSonTaskByParentId(task.getId());
                    if (sonIds != null && sonIds.size() > 0) {
                        taskCheckPointDao.deleteCheckPointByTaskId(null, sonIds);
                        executorDao.deleteExecutorByTaskId(null, null, sonIds);
                    }
                    taskDao.deleteTask(sonTask);
                }
                taskDao.modifySonTaskByParentId(task);
                if (oldTask.getTaskStatus() == 0) {
                    editExecutor(task);
                    taskTimeSet(task);
                    taskDao.modifyTask(task);
                    if (checkPointIds != null && checkPointIds.length > 0) {
                        modifyTaskCheckPoint(task);
                    }
                    //securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), task, oldTask, task.getProjectId(), 2);
                    oldTask = taskDao.findTaskById(task.getId());
                    oldTask.setCheckPointIds(checkPointIds);
                    createSonTask(oldTask, loginUserId, createUserName);
                } else {
                    //修改已启动任务
                    Integer[] taskStatusArr = {0, 1, 2, 3, 4};
                    sonTask.setTaskStatus(null);
                    sonTask.setDelete(0);
                    sonTask.setTaskStatusArr(taskStatusArr);
                    List<Task> listSonTask = taskDao.findSonTaskList(sonTask);
                    task.setLastModifyTime(new Date());
                    task.setUpdateUserId(loginUserId);
                    if (listSonTask == null || listSonTask.size() <= 0) {
                        sonTask.setPeriodEndTime(task.getPeriodStartTime());
                    } else {
                        sonTask = listSonTask.get(0);
                    }
                    Date startSonTime = sonTask.getPeriodEndTime();
                    taskTimeSet(task);
                    taskDao.modifyTask(task);
                    editExecutor(task);
                    if (checkPointIds != null && checkPointIds.length > 0) {
                        modifyTaskCheckPoint(task);
                    }
                    oldTask = taskDao.findTaskById(task.getId());
                    oldTask.setCheckPointIds(checkPointIds);

                    if (oldEndDate != null && newEndDate != null && oldEndDate.compareTo(newEndDate) < 0 || (oldTask.getCheckCount() == null || oldTask.getCheckCount() != 1)) {
                        Calendar c = Calendar.getInstance();
                        //周期
                        if (oldTask.getCheckCount() == null || oldTask.getCheckCount() == 0) {
                            if (startSonTime == null || startSonTime.compareTo(newEndDate) >= 0) {
                                return;
                            }
                            c.setTime(startSonTime);
                            c.add(Calendar.DAY_OF_MONTH, 1);
                            oldTask.setPeriodStartTime(c.getTime());
                        } else {
                            //日常
                            c.setTime(task.getCheckStartTime());
                            int hour = c.get(Calendar.HOUR_OF_DAY);
                            int minute = c.get(Calendar.MINUTE);
                            c.setTime(sonTask.getCheckEndTime());
                            c.add(Calendar.DAY_OF_MONTH, 1);
                            c.set(Calendar.HOUR_OF_DAY, hour);
                            c.set(Calendar.MINUTE, minute);
                            c.set(Calendar.SECOND, 0);
                            oldTask.setCheckStartTime(c.getTime());
                            c.setTime(c.getTime());
                            c.set(Calendar.HOUR_OF_DAY, 0);
                            oldTask.setPeriodStartTime(c.getTime());
                            oldTask.setCheckEndTime(task.getCheckEndTime());
                        }
                        oldTask.setExecutorUserList(task.getExecutorUserList());
                        oldTask.setReviewUserList(task.getReviewUserList());
                        oldTask.setCheckPointIds(task.getCheckPointIds());
                        oldTask.setReview(task.getReview());
                        oldTask.setRemindTime(task.getRemindTime());
                        createSonTask(oldTask, loginUserId, createUserName);
                    }
                }

            }
        }
    }

    /**
     * 任务周期时间和巡检时间设置
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-11-08 9:20
     */
    private void taskTimeSet(Task task) {
        if (task.getCheckCount() != null && task.getCheckCount() == 1) {
            task.setPeriodStartTime(task.getCheckStartTime());
            task.setPeriodEndTime(task.getCheckEndTime());
        } else {
            Calendar cdate = Calendar.getInstance();
            cdate.setTime(task.getPeriodEndTime());
            cdate.add(Calendar.DAY_OF_MONTH, 1);
            cdate.add(Calendar.SECOND, -1);
            task.setPeriodEndTime(cdate.getTime());
            task.setCheckEndTime(cdate.getTime());
            task.setCheckStartTime(task.getPeriodStartTime());
        }
    }

    /**
     * 修改任务设备
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-11-05 11:04
     */
    @Override
    public void modifyTaskCheckPoint(Task task) {
        //taskCheckPointDao.deleteCheckPointByTaskId(task.getId());
        Set<Long> pointIds = taskCheckPointDao.findTaskCheckPoints(task.getId());
        Set<Long> set = new HashSet<>(Arrays.asList(task.getCheckPointIds()));
        Set<Long> set1 = new HashSet<>();
        set1.addAll(set);
        Set<Long> pointSet;
        if (set.size() > 0) {
            List<Long> taskIds = taskDao.findNotStartSonTaskByParentId(task.getId());
            set.removeAll(pointIds);
            if (set.size() > 0) {
                task.setCheckPointIds(set.toArray(new Long[]{}));
                saveCheckPoint(task);
            }
            if (taskIds != null && taskIds.size() > 0) {
                for (Long id : taskIds) {
                    pointSet = taskCheckPointDao.findTaskCheckPoints(id);
                    set.addAll(set1);
                    if (pointSet != null && pointSet.size() > 0) {
                        set.removeAll(pointSet);
                    }
                    if (set.size() > 0) {
                        task.setCheckPointIds(set.toArray(new Long[]{}));
                        task.setId(id);
                        saveCheckPoint(task);
                    }
                }
            }

            if (pointIds.size() > 0) {
                pointIds.removeAll(set1);
                if (pointIds.size() > 0) {
                    if (taskIds != null && taskIds.size() > 0) {
                        taskIds.add(task.getId());
                    } else {
                        taskIds = new ArrayList<>();
                        taskIds.add(task.getId());
                    }
                    taskCheckPointDao.deleteCheckPointByTaskIdAndPointId(taskIds, pointIds);
                }
            }
        }
    }

    /**
     * 编辑执行人
     *
     * @param task 任务实体，是否审核0：不需要，1：需要
     * @author dwt
     * @date 2019-08-13 15:45
     */
    private void editExecutor(Task task) {
        /*if(task.getReview() == 0 && review == 1){
            taskReviewDao.deleteByTaskId(task.getId());
        }*/
        List<User> executorUserList = task.getExecutorUserList();
        List<User> reviewUserList = task.getReviewUserList();
        if (CollectionUtils.isNotEmpty(executorUserList) || CollectionUtils.isNotEmpty(reviewUserList)) {
            Executor executor = new Executor();
            executor.setTaskId(task.getId());
            executor.setProjectId(task.getProjectId());

            List<Long> taskIds = taskDao.findNotStartSonTaskByParentId(task.getId());
            if (executorUserList != null && executorUserList.size() > 0) {
                executorDao.deleteExecutorByTaskId(task.getId(), 0, null);
                saveExecutor(executorUserList, 0, executor);
                if (taskIds != null && taskIds.size() > 0) {
                    for (Long id : taskIds) {
                        executorDao.deleteExecutorByTaskId(id, 0, null);
                        executor.setTaskId(id);
                        saveExecutor(executorUserList, 0, executor);
                    }
                }
            }

            if (reviewUserList != null && reviewUserList.size() > 0) {
                executorDao.deleteExecutorByTaskId(task.getId(), 1, null);
                executor.setTaskId(task.getId());
                saveExecutor(reviewUserList, 1, executor);
                if (taskIds != null && taskIds.size() > 0) {
                    for (Long id : taskIds) {
                        executorDao.deleteExecutorByTaskId(id, 1, null);
                        executor.setTaskId(id);
                        saveExecutor(reviewUserList, 1, executor);
                    }
                }
                /*if(task.getReview() == 1){
                    taskReviewDao.deleteByTaskId(task.getId());
                    saveTaskReview(reviewUserList,task);
                }*/
            }
        }
    }

    /**
     * 保存巡更点
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-08-09 14:20
     */
    private void saveCheckPoint(Task task) {
        TaskCheckPoint taskCheckPoint = new TaskCheckPoint();
        taskCheckPoint.setProjectId(task.getProjectId());
        taskCheckPoint.setTaskId(task.getId());

        Long[] checkPointIds = task.getCheckPointIds();
        String pointName;
        Integer count;
        Set<Long> set = new HashSet<>(Arrays.asList(checkPointIds));
        for (Long pointId : set) {
            taskCheckPoint.setId(null);
            taskCheckPoint.setCheckPointId(pointId);
            pointName = deviceCheckPointDao.findCheckPointName(pointId);
            taskCheckPoint.setCheckPointName(pointName);
            count = taskCheckPointDao.saveTaskCheckPoint(taskCheckPoint);
            if (count <= 0) {
                throw new RuntimeException("巡更点保存失败");
            }
        }
    }

    /**
     * 保存执行人/审核人
     *
     * @param list     User
     * @param type     type
     * @param executor 执行者实体
     * @author dwt
     * @date 2019-08-09 14:03
     */
    private void saveExecutor(List<User> list, Integer type, Executor executor) {
        Integer id;
        if (CollectionUtils.isNotEmpty(list)) {
            executor.setExecutorType(type);
            for (User u : list) {
                executor.setId(null);
                executor.setExecutorId(u.getOrgUserId());
                executor.setUserId(u.getUserId());
                //executor.setRoleId(u.getRoleId());
                executor.setExecutorName(u.getUserName());
                id = executorDao.saveExecutor(executor);
                if (id <= 0) {
                    throw new RuntimeException("审核人保存失败");
                }
            }
        }
    }

    /**
     * 根据父任务生成对应子任务
     *
     * @param task           　任务实体
     * @param loginUserId    　UserId
     * @param createUserName 　createUserName
     * @return Integer
     * @author dwt
     * @date 2019-07-31 14:26
     */
    private Integer createSonTask(Task task, Long loginUserId, String createUserName) {
        task.setParentId(task.getId());
        task.setId(null);
        task.setTypeTask(1);
        task.setCreateUserId(loginUserId);
        task.setCreateUserName(createUserName);
        Date startDate = task.getPeriodStartTime();
        Date endDate = task.getPeriodEndTime();

        //任务状态  0未开始，1执行中，2已过期，3已完成,4子任务-待审核
        if (new Date().compareTo(startDate) >= 0) {
            task.setTaskStatus(1);
        } else {
            task.setTaskStatus(0);
        }

        Integer taskType = task.getPeriodType();
        Integer count = 0;
        switch (taskType) {
            case 0:
                count = periodTask(task, startDate, endDate);
                break;
            case 1:
                count = dailyTask(task, startDate, endDate);
                break;
            case 2:
                count = customTask(task);
                break;
        }
        return count;
    }

    /**
     * @param task      任务实体
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return java.lang.Integer
     * <p>
     * 周期任务
     * 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周
     * @author dwt
     * @date 2019-08-01 11:16
     */
    private Integer periodTask(Task task, Date startDate, Date endDate) {
        Integer taskPeriodType = task.getTaskPeriodType();
        Integer count = 0;
        switch (taskPeriodType) {
            case 0://每两年
            case 1://每年
            case 2://每半年
            case 3://每季度
            case 4://每两月
            case 5://每月
            case 6://每半月
            case 7://每周
                count = periodCommonMethod(task, taskPeriodType, startDate, endDate);
                break;
        }
        return count;
    }

    /**
     * 日常任务
     *
     * @param task      任务实体
     * @param startDate 任务开始时间
     * @param endDate   结束时间
     * @return java.lang.Integer
     * @author dwt
     * @date 2019-08-05 14:28
     */
    private Integer dailyTask(Task task, Date startDate, Date endDate) {
        Integer checkCount = task.getCheckCount();
        task.setTypeTask(1);
        // 日常巡检次数0：一日一次，1：一日多次
        if (checkCount == 0) {
            return periodCommonMethod(task, 8, startDate, endDate);
        } else {
            return dailyManyTimes(task, startDate);
        }
    }

    //自定义任务

    /**
     * @param task 任务实体
     * @return java.lang.Integer
     * 保存自定义任务
     * @author dwt
     * @date 2019-08-05 14:32
     */
    private Integer customTask(Task task) {
        int a;
        // 任务状态  0未开始，1执行中，3已过期，4已完成
        task.setCheckEndTime(task.getPeriodEndTime());
        task.setCheckStartTime(task.getCheckStartTime());
        a = taskDao.saveTask(task);
        saveTaskRelevantParam(task);
        if (task.getTaskStatus() == 1) {
            // 新增已启动任务消息推送
            taskSendMessageService.sendTaskMessage(task, 1);
        }
        return a;
    }

    /**
     * @param task      任务实体
     * @param type      周期任务类型
     * @param startDate 任务开始时间
     * @param endDate   结束时间
     * @return java.lang.Integer
     * <p>
     * 以对应周期为时间单位生成子任务
     * 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周 ,8:每日一次
     * @author dwt
     * @date 2019-08-02 14:02
     */
    private Integer periodCommonMethod(Task task, Integer type, Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        Integer count = 0;
        switch (type) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                count = periodTaskCommonMethod(c, endDate, task, type);
                break;
            case 8:
                count = taskCommonMethod(c, endDate, task, 1, 8);
                break;
        }
        return count;
    }

    /**
     * @param c       时间
     * @param endDate 结束时间
     * @param task    任务实体
     * @param type    任务周期类型
     * @return java.lang.Integer
     * <p>
     * 生成子任务公用方法
     * 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周
     * @author dwt
     * @date 2020-03-09 12:06
     */
    private Integer periodTaskCommonMethod(Calendar c, Date endDate, Task task, Integer type) {
        Integer count = 0;
        Date sonEndDate = null;
        Date sonStartDate = c.getTime();

        while (true) {
            if (sonStartDate.compareTo(endDate) > 0) {
                break;
            }
            c.setTime(sonStartDate);
            switch (type) {
                case 0:
                    sonEndDate = getTwoYearsByDate(c);
                    break;
                case 1:
                    sonEndDate = getOneYearByDate(c);
                    break;
                case 2:
                    sonEndDate = getHalfYearByDate(c);
                    break;
                case 3:
                    sonEndDate = getQuarterByDate(c);
                    break;
                case 4:
                    sonEndDate = getTwoMonthsByDate(c);
                    break;
                case 5:
                    sonEndDate = getMonthByDate(c);
                    break;
                case 6:
                    sonEndDate = getHalfMonthByDate(c);
                    break;
                case 7:
                    sonEndDate = getWeekByDate(c);
                    break;
            }

            task.setId(null);
            task.setCheckEndTime(sonEndDate);
            task.setCheckStartTime(sonStartDate);
            task.setPeriodStartTime(sonStartDate);
            task.setPeriodEndTime(sonEndDate);
            //任务状态 0未启动，1待执行，2待审核，3已过期，4已完成
            if (sonStartDate.compareTo(new Date()) <= 0) {
                task.setTaskStatus(1);
            } else {
                task.setTaskStatus(0);
            }
            if (task.getCheckEndTime().compareTo(new Date()) > 0) {
                count = taskDao.saveTask(task);
                saveTaskRelevantParam(task);
                if (task.getTaskStatus() == 1) {
                    //新增已启动任务消息推送
                    taskSendMessageService.sendTaskMessage(task, 1);
                }
            }
            c.setTime(sonEndDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            sonStartDate = c.getTime();
        }
        return count;
    }

    /**
     * @param calendar 时间
     * @return java.util.Date
     * <p>
     * 获取周期为每两年的一个周期的结束时间
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getTwoYearsByDate(Calendar calendar) {
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar.getTime();
    }

    /**
     * @param calendar 时间
     * @return java.util.Date
     * 获取周期为每年的一个周期的结束时间
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getOneYearByDate(Calendar calendar) {
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar.getTime();
    }

    /**
     * @param calendar 时间
     * @return java.util.Date
     * <p>
     * 获取周期为每半年的一个周期的结束时间
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getHalfYearByDate(Calendar calendar) {
        int monthNum = calendar.get(Calendar.MONTH);
        if (monthNum <= 5) {
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DAY_OF_MONTH, 30);
        } else {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
        }
        return calendar.getTime();
    }

    /**
     * @param c 时间
     * @return java.util.Date
     * <p>
     * 获取周期为每季度的一个周期的结束时间
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getQuarterByDate(Calendar c) {
        int monthNum = c.get(Calendar.MONTH);
        if (monthNum <= 2) {
            c.set(Calendar.MONTH, 2);
            c.set(Calendar.DAY_OF_MONTH, 31);
        } else if (monthNum <= 5) {
            c.set(Calendar.MONTH, 5);
            c.set(Calendar.DAY_OF_MONTH, 30);
        } else if (monthNum <= 8) {
            c.set(Calendar.MONTH, 8);
            c.set(Calendar.DAY_OF_MONTH, 30);
        } else if (monthNum <= 11) {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DAY_OF_MONTH, 31);
        }
        return c.getTime();
    }

    /**
     * @param c 时间
     * @return java.util.Date
     * <p>
     * 获取周期为每两月的一个周期的结束时间
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getTwoMonthsByDate(Calendar c) {
        int monthNum = c.get(Calendar.MONTH) + 1;
        if (monthNum == 1 || monthNum == 3 || monthNum == 5 || monthNum == 7 || monthNum == 9 || monthNum == 11) {
            c.add(Calendar.MONTH, 1);
        }/*else{
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        }*/
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取周期为每月的一个周期的结束时间
     *
     * @param c 时间
     * @return java.util.Date
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getMonthByDate(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获取周期为每半月的一个周期的结束时间
     *
     * @param c 时间
     * @return java.util.Date
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getHalfMonthByDate(Calendar c) {
        int dayNum = c.get(Calendar.DAY_OF_MONTH);
        if (dayNum <= 15) {
            c.set(Calendar.DAY_OF_MONTH, 15);
        } else {
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return c.getTime();
    }

    /**
     * 获取周期为每周的一个周期的结束时间
     *
     * @param c 时间
     * @return java.util.Date
     * @author dwt
     * @date 2020-03-09 13:36
     */
    private Date getWeekByDate(Calendar c) {
        int dayWeek = c.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int day = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - day);
        c.add(Calendar.DAY_OF_WEEK, 6);
        return c.getTime();
    }

    /**
     * @param c       时间
     * @param endDate 结束时间
     * @param task    任务实体
     * @param num     数量
     * @param type    类型
     * @return java.lang.Integer
     * <p>
     * 生成子任务公用方法
     * 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
     * @author dwt
     * @date 2019-08-01 12:06
     */
    private Integer taskCommonMethod(Calendar c, Date endDate, Task task, Integer num, Integer type) {
        Integer count = -1;
        //父任务结束时间
        Date date = endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = calendar.getTime();
        Date startDate;
        c = getCalendarDate(c, type, num);
        endDate = c.getTime();

        if (date.compareTo(endDate) <= 0) {
            task.setCheckEndTime(task.getPeriodEndTime());
            task.setCheckStartTime(task.getPeriodStartTime());
            if (date.compareTo(new Date()) > 0) {
                count = taskDao.saveTask(task);
                saveTaskRelevantParam(task);
                if (task.getTaskStatus() == 1) {
                    // 新增已启动任务消息推送
                    taskSendMessageService.sendTaskMessage(task, 1);
                }
            }
            return count;
        }

        if (new Date().compareTo(task.getPeriodStartTime()) > 0) {
            task.setTaskStatus(1);
        } else {
            task.setTaskStatus(0);
        }

        task.setPeriodEndTime(endDate);
        task.setCheckEndTime(endDate);
        task.setCheckStartTime(task.getPeriodStartTime());
        if (task.getCheckEndTime().compareTo(new Date()) > 0) {
            count = taskDao.saveTask(task);
            saveTaskRelevantParam(task);
            if (task.getTaskStatus() == 1) {
                //新增已启动任务消息推送
                taskSendMessageService.sendTaskMessage(task, 1);
            }
        }

        c.setTime(endDate);
        c.add(Calendar.SECOND, 1);
        startDate = c.getTime();
        // 任务状态 0未启动，1待执行，2待审核，3已过期，4已完成
        task.setTaskStatus(0);
        while (true) {
            task.setId(null);
            if (new Date().compareTo(startDate) >= 0) {
                task.setTaskStatus(1);
            } else {
                task.setTaskStatus(0);
            }
            task.setPeriodStartTime(startDate);
            task.setCheckStartTime(startDate);
            c.setTime(startDate);
            c = getCalendarDate(c, type, num);
            endDate = c.getTime();

            if (endDate.compareTo(date) > 0) {
                task.setPeriodEndTime(date);
                task.setCheckEndTime(date);
                if (task.getCheckEndTime().compareTo(new Date()) > 0) {
                    count = taskDao.saveTask(task);
                    saveTaskRelevantParam(task);
                    if (task.getTaskStatus() == 1) {
                        //新增已启动任务消息推送
                        taskSendMessageService.sendTaskMessage(task, 1);
                    }
                }
                return count;
            }

            task.setPeriodEndTime(endDate);
            task.setCheckEndTime(endDate);
            if (task.getCheckEndTime().compareTo(new Date()) > 0) {
                count = taskDao.saveTask(task);
                saveTaskRelevantParam(task);
                if (task.getTaskStatus() == 1) {
                    // 新增已启动任务消息推送
                    taskSendMessageService.sendTaskMessage(task, 1);
                }
            }
            c.setTime(endDate);
            c.add(Calendar.SECOND, 1);
            startDate = c.getTime();
            if (startDate.compareTo(date) >= 0) {
                break;
            }
        }
        return count;
    }

    /**
     * @author dwt
     * @date 2020-03-11 15:53
     * 生成执行中的子任务的相关数据
     */
    private void saveTaskRelevantParam(Task task) {
        saveCheckPoint(task);
        Executor executor = new Executor();
        executor.setTaskId(task.getId());
        executor.setProjectId(task.getProjectId());
        saveExecutor(task.getExecutorUserList(), 0, executor);
        saveExecutor(task.getReviewUserList(), 1, executor);
    }

    /**
     * 获取结束时间
     *
     * @param c    时间
     * @param type 类型
     * @param num  数量
     * @return java.util.Calendar
     * @author dwt
     * @date 2019-08-09 17:11
     */
    private Calendar getCalendarDate(Calendar c, Integer type, Integer num) {
        switch (type) {
            case 0:
            case 1:
                c.add(Calendar.YEAR, num);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                c.add(Calendar.MONTH, num);
                break;
            case 6:
            case 7:
            case 8:
                c.add(Calendar.DAY_OF_MONTH, num);
                break;
        }

        if (type < 6) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }

        c.add(Calendar.SECOND, -1);
        return c;
    }

    /**
     * 生成日常巡检子任务
     *
     * @param task      任务实体
     * @param startDate 任务开始时间
     * @return java.lang.Integer
     * @author dwt
     * @date 2019-08-05 11:55
     */
    private Integer dailyManyTimes(Task task, Date startDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(task.getCheckEndTime());
        /*int a = c.get(Calendar.MINUTE);
        if (a == 0) {
            c.add(Calendar.HOUR_OF_DAY,1);
        }*/
        Date checkEndTime = c.getTime();
        Integer interval = task.getIntervalTime();
        if (interval == null) {
            return 0;
        }
        c.setTime(startDate);
        long l = c.getTimeInMillis();
        Integer e = getMinuteNum(task.getCheckEndTime());
        // 日常开始时间
        startDate = task.getCheckStartTime();
        // 日开始时间
        Date startH = startDate;
        c.setTimeInMillis(l + e * 60 * 1000);
        //每日日常结束时间
        /*if (a == 0) {
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
        }*/
        Date endH = c.getTime();//日结束时间
        //日常结束时间
        //endDate = task.getCheckEndTime();
        c.setTime(startH);
        c.add(Calendar.HOUR_OF_DAY, interval);
        Date end1 = c.getTime();
        Date start1;
        int count = -1;
        if (end1.compareTo(checkEndTime) >= 0) {
            task.setPeriodStartTime(startH);
            task.setPeriodEndTime(endH);
            if (task.getCheckEndTime().compareTo(new Date()) > 0) {
                count = taskDao.saveTask(task);
                saveTaskRelevantParam(task);
                if (task.getTaskStatus() == 1) {
                    //新增已启动任务消息推送
                    taskSendMessageService.sendTaskMessage(task, 1);
                }
            }
            return count;
        }

        task.setPeriodStartTime(startH);
        task.setPeriodEndTime(end1);
        task.setCheckEndTime(end1);
        task.setCheckStartTime(startH);
        task.setTaskStatus(0);

        if (new Date().compareTo(startH) > 0) {
            task.setTaskStatus(1);
        }

        if (task.getCheckEndTime().compareTo(new Date()) > 0) {
            count = taskDao.saveTask(task);
            saveTaskRelevantParam(task);
            if (task.getTaskStatus() == 1) {
                // 新增已启动任务消息推送
                taskSendMessageService.sendTaskMessage(task, 1);
            }
        }

        Date date = new Date();
        start1 = end1;

        while (true) {
            task.setId(null);
            task.setPeriodStartTime(start1);
            task.setCheckStartTime(start1);
            if (date.compareTo(start1) >= 0) {
                task.setTaskStatus(1);
            } else {
                task.setTaskStatus(0);
            }
            c.setTime(start1);
            c.add(Calendar.HOUR_OF_DAY, interval);
            end1 = c.getTime();
            if (end1.compareTo(endH) >= 0) {
                task.setPeriodEndTime(endH);
                task.setCheckEndTime(endH);
                if (task.getCheckStartTime().compareTo(task.getPeriodEndTime()) < 0) {
                    taskDao.saveTask(task);
                    saveTaskRelevantParam(task);
                    if (task.getTaskStatus() == 1) {
                        // 新增已启动任务消息推送
                        taskSendMessageService.sendTaskMessage(task, 1);
                    }
                }
                c.setTime(startH);
                c.add(Calendar.DAY_OF_MONTH, 1);
                startH = c.getTime();
                start1 = startH;
                c.setTime(endH);
                c.add(Calendar.DAY_OF_MONTH, 1);
                endH = c.getTime();
                if (start1.compareTo(checkEndTime) >= 0) {
                    break;
                }
                continue;
            }
            task.setPeriodEndTime(end1);
            task.setCheckEndTime(end1);
            if (task.getCheckEndTime().compareTo(new Date()) > 0) {
                count = taskDao.saveTask(task);
                saveTaskRelevantParam(task);
                if (task.getTaskStatus() == 1) {
                    //新增已启动任务消息推送
                    taskSendMessageService.sendTaskMessage(task, 1);
                }
            }
            start1 = end1;
            if (start1.compareTo(checkEndTime) >= 0) {
                break;
            }
        }
        return count;
    }

    /**
     * 定时任务逻辑处理
     * 任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
     *
     * @param str str
     * @return Data
     * @author dwt
     * @date 2019-08-05 15:53
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    public synchronized Data taskHandle(String str) {
        Map<String, Integer> map = JSONObject.parseObject(str, Map.class);
        Integer type = map.get("type");
        Task task = new Task();
        //任务状态数组 0未启动，1执行中，2已过期，3已完成，4子任务-待审核
        Integer[] taskStatusArr = {0, 1};
        task.setTaskStatusArr(taskStatusArr);

        //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周 taskPeriodType
        if (type <= 7) {
            task.setTaskPeriodType(type);
        } else if (type >= 8) {
            //周期类型数组 0周期任务，1日常任务，2自定义任务
            Integer[] periodTypeArr = {1, 2};
            task.setPeriodTypeArr(periodTypeArr);
        }

        //task.setTypeTask(1);
        task.setDelete(0);
        List<Task> taskList = taskDao.findHandleTaskList(task);
        if (taskList != null && taskList.size() > 0) {
            Date date = new Date();
            Integer taskStatus;
            List<TaskCheckPoint> taskCheckPointList;
            Executor executor = new Executor();
            List<Executor> executorList;
            //TaskReview taskReview = new TaskReview();
            Date periodEndTime;
            Date periodStartTime;
            Long parentId;
            Calendar c = Calendar.getInstance();
            long currentTime = c.getTimeInMillis();
            Long periodRemindTime;
            Long remindTime;
            for (Task t : taskList) { //任务状态  0未开始，1执行中，2已过期，3已完成
                taskStatus = t.getTaskStatus();
                if (taskStatus == 4 || taskStatus == 3 || taskStatus == 2 || taskStatus == 5) {
                    continue;
                }
                parentId = t.getParentId();
                periodEndTime = t.getCheckEndTime();
                periodStartTime = t.getCheckStartTime();
                if (periodEndTime != null) {
                    if (t.getCheckCount() == null || t.getCheckCount() != 1) {
                        c.setTime(periodEndTime);
                        c.set(Calendar.HOUR_OF_DAY, 23);
                        c.set(Calendar.MINUTE, 59);
                        c.set(Calendar.SECOND, 59);
                        periodEndTime = c.getTime();
                    }
                    remindTime = t.getRemindTime();
                    if (t.getTaskStatus() == 1 && remindTime != null && remindTime > 0
                            && (t.getIsRemind() == null || t.getIsRemind() == 0)) {
                        c.setTime(periodEndTime);
                        periodRemindTime = c.getTimeInMillis();
                        remindTime = remindTime * 60 * 1000 + currentTime;
                        if (periodRemindTime <= remindTime) {
                            t.setIsRemind(1);
                            taskDao.modifyTask(t);
                            //任务过期时间提醒消息推送
                            taskSendMessageService.sendTaskMessage(t, 3);
                        }
                    }
                    //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-待审核,5:子任务-已驳回
                    if (date.compareTo(periodEndTime) >= 0) {
                        if (parentId != null && parentId != 0) {
                            t.setTaskStatus(2);
                        } else {
                            t.setTaskStatus(3);
                        }
                        taskDao.modifyTask(t);
                        //任务过期消息推送
                        taskSendMessageService.sendTaskMessage(t, 2);
                        //securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", "系统", t, null, t.getProjectId(), 2);
                    } else if (date.compareTo(periodStartTime) >= 0 && t.getTaskStatus() == 0) {
                        t.setTaskStatus(1);
                        taskDao.modifyTask(t);
                        //已启动任务消息推送
                        taskSendMessageService.sendTaskMessage(t, 2);
                        //securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", "系统", t, null, t.getProjectId(), 2);
                        if (parentId != null && parentId != 0) {
                            List<TaskCheckPoint> checkPointIds = taskCheckPointDao.findCheckPointListByTaskId(t.getId());
                            if (checkPointIds == null || checkPointIds.size() <= 0) {
                                taskCheckPointList = taskCheckPointDao.findCheckPointListByTaskId(parentId);
                                if (taskCheckPointList != null && taskCheckPointList.size() > 0) {
                                    for (TaskCheckPoint checkPoint : taskCheckPointList) {
                                        checkPoint.setId(null);
                                        checkPoint.setTaskId(t.getId());
                                        taskCheckPointDao.saveTaskCheckPoint(checkPoint);
                                    }
                                }
                            }
                            executor.setTaskId(t.getId());
                            executor.setExecutorType(0);
                            List<Executor> executors = executorDao.findExecutorList(executor);
                            if (executors == null || executors.size() <= 0) {
                                executor.setTaskId(parentId);
                                executorList = executorDao.findExecutorList(executor);
                                handleExecutor(executorList, t.getId());
                            }
                            if (t.getReview() == 1) {
                                executor.setExecutorType(1);
                                executor.setTaskId(t.getId());
                                executors = executorDao.findExecutorList(executor);
                                if (executors == null || executors.size() <= 0) {
                                    executor.setTaskId(parentId);
                                    executorList = executorDao.findExecutorList(executor);
                                    handleExecutor(executorList, t.getId());
                                }
                            }
                        }
                    }
                } else {
                    t.setTaskStatus(3);
                    taskDao.modifyTask(t);
                    //securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", "系统", t, null, t.getProjectId(), 2);
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", 200);
            jsonObject.put("iotType", ConstantsDevice.TASK);
            WebSocketSendMsgUtils.nettySendMsg(jsonObject);
        }
        return Data.isSuccess();
    }

    private void handleExecutor(List<Executor> executorList, Long taskId) {
        if (executorList != null) {
            for (Executor e : executorList) {
                e.setTaskId(taskId);
                e.setId(null);
                executorDao.saveExecutor(e);
            }
        }
    }

    /**
     * 根据筛选条件查询符合条件的任务
     *
     * @param json 参数
     * @return 任务列表
     * @author dwt
     * @date 2019-07-25 14:01
     */
    @Override
    public Data findTaskList(String json) {
        Task task = parseJsonToTask(json);

        Date periodEndTime = task.getPeriodEndTime();
        if (periodEndTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(periodEndTime);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            task.setPeriodEndTime(c.getTime());
        }

        task.setDeleted(false);
        task.setDelete(0);
        Integer type = task.getTypeTask();
        if (type == 1) {
            task.setNotStartTask(0);
        }

        Long parentId = task.getParentId();
        String[] orderCols = task.getOrderCols();
        if (parentId == null) {
            if (orderCols == null || orderCols.length <= 0) {
                orderCols = new String[]{"t.create_time"};
                task.setOrderDesc(true);
                task.setOrderCols(orderCols);
            }
        } else {
            if (orderCols == null || orderCols.length <= 0) {
                task.setOrderStr(" FIELD(t.task_status, 1, 0, 3, 2, 4), t.create_time DESC ");
            }
        }

        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        if (parentId != null && parentId > 0) {
            task.setNotStartTask(null);
        } else if (parentId == null && type == 1) {
            task.setUserId(loginUserId);
        }

        // isAll ：true只查所有工单，false我的工单
        Boolean isAll = task.getIsAll();
        if (isAll != null && type == 1 && !isAll) {
            task.setUserId(loginUserId);
        }

        if (task.getStatus() != null && task.getStatus() == -1 && task.getCreateTime() != null) {
            task.setPeriodStartTime(DateUtils.monthStarDate(task.getCreateTime()));
            task.setPeriodEndTime(DateUtils.monthEndDate(task.getCreateTime()));
            task.setStatus(null);
        }

        List<Task> list;
        if (type == 0) {
            list = taskDao.findTaskList(task);
        } else {
            list = taskDao.findAllSonTaskList(task);
        }

        if (CollectionUtils.isNotEmpty(list)) {
            Executor executor = new Executor();
            executor.setProjectId(task.getProjectId());
            List<String> executorNameList;
            TaskExecutor taskExecutor = new TaskExecutor();
            for (Task t : list) {
                if (t.getTaskStatus() != 0 && t.getTaskStatus() != 3) {
                    taskExecutor.setTaskId(t.getId());
                    executorNameList = findExecutorsName(t, taskExecutor, 0);
                    t.setTaskExecutorsName(executorNameList);
                }
            }
        }

        // 查询子任务设备数
        deviceNum(list, type);
        Integer totalCount = taskDao.findTaskCount(task);

        Map<String, Object> map = new HashMap<>(8);
        // 任务状态 0未开始，1执行中，2已过期，3已完成,4待审核5已驳回
        TaskStatusReport report = taskDao.findTaskStatusNum(task);
        if (report != null) {
            map.put("executing", report.getExecuting());
            map.put("completed", report.getCompleted());
            map.put("expired", report.getExpired());
            map.put("taskCount", report.getTaskCount());
            map.put("toBeAudit", report.getToBeAudit());
            map.put("notStarted", report.getNotStarted());
        }
        map.put("taskList", list);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * @Description 查询app首页巡查工单信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectCheckWordOrderData(String json){
        Task task = JSONObject.parseObject(json,Task.class);
        task.setDeleted(false);
        task.setDelete(0);
        Integer type = task.getTypeTask();
        if (type == 1) {
            task.setNotStartTask(0);
        }
        // isAll ：true只查所有工单，false我的工单
        Boolean isAll = task.getIsAll();
        if (isAll != null && type == 1 && !isAll) {
            task.setUserId(getLoginUserId(SecurityUserHolder.getUserName()));
        }
        TaskStatusReport report = taskDao.findTaskStatusNum(task);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex",task.getModuleIndex());
        if (report != null) {
            jsonObject.put("executing", report.getExecuting());
            jsonObject.put("toBeAudit", report.getToBeAudit());
            jsonObject.put("notStarted", report.getNotStarted());
        }else{
            jsonObject.put("executing", 0);
            jsonObject.put("toBeAudit", 0);
            jsonObject.put("notStarted", 0);
        }
        return jsonObject;
    }

    private void spotCheckTask(Task task) {
        SpotCheckTask checkTask = taskDao.findSpotCheckTaskDeviceStatusCount(task.getId());
        if (checkTask != null) {
            task.setFaultNum(checkTask.getFaultNum());
            task.setCheckPointNum(checkTask.getDeviceNum());
            task.setCheckedNum(checkTask.getCheckedNum());
            task.setCompletedNum(checkTask.getCheckedNum());
        }
    }

    // 查询子任务巡查点数据
    private void deviceNum(List<Task> list, Integer type) {
        if (list != null && list.size() > 0) {
            if (type == 1) {
                Integer checkPointNum;
                Integer deviceNum;
                for (Task t : list) {
                    if (t.getCheckPointNum() == null || t.getCheckPointNum() == 0) {
                        checkPointNum = taskCheckPointDao.countTaskCheckPointNum(t.getParentId());
                        t.setCheckPointNum(checkPointNum);
                        deviceNum = taskDeviceDao.findDeviceNumByTaskId(t.getId());
                        t.setDeviceNum(deviceNum);
                    }
                }
            }
        }
    }

    /**
     * @param id   id
     * @param flag boolean
     * @return Data
     * <p>
     * 根据id查询任务对象
     * @author dwt
     * @date 2019-07-25 14:03
     */
    @Override
    public Data findTaskById(Long id, boolean flag) {
        Task task = taskDao.findTaskById(id);
        Long parentId = task.getParentId();
        //String projectName = taskDao.findProjectNameById(task.getProjectId());
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, task.getProjectId());
        String projectName = "";
        if (projectJson != null) {
            projectName = projectJson.getString("projectName");
        }

        Map<String, Object> map = new HashMap<>(8);
        map.put("projectName", projectName);
        Integer checkPointNum = taskCheckPointDao.countTaskCheckPointNum(id);
        if (task.getTaskStatus() == 0 && parentId != null && parentId != 0) {
            checkPointNum = taskCheckPointDao.countTaskCheckPointNum(parentId);
        }
        map.put("checkPointNum", checkPointNum);
        Integer checkedPointNum = taskCheckPointDao.countCheckedPointNum(id);
        /*if(task.getTaskType() == 3){
            SpotCheckTask checkTask = taskDao.findSpotCheckTaskDeviceStatusCount(task.getId());
            if(checkTask != null){
                checkedPointNum = checkTask.getCheckedNum();
            }
        }else{
            checkedPointNum = taskCheckPointDao.countCheckedPointNum(id);
        }*/
        map.put("checkedPointNum", checkedPointNum);
        List<TaskDeviceEntity> deviceList = taskDeviceDao.findDeviceListByTaskId(task.getId());
        if (deviceList == null || deviceList.size() <= 0) {
            deviceList = taskDeviceDao.findDeviceListByTaskId(task.getParentId());
        }
        map.put("deviceList", deviceList);

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskId(id);
        taskExecutor.setExecutorType(0);
        List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
        map.put("executorNameList", executorNameList);
        /*if (parentId != null && parentId != 0 && flag) {
            List<CheckRecord> checkRecordList = checkRecordDao.findCheckRecordByTaskId(task.getId());
            map.put("checkRecordList", checkRecordList);
        }*/

        // 是否需要审核 0：不需要，1需要
        if (task.getReview() == 1) {
            taskExecutor.setExecutorType(1);
            taskExecutor.setTaskId(id);
            List<String> reviewNameList = userDao.findUserNameByTaskId(taskExecutor);
            List<User> list = userDao.findUserByTaskId(taskExecutor);
            task.setReviewUserList(list);
            if (reviewNameList == null || reviewNameList.size() <= 0) {
                taskExecutor.setTaskId(task.getParentId());
                reviewNameList = userDao.findUserNameByTaskId(taskExecutor);
            }
            map.put("reviewNameList", reviewNameList);
        }

        if (flag) {
            List<TaskReview> taskReviewList = taskReviewDao.findTaskReviewByTaskId(task.getId());
            map.put("taskReviewList", taskReviewList);
        }
        map.put("task", task);
        return asseData(map);
    }

    /**
     * 查询任务人员   executorType 0、执行人  1、审核人
     *
     * @param task         任务实体
     * @param taskExecutor TaskExecutor
     * @return executorType executorType
     * @author zhangZaiFa
     * @date 2019/12/18 13:59
     **/
    @Override
    public List<String> findExecutorsName(Task task, TaskExecutor taskExecutor, int executorType) {
        List<String> executorNameList;
        taskExecutor.setProjectId(task.getProjectId());
        taskExecutor.setTaskId(task.getId());
        taskExecutor.setExecutorType(executorType);
        executorNameList = userDao.findUserNameByTaskId(taskExecutor);
        List<User> list = userDao.findUserByTaskId(taskExecutor);
        if (executorType == 0) {
            task.setExecutorUserList(list);
        }

        if (executorNameList == null || executorNameList.size() <= 0) {
            if (task.getParentId() != null && task.getParentId() != 0) {
                taskExecutor.setTaskId(task.getParentId());
                executorNameList = userDao.findUserNameByTaskId(taskExecutor);
            }
        }
        return executorNameList;
    }

    /**
     * 任务管理--导出表格功能
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2019-08-12
     */
    @Override
    public void downTaskList(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        Task task = parseJsonToTask(json);
        task.setDelete(0);
        // 不分页
        task.setPageSize(null);
        Integer type = task.getTypeTask();
        Long parentId = task.getParentId();
        String[] orderCols = task.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"t.create_time"};
            task.setOrderDesc(true);
            task.setOrderCols(orderCols);
        }

        Date periodEndTime = task.getPeriodEndTime();
        if (periodEndTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(periodEndTime);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            task.setPeriodEndTime(c.getTime());
        }

        task.setDeleted(false);
        task.setDelete(0);
        if (type == 1) {
            task.setNotStartTask(0);
        }
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        if (parentId != null && parentId > 0) {
            task.setNotStartTask(null);
        } else if (parentId == null && type == 1) {
            task.setUserId(loginUserId);
        }
        //isAll ：true只查所有工单，false我的工单
        Boolean isAll = task.getIsAll();
        if (isAll != null && type == 1 && !isAll) {
            //task.setCreateUserId(loginUserId);
            task.setUserId(loginUserId);
        }
        List<Task> list;
        if (type == 0) {
            list = taskDao.findTaskList(task);
        } else {
            list = taskDao.findAllSonTaskList(task);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Long projectId = task.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--任务管理列表导出");

        String[] keys = {"rowNum", "taskName", "periodStartTimeDesc", "taskStatusDesc", "taskTypeDesc", "taskPeriodTypeDesc", "taskCompleteDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsDevice.TASK_MODEL_FILE_PATH, 3, null, jsonObject, "1:0");
    }

    /**
     * 任务工单--导出表格功能
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2019-08-12
     */
    @Override
    public void downTaskWork(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        //Task task = JSONObject.parseObject(json, Task.class);
        /*if (null == task.getOrderCols()){
            task.setOrderDesc(true);
            String[] strArr = new String[1];
            strArr[0] = "id";
            task.setOrderCols(strArr);
        }*/

        Task task = parseJsonToTask(json);
        Integer type = task.getTypeTask();
        Long parentId = task.getParentId();
        String[] orderCols = task.getOrderCols();
        if (orderCols == null || orderCols.length <= 0) {
            orderCols = new String[]{"t.create_time"};
            task.setOrderDesc(true);
            task.setOrderCols(orderCols);
        }
        Date periodEndTime = task.getPeriodEndTime();
        if (periodEndTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(periodEndTime);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            task.setPeriodEndTime(c.getTime());
        }
        task.setDeleted(false);
        task.setDelete(0);
        // 不分页
        task.setPageSize(null);
        if (type == 1) {
            task.setNotStartTask(0);
        }
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        if (parentId != null && parentId > 0) {
            task.setNotStartTask(null);
        } else if (parentId == null && type == 1) {
            task.setUserId(loginUserId);
        }

        //isAll ：true只查所有工单，false我的工单
        Boolean isAll = task.getIsAll();
        if (isAll != null && type == 1 && !isAll) {
            //task.setCreateUserId(loginUserId);
            task.setUserId(loginUserId);
        }

        ///List<Task> list = taskDao.findTaskList(task);
        List<Task> list;
        if (type == 0) {
            list = taskDao.findTaskList(task);
        } else {
            list = taskDao.findAllSonTaskList(task);
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Long projectId = jsonObject.getLong("projectId");
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "--任务工单列表导出");

        String[] keys = {"rowNum", "taskName", "periodStartTimeDesc", "checkPointNum", "taskStatusDesc", "taskTypeDesc", "periodTypeDesc", "taskPeriodTypeDesc",
                "faultNumDesc", "completedNumDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsDevice.TASK_WORK_MODEL_FILE_PATH, 3, null, jsonObject, "1:0");
    }

    /**
     * 任务筛选结束时间处理
     *
     * @param json 参数
     * @return 任务实体
     * @author dwt
     * @date 2019-08-14 14:52
     */
    private Task parseJsonToTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        Date endTime = task.getPeriodEndTime();
        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.HOUR_OF_DAY, 1);
            endTime = c.getTime();
            task.setPeriodEndTime(endTime);
        }
        return task;
    }

    /**
     * app端子任务列表查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-08-15 10:47
     */
    @Override
    public Data findSonTaskListApp(String json) {
        AppTask appTask = JSONObject.parseObject(json, AppTask.class);
        List<AppTask> appTaskList = taskDao.findSonTaskListApp(appTask);
        List<String> executorNameList;
        Integer deviceNum;
        Integer checkedNum;
        if (appTaskList != null && appTaskList.size() > 0) {
            TaskExecutor taskExecutor = new TaskExecutor();
            taskExecutor.setProjectId(appTask.getProjectId());
            taskExecutor.setExecutorType(0);
            for (AppTask task : appTaskList) {
                taskExecutor.setTaskId(task.getTaskId());
                executorNameList = userDao.findUserNameByTaskId(taskExecutor);
                task.setExecutors(executorNameList);
                deviceNum = taskDeviceDao.findDeviceNumByTaskId(task.getTaskId());
                task.setDeviceNum(deviceNum);
                checkedNum = checkRecordDao.findCheckedDeviceNumByTaskId(task.getTaskId());
                task.setCheckedNum(checkedNum);
            }
        }
        return asseData(appTaskList);
    }

    /**
     * 定时任务处理事物接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-18 11:29
     */
    @Override
    public synchronized Data transactionTaskHandle(String json) {
        return ((TaskServiceImpl) AopContext.currentProxy()).taskHandle(json);
    }

    /**
     * 修改执行中子任务添加任务
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-11-05 14:01
     */
    @Override
    public Data transactionModifySonTask(String json) {
        //Task task = JSONObject.parseObject(json, Task.class);
        /*try{
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), task, null, task.getProjectId(), 2);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        return ((TaskServiceImpl) AopContext.currentProxy()).modifySonTask(json);
    }

    /**
     * 修改执行中子任务
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-11-05 14:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    public Data modifySonTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        Task oldSonTask = taskDao.findTaskById(task.getId());
        Long parentId = oldSonTask.getParentId();
        if (parentId != null && parentId != 0) {
            Date endTime = task.getPeriodEndTime();
            Date startTime = task.getPeriodStartTime();
            Date oldEndTime = oldSonTask.getPeriodEndTime();
            if (endTime != null && startTime != null && (endTime.compareTo(oldEndTime) != 0 || startTime.compareTo(oldSonTask.getPeriodStartTime()) != 0)) {
                task.setTaskStatus(0);
                if (startTime.compareTo(new Date()) <= 0) {
                    task.setTaskStatus(1);
                }/*else if(endTime.compareTo(new Date()) >= 0){
                    task.setTaskStatus(2);
                }*/
                task.setCheckEndTime(endTime);
                task.setCheckStartTime(startTime);
            }
            Long[] checkPointIds = task.getCheckPointIds();
            if (checkPointIds != null && checkPointIds.length > 0) {
                modifyTaskCheckPoint(task);
            }
            editExecutor(task);
            taskDao.modifyTask(task);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", 200);
            jsonObject.put("projectId", task.getProjectId());
            jsonObject.put("iotType", ConstantsDevice.TASK);
            WebSocketSendMsgUtils.nettySendMsg(jsonObject);

            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 删除任务事物接口
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2019-10-18 11:29
     */
    @Override
    public Data transactionDeleteTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        try {
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_TASK", SecurityUserHolder.getUserName(), task, null, task.getProjectId(), 2);
        } catch (Exception e) {
            SysLog.error("transactionDeleteTask---->" + e);
        }
        return ((TaskServiceImpl) AopContext.currentProxy()).deleteTask(json);
    }

    /**
     * 对任务进行逻辑删除和恢复删除
     *
     * @param json 参数
     * @return Date
     * @author dwt
     * @date 2019-08-16 11:10
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    public Data deleteTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        String createUserName = SecurityUserHolder.getUserName();
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUpdateUserName(createUserName);
        task.setUpdateUserId(loginUserId);
        Long[] ids = task.getIds();
        if (ids != null && ids.length > 0) {
            taskDao.modifyDeletedTask(task);
            taskDao.modifyDeletedSonTask(task);
        }
        return Data.isSuccess();
    }

    /**
     * 计算日期获取分钟数
     *
     * @param date 时间
     * @return java.lang.Integer
     * @author dwt
     * @date 2019-11-05 13:58
     */
    private Integer getMinuteNum(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hourNum = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return hourNum * 60 + minute;
    }

    /**
     * 查询我的任务列表
     *
     * @param json 参数
     * @return Data
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @Override
    public Data findMyTaskList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task = initFindTime(task);

        if (task.getIsAll() != null && !task.getIsAll()){

        }else {
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            task.setUserId(loginUserId);
        }

        if (task.getOrderCols() == null || task.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            task.setOrderCols(orderCols);
        }

        if (task.getPeriodStartTime() != null && task.getPeriodEndTime() != null){
            task.setPeriodStartTime(DateUtils.monthStarDate(task.getPeriodStartTime()));
            task.setPeriodEndTime(DateUtils.monthEndDate(task.getPeriodEndTime()));
        }

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskDao.findMyTaskListCount(task);
        }

        List<Task> tasks = taskDao.findMyTaskList(task);

        task = initTaskStatus(task);
        // 查询所有任务状态的数量
        Integer[] arr = {1, 2, 3, 4};
        task.setTaskStatusArr(arr);
        TaskStatusReport tsr = taskDao.findMyTaskStatusReport(task);

        Map<String, Object> data = new HashMap<>(3);
        data.put("totalCount", totalCount);
        data.put("list", tasks);
        data.put("taskStatusReport", tsr);
        return asseData(data);
    }

    /**
     * 初始化任务状态
     *
     * @param task 任务实体
     * @return com.xjt.cloud.task.core.entity.task.Task
     * @author zhangZaiFa
     * @date 2020/6/1 15:15
     **/
    private Task initTaskStatus(Task task) {
        if (task.getTaskStatusArr().length == 1) {
            if (task.getTaskStatusArr()[0] == 1 || task.getTaskStatusArr()[0] == 4) {
                Integer[] arr = {1, 4};
                task.setTaskStatusArr(arr);
            } else if (task.getTaskStatusArr()[0] == 2 || task.getTaskStatusArr()[0] == 3) {
                Integer[] arr = {2, 3};
                task.setTaskStatusArr(arr);
            }
        }
        return task;
    }

    /**
     * initFindTime 初始化查询时间
     *
     * @param task 任务实体
     * @return com.xjt.cloud.report.core.entity.report.DutyRecord
     * @author zhangZaiFa
     * @date 2019/11/13 14:10
     **/
    private Task initFindTime(Task task) {
        try {
            if (task.getEndTime() != null) {
                int dataType = 2;
                String timeFormat = "yyyy-MM";
                if ("month".equals(task.getDateType())) {
                    dataType = Calendar.MONTH;
                } else if ("day".equals(task.getDateType())) {
                    dataType = Calendar.DAY_OF_MONTH;
                    timeFormat = "yyyy-MM-dd";
                }
                SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
                //结束时间按类型加1
                Date startTime = sdf.parse(task.getStartTime());
                Date endTime = sdf.parse(task.getEndTime());
                Calendar ca = Calendar.getInstance();
                ca.setTime(endTime);
                ca.add(dataType, 1);
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                //初始化时间
                task.setStartTime(sdf.format(startTime));
                task.setEndTime(sdf.format(ca.getTime()));
            }
        } catch (Exception e) {
            SysLog.error("initFindTime---->" + e);
        }
        return task;
    }

    /**
     * 查询APP任务工单详情
     *
     * @param json 参数
     * @return Data
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @Override
    public Data findTaskLocation(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<TaskCheckPoint> list = taskCheckPointDao.findTaskCheckPointPage(task);

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskCheckPointDao.findTaskCheckPointPageCount(task);
        }

        //查询巡查点信息
       /* for (TaskCheckPoint tcp : list) {
            tcp.setBuildingName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, tcp.getBuildingId(), "buildingName"));
            tcp.setFloorName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, tcp.getBuildingFloorId(), "floorName"));
            tcp.setOrgName(CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, tcp.getOrgId(), "orgName"));
            if (tcp.getImgUrl() == null) {//巡查点图片为空返回默认图片
                tcp.setImgUrl(ConstantsDevice.CHECK_POINT_IMAGE_URL);
            }
        }*/

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskId(task.getId());
        taskExecutor.setExecutorType(0);
        List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
        List<String> reviewNameList = findExecutorsName(task, taskExecutor, 1);
        // 任务详情
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(userId);
        task = taskDao.findTaskLocation(task);
        task.setTaskExecutorsName(executorNameList);
        task.setTaskReviewName(reviewNameList);

        Map<String, Object> data = new HashMap<>(4);

        List<TaskDeviceEntity> deviceList = taskDeviceDao.findTaskDeviceListByPage(task);
        data.put("deviceList", deviceList);
        data.put("totalCount", totalCount);
        data.put("list", list);
        data.put("task", task);
        return asseData(data);
    }

    /**
     * 查询任务工单详情(用于离线)
     *
     * @param json 参数
     * @return 任务列表
     * @author huanggc
     * @date 2021-02-25
     */
    @Override
    public Data findTaskLocations(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<TaskCheckPoint> taskCheckPointList = taskCheckPointDao.findTaskCheckPointPage(task);

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskCheckPointDao.findTaskCheckPointPageCount(task);
        }

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskId(task.getId());
        taskExecutor.setExecutorType(0);
        List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
        List<String> reviewNameList = findExecutorsName(task, taskExecutor, 1);
        // 任务详情
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(userId);
        task = taskDao.findTaskLocation(task);
        task.setTaskExecutorsName(executorNameList);
        task.setTaskReviewName(reviewNameList);

        Map<String, Object> data = new HashMap<>(4);

        // 不分页
        task.setPageSize(0);
        List<TaskDeviceEntity> deviceList = taskDeviceDao.findTaskDeviceListByPage(task);
        data.put("deviceList", deviceList);
        data.put("totalCount", totalCount);
        data.put("taskCheckPointList", taskCheckPointList);
        data.put("task", task);
        return asseData(data);
    }

    /**
     * 下载离线任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/19 13:51
     **/
    @Override
    public Data downloadOfflineTask(String json) {
        Task entity = JSONObject.parseObject(json, Task.class);

        Task task = taskDao.findTaskById(entity.getId());
        // 离线设备查询所有
        task.setPageIndex(null);
        task.setPageSize(null);
        List<TaskCheckPoint> list = taskCheckPointDao.findTaskCheckPointPage(task);
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        String checkName = getOrgUserName(userId, task.getProjectId());
        for (TaskCheckPoint tcp : list) {
            //tcp.setBuildingName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, tcp.getBuildingId(), "buildingName"));
            //tcp.setFloorName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, tcp.getBuildingFloorId(), "floorName"));
            //tcp.setOrgName(CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, tcp.getOrgId(), "orgName"));
            tcp.setTaskId(task.getId());
            List<Device> devices = taskDeviceDao.findCheckPointIdDeviceList(tcp.getCheckPointId());
            // 暂存巡查人名称
            tcp.setCreateUserName(checkName);

            List<CheckRecord> crList = new ArrayList<>(devices.size());
            // 添加设备巡检记录
            for (Device device : devices) {
                //String deviceName = CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, device.getDeviceTypeId(), "deviceSysName");
                //device.setDeviceName(deviceName);
                CheckRecord checkRecord = new CheckRecord(device);
                checkRecord.setCreateUserName(checkName);

                // 巡检项
                checkRecord.setCheckItemTaskList(new ArrayList<>());
                if (device.getDeviceTypeId() != null) {
                    device.setCheckItemVsType(1);
                    List<CheckItemTask> checkItemTasks = checkItemRecordDao.findCheckItemList(device);
                    //CacheUtils.getCacheGroupListByTypeKey(Constants.DEVICE_CHECK_ITEM_CACHE_INIT_CACHE_KEY, device.getDeviceTypeId().toString(), CheckItemTask.class);
                    if (CollectionUtils.isNotEmpty(checkItemTasks)) {
                        // 添加设备巡检结果
                        for (CheckItemTask checkItemTask : checkItemTasks) {
                            // 0巡查任务，1检查任务，2保养任务
                            // 巡检项类型1:巡检 2:测试 3:保养
                            if (task.getTaskType() + 1 == checkItemTask.getCheckAction()) {
                                CheckItemRecord cir = new CheckItemRecord(checkItemTask);
                                checkRecord.getCheckItemTaskList().add(cir);
                            }
                        }
                    }

                    checkRecord.setDeviceTypeId(device.getDeviceTypeId());
                }
                crList.add(checkRecord);
            }
            tcp.setCheckRecords(crList);
        }
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskId(task.getId());
        taskExecutor.setExecutorType(0);
        List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
        List<String> reviewNameList = findExecutorsName(task, taskExecutor, 1);
        task.setTaskExecutorsName(executorNameList);
        task.setTaskReviewName(reviewNameList);
        List<TaskSpotCheckTool> taskSpotCheckTools = taskSpotCheckToolDao.findTaskSpotCheckTools(task.getId());
        task.setTaskSpotCheckTools(taskSpotCheckTools);

        Map<String, Object> data = new HashMap<>(2);
        data.put("task", task);
        data.put("tcpList", list);
        return asseData(data);
    }

    /**
     * 查询我的执行任务列表
     *
     * @param json 参数
     * @return Data
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @Override
    public Data findMyExecuteTaskList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(loginUserId);
        if (task.getOrderCols() == null || task.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            task.setOrderCols(orderCols);
        }

        Integer[] taskStatusArr = {1};
        task.setTaskStatusArr(taskStatusArr);
        List<Task> tasks = taskDao.findMyTaskList(task);

        Integer pageSize = task.getPageSize();
        Integer totalCount = task.getTotalCount();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskDao.findMyTaskListCount(task);
        }

        Map<String, Object> data = new HashMap<>(2);
        data.put("totalCount", totalCount);
        data.put("list", tasks);
        return asseData(data);
    }

    /**
     * 待办任务数查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-03-18 10:19
     */
    @Override
    public Data findCurrLoginProjectTaskNum(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setTypeTask(1);
        TaskStatusReport report = taskDao.findTaskStatusNum(task);
        return asseData(report);
    }

    /**
     * 任务上传巡查点添加设备
     *
     * @param json 参数
     * @param file 文件
     * @return Data（返回设备列表）
     * @author dwt
     * @date 2020-03-24 13:50
     */
    @Override
    public Data uploadCheckPointExcel(String json, MultipartFile file) {
        TaskPointUpload taskPointUpload = JSONObject.parseObject(json, TaskPointUpload.class);
        // 解析表格，设备系统名称＝表格中的设备名称
        String[] keys = {"rowNum", "pointQrNo"};
        // 解析表格
        List<PointUpload> list = ExcelUtils.readyExcel(file, 1, 0, 0, keys, PointUpload.class);
        if (list == null || list.size() <= 0) {
            return asseData(403, null, "请确定文档格式是否正确!");
        }
        taskPointUpload.setPointUploadList(list);
        List<TaskDeviceEntity> entityList = taskDeviceDao.uploadCheckPointExcel(taskPointUpload);
        return asseData(entityList);

    }

    /**
     * 任务管理详情导出
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param json     参数
     * @author dwt
     * @date 2020-03-26 15:26
     */
    @Override
    public void downloadTaskDetail(HttpServletRequest request, HttpServletResponse response, String json) {
        Task tasks = JSONObject.parseObject(json, Task.class);
        List<Task> listJson = tasks.getTaskList();
        List<File> fileList = new ArrayList<>();

        if (listJson == null || listJson.size() == 0) {
            listJson = new ArrayList<>();
            listJson.add(tasks);
        }

        if (listJson.size() > 0) {
            Long projectId = tasks.getProjectId();
            JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId);
            String projectName = "";
            if (projectJson != null) {
                projectName = projectJson.getString("projectName");
            }
            File file;
            Long id;
            Data data;
            Map<String, Object> map;
            Task task;
            Long parentId;
            TaskManageDetail taskManageDetail;
            String strNames;
            List<String> strList;
            String path;
            String code;
            String[] keyStr;
            List<TaskDeviceEntity> deviceList;
            Integer orgType;
            TaskStatusReport report;
            String[] keys1 = {"rowNum", "pointName", "qrNo", "deviceName", "deviceQrNo", "buildingName", "floorName", "location"};
            String[] keys2 = {"rowNum", "pointName", "qrNo", "deviceName", "deviceQrNo", "buildingName", "floorName", "location",
                    "checkerName", "orgName", "createTimeDesc", "deviceStatusDesc"};
            String taskName;
            for (Task taskJson : listJson) {
                id = taskJson.getId();
                data = findTaskById(id, false);
                map = (Map<String, Object>) data.getObject();
                task = (Task) map.get("task");
                taskName = task.getTaskName();
                parentId = task.getParentId();
                taskManageDetail = new TaskManageDetail(task);
                strList = (List<String>) map.get("executorNameList");
                strNames = String.join(",", strList);
                taskManageDetail.setExecutorNames(strNames);
                strList = (List<String>) map.get("reviewNameList");
                if (task.getRemindTime() != null) {
                    taskManageDetail.setRemindTime(DateUtils.getDayHourMin(task.getRemindTime()));
                }
                if (strList != null && strList.size() > 0) {
                    strNames = String.join(",", strList);
                    taskManageDetail.setReviewNames(strNames);
                }
                taskManageDetail.setTaskCompletion(taskManageDetail.getTaskStatus());
                deviceList = (List<TaskDeviceEntity>) map.get("deviceList");
                taskManageDetail.setOrgTypeDesc(task.getOrgTypeDesc());
                int headIndex = 10;
                orgType = task.getOrgType();
                if (parentId == null || parentId == 0) {
                    Task sonTask = new Task();
                    sonTask.setParentId(task.getId());
                    sonTask.setProjectId(projectId);
                    sonTask.setTypeTask(1);
                    //任务状态 0未开始，1执行中，2已过期，3已完成,4待审核5已驳回
                    report = taskDao.findTaskStatusNum(sonTask);
                    taskManageDetail.setTaskCompletion("共" + report.getTaskCount() + "任务，" + "已完成" + report.getCompleted() +
                            "，剩余" + (report.getTaskCount() - report.getCompleted()));
                    if (orgType == null || orgType == 0) {
                        path = ConstantsDevice.TASK_MANAGE_DETAIL_FILE_PATH;
                        code = ConstantsDevice.TASK_MANAGE_DETAIL_DICT_ITEM_CODE;
                    } else {
                        path = ConstantsDevice.METRO_TASK_MANAGE_DETAIL_FILE_PATH;
                        code = ConstantsDevice.METRO_TASK_MANAGE_DETAIL_DICT_ITEM_CODE;
                        headIndex = 11;
                    }
                    keyStr = keys1;
                } else {
                    taskManageDetail.setDeviceNum(deviceList.size());
                    taskManageDetail.setPointNum(taskJson.getCheckPointNum());
                    Integer checkedPointNum = (Integer) map.get("checkedPointNum");
                    taskJson.setCheckedNum(checkedPointNum);
                    taskManageDetail.setCheckedPointNum(checkedPointNum);
                    taskManageDetail.setCompleteRate(taskJson.getCompletedNumDesc());
                    taskManageDetail.setFaultRate(taskJson.getFaultNumDesc());
                    taskManageDetail.setFaultNum(taskJson.getFaultNum());
            /*taskManageDetail.setTaskCompletion("已检巡查点数:"+ checkedPointNum +"，故障数:"
                    + taskJson.getFaultNum() +"，完成率:"+ taskJson.getCompletedNumDesc()
                    + "，故障率:" + taskJson.getFaultNumDesc());*/
                    if (orgType == null || orgType == 0) {
                        path = ConstantsDevice.TASK_SHEET_DETAIL_FILE_PATH;
                        code = ConstantsDevice.TASK_SHEET_DETAIL_DICT_ITEM_CODE;
                    } else {
                        path = ConstantsDevice.METRO_TASK_SHEET_DETAIL_FILE_PATH;
                        code = ConstantsDevice.METRO_TASK_SHEET_DETAIL_DICT_ITEM_CODE;
                        headIndex = 11;
                    }
                    keyStr = keys2;
                }
                taskManageDetail.setTitle(projectName + "-" + taskName);
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(taskManageDetail);
                if (listJson.size() == 1) {
                    ExcelUtils.createAndDownloadExcel(response, deviceList, keyStr, path, headIndex, code, jsonObject, "1:0");
                    return;
                } else {
                    file = ExcelUtils.getExcelFile(deviceList, keyStr, path, headIndex, code, jsonObject, "1:0");
                    fileList.add(file);
                }
            }

            try {
                FileUtils.downLoadFiles(fileList, projectName + "工单详情", request, response);
            } catch (Exception e) {
                SysLog.error("任务工单导出详情-------->" + e);
            } finally {
                for (File f : fileList) {
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        }
    }

    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /***
     * 删除文件夹
     *
     */
    private static void delFolder(String folderPath) {
        try {
            // 删除完里面所有内容
            delAllFile(folderPath);
            java.io.File myFilePath = new java.io.File(folderPath);
            // 删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            SysLog.error("delFolder---->" + e);
        }
    }

    /**
     * findScanQrNoCheckTaskList
     * 查询扫描二维码巡检的任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/23 14:45
     **/
    @Override
    public Data findScanQrNoCheckTaskList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(loginUserId);
        List<Task> list = taskDao.findScanQrNoCheckTaskList(task);
        return asseData(list);
    }

    /**
     * findMySpotCheckTaskList
     * 查询我的抽查任务列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/7 13:54
     **/
    @Override
    public Data findMySpotCheckTaskList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setTaskType(3);
        task = initFindTime(task);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(loginUserId);
        List<Task> tasks = taskDao.findMySpotCheckTaskList(task);
        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        //判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskDao.findMySpotCheckTaskListCount(task);
        }
        task = initTaskStatus(task);
        TaskStatusReport tsr = taskDao.findMyTaskStatusReport(task);

        JSONObject data = new JSONObject(3);
        data.put("totalCount", totalCount);
        data.put("list", tasks);
        data.put("taskStatusReport", tsr);
        return asseData(data);
    }

    /**
     * 查询我的抽查任务详情
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/7 14:09
     **/
    @Override
    public Data findMySpotCheckTaskLocation(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<Device> list = taskDeviceDao.findMySpotCheckTaskDeviceList(task);
        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = taskDeviceDao.findMySpotCheckTaskDeviceListCount(task);
        }

        // 查询巡查点信息
        for (Device device : list) {
            device.setBuildingName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, device.getBuildingId(), "buildingName"));
            device.setFloorName(CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, device.getBuildingFloorId(), "floorName"));
        }

        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.setTaskId(task.getId());
        taskExecutor.setExecutorType(0);
        List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
        List<String> reviewNameList = findExecutorsName(task, taskExecutor, 1);
        //任务详情
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(userId);
        task = taskDao.findMySpotCheckTaskLocation(task);
        task.setTaskExecutorsName(executorNameList);
        task.setTaskReviewName(reviewNameList);

        JSONObject data = new JSONObject(3);
        data.put("totalCount", totalCount);
        data.put("list", list);
        data.put("task", task);
        return asseData(data);

    }

    /**
     * 地铁任务工单设备导入
     *
     * @param json 参数
     * @param file 文件
     * @return Data
     * @author dwt
     * @date 2020-05-08 9:41
     */
    @Override
    public Data uploadTaskSheetDevice(String json, MultipartFile file) {
        Task task = JSONObject.parseObject(json, Task.class);

        // 解析表格，设备系统名称＝表格中的设备名称
        // String[] keys = {"rowNum", "deviceName", "pointQrNo", "pointLocation"};

        // 解析表格
        // List<TaskSheetDevice> list = ExcelUtils.readyExcel(file, 6,0,0, keys, TaskSheetDevice.class);
        String[] keys = {"rowNum", "pointQrNo"};

        // 解析表格
        List<TaskSheetDevice> list = ExcelUtils.readyExcel(file, 1, 0, 0, keys, TaskSheetDevice.class);
        if (list != null && list.size() > 0) {
            TaskPointUpload pointUpload = new TaskPointUpload();
            pointUpload.setProjectId(task.getProjectId());
            pointUpload.setDeviceList(list);
            List<TaskDeviceEntity> entityList;
            Set<Long> setL = taskDeviceDao.findTaskSheetCheckPoint(pointUpload);
            Set<Long> pointIds = taskCheckPointDao.findTaskCheckPoints(task.getId());
            if (setL != null && setL.size() > 0) {
                if (pointIds != null && pointIds.size() > 0) {
                    setL.addAll(pointIds);
                }
                entityList = taskDeviceDao.findTaskSheetDevice(setL, task.getProjectId());
                return asseData(entityList);
            } else {
                return asseData(403, null, "请确定设备管理是否存在导入设备或者完善导入文档!");
            }
        } else {
            return asseData(403, null, "请确定设备管理是否存在导入设备或者完善导入文档!");
        }
    }

    /**
     * 任务设备导入模板下载(0:默认任务设备导入模板下载,1:任务工单设备导入模板下载)
     *
     * @author dwt
     * @date 2020-05-09 11:15
     */
    @Override
    public void downLoadTaskModel(Integer type, HttpServletResponse response) {
        String path;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "设备导入模板");
        if (type == 1) {
            path = ConstantsDevice.TASK_SHEET_MODEL_FILE_PATH;
        } else if (type == 2){
            // 任务管理模板 20210309
            path = ConstantsDevice.TASK_MANAGE_EXCEL_MODEL_FILE_PATH;
            jsonObject.put("title", "请修改表格名称");
        }else {
            // 0:默认任务设备导入模板下载
            path = ConstantsDevice.TASK_UPLOAD_MODEL_FILE_PATH;
        }

        ExcelUtils.DownloadExcelModel(response, path, jsonObject);
    }

    /**
     * findMonthTaskAnalysis
     * 查询分析任务数据
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/13 10:51
     **/
    @Override
    public Data findMonthTaskAnalysis(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task = taskDao.findMonthTaskAnalysis(task);

        JSONObject data = new JSONObject(5);
        data.put("checkPointNum", task.getCheckPointNum() == null ? 0 : task.getCheckPointNum());
        data.put("noCheckedNum", task.getNoCheckedCount() == null ? 0 : task.getNoCheckedCount());
        data.put("checkedNum", task.getCheckedNum() == null ? 0 : task.getCheckedNum());
        BigDecimal noCheckedNum = new BigDecimal(task.getNoCheckedCount() == null ? 0 : task.getNoCheckedCount());
        BigDecimal checkedNum = new BigDecimal(task.getCheckedNum() == null ? 0 : task.getCheckedNum());
        BigDecimal sum = new BigDecimal(task.getCheckPointNum() == null ? 0 : task.getCheckPointNum());
        data.put("checkedRateDesc", CalculateUtil(checkedNum, sum));
        data.put("noCheckedRateDesc", CalculateUtil(noCheckedNum, sum));
        return asseData(data);
    }

    /**
     * 地铁 巡检工单列表(子任务)
     * <p>
     * findTaskMetroScreenList
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-05-13
     **/
    @Override
    public Data findTaskMetroScreenList(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        task.setDelete(0);
        task.setTypeTask(1);

        String queryDate = task.getQueryDate();
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotEmpty(queryDate)) {
            String[] split = queryDate.split("-");
            cal.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 1);
            cal.add(Calendar.MONTH, -1);// 月份
        }
        Date time = cal.getTime();

        Date starDate = DateUtils.monthStarDate(time);
        Date endDate = DateUtils.monthEndDate(time);

        task.setCreateTime(starDate);
        task.setLastModifyTime(endDate);

        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = taskDao.findTaskCount(task);
        }

        if (null == task.getOrderCols()) {
            String[] orderCols = {"createTime"};
            task.setOrderCols(orderCols);
            task.setOrderDesc(true);
        }
        List<Task> taskList = taskDao.findTaskMetroScreenList(task);
        return asseData(totalCount, taskList);
    }

    /**
     * CalculateUtil
     *
     * @param a BigDecimal
     * @param b BigDecimal
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/5/13 11:06
     **/
    private static String CalculateUtil(BigDecimal a, BigDecimal b) {
        String percent =
                b == null ? "0.00%" :
                        b.compareTo(new BigDecimal(0)) == 0 ? "0.00%" :
                                a == null ? "0.00%" :
                                        a.multiply(new BigDecimal(100)).divide(b, 2, BigDecimal.ROUND_HALF_UP) + "%";
        return percent;
    }

    /**
     * findScreenTaskRecordAnalysis
     * 查询大屏任务记录的数据分析表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/19 13:43
     **/
    @Override
    public Data findScreenTaskRecordAnalysis(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        if (StringUtils.isNotEmpty(task.getAppId())) {
            SysLog.debug(task.getAppId() + "----------------------------->appId");
            String project = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, task.getAppId());
            SysLog.info(project + "------------》项目ID");
            if (project != null) {
                String[] projectArr = project.split(",");
                Long[] projectIds = new Long[projectArr.length];
                for (int i = 0; i < projectArr.length; i++) {
                    projectIds[i] = Long.valueOf(projectArr[i]);
                }
                task.setProjectIds(projectIds);
            }
        }
        SysLog.info(JSONObject.toJSONString(task) + "----------->任务实体");

        List<Task> list = taskDao.findScreenTaskRecordAnalysisList(task);
        Integer totalCount = task.getTotalCount();
        Integer pageSize = task.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            //判断是否存在总数，如没有，则查询总记录数
            totalCount = taskDao.findScreenTaskRecordAnalysisCount(task);
        }
        return asseData(totalCount, list);
    }

    /**
     * findMyTaskSubscript
     * 我的任务角标数量
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/1 13:52
     **/
    @Override
    public Data findMyTaskSubscript(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        // 用户ID
        Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
        task.setUserId(loginUserId);

        Map<String, Object> data = new HashMap<>(6);
        data.put("checkTaskSubscript", 0);//巡查
        data.put("detectTaskSubscript", 0);//检测
        data.put("spotCheckTaskSubscript", 0);//抽查
        data.put("maintenanceTaskSubscript", 0);//保养
        data.put("dailyCheckTaskSubscript", 0);//日常巡检
        List<TaskStatusReport> list = taskDao.findMyTaskSubscript(task);
        for (TaskStatusReport tsr : list) {
            //任务类型 0巡查任务，1检查任务，2保养任务, 3抽检任务,4日常巡检
            if (tsr.getTaskType() == 0) {
                data.put("checkTaskSubscript", tsr.getExecuting() + tsr.getToBeAudit());
            } else if (tsr.getTaskType() == 1) {
                data.put("detectTaskSubscript", tsr.getExecuting() + tsr.getToBeAudit());
            } else if (tsr.getTaskType() == 2) {
                data.put("maintenanceTaskSubscript", tsr.getExecuting() + tsr.getToBeAudit());
            } else if (tsr.getTaskType() == 3) {
                data.put("spotCheckTaskSubscript", tsr.getExecuting() + tsr.getToBeAudit());
            } else if (tsr.getTaskType() == 4) {
                data.put("dailyCheckTaskSubscript", tsr.getExecuting() + tsr.getToBeAudit());
            }
        }
        return asseData(data);
    }

    /**
     * PC 分页查询任务详情设备列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-29 9:53
     */
    @Override
    public Data findTaskSheetDeviceListByTaskId(String json) {
        Task t = JSONObject.parseObject(json, Task.class);
        Map<String, Object> map = new HashMap<>(9);
        Boolean flag = t.getIsAll();
        if (flag) {
            Long id = t.getId();
            Task task = taskDao.findTaskById(id);
            Long parentId = task.getParentId();
            //String projectName = taskDao.findProjectNameById(task.getProjectId());
            JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, task.getProjectId());
            String projectName = "";
            if (projectJson != null) {
                projectName = projectJson.getString("projectName");
            }
            map.put("projectName", projectName);
            map.put("task", task);
            Integer checkPointNum = taskCheckPointDao.countTaskCheckPointNum(id);
            if (task.getTaskStatus() == 0 && parentId != null && parentId != 0) {
                checkPointNum = taskCheckPointDao.countTaskCheckPointNum(parentId);
            }
            map.put("checkPointNum", checkPointNum);
            Integer checkedPointNum = taskCheckPointDao.countCheckedPointNum(id);
            map.put("checkedPointNum", checkedPointNum);
            TaskExecutor taskExecutor = new TaskExecutor();
            taskExecutor.setTaskId(id);
            taskExecutor.setExecutorType(0);
            List<String> executorNameList = findExecutorsName(task, taskExecutor, 0);
            map.put("executorNameList", executorNameList);
            /*if (parentId != null && parentId != 0) {
                List<CheckRecord> checkRecordList = checkRecordDao.findCheckRecordByTaskId(task.getId());
                map.put("checkRecordList", checkRecordList);
            }*/
            //是否需要审核 0：不需要，1需要
            if (task.getReview() == 1) {
                taskExecutor.setExecutorType(1);
                taskExecutor.setTaskId(id);
                List<String> reviewNameList = userDao.findUserNameByTaskId(taskExecutor);
                if (reviewNameList == null || reviewNameList.size() <= 0) {
                    taskExecutor.setTaskId(task.getParentId());
                    reviewNameList = userDao.findUserNameByTaskId(taskExecutor);
                }
                map.put("reviewNameList", reviewNameList);
                List<TaskReview> taskReviewList = taskReviewDao.findTaskReviewByTaskId(task.getId());
                map.put("taskReviewList", taskReviewList);
            }

            Integer totalCount = taskDeviceDao.findCheckDeviceCountByTaskId(id);
            map.put("deviceTotalCount", totalCount);
        }

        List<TaskDeviceEntity> deviceList = taskDeviceDao.findTaskDeviceListByPage(t);
        map.put("deviceList", deviceList);
        return asseData(map);
    }

    /**
     * 任务添加设备查询巡查点id列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-20 11:27
     */
    @Override
    public Data findCheckPointLongList(String json) {
        DeviceCheckPoint checkPoint = JSONObject.parseObject(json, DeviceCheckPoint.class);
        List<Long> longs = deviceCheckPointDao.findCheckPointLongList(checkPoint);
        return asseData(longs);
    }
}
