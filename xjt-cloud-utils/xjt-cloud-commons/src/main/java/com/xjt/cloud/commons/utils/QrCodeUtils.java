package com.xjt.cloud.commons.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * 二维码 工具类
 * 
 * @author wangzhiwen
 * @date 2019/8/19 17:12
 */
public class QrCodeUtils {

    /**
     * 功能描述:切圆角
     *
     * @param srcImage 图片对象
     * @param radius   圆角大小
     * @return java.awt.image.BufferedImage
     * @author wangzhiwen
     * @date 2019/8/21 16:10
     */
    private static BufferedImage clipRounded(BufferedImage srcImage, int radius) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gs = image.createGraphics();

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));

        gs.drawImage(srcImage, 0, 0, null);
        gs.dispose();
        return image;
    }

    /**
     * 功能描述:把图片转成Base64字符串
     *
     * @param buffImg BufferedImage
     * @return java.lang.String
     * @author wangzhiwen
     * @date 2019/8/21 16:09
     */
    public static String generateWaterFile(BufferedImage buffImg) {
        /*String savePath = "E:/log/new1.png";
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "png", baos);
            byte[] bytes = baos.toByteArray();
            String imgStr = Base64.encodeBase64String(bytes);
            return imgStr;
        } catch (IOException ex) {
            SysLog.error(ex);
        }
        return null;
    }

    /**
     * 功能描述:
     *
     * @param backFile       背景图片
     * @param logoFile       logo图片
     * @param content        二维码内容
     * @param width          二维码图片宽度
     * @param height         二维码图片高度
     * @param backWidth      背景图宽度
     * @param backHeight     背景图高度
     * @param logoWidth      logo宽度
     * @param logoHeight     logo高度
     * @param marginX        二维码距离底图x轴距离
     * @param marginY        二维码距离底图y轴距离
     * @param margin         边框
     * @param colorInt       二维码颜色（RGB）new Color(192,14,235).getRGB();
     * @param colorBackInt   二维码背景颜色 new Color(255, 255, 255).getRGB();
     * @param backClipRadius 背景图片圆角半径
     * @param logoClipRadius logo图片圆角半径
     * @return String 图片转成base64字符串
     * @author wangzhiwen
     * @date 2019/8/21 17:31
     */
    public static String generateQrCodeByFile(MultipartFile backFile, MultipartFile logoFile, String content, int width, int height,
                                              int backWidth, int backHeight, int logoWidth, int logoHeight,
                                              int marginX, int marginY, int margin, int colorInt, int colorBackInt, int backClipRadius, int logoClipRadius) {
        BufferedImage image = initQrCode(content, width, height, margin, colorInt, colorBackInt);//二维码初使化
        if (null != logoFile) {
            image = addLogoImg(image, logoFile, logoWidth, logoHeight, logoClipRadius);//添加logo
        }

        if (null != backFile) {
            image = addBackImg(backFile, image, marginX, marginY, backWidth, backHeight, backClipRadius);//加载背景图
        }
        String imgStr = generateWaterFile(image);//把图片转成base64字符串
        return imgStr;
    }

    /**
     * 功能描述:
     *
     * @param logoFile       logo图片
     * @param content        二维码内容
     * @param width          二维码图片宽度
     * @param height         二维码图片高度
     * @param logoWidth      logo宽度
     * @param logoHeight     logo高度
     * @param margin         边框
     * @param colorInt       二维码颜色（RGB）new Color(192,14,235).getRGB();
     * @param colorBackInt   二维码背景颜色 new Color(255, 255, 255).getRGB();
     * @param logoClipRadius logo图片圆角半径
     * @return String 图片转成base64字符串
     * @author wangzhiwen
     * @date 2019/8/21 17:31
     */
    public static String generateQrCodeByFile(MultipartFile logoFile, String content, int width, int height,
                                              int logoWidth, int logoHeight, int margin, int colorInt, int colorBackInt, int logoClipRadius) {
        BufferedImage image = initQrCode(content, width, height, margin, colorInt, colorBackInt);//二维码初使化
        if (null != logoFile) {
            image = addLogoImg(image, logoFile, logoWidth, logoHeight, logoClipRadius);//添加logo
        }
        // 把图片转成base64字符串
        String imgStr = generateWaterFile(image);
        return imgStr;
    }

    /**
     * 功能描述:
     *
     * @param content      内容
     * @param width        二维码宽度
     * @param height       二维码高度
     * @param margin       白边
     * @param colorInt     二维码颜色（RGB）new Color(192,14,235).getRGB();
     * @param colorBackInt 二维码背景颜色 new Color(255, 255, 255).getRGB();
     * @return java.awt.image.BufferedImage
     * @author wangzhiwen
     * @date 2019/8/21 16:40
     */
    public static BufferedImage initQrCode(String content, int width, int height, int margin, int colorInt, int colorBackInt) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
            hints.put(EncodeHintType.MARGIN, margin); // 设置白边
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//容错比例，30%最高
            // 生成二维码
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            // 二维矩阵转为一维像素数组
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    image.setRGB(x, y, matrix.get(x, y) ? colorInt : colorBackInt);
                }
            }
            return image;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        throw BaseServiceException.initException(ConstantsMsg.QR_NO_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述: 添加logo
     *
     * @param image          二维码图片
     * @param logoFile       logo图片
     * @param logoWidth      logo宽度
     * @param logoHeight     logo高度
     * @param logoClipRadius logo图片圆角半径
     * @return java.awt.image.BufferedImage 添加logo后的图片
     * @author wangzhiwen
     * @date 2019/8/21 17:29
     */
    private static BufferedImage addLogoImg(BufferedImage image, MultipartFile logoFile, int logoWidth, int logoHeight, int logoClipRadius) {
        try {
            ByteArrayInputStream in = (ByteArrayInputStream) logoFile.getInputStream();
            // 获取logo
            BufferedImage logoBuffImg = ImageIO.read(in);
            logoBuffImg = setImgSizeClipRounded(logoBuffImg, logoWidth, logoHeight, logoClipRadius);
            // 计算图片放置位置
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;

            Graphics2D graphics = (Graphics2D) image.getGraphics();//二维码内容
            // 开始绘制图片
            graphics.drawImage(logoBuffImg, x, y, logoWidth, logoHeight, null);
            //Shape shape = new RoundRectangle2D.Float(x, y, image.getWidth(), image.getHeight(), 6, 6);//
            graphics.setStroke(new BasicStroke(3f));//线条宽度
            //graphics.draw(shape);
            graphics.dispose();
            return image;
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        throw BaseServiceException.initException(ConstantsMsg.QR_NO_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述:添加背景图片
     *
     * @param backImgFile 背景图片
     * @param waterImg    二维码图片
     * @param x           二维码在背景图上的x坐标
     * @param y           二维码在背景图上Y坐标
     * @param backWidth   背景图宽
     * @param backHeight  背景图高
     * @param clipRadius  背景图片圆角半径
     * @return java.awt.image.BufferedImage 操作后的图片
     * @author wangzhiwen
     * @date 2019/8/21 17:25
     */
    private static BufferedImage addBackImg(MultipartFile backImgFile, BufferedImage waterImg, int x, int y, int backWidth, int backHeight, int clipRadius) {
        try {
            ByteArrayInputStream in = (ByteArrayInputStream) backImgFile.getInputStream();
            // 获取底图
            BufferedImage buffImg = ImageIO.read(in);
            //修改底图大小,并切角
            buffImg = setImgSizeClipRounded(buffImg, backWidth, backHeight, clipRadius);
            // 创建Graphics2D对象，用在底图对象上绘图
            Graphics2D g2d = buffImg.createGraphics();
            int waterImgWidth = waterImg.getWidth();// 获取二维码的宽度
            int waterImgHeight = waterImg.getHeight();// 获取二维码的高度

            // 绘制
            g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
            g2d.dispose();// 释放图形上下文使用的系统资源

            return buffImg;
        } catch (Exception ex) {
            SysLog.error(ex);
        }

        throw BaseServiceException.initException(ConstantsMsg.QR_NO_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述:
     *
     * @param backImgPath    背景图片路径
     * @param logoPath       logo图片路径
     * @param content        二维码内容
     * @param width          二维码图片宽度
     * @param height         二维码图片高度
     * @param backWidth      背景图宽度
     * @param backHeight     背景图高度
     * @param logoWidth      logo宽度
     * @param logoHeight     logo高度
     * @param marginX        二维码距离底图x轴距离
     * @param marginY        二维码距离底图y轴距离
     * @param margin         边框
     * @param colorInt       二维码颜色（RGB）new Color(192,14,235).getRGB();
     * @param colorBackInt   二维码背景颜色 new Color(255, 255, 255).getRGB();
     * @param backClipRadius 背景图片圆角半径
     * @param logoClipRadius logo图片圆角半径
     * @return String 图片转成base64字符串
     * @author wangzhiwen
     * @date 2019/8/21 17:31
     */
    public static String generateQrCodeByFilePath(String backImgPath, String logoPath, String content, int width, int height,
                                                  int backWidth, int backHeight, int logoWidth, int logoHeight,
                                                  int marginX, int marginY, int margin, int colorInt, int colorBackInt, int backClipRadius, int logoClipRadius) {
        try {
            //把背景图片转成MultipartFile对象
            MultipartFile multipartFile = null;
            if (StringUtils.isNotEmpty(backImgPath)) {
                File file = new File(backImgPath);
                FileInputStream input = new FileInputStream(file);
                multipartFile = new MockMultipartFile("file", file.getName(), "image/jpg", IOUtils.toByteArray(input));
            }
            // 把logo图片转成MultipartFile对象
            MultipartFile multipartLogoFile = null;
            if (StringUtils.isNotEmpty(logoPath)) {
                File file = new File(logoPath);
                FileInputStream input = new FileInputStream(file);
                multipartLogoFile = new MockMultipartFile("file", file.getName(), "image/jpg", IOUtils.toByteArray(input));
            }

            return generateQrCodeByFile(multipartFile, multipartLogoFile, content, width, height, backWidth, backHeight, logoWidth, logoHeight,
                    marginX, marginY, margin, colorInt, colorBackInt, backClipRadius, logoClipRadius);
        } catch (Exception ex) {
            SysLog.error(ex);
        }
        throw BaseServiceException.initException(ConstantsMsg.QR_NO_FAIL, ServiceErrCode.SERVER_ERR.getCode());
    }

    /**
     * 功能描述:修改图片大小，并切角
     *
     * @param buffImg    图片
     * @param width      图片新宽
     * @param height     图片新高
     * @param clipRadius 切角半径
     * @return java.awt.image.BufferedImage
     * @author wangzhiwen
     * @date 2019/8/22 9:12
     */
    private static BufferedImage setImgSizeClipRounded(BufferedImage buffImg, int width, int height, int clipRadius) {
        if (width > 0 && width != buffImg.getWidth()) {
            //修改底图大小
            Image newImage = buffImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage newBuffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D g2d = newBuffImg.createGraphics();
            g2d.drawImage(newImage, 0, 0, null);

            buffImg = clipRounded(newBuffImg, clipRadius);
        }
        return buffImg;
    }

    public static void main(String[] args) {
        try {
            generateQrCodeByFilePath(null, "D:/log/qr-logo.png", "http://www.baidu.com",
                    150, 150
                    , 126, 127, 50, 50,
                    10, 10, 0,
                    new Color(0, 0, 0).getRGB(), new Color(255, 255, 255).getRGB(), 40, 30);
        } catch (Exception e) {
            SysLog.error(e);
        }
    }
}
