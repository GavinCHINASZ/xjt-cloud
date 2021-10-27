package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * 用户实体
 *
 * @author wangzhiwen
 * @date 2019/4/29 0029 13:45
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
    private String headPortrait;//头像
    private String socketId;
    private Boolean registerStatus;//注册状态，刚注册为true

    /**
     * 主题颜色
     * (默认)1消防红, 2酒红色, 3大红色, 4深蓝色, 5海军蓝, 6橘红色
     */
    private String themeColor;

    /**
     * 主题图片路径
     */
    private String themeImgUrl;

    /**
     * APP使用的主题
     */
    private String themeImgNo;

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getThemeImgUrl() {
        return themeImgUrl;
    }

    public void setThemeImgUrl(String themeImgUrl) {
        this.themeImgUrl = themeImgUrl;
    }

    public String getThemeImgNo() {
        return themeImgNo;
    }

    public void setThemeImgNo(String themeImgNo) {
        this.themeImgNo = themeImgNo;
    }
}
