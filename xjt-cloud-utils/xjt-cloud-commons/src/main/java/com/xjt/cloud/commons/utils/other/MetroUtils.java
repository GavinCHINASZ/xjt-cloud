package com.xjt.cloud.commons.utils.other;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.entity.EnterpriseWeChatUser;
import com.xjt.cloud.commons.utils.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Pattern;

/**
 * 地铁接口操作类
 *
 * @author wangzhiwen
 * @date 2021/2/24 10:50
 **/
public class MetroUtils {

    /**
     * 功能描述:获取企业微信信息
     *
     * @param code code
     * @return com.xjt.cloud.sys.core.entity.EnterpriseWeChatUser
     * @author wangzhiwen
     * @date 2020/3/12 13:48
     */
    public static EnterpriseWeChatUser getEnterpriseWeChatUser(String code) {
        String url = "http://" + PropertyUtils.getProperty("enterprise.access.location") + "/cgi-bin/user/getuserinfo?access_token=" + getToken(false) + "&code=" + code;
        EnterpriseWeChatUser enterpriseWeChatUser = new EnterpriseWeChatUser();
        try {
            JSONObject jsonObject = HttpUtils.httpGet(url);
            SysLog.error(" getuserinfo=============================:" + jsonObject.toJSONString());
            // 出错返回码，为0表示成功，非0表示调用失败
            String errCode = jsonObject.getString("errcode");
            if ("0".equals(errCode)) {
                // 成员UserID
                enterpriseWeChatUser.setWeChatUserId(jsonObject.getString("UserId"));
                // 手机设备号(由深铁运营通在安装时随机生成，删除重装会改变，升级不受影响)
                enterpriseWeChatUser.setDeviceId(jsonObject.getString("DeviceId"));
                // 成员票据，最大为512字节。 scope为snsapi_userinfo或snsapi_privateinfo，且用户在应用可见范围之内时返回此参数。
                // 后续利用该参数可以获取用户信息或敏感信息。
                enterpriseWeChatUser.setUserTicket(jsonObject.getString("user_ticket"));
                // user_token的有效时间（秒），随user_ticket一起返回
                enterpriseWeChatUser.setExpiresIn(jsonObject.getLong("expires_in"));
                // 成员身份信息，2：超级管理员, 4:分级管理员，5：普通成员
                enterpriseWeChatUser.setUserType(jsonObject.getInteger("usertype"));
            } else {
                enterpriseWeChatUser.setErrCode(errCode);
                enterpriseWeChatUser.setErrMsg(jsonObject.getString("errmsg"));
                SysLog.error(PropertyUtils.getProperty("enterprise.appid") + " token 获取错误:" + errCode + " msg:" + enterpriseWeChatUser.getErrMsg());
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return enterpriseWeChatUser;
    }

    /**
     * 功能描述: 从缓存中获取token
     *
     * @param isClear boolean
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2020/3/12 11:49
     */
    public static String getToken(boolean isClear) {
        String key = "enterpriseWeChatToken" + PropertyUtils.getProperty("enterprise.agentid");
        String token = "";
        if (isClear) {
            // 判断是否需要重新请求token
            CacheUtils.initRedisUtils().del(key);
        } else {
            token = CacheUtils.initRedisUtils().getString(key);
        }

        SysLog.error(" token=============================:" + token);
        if (StringUtils.isEmpty(token)) {
            token = getToken(key);
        } else {
            String[] tokenTime = token.split("_time_");
            long time = DateUtils.getDateTime();
            if (Long.parseLong(tokenTime[1]) < time) {
                // 判断token是否过期
                token = getToken(key);
            } else {
                token = tokenTime[0];
            }
        }
        return token;
    }

    /**
     * 功能描述:获取企业微信权限签名
     *
     * @return String
     * @author wangzhiwen
     * @date 2020/4/3 14:14
     */
    public static String getJsapiTicket(boolean isClear, boolean isAgent) {
        String key;
        String jsapiTicket = "";
        if (isAgent) {
            // 判断是否需要重新请求token
            key = "jsapiTicketAgent" + PropertyUtils.getProperty("enterprise.agentid");
        } else {
            key = "jsapiTicket" + PropertyUtils.getProperty("enterprise.agentid");
        }
        if (isClear) {
            // 判断是否需要重新请求token
            CacheUtils.initRedisUtils().del(key);
        } else {
            jsapiTicket = CacheUtils.initRedisUtils().getString(key);
        }
        SysLog.error(" key=============================:" + key);
        SysLog.error(" jsapiTicket=============================:" + jsapiTicket);
        if (StringUtils.isEmpty(jsapiTicket)) {
            jsapiTicket = getJsapiTicket(key, isAgent);
        } else {
            String[] jsapiTicketTime = jsapiTicket.split("_time_");
            long time = DateUtils.getDateTime();
            SysLog.error(" jsapiTicket=============================:" + jsapiTicketTime[1] + "//////////////////" + time);
            if (Long.parseLong(jsapiTicketTime[1]) < time) {
                //判断jsapiTicket是否过期
                jsapiTicket = getJsapiTicket(key, isAgent);
            } else {
                jsapiTicket = jsapiTicketTime[0];
            }
        }
        return jsapiTicket;
    }

    /**
     * 功能描述: 获取签名
     *
     * @param url 签名url
     * @return JSONObject
     * @author wangzhiwen
     * @date 2020/4/3 14:39
     */
    public static JSONObject getSign(String url, boolean isAgent) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        String noncestr = "xjt";
        String signature = Sha1Util.SHA1Digest("jsapi_ticket=" + getJsapiTicket(false, isAgent) +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url);
        SysLog.error("signature=====================" + signature);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("signature", signature);
        jsonObject.put("noncestr", noncestr);
        jsonObject.put("url", url);
        return jsonObject;
    }
    /**
     * 发送运营通提醒
     *
     * @param userIds userId
     * @param content 内容
     * @author zhangZaiFa
     * @date 2020/3/17 10:40
     **/
    public static boolean sendMsg(String userIds, String content) {
        String isOperatorPushStr = DictUtils.getDictItemValueByDictAndItemCode("MSG_PUSH_METHOD", "IS_OPERATOR_PUSH");
        // 是否推送运营通提醒
        if(isOperatorPushStr != null){
            int isOperatorPush = Integer.valueOf(isOperatorPushStr);
            if(isOperatorPush == 1) {
                StringBuilder sb = new StringBuilder();
                // 通过本平台用户ID关联获取运营商的用户ID
                String touser = getTouserIds(userIds);
                return sendMsgByTouserIds(touser,content);
            }
        }
        return false;
    }

    /**
     * 以平台的用户关联的运营通id得到运营通用户的ids
     *
     * @param userIds 平台的用户id
     * @author wangzhiwen
     * @date 2021/2/24 11:10
     * @return java.lang.String
     */
    private static String getTouserIds(String userIds){
        //获取运营通token
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        // 通过本平台用户ID关联获取运营商的用户ID
        String touser = HttpUtils.sendHttpPostData(PropertyUtils.getProperty("enterprise.domain.url") + "/enterprise/we/chat/getUserLoginNames","userIds=" + userIds);
        touser = p.matcher(touser).replaceAll("");
        SysLog.info(userIds + "《======touser========》:" + touser.trim());
        return touser;
    }

    /**
     * 发送运营通文本信息
     *
     * @param touserIds touserIds
     * @param content content
     * @author wangzhiwen
     * @date 2021/2/24 11:16
     * @return boolean
     */
    private static boolean sendMsgByTouserIds(String touserIds, String content){
        try {
            // 调用运营通文本信息推送接口
            String url = "http://" + PropertyUtils.getProperty("enterprise.domain.url") + "/cgi-bin/message/send?access_token=" + getToken(false);

            JSONObject jsonObject = new JSONObject(5);
            jsonObject.put("touser", touserIds);
            jsonObject.put("msgtype", "text");
            jsonObject.put("agentid", PropertyUtils.getProperty("enterprise.agentid"));
            JSONObject text = new JSONObject(1);
            text.put("content", content);
            jsonObject.put("text", text);
            SysLog.info(jsonObject.toJSONString() + "-----------》请求参数");
            JSONObject obj = HttpUtils.httpPost(url, jsonObject.toJSONString());

            SysLog.info("《=========================运营通消息调用结果=========================》" + obj.toJSONString());
            if ("0".equals(obj.getString("errcode"))){
                return true;
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return false;
    }

    /**
     * 功能描述:获得JsapiTicket
     *
     * @param key key
     * @param isAgent boolean
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2020/4/3 14:35
     */
    private static String getJsapiTicket(String key, boolean isAgent) {
        String accessToken = getToken(false);
        String url = "http://" + PropertyUtils.getProperty("enterprise.access.location");
        if (isAgent) {
            url += "/cgi-bin/ticket/get?access_token=" + accessToken + "&type=agent_config";
        } else {
            url += "/cgi-bin/get_jsapi_ticket?access_token=" + accessToken;
        }
        String jsapiTicket;
        try {
            JSONObject jsonObject = HttpUtils.httpGet(url);
            SysLog.error(" jsapiTicket=============================:" + jsonObject.toJSONString());
            // 出错返回码，为0表示成功，非0表示调用失败
            String errCode = jsonObject.getString("errcode");
            if ("0".equals(errCode)) {
                // 获取到的凭证，最长为512字节
                jsapiTicket = jsonObject.getString("ticket");
                // 凭证的有效时间（秒）
                Long expiresIn = jsonObject.getLong("expires_in");
                expiresIn = DateUtils.getDateTime() + (expiresIn * 1000);
                CacheUtils.initRedisUtils().set(key, jsapiTicket + "_time_" + expiresIn, expiresIn);
            } else {
                SysLog.error(PropertyUtils.getProperty("enterprise.appid") + " jsapiTicket 获取错误:" + errCode + " msg:" + jsonObject.getString("errmsg"));
                return null;
            }
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
        return jsapiTicket;
    }

    /**
     * 功能描述:从企业微信中获取token
     *
     * @param key key
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2020/3/12 11:49
     */
    private static String getToken(String key) {
        String url = "http://" + PropertyUtils.getProperty("enterprise.access.location") + "/cgi-bin/gettoken?corpid=" +
                PropertyUtils.getProperty("enterprise.appid") + "&corpsecret=" + PropertyUtils.getProperty("enterprise.corpsecret");
        String token;
        try {
            JSONObject jsonObject = HttpUtils.httpGet(url);
            SysLog.error(" gettoken=============================:" + jsonObject.toJSONString());
            // 出错返回码，为0表示成功，非0表示调用失败
            String errCode = jsonObject.getString("errcode");
            if ("0".equals(errCode)) {
                // 获取到的凭证，最长为512字节
                token = jsonObject.getString("access_token");
                // 凭证的有效时间（秒）
                Long expiresIn = jsonObject.getLong("expires_in");
                expiresIn = DateUtils.getDateTime() + (expiresIn * 1000);
                CacheUtils.initRedisUtils().set(key, token + "_time_" + expiresIn, expiresIn);
            } else {
                SysLog.error(PropertyUtils.getProperty("enterprise.appid") + " token 获取错误:" + errCode + " msg:" + jsonObject.getString("errmsg"));
                return null;
            }
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
        return token;
    }

    /**
     * 和利时智慧车站平台数据接口-消检通
     * 通过该接口可获得综合监控系统的实时测点数据
     *
     * @param data 消息体
     * @return String
     */
    public static String heLiShiIntegratedMonitoring(String data) {
        SysLog.info("《=========和利时智慧车站平台数据接口-消检通=========》data=" + data);
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;

            try {
                httpclient = HttpClients.createDefault();

                String url = PropertyUtils.getProperty("he.li.shi.integrated.monitoring.url");
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(data, ContentType.create("application/json", "UTF-8"));
                httppost.setEntity(stringentity);

                String appId = PropertyUtils.getProperty("he.li.shi.integrated.monitoring.app.id");
                String privateKey = PropertyUtils.getProperty("he.li.shi.integrated.monitoring.private.key");
                httppost.addHeader("appId", appId);
                httppost.addHeader("privateKey", privateKey);

                SysLog.info("《=========和利时智慧车站平台数据接口-消检通=========》请求参数封装httppost=" + httppost.toString());

                // 发post请求
                httpresponse = httpclient.execute(httppost);

                // utf-8参数防止中文乱码
                response = EntityUtils.toString(httpresponse.getEntity(), "utf-8");
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }

                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            SysLog.error("和利时智慧车站平台数据接口-消检通---->" + e);
        }

        return response;
    }
}
