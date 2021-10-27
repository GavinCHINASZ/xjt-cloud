package com.xjt.cloud.report.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;

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
public class Task extends BaseEntity {
    //项目id
    private Long projectId;
    //任务父id
    private Long parentId;
    //项目id
    private String projectName;
    //任务名称
    private String taskName;
    //备注
    private String remark;
    //任务类型 0巡查任务，1检查任务，2保养任务
    private Integer taskType;
    //周期类型 0周期任务，1日常任务，2自定义任务
    private Integer periodType;
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
    private Integer taskPeriodType;
    //任务开始时间
    private Date periodStartTime;
    //任务结束时间
    private Date periodEndTime;
    //任务结束提醒时间
    private Long remindTime;
    //巡检次数 0：一日一次，1：一日多次
    private Integer checkCount;
    //日常巡检开始时间
    private Date checkStartTime;
    //日常巡检结束时间
    private Date checkEndTime;
    //间隔时间
    private Integer intervalTime;
    //是否需要审核 0：不需要，1需要
    private Integer review;
    //设备数
    private Integer deviceNumber;
    //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
    private Integer taskStatus;
    //类型任务：0：父任务，1：子任务
    private Integer typeTask;


    //巡更点id数组
    private Long[] checkPointIds;
    //任务状态数组 0未启动，1待执行，2已过期，3已完成,4:子任务-待审核
    private Integer[] taskStatusArr;
    //周期类型数组 0周期任务，1日常任务，2自定义任务
    private Integer[] periodTypeArr;
    //任务类型数组 0巡查任务，1检查任务，2保养任务
    private Integer[] taskTypeArr;
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
    private Integer[] taskPeriodTypeArr;
    //执行角色
    private List<User> executorUserList;
    //审核角色
    private List<User> reviewUserList;
    //任务总数
    private Integer taskNum;
    //已完任务
    private Integer completedTask;
    //故障数
    private Integer faultNum;
    //完成数
    private Integer completedNum;
    //审核意见
    private String reviewOpinion;
    //设备数
    private Integer deviceNum;
    private Integer totalCount;
    private Long[] ids;
    private Integer delete;
    private Integer notStartTask;


