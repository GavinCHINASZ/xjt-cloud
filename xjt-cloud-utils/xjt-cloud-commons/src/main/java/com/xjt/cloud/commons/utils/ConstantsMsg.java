package com.xjt.cloud.commons.utils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/1 09:53
 * @Description: 提示信息常量公共类
 */
public interface ConstantsMsg {
    String SEND_MSG_FAIL = "提示：短信发送异常，请联系管理员";

    String PATH_NULL = "提示：服务器繁忙，请稍后重试";//"路径为空";
    String UPLOAD_FAIL = "提示：文件/图片上传失败，请重新上传";
    String UPLOAD_FILE_NULL = "提示：上传文件为空，请重新上传";
    String READ_CONFIG_FILE_FAIL = "提示：服务器繁忙，请稍后重试";//"读取配置文件失败";
    String FILE_NOT_EXIST = "提示：文件不存在，请重新选择";
    String DEL_FAIL = "提示：文件删除失败，请重新删除";
    String PATH_FAIL = "提示：服务器繁忙，请稍后重试";//"地址不正确";
    String UPLOAD_FILE_READ_FAIL = "提示：请检查文件内容是否正确";
    String NOT_URL = "提示：服务器繁忙，请稍后重试";//"没有此请求路径";
    String FILE_TYPE_FAIL = "提示：文件类型错误，请确认文件格式";
    String CONNECTION_TIMEOUT = "提示：服务器繁忙，请稍后重试";//"连接超时";
    String CONNECTION_FAIL = "提示：服务器繁忙，请稍后重试";//"连接服务失败";
    String CLOSE_SERVICE_FAIL = "提示：服务器繁忙，请稍后重试";//"服务关闭异常";
    String CLOSE_FAIL = "提示：服务器繁忙，请稍后重试";//"关闭异常";
    String CREATE_FILE_FAIL = "提示：内容创建失败，请重试";
    String UPDATE_FILE_FAIL = "提示：文件更新错误，请重新再试";
    String GET_SERVER_FILE_FAIL = "提示：获取文件失败，请重新再试";
    String LIST_FILE_NAMES_FAIL = "提示：服务器繁忙，请稍后重试";//"列出远程目录下所有的文件时出现异常";

    String REFRESH_TOKEN_FAIL = "提示：登录信息过期，请重新登录";//"刷新token，获取refresh_token失败";
    String GET_USER_PERMISSION_FAIL = "提示：请刷新页面重新登录";//"用户权限信息获取失败";
    String USER_NOT_PERMISSION = "提示：暂无权限，请联系管理员";
    String LOGIN_QR_NO_FAIL = "提示：请刷新页面重新登录";//"登录二维码生成失败";
    String LOGIN_CAPTCHA_FAIL = "提示：请刷新页面重新登录";//"登录验证码生成失败";
    String USER_LOGIN_FAIL = "提示：请重新登录";//"用户不存在或未登录！";
    String EXPIRE_TOKEN = "登录过期，请重新登录";
    String USER_NOT_EXIST = "提示：用户不存在，请注册";
    String GET_USER_FAIL = "提示：请重新登录";//"获取用户信息失败";
    String QR_NO_FAIL = "提示：请刷新页面";//"二维码生成失败";
}
