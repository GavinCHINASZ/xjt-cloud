package com.xjt.cloud.message.core.service.service;


import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName MessageService 消息
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
public interface MessageService {
    /**@MethodName: findByMessageList 查询消息列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 13:56
     **/
    Data findByMessageList(String json);

    /**@MethodName: findByMessageList 查询消息项目列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/15 13:57
     **/
    Data findByMessageProjectList(String json);

    /**@MethodName: deleteMessage 删除消息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 10:11
     **/
    Data deleteMessage(String json);

    /**@MethodName: findByUserUnreadMessageCount 查询用户未读信息数量
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:52
     **/
    Data findByUserUnreadMessageCount(String json);

    /**@MethodName: updateMessageStatus 修改消息状态为已读
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:54
     **/
    Data updateMessageStatus(String json);

    /**@MethodName: findMessageCategoryList
     * @Description: 查询消息类别集合
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/17 10:32
     **/
    Data findMessageCategoryList(String json);

    /**@MethodName: saveUserMsgManage
     * @Description: 保存用户消息管理
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/17 10:31
     **/
    Data saveUserMsgManage(String json);

    /**@MethodName: findProjectMsg
     * @Description: 查询项目消息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/9 15:46
     **/
    Data findProjectMsgList(String json);
}