    public Task(){

    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTaskType() {
        return taskType;
    }

    //任务类型 0巡查任务，1检查任务，2保养任务
    public String getTaskTypeDesc() {
        String taskTypes = "巡查任务";
        if (taskType != null){
            switch (taskType){
                case 1 :
                    taskTypes = "检查任务";
                    break;
                case 2 :
                    taskTypes = "保养任务";
                    break;
                default:
                    taskTypes = "巡查任务";
                    break;
            }
        }
        return taskTypes;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public String getPeriodTypeDesc() {
        String periodTypeDesc  = "周期任务";
        //周期类型 0周期任务，1日常任务，2自定义任务
        if (periodType != null){
            switch (this.periodType){
                case 1 :
                    periodTypeDesc = "日常任务";
                    break;
                case 2 :
                    periodTypeDesc = "自定义任务";
                    break;
                default:
                    periodTypeDesc = "周期任务";
                    break;
            }
        }

        return periodTypeDesc;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Date getPeriodStartTime() {
        return periodStartTime;
    }

    SimpleDateFormat sdf = new SimpleDateFormat( " yyyy年MM月dd日" );
    public String getPeriodStartTimeDesc() {
        String periodStartTimeDesc = sdf.format(getPeriodStartTime()) + "-" + sdf.format(getPeriodEndTime()) + "截止";
        return periodStartTimeDesc;
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

    public Long getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Long remindTime) {
        this.remindTime = remindTime;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public String getTaskStatusDesc() {
        //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-待审核,5:子任务-已驳回6:子任务-通过
        String taskStatusDesc = "未开始";
        if (taskStatus != null){
            switch (taskStatus){
                case 1 :
                    taskStatusDesc = "执行中";
                    break;
                case 2 :
                    taskStatusDesc = "已过期";
                    break;
                case 3 :
                    taskStatusDesc = "已完成";
                    break;
                case 4 :
                    taskStatusDesc = "待审核";
                    break;
                case 5 :
                    taskStatusDesc = "已驳回";
                    break;
                case 6 :
                    taskStatusDesc = "通过";
                    break;
                default:
                    taskStatusDesc = "未开始";
                    break;
            }
        }
        return taskStatusDesc;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(Integer typeTask) {
        this.typeTask = typeTask;
    }

    public Integer getTaskPeriodType() {
        return taskPeriodType;
    }

    public String getTaskPeriodTypeDesc() {
        //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
        String taskPeriodTypeDesc = "每两年";
        if (taskPeriodType != null){
            switch (taskPeriodType){
                case 1 :
                    taskPeriodTypeDesc = "每年";
                    break;
                case 2 :
                    taskPeriodTypeDesc = "每半年";
                    break;
                case 3 :
                    taskPeriodTypeDesc = "每季度";
                    break;
                case 4 :
                    taskPeriodTypeDesc = "每两月";
                    break;
                case 5 :
                    taskPeriodTypeDesc = "每月";
                    break;
                case 6 :
                    taskPeriodTypeDesc = "每半月";
                    break;
                case 7 :
                    taskPeriodTypeDesc = "每周";
                    break;
                case 8 :
                    taskPeriodTypeDesc = "一日一次";
                    break;
                default:
                    taskPeriodTypeDesc = "每两年";
                    break;
            }
        }

        return taskPeriodTypeDesc;
    }

    public void setTaskPeriodType(Integer taskPeriodType) {
        this.taskPeriodType = taskPeriodType;
    }

    public Long[] getCheckPointIds() {
        return checkPointIds;
    }

    public void setCheckPointIds(Long[] checkPointIds) {
        this.checkPointIds = checkPointIds;
    }

    public Integer[] getTaskStatusArr() {
        return taskStatusArr;
    }

    public void setTaskStatusArr(Integer[] taskStatusArr) {
        this.taskStatusArr = taskStatusArr;
    }

    public Integer[] getPeriodTypeArr() {
        return periodTypeArr;
    }

    public void setPeriodTypeArr(Integer[] periodTypeArr) {
        this.periodTypeArr = periodTypeArr;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Integer getCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(Integer completedTask) {
        this.completedTask = completedTask;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    // 故障率
    public String getFaultNumDesc() {
        String faultNumDesc = "0%";
        if (faultNum != null && faultNum > 0 && deviceNum != null && deviceNum >0){
            faultNumDesc = (faultNum / deviceNum) + "%";
        }
        return faultNumDesc;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getCompletedNum() {
        return completedNum;
    }

    // 完成率
    public String getCompletedNumDesc() {
        String completedNumDesc = "0%";
        if (completedNum != null && completedNum > 0 && deviceNum != null && deviceNum > 0){
            completedNumDesc = (completedNum / deviceNum) + "%";
        }

        return completedNumDesc;
    }

    public void setCompletedNum(Integer completedNum) {
        this.completedNum = completedNum;
    }

    public Integer[] getTaskTypeArr() {
        return taskTypeArr;
    }

    public void setTaskTypeArr(Integer[] taskTypeArr) {
        this.taskTypeArr = taskTypeArr;
    }

    public String getReviewOpinion() {
        return reviewOpinion;
    }

    public void setReviewOpinion(String reviewOpinion) {
        this.reviewOpinion = reviewOpinion;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer[] getTaskPeriodTypeArr() {
        return taskPeriodTypeArr;
    }

    public void setTaskPeriodTypeArr(Integer[] taskPeriodTypeArr) {
        this.taskPeriodTypeArr = taskPeriodTypeArr;
    }

    public List<User> getExecutorUserList() {
        return executorUserList;
    }

    public void setExecutorUserList(List<User> executorUserList) {
        this.executorUserList = executorUserList;
    }

    public List<User> getReviewUserList() {
        return reviewUserList;
    }

    public void setReviewUserList(List<User> reviewUserList) {
        this.reviewUserList = reviewUserList;
    }


    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public Integer getNotStartTask() {
        return notStartTask;
    }

    public void setNotStartTask(Integer notStartTask) {
        this.notStartTask = notStartTask;
    }
}
