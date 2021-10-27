package com.xjt.cloud.task.core.entity.project;

import com.xjt.cloud.task.core.entity.User;

import java.util.List;

/**
 * @ClassName Role
 * @Author dwt
 * @Date 2019-08-09 8:47
 * @Description 角色实体
 * @Version 1.0
 */
public class Role {
    //角色id
    private Long id;
    //角色名称
    private String roleName;
    private Long sourceId;
    //部门员工列表
    private List<User> userList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
