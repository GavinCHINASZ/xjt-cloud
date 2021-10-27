package com.xjt.cloud.commons.utils;


import com.alibaba.fastjson.JSONObject;

/**
 * NB烟感消息内容转换
 *
 * @date 2020/08/03
 * @author huanggc
 */
public class NbSmokeComParseUtils {

    public static void main(String[] args) {
        // 拆卸 29067720c940001d03200000d079    装回 290676c0c900001d033000006624
        // 烟雾测试按键消音 29077400c903001b0ff00000fc4e     无烟雾报警 29077460c900401c02e00000abc8
        // 烟雾报警 28f75e60c902001e0ff0000003dc
        String value = "580001a3006efd080864774000000e64c940001c02f00000a23a";

        String powerOn = "powerOn";
        String heartbeat = "heartbeat";
        String alarmStatus = "alarmStatus";
        String fullInfo = "fullInfo";
        String log = "log";
        JSONObject jsonObject = comParse(heartbeat, value);
    }

    /**
     * 解析方法
     *
     * @param valueType powerOn      上电上报信息, 26261_1_26241
     *                  heartbeat    心跳信息,    26261_1_26243
     *                  alarmStatus  告警状态信息 26261_1_26244
     *                  fullInfo     全量信息,
     *                  log          日志信息
     * @param value
     */
    public static JSONObject comParse(String valueType, String value) {
        int valueLen = value.length();

        if (valueLen == 0) {
            // "未输入消息内容"
        }

        if ("powerOn".equals(valueType)) {
            return powerReportParse(value, valueLen);

        } else if ("heartbeat".equals(valueType)) {
            return heartbeatParse(value, valueLen);

        } else if ("alarmStatus".equals(valueType)) {
            return alarmStatusParse(value, valueLen);

        } else if ("fullInfo".equals(valueType)) {
            return fullInformationParse(value, valueLen);

        } else if ("log".equals(valueType)) {
            return logParse(value, valueLen);
        }

        return null;
    }

