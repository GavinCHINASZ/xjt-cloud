package com.xjt.cloud.message.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.MsgAbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.message.core.dao.message.UserMsgManageDao;
import com.xjt.cloud.message.core.dao.message.MessageDao;
import com.xjt.cloud.message.core.entity.Message;
import com.xjt.cloud.message.core.entity.UserMsgManage;
import com.xjt.cloud.message.core.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * MessageServiceImpl 消息
 * @author zhangZaiFa
 * @date 2019-11-14 10:10
 */
@Service
public class MessageServiceImpl extends MsgAbstractService implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserMsgManageDao userMsgManageDao;

    /**
     * setDatabases 根据参数动态获取库名、表名
     * 
     * @Param: [msg]
     * @return com.xjt.cloud.message.manage.entity.Message
     * @author zhangZaiFa
     * @date 2019/11/15 11:40
     **/
    private Message setDatabases(Message msg) {
        msg.setDatabasesName(getDatabaseName(msg.getProjectId(), msg.getMessageType()));
        msg.setTableName(getTableName(msg.getProjectId(), msg.getMessageType()));
        return msg;
    }

    /**
     * findByMessageList 查询消息列表
     *
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 16:35
     **/
    @Override
    public Data findByMessageList(String json) {
        Message message = JSONObject.parseObject(json, Message.class);
        message = setDatabases(message);

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        if(message.getOrderCols() == null || message.getOrderCols().length == 0){
            String [] cols = {"createTime"};
            message.setOrderCols(cols);
            message.setOrderDesc(true);
        }
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return findByMessageList(message);
        }
        List<Message> list;
        list = messageDao.findByUserMessageList(message);

        // 正常搜索
        if(message.getSearchMethod() == null){
            Integer totalCount = message.getTotalCount();
            Integer pageSize = message.getPageSize();
            // 判断是否存在总数，如没有，则查询总记录数
            if (null == totalCount && null != pageSize && 0 != pageSize) {
                totalCount = messageDao.findByUserMessageListCount(message);
            }
            JSONObject data = new JSONObject(3);
            data.put("totalCount", totalCount);
            data.put("list", list);
            return asseData(data);
        }else{
            // 名称搜索搜索
           return asseData(packageData(list)) ;
        }
    }

    private Data findByMessageList(Message message) {
        List<Message> list = messageDao.findByMessageListCV5(message);

        // 正常搜索
        if (message.getSearchMethod() == null) {
            Integer totalCount = message.getTotalCount();
            Integer pageSize = message.getPageSize();
            // 判断是否存在总数，如没有，则查询总记录数
            if (null == totalCount && null != pageSize && 0 != pageSize) {
                totalCount = messageDao.findByMessageListCountCV5(message);
            }

            List<String> events = messageDao.findByMessageEventsCV5(message);
            JSONObject data = new JSONObject(3);
            data.put("events", events);
            data.put("totalCount", totalCount);
            data.put("list", list);
            return asseData(data);
        }else{
            // 名称搜索搜索
            return asseData(packageData(list)) ;
        }
    }

    /**
     * packageData
     * 封装数据包
     * 
     * @param list List<Message>
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     * @author zhangZaiFa
     * @date 2020/3/11 15:27
     **/
    private List<JSONObject> packageData(List<Message> list) {
        Map<Long, List< Message>> map = new HashMap<>();
        for(Message m : list){
            //获取该对象id的列表
            List<Message> temp = map.get(m.getProjectId());
            if(temp == null){
                //如果列表为null，我们没有看到一个带有此id的对象，因此create
                temp = new ArrayList<>(1);
                temp.add(m);
                //并将其添加到映射
                map.put(m.getProjectId(), temp);
            }
            //是否从地图中获取列表
            //或者做一个新的，我们需要添加我们的对象。
            temp.add(m);
        }

        // 封装数据格式
        Set<Long> set = map.keySet();
        Iterator<Long> it = set.iterator();
        List<JSONObject> data = new ArrayList<>(set.size());
        Long projectId;
        JSONObject jsonObject;
        while (it.hasNext()){
            jsonObject = new JSONObject(2);
            projectId = it.next();
            jsonObject.put("msgList", map.get(projectId));
            jsonObject.put("projectName", map.get(projectId).get(0).getProjectName());
            data.add(jsonObject);
        }
        return data;
    }

    /**
     * findByMessageProjectList 查询消息项目列表
     * 
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 18:02
     **/
    @Override
    public Data findByMessageProjectList(String json) {
        Message message = new Message();
        message = setDatabases(message);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        List<Long> projectIds = messageDao.findByMessageProjectList(message);

        List<Object> list = new ArrayList<>(projectIds.size());
        for (Long projectId : projectIds) {
            JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId);
            list.add(projectJson);
        }
        return asseData(list);
    }

    /**
     * deleteMessage 删除消息
     * 
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/18 10:13
     **/
    @Override
    public Data deleteMessage(String json) {
        Message message = JSONObject.parseObject(json, Message.class);
        message = setDatabases(message);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            messageDao.deleteMessageCV5(message);
        }else{
            messageDao.deleteMessage(message);
        }

        return Data.isSuccess();
    }

    /**
     * findByUserUnreadMessageCount 查询用户未读消息数量
     *
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/18 11:52
     **/
    @Override
    public Data findByUserUnreadMessageCount(String json) {
        Message message = new Message();
        message = setDatabases(message);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return asseData(messageDao.findByUserUnreadMessageCountCV5(message));
        }else{
            return asseData(messageDao.findByUserUnreadMessageCount(message));
        }
    }

    /**
     * updateMessageStatus 修改消息状态为已读
     *
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/18 11:54
     **/
    @Override
    public Data updateMessageStatus(String json) {
        Message message = JSONObject.parseObject(json, Message.class);
        message = setDatabases(message);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            messageDao.updateMessageStatusCV5(message);
        }else {
            if (message.getId() != null || CollectionUtils.isNotEmpty(message.getIds())) {
                messageDao.updateMessageStatus(message);
            } else {
                messageDao.updateUserMessageStatus(message);
            }
        }

        return Data.isSuccess();
    }

    /**
     * findMessageCategoryList
     * 查询消息类别列表
     *
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/17 10:34
     **/
    @Override
    public Data findMessageCategoryList(String json) {
        Message message = JSONObject.parseObject(json, Message.class);
        if (message == null){
            message = new Message();
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        message.setOwnUserId(userId);
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            setDatabases(message);
            return asseData(messageDao.findMessageCategoryListCV5(message));
        }else{
            return asseData(messageDao.findMessageCategoryList(message));
        }
    }

    /**
     * saveUserMsgManage
     * 保存用户消息管理
     * 
     * @Param: [json]
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/12/17 10:31
     **/
    @Override
    public Data saveUserMsgManage(String json) {
        UserMsgManage userMsgManage = JSONObject.parseObject(json, UserMsgManage.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        userMsgManage.setUserId(userId);
        userMsgManageDao.deleteUserMsgManage(userMsgManage);

        if(userMsgManage.getMsgType() != null){
            userMsgManageDao.saveUserMsgManage(userMsgManage);
        }else{
            userMsgManageDao.saveUserMsgManages(userMsgManage);
        }
        return asseData(userMsgManage.getIsNotify());
    }

    /**
     * findProjectMsgList
     * 查询项目消息列表
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/9 15:46
     **/
    @Override
    public Data findProjectMsgList(String json) {
        Message message = JSONObject.parseObject(json, Message.class);
        message = setDatabases(message);

        if(message.getOrderCols() == null || message.getOrderCols().length == 0){
            String [] cols = {"createTime"};
            message.setOrderCols(cols);
            message.setOrderDesc(true);
        }

        Integer totalCount = message.getTotalCount();
        Integer pageSize = message.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = messageDao.findProjectMsgListCount(message);
        }

        List<Message> list = messageDao.findProjectMsgList(message);

        JSONObject data = new JSONObject(2);
        data.put("totalCount", totalCount);
        data.put("list", list);
        return asseData(data);
    }
}
