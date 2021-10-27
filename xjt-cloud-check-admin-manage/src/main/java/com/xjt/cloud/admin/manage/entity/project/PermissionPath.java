package com.xjt.cloud.admin.manage.entity.project;


import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/5 17:40
 * @Description: 项目权限信息列表
 */
public class PermissionPath extends BaseEntity {
    private String url;//路径
    private Long permissionId;//父级id
    private Long pathId;//接口路径ID
    private Integer perType;//URL类型 1后台管理系统权限，2项目,3pc非项目内
    private Integer permissionType;//1模块，2菜单，3功能
    private Integer projectType;//项目类型1　客户pc　2app　3管理后台
    private String permissionName;//权限名称
    private String pathName;//Path名称
    private String type;//管理员默认选择类型 1是 2否
    private List<Long> pathIds;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    public List<Long> getPathIds() {
        return pathIds;
    }

    public void setPathIds(List<Long> pathIds) {
        this.pathIds = pathIds;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }


    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }
}
