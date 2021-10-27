package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 17:16
 * @Description:
 */
public class WeChatUser extends BaseEntity {
    //授权用户唯一标识
    private String openid;

    //且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段。
    private String unionid;

    //接口调用凭证
    private String accessToken;

    //用户刷新access_token
    private String refreshToken;
    //access_token接口调用凭证超时时间，单位（秒）
    private int expiresIn;
    //绑定的用户id
    private Long userId;
    //微信绑定的后台用户登录账号
    private String loginName;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
