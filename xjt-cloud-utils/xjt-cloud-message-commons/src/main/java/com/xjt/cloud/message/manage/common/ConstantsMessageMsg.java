package com.xjt.cloud.message.manage.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/6 11:41
 * @Description:
 */
public interface ConstantsMessageMsg {
    //极光推送appKey
    String PUSH_JPUSH_APPKEY = PropertyUtils.getProperty("push.jpush.appkey");
    //极光推送secret
    String PUSH_JPUSH_SECRET = PropertyUtils.getProperty("push.jpush.secret");

    String TTS_CODE = PropertyUtils.getProperty("tts.code");

    Integer APNS_PRODUCTION = 1;
    //查询项目指定权限的UserId
    String FIND_PERMISSION_SIGN_USERID_URL = PropertyUtils.getProperty("find.permission.sign.userId.url");

    String MSG_PUSH_METHOD = "MSG_PUSH_METHOD";//消息推送方式key
    String IS_SMS_PUSH = "IS_SMS_PUSH";//短信推送key
    String IS_PHONE_PUSH = "IS_PHONE_PUSH";//语音推送key
    String IS_URORA_PUSH = "IS_URORA_PUSH";//极光推送key
    String IS_OPERATOR_PUSH = "IS_OPERATOR_PUSH";//运营商推送key
    String IOT_MSG_THREE_PUSH = "IOT_MSG_THREE_PUSH";//第三方物联信息推送


}
