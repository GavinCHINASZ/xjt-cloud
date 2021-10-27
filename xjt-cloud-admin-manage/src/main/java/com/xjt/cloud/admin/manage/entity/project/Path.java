package com.xjt.cloud.admin.manage.entity.project;


import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/5 17:40
 * @Description: 项目权限信息列表
 */
public class Path extends BaseEntity {
    private String url;//路径
    private Long projectId;//项目id
    private Long id;//路径url
    private String pathName;//路径名称
    private Integer type;//1模块，2菜单，3功能
    private Long parentId;//父级id
    private Integer projectType;//项目类型1　客户pc　2app　3管理后台 4项目外
    private Integer orderNum;//排序序号
    private Long permissionId;//权限ID
    private List<Integer> projectTypes;//项目类型1　客户pc　2app　3管理后台
    private List<Path> children;//子Path
    private Boolean checked;//是否选中


    public List<Integer> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(List<Integer> projectTypes) {
        this.projectTypes = projectTypes;
    }

    public Long getPermissionId() {
        return permissionId;
    }



    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
    public String getState() {
        return "open";
    }


    public String getText() {
        return this.pathName+"----->"+this.url;
    }

    public List<Path> getChildren() {
        return children;
    }

    public void setChildren(List<Path> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
