package com.xjt.cloud.message.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * MsgPushManage 消息推送管理
 *
 * @author zhangZaiFa
 * @date 2019-11-14 10:10
 */
public class MsgPushManage extends BaseEntity {
    private String phoneUserIds;//语音推送人userId
    private String phoneLevels;//语音发送级别以;分组如:1;2;3
    private String smsUserIds;//短信推送人userId
    private String smsLevels;//短信发送级别以;分组如:1;2;3
    private String linkageLevels;//声光关联级别以;分组如:1;2;3
    private String msgUserIds;//app与pc信息发送用户id以;分组如:1;2;3
    private String msgLevels;//app与pc信息发送级别以;分组如:1;2;3
    private Long projectMsgLevelId;//项目信息级别配置的主键id，如果为空，则表式未配置项目级别信息
    private String eventTypeLevel1;//一级信息事件类型以;分组如:1;2;3
    private String eventTypeLevel2;//二级信息事件类型以;分组如:1;2;3
    private String eventTypeLevel3;//三级信息事件类型以;分组如:1;2;3
    private String eventTypeLevel4;//四级信息事件类型以;分组如:1;2;3
    private String eventTypeNameLevel1;//事件级别1类型名称，与事件类型对应
    private String eventTypeNameLevel2;//事件级别2类型名称，与事件类型对应
    private String eventTypeNameLevel3;//事件级别3类型名称，与事件类型对应
    private String eventTypeNameLevel4;//事件级别4类型名称，与事件类型对应

    //消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核    2、工作通知 3、审核通知    4、(故障报修)报修通知   5、智能消火栓
    //7、水浸 8、可燃气   9、烟感   10、电气火灾通知  13、极早期预警, 20声光报警(联动)
    private Integer msgType;
    //项目名称
    private String name;
    //父类ID
    private Long parentId;
    //推送类型 1、短信 2、语音, 3声光
    private Integer pushType;
    // 别名
    private String sign;
    //是否选中 0未选中, 1选中
    private Integer isChecked;
    //消息推送类型Ids;
    private String msgPushTypeIds;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public String getMsgPushTypeIds() {
        return msgPushTypeIds;
    }

    public void setMsgPushTypeIds(String msgPushTypeIds) {
        this.msgPushTypeIds = msgPushTypeIds;
    }

    public String getSmsUserIds() {
        return smsUserIds;
    }

    public void setSmsUserIds(String smsUserIds) {
        this.smsUserIds = smsUserIds;
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

    public Long getProjectMsgLevelId() {
        return projectMsgLevelId;
    }

    public void setProjectMsgLevelId(Long projectMsgLevelId) {
        this.projectMsgLevelId = projectMsgLevelId;
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

    public String getEventTypeNameLevel1() {
        return eventTypeNameLevel1;
    }

    public void setEventTypeNameLevel1(String eventTypeNameLevel1) {
        this.eventTypeNameLevel1 = eventTypeNameLevel1;
    }

    public String getEventTypeNameLevel2() {
        return eventTypeNameLevel2;
    }

    public void setEventTypeNameLevel2(String eventTypeNameLevel2) {
        this.eventTypeNameLevel2 = eventTypeNameLevel2;
    }

    public String getEventTypeNameLevel3() {
        return eventTypeNameLevel3;
    }

    public void setEventTypeNameLevel3(String eventTypeNameLevel3) {
        this.eventTypeNameLevel3 = eventTypeNameLevel3;
    }

    public String getEventTypeNameLevel4() {
        return eventTypeNameLevel4;
    }

    public void setEventTypeNameLevel4(String eventTypeNameLevel4) {
        this.eventTypeNameLevel4 = eventTypeNameLevel4;
    }
}
