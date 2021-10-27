package com.xjt.cloud.netty.manage.common.utils;

/**
 * @ClassName StringToByteArr
 * @Author dwt
 * @Date 2019-12-27 13:56
 * @Version 1.0
 */
public class StringToByteArr {
    /**
     *@Author: dwt
     *@Date: 2019-12-27 13:57
     *@Param: java.lang.String
     *@Return: byte[]
     *@Description 字符串转byte数组
     */
    public static byte[] getByteArr(String msg){
        byte[] bs = new byte[msg.length()/2];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) Integer.parseInt(msg.substring(i*2, i*2+2), 16);
        }
        return bs;
    }

    /**
     *
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] hexStringToBinary(String hexString, int length) {
        // hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;// 字节高四位
        byte low = 0;// 字节低四位
        String hexStr = "0123456789ABCDEF";
        for (int i = 0; i < len; i++) {
            // 右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);// 高地位做或运算
        }
        return bytes;
    }
}
