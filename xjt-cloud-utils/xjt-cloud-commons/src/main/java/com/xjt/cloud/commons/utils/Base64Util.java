package com.xjt.cloud.commons.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base64工具类
 *
 * @author huanggc
 * @date 2021/04/20
 */
public class Base64Util {

    /**
     * 字符串转图片
     *
     * @param base64Str
     * @return byte[]
     */
    public static byte[] decode(String base64Str) {
        byte[] b = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            b = decoder.decodeBuffer(replaceEnter(base64Str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 图片转字符串
     *
     * @param image byte[]
     * @return String
     */
    public static String encode(byte[] image) {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    /**
     * @param uri
     * @return String
     */
    public static String encode(String uri) {
        BASE64Encoder encoder = new BASE64Encoder();
        return replaceEnter(encoder.encode(uri.getBytes()));
    }

    /**
     * @param path 图片路径
     * @return byte[]
     */
    public static byte[] imageTobyte(String path) {
        byte[] data = null;
        FileImageInputStream input;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 正则
     *
     * @param str
     * @return String
     */
    public static String replaceEnter(String str) {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 根据图片的路径转Base64
     *
     * @param imgFile 图片本地存储路径
     * @return String
     * @author huanggc
     */
    public static String getImgFileToBase64(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] buffer = null;
        // 读取图片字节数组
        try {
            inputStream = new FileInputStream(imgFile);
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (buffer != null) {
            // 对字节数组Base64编码
            return new BASE64Encoder().encode(buffer);
        } else {
            return "";
        }
    }
}