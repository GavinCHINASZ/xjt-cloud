package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Description: 组织架构--用户(成员)--角色 中间表实体
 * @Author hunaggc
 * @Date 2019/5/23
 **/
public class OrgUserRole extends BaseEntity {

    private Long[] ids;
    //组织架构成员表ID
    private Long orgUserId;
    private Long userId;
    private Long[] orgUserIds;
    private Long[] userIds;
    //角色表ID
    private Long roleId;
    private Long newRoleId;
    private Long[] roleIds;
    //父类ID
    private Long parentId;
    //类型1、角色 2、成员
    private Integer type;
    //名称
    private String name;
    //角色名称
    private String roleName;
    //权限别名
    private List<String> signList;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public List<String> getSignList() {
        return signList;
    }

    public void setSignList(List<String> signList) {
        this.signList = signList;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Long getNewRoleId() {
        return newRoleId;
    }

    public void setNewRoleId(Long newRoleId) {
        this.newRoleId = newRoleId;
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
