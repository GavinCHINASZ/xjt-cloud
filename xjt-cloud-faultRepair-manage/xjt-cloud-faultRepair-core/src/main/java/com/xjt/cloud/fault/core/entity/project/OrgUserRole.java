package com.xjt.cloud.fault.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Description: 组织架构--用户(成员)--角色 中间表实体
 * @Author hunaggc
 * @Date 2019/5/23
 **/
public class OrgUserRole extends BaseEntity {
    //组织架构成员表ID
    private Long orgUserId;
    private Long userId;
    private Long[] orgUserIds;
    private Long[] userIds;
    //角色表ID
    private Long roleId;
    private Long[] roleIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getOrgUserIds() {
        return orgUserIds;
    }

    public void setOrgUserIds(Long[] orgUserIds) {
        this.orgUserIds = orgUserIds;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public void initOrgUserRole(Long orgUserId, Long roleId, Long projectId){
        this.orgUserId = orgUserId;
        this.roleId = roleId;
        this.setProjectId(projectId);
    }
}
