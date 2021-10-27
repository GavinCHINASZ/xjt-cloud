package com.xjt.cloud.log.manage.entity;

import java.util.Date;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:45
 * @Description:安全日志信息
 */
public class SecurityLog{
    private Long id;//主键id
    private Long sourceId;//所属ID(平台ID或项目ID
    private Integer sourceType;//所属:0工程 1平台 2项目
    private Integer modelType;//所属模块类型
    private String modelName;//所属模块名称
    private Integer actionType;//接口类型
    private String actionName;//接口类型名称
    private String userId;//操作用户id
    private String userName;//操作用户名称
    private String content;//操作内容
    private Date createTime;//操作时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}