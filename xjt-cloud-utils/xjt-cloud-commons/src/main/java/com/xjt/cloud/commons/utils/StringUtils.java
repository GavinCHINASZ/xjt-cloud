package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MTA 系统 字符串工具类
 *
 * @author yaoyuan
 */
public class StringUtils {
    public static long sequenctNo = 0;
    // 会员编号中的流水号
    public static long memCodeSequenceNo = 0;
    // 会员卡号中的流水号
    public static long cardNo = 0;

    public static long getCardNo() {
        return cardNo;
    }

    public static void setCardNo(long cardNo) {
        StringUtils.cardNo = cardNo;
    }

    public static long getMemCodeSequenceNo() {
        return memCodeSequenceNo;
    }

    public static void setMemCodeSequenceNo(long memCodeSequenceNo) {
        StringUtils.memCodeSequenceNo = memCodeSequenceNo;
    }

    public static long getSequenctNo() {
        return sequenctNo;
    }

    public static void setSequenctNo(long sequenctNo) {
        StringUtils.sequenctNo = sequenctNo;
    }

    /**
     * 取得最近三月的时间
     *
     * @return Date
     * @author qiaoliang
     */
    @SuppressWarnings("unused")
    private Date getLast3Month() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 3);
        Date date = new Date(calendar.getTime().getTime());
        return date;
    }

    /**
     * 将字符串数组拼装成用","隔开的字符串,
     *
     * @param array 需要拼装的数组
     * @return 需要返回的字符串
     */
    public static String getLinkString(String[] array) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != array && array.length > 0) {
            for (String s : array) {
                sb.append(s).append(",");
            }
            str = sb.toString();
        }
        if (str.indexOf(",") != -1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 将字符串数组拼装成用","隔开的字符串,
     *
     * @param array 需要拼装的数组
     * @return 需要返回的字符串
     */
    public static String getLinkString(Object[] array) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != array && array.length > 0) {
            for (Object s : array) {
                sb.append(s.toString()).append(",");
            }
            str = sb.toString();
        }
        if (str.indexOf(",") != -1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


    /**
     * 将字符串List拼装成用"'1','2','3'"隔开的字符串,
     *
     * @param strArray 需要拼装的List
     * @return 需要返回的字符串
     * @author yaoyuan
     */
    public static String getSqlInClauseByList(String[] strArray) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != strArray && strArray.length > 0) {
            for (String s : strArray) {
                sb.append("'");
                sb.append(s);
                sb.append("'");
                sb.append(",");
            }
            str = sb.toString();
        }
        if (str.indexOf(",") != -1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 判断字符是否有内容
     *
     * @param src String
     * @return boolean
     */
    public static boolean isEmpty(String src) {
        return (src == null || "".equals(src) || "".equals(src.trim()) || "null".equals(src.trim()));
    }

    /**
     * @param src String
     * @return boolean
     */
    public static boolean isNotEmpty(String src) {
        return !isEmpty(src);
    }

    /**
     * 字符串转码方法
     *
     * @param args 需转码的字符串
     * @return String 已转码的字和符串
     */
    public static String getDecoding(String args) {
        try {
            return new String(args.getBytes(Constants.CHAR_SET), Constants.UTF_CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
        }
        return "";
    }

    /**
     * 将数字格式化为指定的格式的字符 例如pattern为“00000000”，number为1 则返回00000001
     *
     * @param pattern 格式模板
     * @param number  需要格式的数字
     * @return String 格式化后的字符
     * @author 乔鹏程
     */
    public static String formatNumber(String pattern, long number) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    /**
     * @param str String
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String decodingFromISO8858_1(String str) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return new String(str.getBytes("ISO8859-1"));
    }

    /**
     * str中查找出特定标签(${xxx},<b>xxx</b>)的集合 2008-10-25
     *
     * @param content  String
     * @param startTag 查找的开始标签${,<b>
     * @param endTag   结束的标签},</b>
     * @return List<String>
     */
    public static List<String> stringToListByWrod(String content, String startTag, String endTag) {
        List<String> contentList = new ArrayList<>();
        if (content != null) {
            // 查找第一个起始的startTag位置
            int beginInt = content.indexOf(startTag);

            while (beginInt != -1) {
                // 找结束的标签是否存在
                int endInt = content.indexOf(endTag, beginInt);
                // 结束标签不存在把beginInt加1
                if (endInt == -1) {
                    break;
                }

                String elementStr = content.substring(beginInt, content.indexOf(endTag, beginInt) + endTag.length());
                contentList.add(elementStr);
                // endTag.length()会查询<b>author</ab>:yaoyuan<b>s</b>子元素<b>s</b>,暂不需要
                beginInt = content.indexOf(startTag, beginInt + elementStr.length());
            }

        }
        return contentList;
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return boolean 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线
     *
     * @param str String
     * @return String
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 字符解码（UTF-8）解码
     * Dec 1, 2008
     *
     * @param src String
     * @return String
     * @author 黄豆豆
     */
    public static String urlDecode(String src) {
        try {
            return URLDecoder.decode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
            return "";
        }
    }

    /**
     * 直接出现在URL中要进行编码,转码
     *
     * @date 2008-12-23
     * @param src String
     * @return String
     * @author yaoyuan
     */
    public static String urlEncode(String src) {
        try {
            return URLEncoder.encode(src, "UTF-8");
        } catch (Exception e) {
            SysLog.error(e);
            return "";
        }
    }

    /**
     * 把数组转换成IN条件所需要的字符串
     *
     * @param array String[]
     * @return String
     * @date 2009-1-17
     * @author yaoyuan
     */
    public static String getLinkStringForIn(String[] array) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != array && array.length > 0) {
            for (String s : array) {
                sb.append("'").append(s).append("'").append(",");
            }
            str = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return str;
    }

    /**
     * 把数组转换成IN条件所需要的字符串
     *
     * @param array Object[]
     * @return String
     * @date 2009-1-17
     * @author yaoyuan
     */
    public static String getLinkStringForIn(Object[] array) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (null != array && array.length > 0) {
            for (Object s : array) {
                sb.append("'").append(s.toString()).append("'").append(",");
            }
            str = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return str;
    }

    /**
     * 取得问号 如 "?,?,?"
     * 这个方法没有考虑超过1000的情况，请使用getPlaceHoldersForIn(String,int)代替
     *
     * @param paramsCount 需要的问号的个数
     * @return String
     * @date 2009-2-11
     * @author yaoyuan
     */
    public static String getPlaceHoldersForIn(int paramsCount) {
        if (paramsCount <= 0) {
            return null;
        }

        if (paramsCount == 1) {
            return "?";
        }

        StringBuilder sb = new StringBuilder(paramsCount * 2 - 1);
        sb.append("?");
        for (int i = 1; i < paramsCount; i++) {
            sb.append(",?");
        }
        return sb.toString();
    }

    private static final int MAX_PLACE_HOLDER_COUNT = 999;

    /**
     * 取得问号 如 "a.organizationId in (?,?,?)" 或 "a.organizationId in (?,?,?) or a.organizationId in (?,?,?)"
     *
     * @param propertyName 属性名
     * @param paramsCount  需要的问号的个数
     * @return String
     * @date 2010-3-16
     * @author yaoyuan
     */
    private static String getPlaceHoldersForIn2(String propertyName, int paramsCount) {
        if (paramsCount <= MAX_PLACE_HOLDER_COUNT) {
            return propertyName + " in (" + getPlaceHoldersForIn(paramsCount) + ")";
        } else {
            return getPlaceHoldersForIn2(propertyName, MAX_PLACE_HOLDER_COUNT) + " or " + getPlaceHoldersForIn2(propertyName, paramsCount - MAX_PLACE_HOLDER_COUNT);
        }
    }

    /**
     * 取得问号 如 "a.organizationId in (?,?,?)" 或 "(a.organizationId in (?,?,?) or a.organizationId in (?,?,?))"
     * eg: sql += "and " + getPlaceHoldersForIn("t.organization_id", orgIds.length) ;
     * 2010-3-16
     *
     * @param propertyName 属性名
     * @param paramsCount  需要的问号的个数
     * @return String
     * @author yaoyuan
     */
    public static String getPlaceHoldersForIn(String propertyName, int paramsCount) {
        if (paramsCount <= MAX_PLACE_HOLDER_COUNT) {
            return propertyName + " in (" + getPlaceHoldersForIn(paramsCount) + ")";
        } else {
            return "(" + getPlaceHoldersForIn2(propertyName, paramsCount) + ")";
        }
    }

    /**
     * 取得当前操作系统的行分割符
     *
     * @return String
     * @date 2009-5-13
     * @author yaoyuan
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * 判断 String 是否为数字
     *
     * @param val String
     * @return boolean
     * @date 2009-8-20
     * @author yaoyuan
     */
    public static boolean isNumber(String val) {
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            SysLog.error(e);
            return false;
        }
    }

    /**
     * 将iso-88591编码转换成utf8格式
     *
     * @param strSrc String
     * @return String
     * @author LQY
     * @date 2011-5-6
     */
    public static String convert88591Toutf8(String strSrc) {
        try {
            return new String(strSrc.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * 字符串 转换成 十六进制 字符串
     *
     * @param s String
     * @return String
     */
    public static String toStringHex(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                SysLog.error(e);
            }
        }

        try {
            // UTF-16le:Not
            s = new String(baKeyword, "utf-8");
        } catch (Exception e) {
            SysLog.error(e);
        }
        return s;
    }

    /**
     * 功能描述:bytes转成16进制字符串
     *
     * @param bArray byte[]
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/5/5 0005 14:11
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 二进制转字符串
     *
     * @param b byte[]
     * @return String
     */
    public static String byte2hex(byte[] b, int... len) {
        StringBuilder sb = new StringBuilder();
        String stmp = "";
        int blen = len.length == 0 ? b.length : len[0];
        for (int n = 0; n < blen; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串 转 二进制
     *
     * @param str 要转换的字符串
     * @return 转换后的二进制数组
     */
    public static byte[] hex2byte(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1) {
            return null;
        }
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            SysLog.error(e);
            return null;
        }
    }

    /**
     * 16进制 十六进制字符串 转 十进制数 10进制
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     * @author dwt
     * @date 2019-05-17 14:04
     */
    public static int hexStringToAlgorism(String hex) {
        if(StringUtils.isEmpty(hex)){
            return -1;
        }

        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        int algorism = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }

    /**
     * 10进制 转 16进制
     *
     * @param numb 要转换的数字
     * @param dataLength 最后要返回的字符串长度
     * @return String
     */
    public static String encodeHEX(Integer numb, int dataLength){
        String string = Integer.toHexString(numb);
        if (dataLength > 0 && string.length() < dataLength){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dataLength - string.length(); i++) {
                sb.append("0");
            }
            sb.append(string);
            return sb.toString();
        }

        if (dataLength > 0 && string.length() > dataLength){
            // 保留后 dataLength 位
            return string.substring(string.length() - dataLength);
        }
        return string;
    }

    /**
     * 字符串转数字
     *
     * @param str 字符串
     * @return 十进制数值
     * @author dwt
     * @date 2019-05-17 14:04
     */
    public static int stringToInt(String str) {
        if(StringUtils.isEmpty(str)){
            return 0;
        }

        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return 0;
        }
    }

    public static void main(String[] args) {
        // 40 11030000000130040000000000 08 03 00 00 00 00 14 01 00 0B FF 00 01 FF FF FF FF FF FF 01 01 70 23
        String msg = "40 11030000000130040000000000 08 03 00 00 00 00 14 01 00 0B FF 00 01 FF FF FF FF FF FF 01 01 70 23".replace(" ", "");
        msg = msg.substring(2, msg.length() -4);
        String[] bs = string2StringArr(msg, 2);

        int a = 0;
        for (int i = 0; i < bs.length; i++) {
            a += hexStringToAlgorism(bs[i]);
        }

        String s = encodeHEX(a, 2);
        System.out.println(s);
    }

    /**
     * 字符串转byte数组
     *
     * @param msg java.lang.String
     * @return byte[]
     * @author dwt
     * @date 2019-12-27 13:57
     */
    public static byte[] getByteArr(String msg) {
        byte[] bs = new byte[msg.length() / 2];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) Integer.parseInt(msg.substring(i * 2, i * 2 + 2), 16);
        }
        return bs;
    }

    /**
     * @param str String
     * @return String
     */
    public static String hexToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\\\\\x[0-9A-Fa-f]{2})+");
        Matcher matcher = pattern.matcher(str);
        Map<String, String> result = new TreeMap<>();
        List<String> listKey = new LinkedList<>();
        while (matcher.find()) {
            String group = matcher.group(0);
            String groupReplace = group.replaceAll("\\\\\\\\x", "");
            // 每两个字符为一个十六进制确定数字长度
            byte[] b = new byte[groupReplace.length() / 2];
            for (int i = 0; i < b.length; i++) {
                // 将字符串每两个字符做为一个十六进制进行截取
                String a = groupReplace.substring(i * 2, i * 2 + 2);
                // 将如e4转成十六进制字节，放入数组
                b[i] = (byte) Integer.parseInt(a, 16);
            }
            try {
                // 将字节数字以utf-8编码以字符串形式输出
                String ccc = new String(b, "UTF-8");
                result.put(ccc, group);
                listKey.add(ccc);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (listKey != null && listKey.size() > 0) {
            listKey.sort(Comparator.reverseOrder());
            for (String key : listKey) {
                String value = result.get(key);
                str = str.replace(value, key);
            }
        }
        return str;
    }

    /**
     * 十六进制字符串 转 十进制字符串
     *
     * @param hexStr 十六进制字符串
     * @return 十进制数值
     * @author dwt
     * @date 2019-05-17 14:04
     */
    public static String hexToDecimal(String hexStr) {
        hexStr = hexStr.toUpperCase();
        int sum = 0;
        int m = 0;
        int num = 0;
        for (int i = 0; i < hexStr.length(); i++) {
            m = hexStr.charAt(i);
            num = m >= 'A' ? m - 'A' + 10 : m - '0';
            sum += Math.pow(16, hexStr.length() - 1 - i) * num;
        }
        return sum + "";
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str String
     * @return str
     * @author dwt
     * @date 2019-05-31 15:17
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 为需要生成CRC校验码的16进制字符串生成低位在左高位在右的两个字节码字符串
     *
     * @param str java.lang.String
     * @return java.lang.String
     * @author dwt
     * @date 2019-05-17 16:57
     */
    public static String Make_CRC(String str) {
        byte[] bs = new byte[str.length() / 2];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        // 存储需要产生校验码的数据
        byte[] buf = new byte[bs.length];
        for (int i = 0; i < bs.length; i++) {
            buf[i] = bs[i];
        }
        int len = buf.length;
        // 16位
        int crc = 0xFFFF;
        for (int pos = 0; pos < len; pos++) {
            if (buf[pos] < 0) {
                // XOR byte into least sig. byte of
                crc ^= (int) buf[pos] + 256;
                // crc
            } else {
                crc ^= (int) buf[pos]; // XOR byte into least sig. byte of crc
            }
            for (int i = 8; i != 0; i--) { // Loop over each bit
                if ((crc & 0x0001) != 0) { // If the LSB is set
                    crc >>= 1; // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else {
                    // Else LSB is not set
                    crc >>= 1; // Just shift right
                }
            }
        }
        String c = Integer.toHexString(crc);
        if (c.length() == 4) {
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2) + "0" + c.substring(0, 1);
        }
        return c;
    }

    /**
     * 16进制字符串转byte数组
     *
     * @param src String
     * @return byte[]
     * @author dwt
     * @date 2019-06-06 9:20
     */
    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 字符串转字符串数组
     *
     * @param src String
     * @param dateNum 取几个字符
     * @return String[]
     * @author huanggc
     * @date 2021-04-16
     */
    public static String[] string2StringArr(String src, int dateNum) {
        String[] bs = new String[src.length() / dateNum];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = src.substring(i * 2, i * 2 + dateNum);
        }
        return bs;
    }

    /**
     * byte数组转换16进制字符串
     *
     * @param by byte[]
     * @return String
     * @author dwt
     * @date 2019-06-06 9:22
     */
    public static String receiveHexToString(byte[] by) {
        try {
 			/*io.netty.buffer.WrappedByteBuf buf = (WrappedByteBuf)msg;
 			ByteBufInputStream is = new ByteBufInputStream(buf);
 			byte[] by = input2byte(is);*/
            String str = bytes2Str(by);
            if (StringUtils.isNotEmpty(str)) {
                str = str.toLowerCase();
            }
            return str;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        return null;
    }

    /**
     * 转换16进制数
     *
     * @param src byte[]
     * @return String
     * @author dwt
     * @date 2019-06-06 9:23
     */
    public static String bytes2Str(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 判断是否是16进制
     *
     * @param str String
     * @return boolean
     * @author dwt
     * @date 2019-06-05 19:13
     */
    public static boolean isHexNumber(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            char cc = str.charAt(i);
            if (cc == '0' || cc == '1' || cc == '2' || cc == '3' || cc == '4' || cc == '5' || cc == '6' || cc == '7' || cc == '8' || cc == '9'
                    || cc == 'A' || cc == 'B' || cc == 'C' ||
                    cc == 'D' || cc == 'E' || cc == 'F' || cc == 'a' || cc == 'b' || cc == 'c' || cc == 'd' || cc == 'e' || cc == 'f') {
                flag = true;
            } else {
                return false;
            }
        }
        return flag;
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否含有乱码字符
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isContainErrorCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        boolean flag = false;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (Character.isLetterOrDigit(c)) {
                continue;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述: map.toString 字符串转json
     *
     * @param str String
     * @return JSONObject
     * @author wangzhiwen
     * @date 2019/7/23 10:43
     */
    public static JSONObject mapStringToJson(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("{");
        for (String string : strs) {
            jsonStr.append("\"" + string.split("=")[0] + "\":\"" + string.split("=")[1] + "\",");
        }
        JSONObject json = JSONObject.parseObject(jsonStr.substring(0, jsonStr.length() - 1) + "}");
        return json;
    }

    /**
     * 功能描述: 按长度生成字母加数字的随机字符串
     *
     * @param size int
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/7/31 18:11
     */
    public static String generateRanStr(int size) {
        String loginStr = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder loginName = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            loginName.append(loginStr.charAt(rand.nextInt(36)));
        }
        return loginName.toString();
    }

    /**
     * 功能描述: 按长度生成字母加数字的随机字符串
     *
     * @param size int
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/7/31 18:11
     */
    public static String generateRanNum(int size) {
        Random rand = new Random();
        StringBuffer loginName = new StringBuffer();
        for (int i = 0; i < size; i++) {
            loginName.append(rand.nextInt(10));
        }
        return loginName.toString();
    }

    /**
     * 判断字符串是否符合指定的日期格式;默认格式：yyyy/MM/dd HH:mm:ss
     *
     * @param str       java.lang.String
     * @param formatStr java.lang.String
     * @return java.lang.Boolean
     * @author dwt
     * @date 2019-08-29 18:04
     */
    public static boolean isValidDate(String str, String formatStr) {
        if (StringUtils.isEmpty(formatStr)) {
            formatStr = "yyyy/MM/dd HH:mm:ss";
        }
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 根据CRC16/XMODEM协议,计算得到16进制字符串的4位校验码
     *
     * @param src java.lang.String
     * @return java.lang.String
     * @author dwt
     * @date 2019-09-05 11:38
     */
    public static String getCRC16CheckCode(String src) {
        byte[] ret = hexString2Bytes(src);
        // initial value
        int crc = 0x00;
        int polynomial = 0x1021;
        for (int index = 0; index < ret.length; index++) {
            byte b = ret[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        return Integer.toHexString(crc).toUpperCase();
    }

    /**
     * makeChecksum 校验CRC16
     *
     * @param data String
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2019/10/14 14:43
     **/
    public static String makeChecksum(String data) {
        if (data == null || data.equals("")) {
            return "";
        }

        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        // 用256求余最大是255，即16进制的FF
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        // 如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }


    /**
     * 字符串 转成为 16进制字符串
     *
     * @param s String
     * @return String
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 获取爱德华交互信息校验码 高位在前 低位在后
     *
     * @param str java.lang.String
     * @return java.lang.String
     * @author dwt
     * @date 2019-12-25 10:32
     */
    public static String Make_Edward_CRC(String str) {
        byte[] bs = new byte[str.length() / 2];
        for (int i = 0; i < bs.length; ++i) {
            bs[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        String crc = Integer.toHexString(CRC16_IBM(bs, bs.length)).toUpperCase();
        if (crc.length() == 1) {
            crc = "000" + crc;
        } else if (crc.length() == 2) {
            crc = "00" + crc;
        } else if (crc.length() == 3) {
            crc = "0" + crc;
        }
        return crc;
    }

    /**
     * CRC-16/IBM
     *
     * @param source byte[]
     * @param length int
     * @return int
     */
    public static int CRC16_IBM(byte[] source, int length) {
        int wCRCin = 0x0000;
        // Integer.reverse(0x8005) >>> 16
        int wCPoly = 0xA001;
        for (int i = 0, cnt = length; i < cnt; i++) {
            wCRCin ^= ((int) source[i] & 0x00FF);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= 0x0000;
    }

    /**
     * 判断source字符串是否是以prefix开始
     *
     * @param source String
     * @param prefix String
     * @return boolean
     * @author wangzhiwen
     * @date 2020/12/3 15:18
     */
    public static boolean startsWith(String source, String prefix) {
        if (isEmpty(source) || isEmpty(prefix) || !source.startsWith(prefix)) {
            return false;
        }
        return true;
    }

    /**
     * 判断source字符串是否是以suffix结束
     *
     * @param source String
     * @param suffix String
     * @return boolean
     * @author wangzhiwen
     * @date 2020/12/3 15:18
     */
    public static boolean endsWith(String source, String suffix) {
        if (isEmpty(source) || isEmpty(suffix) || !source.endsWith(suffix)) {
            return false;
        }
        return true;
    }
}
