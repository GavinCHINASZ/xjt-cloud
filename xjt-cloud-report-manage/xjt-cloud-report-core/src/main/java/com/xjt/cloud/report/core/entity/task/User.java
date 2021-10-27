package com.xjt.cloud.report.core.entity.task;

/**
 * @ClassName User
 * @Author Administrator
 * @Date 2019-08-09 8:48
 * @Description TODO
 * @Version 1.0
 */
public class User {
    //部门员工角色id
    private Long orgUserRoleId;
    //用户名称
    private String userName;
    //用户id
    private Long userId;
    //角色id
    private Long roleId;

    public Long getOrgUserRoleId() {
        return orgUserRoleId;
    }

    public void setOrgUserRoleId(Long orgUserRoleId) {
        this.orgUserRoleId = orgUserRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
