package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.task.core.dao.device.TaskDeviceDao;
import com.xjt.cloud.task.core.dao.task.ExecutorDao;
import com.xjt.cloud.task.core.dao.task.TaskDao;
import com.xjt.cloud.task.core.dao.task.TaskReviewDao;
import com.xjt.cloud.task.core.dao.task.TaskReviewDeviceDao;
import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.service.service.TaskReviewService;
import com.xjt.cloud.task.core.service.service.TaskSendMessageService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * @ClassName TaskReviewServiceImpl
 * @Author dwt
 * @Date 2019-07-26 10:49
 * 任务审核Service
 * @Version 1.0
 */
@Service
public class TaskReviewServiceImpl extends AbstractService implements TaskReviewService {

    @Autowired
    private TaskReviewDao taskReviewDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private ExecutorDao executorDao;
    @Autowired
    private TaskDeviceDao taskDeviceDao;
    @Autowired
    private TaskReviewDeviceDao taskReviewDeviceDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private TaskSendMessageService taskSendMessageService;

    /**
     * 查询任务审核列表
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-07-26 10:50
     */
    @Override
    public Data findTaskReviewList(String json) {
        TaskReview taskReview = JSONObject.parseObject(json, TaskReview.class);
        List<TaskReview> list = taskReviewDao.findTaskReviewList(taskReview);
        return asseData(list);
    }

    /**
     * @param json 参数
     * @return Data
     * 保存任务审核添加事物
     * @author dwt
     * @date 2019-11-05 13:38
     */
    @Override
    public Data transactionSaveTaskReview(String json) {
        return ((TaskReviewService) AopContext.currentProxy()).saveTaskReview(json);
    }

    /**
     * 根据审核记录id查询审核设备列表
     *
     * @param id id
     * @return Data
     * @author dwt
     * @date 2019-11-22 16:27
     */
    @Override
    public Data findTaskReviewDeviceList(Long id) {
        List<TaskReviewDevice> list = taskReviewDeviceDao.findTaskReviewDeviceList(id);
        return asseData(list);
    }

