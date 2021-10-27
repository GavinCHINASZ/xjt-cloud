package com.xjt.cloud.commons.utils.img;

import com.xjt.cloud.commons.utils.Base64DecodeMultipartFile;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

/**
 * @ClassName ImageFindDemo
 * @Description 图片计算工具类
 * @Author wangzhiwen
 * @Date 2021/1/28 14:01
 **/
public class ImgComputeUtils {
    private static int scrShotImgWidth;       //屏幕截图宽度
    private static int scrShotImgHeight;       //屏幕截图高度
    private static int keyImgWidth;         //查找目标图片宽度
    private static int keyImgHeight;         //查找目标图片高度
    private static String[][] newImageRGBData;  //屏幕截图RGB数据
    private static String[][] keyImageRGBData;     //查找目标图片RGB数据


    /**
     * @Description 以图片路径比较图片是否存在
     *
     * @param keyImagePath 要比较的小图
     * @param newImagePath 大图
     * @author wangzhiwen
     * @date 2021/2/4 14:09
     * @return void
     */
    public static boolean imgComparisonByPath(String keyImagePath,String newImagePath,int differ){
        return comparisonParameterInit(getBfImageFromPath(newImagePath),getBfImageFromPath(keyImagePath),differ);
    }

    /**
     * @Description 以图片比较图片是否存在
     *
     * @param keyImage 要比较的小图
     * @param newImage 大图
     * @author wangzhiwen
     * @date 2021/2/4 14:09
     * @return void
     */
    public static boolean imgComparisonByBufferedImage(BufferedImage newImage,BufferedImage keyImage,int differ){
        return comparisonParameterInit(newImage,keyImage,differ);
    }

    /**
     * @Description 以图片比较图片是否存在
     *
     * @param keyImage 要比较的小图
     * @param multipartFile 大图
     * @author wangzhiwen
     * @date 2021/2/4 14:09
     * @return void
     */
    public static boolean imgComparisonByMultipartFile(MultipartFile multipartFile, BufferedImage keyImage,int differ){
        return comparisonParameterInit(Base64DecodeMultipartFile.MultipartFileToBase64(multipartFile),keyImage,differ);
    }

    /**
     * @Description 以图片Base64比较图片是否存在
     *
     * @param keyImage 要比较的小图
     * @param newImage 大图
     * @author wangzhiwen
     * @date 2021/2/4 14:09
     * @return void
     */
    public static boolean imgComparisonByBase64(String newImage,String keyImage,int differ){
        return comparisonParameterInit(Base64DecodeMultipartFile.base64ToBufferedImage(newImage),Base64DecodeMultipartFile.base64ToBufferedImage(keyImage),differ);
    }

    /**
     * @Description 查找图片数据数初使化
     *
     * @param newImage 大图
     * @param keyImage 小图
     * @author wangzhiwen
     * @date 2021/2/4 14:13
     * @return void
     */
    private static boolean comparisonParameterInit(BufferedImage newImage,BufferedImage keyImage,int differ){
        newImageRGBData = getImageGRB(newImage);
        keyImageRGBData = getImageGRB(keyImage);
        scrShotImgWidth = newImage.getWidth();
        scrShotImgHeight = newImage.getHeight();
        keyImgWidth = keyImage.getWidth();
        keyImgHeight = keyImage.getHeight();
        //开始查找
        return comparisonImage(differ);
    }

