package com.xjt.cloud.safety.api;

import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.xjt.cloud.*.*.dao")
@ComponentScan({"com.xjt.cloud"})
@EnableCaching
@EnableConfigurationProperties({AuthorizationParam.class})
@ServletComponentScan(basePackages = "com.xjt.cloud.commons.filter")
public class XjtCloudSafetyMaintenanceApiManageApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudSafetyMaintenanceApiManageApplication.class, args));
        //SpringApplication.run(XjtCloudSysManageApplication.class, args);
    }
}
