package com.xjt.cloud.netty.manage.netty.msg.electrical;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.RedisUtils;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.entity.ElectricalFireEventRecord;
import com.xjt.cloud.netty.manage.netty.msg.fireAlarm.BaseReceive;
import io.netty.channel.socket.SocketChannel;


/**
 * 天津合极
 * 电气火灾协议解析
 *
 * @author huanggc
 * @date 2021/04/13
 */
public class HeJiElectricalFire extends BaseReceive {
    private static volatile RedisUtils redisUtils;

    /**
     * 功能描述: redis工具类初始化
     *
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

    // 心跳 40110300000001300400000000000703000000658801000BFF0001FFFFFFFFFFFF01014823

    public static void main(String[] args) {
        String msg = "40 11 03 00000002" +
                "3004 000000000008" +
                "03 00 00000014" +
                "29 0216" +
                "00 0000 FFFFFFFFFFFF 04 00000001" +
                "01 0000 FFFFFFFFFFFF 04 00000000" +
                "02 0000 FFFFFFFFFFFF 04 00000000" +
                "03 0000 FFFFFFFFFFFF 04 00000000" +
                "04 0000 FFFFFFFFFFFF 04 00000000" +
                "05 0000 FFFFFFFFFFFF 04 00000004" +
                "06 0000 FFFFFFFFFFFF 04 00000004" +
                "07 0000 FFFFFFFFFFFF 04 00000000" +
                "08 0000 FFFFFFFFFFFF 04 00000000" +
                "09 0000 FFFFFFFFFFFF 04 00000000" +
                "0A 0000 FFFFFFFFFFFF 04 00000000" +
                "0B 0000 FFFFFFFFFFFF 04 00000000" +
                "0C 0000 FFFFFFFFFFFF 04 00000000" +
                "0D 0000 FFFFFFFFFFFF 04 00000000" +
                "0E 0000 FFFFFFFFFFFF 04 00000000" +
                "0F 0002 FFFFFFFFFFFF 02 0000" +
                "10 0002 FFFFFFFFFFFF 02 0000" +
                "11 0002 FFFFFFFFFFFF 02 0000" +
                "12 0002 FFFFFFFFFFFF 02 0000" +
                "13 0002 FFFFFFFFFFFF 02 07D0" +
                "14 0002 FFFFFFFFFFFF 02 07D0" +
                "15 0002 FFFFFFFFFFFF 02 01F4" +
                "16 0002 FFFFFFFFFFFF 02 01F4" +
                "17 0002 FFFFFFFFFFFF 02 094B" +
                "18 0002 FFFFFFFFFFFF 02 0000" +
                "19 0002 FFFFFFFFFFFF 02 0000" +
                "1A 0002 FFFFFFFFFFFF 02 0000" +
                "1B 0002 FFFFFFFFFFFF 02 0000" +
                "1C 0002 FFFFFFFFFFFF 02 0000" +
                "1D 0003 FFFFFFFFFFFF 04 00000000" +
                "1E 0003 FFFFFFFFFFFF 04 00000000" +
                "1F 0003 FFFFFFFFFFFF 04 00000000" +
                "20 0002 FFFFFFFFFFFF 02 0000" +
                "21 0002 FFFFFFFFFFFF 02 0000" +
                "22 0002 FFFFFFFFFFFF 02 0000" +
                "23 0002 FFFFFFFFFFFF 02 01F3" +
                "24 0002 FFFFFFFFFFFF 02 0000" +
                "25 0002 FFFFFFFFFFFF 02 0000" +
                "26 0004 FFFFFFFFFFFF 04 00000175" +
                "27 0004 FFFFFFFFFFFF 04 00000000" +
                "28 0004 FFFFFFFFFFFF 04 00000000" +
                "0023";

        msg = msg.replace(" ", "");
        System.out.println("msg总长度:" + msg.length());
        String substring = msg.substring(68, 76);
        System.out.println(substring);
        System.out.println("---------------------------------------------------------------------------");
        String s = msg.substring(32, 34);
        System.out.println("s:" + s);
        int h = StringUtils.hexStringToAlgorism("0000000a");
        System.out.println(h);
        System.out.println("---------------------------------------------------------------------------");

        JSONObject jsonObject = new JSONObject();
        // 设备信息--回路
        int channel = StringUtils.hexStringToAlgorism(msg.substring(32, 34));
        jsonObject.put("channel", channel);
        // 设备信息--设备地址,例:测试设备地址 00006588
        String s2 = msg.substring(34, 42);
        // 数据总长: 2byte   0216
        // String substring = msg.substring(44, 48);

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("心跳包:"+heartbeatPackage("00006588",msg));
        System.out.println("---------------------------------------------------------------------------");
        //登录包
        String msgLogin="40110300000001300400000000000703000000658801000BFF0001FFFFFFFFFFFF01014823";
        System.out.println("登录包:"+loginPackage("00006588",msgLogin));
        System.out.println("---------------------------------------------------------------------------");
    }

    /**
     * 1登录包
     * 40110300000001300400000000000703000000658801000BFF0001FFFFFFFFFFFF01014823
     * 40 11 03 00 00 00 01 30 04 00 00 00 00 00 08 03 00 00 00 00 14 01 00 0B FF 00 01 FF FF FF FF FF FF 01 01 70 23
     *
     * @param msg 登录的消息
     * @return String
     */
    private static String loginPackage(String regId, String msg) {
        // 登录包消息解析 省略



        // 2.2 返馈应答登录包格式（接收方）
        // 平台反馈 40 11 04 00 00 00 01 01 00 17 23
        StringBuilder sb = new StringBuilder();
        // 1起始符 40
        sb.append("40");
        // 2协议版本号 1 byte 固定 11
        sb.append("11");
        // 3消息包类型 1 byte 智慧用电为：04
        sb.append("04");
        // 4 消息包索引 4 byte 每次发送，自增 1，例如：0x00000001
        Integer loginPackage = get(regId, "loginPackage");
        String loginIndex = StringUtils.encodeHEX(loginPackage, 8);
        sb.append(loginIndex);
        // 5 数据长度 1 byte N，数据部分长度，N≥0，智慧用电为：（0x01）
        sb.append("01");
        // 6 数据 1 byte 根据具体内容定义，智慧用电为：（0x00）
        sb.append("00");
        // 7 校验位 1 byte 校验和，同上
        // 校验位 说明：校验和=设备所有数据除去第一位（0x40）之后到本位之前所有数据累加和，取最低一个字节
        String[] bs = StringUtils.string2StringArr(msg.substring(2, msg.length() -4), 2);
        int a = 0;
        for (int i = 0; i < bs.length; i++) {
            a += StringUtils.hexStringToAlgorism(bs[i]);
        }
        String verification = StringUtils.encodeHEX(a, 2);
        sb.append(verification);
        // 8 结束符 1 byte ’#’（0x23）结束符
        sb.append("23");
        return sb.toString();
    }

