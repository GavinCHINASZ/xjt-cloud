package com.xjt.cloud.client.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Task 任务
 * @Author dwt
 * @Date 2019-07-24 16:38
 * @Description 巡检任务
 * @Version 1.0
 */
public class Task {
    private Date createTime;
    private String appId;
    //任务名称
    private String taskName;
    //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
    private Integer taskStatus;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
}
