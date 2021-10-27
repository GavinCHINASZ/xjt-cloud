package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.alibaba.fastjson.JSONArray;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.common.utils.Constants;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Auther: zhangzf
 * @Date: 2019/10/18 0009 17:05
 * @Description:德杰任浩火警主机协议解析
 */
public class DeJieFireAlarm extends BaseReceive {

    private static Map<String,String> mtMap = new HashMap<>();

    public static Map<String, String> getMtName() {
        mtMap = new HashMap();
        mtMap.put("0", "感烟探头");
        mtMap.put("1", "感温探头");
        mtMap.put("2", "差温探头");
        mtMap.put("3", "剩余电流");
        mtMap.put("4", "电气测温");
        mtMap.put("5", "手动按钮");
        mtMap.put("6", "消火栓钮");
        mtMap.put("7", "感温电缆");
        mtMap.put("8", "感温光纤");
        mtMap.put("9", "红外光束");
        mtMap.put("10", "压力开关");
        mtMap.put("11", "可燃气体");
        mtMap.put("12", "水流指示");
        mtMap.put("13", "输入模块");
        mtMap.put("14", "火灾显示");
        mtMap.put("15", "关联探头");
        mtMap.put("16", "传感器2");
        mtMap.put("17", "传感器3");
        mtMap.put("18", "传感器4");
        mtMap.put("19", "传感器5");
        mtMap.put("20", "输入输出");
        mtMap.put("21", "脉冲方式");
        mtMap.put("22", "自动方式");
        mtMap.put("23", "自动脉冲");
        mtMap.put("24", "消防广播");
        mtMap.put("25", "消防警铃");
        mtMap.put("26", "声光报警");
        mtMap.put("27", "新风机");
        mtMap.put("28", "照明切断");
        mtMap.put("29", "动力切断");
        mtMap.put("30", "防排烟阀");
        mtMap.put("31", "正压风阀");
        mtMap.put("32", "卷帘半降");
        mtMap.put("33", "卷帘全降");
        mtMap.put("34", "消防警笛");
        mtMap.put("35", "排烟风机");
        mtMap.put("36", "防火阀");
        mtMap.put("37", "防火门");
        mtMap.put("38", "空调切断");
        mtMap.put("39", "正压风机");
        mtMap.put("40", "消防水幕");
        mtMap.put("41", "电梯迫降");
        mtMap.put("42", "信号蝶阀");
        mtMap.put("43", "应急照明");
        mtMap.put("44", "其它 02");
        mtMap.put("45", "其它 03");
        mtMap.put("46", "其它 04");
        mtMap.put("47", "其它 05");
        mtMap.put("48", "其它 06");
        mtMap.put("49", "其它 07");
        mtMap.put("50", "其它 08");
        mtMap.put("51", "其它 09");
        mtMap.put("52", "其它 10");
        mtMap.put("53", "其它 11");
        mtMap.put("54", "多线模块");
        mtMap.put("55", "气体灭火");
        return mtMap;
    }