    /**
     * 2心跳包
     * 40 11 03 00 00 00 03 30 04 00 00 00 00 00 08 03 00 00 00 00 14 01 00 0B FE 00 01 FF FF FF FF FF FF 01 02 72 23
     *
     * @param msg 登录的消息
     * @return String
     */
    private static String heartbeatPackage(String regId, String msg) {

        // 3.2 返馈心跳应答格式（接收方）
        StringBuilder sb = new StringBuilder();
        // 1起始符 40
        sb.append("40");
        // 2协议版本号 1 byte 固定 11
        sb.append("11");
        // 3消息包类型 1 byte 智慧用电为：04
        sb.append("04");
        // 4 消息包索引 4 byte 每次发送，自增 1，例如：0x00000001
        Integer loginPackage = get(regId, "heartbeat");
        String loginIndex = StringUtils.encodeHEX(loginPackage, 8);
        sb.append(loginIndex);
        // 5 数据长度 1 byte N，数据部分长度，N≥0，智慧用电为：（0x01）
        sb.append("01");
        // 6 数据 1 byte 根据具体内容定义，智慧用电为：（0x00）
        sb.append("00");
        // 7 校验位 1 byte 校验和，同上
        // 校验位 说明：校验和=设备所有数据除去第一位（0x40）之后到本位之前所有数据累加和，取最低一个字节
        String[] bs = StringUtils.string2StringArr(msg.substring(2, msg.length() -4), 2);
        int a = 0;
        for (int i = 0; i < bs.length; i++) {
            a += StringUtils.hexStringToAlgorism(bs[i]);
        }
        String verification = StringUtils.encodeHEX(a, 2);
        sb.append(verification);
        // 8 结束符 1 byte ’#’（0x23）结束符
        sb.append("23");
        return sb.toString();
    }

