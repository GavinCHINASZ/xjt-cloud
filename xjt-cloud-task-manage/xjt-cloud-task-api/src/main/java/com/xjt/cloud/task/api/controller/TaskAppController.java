package com.xjt.cloud.task.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.service.service.CheckPointService;
import com.xjt.cloud.task.core.service.service.device.DeviceService;
import com.xjt.cloud.task.core.service.service.project.RoleService;
import com.xjt.cloud.task.core.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TaskAppController
 * @Author dwt
 * @Date 2020-08-24 15:09
 * @Version 1.0
 */
@RestController
@RequestMapping("/task/app")
public class TaskAppController extends AbstractController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CheckPointService checkPointService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RoleService roleService;

    /**
     *@Author: dwt
     *@Date: 2019-07-25 11:33
     *@Param: 任务实体
     *@Return: id
     *@Description:保存任务
     */
    @RequestMapping(value = "saveTask/{projectId}")
    public Data saveTask(String json){
        return taskService.transactionSaveTask(json);
    }

    /**
     *@Author: dwt
     *@Date: 2020-08-25 14:37
     *@Param: json
     *@Return: Data
     *@Description APP端任务管理添加设备巡查点列表
     */
    @RequestMapping(value = "findCheckPointListBuildingAndSys/{projectId}")
    public Data findCheckPointListBuildingAndSys(String json) throws Exception{
        return checkPointService.findCheckPointListBuildingAndSys(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-08-09 11:11
     *@Param: java.lang.Long
     *@Return: 设备列表
     *@Description 根据巡更点id查询设备列表
     */
    @RequestMapping(value = "/findDeviceList/{projectId}")
    public Data findDeviceListByCheckPointId(String json){
        return deviceService.findDeviceListByCheckPointId(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-08-09 11:27
     *@Param: java.lang.Long
     *@Return: 角色列表
     *@Description 根据项目id查询角色列表
     */
    @RequestMapping(value = "findRole/{projectId}")
    public Data findRoleByProjectId(String json){
        return roleService.findRoleByProjectId(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-07-25 14:01
     *@Param: 任务实体
     *@Return: 任务列表
     *@Description: 根据筛选条件查询符合条件的任务
     */
    @RequestMapping(value = "findTaskList/{projectId}")
    public Data findTaskList(String json){
        return taskService.findTaskList(json);
    }

    /**
     * @Description 查询app首页巡查工单信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @RequestMapping(value = "findUserProjectCheckWordOrderData/{projectId}")
    public JSONObject findUserProjectCheckWordOrderData(String json){
        return taskService.findUserProjectCheckWordOrderData(json);
    }

    /**
     *@Author: dwt
     *@Date: 2019-08-15 14:49
     *@Param: json
     *@Return: Data
     *@Description app端子任务列表查询
     */
    @RequestMapping(value = "findSonTaskListApp/{projectId}")
    public Data findSonTaskListApp(String json){
        return taskService.findSonTaskListApp(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-08-16 13:47
     *@Param: json
     *@Return: Data
     *@Description 对任务进行逻辑删除和恢复
     */
    @RequestMapping(value = "deletedTask/{projectId}")
    public Data deletedTask(String json){
        return taskService.transactionDeleteTask(json);
    }
    /**
     *@Author: dwt
     *@Date: 2019-07-25 14:03
     *@Param: id
     *@Return: 任务对象
     *@Description: 根据id查询任务对象
     */
    @RequestMapping(value = "findTaskById/{projectId}")
    public Data findTaskById(String json){
        Task task = JSONObject.parseObject(json,Task.class);
        return taskService.findTaskById(task.getId(),false);
    }
}
