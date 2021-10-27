package com.xjt.cloud.commons.utils;

/**
 * 转换工具类
 *
 * @author hunaggc
 */
public class ConvertUtils {

    /**
     * 功能描述: String数组 转换成 Long数组
     *
     * @param stringArray String数组
     * @return Long[]
     * @author hunaggc
     * @date 2020-05-15
     */
    public static Long[] stringToLong(String[] stringArray) {
        if (stringArray == null || stringArray.length < 1) {
            return null;
        }
        Long[] longArray = new Long[stringArray.length];

        for (int i = 0; i < longArray.length; i++) {
            try {
                longArray[i] = Long.valueOf(stringArray[i]);
            } catch (NumberFormatException e) {
                longArray[i] = Long.valueOf(0);
                continue;
            }
        }
        return longArray;
    }

    /**
     * 功能描述: String 转换成 Long数组
     *
     * @param string 字符串
     * @return Long[]
     * @author hunaggc
     * @date 2020-06-09
     */
    public static Long[] stringToLongArray(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        String[] split = string.split(",");
        Long[] longArray = new Long[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                longArray[i] = Long.valueOf(split[i]);
            } catch (NumberFormatException e) {
                longArray[i] = Long.valueOf(0);
                continue;
            }
        }
        return longArray;
    }
}
