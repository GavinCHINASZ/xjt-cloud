package com.xjt.cloud.project.core.entity.project;

import java.util.List;

/**
 * @ClassName ProjectRole 项目角色实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class TreeNode {
    private String id;
    private String name;
    private String parentId;
    private Boolean isChecked;
    private List<TreeNode> childs;
    public TreeNode(String id, String name, String parentId, List<String> ids) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        if(ids!=null && ids.contains(id)){
            this.isChecked = true;
        }else{
            this.isChecked = false;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public List<TreeNode> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeNode> childs) {
        this.childs = childs;
    }
}
