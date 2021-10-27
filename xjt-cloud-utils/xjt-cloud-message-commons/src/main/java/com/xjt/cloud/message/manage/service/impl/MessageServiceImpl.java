package com.xjt.cloud.message.manage.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.google.gson.JsonArray;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xjt.cloud.commons.abstracts.MsgAbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.commons.utils.other.MetroUtils;
import com.xjt.cloud.message.manage.common.ConstantsMessageMsg;
import com.xjt.cloud.message.manage.common.util.SendMsgThread;
import com.xjt.cloud.message.manage.common.util.SendPhoneUtils;
import com.xjt.cloud.message.manage.common.util.SendSmsUtils;
import com.xjt.cloud.message.manage.common.util.VoiceUtils;
import com.xjt.cloud.message.manage.dao.message.MessageDao;
import com.xjt.cloud.message.manage.entity.Message;
import com.xjt.cloud.message.manage.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 消息
 *
 * @author zhangZaiFa
 * @date 2019/4/29 0029 13:45
 */
@Service
public class MessageServiceImpl extends MsgAbstractService implements MessageService {
    @Autowired
    private MessageDao messageDao;

    /**
     * saveMessage 保存消息，并发送极光推送
     *
     * @param msgType 类型
     * @param userIds userId
     * @param title 标题
     * @param eventType 事件类型
     * @param fontColor fontColor
     * @param content 内容
     * @param url url
     * @param projectId 项目ID
     * @return com.xjt.cloud.commons.utils.Data
     * @author dwt
     * @date 2019/11/14 16:24
     **/
    @Override
    public Data saveMessageUser(Integer msgType, List<Long> userIds, String title, Integer eventType, Integer fontColor, String content, String url, Long projectId,
                                Long recordId, String data,JSONObject json) {
        ThreadPoolUtils.getInstance().execute(new SendMsgThread(msgType, userIds, null, title, eventType, fontColor, content, url, projectId, recordId,
                data, json,1));
        return Data.isSuccess();
    }

