package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.task.core.entity.task.Task;

/**
 * @ClassName TaskManageDetail
 * @Author dwt
 * @Date 2020-03-26 10:35
 * @Description 任务管理详情导出
 * @Version 1.0
 */
public class TaskManageDetail {
    //任务名称
    private String taskName;
    //任务周期
    private String taskPeriod;
    //任务状态
    private String taskStatus;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //任务结束提醒时间
    private String remindTime;
    //执行任务名字
    private String executorNames;
    //审核人名字
    private String reviewNames;
    //任务完成情况
    private String taskCompletion;
    //标题
    private String title;
    private Integer deviceNum;
    private Integer pointNum;
    private Integer checkedPointNum;
    private Integer faultNum;
    private String completeRate;
    private String faultRate;
    private String orgTypeDesc;


    public TaskManageDetail(){
    }
    public TaskManageDetail(Task task){
        this.setTaskName(task.getTaskName());
        this.setTaskPeriod("/");
        switch (task.getPeriodType()){
            case 0:
                switch (task.getTaskPeriodType()){
                    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周
                    case 0:
                        this.setTaskPeriod("每两年");
                        break;
                    case 1:
                        this.setTaskPeriod("每年");
                        break;
                    case 2:
                        this.setTaskPeriod("每半年");
                        break;
                    case 3:
                        this.setTaskPeriod("每季度");
                        break;
                    case 4:
                        this.setTaskPeriod("每两月");
                        break;
                    case 5:
                        this.setTaskPeriod("每月");
                        break;
                    case 6:
                        this.setTaskPeriod("每半月");
                        break;
                    case 7:
                        this.setTaskPeriod("每周");
                        break;
                }
                break;
            case 1:
                this.setTaskPeriod("一日一次");
                if(task.getCheckCount() ==1){
                    this.setTaskPeriod("一日多次");
                }
                break;
            case 2:
                this.setTaskPeriod("自定义");
                break;
        }
        //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
        switch (task.getTaskStatus()){
            case 0:
                this.setTaskStatus("未开始");
                break;
            case 1:
                this.setTaskStatus("执行中");
                break;
            case 2:
                this.setTaskStatus("已过期");
                break;
            case 3:
                this.setTaskStatus("已完成");
                break;
            case 4:
                this.setTaskStatus("待审核");
                break;
            case 5:
                this.setTaskStatus("已驳回");
                break;
            case 6:
                this.setTaskStatus("通过");
                break;
        }
        if(task.getPeriodStartTime() != null){
            this.setStartTime(DateUtils.getDateYearMonthDay(task.getPeriodStartTime()));
        }
        if(task.getPeriodEndTime() != null){
            this.setEndTime(DateUtils.getDateYearMonthDay(task.getPeriodEndTime()));
        }
        if(task.getRemindTime() != null){
            this.setRemindTime(DateUtils.getDayHourMin(task.getRemindTime()));
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPeriod() {
        return taskPeriod;
    }

    public void setTaskPeriod(String taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getExecutorNames() {
        return executorNames;
    }

    public void setExecutorNames(String executorNames) {
        this.executorNames = executorNames;
    }

    public String getReviewNames() {
        return reviewNames;
    }

    public void setReviewNames(String reviewNames) {
        this.reviewNames = reviewNames;
    }

    public String getTaskCompletion() {
        return taskCompletion;
    }

    public void setTaskCompletion(String taskCompletion) {
        this.taskCompletion = taskCompletion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getCheckedPointNum() {
        return checkedPointNum;
    }

    public void setCheckedPointNum(Integer checkedPointNum) {
        this.checkedPointNum = checkedPointNum;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public String getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(String completeRate) {
        this.completeRate = completeRate;
    }

    public String getFaultRate() {
        return faultRate;
    }

    public void setFaultRate(String faultRate) {
        this.faultRate = faultRate;
    }

    public String getOrgTypeDesc() {
        return orgTypeDesc;
    }

    public void setOrgTypeDesc(String orgTypeDesc) {
        this.orgTypeDesc = orgTypeDesc;
    }
}
