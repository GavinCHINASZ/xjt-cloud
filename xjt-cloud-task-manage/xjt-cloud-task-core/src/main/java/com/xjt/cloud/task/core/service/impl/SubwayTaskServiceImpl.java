package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.dao.task.TaskCheckPointDao;
import com.xjt.cloud.task.core.dao.task.TaskDao;
import com.xjt.cloud.task.core.entity.check.CheckPoint;
import com.xjt.cloud.task.core.entity.SubwayTask;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.User;
import com.xjt.cloud.task.core.service.service.SubwayTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SubwayTaskServiceImpl
 * @Author dwt
 * @Date 2020-06-19 9:38
 * @Version 1.0
 */
@Service
public class SubwayTaskServiceImpl extends AbstractService implements SubwayTaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskCheckPointDao checkPointDao;
    @Autowired
    private UserDao userDao;

    /**
     * @Author: dwt
     * @Date: 2020-06-19 10:23
     * @Param: json
     * @Return: Data
     * @Description 地铁平面图巡查点统计
     */
    @Override
    public Data findCurrentMontSubwayTask(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        SubwayTask subwayTask = taskDao.findCurrentMontSubwayTask(task);
        return asseData(subwayTask);
    }

    /**
     * @Author: dwt
     * @Date: 2020-06-22 14:31
     * @Param: json
     * @Return: Data
     * @Description 地铁平面图建筑物楼层巡查点统计
     */
    @Override
    public Data findBuildingFloorMetroCheckPointCount(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<SubwayTask> subwayTaskList = taskDao.findBuildingFloorMetroCheckPointCount(task);
        return asseData(subwayTaskList);
    }

    /**
     * @Author: dwt
     * @Date: 2020-06-30 14:32
     * @Param: json
     * @Return: Data
     * @Description 地铁平面图根据任务id查询建筑物楼层
     */
    @Override
    public Data findBuildingAndFloorListByTaskId(String json) {
        Task task = JSONObject.parseObject(json, Task.class);
        List<SubwayTask> subwayTaskList = taskDao.findBuildingAndFloorListByTaskId(task);
        return asseData(subwayTaskList);
    }

    /**
     * @Author: dwt
     * @Date: 2020-07-02 16:43
     * @Param: json
     * @Return: Data
     * @Description 地铁平面图布点巡查点列表查询
     */
    @Override
    public Data findCheckPointListByTaskIdSubway(String json) {
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        List<CheckPoint> list = checkPointDao.findCheckPointListByTaskIdSubway(checkPoint);
        return asseData(list);
    }

    /**
     * @Author: dwt
     * @Date: 2020-07-02 17:36
     * @Param: json
     * @Return: Data
     * @Description 地铁平面图查询用户信息
     */
    @Override
    public Data findUserMessageByUserId(String json) {
        User user = JSONObject.parseObject(json, User.class);
        user = userDao.findUserMessageByUserId(user);
        return asseData(user);
    }

}
