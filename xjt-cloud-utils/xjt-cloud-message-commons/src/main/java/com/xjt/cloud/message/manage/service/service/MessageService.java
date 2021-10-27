package com.xjt.cloud.message.manage.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;


/**
 * @Auther: zhangZaiFa
 * @Date: 2019/4/29 0029 13:45
 * @Description:消息
 */
public interface MessageService extends BaseService {

    /**@MethodName: saveMessage
     * @Description: 保存消息，推送提醒给用户    用户ID推送
     * @param msgType 消息类型 //消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核 2、工作通知 3、审核通知 4、报修通知 5、智能消火栓
     *     //7、水浸 8、可燃气 9、烟感 10、电气火灾通知 13、极早期预警
     * @param userIds 消息接受人userId
     * @param title 消息头
     * @param eventType 事件类型，根据每个消息类型来定义
     * @param fontColor 字体颜色
     * @param content 消息正文
     * @param url 跳转url
     * @param projectId  项目ID
     * @param recordId 关联记录ID
     * @param data  跳转携带数据
     * @param jsonObject 短信和语音文本字段，不同类型参数不一样要按，按文档要求传参
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/14 14:21
     **/
    Data saveMessageUser(Integer msgType, List<Long> userIds, String title,Integer eventType,Integer fontColor, String content, String url, Long projectId,
                         Long recordId, String data,JSONObject jsonObject );

    /**@MethodName: saveMessageRole
     * @Description: 保存消息，推送提醒给用户   角色别名推送
     * @param msgType  消息类型 //消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核 2、工作通知 3、审核通知 4、报修通知 5、智能消火栓
     *      *     //7、水浸 8、可燃气 9、烟感 10、电气火灾通知 13、极早期预警
     * @param roleSignList 消息接受人权限别名
     * @param title 消息头
    * @param eventType 事件类型，根据每个消息类型来定义
    * @param fontColor 字体颜色
     * @param content 消息正文
     * @param url 跳转url
     * @param projectId  项目ID
     * @param recordId 关联记录ID
     * @param data  跳转携带数据
     * @param jsonObject 短信和语音文本字段，不同类型参数不一样要按，按文档要求传参
     * @Author: zhangZaiFa
     * @Date:2019/11/15 13:36
     **/

    Data saveMessageRole(Integer msgType, List<String> roleSignList, String title ,Integer eventType,Integer fontColor, String content, String url, Long projectId,
                         Long recordId, String data, JSONObject jsonObject);

    void saveMessageUserThread(Integer msgType, List<Long> userIds, String title,Integer eventType,Integer fontColor, String content, String url, Long projectId,
                               Long recordId, String data,JSONObject json);

    void saveMessageRoleThread(Integer msgType, List<String> roleSignList, String title,Integer eventType,Integer fontColor, String content, String url,
                               Long projectId, Long recordId, String data,JSONObject jsonObject);

    /**
     * 短信通知
     *
     * @param event 类型
     * @param telPhone 电话号码
     * @param json  JSONObject数据
     * @author huanggc
     */
    void sendSMS(Integer event, String telPhone, JSONObject json);
}
