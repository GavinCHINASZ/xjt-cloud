package com.xjt.cloud.admin.manage.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;

/**
 * pvUv统计
 *
 * @author huanggc
 * @date 2020/11/05
 */
public class PvUvReport extends BaseEntity {
    private String dateName;

    /**
     * 总PV
     */
    private Integer pvTotalCount;
    private Integer uvTotalCount;

    /**
     * 1级PV
     */
    private Integer oneLevelPvCount;
    private Integer oneLevelUvCount;

    /**
     * 2级PV
     */
    private Integer twoLevelPvCount;
    private Integer twoLevelUvCount;

    /**
     * 其它PV
     */
    private Integer otherPvCount;
    private Integer otherUvCount;

    private Date startTime;
    private String startTimeStr;
    private Date endTime;
    private String endTimeStr;

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public Integer getPvTotalCount() {
        return pvTotalCount;
    }

    public void setPvTotalCount(Integer pvTotalCount) {
        this.pvTotalCount = pvTotalCount;
    }

    public Integer getUvTotalCount() {
        return uvTotalCount;
    }

    public void setUvTotalCount(Integer uvTotalCount) {
        this.uvTotalCount = uvTotalCount;
    }

    public Integer getOneLevelPvCount() {
        return oneLevelPvCount;
    }

    public void setOneLevelPvCount(Integer oneLevelPvCount) {
        this.oneLevelPvCount = oneLevelPvCount;
    }

    public Integer getOneLevelUvCount() {
        return oneLevelUvCount;
    }

    public void setOneLevelUvCount(Integer oneLevelUvCount) {
        this.oneLevelUvCount = oneLevelUvCount;
    }

    public Integer getTwoLevelPvCount() {
        return twoLevelPvCount;
    }

    public void setTwoLevelPvCount(Integer twoLevelPvCount) {
        this.twoLevelPvCount = twoLevelPvCount;
    }

    public Integer getTwoLevelUvCount() {
        return twoLevelUvCount;
    }

    public void setTwoLevelUvCount(Integer twoLevelUvCount) {
        this.twoLevelUvCount = twoLevelUvCount;
    }

    public Integer getOtherPvCount() {
        return otherPvCount;
    }

    public void setOtherPvCount(Integer otherPvCount) {
        this.otherPvCount = otherPvCount;
    }

    public Integer getOtherUvCount() {
        return otherUvCount;
    }

    public void setOtherUvCount(Integer otherUvCount) {
        this.otherUvCount = otherUvCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }
}