    /**
     * 按角色推送消息
     *
     * @author dwt
     * @date 2020-08-20 13:46
     * @Param: [msgType, roleSignList, content, url, projectId, recordId, data]
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveMessageRole(Integer msgType, List<String> roleSignList, String title,Integer eventType,Integer fontColor, String content, String url, Long projectId,
                                Long recordId, String data,JSONObject jsonObject) {
        ThreadPoolUtils.getInstance().execute(new SendMsgThread(msgType,null, roleSignList, title, eventType, fontColor, content, url, projectId, recordId,
                data, jsonObject,2));
        return Data.isSuccess();
    }

    /**
     * saveMessage 保存消息，并发送极光推送
     *
     * @param msgType 类型
     * @param userIds userId
     * @param title 标题
     * @param eventType 事件类型
     * @param fontColor fontColor
     * @param content 内容
     * @param url url
     * @param projectId 项目ID
     * @param recordId recordId
     * @param data data
     * @param json JSONObject
     * @author zhangZaiFa
     * @date 2019/11/14 16:24
     **/
    @Override
    public void saveMessageUserThread(Integer msgType, List<Long> userIds, String title, Integer eventType, Integer fontColor, String content, String url, Long projectId,
                                      Long recordId, String data, JSONObject json) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            saveMessageCV5(msgType, userIds, title, eventType, fontColor, content, url, projectId, recordId, data, json);
        }else {
            saveMessage(msgType, userIds, title, eventType, fontColor, content, url, projectId, recordId, data, json);
        }
    }


    /**
     * saveMessage 保存消息，并发送极光推送
     *
     * @Param: [msgType, roleSignList, content, url, projectId, recordId, data]
     * @author zhangZaiFa
     * @date 2019/11/14 16:24
     **/
    @Override
    public void saveMessageRoleThread(Integer msgType, List<String> roleSignList, String title,Integer eventType,Integer fontColor, String content, String url,
                                      Long projectId, Long recordId, String data, JSONObject jsonObject) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            saveMessageRoleThreadCV5(msgType, roleSignList, title,eventType,fontColor, content, url,
                     projectId, recordId, data, jsonObject);
        }else {
            try {
                JSONObject obj = HttpUtils.httpGetRestTemplate(ConstantsMessageMsg.FIND_PERMISSION_SIGN_USERID_URL, "{\"projectId\":" + projectId + ",\"signList\":"
                        + JSONArray.toJSONString(roleSignList) + "}", "json");

                List<Long> userIdList = null;
                if (Integer.valueOf(obj.get("status").toString()) == 200) {
                    userIdList = JSONArray.parseArray(obj.get("object").toString(), Long.class);
                }
                //按用户发送信息
                saveMessage(msgType, userIdList, title, eventType, fontColor, content, url, projectId, recordId, data, jsonObject);
            } catch (Exception ex) {
                SysLog.error(ex);
            }
        }
    }

    /**
     * @Description 保存信息发送
     *
     * @param msgType
     * @param userIdList
     * @param title
     * @param eventType
     * @param fontColor
     * @param content
     * @param url
     * @param projectId
     * @param recordId
     * @param data
     * @param json
     * @author wangzhiwen
     * @date 2021/1/8 15:08
     * @return void
     */
    private void saveMessage(Integer msgType, List<Long> userIdList, String title, Integer eventType, Integer fontColor, String content, String url, Long projectId,
                             Long recordId, String data, JSONObject json){
        try {
            int eventLevel = 10;//默认为非4级类信息
            int type = 1;//信息类型 1个人信息推送，2大屏弹框信息
            String phoneUserIds = null;
            String smsUserIds = null;
            Set<String> msgUserIdsSet = null;
            SysLog.info("发送消息：" + json.toJSONString());
            if (CollectionUtils.isEmpty(userIdList)){
                return;
            }
            SysLog.info("用户id：" + userIdList.stream().map(s -> s.toString()).collect(Collectors.joining("_")));

            if (msgType == -4 || msgType == 2 || msgType == 3 || msgType == 4) {//判断是否是-4、项目审核 2、工作通知 3、审核通知 4、报修通知 ，这几种类型不需要经4级告警判断
                msgUserIdsSet = userIdList.stream().map(s -> s.toString()).collect(Collectors.toSet());
            }else{//判断是否是所有人都能看信息,有角色权限
                if (msgType == 16) {//如果不是报表信息，则按级别处理
                    msgUserIdsSet = userIdList.stream().map(s -> s.toString()).collect(Collectors.toSet());
                }else{
                    String saveMsgType = getMsgType(msgType, eventType);//得到消息类型
                    //获取项目消息级别配置,
                    JSONObject projectMsgLevelScreenJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_MSG_LEVEL_CACHE_KEY, projectId);
                    if (StringUtils.isNotEmpty(saveMsgType) && projectMsgLevelScreenJson != null) {//根据项目消息级别配置发送不同的信息
                        saveMsgType = ";" + saveMsgType + ";";

                        //判断报警信息是否发送
                        String eventTypeConfig;//信息事件类型以;分组如:1;2;3',
                        //判断报警事件是否要发送信息
                        for (int i = 1; i <= 4; i++) {
                            eventTypeConfig = ";" + projectMsgLevelScreenJson.getString("eventTypeLevel" + i) + ";";
                            if (eventTypeConfig.indexOf(saveMsgType) != -1) {//判断该级别是否存在此事件信息类型
                                eventLevel = i;
                                break;
                            }
                        }

                        //判断是否要显示大屏信息
                        eventTypeConfig = ";" + projectMsgLevelScreenJson.getString("screenTypes") + ";";
                        if (eventTypeConfig.indexOf(saveMsgType) != -1) {
                            type = 2;
                        }
                    }

                    //以信息级别得到要发送语音的用户ids
                    String configLevel = ";" + projectMsgLevelScreenJson.getString("phoneLevels") + ";";
                    String temLevel = ";" + eventLevel + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        phoneUserIds = projectMsgLevelScreenJson.getString("phoneUserIds");
                    }

                    //以信息级别得到要发送短信的用户ids
                    configLevel = ";" + projectMsgLevelScreenJson.getString("smsLevels") + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        smsUserIds = projectMsgLevelScreenJson.getString("smsUserIds");
                    }

                    //以信息级别得到要发送消息推送的用户ids
                    configLevel = ";" + projectMsgLevelScreenJson.getString("msgLevels") + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        msgUserIdsSet = new HashSet<>(Arrays.asList(projectMsgLevelScreenJson.getString("msgUserIds").split(";")));
                    }
                }
            }

            //消息对象初使化
            Message msg = new Message(msgType, userIdList, title, content, url, projectId, recordId, data, eventType,
                    fontColor, (String) json.get("buildingName"), (String) json.get("floorName"), (String) json.get("deviceLocation"),
                    (String) json.get("deviceName"), (String) json.get("qrNo"),json.get("buildingId"),
                    CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName"),eventLevel,type);


            //我保存项目信息，每一条信息一个项目只有一条
            int num = messageDao.saveProjectMsg(msg);
            if (num == 0){
                return;
            }
            Long id = msg.getId();
            if (CollectionUtils.isNotEmpty(userIdList)) {//批量保存用户与消息关联关系，
                for (Long userId : userIdList){
                    msg.setTableName("m_user_message_" + (userId % 10L));
                    msg.setUserId(userId);
                    msg.setId(id);
                    messageDao.saveUserMsg(msg);
                }

            }else {
                return;
            }

            thirdMsgPush(msg);//根据数据词典推送第三方信息。如：福德花园
            sendOperator(userIdList, content);// 运营商提醒

            pushVoiceByPhone(phoneUserIds,msgType, json);//发送语音信息
            pushSmsByPhone(smsUserIds, msgType, json);//发送短信
            push(content, msgUserIdsSet, 1, url, recordId);// 是否推送极光推送
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 发送运营通提醒
     *
     * @param userIds userId
     * @param content 内容
     * @author zhangZaiFa
     * @date 2020/3/17 10:40
     **/
    private void sendOperator(List<Long> userIds, String content) {
        MetroUtils.sendMsg(userIds.stream().map(s -> s + "").collect(Collectors.joining(",")),content);
    }

    /**
     * @Description 发送短信
     *
     * @param smsUserIdStr 用户数组
     * @param msgType 消息类型
     * @param jsonObject 类型内容
     * @author wangzhiwen
     * @date 2021/1/8 10:31
     * @return void
     */
    private void pushSmsByPhone(String smsUserIdStr, Integer msgType,JSONObject jsonObject){
        String isSmsPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_SMS_PUSH);

        // 是否推送短信提醒
        if(isSmsPushStr != null){
            int isSmsPush = Integer.valueOf(isSmsPushStr);

            if(isSmsPush == 1){
                //查询此内信息要发送的用户
                //String smsUserIdStr = messageDao.findProjectSMSUser(projectId, roleSignList);
                if (smsUserIdStr != null){
                    String[] smsUserIds = smsUserIdStr.split(";");
                    // 查询手机号
                    List<String> phones =  messageDao.findUserPhone(Arrays.asList(smsUserIds));
                    TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953",
                            "320396eba397264367f1c602cd735b97");

                    AlibabaAliqinFcSmsNumSendRequest request = SendSmsUtils.getAlibabaAliqinFcSmsNumSendRequest(msgType, "", jsonObject);
                    AlibabaAliqinFcSmsNumSendResponse rsp;
                    for (String telPhone : phones) {
                        request.setRecNum(telPhone);
                        try {
                            rsp = client.execute(request);
                            SysLog.info(rsp.getBody());
                        } catch (ApiException e) {
                            SysLog.error(e);
                        }
                    }
                }
            }
        }
    }

    /**
     * @Description 发送语音信息
     *
     * @param phoneUserStr 项目id
     * @param msgType 消息类型
     * @param jsonObject 类型内容
     * @author wangzhiwen
     * @date 2021/1/8 10:31
     * @return void
     */
    private void pushVoiceByPhone(String phoneUserStr, Integer msgType,JSONObject jsonObject){
        String isPhonePushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_PHONE_PUSH);
        // 是否推送语音提醒
        if(isPhonePushStr != null){
            int isPhonePush = Integer.valueOf(isPhonePushStr);
            if(isPhonePush == 1){
                try {
                    // 查询此内信息要发送的用户
                    //String phoneUserStr = messageDao.findProjectPhoneUser(projectId, roleSignList);
                    if (phoneUserStr != null) {
                        //String ttsCode = ConstantsMessageMsg.TTS_CODE;//   TTS_205607789 SendPhoneUtils.getCode(msgType);
                        String ttsParam = SendPhoneUtils.getParam(msgType, jsonObject);

                        String[] phoneUserIds = phoneUserStr.split(";");
                        List<String> phones = messageDao.findUserPhone(Arrays.asList(phoneUserIds));
                        for (String telPhone : phones) {
                            VoiceUtils.singleCallByTts(telPhone, ConstantsMessageMsg.TTS_CODE, ttsParam);
                        }
                    }
                }catch (Exception ex){
                    SysLog.error(ex);
                }
            }
        }
    }

    /**
     * push 发送极光推送
     *
     * @Param: [msg, alias, badge, url, recordId]
     * @author zhangZaiFa
     * @date 2019/11/14 16:04
     **/
    private void push(String msg, Set<String> alias, int badge, String url, Long recordId) {
        String isUroraPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_URORA_PUSH);

        // 是否推送极光推送
        if(isUroraPushStr != null){
            int isPush = Integer.valueOf(isUroraPushStr);
            if(isPush == 1) {
                ClientConfig clientConfig = ClientConfig.getInstance();
                clientConfig.put(ClientConfig.APNS_PRODUCTION, ConstantsMessageMsg.APNS_PRODUCTION);
                SysLog.info("========>极光推送key:" + ConstantsMessageMsg.PUSH_JPUSH_SECRET + ",secret:"+ConstantsMessageMsg.PUSH_JPUSH_APPKEY);
                JPushClient jpushClient = new JPushClient(ConstantsMessageMsg.PUSH_JPUSH_SECRET, ConstantsMessageMsg.PUSH_JPUSH_APPKEY, null, clientConfig);
                String authCode = ServiceHelper.getBasicAuthorization(ConstantsMessageMsg.PUSH_JPUSH_APPKEY, ConstantsMessageMsg.PUSH_JPUSH_SECRET);
                NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
                jpushClient.getPushClient().setHttpClient(httpClient);
                Notification notification = Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder().setBadge(badge).setAlert(msg).addExtra("url", url).addExtra("messageId", recordId).build())
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(msg).addExtra("badge", badge).addExtra("url", url)
                                .addExtra("recordId", recordId).build()).build();

                PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias)).setNotification(notification).build();

                try {
                    PushResult result = jpushClient.sendPush(payload);
                    SysLog.info("Got result - " + result);
                } catch (APIConnectionException e) {
                    SysLog.error(e);
                    SysLog.error("Sendno: " + payload.getSendno());
                } catch (APIRequestException e) {
                    SysLog.info(JSONObject.toJSONString(e));
                }
            }
        }
    }

    /**
     * 短信通知
     *
     * @param event 类型
     * @param telPhone 电话号码
     * @param json  JSONObject数据
     */
    @Override
    public void sendSMS(Integer event, String telPhone, JSONObject json) {
        if(StringUtils.isNotEmpty(telPhone)){
            TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953","320396eba397264367f1c602cd735b97");
            AlibabaAliqinFcSmsNumSendRequest request = SendSmsUtils.getAlibabaAliqinFcSmsNumSendRequest(event, telPhone, json);

            AlibabaAliqinFcSmsNumSendResponse rsp;
            try {
                rsp = client.execute(request);
                SysLog.info(rsp.getBody());
            } catch (ApiException e) {
                SysLog.error(e);
            }
        }
    }

    /**
     * 第三方物联信息推送
     *
     * @param msg Message
     */
    private void thirdMsgPush(Message msg){
        try {
            List<Dict> list = DictUtils.getDictListByDictCode(ConstantsMessageMsg.IOT_MSG_THREE_PUSH);
            if (CollectionUtils.isNotEmpty(list)) {
                String projectId = "," + msg.getProjectId() + ",";
                String url;
                for (Dict dict : list) {
                    if (dict.getItemDescription().indexOf(projectId) != -1) {
                        url = dict.getItemValue();
                        if (StringUtils.isNotEmpty(url)) {
                            iotMsgPush(msg, url);
                            return;
                        }
                    }
                }
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     * iotMsgPush
     *
     * @param msg Message
     * @param url url
     */
    private void iotMsgPush(Message msg, String url){
        JSONObject json = new JSONObject(7);
        json.put("status", "200");
        json.put("projectName", msg.getProjectName());
        json.put("deviceLocation", msg.getDeviceLocation());
        json.put("deviceName", msg.getDeviceName());
        json.put("qrNo", msg.getQrNo());
        json.put("eventType", msg.getEventType() + "");
        json.put("msgType", msg.getMessageType() + "");
        HttpUtils.httpPost(url, json.toString());
    }

    /**
     * 是否添加项目消息
     *
     * @param eventType Message
     * @param eventType msgType
     * @author zhangZaiFa
     * @date 2020/4/8 14:24
     **/
    private String getMsgType(int msgType, Integer eventType) {
        if(eventType != null) {
            // 增加中向下排, 与数据库对应
            String type = "";
            //火警
            if (msgType == -1) {
                if (eventType == 1) {//火警
                    type = "2";
                } else if (eventType == 2) {//监视
                    type = "4";
                } else if (eventType == 7) {//故障
                    type = "3";
                } else if (eventType == 21) {//离线
                    type = "5";
                }
            } else if (msgType == 13) {
                //极早期
                //0:故障，1：警告，2：行动，3：火警1，4：火警2，5：离线
                if (eventType == 0) {//故障
                    type = "8";
                } else if (eventType == 1) {//警告
                    type = "9";
                } else if (eventType == 2) {//行动
                    type = "10";
                } else if (eventType == 3) {//火警1
                    type = "7";
                } else if (eventType == 4) {//火警2
                    type = "6";
                } else if (eventType == 5) {//离线
                    type = "11";
                }
            } else if (msgType == -3) {
                //水压
                if (eventType == 1) {//电量是否过低
                    type = "13";
                } else if (eventType == 2) {//超高超低
                    type = "12";
                } else if (eventType == 99) {//离线
                    type = "14";
                }
            } else if (msgType == 7) {//水浸
                if (eventType == 1) {//电量是否过低
                    type = "16";
                } else if (eventType == 3) {//漏水
                    type = "15";
                } else if (eventType == 99) {//离线
                    type = "17";
                }
            } else if (msgType == 4) {
                type = "1";
            } else if (msgType == 9) {// 智能无线烟感
                if (eventType == 1) {// 报警
                    type = "24";
                } else if (eventType == 5) {// 故障
                    type = "25";
                } else if (eventType == 4) {// 低电量
                    type = "26";
                }else if (eventType == 21) {
                    // 离线
                    type = "44";
                }
            } else if (msgType == 8) {
                // 智能消火栓高压_低压_（撞击_漏水）（故障）_低电压_离线 18_19_20_21_22_23
                if (eventType == 99) {//离线
                    type = "23";
                } else if (eventType == 1) {//低电压
                    type = "22";
                } else if (eventType == 2) {//高压
                    type = "18";
                } else if (eventType == 3) {//低压
                    type = "19";
                } else if (eventType == 4) {//故障
                    type = "21";//21
                } else if (eventType == 5) {//撞击_漏水
                    type = "20";
                }
            } else if (msgType == 15) {// 声光报警通短号
                if (eventType == 2) {// 报警
                    type = "27";
                } else if (eventType == 3) {// 离线
                    type = "28";
                }
            } else if (msgType == 10) {//电气火灾
                if (eventType == 1) {//漏电流报警
                    type = "29";
                } else if (eventType == 2) {//温度报警
                    type = "30";
                } else if (eventType == 3) {//故障事件
                    type = "31";
                } else if (eventType == 4) {//离线事件
                    type = "32";
                } else if (eventType == 5) {//漏电流故障恢复
                    type = "33";
                } else if (eventType == 6) {//温度故障恢复
                    type = "34";
                } else if (eventType == 7) {//恢复在线通知
                    type = "35";
                }
            }else if(msgType == 16){
                // 报表通知
                if(eventType == 1){
                    // 巡查设备运营周报
                    type = "36";
                }else if(eventType == 2){
                    // 物联设备运营周报
                    type = "37";
                }
            }else if (msgType == -2) {
                // 火眼
                /*
                 * 0:正常(恢复所有事件,不新增事件), ,98 心跳(更新心跳,不新增事件)
                 *  1：烟雾预警(烟雾)、2：烟雾报警、4火焰预警、8：火焰报警、16遮挡、32故障
                 */
                if (eventType == 1) {//烟雾预警(烟雾)
                    type = "38";
                } else if (eventType == 2) {//烟雾报警
                    type = "39";
                } else if (eventType == 4) {//火焰预警
                    type = "40";
                }else if (eventType == 8) {//火焰报警
                    type = "41";
                }else if (eventType == 16) {//遮挡
                    type = "42";
                }else if (eventType == 32) {//故障
                    type = "43";
                }else if (eventType == 99) {//离线
                    type = "45";
                }
            }

            return type;
        }
        return null;
    }

    //////////////////////////////////////////////////////////////兼容5.0版本方法//////////////////////////////
    /**
     * @Description 兼容5.0版本保存信息发送
     *
     * @param msgType
     * @param userIds
     * @param title
     * @param eventType
     * @param fontColor
     * @param content
     * @param url
     * @param projectId
     * @param recordId
     * @param data
     * @param json
     * @author wangzhiwen
     * @date 2021/1/8 15:08
     * @return void
     */
    public void saveMessageCV5(Integer msgType, List<Long> userIds, String title, Integer eventType, Integer fontColor, String content, String url, Long projectId,
                               Long recordId, String data, JSONObject json){
        try{
            if (CollectionUtils.isEmpty(userIds)) {
                SysLog.info("---》没有要发送的人员");
                return;
            }

            SysLog.info(userIds.size() + "--------要发送的人员----------->" + JSONArray.toJSONString(userIds));
            try {
                Set<String> alias = new HashSet<>(userIds.size());
                Set<Long> userSet = new HashSet<>(userIds.size());
                for (Long userId : userIds) {
                    alias.add(userId.toString());
                    userSet.add(userId);
                }

                Message msg = new Message(msgType, userIds, title, content, url, projectId, recordId, data,eventType,
                        fontColor, (String)json.get("buildingName"), (String)json.get("floorName"), (String)json.get("deviceLocation"),
                        (String)json.get("deviceName"), (String)json.get("qrNo"));

                SysLog.info(json + "-----------------》参数-------<" + JSONArray.toJSONString(alias));
                if(json.get("buildingId") != null){
                    SysLog.info(json.getLong("buildingId") + "--------》参数内容");
                    msg.setBuildingId(json.getLong("buildingId"));
                }

                msg.setProjectName(CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName"));
                msg = setDatabasesCV5(msg);
                if(userSet.size() != 0){
                    msg.getUserIds().clear();
                    msg.getUserIds().addAll(userSet);
                    messageDao.batchSaveCV5(msg);
                }
                ////消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核 2、工作通知 3、审核通知 4、报修通知 5、智能消火栓
                //     *     //7、水浸  8、可燃气  9、烟感 10、电气火灾通知 13、极早期预警, 15声光报警, 16报表通知,17空气采样
                if(msgType == -1 || msgType == -2 ||msgType == -3 || msgType == 4 || msgType == 5 || msgType == 7 || msgType == 8 || msgType == 9 || msgType == 10
                        || msgType == 13 || msgType == 15 || msgType == 16){

                    SysLog.info(JSONObject.toJSON(msg) + "----------------->消息内容");
                    saveProjectMsgCV5(msg);
                }

                String isUroraPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_URORA_PUSH);
                String isOperatorPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_OPERATOR_PUSH);
                // 是否推送极光推送
                if(isUroraPushStr != null){
                    int isPush = Integer.valueOf(isUroraPushStr);
                    if(isPush == 1) {
                        push(content, alias, 1, url, recordId);
                    }
                }

                // 是否推送运营商提醒
                if(isOperatorPushStr != null){
                    int isOperatorPush = Integer.valueOf(isOperatorPushStr);
                    if(isOperatorPush == 1) {
                        // 运营商提醒
                        sendOperator(userIds, content);
                    }
                }
            } catch (Exception e) {
                SysLog.error(e);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     * setDatabases
     *
     * @param msg Message
     * @return com.xjt.cloud.message.manage.entity.Message
     * @author zhangZaiFa
     * @date 2019/11/15 11:40
     **/
    private Message setDatabasesCV5(Message msg) {
        msg.setDatabasesName(getDatabaseName(msg.getProjectId(), msg.getMessageType()));
        msg.setTableName(getTableName(msg.getProjectId(), msg.getMessageType()));
        return msg;
    }
    /**
     * 是否添加项目消息
     *
     * @param msg Message
     * @author zhangZaiFa
     * @date 2020/4/8 14:24
     **/
    private void saveProjectMsgCV5(Message msg) {
        if(msg.getEventType() != null){
            Integer messageType = msg.getMessageType();
            int eventType = msg.getEventType();
            // 增加中向下排, 与数据库对应
            String type = "0";
            //火警
            if(messageType == -1){
                if(eventType == 1){//火警
                    type = "2";
                }else if(eventType == 2){//监视
                    type = "4";
                }else if(eventType == 7){//故障
                    type = "3";
                }else if(eventType == 21){//离线
                    type = "5";
                }
            }else if(messageType == 13){
                //极早期
                //0:故障，1：警告，2：行动，3：火警1，4：火警2，5：离线
                if(eventType == 0){//故障
                    type = "8";
                }else if(eventType == 1){//警告
                    type = "9";
                }else if(eventType == 2){//行动
                    type = "10";
                }else if(eventType == 3){//火警1
                    type = "7";
                }else if(eventType == 4){//火警2
                    type = "6";
                }else if(eventType == 5){//离线
                    type = "11";
                }
            }else if(messageType == -3){
                //水压
                if(eventType == 1){//电量是否过低
                    type = "13";
                }else if(eventType == 2){//超高超低
                    type = "12";
                }else if(eventType == 99){//离线
                    type = "14";
                }
            }else if(messageType == 7){//水浸
                if(eventType == 1){//电量是否过低
                    type = "16";
                }else if(eventType == 3){//漏水
                    type = "15";
                }else if(eventType == 99){//离线
                    type = "17";
                }
            }else if(messageType == 4){
                type = "1";
            }else if(messageType == 9){// 智能无线烟感
                if(eventType == 1){// 报警
                    type = "24";
                }else if(eventType == 5){// 故障
                    type = "25";
                }else if(eventType == 4){// 低电量
                    type = "26";
                }
            }else if(messageType == 8){
                // 智能消火栓高压_低压_（撞击_漏水）（故障）_低电压_离线 18_19_20_21_22_23
                if(eventType == 99){//离线
                    type = "23";
                }else if(eventType == 1){
                    type = "22";
                }else if(eventType == 2){
                    type = "18";
                }else if(eventType == 3){
                    type = "19";
                }else if(eventType == 4){
                    type = "20";//21
                }else if(eventType == 5){
                    type = "20";
                }
            }else if(messageType == 15){// 声光报警通短号
                if(eventType == 2){// 报警
                    type = "27";
                }else if(eventType == 3){// 离线
                    type = "28";
                }
            }else if(messageType == 10){//电气火灾
                if(eventType == 1){//漏电流报警
                    type = "29";
                }else if(eventType == 2){//温度报警
                    type = "30";
                }else if(eventType == 3){//故障事件
                    type = "31";
                }else if(eventType == 4){//离线事件
                    type = "32";
                }else if(eventType == 5){//漏电流故障恢复
                    type = "33";
                }else if(eventType == 6){//温度故障恢复
                    type = "34";
                }else if(eventType == 7){//恢复在线通知
                    type = "35";
                }
            }else if(messageType == 16){
                // 报表通知
                if(eventType == 1){
                    // 巡查设备运营周报
                    type = "36";
                }else if(eventType == 2){
                    // 物联设备运营周报
                    type = "37";
                }
            }

            String msgTypeStr = messageDao.findProjectScreenMsgSetCV5(msg.getProjectId());
            if(msgTypeStr != null){//保存大屏消息
                String[] msgTypes = msgTypeStr.split(";");
                SysLog.info(type + "------------》消息类型=====》" + JSONObject.toJSONString(msgTypes));
                if(Arrays.asList(msgTypes).contains(type)){
                    SysLog.info("------------》满足条件开始保存=====》" + JSONObject.toJSONString(msg));
                    msg = setDatabasesCV5(msg);
                    messageDao.saveProjectMsgCV5(msg);
                }
            }
            // 福德花园接口
            thirdMsgPush(msg);
        }
    }

    private void saveMessageRoleThreadCV5(Integer msgType, List<String> roleSignList, String title,Integer eventType,Integer fontColor, String content, String url,
                                      Long projectId, Long recordId, String data, JSONObject jsonObject) {

        try{
            if (roleSignList == null || roleSignList.size() == 0) {
                SysLog.info("---》没有要发送的人员");
                return;
            }

            SysLog.info(ConstantsMessageMsg.FIND_PERMISSION_SIGN_USERID_URL + "{\"projectId\":" + projectId + ",\"signList\":" + JSONArray.toJSONString(roleSignList)
                    + "---------------->连接地址");

            JSONObject obj = HttpUtils.httpGetRestTemplate(ConstantsMessageMsg.FIND_PERMISSION_SIGN_USERID_URL, "{\"projectId\":" + projectId + ",\"signList\":"
                    + JSONArray.toJSONString(roleSignList) + "}","json");

            if (Integer.valueOf(obj.get("status").toString()) == 200) {
                List<Long> userIds = JSONArray.parseArray(obj.get("object").toString(), Long.class);
                saveMessageUserThread(msgType, userIds, title,eventType, fontColor, content, url, projectId, recordId, data, jsonObject);
                String isSmsPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_SMS_PUSH);
                SysLog.info(isSmsPushStr + "----短信--------->" + isSmsPushStr);

                String isPhonePushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_PHONE_PUSH);
                SysLog.info(isSmsPushStr + "----电话--------->" + isPhonePushStr);

                // 是否推送短信提醒
                if(isSmsPushStr != null){
                    int isSmsPush = Integer.valueOf(isSmsPushStr);
                    SysLog.info("----isSmsPush--------->" + isSmsPush);

                    if(isSmsPush == 1){
                        //查询此内信息要发送的用户
                        String smsUserIdStr = messageDao.findProjectSMSUserCV5(projectId, roleSignList);
                        if (smsUserIdStr != null){
                            String[] smsUserIds = smsUserIdStr.split(";");
                            // 查询手机号
                            List<String> phones =  messageDao.findUserPhone(Arrays.asList(smsUserIds));

                            TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23469953",
                                    "320396eba397264367f1c602cd735b97");

                            AlibabaAliqinFcSmsNumSendRequest request = SendSmsUtils.getAlibabaAliqinFcSmsNumSendRequest(msgType, "", jsonObject);
                            AlibabaAliqinFcSmsNumSendResponse rsp;
                            for (String telPhone : phones) {
                                SysLog.info("----isSmsPush-----telPhone---->" + telPhone);
                                request.setRecNum(telPhone);

                                try {
                                    rsp = client.execute(request);
                                    SysLog.info(rsp.getBody());
                                } catch (ApiException e) {
                                    SysLog.error(e);
                                }
                            }
                        }
                    }
                }

                // 是否推送语音提醒
                if(isPhonePushStr != null){
                    int isPhonePush = Integer.valueOf(isPhonePushStr);
                    SysLog.info("----isPhonePush--------->" + isPhonePush);
                    if(isPhonePush == 1){
                        // 查询此内信息要发送的用户
                        String phoneUserStr = messageDao.findProjectPhoneUserCV5(projectId,roleSignList);
                        if (phoneUserStr != null){
                            SingleCallByTtsResponse singleCallByTtsResponse;
                            //String ttsCode = ConstantsMessageMsg.TTS_CODE;//   TTS_205607789 SendPhoneUtils.getCode(msgType);
                            String ttsParam = SendPhoneUtils.getParam(msgType, jsonObject);

                            String[] phoneUserIds = phoneUserStr.split(";");
                            List<String> phones = messageDao.findUserPhone(Arrays.asList(phoneUserIds));
                            for (String telPhone : phones) {
                                SysLog.info("--isPhonePush--phones--------->" + telPhone);
                                //sendPhone(msgType, telPhone, jsonObject);

                                singleCallByTtsResponse = VoiceUtils.singleCallByTts(telPhone, ConstantsMessageMsg.TTS_CODE, ttsParam);
                                SysLog.info("RequestId=" + singleCallByTtsResponse.getRequestId());
                                SysLog.info("Code=" + singleCallByTtsResponse.getCode());
                                SysLog.info("Message=" + singleCallByTtsResponse.getMessage());
                                SysLog.info("CallId=" + singleCallByTtsResponse.getCallId());
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }
}
