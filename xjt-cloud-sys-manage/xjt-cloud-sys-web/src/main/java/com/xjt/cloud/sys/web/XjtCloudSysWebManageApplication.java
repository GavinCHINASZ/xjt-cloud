package com.xjt.cloud.sys.web;

import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.ftp.utils.FtpConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication(scanBasePackages = "com.xjt.cloud")//第三方包扫描路径
@MapperScan("com.xjt.cloud.*.*.dao")//mybatis dao扫描路径
@ComponentScan({"com.xjt.cloud"})//注解扫描路径
@EnableCaching
@EnableTransactionManagement //开启事务
@EnableAspectJAutoProxy(exposeProxy = true)//开启代理调用方法，用于事务
@EnableConfigurationProperties({AuthorizationParam.class,FtpConfig.class})
@ServletComponentScan(basePackages ="com.xjt.cloud.commons.filter")
public class XjtCloudSysWebManageApplication {
    public static void main(String[] args) {
        try {
            SysLog.logger.error("项目启动开始！");
            SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudSysWebManageApplication.class, args));
            SysLog.logger.error("项目启动成功！");
        }catch (Exception ex){
            SysLog.error(ex);
            SysLog.logger.error("项目启动失败！");
        }
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
