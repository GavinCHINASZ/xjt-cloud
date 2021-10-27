package com.xjt.cloud.commons.sign;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.xjt.cloud.commons.utils.SysLog;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * 生成图片
 *
 * @author huanggc
 * @date 2021/05/13
 */
public class SignImage {

    /**
     * @param country           国家
     * @param certificateLevel  证书等级
     * @param userName          签名日期
     * @param certificateNumber 证书编号
     * @param jpgName           路径+图片名
     * @return boolean
     * @author huanggc
     * @date 2021/05/13
     */
    public static boolean createSignTextImg(String country, String certificateLevel, String userName, String certificateNumber, String jpgName) {

        //FileOutputStream out = null;
        try {
            // (String 字体，int 风格，int 字号)
            Font baseFont = new Font("黑体", Font.BOLD, 16);
            Font otherTextFont = new Font("黑体", Font.BOLD, 22);

            int width = 166;
            int height = 82;
            // 宽度 高度
            BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = bimage.createGraphics();
            // 背景色
            /*Color bgcolor = Color.WHITE;
            graphics2D.setColor(bgcolor);*/

            // 1.0f为透明度 ，值从0-1.0，依次变得不透明
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0f));
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            /*bimage = graphics2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            graphics2D.dispose();
            graphics2D = bimage.createGraphics();*/

            // 画一个矩形
            graphics2D.fillRect(0, 0, width, height);
            // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 字的颜色
            Color fontcolor = new Color(215, 80, 97);
            graphics2D.setColor(fontcolor);
            graphics2D.fillRect(0, 0, 2, height);
            graphics2D.fillRect(0, 0, width, 2);
            graphics2D.fillRect(0, height - 2, width, height);
            graphics2D.fillRect(width - 2, 0, width, height);
            // 字的颜色
            graphics2D.setColor(fontcolor);

            // 字体字形字号
            graphics2D.setFont(baseFont);
            FontMetrics fm = FontDesignMetrics.getMetrics(baseFont);
            int font1Hight = fm.getHeight();
            int strWidth = fm.stringWidth(country);
            int y = 17;
            int x = (width - strWidth) / 2;
            // 在指定坐标除添加文字
            graphics2D.drawString(country, x, y);
            // 字体字形字号
            graphics2D.setFont(baseFont);

            fm = FontDesignMetrics.getMetrics(baseFont);
            int font2Hight = fm.getHeight();
            strWidth = fm.stringWidth(certificateLevel);
            x = (width - strWidth) / 2;
            graphics2D.drawString(certificateLevel, x, y + font1Hight);

            fm = FontDesignMetrics.getMetrics(otherTextFont);
            int font3Hight = fm.getHeight();
            strWidth = fm.stringWidth(userName);
            x = (width - strWidth) / 2;
            graphics2D.setFont(otherTextFont);
            graphics2D.drawString(userName, x, y + font1Hight + font2Hight + 4);

            fm = FontDesignMetrics.getMetrics(baseFont);
            strWidth = fm.stringWidth(certificateNumber);
            x = (width - strWidth) / 2;
            graphics2D.setFont(baseFont);
            graphics2D.drawString(certificateNumber, x - 1, y + font1Hight + font2Hight + font3Hight - 4);

            //graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            //bimage = graphics2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            graphics2D.dispose();
            //graphics2D = bimage.createGraphics();
            // 指定输出文件
            //out = new FileOutputStream(jpgName);

            //int temp = jpgName.lastIndexOf(".") + 1;
            transferAlpha(bimage, jpgName);

            //ImageIO.write(bimage, jpgName.substring(temp), new File(jpgName));
            /*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(50f, true);
            // 存盘
            encoder.encode(bimage, param);
            out.flush();*/

            return true;
        } catch (Exception e) {
            SysLog.error(e);
            return false;
        } /*finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    SysLog.error(e);
                }
            }
        }*/
    }

    /**
     * 图片背景色(透明)
     *
     * @param image 图片
     * @param path  路径
     * @return byte[]
     */
    private static byte[] transferAlpha(Image image, String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;

            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }

                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            ImageIO.write(bufferedImage, "png", new File(path));
        } catch (Exception e) {
            SysLog.error(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) {
        //String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;

        String url = "E:\\logs\\";
        String certificateNumber = "1440000798";
        createSignTextImg("中华人民共和国", "一级注册消防工程师", "黄 桂 明", "" + "14418000097",
                url + "sign.png");

    }
}
