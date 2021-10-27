package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.task.AppTask;
import com.xjt.cloud.task.core.entity.task.AppTaskDevice;
import com.xjt.cloud.task.core.entity.task.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskDao 任务管理Dao
 *
 * @author dwt
 * @date 2019-07-25 11:30
 */
@Repository
public interface TaskDao {

    /**
     * 保存任务
     *
     * @param task 任务实体
     * @return Integer
     * @author dwt
     * @date 2019-07-25 11:33
     */
    Integer saveTask(Task task);

    /**
     * 查询任务列表总数
     *
     * @param task 任务
     * @return 任务列表总数
     * @author dwt
     * @date 2019-07-25 11:44
     */
    Integer findTaskCount(Task task);

    /**
     * 根据筛选条件查询符合条件的任务
     *
     * @param task 任务实体
     * @return List<Task> 任务列表
     * @author dwt
     * @date 2019-07-25 14:01
     */
    List<Task> findTaskList(Task task);

    /**
     * 根据筛选条件查询符合条件的任务
     *
     * @param task 任务实体
     * @return List<Task> 任务列表
     * @author dwt
     * @date 2019-07-25 14:01
     */
    List<Task> findAllSonTaskList(Task task);

    /**
     * 根据id查询任务对象
     *
     * @param id id
     * @return Task 任务对象
     * @author dwt
     * @date 2019-07-25 14:03
     */
    Task findTaskById(@Param("id") Long id);

    /**
     * 修改任务信息
     *
     * @param task 任务实体
     * @return Integer
     * @author dwt
     * @date 2019-08-05 15:49
     */
    Integer modifyTask(Task task);

    /**
     * 根据 任务id 查询实体
     *
     * @param idList 任务id集合
     * @return List<Task> list任务集合
     * @author huanggc
     * @date 2019-08-13
     */
    List<Task> findTaskByIds(List<Long> idList);

    /**
     * 删除任务
     *
     * @param task 任务实体
     * @author dwt
     * @date 2019-08-13 13:37
     */
    void deleteTask(Task task);

    /**
     * 查询符合条件的任务
     *
     * @param appTask app端任务实体
     * @return List<AppTask> app任务实体
     * @author dwt
     * @date 2019-08-15 10:43
     */
    List<AppTask> findSonTaskListApp(AppTask appTask);

    /**
     * App 查询未检设备任务详情
     *
     * @param appTaskDevice AppTaskDevice
     * @return AppTaskDeviceItem
     * @author dwt
     * @date 2019-08-15 17:10
     */
    AppTaskDeviceItem findTaskDeviceDetailsApp(AppTaskDevice appTaskDevice);

    /**
     * 逻辑删除父任务
     *
     * @param task Task
     * @author dwt
     * @date 2019-10-16 9:14
     */
    void modifyDeletedTask(Task task);

    /**
     * 逻辑删除子任务
     *
     * @param task Task
     * @author dwt
     * @date 2019-10-16 9:14
     */
    void modifyDeletedSonTask(Task task);

    /**
     * 根据任务ID数据查询任务实体
     *
     * @param ids 任务ID
     * @return List<Task> 任务实体 list集合
     * @author huanggc
     * @date 2019/10/21
     */
    List<Task> findIds(Long[] ids);

    /**
     * 统计任务转态数
     *
     * @param task Task
     * @return TaskStatusReport
     * @author dwt
     * @date 2019-11-05 18:18
     */
    TaskStatusReport findTaskStatusNum(Task task);

    /*
     * @author dwt
     * @date 2019-11-22 15:53
     * @param projectId 项目ID
     * @return java.lang.String
     *  查询项目名称
     */
    String findProjectNameById(Long projectId);

    /**
     * 查询任务详情
     *
     * @param task 任务
     * @return com.xjt.cloud.task.core.entity.task.Task
     * @author zhangZaiFa
     * @date 2019/11/26 10:22
     **/
    Task findTaskLocation(Task task);

    /**
     * 查询我的任务列表
     *
     * @param task 任务
     * @return java.util.List<com.xjt.cloud.task.core.entity.task.Task>
     * @author zhangZaiFa
     * @date 2019/11/26 10:24
     **/
    List<Task> findMyTaskList(Task task);

    /**
     * 查询我的任务总数
     *
     * @param task 任务
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2019/11/26 10:24
     **/
    Integer findMyTaskListCount(Task task);

    /**
     * 查询我的任务状态数量
     *
     * @param task 任务
     * @return com.xjt.cloud.task.core.entity.TaskStatusReport
     * @author zhangZaiFa
     * @date 2019/11/26 10:24
     **/
    TaskStatusReport findMyTaskStatusReport(Task task);

    /**
     * 根据父任务id查询子任务列表
     *
     * @param task 任务
     * @return List<Task>
     * @author dwt
     * @date 2019-12-12 13:33
     */
    List<Task> findSonTaskList(Task task);

    /**
     * 根据父任务id修改子任务
     *
     * @param task 任务
     * @author dwt
     * @date 2019-12-13 10:23
     */
    void modifySonTaskByParentId(Task task);

    /**
     * 查询符合定时任务处理的任务列表
     *
     * @param task 任务
     * @return java.util.List
     * @author dwt
     * @date 2019-12-17 10:27
     */
    List<Task> findHandleTaskList(Task task);

    /**
     * 月度工单统计--工单概览
     *
     * @param task 任务实体
     * @return Task 任务实体
     * @author huanggc
     * @date 2019-11-22
     */
    Task taskOverview(Task task);

