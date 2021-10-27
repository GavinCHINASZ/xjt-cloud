package com.xjt.cloud.cas.server;

import com.xjt.cloud.cas.server.config.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.data.source.dynamic.DataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.xjt.cloud")//第三方包扫描路径
@MapperScan("com.xjt.cloud.**.server.dao")//mybatis dao扫描路径
@ComponentScan({"com.xjt.cloud"})//注解扫描路径
@EnableConfigurationProperties({AuthorizationParam.class, DataSourceConfig.class})
@ServletComponentScan(basePackages ="com.xjt.cloud.commons.filter")
public class XjtCloudCasServerApplication {

    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudCasServerApplication.class, args));
    }
}

