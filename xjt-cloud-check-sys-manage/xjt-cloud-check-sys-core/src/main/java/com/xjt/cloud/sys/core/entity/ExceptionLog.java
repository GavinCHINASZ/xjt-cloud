package com.xjt.cloud.sys.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * @ClassName ExceptionLog
 * @Description pc或app请求错误日志
 * @Author wangzhiwen
 * @Date 2020/9/14 9:36
 **/
public class ExceptionLog extends BaseEntity {
    private Integer type;//类型，1pc 2app
    private String url;//报错误时，请求的url
    private String parameters;//报错时请求的参数
    private String errLog;//返回的错误信息

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getErrLog() {
        return errLog;
    }

    public void setErrLog(String errLog) {
        this.errLog = errLog;
    }
}
