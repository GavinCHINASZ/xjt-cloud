package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;

import java.util.List;

/**
 * @ClassName Organization 项目日志
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Log extends BaseEntity {
    //文本
    private String content;
    //日志模块名称
    private String modelName;
    //日志类型
    private String modelType;
    //所属ID
    private Long sourceId;
    //所属类型0、系统 1、平台 2、项目
    private Integer sourceType;
    //接口类型
    private Integer actionType;
    //接口名称
    private String actionName;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //用户ID
    private String userId;
    //用户名称
    private String userName;
    //接口名称
    private List<String> actionNames;

    //表头
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getActionNames() {
        return actionNames;
    }

    public void setActionNames(List<String> actionNames) {
        this.actionNames = actionNames;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getCreateTimeDesc(){
        return DateUtils.formatDateTime(getCreateTime());
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
