package com.xjt.cloud.task.core.service.service;

import com.xjt.cloud.task.core.entity.task.Task;

/**
 *@ClassName TaskSendMessageService
 *@Author dwt
 *@Date 2019-12-17 11:05
 *@Version 1.0
 */
public interface TaskSendMessageService {
    /**
     *@Author: dwt
     *@Date: 2019-12-17 11:05
     *@Param: com.xjt.cloud.task.core.entity.task.Task,java.lang.int
     *@Return: void
     *@Description 任务消息推送接口
     */
    void sendTaskMessage(Task task, int type);
}
