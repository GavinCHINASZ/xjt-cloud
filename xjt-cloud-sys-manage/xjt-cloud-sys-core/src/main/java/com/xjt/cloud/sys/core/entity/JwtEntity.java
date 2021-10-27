package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import javax.xml.crypto.Data;

/**
 * JWT封装数据 实体
 *
 * @author huanggc
 * @date 2020/11/27
 */
public class JwtEntity extends BaseEntity {
    /**
     * jwt_authorization
     */
    private String jwt_authorization;

    /**
     * 用户名
     */
    private String user;

    /**
     * 时间,unixstamp时间戳
     */
    private String time;

    /**
     * 签名
     */
    private String sign;

    public String getJwt_authorization() {
        return jwt_authorization;
    }

    public void setJwt_authorization(String jwt_authorization) {
        this.jwt_authorization = jwt_authorization;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