    /**
     * 从本地文件读取目标图片
     * @param keyImagePath - 图片绝对路径
     * @return 本地图片的BufferedImage对象
     */
    private static BufferedImage getBfImageFromPath(String keyImagePath) {
        BufferedImage bfImage = null;
        try {
            bfImage = ImageIO.read(new File(keyImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bfImage;
    }

    /**
     * @Description 根据BufferedImage获取图片RGB数组
     *
     * @param bfImage
     * @author wangzhiwen
     * @date 2021/2/4 14:07
     * @return java.lang.String[][]
     */
    private static String[][] getImageGRB(BufferedImage bfImage) {
        int width = bfImage.getWidth();
        int height = bfImage.getHeight();
        String[][] result = new String[width][height];
        int[] rgb = new int[3];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //使用getRGB(w, h)获取该点的颜色值是ARGB，而在实际应用中使用的是RGB，所以需要将ARGB转化成RGB，即bufImg.getRGB(w, h) & 0xFFFFFF。
                int pixel = bfImage.getRGB(x, y);
                Color color = new Color(pixel);
                rgb[0] = color.getRed();
                rgb[1] = color.getGreen();
                rgb[2] = color.getBlue();
                /*rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);*/
                result[x][y] = rgb[0] + "," + rgb[1] + "," + rgb[2];
            }
        }
        return result;
    }

    /**
     * @Description
     *
     * @param w 生成找大图中找到的小图
     * @author wangzhiwen
     * @date 2021/2/4 14:07
     * @return void
     */
    private static void generateWaterFile(int w,int h) {
        String savePath = "E:/img/test/new" + w + ".png";
        BufferedImage b = new BufferedImage(keyImgWidth, keyImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = b.getGraphics();
        Color[][] arr = new Color[keyImgWidth][keyImgHeight];
        for(int x = 0; x < keyImgWidth; x++) {
            for(int y = 0; y < keyImgHeight; y++) {
                String[] rgb = newImageRGBData[w + x][h + y].split(",");
                arr[x][y] = new Color(Integer.parseInt(rgb[0]),Integer.parseInt(rgb[1]),Integer.parseInt(rgb[2]));
                g.setColor(arr[x][y]);
                g.fillRect( x, y,1, 1);
            }
        }

        try {
            ImageIO.write(b, "png", new FileOutputStream(new File(savePath)));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @Description 查找小图是否存在于大力中
     *
     * @author wangzhiwen
     * @date 2021/2/4 14:06
     * @return void
     */
    private static boolean comparisonImage(int differ) {
        //遍历屏幕截图像素点数据
        for(int w = 0; w <= scrShotImgWidth - keyImgWidth; w++) {
            for(int h = 0; h <= scrShotImgHeight - keyImgHeight; h++) {
                //根据目标图的尺寸，得到目标图四个角映射到屏幕截图上的四个点，
                //判断截图上对应的四个点与图B的四个角像素点的值是否相同，
                //如果相同就将屏幕截图上映射范围内的所有的点与目标图的所有的点进行比较。
                if(computeAngle(w,h,differ)){
                    boolean isFinded = isMatchAll(h, w,differ);
                    //如果比较结果完全相同，则说明图片找到，填充查找到的位置坐标数据到查找结果数组。
                    if(isFinded) {
                        //generateWaterFile(w,h);//生成查找到的图片
                        SysLog.error("图片存在");
                        return true;
                    }
                }
            }
        }
        SysLog.error("图片不存在");
        return false;
    }

    /**
     * @Description 以大图的宽高坐标，计算相对应的点是否相似
     *
     * @param w x坐标
     * @param h y坐标
     * @author wangzhiwen
     * @date 2021/2/4 14:03
     * @return boolean
     */
    private static boolean computeAngle(int w,int h, int differ){
        int width;
        int height;
        for(int i = 1;i < 50;i++){
            width = i == 1 ? 1 : keyImgHeight / i;//二分之一
            height = i == 1 ? 1 : keyImgWidth / i ;
            if (!computeXY(width,height,w + width,h + height,differ) || //与左上角判断是否相似
                    !computeXY(width,keyImgHeight - height,w + width,h + keyImgHeight - height,differ) || //与左下角判断是否相似
                    !computeXY(keyImgWidth - width,height,w + keyImgWidth - width,h + height,differ) ||  //与右上角判断是否相似
                    !computeXY(keyImgWidth - width,keyImgHeight - height,w + keyImgWidth - width,h + keyImgHeight - height,differ)){
                return false;
            }
        }
        return true;
    }

    /**
     * @Description 根据两张图片的xy的像素判断像素点是否相似
     *
     * @param keyW
     * @param keyH
     * @param newW
     * @param newH
     * @author wangzhiwen
     * @date 2021/2/1 13:40
     * @return boolean
     */
    private static boolean computeXY(int keyW,int keyH,int newW,int newH,int differ){
        String[] keyRGB = keyImageRGBData[keyW][keyH].split(",");
        String[] newRGB = newImageRGBData[newW][newH].split(",");

        if (Math.abs(Integer.parseInt(keyRGB[0]) - Integer.parseInt(newRGB[0])) > differ || Math.abs(Integer.parseInt(keyRGB[1]) - Integer.parseInt(newRGB[1])) > differ ||
        Math.abs(Integer.parseInt(keyRGB[2]) - Integer.parseInt(newRGB[2])) > differ
                /*|| Math.abs(Integer.parseInt(keyRGB[0] + keyRGB[1] + keyRGB[2] ) - Integer.parseInt(newRGB[0] + newRGB[1] +newRGB[2])) > 200*/
        ){
            return false;
        }

        /*double semblance = Math.abs((255 - (Math.abs(Integer.parseInt(keyRGB[0]) - Integer.parseInt(newRGB[0])) * 255 * 0.297 +
                Math.abs(Integer.parseInt(keyRGB[1]) - Integer.parseInt(newRGB[1])) * 255 * 0.593 +
                        Math.abs(Integer.parseInt(keyRGB[2]) - Integer.parseInt(newRGB[2])) * 255 * 11.0 / 100)) / 255);
       */
        //SysLog.error(semblance);

        return true;
    }

    /**
     * 判断屏幕截图上目标图映射范围内的全部点是否全部和小图的点一一对应。
     * @param h - 与目标图左上角像素点想匹配的屏幕截图y坐标
     * @param w - 与目标图左上角像素点想匹配的屏幕截图x坐标
     * @return
     */
    private static boolean isMatchAll(int h, int w,int differ) {
        int biggerH = 0;
        int biggerW = 0;
        int similarity = 0;//相似数
        int dissimilarity = 0;//不相似数
        for(int smallerW = 0; smallerW < keyImgWidth; smallerW++) {
            biggerW = w + smallerW;
            for(int smallerH = 0; smallerH < keyImgHeight; smallerH++) {
                biggerH = h + smallerH;
                if(biggerH >= scrShotImgHeight || biggerW >= scrShotImgWidth) {
                    return false;
                }
                if (!computeXY(smallerW,smallerH,biggerW,biggerH,differ)){
                    dissimilarity++;
                }else{
                    similarity++;
                }
            }
        }
        Double similarityRate = Double.parseDouble(similarity + "") / Double.parseDouble((similarity + dissimilarity) + "");
        SysLog.error("相似像素数量：" + similarity + " 不相似像素数量：" + dissimilarity + " 相似率：" + similarityRate + "");
        if (similarityRate > 0.8d){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String keyImagePath = "E:/img/test/1612492329009.jpeg";
        String newImagePath = "E:/img/test/1612492055645.jpeg";

        try {
            String key1 = "data%3Aimage%2Fjpeg%3Bbase64%2C%2F9j%2F4AAQSkZJRgABAQAAAQABAAD%2F2wBDAAMCAgICAgMCAgIDAwMDBAYEBAQEBAgGBgUGCQgKCgkICQkKDA8MCgsOCwkJDRENDg8QEBEQCgwSExIQEw8QEBD%2F2wBDAQMDAwQDBAgEBAgQCwkLEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBD%2FwAARCABtAG0DASIAAhEBAxEB%2F8QAHQAAAAYDAQAAAAAAAAAAAAAAAgMEBQYJAQcIAP%2FEAD4QAAEDAwIDBgQEBAILAAAAAAECAwQABREGIQcSMQgTIkFRYQkUI4EVMpHhFkJScZKhGCQ1Q1RicoKywfD%2FxAAcAQACAgMBAQAAAAAAAAAAAAAFBgQHAQMIAgD%2FxAA2EQABAgQEAwYEBAcAAAAAAAABAgMABAUREiExQQYTUQcUImFxgZGhscEVI1LRJTI0QmKC4f%2FaAAwDAQACEQMRAD8AtToCiQDt50Oi15G9fRgwhulyi2uFInz3kRo8dtTi3nFAJSkbkk1wKrtmaH0xedUwI9mkT3pM50JlNKBStOeo26V23xN0dD13oi86XuElxiPcIrjaltKwobeVUz3OzI01frvpxl9CmrZMcitOqT4lJSo7n1NAq5MOMBHLVbOLf7LKJJVdM13pBcsE5Xw2F73uLbgRv%2BT2sYiS4qFpx9SlqKvEP2qO3XtSaulpU3brKwyDsCtGSK06nnXjupiFHGeUbYoaQ%2BndUkDPr0pYJdcPictfyEXw1J02Xs4JPEAMyVEj6xIr3xV4g39JRNu7zSFHPK14R%2Faog8mTIkmVI7x5R6lxRJpehLjqT3MxtSh1SKLdadQrCpSBkedR1U%2FETicuYNs8SIkmsUvKJCeo%2FfWEpbkHA7sjBzttQi2%2FzZS3g%2B3l715UuQ2eVl1DuOpFJnbtMb2UlOev2rKaULWCo1v8eICbrZ19%2FiekdA9iXXcPQfHNhV6ntwrfdI7jS3HlcqS4QAlOfU1anCfRIYbebUFIWAUkHII9qpa4G6cPFzjNYOHl0mmHDfc%2BZU83srwEHH3q5nTtpj2G0xbJEUpTMFpLKCo5JAHUmnGhNLZluUrQaRzJ2sT0lVKwmfZulxYAUm2VxuDqYdq9WKzRqKtj1FLOc%2B2%2BKMzRSlBWc5GPSvowYR3Uk2yUeX%2FcLwMb9KpN4lYGudUHG6ro90P%2FADGrsbqUptcpzmJAYXkZ9qpO4lEHXGplAjAuj3%2FkaXq8BzGb6X%2B0XV2Tf0NSI%2FQjP%2FaIZYSsX1IKlFJAHLnrT3qNC0cndFRBz4U0hstnlCcm5uLDbYGTzHGBU109pDUmrX1KsFqW60FYVJfSUtD%2FALsYoJME95xtpBtFsUqWSzRSxOOFsrKrbkp2iD6WYmRrm5IlNrSyRtnzpXqBLzsgONNkpIOMGtuI4KugFF01paobpHibbkpVg0lkcHJrSeWw6ktc90H8rklIzXguOBzmeH0iQJWQ%2FD%2B5Y3QnXFbf9o09plSgiQhwkLzsDQ7zztKWOY5KalV407OsUws3a2Liknd1Kfpq%2FsfOo1f4chBU%2BUEspBwr1FbWn8boxix6dfeB9SpxapeGVUXcOZ2VYa%2FKNmdilzvO0zpgnqW3Nz9quWa5hkqGM9Nqpn7ExSe0pphYIwEOY367irlkuBO2SRTVTLqYxeZjn7tAV%2FEW1HQtpI9%2FKDwrO3Kazn2oCVZPhOfvWT186IAdYRvSG%2B73%2B0WCP83ergxDZJwFvKCRmtB8du13pPg6uAmEW738%2FlRMdwLCE%2B%2BDUJ7fmqrTL4ScltvqFSGJIJQy74iM48qruS7LfbQiXLekEp27xfNy%2Bwz5UrViuKlVlhnXrF69mvZhI8RtN1CqLIHj%2FLIIJtob9I7wc%2BJBYH4zra9IPkLQUpwdhkf3rh%2FUExrUmpbvfi2GWbhNclpb%2FpCiTj%2FOm9bJ70hCQM70rtEEXW5xbUFYDy%2Fqq%2FpSN6Xkz01Prs9bw5%2FaLtTwrQODZYrpTZDj4AKSbjLO8SjS%2BnLS5EGrNYr7mzRj9GMPzSVDyx6bVnUPFrUN8bFvsI%2FBbWgFKI8Xw8yfIq96a9Z3dd3ujdtjDlt9sSEMs%2Fyqc6E%2F%2B6aFBEcEJHsK8TKy8LpNkwTpsixL8svjmPKySDmBvkPaErvOtSlvPqUtW5JVkk%2B9HRlyGHA7ElKbcTvzBVI3JbCHS0882lR8ielDjuMvKUGX0rUnyBoSUf3BJt1h0TMgpwlab7jw2Hla94mVs4izwyLTqxn8Xtyzgh0ZU15ZSfakmorNEhDv4Dhk2mWPpqVuUH0NMQSXU92oY86c9PTgkP2CWcxJAJbSd%2B7V7fep7LzrRAWolJ0hZqtNl5vE5KjC4NQND7ee4hZwO1DD4McW7Zr99hUyPCSsFlPorFdlyPiR2FKlKRpKUAN%2FCcfrvXC8ho5XGXjvG1HJohDHMFDGdvSpxqc5IK5KD4TmLwqngDhXiRlNQm2yHRZCrGwFsshn1i43RPGrRGqtL2zUDuoYMZy5MpcDDjqQpJPkfep%2FHmNSGkvNEqQoZSodCPUVR3bbhc0Xu0si7S2mzKbSQHCEoSCOgq5PRWqtNtaUtLP8QRnFIhMhRLic55R1ppo9WE8gh3IiOeO0fs8Rwe6hcq6Vhali2HQAi2fpFM8i43a7plyZdwlykfNPNnvHStKsE%2BRoEIEtd8vp5VtXjX2RtfdmzR0zV961Qm52wylhtoJAIKlbVpCJeFrZ5McoUOYfegblLLalY8yTF0yHH7NQabmJUAJCcOm%2B8P4CypTgIxg0LR0nF5myAd0tEA%2B9M8W5fVQ0VjDmd%2Feg6fnmFqd2Eo4DuRWvuxZaWU62y%2BIiUa6urTMm04qycYSPcG1%2FpD68so7ySoYKlZoiZNabiLnOjKWU5x70qukcmK6lH5gcgetNdokRLi0u3yhkHKVCtXJSpu6RcXH3vBWXqqk1NBJwKKVAE7KvhA9cvnDNDfiPIMyQ33i3ycew8qBIlRoEpmdE%2BmAoJcHqKQSol1tE96KiK46xnLSgnIwaPs9suN7uSESIqmorPiXzDGTRMol%2B7qIOVrWiuJR2eE80ltsh8OG6rbg5lR%2FT5RNWCl1AfSNnBzJB22oTbaUSUOjZST1pLMloYcRHa2KPClI8h6UrSlSW%2B9cIGBQbkBLAi1papl%2BpKba2sPIq3hNcZfPdjyJwFAZoxWGlHlOysUyolKkXFbhPhTkZryrnlavGDvtv6VKRKc9tAXqIAKrwkucmXIwKcVbztv6XyhyfZUJCRndXQ%2BYNOEHU16gypMQ3%2BcwpHJkCSsZGDiosq%2BqVc4MRaTzzXhHbJzso7Z%2Fzrqez%2FDR4n6ijov8AJ4gIQmew062kIHhBBOD%2BtSpekLWsKQqw8oD1btGYpkupMwApSzfMA9NL%2Bkde9tPgDq%2FtD8ME6P0bcWIc1D4c5pCuVKhnpVZHHHsv8S%2BzG5axqkOX1F2yllUIFwMlPkcVeB06Cma96W0%2FqEJ%2FHbNEnhs5R37YXy%2F2zTa8wl04jHMlOrMxTiEoPg6RQCudqBKi7%2FB17yghSf8AVVbClN7muRzB1SyhxvmIacQoYKVeYPoavdm8O9CMwpDydH2olLa1AGOnfAPtVOHEyDa5OsNU2pcVpmM7cn0hKejR5jjHpQifLcktAIuFG3yi0uEVzPFEtOrbc5ZZSlaD%2FlfL5iEVkvLN6htSmwFHl8aQc%2B1NN3s0iPK%2Bfs6j%2BbmKBUEadv2gLsSpZcgr%2FKsZKceW%2FStkWTVdrvLIW3JaQ5y5UFYoOpldO8N8SDuNM9j5xZcrPSvFQwukNzLYBUjSyrfzIJ1BOe9jDexrh2OktzouOXZQKdzWHdXSZxDVuiKSXPPlqQqiQpB71yKwsH%2BblrIZhxEZbYZbV1yMVjmMAXFgT5%2FaJnca44CgvqI3OEBXx39obrPanUKM25eJ5e6U%2BlF6kvDNqhEkhTqs4TneiL%2FrW3WpgnvQ68nokbnNQuGi5aynm4TwpiAyStwqOxHXArCGFTliQUoTqdL9LA6xDmqozw6lMrLELmTokHFhvqtZGltbfGHFNxdh2Vyc3DekyphKGWWUkqPqcU1Gde48Yd9o6894B%2FwysKJ%2F%2BFb%2FAOyEbZfe0jpaNLtrEuCllxCWHkgoI23I9atuVw00A%2Fu5o60qwc4%2BWR1%2FSj0hLpfb5h62%2BEVHxRW3KNNIk2yFJCAQob4rlXzirDQHw3%2BNHEC06a4hs6jjQ4stbc9MZ9ZS4yjIOMetWx6WtMiy6ctlnlOd49CiNMLX%2FUpKQCacIcWPBYbiQ2EMMNgJQ2gYCR6AelHnr0os2yhoWSIraeqL1RVd46QKilYOwz1oyiubBUQnpWzyiAbjOE1zUk26SMg%2FRWDjy2qlDiZ3Y1vqggHa6v8ANn15jV0GorpbbTaZcq5SmozJaWFKUrGSRVM%2BubPc7rrjU7sSI84w9cn1tOBBwpJUcEUvV0lJaWCMlfYxd%2FY8hbrFQQgHxISBlqQY19BLU%2B4Js1xSJEV1PmN07Ugu3DH5N5T9iufdJUSQ0Vb1K7foq9Q7mLgYj6uX%2BXkO1OUywXuevP4fIbA%2FLhJzQ0zS23rNkYTqNrw%2FDhlmZpmOoN%2FnoJwqBsbHa49rdI1imBruJ9KO6t056JVnArK7Pr2dgOyFtJHUrVWw7HpLUVrua5z8SUtKwQEEHalF007qKasFmDJbCs%2BEJOK2F9BXYtp9bZxCNCm1SvMXNvX2Tiyt62uYgNl0FbHH%2Fm7rcjKW2rmU0k7E%2B9Pt4S200qLEbSzHSjPKmllt0nqG0B0mzyni6okqKDtXrhYdTTOfu7BIGRg%2BE1nmqccBcVcDTyjwilS0jIFMs1hWrU6nzz1zif8AYpUD2k9L8qceBew8txVzUcpKcpB39RVN3ZAttysPaQ0zMvduehRGkrC31pISkkjqelXGwpDcpHzEd1DjK8FCknIIo5S7BkpB3v8AGKZ4%2BB%2FEGyEkDAkaZZA5QpA9qFWKzRKEmPUXkc1D39aLSeufUivowdbRwB8Qjire9N8QbHpLvVm1rjGUppCiCpQx1x5VzgjjfJSyhCLYwQkbdDn7neth%2FFImqjca9ONpSdrYVZCseYrkNN4d5QQjA9Ob9qWJ2RS4%2BVGOgeFK2afRWGQgbm%2Fr1jfSeOcxJx%2BEMH7prKuPUxI2s0cn1ymtC%2Firx3wr%2FF%2B1BVd3gccpx%2F1ftUT8PSBB1XFClG%2BCN%2BJ7QEwDxWVjP2oQ7QU0eL8Ejn9BWgfxZ3fwH%2FF%2B1BVd3ichJHl%2Bas9zTbONKuJl64fpHQQ7RUwD%2FYkfH2NC%2FwBI%2BUgcosMc%2BfRNc8i5u83Ng%2FrRbt3eCtk4%2B%2F7V6Eog5CNSuI1alH0joWZ2j33Y7ocsLASUHPIoJV06giu6%2Fh%2B8SrtxH4WzpFzUopgSxHZClcxCcHbNVESLu98u6VIz9M7c37VZz8KWWqRwavilJxi5%2BvsaIU9gtOi2kKHGlUE7Si0UC4KTfLr%2FANjuIVmgJVkbChYJ86PxTkf%2F2Q%3D%3D";
            long t = System.currentTimeMillis();

            // 获取底图
            imgComparisonByPath(StringUtils.urlDecode(keyImagePath), StringUtils.urlDecode(newImagePath),20);
            System.out.println(System.currentTimeMillis() - t);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}