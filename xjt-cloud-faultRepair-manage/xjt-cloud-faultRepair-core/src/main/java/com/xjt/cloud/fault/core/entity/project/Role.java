package com.xjt.cloud.fault.core.entity.project;

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
