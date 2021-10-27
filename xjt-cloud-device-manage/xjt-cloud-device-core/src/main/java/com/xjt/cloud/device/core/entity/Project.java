package com.xjt.cloud.device.core.entity;


/**
 * @ClassName Project 项目实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class Project {
    private Long id;
    //项目名称
    private String projectName;

    //描述
    private String description;

    //平台Id
    private Long cloudId;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCloudId() {
        return cloudId;
    }

    public void setCloudId(Long cloudId) {
        this.cloudId = cloudId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
