package com.xjt.cloud.admin.manage.entity.project;


import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/5 17:40
 * @Description: 项目权限信息列表
 */
public class Permission extends BaseEntity {
    private String url;//路径
    private String pathName;//路径名称
    private String menuName;
    private String menuSign;
    private Integer type;//1模块，2菜单，3功能
    private Long parentId;//父级id
    //权限名称
    private String permissionName;
    //别名
    private String sign;
    //类型  1后台管理系统权限，2项目
    private Integer perType;
    //1模块，2菜单，3功能
    private Integer permissionType;
    private Integer sort;
    private Long rolePermissionId;//角色关联权限id
    private Long roleId;//角色id
    private Long permissionId;
    private String groupByStr;
    private String projectIds;
    private String projectName;
    private Integer roleType;//角色类型 1管理员  2普通用户
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMenuSign() {
        return menuSign;
    }

    public void setMenuSign(String menuSign) {
        this.menuSign = menuSign;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getGroupByStr() {
        return groupByStr;
    }

    public void setGroupByStr(String groupByStr) {
        this.groupByStr = groupByStr;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
