package com.xjt.cloud.project.core.entity;

import java.util.List;

/**
 * 树形结构
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
public class TreeNode {
    private String id;
    private String name;
    private String parentId;
    private Boolean isChecked;
    private Boolean selected;
    private List<TreeNode> children;

    public TreeNode(String id, String name, String parentId, List<String> ids) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        if (ids != null && ids.contains(id)) {
            this.isChecked = true;
            this.selected = true;
        } else {
            this.selected = false;
            this.isChecked = false;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean checked) {
        isChecked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
