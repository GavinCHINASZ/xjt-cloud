package com.xjt.cloud.task.core.entity.project;

/**
 * @ClassName OrgRoleUser
 * @Author dwt
 * @Date 2020-11-04 11:42
 * @Version 1.0
 */
public class OrgRoleUser {
    private Long projectId;
    private String userName;
    private Long[] orgIds;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }
}
