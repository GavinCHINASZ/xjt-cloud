package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.check.CheckPoint;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.TaskCheckPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 *@ClassName TaskCheckPoint
 *@Author dwt
 *@Date 2019-07-25 15:05
 *@Description: 任务巡检项Dao
 *@Version 1.0
 */
@Repository
public interface TaskCheckPointDao {

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:11
     *@Param: 巡更点对象
     *@Return: 巡更点列表
     *@Description:筛选符合条件的巡更点对象
     */
    TaskCheckPoint findTaskCheckPoint(TaskCheckPoint taskCheckPoint);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:12
     *@Param: 巡更点对象
     *@Return: 主键id
     *@Description: 保存巡更点对象
     */
    Integer saveTaskCheckPoint(TaskCheckPoint taskCheckPoint);

    /**
     *@Author: dwt
     *@Date: 2019-07-25 15:13
     *@Param: 巡更点对象
     *@Return:
     *@Description: 修改巡更点对象
     */
    void modifyTaskCheckPoint(TaskCheckPoint taskCheckPoint);
    /**
     *@Author: dwt
     *@Date: 2019-08-12 11:25
     *@Param: java.lang.Long
     *@Return: 巡更点列表
     *@Description: 根据任务id查询
     */
    List<TaskCheckPoint> findCheckPointListByTaskId(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-10-14 11:28
     *@Param: java.lang.Long
     *@Return: 寻根点设备列表
     *@Description 根据任务id查询
     */
    List<CheckPoint> findCheckPointDeviceList(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-10-14 14:19
     *@Param: java.lang.Long
     *@Return: void
     *@Description 根据taskId和checkPointId删除对应的数据
     */
    void deleteCheckPointByTaskId(@Param("taskId") Long taskId,@Param("taskIds") List<Long> taskIds);

    /**@MethodName: findTaskCheckPointPage
     * @Description: 查询任务巡查点分页列表
     * @Param: [task]
     * @Return: java.util.List<com.xjt.cloud.task.core.entity.TaskCheckPoint>
     * @Author: zhangZaiFa
     * @Date:2019/11/26 10:23
     **/
    List<TaskCheckPoint> findTaskCheckPointPage(Task task);

    /**@MethodName: findTaskCheckPointPageCount
     * @Description: 查询任务巡查点总数
     * @Param: [task]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/11/26 10:22
     **/
    Integer findTaskCheckPointPageCount(Task task);

    /**@MethodName: updateTaskDeviceCheckPointStatus
     * @Description: 修改任务巡检点状态
     * @Param: [tcp]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/28 14:13
     **/
    void updateTaskDeviceCheckPointStatus(@Param("list")List<TaskCheckPoint> list, @Param("taskCheckPointStatus") Integer status);

    /**@MethodName: findCheckPointIdAndTaskId
     * @Description: 通过巡查点ID和任务ID查询任务巡查点
     * @Param: [checkPointId, taskId]
     * @Return: com.xjt.cloud.task.core.entity.TaskCheckPoint
     * @Author: zhangZaiFa
     * @Date:2019/12/17 16:14
     **/
    TaskCheckPoint findCheckPointIdAndTaskId(@Param("checkPointId") Long checkPointId, @Param("taskId")Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-12-18 14:52
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 根据任务id统计任务巡查点数
     */
    Integer countTaskCheckPointNum(Long taskId);

    /**
     * 根据任务统计任务巡查点数
     *
     * @Author: huanggc
     * @Date: 2020-03-17
     * @param task
     * @return Integer
     */
    Integer findByTaskCount(Task task);

    /**
     * APP月度工单统计--巡查点概览列表 数量
     *
     * @Author: huanggc
     * @Date: 2020-03-18
     * @param task
     * @return Data
     */
    Integer findCheckOverviewCount(Task task);

    /**
     * APP月度工单统计--巡查点概览列表
     *
     * @Author: huanggc
     * @Date: 2020-03-18
     * @param task
     * @return List<TaskCheckPoint>
     */
    List<TaskCheckPoint> findCheckOverviewList(Task task);
    /**
     *@Author: dwt
     *@Date: 2020-04-02 10:40
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 统计已检巡查点数
     */
    Integer countCheckedPointNum(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2020-05-09 10:07
     *@Param: java.lang.Long
     *@Return: java.lang.Long
     *@Description 查询任务的巡查点
     */
    Set<Long> findTaskCheckPoints(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2020-07-06 15:00
     *@Param:
     *@Return:
     *@Description 删除任务的部分巡查点
     */
    void deleteCheckPointByTaskIdAndPointId(@Param("taskIds") List<Long> taskIds,@Param("checkPointIds") Set<Long> checkPointIds);
    /**
     *@Author: dwt
     *@Date: 2020-06-28 15:20
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 根据巡查点id删除任务中的巡查点
     */
    Integer deleteCheckPointByCheckPointId(TaskCheckPoint checkPoint);
    /**
     *@Author: dwt
     *@Date: 2020-07-02 16:43
     *@Param: CheckPoint
     *@Return: CheckPoint
     *@Description 地铁平面图布点巡查点列表查询
     */
    List<CheckPoint> findCheckPointListByTaskIdSubway(CheckPoint checkPoint);
}
