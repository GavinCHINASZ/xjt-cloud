package com.xjt.cloud.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * IO工具类
 *
 * @author huanggc
 * @date 2021/01/05
 */
public class IoUtils {

    /**
     * 根据 输入流 返回 byte数组
     *
     * @param inputStream 输入流
     * @throws Exception Exception
     * @date 2021/01/05
     * @return byte[]
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inputStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        inputStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }
}
