package com.xjt.cloud.message.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.Date;
import java.util.List;


/**
 * Message 消息
 *
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
public class Message extends BaseEntity {
    // 消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核 2、工作通知 3、审核通知 4、报修通知 5、智能消火栓
    // 7、水浸 8、可燃气 9、烟感 10、电气火灾通知 13、极早期预警  14、值班通知消息 15、声光报警通知
    private Integer messageType;
    private Integer[] messageTypes;
    // 事件类型 权限消息类型不同对应自己消息类型的事件
    private Integer eventType;
    // 消息字体显示颜色 0、灰 1、红 2、橙 3、黄 4、绿 5、青 6、蓝 7、紫
    private Integer fontColor;
    // 项目名称
    private String projectName;
    // 标题
    private String title;
    // 正文
    private String content;
    // 所属用户Id
    private Long ownUserId;
    private Long userMsgId;//用户信息id
    //消息状态 1、未读   2、已读
    private String messageStatus;
    //消息跳转地址
    private String url;
    //关联记录ID
    private Long recordId;
    //数据存放
    private String data;
    //数据库名
    private String databasesName;
    //表名
    private String tableName;
    //id集合
    private List<Long> ids;
    //未读数量
    private Integer unreadCount;
    //是否打开提醒 1、不打开  2、打开
    private Integer isNotify;
    //搜索方式 1、多条件模糊搜索返回数据格式为多重集合  其他为正常搜索
    private Integer searchMethod;
    //建筑ID
    private Long buildingId;
    //项目ID
    private List<String> projectIds;
    private Integer eventLevel;//消息级别
    private Integer[] eventLevels;//消息级别
    private Date endTime;
    private Integer alarmMsgTotal;//告警信息总数
    private Integer workMsgTotal;//工作信息总数
    private Integer msgTotal;//信息总数
    private Integer alarmMsg;//查询 1告警消息  2消息记录

    public Long getUserMsgId() {
        return userMsgId;
    }

    public void setUserMsgId(Long userMsgId) {
        this.userMsgId = userMsgId;
    }

    public Integer getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(Integer alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public Integer[] getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(Integer[] messageTypes) {
        this.messageTypes = messageTypes;
    }

    public Integer[] getEventLevels() {
        return eventLevels;
    }

    public void setEventLevels(Integer[] eventLevels) {
        this.eventLevels = eventLevels;
    }

    public Integer getMsgTotal() {
        return msgTotal;
    }

    public void setMsgTotal(Integer msgTotal) {
        this.msgTotal = msgTotal;
    }

    public Integer getAlarmMsgTotal() {
        return alarmMsgTotal;
    }

    public void setAlarmMsgTotal(Integer alarmMsgTotal) {
        this.alarmMsgTotal = alarmMsgTotal;
    }

    public Integer getWorkMsgTotal() {
        return workMsgTotal;
    }

    public void setWorkMsgTotal(Integer workMsgTotal) {
        this.workMsgTotal = workMsgTotal;
    }

    public Date getEndTime() {
        if (endTime != null){
            return DateUtils.add24Hours(endTime);
        }
        return null;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }

    public Integer getSearchMethod() {
        return searchMethod;
    }

    public void setSearchMethod(Integer searchMethod) {
        this.searchMethod = searchMethod;
    }

    public Integer getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Integer isNotify) {
        this.isNotify = isNotify;
    }



    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getTableName() {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return tableName;
        }
        if (ownUserId != null){
            return "m_user_message_" + (ownUserId % 10L);
        }
        return "m_user_message_0";
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatabasesName() {
        return databasesName;
    }

    public void setDatabasesName(String databasesName) {
        this.databasesName = databasesName;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(Long ownUserId) {
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

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}
