package com.xjt.cloud.task.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.SubwayTaskService;
import com.xjt.cloud.task.core.service.service.TaskReviewService;
import com.xjt.cloud.task.core.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务Controller层
 *
 * @author dwt
 * @version 1.0
 * @date 2019-07-26 10:32
 */
@RestController
@RequestMapping("/task/")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskReviewService taskReviewService;
    @Autowired
    private SubwayTaskService subwayTaskService;

    /**
     * 查询我的任务列表
     *
     * @param json 参数
     * @return 任务列表
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "findMyTaskList/{projectId}")
    public Data findMyTaskList(String json) {
        return taskService.findMyTaskList(json);
    }

    /**
     * 查询任务工单详情
     *
     * @param json 参数
     * @return 任务列表
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "findTaskLocation/{projectId}")
    public Data findTaskLocation(String json) {
        return taskService.findTaskLocation(json);
    }

    /**
     * 查询任务工单详情(用于离线)
     *
     * @param json 参数
     * @return 任务列表
     * @author huanggc
     * @date 2021-02-25
     */
    @RequestMapping(value = "findTaskLocations/{projectId}")
    public Data findTaskLocations(String json) {
        return taskService.findTaskLocations(json);
    }

    /**
     * 根据筛选条件查询符合条件的任务
     *
     * @param json 任务实体
     * @return 任务列表
     * @author dwt
     * @date 2019-07-25 1401
     */
    @RequestMapping(value = "findTaskList/{projectId}")
    public Data findTaskList(String json) {
        return taskService.findTaskList(json);
    }

    /**
     * 提交任务
     *
     * @param json 参数
     * @return 任务列表
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "saveTask/{projectId}")
    public Data saveTask(String json) {
        return taskService.saveTask(json);
    }

    /**
     * 任务审核
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/29 10:59
     **/
    @RequestMapping(value = "saveTaskReview/{projectId}")
    public Data saveTaskReview(String json) {
        return taskReviewService.transactionSaveTaskReview(json);
    }


    /**
     * 查询我的执行任务列表
     *
     * @param json 参数
     * @return 任务列表
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "findMyExecuteTaskList/{projectId}")
    public Data findMyExecuteTaskList(String json) {
        return taskService.findMyExecuteTaskList(json);
    }


    /**
     * 下载离线任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/19 13:51
     **/
    @RequestMapping(value = "downloadOfflineTask/{projectId}")
    public Data downloadOfflineTask(String json) {
        return taskService.downloadOfflineTask(json);
    }

    /**
     * 查询当前登录人的待办任务数（执行中和待审核）
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2020-03-18 10:20
     */
    @RequestMapping(value = "findCurrLoginProjectTaskNum/{projectId}")
    public Data findCurrLoginProjectTaskNum(String json) {
        return taskService.findCurrLoginProjectTaskNum(json);
    }


    /**
     * 查询扫描二维码巡检的任务
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/23 14:43
     **/
    @RequestMapping(value = "findScanQrNoCheckTaskList")
    public Data findScanQrNoCheckTaskList(String json) {
        return taskService.findScanQrNoCheckTaskList(json);
    }


    /**
     * 查询我的抽查任务列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/7 13:54
     **/
    @RequestMapping(value = "findMySpotCheckTaskList/{projectId}")
    public Data findMySpotCheckTaskList(String json) {
        return taskService.findMySpotCheckTaskList(json);
    }


    /**
     * 查询任务工单详情
     *
     * @param json 参数
     * @return 任务列表
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "findMySpotCheckTaskLocation/{projectId}")
    public Data findMySpotCheckTaskLocation(String json) {
        return taskService.findMySpotCheckTaskLocation(json);
    }

    /**
     * 查询任务工单详情
     *
     * @param json 参数
     * @return 我的任务角标数量
     * @author zhangzf
     * @date 2019-07-25 14:01
     */
    @RequestMapping(value = "findMyTaskSubscript/{projectId}")
    public Data findMyTaskSubscript(String json) {
        return taskService.findMyTaskSubscript(json);
    }

    /**
     * 地铁平面图巡查点统计
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-19 10:26
     */
    @RequestMapping(value = "findCurrentMontSubwayTask/{projectId}")
    public Data findCurrentMontSubwayTask(String json) {
        return subwayTaskService.findCurrentMontSubwayTask(json);
    }

    /**
     * 地铁平面图建筑物楼层巡查点统计
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-22 14:31
     */
    @RequestMapping(value = "findBuildingFloorMetroCheckPointCount/{projectId}")
    public Data findBuildingFloorMetroCheckPointCount(String json) {
        return subwayTaskService.findBuildingFloorMetroCheckPointCount(json);
    }

    /**
     * 地铁平面图根据任务id查询建筑物楼层列表
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-06-30 14:38
     */
    @RequestMapping(value = "findBuildingAndFloorListByTaskId/{projectId}")
    public Data findBuildingAndFloorListByTaskId(String json) {
        return subwayTaskService.findBuildingAndFloorListByTaskId(json);
    }

    /**
     * 地铁平面图用户信息查询
     *
     * @param json 参数
     * @return Data
     * @author dwt
     * @date 2020-07-02 1742
     */
    @RequestMapping(value = "findUserMessageByUserId/{projectId}")
    public Data findUserMessageByUserId(String json) {
        return subwayTaskService.findUserMessageByUserId(json);
    }
}
