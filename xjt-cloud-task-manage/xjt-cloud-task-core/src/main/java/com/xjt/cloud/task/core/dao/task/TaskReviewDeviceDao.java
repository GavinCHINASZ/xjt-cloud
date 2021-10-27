package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.TaskReviewDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TaskReviewDeviceDao
 * @Author dwt
 * @Date 2019-11-22 15:55
 * @Version 1.0
 */
@Repository
public interface TaskReviewDeviceDao {
    /**
     *@Author: dwt
     *@Date: 2019-11-22 16:12
     *@Param: TaskReviewDevice
     *@Return: void
     *@Description 保存审核设备
     */
    void saveTaskReviewDevice(TaskReviewDevice taskReviewDevice);
    /**
     *@Author: dwt
     *@Date: 2019-11-22 16:13
     *@Param: java.lang.Long
     *@Return: List<TaskReviewDevice>
     *@Description 根据审核id查询审核设备列表
     */
    List<TaskReviewDevice> findTaskReviewDeviceList(@Param("id") Long id);
}
