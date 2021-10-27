package com.xjt.cloud.task.core.entity.task;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.task.core.entity.TaskCheckPoint;
import com.xjt.cloud.task.core.entity.TaskSpotCheckTool;
import com.xjt.cloud.task.core.entity.User;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Task 任务
 *
 * @author dwt
 * @date 2019-07-24 16:38
 */
public class Task extends BaseEntity {
    private Long[] ids;
    //项目id
    private Long[] projectIds;

    //任务父id
    private Long parentId;

    //项目id
    private String projectName;

    //任务名称
    private String taskName;
    //备注
    private String remark;
    //任务类型 0巡查任务，1检查任务，2保养任务, 3抽检任务,4日常巡检
    private Integer taskType;
    //周期类型 0周期任务，1日常任务，2自定义任务
    private Integer periodType;
    //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:日任务（checkCount 0：一日一次，1：一日多次）
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
    //任务状态 0未开始，1执行中，2已过期，3已完成,4:子任务-（审核中）待审核,5:子任务-已驳回6:子任务-通过
    private Integer taskStatus;
    //类型任务：0：父任务，1：子任务
    private Integer typeTask;
    //地铁部门类型：0默认没有部门，1站务部门，2机电部门
    private Integer orgType;

    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //查询时间类型 year/month/day/   例：2019/2019-8/2019-9-1
    private String dateType;

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
    //巡检数
    private Integer checkedNum;
    //完成数
    private Integer completedNum;
    //审核意见
    private String reviewOpinion;
    private String checkPointName;// 巡查点名称
    private String checkPointQrNo;// 巡查点ID
    private String[] checkPointNames;// 巡查点名称
    //巡查点未检数量
    private Integer noCheckedCount;
    //巡查点故障数量
    private Integer faultCount;
    //巡查点正常数量
    private Integer normalCount;
    //巡查点已检数量
    private Integer checkedCount;
    //巡查点故障率
    private Double faultRate;
    //巡查点正常率;
    private Double checkedRate;

    private Integer totalCount;

    private Integer delete;
    private Integer notStartTask;
    private Boolean isAll;
    private List<String> taskExecutorsName;//任务执行人
    private List<String> taskReviewName;//任务审核人
    private Long userId;
    private String name;//搜索框模糊搜索
    private Long buildingId;//建筑物ID
    private Long buildingFloorId;//建筑物楼层ID
    //任务设备状态   0、正常   1、故障  2、未检(查询:任务详情--设备--巡检状态 -1未检)
    private Integer deviceStatus;
    private Integer checkPointNum;
    //任务巡查点状态  0、未检  1、故障   2、正常
    private Integer taskCheckPointStatus;
    //编辑类型：1:新增, 2:编辑
    private Integer editType;
    private Date editStartTime;
    //是否发送过期提醒：0：未提醒，1：已提醒
    private Integer isRemind;

    private Integer deviceNum;// 设备数
    private Integer faultDeviceNum;// 故障设备数

    //当前用户是否能审核并且有审核权限  0、没有  1、有
    private Integer isReview;

    private Integer rowCount;
    private String queryDate;// 查询时间: 年月

    private String appId;

    private String executorsName; //执行人名称拼接

    private Long lastModifyUserId;
    //任务检测工具
    private List<TaskSpotCheckTool> taskSpotCheckTools;

    //任务巡查点
    private List<TaskCheckPoint> taskCheckPoints;

    private List<Task> taskList;

    //是否巡检 1：已检，2：未检
    private Integer checkState;
    private Integer moduleIndex;
    /**
     * 图片
     */
    private List<String> imageUrls;

    public Integer getModuleIndex() {
        return moduleIndex;
    }

    public void setModuleIndex(Integer moduleIndex) {
        this.moduleIndex = moduleIndex;
    }

    public List<TaskSpotCheckTool> getTaskSpotCheckTools() {
        return taskSpotCheckTools;
    }

    public void setTaskSpotCheckTools(List<TaskSpotCheckTool> taskSpotCheckTools) {
        this.taskSpotCheckTools = taskSpotCheckTools;
    }

    public List<TaskCheckPoint> getTaskCheckPoints() {
        return taskCheckPoints;
    }

