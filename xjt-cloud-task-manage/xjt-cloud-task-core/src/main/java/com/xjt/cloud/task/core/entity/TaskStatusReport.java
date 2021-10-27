package com.xjt.cloud.task.core.entity;

/**
 * @ClassName TaskStatusReport
 * @Author dwt
 * @Date 2019-11-05 17:56
 * @Version 1.0
 */
public class TaskStatusReport {
    private Integer taskCount;// 总数
    private Integer executing;// 执行中
    private Integer notStarted;// 未开始
    private Integer toBeAudit;// 待审核
    private Integer completed;// 已完成
    private Integer noComplete;// 未完成
    private Integer expired;// 已过期
    private Integer cycleCount;//周期任务数量
    private Integer dailyCount;//日常任务数量
    private Integer customizeCount;//自定义任务数量
    //任务类型 0巡查任务，1检查任务，2保养任务, 3抽检任务,4日常巡检
    private Integer taskType;

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(Integer cycleCount) {
        this.cycleCount = cycleCount;
    }

    public Integer getDailyCount() {
        return dailyCount;
    }

    public void setDailyCount(Integer dailyCount) {
        this.dailyCount = dailyCount;
    }

    public Integer getCustomizeCount() {
        return customizeCount;
    }

    public void setCustomizeCount(Integer customizeCount) {
        this.customizeCount = customizeCount;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getExecuting() {
        return executing;
    }

    public void setExecuting(Integer executing) {
        this.executing = executing;
    }

    public Integer getNotStarted() {
        return notStarted;
    }

    public void setNotStarted(Integer notStarted) {
        this.notStarted = notStarted;
    }

    public Integer getToBeAudit() {
        return toBeAudit;
    }

    public void setToBeAudit(Integer toBeAudit) {
        this.toBeAudit = toBeAudit;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getNoComplete() {
        return noComplete;
    }

    public void setNoComplete(Integer noComplete) {
        this.noComplete = noComplete;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }
}
