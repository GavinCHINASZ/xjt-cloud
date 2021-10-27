package com.xjt.cloud.admin.manage;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.ftp.utils.FtpConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.xjt.cloud")//第三方包扫描路径
@MapperScan("com.xjt.cloud.*.*.dao")//mybatis dao扫描路径
@EnableScheduling //Quartz定时任务
@ComponentScan({"com.xjt.cloud"})//注解扫描路径
@EnableConfigurationProperties({FtpConfig.class, com.xjt.cloud.cas.client.adapters.AuthorizationParam.class})
public class XjtCloudAdminManageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SysLog.error("开始启动项目！");
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudAdminManageApplication.class, args));
        SysLog.error("项目启动成功！");
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}
