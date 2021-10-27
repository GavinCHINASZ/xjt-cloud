package com.xjt.cloud.project.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName ProjectRole 项目角色实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Role extends BaseEntity {
    //角色名称
    private String roleName;

    //所属: 2项目
    private Integer sourceType;

    //所属ID(平台ID,项目ID)
    private Long sourceId;

    //是否管理员:1是,2不是
    private Integer isAdmin;

    //是否为普通成员 1是，2 不是
    private Integer isOrdinary;

    //权限ID
    private List<Long> permissionIds;

    //成员id
    private List<Long>  orgUserIds;

    //权限名称
    private String permissionName;
    //权限名称
    private String[] permissionNames;

    // 角色类型 1、系统管理员  2、项目管理员  3、工程师   4、检测员,5维保人员 ,6项目负责人
    private Integer roleType;

    //角色ID
    private List<Long> ids;
    private Integer superAdmin;//'是否超级管理员，1普通权限，2超级管理员'

    public String[] getPermissionNames() {
        return permissionNames;
    }

    public void setPermissionNames(String[] permissionNames) {
        this.permissionNames = permissionNames;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public List<Long> getOrgUserIds() {
        return orgUserIds;
    }

    public void setOrgUserIds(List<Long> orgUserIds) {
        this.orgUserIds = orgUserIds;
    }

    public Integer getIsOrdinary() {
        return isOrdinary;
    }

    public void setIsOrdinary(Integer isOrdinary) {
        this.isOrdinary = isOrdinary;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
