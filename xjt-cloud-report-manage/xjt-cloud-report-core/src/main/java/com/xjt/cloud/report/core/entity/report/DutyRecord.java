package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 值班记录 实体类
 */
public class DutyRecord extends BaseEntity {
    //隔离数量
    private Integer isolateCount;
    //隔离情况描述
    private String isolateDescription;
    //无应答数量
    private Integer noResponseCount;
    //无应答情况描述
    private String noResponseDescription;
    //脏数量
    private Integer dirtyCount;
    //脏情况描述
    private String dirtyDescription;
    //其他数量
    private Integer otherCount;
    //其他情况描述
    private String otherDescription;
    //错误应答数量
    private Integer errorResponseCount;
    //错误应答情况描述
    private String errorResponseDescription;
    //故障数量
    private Integer faultCount;
    //故障等级
    private Integer faultLevel;
    //填报时间
    private String fillTime;
    //填报人名称
    private String fillOrgUserName;
    //故障等级ID
    private Long faultLevelId;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //查询时间类型 year/month/day/   例：2019/2019-8/2019-9-1
    private String dateType;
    //图片Url
    private List<String> imageUrls;
    //记录数
    private Integer recordCount;
    //故障总数
    private Integer faultSumCount;

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getFaultSumCount() {
        return faultSumCount;
    }

    public void setFaultSumCount(Integer faultSumCount) {
        this.faultSumCount = faultSumCount;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getErrorResponseCount() {
        return errorResponseCount;
    }

    public void setErrorResponseCount(Integer errorResponseCount) {
        this.errorResponseCount = errorResponseCount;
    }

    public String getErrorResponseDescription() {
        return errorResponseDescription;
    }

    public void setErrorResponseDescription(String errorResponseDescription) {
        this.errorResponseDescription = errorResponseDescription;
    }


    public Long getFaultLevelId() {
        return faultLevelId;
    }

    public void setFaultLevelId(Long faultLevelId) {
        this.faultLevelId = faultLevelId;
    }

    public Integer getIsolateCount() {
        return isolateCount;
    }

    public void setIsolateCount(Integer isolateCount) {
        this.isolateCount = isolateCount;
    }

    public String getIsolateDescription() {
        return isolateDescription;
    }

    public void setIsolateDescription(String isolateDescription) {
        this.isolateDescription = isolateDescription;
    }

    public Integer getNoResponseCount() {
        return noResponseCount;
    }

    public void setNoResponseCount(Integer noResponseCount) {
        this.noResponseCount = noResponseCount;
    }

    public String getNoResponseDescription() {
        return noResponseDescription;
    }

    public void setNoResponseDescription(String noResponseDescription) {
        this.noResponseDescription = noResponseDescription;
    }

    public Integer getDirtyCount() {
        return dirtyCount;
    }

    public void setDirtyCount(Integer dirtyCount) {
        this.dirtyCount = dirtyCount;
    }

    public String getDirtyDescription() {
        return dirtyDescription;
    }

    public void setDirtyDescription(String dirtyDescription) {
        this.dirtyDescription = dirtyDescription;
    }

    public Integer getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(Integer otherCount) {
        this.otherCount = otherCount;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(Integer faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getFillTime() {
        return fillTime;
    }

    public void setFillTime(String fillTime) {
        this.fillTime = fillTime;
    }

    public String getFillOrgUserName() {
        return fillOrgUserName;
    }

    public void setFillOrgUserName(String fillOrgUserName) {
        this.fillOrgUserName = fillOrgUserName;
    }
}
