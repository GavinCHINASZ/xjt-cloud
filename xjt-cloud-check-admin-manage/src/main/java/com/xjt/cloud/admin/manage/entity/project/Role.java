package com.xjt.cloud.admin.manage.entity.project;

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
    private Long userId;
    private Long orgUserRoleId;//部门用户角色对应id
    //所属:1管理后台 2项目
    private Integer sourceType;
    //项目名称
    private String projectName;

    //所属ID(平台ID,项目ID)
    private Long sourceId;
    //是否管理员:1是,2不是
    private Integer isAdmin;
    //是否管理员:是否超级管理员，1普通权限，2超级管理员
    private Integer superAdmin;

    //是否为普通成员 1是，2 不是
    private Integer isOrdinary;
    //权限ID
    private List<Long> permissionIds;
    //权限ID
    private Long permissionId;
    //部门用户id
    private Long orgUserId;
    //部门名称
    private String orgName;
    //公司名称
    private String coName;
    //成员id
    private List<Long>  orgUserIds;
    //权限名称
    private String permissionName;
    //角色ID
    private List<Long> ids;
    private String idsStr;
    //用户名
    private String loginName;
    //员工姓名(成员姓名)
    private String userName;
    //手机号码
    private String phone;

    public Integer getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Integer superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getIdsStr() {
        return idsStr;
    }

    public void setIdsStr(String idsStr) {
        this.idsStr = idsStr;
    }

    public Long getOrgUserRoleId() {
        return orgUserRoleId;
    }

    public void setOrgUserRoleId(Long orgUserRoleId) {
        this.orgUserRoleId = orgUserRoleId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getSourceTypeDesc(){
        if (sourceType != null){
            if(sourceType == 1){
                return "管理后台";
            }else if(sourceType == 2){
                return "项目";
            }
        }
        return null;
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
