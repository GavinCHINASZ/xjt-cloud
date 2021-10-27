package com.xjt.cloud.netty.manage.service.service;


import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:55
 * @Description:netty逻辑入口接口
 */
public interface NettyIndexService extends BaseService {
    /**
     *
     * 功能描述:以端口号启动netty服务
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 10:05
     */
    Data nettyInit(String json);

    /**
     *
     * 功能描述:主动发送信息到客户端
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 10:06
     */
    Data nettySendMsg(String json);

    /**
     *
     * 功能描述: 定时启动netty服务
     *
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 14:19
     */
    void connRedisTask();

    Data isChannelByRegId(String json);
}
