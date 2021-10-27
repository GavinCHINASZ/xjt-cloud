package com.xjt.cloud.task.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AppTask
 * @Author dwt
 * @Date 2019-08-15 9:58
 * @Description APP端任务实体
 * @Version 1.0
 */
public class AppTask extends BaseEntity {
    //任务id
    private Long taskId;
    //父任务id
    private Long parentTaskId;
    //项目id
    private Long projectId;
    //任务名称
    private String taskName;
    //项目名称
    private String projectName;
    //任务状态
    private Integer taskStatus;
    //是否审核0：不需要，1：需要
    private Integer review;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //执行人
    private List<String> executors;
    //设备数量
    private Integer deviceNum;
    //已检设备数量
    private Integer checkedNum;
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
    private Integer taskPeriodType;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        this.checkedNum = checkedNum;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

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

    public Integer getTaskPeriodType() {
        return taskPeriodType;
    }

    public void setTaskPeriodType(Integer taskPeriodType) {
        this.taskPeriodType = taskPeriodType;
    }

    public List<String> getExecutors() {
        return executors;
    }

    public void setExecutors(List<String> executors) {
        this.executors = executors;
    }
}
