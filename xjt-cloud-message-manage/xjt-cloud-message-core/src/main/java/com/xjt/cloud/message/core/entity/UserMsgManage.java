package com.xjt.cloud.message.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;


/**
 * @ClassName Message 消息
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
public class UserMsgManage  {

    //消息类型-1、火警通知 -2、火眼通知 -3、水压通知  -4、项目审核 2、工作通知 3、审核通知 4、报修通知 5、智能消火栓
    //7、水浸 8、可燃气 9、烟感 10、电气火灾通知 13、极早期预警
    private Integer msgType;

    //用户ID
    private Long userId;

    //是否打开提醒 1、不打开  2、打开
    private Integer isNotify;

    private List<Integer> msgTypes;

    public List<Integer> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<Integer> msgTypes) {
        this.msgTypes = msgTypes;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Integer isNotify) {
        this.isNotify = isNotify;
    }
}
