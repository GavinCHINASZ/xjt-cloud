package com.xjt.cloud.commons.filter;


import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/5/15 16:41
 * @Description: 自定义环境处理，在运行SpringApplication之前加载任意配置文件到Environment环境中
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     *
     * 功能描述: 加载资源文件
     *
     * @param environment
     * @param application
     * @return: void
     * @auther: wangzhiwen
     * @date: 2020/5/18 9:13
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,SpringApplication application) {
        try {
            InputStream is = new FileInputStream(System.getProperty("user.dir") + "/config/application.properties");//加载默认工作目录下的默认资源文件
            Properties propertiesAll = new Properties();
            Properties properties = new Properties();
            properties.load(is);
            propertiesAll.putAll(properties);
            //自定义配置文件
            String path = properties.getProperty("config.path");//获取外部资源文件目录
            if (StringUtils.isNotEmpty(path)){//判断是否有多个文件
                File directory = new File(path);
                path = directory.getCanonicalPath();//得到配置的路径的绝对目录
                String[] profiles = properties.getProperty("config.files.name").split(",");//猎获取多个资源文件名
                if (profiles != null && profiles.length > 0){//判断是否有多个文件
                    //循环添加外部资源文件
                    for (String profile : profiles) {
                        if (StringUtils.isNotEmpty(profile)) {
                            is = new FileInputStream(path + File.separator + "application-" + profile + ".properties");
                            properties = new Properties();
                            properties.load(is);
                            propertiesAll.putAll(properties);
                        }
                    }
                }
            }
            propertiesAll = replaceAllPlaceholder(propertiesAll);
            PropertyUtils.propertiesInit(propertiesAll);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("application", propertiesAll);
            environment.getPropertySources().addLast(propertySource);
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     *
     * 功能描述:把所有的${}替换成值
     *
     * @param propertiesAll
     * @return: java.util.Properties
     * @auther: wangzhiwen
     * @date: 2020/6/1 14:24
     */
    private Properties replaceAllPlaceholder(Properties propertiesAll){
        Enumeration<?> e = propertiesAll.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = propertiesAll.getProperty(key);
            value  = PropertyUtils.replacePlaceholder(propertiesAll, value);
            propertiesAll.setProperty(key, value);
        }
        return propertiesAll;
    }

}