    /**
     * 月度工单统计--巡查点概览
     *
     * @param task 任务
     * @return Task
     * @author huanggc
     * @date 2020-03-16
     */
    Task checkOverview(Task task);

    /**
     * 月度工单统计--设备详情 数量
     *
     * @param task 任务
     * @return Task
     * @author huanggc
     * @date 2020-03-16
     */
    Integer findDeviceCount(Task task);

    /**
     * 月度工单统计--设备系统详情
     *
     * @param task 任务
     * @return List<Device>
     * @author huanggc
     * @date 2020-03-16
     */
    List<Device> deviceDetails(Task task);

    /**
     * 大屏--巡检管理--设备数量
     *
     * @param task 任务
     * @return Device
     * @author huanggc
     * @date 2020-04-07
     */
    Device taskDeviceNum(Task task);

    /**
     * 月度工单统计--任务概览列表
     *
     * @param task 任务
     * @return Integer
     * @author huanggc
     * @date 2020-03-17
     */
    List<Task> findTaskOverviewTable(Task task);

    /**
     * PC月度工单统计--巡查点概览表
     *
     * @param task 任务
     * @return List<Device>
     * @author huanggc
     * @date 2020-03-17
     */
    List<Task> checkOverviewTable(Task task);

    /**
     * 月度工单统计--巡查点概览表 数量
     *
     * @param task 任务
     * @return Integer
     * @author huanggc
     * @date 2020-03-17
     */
    Integer findTaskOverviewTableCount(Task task);

    /**
     * 查询扫描二维码巡检的任务
     *
     * @param task 任务
     * @return java.util.List<com.xjt.cloud.task.core.entity.task.Task>
     * @author zhangZaiFa
     * @date 2020/4/23 15:08
     **/
    List<Task> findScanQrNoCheckTaskList(Task task);

    /**
     * 查询我的抽查任务列表
     *
     * @param task 任务
     * @return java.util.List<com.xjt.cloud.task.core.entity.task.Task>
     * @author zhangZaiFa
     * @date 2020/5/7 13:58
     **/
    List<Task> findMySpotCheckTaskList(Task task);

    /**
     * 查询我的抽查任务列表数量
     *
     * @param task 任务
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/5/7 13:59
     **/
    Integer findMySpotCheckTaskListCount(Task task);

    /**
     * 查询我的抽查任务详情
     *
     * @param task 任务
     * @return com.xjt.cloud.task.core.entity.task.Task
     * @author zhangZaiFa
     * @date 2020/5/7 14:48
     **/
    Task findMySpotCheckTaskLocation(Task task);

    /**
     * 查询任务分析数据
     *
     * @param task 任务
     * @return com.xjt.cloud.task.core.entity.task.Task
     * @author zhangZaiFa
     * @date 2020/5/13 10:53
     **/
    Task findMonthTaskAnalysis(Task task);

    /**
     * 地铁 巡检工单列表(子任务)
     *
     * @param task 任务
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-05-13
     **/
    List<Task> findTaskMetroScreenList(Task task);

    /**
     * 查询所有未开始和执行中的父任务
     *
     * @author dwt
     * @date 2020-05-18 14:13
     * @return Task
     */
    List<Task> findAllParentTask();

    /**
     * 查询大屏任务记录的数据列表
     *
     * @param task 任务
     * @return java.util.List<com.xjt.cloud.task.core.entity.task.Task>
     * @author zhangZaiFa
     * @date 2020/5/19 13:57
     **/
    List<Task> findScreenTaskRecordAnalysisList(Task task);

    /**
     * 查询大屏任务记录的数据数量
     *
     * @param task 任务
     * @return java.lang.Integer
     * @author zhangZaiFa
     * @date 2020/5/19 13:58
     **/
    Integer findScreenTaskRecordAnalysisCount(Task task);

    /**
     * 查询我的任务角标
     *
     * @param task 任务
     * @return com.xjt.cloud.task.core.entity.TaskStatusReport
     * @author zhangZaiFa
     * @date 2020/6/1 14:05
     **/
    List<TaskStatusReport> findMyTaskSubscript(Task task);

    /**
     * 根据父任务id查询未开始的任务工单
     *
     * @param parentTd 父ID
     * @return java.lang.Long
     * @author dwt
     * @date 2020-06-04 9:12
     */
    List<Long> findNotStartSonTaskByParentId(@Param("parentTd") Long parentTd);

    /**
     * @param task 任务
     * @return SubwayTask
     * @author dwt
     * @date 2020-06-19 10:19
     */
    SubwayTask findCurrentMontSubwayTask(Task task);

    /**
     * @param task 任务
     * @return SubwayTask
     * 地铁平面图建筑物楼层巡查点统计
     * @author dwt
     * @date 2020-06-22 14:23
     */
    List<SubwayTask> findBuildingFloorMetroCheckPointCount(Task task);

    /**
     * 地铁抽检工单故障率完成率设备数量统计
     *
     * @param taskId 任务ID
     * @return SpotCheckTask
     * @author dwt
     * @date 2020-06-23 10:13
     */
    SpotCheckTask findSpotCheckTaskDeviceStatusCount(Long taskId);

    /**
     * 根据任务id查询建筑物楼层
     *
     * @param task 任务
     * @return SubwayTask
     * @author dwt
     * @date 2020-06-30 14:29
     */
    List<SubwayTask> findBuildingAndFloorListByTaskId(Task task);
}
