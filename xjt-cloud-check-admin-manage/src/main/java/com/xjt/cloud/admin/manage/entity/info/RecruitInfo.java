package com.xjt.cloud.admin.manage.entity.info;

import com.xjt.cloud.commons.base.BaseEntity;

public class RecruitInfo extends BaseEntity {

    private String userName;//用户名称
    private String phone;//手机号码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
