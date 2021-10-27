package com.xjt.cloud.report.web;

import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
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
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = "com.xjt.cloud")//第三方包扫描路径
@MapperScan("com.xjt.cloud.*.*.dao")
@ComponentScan({"com.xjt.cloud"})
@EnableCaching
@EnableConfigurationProperties({AuthorizationParam.class, FtpConfig.class})
@ServletComponentScan(basePackages ="com.xjt.cloud.commons.filter")
public class XjtCloudReportWebManageApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudReportWebManageApplication.class, args));
    }

    /**
     *
     * 功能描述:修改上传文件大小限制
     *
     * @param
     * @return: javax.servlet.MultipartConfigElement
     * @auther: wangzhiwen
     * @date: 2019/8/9 16:51
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        DataSize dataSize= DataSize.ofMegabytes(10);//10MB
        //允许上传的文件最大值
        factory.setMaxFileSize(dataSize);
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(dataSize);
        return factory.createMultipartConfig();
    }
}