    /**
     * 数据包 设备上发
     *
     * @param msg 消息
     * @param regId regId=设备地址
     * @return JSONObject
     */
    private static JSONObject deviceSendUp(String regId, String msg) {
        msg = msg.replace(" ", "");
        JSONObject jsonObject = new JSONObject();

        // 设备信息--回路
        int channel = StringUtils.hexStringToAlgorism(msg.substring(32, 34));
        jsonObject.put("channel", channel);

        // 设备信息--设备地址,例:测试设备地址 00006588
        String s2 = msg.substring(34, 42);

        // 数据总长: 2byte   0216
        // String substring = msg.substring(44, 48);

        // 数据1--故障状态--通道号 1byte
        //String s11 = msg.substring(48, 50);
        // 数据类型 2byte
        //String s12 = msg.substring(50, 54);
        // 产生时间 6byte(所有产生时间不用解析,按消息接收时间)
        //String s13 = msg.substring(54, 66);
        // 数据长度 1byte
        //String s14 = msg.substring(66, 68);
        // 数据1--4byte--故障状态--数据1 00000000 为正常 设备火警、故障和异常允许同时存在，状态优先级：报警>故障>异常>正常   报警3>故障2>异常1>正常0
        // 数据值 bit0 为 1，表示设备报警 bit0 为 0，表示设备无火警
        // 数据值 bit1 为 1，表示设备故障 bit1 为 0，表示设备无故障
        // 数据值 bit2 为 1，表示设备异常 bit2 为 0，表示设备无异常
        int fault = StringUtils.hexStringToAlgorism(msg.substring(68, 76));
        if (fault == 1) {
            jsonObject.put("faultState", 3);
        } else if (fault == 16) {
            jsonObject.put("faultState", 2);
        } else if (fault == 512) {
            jsonObject.put("faultState", 1);
        } else {
            jsonObject.put("faultState", 0);
        }

        // 数据2 漏电状态1--设备通道1 1byte
        //String s21 = msg.substring(76, 78);
        // 数据类型 2byte
        String s22 = msg.substring(78, 82);
        // 产生时间 6byte
        //String s23 = msg.substring(82, 94);
        // 数据长度 1byte
        //String s24 = msg.substring(94, 96);
        // 数据 4byte
        // 状态代码：00000000 正常; 00000001 剩余电流过载（报警）;00000002 剩余电流端口开路（故障）;00000003 剩余电流端口短路（故障）
        int leakageState1 = StringUtils.hexStringToAlgorism(msg.substring(96, 104));
        if (leakageState1 == 1) {
            jsonObject.put("leakageState1", leakageState1);
        } else if (leakageState1 == 2) {
            jsonObject.put("leakageState1", leakageState1);
        } else if (leakageState1 == 3) {
            jsonObject.put("leakageState1", leakageState1);
        } else {
            jsonObject.put("leakageState1", 0);
        }

        // 数据3--漏电状态2--设备通道2  1byte
        //String s31 = msg.substring(104, 106);
        // 数据类型  2byte
        //String s32 = msg.substring(106, 110);
        // 产生时间  6byte
        //String s33 = msg.substring(110, 122);
        // 数据长度  1byte
        //String s34 = msg.substring(122, 124);
        // 数据3  4byte
        // 状态代码：00000000 正常; 00000001 剩余电流过载（报警）;00000002 剩余电流端口开路（故障）;00000003 剩余电流端口短路（故障）
        int leakageState2 = StringUtils.hexStringToAlgorism(msg.substring(124, 132));
        if (leakageState2 == 1) {
            jsonObject.put("leakageState2", leakageState2);
        } else if (leakageState2 == 2) {
            jsonObject.put("leakageState2", leakageState2);
        } else if (leakageState2 == 3) {
            jsonObject.put("leakageState2", leakageState2);
        } else {
            jsonObject.put("leakageState2", 0);
        }

        // 数据4--漏电状态3--设备通道3  1byte
        //String s41 = msg.substring(132, 134);
        // 数据类型  2byte
        //String s42 = msg.substring(134, 138);
        // 产生时间  6byte
        //String s43 = msg.substring(138, 150);
        // 数据长度  1byte
        //String s44 = msg.substring(150, 152);
        // 数据4  4byte
        // 状态代码：00000000 正常; 00000001 剩余电流过载（报警）;00000002 剩余电流端口开路（故障）;00000003 剩余电流端口短路（故障）
        int leakageState3 = StringUtils.hexStringToAlgorism(msg.substring(152, 160));
        if (leakageState3 == 1) {
            jsonObject.put("leakageState3", leakageState3);
        } else if (leakageState3 == 2) {
            jsonObject.put("leakageState3", leakageState3);
        } else if (leakageState3 == 3) {
            jsonObject.put("leakageState3", leakageState3);
        } else {
            jsonObject.put("leakageState3", 0);
        }

        // 数据5--漏电状态4--设备通道4  1byte
        //String s51 = msg.substring(160, 162);
        // 数据类型  2byte
        //String s52 = msg.substring(162, 166);
        // 产生时间  6byte
        //String s53 = msg.substring(166, 178);
        // 数据长度  1byte
        //String s54 = msg.substring(178, 180);
        // 数据5  4byte
        // 状态代码：00000000 正常; 00000001 剩余电流过载（报警）;00000002 剩余电流端口开路（故障）;00000003 剩余电流端口短路（故障）
        int leakageState4 = StringUtils.hexStringToAlgorism(msg.substring(180, 188));
        if (leakageState4 == 1) {
            jsonObject.put("leakageState4", leakageState4);
        } else if (leakageState4 == 2) {
            jsonObject.put("leakageState4", leakageState4);
        } else if (leakageState4 == 3) {
            jsonObject.put("leakageState4", leakageState4);
        } else {
            jsonObject.put("leakageState4", 0);
        }

        // 数据6--温度状态1--设备通道5  1byte
        //String s61 = msg.substring(188, 190);
        // 数据类型  2byte
        //String s62 = msg.substring(190, 194);
        // 产生时间  6byte
        //String s63 = msg.substring(194, 206);
        // 数据长度  1byte
        //String s64 = msg.substring(206, 208);
        // 数据  4byte
        // 状态代码：00000000 正常; 00000004 过温（报警）; 00000005 温度端口开路（故障）; 00000006 温度端口短路（故障）
        int temperatureState1 = StringUtils.hexStringToAlgorism(msg.substring(208, 216));
        if (temperatureState1 == 4) {
            jsonObject.put("temperatureState1", temperatureState1);
        } else if (temperatureState1 == 5) {
            jsonObject.put("temperatureState1", temperatureState1);
        } else if (temperatureState1 == 6) {
            jsonObject.put("temperatureState1", temperatureState1);
        } else {
            jsonObject.put("temperatureState1", 0);
        }

        // 数据7--温度状态2--设备通道6  1byte
        //String s71 = msg.substring(216, 218);
        // 数据类型  2byte
        //String s72 = msg.substring(218, 222);
        // 产生时间  6byte
        //String s73 = msg.substring(222, 234);
        // 数据长度  1byte
        //String s74 = msg.substring(234, 236);
        // 数据7  4byte
        // 状态代码：00000000 正常; 00000004 过温（报警）; 00000005 温度端口开路（故障）; 00000006 温度端口短路（故障）
        int temperatureState2 = StringUtils.hexStringToAlgorism(msg.substring(236, 244));
        if (temperatureState2 == 4) {
            jsonObject.put("temperatureState2", temperatureState2);
        } else if (temperatureState2 == 5) {
            jsonObject.put("temperatureState2", temperatureState2);
        } else if (temperatureState2 == 6) {
            jsonObject.put("temperatureState2", temperatureState2);
        } else {
            jsonObject.put("temperatureState2", 0);
        }

        // 数据8--温度状态3--设备通道7 1byte
        //String s81 = msg.substring(244, 246);
        // 数据类型  2byte
        //String s82 = msg.substring(246, 250);
        // 产生时间  6byte
        //String s83 = msg.substring(250, 262);
        // 数据长度  1byte
        //String s84 = msg.substring(262, 264);
        // 数据8  4byte
        // 状态代码：00000000 正常; 00000004 过温（报警）; 00000005 温度端口开路（故障）; 00000006 温度端口短路（故障）
        int temperatureState3 = StringUtils.hexStringToAlgorism(msg.substring(264, 272));
        if (temperatureState3 == 4) {
            jsonObject.put("temperatureState3", temperatureState3);
        } else if (temperatureState3 == 5) {
            jsonObject.put("temperatureState3", temperatureState3);
        } else if (temperatureState3 == 6) {
            jsonObject.put("temperatureState3", temperatureState3);
        } else {
            jsonObject.put("temperatureState3", 0);
        }

        // 数据9--温度状态4--设备通道8  1byte
        //String s91 = msg.substring(272, 274);
        // 数据类型  2byte
        //String s92 = msg.substring(274, 278);
        // 产生时间  6byte
        //String s93 = msg.substring(278, 290);
        // 数据长度  1byte
        //String s94 = msg.substring(290, 292);
        // 数据9  4byte
        // 状态代码：00000000 正常; 00000004 过温（报警）; 00000005 温度端口开路（故障）; 00000006 温度端口短路（故障）
        int temperatureState4 = StringUtils.hexStringToAlgorism(msg.substring(292, 300));
        if (temperatureState4 == 4) {
            jsonObject.put("temperatureState4", temperatureState4);
        } else if (temperatureState4 == 5) {
            jsonObject.put("temperatureState4", temperatureState4);
        } else if (temperatureState4 == 6) {
            jsonObject.put("temperatureState4", temperatureState4);
        } else {
            jsonObject.put("temperatureState4", 0);
        }


        // 数据10--电压状态1--设备通道9  1byte
        //String s101 = msg.substring(300, 302);
        // 数据类型  2byte
        //String s102 = msg.substring(302, 306);
        // 产生时间  6byte
        //String s103 = msg.substring(306, 318);
        // 数据长度  1byte
        //String s104 = msg.substring(318, 320);
        // 数据10  4byte
        // 状态代码：00000000 正常; 00000007 缺相（报警）; 00000008 电压过压（报警）; 00000009 电压欠压（报警）
        int voltageState1 = StringUtils.hexStringToAlgorism(msg.substring(320, 328));
        if (voltageState1 == 7) {
            jsonObject.put("voltageState1", voltageState1);
        } else if (voltageState1 == 8) {
            jsonObject.put("voltageState1", voltageState1);
        } else if (voltageState1 == 9) {
            jsonObject.put("voltageState1", voltageState1);
        } else {
            jsonObject.put("voltageState1", 0);
        }

        // 数据11--电压状态2--设备通道10  1byte
        //String s111 = msg.substring(328, 330);
        // 数据类型  2byte
        //String s112 = msg.substring(330, 334);
        // 产生时间  6byte
        //String s113 = msg.substring(334, 346);
        // 数据长度  1byte
        //String s114 = msg.substring(346, 348);
        // 数据11 4byte
        // 状态代码：00000000 正常; 00000007 缺相（报警）; 00000008 电压过压（报警）; 00000009 电压欠压（报警）
        //String s115 = msg.substring(348, 356);
        int voltageState2 = StringUtils.hexStringToAlgorism(msg.substring(348, 356));
        if (voltageState2 == 7) {
            jsonObject.put("voltageState2", voltageState2);
        } else if (voltageState2 == 8) {
            jsonObject.put("voltageState2", voltageState2);
        } else if (voltageState2 == 9) {
            jsonObject.put("voltageState2", voltageState2);
        } else {
            jsonObject.put("voltageState2", 0);
        }

        // 数据12--电压状态3--设备通道11  1byte
        //String s121 = msg.substring(356, 358);
        // 数据类型  2byte
        //String s122 = msg.substring(358, 362);
        // 产生时间  6byte
        //String s123 = msg.substring(362, 374);
        // 数据长度  1byte
        //String s124 = msg.substring(374, 376);
        // 数据12  4byte
        // 状态代码：00000000 正常; 00000007 缺相（报警）; 00000008 电压过压（报警）; 00000009 电压欠压（报警）
        //String s125 = msg.substring(376, 384);
        int voltageState3 = StringUtils.hexStringToAlgorism(msg.substring(376, 384));
        if (voltageState3 == 7) {
            jsonObject.put("voltageState3", voltageState3);
        } else if (voltageState3 == 8) {
            jsonObject.put("voltageState3", voltageState3);
        } else if (voltageState3 == 9) {
            jsonObject.put("voltageState3", voltageState3);
        } else {
            jsonObject.put("voltageState3", 0);
        }


        // 数据13--电流状态1--设备通道12  1byte
        //String s131 = msg.substring(384, 386);
        // 数据类型  2byte
        //String s132 = msg.substring(386, 390);
        // 产生时间  6byte
        //String s133 = msg.substring(390, 402);
        // 数据长度  1byte
        //String s134 = msg.substring(402, 404);
        // 数据13  4byte
        // 状态代码：00000000 正常; 0000000a 电流过载（报警）; 0000000b 电流端口开路（故障）; 0000000c 电流端口短路（故障）
        //String s135 = msg.substring(404, 412);
        int flowState1 = StringUtils.hexStringToAlgorism(msg.substring(404, 412));
        if (flowState1 == 10) {
            jsonObject.put("flowState1", flowState1);
        } else if (flowState1 == 11) {
            jsonObject.put("flowState1", flowState1);
        } else if (flowState1 == 12) {
            jsonObject.put("flowState1", flowState1);
        } else {
            jsonObject.put("flowState1", 0);
        }

        // 数据14--电流状态2--设备通道13  1byte
        //String s141 = msg.substring(412, 414);
        // 数据类型  2byte
        //String s142 = msg.substring(414, 418);
        // 产生时间  6byte
        //String s143 = msg.substring(418, 430);
        // 数据长度  1byte
        //String s144 = msg.substring(430, 432);
        // 数据  4byte
        // 状态代码：00000000 正常; 0000000a 电流过载（报警）; 0000000b 电流端口开路（故障）; 0000000c 电流端口短路（故障）
        //String s145 = msg.substring(432, 440);
        int flowState2 = StringUtils.hexStringToAlgorism(msg.substring(432, 440));
        if (flowState2 == 10) {
            jsonObject.put("flowState2", flowState2);
        } else if (flowState2 == 11) {
            jsonObject.put("flowState2", flowState2);
        } else if (flowState2 == 12) {
            jsonObject.put("flowState2", flowState2);
        } else {
            jsonObject.put("flowState2", 0);
        }

        // 数据15--电流状态3--设备通道14  1byte
        //String s151 = msg.substring(440, 442);
        // 数据类型  2byte
        //String s152 = msg.substring(442, 446);
        // 产生时间  6byte
        //String s153 = msg.substring(446, 458);
        // 数据长度  1byte
        //String s154 = msg.substring(458, 460);
        // 数据  4byte
        // 状态代码：00000000 正常; 0000000a 电流过载（报警）; 0000000b 电流端口开路（故障）; 0000000c 电流端口短路（故障）
        //String s155 = msg.substring(460, 468);
        int flowState3 = StringUtils.hexStringToAlgorism(msg.substring(460, 468));
        if (flowState3 == 10) {
            jsonObject.put("flowState3", flowState3);
        } else if (flowState3 == 11) {
            jsonObject.put("flowState3", flowState3);
        } else if (flowState3 == 12) {
            jsonObject.put("flowState3", flowState3);
        } else {
            jsonObject.put("flowState3", 0);
        }

        // 数据16--漏电值1--设备通道15  1byte
        //String s161 = msg.substring(468, 470);
        // 数据类型  2byte
        //String s162 = msg.substring(470, 474);
        // 产生时间  6byte
        //String s163 = msg.substring(474, 486);
        // 数据长度   1byte
        //String s164 = msg.substring(486, 488);
        // 2byte  数据: 500mA=(16进制的 1388 转换成10进制是 5000)*0.1; 范围0 ~ 20000 倍率0.1; 单位mA 例如：0x1388 代表漏电1值为：500mA
        //String s165 = msg.substring(488, 492);
        int leakageAlarmValue = StringUtils.hexStringToAlgorism(msg.substring(488, 492));
        jsonObject.put("leakageAlarmValue", leakageAlarmValue * 0.1 + "mA");

        // 数据17--漏电值2--设备通道16  1byte
        //String s171 = msg.substring(492, 494);
        // 数据类型  2byte
        //String s172 = msg.substring(494, 498);
        // 产生时间  6byte
        //String s173 = msg.substring(498, 510);
        // 数据长度  1byte
        //String s174 = msg.substring(510, 512);
        // 2byte  数据: 500mA=(16进制的 1388 转换成10进制是 5000)*0.1
        // 范围0 ~ 20000 倍率0.1; 单位mA 例如：0x1388 代表漏电1值为：500mA
        //String s175 = msg.substring(512, 516);
        int leakageAlarmValue2 = StringUtils.hexStringToAlgorism(msg.substring(512, 516));
        jsonObject.put("leakageAlarmValue2", leakageAlarmValue2 * 0.1 + "mA");

        // 数据18--漏电值3--设备通道17  1byte
        //String s181 = msg.substring(516, 518);
        // 数据类型  2byte
        //String s182 = msg.substring(518, 522);
        // 产生时间   6byte
        //String s183 = msg.substring(522, 534);
        // 数据长度  1byte
        //String s184 = msg.substring(534, 536);
        // 2byte  数据: 500mA=(16进制的 1388 转换成10进制是 5000)*0.1
        // 范围0 ~ 20000 倍率0.1; 单位mA 例如：0x1388 代表漏电1值为：500mA
        //String s185 = msg.substring(536, 540);
        int leakageAlarmValue3 = StringUtils.hexStringToAlgorism(msg.substring(536, 540));
        jsonObject.put("leakageAlarmValue3", leakageAlarmValue3 * 0.1 + "mA");

        // 数据19--漏电值4--设备通道18  1byte
        //String s191 = msg.substring(540, 542);
        // 数据类型  2byte
        //String s192 = msg.substring(542, 546);
        // 产生时间  6byte
        //String s193 = msg.substring(546, 558);
        // 数据长度  1byte
        //String s194 = msg.substring(558, 560);
        // 数据: 2byte   500mA=(16进制的 1388 转换成10进制是 5000)*0.1
        // 范围0 ~ 20000 倍率0.1; 单位mA 例如：0x1388 代表漏电1值为：500mA
        //String s195 = msg.substring(560, 564);
        int leakageAlarmValue4 = StringUtils.hexStringToAlgorism(msg.substring(560, 564));
        jsonObject.put("leakageAlarmValue4", leakageAlarmValue4 * 0.1 + "mA");

        // 数据20--温度值1--设备通道19  1byte
        //String s201 = msg.substring(564, 566);
        // 数据类型  2byte
        //String s202 = msg.substring(566, 570);
        // 产生时间  6byte
        //String s203 = msg.substring(570, 582);
        // 数据长度  1byte
        //String s204 = msg.substring(582, 584);
        // 数据: 2byte
        // 范围0~2500; 实际值=上传值-50，例：上传1000，表示1000/10 - 50 = 50℃
        //String s205 = msg.substring(584, 588);
        int temperature1 = StringUtils.stringToInt(msg.substring(584, 588));
        jsonObject.put("temperature1", (temperature1 > 0 ? temperature1 / 10 - 50 : 0) + "℃");

        // 数据21--温度值2--设备通道20  1byte
        //String s211 = msg.substring(588, 590);
        // 数据类型  2byte
        //String s212 = msg.substring(590, 594);
        // 产生时间  6byte
        //String s213 = msg.substring(594, 606);
        // 数据长度  1byte
        //String s214 = msg.substring(606, 608);
        // 数据: 2byte
        // 范围0~2500; 实际值=上传值-50，例：上传1000，表示1000/10 – 50 = 50℃
        //String s215 = msg.substring(608, 612);
        int temperature2 = StringUtils.stringToInt(msg.substring(584, 588));
        jsonObject.put("temperature2", (temperature2 > 0 ? temperature2 / 10 - 50 : 0) + "℃");

        // 数据22--温度值3--设备通道21  1byte
        //String s221 = msg.substring(612, 614);
        // 数据类型  2byte
        //String s222 = msg.substring(614, 618);
        // 产生时间  6byte
        //String s223 = msg.substring(618, 630);
        // 数据长度  1byte
        //String s224 = msg.substring(630, 632);
        // 数据: 2byte
        // 范围0~2500; 实际值=上传值-50，例：上传1000，表示1000/10 – 50 = 50℃
        //String s225 = msg.substring(632, 636);
        int temperature3 = StringUtils.stringToInt(msg.substring(632, 636));
        jsonObject.put("temperature3", (temperature3 > 0 ? temperature3 / 10 - 50 : 0) + "℃");

        // 数据23--温度值4--设备通道22  1byte
        //String s231 = msg.substring(636, 638);
        // 数据类型  2byte
        //String s232 = msg.substring(638, 642);
        // 产生时间  6byte
        //String s233 = msg.substring(642, 654);
        // 数据长度  1byte
        //String s234 = msg.substring(654, 656);
        // 数据: 2byte
        // 范围0~2500; 实际值=上传值-50，例：上传1000，表示1000/10 – 50 = 50℃
        //String s235 = msg.substring(656, 660);
        int temperature4 = StringUtils.stringToInt(msg.substring(656, 660));
        jsonObject.put("temperature4", (temperature4 > 0 ? temperature4 / 10 - 50 : 0) + "℃");

        // 数据24--电压值1--设备通道23  1byte
        //String s241 = msg.substring(660, 662);
        // 数据类型  2byte
        //String s242 = msg.substring(662, 666);
        // 产生时间  6byte
        //String s243 = msg.substring(666, 678);
        // 数据长度  1byte
        //String s244 = msg.substring(678, 680);
        // 数据: 2byte
        // 范围0~6000; 倍率0.1 单位V; 例：上传0x094b，表示2379/10 = 237.9V
        //String s245 = msg.substring(680, 684);
        int voltageValue1 = StringUtils.hexStringToAlgorism(msg.substring(680, 684));
        jsonObject.put("voltageValue1", (voltageValue1 >= 0 ? voltageValue1 / 10 : 0) + "V");

        // 数据25--电压值2--设备通道24  1byte
        //String s251 = msg.substring(684, 686);
        // 数据类型  2byte
        //String s252 = msg.substring(686, 690);
        // 产生时间  6byte
        //String s253 = msg.substring(690, 702);
        // 数据长度  1byte
        //String s254 = msg.substring(702, 704);
        // 数据: 2byte
        // 范围0~6000; 倍率0.1 单位V; 例：上传0x094b，表示2379/10 = 237.9V
        //String s255 = msg.substring(704, 708);
        int voltageValue2 = StringUtils.hexStringToAlgorism(msg.substring(704, 708));
        jsonObject.put("voltageValue2", (voltageValue2 >= 0 ? voltageValue2 / 10 : 0) + "V");

        // 数据26--电压值3--设备通道25  1byte
        //String s261 = msg.substring(708, 710);
        // 数据类型  2byte
        //String s262 = msg.substring(710, 714);
        // 产生时间  6byte
        //String s263 = msg.substring(714, 726);
        // 数据长度  1byte
        //String s264 = msg.substring(726, 728);
        // 数据: 2byte
        // 范围0~6000; 倍率0.1 单位V; 例：上传0x094b，表示2379/10 = 237.9V
        //String s265 = msg.substring(728, 732);
        int voltageValue3 = StringUtils.hexStringToAlgorism(msg.substring(728, 732));
        jsonObject.put("voltageValue3", (voltageValue3 >= 0 ? voltageValue3 / 10 : 0) + "V");

        // 数据27--电流值1--设备通道26  1byte
        //String s271 = msg.substring(732, 734);
        // 数据类型  2byte
        //String s272 = msg.substring(734, 738);
        // 产生时间  6byte
        //String s273 = msg.substring(738, 750);
        // 数据长度  1byte
        //String s274 = msg.substring(750, 752);
        // 数据: 2byte
        // 范围0~10000; 倍率0.1 单位A; 例：上传0x004b，表示75/10 = 7.5A
        //String s275 = msg.substring(752, 756);
        int leakageCurrent1 = StringUtils.hexStringToAlgorism(msg.substring(752, 756));
        jsonObject.put("leakageCurrent1", (leakageCurrent1 >= 0 ? leakageCurrent1 / 10 : 0) + "A");

        // 数据28--电流值2--设备通道27  1byte
        //String s281 = msg.substring(756, 758);
        // 数据类型  2byte
        //String s282 = msg.substring(758, 762);
        // 产生时间  6byte
        //String s283 = msg.substring(762, 774);
        // 数据长度  1byte
        //String s284 = msg.substring(774, 776);
        // 数据: 2byte
        // 范围0~10000; 倍率0.1 单位A; 例：上传0x004b，表示75/10 = 7.5A
        //String s285 = msg.substring(776, 780);
        int leakageCurrent2 = StringUtils.hexStringToAlgorism(msg.substring(776, 780));
        jsonObject.put("leakageCurrent2", (leakageCurrent2 >= 0 ? leakageCurrent2 / 10 : 0) + "A");

        // 数据29--电流值3--设备通道28  1byte
        //String s291 = msg.substring(780, 782);
        // 数据类型  2byte
        //String s292 = msg.substring(782, 786);
        // 产生时间  6byte
        //String s293 = msg.substring(786, 798);
        // 数据长度  1byte
        //String s294 = msg.substring(798, 800);
        // 数据: 2byte
        // 范围0~10000; 倍率0.1 单位A; 例：上传0x004b，表示75/10 = 7.5A
        //String s295 = msg.substring(800, 804);
        int leakageCurrent3 = StringUtils.hexStringToAlgorism(msg.substring(800, 804));
        jsonObject.put("leakageCurrent3", (leakageCurrent3 >= 0 ? leakageCurrent3 / 10 : 0) + "A");

        // 数据30--有功率1--设备通道29  1byte
        //String s301 = msg.substring(804, 806);
        // 数据类型  2byte
        //String s302 = msg.substring(806, 810);
        // 产生时间  6byte
        //String s303 = msg.substring(810, 822);
        // 数据长度  1byte
        //String s304 = msg.substring(822, 824);
        // 数据: 4byte
        // 倍率0.001; 单位kW; 例：上传100，表示100/1000= 0.1kW
        //String s305 = msg.substring(824, 832);
        int meritoriousPowerValue1 = StringUtils.stringToInt(msg.substring(824, 832));
        jsonObject.put("meritoriousPowerValue1", (meritoriousPowerValue1 > 0 ? meritoriousPowerValue1 / 1000 : 0) + "kW");

        // 数据31--有功率2--设备通道30  1byte
        //String s311 = msg.substring(832, 834);
        // 数据类型  2byte
        //String s312 = msg.substring(834, 838);
        // 产生时间  6byte
        //String s313 = msg.substring(838, 850);
        // 数据长度  1byte
        //String s314 = msg.substring(850, 852);
        // 数据31: 4byte
        // 倍率0.001; 单位kW; 例：上传100，表示100/1000= 0.1kW
        //String s315 = msg.substring(852, 860);
        int meritoriousPowerValue2 = StringUtils.stringToInt(msg.substring(852, 860));
        jsonObject.put("meritoriousPowerValue2", (meritoriousPowerValue2 > 0 ? meritoriousPowerValue2 / 1000 : 0) + "kW");

        // 数据32--有功率3--设备通道31  1byte
        //String s321 = msg.substring(860, 862);
        // 数据类型  2byte
        //String s322 = msg.substring(862, 866);
        // 产生时间  6byte
        //String s323 = msg.substring(866, 878);
        // 数据长度  1byte
        //String s324 = msg.substring(878, 880);
        // 数据32: 4byte
        // 倍率0.001; 单位kW; 例：上传100，表示100/1000= 0.1kW
        //String s325 = msg.substring(880, 888);
        int meritoriousPowerValue3 = StringUtils.stringToInt(msg.substring(880, 888));
        jsonObject.put("meritoriousPowerValue3", (meritoriousPowerValue3 > 0 ? meritoriousPowerValue3 / 1000 : 0) + "kW");

        // 数据33--功率因数1--设备通道32  1byte
        //String s331 = msg.substring(888, 890);
        // 数据类型  2byte
        //String s332 = msg.substring(890, 894);
        // 产生时间  6byte
        //String s333 = msg.substring(894, 906);
        // 数据长度  1byte
        //String s334 = msg.substring(906, 908);
        // 数据33: 2byte
        // 范围-1000~1000; 倍率0.1 单位%; 最高位bit15为1表示为负; 最高位bit15为0表示为正
        String s335 = msg.substring(908, 912);
        int powerFactorValue1 = StringUtils.stringToInt(s335);
        if (s335.startsWith("1")) {
            jsonObject.put("powerFactorValue1", (powerFactorValue1 > 0 ? -powerFactorValue1 * 0.1 : 0) + '%');
        } else {
            jsonObject.put("powerFactorValue1", (powerFactorValue1 > 0 ? powerFactorValue1 * 0.1 : 0) + "%");
        }

        // 数据34--功率因数2--设备通道33  1byte
        //String s341 = msg.substring(912, 914);
        // 数据类型  2byte
        //String s342 = msg.substring(914, 918);
        // 产生时间  6byte
        //String s343 = msg.substring(918, 930);
        // 数据长度  1byte
        //String s344 = msg.substring(930, 932);
        // 数据33: 2byte
        // 范围-1000~1000; 倍率0.1 单位%; 最高位bit15为1表示为负; 最高位bit15为0表示为正
        String s345 = msg.substring(932, 936);
        int powerFactorValue2 = StringUtils.stringToInt(s345);
        if (s335.startsWith("1")) {
            jsonObject.put("powerFactorValue2", (powerFactorValue2 > 0 ? -powerFactorValue2 * 0.1 : 0) + "%");
        } else {
            jsonObject.put("powerFactorValue2", (powerFactorValue2 > 0 ? powerFactorValue2 * 0.1 : 0) + "%");
        }

        // 数据35--功率因数3--设备通道34  1byte
        //String s351 = msg.substring(936, 938);
        // 数据类型  2byte
        //String s352 = msg.substring(938, 942);
        // 产生时间  6byte
        //String s353 = msg.substring(942, 954);
        // 数据长度  1byte
        //String s354 = msg.substring(954, 956);
        // 数据33: 2byte
        // 范围-1000~1000; 倍率0.1 单位%; 最高位bit15为1表示为负; 最高位bit15为0表示为正
        String s355 = msg.substring(956, 960);
        int powerFactorValue3 = StringUtils.stringToInt(s355);
        if (s335.startsWith("1")) {
            jsonObject.put("powerFactorValue3", (powerFactorValue3 > 0 ? -powerFactorValue3 * 0.1 : 0) + "%");
        } else {
            jsonObject.put("powerFactorValue3", (powerFactorValue3 > 0 ? powerFactorValue3 * 0.1 : 0) + "%");
        }

        // 数据36--A频率--设备通道35  1byte
        //String s361 = msg.substring(960, 962);
        // 数据类型  2byte
        //String s362 = msg.substring(962, 966);
        // 产生时间  6byte
        //String s363 = msg.substring(966, 978);
        // 数据长度  1byte
        //String s364 = msg.substring(978, 980);
        // 数据36: 2byte
        // 范围0~1000; 倍率0.1; 单位Hz
        //String s365 = msg.substring(980, 984);
        int frequencyValue1 = StringUtils.hexStringToAlgorism(msg.substring(980, 984));
        jsonObject.put("frequencyValue1", (frequencyValue1 > 0 ? frequencyValue1 * 0.1 : 0) + "Hz");

        // 数据37--B频率--设备通道36  1byte
        //String s371 = msg.substring(984, 986);
        // 数据类型  2byte
        //String s372 = msg.substring(986, 990);
        // 产生时间  6byte
        //String s373 = msg.substring(990, 1002);
        // 数据长度  1byte
        //String s374 = msg.substring(1002, 1004);
        // 数据37: 2byte
        // 范围0~1000; 倍率0.1; 单位Hz
        //String s375 = msg.substring(1004, 1008);
        int frequencyValue2 = StringUtils.hexStringToAlgorism(msg.substring(1004, 1008));
        jsonObject.put("frequencyValue2", (frequencyValue2 > 0 ? frequencyValue2 * 0.1 : 0) + "Hz");

        // 数据38--C频率--设备通道37  1byte
        //String s381 = msg.substring(1008, 1010);
        // 数据类型  2byte
        //String s382 = msg.substring(1010, 1014);
        // 产生时间  6byte
        //String s383 = msg.substring(1014, 1026);
        // 数据长度  1byte
        //String s384 = msg.substring(1026, 1028);
        // 数据38: 2byte
        // 范围0~1000; 倍率0.1; 单位Hz
        //String s385 = msg.substring(1028, 1032);
        int frequencyValue3 = StringUtils.hexStringToAlgorism(msg.substring(1028, 1032));
        jsonObject.put("frequencyValue3", (frequencyValue3 > 0 ? frequencyValue3 * 0.1 : 0) + "Hz");

        // 数据39--A电量--设备通道38  1byte
        //String s391 = msg.substring(1032, 1034);
        // 数据类型  2byte
        //String s392 = msg.substring(1034, 1038);
        // 产生时间  6byte
        //String s393 = msg.substring(1038, 1050);
        // 数据长度  1byte
        //String s394 = msg.substring(1050, 1052);
        // 数据39: 4byte
        // 倍率0.001; 单位kw*h 例如：上传0x00000175 = 373/1000=0.373KW
        //String s395 = msg.substring(1052, 1060);
        int electricValue1 = StringUtils.hexStringToAlgorism(msg.substring(1052, 1060));
        jsonObject.put("electricValue1", (electricValue1 > 0 ? electricValue1 / 1000 : 0) + "KW");

        // 数据40--B电量--设备通道39  1byte
        //String s401 = msg.substring(1060, 1062);
        // 数据类型  2byte
        //String s402 = msg.substring(1062, 1066);
        // 产生时间  6byte
        //String s403 = msg.substring(1066, 1078);
        // 数据长度  1byte
        //String s404 = msg.substring(1078, 1080);
        // 数据40: 4byte;  (16进制)00000175=373(10进制)
        // 倍率0.001; 单位kw*h 例如：上传0x00000175 = 373/1000=0.373KW
        //String s405 = msg.substring(1080, 1088);
        int electricValue2 = StringUtils.hexStringToAlgorism(msg.substring(1080, 1088));
        jsonObject.put("electricValue2", (electricValue2 > 0 ? electricValue2 / 1000 : 0) + "KW");

        // 数据41--C电量--设备通道10  1byte
        //String s411 = msg.substring(1088, 1090);
        // 数据类型  2byte
        //String s412 = msg.substring(1090, 1094);
        // 产生时间  6byte
        //String s413 = msg.substring(1094, 1106);
        // 数据长度  1byte
        //String s414 = msg.substring(1106, 1108);
        // 数据41: 4byte;  (16进制)00000175=373(10进制)
        // 倍率0.001; 单位kw*h 例如：上传0x00000175 = 373/1000=0.373KW
        //String s415 = msg.substring(1108, 1116);
        int electricValue3 = StringUtils.hexStringToAlgorism(msg.substring(1108, 1116));
        jsonObject.put("electricValue3", (electricValue3 > 0 ? electricValue3 / 1000 : 0) + "KW");


        /**
         * 平台反馈 40 11 04 00 00 00 02 01 00 18 23
         */
        StringBuilder sb = new StringBuilder();
        // 1起始符 40
        sb.append("40");
        // 2协议版本号 1 byte 固定 11
        sb.append("11");
        // 3消息包类型 1 byte 智慧用电为：04
        sb.append("04");
        // 4 消息包索引 4 byte 每次发送，自增 1，例如：0x00000001
        Integer loginPackage = get(regId, "deviceSendUp");
        String loginIndex = StringUtils.encodeHEX(loginPackage, 8);
        sb.append(loginIndex);
        // 5 数据长度 1 byte N，数据部分长度，N≥0，智慧用电为：（0x01）
        sb.append("01");
        // 6 数据 1 byte 根据具体内容定义，智慧用电为：（0x00）
        sb.append("00");
        // 7 校验位 1 byte 校验和，同上
        // 校验位 说明：校验和=设备所有数据除去第一位（0x40）之后到本位之前所有数据累加和，取最低一个字节
        String[] bs = StringUtils.string2StringArr(msg.substring(2, msg.length() -4), 2);
        int a = 0;
        for (int i = 0; i < bs.length; i++) {
            a += StringUtils.hexStringToAlgorism(bs[i]);
        }
        String verification = StringUtils.encodeHEX(a, 2);
        sb.append(verification);
        // 8 结束符 1 byte ’#’（0x23）结束符
        sb.append("23");
        jsonObject.put("deviceSendUp", sb.toString());

        return jsonObject;
    }

