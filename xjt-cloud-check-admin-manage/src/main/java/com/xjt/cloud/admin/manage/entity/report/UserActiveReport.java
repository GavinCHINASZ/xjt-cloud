package com.xjt.cloud.admin.manage.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * 用户活跃
 *
 * @author huanggc
 * @date 2021/03/03
 */
public class UserActiveReport extends BaseEntity {
    /**
     * 1 Android, 2 ios,3 PC
     */
    private Integer clientType;
    private String clientTypeName;

    private String dateName;

    /**
     * 活跃用户数
     */
    private Integer activeUserCount;

    /**
     * 活跃用户数
     */
    private Integer quietUserCount;

    /**
     * 总用户数
     */
    private Integer userCount;

    /**
     * 数量
     */
    private Integer num;

    /**
     * uv数量
     */
    private Integer uvTotalCount;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 模块类型(名称)
     */
    private Integer pageType;
    private String pageTypeName;
    private Integer pageTypeNum;

    private String pageUrl;

    /**
     * 页面级别
     */
    private Integer pageLevel;

    private String projectName;

    private Integer androidCount;
    private Integer iosCount;
    private Integer pcCount;

    private Date startTime;
    private String startTimeStr;
    private Date endTime;
    private String endTimeStr;
    private String timeStr;

    public Integer getClientType() {
        return clientType;
    }

    public String getClientTypeDesc() {
        if (clientType != null) {
            switch (clientType) {
                case 1:
                    return "Android";
                case 2:
                    return "ios";
                default:
                    return "PC";
            }
        }

        return "PC";
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getClientTypeName() {
        return clientTypeName;
    }

    public void setClientTypeName(String clientTypeName) {
        this.clientTypeName = clientTypeName;
        if (StringUtils.isNotEmpty(clientTypeName)){
            if ("Android".equalsIgnoreCase(clientTypeName)){
                this.clientType = 1;
            }else if ("ios".equalsIgnoreCase(clientTypeName)){
                this.clientType = 2;
            }else if ("PC".equalsIgnoreCase(clientTypeName)){
                this.clientType = 3;
            }
        }
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public Integer getActiveUserCount() {
        return activeUserCount;
    }

    public String getActiveUserCountRate() {
        if (this.userCount != null && this.activeUserCount != null){
            return decimalFormat(this.activeUserCount, this.userCount) + "%";
        }
        return "/%";
    }

    public void setActiveUserCount(Integer activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public Integer getQuietUserCount() {
        if (this.userCount != null && this.activeUserCount != null){
            return userCount - activeUserCount;
        }
        return quietUserCount;
    }

    public String getQuietUserCountRate() {
        if (this.userCount != null && this.activeUserCount != null){
            return decimalFormat(this.userCount - this.activeUserCount, this.userCount) + "%";
        }
        return "/%";
    }

    public void setQuietUserCount(Integer quietUserCount) {
        this.quietUserCount = quietUserCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getUvTotalCount() {
        return uvTotalCount;
    }

    public void setUvTotalCount(Integer uvTotalCount) {
        this.uvTotalCount = uvTotalCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public String getPageTypeName() {
        return pageTypeName;
    }

    public void setPageTypeName(String pageTypeName) {
        this.pageTypeName = pageTypeName;
    }

    public Integer getPageTypeNum() {
        return pageTypeNum;
    }

    public void setPageTypeNum(Integer pageTypeNum) {
        this.pageTypeNum = pageTypeNum;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Integer getPageLevel() {
        return pageLevel;
    }

    public void setPageLevel(Integer pageLevel) {
        this.pageLevel = pageLevel;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getAndroidCount() {
        return androidCount;
    }

    public void setAndroidCount(Integer androidCount) {
        this.androidCount = androidCount;
    }

    public Integer getIosCount() {
        return iosCount;
    }

    public void setIosCount(Integer iosCount) {
        this.iosCount = iosCount;
    }

    public Integer getPcCount() {
        return pcCount;
    }

    public void setPcCount(Integer pcCount) {
        this.pcCount = pcCount;
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

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime){
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    private String decimalFormat(Integer a, Integer b) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format((float)a / (float)b * 100);
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
