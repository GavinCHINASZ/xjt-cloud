package com.xjt.cloud.message.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.message.core.service.service.MsgPushManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MsgPushManageController 消息推送管理
 *
 * @author zhangZaiFa
 * @date 2019-11-14 10100
 */
@RestController
@RequestMapping("/msgPushManage/")
class MsgPushManageController extends AbstractController {

    @Autowired
    private MsgPushManageService msgPushManageService;


    /** 
     * findMsgPushManage 查询消息推送管理
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 1356
     **/
    @RequestMapping(value = "/findMsgPushManage/{projectId}")
    public Data findMsgPushManage(String json) {
        return msgPushManageService.findMsgPushManage(json);

    }

    /** saveMsgPushManage 保存消息推送管理
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/15 1356
     **/
    @RequestMapping(value = "/saveMsgPushManage/{projectId}")
    public Data saveMsgPushManage(String json) {
        return msgPushManageService.saveMsgPushManage(json);
    }

    /**
     * findMsgPushManageNum 查询 消息推送管理 数量
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/09/04
     * @return com.xjt.cloud.commons.utils.Data
     **/
    @RequestMapping(value = "/findMsgPushManageNum")
    public Data findMsgPushManageNum(String json) {
        return msgPushManageService.findMsgPushManageNum(json);
    }
}
