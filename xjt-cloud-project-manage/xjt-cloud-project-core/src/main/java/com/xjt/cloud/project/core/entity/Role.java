package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * Role 角色实体
 *
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Role extends BaseEntity {
    //角色名称
    private String roleName;

    //所属: 2项目
    private Integer sourceType;
    private String menuName;
    private String menuSign;
    private String sign;
    //权限ID
    private List<Long> permissionIds;
    //成员id
    private List<Long>  orgUserIds;
    //权限名称
    private String permissionName;
    //角色ID
    private List<Long> ids;
    private Integer superAdmin;//是否超级管理员，1普通权限，2超级管理员,3普通用户

    //所属ID(平台ID,项目ID)
    private Long sourceId;

    //是否管理员:1是,2不是
    private Integer isAdmin;

    //是否为普通成员 1是，2 不是
    private Integer isOrdinary;

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

    public Integer getIsOrdinary() {
        return isOrdinary;
    }

    public void setIsOrdinary(Integer isOrdinary) {
        this.isOrdinary = isOrdinary;
    }

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuSign() {
        return menuSign;
    }

    public void setMenuSign(String menuSign) {
        this.menuSign = menuSign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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

}
