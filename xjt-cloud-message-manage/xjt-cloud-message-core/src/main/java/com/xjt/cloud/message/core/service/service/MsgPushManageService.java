package com.xjt.cloud.message.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * MsgPushManageService 消息推送管理
 *
 * @author zhangZaiFa
 * @date 2019-11-14 10:10
 */
public interface MsgPushManageService {

    /**
     * 查询
     *
     * @param json 参数
     * @author zhangZaiFa
     * @date 2020-09-04
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findMsgPushManage(String json);

    /**
     * 保存
     *
     * @param json 参数
     * @author zhangZaiFa
     * @date 2020-09-04
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data saveMsgPushManage(String json);

    /**
     * findMsgPushManageNum 查询 消息推送管理 数量
     *
     * @param json 参数
     * @author huanggc
     * @date 2020-09-04
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findMsgPushManageNum(String json);
}
