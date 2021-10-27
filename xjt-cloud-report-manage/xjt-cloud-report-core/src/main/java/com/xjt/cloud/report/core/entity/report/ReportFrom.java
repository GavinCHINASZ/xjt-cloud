package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 日报报表 实体类
 *
 * @Auther: huanggc
 * @Date: 2020/07/06
 */
public class ReportFrom extends BaseEntity {
    private String projectName;

    private Date createTime;
    // 表头
    private String headerName;
    // 天气
    private String weather;
    // 人员
    private String personnel;
    // 单位
    private String company;
    // 主管
    private String director;
    // 业主单位
    private String owners;
    // 工程师
    private String engineer;

    private Integer downType;// 1 doc,  2 excel,  3 pdf
    private String title;
    private String colName;

    private List<ReportFromRecord> reportFromRecordList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getWeather() {
        if(StringUtils.isEmpty(weather)){
            return "";
        }
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getCompany() {
        if (StringUtils.isEmpty(company)){
            return "";
        }
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDirector() {
        if (StringUtils.isEmpty(director)){
            return "";
        }
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getOwners() {
        if (StringUtils.isEmpty(owners)){
            return "";
        }
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getEngineer() {
        if (StringUtils.isEmpty(engineer)){
            return "";
        }
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }

    public List<ReportFromRecord> getReportFromRecordList() {
        return reportFromRecordList;
    }

    public void setReportFromRecordList(List<ReportFromRecord> reportFromRecordList) {
        this.reportFromRecordList = reportFromRecordList;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeDesc() {
        if (createTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.format(createTime);
        }
        return "";
    }

    public String getCreateTimeDescs() {
        if (createTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(createTime);
        }
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColName() {
        if (StringUtils.isEmpty(colName)){
            return "";
        }
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