    /**
     * 保存任务审核对象
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-07-26 10:53
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "dataSourceTransactionManager")
    public Data saveTaskReview(String json) {
        TaskReview taskReview = JSONObject.parseObject(json, TaskReview.class);
        Long[] ids = taskReview.getTaskIds();
        if (ids != null && ids.length > 0) {
            Integer[] taskStatusArr = {0, 1};
            // 1:提交，2,：驳回，3：通过
            Integer reviewStatus = taskReview.getReviewStatus();
            String[] taskTimeArr = taskReview.getTaskTimeArr();
            String[] taskExecutorsArr = taskReview.getTaskExecutorsArr();
            //Long parentId = taskReview.getTaskParentId();
            Task oldSonTask;
            Long projectId = taskReview.getProjectId();
            Long loginUserId = getLoginUserId(SecurityUserHolder.getUserName());
            String createUserName = SecurityUserHolder.getUserName();

            Task task = new Task();
            List<User> listUser = taskReview.getExecutorUserList();
            Executor executor = new Executor();
            Long id;
            Integer isReview;
           /* Integer taskCount;
            Task parent = new Task();*/
            Task sonTask = new Task();
            sonTask.setTypeTask(1);
            sonTask.setDelete(0);
            sonTask.setTaskStatusArr(taskStatusArr);
            Task sonLogTask;
            for (int i = 0; i < ids.length; i++) {
                //1：完成，2，驳回，3：已过期
                id = ids[i];
                oldSonTask = taskDao.findTaskById(id);
                if (oldSonTask == null) {
                    continue;
                }
                sonLogTask = oldSonTask;
                sonTask.setParentId(oldSonTask.getParentId());
                sonTask.setProjectId(projectId);
                sonTask.setId(id);
                sonTask.setTaskName(oldSonTask.getTaskName());
                isReview = oldSonTask.getReview();
                if (isReview == 1) {
                    taskReview.setReviewId(loginUserId);
                    taskReview.setReviewName(createUserName);
                    taskReview.setId(null);
                    taskReview.setTaskId(id);
                    if (taskTimeArr != null && taskTimeArr.length > 0) {
                        taskReview.setTaskTime(taskTimeArr[0]);
                    }
                    if (taskExecutorsArr != null && taskExecutorsArr.length > 0) {
                        taskReview.setTaskExecutors(taskExecutorsArr[0]);
                    } else {
                        taskReview.setTaskExecutors(createUserName);
                    }
                    // 1:提交，2,：驳回，3：通过
                    if (reviewStatus == 1) {
                        //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
                        oldSonTask.setTaskStatus(4);
                        taskDao.modifyTask(oldSonTask);
                        taskSendMessageService.sendTaskMessage(sonTask, 4);
                        try {
                            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), oldSonTask, sonLogTask, oldSonTask.getProjectId(), 3);
                        } catch (Exception e) {
                            SysLog.error(e);
                        }
                        taskReview.setReviewStatus(1);
                    } else {
                        if (reviewStatus == 2) {
                            taskReview.setReviewStatus(2);
                        } else {
                            oldSonTask.setTaskStatus(3);
                            taskReview.setReviewStatus(3);
                            taskDao.modifyTask(oldSonTask);
                            taskSendMessageService.sendTaskMessage(sonTask, 6);
                            try {
                                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), oldSonTask, sonLogTask, oldSonTask.getProjectId(), 3);
                            } catch (Exception e) {
                                SysLog.error(e);
                            }
                        }
                        //taskReview.setTaskParentId(parentId);
                    }
                    taskReviewDao.saveTaskReview(taskReview);
                    if (taskReview.getId() != null && taskReview.getId() != 0) {
                        saveTaskReviewDevice(oldSonTask, taskReview.getId());
                    }
                    if (reviewStatus == 2) {
                        task.setId(id);
                        Calendar c = Calendar.getInstance();
                        c.setTime(taskReview.getPeriodEndTime());
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        c.add(Calendar.SECOND, -1);
                        task.setCheckEndTime(c.getTime());
                        task.setPeriodEndTime(c.getTime());
                        task.setTaskStatus(1);
                        task.setIsRemind(0);
                        task.setTaskName(oldSonTask.getTaskName());
                        task.setProjectId(projectId);
                        taskDao.modifyTask(task);
                        try {
                            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_TASK", SecurityUserHolder.getUserName(), task, sonLogTask, task.getProjectId(), 3);
                        } catch (Exception e) {
                            SysLog.error(e);
                        }
                        executorDao.deleteExecutorByTaskId(id, 0, null);
                        if (listUser != null && listUser.size() > 0) {
                            executor.setProjectId(projectId);
                            executor.setTaskId(id);
                            executor.setExecutorType(0);
                            for (User u : listUser) {
                                if (u != null) {
                                    executor.setId(null);
                                    executor.setExecutorName(u.getUserName());
                                    //executor.setRoleId(u.getRoleId());
                                    executor.setUserId(u.getUserId());
                                    executor.setExecutorId(u.getOrgUserId());
                                    executorDao.saveExecutor(executor);
                                }
                            }
                        }
                        taskSendMessageService.sendTaskMessage(sonTask, 5);
                    }
                } else {
                    oldSonTask.setTaskStatus(3);
                    taskDao.modifyTask(oldSonTask);
                }
                /*taskCount = taskDao.findTaskCount(sonTask);
                if(taskCount <= 0){
                    parent.setId(oldSonTask.getParentId());
                    parent.setTaskStatus(3);
                    taskDao.modifyTask(oldSonTask);
                }*/
            }
        }
        return Data.isSuccess();
    }

    /**
     * 保存
     *
     * @param task     Task
     * @param reviewId reviewId
     */
    private void saveTaskReviewDevice(Task task, Long reviewId) {
        List<TaskDeviceEntity> deviceList = taskDeviceDao.findDeviceListByTaskId(task.getId());
        if (deviceList != null && deviceList.size() > 0) {
            TaskReviewDevice taskReviewDevice = new TaskReviewDevice();
            taskReviewDevice.setProjectId(task.getProjectId());
            taskReviewDevice.setTaskReviewId(reviewId);
            for (TaskDeviceEntity entity : deviceList) {
                taskReviewDevice.setId(null);
                taskReviewDevice.setBuildingName(entity.getBuildingName());
                taskReviewDevice.setDeviceName(entity.getDeviceName());
                taskReviewDevice.setDeviceQrNo(entity.getDeviceQrNo());
                taskReviewDevice.setFloorName(entity.getFloorName());
                taskReviewDevice.setDeviceStatus(entity.getDeviceStatus());
                taskReviewDevice.setQrNo(entity.getQrNo());
                taskReviewDeviceDao.saveTaskReviewDevice(taskReviewDevice);
            }
        }
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019-07-26 10:54
     */
    @Override
    public Data modifyTaskReview(String json) {
        TaskReview taskReview = JSONObject.parseObject(json, TaskReview.class);
        Integer num = taskReviewDao.modifyTaskReview(taskReview);
        return asseData(num);
    }

    /**
     * 根据id查询审核状态
     *
     * @param reviewId reviewId
     * @return Data
     * @author dwt
     * @date 2019-10-17 10:29
     */
    @Override
    public Data findTaskReviewDetail(Long reviewId) {
        //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-待审核,5:子任务-已驳回6:子任务-通过
        Integer reviewStatus = taskReviewDao.findReviewStatusById(reviewId);
        if (reviewStatus == 1) {
            reviewStatus = 6;
        } else if (reviewStatus == 2) {
            reviewStatus = 5;
        }
        return asseData(reviewStatus);
    }

}
