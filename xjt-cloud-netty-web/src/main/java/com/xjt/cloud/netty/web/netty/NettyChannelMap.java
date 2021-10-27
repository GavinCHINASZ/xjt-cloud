package com.xjt.cloud.netty.web.netty;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.netty.web.common.ConstantsNettyWeb;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:连接客户端信息保存map类
 */
public class NettyChannelMap {
    private static volatile ExpiryMap<String, ClientMsg> socketChannelIdMap = ExpiryMap.getInstance(ConstantsNettyWeb.MAP_EXPIRED);//客户端连接对象map <连接对象id，连接信息对象(注册id，连接对象)>
    private static volatile Map<String,List<Integer>> portList = new ConcurrentHashMap<>();
    private static volatile RedisUtils redisUtils = initRedisUtils();

    /**
     *
     * 功能描述: redis工具类初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.RedisUtils
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:30
     */
    private static RedisUtils initRedisUtils(){
        if (redisUtils == null){
            synchronized ("redisUtils") {
                if (redisUtils == null){
                    redisUtils = (RedisUtils) SpringBeanUtil.getBean("redisUtils");
                }
            }
        }
        return redisUtils;
    }
    /**
     *
     * 功能描述:添加netty启动的端口号
     *
     * @param port
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 17:20
     */
    public static void addNettyPort(Integer port){
        List<Integer> list = portList.get("portList");
        if (CollectionUtils.isEmpty(list)){
            list = new ArrayList<>();
        }
        list.add(port);
        portList.put("portList",list);
    }