    /**
     * 天津合极电气火灾消息解析
     *
     * @param socketChannel SocketChannel
     * @param msg           msg
     * @param regId         注册ID
     * @return java.lang.String
     * @author huanggc
     * @date 2021/04/09
     */
    public static String analysisHeJiElectricalFire(SocketChannel socketChannel, String msg, String regId) {
        if (StringUtils.isEmpty(msg)) {
            return "";
        }

        // 设备上发
        if (msg.length() >= 1120) {
            // 反馈应答数据包格式 401104 00000001(自增1) 0100  校验位?  23

            // 校验位
            /*String[] bs = StringUtils.string2StringArr(msg.substring(2, msg.length() -4), 2);
            int a = 0;
            for (int i = 0; i < bs.length; i++) {
                a += StringUtils.hexStringToAlgorism(bs[i]);
            }
            String s = StringUtils.encodeHEX(a, 2);*/

            JSONObject jsonObject = deviceSendUp(regId, msg);
            ElectricalFireEventRecord electricalFireEventRecord = JSONObject.parseObject(jsonObject.toString(), ElectricalFireEventRecord.class);
            String json = sendHttpGet(socketChannel, msg, regId, electricalFireEventRecord);
            SysLog.info("天津合极电气火灾消息解析----sendHttpGet----设备上发----返回值json=" + json);

            String deviceSendUp = jsonObject.getString("deviceSendUp");
            SysLog.info("天津合极电气火灾消息解析----deviceSendUp----设备反馈----regId=" + regId + ":" + deviceSendUp);

            return deviceSendUp;
        }else {
            // 1登录包
            String loginPackageFeedback = loginPackage(regId, msg);
            SysLog.info("天津合极电气火灾 登录包 返馈应答-------->regId=" + regId + ":" + loginPackageFeedback);
            return loginPackageFeedback;
            // 2心跳包
            //return heartbeatPackage(regId, msg);
        }
    }

    /**
     * 消息包索引
     *
     * @param regId String
     * @author huanggc
     * @date 2021/05/17
     * @return Integer
     */
    private static Integer get(String regId, String packageName) {
        String keyPrefix = regId + packageName;
        Integer keySuffix = 0;
        try {
            keySuffix = initRedisUtils().getInteger(keyPrefix);

            if (null == keySuffix || keySuffix >= 99999999) {
                keySuffix = 0;
            }
            keySuffix++;
            // 设置 key
            initRedisUtils().set(keyPrefix, keySuffix);
        }catch (Exception e){
            SysLog.info("HeJiElectricalFire 消息包索引 失败");
            SysLog.error(e);
        }
        return keySuffix;
    }

}
