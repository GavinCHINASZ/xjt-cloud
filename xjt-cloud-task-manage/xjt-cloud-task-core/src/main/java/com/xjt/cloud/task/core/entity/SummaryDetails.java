package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 月任务汇总--任务详情
 *
 * @Author huanggc
 * @Date 2019-11-27
 */
public class SummaryDetails extends BaseEntity {
    private Long taskId;// 任务ID
    private String taskName;//任务名称

    private String projectName;//项目名称

    private Long deviceId;//设备id
    private String deviceQrNo;//设备码
    private String deviceName;//设备名称
    private String[] deviceNameArr;//设备名称

    private Long checkPointId;// 巡检点ID(巡查, 巡更)
    private String checkPointQrNo;// 巡查点码
    private String checkPointName;// 巡查点名称

    private Long buildingId;// 建筑物ID
    private String buildingName;// 建筑物名称

    private Long floorId;// 楼层ID
    private String floorName;// 楼层名称
    private Integer floor;// 楼层

    private Integer checkStatus;// 任务巡查点状态: 0未检  1故障  2正常;(来自 task_check_point 表中)

    /**
     * 以下字段不在数据库中
     */
    private Long[] taskIdArr;
    private Date periodStartTime;//任务开始时间
    private Date periodEndTime;//任务结束时间
    private Long[] ids;
    private Integer checkPointNum;// 巡查点ID总数
    private Integer checkPointNormalNum;// 巡查点 正常数
    private Integer checkPointFaultNum;// 巡查点 故障数
    private Integer dateType;// 默认0显示当月, 1上月同期, 2去年同期

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long[] getTaskIdArr() {
        return taskIdArr;
    }

    public void setTaskIdArr(Long[] taskIdArr) {
        this.taskIdArr = taskIdArr;
    }

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
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

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getCheckPointNum() {
        return checkPointNum;
    }

    public void setCheckPointNum(Integer checkPointNum) {
        this.checkPointNum = checkPointNum;
    }

    /**
     * 设备点检概览表 已检数
     */
    public Integer getCheckCompletedNumDesc() {
        Integer completeNum = 0;
        if (checkPointNormalNum != null && checkPointNormalNum > 0){
            completeNum = checkPointNormalNum;
        }
        if (checkPointFaultNum != null && checkPointFaultNum > 0){
            completeNum += checkPointFaultNum;
        }
        return completeNum;
    }

    /**
     * 设备点检概览表 完成率
     */
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

    /**
     * 月任务汇总 任务概览表--故障率
     */
    public String getCheckFaultNumDesc() {
        if (checkPointFaultNum != null && checkPointFaultNum > 0 && checkPointNum != null && checkPointNum >0){
            return (checkPointFaultNum / checkPointNum) + "%";
        }
        return "0%";
    }

    public Integer getCheckPointNormalNum() {
        return checkPointNormalNum;
    }

    public void setCheckPointNormalNum(Integer checkPointNormalNum) {
        this.checkPointNormalNum = checkPointNormalNum;
    }

    public Integer getCheckPointFaultNum() {
        return checkPointFaultNum;
    }

    public void setCheckPointFaultNum(Integer checkPointFaultNum) {
        this.checkPointFaultNum = checkPointFaultNum;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String[] getDeviceNameArr() {
        return deviceNameArr;
    }

    public void setDeviceNameArr(String[] deviceNameArr) {
        this.deviceNameArr = deviceNameArr;
    }
}
