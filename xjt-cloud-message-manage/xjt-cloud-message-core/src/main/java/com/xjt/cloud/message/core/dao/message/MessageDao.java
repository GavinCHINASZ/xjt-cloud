package com.xjt.cloud.message.core.dao.message;

import com.xjt.cloud.message.core.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName MessageDao 消息
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
@Repository
public interface MessageDao {

    /**@MethodName: findByMessageList 查询消息列表
     * @Description:
     * @Param: [message]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    List<Message> findByMessageListCV5(Message message);

    /**@MethodName: findByMessageListCount 查询消息总数
     * @Description:
     * @Param: [message]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    Integer findByMessageListCountCV5(Message message);

    /**@MethodName: findByMessageList 查询固定用户消息列表
     * @Description:
     * @Param: [message]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    List<Message> findByUserMessageList(Message message);

    /**@MethodName: findByMessageListCount 查询消息总数
     * @Description:
     * @Param: [message]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    Integer findByUserMessageListCount(Message message);

    /**@MethodName: findByMessageProjectList 查询项目
     * @Description:
     * @Param: [message]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:37
     **/
    List<Long> findByMessageProjectList(Message message);

    /**@MethodName: deleteMessage 删除消息记录
     * @Description:
     * @Param: [message]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/18 10:15
     **/
    void deleteMessage(Message message);
    /**@MethodName: deleteMessage 删除消息记录
     * @Description:
     * @Param: [message]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/18 10:15
     **/
    void deleteMessageCV5(Message message);

    /**@MethodName: updateMessageStatus 修改消息状态为已读
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:54
     **/
    void updateMessageStatus(Message message);

    /**@MethodName: updateMessageStatus 修改消息状态为已读
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:54
     **/
    void updateMessageStatusCV5(Message message);

    /**
     * @param message
     * @return int
     * @Description 修改用户信息状态，只修改信息为指定用户的
     * @author wangzhiwen
     * @date 2021/1/14 14:25
     */
    int updateUserMessageStatus(Message message);

    /**@MethodName: findByUserUnreadMessageCount 查询用户未读消息数量
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:52
     **/
    Message findByUserUnreadMessageCount(Message message);

    /**@MethodName: findByUserUnreadMessageCount 查询用户未读消息数量
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/18 11:52
     **/
    Long findByUserUnreadMessageCountCV5(Message message);

    /**@MethodName: findMessageCategoryList
     * @Description: 查询消息类别
     * @Param: [message]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/12/17 11:05
     **/
    List<Message> findMessageCategoryList(Message message);

    /**@MethodName: findMessageCategoryList
     * @Description: 查询消息类别
     * @Param: [message]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/12/17 11:05
     **/
    List<Message> findMessageCategoryListCV5(Message message);

    /**@MethodName: findByMessageEvents
     * @Description: 查询消息事件
     * @Param: [message]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2020/3/11 17:06
     **/
    List<String> findByMessageEventsCV5(Message message);

    /**@MethodName: findProjectMsgList
     * @Description: 查询项目消息列表
     * @Param: [message]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2020/4/10 11:09
     **/
    List<Message> findProjectMsgList(Message message);

    /**@MethodName: findProjectMsgListCount
     * @Description: 我的消息列表数量
     * @Param: [message]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/4/26 13:44
     **/
    Integer findProjectMsgListCount(Message message);
}
