package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.ExecutorDao;
import com.xjt.cloud.task.core.dao.task.TaskDao;
import com.xjt.cloud.task.core.entity.Executor;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.User;
import com.xjt.cloud.task.core.service.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExecutorServiceImpl
 * @Author dwt
 * @Date 2019-10-12 16:11
 * @Version 1.0
 */
@Service
public class ExecutorServiceImpl extends AbstractService implements ExecutorService {

    @Autowired
    private ExecutorDao executorDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskDao taskDao;

    @Override
    public Data findExecutorList(String json) {
        Executor executor = JSONObject.parseObject(json, Executor.class);
        Long taskId = executor.getTaskId();
        Long projectId = executor.getProjectId();
        Map<String, Object> mapList = new HashMap<>(3);
        if (taskId != null && taskId != 0 && projectId != null && projectId != 0) {
            Task oldTask = taskDao.findTaskById(taskId);
            List<User> executorList = executorDao.findExecutorListByTaskId(0, taskId);

            List<User> reviewList = executorDao.findExecutorListByTaskId(1, taskId);

            if (oldTask.getParentId() != null && oldTask.getParentId() != 0) {
                if (executorList == null || executorList.size() <= 0) {
                    executorList = executorDao.findExecutorListByTaskId(0, oldTask.getParentId());
                }
                if (reviewList == null || reviewList.size() <= 0) {
                    reviewList = executorDao.findExecutorListByTaskId(1, oldTask.getParentId());
                }
            }
            List<User> userList = userDao.findUserByProjectId(projectId);
            mapList.put("executorList", executorList);
            mapList.put("reviewList", reviewList);
            mapList.put("userList", userList);
        }
        return asseData(mapList);
    }
}
