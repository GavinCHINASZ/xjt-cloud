package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.TaskSpotCheckTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-task-manage
 * @description:任务抽查工具
 * @author: zhangZaifa
 * @create: 2020-05-09 13:58
 **/
@Repository
public interface TaskSpotCheckToolDao {
    /**@MethodName: findTaskSpotCheckTools
     * @Description: 查询抽查任务的工具
     * @Param: [id]
     * @Return: java.util.List<com.xjt.cloud.task.core.entity.TaskSpotCheckTool>
     * @Author: zhangZaiFa
     * @Date:2020/5/9 14:07
     **/
    List<TaskSpotCheckTool> findTaskSpotCheckTools(@Param("taskId") Long taskId);

    /**@MethodName: delTaskSpotCheckTools
     * @Description: 删除任务工具
     * @Param: [taskId]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/5/9 14:37
     **/
    Integer delTaskSpotCheckTools(@Param("taskId")Long taskId);

    /**@MethodName: saveTaskSpotCheckTools
     * @Description: 保存任务工具
     * @Param: [taskSpotCheckTools]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/5/9 14:37
     **/
    Integer saveTaskSpotCheckTools(List<TaskSpotCheckTool> taskSpotCheckTools);

    /**
     * 根据 系统设备名称 查询工具
     *
     * @MethodName: findByDeviceTypeList
     * @param taskSpotCheckTool
     * @Return TaskSpotCheckTool
     * @Author huanggc
     * @Date 2020-05-11
     **/
    List<TaskSpotCheckTool> findByDeviceTypeList(TaskSpotCheckTool taskSpotCheckTool);

    /**
     * 查询工具, 返回为CheckRecord
     *
     * @MethodName: findDateCheckRecordList
     * @param taskSpotCheckTool
     * @Author huanggc
     * @Date 2020-05-12
     * @Return List<CheckRecord>
     **/
    List<CheckRecord> findDateCheckRecordList(TaskSpotCheckTool taskSpotCheckTool);

    /**
     * 查询工具
     *
     * @MethodName: findTaskSpotCheckToolList
     * @param taskSpotCheckTool
     * @Author huanggc
     * @Date 2020-05-22
     * @Return List<TaskSpotCheckTool>
     **/
    List<TaskSpotCheckTool> findTaskSpotCheckToolList(TaskSpotCheckTool taskSpotCheckTool);
}
