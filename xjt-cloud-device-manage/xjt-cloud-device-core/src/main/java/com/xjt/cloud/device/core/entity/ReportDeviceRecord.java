package com.xjt.cloud.device.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/10 10:12
 * @Description: 设备档案信息报表实体类
 */
public class ReportDeviceRecord extends BaseEntity {
    //标题
    private String title;
    //设备名称
    private String deviceName;
    //设备码
    private String qrNo;
    //巡查码
    private String pointQrNo;
    //设备位置
    private String pointLocation;
    //巡查次数
    private String checkNum;
    //异常次数
    private String failNum;
    private String registerNum;//登录次数
    //维修次数
    private String repairNum;
    //责任单位
    private String orgName;
    //送修期
    private String sendModifyTime;
    //有效期
    private String expiryDate;
    //所属项目
    private String projectName;
    //备注
    private String memo;
    //工单名称
    private String orderName;
    //操作结果
    private String operationResult;
    //类型
    private String type;
    private String[] files;
    //序号
    private Long rowNum;
    private Date endTime;//日期结束时间
    private Long buildingId;//建筑物id
    private Long checkPointId;//巡检点id
    private Long buildingFloorId;// 楼层id
    private String imgUrl;//设备图片
    private Integer countNum;
    private String faultType;//异常类型

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getFailNum() {
        return failNum;
    }

    public void setFailNum(String failNum) {
        this.failNum = failNum;
    }

    public String getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(String repairNum) {
        this.repairNum = repairNum;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSendModifyTime() {
        return sendModifyTime;
    }

    public void setSendModifyTime(String sendModifyTime) {
        this.sendModifyTime = sendModifyTime;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(String registerNum) {
        this.registerNum = registerNum;
    }

    public Date getEndTime() {
        if (endTime != null) {
            return DateUtils.add24Hours(endTime);
        }
        return null;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public String getCreateTimeDesc(){
        if (getCreateTime() != null){
            return DateUtils.formatDateTime(getCreateTime());
        }
        return null;
    }
}
