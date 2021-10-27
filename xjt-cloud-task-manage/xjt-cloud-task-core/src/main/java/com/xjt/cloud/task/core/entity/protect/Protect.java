package com.xjt.cloud.task.core.entity.protect;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 地铁 班前防护
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public class Protect extends BaseEntity {
    private Long[] ids;
    /**
     * 作业名称
     */
    private String protectName;

    /**
     * 施工负责人
     */
    private String constructionDirector;
    /**
     * 施工负责人ID  p_org_user.id
     */
    private Long constructionDirectorId;

    /**
     * 防护等级ID
     */
    private Long protectGradeId;

    /**
     * 防护等级名称
     */
    private String protectGrade;

    /**
     * 防护时长
     */
    private Integer protectDuration;

    /**
     * 作业人员
     */
    private String operators;

    private String operatorIdStr;

    /**
     * 作业人员ID
     */
    private Long[] operatorsIds;

    /**
     * 提交人
     */
    private String submitter;

    private String[] imageUrls;

    /**
     * 1未开始
     * 2进行中
     * 4防护失效
     */
    private Integer taskState;
    private Integer[] taskStates;

    /**
     * 1默认 3结束防护  4防护关闭
     */
    private Integer protectState;
    private Integer[] protectStates;

    private Long userId;
    private List<Long> userIdList;

    private String fireAlarmEventId;
    private Long[] fireAlarmEventIds;
    private List<FireAlarmEvent> fireAlarmEventList;

    private List<IntegratedMonitoring> integratedMonitoringList;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 事件状态 1已恢复, 2未恢复
     */
    private Integer recoverStatus;

    private Integer sign;

    /**
     * 防护开始时间
     */
    private Date protectCreateTime;

    /**
     * 防护结束时间
     */
    private Date protectEndTime;

    /**
     * 防护关闭时间
     */
    private Date protectCloseTime;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getProtectName() {
        return protectName;
    }

    public void setProtectName(String protectName) {
        this.protectName = protectName;
    }

    public String getConstructionDirector() {
        return constructionDirector;
    }

    public void setConstructionDirector(String constructionDirector) {
        this.constructionDirector = constructionDirector;
    }

    public Long getConstructionDirectorId() {
        return constructionDirectorId;
    }

    public void setConstructionDirectorId(Long constructionDirectorId) {
        this.constructionDirectorId = constructionDirectorId;
    }

    public Long getProtectGradeId() {
        return protectGradeId;
    }

    public void setProtectGradeId(Long protectGradeId) {
        this.protectGradeId = protectGradeId;
    }

    public String getProtectGrade() {
        return protectGrade;
    }

    public void setProtectGrade(String protectGrade) {
        this.protectGrade = protectGrade;
    }

    public Integer getProtectDuration() {
        return protectDuration;
    }

    public void setProtectDuration(Integer protectDuration) {
        this.protectDuration = protectDuration;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getOperatorIdStr() {
        return operatorIdStr;
    }

    public void setOperatorIdStr(String operatorIdStr) {
        this.operatorIdStr = operatorIdStr;
    }

    public Long[] getOperatorsIds() {
        return operatorsIds;
    }

    public void setOperatorsIds(Long[] operatorsIds) {
        this.operatorsIds = operatorsIds;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Integer[] getTaskStates() {
        return taskStates;
    }

    public void setTaskStates(Integer[] taskStates) {
        this.taskStates = taskStates;
    }

    public Integer getProtectState() {
        return protectState;
    }

    public void setProtectState(Integer protectState) {
        this.protectState = protectState;
    }

    public Integer[] getProtectStates() {
        return protectStates;
    }

    public void setProtectStates(Integer[] protectStates) {
        this.protectStates = protectStates;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public String getFireAlarmEventId() {
        return fireAlarmEventId;
    }

    public void setFireAlarmEventId(String fireAlarmEventId) {
        this.fireAlarmEventId = fireAlarmEventId;
    }

    public Long[] getFireAlarmEventIds() {
        return fireAlarmEventIds;
    }

    public void setFireAlarmEventIds(Long[] fireAlarmEventIds) {
        this.fireAlarmEventIds = fireAlarmEventIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public List<FireAlarmEvent> getFireAlarmEventList() {
        return fireAlarmEventList;
    }

    public void setFireAlarmEventList(List<FireAlarmEvent> fireAlarmEventList) {
        this.fireAlarmEventList = fireAlarmEventList;
    }

    public List<IntegratedMonitoring> getIntegratedMonitoringList() {
        return integratedMonitoringList;
    }

    public void setIntegratedMonitoringList(List<IntegratedMonitoring> integratedMonitoringList) {
        this.integratedMonitoringList = integratedMonitoringList;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Date getProtectCreateTime() {
        return protectCreateTime;
    }

    public void setProtectCreateTime(Date protectCreateTime) {
        this.protectCreateTime = protectCreateTime;
    }

    public Date getProtectEndTime() {
        return protectEndTime;
    }

    public void setProtectEndTime(Date protectEndTime) {
        this.protectEndTime = protectEndTime;
    }

    public Date getProtectCloseTime() {
        return protectCloseTime;
    }

    public void setProtectCloseTime(Date protectCloseTime) {
        this.protectCloseTime = protectCloseTime;
    }
}
