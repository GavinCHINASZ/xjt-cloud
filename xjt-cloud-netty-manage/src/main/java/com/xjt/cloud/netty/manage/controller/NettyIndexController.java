package com.xjt.cloud.netty.manage.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.service.service.NettyIndexService;
import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:netty控制类
 */
@RestController
@RequestMapping("netty/")
public class NettyIndexController extends AbstractController {

    @Autowired
    private NettyIndexService nettyIndexService;

    /**
     *
     * 功能描述: 以传入的端口号，启动netty监控进程接口
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 14:36
     */
    @RequestMapping(value = "nettyStart")
    public Data nettyStart(String json){
        return nettyIndexService.nettyInit(json);
    }

    /**
     *
     * 功能描述: 服务器主动发送信息给客户端接口
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 14:37
     */
    @RequestMapping(value = "nettySendMsg")
    public Data nettySendMsg(HttpServletRequest request, String json){
        return nettyIndexService.nettySendMsg(json);
    }
    @RequestMapping(value = "isChannelByRegId")
    public Data isChannelByRegId(String json){
        return nettyIndexService.isChannelByRegId(json);
    }
}