    public void setTaskCheckPoints(List<TaskCheckPoint> taskCheckPoints) {
        this.taskCheckPoints = taskCheckPoints;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    private Integer downType;// 1 doc,  2 excel,  3 pdf
    public Task(){

    }

    public String getExecutorsName() {
        return executorsName;
    }

    public void setExecutorsName(String executorsName) {
        this.executorsName = executorsName;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public List<String> getTaskReviewName() {
        return taskReviewName;
    }

    public void setTaskReviewName(List<String> taskReviewName) {
        this.taskReviewName = taskReviewName;
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

    public Integer getTaskCheckPointStatus() {
        return taskCheckPointStatus;
    }

    public void setTaskCheckPointStatus(Integer taskCheckPointStatus) {
        this.taskCheckPointStatus = taskCheckPointStatus;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Integer getIsReview() {
        return isReview;
    }

    public void setIsReview(Integer isReview) {
        this.isReview = isReview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCheckPointNum() {
        return checkPointNum;
    }

    public void setCheckPointNum(Integer checkPointNum) {
        this.checkPointNum = checkPointNum;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String[] getCheckPointNames() {
        return checkPointNames;
    }

    public void setCheckPointNames(String[] checkPointNames) {
        this.checkPointNames = checkPointNames;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public Integer getNoCheckedCount() {
        return noCheckedCount;
    }

    public void setNoCheckedCount(Integer noCheckedCount) {
        this.noCheckedCount = noCheckedCount;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getNormalCount() {
        if (null == normalCount){
            return 0;
        }
        return normalCount;
    }

    public void setNormalCount(Integer normalCount) {
        this.normalCount = normalCount;
    }

    public Integer getCheckedCount() {
        return checkedCount;
    }

    public void setCheckedCount(Integer checkedCount) {
        this.checkedCount = checkedCount;
    }

    public Double getFaultRate() {
        return faultRate;
    }

    public void setFaultRate(Double faultRate) {
        this.faultRate = faultRate;
    }

    public Double getCheckedRate() {
        return checkedRate;
    }

    public void setCheckedRate(Double checkedRate) {
        this.checkedRate = checkedRate;
    }

    public Long[] getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Long[] projectIds) {
        this.projectIds = projectIds;
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
        String taskTypes = "巡查";
        if (taskType != null){
            switch (taskType){
                case 1 :
                    taskTypes = "检查";
                    break;
                case 2 :
                    taskTypes = "保养";
                    break;
                case 3 :
                    taskTypes = "抽查";
                    break;
                default:
                    taskTypes = "巡查";
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
        String periodTypeDesc  = "周期";
        //周期类型 0周期任务，1日常任务，2自定义任务
        if (periodType != null){
            switch (this.periodType){
                case 1 :
                    periodTypeDesc = "日常";
                    break;
                case 2 :
                    periodTypeDesc = "自定义";
                    break;
                default:
                    periodTypeDesc = "周期";
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
        if (null == periodStartTime || null == periodEndTime){
            return "/";
        }
        if(this.taskPeriodType != null && this.taskPeriodType == 8 && this.checkCount!=null &&this.checkCount == 1 ){
            sdf = new SimpleDateFormat( " yyyy年MM月dd日 HH:mm" );
            return sdf.format(getCheckStartTime()) + "-" + sdf.format(getCheckEndTime()) + "截止";
        }else{
            return sdf.format(getPeriodStartTime()) + "-" + sdf.format(getPeriodEndTime()) + "截止";

        }
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

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public String getTaskStatusDesc() {
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
                    if (null != typeTask && typeTask == 0){
                        taskStatusDesc = "待审核";
                    }else {
                        taskStatusDesc = "审核中";
                    }
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
        String periodTypeDesc = "周期";
        //周期类型 0周期任务，1日常任务，2自定义任务
        if (periodType != null){
            switch (this.periodType){
                case 1 :
                    periodTypeDesc = "日常";
                    break;
                case 2 :
                    periodTypeDesc = "自定义";
                    break;
                default:
                    periodTypeDesc = "周期";
                    break;
            }
        }
        //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
        //任务周期类型 0:每两年，1:每年，2:每半年，3:每季度，4:每两月，5:每月，6:每半月，7:每周，8:一日一次
        String taskPeriodTypeDesc = periodTypeDesc;
        if (taskPeriodType != null && this.taskType != null && this.taskType != 2){
            switch (taskPeriodType){
                case 1 :
                    taskPeriodTypeDesc = periodTypeDesc + "•每年";
                    break;
                case 2 :
                    taskPeriodTypeDesc = periodTypeDesc + "•每半年";
                    break;
                case 3 :
                    taskPeriodTypeDesc = periodTypeDesc + "•每季度";
                    break;
                case 4 :
                    taskPeriodTypeDesc = periodTypeDesc+ "•每两月";
                    break;
                case 5 :
                    taskPeriodTypeDesc = periodTypeDesc+ "•每月";
                    break;
                case 6 :
                    taskPeriodTypeDesc = periodTypeDesc + "•每半月";
                    break;
                case 7 :
                    taskPeriodTypeDesc = periodTypeDesc + "•每周";
                    break;
                case 8 :
                    taskPeriodTypeDesc = "一日一次";
                    if(this.checkCount ==1){
                        taskPeriodTypeDesc = "一日多次";
                    }
                    break;
                default:
                    taskPeriodTypeDesc = periodTypeDesc + "•每两年";
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
        if (null == taskNum){
            return 0;
        }
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
        if (null == faultNum){
            return 0;
        }
        return faultNum;
    }

    // 故障率
    public String getFaultNumDesc() {
        String faultNumDesc = getPercentageStr(faultNum,checkPointNum);
        return faultNumDesc;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getCompletedNum() {
        if (null == completedNum){
            return 0;
        }
        return completedNum;
    }

    // 完成率
    public String getCompletedNumDesc() {
        String completedNumDesc = getPercentageStr(checkedNum,checkPointNum);
        return completedNumDesc;
    }
    public String getPercentageStr(Integer num1,Integer num2){
        String str = "0%";
        if (num1 != null && num1 > 0 && num2 != null && num2 > 0){
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMinimumFractionDigits(4);
            str = numberFormat.format((Float.parseFloat(numberFormat.format((float)num1 /(float)num2))) * 100);
            if("100.0000".equals(str)){
                str = "100%";
            }else{
                str = str.substring(0, str.indexOf(".") + 3) + "%";
            }
        }
        return str;
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

    // 任务完成情况
    public String getTaskCompleteDesc() {
        // 共100个任务,已完成56,剩余44
        StringBuffer sb = new StringBuffer();
        sb.append("共" + totalCount + "个任务,已完成");
        if (completedNum != null){// 完成数
            sb.append(completedNum);
            if (totalCount != null && totalCount >= 0){
                sb.append(",剩余" + (totalCount - completedNum));
            }
        }
        return sb.toString();
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

    public Integer getEditType() {
        return editType;
    }

    public void setEditType(Integer editType) {
        this.editType = editType;
    }

    public Date getEditStartTime() {
        return editStartTime;
    }

    public void setEditStartTime(Date editStartTime) {
        this.editStartTime = editStartTime;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }

    public Integer getCheckedNum() {
        if (null == checkedNum){
            return 0;
        }
        return checkedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        this.checkedNum = checkedNum;
    }

    // PC月度工单统计 任务概览表 已检巡查点ID数
    public Integer getCheckCompletedNumDesc() {
        if (null == normalCount){
            normalCount = 0;
        }

        if (null == faultCount){
            faultCount = 0;
        }
        return normalCount + faultCount;
    }

    // PC月度工单统计 任务概览表 完成率
    public String getCheckCompletedDesc() {
        Integer completeNum = 0;// 完成数
        if (checkedCount != null){
            completeNum = checkedCount;
        }
        /*if (null != normalCount){
            completeNum += normalCount;
        }*/

        return getPercentageStr(completeNum, checkPointNum);
    }

    // PC月度工单统计 任务概览表--故障率
    public String getCheckFaultNumDesc() {
        return getPercentageStr(faultCount, checkPointNum);
    }

    // PC月度工单统计 任务概览表 任务状态
    public String getTaskOverviewStatusDesc() {
        if (taskStatus != null){
            if (taskStatus == 2 || taskStatus == 3){
                return "已完成";
            }
        }
        return "未完成";
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Integer getFaultDeviceNum() {
        return faultDeviceNum;
    }

    public void setFaultDeviceNum(Integer faultDeviceNum) {
        this.faultDeviceNum = faultDeviceNum;
    }


    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }

    public Long getLastModifyUserId() {
        return lastModifyUserId;
    }

    public void setLastModifyUserId(Long lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    public String getCreateTimeDesc(){
        return DateUtils.formatDateTime(getCreateTime()) + " ";
    }

    //地铁部门类型：0默认没有部门，1站务部门，2机电部门
    public String getOrgTypeDesc(){
        if (orgType == null){
            return "";
        }

        if (orgType == 1){
            return "站务部门";
        }else if(orgType == 2){
            return "机电部门";
        }
        return "";
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }
}
