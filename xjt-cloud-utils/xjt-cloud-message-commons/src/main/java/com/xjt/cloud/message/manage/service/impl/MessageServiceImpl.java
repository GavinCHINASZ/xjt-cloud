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
 * ??????
 *
 * @author zhangZaiFa
 * @date 2019/4/29 0029 13:45
 */
@Service
public class MessageServiceImpl extends MsgAbstractService implements MessageService {
    @Autowired
    private MessageDao messageDao;

    /**
     * saveMessage ????????????????????????????????????
     *
     * @param msgType ??????
     * @param userIds userId
     * @param title ??????
     * @param eventType ????????????
     * @param fontColor fontColor
     * @param content ??????
     * @param url url
     * @param projectId ??????ID
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
     * ?????????????????????
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
     * saveMessage ????????????????????????????????????
     *
     * @param msgType ??????
     * @param userIds userId
     * @param title ??????
     * @param eventType ????????????
     * @param fontColor fontColor
     * @param content ??????
     * @param url url
     * @param projectId ??????ID
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
     * saveMessage ????????????????????????????????????
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
                //?????????????????????
                saveMessage(msgType, userIdList, title, eventType, fontColor, content, url, projectId, recordId, data, jsonObject);
            } catch (Exception ex) {
                SysLog.error(ex);
            }
        }
    }

    /**
     * @Description ??????????????????
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
            int eventLevel = 10;//????????????4????????????
            int type = 1;//???????????? 1?????????????????????2??????????????????
            String phoneUserIds = null;
            String smsUserIds = null;
            Set<String> msgUserIdsSet = null;
            SysLog.info("???????????????" + json.toJSONString());
            if (CollectionUtils.isEmpty(userIdList)){
                return;
            }
            SysLog.info("??????id???" + userIdList.stream().map(s -> s.toString()).collect(Collectors.joining("_")));

            if (msgType == -4 || msgType == 2 || msgType == 3 || msgType == 4) {//???????????????-4??????????????? 2??????????????? 3??????????????? 4??????????????? ??????????????????????????????4???????????????
                msgUserIdsSet = userIdList.stream().map(s -> s.toString()).collect(Collectors.toSet());
            }else{//???????????????????????????????????????,???????????????
                if (msgType == 16) {//?????????????????????????????????????????????
                    msgUserIdsSet = userIdList.stream().map(s -> s.toString()).collect(Collectors.toSet());
                }else{
                    String saveMsgType = getMsgType(msgType, eventType);//??????????????????
                    //??????????????????????????????,
                    JSONObject projectMsgLevelScreenJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_MSG_LEVEL_CACHE_KEY, projectId);
                    if (StringUtils.isNotEmpty(saveMsgType) && projectMsgLevelScreenJson != null) {//???????????????????????????????????????????????????
                        saveMsgType = ";" + saveMsgType + ";";

                        //??????????????????????????????
                        String eventTypeConfig;//?????????????????????;?????????:1;2;3',
                        //???????????????????????????????????????
                        for (int i = 1; i <= 4; i++) {
                            eventTypeConfig = ";" + projectMsgLevelScreenJson.getString("eventTypeLevel" + i) + ";";
                            if (eventTypeConfig.indexOf(saveMsgType) != -1) {//????????????????????????????????????????????????
                                eventLevel = i;
                                break;
                            }
                        }

                        //?????????????????????????????????
                        eventTypeConfig = ";" + projectMsgLevelScreenJson.getString("screenTypes") + ";";
                        if (eventTypeConfig.indexOf(saveMsgType) != -1) {
                            type = 2;
                        }
                    }

                    //?????????????????????????????????????????????ids
                    String configLevel = ";" + projectMsgLevelScreenJson.getString("phoneLevels") + ";";
                    String temLevel = ";" + eventLevel + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        phoneUserIds = projectMsgLevelScreenJson.getString("phoneUserIds");
                    }

                    //?????????????????????????????????????????????ids
                    configLevel = ";" + projectMsgLevelScreenJson.getString("smsLevels") + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        smsUserIds = projectMsgLevelScreenJson.getString("smsUserIds");
                    }

                    //???????????????????????????????????????????????????ids
                    configLevel = ";" + projectMsgLevelScreenJson.getString("msgLevels") + ";";
                    if (configLevel.indexOf(temLevel) != -1) {
                        msgUserIdsSet = new HashSet<>(Arrays.asList(projectMsgLevelScreenJson.getString("msgUserIds").split(";")));
                    }
                }
            }

            //?????????????????????
            Message msg = new Message(msgType, userIdList, title, content, url, projectId, recordId, data, eventType,
                    fontColor, (String) json.get("buildingName"), (String) json.get("floorName"), (String) json.get("deviceLocation"),
                    (String) json.get("deviceName"), (String) json.get("qrNo"),json.get("buildingId"),
                    CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName"),eventLevel,type);


            //???????????????????????????????????????????????????????????????
            int num = messageDao.saveProjectMsg(msg);
            if (num == 0){
                return;
            }
            Long id = msg.getId();
            if (CollectionUtils.isNotEmpty(userIdList)) {//??????????????????????????????????????????
                for (Long userId : userIdList){
                    msg.setTableName("m_user_message_" + (userId % 10L));
                    msg.setUserId(userId);
                    msg.setId(id);
                    messageDao.saveUserMsg(msg);
                }

            }else {
                return;
            }

            thirdMsgPush(msg);//????????????????????????????????????????????????????????????
            sendOperator(userIdList, content);// ???????????????

            pushVoiceByPhone(phoneUserIds,msgType, json);//??????????????????
            pushSmsByPhone(smsUserIds, msgType, json);//????????????
            push(content, msgUserIdsSet, 1, url, recordId);// ????????????????????????
        } catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * ?????????????????????
     *
     * @param userIds userId
     * @param content ??????
     * @author zhangZaiFa
     * @date 2020/3/17 10:40
     **/
    private void sendOperator(List<Long> userIds, String content) {
        MetroUtils.sendMsg(userIds.stream().map(s -> s + "").collect(Collectors.joining(",")),content);
    }

