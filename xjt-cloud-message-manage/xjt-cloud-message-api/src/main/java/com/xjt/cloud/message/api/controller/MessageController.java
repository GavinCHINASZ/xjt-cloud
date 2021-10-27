package com.xjt.cloud.message.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.message.core.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MessageController 消息
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:100
 * @Description
 */
@RestController
@RequestMapping("/message")
class MessageController extends AbstractController {

    @Autowired
    private MessageService messageService;


    /**@MethodName: findByMessageList 查询消息列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 13:56
     **/
    @RequestMapping(value = "/findMessageList")
    public Data findByMessageList(String json) {
        return messageService.findByMessageList(json);
    }

    /**@MethodName: findByMessageList 查询消息项目列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 13:57
     **/
    @RequestMapping(value = "/findByMessageProjectList")
    public Data findByMessageProjectList(String json) {
        return messageService.findByMessageProjectList(json);
    }

    /**@MethodName: deleteMessage 删除消息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 10:11
     **/
    @RequestMapping(value = "/deleteMessage")
    public Data deleteMessage(String json) {
        return messageService.deleteMessage(json);
    }

    /**@MethodName: findByUserUnreadMessageCount 查询用户未读消息数量
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:52 
     **/
    @RequestMapping(value = "/findByUserUnreadMessageCount")
    public Data findByUserUnreadMessageCount(String json) {
        return messageService.findByUserUnreadMessageCount(json);
    }

    /**@MethodName: updateMessageStatus 修改消息状态为已读
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:54
     **/
    @RequestMapping(value = "/updateMessageStatus")
    public Data updateMessageStatus(String json) {
        return messageService.updateMessageStatus(json);
    }

    /**@MethodName: findMessageCategoryList
     * @Description: 查询消息类别
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/17 10:31
     **/
    @RequestMapping(value = "/findMessageCategoryList")
    public Data findMessageCategoryList(String json) {
        return messageService.findMessageCategoryList(json);
    }

    /**@MethodName: saveUserMsgManage
     * @Description: 保存用户消息管理
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/17 10:31
     **/
    @RequestMapping(value = "/saveUserMsgManage")
    public Data saveUserMsgManage(String json) {
        return messageService.saveUserMsgManage(json);
    }
}
