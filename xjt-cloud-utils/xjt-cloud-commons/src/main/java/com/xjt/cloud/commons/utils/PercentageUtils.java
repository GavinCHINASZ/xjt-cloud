package com.xjt.cloud.commons.utils;

import java.text.NumberFormat;

/**
 * 百分比工具类
 *
 * @author  huanggc
 * @date 2019/8/13
 */
public class PercentageUtils {

    /**
     * 功能描述: 求两个int数的百分比后一位
     * @param num1 除数
     * @param num2 被除数
     * @return String
     */
    public static String percentageOne(int num1, int num2) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(1);
        String result = numberFormat.format((float) num1 / (float) num2 * 100);
        return result;
    }

    /**
     * 数字转换中文
     *
     * @param number number
     * @return String
     */
    public static String numberToChinese(String number){
        final String[] c = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "点"};
        String str = "";
        // 得到输入!
        try{
            char[] temp = number.toCharArray();
            for(int i = 0; i < temp.length; i++){
                // 转成数字
                switch (temp[i]){
                    case '1': str += c[1];break;
                    case '2': str += c[2];break;
                    case '3': str += c[3];break;
                    case '4': str += c[4];break;
                    case '5': str += c[5];break;
                    case '6': str += c[6];break;
                    case '7': str += c[7];break;
                    case '8': str += c[8];break;
                    case '9': str += c[9];break;
                    case '0': str += c[0];break;
                    case '.': str += c[10];break;
                    default:
                        str += temp[i];
                        break;
                }
            }
        }catch (Exception e){
            SysLog.error(e);
        }
        return str;
    }

}