    /**
     * @Description ????????????
     *
     * @param smsUserIdStr ????????????
     * @param msgType ????????????
     * @param jsonObject ????????????
     * @author wangzhiwen
     * @date 2021/1/8 10:31
     * @return void
     */
    private void pushSmsByPhone(String smsUserIdStr, Integer msgType,JSONObject jsonObject){
        String isSmsPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_SMS_PUSH);

        // ????????????????????????
        if(isSmsPushStr != null){
            int isSmsPush = Integer.valueOf(isSmsPushStr);

            if(isSmsPush == 1){
                //????????????????????????????????????
                //String smsUserIdStr = messageDao.findProjectSMSUser(projectId, roleSignList);
                if (smsUserIdStr != null){
                    String[] smsUserIds = smsUserIdStr.split(";");
                    // ???????????????
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
     * @Description ??????????????????
     *
     * @param phoneUserStr ??????id
     * @param msgType ????????????
     * @param jsonObject ????????????
     * @author wangzhiwen
     * @date 2021/1/8 10:31
     * @return void
     */
    private void pushVoiceByPhone(String phoneUserStr, Integer msgType,JSONObject jsonObject){
        String isPhonePushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_PHONE_PUSH);
        // ????????????????????????
        if(isPhonePushStr != null){
            int isPhonePush = Integer.valueOf(isPhonePushStr);
            if(isPhonePush == 1){
                try {
                    // ????????????????????????????????????
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
     * push ??????????????????
     *
     * @Param: [msg, alias, badge, url, recordId]
     * @author zhangZaiFa
     * @date 2019/11/14 16:04
     **/
    private void push(String msg, Set<String> alias, int badge, String url, Long recordId) {
        String isUroraPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_URORA_PUSH);

        // ????????????????????????
        if(isUroraPushStr != null){
            int isPush = Integer.valueOf(isUroraPushStr);
            if(isPush == 1) {
                ClientConfig clientConfig = ClientConfig.getInstance();
                clientConfig.put(ClientConfig.APNS_PRODUCTION, ConstantsMessageMsg.APNS_PRODUCTION);
                SysLog.info("========>????????????key:" + ConstantsMessageMsg.PUSH_JPUSH_SECRET + ",secret:"+ConstantsMessageMsg.PUSH_JPUSH_APPKEY);
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
     * ????????????
     *
     * @param event ??????
     * @param telPhone ????????????
     * @param json  JSONObject??????
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
     * ???????????????????????????
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
     * ????????????????????????
     *
     * @param eventType Message
     * @param eventType msgType
     * @author zhangZaiFa
     * @date 2020/4/8 14:24
     **/
    private String getMsgType(int msgType, Integer eventType) {
        if(eventType != null) {
            // ??????????????????, ??????????????????
            String type = "";
            //??????
            if (msgType == -1) {
                if (eventType == 1) {//??????
                    type = "2";
                } else if (eventType == 2) {//??????
                    type = "4";
                } else if (eventType == 7) {//??????
                    type = "3";
                } else if (eventType == 21) {//??????
                    type = "5";
                }
            } else if (msgType == 13) {
                //?????????
                //0:?????????1????????????2????????????3?????????1???4?????????2???5?????????
                if (eventType == 0) {//??????
                    type = "8";
                } else if (eventType == 1) {//??????
                    type = "9";
                } else if (eventType == 2) {//??????
                    type = "10";
                } else if (eventType == 3) {//??????1
                    type = "7";
                } else if (eventType == 4) {//??????2
                    type = "6";
                } else if (eventType == 5) {//??????
                    type = "11";
                }
            } else if (msgType == -3) {
                //??????
                if (eventType == 1) {//??????????????????
                    type = "13";
                } else if (eventType == 2) {//????????????
                    type = "12";
                } else if (eventType == 99) {//??????
                    type = "14";
                }
            } else if (msgType == 7) {//??????
                if (eventType == 1) {//??????????????????
                    type = "16";
                } else if (eventType == 3) {//??????
                    type = "15";
                } else if (eventType == 99) {//??????
                    type = "17";
                }
            } else if (msgType == 4) {
                type = "1";
            } else if (msgType == 9) {// ??????????????????
                if (eventType == 1) {// ??????
                    type = "24";
                } else if (eventType == 5) {// ??????
                    type = "25";
                } else if (eventType == 4) {// ?????????
                    type = "26";
                }else if (eventType == 21) {
                    // ??????
                    type = "44";
                }
            } else if (msgType == 8) {
                // ?????????????????????_??????_?????????_?????????????????????_?????????_?????? 18_19_20_21_22_23
                if (eventType == 99) {//??????
                    type = "23";
                } else if (eventType == 1) {//?????????
                    type = "22";
                } else if (eventType == 2) {//??????
                    type = "18";
                } else if (eventType == 3) {//??????
                    type = "19";
                } else if (eventType == 4) {//??????
                    type = "21";//21
                } else if (eventType == 5) {//??????_??????
                    type = "20";
                }
            } else if (msgType == 15) {// ?????????????????????
                if (eventType == 2) {// ??????
                    type = "27";
                } else if (eventType == 3) {// ??????
                    type = "28";
                }
            } else if (msgType == 10) {//????????????
                if (eventType == 1) {//???????????????
                    type = "29";
                } else if (eventType == 2) {//????????????
                    type = "30";
                } else if (eventType == 3) {//????????????
                    type = "31";
                } else if (eventType == 4) {//????????????
                    type = "32";
                } else if (eventType == 5) {//?????????????????????
                    type = "33";
                } else if (eventType == 6) {//??????????????????
                    type = "34";
                } else if (eventType == 7) {//??????????????????
                    type = "35";
                }
            }else if(msgType == 16){
                // ????????????
                if(eventType == 1){
                    // ????????????????????????
                    type = "36";
                }else if(eventType == 2){
                    // ????????????????????????
                    type = "37";
                }
            }else if (msgType == -2) {
                // ??????
                /*
                 * 0:??????(??????????????????,???????????????), ,98 ??????(????????????,???????????????)
                 *  1???????????????(??????)???2??????????????????4???????????????8??????????????????16?????????32??????
                 */
                if (eventType == 1) {//????????????(??????)
                    type = "38";
                } else if (eventType == 2) {//????????????
                    type = "39";
                } else if (eventType == 4) {//????????????
                    type = "40";
                }else if (eventType == 8) {//????????????
                    type = "41";
                }else if (eventType == 16) {//??????
                    type = "42";
                }else if (eventType == 32) {//??????
                    type = "43";
                }else if (eventType == 99) {//??????
                    type = "45";
                }
            }

            return type;
        }
        return null;
    }

    //////////////////////////////////////////////////////////////??????5.0????????????//////////////////////////////
    /**
     * @Description ??????5.0????????????????????????
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
                SysLog.info("---???????????????????????????");
                return;
            }

            SysLog.info(userIds.size() + "--------??????????????????----------->" + JSONArray.toJSONString(userIds));
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

                SysLog.info(json + "-----------------?????????-------<" + JSONArray.toJSONString(alias));
                if(json.get("buildingId") != null){
                    SysLog.info(json.getLong("buildingId") + "--------???????????????");
                    msg.setBuildingId(json.getLong("buildingId"));
                }

                msg.setProjectName(CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName"));
                msg = setDatabasesCV5(msg);
                if(userSet.size() != 0){
                    msg.getUserIds().clear();
                    msg.getUserIds().addAll(userSet);
                    messageDao.batchSaveCV5(msg);
                }
                ////????????????-1??????????????? -2??????????????? -3???????????????  -4??????????????? 2??????????????? 3??????????????? 4??????????????? 5??????????????????
                //     *     //7?????????  8????????????  9????????? 10????????????????????? 13??????????????????, 15????????????, 16????????????,17????????????
                if(msgType == -1 || msgType == -2 ||msgType == -3 || msgType == 4 || msgType == 5 || msgType == 7 || msgType == 8 || msgType == 9 || msgType == 10
                        || msgType == 13 || msgType == 15 || msgType == 16){

                    SysLog.info(JSONObject.toJSON(msg) + "----------------->????????????");
                    saveProjectMsgCV5(msg);
                }

                String isUroraPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_URORA_PUSH);
                String isOperatorPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_OPERATOR_PUSH);
                // ????????????????????????
                if(isUroraPushStr != null){
                    int isPush = Integer.valueOf(isUroraPushStr);
                    if(isPush == 1) {
                        push(content, alias, 1, url, recordId);
                    }
                }

                // ???????????????????????????
                if(isOperatorPushStr != null){
                    int isOperatorPush = Integer.valueOf(isOperatorPushStr);
                    if(isOperatorPush == 1) {
                        // ???????????????
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
     * ????????????????????????
     *
     * @param msg Message
     * @author zhangZaiFa
     * @date 2020/4/8 14:24
     **/
    private void saveProjectMsgCV5(Message msg) {
        if(msg.getEventType() != null){
            Integer messageType = msg.getMessageType();
            int eventType = msg.getEventType();
            // ??????????????????, ??????????????????
            String type = "0";
            //??????
            if(messageType == -1){
                if(eventType == 1){//??????
                    type = "2";
                }else if(eventType == 2){//??????
                    type = "4";
                }else if(eventType == 7){//??????
                    type = "3";
                }else if(eventType == 21){//??????
                    type = "5";
                }
            }else if(messageType == 13){
                //?????????
                //0:?????????1????????????2????????????3?????????1???4?????????2???5?????????
                if(eventType == 0){//??????
                    type = "8";
                }else if(eventType == 1){//??????
                    type = "9";
                }else if(eventType == 2){//??????
                    type = "10";
                }else if(eventType == 3){//??????1
                    type = "7";
                }else if(eventType == 4){//??????2
                    type = "6";
                }else if(eventType == 5){//??????
                    type = "11";
                }
            }else if(messageType == -3){
                //??????
                if(eventType == 1){//??????????????????
                    type = "13";
                }else if(eventType == 2){//????????????
                    type = "12";
                }else if(eventType == 99){//??????
                    type = "14";
                }
            }else if(messageType == 7){//??????
                if(eventType == 1){//??????????????????
                    type = "16";
                }else if(eventType == 3){//??????
                    type = "15";
                }else if(eventType == 99){//??????
                    type = "17";
                }
            }else if(messageType == 4){
                type = "1";
            }else if(messageType == 9){// ??????????????????
                if(eventType == 1){// ??????
                    type = "24";
                }else if(eventType == 5){// ??????
                    type = "25";
                }else if(eventType == 4){// ?????????
                    type = "26";
                }
            }else if(messageType == 8){
                // ?????????????????????_??????_?????????_?????????????????????_?????????_?????? 18_19_20_21_22_23
                if(eventType == 99){//??????
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
            }else if(messageType == 15){// ?????????????????????
                if(eventType == 2){// ??????
                    type = "27";
                }else if(eventType == 3){// ??????
                    type = "28";
                }
            }else if(messageType == 10){//????????????
                if(eventType == 1){//???????????????
                    type = "29";
                }else if(eventType == 2){//????????????
                    type = "30";
                }else if(eventType == 3){//????????????
                    type = "31";
                }else if(eventType == 4){//????????????
                    type = "32";
                }else if(eventType == 5){//?????????????????????
                    type = "33";
                }else if(eventType == 6){//??????????????????
                    type = "34";
                }else if(eventType == 7){//??????????????????
                    type = "35";
                }
            }else if(messageType == 16){
                // ????????????
                if(eventType == 1){
                    // ????????????????????????
                    type = "36";
                }else if(eventType == 2){
                    // ????????????????????????
                    type = "37";
                }
            }

            String msgTypeStr = messageDao.findProjectScreenMsgSetCV5(msg.getProjectId());
            if(msgTypeStr != null){//??????????????????
                String[] msgTypes = msgTypeStr.split(";");
                SysLog.info(type + "------------???????????????=====???" + JSONObject.toJSONString(msgTypes));
                if(Arrays.asList(msgTypes).contains(type)){
                    SysLog.info("------------???????????????????????????=====???" + JSONObject.toJSONString(msg));
                    msg = setDatabasesCV5(msg);
                    messageDao.saveProjectMsgCV5(msg);
                }
            }
            // ??????????????????
            thirdMsgPush(msg);
        }
    }

    private void saveMessageRoleThreadCV5(Integer msgType, List<String> roleSignList, String title,Integer eventType,Integer fontColor, String content, String url,
                                      Long projectId, Long recordId, String data, JSONObject jsonObject) {

        try{
            if (roleSignList == null || roleSignList.size() == 0) {
                SysLog.info("---???????????????????????????");
                return;
            }

            SysLog.info(ConstantsMessageMsg.FIND_PERMISSION_SIGN_USERID_URL + "{\"projectId\":" + projectId + ",\"signList\":" + JSONArray.toJSONString(roleSignList)
                    + "---------------->????????????");

            JSONObject obj = HttpUtils.httpGetRestTemplate(ConstantsMessageMsg.FIND_PERMISSION_SIGN_USERID_URL, "{\"projectId\":" + projectId + ",\"signList\":"
                    + JSONArray.toJSONString(roleSignList) + "}","json");

            if (Integer.valueOf(obj.get("status").toString()) == 200) {
                List<Long> userIds = JSONArray.parseArray(obj.get("object").toString(), Long.class);
                saveMessageUserThread(msgType, userIds, title,eventType, fontColor, content, url, projectId, recordId, data, jsonObject);
                String isSmsPushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_SMS_PUSH);
                SysLog.info(isSmsPushStr + "----??????--------->" + isSmsPushStr);

                String isPhonePushStr = DictUtils.getDictItemValueByDictAndItemCode(ConstantsMessageMsg.MSG_PUSH_METHOD, ConstantsMessageMsg.IS_PHONE_PUSH);
                SysLog.info(isSmsPushStr + "----??????--------->" + isPhonePushStr);

                // ????????????????????????
                if(isSmsPushStr != null){
                    int isSmsPush = Integer.valueOf(isSmsPushStr);
                    SysLog.info("----isSmsPush--------->" + isSmsPush);

                    if(isSmsPush == 1){
                        //????????????????????????????????????
                        String smsUserIdStr = messageDao.findProjectSMSUserCV5(projectId, roleSignList);
                        if (smsUserIdStr != null){
                            String[] smsUserIds = smsUserIdStr.split(";");
                            // ???????????????
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

                // ????????????????????????
                if(isPhonePushStr != null){
                    int isPhonePush = Integer.valueOf(isPhonePushStr);
                    SysLog.info("----isPhonePush--------->" + isPhonePush);
                    if(isPhonePush == 1){
                        // ????????????????????????????????????
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
