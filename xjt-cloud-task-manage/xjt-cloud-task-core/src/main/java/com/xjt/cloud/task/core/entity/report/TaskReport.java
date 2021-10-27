package com.xjt.cloud.task.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

/**
 * 报表实体类(数据库中无表)
 *
 * @author huanggc
 * @date 2020/12/01
 */
public class TaskReport extends BaseEntity {
    private Integer totalCount = 0;
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 任务中设备数量
     */
    private Integer taskDeviceNum;

    /**
     * 故障数量
     */
    private Integer faultNum = 0;

    /**
     * 正常数量
     */
    private Integer normalNum = 0;
    /**
     * 修复数
     */
    private Integer repairNum = 0;

    /**
     * 故障类型
     */
    private String deviceFaultType;
    private Long deviceFaultTypeId;

    /**
     * 原因分析
     */
    private String causeAnalysis;

    /**
     * 维护建议
     */
    private String repairProposal;
    /**
     * 故障颜色
     */
    private String faultColor;
    private String columnarImg;
    private String pieImg;

    /**
     * 巡检结果0：正常，1：故障
     */
    private Integer checkResult;

    public Integer getTaskDeviceNum() {
        if (taskDeviceNum == null){
            return 0;
        }
        return taskDeviceNum;
    }

    public void setTaskDeviceNum(Integer taskDeviceNum) {
        this.taskDeviceNum = taskDeviceNum;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(Integer faultNum) {
        this.faultNum = faultNum;
    }

    public Integer getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(Integer normalNum) {
        this.normalNum = normalNum;
    }

    public Integer getRepairNum() {
        return repairNum;
    }

    public void setRepairNum(Integer repairNum) {
        this.repairNum = repairNum;
    }

    public String getDeviceFaultType() {

        return deviceFaultType;
    }

    public String getDeviceFaultTypeDesc() {
        if (StringUtils.isEmpty(deviceFaultType)) {
            return "/";
        }
        return deviceFaultType;
    }

    public void setDeviceFaultType(String deviceFaultType) {
        this.deviceFaultType = deviceFaultType;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public String getCauseAnalysis() {
        return causeAnalysis;
    }

    public String getCauseAnalysisDesc() {
        if (StringUtils.isEmpty(causeAnalysis)) {
            return "/";
        }
        return causeAnalysis;
    }

    public void setCauseAnalysis(String causeAnalysis) {
        this.causeAnalysis = causeAnalysis;
    }

    public String getRepairProposal() {
        return repairProposal;
    }

    public String getRepairProposalDesc() {
        if (StringUtils.isEmpty(repairProposal)) {
            return "/";
        }
        return repairProposal;
    }

    public void setRepairProposal(String repairProposal) {
        this.repairProposal = repairProposal;
    }

    public String getFaultColor() {
        return faultColor;
    }

    public void setFaultColor(String faultColor) {
        this.faultColor = faultColor;
    }

    public String getColumnarImg() {
        if (StringUtils.isEmpty(columnarImg)) {
            return "";
        }
        return columnarImg;
    }

    public void setColumnarImg(String columnarImg) {
        this.columnarImg = columnarImg;
    }

    public String getPieImg() {
        if (StringUtils.isEmpty(pieImg)) {
            return "";
        }
        return pieImg;
    }

    public void setPieImg(String pieImg) {
        this.pieImg = pieImg;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }
}
