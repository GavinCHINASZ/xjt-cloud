package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Executer 执行者
 * @Author dwt
 * @Date 2019-07-25 9:23
 * @Description:执行人和审核人 的实体
 * @Version 1.0
 */
public class Executor extends BaseEntity {
    //任务id
    private Long taskId;
    //执行者类型 0：执行人，1：审核人
    private Integer executorType;
    //执行者id
    private Long executorId;
    //执行者名称
    private String executorName;
    //项目id
    private Long projectId;

    //角色id
    private Long roleId;
    private Long userId;

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public Integer getExecutorType() {
        return executorType;
    }

    public void setExecutorType(Integer executorType) {
        this.executorType = executorType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
