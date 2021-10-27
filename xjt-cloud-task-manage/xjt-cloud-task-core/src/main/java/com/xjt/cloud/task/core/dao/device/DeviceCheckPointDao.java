package com.xjt.cloud.task.core.dao.device;

import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.device.DeviceCheckPoint;
import com.xjt.cloud.task.core.entity.project.TaskFloor;
import com.xjt.cloud.task.core.entity.task.TaskDevice;
import com.xjt.cloud.task.core.entity.task.TaskModelDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CheckPointDao
 *
 * @author dwt
 * @date 2019-07-31 1117
 */
public interface DeviceCheckPointDao {

    /**
     * 根据id查询
     *
     * @param id id
     * @return java.lang.Long
     * @author dwt
     * @date 2019-08-06 1422
     */
    String findCheckPointName(Long id);

    /**
     * 根据部门id查询巡更点列表
     *
     * @param deviceCheckPoint 巡更点实体
     * @return java.lang.Long
     * @author dwt
     * @date 2019-08-06 1422
     */
    List<TaskDeviceCheckPoint> findCheckPointList(DeviceCheckPoint deviceCheckPoint);

    Integer findCheckPointCount(DeviceCheckPoint deviceCheckPoint);

    /**
     * 根据巡更点添加设备：筛选参数
     *
     * @param taskDevice 任务设备筛选实体
     * @return 巡更点列表
     * @author dwt
     * @date 2019-08-08 1144
     */
    List<TaskDeviceCheckPoint> findCheckPoint(TaskDevice taskDevice);

    /**
     * 按系统添加设备：根据设备类型id查询
     *
     * @param taskDevice 任务设备筛选实体
     * @return 巡更点列表
     * @author dwt
     * @date 2019-08-08 1521
     */
    List<TaskDeviceCheckPoint> findCheckPointByDeviceTypeId(TaskDevice taskDevice);

    Integer findCheckPointCountByDeviceTypeId(TaskDevice taskDevice);

    /**
     * 根据巡查点统计设备数
     *
     * @param projectId 项目ID
     * @return id id
     * @author dwt
     * @date 2019-10-31 1018
     */
    Integer deviceCountByCheckPointId(@Param("projectId") Long projectId, @Param("id") Long id);

    /**
     * 按楼层筛选巡查点
     *
     * @param taskDevice 任务设备筛选实体
     * @return 巡更点列表
     * @author dwt
     * @date 2019-11-13 1900
     */
    List<TaskDeviceCheckPoint> findCheckPointListByTaskDevice(TaskDevice taskDevice);

    List<TaskDeviceCheckPoint> findBuildingFloorCheckPointByProjectId(TaskDevice taskDevice);

    /**
     * 根据巡更点名称查询部门巡更点
     *
     * @param taskDevice 任务设备筛选实体
     * @return 部门巡更点
     * @author dwt
     * @date 2019-11-18 1923
     */
    List<OrgCheckPoint> findCheckPointByPointName(TaskDevice taskDevice);

    /**
     * 根据巡更点名称查询部门巡更点是否选中
     *
     * @param taskDevice 任务设备筛选实体
     * @return 部门巡更点
     * @author dwt
     * @date 2020-08-05 949
     */
    List<OrgCheckPoint> findCheckPointByPointNameSelOrNotSel(TaskDevice taskDevice);

    /**
     * findCheckPointId
     *
     * @param id id
     * @return com.xjt.cloud.task.core.entity.TaskCheckPoint
     * @author zhangZaiFa
     * @date 2019/11/27 1123
     **/
    TaskCheckPoint findCheckPointId(@Param("id") Long id);

    /**
     * 修改设备巡查点状态
     *
     * @param list TaskCheckPoint
     * @param status status
     * @author zhangZaiFa
     * @date 2019/11/29 1417
     **/
    void updateDeviceCheckPointStatus(@Param("list") List<TaskCheckPoint> list, @Param("status") int status);

    /**
     * 查询楼层的巡查点列表
     *
     * @param taskFloor TaskFloor
     * @return TaskDeviceCheckPoint
     * @author dwt
     * @date 2020-05-21 1431
     */
    List<TaskDeviceCheckPoint> findCheckPointListByFloorId(TaskFloor taskFloor);

    /**
     * 任务添加设备查询巡查点id列表
     *
     * @param checkPoint DeviceCheckPoint
     * @return java.lang.Long
     * @author dwt
     * @date 2020-07-20 1119
     */
    List<Long> findCheckPointLongList(DeviceCheckPoint checkPoint);

    /**
     * 查询部门以及部门的子部门下的所有巡查点id
     *
     * @param checkPoint DeviceCheckPoint
     * @return java.lang.Long
     * @author dwt
     * @date 2020-07-24 1340
     */
    List<Long> findOrgAndSonOrgCheckPoints(DeviceCheckPoint checkPoint);

    /**
     * APP端任务管理添加设备巡查点列表
     *
     * @param checkPoint DeviceCheckPoint
     * @return TaskDeviceEntity
     * @author dwt
     * @date 2020-08-25 1413
     */
    List<AppCheckPoint> findCheckPointListBuildingAndSys(DeviceCheckPoint checkPoint);

    /**
     * 根据 任务模板ID查询
     *
     * @param checkPoint 参数
     * @return List
     * @author huanggc
     * @date 2021/03/17
     */
    List<Long> findCheckPointLongListByTaskModelManageId(DeviceCheckPoint checkPoint);

    /**
     * 上传的表格中输入巡检点在项目中不存在
     *
     * @param taskModelDeviceList 参数
     * @param projectId 项目ID
     * @return List<TaskModelDevice>
     * @author huanggc
     * @date 2021/03/29
     */
    List<TaskModelDevice> findCheckPointNotIn(@Param("projectId") Long projectId, List<TaskModelDevice> taskModelDeviceList);

    /**
     * 判断 巡检点 是否存在
     *
     * @param taskModelDevice 参数
     * @return int
     * @author huanggc
     * @date 2021/03/29
     */
    int findByTaskModelDevice(TaskModelDevice taskModelDevice);
}
