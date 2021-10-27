package com.xjt.cloud.project.core.entity;

/**
 * @ClassName ProjectMsgLevel
 * @Description
 * @Author wangzhiwen
 * @Date 2021/1/11 10:55
 **/
public class ProjectMsgLevel {
    private Long projectId;//项目id
    private String phoneUserIds;//发送语音用户id
    private String phoneLevels;//发送语音事件级别
    private String smsUserIds;//发送短信用户id
    private String smsLevels;//发送短信事件级别
    private String linkageLevels;//声光报警关联事件级别
    private String msgUserIds;//app与pc信息发送用户id以;分组如:1;2;3
    private String msgLevels;//app与pc信息发送级别以;分组如:1;2;3
    private String eventTypeLevel1;//一级信息事件类型以;分组如:1;2;3',
    private String eventTypeLevel2;//二级信息事件类型以;分组如:1;2;3',
    private String eventTypeLevel3;//三级信息事件类型以;分组如:1;2;3',
    private String eventTypeLevel4;//四级信息事件类型以;分组如:1;2;3',
    private String screenTypes;//大屏配置信息

    public String getMsgUserIds() {
        return msgUserIds;
    }

    public void setMsgUserIds(String msgUserIds) {
        this.msgUserIds = msgUserIds;
    }

    public String getMsgLevels() {
        return msgLevels;
    }

    public void setMsgLevels(String msgLevels) {
        this.msgLevels = msgLevels;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPhoneUserIds() {
        return phoneUserIds;
    }

    public void setPhoneUserIds(String phoneUserIds) {
        this.phoneUserIds = phoneUserIds;
    }

    public String getPhoneLevels() {
        return phoneLevels;
    }

    public void setPhoneLevels(String phoneLevels) {
        this.phoneLevels = phoneLevels;
    }

    public String getSmsUserIds() {
        return smsUserIds;
    }

    public void setSmsUserIds(String smsUserIds) {
        this.smsUserIds = smsUserIds;
    }

    public String getSmsLevels() {
        return smsLevels;
    }

    public void setSmsLevels(String smsLevels) {
        this.smsLevels = smsLevels;
    }

    public String getLinkageLevels() {
        return linkageLevels;
    }

    public void setLinkageLevels(String linkageLevels) {
        this.linkageLevels = linkageLevels;
    }

    public String getScreenTypes() {
        return screenTypes;
    }

    public void setScreenTypes(String screenTypes) {
        this.screenTypes = screenTypes;
    }

    public String getEventTypeLevel1() {
        return eventTypeLevel1;
    }

    public void setEventTypeLevel1(String eventTypeLevel1) {
        this.eventTypeLevel1 = eventTypeLevel1;
    }

    public String getEventTypeLevel2() {
        return eventTypeLevel2;
    }

    public void setEventTypeLevel2(String eventTypeLevel2) {
        this.eventTypeLevel2 = eventTypeLevel2;
    }

    public String getEventTypeLevel3() {
        return eventTypeLevel3;
    }

    public void setEventTypeLevel3(String eventTypeLevel3) {
        this.eventTypeLevel3 = eventTypeLevel3;
    }

    public String getEventTypeLevel4() {
        return eventTypeLevel4;
    }

    public void setEventTypeLevel4(String eventTypeLevel4) {
        this.eventTypeLevel4 = eventTypeLevel4;
    }
}