    /**
     * 日志信息
     *
     * @param value
     * @param valueLen
     */
    private static JSONObject logParse(String value, int valueLen) {
        JSONObject jsonObject = new JSONObject();

        if (valueLen != (48 * 2)){
            SysLog.debug("信息长度不是48个字节,不符合log信息长度");
            jsonObject.put("errorMsg", "信息长度不是48个字节,不符合log信息长度");
            return jsonObject;
        } else if (valueLen % 2 != 0) {
            SysLog.debug("信息长度不是偶数，不正确");
            jsonObject.put("errorMsg", "信息长度不是偶数，不正确");
            return jsonObject;
        }
                
        int msgCRC = StringUtils.hexStringToAlgorism(value.substring(valueLen - 4, valueLen));// CRC长度两个字节
        // SysLog.debug("msgCRC %02x" %msgCRC)
        // 待支持对数据域的CRC校验
        // CRC合法性处理，待支持

        int pos = 0;
        int csq = (StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1)) >> 2) & 0x3F;
        if (csq == 0x3F){
            // SysLog.debug("信号质量CSQ:not known or not detectable");
        }else if (csq <= 31){
            SysLog.debug("信号质量CSQ:" + csq);
            jsonObject.put("signalValue", csq);
        } else {
            SysLog.debug("信号质量CSQ:非法");
            // return null;
        }

        pos = pos + 2 * 1;
        /*int ecl = (StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1))) & 0x03;
        if (ecl > 2) {
            SysLog.debug("覆盖等级ECL:非法");
            return null;
        }else {
            SysLog.debug("覆盖等级ECL:" + ecl);
        }*/

        pos = pos + 2 * 1;
        /*int Pci = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("物理小区标识PCI:" + Pci);*/

        pos = pos + 2 * 2;
        /*int Snr = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("信燥比SNR:" + Snr);*/

        pos = pos + 2 * 2;
        /*String rsrpHexStr = value.substring(pos, pos + 2 * 2);// 提取为十六进制的字符串
        rsrpHexStr = rsrpHexStr[2] + rsrpHexStr[3] +rsrpHexStr[0]+rsrpHexStr[1];// 存储模式变换
        rsrpNeghex = binascii.unhexlify(rsrpHexStr);// 去十六进制化
        rsrpNeg = struct.unpack('h', rsrpNeghex);
        SysLog.debug("参考信号接收功率RSRP:%d"%(rsrpNeg[0]));// 负值显示*/

        pos = pos + 2 * 2;
        /*int CellId = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 4));
        SysLog.debug("基站小区标识CELL ID:" + CellId);*/

        pos = pos + 2 * 4;
        /*int Earfcn = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 4));
        SysLog.debug("中心频点EARFCN:" + Earfcn);*/

        pos = pos + 2 * 4;
        /*int watchDog = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("看门狗重启次数:" + watchDog);*/

        pos = pos + 2 * 2;
        int offLine = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("设备掉线次数:" + offLine);

        pos = pos + 2 * 2;
        int retryConnect = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("设备重连尝试次数:" + retryConnect);

        pos = pos + 2 * 2;
        int retryFailed = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("设备重连失败次数:" + retryFailed);

        pos = pos + 2 * 2;
        int reportFailed = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("设备上报消息失败次数:" + reportFailed);

        pos = pos + 2 * 2;
        int cmdFailed = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("命令处理失败次数:" + cmdFailed);

        pos = pos + 2 * 2;
        int falshFailed = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("Flash读写失败次数:" + falshFailed);

        pos = pos + 2 * 2;
        int aramHistory = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史报警次数:" + aramHistory);

        pos = pos + 2 * 2;
        int muteByKey = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史报警静音（测试按键静音）次数:" + muteByKey);

        pos = pos + 2 * 2;
        int muteByRemote = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史报警静音（远程消音）次数:" + muteByRemote);

        pos = pos + 2 * 2;
        int testByKey = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史测试次数（按下测试按键测试）:" + testByKey);

        pos = pos + 2 * 2;
        int testByRemote = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史测试次数（远程测试）:" + testByRemote);

        pos = pos + 2 * 2;
        int sensorAbnormal = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("历史传感器异常次数:" + sensorAbnormal);

        pos = pos + 2 * 2;
        int buzzerAbnormal = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("蜂鸣器异常次数:" + buzzerAbnormal);

        pos = pos + 2 * 2;
        int sensorRemove = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("设备被拆开次数:" + sensorRemove);
        pos = pos + 2 * 2;

        return jsonObject;
    }

    /**
     * 全量信息
     *
     * @param value
     * @param valueLen
     */
    private static JSONObject fullInformationParse(String value, int valueLen) {
        JSONObject jsonObject = new JSONObject();

        if (valueLen != (67 * 2)) {
            SysLog.debug("信息长度不是67个字节,不符合全量信息长度");
            jsonObject.put("errorMsg", "信息长度不是67个字节,不符合全量信息长度");
            return jsonObject;
        } else if (valueLen % 2 != 0) {
            SysLog.debug("信息长度不是偶数，不正确");
            jsonObject.put("errorMsg", "信息长度不是偶数，不正确");
            return jsonObject;
        }

        int msgCRC = StringUtils.hexStringToAlgorism(value.substring(valueLen - 4, valueLen));// CRC长度两个字节
        // SysLog.debug("msgCRC %02x" %msgCRC)
        // 待支持对数据域的CRC校验
        // CRC合法性处理，待支持

        /*StringUtils.hexStringToAlgorism(value.substring(0, 2));
        int DemandVersion   = StringUtils.hexStringToAlgorism(value.substring(0, 2));
        int HardwareVersion = StringUtils.hexStringToAlgorism(value.substring(2, 4));
        int EmbeddedVersion = StringUtils.hexStringToAlgorism(value.substring(4, 16));
        SysLog.debug("版本号：" + DemandVersion + "." + HardwareVersion + "." + EmbeddedVersion);*/

        int PartitionNumber = (StringUtils.hexStringToAlgorism(value.substring(6, 8)) >>7) &0x1;
        if (PartitionNumber == 0) {
            // PartitionNumber = "a";
        } else if (PartitionNumber == 1) {
            // PartitionNumber = "b";
        } else {
            SysLog.debug("分区号：非法");
            jsonObject.put("errorMsg", "分区号：非法");
            return jsonObject;
        }
        // SysLog.debug("分区号：" + PartitionNumber + "分区");

        int csq = (StringUtils.hexStringToAlgorism(value.substring(6, 8)) >>1) &0x3F;
        if (csq == 0x3F) {
            SysLog.debug("信号质量CSQ:not known or not detectable");
            jsonObject.put("errorMsg", "信号质量CSQ:not known or not detectable");
        } else if (csq <= 31) {
            SysLog.debug("信号质量CSQ:" + csq);
            jsonObject.put("signalValue", csq);
        } else {
            SysLog.debug("信号质量CSQ:非法");
            jsonObject.put("errorMsg", "信号质量CSQ:非法");
            return jsonObject;
        }

        /*int APN = (StringUtils.hexStringToAlgorism(value.substring(8, 10)) >>5) &0x03;
        if (APN == 0) {
            SysLog.debug("APN: cmnbiot");
        } else if (APN >= 1 && APN <= 7) {
            SysLog.debug("APN: cmnbiot" + APN);
        } else {
            SysLog.debug("APN: 非法");
            return null;
        }*/

        /*int activeTime1 = (StringUtils.hexStringToAlgorism(value.substring(8, 10)) &0x1F) << 3;// active_time高5位
        int activeTime2 = (StringUtils.hexStringToAlgorism(value.substring(10, 12)) &0xE0) >> 5;// active_time低3位
        int activeTime = activeTime1 | activeTime2;
        int activeTimeUint = (activeTime &0xE0) >> 5;// 获得activeTime单位
        int activeTimeSetValue = activeTime &0x1F;
        if (activeTimeUint == 0) {//2 seconds
            int activeTimeUintValue = 2;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            SysLog.debug("activeTime:" + activeTimeSum + " seconds");
        } else if (activeTimeUint == 1) {// 1 minute
            int activeTimeUintValue = 1;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            SysLog.debug("activeTime:" + activeTimeSum + " minutes");
        } else if (activeTimeUint == 2) {// decihours
            int activeTimeUintValue = 6;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            SysLog.debug("activeTime:" + activeTimeSum + " seconds");
        } else if (activeTimeUint == 7) {
            int activeTimeUintValue = 0x70;
            SysLog.debug("activeTime:deactivated");
        } else {
            SysLog.debug("activeTimeUint invalid ");
            return null;
        }*/

        /*int TAU1 = (StringUtils.hexStringToAlgorism(value.substring(10, 12)) &0x1F) << 3;// TAU高5位
        int TAU2 = (StringUtils.hexStringToAlgorism(value.substring(12, 14)) &0xE0) >> 5;// TAU低3位
        int TAU = TAU1 | TAU2;
        int TAUUint = (TAU & 0xE0) >> 5;// TAU单位
        int TAUSetValue = TAU & 0x1F;
        if (TAUUint == 0) {//10 minutes
            int TAUUintValue = 10;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " minutes");
        } else if (TAUUint == 1){// 1 hour
            int TAUUintValue = 1;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " hours");
        } else if (TAUUint == 2) {//10 hours
            int TAUUintValue = 10;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " hours");
        } else if (TAUUint == 3) {//2 seconds
            int TAUUintValue = 2;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " seconds");
        } else if (TAUUint == 4) {//30 seconds
            int TAUUintValue = 30;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " seconds");
        } else if (TAUUint == 5) {//1 minute
            int TAUUintValue = 1;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " minutes");
        } else if (TAUUint == 6) {//320 hours
            int TAUUintValue = 320;
            int TAUSum = TAUUintValue * TAUSetValue;
            SysLog.debug("TAU:" + TAUSum + " hours");
        } else if (TAUUint == 7) {
            int TAUUintValue = 0x70;
            SysLog.debug("TAU:deactivated");
        } else {
            SysLog.debug("TAUUint invalid ");
            return null;
        }*/

        /*int lowBatThreshold1 = StringUtils.hexStringToAlgorism(value.substring(12, 14)) & 0x1F;// 低电压阈值高5位
        int lowBatThreshold2 = StringUtils.hexStringToAlgorism(value.substring(14, 16)) >> 3;// 低电压阈值低5位
        int lowBatThreshold = ((lowBatThreshold1 << 5) | lowBatThreshold2 );
        if (lowBatThreshold >= 0 && lowBatThreshold <= 1000) {
            SysLog.debug("低电压阈值:" + (lowBatThreshold * 10) + " mV");
        } else {
            SysLog.debug("低电压阈值非法 ");
            return null;
        }*/

        /*int ecl = StringUtils.hexStringToAlgorism(value.substring(14, 16)) & 0x03;
        if (ecl > 2) {
            SysLog.debug("覆盖等级ECL非法:" + ecl);
            return null;
        }
        SysLog.debug("覆盖等级ECL:" + ecl);*/

        /*int Pci = StringUtils.hexStringToAlgorism(value.substring(16, 20));
        if (Pci < 0 || Pci > 503){
            SysLog.debug("物理小区标识PCI非法:" + Pci);
            return null;
        }
        SysLog.debug("物理小区标识PCI:" + Pci);*/

        /*int Snr = StringUtils.hexStringToAlgorism(value.substring(20, 24));
        SysLog.debug("信燥比SNR:" + Snr);*/

        /*String rsrpHexStr = value.substring(24, 28);// 提取为十六进制的字符串
        rsrpHexStr = rsrpHexStr[2] + rsrpHexStr[3] + rsrpHexStr[0]+rsrpHexStr[1];// 存储模式变换
        rsrpNeghex = binascii.unhexlify(rsrpHexStr); // 去十六进制化
        rsrpNeg = struct.unpack('h', rsrpNeghex);
        SysLog.debug("参考信号接收功率RSRP:%d"%(rsrpNeg[0]));// 负值显示*/

        /*int CellId = StringUtils.hexStringToAlgorism(value.substring(28, 36));
        SysLog.debug("基站小区标识CELL ID:" + CellId);*/

        /*int Earfcn = StringUtils.hexStringToAlgorism(value.substring(36, 44));
        if (Earfcn < 0 || Earfcn > 262143) {
            SysLog.debug("中心频点EARFCN非法:" + Earfcn);
            return null;
        }
        SysLog.debug("中心频点EARFCN:" + Earfcn);*/

        /*int DeviceImei = StringUtils.hexStringToAlgorism(value.substring(44, 59));// 最后一位填补
        SysLog.debug("设备IMEI：" + DeviceImei);
        jsonObject.put("DeviceImei", DeviceImei);*/

        /*int ModuleImei = StringUtils.hexStringToAlgorism(value.substring(60, 75));// 最后一位填补
        SysLog.debug("模组IMEI:" + ModuleImei);
        jsonObject.put("ModuleImei", ModuleImei);*/

        /*int SN = StringUtils.hexStringToAlgorism(value.substring(76, 98));
        SysLog.debug("SN：" + SN);*/

        /*int DeviceIMSI = StringUtils.hexStringToAlgorism(value.substring(98, 113));// 最后一位填补
        SysLog.debug("设备IMSI:" + DeviceIMSI);
        jsonObject.put("DeviceIMSI", DeviceIMSI);*/

        int batCap = (StringUtils.hexStringToAlgorism(value.substring(114, 116)) & 0xFE ) >> 0x01;
        SysLog.debug("电池电量:" + batCap);
        jsonObject.put("electricQuantity", batCap * 100);

        int SensorADCflag = StringUtils.hexStringToAlgorism(value.substring(114, 116)) & 0x01;
        int sensorStatus = StringUtils.hexStringToAlgorism(value.substring(116, 118));
        /*if ((sensorStatus&0x80) == 0x80){
            SysLog.debug("设备超出使用寿命");
        } else {
            SysLog.debug("设备在使用期限内");
        }*/

        if ((sensorStatus&0x40) == 0x40){
            /*SysLog.debug("设备被拆开");
            jsonObject.put("eventType", 5);
            return jsonObject;*/
        } else {
            // SysLog.debug("设备未被拆开");
        }

        /*if ((sensorStatus&0x20) == 0x20){
            SysLog.debug("蜂鸣器故障");
        } else {
            SysLog.debug("蜂鸣器正常");
        }*/

        if ((sensorStatus&0x18) == 0x00) {
            // SysLog.debug("传感器正常");
        } else if ((sensorStatus&0x18) == 0x08) {
            SysLog.debug("传感器故障");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else if ((sensorStatus&0x18) == 0x10) {
            // SysLog.debug("传感器免打扰期间");
        } else {
            SysLog.debug("传感器状态异常");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        if ((sensorStatus & 0x07) == 0x00) {
            // SysLog.debug("无烟雾报警");
        } else if ((sensorStatus & 0x07) == 0x01) {
            // SysLog.debug("烟雾预报警（保留）");
        } else if ((sensorStatus & 0x07) == 0x02) {
            SysLog.debug("烟雾报警");
            jsonObject.put("eventType", 1);
            return jsonObject;
        } else if ((sensorStatus & 0x07) == 0x03) {
            // SysLog.debug("烟雾报警静音（测试按键消音）");
        } else if ((sensorStatus & 0x07) == 0x04) {
            // SysLog.debug("烟雾报警静音（远程消音）");
        } else {
            SysLog.debug("烟雾报警状态异常");
            jsonObject.put("eventType", 1);
            return jsonObject;
        }

        int sensorStatus1 = StringUtils.hexStringToAlgorism(value.substring(118, 120));
        /*if ((sensorStatus1 & 0xC0) == 0x00) {
            SysLog.debug("测试模式：无测试");
        } else if ((sensorStatus1 & 0xC0) == 0x40) {
            SysLog.debug("测试模式：按键测试状态");
        } else if ((sensorStatus1 & 0xC0) == 0x80) {
            SysLog.debug("测试模式：远程测试状态");
        } else {
            SysLog.debug("测试模式：异常状态");
            return null;
        }*/

        /*if ((sensorStatus1 & 0x30) == 0x00) {
            SysLog.debug("低压报警状态：电压正常");
        } else if ((sensorStatus1 & 0x30) == 0x10) {
            SysLog.debug("低压报警状态：低压报警");
        } else if ((sensorStatus1 & 0x30) == 0x20) {
            SysLog.debug("低压报警状态：低压报警（消音状态）");
        } else {
            SysLog.debug("低压报警状态：异常状态");
            return null;
        }*/

        if ((sensorStatus1 & 0x08) == 0x08) {
            SysLog.debug("传感器无响应，失联");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        /*if ((sensorStatus1&0x07) == 0x00) {
            SysLog.debug("蜂鸣器当前发出的声音 ：静音");
        } else if ((sensorStatus1&0x07) == 0x01) {
            SysLog.debug("蜂鸣器当前发出的声音 ：故障");
        } else if ((sensorStatus1&0x07) == 0x02) {
            SysLog.debug("蜂鸣器当前发出的声音 ：报警");
        } else if ((sensorStatus1&0x07) == 0x07) {
            SysLog.debug("蜂鸣器当前发出的声音 ：蜂鸣器故障");
        } else {
            SysLog.debug("蜂鸣器当前发出的声音 ：状态非法");
            return null;
        }*/

        /*int sensorToC = StringUtils.hexStringToAlgorism(value.substring(120, 122));
        if (sensorToC == 0xFF) {
            SysLog.debug("传感器不支持温度功能");
        } else if ((sensorToC & 0x80 ) == 0x00) {
            SysLog.debug("传感器温度:" + (sensorToC & 0x7F) + "oC");
        } else {
            SysLog.debug("传感器温度:-" + (sensorToC & 0x7F) + "oC");
        }*/

        /*if (SensorADCflag == 0x01) {
            int sensorADC = StringUtils.hexStringToAlgorism(value.substring(122, 126));
            SysLog.debug("sensorADC: 0x" + sensorADC);

            int sensorADCoffset = StringUtils.hexStringToAlgorism(value.substring(126, 130));
            SysLog.debug("sensorADCoffset: 0x" + sensorADCoffset);
        }*/
                
        return jsonObject;
    }

    /**
     * 告警状态信息
     *
     * @param value
     * @param valueLen
     */
    private static JSONObject alarmStatusParse(String value, int valueLen) {
        JSONObject jsonObject = new JSONObject();
        if (valueLen != ((8 + 4 + 2) * 2)) {
            SysLog.debug("信息长度不是14个字节,不符合主动变化信息长度,实际长度:" + valueLen);
            jsonObject.put("errotvalue", "信息长度不是14个字节,不符合主动变化信息长度,实际长度:" + valueLen);
            return jsonObject;
        } else if (valueLen % 2 != 0) {
            SysLog.debug("信息长度不是偶数，不正确");
            jsonObject.put("errotvalue", "信息长度不是偶数，不正确");
            return jsonObject;
        }

        /*String s = value.substring(valueLen - 4, valueLen);
        int valueCRC = StringUtils.hexStringToAlgorism(s);// CRC长度两个字节*/

        // jsonObject.put("valueCRC %02x" %valueCRC)
        // 待支持对数据域的CRC校验
        // CRC合法性处理，待支持

        int pos = 0;
        /*s = value.substring(pos, pos + 2 * 2);
        int u16Data = StringUtils.hexStringToAlgorism(s);
        int year = u16Data >> 9;
        int month = (u16Data & 0x01E0) >> 5;
        int day = (u16Data & 0x001F);*/

        pos = pos + 2 * 2;
        /*s = value.substring(pos, pos + 2 * 2);
        u16Data = StringUtils.hexStringToAlgorism(s);
        int hour = u16Data >> 11;
        int minute = (u16Data & 0x07E0) >> 5;*/

        /*if ((year < 0) || (year > 99)) {
            // SysLog.debug("年非法=" + year);
            jsonObject.put("errotvalue", "年非法=" + year);
            return jsonObject;
        } else {
            year += 2000;
        }*/

        /*if ((month < 1) || (month > 12)) {
            // SysLog.debug("月非法=" + month);
            jsonObject.put("errotvalue", "月非法=" + month);
            return jsonObject;
        }*/

        /*if ((day < 1) || (day > 31)) {
            // SysLog.debug("日非法=" + day);
            jsonObject.put("errotvalue", "日非法=" + day);
            return jsonObject;
        }*/

        /*if ((hour < 0) || (hour > 23)) {
            // SysLog.debug("小时非法=" + hour);
            jsonObject.put("errotvalue", "小时非法=" + hour);
            return jsonObject;
        }*/

        /*if ((minute < 0) || (minute > 59)) {
            // SysLog.debug("分钟非法=" + minute);
            jsonObject.put("errotvalue", "分钟非法=" + minute);
            return jsonObject;
        }*/

        /*String heartbeatTime = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day) + " "
                + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":00";
        jsonObject.put("heartbeatTime", heartbeatTime);
        SysLog.debug("时间:" + heartbeatTime);*/

        pos = pos + 2 * 2;
        String s1 = value.substring(pos, pos + 2 * 1);
        int batCap = (StringUtils.hexStringToAlgorism(s1) & 0xFE) >> 0x01;
        // SysLog.debug("电池电量:" + batCap+ "%");
        jsonObject.put("electricQuantity", batCap * 100);

        /*String s4 = value.substring(pos, pos + 2 * 1);
        int SensorADCflag = StringUtils.hexStringToAlgorism(s4) & 0x01;*/

        pos = pos + 2 * 1;
        String s5 = value.substring(pos, pos + 2 * 1);
        int sensorStatus = StringUtils.hexStringToAlgorism(s5);
        /*if ((sensorStatus & 0x80) == 0x80) {
            jsonObject.put("devicelse if {e", 1);
            // SysLog.debug("设备超出使用寿命");
        } else {
            jsonObject.put("devicelse if {e", 0);
            // SysLog.debug("设备在使用期限内");
        }*/

        if ((sensorStatus & 0x40) == 0x40) {// 10. 拆卸报警
            // jsonObject.put("eventType", 5);
            // return jsonObject;
            // SysLog.debug("设备被拆开");
        } else {// 11. 拆卸恢复
            // jsonObject.put("eventType", 11);
            // SysLog.debug("设备未被拆开");
        }

        /*if ((sensorStatus & 0x20) == 0x20) {
            jsonObject.put("buzzer", 1);
            // SysLog.debug("蜂鸣器故障");
        } else {
            jsonObject.put("buzzer", 0);
            // SysLog.debug("蜂鸣器正常");
        }*/

        if ((sensorStatus & 0x18) == 0x00) {
            SysLog.debug("传感器正常");
            jsonObject.put("eventTypes", 5);
        } else if ((sensorStatus & 0x18) == 0x08) {
            // SysLog.debug("传感器故障");
            jsonObject.put("eventType", 5);
        } else if ((sensorStatus & 0x18) == 0x10) {
            // SysLog.debug("传感器免打扰期间");
        } else {
            // SysLog.debug("传感器状态异常");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        if ((sensorStatus & 0x07) == 0x00) {
            SysLog.debug("无烟雾报警");
            jsonObject.put("eventTypeSmoke", 1);
            return jsonObject;
        } else if ((sensorStatus & 0x07) == 0x01) {
            // SysLog.debug("烟雾预报警（保留）");
        } else if ((sensorStatus & 0x07) == 0x02) {
            SysLog.debug("烟雾报警");
            jsonObject.put("eventType", 1);
            return jsonObject;
        } else if ((sensorStatus & 0x07) == 0x03) {
            SysLog.debug("烟雾报警静音（测试按键消音）");
            jsonObject.put("eventTypeSmoke", 1);
            return jsonObject;
        } else if ((sensorStatus & 0x07) == 0x04) {
            SysLog.debug("烟雾报警静音（远程消音）");
            jsonObject.put("eventTypeSmoke", 1);
            return jsonObject;
        } else {
            SysLog.debug("烟雾报警状态异常");
            jsonObject.put("eventType", 1);
            return jsonObject;
        }

        pos = pos + 2 * 1;
        String s2 = value.substring(pos, pos + 2 * 1);
        int sensorStatus1 = StringUtils.hexStringToAlgorism(s2);

        /*if ((sensorStatus1 & 0xC0) == 0x00) {
            jsonObject.put("testMode", 0);
            // SysLog.debug("测试模式：无测试");
        } else if ((sensorStatus1 & 0xC0) == 0x40) {
            jsonObject.put("testMode", 1);
            // SysLog.debug("测试模式：按键测试状态");
        } else if ((sensorStatus1 & 0xC0) == 0x80) {
            jsonObject.put("testMode", 2);
            // SysLog.debug("测试模式：远程测试状态");
        } else {
            jsonObject.put("testMode", 3);
            // SysLog.debug("测试模式：异常状态");
            return jsonObject;
        }*/

        /*if ((sensorStatus1 & 0x30) == 0x00) {
            jsonObject.put("voltage", 0);
            // SysLog.debug("低压报警状态：电压正常");
        } else if ((sensorStatus1 & 0x30) == 0x10) {
            jsonObject.put("voltage", 1);
            // SysLog.debug("低压报警状态：低压报警");
        } else if ((sensorStatus1 & 0x30) == 0x20) {
            jsonObject.put("voltage", 2);
            // SysLog.debug("低压报警状态：低压报警（消音状态）");
        } else {
            jsonObject.put("voltage", 3);
            // SysLog.debug("低压报警状态：异常状态");
            return jsonObject;
        }*/

        if ((sensorStatus1 & 0x08) == 0x08) {
            SysLog.debug("传感器无响应，失联");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        /*if ((sensorStatus1 & 0x07) == 0x00) {
            jsonObject.put("buzzerVoice", 0);
            // SysLog.debug("蜂鸣器当前发出的声音 ：静音");
        } else if ((sensorStatus1 & 0x07) == 0x01) {
            jsonObject.put("buzzerVoice", 1);
            // SysLog.debug("蜂鸣器当前发出的声音 ：故障");
        } else if ((sensorStatus1 & 0x07) == 0x02) {
            jsonObject.put("buzzerVoice", 2);
            // SysLog.debug("蜂鸣器当前发出的声音 ：报警");
        } else if ((sensorStatus1 & 0x07) == 0x07) {
            jsonObject.put("buzzerVoice", 7);
            // SysLog.debug("蜂鸣器当前发出的声音 ：蜂鸣器故障");
        } else {
            jsonObject.put("buzzerVoice", 8);
            // SysLog.debug("蜂鸣器当前发出的声音 ：状态非法");
            return jsonObject;
        }*/

        /*pos = pos + 2 * 1;
        String s3 = value.substring(pos, pos + 2 * 1);
        int sensorToC = StringUtils.hexStringToAlgorism(s3);
        if (sensorToC == 0xFF) {
            SysLog.debug("传感器不支持温度功能");
        } else if ((sensorToC & 0x80) == 0x00) {
            SysLog.debug("传感器温度:" + (sensorToC & 0x7F) + "oC");
        } else {
            SysLog.debug("传感器温度:-" + (sensorToC & 0x7F) + "oC");
        }*/

        /*pos = pos + 2 * 1;
        if (SensorADCflag == 0x01) {
            String sensorADC = value.substring(pos, pos + 2 * 2);
            SysLog.debug("sensorADC: 0x" + sensorADC);

            pos = pos + 2 * 2;
            String sensorADCoffset = value.substring(pos, pos + 2 * 2);
            SysLog.debug("sensorADCoffset: 0x" + sensorADCoffset);
            pos = pos + 2 * 2;
        }*/
        return jsonObject;
    }

    /**
     * 心跳信息
     *
     * @param value
     * @param valueLen
     */
    private static JSONObject heartbeatParse(String value, int valueLen) {
        JSONObject jsonObject = new JSONObject();

        if (valueLen != (26 * 2)) {
            SysLog.debug("信息长度不是26个字节,不符合心跳信息长度");
            jsonObject.put("errorMsg", "信息长度不是26个字节,不符合心跳信息长度");
            return jsonObject;
        } else if (valueLen % 2 != 0) {
            SysLog.debug("信息长度不是偶数，不正确");
            jsonObject.put("errorMsg", "信息长度不是偶数，不正确");
            return jsonObject;
        }

        // int msgCRC = StringUtils.hexStringToAlgorism(value.substring(valueLen - 4, valueLen));// CRC长度两个字节

        // SysLog.debug("msgCRC %02x" %msgCRC)
        // 待支持对数据域的CRC校验
        // CRC合法性处理，待支持

        int pos = 0;
        int csq = (StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1)) >> 2) & 0x3F;
        if (csq == 0x3F) {
            SysLog.debug("信号质量CSQ:not known or not detectable");
        } else if (csq <= 31) {
            SysLog.debug("信号质量CSQ:" + csq);
            jsonObject.put("signalValue", csq);
        } else {
            SysLog.debug("信号质量CSQ:非法");
            // return False;
        }

        pos = pos + 2 * 1;
        /*int ecl = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1)) & 0x03;
        if (ecl > 2) {
            SysLog.debug("覆盖等级ECL:非法");
            // return False;
        }
        SysLog.debug("覆盖等级ECL:" + ecl);*/

        pos = pos + 2 * 1;
        /*int Pci = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        if (Pci < 0 || Pci > 503) {
            SysLog.debug("物理小区标识PCI非法:" + Pci);
            // return False;
        }
        SysLog.debug("物理小区标识PCI:" + Pci);*/

        pos = pos + 2 * 2;
        /*int Snr = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
        SysLog.debug("信燥比SNR:" + Snr);*/

        pos = pos + 2 * 2;
        /*int rsrpHexStr = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2)); //提取为十六进制的字符串
        rsrpHexStr = rsrpHexStr[2] + rsrpHexStr[3] +rsrpHexStr[0]+rsrpHexStr[1]; //存储模式变换
        rsrpNeghex = binascii.unhexlify(rsrpHexStr); //去十六进制化
        rsrpNeg = struct.unpack('h', rsrpNeghex);
        SysLog.debug("参考信号接收功率RSRP:%d"%(rsrpNeg[0]));//负值显示*/

        pos = pos + 2 * 2;
        /*int CellId = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 4));
        SysLog.debug("基站小区标识CELL ID:" + CellId);*/

        pos = pos + 2 * 4;
        /*int Earfcn = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 4));
        if (Earfcn < 0 || Earfcn > 262143) {
            SysLog.debug("中心频点EARFCN非法:" + Earfcn);
            // return False;
        }
        SysLog.debug("中心频点EARFCN:" + Earfcn);*/

        pos = pos + 2 * 4;
        int batCap = (StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1)) & 0xFE) >> 0x01;
        // SysLog.debug("电池电量:" + batCap);
        jsonObject.put("electricQuantity", batCap * 100);

        int SensorADCflag = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1)) & 0x01;

        pos = pos + 2 * 1;
        int sensorStatus = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1));
        /*if ((sensorStatus & 0x80) == 0x80) {
            SysLog.debug("设备超出使用寿命");
        } else {
            SysLog.debug("设备在使用期限内");
        }*/

        if ((sensorStatus & 0x40) == 0x40) {
            /*SysLog.debug("设备被拆开");
            jsonObject.put("eventType", 5);
            return jsonObject;*/
        } else {
            //SysLog.debug("设备未被拆开");
        }

        /*if ((sensorStatus & 0x20) == 0x20) {
            SysLog.debug("蜂鸣器故障");
        } else {
            SysLog.debug("蜂鸣器正常");
        }*/

        if ((sensorStatus & 0x18) == 0x00) {
            // SysLog.debug("传感器正常");
        } else if ((sensorStatus & 0x18) == 0x08) {
            SysLog.debug("传感器故障");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else if ((sensorStatus & 0x18) == 0x10) {
            // SysLog.debug("传感器免打扰期间");
        } else {
            SysLog.debug("传感器状态异常");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        if ((sensorStatus & 0x07) == 0x00) {
            // SysLog.debug("无烟雾报警");
        } else if ((sensorStatus & 0x07) == 0x01) {
            // SysLog.debug("烟雾预报警（保留）");
        } else if ((sensorStatus & 0x07) == 0x02){
            SysLog.debug("烟雾报警");
            jsonObject.put("eventType", 1);
            return jsonObject;
        } else if ((sensorStatus & 0x07) == 0x03) {
            // SysLog.debug("烟雾报警静音（测试按键消音）");
        } else if ((sensorStatus & 0x07) == 0x04) {
            // SysLog.debug("烟雾报警静音（远程消音）");
        } else {
            SysLog.debug("烟雾报警状态异常");
            jsonObject.put("eventType", 1);
            return jsonObject;
        }

        pos = pos + 2 * 1;
        int sensorStatus1 = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1));
        /*if ((sensorStatus1&0xC0) == 0x00) {
            SysLog.debug("测试模式：无测试");
        } else if ((sensorStatus1&0xC0) == 0x40) {
            SysLog.debug("测试模式：按键测试状态");
        } else if ((sensorStatus1&0xC0) == 0x80) {
            SysLog.debug("测试模式：远程测试状态");
        } else {
            SysLog.debug("测试模式：异常状态");
            return jsonObject;
        }*/

        /*if ((sensorStatus1&0x30) == 0x00) {
            SysLog.debug("低压报警状态：电压正常");
        } else if ((sensorStatus1&0x30) == 0x10) {
            SysLog.debug("低压报警状态：低压报警");
        }else if ((sensorStatus1&0x30) == 0x20) {
            SysLog.debug("低压报警状态：低压报警（消音状态）");
        } else {
            SysLog.debug("低压报警状态：异常状态");
            return jsonObject;
        }*/

        if ((sensorStatus1&0x08) == 0x08) {
            SysLog.debug("传感器无响应，失联");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        /*if ((sensorStatus1&0x07) == 0x00) {
            // SysLog.debug("蜂鸣器当前发出的声音 ：静音");
        } else if ((sensorStatus1&0x07) == 0x01) {
            SysLog.debug("蜂鸣器当前发出的声音 ：故障");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else if ((sensorStatus1&0x07) == 0x02) {
            SysLog.debug("蜂鸣器当前发出的声音 ：报警");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else if ((sensorStatus1&0x07) == 0x07) {
            SysLog.debug("蜂鸣器当前发出的声音 ：蜂鸣器故障");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else {
            SysLog.debug("蜂鸣器当前发出的声音 ：状态非法");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }*/

        pos = pos + 2 * 1;
        /*int sensorToC = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 1));
        if (sensorToC == 0xFF) {
            SysLog.debug("传感器不支持温度功能");
        } else if ((sensorToC & 0x80 ) == 0x00) {
            SysLog.debug("传感器温度:" + (sensorToC & 0x7F) + "oC");
        } else{
            SysLog.debug("传感器温度:-" + (sensorToC&0x7F) + "oC");
        }*/

        /*pos = pos + 2 * 1;
        if (SensorADCflag == 0x01) {
            int sensorADC = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
            SysLog.debug("sensorADC: 0x" + sensorADC);

            pos = pos + 2 * 2;
            int sensorADCoffset = StringUtils.hexStringToAlgorism(value.substring(pos, pos + 2 * 2));
            SysLog.debug("sensorADCoffset: 0x" + sensorADCoffset);
            pos = pos + 2 * 2;
        }*/

        return jsonObject;
    }

    /**
     * 上电上报信息
     *
     * @param value
     * @param valueLen
     */
    private static JSONObject powerReportParse(String value, int valueLen) {
        JSONObject jsonObject = new JSONObject();

        if (valueLen != (67 * 2)) {
            jsonObject.put("errorMsg", "信息长度不是67个字节,不符合上电信息长度");
            return jsonObject;
        } else if (valueLen % 2 != 0) {
            jsonObject.put("errorMsg", "信息长度不是偶数,不正确");
            return jsonObject;
        }

        // int valueCRC = StringUtils.hexStringToAlgorism(value.substring(valueLen - 4, valueLen));// CRC长度两个字节
        // jsonObject.put("valueCRC %02x" % valueCRC)
        // 待支持对数据域的CRC校验
        // CRC合法性处理，待支持
        
        /*int DemandVersion = StringUtils.hexStringToAlgorism(value.substring(0, 2));// (int(value[0:2], 16));
        int HardwareVersion = StringUtils.hexStringToAlgorism(value.substring(2, 4));// (int(value[2:4], 16));
        int EmbeddedVersion = StringUtils.hexStringToAlgorism(value.substring(4, 6));// (int(value[4:6], 16));
        jsonObject.put("versionNumber", "版本号：" + DemandVersion + "." + HardwareVersion + "." + EmbeddedVersion);*/

        /*int PartitionNumber = (StringUtils.hexStringToAlgorism(value.substring(6, 8)) >> 7) &0x1;// (int(value[6:8],16) >>7) &0x1;
        if (PartitionNumber == 0) {
            PartitionNumber = "a";
        } else if (PartitionNumber == 1) {
            PartitionNumber = "b";
        } else {
            jsonObject.put("PartitionNumber", "分区号：非法");
            return jsonObject;
        }
        jsonObject.put("PartitionNumber", "分区号："+ PartitionNumber + "分区");*/

        int csq = (StringUtils.hexStringToAlgorism(value.substring(6, 8)) >> 1) &0x3F;
        if (csq == 0x3F) {
            jsonObject.put("errorMsg", "信号质量CSQ:not known or not detectable");
        } else if (csq <= 31) {
            jsonObject.put("signalValue", csq);
        } else {
            /*jsonObject.put("errorMsg", "信号质量CSQ:非法");
            return jsonObject;*/
        }

        /*int APN = (StringUtils.hexStringToAlgorism(value.substring(8, 10)) >> 5) &0x03;
        if (APN == 0) {
            jsonObject.put("APN", "APN: cmnbiot");
        } else if (APN >= 1 && APN <= 7){
            jsonObject.put("APN", "APN: cmnbiot " + APN);
        } else{
            jsonObject.put("APN", "APN: 非法");
            return jsonObject;
        }*/

        /*int activeTime1 = (StringUtils.hexStringToAlgorism(value.substring(8, 10)) &0x1F) << 3;// active_time高5位
        int activeTime2 = (StringUtils.hexStringToAlgorism(value.substring(10, 12)) &0xE0) >> 5;// active_time低3位
        int activeTime = activeTime1 | activeTime2;
        int activeTimeUint = (activeTime & 0xE0) >> 5;// 获得activeTime单位
        int activeTimeSetValue = activeTime & 0x1F;
        if (activeTimeUint == 0){// //2 seconds
            int activeTimeUintValue = 2;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            jsonObject.put("activeTime", "activeTime:" + activeTimeSum + " seconds");
        } else if (activeTimeUint == 1){//://1 minute
            int activeTimeUintValue = 1;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            jsonObject.put("activeTime", "activeTime:" + activeTimeSum + " minutes");
        } else if ( activeTimeUint == 2){// decihours
            int activeTimeUintValue = 6;
            int activeTimeSum = activeTimeSetValue * activeTimeUintValue;
            jsonObject.put("activeTime", "activeTime:" + activeTimeSum + " seconds");
        } else if (activeTimeUint == 7){
            int activeTimeUintValue = 0x70;
            jsonObject.put("activeTime", "activeTime:deactivated");
        } else {
            jsonObject.put("activeTime", "activeTimeUint invalid ");
            return jsonObject;
        }*/

        /*int TAU1 = (StringUtils.hexStringToAlgorism(value.substring(10, 12)) & 0x1F) << 3;// TAU高5位
        int TAU2 = (StringUtils.hexStringToAlgorism(value.substring(12, 14)) & 0xE0) >> 5;// TAU低3位
        int TAU = TAU1 | TAU2;// 按位或运算符：只要对应的二个二进位有一个为1时，结果位就为1。	(a | b) 输出结果 61 ，二进制解释： 0011 1101
        int TAUUint = ( TAU & 0xE0 ) >> 5;// TAU单位
        int TAUSetValue = TAU & 0x1F;
        if (TAUUint == 0){// //10 minutes
            int TAUUintValue = 10;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", "TAU:" + TAUSum + " minutes");
            SysLog.debug("TAU:" + TAUSum + " minutes");
        } else if (TAUUint == 1){// //1 hour
            int TAUUintValue = 1;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", "TAU:" + TAUSum + " hours");
        } else if (TAUUint == 2){// //10 hours
            int TAUUintValue = 10;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", "TAU:" + TAUSum + " hours");
        } else if (TAUUint == 3){// //2 seconds
            int TAUUintValue = 2;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", TAUSum);
        } else if (TAUUint == 4){// //30 seconds
            int TAUUintValue = 30;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", TAUSum);
        } else if (TAUUint == 5){// 1 minute
            int TAUUintValue = 1;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", TAUSum);
        } else if (TAUUint == 6){// //320 hours
            int TAUUintValue = 320;
            int TAUSum = TAUUintValue * TAUSetValue;
            jsonObject.put("TAU", TAUSum);
        } else if (TAUUint == 7){
            int TAUUintValue = 0x70;
            // jsonObject.put("TAU", TAUUintValue);
        }else{
            // jsonObject.put("TAUUint invalid ")
            return jsonObject;
        }*/

        /*int lowBatThreshold1 = (StringUtils.hexStringToAlgorism(value.substring(12, 14)) & 0x1F);// 低电压阈值高5位
        int lowBatThreshold2 = (StringUtils.hexStringToAlgorism(value.substring(14, 16)) & 0xF8) >>3;// 低电压阈值低5位
        int lowBatThreshold = ( ( lowBatThreshold1 << 5 ) | lowBatThreshold2 );
        if ((lowBatThreshold >= 0 ) && (lowBatThreshold <= 1000)) {
            jsonObject.put("", "低电压阈值:%d mV" % (lowBatThreshold * 10));
        } else {
            // jsonObject.put("低电压阈值非法 ");
            return jsonObject;
        }*/

        /*int ecl = (StringUtils.hexStringToAlgorism(value.substring(14, 16))) & 0x03;
        if (ecl > 2) {
            jsonObject.put("覆盖等级ECL非法:%d" % ecl);
            return jsonObject;
        }
        jsonObject.put("覆盖等级", "覆盖等级ECL:" + ecl);*/

        /*int Pci = (StringUtils.hexStringToAlgorism(value.substring(16, 20)));
        if (Pci < 0 || Pci > 503) {
            jsonObject.put("覆盖等级", "物理小区标识PCI非法:%d" % (Pci));
            return jsonObject;
        }
        jsonObject.put("", "物理小区标识PCI:" + Pci);*/

        /*int Snr = (StringUtils.hexStringToAlgorism(value.substring(20, 24)));
        jsonObject.put("Snr", Snr);*/

        /*int rsrpHexStr = StringUtils.hexStringToAlgorism(value.substring(24, 28));// 提取为十六进制的字符串
        int rsrpHexStr = rsrpHexStr[2] + rsrpHexStr[3] + rsrpHexStr[0] + rsrpHexStr[1];// 存储模式变换
        int rsrpNeghex = binascii.unhexlify(rsrpHexStr);// 去十六进制化
        rsrpNeg = struct.unpack('h', rsrpNeghex);
        // SysLog.debug("参考信号接收功率RSRP:%d"%(rsrpNeg[0]));// 负值显示
        jsonObject.put("参考信号接收功率RSRP:%d"%(rsrpNeg[0]));// 负值显示*/

        // int CellId = StringUtils.hexStringToAlgorism(value.substring(28, 36));// (int(value[28:36], 16));
        // jsonObject.put("基站小区标识CELL ID:{}".format(CellId));
        // jsonObject.put("CellId", CellId);

        /*int Earfcn = StringUtils.hexStringToAlgorism(value.substring(36, 44));//(int(value[36:44], 16));
        if (Earfcn < 0 || Earfcn > 262143) {
            SysLog.debug("中心频点EARFCN非法:" + Earfcn);
        }
        jsonObject.put("Earfcn", Earfcn);
        return jsonObject;*/

        /*String DeviceImei = value.substring(44, 59);// 最后一位填补
        SysLog.debug("设备IMEI:" + DeviceImei);
        jsonObject.put("DeviceImei", DeviceImei);*/

        /*String ModuleImei = value.substring(60, 75);// 最后一位填补
        SysLog.debug("模组IMEI:" + ModuleImei);
        jsonObject.put("ModuleImei", ModuleImei);*/

        /*String SN = value.substring(76, 98);
        SysLog.debug("SN：" + SN);
        jsonObject.put("SN", SN);*/

        /*String DeviceIMSI = value.substring(98, 113);// 最后一位填补
        SysLog.debug("设备IMSI:" + DeviceIMSI);
        jsonObject.put("DeviceIMSI", DeviceIMSI);*/

        int batCap = (StringUtils.hexStringToAlgorism(value.substring(114, 116)) & 0xFE) >> 0x01;
        // SysLog.debug("电池电量:" + batCap);
        jsonObject.put("electricQuantity", batCap * 100);

        int SensorADCflag = (StringUtils.hexStringToAlgorism(value.substring(114, 116))) & 0x01;

        int sensorStatus = StringUtils.hexStringToAlgorism(value.substring(116, 118));
        /*if ((sensorStatus & 0x80) == 0x80){
            SysLog.debug("设备超出使用寿命");
            jsonObject.put("deviceLife", 1);
        } else {
            SysLog.debug("设备在使用期限内");
            jsonObject.put("deviceLife", 0);
        }*/

        if ((sensorStatus & 0x40) == 0x40) {
            // SysLog.debug("设备被拆开");
            /*jsonObject.put("eventType", 5);
            return jsonObject;*/
        } else {
            // SysLog.debug("设备未被拆开");
            // jsonObject.put("eventType", 11);
        }

        /*if ((sensorStatus & 0x20) == 0x20) {
            // SysLog.debug("蜂鸣器故障");
            jsonObject.put("buzzer", 1);
        } else {
            // SysLog.debug("蜂鸣器正常");
            jsonObject.put("buzzer", 0);
        }*/

        if ((sensorStatus&0x18) == 0x00) {
            SysLog.debug("传感器正常");
        } else if ((sensorStatus&0x18) == 0x08) {
            SysLog.debug("传感器故障");
            jsonObject.put("eventType", 5);
            return jsonObject;
        } else if ((sensorStatus&0x18) == 0x10) {
            SysLog.debug("传感器免打扰期间");
        } else {
            SysLog.debug("传感器状态异常");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        if ((sensorStatus&0x07) == 0x00) {
            // SysLog.debug("无烟雾报警");
        } else if ((sensorStatus&0x07) == 0x01) {
            // SysLog.debug("烟雾预报警（保留）");
        } else if ((sensorStatus&0x07) == 0x02) {
            // SysLog.debug("烟雾报警");
            jsonObject.put("eventType", 1);
            return jsonObject;
        } else if ((sensorStatus&0x07) == 0x03) {
            // SysLog.debug("烟雾报警静音（测试按键消音）");
        } else if ((sensorStatus&0x07) == 0x04) {
            // SysLog.debug("烟雾报警静音（远程消音）");
        } else {
            SysLog.debug("烟雾报警状态异常");
            jsonObject.put("eventType", 1);
            return jsonObject;
        }

        int sensorStatus1 = StringUtils.hexStringToAlgorism(value.substring(118, 120));
        /*if ((sensorStatus1 & 0xC0) == 0x00) {
            // SysLog.debug("测试模式：无测试");
            jsonObject.put("testMode", 0);
        } else if ((sensorStatus1&0xC0) == 0x40) {
            // SysLog.debug("测试模式：按键测试状态");
            jsonObject.put("testMode", 1);
        } else if ((sensorStatus1&0xC0) == 0x80) {
            // SysLog.debug("测试模式：远程测试状态");
            jsonObject.put("testMode", 2);
        } else {
            // SysLog.debug("测试模式：异常状态");
            jsonObject.put("testMode", 3);
            return jsonObject;
        }*/

        /*if ((sensorStatus1 & 0x30) == 0x00) {
            // SysLog.debug("低压报警状态：电压正常");
            jsonObject.put("voltage", 0);
        } else if ((sensorStatus1&0x30) == 0x10) {
            // SysLog.debug("低压报警状态：低压报警");
            jsonObject.put("voltage", 1);
        } else if ((sensorStatus1&0x30) == 0x20) {
            // SysLog.debug("低压报警状态：低压报警（消音状态）");
            jsonObject.put("voltage", 2);
        } else {
            // SysLog.debug("低压报警状态：异常状态");
            jsonObject.put("voltage", 3);
            return jsonObject;
        }*/

        if ((sensorStatus1 & 0x08) == 0x08) {
            SysLog.debug("传感器无响应，失联");
            jsonObject.put("eventType", 5);
            return jsonObject;
        }

        /*if ((sensorStatus1 & 0x07) == 0x00) {
            // SysLog.debug("蜂鸣器当前发出的声音 ：静音");
            jsonObject.put("buzzerVoice", 0);
        } else if ((sensorStatus1 & 0x07) == 0x01) {
            // SysLog.debug("蜂鸣器当前发出的声音 ：故障");
            jsonObject.put("buzzerVoice", 1);
        } else if ((sensorStatus1 & 0x07) == 0x02) {
            // SysLog.debug("蜂鸣器当前发出的声音 ：报警");
            jsonObject.put("buzzerVoice", 2);
        } else if ((sensorStatus1 & 0x07) == 0x07) {
            // SysLog.debug("蜂鸣器当前发出的声音 ：蜂鸣器故障");
            jsonObject.put("buzzerVoice", 7);
        } else {
            // SysLog.debug("蜂鸣器当前发出的声音 ：状态非法");
            jsonObject.put("buzzerVoice", 8);
            return jsonObject;
        }*/

        /*int sensorToC = StringUtils.hexStringToAlgorism(value.substring(120, 122));
        if (sensorToC == 0xFF) {
            // SysLog.debug("传感器不支持温度功能");
            jsonObject.put("", "传感器不支持温度功能");
        } else if ((sensorToC & 0x80) == 0x00){
            // SysLog.debug("传感器温度:" + (sensorToC & 0x7F) + "oC");
            jsonObject.put("", "传感器温度:" + (sensorToC & 0x7F) + "oC");
        }else {
            // SysLog.debug("传感器温度:-" + (sensorToC & 0x7F) + "oC");
            jsonObject.put("", "传感器温度:-" + (sensorToC & 0x7F) + "oC");
        }*/

        /*if (SensorADCflag == 0x01) {
            String sensorADC = value.substring(122, 126);
            // SysLog.debug("sensorADC: 0x" + sensorADC);
            jsonObject.put("sensorADC", "sensorADC: 0x{}".format(sensorADC));

            String sensorADCoffset = value.substring(126, 130);
            // SysLog.debug("sensorADCoffset: 0x{}".format(sensorADCoffset));
            jsonObject.put("sensorADCoffset", "sensorADCoffset: 0x" + sensorADCoffset);
        }*/
                
        return jsonObject;
    }

}
