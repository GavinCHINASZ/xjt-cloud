package com.xjt.cloud.task.core.entity;

import java.util.List;

/**
 * @ClassName Organizatioin
 * @Author dwt
 * @Date 2019-08-07 16:58
 * @Version 1.0
 */
public class TaskOrganization {
    private Long id;
    private String orgName;
    private Integer type;
    private List<TaskOrganization> child;
    private List<TaskDeviceCheckPoint> checkPointList;
    private Integer checkPointCount;
    private Long parentId;
    private Integer sel;
    private Integer notSel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<TaskOrganization> getChild() {
        return child;
    }

    public void setChild(List<TaskOrganization> child) {
        this.child = child;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<TaskDeviceCheckPoint> getCheckPointList() {
        return checkPointList;
    }

    public void setCheckPointList(List<TaskDeviceCheckPoint> checkPointList) {
        this.checkPointList = checkPointList;
    }

    public Integer getCheckPointCount() {
        return checkPointCount;
    }

    public void setCheckPointCount(Integer checkPointCount) {
        this.checkPointCount = checkPointCount;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSel() {
        return sel;
    }

    public void setSel(Integer sel) {
        this.sel = sel;
    }

    public Integer getNotSel() {
        return notSel;
    }

    public void setNotSel(Integer notSel) {
        this.notSel = notSel;
    }
}
