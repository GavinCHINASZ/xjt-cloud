package com.xjt.cloud.netty.manage.modbus;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.ExpiryMap;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ModbusChannelMap
 * @Description
 * @Author wangzhiwen
 * @Date 2021/1/20 13:27
 **/
public class ModbusChannelMap {
    private static volatile ExpiryMap<String, Integer> socketChannelIdMap = ExpiryMap.getInstance(ConstantsClient.MAP_EXPIRED);//客户端连接对象map <连接对象id，连接信息对象(注册id，连接对象)>
    public static volatile ExpiryMap<String, Long> timeMap = ExpiryMap.getInstance(ConstantsClient.MAP_EXPIRED);
    public static volatile ExpiryMap<String,String> ipMap = ExpiryMap.getInstance(ConstantsClient.MAP_EXPIRED);//客户端ip列表
    private  final static String sendTimeKey = "sendTime";

    /**
     * @Description modbus信息处理
     *
     * @param startAddress
     * @param registerValues
     * @author wangzhiwen
     * @date 2021/1/20 16:09
     * @return void
     */
    public static void msgHandle(int startAddress,int[] registerValues, int endAddress){
        int value;
        Long sendTime = timeMap.get(sendTimeKey);
        boolean isHeart = sendTime == null || (sendTime + ConstantsClient.MAP_EXPIRED) < DateUtils.getDateTime() ;
        SysLog.error("modbus内容：" + Arrays.toString(registerValues));
        // 对读取到的信息进行处理
        for (int address = startAddress;address <= endAddress;address++) {
            Integer saveStatus = getMap(address);//已处理的摄像机状态
            value = registerValues[address];
            if (saveStatus == null || saveStatus != value) {
                sendSaveMsg(address, value);//上传事件信息
                saveMap(address, value);//报事件信息保存到缓存中
            }else if (isHeart){//心跳
                sendSaveMsg(address, 98);
            }
        }
    }

    /**
     * @Description 把要信息上传服务器
     *
     * @param address
     * @param value
     * @author wangzhiwen
     * @date 2021/1/20 16:10
     * @return void
     */
    private static void sendSaveMsg(int address,int value){
        JSONObject json = new JSONObject();
        json.put("sensorNo",ConstantsClient.MODBUS_BEGIN_REG_ID);
        json.put("channelNo",address);
        json.put("status",value);
        System.out.println(json.toJSONString());
        timeMap.put(sendTimeKey, DateUtils.getDateTime());
        HttpUtils.httpGetRestTemplate(ConstantsClient.MODBUS_SEND_MSG_URL, json.toJSONString(),"json");
    }

    /**
     * @Description 保存摄像头基本信息入缓存
     *
     * @param address
     * @param value
     * @author wangzhiwen
     * @date 2021/1/20 16:10
     * @return void
     */
    private static void saveMap(int address,int value){
        String beginRegId = ConstantsClient.MODBUS_BEGIN_REG_ID + "_" + address;//modbus注册码
        socketChannelIdMap.put(beginRegId,value);
    }

    /**
     * @Description 从缓存中获取摄像头信息
     *
     * @param address
     * @author wangzhiwen
     * @date 2021/1/20 16:10
     * @return java.lang.Integer
     */
    private static Integer getMap(int address){
        String beginRegId = ConstantsClient.MODBUS_BEGIN_REG_ID + "_" + address;//modbus注册码
        return socketChannelIdMap.get(beginRegId);
    }

    public static void addIpMap(String ip) {
        if (ipMap.get(ip) == null){
            ipMap.put(ip,ip);
        }
    }
}
