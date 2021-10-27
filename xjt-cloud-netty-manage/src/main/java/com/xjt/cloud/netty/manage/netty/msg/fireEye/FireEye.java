package com.xjt.cloud.netty.manage.netty.msg.fireEye;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.CharacterType;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import com.xjt.cloud.netty.manage.common.utils.StringToByteArr;
import com.xjt.cloud.netty.manage.entity.FireEyeEvent;
import com.xjt.cloud.netty.manage.entity.VesdaVSMEvent;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName VesdaVSM
 * @Author dwt
 * @Date 2019-06-26 9:53
 * @Description:vesda vsm 信息解析
 * @Version 1.0
 */
public class FireEye extends BaseReceive {
    //暂时保存分段消息
    private static Map<String,String> map = new HashMap<>();
    private static String  sectionMsg ;
    /**
     *@Author: dwt
     *@Date: 2019-07-17 10:29
     *@Param: io.netty.channel.socket,String msg,String regId
     *@Return:java.lang.String
     *@Description:判断信息是否是多条组合，拆分判断每条信息是否符合协议标准
     */
    public static String analysisFireEye(SocketChannel socketChannel, String msg, String regId) throws UnsupportedEncodingException {
        byte[] by = StringToByteArr.hexStringToBinary(msg,msg.length());
        byte[] dataArr = new byte[4];
        System.arraycopy(by, 0, dataArr,  0, 4);//截取字节数量
        int dataSize = bytes2Int(dataArr);//字节数量
        byte[] aisleNumberArr = new byte[4]; //通道号
        int tag = 0;
        if(dataSize==100027) {
            tag = 4;
        }
        System.arraycopy(by, (8-tag), aisleNumberArr,  0, 4);
        byte[] eventTypeArr = new byte[6];//事件
        System.arraycopy(by, (13-tag), eventTypeArr,  0, 6);
        byte[] ipAddressArr = new byte[16];//ip
        System.arraycopy(by, (19-tag), ipAddressArr,  0, 16);
        byte[] imgArr = new byte[by.length-35-tag];
        FireEyeEvent fireEye = new FireEyeEvent();
        String aisleNumber = new String(aisleNumberArr).trim();//
        String ipAddress = new String(ipAddressArr).trim();
        String eventType = new String(eventTypeArr).trim();
        fireEye.setMsgType(Constants.FIRE_EYE_MSG_TYPE);
        new String(eventTypeArr).trim();
        fireEye.setAisleNumber(aisleNumber);
        fireEye.setIpAddress(ipAddress);
        if("fire".equalsIgnoreCase(eventType)){
            fireEye.setEventType(1);
        }else{
            fireEye.setEventType(2);
        }

        SysLog.info(  aisleNumber+"----------------------->aisleNumber");
        SysLog.info(	ipAddress+"----------------------->ipAddress");
        SysLog.info(eventType+"----------------------->eventType");
        SysLog.info(dataSize+"----------------------->dataSize");
        System.arraycopy(by, (35-tag), imgArr,  0, by.length-35-tag);
        try {
            aisleNumber = aisleNumber.substring(2,aisleNumber.length());
            msg = StringUtils.bytesToHexString(imgArr);
            SysLog.info(msg);
            String image =  Base64.encodeBase64String(imgArr);
            Map<String,String> map = new HashMap<>();
            map.put("file",StringUtils.urlEncode("data:image/jpg;base64,"+image));
            map.put("packageName","fireEye");
            String json = HttpUtils.httpPost(ConstantsClient.BEGIN_FIRE_EYE_IMAGE_URL,map);
            if(json!=null){
                JSONObject jsonObject = JSONObject.parseObject(json);
                fireEye.setImageUrl(jsonObject.getString("object"));
            }
            sendHttpGet(socketChannel, null, regId, fireEye);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String num = "02" + aisleNumber;
        num = "000" + num.length() + num;
        return num;
    }


    public static int bytes2Int(byte[] bytes ){
        //如果不与0xff进行按位与操作，转换结果将出错，有兴趣的同学可以试一下。
        int int1=bytes[0]&0xff;
        int int2=(bytes[1]&0xff)<<8;
        int int3=(bytes[2]&0xff)<<16;
        int int4=(bytes[3]&0xff)<<24;
        return int1|int2|int3|int4;
    }



}
