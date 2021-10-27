package com.xjt.cloud.netty.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.RedisUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.netty.NettyStartThread;
import com.xjt.cloud.netty.manage.common.utils.ThreadPoolUtils;
import com.xjt.cloud.netty.manage.netty.NettyChannelMap;
import com.xjt.cloud.netty.manage.netty.msg.fireEye.FireEye;
import com.xjt.cloud.netty.manage.service.service.NettyIndexService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:56
 * @Description:netty逻辑入口接口中实现类
 */
@Service
public class NettyIndexServiceImpl extends AbstractService implements NettyIndexService {
    @Autowired
    private RedisUtils redisUtils;
    /**
     *
     * 功能描述:以端口号启动netty服务
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 10:05
     */
    @Override
    public Data nettyInit(String json){
        synchronized ("nettyInit") {
            JSONObject jSONObject = JSON.parseObject(json);
            Integer port = jSONObject.getInteger("port");
            if (NettyChannelMap.isNettyPortInit(port)) {
                ThreadPoolUtils.getInstance().execute(new NettyStartThread(port));
            }
        }
        return asseData(1,json);
    }
    @Override
    public Data isChannelByRegId(String json){
        JSONObject jSONObject = JSON.parseObject(json);
        SocketChannel channel=(SocketChannel) NettyChannelMap.getChannelByRegId(jSONObject.getString("regId"));
        return asseData(channel.id());
    }

    /**
     *
     * 功能描述:主动发送信息到客户端
     *
     * @param json
     * @return: com.xjt.cloud.netty.manage.common.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/4/25 10:06
     */
    @Override
    public Data nettySendMsg(String json){
        try {
            SysLog.info("收到的参数----------》"+json);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String regId = jsonObject.getString("regId");
            String msg = jsonObject.getString("msg");
            SocketChannel channel=(SocketChannel) NettyChannelMap.getChannelByRegId(regId);
            if(channel != null){
                byte[] bytes = StringToByteArr.hexStringToBinary(msg,msg.length());
                ByteBuf sendMsgBf = Unpooled.copiedBuffer(bytes);//进行处理
                channel.writeAndFlush(sendMsgBf);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return asseData(1,json);
    }

    /**
     *
     * 功能描述: 定时启动netty服务
     *
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 14:19
     */
    @Override
    public void connRedisTask(){
        redisUtils.get("test");
        /*JSONObject portJson = HttpUtils.httpGet("");//用接口取得netty要监控的端口号列表
        List<Integer> listPort = portJson.getJSONArray("ports");
        for (Integer port : listPort){
            if (NettyChannelMap.isNettyPortInit(port)) {//判断该端口是否已监控
                ThreadPoolUtils.getInstance().execute(new NettyStartThread(port));
            }
        }*/
    }



}