    /**
     *
     * 功能描述:判断该端口是否已启动netty监控
     *
     * @param port
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/28 0028 17:27
     */
    public static boolean isNettyPortInit(Integer port){
        List<Integer> list = portList.get("portList");
        if (CollectionUtils.isNotEmpty(list)){
            for (Integer i : list){
                if (i.equals(port)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * 功能描述: 把连接信息，注册信息，设备信息添加到map中
     *
     * @param regMsg
     * @param socketChannel
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 18:48
     */
    public static void addClient(String regMsg, ChannelHandlerContext socketChannel){
        String[] regMsgs = regMsg.split("_");//分解注册信息　0　注册标志　　1请求类型　　2客户端id，3客户端类型
        String clientType = regMsgs[1].toUpperCase();
        String clientId = regMsgs[2];
        String channelId = socketChannel.channel().id().asLongText();
        if (ConstantsNettyWeb.LOGIN_TYPE.equals(clientType)){//判断是否是登录注册web socket
            String key = clientId + ConstantsNettyWeb.LOGIN_WEBSOCKET + regMsgs[3] +"_" + regMsgs[4];
            SysLog.info("添加的channelId= " + channelId);
            redisUtils.set(key, channelId + "_" + ConstantsNettyWeb.WEB_SOCKET_SEND_MSG_URL + "_" + regMsgs[4],ConstantsNettyWeb.MSG_BACK_TIME);//保存注册码与连接id的关系
            socketChannelIdMap.put(channelId, new ClientMsg(regMsg, socketChannel), ConstantsNettyWeb.MAP_EXPIRED);//保存连接id与连接用户、对像的关系
        }else {
            String regId = clientType + "_" + clientId;
            List<String> regIdList = getRegIdListByIotType(clientType);

            boolean isExist = false;
            for (String id : regIdList) {//判断是否存该类型的客户端信息
                if (regId.equals(id)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {//如不存在，则添加进list中
                regIdList.add(regId);
            }
            redisUtils.set(clientType, JSONObject.toJSONString(regIdList),ConstantsNettyWeb.MSG_BACK_TIME);
            redisUtils.set(regId, channelId,ConstantsNettyWeb.MSG_BACK_TIME);//保存注册码与连接id的关系
            socketChannelIdMap.put(channelId, new ClientMsg(regMsg, socketChannel), ConstantsNettyWeb.MAP_EXPIRED);//保存连接id与连接用户、对像的关系
        }
    }

    /**
     *
     * 功能描述: 以注册id得到连接通道对象
     *
     * @param regId
     * @return: io.netty.channel.Channel
     * @auther: wangzhiwen
     * @date: 2019/4/25 17:01
     */
    public static ChannelHandlerContext getChannelByRegId(String regId){
        regId = StringUtils.urlEncode(regId);
        Object clientId = redisUtils.get(regId);
        if (null != clientId){
            ClientMsg clientMsg = socketChannelIdMap.get(clientId);
            if (clientMsg != null){
                return clientMsg.getSocketChannel();
            }
        }
        return null;
    }

    /**
     * @Description 以通道号得到通道信息
     *
     * @param channelId
     * @author wangzhiwen
     * @date 2020/8/24 11:31
     * @return io.netty.channel.ChannelHandlerContext
     */
    public static ChannelHandlerContext getChannelByChannelId(String channelId){
        ClientMsg clientMsg = socketChannelIdMap.get(channelId);
        if (clientMsg != null){
            return clientMsg.getSocketChannel();
        }
        return null;
    }

    /**
     *
     * 功能描述: 连接通道断开后，删除其中的缓存信息
     *
     * @param socketChannel
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/4/25 17:02
     */
    public static void remove(SocketChannel socketChannel){
        String id = socketChannel.id().asLongText();
        ClientMsg clientMsg = socketChannelIdMap.get(id);
        SysLog.logger.error("开始清除保存的用户信息:RegId=" + (clientMsg != null ? clientMsg.getRegId() : "") + "  id=" + id);
        try {
            socketChannelIdMap.remove(id);
        }catch (Exception ex){

        }
        try {
            //删除客户id信息
            String str = clientMsg.getRegId();
            if (StringUtils.isNotEmpty(str)) {
                String[] regMsgs = str.split("_");
                str = regMsgs[1] + "_" + regMsgs[2];
                try {
                    if (clientMsg != null) {
                        redisUtils.del(str);
                    }
                } catch (Exception ex) {

                }
                String iotType = regMsgs[1].toUpperCase();
                List<String> regIdList = getRegIdListByIotType(iotType);//得到该类型的web客户端id列表
                if (regIdList.size() > 0) {
                    Iterator<String> it = regIdList.iterator();
                    while (it.hasNext()) {//删除该id列表
                        String regId = it.next();
                        if (str.equals(regId)) {
                            it.remove();
                            break;
                        }
                    }
                    try {
                        redisUtils.set(iotType, JSONObject.toJSONString(regIdList) ,ConstantsNettyWeb.MSG_BACK_TIME);
                    } catch (Exception ex) {

                    }
                }
            }
        }catch (Exception ex){

        }

        SysLog.logger.error("清除保存的用户信息成功:RegId=" + (clientMsg != null ? clientMsg.getRegId() : "") + "  id=" + id);
    }

    /**
     *
     * 功能描述:以物联类型得到该类型的web客户端id列表
     *
     * @param iotType
     * @return: java.util.List<java.lang.String>
     * @auther: wangzhiwen
     * @date: 2019/9/27 14:56
     */
    public static List<String> getRegIdListByIotType(String iotType){
        Object object = redisUtils.get(iotType);//从缓存中取得该类开的客户端id列表
        List<String> regIdList;
        if (object != null) {
            regIdList = JSONObject.parseObject(object.toString(), List.class);
        }else {
            regIdList = new ArrayList<>();
        }
        return regIdList;
    }

    /**
     * @Description 以客户id得到注册id
     *
     * @param clientId
     * @author wangzhiwen
     * @date 2020/8/24 10:46
     * @return java.lang.String
     */
    public static String getRegIdByClientId(String clientId){
        Object object = redisUtils.get(clientId);//从缓存中取得该类开的客户端id列表
        if (object != null) {
            return object.toString().split("_")[0];
        }
        return null;
    }
}