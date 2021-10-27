package com.xjt.cloud.task.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @ClassName User
 * @Author Administrator
 * @Date 2019-08-09 8:48
 * @Description TODO
 * @Version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    //部门员工角色id
    private Long orgUserId;
    //用户名称
    private String userName;
    //用户id
    private Long userId;
    //角色id
    private Long roleId;
    private Long taskId;
    private Long buildingId;
    private Long buildingFloorId;
    private Long projectId;
    private Long id;
    private Long orgId;//部门id
    //部门ID
    private Long appOrgId;
    private Long appTaskId;
    private Long appTaskParentId;
    private Long appBuildingId;
    private Long appBuildingFloorId;

    public Long getAppOrgId() {
        return appOrgId;
    }

    public void setAppOrgId(Long appOrgId) {
        this.appOrgId = appOrgId;
    }

    public Long getAppTaskId() {
        return appTaskId;
    }

    public void setAppTaskId(Long appTaskId) {
        this.appTaskId = appTaskId;
    }

    public Long getAppTaskParentId() {
        return appTaskParentId;
    }

    public void setAppTaskParentId(Long appTaskParentId) {
        this.appTaskParentId = appTaskParentId;
    }

    public Long getAppBuildingId() {
        return appBuildingId;
    }

    public void setAppBuildingId(Long appBuildingId) {
        this.appBuildingId = appBuildingId;
    }

    public Long getAppBuildingFloorId() {
        return appBuildingFloorId;
    }

    public void setAppBuildingFloorId(Long appBuildingFloorId) {
        this.appBuildingFloorId = appBuildingFloorId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

   /* public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }*/
}
