package com.xjt.cloud.task.core.dao.device;

import com.xjt.cloud.task.core.entity.*;
import com.xjt.cloud.task.core.entity.check.CheckItemRecord;
import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.device.Device;
import com.xjt.cloud.task.core.entity.task.AppTaskCheckItem;
import com.xjt.cloud.task.core.entity.task.AppTaskDevice;
import com.xjt.cloud.task.core.entity.task.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 *@ClassName DeviceDao
 *@Author dwt
 *@Date 2019-08-06 17:13
 *@Description: 设备Dao层接口
 *@Version 1.0
 */
public interface TaskDeviceDao {
    /**
     *@Author: dwt
     *@Date: 2019-08-07 11:23
     *@Param: java.lang.Long
     *@Return: 设备列表
     *@Description 根据巡更点查询设备列表
     */
    List<TaskDeviceEntity> findDeviceListByCheckPointId(Task task);
    /**
     *@Author: dwt
     *@Date: 2019-08-12 14:31
     *@Param: java.lang.Long
     *@Return: 设备列表
     *@Description: 根据任务id查询设备列表
     */
    List<TaskDeviceEntity> findDeviceListByTaskId(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2020-06-29 9:35
     *@Param: java.lang.Long
     *@Return: TaskDeviceEntity
     *@Description 分页查询任务设备列表
     */
    List<TaskDeviceEntity> findTaskDeviceListByPage(Task task);
    /**
     *@Author: dwt
     *@Date: 2019-08-15 14:09
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 根据任务id统计设备数量
     */
    Integer findDeviceNumByTaskId(Long taskId);
    /**
     *@Author: dwt
     *@Date: 2019-08-15 14:52
     *@Param: java.lang.Long
     *@Return: AppTaskDevice
     *@Description 根据任务id查询任务设备列表
     */
    List<AppTaskDevice> findAppTaskDeviceListByTaskId(AppTaskDevice appTaskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-08-15 17:36
     *@Param: java.lang.Long
     *@Return: String
     *@Description APP 根据设备id查询设备巡检项
     */
    List<AppTaskCheckItem> findDeviceItemByDeviceIdApp(AppTaskDevice appTaskDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-04 15:58
     *@Param: java.lang.Long
     *@Return: java.lang.Integer
     *@Description 根据任务id统计设备数
     */
    Integer findDeviceCountByTaskId(Long taskId);

    /**@MethodName: findCheckPointIdDeviceList
     * @Description: 查询巡查点下的设备
     * @Param: [checkPointId]
     * @Return: java.util.List<com.xjt.cloud.task.core.entity.device.Device>
     * @Author: zhangZaiFa
     * @Date:2019/11/27 14:09
     **/
    List<Device> findCheckPointIdDeviceList(Long checkPointId);

    /**@MethodName: updateDeviceStatus
     * @Description: 修改设备状态
     * @Param: [faultDeviceList, i]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/28 14:13
     **/
    void updateDeviceStatus(@Param("ids")List<Long> ids,@Param("status") int status);
    /**
     *@Author: dwt
     *@Date: 2020-03-25 9:44
     *@Param: TaskPointUpload
     *@Return: list
     *@Description 任务导入巡查点excel查询设备
     */
    List<TaskDeviceEntity> uploadCheckPointExcel(TaskPointUpload taskPointUpload);

    /**@MethodName: findMySpotCheckTaskDeviceList
     * @Description: 查询我的抽查任务设备列表
     * @Param: [task]
     * @Return: java.util.List<com.xjt.cloud.task.core.entity.device.Device>
     * @Author: zhangZaiFa
     * @Date:2020/5/7 14:29
     **/
    List<Device> findMySpotCheckTaskDeviceList(Task task);

    /**@MethodName: findMySpotCheckTaskDeviceListCount
     * @Description: 查询我的抽查任务设备列表数量
     * @Param: [task]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/5/7 14:29
     **/
    Integer findMySpotCheckTaskDeviceListCount(Task task);
    /**
     *@Author: dwt
     *@Date: 2020-05-08 13:47
     *@Param: TaskPointUpload
     *@Return: List<TaskDeviceEntity>
     *@Description 任务工单设备导入查询设备
     */
    List<TaskDeviceEntity> findTaskSheetDevice(@Param("set") Set<Long> set,@Param("projectId") Long projectId);
    /**
     *@Author: dwt
     *@Date: 2020-05-09 10:34
     *@Param: TaskPointUpload
     *@Return: java.util.Set
     *@Description 任务工单设备导入查询巡查点ids
     */
    Set<Long> findTaskSheetCheckPoint(TaskPointUpload taskPointUpload);
    /**@MethodName: findDeviceLocation
     * @Description: 查询设备详情
     * @Param: [checkPointId]
     * @Return: com.xjt.cloud.task.core.entity.device.Device
     * @Author: zhangZaiFa
     * @Date:2020/5/8 15:38
     **/
    Device findDeviceLocation(@Param("id") Long id);

    /**@MethodName: findDeviceSpotCheckItem
     * @Description: 查询设备的抽查项
     * @Param: [device]
     * @Return: java.util.List<com.xjt.cloud.task.core.entity.check.CheckItemTask>
     * @Author: zhangZaiFa
     * @Date:2020/5/9 15:48
     **/
    List<CheckItemRecord> findDeviceSpotCheckItem(Device device);

    /**
     * @MethodName: findListByTaskId
     * @Description: 查询设备的抽查项
     * @Param: [taskId]
     * @Return: List<TaskDeviceEntity>
     * @Author: huanggc
     * @Date: 2020/05/26
     **/
    List<CheckRecord> findListByTaskId(CheckRecord checkRecord);

    Integer findCheckDeviceCountByTaskId(@Param("taskId") Long taskId);
}
