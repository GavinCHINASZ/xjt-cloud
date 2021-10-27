package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName CheckProjectReport
 * @Description 评估项目报表记录
 * @Author wangzhiwen
 * @Date 2021/5/14 10:42
 **/
public class CheckProjectReport extends BaseEntity {
    //报告名称
    private String reportName;
    //报表编号
    private String reportNumber;
    //1、执行中  2、待签章  3、已签章   5、已发布
    private Integer checkProjectStatus;
    //是否修改 0.未修改 1.修改
    private Integer isModify;
    //修改原因 1.正常 2.查看原因
    private String modifyReason;
    //检测版本
    private Integer checkVersion;
    //报告文件路径
    private String fileUrl;
    private Long checkProjectId;//评估项目id

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getCheckVersion() {
        return checkVersion;
    }

    public void setCheckVersion(Integer checkVersion) {
        this.checkVersion = checkVersion;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Integer getCheckProjectStatus() {
        return checkProjectStatus;
    }

    public void setCheckProjectStatus(Integer checkProjectStatus) {
        this.checkProjectStatus = checkProjectStatus;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
    }

    public Integer getIsModify() {
        return isModify;
    }

    public void setIsModify(Integer isModify) {
        this.isModify = isModify;
    }
}