    /**
     * @MethodName: analysisFireAlarm 德杰任浩火警主机协议解析
     * @Description:
     * @Param: [socketChannel, msg, regId]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/10/18 9:29
     **/
    public static String analysisFireAlarm(SocketChannel socketChannel, String msg, String regId) {
        SimpleDateFormat format = new SimpleDateFormat("{\"yyyy-MM-dd,HH:mm:ss\":\"--\"}");
        SysLog.info(msg + "------>信息");
        msg = msg.replaceAll("\r|\n*", "");
        msg = "[" + msg + "]";
        if (msg.indexOf("}{") > -1) {
            msg = msg.replaceAll("\\}\\{", "\\},\\{");
        }
        List<Map> list;
        try {
            list = JSONArray.parseArray(msg, Map.class);
            for (Map<String, String> map : list) {
                String msgtype = map.get("msgType");// 报警类型
                if (msgtype == null) {
                    msgtype = map.get("msgtype");// 报警类型
                }
                SysLog.info(msgtype + "-->报警类型");
                String from = map.get("from");// 传输装置ID
                SysLog.info(from + "-->传输装置ID");
                String type = map.get("type");// 设备类型
                SysLog.info(type + "-->设备类型");
                String Heartbeat = map.get("Heartbeat");// 是否为心跳包
                if (Heartbeat == null) {
                    Heartbeat = map.get("heartbeat");// 报警类型
                }
                SysLog.info(Heartbeat + "-->是否为心跳包");
                if ("FireAlarm".equals(msgtype.trim())) {// 是否为报警主机
                    if ("F".equals(Heartbeat.trim())) {// 是否为心跳包
                        String event = map.get("event");// 事件类型
                        SysLog.info(event + "-->转换前事件类型");
                        String positionCode = (String) map.get("positionCode");// 位置编号
                        SysLog.info(positionCode + "-->位置编号");
                        String position = map.get("position");// 报警位置
                        SysLog.info(position + "-->报警位置");
                        String mtName = map.get("mtName");// 设备名称
                        SysLog.info(mtName + "-->设备名称");
                        String description = map.get("description");// 设备名称
                        SysLog.info(description + "-->设备描述");
                        String state = map.get("state");// 状态
                        SysLog.info(state + "-->状态");
                        String action = (String) map.get("action");// 状态
                        SysLog.info(action + "-->设备类型");
                        FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
                        fireAlarmEvent.setMsgType(Constants.FIRE_ALARM_MSG_TYPE);
                        fireAlarmEvent.setMtCode(positionCode);// 设备编号
                        fireAlarmEvent.setMtName(mtName);
                        fireAlarmEvent.setPosition(description);
                        fireAlarmEvent.setSourceData(msg);
                        if (action != null && type.equals("SongJiang JB-3208")) {
                            fireAlarmEvent = songJiang(fireAlarmEvent,mtName,positionCode,action);
                        } else if (type.equals("JB_TG_N_6000")) {
                            fireAlarmEvent = JB_TG_N_6000(fireAlarmEvent,event);
                        }
                        fireAlarmEvent.setAddress(position);
                        if (description != null) {
                            fireAlarmEvent.setAddress(description);
                        }
                        if (positionCode != null) {
                            if (event.trim().endsWith("-") || event.trim().endsWith("*")) {
                                String collenId = ""; // 火警主机号
                                String dsnum = positionCode; // 回路 // 26 29 31 42 41 35 40
                                if (positionCode.indexOf(":") > -1) {
                                    collenId = positionCode.split(":")[0];
                                    dsnum = positionCode.split(":")[1];
                                }
                                if (dsnum.split("-").length == 3) {
                                    dsnum = dsnum.replace("-0", "");
                                }
                                fireAlarmEvent.setPositionCode(dsnum.trim());// 回路地址
                                fireAlarmEvent.setHostNumber(Integer.valueOf(collenId.trim())+"");// 主机号
                            } else {
                                String[] arr = positionCode.split("-");
                                if (arr.length > 2) {
                                    if (positionCode.indexOf("x") < 0) {
                                        fireAlarmEvent.setPositionCode(arr[1]);// 回路地址
                                        fireAlarmEvent.setHostNumber(Integer.valueOf(arr[0].trim())+"");// 主机号
                                        fireAlarmEvent.setAddress(arr[2]);// 设备地址
                                    } else {
                                        int dsNum = hexStringToAlgorism(arr[1].trim().replace("0x", ""));
                                        int slewDevice = hexStringToAlgorism(arr[0].trim().replace("0x", ""));
                                        int address = hexStringToAlgorism(
                                                arr[2].split(" ")[0].trim().replace("0x", ""));
                                        SysLog.info(dsNum + "回路地址");
                                        SysLog.info(slewDevice + "主机号");
                                        SysLog.info(address + "设备地址");
                                        fireAlarmEvent.setPositionCode(dsNum + "");// 回路地址
                                        fireAlarmEvent.setHostNumber(slewDevice + "");// 主机号
                                        fireAlarmEvent.setAddress(address + "");// 设备地址
                                    }
                                }
                            }
                        }
                        fireAlarmEvent.setMtCode(positionCode);// 地址编号
                        fireAlarmEvent.setFrom(from);// 传输装置ID
                        int eventType = 10;
                        if (isInteger(event)) {
                            eventType = Integer.valueOf(event);
                        }
                        if (event.length() > 4) {
                            /*
                             * 事件类型：1-火警事件，2-监管事件，3-反馈事件，4-联动事件，5-组件状态，6-故障恢复，7-故障事件 ，8-系统事件，（9-例行检查），10-其他
                             * 11-屏蔽,12-启动,13-延时状态,14-主电故障, 15-备电故障,16-总线故障,17-系统复位
                             */
                            if (event.startsWith("01")) {
                                eventType = 1;//火警事件
                            } else if (event.startsWith("02")) {
                                eventType = 2;//监管事件
                            } else if (event.startsWith("03")) {
                                eventType = 7;//故障事件
                            }
                            fireAlarmEvent.setEvent(eventType);

                        } else if (event.indexOf("x") > -1) {
                            eventType = hexStringToAlgorism(event.trim().replace("0x", ""));
                            if (eventType > 20 || eventType == 0) {
                                if (eventType == 0) {
                                    eventType = 17;
                                    continue;
                                } else if (eventType == 110) {// 火警
                                    eventType = 1;
                                } else if (eventType == 111) {// 故障
                                    eventType = 7;
                                } else if (eventType == 112) {// 启动
                                    eventType = 12;
                                    continue;
                                } else if (eventType == 113) {// 反馈
                                    eventType = 3;
                                    continue;
                                } else if (eventType == 114) {// 监管
                                    eventType = 2;
                                } else if (eventType == 115) {// 屏蔽
                                    eventType = 11;
                                    continue;
                                } else {
                                    eventType = 10;
                                    continue;
                                }
                            }
                            SysLog.info(eventType + "-->转换后事件类型");
                        }  else if(fireAlarmEvent.getEvent()==0) {
                            eventType = Integer.valueOf(event.trim());
                            fireAlarmEvent.setEvent(eventType);
                        }
                        if (eventType == 17) {
                            fireAlarmEvent.setMtName("系统复位");
                            continue;
                        }
                        if (eventType == 14) {
                            eventType = 2 ;
                            fireAlarmEvent.setMtName("主电故障");
                        }
                        if (eventType == 15) {
                            eventType = 2 ;
                            fireAlarmEvent.setMtName("备电故障");
                        }
                        if(fireAlarmEvent.getEvent()==10){
                            continue;
                        }
                        return sendHttpGet(socketChannel, msg, regId, fireAlarmEvent);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return format.format(new Date()).replace("--","ERROR");
        }

        return format.format(new Date()).replace("--","OK");
    }

    /**@MethodName: JB_TG_N_6000 西门子
     * @Description: 
     * @Param: [fireAlarmEvent, event]
     * @Return: com.xjt.cloud.netty.manage.entity.FireAlarmEvent
     * @Author: zhangZaiFa
     * @Date:2019/10/18 14:40 
     **/
    private static FireAlarmEvent JB_TG_N_6000(FireAlarmEvent fireAlarmEvent, String event) {
        if (event.trim().indexOf("ACTIVE") > -1) { // 监控
            fireAlarmEvent.setEvent(2);
        } else if (event.trim().indexOf("RESET") > -1) { // 复位
            fireAlarmEvent.setEvent(17);
        } else if (event.trim().indexOf("NORMAL") > -1) { // 故障恢复
            fireAlarmEvent.setEvent(6);
        } else if (event.trim().indexOf("FIRE ALARM") > -1) { // 火警事件
            fireAlarmEvent.setEvent(1);
        } else if (event.trim().indexOf("TROUBLE") > -1 || event.trim().indexOf("OFFLINE") > -1) { // 故障事件
            fireAlarmEvent.setEvent(7);
        } else {
            fireAlarmEvent.setEvent(10);
        }
        return fireAlarmEvent;
    }


    /**@MethodName: songJiang 松江火警主机
     * @Description: 
     * @Param: [fireAlarmEvent, mtName, positionCode, action]
     * @Return: com.xjt.cloud.netty.manage.entity.FireAlarmEvent
     * @Author: zhangZaiFa
     * @Date:2019/10/18 14:40 
     **/
    private static FireAlarmEvent songJiang(FireAlarmEvent fireAlarmEvent,String mtName,String positionCode,String action) {
        action = action.split(" ")[0];
        String mtType = String.valueOf(hexStringToAlgorism(action.trim().replace("0x", "")));
        if (mtMap.size()==0) {
            mtMap = getMtName();
        }
        mtName = mtMap.get(mtType);
        if (mtName == null) {
            mtName = "";
        }
        String[] arr = positionCode.split("-");
        int dsnum = hexStringToAlgorism(arr[1].trim().replace("0x", ""));
        if (dsnum > 70 || dsnum == 0) {
            if (dsnum == 0) {
                mtName = "多线回路";
            } else if (dsnum == 73) {
                mtName = "显回路";
            } else if (dsnum == 74) {
                mtName = "主电电源";
            } else if (dsnum == 76) {
                mtName = "备电电源";
            } else if (dsnum == 77) {
                mtName = "串行口";
            } else if (dsnum == 78) {
                mtName = "短路";
            } else if (dsnum == 79) {
                mtName = "开路";
            } else if (dsnum == 80) {
                mtName = "CPU版回路板通讯故障";
            } else if (dsnum == 82) {
                mtName = "CPU板多线回路板通讯故障";
            } else if (dsnum == 83) {
                mtName = "CPU与气体控制板通讯故障";
            } else if (dsnum == 85) {
                mtName = "外控电源故障";
            } else if (dsnum == 196) {
                mtName = "应急广播";
            } else if (dsnum >= 200) {
                dsnum = dsnum - 199;
                if (dsnum == 1) {
                    mtName = "气体声光报警器";
                } else if (dsnum == 2) {
                    mtName = "气体喷洒警告灯";
                } else if (dsnum == 3) {
                    mtName = "气体灭火瓶头阀";
                } else if (dsnum == 4) {
                    mtName = "气体灭火启动阀";
                } else if (dsnum == 5) {
                    mtName = "气体灭火控制模块";
                } else if (dsnum == 6) {
                    mtName = "气体现场控制盘";
                } else if (dsnum == 10) {
                    mtName = "声光报警器启动";
                } else if (dsnum == 11) {
                    mtName = "喷洒警告灯启动";
                } else if (dsnum == 12) {
                    mtName = "瓶头阀启动";
                } else if (dsnum == 13) {
                    mtName = "启动阀启动";
                } else if (dsnum == 14) {
                    mtName = "控制模块启动";
                }
            }
        }
        fireAlarmEvent.setMtName(mtName);// 设备名称
        SysLog.info(mtName + "-----》》》设备名称");
        return fireAlarmEvent;
    }

    /**
     * 十六进制字符串转十进制
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    public static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }


    /**@MethodName: isInteger 校验是否是字符串
     * @Description: 
     * @Param: [str]
     * @Return: boolean
     * @Author: zhangZaiFa
     * @Date:2019/10/18 14:38 
     **/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}