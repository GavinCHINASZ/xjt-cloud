package com.xjt.cloud.safety.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName CheckUser
 * @Author dwt
 * @Date 2020-04-12 11:14
 * @Description 项目检测员实体
 * @Version 1.0
 */
public class CheckUser extends BaseEntity {
    private Long userId;
    private String userName;
    private Long checkProjectId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCheckProjectId() {
        return checkProjectId;
    }

    public void setCheckProjectId(Long checkProjectId) {
        this.checkProjectId = checkProjectId;
    }

    public CheckUser(){

    }
    public CheckUser(Long userId,Long checkProjectId,Long projectId){
        this.setCheckProjectId(checkProjectId);
        this.setUserId(userId);
        this.setProjectId(projectId);
    }
}
