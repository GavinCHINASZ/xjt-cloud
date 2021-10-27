package com.xjt.cloud.commons.exception;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 10:00
 * @Description:
 */
public enum ServiceErrCode {
    REQ_ERR(600,"请求异常！"),
    REQ_PARAM_ERR(601,"请求参数异常！"),
    NOTFOUND_RESULT_ERR(602,"未找到结果！"),
    REQ_TOKEN_ERR(603,"提示：登录信息过期，请重新登录！"),
    REQ_PERMISSION_ERR(604,"提示：暂无权限，请联系管理员！"),
    REQ_URL_ERR(605,"没有此请求路径！"),
    NOT_URL_ERR(606,"提示：请重新登录！"),
    SERVER_ERR(607,"提示：服务器繁忙，请稍后重试！"),
    FTP_ERR(630,"ftp错误！"),
    REQ_SERVICE_ERR(608,"提示：服务器繁忙，请稍后重试!");

    private int code;
    private String msg;

    ServiceErrCode(int code,String msg){
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
