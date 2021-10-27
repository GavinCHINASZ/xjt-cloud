package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.task.TaskModelDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskModelDeviceDao 模板导入的设备
 *
 * @author huanggc
 * @date 2021/03/10
 */
@Repository
public interface TaskModelDeviceDao {
    /**
     * 任务模板导入设备保存
     *
     * @param taskModelDeviceList 任务模板设备
     * @param taskModelManageId 任务模板ID
     * @param projectId 项目ID
     * @return int
     * @author huanggc
     * @date 2021/03/10
     */
    int saveTaskModelDeviceList(@Param("taskModelDeviceList") List<TaskModelDevice> taskModelDeviceList,
                                @Param("projectId") Long projectId,
                                @Param("taskModelManageId") Long taskModelManageId);

    /**
     * 任务模板设备 数量
     *
     * @param taskModelDevice 任务模板设备
     * @return int
     * @author huanggc
     * @date 2021/03/10
     */
    Integer findTaskModelDeviceTotalCount(TaskModelDevice taskModelDevice);

    /**
     * 任务模板设备list
     *
     * @param taskModelDevice 任务模板设备
     * @return List<TaskModelDevice>
     * @author huanggc
     * @date 2021/03/10
     */
    List<TaskModelDevice> findTaskModelDeviceList(TaskModelDevice taskModelDevice);

    /**
     * 删除模板设备
     *
     * @param taskModelDevice 任务模板设备
     * @author huanggc
     * @date 2021/03/10
     * @return int
     */
    int deletedTaskModelDevice(TaskModelDevice taskModelDevice);

    /**
     * 保存 模板设备
     *
     * @param taskModelDevice TaskModelDevice
     * @return int
     * @author huanggc
     * @date 2021/03/10
     */
    int saveTaskModelDevice(TaskModelDevice taskModelDevice);

    /**
     * 更新 模板设备
     *
     * @param taskModelDevice TaskModelDevice
     * @return int
     * @author huanggc
     * @date 2021/03/17
     */
    int updateTaskModelDevice(TaskModelDevice taskModelDevice);
}
