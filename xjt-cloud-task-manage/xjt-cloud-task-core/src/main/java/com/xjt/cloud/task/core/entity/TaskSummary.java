package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 月任务汇总
 *
 * @Author huanggc
 * @Date 2019-11-25
 */
public class TaskSummary extends BaseEntity {
    private Long taskId;// 任务ID
    private String taskName;// 任务名称

    private Long projectId;// 项目id
    private Long parentId;// 任务父id
    private String projectName;// 项目名称

    private String remark;// 备注
    private Integer taskType;// 任务类型 0巡查任务，1检查任务，2保养任务
    private Integer periodType;//周期类型 0周期任务，1日常任务，2自定义任务
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:日任务（checkCount 0：一日一次，1：一日多次）
    private Integer taskPeriodType;
    private Date periodStartTime;// 任务开始时间
    private Date periodEndTime;// 任务结束时间
    private Long remindTime;// 任务结束提醒时间
    private Integer checkCount;// 巡检次数 0：一日一次，1：一日多次
    private Date checkStartTime;// 日常巡检开始时间
    private Date checkEndTime;// 日常巡检结束时间
    private Integer intervalTime;// 间隔时间

    private Integer review;// 是否需要审核 0：不需要，1需要
    private Integer deviceNumber;// 设备数
    private Integer taskStatus;// 任务状态 0未开始，1执行中，2已过期，3已完成, 4:子任务-（审核中）待审核, 5:子任务-已驳回 6:子任务-通过
    private Integer typeTask;// 类型任务：0：父任务，1：子任务

    // 以下字段不在数据库中
    //巡更点id数组
    private Long[] checkPointIds;
    //任务状态数组 0未启动，1待执行，2已过期，3已完成, 4:子任务-待审核
    private Integer[] taskStatusArr;
    //周期类型数组 0周期任务，1日常任务，2自定义任务
    private Integer[] periodTypeArr;
    //任务类型数组 0巡查任务，1检查任务，2保养任务
    private Integer[] taskTypeArr;
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
    private Integer[] taskPeriodTypeArr;
    private List<User> executorUserList;// 执行角色
    private List<User> reviewUserList;// 审核角色
    private Integer taskNum;// 任务总数

    private Integer completedTask;// 已完任务
    private Integer checkPointNum;// 巡查点ID总数
    private String pointName;// 巡查点名称
    private Integer checkPointNormalNum;// 巡查点 正常数
    private Integer checkPointFaultNum;// 巡查点 故障数

    private Integer expired;// 已过期
    private Integer completedNum;//完成数

    private String reviewOpinion;//审核意见

    private Integer totalCount;
    private Long[] ids;
    private Integer delete;
    private Integer notStartTask;
    private Boolean isAll;
    private List<String> taskExecutorsName;
    private Long userId;

    private Integer downType;// 1任务管理导出详情, 2任务工单导出详情, 3月任务汇总--任务概览表导出详情

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TaskSummary(){

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

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Date getPeriodStartTime() {
        return periodStartTime;
    }

    /*SimpleDateFormat sdf = new SimpleDateFormat( " yyyy年MM月dd日" );
    public String getPeriodStartTimeDesc() {
        String periodStartTimeDesc = sdf.format(getPeriodStartTime()) + "-" + sdf.format(getPeriodEndTime()) + "截止";
        return periodStartTimeDesc;
    }*/

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

    // 月任务汇总 任务概览表 任务状态
    public String getTaskOverviewStatusDesc() {
        // PC月任务汇总--任务概览表导出列表,除了"已完成",其它是"已过期"
        if (taskStatus != null && taskStatus == 3){
            return "已完成";
        }
        return "已过期";
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
        if (null != taskPeriodType){
            if (taskPeriodType == 0) {
                return "每两年";
            } else if (taskPeriodType == 1) {
                return "每年";
            } else if (taskPeriodType == 2) {
                return "每半年";
            } else if (taskPeriodType == 3) {
                return "每季度";
            } else if (taskPeriodType == 4) {
                return "每两月";
            } else if (taskPeriodType == 5) {
                return "每月";
            } else if (taskPeriodType == 6) {
                return "每半月";
            } else if (taskPeriodType == 7) {
                return "每周";
            } else if (taskPeriodType == 8) {
                if (null != checkCount){
                    if (checkCount == 0){
                        return "一日一次";
                    }else if (checkCount == 1){
                        return "一日多次";
                    }
                }
            }
        }
        return "/";
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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getCheckPointFaultNum() {
        return checkPointFaultNum;
    }

    public void setCheckPointFaultNum(Integer checkPointFaultNum) {
        this.checkPointFaultNum = checkPointFaultNum;
    }

    // 月任务汇总 任务概览表--故障率
    public String getCheckFaultNumDesc() {
        if (checkPointFaultNum != null && checkPointFaultNum > 0 && checkPointNum != null && checkPointNum >0){
            return (checkPointFaultNum / checkPointNum) + "%";
        }
        return "0%";
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getCompletedNum() {
        return completedNum;
    }

    // 月任务汇总 任务概览表 完成率
    public String getCheckCompletedDesc() {
        if (checkPointNum != null && checkPointNum > 0){
            Integer completeNum = 0;// 完成数
            if (checkPointNormalNum != null){
                completeNum = checkPointNormalNum;
            }
            if (null != checkPointFaultNum){
                completeNum += checkPointFaultNum;
            }
            if (completeNum > 0){
                return (completeNum / checkPointNum) + "%";
            }
        }

        return "0%";
    }

    public void setCompletedNum(Integer completedNum) {
        this.completedNum = completedNum;
    }

    // 月任务汇总 任务概览表 完成数(已检巡查点ID数)
    public Integer getCheckCompletedNumDesc() {
        Integer completeNum = 0;
        if (checkPointNormalNum != null){
            completeNum = checkPointNormalNum;
        }
        if (null != checkPointFaultNum){
            completeNum += checkPointFaultNum;
        }
        return completeNum;
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

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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

    public Boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(Boolean all) {
        isAll = all;
    }

    public List<String> getTaskExecutorsName() {
        return taskExecutorsName;
    }

    public void setTaskExecutorsName(List<String> taskExecutorsName) {
        this.taskExecutorsName = taskExecutorsName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCheckPointNum() {
        return checkPointNum;
    }

    public void setCheckPointNum(Integer checkPointNum) {
        this.checkPointNum = checkPointNum;
    }


    public Integer getCheckPointNormalNum() {
        return checkPointNormalNum;
    }

    public void setCheckPointNormalNum(Integer checkPointNormalNum) {
        this.checkPointNormalNum = checkPointNormalNum;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }
}
