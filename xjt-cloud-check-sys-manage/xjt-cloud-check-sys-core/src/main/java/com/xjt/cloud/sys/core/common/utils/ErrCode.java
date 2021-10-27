package com.xjt.cloud.sys.core.common.utils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 11:01
 * @Description:
 */
public enum ErrCode {
    NOT_USER(601,"用户不存在！"),
    PASSWORD_ERR(602,"密码错误");

    private int code;

    private String msg;

    ErrCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
