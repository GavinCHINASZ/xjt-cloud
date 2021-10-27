package com.xjt.cloud.project.web;

import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
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
@ServletComponentScan(basePackages ="com.xjt.cloud.commons.filter")
public class XjtCloudProjectWebManageApplication {
    public static void main(String[] args) {
        SysLog.error("开始启动项目！");
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudProjectWebManageApplication.class, args));
        SysLog.error("项目启动成功！");
        //SpringApplication.run(XjtCloudSysManageApplication.class, args);
    }
}
