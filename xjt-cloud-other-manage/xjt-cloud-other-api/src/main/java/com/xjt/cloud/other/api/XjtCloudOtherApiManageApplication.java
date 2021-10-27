package com.xjt.cloud.other.api;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.xjt.cloud"})
@EnableCaching
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//不启用数据库配置
public class XjtCloudOtherApiManageApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudOtherApiManageApplication.class, args));
    }
}
