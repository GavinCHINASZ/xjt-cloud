package com.xjt.cloud.commons.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 图片工具类
 *
 * @author wangzhiwen
 * @date 2019/7/26 09:49
 */
public class ImgUtils {
    /**
     * 功能描述: 以内容与logo生成二维码
     *
     * @param content
     * @param logoPath
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/26 11:50
     */
    public static String generateQRCode(String content, String logoPath) {
        try {
            int marginWidth = 8;
            int margin = 8;
            // 图像宽度
            int width = 260;
            // 图像高度
            int height = 260;
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 设置白边
            hints.put(EncodeHintType.MARGIN, margin);
            //容错比例，30%最高
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MIN_SIZE, 100);
            // 生成矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int matrixWidth = bitMatrix.getWidth();

            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_ARGB);
            image.createGraphics();

            // 二维码内容
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            Color bg = new Color(0, 0, 0, 0);
            graphics.setBackground(bg);
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);
            Color fillColor = new Color(0, 0, 0);

            graphics.setColor(fillColor);
            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (bitMatrix.get(i, j) == true) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            //得到当前项目静态资源文件绝对路径，代码中的resources下
            String path = System.getProperty("user.dir");
            File file = new File(path + logoPath);
            BufferedImage logo = ImageIO.read(file);
            int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();

            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;

            // 开始绘制图片
            graphics.setColor(Color.WHITE);
            graphics.clearRect(x, y, widthLogo, heightLogo);
            graphics.drawImage(logo, x, y, widthLogo, heightLogo, null);
            graphics.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            graphics.setStroke(new BasicStroke(0));
            graphics.drawRect(x, y, widthLogo, heightLogo);
            graphics.dispose();
            // 插入LOGO
            graphics.drawImage(logo, x, y, widthLogo, heightLogo, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graphics.setStroke(new BasicStroke(3f));
            graphics.draw(shape);
            graphics.dispose();

            image = image.getSubimage(marginWidth, marginWidth, width - marginWidth * 2, height - marginWidth * 2);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            String imgStr = Base64.encodeBase64String(bytes);
            return imgStr;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        throw BaseServiceException.initException(ConstantsMsg.LOGIN_QR_NO_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述:生成验证码图片
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/13 16:48
     */
    public static String generateCaptcha(String content) {
        try {
            int width = 200;
            int height = 69;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            //生成对应宽高的初始图片
            drawRandomText(width, height, verifyImg, content);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(verifyImg, "png", baos);
            byte[] bytes = baos.toByteArray();
            String imgStr = Base64.encodeBase64String(bytes);
            return imgStr;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        throw BaseServiceException.initException(ConstantsMsg.LOGIN_CAPTCHA_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述:生成验证码图片内容
     *
     * @param width
     * @param height
     * @param verifyImg
     * @param content
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/13 17:00
     */
    private static void drawRandomText(int width, int height, BufferedImage verifyImg, String content) {
        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));

        //旋转原点的 x 坐标
        int x = 10;
        char[] contentCh = content.toCharArray();

        String ch;
        Random random = new Random();
        for (int i = 0; i < contentCh.length; i++) {
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            //角度小于30度
            int degree = random.nextInt() % 30;
            ch = contentCh[i] + "";
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }

        //画干扰线
        for (int i = 0; i < 36; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        //添加噪点
        for (int i = 0; i < 72; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    /**
     * url获取图片Base64字符串
     *
     * @author huanggc
     * @param url url
     * @return String Base64字符串
     */
    public static String getBase64ByImgUrl(String url) {
        if (StringUtils.isEmpty(url)){
            return "";
        }

        String suffix = url.substring(url.lastIndexOf(".") + 1);
        try {
            URL urls = new URL(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Image image = Toolkit.getDefaultToolkit().getImage(urls);
            BufferedImage biOut = toBufferedImage(image);
            ImageIO.write(biOut, suffix, baos);
            return Base64Util.encode(baos.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 图片 转 流
     *
     * @author huanggc
     * @param image 图片
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
            SysLog.error("BufferedImage 图片转流失败---->" + e);
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     * 通过图片的url获取图片的base64字符串
     *
     * @param imgUrl 图片url
     * @return 返回图片base64的字符串
     */
    public static String image2Base64(String imgUrl) {
        URL url;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;

        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();

            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];

            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;

            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return Base64Util.encode(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    public static void main(String[] args) {
        String url = "http://192.168.0.200:6020/checkrecord/2021/04/09/1617946918555.jpeg";
        String base64ByImgUrl = getBase64ByImgUrl(url);
        System.out.println(base64ByImgUrl);
    }
}
