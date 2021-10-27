package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 16:52
 * @Description: 微信登录逻辑接口实现类
 */
@Service
public class WeChatUtils {

    /**@MethodName: getWeChatUser
     * @Description: 根据code获取用户信息
     * @Param: [code]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Author: zhangZaiFa
     * @Date:2019/12/3 17:11
     **/
    public static JSONObject getCodeWeChatUser(String code) {
        String secretStr = Constants.SECRET;
        String appIdStr = Constants.APPID;
        JSONObject jsonObject = null;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appIdStr + "&secret=" + secretStr + "&code=" + code + "&grant_type=authorization_code";
        try {
            jsonObject = HttpUtils.httpGets(url);
            if(jsonObject.getString("openid")!=null){
                jsonObject = getOpenIdWeChatUser(jsonObject.getString("openid"));
            }
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
        return jsonObject;
    }

    /**@MethodName: getOpenIdWeChatUser
     * @Description: 根据OpenId查询用户信息
     * @Param: [openId]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Author: zhangZaiFa
     * @Date:2019/12/4 9:32
     **/
    public static JSONObject getOpenIdWeChatUser(String openId) {
        JSONObject jsonObject = null;
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN" ;
        try {
            jsonObject = HttpUtils.httpGets(url);
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
        return jsonObject;
    }


    /**@MethodName: refreshToken
     * @Description: 延长授权时间
     * @Param: [refresh_token]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/12/3 17:25
     **/
    public static String refreshToken(String refresh_token) {
        String appIdStr = Constants.APPID;
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                "appid=" + appIdStr + "&refresh_token=" + refresh_token + "&grant_type=refresh_token";
        try {
            JSONObject jsonObject = HttpUtils.httpGets(url);
            return jsonObject.getString("refresh_token");
        } catch (Exception e) {
            SysLog.error(e);
        }
        return null;
    }

    /**@MethodName: weChatSendMessage
     * @Description: 微信公众号推送信息
     * @Param: [openId, templateId, url, data] openId 微信号  templateId 信息模板ID  url 跳转链接   data数据包
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/3 17:01
     **/
    public static String weChatSendMessage(String openId,String templateId,String url,JSONObject data) {
        JSONObject params = new JSONObject();
        params.put("touser",openId);
        params.put("template_id",templateId);
        params.put("url",url);
        params.put("data",data);
        //获取access_token
        String accessToken = getAccessToken();
        String sendUrl ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        String scuess = HttpUtils.sendHttpPostData(sendUrl,params.toJSONString());
        return scuess;
    }

    /**@MethodName: getAccessToken
     * @Description: 获取AccessToken
     * @Param: []
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/12/3 17:19
     **/
    public  static String getAccessToken(){
        String secretStr = Constants.SECRET;
        String appIdStr = Constants.APPID;
        String access_tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appIdStr+"&secret="+secretStr;
        JSONObject jsonObject = HttpUtils.httpGets(access_tokenUrl);
        SysLog.debug(jsonObject.toJSONString());
        return jsonObject.get("access_token").toString();
    }

    /*public static void main(String[] args) {
        String openId = "onnphwTpK-CP4Scne2OfCpLZgDTI";
        JSONObject jsonObject = new JSONObject();
        JSONObject remark = new JSONObject();
        JSONObject data = getOpenIdWeChatUser(openId);
        SysLog.debug(data);
        remark.put("value", data.get("nickname"));
        remark.put("color", "#303030");
        jsonObject.put("userName",remark);
        weChatSendMessage(openId,"5BzKDayyV9aRzh46Uf6z3-47ZRqrSeEk0jVg0TPdEfg","http://192.168.0.100:9080",jsonObject);
    }*/
}
