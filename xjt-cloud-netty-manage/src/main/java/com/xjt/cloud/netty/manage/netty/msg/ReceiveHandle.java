package com.xjt.cloud.netty.manage.netty.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.common.utils.ConstantsClient;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import com.xjt.cloud.netty.manage.entity.VesdaVSMEvent;
import com.xjt.cloud.netty.manage.modbus.ModbusChannelMap;
import com.xjt.cloud.netty.manage.modbus.ModbusInit;
import com.xjt.cloud.netty.manage.netty.NettyChannelMap;
import com.xjt.cloud.netty.manage.netty.msg.electrical.HeJiElectricalFire;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.*;
import com.xjt.cloud.netty.manage.netty.msg.fireEye.FireEye;
import com.xjt.cloud.netty.manage.netty.msg.vesda.VesdaVSM;
import com.xjt.cloud.netty.manage.netty.msg.waterSys.MKWaterSys;
import com.xjt.cloud.netty.manage.netty.msg.waterSys.TKWaterSys;
import com.xjt.cloud.netty.manage.netty.msg.waterSys.TopsailWaterSys;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * netty协议入口类
 *
 * @author wangzhiwen
 * @date 2019/4/25 11:00
 */
public class ReceiveHandle {

    /**
     * 功能描述:netty接收到的客户端上传信息处理
     *
     * @param obj 上传信息obj
     * @return java.lang.String 返回给客户端的信息，如果为空，表示不用返回信息
     * @author wangzhiwen
     * @date 2019/4/25 10:04
     */
    public String receiveHandle(SocketChannel socketChannel, Object obj) {
        // ByteBuf是一个引用计数对象实现ReferenceCounted，他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        // 十进制信息转化
        // 是否需要调用心跳接口
        boolean isHeartbeat = true;
        ByteBuf in = (ByteBuf) obj;
        String msg = null;
        InetSocketAddress inSocket = (InetSocketAddress) socketChannel.remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        // 处理堆缓冲区
        if (in.hasArray()) {
            msg = new String(in.array(), in.arrayOffset() + in.readerIndex(), in.readableBytes());
        } else {
            // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[in.readableBytes()];
            in.getBytes(in.readerIndex(), bytes);
            try {
                msg = new String(bytes, "UTF-8");
                if (StringUtils.startsWith(msg, "$${") && StringUtils.endsWith(msg, "}@@")) {
                    // 海湾200、5000协议字符格式不一样
                    msg = new String(bytes, "GB18030");
                }
            } catch (Exception e) {
                SysLog.error(e);
            }

            boolean msgWithBoolean = StringUtils.startsWith(msg, "{") && StringUtils.endsWith(msg, "}");
            boolean msgWithRegBoolean = StringUtils.startsWith(msg, ConstantsClient.BEGIN_REG_ID);
            boolean msgBoolean = StringUtils.isEmpty(msg) || StringUtils.isEmpty(msg.trim())
                    || msg.length() == 0 || StringUtils.isMessyCode(msg) || StringUtils.isContainErrorCode(msg);
            if (!msgWithBoolean && !msgWithRegBoolean && msgBoolean) {
                // 判断信息是否为空，如为空，做16进制信息转化
                msg = StringUtils.bytesToHexString(bytes);
                SysLog.logger.info("IP:" + clientIp + "  接收到的十六进制信息:" + msg);
            } else {
                SysLog.logger.info("IP:" + clientIp + "  接收到的ASCII码信息:" + msg);
            }
        }

        msg = msg.replace("\r", "").replace("\n", "").trim();
        if ("00000000".equals(msg)) {
            return "";
        }

        // 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
        boolean msgStartsBoolean = StringUtils.startsWith(msg, ConstantsClient.BEGIN_REG_ID) && !msg.startsWith("$$(") && !msg.startsWith("$${")
                && !StringUtils.startsWith(msg, ConstantsClient.BEGIN_FIRE_EYE_REG_ID);
        boolean modBoolean = msg.indexOf("modbus") == -1;
        if (msgStartsBoolean && modBoolean) {
            if (null == NettyChannelMap.getChannelByRegId(msg)) {
                if (!isMyProductByRegId(socketChannel, msg, msg)) {
                    SysLog.logger.error("IP:" + clientIp + "  该注册码不存在:" + msg);
                    return "";
                }
            }
            // 把连接信息与客户端信息保存在map中
            NettyChannelMap.addClient(msg, socketChannel);
        } else if (StringUtils.startsWith(msg, ConstantsClient.BEGIN_FIRE_EYE_REG_ID) || msg.length() > 2000) {
            NettyChannelMap.addClient(msg, socketChannel);
            isHeartbeat = false;
        } else if (msg.startsWith("$$(") && msg.endsWith(")")) {
            // 海湾200、5000 张玖利盒子
            String from = msg.substring(3, 8).trim();
            NettyChannelMap.addClient(from, socketChannel);
        } else if (msg.startsWith("$${") && msg.endsWith("}@@")) {
            // 海湾200、5000 事件信息  张玖利盒子
            isHeartbeat = false;
            String from = msg.substring(3, 8).trim();
            NettyChannelMap.addClient(from, socketChannel);
        } else if (StringUtils.startsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG) && StringUtils.endsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_END_MSG)) {
            // 麦克水压
            String[] arr = msg.split(";");
            String from;
            if (arr[0].length() > 8) {
                // 水压信息数据接收
                from = arr[0];
            } else {//水压返回参数接收
                from = arr[1];
            }
            //把连接信息与客户端信息保存在map中
            NettyChannelMap.addClient(from, socketChannel);
            isHeartbeat = false;
        } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("WaterGage") > 0) {
            // 麦克旧水压水浸协议
            isHeartbeat = false;
            NettyChannelMap.addClient(msg, socketChannel);
        } else if (msg.startsWith("{") && msg.endsWith("}") && (msg.indexOf("Hydrant") > 0 || msg.startsWith("{C"))) {
            // 消火栓
            isHeartbeat = false;
            String[] arr = msg.split(":");
            String from = arr[0].replace("{", "");
            NettyChannelMap.addClient(from, socketChannel);
        } else if (StringUtils.startsWith(msg, Constants.TOPSAIL_MSG_STARTS_WITH)) {
            // 西安 拓普索尔 消火栓
            isHeartbeat = false;
            try {
                String substring = msg.substring(8 * 2, 23 * 2 + 2);
                String sensorNo = new String(StringUtils.hex2byte(substring), "UTF-8");
                NettyChannelMap.addClient(sensorNo.substring(0, sensorNo.length() - 1), socketChannel);
            } catch (UnsupportedEncodingException e) {
                SysLog.error(e);
            }
        } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("from") < 0 && msg.indexOf("type") < 0) {
            // 泰科水压设备
            isHeartbeat = false;
            NettyChannelMap.addClient(msg, socketChannel);
        } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("FireAlarm") > 0) {
            // 德杰火警主机协议
            isHeartbeat = false;
            Map<String, String> map = JSONObject.parseObject(msg, Map.class);
            NettyChannelMap.addClient(map.get("from"), socketChannel);
        } else if (msg.indexOf("modbus") != -1) {
            // 火眼modbus
            isHeartbeat = false;
            NettyChannelMap.addClient(msg, socketChannel);
            ModbusChannelMap.addIpMap(clientIp);
            ModbusInit.ModbusPortInit(clientIp);

        } else if (msg.startsWith("4011") && msg.endsWith("23")) {
            // 天津合极电气火灾-->设备上发 40 11
            isHeartbeat = false;
            // 设备信息--设备地址,例:测试设备地址 00006588
            String s2 = msg.substring(34, 42);
            NettyChannelMap.addClient(s2, socketChannel);
        }

        // 是否需要调用心跳接口
        if (isHeartbeat) {
            // 心跳接口
            nettyInitAnalysis(socketChannel, msg);
        }

        // 判断是否是以注册id的固定开始字符串开始，如果不是以注册id固定值开头，则是其它信息接口
        boolean msgBoolean = !StringUtils.startsWith(msg, ConstantsClient.BEGIN_REG_ID);
        boolean msgStartsWithBoolean = msgBoolean || msg.startsWith("$${") || msg.startsWith("$$(");
        if (msgStartsWithBoolean && msg.length() >= 10) {
            return receiveAnalysis(socketChannel, msg, in, isHeartbeat) + "";
        }
        return "";
    }

    /**
     * 功能描述: 心跳信息处理
     *
     * @param socketChannel SocketChannel
     * @param msg           msg
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/5/6 0006 14:13
     */
    private String nettyInitAnalysis(SocketChannel socketChannel, String msg) {
        try {
            String regId = URLDecoder.decode(NettyChannelMap.getRegIdByChannelId(socketChannel), "UTF-8");
            if (StringUtils.isEmpty(regId)) {
                InetSocketAddress inSocket = socketChannel.remoteAddress();
                String clientIp = inSocket.getAddress().getHostAddress();
                SysLog.logger.error("IP:" + clientIp + "  没有该用户的注册信息：" + regId + ":" + msg);
                socketChannel.close();
                return Constants.FAIL_CODE + "";
            }

            boolean flag;
            if (StringUtils.startsWith(regId, "%24%24_VESDA_") || StringUtils.startsWith(regId, "$$_VESDA_")) {
                flag = true;
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_FIRE_EYE_REG_ID)) {
                flag = false;
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_LINKAGE_DEVICE_REG_ID)) {
                // 联动设备
                flag = true;
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_TOPSAIL_DEVICE_REG_ID)) {
                // 西安 拓普索尔 消火栓
                flag = true;
            } else {
                flag = NettyChannelMap.isClientMsgSave(socketChannel, msg);
            }

            if (flag) {
                // 判断信息是否要保存
                regId = StringUtils.urlDecode(regId);
                JSONObject jsonObject = null;
                if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_VESDA_REG_ID)) {
                    if (msg.equals(ConstantsClient.XPLS_EVENT_XTXT) || StringUtils.startsWith(msg, ConstantsClient.BEGIN_VESDA_REG_ID)) {
                        jsonObject = vesdaVSMEventSave(regId);
                    } else {
                        return Constants.FAIL_CODE + "";
                    }
                } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_LINKAGE_DEVICE_REG_ID)) {
                    // 设备联动 声光报警
                    jsonObject = linkageDeviceSave(regId, msg, Constants.LINKAGE_DEVICE_MSG_TYPE);
                } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_TOPSAIL_DEVICE_REG_ID)) {
                    // 西安 拓普索尔 消火栓
                    TopsailWaterSys.topsailHydrant(socketChannel, msg, regId);
                } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_XPLS_REG_ID) || StringUtils.startsWith(regId, ConstantsClient.BEGIN_XPLS_GRAPHIC_REG_ID) ||
                        StringUtils.startsWith(regId, ConstantsClient.BEGIN_SIEMENS_REG_ID) || StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS_REG_ID) ||
                        StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS6000_REG_ID) || StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS_GRAPHIC_REG_ID) ||
                        StringUtils.startsWith(regId, ConstantsClient.BEGIN_FUAN_REG_ID) || StringUtils.startsWith(regId, ConstantsClient.BEGIN_FUAN_PRINT_REG_ID) ||
                        StringUtils.startsWith(regId, ConstantsClient.BEGIN_ADH_REG_ID) || (msg.startsWith("$$(") && msg.endsWith(")")) ||
                        (msg.startsWith("$${") && msg.endsWith("}@@"))) {
                    // 判断是否是火警主机
                    jsonObject = fireAlarmEventSave(regId);
                }

                if (null != jsonObject) {
                    return Constants.SUCCESS_CODE + "";
                }
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return Constants.FAIL_CODE + "";
    }

    /**
     * 声光设备
     *
     * @param regId   注册ID
     * @param msg     msg
     * @param msgType 消息类型
     * @return JSONObject
     */
    private JSONObject linkageDeviceSave(String regId, String msg, String msgType) {
        JSONObject json = new JSONObject(3);
        json.put("from", regId);
        json.put("msg", msg);
        json.put("msgType", msgType);
        List<JSONObject> list = HttpUtils.httpGets(ConstantsClient.SAVE_MSG_URL, json);
        return JSONObject.parseObject(JSON.toJSONString(list.get(0)));
    }

    /**
     * 保存VESDA_VSM_DTU心跳信息接口
     *
     * @param regId 注册ID
     * @return JSONObject
     * @author dwt
     * @date 2019-06-27 10:55
     */
    private JSONObject vesdaVSMEventSave(String regId) {
        VesdaVSMEvent vesdaVSMEvent = new VesdaVSMEvent();
        vesdaVSMEvent.setHeartbeat(true);
        vesdaVSMEvent.setMsgType(Constants.VESDA_VSM_MSG_TYPE);
        vesdaVSMEvent.setFrom(regId);
        List<JSONObject> list = HttpUtils.httpGets(ConstantsClient.SAVE_MSG_URL, vesdaVSMEvent);
        return JSONObject.parseObject(JSON.toJSONString(list.get(0)));
    }

    /**
     * 保存火警主机心跳信息接口
     *
     * @param regId 注册ID
     * @return JSONObject
     * @author dwt
     * @date 2019-06-27 10:53
     */
    private JSONObject fireAlarmEventSave(String regId) {
        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
        // 消息类型
        fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
        // 来源ID
        fireAlarmEvent.setFrom(regId);
        fireAlarmEvent.setHeartbeat(true);
        List<JSONObject> list = HttpUtils.httpGets(ConstantsClient.SAVE_MSG_URL, fireAlarmEvent);
        return JSONObject.parseObject(JSON.toJSONString(list.get(0)));
    }

    /**
     * 功能描述:解析接收到的接口信息
     *
     * @param socketChannel 连接对象
     * @param msg           解析后的字符串
     * @param in            解析前的进制串
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/4/25 15:37
     */
    private String receiveAnalysis(SocketChannel socketChannel, String msg, ByteBuf in, boolean isHeartbeat) {
        try {
            String res = Constants.DISCARD_CODE + "";

            String regId = NettyChannelMap.getRegIdByChannelId(socketChannel);
            SysLog.logger.info("receiveAnalysis 用户事件信息：" + regId + ":" + msg);
            // 有心跳包才判断注册信息
            if (isHeartbeat) {
                if (StringUtils.isEmpty(regId)) {
                    socketChannel.close();
                    return res;
                }
                regId = StringUtils.urlDecode(regId);
            } else if (StringUtils.isNotEmpty(regId)) {

            } else {
                SysLog.error("没有注册码信息打印：" + regId + ":" + msg);
                regId = "";
            }

            if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_XPLS_GRAPHIC_REG_ID)) {
                // 新普利斯图文报警 判断是否是以注册id的固定字符串开始，如果是，则是注册接口
                res = XPLSGraphic.xplsGraphicReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS_GRAPHIC_REG_ID)) {
                // 诺蒂菲尔图文报警 判断是否是以注册id的固定字符串开始，如果是，则是注册接口
                res = NFSGraphic.nfsGraphicReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_XPLS_REG_ID)) {
                // 新普利斯 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = XPLS.receiveAnalysisMsgs(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_SNAJIANG_REG_ID)) {
                // 三江火警主机 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = SanJiang.sanJiangReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_ADH_REG_ID)) {
                // 爱德华 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = EdwardPrint.nfsReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS_REG_ID)) {
                // 诺蒂菲尔3030 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = NFS_3030.analysisNFSInfo(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_SIEMENS_REG_ID)) {
                // 西门子 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = SIEMENS.siemensReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_NFS6000_REG_ID)) {
                // 诺蒂菲尔6000 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = NFS_6000.analysisNFS6000Info(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_VESDA_REG_ID)) {
                // VESDA VSM 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = VesdaVSM.analysisVSM(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_FUAN_REG_ID)) {
                // FuAn 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = FuAn.fuAnReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_PEKING_BLUE_BIRD_REG_ID)) {
                // PeKingBuleBird 判断是否是以注册id的固定开始字符串开始，如果是，则是注册接口
                res = PeKingBuleBird.pekingBuleBirdReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_FIRE_EYE_REG_ID) || msg.length() > 2000) {
                // 火眼
                res = FireEye.analysisFireEye(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG)
                    && StringUtils.endsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_END_MSG)) {
                // 水压 麦克水压新协议
                res = MKWaterSys.analysisWaterSys(socketChannel, msg, regId);
            } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("WaterGage") > 0) {
                // 泰科水压水浸
                res = MKWaterSys.oldAnalysisWaterSys(socketChannel, msg, regId);
            } else if (msg.startsWith("{") && msg.endsWith("}") && (msg.indexOf("Hydrant") > 0 || msg.startsWith("{C") || msg.startsWith("{A:"))) {
                // 泰科消火栓 {C泰科消火栓新协议
                res = TKWaterSys.analysisHydrant(socketChannel, msg, regId);
            } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("from") < 0 && msg.indexOf("type") < 0) {
                // 麦克水压老协议
                res = TKWaterSys.analysisWaterSys(socketChannel, msg, regId);
            } else if (msg.startsWith("$${") && msg.endsWith("}@@")) {
                // 海湾200、海湾5000 张久利协议
                res = GST_200_5000.analysisFireAlarm(socketChannel, msg, regId);
            } else if (msg.startsWith("{") && msg.endsWith("}") && msg.indexOf("FireAlarm") > 0) {
                res = DeJieFireAlarm.analysisFireAlarm(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_FUAN_PRINT_REG_ID)) {
                res = FuAnPrint.fuAnPrintReceiveAnalysis(socketChannel, msg, regId);
            } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_LINKAGE_DEVICE_REG_ID)) {
                // 联动设备  声光报警
                JSONObject jsonObject = linkageDeviceSave(regId, msg, Constants.LINKAGE_DEVICE_MSG_TYPE);
                res = jsonObject.toString();
            } else if (StringUtils.startsWith(msg, Constants.TOPSAIL_MSG_STARTS_WITH)) {
                // 西安 拓普索尔 消火栓
                res = TopsailWaterSys.topsailHydrant(socketChannel, msg, regId);

            } else if (StringUtils.startsWith(msg, Constants.HE_JI_MSG_STARTS_WITH) && msg.endsWith(Constants.HE_JI_MSG_END_WITH)) {
                // 天津合极 电气火灾
                SysLog.info("----天津合极 电气火灾----");
                res = HeJiElectricalFire.analysisHeJiElectricalFire(socketChannel, msg, regId);

            }

            return res;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        return Constants.DISCARD_CODE + "";
    }

    /**
     * 功能描述: 判断注册码是否是本公司产品，通过接口，判断注册码，在数据库中是否存在
     *
     * @param socketChannel SocketChannel
     * @param regId         注册ID
     * @param msg           msg
     * @return boolean
     * @author wangzhiwen
     * @date 2019/5/21 0021 13:54
     */
    private boolean isMyProductByRegId(SocketChannel socketChannel, String regId, String msg) {
        if (StringUtils.isEmpty(regId)) {
            return false;
        }

        String param;
        if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_VESDA_REG_ID)) {
            param = "mtType=VesdaVSM&from=" + StringUtils.urlEncode(regId);
        } else if (StringUtils.startsWith(regId, ConstantsClient.BEGIN_LINKAGE_DEVICE_REG_ID)) {
            return true;
        } else if (StringUtils.startsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG) && StringUtils.endsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_END_MSG)) {
            param = "mtType=WaterGage&from=" + StringUtils.urlEncode(regId);
        } else {
            param = "mtType=FireAlarm&from=" + StringUtils.urlEncode(regId);
        }

        Boolean isRegIdExist = NettyChannelMap.isRegIdExist(regId);
        if (isRegIdExist != null) {
            return true;
            //return isRegIdExist;
        }

        JSONObject jsonObject = HttpUtils.httpGet(ConstantsClient.IS_MY_PRODUCT_BY_REG_ID_URL + param);
        if (null != jsonObject && jsonObject.getBoolean("isPresence")) {
            NettyChannelMap.saveRegIdExistStatus(regId, true);
            return true;
        } else if (StringUtils.startsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_START_MSG) && StringUtils.endsWith(msg, ConstantsClient.BEGIN_MK_WATERGAGE_END_MSG)) {
            // 水压传感器不存在
            NettyChannelMap.saveRegIdExistStatus(regId, false);
            return false;
        } else {
            NettyChannelMap.saveRegIdExistStatus(regId, false);
            // 不存在的注册码时，关闭连接
            socketChannel.close();
            return false;
        }
    }

}
