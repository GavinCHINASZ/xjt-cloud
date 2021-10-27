package com.xjt.cloud.sys.core.entity;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/5 17:40
 * @Description: 项目权限信息列表
 */
public class Permission {
    private String url;

    private Long projectId;

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
}
