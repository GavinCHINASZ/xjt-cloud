package com.xjt.cloud.message.manage.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;


/**
 * 消息
 *
 * @author zhangZaiFa
 * @date 2019/4/29 0029 13:45
 */
public class Message extends BaseEntity {
    // 标题
    private String title;

    /**
     * 消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核
     * 2、工作通知(eventType=99物联设备上月报告,)
     * 3、审核通知 4、报修通知 5、智能消火栓
     * 7、水浸 8、可燃气 9、烟感 10、电气火灾通知, 13、极早期预警, 15声光,
     * 16报表通知(eventType 1=巡查设备运营周报, 2=物联设备运营周报)
     */
    private Integer messageType;

    // 事件类型
    private Integer eventType;
    private Integer eventLevel;//消息级别
    //正文
    private String content;
    //所属用户Id
    private String ownUserId;
    //消息状态 1、未读   2、已读
    private String messageStatus;
    //消息跳转地址
    private String url;
    //关联记录ID
    private Long recordId;
    //数据存放
    private String data;
    //数据库名称
    private String databasesName;
    //表名
    private String tableName;
    //项目名称
    private String projectName;
    //消息字体显示颜色 0、灰  1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫
    private Integer fontColor;
    //建筑建筑ID
    private Long buildingId;
    private String buildingName;
    private String floorName;
    private String deviceLocation;
    private String deviceName;
    private String qrNo;
    private String phoneUserIds;
    private String smsUserIds;
    private Integer screenStatus;//大屏信息状态，1默认未读，2已读
    private Integer type;//信息类型 1个人信息推送，2大屏弹框信息与个人信息
    //userId集合
    private List<Long> userIds;
    private Long userId;

    public Message(Integer msgType, List<Long> userIds, String title, String content, String url, Long projectId,
                   Long recordId, String data, Integer eventType, Integer fontColor, String buildingName, String floorName,
                   String deviceLocation, String deviceName, String qrNo) {
        this.messageType = msgType;
        this.userIds = userIds;
        this.title = title;
        this.content = content;
        this.url = url;
        this.setProjectId(projectId);
        this.recordId = recordId;
        this.data = data;
        this.eventType = eventType;
        this.fontColor = fontColor;
        this.buildingName = buildingName;
        this.floorName = floorName;
        this.deviceLocation = deviceLocation;
        this.deviceName = deviceName;
        this.qrNo = qrNo;
    }

    public Message(Integer msgType, List<Long> userIds, String title, String content, String url, Long projectId,Long recordId, String data,
                   Integer eventType,Integer fontColor,String buildingName,String floorName,String deviceLocation,String deviceName,String qrNo,
                   Object buildingId,String projectName,Integer eventLevel,Integer type) {
        this.messageType = msgType;
        this.userIds = userIds;
        this.title = title;
        this.content = content;
        this.url = url;
        this.setProjectId(projectId);
        this.recordId = recordId;
        this.data = data;
        this.eventType = eventType;
        this.fontColor = fontColor;
        this.buildingName = buildingName;
        this.floorName = floorName;
        this.deviceLocation = deviceLocation;
        this.deviceName = deviceName;
        this.qrNo = qrNo;

        if (buildingId != null) {
            this.buildingId = Long.parseLong(buildingId.toString());
        }
        this.projectName = projectName;
        this.eventLevel = eventLevel;
        this.type = type;
    }

    public Message() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScreenStatus() {
        return screenStatus;
    }

    public void setScreenStatus(Integer screenStatus) {
        this.screenStatus = screenStatus;
    }

    public String getPhoneUserIds() {
        return phoneUserIds;
    }

    public void setPhoneUserIds(String phoneUserIds) {
        this.phoneUserIds = phoneUserIds;
    }

    public String getSmsUserIds() {
        return smsUserIds;
    }

    public void setSmsUserIds(String smsUserIds) {
        this.smsUserIds = smsUserIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getDatabasesName() {
        return databasesName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDatabasesName(String databasesName) {
        this.databasesName = databasesName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getMessageType() {
        return messageType;
    }


    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(String ownUserId) {
        this.ownUserId = ownUserId;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
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
}