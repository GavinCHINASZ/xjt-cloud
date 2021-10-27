package com.xjt.cloud.message.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.MsgAbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.message.core.dao.message.MsgPushManageDao;
import com.xjt.cloud.message.core.entity.MsgPushManage;
import com.xjt.cloud.message.core.entity.User;
import com.xjt.cloud.message.core.service.service.MsgPushManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MsgPushManageServiceImpl 消息推送管理
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
@Service
public class MsgPushManageServiceImpl extends MsgAbstractService implements MsgPushManageService {

    @Autowired
    private MsgPushManageDao msgPushManageDao;

    /**
     * @Description 查询项目信息推送配置
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/1/13 14:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findMsgPushManage(String json) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return findMsgPushManageCV5(json);
        }
        MsgPushManage msgPushManage = JSONObject.parseObject(json, MsgPushManage.class);

        JSONObject data = new JSONObject(4);
        msgPushManage = msgPushManageDao.findMsgPushManage(msgPushManage);//查询项目信息推送配置

        //查询语音推送用户信息
        List<User> userList = new ArrayList<>();
        String[] userIds;
        if (StringUtils.isNotEmpty(msgPushManage.getPhoneUserIds())){
            userIds = msgPushManage.getPhoneUserIds().split(";");
            userList = msgPushManageDao.findUserList(msgPushManage.getProjectId(),userIds);
        }
        data.put("phoneUsers", userList);

        //查询短信推送用户信息
        userList = new ArrayList<>();
        if (StringUtils.isNotEmpty(msgPushManage.getSmsUserIds())){
            userIds = msgPushManage.getSmsUserIds().split(";");
            userList = msgPushManageDao.findUserList(msgPushManage.getProjectId(),userIds);
        }
        data.put("smsUsers", userList);

        //查询消息推送用户信息
        userList = new ArrayList<>();
        if (StringUtils.isNotEmpty(msgPushManage.getMsgUserIds())){
            userIds = msgPushManage.getMsgUserIds().split(";");
            userList = msgPushManageDao.findUserList(msgPushManage.getProjectId(),userIds);
        }
        data.put("msgUsers", userList);
        data.put("msgPushManage", msgPushManage);

        return asseData(data);
    }

    private Data findMsgPushManageCV5(String json) {
        MsgPushManage msgPushManage = JSONObject.parseObject(json, MsgPushManage.class);
        List<MsgPushManage> list = msgPushManageDao.findMsgPushManageListCV5(msgPushManage);
        List<User> smsUsers = msgPushManageDao.findSmsUsersCV5(msgPushManage);
        List<User> phoneUsers = msgPushManageDao.findPhoneUsersCV5(msgPushManage);

        JSONObject data = new JSONObject(3);
        data.put("smsUsers", smsUsers);
        data.put("phoneUsers", phoneUsers);
        data.put("list", list);
        return asseData(data);
    }

    /**
     * @Description 保存项目信息推送配置
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/1/13 14:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveMsgPushManage(String json) {
        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            return saveMsgPushManageCV5(json);
        }
        MsgPushManage msgPushManage = JSONObject.parseObject(json, MsgPushManage.class);
        if (msgPushManage.getProjectMsgLevelId() == null){//判断是否存在项目信息级别配置信息id
            MsgPushManage saveMsgPushManage = new MsgPushManage();
            saveMsgPushManage.setProjectId(msgPushManage.getProjectId());
            saveMsgPushManage.setEventTypeLevel1(msgPushManage.getEventTypeLevel1());
            saveMsgPushManage.setEventTypeLevel2(msgPushManage.getEventTypeLevel2());
            saveMsgPushManage.setEventTypeLevel3(msgPushManage.getEventTypeLevel3());
            saveMsgPushManage.setEventTypeLevel4(msgPushManage.getEventTypeLevel4());
            saveMsgPushManage.setEventTypeNameLevel1(msgPushManage.getEventTypeNameLevel1());
            saveMsgPushManage.setEventTypeNameLevel2(msgPushManage.getEventTypeNameLevel2());
            saveMsgPushManage.setEventTypeNameLevel3(msgPushManage.getEventTypeNameLevel3());
            saveMsgPushManage.setEventTypeNameLevel4(msgPushManage.getEventTypeNameLevel4());
            msgPushManageDao.saveProjectMsgLevel(saveMsgPushManage);
            msgPushManage.setProjectMsgLevelId(saveMsgPushManage.getId());
        }
        int num;
        if (msgPushManage.getId() == null ){//判断是否存在信息发送配置id，如没有就新增
            num = msgPushManageDao.saveMsgPushManage(msgPushManage);
        }else{
            num = msgPushManageDao.modifyMsgPushManage(msgPushManage);
        }
        HttpUtils.httpGet(PropertyUtils.getProperty(Constants.PROJECT_MSG_LEVEL_CACHE_KEY));
        return asseData(num);
    }

    private Data saveMsgPushManageCV5(String json) {
        MsgPushManage msgPushManage = JSONObject.parseObject(json, MsgPushManage.class);
        msgPushManageDao.deleteMsgPushManageCV5(msgPushManage);
        msgPushManageDao.saveMsgPushManageCV5(msgPushManage);
        return Data.isSuccess();
    }

    /**
     * findMsgPushManageNum 查询 消息推送管理 数量
     *
     * @param json 参数
     * @author huanggc
     * @date 2020-09-04
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @Override
    public Data findMsgPushManageNum(String json) {
        MsgPushManage msgPushManage = JSONObject.parseObject(json, MsgPushManage.class);
        Integer msgPushManageNum = msgPushManageDao.findMsgPushManageNum(msgPushManage);
        return asseData(msgPushManageNum);
    }
}
