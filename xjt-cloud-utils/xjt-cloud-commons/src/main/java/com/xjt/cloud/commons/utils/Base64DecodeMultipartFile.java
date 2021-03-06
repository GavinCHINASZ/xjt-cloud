package com.xjt.cloud.commons.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片Base64转MultipartFile工具类
 *
 * @author wangzhiwen
 * @date 2019/12/18 18:04
 */
public class Base64DecodeMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public Base64DecodeMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    /**
     * base64转multipartFile
     *
     * @param base64 String
     * @return MultipartFile
     */
    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(",");

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(baseStrs[1]);
        } catch (IOException e) {
            SysLog.error(e);
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64DecodeMultipartFile(b, baseStrs[0]);
    }


    /**
     * base64转BufferedImage
     *
     * @param base64 String
     * @return BufferedImage
     */
    public static BufferedImage base64ToBufferedImage(String base64) {
        try {
            MultipartFile f = Base64DecodeMultipartFile.base64Convert(base64);
            ByteArrayInputStream in = (ByteArrayInputStream) f.getInputStream();
            // 获取底图
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MultipartFile转base64
     *
     * @param multipartFile MultipartFile
     * @return BufferedImage
     */
    public static BufferedImage MultipartFileToBase64(MultipartFile multipartFile) {
        byte[] fileByte;
        try {
            fileByte = multipartFile.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(fileByte);
            return ImageIO.read(bais);
        } catch (IOException e) {
            return null;
        }
    }
}