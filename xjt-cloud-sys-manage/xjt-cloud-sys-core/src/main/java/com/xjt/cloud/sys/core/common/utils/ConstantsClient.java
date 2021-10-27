package com.xjt.cloud.sys.core.common.utils;


import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * 客户端资源文件信息与常量类
 *
 * @author wangzhiwen
 * @date 2019/4/25 10:43
 */
public interface ConstantsClient {

    //logo图片路径
    String LOGIN_LOGO_PATH = PropertyUtils.getProperty("login.logo.path");
    //#二维码app扫描上传登录信息接口路径
    String QR_CODE_UP_LOGIN_INFO_URL = PropertyUtils.getProperty("qr.code.up.login.info.url");
    //是否要删除token信息 true为删除，默认不删除
    String REMOVE_ACCESS_TOKEN = PropertyUtils.getProperty("remove.access.token");



    String IS_PERMISSION_NOT_INIT = PropertyUtils.getProperty("is.permission.not.init");

    String PHONE_FAIL = "手机号码不正确！";
    String CAPTCHA_FAIL = "验证码不正确！";
    String CAPTCHA_NULL_FAIL = "验证码为空！";
    String CAPTCHA_EXPIRE = "验证码过期！";
    String REGISTER_FAIL  = "注册失败！";
    String SEND_MSG_FAIL  = "验证码发送失败！";
    String USER_LOGIN_NAME_EXIST  = "用户账号已存在！";
    String USER_PHONE_EXIST  = "用户手机号码已存在！";

    String SECURITY_LOG_TYPE_MODIFY_USER = "SECURITY_LOG_TYPE_MODIFY_USER";//修改用户日志标志

    String IS_METRO = "IS_METRO";//数据词典中是否是地铁项目的code
    /**
     * 主题配置
     */
    String THEME_CONFIG = "THEME_CONFIG";
    /**
     * PC主题颜色
     */
    String PC_THEME_COLOR = "PC_THEME_COLOR";

    String SHE_QU_YUN_JWT_AUTHORIZE_CALLBACK_TOKEN = PropertyUtils.getProperty("she.qu.yun.jwt.authorize.callback.token");
    String SHE_QU_YUN_JWT_AUTHORIZE_PUBLIC_KEY = PropertyUtils.getProperty("she.qu.yun.jwt.authorize.public.key");

    // jwt重定向URL
    String JWT_SEND_REDIRECT_URL = PropertyUtils.getProperty("jwt.send.redirect.url");
}
