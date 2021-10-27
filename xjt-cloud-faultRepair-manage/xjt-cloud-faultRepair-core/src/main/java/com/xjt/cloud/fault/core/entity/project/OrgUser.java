package com.xjt.cloud.fault.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName Project 平台成员实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class OrgUser extends BaseEntity {
    private Long[] ids;
    //组织架构ID
    private Long orgId;
    //用户ID
    private Long userId;
    //成员名称
    private String userName;
    //成员名称
    private String[] userNames;
    private Long roleId;//角色id
    private Long[] roleIds;//角色id
    private Long[] userIds;//userIds
    //角色名称
    private String roleName;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getUserNames() {
        return userNames;
    }

    public void setUserNames(String[] userNames) {
        this.userNames = userNames;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void initOrgUser(Long orgId, Long userId, Long projectId, String userName){
        this.orgId = orgId;
        this.userId = userId;
        this.setProjectId(projectId);
        this.userName = userName;
    }
}
