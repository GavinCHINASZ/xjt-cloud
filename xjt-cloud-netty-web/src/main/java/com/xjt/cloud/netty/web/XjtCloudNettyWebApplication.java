package com.xjt.cloud.netty.web;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.netty.web.common.SpringBootInitUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//不启用数据库配置
@ComponentScan(basePackages ={"com.xjt.cloud"})
public class XjtCloudNettyWebApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudNettyWebApplication.class, args));
        SpringBootInitUtils.SpringBootServletInitializer();//启动完成后，调用的方法，可以做一些被使化设置
    }
}
