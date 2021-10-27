package com.xjt.cloud.commons.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/3/12 09:43
 * @Description: 企业微信管理
 */
public class EnterpriseWeChatUser extends BaseEntity {
    private String errCode;//返回码
    private String errMsg;//对返回码的文本描述内容
    private String weChatUserId;//成员UserID
    private Long userId;//绑定的用户id
    private String deviceId;//手机设备号(由深铁运营通在安装时随机生成，删除重装会改变，升级不受影响)
    //成员票据，最大为512字节。 scope为snsapi_userinfo或snsapi_privateinfo，且用户在应用可见范围之内时返回此参数。
    // 后续利用该参数可以获取用户信息或敏感信息。
    private String userTicket;
    private Long expiresIn;//user_token的有效时间（秒），随user_ticket一起返回
    private Integer userType;//成员身份信息，2：超级管理员, 4:分级管理员，5：普通成员
    private String userNames;//用户名称数组

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getWeChatUserId() {
        return weChatUserId;
    }

    public void setWeChatUserId(String weChatUserId) {
        this.weChatUserId = weChatUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
}
