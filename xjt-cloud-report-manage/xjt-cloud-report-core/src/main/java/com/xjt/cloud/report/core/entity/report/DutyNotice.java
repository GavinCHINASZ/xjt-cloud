package com.xjt.cloud.report.core.entity.report;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Auther: zhangzf
 * @Date: 2020/05/19
 * @Description: 值班提醒
 */
public class DutyNotice extends BaseEntity {

    //第一次提醒时间 HH:mm
    private String noticeTime1;
    //第二次提醒时间 HH:mm
    private String noticeTime2;
    //值班类型 0白班,1夜班
    private Integer dutyType;
    //第一次提醒是否启用 0、否  1、是
    private Integer dutyStatus1;
    //第二次提醒是否启用 0、否  1、是
    private Integer dutyStatus2;
    //项目名称
    private String projectName;
    //时间
    private String time;
    //自定义事件标题
    private String title;
    //自定义事件时间
    private String autoTime;


    private List<DutyNotice> list;

    public List<DutyNotice> getList() {
        return list;
    }

    public void setList(List<DutyNotice> list) {
        this.list = list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getNoticeTime1() {
        return noticeTime1;
    }

    public void setNoticeTime1(String noticeTime1) {
        this.noticeTime1 = noticeTime1;
    }

    public String getNoticeTime2() {
        return noticeTime2;
    }

    public void setNoticeTime2(String noticeTime2) {
        this.noticeTime2 = noticeTime2;
    }

    public Integer getDutyType() {
        return dutyType;
    }

    public void setDutyType(Integer dutyType) {
        this.dutyType = dutyType;
    }

    public Integer getDutyStatus1() {
        return dutyStatus1;
    }

    public void setDutyStatus1(Integer dutyStatus1) {
        this.dutyStatus1 = dutyStatus1;
    }

    public Integer getDutyStatus2() {
        return dutyStatus2;
    }

    public void setDutyStatus2(Integer dutyStatus2) {
        this.dutyStatus2 = dutyStatus2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutoTime() {
        return autoTime;
    }

    public void setAutoTime(String autoTime) {
        this.autoTime = autoTime;
    }
}
