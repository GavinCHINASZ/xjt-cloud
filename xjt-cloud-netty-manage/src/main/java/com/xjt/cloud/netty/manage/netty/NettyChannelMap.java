package com.xjt.cloud.netty.manage.netty;

import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接客户端信息保存map类
 *
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 */
public class NettyChannelMap {
    // 客户端连接对象map <连接对象id，连接信息对象(注册id，连接对象)>
    private static volatile ExpiryMap<String, ClientMsg> socketChannelIdMap = ExpiryMap.getInstance(ConstantsClient.MAP_EXPIRED);
    private static volatile Map<String, List<Integer>> portList = new ConcurrentHashMap<>();
    private static volatile RedisUtils redisUtils;

    /**
     * 功能描述: redis工具类初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.RedisUtils
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:30
     */
    private static RedisUtils initRedisUtils() {
        if (redisUtils == null) {
            synchronized ("redisUtils") {
                if (redisUtils == null) {
                    redisUtils = (RedisUtils) SpringBeanUtil.getBean("redisUtils");
                }
            }
        }
        return redisUtils;
    }

    /**
     * 功能描述:添加netty启动的端口号
     *
     * @param port
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 17:20
     */
    public static void addNettyPort(Integer port) {
        List<Integer> list = portList.get("portList");
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list.add(port);
        portList.put("portList", list);
    }

    /**
     * 功能描述:判断该端口是否已启动netty监控
     *
     * @param port
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 17:27
     */
    public static boolean isNettyPortInit(Integer port) {
        List<Integer> list = portList.get("portList");
        if (CollectionUtils.isNotEmpty(list)) {
            for (Integer i : list) {
                if (i.equals(port)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 功能描述: 把连接信息，注册信息，设备信息添加到map中
     *
     * @param regId
     * @param socketChannel
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 18:48
     */
    public static void addClient(String regId, SocketChannel socketChannel) {
        if (null == NettyChannelMap.getRegIdByChannelId(socketChannel)) {
            regId = StringUtils.urlEncode(regId);
            String channelId = socketChannel.id().asLongText();
            // 保存注册码与连接id的关系
            initRedisUtils().set(regId, channelId + "_" + ConstantsClient.NETTY_SOCKET_SEND_MSG_URL, ConstantsClient.MSG_BACK_TIME);
            SysLog.info(regId + "-------添加的注册码----------->channelId=" + channelId);
            // 保存连接id与连接用户、对像的关系
            socketChannelIdMap.put(channelId, new ClientMsg(regId, socketChannel), ConstantsClient.MAP_EXPIRED);
        }
    }

    /**
     * 功能描述: 判断用户信息是否要保存
     *
     * @param socketChannel
     * @param msg
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/4/25 17:00
     */
    public static boolean isClientMsgSave(SocketChannel socketChannel, String msg) {
        String key = getRegIdByChannelId(socketChannel) + msg;
        Long dateTime = DateUtils.getDateTime();
        Object obj = initRedisUtils().get(key);
        // 判断信息是否存在
        if (obj != null) {
            Long time = Long.parseLong(obj.toString());
            // 判断是否有超过十分钟
            if (time + ConstantsClient.MSG_BACK_TIME * 1000 > dateTime) {
                return false;
            }
        }
        initRedisUtils().set(key, dateTime, ConstantsClient.MSG_BACK_TIME);
        return true;
    }
    /*public static boolean isClientMsgSave(SocketChannel socketChannel,String msg){
        String key = getRegIdByChannelId(socketChannel) + "msg";
        Long dateTime = DateUtils.getDateTime();
        List<Object> list = initRedisUtils().listGet(key,0,-1);
        ClientMsg clientMsg;
        if (CollectionUtils.isNotEmpty(list)){//判断信息是否存在
            for (int i = 0; i < list.size(); i++) {
                clientMsg = (ClientMsg)list.get(i);
                Long time = clientMsg.getTime();
                if (msg.equals(clientMsg.getMsg()) && time + ConstantsClient.MSG_BACK_TIME * 1000 > dateTime) {//判断是否有超过十分钟
                    return false;
                }
            }
        }else {
            list = new ArrayList<>();
        }
        clientMsg = new ClientMsg();
        clientMsg.setMsg(msg);
        clientMsg.setTime(dateTime);
        list.add(clientMsg);
        initRedisUtils().listSet(key,list,ConstantsClient.MSG_BACK_TIME);
        return true;
    }*/

    /**
     * 功能描述: 以注册id得到连接通道对象
     *
     * @param regId
     * @return: io.netty.channel.Channel
     * @auther: wangzhiwen
     * @date: 2019/4/25 17:01
     */
    public static Channel getChannelByRegId(String regId) {
        regId = StringUtils.urlEncode(regId);
        String clientId = initRedisUtils().getString(regId);
        if (StringUtils.isNotEmpty(clientId)) {
            clientId = clientId.split("_")[0];
            ClientMsg clientMsg = socketChannelIdMap.get(clientId);
            if (clientMsg != null) {
                return clientMsg.getSocketChannel();
            }
        }
        return null;
    }

    /**
     * 功能描述: 以连接通道id得到客户端注册码id
     *
     * @param socketChannel
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019-04-26 17:21
     */
    public static String getRegIdByChannelId(SocketChannel socketChannel) {
        ClientMsg clientMsg = socketChannelIdMap.get(socketChannel.id().asLongText());
        if (null != clientMsg) {
            return clientMsg.getRegId();
        }
        return null;
    }

    /**
     * 功能描述: 连接通道断开后，删除其中的缓存信息
     *
     * @param socketChannel
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 17:02
     */
    public static void remove(SocketChannel socketChannel) {
        String id = socketChannel.id().asLongText();
        ClientMsg clientMsg = socketChannelIdMap.get(id);
        SysLog.info("开始清除保存的用户信息:RegId=" + (clientMsg != null ? clientMsg.getRegId() : "") + "  id=" + id);
        try {
            socketChannelIdMap.remove(id);
        } catch (Exception ex) {

        }
        try {
            initRedisUtils().del(id);
        } catch (Exception ex) {

        }
        try {
            if (clientMsg != null) {
                initRedisUtils().del(clientMsg.getRegId());
            }
        } catch (Exception ex) {

        }
    }

    /**
     * 添加设备注册码是否存在
     *
     * @param regId
     * @return void
     * @author wangzhiwen
     * @date 2020/12/3 13:52
     */
    public static void saveRegIdExistStatus(String regId, boolean status) {
        redisUtils.set(regId, status, ConstantsClient.MAP_EXPIRED);
    }

    /**
     * @param regId
     * @return boolean
     * @Description 判断设备注册码是否存在
     * @author wangzhiwen
     * @date 2020/12/3 13:58
     */
    public static Boolean isRegIdExist(String regId) {
        String status = redisUtils.getString(regId);
        if (StringUtils.isNotEmpty(status)) {
            if ("true".equals(status)) {
                return true;
            }
            return false;
        }
        return null;
    }
}