package com.xjt.cloud.task.core.entity;

/**
 * TaskExecutor 执行人, 审核人
 *
 * @Author dwt
 * @Date 2019-11-18 16:20
 * @Version 1.0
 */
public class TaskExecutor {
    private Long taskId;
    private Long projectId;
    private int executorType;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getExecutorType() {
        return executorType;
    }

    public void setExecutorType(int executorType) {
        this.executorType = executorType;
    }
}
