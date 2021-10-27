package com.xjt.cloud.admin.manage.entity.sys;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/29 0029 13:45
 * @Description:
 */
public class User extends BaseEntity {
    //用户名
    private String loginName;
    //密码
    private String password;
    //员工姓名(成员姓名)
    private String userName;
    //手机号码
    private String phone;
    //项目名称
    private String projectName;
    private Integer modifyStatus;//用户信息修改状态 1是主要信息未做修改 2登录名称已做修改
    private String key;//验证码的key
    private String captcha;//验证码
    private String accessToken;
    private String oldPassword;//旧密码
    private String headPortrait;//头像

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getModifyStatus() {
        return modifyStatus;
    }

    public void setModifyStatus(Integer modifyStatus) {
        this.modifyStatus = modifyStatus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getModifyStatusDesc(){
        if (getModifyStatus() != null){
            if (1 == modifyStatus){
                return "未修改";
            }else if (2 == modifyStatus){
                return "已修改";
            }
        }
        return null;
    }

    public String getStatusDesc(){
        if (getModifyStatus() != null){
            if (0 == getStatus()){
                return "未激活";
            }else if (1 == getStatus()){
                return "已激活";
            }else if (99 == getStatus()){
                return "已删除";
            }
        }
        return null;
    }
}
