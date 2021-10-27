package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @ClassName Organization 建筑物实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class InviteConnection extends BaseEntity {

    //随机码
    private String randomCode;

    //项目ID
    private Long projectId;

    //状态 0、不需要重新生成  1、重新生成
    private Integer state;

    //倒计时时间
    private Date countdownTime;

    public Date getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(Date countdownTime) {
        this.countdownTime = countdownTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public Long getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
