package com.xjt.cloud.message.manage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.message.manage.service.service.MessageService;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/30 09:06
 * @Description:
 */
public class SendMsgThread extends Thread{
    private Integer msgType;
    private List<Long> userIds;
    private String title;
    private Integer eventType;
    private Integer fontColor;
    private String content;
    private String url;
    private Long projectId;
    private Long recordId;
    private String data;
    private JSONObject json;
    private List<String> roleSignList;
    private Integer type;

    public SendMsgThread(Integer msgType, List<Long> userIds,List<String> roleSignList, String title, Integer eventType,
                         Integer fontColor, String content, String url, Long projectId, Long recordId, String data, JSONObject json,Integer type){
       this.msgType = msgType;
       this.userIds = userIds;
       this.title = title;
       this.eventType = eventType;
       this.fontColor = fontColor;
       this.content = content;
       this.url = url;
       this.projectId = projectId;
       this.recordId = recordId;
       this.data = data;
       this.json = json;
       this.type = type;
       this.roleSignList = roleSignList;
    }

    public SendMsgThread(){

    }

    @Override
    public void run(){
        try {
            MessageService messageService = SpringBeanUtil.getBean(MessageService.class);
            if(type == 1){//判断是根据用户发送信息还是角色发送信息
                messageService.saveMessageUserThread(msgType, userIds, title, eventType, fontColor, content, url, projectId, recordId, data, json);
            }else if(type == 2){
                messageService.saveMessageRoleThread(msgType, roleSignList, title, eventType, fontColor, content, url, projectId, recordId, data, json);
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
    }
}
