package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * 大屏设置
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
public class ScreenSet extends BaseEntity {
    // 是否显示火警模块
    private Integer isShowFireAlarm;
    // 是否显示极早期
    private Integer isShowVesa;
    // 是否显示水压
    private Integer isShowWaterGage;
    // 是否显示水浸
    private Integer isShowWaterImmersion;
    // 是否显示消火栓
    private Integer isShowHydrant;
    // 是否显示烟感
    private Integer isShowSmoke;
    // 是否显示声光
    private Integer isShowLinkage;

    //  是否显示火眼
    private Integer isShowFireEye;

    // 项目名称
    private String projectName;
    // 设置总数
    private Integer deviceCount;
    // 项目ID
    private List<Long> projectIds;

    //  1工单管理, 2设备管理, 3故障报修, 4故障分析, 5火警原因分析
    private Integer modelType;

    //  1周, 2月, 3年
    private Integer type;
    private String urlPth;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 结束时间
    private String appId;
    private String eventTypeNameLevel1;// 事件级别1类型名称，与事件类型对应
    private String eventTypeNameLevel2;// 事件级别2类型名称，与事件类型对应
    private String eventTypeNameLevel3;// 事件级别3类型名称，与事件类型对应
    private String eventTypeNameLevel4;// 事件级别4类型名称，与事件类型对应
    private String msgLevels;

    private String msgTypes;

    private List<TreeNode> treeList;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ScreenSet() {
    }

    public ScreenSet(Long projectId, Long userId, String userName, String msgLevels) {
        this.setProjectId(projectId);
        this.setCreateUserId(userId);
        this.setUpdateUserName(userName);
        this.isShowFireAlarm = 1;
        this.isShowVesa = 1;
        this.isShowWaterGage = 1;
        this.isShowWaterImmersion = 1;
        this.msgLevels = msgLevels;
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

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Integer getIsShowFireAlarm() {
        return isShowFireAlarm;
    }

    public void setIsShowFireAlarm(Integer isShowFireAlarm) {
        this.isShowFireAlarm = isShowFireAlarm;
    }

    public Integer getIsShowVesa() {
        return isShowVesa;
    }

    public void setIsShowVesa(Integer isShowVesa) {
        this.isShowVesa = isShowVesa;
    }

    public Integer getIsShowWaterGage() {
        return isShowWaterGage;
    }

    public void setIsShowWaterGage(Integer isShowWaterGage) {
        this.isShowWaterGage = isShowWaterGage;
    }

    public Integer getIsShowWaterImmersion() {
        return isShowWaterImmersion;
    }

    public void setIsShowWaterImmersion(Integer isShowWaterImmersion) {
        this.isShowWaterImmersion = isShowWaterImmersion;
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

    public String getMsgLevels() {
        return msgLevels;
    }

    public void setMsgLevels(String msgLevels) {
        this.msgLevels = msgLevels;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrlPth() {
        return urlPth;
    }

    public void setUrlPth(String urlPth) {
        this.urlPth = urlPth;
    }

    public Integer getIsShowHydrant() {
        return isShowHydrant;
    }

    public void setIsShowHydrant(Integer isShowHydrant) {
        this.isShowHydrant = isShowHydrant;
    }

    public Integer getIsShowSmoke() {
        return isShowSmoke;
    }

    public void setIsShowSmoke(Integer isShowSmoke) {
        this.isShowSmoke = isShowSmoke;
    }

    public Integer getIsShowLinkage() {
        return isShowLinkage;
    }

    public void setIsShowLinkage(Integer isShowLinkage) {
        this.isShowLinkage = isShowLinkage;
    }

    public Integer getIsShowFireEye() {
        return isShowFireEye;
    }

    public void setIsShowFireEye(Integer isShowFireEye) {
        this.isShowFireEye = isShowFireEye;
    }

    public String getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(String msgTypes) {
        this.msgTypes = msgTypes;
    }

    public List<TreeNode> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<TreeNode> treeList) {
        this.treeList = treeList;
    }
}
