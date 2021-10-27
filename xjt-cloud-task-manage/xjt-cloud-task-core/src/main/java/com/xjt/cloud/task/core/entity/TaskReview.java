package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TaskReview
 * @Author dwt
 * @Date 2019-07-25 15:36
 * @Description 任务审核
 * @Version 1.0
 */
public class TaskReview extends BaseEntity implements Serializable {
    //任务id
    private Long taskId;
    private Long taskParentId;
    //项目id
    private Long projectId;
    //审核人id
    private Long reviewId;
    //审核人名称
    private String reviewName;
    //审核人意见
    private String reviewOpinion;
    //审核结果
    private String reviewResult;
    private Date createTime;
    // 1:提交，2,：驳回，3：通过
    //1：完成，2，驳回，3：已过期
    private Integer reviewStatus;
    //执行角色
    private List<User> executorUserList;
    //任务开始时间
    private Date periodStartTime;
    //任务结束时间
    private Date periodEndTime;
    private Long[] taskIds;
    //任务执行时间
    private String taskTime;
    //任务执行人
    private String taskExecutors;
    //任务执行时间数组
    private String[] taskTimeArr;
    //任务执行人数组
    private String[] taskExecutorsArr;

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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion;
    }

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public List<User> getExecutorUserList() {
        return executorUserList;
    }

    public void setExecutorUserList(List<User> executorUserList) {
        this.executorUserList = executorUserList;
    }

    public Date getPeriodStartTime() {
        return periodStartTime;
    }

    public void setPeriodStartTime(Date periodStartTime) {
        this.periodStartTime = periodStartTime;
    }

    public Date getPeriodEndTime() {
        return periodEndTime;
    }

    public void setPeriodEndTime(Date periodEndTime) {
        this.periodEndTime = periodEndTime;
    }

    public Long getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(Long taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long[] taskIds) {
        this.taskIds = taskIds;
    }

    public String getTaskExecutors() {
        return taskExecutors;
    }

    public void setTaskExecutors(String taskExecutors) {
        this.taskExecutors = taskExecutors;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String[] getTaskTimeArr() {
        return taskTimeArr;
    }

    public void setTaskTimeArr(String[] taskTimeArr) {
        this.taskTimeArr = taskTimeArr;
    }

    public String[] getTaskExecutorsArr() {
        return taskExecutorsArr;
    }

    public void setTaskExecutorsArr(String[] taskExecutorsArr) {
        this.taskExecutorsArr = taskExecutorsArr;
    }
}
