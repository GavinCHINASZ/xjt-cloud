package com.xjt.cloud.netty.manage.netty.msg.fireAlarm;

import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.netty.manage.entity.FireAlarmEvent;
import io.netty.channel.socket.SocketChannel;

import java.util.Arrays;

/**
 * @Auther: zhangzf
 * @Date: 2019/10/16 0009 17:05
 * @Description:海湾200、5000协议解析
 */
public class GST_200_5000 extends BaseReceive{

    /**@MethodName: analysisWaterSys 海湾200、5000协议解析  张玖利
     * @Description:
     * @Param: [socketChannel, msg, regId]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2019/10/16 16:43
     **/
    public static String analysisFireAlarm(SocketChannel socketChannel, String msg, String regId) {
        //SysLog.debug(msg + "--->字符");
        try {
            if (msg.startsWith("$${") && msg.endsWith("}@@")) {
                byte[] by = msg.getBytes("GB18030");
                String engineeringId = new String(Arrays.copyOfRange(by, 3, 8)).trim();
                SysLog.info(engineeringId + "--->传输装置ID");
                String num = new String(Arrays.copyOfRange(by, 30, 33)).trim();
                SysLog.info(num + "--->主机号");
                String model = new String(Arrays.copyOfRange(by, 34, 35)).trim();
                SysLog.info(model + "--->模块");
                String dsNum = new String(Arrays.copyOfRange(by, 36, 38)).trim();
                SysLog.info(dsNum + "--->回路");
                String code = new String(Arrays.copyOfRange(by, 39, 43)).trim();
                SysLog.info(code + "--->地址编号");
                String mtCode = new String(Arrays.copyOfRange(by, 43, 58)).trim();
                SysLog.info(mtCode + "--->设备编号");
                String even = new String(Arrays.copyOfRange(by, 59, 60)).trim();
                SysLog.info(even + "--->事件");
                String status = new String(Arrays.copyOfRange(by, 60, 61)).trim();
                SysLog.info(status + "--->状态");
                FireAlarmEvent fireAlarmEvent = new FireAlarmEvent();
                fireAlarmEvent.setFrom(engineeringId);
                fireAlarmEvent.setSourceData(msg);
                fireAlarmEvent.setAddress(code);// 设备地址
                fireAlarmEvent.setMsgType("FireAlarm");
                fireAlarmEvent.setMtCode(mtCode);// 设备编号
                fireAlarmEvent.setAddress(code);// 设备地址
                String mtName = new String(Arrays.copyOfRange(by, 61, 91), "GB18030").trim();
                String mtLocation = new String(Arrays.copyOfRange(by, 91, 131), "GB18030").trim();
                SysLog.info(mtName + "--->设备名称");
                SysLog.info(mtLocation + "--->设备位置");
                fireAlarmEvent.setMtName(mtName);
                fireAlarmEvent.setAddress(mtLocation);

                fireAlarmEvent.setPositionCode(dsNum + "-" + code);// 回路地址
                fireAlarmEvent.setBrand(model);
                fireAlarmEvent.setHostNumber(Integer.valueOf(num)+"");
                if ("F".equals(even)) { // 火警事件
                    fireAlarmEvent.setEvent(1); // 事件
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                } else if ("T".equals(even)) { // 故障事件
                    fireAlarmEvent.setEvent(7);
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                } else if ("S".equals(even)) {// 监管事件
                    fireAlarmEvent.setEvent(2);
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                } else if ("C".equals(even)) {// 反馈事件
                    fireAlarmEvent.setEvent(3);
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                }  else if ("H".equals(even)) {
                    fireAlarmEvent.setMtName("系统复位");
                    fireAlarmEvent.setEvent(17);

                } else if ("Z".equals(even)) {
                    fireAlarmEvent.setMtName("主电故障");
                    fireAlarmEvent.setEvent(14);
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                } else if ("B".equals(even)) {// 14-主电故障,15-备电故障
                    fireAlarmEvent.setMtName("备电故障");
                    fireAlarmEvent.setEvent(15);
                    if ("F".equals(status)) {
                        fireAlarmEvent.setEvent(6);
                    }
                }
                return sendHttpGet(socketChannel, msg, regId, fireAlarmEvent);
            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        return "";
    }
}
