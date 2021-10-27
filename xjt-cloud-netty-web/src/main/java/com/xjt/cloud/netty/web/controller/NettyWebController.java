package com.xjt.cloud.netty.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.netty.web.netty.ReceiveHandle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/23 14:25
 * @Description: 物联网接口Controller
 */
@RestController
@RequestMapping("/netty/web/")
public class NettyWebController extends AbstractController {

    /**
     *
     * 功能描述:物联网设备信息保存
     *
     * @param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/9/27 10:16
     */
    @RequestMapping(value = "sendMsg")
    public Data dataAccess(String json){
            nettySendMsg(JSON.parseObject(json));
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述: 服务器主动给web端发送信息
     *
     * @param jsonObject
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/9/27 11:37
     */
    private void nettySendMsg(JSONObject jsonObject){
        ReceiveHandle.nettySendMsg(jsonObject);
    }
}
