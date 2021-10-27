package com.xjt.cloud.commons.utils;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.util.Properties;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 10:46
 * @Description: 资源文件读取工具类
 */
public class PropertyUtils {
    private static Properties properties;

    public static void propertiesInit(Properties allProperties){
        if (properties == null) {//双重锁模式，以免重复加载
            synchronized (PropertyUtils.class) {
                if (properties == null) {
                    properties = allProperties;
                }
            }
        }
    }
    /**
     *
     * 功能描述:以key取得资源文件值
     *
     * @param key
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/2 15:37
     */
    public static String getProperty(String key){
        if (properties == null) {//双重锁模式，以免重复加载
            synchronized(PropertyUtils.class){
                if(properties == null){
                    try {
                        properties = PropertiesLoaderUtils.loadProperties(new PathResource(System.getProperty("user.dir") + "/config/application.properties"));
                        String path = properties.getProperty("config.path");//获取导入的配置文件
                        if (StringUtils.isNotEmpty(path)){
                            File directory = new File(path);
                            path = directory.getCanonicalPath();//得到配置的路径的绝对目录
                            String[] fileNames = properties.getProperty("config.files.name").split(",");
                            if (fileNames != null && fileNames.length > 0) {//判断是否有多个文件
                                for (String fileName : fileNames) {
                                    if (StringUtils.isNotEmpty(fileName)) {
                                        //读取导入文件配置
                                        Properties tempProperties = PropertiesLoaderUtils.loadProperties(new PathResource(path + File.separator +"application-" + fileName + ".properties"));
                                        properties.putAll(tempProperties);//添加导入文件配置属性
                                    }
                                }
                            }
                        }
                    }catch (Exception ex){
                        SysLog.error(ex);
                    }
                }
            }  
        }
        String value = properties.getProperty(key);
        if (StringUtils.isEmpty(value)){
            SysLog.error("配置参数 key=" +  key + " 不存在！");
            SysLog.error(SysLog.getCodeLocation());
            return null;
        }
        return replacePlaceholder(properties,value);
    }

    /**
     * @Description 获取配置参数，如为null里返回空字符串
     *
     * @param key
     * @author wangzhiwen
     * @date 2020/11/11 14:45
     * @return java.lang.String
     */
    public static String getPropertyNull(String key){
        String value = getProperty(key);
        return StringUtils.isEmpty(value) ? "" : value;
    }

    /**
     * @Description 获取配置参数，数字
     *
     * @param key
     * @author wangzhiwen
     * @date 2020/11/11 14:45
     * @return java.lang.String
     */
    public static Integer getPropertyInteger(String key){
        String value = getProperty(key);
        return StringUtils.isEmpty(value) ? null : Integer.parseInt(value);
    }

    /**
     *
     * 功能描述: 替换占位符
     *
     * @param value
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/11/5 14:45
     */
    public static String replacePlaceholder(Properties properties,String value){
        if (StringUtils.isNotEmpty(value) && value.indexOf("${") != -1){
            int begin = value.indexOf("${") + 2;
            int end = value.indexOf("}");
            String key = value.substring(begin, end);
            value = value.replace("${" + key + "}", properties.getProperty(key));
            value = replacePlaceholder(properties,value);
        }
        return value;
    }
}
